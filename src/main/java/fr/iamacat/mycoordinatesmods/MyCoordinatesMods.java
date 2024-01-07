package fr.iamacat.mycoordinatesmods;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.proxy.CommonProxy;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION)
public class MyCoordinatesMods {
    private CoordinatesEventHandler handler;

    public static KeyBinding toggleKeyBinding;
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
    public static void postInit(FMLPostInitializationEvent event) {
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        KeyBinding[] keyBindings = {
                new KeyBinding("Toggle Coordinates", Keyboard.KEY_T, "IamacatCoordinatesMod")
        };
        toggleKeyBinding = keyBindings[0];
        ClientRegistry.registerKeyBinding(toggleKeyBinding);

        handler = new CoordinatesEventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
    }
}