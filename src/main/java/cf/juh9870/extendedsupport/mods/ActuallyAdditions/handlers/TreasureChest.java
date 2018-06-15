package cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.helpers.StackHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedList;
import java.util.List;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;

@ZenClass("mods.actuallyadditions.TreasureChest")
public class TreasureChest {
    public static final String name = "Actually Additions Treasure Chest";

    @ZenMethod
    public static void addLoot(IItemStack output, int chance, int minAmount, int MaxAmount) {
        MineTweakerAPI.apply(new Add(new TreasureChestLoot(toStack(output),chance,minAmount,MaxAmount)));
    }

    private static class Add extends BaseListAddition<TreasureChestLoot> {
        public Add(TreasureChestLoot recipe) {
            super(TreasureChest.name, ActuallyAdditionsAPI.treasureChestLoot);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @ZenMethod
    public static void removeLoot(IItemStack output){
        List<TreasureChestLoot> recipes = new LinkedList();

        for (TreasureChestLoot recipe : ActuallyAdditionsAPI.treasureChestLoot) {
            if (StackHelper.matches(toIItemStack(recipe.returnItem),output))recipes.add(recipe);
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }

    @ZenMethod
    public static void removeAll(){
        MineTweakerAPI.apply(new Remove(ActuallyAdditionsAPI.treasureChestLoot));
    }

    private static class Remove extends BaseListRemoval<TreasureChestLoot>{
        public Remove(List<TreasureChestLoot> recipes){
            super(TreasureChest.name, ActuallyAdditionsAPI.treasureChestLoot, recipes);
        }

        @Override
        public String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }
}
