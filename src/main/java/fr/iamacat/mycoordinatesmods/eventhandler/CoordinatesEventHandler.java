package fr.iamacat.mycoordinatesmods.eventhandler;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CoordinatesEventHandler {

    public static boolean showCoordinates = true;

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            renderGameOverlay();
        }
    }

    public static void renderGameOverlay() {
        Minecraft minecraft = Minecraft.getInstance();
        if (showCoordinates && !minecraft.getDebugOverlay().showDebugScreen() && minecraft.player != null) {
            PoseStack poseStack = new PoseStack();
            Font textRenderer = minecraft.font;
            int xCoord, yCoord;
            boolean isTop = false;
            int scaledWidth = minecraft.getWindow().getGuiScaledWidth();
            int scaledHeight = minecraft.getWindow().getGuiScaledHeight();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            switch (CoordinatesConfig.hudPosition.get()) {
                case TOP_LEFT:
                    xCoord = 2;
                    yCoord = 2;
                    isTop = true;
                    break;
                case TOP_RIGHT:
                    xCoord = scaledWidth - 2 - textRenderer.width("X: 0000.00");
                    yCoord = 2;
                    isTop = true;
                    break;
                case BOTTOM_LEFT:
                    xCoord = 2;
                    yCoord = scaledHeight - 10;
                    break;
                case BOTTOM_RIGHT:
                default:
                    xCoord = scaledWidth - 2 - textRenderer.width("X: 0000.00");
                    yCoord = scaledHeight - 10;
                    break;
            }
            if (!CoordinatesConfig.disableXCoord.get()) {
                String x = String.format("%.2f", minecraft.player.getX());
                textRenderer.drawInBatch("X: " + x, xCoord, yCoord, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableYCoord.get()) {
                String y = String.format("%.2f", minecraft.player.getY());
                textRenderer.drawInBatch("Y: " + y, xCoord, yCoord, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableZCoord.get()) {
                String z = String.format("%.2f", minecraft.player.getZ());
                textRenderer.drawInBatch("Z: " + z, xCoord, yCoord, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFacing.get()) {
                char facing = getCardinalPoint(minecraft.player.getYRot());
                textRenderer.drawInBatch("Facing: " + facing, xCoord, yCoord, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yCoord += isTop ? 10 : -10;
            }
            if (!CoordinatesConfig.disableFPSCounter.get()) {
                String fpsString = minecraft.fpsString;
                String fps = fpsString.split(" ")[0];
                textRenderer.drawInBatch("FPS: " + fps, xCoord, yCoord, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            }
            if (bufferSource instanceof MultiBufferSource.BufferSource) {
                bufferSource.endBatch();
            }
        }
    }

    private final static String[] cardinalPoints = {"S", "SW", "W", "NW", "N", "NE", "E", "SE"};

    private static char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }
}
