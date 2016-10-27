package code.elix_x.excore.utils.client.gui.elements;

import java.lang.reflect.Array;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;

public class GridGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected GridElement[][] elements;

	protected int elementsX;
	protected int elementsY;

	public int elementX;
	public int elementY;

	public RGBA backgroundColor;

	public GridGuiElement(String name, int xPos, int yPos, int elementX, int elementY, int elementsX, int elementsY, int borderX, int borderY, RGBA backgroundColor){
		super(name, xPos, yPos, 0, 0, borderX, borderY);
		// this.elements = new GridElement[elementsX][elementsY];
		this.elements = (GridGuiElement<H>.GridElement[][]) Array.newInstance(GridElement.class, elementsX, elementsY);
		this.elementsX = elementsX;
		this.elementsY = elementsY;
		this.elementX = elementX;
		this.elementY = elementY;
		this.backgroundColor = backgroundColor;
	}

	public GridGuiElement(String name, int posX, int posY, int elementX, int elementY, int elementsX, int elementsY, int borderX, int borderY){
		this(name, posX, posY, elementX, elementY, elementsX, elementsY, borderX, borderY, new RGBA(0, 0, 0, 0));
	}

	public GridGuiElement(String name, int posX, int posY, int elementX, int elementY, int elementsX, int elementsY){
		this(name, posX, posY, elementX, elementY, elementsX, elementsY, 2, 2);
	}

	@Override
	public int getWidth(){
		return borderX + this.elementsX * (elementX + borderX);
	}

	@Override
	public int getHeight(){
		return borderY + this.elementsY * (elementY + borderY);
	}

	protected void reInitElements(){
		elements = (GridGuiElement<H>.GridElement[][]) Array.newInstance(GridElement.class, elementsX, elementsY);
	}

	public int getElementsX(){
		return elementsX;
	}

	public int getElementsY(){
		return elementsY;
	}

	public void setElementsXY(int elementsX, int elementsY){
		this.elementsX = elementsX;
		this.elementsY = elementsY;
		GridElement[][] old = elements;
		reInitElements();
		for(int x = 0; x < Math.min(old.length, elements.length); x++){
			GridElement[] oldd = old[x];
			GridElement[] neww = elements[x];
			for(int y = 0; y < Math.min(oldd.length, neww.length); y++){
				neww[y] = oldd[y];
			}
		}
	}

	public GridElement getElement(int x, int y){
		return elements[x][y];
	}

	public void addElement(GridElement element, int x, int y){
		elements[x][y] = element;
	}

	public void addElement(GridElement element){
		for(int x = 0; x < elementsX; x++){
			for(int y = 0; y < elementsY; y++){
				if(elements[x][y] == null){
					elements[x][y] = element;
					return;
				}
			}
		}
	}

	public int getX(GridElement element){
		for(int x = 0; x < elementsX; x++){
			for(int y = 0; y < elementsY; y++){
				if(elements[x][y] == element){
					return x;
				}
			}
		}
		return -1;
	}

	public int getY(GridElement element){
		for(int x = 0; x < elementsX; x++){
			for(int y = 0; y < elementsY; y++){
				if(elements[x][y] == element){
					return y;
				}
			}
		}
		return -1;
	}

	public int calcAbsX(int x){
		return getXPos() + borderX + x * (borderX + elementX);
	}

	public int calcAbsY(int y){
		return getYPos() + borderY + y * (borderY + elementY);
	}

	public int calcLocalX(int x){
		return (x - getXPos() - borderX) / (borderX + elementX);
	}

	public int calcLocalY(int y){
		return (y - getYPos() - borderY) / (borderY + elementY);
	}

	@Override
	public void openGui(H handler, GuiScreen gui){

	}

	@Override
	public void initGui(H handler, GuiScreen gui){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null) e.initGui(handler, gui, calcAbsX(x), calcAbsY(y));
			}
		}
	}

	@Override
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null) e.drawGuiPre(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY);
			}
		}
	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null) e.drawBackground(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY);
			}
		}
	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		fill(backgroundColor);
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null) e.drawGuiPost(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY);
			}
		}
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null) e.drawGuiPostPost(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY);
			}
		}
	}

	@Override
	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null && e.handleKeyboardEvent(handler, gui, calcAbsX(x), calcAbsY(y), down, key, c))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null && e.handleMouseEvent(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY, down, key))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		for(int x = 0; x < elements.length; x++){
			GridElement[] es = elements[x];
			for(int y = 0; y < es.length; y++){
				GridElement e = es[y];
				if(e != null && e.handleMouseEvent(handler, gui, calcAbsX(x), calcAbsY(y), mouseX, mouseY, dWheel))
					return true;
			}
		}
		return false;
	}

	public class GridElement {

		public boolean inside(int relX, int relY, int mouseX, int mouseY){
			return relX <= mouseX && mouseX <= relX + elementX && relY <= mouseY && mouseY <= relY + elementY;
		}

		public void initGui(H handler, GuiScreen gui, int relX, int relY){

		}

		public void drawBackground(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPre(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPost(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPostPost(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY){

		}

		public boolean handleKeyboardEvent(H handler, GuiScreen gui, int relX, int relY, boolean down, int key, char c){
			return false;
		}

		public boolean handleMouseEvent(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY, boolean down, int key){
			return false;
		}

		public boolean handleMouseEvent(H handler, GuiScreen gui, int relX, int relY, int mouseX, int mouseY, int dWheel){
			return false;
		}

	}

}
