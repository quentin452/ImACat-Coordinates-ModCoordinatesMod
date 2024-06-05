package fr.iamacat.mycoordinatesmods;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfigMixin;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.proxy.CommonProxy;
import fr.iamacat.mycoordinatesmods.utils.Reference;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.MOD_VERSION,
    acceptedMinecraftVersions = Reference.MC_VERSION)
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
    public void init(FMLInitializationEvent event) {}

    public static KeyBinding toggleKeyBinding;
    public static KeyBinding toggleKeyBinding2;

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
        if (CoordinatesConfigMixin.enableInputEvent) {
            KeyBinding[] keyBindings = {
                new KeyBinding("Toggle Coordinates Showing", Keyboard.KEY_T, "IamacatCoordinatesMod") };
            toggleKeyBinding = keyBindings[0];
            ClientRegistry.registerKeyBinding(toggleKeyBinding);
            KeyBinding[] keyBindings2 = {
                new KeyBinding("Toggle Coordinates Position", Keyboard.KEY_Y, "IamacatCoordinatesMod") };
            toggleKeyBinding2 = keyBindings2[0];
            ClientRegistry.registerKeyBinding(toggleKeyBinding2);
        }
    }
}
