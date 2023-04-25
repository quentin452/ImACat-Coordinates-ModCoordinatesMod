package fr.iamacat.mycoordinatesmods;

import net.minecraft.client.gui.GuiScreen;

public class GuiCoordinates extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawString(fontRendererObj, "X: " + mc.thePlayer.posX, 10, 10, 0xFFFFFF);
        drawString(fontRendererObj, "Y: " + mc.thePlayer.posY, 10, 20, 0xFFFFFF);
        drawString(fontRendererObj, "Z: " + mc.thePlayer.posZ, 10, 30, 0xFFFFFF);
        drawString(fontRendererObj, "Facing: " + getCardinalPoint(mc.thePlayer.rotationYaw), 10, 40, 0xFFFFFF);
    }

    private String getCardinalPoint(float yaw) {
        String[] cardinalPoints = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index];
    }
}

