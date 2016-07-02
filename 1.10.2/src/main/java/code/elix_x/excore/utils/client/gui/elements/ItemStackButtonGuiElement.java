package code.elix_x.excore.utils.client.gui.elements;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

public class ItemStackButtonGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends ButtonGuiElement<H> {

	protected ItemStack itemstack;

	public ItemStackButtonGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, ItemStack itemstack){
		super(name, xPos, yPos, width, height, borderX, borderY, "");
		this.itemstack = itemstack;
	}

	public ItemStack getItemStack(){
		return itemstack;
	}

	public void setItemStack(ItemStack itemstack){
		this.itemstack = itemstack;
	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		super.drawGuiPost(handler, gui, mouseX, mouseY);
		renderItemStackPre();
		renderItemStack(itemstack, getXPos() + (getWidth() - 16) / 2, getYPos() + (getHeight() - 16) / 2);
		renderItemStackPost();
	}

}
