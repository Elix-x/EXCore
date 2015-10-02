package code.elix_x.excore.utils.recipes;

import java.util.Map;

import code.elix_x.excore.utils.items.ItemStackStringTranslator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeStringTranslator {

	public static boolean isShaped(String... recipe) {
		return !ItemStackStringTranslator.isValidItemstackAdvanced(recipe[0]);
	}

	public static Object[] fromString(String... srecipe){
		Object[] recipe = new Object[srecipe.length];
		if(isShaped(srecipe)){
			int i;
			for(i = 0; i < srecipe.length; i++){
				if(srecipe[i].length() == 1 && ItemStackStringTranslator.isValidItemstackAdvanced(srecipe[i + 1])) break;
			}
			for(int j = 0; j < i; j++){
				recipe[j] = srecipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(srecipe[j].length() == 1){
					recipe[j] = srecipe[j].charAt(0);
				} else {
					recipe[j] = ItemStackStringTranslator.fromStringAdvanced(srecipe[j]);
				}
			}
		} else {
			for(int i = 0; i < srecipe.length; i++){
				recipe[i] = ItemStackStringTranslator.fromStringAdvanced(srecipe[i]);
			}
		}
		return recipe;
	}

	public static IRecipe fromString(ItemStack result, String... srecipe){
		if(isShaped(srecipe)){
			return new ShapedOreRecipe(result, fromString(srecipe));
		} else {
			return new ShapelessOreRecipe(result, fromString(srecipe));
		}
	}

	public static boolean isShapedAdvanced(Map<String, Object> map, String... recipe) {
		return !(map.containsKey(recipe[0]) || ItemStackStringTranslator.isValidItemstackAdvanced(recipe[0]));
	}

	public static Object[] fromStringAdvanced(Map<String, Object> map, String... srecipe){
		Object[] recipe = new Object[srecipe.length];
		if(isShapedAdvanced(map, srecipe)){
			int i;
			for(i = 0; i < srecipe.length; i++){
				if(srecipe[i].length() == 1 && (map.containsKey(srecipe[i + 1]) || ItemStackStringTranslator.isValidItemstackAdvanced(srecipe[i + 1]))) break;
			}
			for(int j = 0; j < i; j++){
				recipe[j] = srecipe[j];
			}
			for(int j = i; j < srecipe.length; j++){
				if(srecipe[j].length() == 1){
					recipe[j] = srecipe[j].charAt(0);
				} else if(map.containsKey(srecipe[j])){
					recipe[j] = map.get(srecipe[j]);
				} else {
					recipe[j] = ItemStackStringTranslator.fromStringAdvanced(srecipe[j]);
				}
			}
		} else {
			for(int i = 0; i < srecipe.length; i++){
				if(map.containsKey(srecipe[i])){
					recipe[i] = map.get(srecipe[i]);
				} else {
					recipe[i] = ItemStackStringTranslator.fromStringAdvanced(srecipe[i]);
				}
			}
		}
		return recipe;
	}

	public static IRecipe fromStringAdvanced(ItemStack result, Map<String, Object> map, String... srecipe){
		if(isShapedAdvanced(map, srecipe)){
			return new ShapedOreRecipe(result, fromStringAdvanced(map, srecipe));
		} else {
			return new ShapelessOreRecipe(result, fromStringAdvanced(map, srecipe));
		}
	}
	
	public static boolean isShaped(Object... recipe) {
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
				} else {
					srecipe[j] = ItemStackStringTranslator.toStringAdvanced(recipe[j]);
				}
			}
		} else {
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
		}/* else if(recipe instanceof ShapedRecipes){
			return toString(recipe.)
		} else if(recipe instanceof ShapelessRecipes){
			
		}*/ else {
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe or ShapedRecipes or ShapelessRecipes");
		}
	}
	
	public static boolean isShaped(Map<Object, String> map, Object... recipe) {
		return recipe[0] instanceof String && !map.containsKey(recipe[0]);
	}
	
	public static String[] toString(Map<Object, String> map, Object... recipe){
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
					srecipe[j] = map.get(srecipe[j]);
				} else {
					srecipe[j] = ItemStackStringTranslator.toStringAdvanced(recipe[j]);
				}
			}
		} else {
			for(int i = 0; i < recipe.length; i++){
				if(map.containsKey(recipe[i])){
					srecipe[i] = map.get(srecipe[i]);
				} else {
					srecipe[i] = ItemStackStringTranslator.toStringAdvanced(recipe[i]);
				}
			}
		}
		return srecipe;
	}
	
	public static String[] toString(IRecipe recipe, Map<Object, String> map){
		if(recipe instanceof ShapedOreRecipe){
			return toString(map, ((ShapedOreRecipe) recipe).getInput());
		} else if(recipe instanceof ShapelessOreRecipe){
			return toString(map, ((ShapelessOreRecipe) recipe).getInput().toArray());
		}/* else if(recipe instanceof ShapedRecipes){
			return toString(recipe.)
		} else if(recipe instanceof ShapelessRecipes){
			
		}*/ else {
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe or ShapedRecipes or ShapelessRecipes");
		}
	}
	
}