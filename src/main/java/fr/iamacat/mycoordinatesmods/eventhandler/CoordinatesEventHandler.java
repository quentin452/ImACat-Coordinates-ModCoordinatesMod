package fr.iamacat.mycoordinatesmods.eventhandler;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> onRenderGameOverlay(drawContext));
    }

    public static void onRenderGameOverlay(DrawContext drawContext) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (showCoordinates && !minecraft.getDebugHud().shouldShowDebugHud() && minecraft.player != null) {
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
                drawContext.drawText(textRenderer, "X: " + x, xCoord, yCoord, 0xFFFFFF, false);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord) {
                String y = String.format("%.2f", minecraft.player.getY());
                drawContext.drawText(textRenderer, "Y: " + y, xCoord, yCoord, 0xFFFFFF, false);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord) {
                String z = String.format("%.2f", minecraft.player.getZ());
                drawContext.drawText(textRenderer, "Z: " + z, xCoord, yCoord, 0xFFFFFF, false);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing) {
                char facing = getCardinalPoint(minecraft.player.getYaw());
                drawContext.drawText(textRenderer, "Facing: " + facing, xCoord, yCoord, 0xFFFFFF, false);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter) {
                String fpsString = minecraft.fpsDebugString.split(" ")[0];
                drawContext.drawText(textRenderer, "FPS: " + fpsString, xCoord, yCoord, 0xFFFFFF, false);
            }
        }
    }

    private static final String[] cardinalPoints = { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };

    private static char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }
}
