/*******************************************************************************
 * Copyright 2016 Elix_x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){
		textField.drawTextBox();
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

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
