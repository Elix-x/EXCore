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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackStringTranslator {

	public static final String EMPTY = "EMPTY";
	public static final String OREDICT = "OREDICT";

	public final String empty;
	public final String oredict;
	public final int defaultMetadata;

	public ItemStackStringTranslator(String empty, String oredict, int defaultMetadata){
		this.empty = empty;
		this.oredict = oredict;
		this.defaultMetadata = defaultMetadata;
	}

	public ItemStackStringTranslator(int defaultMetadata){
		this(EMPTY, OREDICT, defaultMetadata);
	}

	/**
	 * Serialize item stack as string.<br>
	 * Supports empty stacks.<br>
	 * Does not support NBT.
	 * @param itemstack stack to serialize
	 * @return serialized version of stack
	 */
	@Nonnull
	public String toString(@Nonnull ItemStack itemstack){
		return itemstack.isEmpty() ? empty : (Item.REGISTRY.getNameForObject(itemstack.getItem())) + (itemstack.getItemDamage() == defaultMetadata ? "" : "/" + itemstack.getItemDamage());
	}

	/**
	 * Deserialize given string into item stack.<br>
	 * Supports empty stacks.<br>
	 * Does not support NBT.<br>
	 * Supports <tt>null</tt> string.<br>
	 * If input is invalid, an empty item stack will be returned.
	 * 
	 * @param string string representation of item stack to deserialize
	 * @return item stack result of deserialization
	 */
	@Nonnull
	public ItemStack fromString(@Nullable String string){
		if(string == null) return ItemStack.EMPTY;
		ResourceLocation id = new ResourceLocation(string);
		int meta = defaultMetadata;
		if(id.getResourcePath().contains("/")){
			String[] idMeta = id.getResourcePath().split("/");
			id = new ResourceLocation(id.getResourceDomain(), idMeta[0]);
			meta = Integer.parseInt(idMeta[1]);
		}
		return new ItemStack(Item.REGISTRY.getObject(id), 1, meta);
	}

	public String toStringAdvanced(Object o){
		if(o == null){
			return empty;
		} else if(o instanceof ItemStack){
			return toString((ItemStack) o);
		} else if(o instanceof ItemCount){
			return toString(((ItemCount) o).toItemstack());
		} else if(o instanceof Item){
			return toString(new ItemStack((Item) o, 1, OreDictionary.WILDCARD_VALUE));
		} else if(o instanceof Block){
			return toString(new ItemStack((Block) o, 1, OreDictionary.WILDCARD_VALUE));
		} else if(o instanceof String){
			return oredict + ":" + o;
		} else{
			throw new IllegalArgumentException("Illegal argument: " + o);
		}
	}

	public Object fromStringAdvanced(String s){
		if(s.equals(empty)){
			return null;
		} else{
			if(s.split(":").length == 1){
				s = "minecraft:" + s;
			}
			if(s.split(":")[0].equals(oredict)){
				return s.split(":")[1];
			} else{
				return fromString(s);
			}
		}
	}

}
