package code.elix_x.excore.utils.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
				Item item = GameRegistry.findItem(modid, id);
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
