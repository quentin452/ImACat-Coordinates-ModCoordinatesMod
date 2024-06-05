package fr.iamacat.mycoordinatesmods.config;

import com.falsepattern.lib.config.Config;

import fr.iamacat.mycoordinatesmods.Tags;

@Config(modid = Tags.MODID)
public class CoordinatesConfigMixin {

    @Config.Comment("Enable Hud disabler/enabler + hud mover input(See Controls)")
    @Config.DefaultBoolean(true)
    @Config.RequiresWorldRestart
    public static boolean enableInputEvent;

}
