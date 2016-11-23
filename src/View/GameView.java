package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameView extends Scene {

	public GameView(Group root, Stage fenetre)
	{
		super(root, 1000, 750, Color.RED);
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("en Jeu");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//GameView game = new GameView(primaryStage, Mainargs);
			}
		});
		root.getChildren().add(btn);
	}


}
