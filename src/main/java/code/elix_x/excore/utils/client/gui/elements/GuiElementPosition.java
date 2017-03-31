package code.elix_x.excore.utils.client.gui.elements;

import org.lwjgl.util.Rectangle;

import net.minecraft.client.gui.GuiScreen;

public interface GuiElementPosition<E extends PositionedGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPosition<E, H, P>> {

	void initGui(E element, GuiScreen gui);

	int getX();

	int getY();

	public class FixedGuiElementPosition<E extends PositionedGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPosition<E, H, P>> implements GuiElementPosition<E, H, P> {

		private int x;
		private int y;

		public FixedGuiElementPosition(int x, int y){
			this.x = x;
			this.y = y;
		}

		@Override
		public void initGui(E element, GuiScreen gui){

		}

		@Override
		public int getX(){
			return this.x;
		}

		@Override
		public int getY(){
			return this.y;
		}

		public void setX(int x){
			this.x = x;
		}

		public void setY(int y){
			this.y = y;
		}

	}

	public class RelativeGuiElementPosition<E extends PositionedGuiElement<E, H, P>, H extends IGuiElementsHandler<E>, P extends GuiElementPosition<E, H, P>> implements GuiElementPosition<E, H, P> {

		private float x;
		private float y;

		private Rectangle currentGui;
		
		public RelativeGuiElementPosition(float x, float y){
			this.x = x;
			this.y = y;
		}

		@Override
		public void initGui(E element, GuiScreen gui){
//			element.screen();
		}

		@Override
		public int getX(){
			return 0;
		}

		@Override
		public int getY(){
			return 0;
		}

		public void setX(float x){
			this.x = x;
		}

		public void setY(float y){
			this.y = y;
		}

	}
	
}
