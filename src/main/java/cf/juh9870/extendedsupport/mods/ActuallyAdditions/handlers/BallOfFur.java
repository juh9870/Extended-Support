package cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import modtweaker2.helpers.InputHelper;
import modtweaker2.helpers.LogHelper;
import modtweaker2.helpers.StackHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;

@ZenClass("mods.actuallyadditions.BallOfFur")
public class BallOfFur {
    public static final String name = "Actually Additions Ball of Fur";

    @ZenMethod
    public static void addReturn(IItemStack output, int chance) {
        MineTweakerAPI.apply(new Add(new BallOfFurReturn(toStack(output),chance)));
    }

    private static class Add extends BaseListAddition<BallOfFurReturn> {
        public Add(BallOfFurReturn recipe) {
            super(BallOfFur.name, ActuallyAdditionsAPI.ballOfFurReturnItems);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(BallOfFurReturn recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @ZenMethod
    public static void removeReturn(IItemStack output){
        List<BallOfFurReturn> recipes = new LinkedList();

        for (BallOfFurReturn recipe : ActuallyAdditionsAPI.ballOfFurReturnItems) {
            if (StackHelper.matches(toIItemStack(recipe.returnItem),output))recipes.add(recipe);
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }

    @ZenMethod
    public static void removeAll(){
        MineTweakerAPI.apply(new Remove(ActuallyAdditionsAPI.ballOfFurReturnItems));
    }

    private static class Remove extends BaseListRemoval<BallOfFurReturn>{
        public Remove(List<BallOfFurReturn> recipes){
            super(BallOfFur.name, ActuallyAdditionsAPI.ballOfFurReturnItems, recipes);
        }

        @Override
        public String getRecipeInfo(BallOfFurReturn recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }
}
