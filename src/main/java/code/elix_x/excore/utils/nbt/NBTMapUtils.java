package code.elix_x.excore.utils.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTMapUtils {

	public static <K, V> NBTTagList toNbt(Map<K, V> map, Function<K, ? extends NBTBase> keyToNBT, Function<V, ? extends NBTBase> valueToNBT){
		NBTTagList list = new NBTTagList();
		for(Entry<K, V> entry : map.entrySet()){
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag("key", keyToNBT.apply(entry.getKey()));
			tag.setTag("value", valueToNBT.apply(entry.getValue()));
			list.appendTag(tag);
		}
		return list;
	}

	public static <K, V> Map<K, V> fromNbt(NBTTagList list, Function<NBTBase, K> nbtToKey, Function<NBTBase, V> nbtToValue){
		return fromNbt(new HashMap<K, V>(), list, nbtToKey, nbtToValue);
	}

	public static <K, V> Map<K, V> fromNbt(Map<K, V> map, NBTTagList list, Function<NBTBase, K> nbtToKey, Function<NBTBase, V> nbtToValue){
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound tag = list.getCompoundTagAt(i);
			map.put(nbtToKey.apply(tag.getTag("key")), nbtToValue.apply(tag.getTag("value")));
		}
		return map;
	}
}
