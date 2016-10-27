package code.elix_x.excore.utils.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
		this(itemstack.getItem(), itemstack.stackSize);
	}

	public ItemStack toItemstack(){
		return new ItemStack(item, count, OreDictionary.WILDCARD_VALUE);
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
