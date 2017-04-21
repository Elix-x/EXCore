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

public class ItemCount {

	public Item item;
	public int count;

	public ItemCount(Item i, int c){
		item = i;
		count = c;
	}

	public ItemCount(Block block, int c){
		this(Item.getItemFromBlock(block), c);
	}

	public ItemCount(ItemStack itemstack){
		this(itemstack.getItem(), itemstack.getCount());
	}

	public ItemStack toItemstack(int meta){
		return new ItemStack(item, count, meta);
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		ItemCount other = (ItemCount) obj;
		if(count != other.count) return false;
		if(item == null){
			if(other.item != null) return false;
		} else if(!item.equals(other.item)) return false;
		return true;
	}

	@Override
	public String toString(){
		return item.getUnlocalizedName() + " * " + count;
	}

}
