package com.pluslatte.techgunssoundfix.mixins;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import techguns.client.audio.TGSound;
import techguns.util.MathUtil;

/**
 * Mixin to fix TGSound positioning issue in Techguns mod.
 * 
 * The problem: Minecraft's coordinate system and mathematical polar coordinates
 * have different angle orientations:
 * - Minecraft: yaw=0°=South (+Z), -90°=East (+X)
 * - Polar coordinates: angle=0°=East (+X), 90°=South (+Z)
 * 
 * The polarOffsetXZ method calculates:
 * - new_x = x + radius * cos(angle)
 * - new_z = z + radius * sin(angle)
 * 
 * For standard polar coordinates:
 * - angle=0° → (+X direction, East)
 * - angle=90° → (+Z direction, South)
 * 
 * For Minecraft:
 * - yaw=0° → South (+Z)
 * - yaw=-90° → East (+X)
 * - yaw=180° → North (-Z)
 * - yaw=90° → West (-X)
 * 
 * Conversion formula: polar_angle = minecraft_yaw + 90°
 * In radians: angle = yaw_radians + π/2
 * 
 * This mixin adjusts the yaw angle calculation to correct the sound position
 * in both the constructor and update method.
 */
@Mixin(value = TGSound.class, remap = false)
public abstract class MixinTGSound {

    /**
     * Redirects polarOffsetXZ calls in the update method (func_73660_a) to fix
     * angle.
     */
    @Redirect(method = "func_73660_a", at = @At(value = "INVOKE", target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;", remap = false), remap = false, require = 1)
    private MathUtil.Vec2 redirectPolarOffsetXZInUpdate(double x, double z, double radius, double angle) {
        // Adjust angle: yaw_radians + π/2
        double adjustedAngle = angle + (Math.PI / 2.0);
        return MathUtil.polarOffsetXZ(x, z, radius, adjustedAngle);
    }

    /**
     * Redirects polarOffsetXZ calls in the constructor to fix angle.
     */
    @Redirect(method = "<init>(Ljava/lang/String;Lnet/minecraft/entity/Entity;FFZZZLtechguns/client/audio/TGSoundCategory;)V", at = @At(value = "INVOKE", target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;", remap = false), remap = false, require = 1)
    private MathUtil.Vec2 redirectPolarOffsetXZInConstructor(double x, double z, double radius, double angle) {
        // Adjust angle: yaw_radians + π/2
        double adjustedAngle = angle + (Math.PI / 2.0);
        return MathUtil.polarOffsetXZ(x, z, radius, adjustedAngle);
    }
}
