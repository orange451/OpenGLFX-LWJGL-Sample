package dev.anarchy.jotepad;

import dev.anarchy.jotepad.ui.UIBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JotepadPlusPlusApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(UIBuilder.build(stage), 320, 240));
		
		UIBuilder.setTheme(stage.getScene());
		
		stage.show();
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
