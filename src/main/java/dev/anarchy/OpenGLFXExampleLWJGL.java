package dev.anarchy;

import com.huskerdev.openglfx.DirectDrawPolicy;
import com.huskerdev.openglfx.OpenGLCanvas;
import com.huskerdev.openglfx.lwjgl.LWJGLInitializer;

import dev.anarchy.renderable.Gears;
import dev.anarchy.renderable.Renderable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OpenGLFXExampleLWJGL extends Application {

	private Renderable renderable;

	@Override
	public void start(Stage stage) throws Exception {
		/* Root Node */
		StackPane root = new StackPane();
		
		/* Create OpenGLCanvas underneath */
		root.getChildren().add(createGLPane());
		
		/* Add Label On top */
		Label label = new Label("JavaFX rendering on top. OpenFXGL rendering below");
		label.setBackground(new Background(new BackgroundFill(new Color(0, 0.5, 0.9, 0.5), null, null)));
		root.getChildren().add(label);
		
		/* Setup Scene */
		stage.setScene(new Scene(root, 320, 240));
		stage.show();
	}

	private Pane createGLPane() {
		OpenGLCanvas canvas = OpenGLCanvas.create(new LWJGLInitializer(), DirectDrawPolicy.ALWAYS);
		canvas.createTimer(60.0);

		canvas.onInitialize(()->{
			renderable = new Gears();
		});
		
		canvas.onReshape(() -> {
			renderable.onSizeChange(canvas.getWidth(), canvas.getHeight());
		});

		canvas.onUpdate(() -> {
			if ( renderable == null )
				return;
			
			renderable.onUpdate();
		});

		canvas.onRender(() -> {
			renderable.onRender(canvas.getWidth(), canvas.getHeight());
		});

		return canvas;
	}

	public static void main(String[] args) {
		System.setProperty("prism.order", "es2,d3d,sw");
		System.setProperty("prism.vsync", "false");
		launch(args);
	}
}
