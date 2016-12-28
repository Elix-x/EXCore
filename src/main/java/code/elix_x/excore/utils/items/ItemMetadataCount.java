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

public class ItemMetadataCount extends ItemCount {

	public int metadata;

	public ItemMetadataCount(Item i, int c, int metadata){
		super(i, c);
		this.metadata = metadata;
	}

	public ItemMetadataCount(Block block, int c, int metadata){
		this(Item.getItemFromBlock(block), c, metadata);
	}

	public ItemMetadataCount(ItemStack itemstack){
		this(itemstack.getItem(), itemstack.getCount(), itemstack.getItemDamage());
	}

	@Override
	public ItemStack toItemstack(){
		return new ItemStack(item, count, metadata);
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + metadata;
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(getClass() != obj.getClass()) return false;
		ItemMetadataCount other = (ItemMetadataCount) obj;
		if(metadata != other.metadata) return false;
		return true;
	}

	@Override
	public String toString(){
		return item.getUnlocalizedName() + "/" + metadata + " * " + count;
	}

}
