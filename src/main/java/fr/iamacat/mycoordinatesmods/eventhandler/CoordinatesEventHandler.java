package fr.iamacat.mycoordinatesmods.eventhandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;

public class CoordinatesEventHandler {

    static KeyBinding toggleKeyBinding;
    private boolean showCoordinates = true;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (showCoordinates && !minecraft.gameSettings.showDebugInfo && minecraft.currentScreen == null) {
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
                char facing = getCardinalPoint(minecraft.thePlayer.rotationYaw);
                fontRenderer.drawString("Facing: " + facing, xCoord, yCoord, 0xFFFFFF);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter) {
                String fps = minecraft.debug.split(" ")[0];
                fontRenderer.drawString("FPS: " + fps, xCoord, yCoord, 0xFFFFFF);
            }
        }
    }

    private final String[] cardinalPoints = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };

    private char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }

    public static void init(FMLPostInitializationEvent event) {
        KeyBinding[] keyBindings = { new KeyBinding("Toggle Coordinates", Keyboard.KEY_T, "IamacatCoordinatesMod") };
        toggleKeyBinding = keyBindings[0];
        ClientRegistry.registerKeyBinding(toggleKeyBinding);
    }
}
