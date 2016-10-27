package code.elix_x.excore.utils.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;

public class BasicGuiScreen extends GuiScreen {

	public final GuiScreen parent;

	protected int guiWidth;
	protected int guiHeight;

	protected int xPos;
	protected int yPos;

	public BasicGuiScreen(GuiScreen parent, int guiWidth, int guiHeight){
		this.parent = parent;
		this.guiWidth = guiWidth;
		this.guiHeight = guiHeight;
	}

	@Override
	public void initGui(){
		xPos = (width - guiWidth) / 2;
		yPos = (height - guiHeight) / 2;
	}

	@Override
	protected void keyTyped(char c, int key) throws IOException{
		if(key == 1){
			onClose();
			this.mc.displayGuiScreen(parent);
			return;
		}
	}

	protected void onClose(){

	}

}
