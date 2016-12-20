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
package code.elix_x.excore.utils.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackStringTranslator {

	public static final String NULL = "NULL";
	public static final String OREDICT = "oreDict";

	public static String toString(ItemStack itemstack){
		return itemstack == null ? NULL : (Item.REGISTRY.getNameForObject(itemstack.getItem())) + (itemstack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? "" : "/" + itemstack.getItemDamage());
	}

	public static ItemStack fromString(String string){
		if(string.equals(NULL)){
			return null;
		} else{
			try{
				if(string.split(":").length == 1){
					string = "minecraft:" + string;
				}
				String[] modidId = string.split(":");
				String modid = modidId[0];
				String id = modidId[1];
				String[] idMeta = id.split("/");
				int meta = OreDictionary.WILDCARD_VALUE;
				if(idMeta.length == 2){
					id = idMeta[0];
					meta = Integer.parseInt(idMeta[1]);
				}
				Item item = Item.REGISTRY.getObject(new ResourceLocation(modid, id));
				if(item != null){
					return new ItemStack(item, 1, meta);
				}
			} catch(Exception e){
				throw new IllegalArgumentException("Could not initalize item stack. Invalid argument was given: " + string, e);
			}
			throw new IllegalArgumentException("Could not initalize item stack. Invalid argument was given: " + string);
		}
	}

	public static String toStringAdvanced(Object o){
		if(o == null){
			return NULL;
		} else if(o instanceof ItemStack){
			return toString((ItemStack) o);
		} else if(o instanceof ItemCount){
			return toString(((ItemCount) o).toItemstack());
		} else if(o instanceof Item){
			return toString(new ItemStack((Item) o, 1, OreDictionary.WILDCARD_VALUE));
		} else if(o instanceof Block){
			return toString(new ItemStack((Block) o, 1, OreDictionary.WILDCARD_VALUE));
		} else if(o instanceof String){
			return OREDICT + ":" + o;
		} else{
			throw new IllegalArgumentException("Illegal argument: " + o);
		}
	}

	public static Object fromStringAdvanced(String s){
		if(s.equals(NULL)){
			return null;
		} else{
			if(s.split(":").length == 1){
				s = "minecraft:" + s;
			}
			if(s.split(":")[0].equals(OREDICT)){
				return s.split(":")[1];
			} else{
				return fromString(s);
			}
		}
	}

	public static boolean isValidItemstack(String s){
		try{
			ItemStackStringTranslator.fromString(s);
			return true;
		} catch(IllegalArgumentException e){
			return false;
		}
	}

	public static boolean isValidItemstackAdvanced(String s){
		try{
			ItemStackStringTranslator.fromStringAdvanced(s);
			return true;
		} catch(IllegalArgumentException e){
			return false;
		}
	}

}
