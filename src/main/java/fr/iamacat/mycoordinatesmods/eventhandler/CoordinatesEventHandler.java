package fr.iamacat.mycoordinatesmods.eventhandler;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGuiOverlayEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (showCoordinates && !minecraft.options.renderDebug && minecraft.player != null) {
            // Remplacer FontRenderer par TextRenderer
            Font textRenderer = minecraft.font;
            PoseStack matrixStack = event.getPoseStack();
            int xCoord, yCoord;
            boolean isTop = false;

            // Positionner le texte en fonction de la configuration
            switch (CoordinatesConfig.hudPosition.get()) {
                case TOP_LEFT:
                    xCoord = 2;
                    yCoord = 2;
                    isTop = true;
                    break;
                case TOP_RIGHT:
                    xCoord = event.getWindow().getGuiScaledWidth() - 2 - textRenderer.width("X: 0000.00");
                    yCoord = 2;
                    isTop = true;
                    break;
                case BOTTOM_LEFT:
                    xCoord = 2;
                    yCoord = event.getWindow().getGuiScaledHeight() - 10;
                    break;
                case BOTTOM_RIGHT:
                default:
                    xCoord = event.getWindow().getGuiScaledWidth() - 2 - textRenderer.width("X: 0000.00");
                    yCoord = event.getWindow().getGuiScaledHeight() - 10;
                    break;
            }

            if (!CoordinatesConfig.disableXCoord.get()) {
                String x = String.format("%.2f", minecraft.player.getX());
                textRenderer.draw(matrixStack, "X: " + x, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord.get()) {
                String y = String.format("%.2f", minecraft.player.getY());
                textRenderer.draw(matrixStack, "Y: " + y, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord.get()) {
                String z = String.format("%.2f", minecraft.player.getZ());
                textRenderer.draw(matrixStack, "Z: " + z, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing.get()) {
                char facing = getCardinalPoint(minecraft.player.getYRot());
                textRenderer.draw(matrixStack, "Facing: " + facing, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter.get()) {
                String fpsString = minecraft.fpsString;
                String fps = fpsString.split(" ")[0];
                textRenderer.draw(matrixStack, "FPS: " + fps, xCoord, yCoord, 0xFFFFFF);
            }
        }
    }

    private final String[] cardinalPoints = { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };

    private char getCardinalPoint(float yaw) {
        yaw = (yaw + 360) % 360;
        int index = Math.round((yaw + 22.5f) / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }

}
