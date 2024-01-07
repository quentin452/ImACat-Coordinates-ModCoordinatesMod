package fr.iamacat.mycoordinatesmods.eventhandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fr.iamacat.mycoordinatesmods.MyCoordinatesMods;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class CoordinatesEventHandler {
    static KeyBinding toggleKeyBinding;
    private boolean showCoordinates = true;
    private long lastToggleTime;
    private static final long TOGGLE_DELAY = 100; // ms

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (Keyboard.isKeyDown(toggleKeyBinding.getKeyCode()) && System.currentTimeMillis() > lastToggleTime + TOGGLE_DELAY) {
            lastToggleTime = System.currentTimeMillis();
            showCoordinates = !showCoordinates;
            Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
            Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
            System.out.println("Toggle Coordinates key pressed. showCoordinates = " + showCoordinates);
        }
    }
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (showCoordinates && !minecraft.gameSettings.showDebugInfo && minecraft.currentScreen == null) {
            FontRenderer fontRenderer = minecraft.fontRenderer;

            int yCoord = 10;

            if (!CoordinatesConfig.disableXCoord) {
                String x = String.format("%.2f", minecraft.thePlayer.posX);
                fontRenderer.drawString("X: " + x, 10, yCoord, 0xFFFFFF);
                yCoord += 10;
            }

            if (!CoordinatesConfig.disableYCoord) {
                String y = String.format("%.2f", minecraft.thePlayer.posY);
                fontRenderer.drawString("Y: " + y, 10, yCoord, 0xFFFFFF);
                yCoord += 10;
            }

            if (!CoordinatesConfig.disableZCoord) {
                String z = String.format("%.2f", minecraft.thePlayer.posZ);
                fontRenderer.drawString("Z: " + z, 10, yCoord, 0xFFFFFF);
                yCoord += 10;
            }

            if (!CoordinatesConfig.disableFacing) {
                char facing = getCardinalPoint(minecraft.thePlayer.rotationYaw);
                fontRenderer.drawString("Facing: " + facing, 10, yCoord, 0xFFFFFF);
                yCoord += 10;
            }

            if (!CoordinatesConfig.disableFPSCounter) {
                String fps = minecraft.debug.split(" ")[0];
                fontRenderer.drawString("FPS: " + fps, 10, yCoord, 0xFFFFFF);
            }
        }
    }

    private final String[] cardinalPoints = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    private char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }

    public static void init(FMLInitializationEvent event) {
        KeyBinding[] keyBindings = {
                new KeyBinding("Toggle Coordinates", Keyboard.KEY_T, "IamacatCoordinatesMod")
        };
        toggleKeyBinding = keyBindings[0];
        ClientRegistry.registerKeyBinding(toggleKeyBinding);
    }
}
