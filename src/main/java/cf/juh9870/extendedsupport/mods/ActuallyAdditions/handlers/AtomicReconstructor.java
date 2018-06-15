package cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;
import static modtweaker2.helpers.StackHelper.areEqual;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import modtweaker2.helpers.InputHelper;
import modtweaker2.helpers.LogHelper;
import modtweaker2.helpers.StackHelper;
import modtweaker2.mods.botania.handlers.PureDaisy;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import modtweaker2.utils.BaseMapAddition;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@ZenClass("mods.actuallyadditions.AtomicReconstructor")
public class AtomicReconstructor {
    public static final String name = "Actually Additions Atomic Recostructor";

    @ZenMethod
    public static void addRecipe(IItemStack  output, IItemStack  input, int energy) {
        //if(energy>299000)MineTweakerAPI.getLogger().logWarning("Recipes with energy consumptions over 300000 can't be created in regular Atomic Reconstructor");
        MineTweakerAPI.apply(new Add(new LensNoneRecipe(toStack(input),toStack(output),energy)));
    }

    @ZenMethod
    public static void addRecipe(final IItemStack  output, IOreDictEntry input, int energy) {
        //if(energy>299000)MineTweakerAPI.getLogger().logWarning("Recipes with energy consumptions over 300000 can't be created in regular Atomic Reconstructor");
        //I know it's bad but i can't find another way to do it, sorry.
        LensNoneRecipe rcp = new LensNoneRecipe(input.getName(),"",energy){
            @Override
            public List<ItemStack> getOutputs() {
                return Collections.singletonList(toStack(output).copy());
            }
        };

        MineTweakerAPI.apply(new Add(rcp));
    }

    private static class Add extends BaseListAddition<LensNoneRecipe> {
        public Add(LensNoneRecipe recipe) {
            super(AtomicReconstructor.name, ActuallyAdditionsAPI.reconstructorLensNoneRecipes);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(LensNoneRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getOutputs());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @ZenMethod
    public static void removeRecipe(IItemStack output){
        List<LensNoneRecipe> recipes = new LinkedList();

        for (LensNoneRecipe recipe : ActuallyAdditionsAPI.reconstructorLensNoneRecipes) {
            List<ItemStack> outputs = recipe.getOutputs();

            if (output==null)continue;

            IItemStack out = InputHelper.toIItemStack(outputs.get(0));
            if (StackHelper.matches(output, out)) {
                recipes.add(recipe);
            }
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output,IItemStack input){
        List<LensNoneRecipe> recipes = new LinkedList();

        for (LensNoneRecipe recipe : ActuallyAdditionsAPI.reconstructorLensNoneRecipes) {
            List<ItemStack> inputs = recipe.getInputs();
            List<ItemStack> outputs = recipe.getOutputs();
            if (outputs==null||inputs==null||inputs.size()!=1)continue;
            IItemStack out = InputHelper.toIItemStack(recipe.getOutputs().get(0));
            IItemStack in = InputHelper.toIItemStack(recipe.getInputs().get(0));
            if (StackHelper.matches(output, out)&&StackHelper.matches(input, in)) {
                recipes.add(recipe);
            }
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }
    @ZenMethod
    public static void removeAll(){
        MineTweakerAPI.apply(new Remove(ActuallyAdditionsAPI.reconstructorLensNoneRecipes));
    }

    private static class Remove extends BaseListRemoval<LensNoneRecipe>{
        public Remove(List<LensNoneRecipe> recipes){
            super(AtomicReconstructor.name, ActuallyAdditionsAPI.reconstructorLensNoneRecipes, recipes);
        }

        @Override
        public String getRecipeInfo(LensNoneRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getOutputs());
        }
    }
}
