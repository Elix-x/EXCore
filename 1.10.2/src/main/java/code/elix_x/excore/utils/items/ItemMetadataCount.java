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
		this(itemstack.getItem(), itemstack.stackSize, itemstack.getItemDamage());
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
