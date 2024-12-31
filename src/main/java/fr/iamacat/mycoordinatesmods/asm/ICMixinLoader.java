package fr.iamacat.mycoordinatesmods.asm;

import com.google.common.collect.ImmutableMap;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfigMixin;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class ICMixinLoader implements ILateMixinLoader
{
    private static final Map<String, BooleanSupplier> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {

        }
    });

    private static final Map<String, BooleanSupplier> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
          // put("mixins.vanilla.playertick.json", () -> CoordinatesConfigMixin.PLAYER_TICK.enableInputEvent);
        }
    });

    private static boolean loaded(String modid)
    {
        return Loader.isModLoaded(modid);
    }

    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        if (ICLoadingPlugin.isClient)
        {
            configs.addAll(clientsideMixinConfigs.keySet());
        }
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        BooleanSupplier sidedSupplier = ICLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : null;
        BooleanSupplier commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.getAsBoolean() : commonSupplier == null || commonSupplier.getAsBoolean();
    }
}