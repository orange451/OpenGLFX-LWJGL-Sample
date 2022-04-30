package dev.anarchy.renderable;

public interface Renderable {
	public void onSizeChange(double width, double height);
	public void onRender(double width, double height);
	public void onUpdate();
}
