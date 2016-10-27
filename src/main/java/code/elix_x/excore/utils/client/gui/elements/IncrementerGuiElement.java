package code.elix_x.excore.utils.client.gui.elements;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class IncrementerGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected int numberWidth;
	protected int buttonsWidth;

	protected double step;
	protected double min;
	protected double max;
	protected double value;

	public IncrementerGuiElement(String name, int xPos, int yPos, int numberWidth, int buttonsWidth, int height, int borderX, int borderY, double step, double min, double max, double defaultValue){
		super(name, xPos, yPos, numberWidth + buttonsWidth, height, borderX, borderY);
		this.numberWidth = numberWidth;
		this.buttonsWidth = buttonsWidth;

		this.step = step;
		this.min = min;
		this.max = max;
		this.value = defaultValue;
	}

	public double getValue(){
		return value;
	}

	public void setValue(double value){
		this.value = Math.max(Math.min(value, max), min);
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
		drawStringFull(gui.mc.fontRendererObj, String.valueOf(getValue()), xPos + borderX, yPos + borderY + height - 8, new RGBA(1f, 1f, 1f, 1f));
		new GuiButtonExt(0, xPos + borderX + numberWidth, yPos + borderY, buttonsWidth, height / 2, "▲").drawButton(gui.mc, mouseX, mouseY);
		new GuiButtonExt(0, xPos + borderX + numberWidth, yPos + borderY + height / 2, buttonsWidth, height / 2, "▼").drawButton(gui.mc, mouseX, mouseY);
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
		if(down && key == 0 && inside(mouseX, mouseY)){
			if(mouseX >= xPos + borderX + numberWidth){
				if(mouseY < yPos + borderY + height / 2){
					setValue(getValue() + step);
				} else{
					setValue(getValue() - step);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		return false;
	}

}
