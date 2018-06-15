package cf.juh9870.extendedsupport.mods.ActuallyAdditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static modtweaker2.helpers.InputHelper.toStack;

@ZenClass("mods.actuallyadditions.Crusher")
public class Crusher {
    public static final String name = "Actually Additions Crusher";

    @ZenMethod
    public static void addRecipe(IItemStack  output, IItemStack  input, IItemStack output2, int chance) {
        MineTweakerAPI.apply(new Add(new CrusherRecipe(toStack(input),toStack(output),toStack(output2),chance)));
    }
    @ZenMethod
    public static void addRecipe(final IItemStack  output, IItemStack  input) {
        CrusherRecipe rcp = new CrusherRecipe(toStack(input),"",0){
            @Override
            public List<ItemStack> getRecipeOutputOnes() {
                return Collections.singletonList(toStack(output).copy());
            }
        };
        MineTweakerAPI.apply(new Add(rcp));
    }

    @ZenMethod
    public static void addRecipe(final IItemStack  output, IOreDictEntry input) {
        //I know it's bad but i can't find another way to do it, sorry.
        CrusherRecipe rcp = new CrusherRecipe(input.getName(),null,0,null,0,0){
            @Override
            public List<ItemStack> getRecipeOutputOnes() {
                return Collections.singletonList(toStack(output).copy());
            }
        };

        MineTweakerAPI.apply(new Add(rcp));
    }
    @ZenMethod
    public static void addRecipe(final IItemStack  output, IOreDictEntry input, final IItemStack output2, int chance ) {
        //I know it's bad but i can't find another way to do it, sorry.
        CrusherRecipe rcp = new CrusherRecipe(input.getName(),null,0,null,0,chance){
            @Override
            public List<ItemStack> getRecipeOutputOnes() {
                return Collections.singletonList(toStack(output).copy());
            }
            @Override
            public List<ItemStack> getRecipeOutputTwos() {
                return Collections.singletonList(toStack(output2).copy());
            }
        };

        MineTweakerAPI.apply(new Add(rcp));
    }

    private static class Add extends BaseListAddition<CrusherRecipe> {
        public Add(CrusherRecipe recipe) {
            super(Crusher.name, ActuallyAdditionsAPI.crusherRecipes);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(CrusherRecipe recipe) {
            List<ItemStack> outs = new ArrayList(recipe.getRecipeOutputOnes());
            if(recipe.getRecipeOutputTwos()!=null)
                outs.addAll(recipe.getRecipeOutputTwos());
            return LogHelper.getStackDescription(outs);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @ZenMethod
    public static void removeRecipe(IItemStack input){
        List<CrusherRecipe> recipes = new LinkedList();

        for (CrusherRecipe recipe : ActuallyAdditionsAPI.crusherRecipes) {
            List<ItemStack> outs = recipe.getRecipeInputs();
            if (outs==null)continue;
            IItemStack out = InputHelper.toIItemStack(outs.get(0));
            if (StackHelper.matches(input, out)) {
                recipes.add(recipe);
            }
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }

    @ZenMethod
    public static void removeRecipe(IOreDictEntry input){
        List<CrusherRecipe> recipes = new LinkedList();

        for (CrusherRecipe recipe : ActuallyAdditionsAPI.crusherRecipes) {
            if (recipe.input!=null&&recipe.input.equals(input.getName()))recipes.add(recipe);
        }

        if (!recipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(recipes));
        }
    }



    @ZenMethod
    public static void removeAll(){
        MineTweakerAPI.apply(new Remove(ActuallyAdditionsAPI.crusherRecipes));
    }

    private static class Remove extends BaseListRemoval<CrusherRecipe>{
        public Remove(List<CrusherRecipe> recipes){
            super(Crusher.name, ActuallyAdditionsAPI.crusherRecipes, recipes);
        }

        @Override
        public String getRecipeInfo(CrusherRecipe recipe) {
            List<ItemStack> outs = new ArrayList(recipe.getRecipeOutputOnes());
            if(recipe.getRecipeOutputTwos()!=null)
                outs.addAll(recipe.getRecipeOutputTwos());
            return LogHelper.getStackDescription(outs);
        }
    }
}
