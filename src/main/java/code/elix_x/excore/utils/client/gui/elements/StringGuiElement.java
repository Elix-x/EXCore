package code.elix_x.excore.utils.client.gui.elements;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class StringGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected String text;
	protected FontRenderer fontRenderer;
	protected RGBA color;

	public StringGuiElement(String name, int xPos, int yPos, int borderX, int borderY, String text, FontRenderer fontRenderer, RGBA color){
		super(name, xPos, yPos, fontRenderer.getStringWidth(text), fontRenderer.FONT_HEIGHT, borderX, borderY);
		this.text = text;
		this.fontRenderer = fontRenderer;
		this.color = color;
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
		drawStringFull(fontRenderer, text, xPos + borderX, yPos + borderY, color);
		GlStateManager.color(1, 1, 1, 1);
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
		return false;
	}

	@Override
	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel){
		return false;
	}

}
