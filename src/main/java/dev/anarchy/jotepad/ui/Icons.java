package dev.anarchy.jotepad.ui;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public enum Icons {
	FILE_NEW(FontAwesomeSolid.FILE, ColorExt.BLUE, FontAwesomeSolid.PLUS, ColorExt.YELLOW),
	OPEN(FontAwesomeSolid.FOLDER_OPEN, ColorExt.YELLOW),
	SAVE(FontAwesomeSolid.SAVE, ColorExt.BLUE),
	SAVE_MODIFIED(FontAwesomeSolid.SAVE, ColorExt.RED),
	SAVE_ALL(FontAwesomeSolid.SAVE, ColorExt.BLUE, FontAwesomeSolid.SAVE, ColorExt.BLUE),
	;
	
	private Ikon icon;
	private Ikon subIcon;
	private Color primaryColor;
	private Color secondaryColor;
	private Pos alignment;

	Icons(Ikon icon) {
		this(icon, (Ikon)null);
	}

	Icons(Ikon icon, Color primaryColor) {
		this(icon, primaryColor, null, null);
	}
	
	Icons(Ikon icon, Ikon subIcon) {
		this(icon, subIcon, Color.BLUE);
	}
	
	Icons(Ikon icon, Ikon subIcon, Color secondaryColor) {
		this(icon, Color.BLACK, subIcon, secondaryColor);
	}
	
	Icons(Ikon icon, Color primaryColor, Ikon subIcon, Color secondaryColor) {
		this(icon, primaryColor, subIcon, secondaryColor, Pos.BOTTOM_RIGHT);
	}
	
	Icons(Ikon icon, Color primaryColor, Ikon subIcon, Color secondaryColor, Pos alignment) {
		this.icon = icon;
		this.subIcon = subIcon;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.alignment = alignment;
	}

	public Node get() {
		return get(24);
	}
	
	public Node get(int size) {
		Icon icon = new Icon(new FontIcon(this.icon), primaryColor);
		icon.setSecondaryGraphicAlignment(alignment);
		icon.setSize(size);
		
		if ( this.subIcon != null ) {
			icon.setSecondaryGraphic(new FontIcon(this.subIcon));
			icon.setSecondaryFill(secondaryColor);
		}
		
		return icon;
	}
}
