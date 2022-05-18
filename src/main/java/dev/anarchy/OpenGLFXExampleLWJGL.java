package dev.anarchy;

import com.huskerdev.openglfx.DirectDrawPolicy;
import com.huskerdev.openglfx.ext.OpenGLPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OpenGLFXExampleLWJGL extends Application {
	
	private Label fpsLabel;

	@Override
	public void start(Stage stage) throws Exception {
		/* Root Node */
		StackPane root = new StackPane();
		

		{
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			Text scenetitle = new Text("Welcome");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);

			Label userName = new Label("User Name:");
			grid.add(userName, 0, 1);

			TextField userTextField = new TextField();
			grid.add(userTextField, 1, 1);

			Label pw = new Label("Password:");
			grid.add(pw, 0, 2);

			PasswordField pwBox = new PasswordField();
			grid.add(pwBox, 1, 2);

			Button btn = new Button("Sign in");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn);
			grid.add(hbBtn, 1, 4);
			
			root.getChildren().add(grid);
			
			
			MenuBar menuBar = new MenuBar();
			Menu menuFile = new Menu("File");
			Menu menuEdit = new Menu("Edit");
			Menu menuView = new Menu("View");

			menuFile.getItems().add(new MenuItem("NO"));
			menuEdit.getItems().add(new MenuItem("Yes?"));
			menuView.getItems().add(new MenuItem("Maybe :)"));
			
			menuBar.getMenus().add(menuFile);
			menuBar.getMenus().add(menuEdit);
			menuBar.getMenus().add(menuView);
			root.getChildren().add(menuBar);
		}
		
		
		/* Setup Scene */
		stage.setScene(new Scene(root, 320, 240));
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
