package dev.anarchy;

import com.huskerdev.openglfx.DirectDrawPolicy;
import com.huskerdev.openglfx.ext.OpenGLPane;

import dev.anarchy.renderable.Gears;
import dev.anarchy.renderable.Renderable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
	
	private Label fpsLabel;

	@Override
	public void start(Stage stage) throws Exception {
		/* Root Node */
		StackPane root = new StackPane();
		
		/* Create OpenGLCanvas underneath */
		root.getChildren().add(createGLPane());
		
		/* Add Label On top */
		root.getChildren().add(createLabel());
		
		/* Setup Scene */
		stage.setScene(new Scene(root, 320, 240));
		stage.show();
	}
	
	private Label createLabel() {
		Label label = new Label("JavaFX rendering on top. OpenGLFX rendering below");
		label.setBackground(new Background(new BackgroundFill(new Color(0, 0.5, 0.9, 0.5), null, null)));
		return label;
	}

	private Pane createGLPane() {
		// Create new OpenGLPane
		OpenGLPane openGLPane = OpenGLPane.create(OpenGLPane.LWJGL_MODULE, DirectDrawPolicy.ALWAYS);
		openGLPane.desiredFPSProperty().set(144);
		
		// Add fps Label
		fpsLabel = new Label("");
		openGLPane.getChildren().add(fpsLabel);
		openGLPane.setAlignment(Pos.TOP_RIGHT);

		// Setup example
		openGLPane.setOnGLInitialize((event)->{
			renderable = new Gears();
		});
		
		// Render example
		openGLPane.setOnRender((event)-> {
			Platform.runLater(()->{
				fpsLabel.setText("fps: " + Math.floor(1d/event.getDelta()));
			});
			
			renderable.onRender(openGLPane.getWidth(), openGLPane.getHeight(), event.getDelta());
		});
		
		return openGLPane;
	}
	
	/**
	 * JavaFX Application Entry Point.
	 * When testing use Main.class
	 */
	public static void main(String[] args) {
		System.setProperty("prism.order", "es2,d3d,sw");
		System.setProperty("prism.vsync", "false");
		launch(args);
	}
}
