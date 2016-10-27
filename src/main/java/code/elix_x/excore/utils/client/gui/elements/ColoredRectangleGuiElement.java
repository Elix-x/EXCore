package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.opengl.GL11;

import code.elix_x.excore.utils.color.RGBA;
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
	public void drawGuiPre(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawBackground(H handler, GuiScreen gui, int mouseX, int mouseY){

	}

	@Override
	public void drawGuiPost(H handler, GuiScreen gui, int mouseX, int mouseY){
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
