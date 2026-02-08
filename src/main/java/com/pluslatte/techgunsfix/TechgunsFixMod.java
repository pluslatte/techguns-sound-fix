package com.pluslatte.techgunsfix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TechgunsFixMod.MODID, name = TechgunsFixMod.NAME, version = TechgunsFixMod.VERSION, dependencies = "required-after:techguns")
public class TechgunsFixMod {
    public static final String MODID = "techgunsfix";
    public static final String NAME = "Techguns Audio Fix";
    public static final String VERSION = "1.0.0";
    
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Techguns Audio Fix is loading...");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Techguns Audio Fix initialized successfully!");
    }
}
