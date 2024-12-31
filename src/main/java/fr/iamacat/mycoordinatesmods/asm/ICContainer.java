package fr.iamacat.mycoordinatesmods.asm;

import com.google.common.eventbus.EventBus;
import fr.iamacat.mycoordinatesmods.Tags;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class ICContainer extends DummyModContainer
{
    public ICContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = Tags.MODID;
        meta.name = Tags.MODNAME;
        meta.description = "";
        meta.version = "0.8mc1.12.2";
        meta.authorList.add("quentin452");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}