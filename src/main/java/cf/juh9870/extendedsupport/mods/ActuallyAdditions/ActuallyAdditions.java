package cf.juh9870.extendedsupport.mods.ActuallyAdditions;

import cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers.AtomicReconstructor;
import cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers.BallOfFur;
import cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers.Crusher;
import cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers.TreasureChest;
import minetweaker.MineTweakerAPI;

public class ActuallyAdditions {


    public ActuallyAdditions(){
        MineTweakerAPI.registerClass(AtomicReconstructor.class);
        MineTweakerAPI.registerClass(BallOfFur.class);
        MineTweakerAPI.registerClass(Crusher.class);
        MineTweakerAPI.registerClass(TreasureChest.class);
    }
}
