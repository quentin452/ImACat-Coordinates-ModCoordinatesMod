package fr.iamacat.mycoordinatesmods.eventhandler;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    public static void register() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> onRenderGameOverlay(matrixStack));
    }

    public static void onRenderGameOverlay(MatrixStack matrixStack) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (showCoordinates && !minecraft.options.debugEnabled && minecraft.player != null) {
            TextRenderer textRenderer = minecraft.textRenderer;
            int xCoord, yCoord;
            boolean isTop = false;

            // Use HudPosition enum for position
            switch (CoordinatesConfig.hudPosition) {
                case TOP_LEFT:
                    xCoord = 2;
                    yCoord = 2;
                    isTop = true;
                    break;
                case TOP_RIGHT:
                    xCoord = minecraft.getWindow().getScaledWidth() - 2 - textRenderer.getWidth("X: 0000.00");
                    yCoord = 2;
                    isTop = true;
                    break;
                case BOTTOM_LEFT:
                    xCoord = 2;
                    yCoord = minecraft.getWindow().getScaledHeight() - 10;
                    break;
                case BOTTOM_RIGHT:
                default:
                    xCoord = minecraft.getWindow().getScaledWidth() - 2 - textRenderer.getWidth("X: 0000.00");
                    yCoord = minecraft.getWindow().getScaledHeight() - 10;
                    break;
            }

            // Draw text based on the position
            if (!CoordinatesConfig.disableXCoord) {
                String x = String.format("%.2f", minecraft.player.getX());
                textRenderer.draw(matrixStack, "X: " + x, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord) {
                String y = String.format("%.2f", minecraft.player.getY());
                textRenderer.draw(matrixStack, "Y: " + y, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord) {
                String z = String.format("%.2f", minecraft.player.getZ());
                textRenderer.draw(matrixStack, "Z: " + z, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing) {
                char facing = getCardinalPoint(minecraft.player.yaw);
                textRenderer.draw(matrixStack, "Facing: " + facing, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter) {
                String fpsString = minecraft.fpsDebugString.split(" ")[0];
                textRenderer.draw(matrixStack, "FPS: " + fpsString, xCoord, yCoord, 0xFFFFFF);
            }
        }
    }

    private static final String[] cardinalPoints = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };

    private static char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }
}
