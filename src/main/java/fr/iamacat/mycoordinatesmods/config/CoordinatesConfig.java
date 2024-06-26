package fr.iamacat.mycoordinatesmods.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.iamacat.mycoordinatesmods.utils.Reference;

public class CoordinatesConfig {

    public static Configuration config;
    private static final List<ConfigCategory> allCategories = new ArrayList();
    public static String CATEOGY_BIOMES;
    public static boolean disableFPSCounter;
    public static boolean disableXCoord;
    public static boolean disableYCoord;
    public static boolean disableZCoord;
    public static boolean disableFacing;
    public static String hudPosition;
    public static String _Position;

    private static void setupCategories() {
        CATEOGY_BIOMES = makeCategory("all");
    }

    private static String makeCategory(String name) {
        ConfigCategory category = config.getCategory(name);
        category.setLanguageKey(Reference.MOD_ID + ".config." + name);
        allCategories.add(category);
        return name;
    }

    public static void setupAndLoad(FMLPreInitializationEvent event) {
        config = new Configuration(new File(event.getModConfigurationDirectory(), "mycroordinatesmods_nomixins.cfg"));
        setupCategories();
        load();
    }

    public static void load() {
        _Position = "top_right";
        disableFPSCounter = config.get(CATEOGY_BIOMES, "Disable FPS Counter???", false)
            .getBoolean();
        disableXCoord = config.get(CATEOGY_BIOMES, "Disable X Coordinates Calculation?", false)
            .getBoolean();
        disableYCoord = config.get(CATEOGY_BIOMES, "Disable Y Coordinates Calculation?", false)
            .getBoolean();
        disableZCoord = config.get(CATEOGY_BIOMES, "Disable Z Coordinates Calculation?", false)
            .getBoolean();
        disableFacing = config.get(CATEOGY_BIOMES, "Disable Facing Calculation?", false)
            .getBoolean();
        hudPosition = config
            .get(
                CATEOGY_BIOMES,
                "HUD Position(Possible config : top_left , top_right , bottom_left , bottom_right)",
                _Position)
            .getString();
        if (config.hasChanged()) {
            config.save();
        }
    }
}
