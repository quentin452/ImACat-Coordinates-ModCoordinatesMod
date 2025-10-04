package fr.iamacat.mycoordinatesmods.eventhandler;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.proxy.ClientProxy;
public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            return;
        }

        if (ClientProxy.toggleKeyBinding != null && 
            ClientProxy.toggleKeyBinding.isPressed()) {
            CoordinatesEventHandler.showCoordinates = !CoordinatesEventHandler.showCoordinates;
        }

        if (ClientProxy.toggleKeyBinding2 != null &&
            ClientProxy.toggleKeyBinding2.isPressed()) {
            switch (CoordinatesConfig.hudPosition) {
                case "top_left":
                    CoordinatesConfig._Position = "top_right";
                    break;
                case "top_right":
                    CoordinatesConfig._Position = "bottom_left";
                    break;
                case "bottom_left":
                    CoordinatesConfig._Position = "bottom_right";
                    break;
                case "bottom_right":
                    CoordinatesConfig._Position = "top_left";
                    break;
                default:
                    CoordinatesConfig._Position = "bottom_right";
                    break;
            }
            CoordinatesConfig.hudPosition = CoordinatesConfig._Position;
            CoordinatesConfig.config
                .get(
                    CoordinatesConfig.CATEOGY_BIOMES,
                    "HUD Position(Possible config : top_left , top_right , bottom_left , bottom_right)",
                    CoordinatesConfig._Position)
                .set(CoordinatesConfig._Position);
            if (CoordinatesConfig.config.hasChanged()) {
                CoordinatesConfig.config.save();
            }
        }
    }
}