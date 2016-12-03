package code.elix_x.excore.utils.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class CheckBoxGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected boolean checked;

	public CheckBoxGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, boolean defaultValue){
		super(name, xPos, yPos, width, height, borderX, borderY);
		this.checked = defaultValue;
	}

	public boolean isChecked(){
		return checked;
	}

	public void setChecked(boolean checked){
		this.checked = checked;
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
		new GuiButtonExt(0, xPos + borderX, yPos + borderY, width, height, isChecked() ? "x" : "").drawButton(gui.mc, mouseX, mouseY);
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
		if(key == 0 && down && inside(mouseX, mouseY)){
			setChecked(!isChecked());
			return true;
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		return false;
	}

}
