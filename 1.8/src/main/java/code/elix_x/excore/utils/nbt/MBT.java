package code.elix_x.excore.utils.nbt;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Throwables;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

public class MBT {

	public static final NBTEncoder<Boolean, NBTTagByte> booleanEncoder = new NBTEncoder<Boolean, NBTTagByte>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Byte;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagByte && Boolean.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagByte toNBT(MBT mbt, Boolean b) {
			return new NBTTagByte((byte) (b ? 1 : 0));
		}

		@Override
		public Boolean fromNBT(MBT mbt, NBTTagByte nbt, Class<Boolean> clazz, Class... tsclasses) {
			return nbt.getByte() == 1;
		}

	};

	public static final NBTEncoder<Byte, NBTTagByte> byteEncoder = new NBTEncoder<Byte, NBTTagByte>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Byte;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagByte;
		}

		@Override
		public NBTTagByte toNBT(MBT mbt, Byte b) {
			return new NBTTagByte(b);
		}

		@Override
		public Byte fromNBT(MBT mbt, NBTTagByte nbt, Class<Byte> clazz, Class... tsclasses) {
			return nbt.getByte();
		}

	};

	public static final NBTEncoder<Short, NBTTagShort> shortEncoder = new NBTEncoder<Short, NBTTagShort>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Short;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagShort;
		}

		@Override
		public NBTTagShort toNBT(MBT mbt, Short s) {
			return new NBTTagShort(s);
		}

		@Override
		public Short fromNBT(MBT mbt, NBTTagShort nbt, Class<Short> clazz, Class... tsclasses) {
			return nbt.getShort();
		}

	};

	public static final NBTEncoder<Integer, NBTTagInt> intEncoder = new NBTEncoder<Integer, NBTTagInt>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Integer;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagInt;
		}

		@Override
		public NBTTagInt toNBT(MBT mbt, Integer i) {
			return new NBTTagInt(i);
		}

		@Override
		public Integer fromNBT(MBT mbt, NBTTagInt nbt, Class<Integer> clazz, Class... tsclasses) {
			return nbt.getInt();
		}

	};

	public static final NBTEncoder<Long, NBTTagLong> longEncoder = new NBTEncoder<Long, NBTTagLong>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Long;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagLong;
		}

		@Override
		public NBTTagLong toNBT(MBT mbt, Long l) {
			return new NBTTagLong(l);
		}

		@Override
		public Long fromNBT(MBT mbt, NBTTagLong nbt, Class<Long> clazz, Class... tsclasses) {
			return nbt.getLong();
		}

	};

	public static final NBTEncoder<Float, NBTTagFloat> floatEncoder = new NBTEncoder<Float, NBTTagFloat>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Float;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagFloat;
		}

		@Override
		public NBTTagFloat toNBT(MBT mbt, Float f) {
			return new NBTTagFloat(f);
		}

		@Override
		public Float fromNBT(MBT mbt, NBTTagFloat nbt, Class<Float> clazz, Class... tsclasses) {
			return nbt.getFloat();
		}

	};

	public static final NBTEncoder<Double, NBTTagDouble> doubleEncoder = new NBTEncoder<Double, NBTTagDouble>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Double;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagDouble;
		}

		@Override
		public NBTTagDouble toNBT(MBT mbt, Double d) {
			return new NBTTagDouble(d);
		}

		@Override
		public Double fromNBT(MBT mbt, NBTTagDouble nbt, Class<Double> clazz, Class... tsclasses) {
			return nbt.getDouble();
		}

	};

	public static final NBTEncoder<byte[], NBTTagByteArray> byteArrayEncoder = new NBTEncoder<byte[], NBTTagByteArray>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof byte[];
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagByteArray;
		}

		@Override
		public NBTTagByteArray toNBT(MBT mbt, byte[] bs) {
			return new NBTTagByteArray(bs);
		}

		@Override
		public byte[] fromNBT(MBT mbt, NBTTagByteArray nbt, Class<byte[]> clazz, Class... tsclasses) {
			return nbt.getByteArray();
		}

	};

	public static final NBTEncoder<int[], NBTTagIntArray> intArrayEncoder = new NBTEncoder<int[], NBTTagIntArray>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof int[];
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagIntArray;
		}

		@Override
		public NBTTagIntArray toNBT(MBT mbt, int[] is) {
			return new NBTTagIntArray(is);
		}

		@Override
		public int[] fromNBT(MBT mbt, NBTTagIntArray nbt, Class<int[]> clazz, Class... tsclasses) {
			return nbt.getIntArray();
		}

	};

	public static final NBTEncoder<String, NBTTagString> stringEncoder = new NBTEncoder<String, NBTTagString>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof String;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagString && !clazz.isEnum();
		}

		@Override
		public NBTTagString toNBT(MBT mbt, String s) {
			return new NBTTagString(s);
		}

		@Override
		public String fromNBT(MBT mbt, NBTTagString nbt, Class<String> clazz, Class... tsclasses) {
			return nbt.getString();
		}

	};

	public static final NBTEncoder<NBTBase, NBTBase> nbtEncoder = new NBTEncoder<NBTBase, NBTBase>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof NBTBase;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return NBTBase.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTBase toNBT(MBT mbt, NBTBase nbt) {
			return nbt;
		}

		@Override
		public NBTBase fromNBT(MBT mbt, NBTBase nbt, Class<NBTBase> clazz, Class... tsclasses) {
			return nbt;
		}

	};
	
	public static final NBTEncoder<ItemStack, NBTTagCompound> itemStackEncoder = new NBTEncoder<ItemStack, NBTTagCompound>(){

		@Override
		public boolean canEncode(Object o) {
			return o instanceof ItemStack;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return ItemStack.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagCompound toNBT(MBT mbt, ItemStack itemstack) {
			return itemstack.writeToNBT(new NBTTagCompound());
		}

		@Override
		public ItemStack fromNBT(MBT mbt, NBTTagCompound nbt, Class<ItemStack> clazz, Class... tsclasses) {
			return ItemStack.loadItemStackFromNBT(nbt);
		}

	};

	public static final NBTEncoder<Enum, NBTTagString> enumEncoder = new NBTEncoder<Enum, NBTTagString>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Enum;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagString && clazz.isEnum();
		}

		@Override
		public NBTTagString toNBT(MBT mbt, Enum e) {
			return new NBTTagString(e.name());
		}

		@Override
		public Enum fromNBT(MBT mbt, NBTTagString nbt, Class<Enum> clazz, Class... tsclasses) {
			return Enum.valueOf(clazz, nbt.getString());
		}

	};
	
	public static final NBTEncoder<Object, NBTTagCompound> nullEncoder = new NBTEncoder<Object, NBTTagCompound>(){

		public static final String NULL = "#NULL";

		@Override
		public boolean canEncode(Object o) {
			return o == null;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagCompound && ((NBTTagCompound) nbt).hasKey(NULL) && ((NBTTagCompound) nbt).getString(NULL).equals(NULL);
		}

		@Override
		public NBTTagCompound toNBT(MBT mbt, Object t) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString(NULL, NULL);
			return nbt;
		}

		@Override
		public Object fromNBT(MBT mbt, NBTTagCompound nbt, Class<Object> clazz, Class... tsclasses) {
			return null;
		}

	};

	public static final NBTEncoder<Object[], NBTTagList> arrayEncoder = new NBTEncoder<Object[], NBTTagList>() {

		@Override
		public boolean canEncode(Object o) {
			return o.getClass().isArray();
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagList && clazz.isArray();
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Object[] os) {
			NBTTagList nlist = new NBTTagList();
			for(Object o : os){
				nlist.appendTag(mbt.toNBT(o));
			}
			return nlist;
		}

		@Override
		public Object[] fromNBT(MBT mbt, NBTTagList list, Class<Object[]> clazz, Class... tsclasses) {
			Object[] os = (Object[]) Array.newInstance(clazz.getComponentType(), 0);
			for(int i = 0; i < list.tagCount(); i++){
				os = ArrayUtils.add(os, mbt.fromNBT(list.get(i), clazz.getComponentType()));
			}
			return os;
		}

	};

	public static final NBTEncoder<List<?>, NBTTagList> listEncoder = new NBTEncoder<List<?>, NBTTagList>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof List;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagList && List.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, List<?> list) {
			NBTTagList nlist = new NBTTagList();
			for(Object o : list){
				nlist.appendTag(mbt.toNBT(o));
			}
			return nlist;
		}

		@Override
		public List<Object> fromNBT(MBT mbt, NBTTagList nlist, Class<List<?>> clazz, Class... tsclasses) {
			try{
				List list;
				try{
					list = clazz.newInstance();
				} catch(InstantiationException e){
					list = new ArrayList();
				}
				for(int i = 0; i < nlist.tagCount(); i++){
					list.add(mbt.fromNBT(nlist.get(i), tsclasses[0]));
				}
				return list;
			} catch (IllegalAccessException e) {
				throw Throwables.propagate(e);
			}
		}

	};

	public static final NBTEncoder<Set<?>, NBTTagList> setEncoder = new NBTEncoder<Set<?>, NBTTagList>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Set;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagList && Set.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Set<?> set) {
			NBTTagList list = new NBTTagList();
			for(Object o : set){
				list.appendTag(mbt.toNBT(o));
			}
			return list;
		}

		@Override
		public Set<Object> fromNBT(MBT mbt, NBTTagList list, Class<Set<?>> clazz, Class... tsclasses) {
			try{
				Set set;
				try{
					set = clazz.newInstance();
				} catch(InstantiationException e){
					set = new HashSet();
				}
				for(int i = 0; i < list.tagCount(); i++){
					set.add(mbt.fromNBT(list.get(i), tsclasses[0]));
				}
				return set;
			} catch (IllegalAccessException e) {
				throw Throwables.propagate(e);
			}
		}

	};

	public static final NBTEncoder<Map<?, ?>, NBTTagList> mapEncoder = new NBTEncoder<Map<?,?>, NBTTagList>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Map;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagList && Map.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Map<?, ?> map) {
			NBTTagList list = new NBTTagList();
			for(Entry<?, ?> e : map.entrySet()){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setTag("key", mbt.toNBT(e.getKey()));
				tag.setTag("value", mbt.toNBT(e.getValue()));
				list.appendTag(tag);
			}
			return list;
		}

		@Override
		public Map<?, ?> fromNBT(MBT mbt, NBTTagList list, Class<Map<?, ?>> clazz, Class... tsclasses) {
			try{
				Map map;
				try{
					map = clazz.newInstance();
				} catch(InstantiationException e){
					map = new HashMap();
				}
				for(int i = 0; i < list.tagCount(); i++){
					NBTTagCompound tag = list.getCompoundTagAt(i);
					map.put(mbt.fromNBT(tag.getTag("key"), tsclasses[0]), mbt.fromNBT(tag.getTag("value"), tsclasses[1]));
				}
				return map;
			} catch (IllegalAccessException e) {
				throw Throwables.propagate(e);
			}
		}

	};

	public static final NBTEncoder<Multimap<?, ?>, NBTTagList> multimapEncoder = new NBTEncoder<Multimap<?,?>, NBTTagList>() {

		@Override
		public boolean canEncode(Object o) {
			return o instanceof Multimap;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagList && Multimap.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Multimap<?, ?> map) {
			NBTTagList list = new NBTTagList();
			for(Entry<?, ?> e : map.entries()){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setTag("key", mbt.toNBT(e.getKey()));
				tag.setTag("value", mbt.toNBT(e.getValue()));
				list.appendTag(tag);
			}
			return list;
		}

		@Override
		public Multimap<?, ?> fromNBT(MBT mbt, NBTTagList list, Class<Multimap<?, ?>> clazz, Class... tsclasses) {
			try{
				Multimap map;
				try{
					map = clazz.newInstance();
				} catch(InstantiationException e){
					map = HashMultimap.create();
				}
				for(int i = 0; i < list.tagCount(); i++){
					NBTTagCompound tag = list.getCompoundTagAt(i);
					map.put(mbt.fromNBT(tag.getTag("key"), tsclasses[0]), mbt.fromNBT(tag.getTag("value"), tsclasses[1]));
				}
				return map;
			} catch (IllegalAccessException e) {
				throw Throwables.propagate(e);
			}
		}

	};

	public static final NBTEncoder<? extends Object, NBTTagCompound> classEncoder = new NBTEncoder<Object, NBTTagCompound>() {

		@Override
		public boolean canEncode(Object o) {
			return o != null;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz) {
			return nbt instanceof NBTTagCompound;
		}

		@Override
		public NBTTagCompound toNBT(MBT mbt, Object t) {
			NBTTagCompound nbt = new NBTTagCompound();
			for(Field field : t.getClass().getDeclaredFields()){
				field.setAccessible(true);
				if(!Modifier.isStatic(field.getModifiers())){
					try {
						nbt.setTag(field.getName(), mbt.toNBT(field.get(t)));
					} catch (IllegalArgumentException e) {
						Throwables.propagate(e);
					} catch (IllegalAccessException e) {
						Throwables.propagate(e);
					}
				}
			}
			return nbt;
		}

		@Override
		public Object fromNBT(MBT mbt, NBTTagCompound nbt, Class clazz, Class... tsclasses) {
			try{
				Constructor constructor;
				try {
					constructor = clazz.getConstructor();
				} catch (NoSuchMethodException e) {
					constructor = clazz.getDeclaredConstructor();
				}
				constructor.setAccessible(true);
				Object o = constructor.newInstance();
				for(Field field : clazz.getDeclaredFields()){
					field.setAccessible(true);
					if(!Modifier.isStatic(field.getModifiers())){
						if(nbt.hasKey(field.getName())){
							if(field.getGenericType() instanceof ParameterizedType && ((ParameterizedType) field.getGenericType()).getActualTypeArguments() instanceof Class[]){
								field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType(), (Class[]) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()));
							} else {
								field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType()));
							}
						}
					}
				}
				return o;
			} catch(InstantiationException e){
				throw Throwables.propagate(e);
			} catch (IllegalAccessException e) {
				throw Throwables.propagate(e);
			} catch (NoSuchMethodException e) {
				throw Throwables.propagate(e);
			} catch (SecurityException e) {
				throw Throwables.propagate(e);
			} catch (IllegalArgumentException e) {
				throw Throwables.propagate(e);
			} catch (InvocationTargetException e) {
				throw Throwables.propagate(e);
			}
		}

	};

	public static final NBTEncoder[] DEFAULTENCODERS = new NBTEncoder[]{booleanEncoder, byteEncoder, shortEncoder, intEncoder, longEncoder, floatEncoder, doubleEncoder, byteArrayEncoder, intArrayEncoder, stringEncoder, nbtEncoder, itemStackEncoder, enumEncoder, nullEncoder, arrayEncoder, listEncoder, setEncoder, mapEncoder, multimapEncoder, classEncoder};

	public static final NBTEncoder[] DEFAULTSPECIFICENCODERS = new NBTEncoder[]{booleanEncoder, byteEncoder, shortEncoder, intEncoder, longEncoder, floatEncoder, doubleEncoder, byteArrayEncoder, intArrayEncoder, stringEncoder, nbtEncoder, itemStackEncoder, enumEncoder, nullEncoder, arrayEncoder, listEncoder, setEncoder, mapEncoder, multimapEncoder};

	public static final NBTEncoder[] PRIMITIVEENCODERS = new NBTEncoder[]{booleanEncoder, byteEncoder, shortEncoder, intEncoder, longEncoder, floatEncoder, doubleEncoder};

	public static final NBTEncoder[] OBJECTSPECIFICENCODERS = new NBTEncoder[]{stringEncoder, nbtEncoder, itemStackEncoder, enumEncoder, nullEncoder};

	public static final NBTEncoder[] ITERABLEENCODERS = new NBTEncoder[]{byteArrayEncoder, intArrayEncoder, arrayEncoder, listEncoder, setEncoder};

	public static final NBTEncoder[] MAPENCODERS = new NBTEncoder[]{mapEncoder, multimapEncoder};

	public static final NBTEncoder[] DIRECTNBTENCODERS = new NBTEncoder[]{booleanEncoder, byteEncoder, shortEncoder, intEncoder, longEncoder, floatEncoder, doubleEncoder, byteArrayEncoder, intArrayEncoder, stringEncoder};

	private List<NBTEncoder> encoders;

	public MBT() {
		this(DEFAULTENCODERS);
	}

	public MBT(NBTEncoder... encoders) {
		this.encoders = Lists.newArrayList(encoders);
	}

	public <T> NBTBase toNBT(T t){
		for(NBTEncoder encoder : encoders){
			if(encoder.canEncode(t)){
				return encoder.toNBT(this, t);
			}
		}
		return null;
	}

	public <T> T fromNBT(NBTBase nbt, Class<T> clazz, Class... tsclasses){
		for(NBTEncoder encoder : encoders){
			if(encoder.canDecode(nbt, clazz)){
				return (T) encoder.fromNBT(this, nbt, clazz, tsclasses);
			}
		}
		return null;
	}

	public static interface NBTEncoder<T, NT extends NBTBase> {

		public boolean canEncode(Object o);

		public boolean canDecode(NBTBase nbt, Class clazz);

		public NT toNBT(MBT mbt, T t);

		public T fromNBT(MBT mbt, NT nbt, Class<T> clazz, Class... tsclasses);

	}

}
