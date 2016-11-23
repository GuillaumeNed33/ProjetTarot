package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends Scene {

	private Window mother;
	public Menu(Group root, Window mother)
	{
		super(root, 1000, 750, Color.GREEN);
		this.mother = mother;
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				mother.changeToGameView();
			}
		});
		root.getChildren().add(btn);
	}

}
