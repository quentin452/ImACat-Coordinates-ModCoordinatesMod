package fr.iamacat.mycoordinatesmods.eventhandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;

public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        if (showCoordinates && !minecraft.gameSettings.showDebugInfo) {
            FontRenderer fontRenderer = minecraft.fontRenderer;
            ScaledResolution scaledResolution = event.resolution;
            int xCoord, yCoord;
            boolean isTop = false;
            switch (CoordinatesConfig.hudPosition) {
                case "top_left":
                    xCoord = 2;
                    yCoord = 2;
                    isTop = true;
                    break;
                case "top_right":
                    xCoord = scaledResolution.getScaledWidth() - 2 - fontRenderer.getStringWidth("X: 0000.00");
                    yCoord = 2;
                    isTop = true;
                    break;
                case "bottom_left":
                    xCoord = 2;
                    yCoord = scaledResolution.getScaledHeight() - 10;
                    break;
                case "bottom_right":
                default:
                    xCoord = scaledResolution.getScaledWidth() - 2 - fontRenderer.getStringWidth("X: 0000.00");
                    yCoord = scaledResolution.getScaledHeight() - 10;
                    break;
            }
            if (!CoordinatesConfig.disableXCoord) {
                String x = String.format("%.2f", minecraft.thePlayer.posX);
                fontRenderer.drawString("X: " + x, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord) {
                String y = String.format("%.2f", minecraft.thePlayer.posY);
                fontRenderer.drawString("Y: " + y, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord) {
                String z = String.format("%.2f", minecraft.thePlayer.posZ);
                fontRenderer.drawString("Z: " + z, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing) {
                String facing = getCardinalPoint(minecraft.thePlayer.rotationYaw);
                fontRenderer.drawString("Facing: " + facing, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter) {
                String fps = minecraft.debug.split(" ")[0];
                fontRenderer.drawString("FPS: " + fps, xCoord, yCoord, 0xFFFFFF);
            }
        }
    }

    private final String[] cardinalPoints = { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };

    private String getCardinalPoint(float yaw) {
        yaw = (yaw % 360 + 360) % 360;
        int index = (int) ((yaw + 22.5) / 45) % 8;
        return cardinalPoints[index];
    }
}
