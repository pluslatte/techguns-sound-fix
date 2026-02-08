package com.pluslatte.techgunssoundfix.mixins;

import org.spongepowered.asm.mixin.Mixins;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Mixin plugin for loading Techguns Sound Fix mixins.
 * This plugin is loaded early in the Minecraft startup process to ensure
 * mixins are applied before the target classes are loaded.
 */
@IFMLLoadingPlugin.Name("TechgunsSoundFixMixinLoader")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1001)
public class TechgunsSoundFixMixinPlugin implements IFMLLoadingPlugin {

    public TechgunsSoundFixMixinPlugin() {
        // Don't add mixin configuration here - MixinBootstrap hasn't been initialized
        // yet
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        // Mixin configuration is now loaded as a late mixin via
        // TechgunsSoundFixLateMixinLoader
        // This ensures Techguns classes are available before mixin application
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
