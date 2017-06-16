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

public interface IGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> {

	public String getName();

	public void openGui(H handler, GuiScreen gui);

	public void initGui(H handler, GuiScreen gui);

	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks);

	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks);

	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks);

	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks);

	public boolean handleKeyboardEvent(H handler, GuiScreen gui, boolean down, int key, char c);

	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, boolean down, int key);

	public boolean handleMouseEvent(H handler, GuiScreen gui, int mouseX, int mouseY, int dWheel);

}
