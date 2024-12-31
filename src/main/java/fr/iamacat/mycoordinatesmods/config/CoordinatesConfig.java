package fr.iamacat.mycoordinatesmods.config;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import com.electronwill.nightconfig.core.file.FileConfig;

import java.io.File;
import java.nio.file.Path;

public class CoordinatesConfig {

    public static final String CATEGORY_HUD = "all";
    public static boolean disableFPSCounter;
    public static boolean disableXCoord;
    public static boolean disableYCoord;
    public static boolean disableZCoord;
    public static boolean disableFacing;
    public static HudPosition hudPosition;
    public static HudPosition _Position;

    private static final String CONFIG_FILE_NAME = "mycoordinatesmods.toml";
    public static FileConfig config;

    public static void init() {
        // Charger la configuration au démarrage
        Path configDir = FabricLoader.getInstance().getConfigDir();
        File configFile = new File(configDir.toFile(), CONFIG_FILE_NAME);

        // Vérifier si le fichier de configuration existe, sinon en créer un nouveau
        if (!configFile.exists()) {
            createDefaultConfig(configFile);
        }

        config = FileConfig.of(configFile);
        loadConfig();

        // Appliquer les valeurs à la configuration
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            bakeConfig();
        });
    }

    private static void loadConfig() {
        config.load();
        // Charger les valeurs depuis le fichier
        disableFPSCounter = config.getOrElse(CATEGORY_HUD + ".disableFPSCounter", false);
        disableXCoord = config.getOrElse(CATEGORY_HUD + ".disableXCoord", false);
        disableYCoord = config.getOrElse(CATEGORY_HUD + ".disableYCoord", false);
        disableZCoord = config.getOrElse(CATEGORY_HUD + ".disableZCoord", false);
        disableFacing = config.getOrElse(CATEGORY_HUD + ".disableFacing", false);
        String hudPos = config.getOrElse(CATEGORY_HUD + ".hudPosition", HudPosition.TOP_RIGHT.name());
        hudPosition = HudPosition.valueOf(hudPos);
    }

    private static void createDefaultConfig(File configFile) {
        // Créer un fichier de configuration avec des valeurs par défaut si le fichier n'existe pas
        config = FileConfig.of(configFile);
        config.add(CATEGORY_HUD + ".disableFPSCounter", false);
        config.add(CATEGORY_HUD + ".disableXCoord", false);
        config.add(CATEGORY_HUD + ".disableYCoord", false);
        config.add(CATEGORY_HUD + ".disableZCoord", false);
        config.add(CATEGORY_HUD + ".disableFacing", false);
        config.add(CATEGORY_HUD + ".hudPosition", HudPosition.TOP_RIGHT.name());
        config.save();
    }

    public static void bakeConfig() {
        _Position = hudPosition;
    }

    public static void saveConfig() {
        // Sauvegarder la configuration
        config.set(CATEGORY_HUD + ".disableFPSCounter", disableFPSCounter);
        config.set(CATEGORY_HUD + ".disableXCoord", disableXCoord);
        config.set(CATEGORY_HUD + ".disableYCoord", disableYCoord);
        config.set(CATEGORY_HUD + ".disableZCoord", disableZCoord);
        config.set(CATEGORY_HUD + ".disableFacing", disableFacing);
        config.set(CATEGORY_HUD + ".hudPosition", hudPosition.name());
        config.save();
    }

    public enum HudPosition {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }
}
