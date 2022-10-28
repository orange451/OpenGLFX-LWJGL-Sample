package dev.anarchy.jotepad.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.panemu.tiwulfx.control.dock.DetachableTabPane;

import dev.anarchy.ace.AceEditor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIBuilder {
	/**
	 * Build all UI-elements used by the application.
	 */
	public static Parent build(Stage stage) {
		BorderPane layout = new BorderPane();
		
		// Top
		{
			BorderPane top = new BorderPane();
			
			HBox actions = buildActionBar();
			top.setCenter(actions);
			
			layout.setTop(top);
		}
		
		// Center
		{
			BorderPane center = new BorderPane();
			
			Node stuff = buildContentPane(stage);
			center.setCenter(stuff);
			
			layout.setCenter(center);
		}
		
		// Bottom
		{
			StackPane bottom = new StackPane();
			
			bottom.getChildren().add(buildStatusBar());
			
			layout.setBottom(bottom);
		}
		
		return layout;
	}

	/**
	 * Status bar on bottom of application.
	 * Displays metadata information.
	 */
	private static Node buildStatusBar() {
		HBox status = new HBox();
		
		Label label = new Label("Status Bar");
		status.getChildren().add(label);
		
		return status;
	}

	/**
	 * Content pane dictates the actively opened documents.
	 */
	private static Node buildContentPane(Stage stage) {
		SplitPane split = new SplitPane();
		split.setStyle("-fx-background-insets:0; -fx-background-color:TRANSPARENT;");
		{
			DetachableTabPane tabPane = new DetachableTabPane();
			tabPane.setStyle("-fx-background-insets:0; -fx-background-color:TRANSPARENT;");
			{
				for (int i = 0; i < 3; i++) {
					Tab tab = new Tab("Test.lua");
					tab.setGraphic(Icons.SAVE_MODIFIED.get());
		
					AceEditor editor = new AceEditor();
					tab.setContent(editor);
					
					tabPane.getTabs().add(tab);
				}
				
			}
			split.getItems().add(tabPane);
			
			tabPane.setStageOwnerFactory((stg) -> stage);
		}
		
		return split;
	}

	/**
	 * Action Bar at the top of application.
	 */
	private static HBox buildActionBar() {
		HBox actionBar = new HBox();
		
		{
			actionBar.getChildren().add(Icons.FILE_NEW.get());
			actionBar.getChildren().add(Icons.OPEN.get());
			Node save = Icons.SAVE.get();
			save.setDisable(true);
			actionBar.getChildren().add(save);
			actionBar.getChildren().add(Icons.SAVE_ALL.get());
		}
		
		return actionBar;
	}

	protected static String getExternalResource(String resourceName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			return classLoader.getResource(resourceName).toExternalForm();
		} catch(Exception e) {
			return null;
		}
	}
	
	protected static List<String> getStylesheet() {
		
		// List of stylesheets we want to load
		List<String> styleSheets = Arrays.asList(
			"stylesheet/bootstrap2.css",
			"stylesheet/style.css"
		);
		
		// Styles in IDE
		List<String> styles = new ArrayList<>();
		styles.addAll(styleSheets);
		
		// Styles outside of IDE (external)
		for (String style : styleSheets) {
			String externalStyle = "resources/" + style;
			String externalResource = getExternalResource(externalStyle);
			if ( externalResource != null ) {
				styles.add(externalStyle);
			}
		}
		
		return styles;
	}

	public static void setTheme(Parent scene) {
		try {
			scene.getStylesheets().addAll(getStylesheet());
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void setTheme(Scene scene) {
		try {
			scene.getStylesheets().addAll(getStylesheet());
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
