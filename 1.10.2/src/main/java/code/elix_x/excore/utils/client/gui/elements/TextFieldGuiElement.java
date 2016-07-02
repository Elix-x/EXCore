package code.elix_x.excore.utils.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class TextFieldGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected GuiTextField textField;

	public TextFieldGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, FontRenderer fontRenderer, String defaultText){
		super(name, xPos, yPos, width, height, borderX, borderY);
		this.textField = new GuiTextField(0, fontRenderer, xPos + borderX, yPos + borderY, width, height);
		this.textField.setText(defaultText);
	}

	public String getCurrentText(){
		return textField.getText();
	}

	public void setCurrentText(String text){
		textField.setText(text);
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
		textField.drawTextBox();
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c){
		return down && textField.textboxKeyTyped(c, key);
	}

	@Override
	public boolean handleMouseEvent(IGuiElementsHandler handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		if(down){
			if(inside(mouseX, mouseY) || textField.isFocused()){
				textField.mouseClicked(mouseX, mouseY, key);
				if(textField.isFocused()){
					handler.setFocused(this);
				} else{
					handler.looseFocus();
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
