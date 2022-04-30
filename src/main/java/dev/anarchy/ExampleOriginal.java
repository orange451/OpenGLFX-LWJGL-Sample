package dev.anarchy;

import org.lwjgl.opengl.GL11;

import com.huskerdev.openglfx.DirectDrawPolicy;
import com.huskerdev.openglfx.OpenGLCanvas;
import com.huskerdev.openglfx.lwjgl.LWJGLInitializer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ExampleOriginal extends Application {

	double animVar = 0.0;
	double y = 0.0;

	@Override
	public void start(Stage stage) throws Exception {

		stage.setScene(new Scene(createGL(stage), 320, 240));
		stage.show();
	}

	private Region createGL(Stage stage) {
		OpenGLCanvas canvas = OpenGLCanvas.create(new LWJGLInitializer(), DirectDrawPolicy.ALWAYS);
		canvas.createTimer(200.0);

		canvas.onReshape(() -> {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0, canvas.getScene().getWidth(), 0.0, canvas.getScene().getHeight(), -1.0, 100.0);
		});

		canvas.onUpdate(() -> {
			animVar += 0.1;
			y = Math.sin(animVar) * (canvas.getHeight() / 3);
		});

		canvas.onRender(() -> {
			double width = canvas.getWidth();
			double height = canvas.getHeight();

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
		});

		return canvas;
	}

	public static void main(String[] args) {
		System.setProperty("prism.order", "es2,d3d,sw");
		System.setProperty("prism.vsync", "false");
		launch(args);
	}
}
