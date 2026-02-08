package com.pluslatte.techgunsfix.mixin;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name("TechgunsFixCore")
public class TechgunsFixLoadingPlugin implements IFMLLoadingPlugin {

    /**
     * Constructor called by FML during CoreMod loading phase, before mod initialization.
     * 
     * Ensures Mixin framework is initialized and registers our mixin configuration.
     * Handles both scenarios: with and without UniMixins pre-installed.
     */
    public TechgunsFixLoadingPlugin() {
        // Check if MixinBootstrap has already been initialized (e.g., by UniMixins)
        // If not, initialize it ourselves
        try {
            MixinEnvironment.getCurrentEnvironment();
        } catch (Exception e) {
            // MixinBootstrap not yet initialized, initialize it now
            MixinBootstrap.init();
        }
        
        // Register our mixin configuration file
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
