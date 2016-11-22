package View;

import Controler.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	private String title;
	private Controller control;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(title);
		Group root = new Group();
		Scene scene = new Scene(root, 1200, 900, Color.GREEN);
		Button btn = new Button();
		btn.setLayoutX(480);
		btn.setLayoutY(400);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("Hello World");
			}
		});
		
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void run(String[] args) {
		launch(args);
	}
	
	public void setController(Controller c)
	{
		control =c;
	}
}
