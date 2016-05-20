package code.elix_x.excore.utils.nbt.mbt;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTBase;

public class MBT {

	public static final NBTEncoder[] DEFAULTENCODERS = new NBTEncoder[]{NBTEncoder.booleanEncoder, NBTEncoder.byteEncoder, NBTEncoder.shortEncoder, NBTEncoder.intEncoder, NBTEncoder.longEncoder, NBTEncoder.floatEncoder, NBTEncoder.doubleEncoder, NBTEncoder.byteArrayEncoder, NBTEncoder.intArrayEncoder, NBTEncoder.stringEncoder, NBTEncoder.nbtEncoder, NBTEncoder.itemStackEncoder, NBTEncoder.uuidEncoder, NBTEncoder.enumEncoder, NBTEncoder.nullEncoder, NBTEncoder.arrayEncoder, NBTEncoder.listEncoder, NBTEncoder.setEncoder, NBTEncoder.mapEncoder, NBTEncoder.multimapEncoder, NBTEncoder.classEncoder};

	public static final NBTEncoder[] DEFAULTSPECIFICENCODERS = new NBTEncoder[]{NBTEncoder.booleanEncoder, NBTEncoder.byteEncoder, NBTEncoder.shortEncoder, NBTEncoder.intEncoder, NBTEncoder.longEncoder, NBTEncoder.floatEncoder, NBTEncoder.doubleEncoder, NBTEncoder.byteArrayEncoder, NBTEncoder.intArrayEncoder, NBTEncoder.stringEncoder, NBTEncoder.nbtEncoder, NBTEncoder.itemStackEncoder, NBTEncoder.uuidEncoder, NBTEncoder.enumEncoder, NBTEncoder.nullEncoder, NBTEncoder.arrayEncoder, NBTEncoder.listEncoder, NBTEncoder.setEncoder, NBTEncoder.mapEncoder, NBTEncoder.multimapEncoder};

	public static final NBTEncoder[] PRIMITIVEENCODERS = new NBTEncoder[]{NBTEncoder.booleanEncoder, NBTEncoder.byteEncoder, NBTEncoder.shortEncoder, NBTEncoder.intEncoder, NBTEncoder.longEncoder, NBTEncoder.floatEncoder, NBTEncoder.doubleEncoder};

	public static final NBTEncoder[] OBJECTSPECIFICENCODERS = new NBTEncoder[]{NBTEncoder.stringEncoder, NBTEncoder.nbtEncoder, NBTEncoder.itemStackEncoder, NBTEncoder.uuidEncoder, NBTEncoder.enumEncoder, NBTEncoder.nullEncoder};

	public static final NBTEncoder[] ITERABLEENCODERS = new NBTEncoder[]{NBTEncoder.byteArrayEncoder, NBTEncoder.intArrayEncoder, NBTEncoder.arrayEncoder, NBTEncoder.listEncoder, NBTEncoder.setEncoder};

	public static final NBTEncoder[] MAPENCODERS = new NBTEncoder[]{NBTEncoder.mapEncoder, NBTEncoder.multimapEncoder};

	public static final NBTEncoder[] DIRECTNBTENCODERS = new NBTEncoder[]{NBTEncoder.booleanEncoder, NBTEncoder.byteEncoder, NBTEncoder.shortEncoder, NBTEncoder.intEncoder, NBTEncoder.longEncoder, NBTEncoder.floatEncoder, NBTEncoder.doubleEncoder, NBTEncoder.byteArrayEncoder, NBTEncoder.intArrayEncoder, NBTEncoder.stringEncoder};

	private List<NBTEncoder> encoders;

	public MBT(){
		this(DEFAULTENCODERS);
	}

	public MBT(NBTEncoder... encoders){
		this.encoders = Lists.newArrayList(encoders);
	}

	public MBT(List<NBTEncoder> encoders){
		this.encoders = encoders;
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

}
