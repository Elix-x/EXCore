package code.elix_x.excore.utils.color;

import net.minecraft.nbt.NBTTagCompound;

public class HSBA implements IColor {

	public float hue;
	public float saturation;
	public float brightness;
	public float alpha;

	private HSBA(){

	}

	public HSBA(float hue, float saturation, float brightness, float alpha){
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		this.alpha = alpha;
	}

	public HSBA(float hue, float saturation, float brightness){
		this(hue, saturation, brightness, 1);
	}

	@Override
	public RGBA toRGBA(){
		int red = 0;
		int green = 0;
		int blue = 0;
		if(saturation == 0.0F){
			red = green = blue = (byte) (brightness * 255F + 0.5F);
		} else{
			float f3 = (hue - (float) Math.floor(hue)) * 6F;
			float f4 = f3 - (float) Math.floor(f3);
			float f5 = brightness * (1.0F - saturation);
			float f6 = brightness * (1.0F - saturation * f4);
			float f7 = brightness * (1.0F - saturation * (1.0F - f4));
			switch((int) f3){
				case 0:
					red = (byte) (brightness * 255F + 0.5F);
					green = (byte) (f7 * 255F + 0.5F);
					blue = (byte) (f5 * 255F + 0.5F);
					break;
				case 1:
					red = (byte) (f6 * 255F + 0.5F);
					green = (byte) (brightness * 255F + 0.5F);
					blue = (byte) (f5 * 255F + 0.5F);
					break;
				case 2:
					red = (byte) (f5 * 255F + 0.5F);
					green = (byte) (brightness * 255F + 0.5F);
					blue = (byte) (f7 * 255F + 0.5F);
					break;
				case 3:
					red = (byte) (f5 * 255F + 0.5F);
					green = (byte) (f6 * 255F + 0.5F);
					blue = (byte) (brightness * 255F + 0.5F);
					break;
				case 4:
					red = (byte) (f7 * 255F + 0.5F);
					green = (byte) (f5 * 255F + 0.5F);
					blue = (byte) (brightness * 255F + 0.5F);
					break;
				case 5:
					red = (byte) (brightness * 255F + 0.5F);
					green = (byte) (f5 * 255F + 0.5F);
					blue = (byte) (f6 * 255F + 0.5F);
					break;
			}
		}
		return new RGBA(red, green, blue, (int) (alpha * 255f));
	}

	@Override
	public HSBA toHSBA(){
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt.setFloat("h", hue);
		nbt.setFloat("s", saturation);
		nbt.setFloat("b", brightness);
		nbt.setFloat("a", alpha);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		hue = nbt.getFloat("h");
		saturation = nbt.getFloat("s");
		brightness = nbt.getFloat("b");
		alpha = nbt.getFloat("a");
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(alpha);
		result = prime * result + Float.floatToIntBits(brightness);
		result = prime * result + Float.floatToIntBits(hue);
		result = prime * result + Float.floatToIntBits(saturation);
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		HSBA other = (HSBA) obj;
		if(Float.floatToIntBits(alpha) != Float.floatToIntBits(other.alpha)) return false;
		if(Float.floatToIntBits(brightness) != Float.floatToIntBits(other.brightness)) return false;
		if(Float.floatToIntBits(hue) != Float.floatToIntBits(other.hue)) return false;
		if(Float.floatToIntBits(saturation) != Float.floatToIntBits(other.saturation)) return false;
		return true;
	}

	@Override
	public String toString(){
		return "HSBA [hue=" + hue + ", saturation=" + saturation + ", brightness=" + brightness + ", alpha=" + alpha + "]";
	}

}
