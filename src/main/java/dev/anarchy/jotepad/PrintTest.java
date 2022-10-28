package dev.anarchy.jotepad;

import javafx.application.Application;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrintTest extends Application {

	private Stage stage;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		VBox root = new VBox(5);
		
		this.stage = stage;

		Label textLbl = new Label("Text:");
		TextArea text = new TextArea();
		text.setPrefRowCount(10);
		text.setPrefColumnCount(20);
		text.setWrapText(true);

		// Button to print the TextArea node
		Button printTextBtn = new Button("Print Text");
		printTextBtn.setOnAction(e -> print(text));

		// Button to print the entire scene
		Button printSceneBtn = new Button("Print Scene");
		printSceneBtn.setOnAction(e -> print(root));

		HBox buttonBox = new HBox(5, printTextBtn, printSceneBtn);

		root.getChildren().addAll(textLbl, text, buttonBox);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Printing Nodes");
		stage.show();
	}

	private void print(Node node) {
		System.out.println("Creating a printer job...");

		try {
			Printer pdf = Printer.class.newInstance();
			System.out.println(pdf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//PrinterJob.showPrintDialog(stage);
		
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null) {
			System.out.println(job.jobStatusProperty().asString());

			boolean printed = job.printPage(node);
			if (printed) {
				job.endJob();
			} else {
				System.out.println("Printing failed.");
			}
		} else {
			System.out.println("Could not create a printer job.");
		}
	}
}