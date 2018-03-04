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
package code.elix_x.excore.utils.client.gui;

import code.elix_x.excomms.color.RGBA;
import code.elix_x.excore.utils.client.gui.elements.ColoredRectangleGuiElement;
import code.elix_x.excore.utils.client.gui.elements.IntegralSliderGuiElement;
import net.minecraft.client.gui.GuiScreen;

public class ColorSelectorGuiScreen extends ElementalGuiScreen {

	protected RGBA color;

	public ColorSelectorGuiScreen(GuiScreen parent, RGBA color){
		super(parent, 256, 104);
		this.color = color;
	}

	@Override
	protected void addElements(){
		add(new ColoredRectangleGuiElement("Color Display", xPos + (guiWidth - 20) / 2, yPos, 20, 20, 0, 2, color));

		nextY += 2 + 20 + 2;

		add(new ColoredRectangleGuiElement("Red Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255)));
		add(new IntegralSliderGuiElement("Red Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.getRI(), true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color = color.setRI(getValue());
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Green Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255)));
		add(new IntegralSliderGuiElement("Green Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.getGI(), true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color = color.setGI(getValue());
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Blue Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255)));
		add(new IntegralSliderGuiElement("Blue Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.getBI(), true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color = color.setBI(getValue());
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Alpha Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255)));
		add(new IntegralSliderGuiElement("Alpha Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.getAI(), true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color = color.setAI(getValue());
			}

		});

		nextY += 2 + 16 + 2;
	}

}
