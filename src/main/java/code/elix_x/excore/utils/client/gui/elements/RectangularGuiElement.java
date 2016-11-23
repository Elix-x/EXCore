package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.util.Rectangle;

import code.elix_x.excore.utils.color.RGBA;

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
