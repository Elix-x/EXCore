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

import org.lwjgl.util.Rectangle;

import code.elix_x.excomms.color.RGBA;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LogarithmicSliderGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

	protected RGBA textColor = new RGBA(1f, 1f, 1f, 1f);

	protected int sliderWidth;

	protected double min;
	protected double max;
	protected double value;

	protected boolean displayValue;

	protected boolean dragged;

	public LogarithmicSliderGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, int sliderWidth, double min, double max, double defaultValue, boolean displayValue){
		super(name, xPos, yPos, width, height, borderX, borderY);
		this.sliderWidth = sliderWidth;
		this.min = min;
		this.max = max;
		double minv = Math.log(min);
		double maxv = Math.log(max);
		double scale = maxv - minv;
		this.value = (Math.log(defaultValue) - minv) / scale;
		this.displayValue = displayValue;
	}

	public double getValue(){
		double minv = Math.log(min);
		double maxv = Math.log(max);
		double scale = maxv - minv;
		return Math.exp(minv + scale * value);
	}

	protected void checkSliderValue(){
		value = Math.min(Math.max(value, 0), 1);
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
		if(dragged){
			value = (mouseX - (xPos + borderX + sliderWidth / 2)) / (double) (width - sliderWidth);
			checkSliderValue();
		}
		gui.mc.getTextureManager().bindTexture(buttonTextures);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedRect(new Rectangle(xPos + borderX + (int) (this.value * (float) (this.width - sliderWidth)), yPos + borderY, sliderWidth / 2, height), new Rectangle(0, 66, 4, 20), 256, 256);
		drawTexturedRect(new Rectangle(xPos + borderX + (int) (this.value * (float) (this.width - sliderWidth)) + sliderWidth / 2, yPos + borderY, sliderWidth / 2, height), new Rectangle(196, 66, 4, 20), 256, 256);
		if(displayValue){
			int w = gui.mc.fontRenderer.getStringWidth(String.valueOf(getValue()));
			Rectangle r = smartPos(toBoundsRectangle(), new Rectangle(xPos + borderX + (int) (value * (float) (this.width - sliderWidth)), yPos + borderY, sliderWidth, height), new Rectangle(xPos + borderX + (width - w) / 2, yPos + borderY + (height - 8) / 2, w, 8), false, false);
			drawStringFull(gui.mc.fontRenderer, String.valueOf(getValue()), r.getX(), yPos + borderY + (height - 8) / 2, textColor);
		}
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
		if(key == 0){
			if(down){
				if(inside(mouseX, mouseY)){
					if(xPos + borderX + value * width - sliderWidth / 2 <= mouseX && mouseX <= xPos + borderX + value * width + sliderWidth / 2){
						dragged = true;
					} else{
						value = (mouseX - (xPos + borderX + sliderWidth / 2)) / (double) (width - sliderWidth);
						checkSliderValue();
					}
				}
			} else{
				if(dragged){
					dragged = false;
					value = (mouseX - (xPos + borderX + sliderWidth / 2)) / (double) (width - sliderWidth);
					checkSliderValue();
				}
			}
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		return false;
	}

}
