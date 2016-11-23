package code.elix_x.excore.utils.client.gui;

import code.elix_x.excore.utils.client.gui.elements.ColoredRectangleGuiElement;
import code.elix_x.excore.utils.client.gui.elements.IntegralSliderGuiElement;
import code.elix_x.excore.utils.color.RGBA;
import net.minecraft.client.gui.GuiScreen;

public class ColorSelectorGuiScreen extends ElementalGuiScreen {

	protected RGBA color;

	public ColorSelectorGuiScreen(GuiScreen parent, RGBA color){
		super(parent, 256, 104);
		this.color = color;
	}

	@Override
	protected void addElements(){
		add(new ColoredRectangleGuiElement("Color Display", xPos + (guiWidth - 20) / 2, yPos, 20, 20, 0, 2, color));

		nextY += 2 + 20 + 2;

		add(new ColoredRectangleGuiElement("Red Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(255, 0, 0, 255)));
		add(new IntegralSliderGuiElement("Red Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.r, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.r = getValue();
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Green Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 255, 0, 255)));
		add(new IntegralSliderGuiElement("Green Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.g, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.g = getValue();
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Blue Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 255, 255)));
		add(new IntegralSliderGuiElement("Blue Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.b, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.b = getValue();
			}

		});

		nextY += 2 + 16 + 2;

		add(new ColoredRectangleGuiElement("Alpha Gradient", xPos, nextY, guiWidth, 16, 0, 2, new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255), new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255)));
		add(new IntegralSliderGuiElement("Alpha Slider", xPos, nextY, guiWidth, 16, 0, 2, 4, 0, 255, color.a, true){

			@Override
			protected void checkSliderValue(){
				super.checkSliderValue();
				color.a = getValue();
			}

		});

		nextY += 2 + 16 + 2;
	}

}
