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
import code.elix_x.excore.utils.client.gui.GuiHelper;

public abstract class RectangularGuiElement<E extends RectangularGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPositionAndSize<E, H, P>> extends PositionedGuiElement<E, H, P> {

	protected int width;
	protected int height;

	protected int borderX;
	protected int borderY;

	public RectangularGuiElement(String name, P position){
		super(name, position);
	}

	public int getWidth(){
		return position.getWidth();
	}

	public int getHeight(){
		return position.getHeight();
	}

	public int getRight(){
		return getXPos() + getWidth();
	}

	public int getBottom(){
		return getYPos() + getHeight();
	}

	public boolean inside(int x, int y){
		return toInnerRectangle().contains(x, y);
	}

	public Rectangle toRectangle(){
		return position.toBoundsRectangle();
	}

	public Rectangle toInnerRectangle(){
		return position.toInnerRectangle();
	}

	public void fill(RGBA color){
		GuiHelper.drawColoredRect(toInnerRectangle(), color);
	}

	public void fillWithinBouds(RGBA color){
		GuiHelper.drawColoredRect(toRectangle(), color);
	}

}
