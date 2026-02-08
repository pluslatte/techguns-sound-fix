package com.pluslatte.techgunsfix.mixin;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name("TechgunsFixCore")
public class TechgunsFixLoadingPlugin implements IFMLLoadingPlugin {

    /**
     * Constructor called by FML during CoreMod loading phase, before mod initialization.
     * 
     * Registers our mixin configuration with the Mixin framework.
     * Note: MixinBootstrap.init() should NOT be called here when using UniMixins,
     * as UniMixins handles Mixin initialization. Calling it again causes classloader conflicts.
     */
    public TechgunsFixLoadingPlugin() {
        // Only register our mixin configuration file
        // UniMixins (if present) has already initialized MixinBootstrap
        Mixins.addConfiguration("mixins.techgunsfix.json");
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
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
