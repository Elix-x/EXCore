package code.elix_x.excore.utils.recipes;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import code.elix_x.excore.utils.items.ItemStackStringTranslator;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeStringTranslator {

	public static String[] validateFromConfig(String... recipe){
		recipe = ArrayUtils.clone(recipe);
		if(!isEmpty(recipe) && isShaped(recipe)){
			for(int i = 0; i < getDefinitionsBegining(recipe); i++){
				recipe[i] = recipe[i].replace('_', ' ');
			}
		}
		return recipe;
	}

	public static boolean isEmpty(String... srecipe){
		return ArrayUtils.isEmpty(srecipe);
	}

	public static boolean isShaped(String... recipe){
		return !ItemStackStringTranslator.isValidItemstackAdvanced(recipe[0]);
	}

	public static int getDefinitionsBegining(String... recipe){
		int i;
		for(i = 0; i < recipe.length; i++){
			if(recipe[i].length() == 1 && ItemStackStringTranslator.isValidItemstackAdvanced(recipe[i + 1])) break;
		}
		return i;
	}

	public static Object[] fromString(String... srecipe){
		Object[] recipe = new Object[srecipe.length];
		if(isShaped(srecipe)){
			int i = getDefinitionsBegining(srecipe);
			for(int j = 0; j < i; j++){
				recipe[j] = srecipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(srecipe[j].length() == 1){
					recipe[j] = srecipe[j].charAt(0);
				} else{
					recipe[j] = ItemStackStringTranslator.fromStringAdvanced(srecipe[j]);
				}
			}
		} else{
			for(int i = 0; i < srecipe.length; i++){
				recipe[i] = ItemStackStringTranslator.fromStringAdvanced(srecipe[i]);
			}
		}
		return recipe;
	}

	public static IRecipe fromString(ItemStack result, String... srecipe){
		if(isEmpty(srecipe)){
			return new IRecipe(){

				@Override
				public boolean matches(InventoryCrafting inventory, World world){
					return false;
				}

				@Override
				public ItemStack getCraftingResult(InventoryCrafting inventory){
					return null;
				}

				@Override
				public int getRecipeSize(){
					return 0;
				}

				@Override
				public ItemStack getRecipeOutput(){
					return null;
				}

				@Override
				public ItemStack[] getRemainingItems(InventoryCrafting inventory){
					return null;
				}

			};
		} else if(isShaped(srecipe)){
			return new ShapedOreRecipe(result, fromString(srecipe));
		} else{
			return new ShapelessOreRecipe(result, fromString(srecipe));
		}
	}

	public static boolean isShaped(Map<String, ?> map, String... recipe){
		return !(map.containsKey(recipe[0]) || ItemStackStringTranslator.isValidItemstackAdvanced(recipe[0]));
	}

	public static int getDefinitionsBegining(Map<String, ?> map, String... recipe){
		int i;
		for(i = 0; i < recipe.length; i++){
			if(recipe[i].length() == 1 && (map.containsKey(recipe[i + 1]) || ItemStackStringTranslator.isValidItemstackAdvanced(recipe[i + 1])))
				break;
		}
		return i;
	}

	public static Object[] fromString(Map<String, ?> map, String... srecipe){
		Object[] recipe = new Object[srecipe.length];
		if(isShaped(map, srecipe)){
			int i = getDefinitionsBegining(map, srecipe);
			for(int j = 0; j < i; j++){
				recipe[j] = srecipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(srecipe[j].length() == 1){
					recipe[j] = srecipe[j].charAt(0);
				} else if(map.containsKey(srecipe[j])){
					recipe[j] = map.get(srecipe[j]);
				} else{
					recipe[j] = ItemStackStringTranslator.fromStringAdvanced(srecipe[j]);
				}
			}
		} else{
			for(int i = 0; i < srecipe.length; i++){
				if(map.containsKey(srecipe[i])){
					recipe[i] = map.get(srecipe[i]);
				} else{
					recipe[i] = ItemStackStringTranslator.fromStringAdvanced(srecipe[i]);
				}
			}
		}
		return recipe;
	}

	public static IRecipe fromString(ItemStack result, Map<String, ?> map, String... srecipe){
		if(isEmpty(srecipe)){
			return new IRecipe(){

				@Override
				public boolean matches(InventoryCrafting inventory, World world){
					return false;
				}

				@Override
				public ItemStack getCraftingResult(InventoryCrafting inventory){
					return null;
				}

				@Override
				public int getRecipeSize(){
					return 0;
				}

				@Override
				public ItemStack getRecipeOutput(){
					return null;
				}

				@Override
				public ItemStack[] getRemainingItems(InventoryCrafting inventory){
					return null;
				}

			};
		} else if(isShaped(map, srecipe)){
			return new ShapedOreRecipe(result, fromString(map, srecipe));
		} else{
			return new ShapelessOreRecipe(result, fromString(map, srecipe));
		}
	}

	public static boolean isShaped(Object... recipe){
		return recipe[0] instanceof String;
	}

	public static String[] toString(Object... recipe){
		String[] srecipe = new String[recipe.length];
		if(isShaped(recipe)){
			int i = 0;
			for(i = 0; i < recipe.length; i++){
				if(recipe[i] instanceof Character) break;
			}
			for(int j = 0; j < i; j++){
				srecipe[j] = (String) recipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(recipe[j] instanceof Character){
					srecipe[j] = "" + recipe[j];
				} else{
					srecipe[j] = ItemStackStringTranslator.toStringAdvanced(recipe[j]);
				}
			}
		} else{
			for(int i = 0; i < recipe.length; i++){
				srecipe[i] = ItemStackStringTranslator.toStringAdvanced(recipe[i]);
			}
		}
		return srecipe;
	}

	public static String[] toString(IRecipe recipe){
		if(recipe instanceof ShapedOreRecipe){
			return toString(((ShapedOreRecipe) recipe).getInput());
		} else if(recipe instanceof ShapelessOreRecipe){
			return toString(((ShapelessOreRecipe) recipe).getInput().toArray());
		} else{
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe");
		}
	}

	public static boolean isShaped(Map<?, String> map, Object... recipe){
		return recipe[0] instanceof String && !map.containsKey(recipe[0]);
	}

	public static String[] toString(Map<?, String> map, Object... recipe){
		String[] srecipe = new String[recipe.length];
		if(isShaped(map, recipe)){
			int i = 0;
			for(i = 0; i < recipe.length; i++){
				if(recipe[i] instanceof Character) break;
			}
			for(int j = 0; j < i; j++){
				srecipe[j] = (String) recipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(recipe[j] instanceof Character){
					srecipe[j] = "" + recipe[j];
				} else if(map.containsKey(recipe[j])){
					srecipe[j] = map.get(recipe[j]);
				} else{
					srecipe[j] = ItemStackStringTranslator.toStringAdvanced(recipe[j]);
				}
			}
		} else{
			for(int i = 0; i < recipe.length; i++){
				if(map.containsKey(recipe[i])){
					srecipe[i] = map.get(recipe[i]);
				} else{
					srecipe[i] = ItemStackStringTranslator.toStringAdvanced(recipe[i]);
				}
			}
		}
		return srecipe;
	}

	public static String[] toString(IRecipe recipe, Map<?, String> map){
		if(recipe instanceof ShapedOreRecipe){
			return toString(map, ((ShapedOreRecipe) recipe).getInput());
		} else if(recipe instanceof ShapelessOreRecipe){
			return toString(map, ((ShapelessOreRecipe) recipe).getInput().toArray());
		} else{
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe");
		}
	}

}
