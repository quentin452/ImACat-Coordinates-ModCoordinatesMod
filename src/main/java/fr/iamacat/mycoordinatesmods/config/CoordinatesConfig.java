package fr.iamacat.mycoordinatesmods.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesConfig {

    public static Configuration config;
    private static final List<ConfigCategory> allCategories = new ArrayList();
    private static String CATEOGY_BIOMES;
    public static boolean disableFPSCounter;
    public static boolean disableXCoord;
    public static boolean disableYCoord;
    public static boolean disableZCoord;
    public static boolean disableFacing;
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
        config = new Configuration(new File(event.getModConfigurationDirectory(), "iamacatcoordinates.cfg"));
        setupCategories();
        load();
    }

    public static void load() {
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
        if (config.hasChanged()) {
            config.save();
        }
    }
}
