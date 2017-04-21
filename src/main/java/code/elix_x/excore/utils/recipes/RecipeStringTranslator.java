/*******************************************************************************
 * Copyright 2016 Elix_x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package code.elix_x.excore.utils.recipes;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import code.elix_x.excore.utils.items.ItemStackStringSerializer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeStringTranslator {

	public final ItemStackStringSerializer stackSerializer;

	public RecipeStringTranslator(ItemStackStringSerializer stackSerializer){
		this.stackSerializer = stackSerializer;
	}

	public String[] validateFromConfig(String... recipe){
		recipe = ArrayUtils.clone(recipe);
		if(!isEmpty(recipe) && isShaped(recipe)){
			for(int i = 0; i < getDefinitionsBegining(recipe); i++){
				recipe[i] = recipe[i].replace('_', ' ');
			}
		}
		return recipe;
	}

	public boolean isValidStack(String stack){
		Object o = stackSerializer.fromStringAdvanced(stack);
		return !(o instanceof ItemStack && ((ItemStack) o).isEmpty());
	}

	public boolean isEmpty(String... srecipe){
		return ArrayUtils.isEmpty(srecipe);
	}

	public boolean isShaped(String... recipe){
		return isValidStack(recipe[0]);
	}

	public int getDefinitionsBegining(String... recipe){
		int i;
		for(i = 0; i < recipe.length; i++){
			if(recipe[i].length() == 1 && isValidStack(recipe[i + 1])) break;
		}
		return i;
	}

	public Object[] fromString(String... srecipe){
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
					recipe[j] = stackSerializer.fromStringAdvanced(srecipe[j]);
				}
			}
		} else{
			for(int i = 0; i < srecipe.length; i++){
				recipe[i] = stackSerializer.fromStringAdvanced(srecipe[i]);
			}
		}
		return recipe;
	}

	public IRecipe fromString(ItemStack result, String... srecipe){
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
				public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory){
					return null;
				}

			};
		} else if(isShaped(srecipe)){
			return new ShapedOreRecipe(result, fromString(srecipe));
		} else{
			return new ShapelessOreRecipe(result, fromString(srecipe));
		}
	}

	public boolean isShaped(Map<String, ?> map, String... recipe){
		return !(map.containsKey(recipe[0]) || isValidStack(recipe[0]));
	}

	public int getDefinitionsBegining(Map<String, ?> map, String... recipe){
		int i;
		for(i = 0; i < recipe.length; i++){
			if(recipe[i].length() == 1 && (map.containsKey(recipe[i + 1]) || isValidStack(recipe[i + 1]))) break;
		}
		return i;
	}

	public Object[] fromString(Map<String, ?> map, String... srecipe){
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
					recipe[j] = stackSerializer.fromStringAdvanced(srecipe[j]);
				}
			}
		} else{
			for(int i = 0; i < srecipe.length; i++){
				if(map.containsKey(srecipe[i])){
					recipe[i] = map.get(srecipe[i]);
				} else{
					recipe[i] = stackSerializer.fromStringAdvanced(srecipe[i]);
				}
			}
		}
		return recipe;
	}

	public IRecipe fromString(ItemStack result, Map<String, ?> map, String... srecipe){
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
				public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory){
					return null;
				}

			};
		} else if(isShaped(map, srecipe)){
			return new ShapedOreRecipe(result, fromString(map, srecipe));
		} else{
			return new ShapelessOreRecipe(result, fromString(map, srecipe));
		}
	}

	public boolean isShaped(Object... recipe){
		return recipe[0] instanceof String;
	}

	public String[] toString(Object... recipe){
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
					srecipe[j] = stackSerializer.toStringAdvanced(recipe[j]);
				}
			}
		} else{
			for(int i = 0; i < recipe.length; i++){
				srecipe[i] = stackSerializer.toStringAdvanced(recipe[i]);
			}
		}
		return srecipe;
	}

	public String[] toString(IRecipe recipe){
		if(recipe instanceof ShapedOreRecipe){
			return toString(((ShapedOreRecipe) recipe).getInput());
		} else if(recipe instanceof ShapelessOreRecipe){
			return toString(((ShapelessOreRecipe) recipe).getInput().toArray());
		} else{
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe");
		}
	}

	public boolean isShaped(Map<?, String> map, Object... recipe){
		return recipe[0] instanceof String && !map.containsKey(recipe[0]);
	}

	public String[] toString(Map<?, String> map, Object... recipe){
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
					srecipe[j] = stackSerializer.toStringAdvanced(recipe[j]);
				}
			}
		} else{
			for(int i = 0; i < recipe.length; i++){
				if(map.containsKey(recipe[i])){
					srecipe[i] = map.get(recipe[i]);
				} else{
					srecipe[i] = stackSerializer.toStringAdvanced(recipe[i]);
				}
			}
		}
		return srecipe;
	}

	public String[] toString(IRecipe recipe, Map<?, String> map){
		if(recipe instanceof ShapedOreRecipe){
			return toString(map, ((ShapedOreRecipe) recipe).getInput());
		} else if(recipe instanceof ShapelessOreRecipe){
			return toString(map, ((ShapelessOreRecipe) recipe).getInput().toArray());
		} else{
			throw new IllegalArgumentException("Recipe must either be ShapedOreRecipe or ShapelessOreRecipe");
		}
	}

}
