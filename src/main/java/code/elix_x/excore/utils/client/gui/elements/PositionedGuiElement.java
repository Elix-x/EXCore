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

import net.minecraft.client.gui.GuiScreen;

public abstract class PositionedGuiElement<E extends PositionedGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPosition<E, H, P>> extends GuiElement<H> {

	protected P position;

	public PositionedGuiElement(String name, P position) {
		super(name);
		this.position = position;
	}

	@Override
	public void initGui(H handler, GuiScreen gui){
		position.initGui((E) this, gui);
	}
	
	public int getXPos(){
		return position.getX();
	}

	public int getYPos(){
		return position.getY();
	}

}
