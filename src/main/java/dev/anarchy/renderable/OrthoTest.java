package dev.anarchy.renderable;

import org.lwjgl.opengl.GL11;

public class OrthoTest implements Renderable {
	
	private double y;
	
	private double animVar;

	@Override
	public void onRender(double width, double height, double deltaTime) {
		animVar += 0.1;
		y = Math.sin(animVar) * (height / 3);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, width, 0.0, height, -1.0, 100.0);
		
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glClearDepth(1.0);

		GL11.glColor3f(1.0f, 0.5f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(0.0, 0.0);
		GL11.glVertex2d(width, 0.0);
		GL11.glVertex2d(width, height);
		GL11.glVertex2d(0.0, height);
		GL11.glEnd();

		// Moving rectangle
		double rectSize = 40.0;
		double rectX = (width - rectSize) / 2;
		double rectY = (height - rectSize) / 2 + y;

		GL11.glColor3f(0f, 0.5f, 0.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(rectX, rectY);
		GL11.glVertex2d(rectX + rectSize, rectY);
		GL11.glVertex2d(rectX + rectSize, rectY + rectSize);
		GL11.glVertex2d(rectX, rectY + rectSize);
		GL11.glEnd();
	}
}
