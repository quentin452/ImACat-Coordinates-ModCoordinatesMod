package fr.iamacat.mycoordinatesmods;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fr.iamacat.mycoordinatesmods.proxy.CommonProxy;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION)
public class MyCoordinatesMods {
    private boolean showCoordinates = true;
    @Mod.Instance(Reference.MOD_ID)
    public static MyCoordinatesMods instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        System.out.println("MyCoordinatesMods loaded successfully.");
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Add your custom key bindings
        KeyBinding[] keyBindings = {
                new KeyBinding("Toggle Coordinates", Keyboard.KEY_C, "MyCoordinatesMods")
        };
        ClientRegistry.registerKeyBinding(keyBindings[0]);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (showCoordinates && !Minecraft.getMinecraft().gameSettings.showDebugInfo && Minecraft.getMinecraft().currentScreen == null) {
            Minecraft minecraft = Minecraft.getMinecraft();
            FontRenderer fontRenderer = minecraft.fontRenderer;
            String x = String.format("%.2f", minecraft.thePlayer.posX);
            String y = String.format("%.2f", minecraft.thePlayer.posY);
            String z = String.format("%.2f", minecraft.thePlayer.posZ);
            char facing = getCardinalPoint(minecraft.thePlayer.rotationYaw);
            List<String> coordinates = Arrays.asList("X: " + x, "Y: " + y, "Z: " + z, "Facing: " + facing, "FPS: " + minecraft.debug.split(" ")[0]);
            int yCoord = 10;
            for (String coord : coordinates) {
                fontRenderer.drawString(coord, 10, yCoord, 0xFFFFFF);
                yCoord += 10;
            }
            System.out.println("onRenderGameOverlay called");
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        // Toggle the coordinate display when the "Toggle Coordinates" key is pressed
        if (Keyboard.isKeyDown(Keyboard.KEY_C) && Minecraft.getMinecraft().currentScreen == null) {
            showCoordinates = !showCoordinates;
            System.out.println("Toggle Coordinates key pressed");
        }
        System.out.println("onTick called");
    }

    private String[] cardinalPoints = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    private char getCardinalPoint(float yaw) {
        int index = Math.round(yaw / 45f) & 7;
        return cardinalPoints[index].charAt(0);
    }
    }