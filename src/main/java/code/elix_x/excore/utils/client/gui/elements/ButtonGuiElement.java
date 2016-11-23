package code.elix_x.excore.utils.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class ButtonGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected GuiButtonExt button;

	public ButtonGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, String buttonText){
		super(name, xPos, yPos, width, height, borderX, borderY);
		this.button = new GuiButtonExt(0, xPos + borderX, yPos + borderY, width, height, buttonText);
	}

	public void resetButton(){
		this.button = new GuiButtonExt(0, xPos + borderX, yPos + borderY, width, height, button.displayString);
	}

	@Override
	public void openGui(H handler, GuiScreen gui){

	}

	@Override
	public void initGui(H handler, GuiScreen gui){

	}

	@Override
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		button.drawButton(gui.mc, mouseX, mouseY);
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c){
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		if(down && key == 0 && button.mousePressed(gui.mc, mouseX, mouseY)){
			onButtonPressed();
			return true;
		}
		return false;
	}

	public void onButtonPressed(){
		button.playPressSound(Minecraft.getMinecraft().getSoundHandler());
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		return false;
	}

}
