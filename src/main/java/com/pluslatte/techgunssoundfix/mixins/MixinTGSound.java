package com.pluslatte.techgunssoundfix.mixins;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import techguns.client.audio.TGSound;

/**
 * Mixin to fix TGSound positioning issue in Techguns mod.
 * 
 * The problem: Minecraft's coordinate system and mathematical polar coordinates
 * have different angle orientations:
 * - Minecraft: yaw=0°=South (+Z), -90°=East (+X)
 * - Polar coordinates: angle=0°=East (+X), 90°=North (+Z)
 * 
 * This mixin adjusts the yaw angle calculation to correct the sound position.
 */
@Mixin(value = TGSound.class, remap = false)
public abstract class MixinTGSound {

    /**
     * Modifies the yaw angle passed to polarOffsetXZ to correct the coordinate
     * system mismatch.
     * 
     * The polarOffsetXZ function is called in TGSound.func_73660_a (update method):
     * MathUtil.Vec2 pos = MathUtil.polarOffsetXZ(
     * (double)this.xPosF,
     * (double)this.zPosF,
     * 1.0,
     * (double)((EntityLivingBase)entity).renderYawOffset * Math.PI / 180.0
     * );
     * 
     * We need to adjust the angle to account for:
     * 1. Minecraft uses South as 0°, while polar coordinates use East as 0° (90°
     * difference)
     * 2. Minecraft rotates clockwise (negative), while polar coordinates rotate
     * counter-clockwise (positive)
     * 
     * The transformation: adjusted_angle = -(yaw - 90°) = -yaw + 90° = 90° - yaw
     * In radians: adjusted_angle = π/2 - yaw_radians
     */
    @Redirect(method = "func_73660_a", at = @At(value = "INVOKE", target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;", remap = false), remap = false, require = 1)
    private Object redirectPolarOffsetXZ(double x, double z, double radius, double angle) {
        // Adjust the angle to convert from Minecraft coordinates to polar coordinates
        // Original: yaw * PI / 180
        // Fixed: (90 - yaw) * PI / 180 = PI/2 - yaw * PI / 180
        double adjustedAngle = (Math.PI / 2.0) - angle;

        // Call the original method with the corrected angle
        try {
            Class<?> mathUtilClass = Class.forName("techguns.util.MathUtil");
            java.lang.reflect.Method method = mathUtilClass.getDeclaredMethod("polarOffsetXZ",
                    double.class, double.class, double.class, double.class);
            return method.invoke(null, x, z, radius, adjustedAngle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke MathUtil.polarOffsetXZ", e);
        }
    }
}
