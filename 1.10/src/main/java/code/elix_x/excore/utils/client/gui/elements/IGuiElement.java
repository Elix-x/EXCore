package code.elix_x.excore.utils.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;

public interface IGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> {

	public String getName();

	public void openGui(H handler, GuiScreen gui);

	public void initGui(H handler, GuiScreen gui);

	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY);

	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY);

	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY);

	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY);

	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c);

	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key);

	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel);

}
