package dev.anarchy.jotepad.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Icon extends Parent {
	
	private StackPane root;
	
	private DoubleProperty sizeProperty;
	
	private static final double DEFAULT_SIZE = 24;
	
	private FontIcon primaryGraphic;
	
	private Color primaryFill;
	
	private FontIcon secondaryGraphic;
	
	private Color secondaryFill;
	
	private Pos secondaryGraphicAlignment;
	
	private ColorAdjust colorAdjust;
	
	public Icon() {
		this(null);
	}
	
	public Icon(FontIcon primaryGraphic) {
		this(primaryGraphic, Color.BLACK);
	}
	
	public Icon(FontIcon primaryGraphic, Color primaryFill) {
		this.sizeProperty = new SimpleDoubleProperty(DEFAULT_SIZE);
		
		this.primaryGraphic = primaryGraphic;
		this.primaryFill = primaryFill;
		this.secondaryFill = Color.BLACK;
		this.secondaryGraphicAlignment = Pos.BOTTOM_RIGHT;

		this.root = new StackPane();
		this.root.prefWidthProperty().bind(sizeProperty);
		this.root.prefHeightProperty().bind(sizeProperty);
		this.root.maxWidthProperty().bind(sizeProperty);
		this.root.maxHeightProperty().bind(sizeProperty);
		this.root.setAlignment(Pos.CENTER);
		this.getChildren().add(root);
		
		// Color adjust when clicked
		colorAdjust = new ColorAdjust();
		root.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> colorAdjust.setBrightness(-0.25) );
		root.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> colorAdjust.setBrightness(0.0) );
		
		// Fade when disable
		this.disableProperty().addListener((node, oldValue, newValue) -> {
			colorAdjust.setSaturation(newValue ? -1.0 : 0);
			colorAdjust.setBrightness(newValue ? -0.1 : 0);
		});
		
		this.rebuild();
	}
	
	public void setPrimaryGraphic(FontIcon node) {
		this.primaryGraphic = node;
		this.rebuild();
	}
	
	public void setSecondaryGraphic(FontIcon node) {
		this.secondaryGraphic = node;
		this.rebuild();
	}
	
	public void setSecondaryGraphicAlignment(Pos pos) {
		this.secondaryGraphicAlignment = pos;
		this.rebuild();
	}
	
	public void setPrimaryFill(Color color) {
		this.primaryFill = color;
		this.rebuild();
	}
	
	public void setSecondaryFill(Color color) {
		this.secondaryFill = color;
		this.rebuild();
	}
	
	public void setSize(double size) {
		this.sizeProperty.set(size);
	}
	
	public double getSize() {
		return this.sizeProperty.get();
	}

	protected void rebuild() {
		this.root.getChildren().clear();
		
		if ( primaryGraphic == null )
			return;
		
		primaryGraphic.setMouseTransparent(true);
		primaryGraphic.setIconSize((int) (getSize() * 0.75f));
		primaryGraphic.setIconColor(primaryFill);
		primaryGraphic.setEffect(colorAdjust);
		this.root.getChildren().add(primaryGraphic);

		if ( secondaryGraphic != null ) {
			// Subicon alignment pane
			StackPane pane = new StackPane();
			pane.setMouseTransparent(true);
			pane.setAlignment(secondaryGraphicAlignment);
			this.root.getChildren().add(pane);
	
			// Outline
			DropShadow outerFocus = new DropShadow();
			outerFocus.setColor(Color.WHITE);
			outerFocus.setBlurType(BlurType.ONE_PASS_BOX);
			outerFocus.setRadius(2.0);
			outerFocus.setSpread(2.0);
			outerFocus.setOffsetX(0);
			outerFocus.setOffsetY(0);
			pane.setEffect(outerFocus);
			
			// Subicon
			secondaryGraphic.setIconSize((int) (getSize() * 0.5));
			secondaryGraphic.setIconColor(secondaryFill);
			secondaryGraphic.setEffect(colorAdjust);
			pane.getChildren().add(secondaryGraphic);
		}
	}
}
