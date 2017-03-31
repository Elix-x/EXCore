package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.util.Rectangle;

public interface GuiElementPositionAndSize<E extends RectangularGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPositionAndSize<E, H, P>> extends GuiElementPosition<E, H, P> {

	int getWidth();

	int getHeight();

	default Rectangle toBoundsRectangle(){
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	default Rectangle toInnerRectangle(){
		return toBoundsRectangle();
	}

}
