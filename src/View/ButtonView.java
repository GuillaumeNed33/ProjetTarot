package View;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *  <i> <b> ButtonView </b> </i><br>
 *  
 * Classe permettant la construction d'un bouton. H�rite de la classe {@link Button} de JavaFX.
 */
public class ButtonView extends Button {

	final static String FONT_BUTTON_TEXT = "file:./ressources/font/Steampunk.otf";
	ButtonView(Double x, Double y, Double h, Double w, String text, Double size) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefSize(w, h);
		this.setText(text);
		this.setTextAlignment(TextAlignment.CENTER);
		this.setFont(Font.loadFont(FONT_BUTTON_TEXT,size));		
	}
}
