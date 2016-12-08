package View;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ButtonView extends Button {

	final static String FONT_BUTTON_TEXT = "file:./ressources/font/Steampunk.otf";
	ButtonView(Double x, Double y, Double h, Double w, String text) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefSize(w, h);
		this.setText(text);
		this.setFont(Font.loadFont(FONT_BUTTON_TEXT,30.));
	}
	
}
