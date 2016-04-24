package code.elix_x.excore.utils.client.gui;

import code.elix_x.excore.utils.client.gui.elements.ColoredRectangleGuiElement;
import code.elix_x.excore.utils.client.gui.elements.SliderGuiElement;
import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenColorSelector extends ElementalGuiScreen {

	protected RGBA color;

	public GuiScreenColorSelector(GuiScreen parent, RGBA color){
		super(parent, 256, 160);
		this.color = color;
	}

	@Override
	public void initGui(){
		super.initGui();

		elements.clear();

		int nextY = yPos;

		elements.add(new ColoredRectangleGuiElement("Color Display", xPos + (guiWidth - 20) / 2, yPos, 20, 20, 0, 2, color));

		nextY += 2 + 20 + 2;

		elements.add(new ColoredRectangleGuiElement("Red Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255)));
		elements.add(new SliderGuiElement("Red Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.r, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.r = (int) getValue();
			}

		});

		nextY += 2 + 16 + 2;

		elements.add(new ColoredRectangleGuiElement("Green Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255)));
		elements.add(new SliderGuiElement("Green Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.g, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.g = (int) getValue();
			}

		});

		nextY += 2 + 16 + 2;

		elements.add(new ColoredRectangleGuiElement("Blue Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255)));
		elements.add(new SliderGuiElement("Blue Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.b, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.b = (int) getValue();
			}

		});

		nextY += 2 + 16 + 2;

		elements.add(new ColoredRectangleGuiElement("Alpha Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255)));
		elements.add(new SliderGuiElement("Alpha Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.a, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.a = (int) getValue();
			}

		});

		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		drawBackground(0);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
