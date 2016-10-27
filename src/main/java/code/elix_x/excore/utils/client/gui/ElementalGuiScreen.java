package code.elix_x.excore.utils.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import code.elix_x.excore.utils.client.gui.elements.IGuiElement;
import code.elix_x.excore.utils.client.gui.elements.IGuiElementsHandler;
import net.minecraft.client.gui.GuiScreen;

public class ElementalGuiScreen extends BasicGuiScreen implements IGuiElementsHandler<IGuiElement<ElementalGuiScreen>> {

	protected List<IGuiElement<ElementalGuiScreen>> elements = new ArrayList<IGuiElement<ElementalGuiScreen>>();

	protected IGuiElement<ElementalGuiScreen> focused;

	protected int nextY;

	public ElementalGuiScreen(GuiScreen parent, int guiWidth, int guiHeight){
		super(parent, guiWidth, guiHeight);
	}

	@Override
	public void add(IGuiElement<ElementalGuiScreen> element){
		elements.add(element);
	}

	@Override
	public IGuiElement<ElementalGuiScreen> getFocused(){
		return focused;
	}

	@Override
	public void setFocused(IGuiElement element){
		focused = element;
	}

	@Override
	public void looseFocus(){
		setFocused(null);
	}

	@Override
	public void initGui(){
		super.initGui();
		elements.clear();
		nextY = yPos;
		addElements();
		for(IGuiElement<ElementalGuiScreen> element : elements)
			element.initGui(this, this);
	}

	protected void addElements(){

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		for(IGuiElement<ElementalGuiScreen> element : elements){
			element.drawGuiPre(this, this, mouseX, mouseY);
		}
		for(IGuiElement<ElementalGuiScreen> element : elements){
			element.drawBackground(this, this, mouseX, mouseY);
		}
		for(IGuiElement<ElementalGuiScreen> element : elements){
			element.drawGuiPost(this, this, mouseX, mouseY);
		}
		for(IGuiElement<ElementalGuiScreen> element : elements){
			element.drawGuiPostPost(this, this, mouseX, mouseY);
		}
	}

	@Override
	protected void keyTyped(char c, int key) throws IOException{
		super.keyTyped(c, key);
		if(focused != null && focused.handleKeyboardEvent(this, this, true, key, c)) return;
		for(IGuiElement<ElementalGuiScreen> element : elements){
			if(element != focused && element.handleKeyboardEvent(this, this, true, key, c)) return;
		}
	}

	@Override
	public void handleMouseInput() throws IOException{
		super.handleMouseInput();
		int dWheel = Mouse.getEventDWheel();
		if(dWheel != 0){
			int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
			int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			if(focused != null && focused.handleMouseEvent(this, this, mouseX, mouseY, dWheel)) return;
			for(IGuiElement<ElementalGuiScreen> element : elements){
				if(element != focused && element.handleMouseEvent(this, this, mouseX, mouseY, dWheel)) return;
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int key) throws IOException{
		if(focused != null && focused.handleMouseEvent(this, this, mouseX, mouseY, true, key)) return;
		for(IGuiElement<ElementalGuiScreen> element : elements){
			if(element != focused && element.handleMouseEvent(this, this, mouseX, mouseY, true, key)) return;
		}
		super.mouseClicked(mouseX, mouseY, key);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int key){
		if(focused != null && focused.handleMouseEvent(this, this, mouseX, mouseY, false, key)) return;
		for(IGuiElement<ElementalGuiScreen> element : elements){
			if(element != focused && element.handleMouseEvent(this, this, mouseX, mouseY, false, key)) return;
		}
		super.mouseReleased(mouseX, mouseY, key);
	}

}
