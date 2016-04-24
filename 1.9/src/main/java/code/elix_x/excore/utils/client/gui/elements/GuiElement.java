package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import code.elix_x.excore.utils.client.render.ItemStackRenderer;
import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.config.GuiUtils;

public abstract class GuiElement<H extends IGuiElementsHandler<? extends IGuiElement<H>>> implements IGuiElement<H> {

	protected String name;

	public GuiElement(String name){
		this.name = name;
	}

	@Override
	public String getName(){
		return name;
	}

	public ScaledResolution screenResolution(){
		return new ScaledResolution(Minecraft.getMinecraft());
	}

	public int screenWidth(){
		return screenResolution().getScaledWidth();
	}

	public int screenHeight(){
		return screenResolution().getScaledHeight();
	}

	public int toLocal(int screen){
		return screen / screenResolution().getScaleFactor();
	}

	public int toScreen(int local){
		return local * screenResolution().getScaleFactor();
	}

	public Rectangle screen(){
		return new Rectangle(0, 0, screenWidth(), screenHeight());
	}

	public int mouseX(){
		return toLocal(Mouse.getX());
	}

	public int mouseY(){
		return screenHeight() - toLocal(Mouse.getY()) - 1;
	}

	public Rectangle cursor(int mouseX, int mouseY){
		return new Rectangle(mouseX, mouseY, 8, 8);
	}

	public int right(Rectangle element){
		return element.getX() + element.getWidth();
	}

	public int bottom(Rectangle element){
		return element.getY() + element.getHeight();
	}

	public Rectangle smartPos(Rectangle screen, Rectangle element){
		if(element.getWidth() > screen.getWidth() || element.getHeight() > screen.getHeight()){
			throw new IllegalArgumentException("Element larger than screen");
		} else {
			if(element.getX() < screen.getX()) element.setX(screen.getX());
			if(element.getY() < screen.getY()) element.setY(screen.getY());
			if(right(element) > right(screen)) element.setX(right(screen) - element.getWidth());
			if(bottom(element) > bottom(screen)) element.setY(bottom(screen) - element.getHeight());
			return element;
		}
	}

	public Rectangle smartPos(Rectangle screen, Rectangle cursor, Rectangle element, boolean alignLeft, boolean alignTop){
		element = smartPos(screen, element);
		if(element.intersects(cursor)){
			if(right(element) > cursor.getX() && element.getX() < right(cursor)){
				if(cursor.getX() - screen.getX() < element.getWidth()) alignLeft = false;
				if(right(screen) - right(cursor) < element.getWidth()) alignLeft = true;
				if(alignLeft){
					element.setX(cursor.getX() - element.getWidth());
				} else {
					element.setX(right(cursor));
				}
			}
			if(bottom(element) > cursor.getY() && element.getY() < bottom(cursor)){
				if(cursor.getY() - screen.getY() < element.getHeight()) alignTop = false;
				if(bottom(screen) - bottom(cursor) < element.getHeight()) alignTop = true;
				if(alignTop){
					element.setY(cursor.getY() - element.getHeight());
				} else {
					element.setY(bottom(cursor));
				}
			}
		}
		return element;
	}

