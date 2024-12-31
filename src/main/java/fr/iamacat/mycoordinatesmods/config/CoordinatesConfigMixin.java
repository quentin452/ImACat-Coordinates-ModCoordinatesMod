package fr.iamacat.mycoordinatesmods.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import fr.iamacat.mycoordinatesmods.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MODID, name = Tags.MODID + " - mixins")
public class CoordinatesConfigMixin
{
    @Config.LangKey("cfg.mycoordinatesmod.config.playertick")
    @Config.Name("PlayerTick")
    public static final PlayerTickCategory PLAYER_TICK = new PlayerTickCategory();

    public static class PlayerTickCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Enable input events?")
        @Config.Comment("Enable Toggle Coordinates Showing and Toggle Coordinates Position")
        public boolean enableInputEvent = true;
    }

    static
    {
        ConfigAnytime.register(CoordinatesConfigMixin.class);
    }

    @Mod.EventBusSubscriber(modid = Tags.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(Tags.MODID))
            {
                ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
            }
        }
    }
}