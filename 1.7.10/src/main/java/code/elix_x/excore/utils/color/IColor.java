package code.elix_x.excore.utils.color;

import net.minecraft.nbt.NBTTagCompound;

public interface IColor {

	public RGBA toRGBA();

	public HSBA toHSBA();

	public NBTTagCompound writeToNBT(NBTTagCompound nbt);

	public void readFromNBT(NBTTagCompound nbt);

}
