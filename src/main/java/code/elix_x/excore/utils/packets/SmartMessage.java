package code.elix_x.excore.utils.packets;

import code.elix_x.excore.utils.nbt.mbt.MBT;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class SmartMessage<T> implements IMessage {

	public static final MBT mbt = new MBT();

	public T t;

	public SmartMessage(T t){
		this.t = t;
	}

	public MBT getMBT(){
		return mbt;
	}

	public abstract Class<? extends T> getTClass();

	@Override
	public void toBytes(ByteBuf buf){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("data", getMBT().toNBT(t));
		ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf){
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		t = getMBT().fromNBT(nbt.getTag("data"), getTClass());
	}

}
