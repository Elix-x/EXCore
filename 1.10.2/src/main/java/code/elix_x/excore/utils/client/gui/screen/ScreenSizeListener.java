package code.elix_x.excore.utils.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenSizeListener {

	public int screenWidth;
	public int screenHeight;

	public int prevScreenWidth;
	public int prevScreenHeight;

	public ScreenSizeListener(){
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		screenWidth = prevScreenWidth = res.getScaledWidth();
		screenHeight = prevScreenHeight = res.getScaledHeight();
	}

	public boolean update(){
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int neww = res.getScaledWidth();
		int newh = res.getScaledHeight();
		if(neww != screenWidth || newh != screenHeight){
			prevScreenWidth = screenWidth;
			prevScreenHeight = screenHeight;
			screenWidth = neww;
			screenHeight = newh;
			return true;
		} else{
			return false;
		}
	}

}
