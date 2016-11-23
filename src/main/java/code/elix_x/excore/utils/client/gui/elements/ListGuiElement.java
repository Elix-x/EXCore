package code.elix_x.excore.utils.client.gui.elements;

import java.lang.reflect.Array;

import org.apache.commons.lang3.ArrayUtils;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;

public class ListGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected ListElement[] elements;

	public int elementY;

	public RGBA backgroundColor;

	protected boolean inverted = false;

	protected int scrollDistance;

	protected int clickTimeThreshold = 250;
	protected int clickDistanceThreshold = 2;

	private long prevClickTime;

	private int prevGrabY = -1;
	private int prevGrabScrollDistance = -1;

	public ListGuiElement(String name, int xPos, int yPos, int width, int height, int elementY, int borderX, int borderY, RGBA backgroundColor){
		super(name, xPos, yPos, width, height, borderX, borderY);
		// this.elements = new ListElement[0];
		this.elements = (ListGuiElement<H>.ListElement[]) Array.newInstance(ListElement.class, 0);
		this.elementY = elementY;
		this.backgroundColor = backgroundColor;
	}

	public ListGuiElement(String name, int xPos, int yPos, int width, int height, int elementY, int borderX, int borderY, RGBA backgroundColor, ListElement... elements){
		this(name, xPos, yPos, width, height, elementY, borderX, borderY, backgroundColor);
		this.elements = elements;
	}

	public ListGuiElement(String name, int xPos, int yPos, int width, int height, int elementY, int borderX, int borderY, RGBA backgroundColor, Iterable<ListElement> elements){
		this(name, xPos, yPos, width, height, elementY, borderX, borderY, backgroundColor);
		for(ListElement element : elements){
			this.elements = ArrayUtils.add(this.elements, element);
		}
	}

	protected void reInitElements(int length){
		this.elements = (ListGuiElement<H>.ListElement[]) Array.newInstance(ListElement.class, length);
	}

	protected void reInitElements(){
		reInitElements(0);
	}

	@Override
	public int getYPos(){
		return inverted ? super.getYPos() + borderY + elementY + borderY - getHeight() : super.getYPos();
	}

	public int getTotalHeight(){
		return borderY + elements.length * (elementY + borderY);
	}

	public int calcX(){
		return getXPos() + borderX;
	}

	public int calcRelY(int index){
		return inverted ? getBottom() - (index + 1) * (elementY + borderY) - scrollDistance : getYPos() + borderY + index * (elementY + borderY) - scrollDistance;
	}

	public int maxScrollDistance(){
		return getTotalHeight() - getHeight();
	}

	public boolean scrollable(){
		return getTotalHeight() > getHeight();
	}

	public float getScrollMultiplier(boolean ctrl){
		return ctrl ? 0.5f : 0.05f;
	}

	public void checkScrollDistance(){
		scrollDistance = inverted ? Math.min(Math.max(scrollDistance, -maxScrollDistance()), 0) : Math.max(Math.min(scrollDistance, maxScrollDistance()), 0);
	}

	public void setScrollDistance(int scrollDistance){
		this.scrollDistance = scrollDistance;
		checkScrollDistance();
	}

	public void scroll(int distance){
		scrollDistance += distance;
		checkScrollDistance();
	}

	public boolean grab(int mouseY){
		return System.currentTimeMillis() - prevClickTime > clickTimeThreshold || Math.abs(mouseY - prevGrabY) > clickDistanceThreshold;
	}

	public ListElement get(int i){
		return elements[i];
	}

	public void set(ListElement element, int i){
		elements[i] = element;
	}

	public void add(ListElement element){
		elements = ArrayUtils.add(elements, element);
	}

	public void add(ListElement element, int i){
		elements = ArrayUtils.add(elements, i, element);
	}

	public void remove(int i){
		elements = ArrayUtils.remove(elements, i);
	}

	public void remove(ListElement element){
		elements = ArrayUtils.removeElement(elements, element);
	}

	@Override
	public void openGui(H handler, GuiScreen gui){

	}

	@Override
	public void initGui(H handler, GuiScreen gui){
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null) elements[i].initGui(handler, gui, i, calcX(), calcRelY(i));
		}
	}

	@Override
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY){
		if(prevClickTime != -1 && grab(mouseY)){
			setScrollDistance(prevGrabScrollDistance - (mouseY - prevGrabY));
		}
		scissorsPre();
		scisors(toRectangle());
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null) elements[i].drawGuiPre(handler, gui, i, calcX(), calcRelY(i), mouseX, mouseY);
		}
		scissorsPost();
	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY){
		scissorsPre();
		scisors(toRectangle());
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null) elements[i].drawBackground(handler, gui, i, calcX(), calcRelY(i), mouseX, mouseY);
		}
		scissorsPost();
	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		scissorsPre();
		scisors(toRectangle());
		fill(backgroundColor);
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null) elements[i].drawGuiPost(handler, gui, i, calcX(), calcRelY(i), mouseX, mouseY);
		}
		scissorsPost();
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		scissorsPre();
		scisors(toRectangle());
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null) elements[i].drawGuiPostPost(handler, gui, i, calcX(), calcRelY(i), mouseX, mouseY);
		}
		scissorsPost();
	}

	@Override
	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c){
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null && elements[i].handleKeyboardEvent(handler, gui, i, calcX(), calcRelY(i), down, key, c))
				return true;
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		return handleMousEventPre(handler, gui, mouseX, mouseY, down, key) || handleMouseEventPost(handler, gui, mouseX, mouseY, down, key);
	}

	protected boolean handleMousEventPre(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		if(key == 0){
			if(down){
				if(inside(mouseX, mouseY) && prevClickTime == -1){
					prevClickTime = System.currentTimeMillis();
					prevGrabY = mouseY;
					prevGrabScrollDistance = scrollDistance;
					return true;
				}
			} else{
				if(prevClickTime != -1){
					if(grab(mouseY)){
						setScrollDistance(prevGrabScrollDistance - (mouseY - prevGrabY));
						prevClickTime = -1;
						prevGrabY = -1;
						prevGrabScrollDistance = -1;
						return true;
					} else{
						prevClickTime = -1;
						prevGrabY = -1;
						prevGrabScrollDistance = -1;
						return handleMouseEventPost(handler, gui, mouseX, mouseY, true, key);
					}
				}
			}
		}
		return false;
	}

	protected boolean handleMouseEventPost(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key){
		for(int i = 0; i < elements.length; i++){
			if(elements[i] != null && elements[i].handleMouseEvent(handler, gui, i, calcX(), calcRelY(i), mouseX, mouseY, down, key))
				return true;
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		if(scrollable() && inside(mouseX, mouseY)){
			scroll((int) (-dWheel * getScrollMultiplier(gui.isCtrlKeyDown())));
			return true;
		}
		return false;
	}

	public class ListElement {

		public boolean inside(int relY, int x, int y){
			return xPos + borderX <= x && x <= getRight() - borderX && relY <= y && y <= relY + elementY && getYPos() <= y && y <= getBottom();
		}

		public void initGui(H handler, GuiScreen gui, int index, int x, int relY){

		}

		public void drawBackground(H handler, GuiScreen gui, int index, int x, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPre(H handler, GuiScreen gui, int index, int x, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPost(H handler, GuiScreen gui, int index, int x, int relY, int mouseX, int mouseY){

		}

		public void drawGuiPostPost(H handler, GuiScreen gui, int index, int x, int relY, int mouseX, int mouseY){

		}

		public boolean handleKeyboardEvent(H handler, GuiScreen gui, int index, int x, int relY, boolean down, int key, char c){
			return false;
		}

		public boolean handleMouseEvent(H handler, GuiScreen gui, int index, int x, int relY, int mouseX, int mouseY, boolean down, int key){
			return false;
		}

	}

}
