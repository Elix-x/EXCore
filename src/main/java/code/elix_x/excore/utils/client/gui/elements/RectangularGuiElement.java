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



public abstract class RectangularGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends PositionedGuiElement<H> {

	protected int width;
	protected int height;

	protected int borderX;
	protected int borderY;

	public RectangularGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY){
		super(name, xPos, yPos);
		this.width = width;
		this.height = height;
		this.borderX = borderX;
		this.borderY = borderY;
	}

	public int getWidth(){
		return borderX + width + borderX;
	}

	public int getHeight(){
		return borderY + height + borderY;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getBorderX(){
		return borderX;
	}

	public void setBorderX(int borderX){
		this.borderX = borderX;
	}

	public int getBorderY(){
		return borderY;
	}

	public void setBorderY(int borderY){
		this.borderY = borderY;
	}

	public int getRight(){
		return getXPos() + getWidth();
	}

	public int getBottom(){
		return getYPos() + getHeight();
	}

	public void centerX(){
		xPos = (screenWidth() - getWidth()) / 2;
	}

	public void centerY(){
		yPos = (screenHeight() - getHeight()) / 2;
	}

	public void center(){
		centerX();
		centerY();
	}

	public boolean inside(int x, int y){
		return getXPos() <= x && x <= getRight() && getYPos() <= y && y <= getBottom();
	}

	public Rectangle toRectangle(){
		return new Rectangle(getXPos(), getYPos(), getWidth(), getHeight());
	}

	public Rectangle toInnerRectangle(){
		return new Rectangle(getXPos() + getBorderX(), getYPos() + getBorderY(), getWidth() - getBorderX() * 2, getHeight() - getBorderY() * 2);
	}

	public void fill(RGBA color){
		drawColoredRect(toRectangle(), color);
	}

}
