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

import org.lwjgl.opengl.GL11;

import code.elix_x.excomms.color.RGBA;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ColoredRectangleGuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> extends RectangularGuiElement<H> {

	protected RGBA tl;
	protected RGBA tr;
	protected RGBA bl;
	protected RGBA br;

	public ColoredRectangleGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, RGBA tl, RGBA tr, RGBA bl, RGBA br){
		super(name, xPos, yPos, width, height, borderX, borderY);
		this.tl = tl;
		this.tr = tr;
		this.bl = bl;
		this.br = br;
	}

	public ColoredRectangleGuiElement(String name, int xPos, int yPos, int width, int height, int borderX, int borderY, RGBA color){
		this(name, xPos, yPos, width, height, borderX, borderY, color, color, color, color);
	}

	@Override
	public void openGui(H handler, GuiScreen gui){

	}

	@Override
	public void initGui(H handler, GuiScreen gui){

	}

	@Override
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos(xPos + borderX, yPos + borderY + height, 0.0D).color(bl.getRF(), bl.getGF(), bl.getBF(), bl.getAF()).endVertex();
		vertexbuffer.pos(xPos + borderX + width, yPos + borderY + height, 0.0D).color(br.getRF(), br.getGF(), br.getBF(), br.getAF()).endVertex();
		vertexbuffer.pos(xPos + borderX + width, yPos + borderY, 0.0D).color(tr.getRF(), tr.getGF(), tr.getBF(), tr.getAF()).endVertex();
		vertexbuffer.pos(xPos + borderX, yPos + borderY, 0.0D).color(tl.getRF(), tl.getGF(), tl.getBF(), tl.getAF()).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	@Override
	public void drawGuiPostPost(H handler, GuiScreen gui, int mouseX, int mouseY, float partialTicks){

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
