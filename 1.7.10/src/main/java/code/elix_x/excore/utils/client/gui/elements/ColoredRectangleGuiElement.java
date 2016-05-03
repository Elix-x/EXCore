package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

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
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA(bl.getRI(), bl.getGI(), bl.getBI(), bl.getAI()); tessellator.addVertex(xPos + borderX, yPos + borderY + height, 0.0D);;
		tessellator.setColorRGBA(br.getRI(), br.getGI(), br.getBI(), br.getAI()); tessellator.addVertex(xPos + borderX + width, yPos + borderY + height, 0.0D);
		tessellator.setColorRGBA(tr.getRI(), tr.getGI(), tr.getBI(), tr.getAI()); tessellator.addVertex(xPos + borderX + width, yPos + borderY, 0.0D);
		tessellator.setColorRGBA(tl.getRI(), tl.getGI(), tl.getBI(), tl.getAI()); tessellator.addVertex(xPos + borderX, yPos + borderY, 0.0D);
		tessellator.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
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
