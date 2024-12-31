package fr.iamacat.mycoordinatesmods;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import org.lwjgl.glfw.GLFW;

@Mod(Reference.MOD_ID)
public class MyCoordinatesMods {

    // Declare key bindings
    public static KeyBinding toggleKeyBinding;
    public static KeyBinding toggleKeyBinding2;

    public MyCoordinatesMods() {
        // Register the common setup method for modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register client setup code
        modEventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register config
        CoordinatesConfig.loadConfig(CoordinatesConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mycoordinatesmod.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Register common setup code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Register client-side only code here, like keybindings
        toggleKeyBinding = new KeyBinding("Toggle Coordinates Showing", GLFW.GLFW_KEY_T, "IamacatCoordinatesMod");
        toggleKeyBinding2 = new KeyBinding("Toggle Coordinates Position", GLFW.GLFW_KEY_Y, "IamacatCoordinatesMod");

        // Register key bindings with Forge
        ClientRegistry.registerKeyBinding(toggleKeyBinding);
        ClientRegistry.registerKeyBinding(toggleKeyBinding2);

        // Register the event handler for rendering coordinates
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        // Some load complete code
    }
}
