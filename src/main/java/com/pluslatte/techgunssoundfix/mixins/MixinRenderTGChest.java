package com.pluslatte.techgunssoundfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import techguns.blocks.BlockTGChest;
import techguns.client.renderer.tileentity.RenderTGChest;
import techguns.tileentities.TGChestTileEnt;

/**
 * Mixin to fix a ClassCastException crash in Techguns' chest renderer.
 *
 * The problem: {@code RenderTGChest.renderTileEntityAt} starts by reading the
 * texture index via {@code ((BlockTGChest) te.getBlockType()).type}. If the
 * chest's actual block has been removed (creeper explosion, world corruption,
 * other mods, ...) but the {@code TGChestTileEnt} is left behind as a "ghost",
 * the block at that position is air ({@code BlockAir}) and the unchecked cast
 * throws:
 *
 * <pre>
 * java.lang.ClassCastException: net.minecraft.block.BlockAir
 *     cannot be cast to techguns.blocks.BlockTGChest
 *     at RenderTGChest.renderTileEntityAt(RenderTGChest.java:40)
 * </pre>
 *
 * This is hit hard by Angelica/Iris shaders, whose shadow pass
 * ({@code ShadowRenderer.renderTileEntity}) actively re-renders tile entities,
 * so a single ghost chest in view crashes the game on world load.
 *
 * The fix: inject at the head of {@code renderTileEntityAt} and skip rendering
 * entirely when the block at the tile entity's position is not a
 * {@code BlockTGChest} (including air or null). There is nothing valid to draw
 * for a ghost tile entity, so cancelling the render is the correct behaviour.
 */
@Mixin(value = RenderTGChest.class, remap = false)
public abstract class MixinRenderTGChest {

    @Inject(method = "renderTileEntityAt", at = @At("HEAD"), cancellable = true, remap = false, require = 1)
    private void techgunssoundfix$skipGhostChest(TGChestTileEnt te, double x, double y, double z, float partialTicks, CallbackInfo ci) {
        if (!(te.getBlockType() instanceof BlockTGChest)) {
            // Block was removed but the tile entity remains: skip rendering to
            // avoid the unchecked (BlockTGChest) cast crashing the render pass.
            ci.cancel();
        }
    }
}
