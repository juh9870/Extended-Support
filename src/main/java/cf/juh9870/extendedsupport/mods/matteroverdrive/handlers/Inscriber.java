package cf.juh9870.extendedsupport.mods.matteroverdrive.handlers;

import matteroverdrive.data.recipes.InscriberRecipe;
import matteroverdrive.handler.recipes.InscriberRecipes;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static modtweaker2.helpers.InputHelper.toStack;

@ZenClass("mods.mo.Inscriber")
public class Inscriber {
    public static final String name = "Matter Overdrive Inscriber";

    @ZenMethod
    public static void addRecipe(IItemStack in1, IItemStack in2, IItemStack out, int energyReq, int time){
        InscriberRecipe rcp = new InscriberRecipe(toStack(in1),toStack(in2),toStack(out),energyReq,time);
        MineTweakerAPI.apply(new Add(rcp));
    }

    private static class Add extends BaseListAddition<InscriberRecipe>{

        public Add(InscriberRecipe recipe){
            super(Inscriber.name, InscriberRecipes.getRecipes());
            recipes.add(recipe);
        }
        @Override
        protected String getRecipeInfo(InscriberRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getRecipeOutput());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @ZenMethod
    public static void removeRecipe(IItemStack first, IItemStack second){
        InscriberRecipe rcp = InscriberRecipes.getRecipe(toStack(first),toStack(second));
        if (rcp!=null)
        MineTweakerAPI.apply(new Remove(Collections.singletonList(rcp)));
    }

    @ZenMethod
    public static void removeAll(){
        MineTweakerAPI.apply(new Remove(InscriberRecipes.getRecipes()));
    }

    private static class Remove extends BaseListRemoval<InscriberRecipe>{
        public Remove(List<InscriberRecipe> recipes){
            super(Inscriber.name,InscriberRecipes.getRecipes(),recipes);
        }
        @Override
        protected String getRecipeInfo(InscriberRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getRecipeOutput());
        }
    }
}
