package fr.iamacat.mycoordinatesmods.config;

import net.minecraft.client.gui.GuiScreen;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;

import fr.iamacat.mycoordinatesmods.Tags;

public class CoordinatesGuiConfigMixin extends SimpleGuiConfig {

    public CoordinatesGuiConfigMixin(GuiScreen parent) throws ConfigException {
        super(parent, CoordinatesConfigMixin.class, Tags.MODID, Tags.MODNAME);
    }
}
