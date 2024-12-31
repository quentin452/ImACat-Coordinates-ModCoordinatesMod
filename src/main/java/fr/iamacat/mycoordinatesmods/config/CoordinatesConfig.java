package fr.iamacat.mycoordinatesmods.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

public class CoordinatesConfig {

    public static final String CATEOGY_BIOMES = "all";
    public static ForgeConfigSpec.BooleanValue disableFPSCounter;
    public static ForgeConfigSpec.BooleanValue disableXCoord;
    public static ForgeConfigSpec.BooleanValue disableYCoord;
    public static ForgeConfigSpec.BooleanValue disableZCoord;
    public static ForgeConfigSpec.BooleanValue disableFacing;
    public static ForgeConfigSpec.EnumValue<HudPosition> hudPosition;
    public static HudPosition _Position;

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;


    static {
        setupConfig();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupConfig() {
        COMMON_BUILDER.comment("General configuration settings").push(CATEOGY_BIOMES);
        disableFPSCounter = COMMON_BUILDER.comment("Disable FPS Counter").define("disableFPSCounter", false);
        disableXCoord = COMMON_BUILDER.comment("Disable X Coordinates Calculation").define("disableXCoord", false);
        disableYCoord = COMMON_BUILDER.comment("Disable Y Coordinates Calculation").define("disableYCoord", false);
        disableZCoord = COMMON_BUILDER.comment("Disable Z Coordinates Calculation").define("disableZCoord", false);
        disableFacing = COMMON_BUILDER.comment("Disable Facing Calculation").define("disableFacing", false);
        hudPosition = COMMON_BUILDER.comment("HUD Position").defineEnum("hudPosition", HudPosition.TOP_RIGHT, HudPosition.values());
        COMMON_BUILDER.pop();
    }

    public enum HudPosition {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == COMMON_CONFIG) {
            bakeConfig();
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == COMMON_CONFIG) {
            bakeConfig();
        }
    }

    public static void bakeConfig() {
        _Position = hudPosition.get();
    }

    public static void loadConfig(ForgeConfigSpec config, Path path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();

        file.load();
        config.setConfig(file);
    }
}
