package cf.juh9870.extendedsupport.mods.matteroverdrive;

import cf.juh9870.extendedsupport.mods.matteroverdrive.handlers.Inscriber;
import minetweaker.MineTweakerAPI;

public class MatterOverdrive {
    public MatterOverdrive(){
        MineTweakerAPI.registerClass(Inscriber.class);
    }
}
