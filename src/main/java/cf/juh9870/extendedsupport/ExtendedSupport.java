package cf.juh9870.extendedsupport;

import cf.juh9870.extendedsupport.mods.ActuallyAdditions.ActuallyAdditions;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import modtweaker2.utils.TweakerPlugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Mod(modid = ExtendedSupport.MODID, name = ExtendedSupport.NAME, version = ExtendedSupport.VERSION, dependencies = ExtendedSupport.DEPENDENCIES)
public class ExtendedSupport {
    public static final String NAME = "Extended Support", name = NAME;
    public static final String MODID = "extendedsupport", modid = MODID;
    public static final String VERSION = "1.0.0", version = VERSION;
    public static final String DEPENDENCIES = "required-after:MineTweaker3;required-after:modtweaker2;after:ActuallyAdditions", dependencies = DEPENDENCIES;

    @Mod.Instance(modid)
    public ExtendedSupport instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        TweakerPlugin.register("ActuallyAdditions", ActuallyAdditions.class);
    }
}
