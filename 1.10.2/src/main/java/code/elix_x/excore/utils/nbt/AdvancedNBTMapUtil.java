package code.elix_x.excore.utils.nbt;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class AdvancedNBTMapUtil<M extends Map<K, V>, N extends NBTBase, K, V, KN extends NBTBase, VN extends NBTBase> {

	public static final String DEFAULTKEYNAME = "key";
	public static final String DEFAULTVALUENAME = "value";

	private String keyName;
	private String valueName;

	public AdvancedNBTMapUtil(String keyName, String valueName){
		this.keyName = keyName;
		this.valueName = valueName;
	}

	public AdvancedNBTMapUtil(){
		this(DEFAULTKEYNAME, DEFAULTVALUENAME);
	}

	public abstract M newMap();

	public abstract N fromList(NBTTagList list);

	public abstract NBTTagList toList(N n);

	public abstract KN keyToNBT(K key);

	public abstract VN valueToNBT(V value);

	public abstract K nbtToKey(KN nbt);

	public abstract V nbtToValue(VN nbt);

	public N toNbt(M map){
		NBTTagList list = new NBTTagList();
		for(Entry<K, V> entry : map.entrySet()){
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag(keyName, keyToNBT(entry.getKey()));
			tag.setTag(valueName, valueToNBT(entry.getValue()));
			list.appendTag(tag);
		}
		return fromList(list);
	}

	public M fromNbt(N nbt){
		return populate(newMap(), nbt);
	}

	public M populate(M map, N nbt){
		NBTTagList list = toList(nbt);
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound tag = list.getCompoundTagAt(i);
			map.put(nbtToKey((KN) tag.getTag(keyName)), nbtToValue((VN) tag.getTag(valueName)));
		}
		return map;
	}

	public abstract static class SimplifiedAdvancedNBTMapUtil<M extends Map<K, V>, K, V, KN extends NBTBase, VN extends NBTBase> extends AdvancedNBTMapUtil<M, NBTTagList, K, V, KN, VN> {

		public SimplifiedAdvancedNBTMapUtil(String keyName, String valueName){
			super(keyName, valueName);
		}

		public SimplifiedAdvancedNBTMapUtil(){
			super();
		}

		@Override
		public NBTTagList fromList(NBTTagList list){
			return list;
		}

		@Override
		public NBTTagList toList(NBTTagList list){
			return list;
		}

	}

	public static class FunctionDefinedAdvancedNBTMapUtil<M extends Map<K, V>, N extends NBTBase, K, V, KN extends NBTBase, VN extends NBTBase> extends AdvancedNBTMapUtil<M, N, K, V, KN, VN> {

		private Supplier<M> mapSupplier;

		private Function<NBTTagList, N> fromList;
		private Function<N, NBTTagList> toList;

		private Function<K, KN> keyToNBT;
		private Function<V, VN> valueToNBT;
		private Function<KN, K> nbtToKey;
		private Function<VN, V> nbtToValue;

		public FunctionDefinedAdvancedNBTMapUtil(Supplier<M> mapSupplier, Function<NBTTagList, N> fromList, Function<N, NBTTagList> toList, Function<K, KN> keyToNbt, Function<V, VN> valueToNbt, Function<KN, K> nbtToKey, Function<VN, V> nbtToValue, String keyName, String valueName){
			super(keyName, valueName);
			this.mapSupplier = mapSupplier;
			this.fromList = fromList;
			this.toList = toList;
			this.keyToNBT = keyToNbt;
			this.valueToNBT = valueToNbt;
			this.nbtToKey = nbtToKey;
			this.nbtToValue = nbtToValue;
		}

		@Override
		public M newMap(){
			return mapSupplier.get();
		}

		@Override
		public N fromList(NBTTagList list){
			return fromList.apply(list);
		}

		@Override
		public NBTTagList toList(N n){
			return toList.apply(n);
		}

		@Override
		public KN keyToNBT(K key){
			return keyToNBT.apply(key);
		}

		@Override
		public VN valueToNBT(V value){
			return valueToNBT.apply(value);
		}

		@Override
		public K nbtToKey(KN nbt){
			return nbtToKey.apply(nbt);
		}

		@Override
		public V nbtToValue(VN nbt){
			return nbtToValue.apply(nbt);
		}

	}

	public static class SimplifiedFunctionDefinedAdvancedNBTMapUtil<M extends Map<K, V>, K, V, KN extends NBTBase, VN extends NBTBase> extends SimplifiedAdvancedNBTMapUtil<M, K, V, KN, VN> {

		private Supplier<M> mapSupplier;

		private Function<K, KN> keyToNBT;
		private Function<V, VN> valueToNBT;
		private Function<KN, K> nbtToKey;
		private Function<VN, V> nbtToValue;

		public SimplifiedFunctionDefinedAdvancedNBTMapUtil(Supplier<M> mapSupplier, Function<K, KN> keyToNbt, Function<V, VN> valueToNbt, Function<KN, K> nbtToKey, Function<VN, V> nbtToValue, String keyName, String valueName){
			super(keyName, valueName);
			this.mapSupplier = mapSupplier;
			this.keyToNBT = keyToNbt;
			this.valueToNBT = valueToNbt;
			this.nbtToKey = nbtToKey;
			this.nbtToValue = nbtToValue;
		}

		@Override
		public M newMap(){
			return mapSupplier.get();
		}

		@Override
		public KN keyToNBT(K key){
			return keyToNBT.apply(key);
		}

		@Override
		public VN valueToNBT(V value){
			return valueToNBT.apply(value);
		}

		@Override
		public K nbtToKey(KN nbt){
			return nbtToKey.apply(nbt);
		}

		@Override
		public V nbtToValue(VN nbt){
			return nbtToValue.apply(nbt);
		}

	}

}
