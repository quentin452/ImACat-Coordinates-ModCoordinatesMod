package fr.iamacat.mycoordinatesmods.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public abstract class CommonProxy {

    public void registerRenders() {}
    
    public void init(FMLInitializationEvent event) {}
    
    public void postInit(FMLPostInitializationEvent event) {}
}
