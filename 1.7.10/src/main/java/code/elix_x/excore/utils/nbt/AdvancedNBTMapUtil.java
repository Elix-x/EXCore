package code.elix_x.excore.utils.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class AdvancedNBTMapUtil <M extends Map<K, V>, N extends NBTBase, K, V, L extends NBTBase, W extends NBTBase> {

	public static final String DEFAULTKEYNAME = "key";
	public static final String DEFAULTVALUENAME = "value";
	
	public static final Function<NBTTagList, NBTTagList> defaultListConvert = new Function<NBTTagList, NBTTagList>() {

		@Override
		public NBTTagList apply(NBTTagList list) {
			return list;
		}
	};

	private Supplier<M> mapSupplier;

	private Function<NBTTagList, N> fromList;
	private Function<N, NBTTagList> toList;

	private Function<K, L> keyToNbt;
	private Function<V, W> valueToNbt;
	private Function<L, K> nbtToKey;
	private Function<W, V> nbtToValue;

	private String keyName;
	private String valueName;

	public AdvancedNBTMapUtil(Supplier<M> mapSupplier, Function<NBTTagList, N> fromList, Function<N, NBTTagList> toList, Function<K, L> keyToNbt, Function<V, W> valueToNbt, Function<L, K> nbtToKey, Function<W, V> nbtToValue, String keyName, String valueName) {
		this.mapSupplier = mapSupplier;
		this.fromList = fromList;
		this.toList = toList;
		this.keyToNbt = keyToNbt;
		this.valueToNbt = valueToNbt;
		this.nbtToKey = nbtToKey;
		this.nbtToValue = nbtToValue;
		this.keyName = keyName;
		this.valueName = valueName;
	}
	
	public AdvancedNBTMapUtil(Supplier<M> mapSupplier, Function<NBTTagList, N> fromList, Function<N, NBTTagList> toList, Function<K, L> keyToNbt, Function<V, W> valueToNbt, Function<L, K> nbtToKey, Function<W, V> nbtToValue) {
		this(mapSupplier, fromList, toList, keyToNbt, valueToNbt, nbtToKey, nbtToValue, DEFAULTKEYNAME, DEFAULTVALUENAME);
	}
	
	public static <M extends Map<K, V>, K, V, L extends NBTBase, W extends NBTBase> AdvancedNBTMapUtil<M, NBTTagList, K, V, L, W> AdvancedNbtMapUtil(Supplier<M> mapSupplier, Function<K, L> keyToNbt, Function<V, W> valueToNbt, Function<L, K> nbtToKey, Function<W, V> nbtToValue, String keyName, String valueName){
		return new AdvancedNBTMapUtil<M, NBTTagList, K, V, L, W>(mapSupplier, defaultListConvert, defaultListConvert, keyToNbt, valueToNbt, nbtToKey, nbtToValue, keyName, valueName);
	}
	
	public static <M extends Map<K, V>, K, V, L extends NBTBase, W extends NBTBase> AdvancedNBTMapUtil<M, NBTTagList, K, V, L, W> AdvancedNbtMapUtil(Supplier<M> mapSupplier, Function<K, L> keyToNbt, Function<V, W> valueToNbt, Function<L, K> nbtToKey, Function<W, V> nbtToValue){
		return new AdvancedNBTMapUtil<M, NBTTagList, K, V, L, W>(mapSupplier, defaultListConvert, defaultListConvert, keyToNbt, valueToNbt, nbtToKey, nbtToValue);
	}
	
	AdvancedNBTMapUtil<HashMap<String, Integer>, NBTTagList, String, Integer, NBTTagString, NBTTagInt> test = new AdvancedNBTMapUtil<HashMap<String, Integer>, NBTTagList, String, Integer, NBTTagString, NBTTagInt>(new Supplier<HashMap<String, Integer>>() {

		@Override
		public HashMap<String, Integer> get() {
			return new HashMap<String, Integer>();
		}

	}, defaultListConvert, defaultListConvert, new Function<String, NBTTagString>(){

		@Override
		public NBTTagString apply(String input) {
			return new NBTTagString(input);
		}

	}, new Function<Integer, NBTTagInt>(){

		@Override
		public NBTTagInt apply(Integer input) {
			return new NBTTagInt(input);
		}


	}, new Function<NBTTagString, String>(){

		@Override
		public String apply(NBTTagString input) {
			return input.func_150285_a_();
		}
		
		
	}, new Function<NBTTagInt, Integer>(){

		@Override
		public Integer apply(NBTTagInt input) {
			return input.func_150287_d();
		}
		
	}, "name", "count");

	public N toNbt(M map){
		NBTTagList list = new NBTTagList();
		for(Entry<K, V> entry : map.entrySet()){
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag(keyName, keyToNbt.apply(entry.getKey()));
			tag.setTag(valueName, valueToNbt.apply(entry.getValue()));
			list.appendTag(tag);
		}
		return fromList.apply(list);
	}

	public M fromNbt(N nbt){
		return populate(mapSupplier.get(), nbt);
	}

	public M populate(M map, N nbt){
		NBTTagList list = toList.apply(nbt);
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound tag = list.getCompoundTagAt(i);
			map.put(nbtToKey.apply((L) tag.getTag(keyName)), nbtToValue.apply((W) tag.getTag(valueName)));
		}
		return map;
	}

}
