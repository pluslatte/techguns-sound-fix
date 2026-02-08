package com.pluslatte.techgunssoundfix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TechgunsSoundFix.MODID, version = TechgunsSoundFix.VERSION, name = TechgunsSoundFix.NAME)
public class TechgunsSoundFix {
    public static final String MODID = "techgunssoundfix";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Techguns Sound Fix";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Techguns Sound Fix loaded - fixing gun sound positioning");
    }
}
