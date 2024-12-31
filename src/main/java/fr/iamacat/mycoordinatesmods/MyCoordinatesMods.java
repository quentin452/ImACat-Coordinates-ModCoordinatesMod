package fr.iamacat.mycoordinatesmods;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class MyCoordinatesMods implements ModInitializer {

    public static KeyBinding toggleKeyBinding;
    public static KeyBinding toggleKeyBinding2;


    @Override
    public void onInitialize() {
        CoordinatesConfig.init();
        CoordinatesEventHandler.register();
        toggleKeyBinding = new KeyBinding("key.mycoordinatesmods.showHud", GLFW.GLFW_KEY_T, "key.categories.mycoordinatesmods");
        toggleKeyBinding2 = new KeyBinding("key.mycoordinatesmods.switchHudPosition", GLFW.GLFW_KEY_Y, "key.categories.mycoordinatesmods");

        KeyBindingHelper.registerKeyBinding(toggleKeyBinding);
        KeyBindingHelper.registerKeyBinding(toggleKeyBinding2);
    }
}
