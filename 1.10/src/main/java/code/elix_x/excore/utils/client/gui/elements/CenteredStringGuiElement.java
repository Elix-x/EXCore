package code.elix_x.excore.utils.client.gui.elements;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.FontRenderer;

public class CenteredStringGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends StringGuiElement<H> {

	public CenteredStringGuiElement(String name, int xPos, int yPos, int borderX, int borderY, String text, FontRenderer fontRenderer, RGBA color){
		super(name, xPos - fontRenderer.getStringWidth(text) / 2, yPos, borderX, borderY, text, fontRenderer, color);
	}

}
