package code.elix_x.excore.utils.nbt.mbt;

import java.util.ArrayList;
import java.util.List;

public class MBTBuilder {
	
	private List<NBTEncoder> encoders = new ArrayList<NBTEncoder>();
	
	public MBTBuilder() {
		
	}
	
	public void add(NBTEncoder encoder){
		encoders.add(encoder);
	}
	
	public void addDefaultEncoders(){
		for(NBTEncoder encoder : MBT.DEFAULTSPECIFICENCODERS){
			add(encoder);
		}
	}
	
	public void addClassEncoder(boolean staticc, boolean superr){
		int i = 0;
		if(staticc) i += 1;
		if(superr) i += 2;
		add(MBT.CLASSENCODERS[i]);
	}
	
	public MBT build(){
		return new MBT(encoders);
	}
	
}
