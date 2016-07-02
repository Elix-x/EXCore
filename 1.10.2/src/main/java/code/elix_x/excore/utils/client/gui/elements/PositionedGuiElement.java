package code.elix_x.excore.utils.client.gui.elements;

public abstract class PositionedGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends GuiElement<H> {

	protected int xPos;
	protected int yPos;

	public PositionedGuiElement(String name, int xPos, int yPos){
		super(name);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getXPos(){
		return xPos;
	}

	public int getYPos(){
		return yPos;
	}

	public void setXPos(int xPos){
		this.xPos = xPos;
	}

	public void setYPos(int yPos){
		this.yPos = yPos;
	}

}
