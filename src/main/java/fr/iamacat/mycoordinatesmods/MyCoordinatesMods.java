package fr.iamacat.mycoordinatesmods;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.proxy.CommonProxy;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION)
public class MyCoordinatesMods {
    private CoordinatesEventHandler handler;


    private boolean showCoordinates = true;
    @Mod.Instance(Reference.MOD_ID)
    public static MyCoordinatesMods instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        CoordinatesConfig.setupAndLoad(event);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }
    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        CoordinatesEventHandler.init(event);
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
    }
}