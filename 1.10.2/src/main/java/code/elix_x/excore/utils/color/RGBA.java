package code.elix_x.excore.utils.color;

import net.minecraft.nbt.NBTTagCompound;

public class RGBA implements IColor {

	public int r;
	public int g;
	public int b;
	public int a;

	private RGBA(){

	}

	public RGBA(int r, int g, int b, int a){
		this.r = Math.max(Math.min(r, 255), 0);
		this.g = Math.max(Math.min(g, 255), 0);
		this.b = Math.max(Math.min(b, 255), 0);
		this.a = Math.max(Math.min(a, 255), 0);
	}

	public RGBA(int r, int g, int b){
		this(r, g, b, 255);
	}

	public RGBA(float r, float g, float b){
		this((int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
	}

	public RGBA(float r, float g, float b, float a){
		this((int) (r * 255f), (int) (g * 255f), (int) (b * 255f), (int) (a * 255f));
	}

	public RGBA(int argb){
		this(argb >> 16 & 255, argb >> 8 & 255, argb & 255, argb >> 24 & 255);
	}

	public int getRI(){
		return r;
	}

	public int getGI(){
		return g;
	}

	public int getBI(){
		return b;
	}

	public int getAI(){
		return a;
	}

	public void setRI(int r){
		this.r = r;
	}

	public void setGI(int g){
		this.g = g;
	}

	public void setBI(int b){
		this.b = b;
	}

	public void setAI(int a){
		this.a = a;
	}

	public float getRF(){
		return r / 255f;
	}

	public float getGF(){
		return g / 255f;
	}

	public float getBF(){
		return b / 255f;
	}

	public float getAF(){
		return a / 255f;
	}

	public void setRF(float r){
		this.r = (int) (r * 255f);
	}

	public void setGF(float g){
		this.g = (int) (g * 255f);
	}

	public void setBF(float b){
		this.b = (int) (b * 255f);
	}

	public void setAF(float a){
		this.a = (int) (a * 255f);
	}

	public RGBA add(RGBA rgba){
		return new RGBA(getRF() + rgba.getRF(), getGF() + rgba.getGF(), getBF() + rgba.getBF(), getAF() + rgba.getAF());
	}

	public RGBA substract(RGBA rgba){
		return new RGBA(getRF() - rgba.getRF(), getGF() - rgba.getGF(), getBF() - rgba.getBF(), getAF() - rgba.getAF());
	}

	public RGBA multiply(RGBA rgba){
		return new RGBA(getRF() * rgba.getRF(), getGF() * rgba.getGF(), getBF() * rgba.getBF(), getAF() * rgba.getAF());
	}

	public RGBA divide(RGBA rgba){
		return new RGBA(getRF() / rgba.getRF(), getGF() / rgba.getGF(), getBF() / rgba.getBF(), getAF() / rgba.getAF());
	}

	public int argb(){
		return a << 24 | r << 16 | g << 8 | b;
	}

	public int rgba(){
		return r << 24 | g << 16 | b << 8 | a;
	}

	@Override
	public RGBA toRGBA(){
		return this;
	}

	@Override
	public HSBA toHSBA(){
		int l = r <= g ? g : r;
		if(b > l) l = b;
		int i1 = r >= g ? g : r;
		if(b < i1) i1 = b;
		float brightness = l / 255F;
		float saturation;
		if(l != 0) saturation = (float) (l - i1) / (float) l;
		else saturation = 0.0F;
		float hue;
		if(saturation == 0.0F){
			hue = 0.0F;
		} else{
			float f3 = (float) (l - r) / (float) (l - i1);
			float f4 = (float) (l - g) / (float) (l - i1);
			float f5 = (float) (l - b) / (float) (l - i1);
			if(r == l) hue = f5 - f4;
			else if(g == l) hue = (2.0F + f3) - f5;
			else hue = (4F + f4) - f3;
			hue /= 6F;
			if(hue < 0.0F) hue++;
		}
		return new HSBA(hue, saturation, brightness, a * 255);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("r", r);
		nbt.setInteger("g", g);
		nbt.setInteger("b", b);
		nbt.setInteger("a", a);
		return nbt;
	}

	public void readFromNBT(NBTTagCompound nbt){
		r = nbt.getInteger("r");
		g = nbt.getInteger("g");
		b = nbt.getInteger("b");
		a = nbt.getInteger("a");
	}

	public static RGBA createFromNBT(NBTTagCompound nbt){
		RGBA rgba = new RGBA();
		rgba.readFromNBT(nbt);
		return rgba;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + a;
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		RGBA other = (RGBA) obj;
		if(b != other.b) return false;
		if(g != other.g) return false;
		if(a != other.a) return false;
		if(r != other.r) return false;
		return true;
	}

	@Override
	public String toString(){
		return "RGBA{ " + r + ", " + g + ", " + b + ", " + a + "}";
	}

}