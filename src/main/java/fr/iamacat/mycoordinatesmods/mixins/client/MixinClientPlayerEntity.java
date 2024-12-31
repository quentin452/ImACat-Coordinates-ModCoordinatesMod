package fr.iamacat.mycoordinatesmods.mixins.client;

import static fr.iamacat.mycoordinatesmods.MyCoordinatesMods.toggleKeyBinding;
import static fr.iamacat.mycoordinatesmods.MyCoordinatesMods.toggleKeyBinding2;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Unique
    private long lastToggleTime;
    @Unique
    private static final long TOGGLE_DELAY = 100; // ms
    @Unique
    private boolean isToggleKey1Pressed = false;
    @Unique
    private boolean isToggleKey2Pressed = false;

    @Inject(method = "tick", at = @At("HEAD")) // Changed to "tick" method for ServerPlayerEntity
    public void onTick(CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (currentTime > lastToggleTime + TOGGLE_DELAY) {
            lastToggleTime = currentTime;

            // Check key presses on the client side (use KeyBinding for this)
            isToggleKey1Pressed = toggleKeyBinding.isPressed();

            isToggleKey2Pressed = toggleKeyBinding2.isPressed();

            // Toggle coordinates visibility
            if (isToggleKey1Pressed) {
                CoordinatesEventHandler.showCoordinates = !CoordinatesEventHandler.showCoordinates;
            }

            // Switch coordinates position
            if (isToggleKey2Pressed) {
                switch (CoordinatesConfig.hudPosition) {
                    case TOP_LEFT:
                        CoordinatesConfig._Position = CoordinatesConfig.HudPosition.TOP_RIGHT;
                        break;
                    case TOP_RIGHT:
                        CoordinatesConfig._Position = CoordinatesConfig.HudPosition.BOTTOM_LEFT;
                        break;
                    case BOTTOM_LEFT:
                        CoordinatesConfig._Position = CoordinatesConfig.HudPosition.BOTTOM_RIGHT;
                        break;
                    case BOTTOM_RIGHT:
                        CoordinatesConfig._Position = CoordinatesConfig.HudPosition.TOP_LEFT;
                        break;
                    default:
                        break;
                }
                CoordinatesConfig.hudPosition = CoordinatesConfig._Position;
                if (CoordinatesConfig.config != null) {
                    CoordinatesConfig.config.save();
                }
            }
        }
    }
}
