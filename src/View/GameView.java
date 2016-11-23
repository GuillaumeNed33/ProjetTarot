package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameView extends Window {

	public GameView()
	{
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		super.root = new Group();
		super.scene = new Scene(root, 1000, 750, Color.RED);
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("ok");
			}
		});
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
