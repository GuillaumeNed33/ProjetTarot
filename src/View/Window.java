package View;

import Controler.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	private String title;
	private Controller control;
	private Scene scene;
	private Group root;


	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle(title);			
		menu(primaryStage);				
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void menu(Stage primaryStage)
	{
		root = new Group();
		scene = new Scene(root, 1000, 750, Color.GREEN);
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				game(primaryStage);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		});
		root.getChildren().add(btn);
	}

	public void game(Stage primaryStage)
	{
		root = new Group();
		scene = new Scene(root, 1000, 750, Color.RED);
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("playing");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("Hello world");
			}
		});
		root.getChildren().add(btn);
	}

	public void run(String[] args) {
		launch(args);
	}

	public void setController(Controller c)
	{
		control =c;
	}
}
