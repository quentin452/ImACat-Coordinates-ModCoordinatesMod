package fr.iamacat.mycoordinatesmods.eventhandler;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (showCoordinates && !minecraft.options.renderDebug && minecraft.player != null) {
            FontRenderer fontRenderer = minecraft.font;
            MatrixStack matrixStack = event.getMatrixStack();
            int xCoord, yCoord;
            boolean isTop = false;

            // Use HudPosition enum for position
            switch (CoordinatesConfig.hudPosition.get()) {
                case TOP_LEFT:
                    xCoord = 2;
                    yCoord = 2;
                    isTop = true;
                    break;
                case TOP_RIGHT:
                    xCoord = event.getWindow().getGuiScaledWidth() - 2 - fontRenderer.width("X: 0000.00");
                    yCoord = 2;
                    isTop = true;
                    break;
                case BOTTOM_LEFT:
                    xCoord = 2;
                    yCoord = event.getWindow().getGuiScaledHeight() - 10;
                    break;
                case BOTTOM_RIGHT:
                default:
                    xCoord = event.getWindow().getGuiScaledWidth() - 2 - fontRenderer.width("X: 0000.00");
                    yCoord = event.getWindow().getGuiScaledHeight() - 10;
                    break;
            }

            // Now you can continue drawing text based on the position
            if (!CoordinatesConfig.disableXCoord.get()) {
                String x = String.format("%.2f", minecraft.player.getX());
                fontRenderer.draw(matrixStack, "X: " + x, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord.get()) {
                String y = String.format("%.2f", minecraft.player.getY());
                fontRenderer.draw(matrixStack, "Y: " + y, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord.get()) {
                String z = String.format("%.2f", minecraft.player.getZ());
                fontRenderer.draw(matrixStack, "Z: " + z, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing.get()) {
                char facing = getCardinalPoint(minecraft.player.yRot);
                fontRenderer.draw(matrixStack, "Facing: " + facing, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter.get()) {
                String fpsString = minecraft.fpsString;
                String fps = fpsString.split(" ")[0];
                fontRenderer.draw(matrixStack, "FPS: " + fps, xCoord, yCoord, 0xFFFFFF);
            }

        }
    }

    private final String[] cardinalPoints = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };

    private char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }
}
