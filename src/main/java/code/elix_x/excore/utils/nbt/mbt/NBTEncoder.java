package code.elix_x.excore.utils.nbt.mbt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Throwables;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import code.elix_x.excore.utils.nbt.mbt.encoders.NBTClassEncoder;
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

public interface NBTEncoder<T, NT extends NBTBase> {

	NBTEncoder<Boolean, NBTTagByte> booleanEncoder = new NBTEncoder<Boolean, NBTTagByte>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Boolean;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagByte && (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz));
		}

		@Override
		public NBTTagByte toNBT(MBT mbt, Boolean b){
			return new NBTTagByte((byte) (b ? 1 : 0));
		}

		@Override
		public Boolean fromNBT(MBT mbt, NBTTagByte nbt, Class<Boolean> clazz, Class... tsclasses){
			return nbt.getByte() == 1;
		}

	};

	NBTEncoder<Byte, NBTTagByte> byteEncoder = new NBTEncoder<Byte, NBTTagByte>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Byte;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagByte;
		}

		@Override
		public NBTTagByte toNBT(MBT mbt, Byte b){
			return new NBTTagByte(b);
		}

		@Override
		public Byte fromNBT(MBT mbt, NBTTagByte nbt, Class<Byte> clazz, Class... tsclasses){
			return nbt.getByte();
		}

	};

	NBTEncoder<Short, NBTTagShort> shortEncoder = new NBTEncoder<Short, NBTTagShort>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Short;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagShort;
		}

		@Override
		public NBTTagShort toNBT(MBT mbt, Short s){
			return new NBTTagShort(s);
		}

		@Override
		public Short fromNBT(MBT mbt, NBTTagShort nbt, Class<Short> clazz, Class... tsclasses){
			return nbt.getShort();
		}

	};

	NBTEncoder<Integer, NBTTagInt> intEncoder = new NBTEncoder<Integer, NBTTagInt>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Integer;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagInt;
		}

		@Override
		public NBTTagInt toNBT(MBT mbt, Integer i){
			return new NBTTagInt(i);
		}

		@Override
		public Integer fromNBT(MBT mbt, NBTTagInt nbt, Class<Integer> clazz, Class... tsclasses){
			return nbt.getInt();
		}

	};

	NBTEncoder<Long, NBTTagLong> longEncoder = new NBTEncoder<Long, NBTTagLong>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Long;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagLong;
		}

		@Override
		public NBTTagLong toNBT(MBT mbt, Long l){
			return new NBTTagLong(l);
		}

		@Override
		public Long fromNBT(MBT mbt, NBTTagLong nbt, Class<Long> clazz, Class... tsclasses){
			return nbt.getLong();
		}

	};

	NBTEncoder<Float, NBTTagFloat> floatEncoder = new NBTEncoder<Float, NBTTagFloat>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Float;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagFloat;
		}

		@Override
		public NBTTagFloat toNBT(MBT mbt, Float f){
			return new NBTTagFloat(f);
		}

		@Override
		public Float fromNBT(MBT mbt, NBTTagFloat nbt, Class<Float> clazz, Class... tsclasses){
			return nbt.getFloat();
		}

	};

	NBTEncoder<Double, NBTTagDouble> doubleEncoder = new NBTEncoder<Double, NBTTagDouble>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Double;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagDouble;
		}

		@Override
		public NBTTagDouble toNBT(MBT mbt, Double d){
			return new NBTTagDouble(d);
		}

		@Override
		public Double fromNBT(MBT mbt, NBTTagDouble nbt, Class<Double> clazz, Class... tsclasses){
			return nbt.getDouble();
		}

	};

	NBTEncoder<byte[], NBTTagByteArray> byteArrayEncoder = new NBTEncoder<byte[], NBTTagByteArray>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof byte[];
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagByteArray;
		}

		@Override
		public NBTTagByteArray toNBT(MBT mbt, byte[] bs){
			return new NBTTagByteArray(bs);
		}

		@Override
		public byte[] fromNBT(MBT mbt, NBTTagByteArray nbt, Class<byte[]> clazz, Class... tsclasses){
			return nbt.getByteArray();
		}

	};

	NBTEncoder<int[], NBTTagIntArray> intArrayEncoder = new NBTEncoder<int[], NBTTagIntArray>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof int[];
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagIntArray;
		}

		@Override
		public NBTTagIntArray toNBT(MBT mbt, int[] is){
			return new NBTTagIntArray(is);
		}

		@Override
		public int[] fromNBT(MBT mbt, NBTTagIntArray nbt, Class<int[]> clazz, Class... tsclasses){
			return nbt.getIntArray();
		}

	};

	NBTEncoder<String, NBTTagString> stringEncoder = new NBTEncoder<String, NBTTagString>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof String;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagString && String.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagString toNBT(MBT mbt, String s){
			return new NBTTagString(s);
		}

		@Override
		public String fromNBT(MBT mbt, NBTTagString nbt, Class<String> clazz, Class... tsclasses){
			return nbt.getString();
		}

	};

	NBTEncoder<NBTBase, NBTBase> nbtEncoder = new NBTEncoder<NBTBase, NBTBase>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof NBTBase;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return NBTBase.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTBase toNBT(MBT mbt, NBTBase nbt){
			return nbt;
		}

		@Override
		public NBTBase fromNBT(MBT mbt, NBTBase nbt, Class<NBTBase> clazz, Class... tsclasses){
			return nbt;
		}

	};

	NBTEncoder<ItemStack, NBTTagCompound> itemStackEncoder = new NBTEncoder<ItemStack, NBTTagCompound>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof ItemStack;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return ItemStack.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagCompound toNBT(MBT mbt, ItemStack itemstack){
			return itemstack.writeToNBT(new NBTTagCompound());
		}

		@Override
		public ItemStack fromNBT(MBT mbt, NBTTagCompound nbt, Class<ItemStack> clazz, Class... tsclasses){
			return ItemStack.loadItemStackFromNBT(nbt);
		}

	};

	NBTEncoder<UUID, NBTTagString> uuidEncoder = new NBTEncoder<UUID, NBTTagString>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof UUID;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagString && UUID.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagString toNBT(MBT mbt, UUID uuid){
			return new NBTTagString(uuid.toString());
		}

		@Override
		public UUID fromNBT(MBT mbt, NBTTagString nbt, Class<UUID> clazz, Class... tsclasses){
			return UUID.fromString(nbt.getString());
		}

	};

	NBTEncoder<Enum, NBTTagString> enumEncoder = new NBTEncoder<Enum, NBTTagString>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Enum;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagString && clazz.isEnum();
		}

		@Override
		public NBTTagString toNBT(MBT mbt, Enum e){
			return new NBTTagString(e.name());
		}

		@Override
		public Enum fromNBT(MBT mbt, NBTTagString nbt, Class<Enum> clazz, Class... tsclasses){
			return Enum.valueOf(clazz, nbt.getString());
		}

	};

	NBTEncoder<Object, NBTTagCompound> nullEncoder = new NBTEncoder<Object, NBTTagCompound>(){

		public static final String NULL = "#NULL";

		@Override
		public boolean canEncode(Object o){
			return o == null;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagCompound && ((NBTTagCompound) nbt).hasKey(NULL) && ((NBTTagCompound) nbt).getString(NULL).equals(NULL);
		}

		@Override
		public NBTTagCompound toNBT(MBT mbt, Object t){
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString(NULL, NULL);
			return nbt;
		}

		@Override
		public Object fromNBT(MBT mbt, NBTTagCompound nbt, Class<Object> clazz, Class... tsclasses){
			return null;
		}

	};

	NBTEncoder<Object[], NBTTagList> arrayEncoder = new NBTEncoder<Object[], NBTTagList>(){

		@Override
		public boolean canEncode(Object o){
			return o.getClass().isArray();
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagList && clazz.isArray();
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Object[] os){
			NBTTagList nlist = new NBTTagList();
			for(Object o : os){
				nlist.appendTag(mbt.toNBT(o));
			}
			return nlist;
		}

		@Override
		public Object[] fromNBT(MBT mbt, NBTTagList list, Class<Object[]> clazz, Class... tsclasses){
			Object[] os = (Object[]) Array.newInstance(clazz.getComponentType(), 0);
			for(int i = 0; i < list.tagCount(); i++){
				os = ArrayUtils.add(os, mbt.fromNBT(list.get(i), clazz.getComponentType()));
			}
			return os;
		}

	};

	NBTEncoder<List<?>, NBTTagList> listEncoder = new NBTEncoder<List<?>, NBTTagList>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof List;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagList && List.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, List<?> list){
			NBTTagList nlist = new NBTTagList();
			for(Object o : list){
				nlist.appendTag(mbt.toNBT(o));
			}
			return nlist;
		}

		@Override
		public List<Object> fromNBT(MBT mbt, NBTTagList nlist, Class<List<?>> clazz, Class... tsclasses){
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
			} catch(IllegalAccessException e){
				throw Throwables.propagate(e);
			}
		}

	};

	NBTEncoder<Set<?>, NBTTagList> setEncoder = new NBTEncoder<Set<?>, NBTTagList>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Set;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagList && Set.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Set<?> set){
			NBTTagList list = new NBTTagList();
			for(Object o : set){
				list.appendTag(mbt.toNBT(o));
			}
			return list;
		}

		@Override
		public Set<Object> fromNBT(MBT mbt, NBTTagList list, Class<Set<?>> clazz, Class... tsclasses){
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
			} catch(IllegalAccessException e){
				throw Throwables.propagate(e);
			}
		}

	};

	NBTEncoder<Map<?, ?>, NBTTagList> mapEncoder = new NBTEncoder<Map<?, ?>, NBTTagList>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Map;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagList && Map.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Map<?, ?> map){
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
		public Map<?, ?> fromNBT(MBT mbt, NBTTagList list, Class<Map<?, ?>> clazz, Class... tsclasses){
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
			} catch(IllegalAccessException e){
				throw Throwables.propagate(e);
			}
		}

	};

	NBTEncoder<Multimap<?, ?>, NBTTagList> multimapEncoder = new NBTEncoder<Multimap<?, ?>, NBTTagList>(){

		@Override
		public boolean canEncode(Object o){
			return o instanceof Multimap;
		}

		@Override
		public boolean canDecode(NBTBase nbt, Class clazz){
			return nbt instanceof NBTTagList && Multimap.class.isAssignableFrom(clazz);
		}

		@Override
		public NBTTagList toNBT(MBT mbt, Multimap<?, ?> map){
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
		public Multimap<?, ?> fromNBT(MBT mbt, NBTTagList list, Class<Multimap<?, ?>> clazz, Class... tsclasses){
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
			} catch(IllegalAccessException e){
				throw Throwables.propagate(e);
			}
		}

	};

	NBTEncoder<? extends Object, NBTTagCompound> classEncoder = new NBTClassEncoder(false, false, false, false);

	@Deprecated
	NBTEncoder<? extends Object, NBTTagCompound> classEncoderSt = new NBTClassEncoder(false, false, true, false);

	@Deprecated
	NBTEncoder<? extends Object, NBTTagCompound> classEncoderSu = new NBTClassEncoder(false, false, false, true);

	@Deprecated
	NBTEncoder<? extends Object, NBTTagCompound> classEncoderStSu = new NBTClassEncoder(false, false, true, true);

	public boolean canEncode(Object o);

	public boolean canDecode(NBTBase nbt, Class clazz);

	public NT toNBT(MBT mbt, T t);

	public T fromNBT(MBT mbt, NT nbt, Class<T> clazz, Class... tsclasses);

}