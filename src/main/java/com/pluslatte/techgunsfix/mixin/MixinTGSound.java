package com.pluslatte.techgunsfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin to fix the Techguns gun sound position offset issue.
 * 
 * The problem: TGSound uses polarOffsetXZ with the entity's yaw angle,
 * but there's a coordinate system mismatch:
 * - Minecraft: yaw=0°=South(+Z), -90°=East(+X)
 * - Polar coordinates: angle=0°=East(+X), 90°=North(+Z)
 * 
 * This causes sounds to be shifted to the left by 90 degrees.
 * 
 * The fix: Subtract π/2 (90 degrees) from the angle before it's used
 * in the polarOffsetXZ calculation to align with Minecraft's coordinate system.
 */
@Mixin(targets = "techguns.client.audio.TGSound", remap = false)
public class MixinTGSound {
    
    /**
     * Modifies the angle parameter before it's passed to MathUtil.polarOffsetXZ.
     * This corrects the coordinate system mismatch between Minecraft yaw angles
     * and mathematical polar coordinates.
     * 
     * The injection point is right before the polarOffsetXZ method is invoked
     * in the TGSound constructor.
     * 
     * @param angle The original angle calculated from entity yaw
     * @return The corrected angle (original - π/2)
     */
    @ModifyVariable(
        method = "<init>(Ljava/lang/String;Lnet/minecraft/entity/Entity;FFZZZLtechguns/client/audio/TGSoundCategory;)V",
        at = @At(
            value = "INVOKE",
            target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;",
            remap = false
        ),
        ordinal = 0,
        remap = false
    )
    private double fixGunSoundAngle(double angle) {
        // Subtract π/2 to convert from Minecraft coordinate system to standard polar coordinates
        return angle - Math.PI / 2.0;
    }
}
