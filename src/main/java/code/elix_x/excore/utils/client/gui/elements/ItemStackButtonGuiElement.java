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
