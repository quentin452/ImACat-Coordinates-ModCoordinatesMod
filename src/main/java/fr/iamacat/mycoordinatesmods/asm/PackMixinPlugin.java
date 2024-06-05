package fr.iamacat.mycoordinatesmods.asm;

import org.apache.logging.log4j.Logger;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.ConfigurationManager;
import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.IMixinPlugin;
import com.falsepattern.lib.mixin.ITargetedMod;

import fr.iamacat.mycoordinatesmods.config.CoordinatesConfigMixin;
import lombok.Getter;

public class PackMixinPlugin implements IMixinPlugin {

    @Getter
    private final Logger logger = IMixinPlugin.createLogger("My Coordinates Mod");

    public PackMixinPlugin() {
        try {
            ConfigurationManager.initialize(CoordinatesConfigMixin.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ITargetedMod[] getTargetedModEnumValues() {
        return TargetedMod.values();
    }

    @Override
    public IMixin[] getMixinEnumValues() {
        return Mixin.values();
    }
}
