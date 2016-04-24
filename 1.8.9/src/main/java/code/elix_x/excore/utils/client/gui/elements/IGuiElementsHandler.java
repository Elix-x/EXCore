package code.elix_x.excore.utils.client.gui.elements;

public interface IGuiElementsHandler<E extends IGuiElement<? extends IGuiElementsHandler<E>>> {

	public void add(E element);

	public E getFocused();

	public void setFocused(E element);

	public void looseFocus();

}
