package code.elix_x.excore.utils.nbt.mbt;

import java.util.ArrayList;
import java.util.List;

import code.elix_x.excore.utils.nbt.mbt.encoders.NBTClassEncoder;

public class MBTBuilder {

	private List<NBTEncoder> encoders = new ArrayList<NBTEncoder>();

	public MBTBuilder(){

	}

	public MBTBuilder add(NBTEncoder encoder){
		encoders.add(encoder);
		return this;
	}

	public MBTBuilder addDefaultEncoders(){
		for(NBTEncoder encoder : MBT.DEFAULTSPECIFICENCODERS){
			add(encoder);
		}
		return this;
	}

	@Deprecated
	public MBTBuilder addClassEncoder(boolean staticc, boolean superr){
		return add(new NBTClassEncoder(false, false, staticc, superr));
	}

	public MBT build(){
		return new MBT(encoders);
	}

}
