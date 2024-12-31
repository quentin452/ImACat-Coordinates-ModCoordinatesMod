package fr.iamacat.mycoordinatesmods;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.proxy.CommonProxy;
import fr.iamacat.mycoordinatesmods.utils.Reference;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.MOD_VERSION,
    acceptedMinecraftVersions = Reference.MC_VERSION,
        dependencies = MyCoordinatesMods.DEPENDENCIES)
public class MyCoordinatesMods {
    public static final String DEPENDENCIES = "required-after:mixinbooter@[9.0,);required-after:configanytime@[3.0,);";
    private CoordinatesEventHandler handler;

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

    public static KeyBinding toggleKeyBinding,toggleKeyBinding2;

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
        KeyBinding[] keyBindings = { new KeyBinding("Toggle Coordinates Showing", Keyboard.KEY_T, "IamacatCoordinatesMod") };
        toggleKeyBinding = keyBindings[0];
        ClientRegistry.registerKeyBinding(toggleKeyBinding);
        KeyBinding[] keyBindings2 = { new KeyBinding("Toggle Coordinates Position", Keyboard.KEY_Y, "IamacatCoordinatesMod") };
        toggleKeyBinding2 = keyBindings2[0];
        ClientRegistry.registerKeyBinding(toggleKeyBinding2);
    }
}