	public void drawColoredRect(Rectangle element, RGBA color, double zLevel){
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(color.getRF(), color.getGF(), color.getBF(), color.getAF());
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(element.getX(), bottom(element), zLevel).endVertex();
		vertexbuffer.pos(right(element), bottom(element), zLevel).endVertex();
		vertexbuffer.pos(right(element), element.getY(), zLevel).endVertex();
		vertexbuffer.pos(element.getX(), element.getY(), zLevel).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public void drawColoredRect(Rectangle element, RGBA color){
		drawColoredRect(element, color, 0);
	}

	public void drawTexturedRect(Rectangle element, Rectangle texture, int textureWidth, int textureHeight, double zLevel){
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		float uScale = 1f / textureWidth;
		float vScale = 1f / textureHeight;
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(element.getX(), bottom(element), zLevel).tex(texture.getX() * uScale, bottom(texture) * vScale).endVertex();
		vertexbuffer.pos(right(element), bottom(element), zLevel).tex(right(texture) * uScale, bottom(texture) * vScale).endVertex();
		vertexbuffer.pos(right(element), element.getY(), zLevel).tex(right(texture) * uScale, texture.getY() * vScale).endVertex();
		vertexbuffer.pos(element.getX(), element.getY(), zLevel).tex(texture.getX() * uScale, texture.getY() * vScale).endVertex();
		tessellator.draw();
		GlStateManager.disableBlend();
	}

	public void drawTexturedRect(Rectangle element, Rectangle texture, int textureWidth, int textureHeight){
		drawTexturedRect(element, texture, textureWidth, textureHeight, 0);
	}

	public void scissorsPre(){
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public void scisors(Rectangle element){
		GL11.glScissor(toScreen(element.getX()), toScreen(screenHeight() - element.getY() - element.getHeight()), toScreen(element.getWidth()), toScreen(element.getHeight()));
	}

	public void scissorsPost(){
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public void drawStringPre(){
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
	}

	public void drawString(FontRenderer font, String text, int x, int y, RGBA color){
		font.drawStringWithShadow(text, (float)x, (float)y, color.argb());
	}

	public void drawStringPost(){
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}

	public void drawStringFull(FontRenderer font, String text, int x, int y, RGBA color){
		drawStringPre();
		drawString(font, text, x, y, color);
		drawStringPost();
	}

	public void renderItemStackPre(){
		RenderHelper.enableGUIStandardItemLighting();	
	}

	public void renderItemStack(ItemStack itemstack, int x, int y){
		ItemStackRenderer.renderItemStack(Minecraft.getMinecraft(), x, y, itemstack);
	}

	public void renderItemStackPost(){
		RenderHelper.disableStandardItemLighting();
	}

	public void renderItemStackFull(ItemStack itemstack, int x, int y){
		renderItemStackPre();
		renderItemStack(itemstack, x, y);
		renderItemStackPost();
	}

	public int maxWidth(FontRenderer font, String... ss){
		int max = 0;
		for(String s : ss){
			max = Math.max(max, font.getStringWidth(s));
		}
		return max;
	}

	public String[] translate(String... strings){
		String[] trans = new String[strings.length];
		for(int i = 0; i < strings.length; i++){
			trans[i] = I18n.translateToLocal(strings[i]);
		}
		return trans;
	}

	public void drawStandartTooltipBackground(Rectangle element){
		final int zLevel = 300;
		final int backgroundColor = 0xF0100010;
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, element.getY() - 4, right(element) + 3, element.getY() - 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, bottom(element) + 3, right(element) + 3, bottom(element) + 4, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, element.getY() - 3, right(element) + 3, bottom(element) + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, element.getX() - 4, element.getY() - 3, element.getX() - 3, bottom(element) + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, right(element) + 3, element.getY() - 3, right(element) + 4, bottom(element) + 3, backgroundColor, backgroundColor);
		final int borderColorStart = 0x505000FF;
		final int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, element.getY() - 3 + 1, element.getX() - 3 + 1, bottom(element) + 3 - 1, borderColorStart, borderColorEnd);
		GuiUtils.drawGradientRect(zLevel, right(element) + 2, element.getY() - 3 + 1, right(element) + 3, bottom(element) + 3 - 1, borderColorStart, borderColorEnd);
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, element.getY() - 3, right(element) + 3, element.getY() - 3 + 1, borderColorStart, borderColorStart);
		GuiUtils.drawGradientRect(zLevel, element.getX() - 3, bottom(element) + 2, right(element) + 3, bottom(element) + 3, borderColorEnd, borderColorEnd);
	}

	public void drawTooltip(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, RGBA textColor, boolean background, boolean translate, String... tooltips){
		if(translate) tooltips = translate(tooltips);
		Rectangle tooltip = smartPos(screen(), cursor(mouseX, mouseY), new Rectangle(mouseX, mouseY, maxWidth(font, tooltips), tooltips.length * (8 + verticalTextSpace) - verticalTextSpace), alignLeft, alignTop);
		if(background) drawStandartTooltipBackground(tooltip);
		drawStringPre();
		for(int i = 0; i < tooltips.length; i++){
			drawString(font, tooltips[i], tooltip.getX(), tooltip.getY() + i * (8 + verticalTextSpace), textColor);
		}
		drawStringPost();
	}

	public void drawTooltip(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, RGBA textColor, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, textColor, false, false, tooltips);
	}

	public void drawTooltip(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, RGBA textColor, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, 2, textColor, tooltips);
	}

	public void drawTooltip(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}

	public void drawTooltip(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, 2, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}


	public void drawTooltipTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, RGBA textColor, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, textColor, false, true, tooltips);
	}

	public void drawTooltipTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, RGBA textColor, String... tooltips){
		drawTooltipTranslate(font, mouseX, mouseY, alignLeft, alignTop, 2, textColor, tooltips);
	}

	public void drawTooltipTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, String... tooltips){
		drawTooltipTranslate(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}

	public void drawTooltipTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, String... tooltips){
		drawTooltipTranslate(font, mouseX, mouseY, alignLeft, alignTop, 2, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}


	public void drawTooltipWithBackground(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, RGBA textColor, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, textColor, true, false, tooltips);
	}

	public void drawTooltipWithBackground(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, RGBA textColor, String... tooltips){
		drawTooltipWithBackground(font, mouseX, mouseY, alignLeft, alignTop, 2, textColor, tooltips);
	}

	public void drawTooltipWithBackground(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, String... tooltips){
		drawTooltipWithBackground(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}

	public void drawTooltipWithBackground(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, String... tooltips){
		drawTooltipWithBackground(font, mouseX, mouseY, alignLeft, alignTop, 2, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}


	public void drawTooltipWithBackgroundTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, RGBA textColor, String... tooltips){
		drawTooltip(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, textColor, true, true, tooltips);
	}

	public void drawTooltipWithBackgroundTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, RGBA textColor, String... tooltips){
		drawTooltipWithBackgroundTranslate(font, mouseX, mouseY, alignLeft, alignTop, 2, textColor, tooltips);
	}

	public void drawTooltipWithBackgroundTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, int verticalTextSpace, String... tooltips){
		drawTooltipWithBackgroundTranslate(font, mouseX, mouseY, alignLeft, alignTop, verticalTextSpace, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}

	public void drawTooltipWithBackgroundTranslate(FontRenderer font, int mouseX, int mouseY, boolean alignLeft, boolean alignTop, String... tooltips){
		drawTooltipWithBackgroundTranslate(font, mouseX, mouseY, alignLeft, alignTop, 2, new RGBA(1f, 1f, 1f, 1f), tooltips);
	}

}
