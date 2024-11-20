package fr.iamacat.mycoordinatesmods.mixins.client;

import static fr.iamacat.mycoordinatesmods.MyCoordinatesMods.toggleKeyBinding;
import static fr.iamacat.mycoordinatesmods.MyCoordinatesMods.toggleKeyBinding2;
import static fr.iamacat.mycoordinatesmods.config.CoordinatesConfig.CATEOGY_BIOMES;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;

@Mixin(EntityPlayerMP.class)
public class MixinPlayerTick {

    @Unique
    private long imACat_Coordinates_ModCoordinatesMod$lastToggleTime;
    @Unique
    private static final long imACat_Coordinates_ModCoordinatesMod$TOGGLE_DELAY = 100; // ms
    @Unique
    private boolean isToggleKey1Pressed = false;
    @Unique
    private boolean isToggleKey2Pressed = false;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void onMyUpdate(CallbackInfo ci) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (currentTime > imACat_Coordinates_ModCoordinatesMod$lastToggleTime + imACat_Coordinates_ModCoordinatesMod$TOGGLE_DELAY) {
            imACat_Coordinates_ModCoordinatesMod$lastToggleTime = currentTime;

            if (Keyboard.getEventKey() == toggleKeyBinding.getKeyCode()) {
                isToggleKey1Pressed = Keyboard.getEventKeyState();
            }

            if (Keyboard.getEventKey() == toggleKeyBinding2.getKeyCode()) {
                isToggleKey2Pressed = Keyboard.getEventKeyState();
            }

            if (isToggleKey1Pressed) {
                CoordinatesEventHandler.showCoordinates = !CoordinatesEventHandler.showCoordinates;
            }

            if (isToggleKey2Pressed) {
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
                        break;
                }
                CoordinatesConfig.hudPosition = CoordinatesConfig._Position;
                CoordinatesConfig.config
                    .get(
                        CATEOGY_BIOMES,
                        "HUD Position(Possible config : top_left , top_right , bottom_left , bottom_right)",
                        CoordinatesConfig._Position)
                    .set(CoordinatesConfig._Position);
                if (CoordinatesConfig.config.hasChanged()) {
                    CoordinatesConfig.config.save();
                }
            }
        }
    }
}
