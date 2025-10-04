package fr.iamacat.mycoordinatesmods.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.eventhandler.KeyInputHandler;

public class ClientProxy extends CommonProxy {

    public static KeyBinding toggleKeyBinding;
    public static KeyBinding toggleKeyBinding2;

    @Override
    public void registerRenders() {}

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        
        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        
        // Register key bindings
        toggleKeyBinding = new KeyBinding("Toggle Coordinates Showing", Keyboard.KEY_T, "IamacatCoordinatesMod");
        ClientRegistry.registerKeyBinding(toggleKeyBinding);
        
        toggleKeyBinding2 = new KeyBinding("Toggle Coordinates Position", Keyboard.KEY_Y, "IamacatCoordinatesMod");
        ClientRegistry.registerKeyBinding(toggleKeyBinding2);
    }
}
