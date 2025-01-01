package fr.iamacat.mycoordinatesmods.mixins.client;

import net.minecraft.client.Minecraft;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;

import static fr.iamacat.mycoordinatesmods.MyCoordinatesMods.*;

@Mixin(Player.class)
public class MixinPlayer {

    @Unique
    private long lastToggleTime;
    @Unique
    private static final long TOGGLE_DELAY = 100; // ms
    @Unique
    private boolean isToggleKey1Pressed = false;
    @Unique
    private boolean isToggleKey2Pressed = false;

    @Inject(method = "tick", at = @At("HEAD"),remap = false)
    public void onTick(CallbackInfo ci) {
        if (Minecraft.getInstance().screen != null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime > lastToggleTime + TOGGLE_DELAY) {
            lastToggleTime = currentTime;

            // Check key presses on the client side (use KeyBinding for this)
            isToggleKey1Pressed = toggleKeyBinding.isDown();
            isToggleKey2Pressed = toggleKeyBinding2.isDown();

            // Toggle coordinates visibility
            if (isToggleKey1Pressed) {
                showCoordinates = !showCoordinates;
            }

            // Switch coordinates position
            if (isToggleKey2Pressed) {
                switch (CoordinatesConfig.hudPosition.get()) {
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
                CoordinatesConfig.hudPosition.set(CoordinatesConfig._Position);
                if (CoordinatesConfig.COMMON_CONFIG != null) {
                    CoordinatesConfig.COMMON_CONFIG.save();
                }
            }
        }
    }
}
