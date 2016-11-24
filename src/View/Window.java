package View;

import Controler.Controller;
import Model.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	protected String title;
	protected Controller control;

	boolean menu = true;
	
	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 750, Color.GREEN);
		primaryStage.setTitle(title);
		
		LoadMenu(root, scene);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void LoadMenu(Group root, Scene scene) {
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				StartGame(root, scene);
			}
		});
		root.getChildren().add(btn);		
	}
	
	private void StartGame(Group root, Scene scene) {
		root.getChildren().clear();
		//control.startGame();
		Game g = new Game();
		g.initGame();
		scene.setFill(Color.RED);
		
        Card_View carte = new Card_View();
        carte.setX(10);
        carte.setY(50);
        
        Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("Change cote");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				carte.flip().play();
		        carte.identify("2Coeur.jpg");
			}
		});
        
        root.getChildren().addAll(carte.getNodes());
        root.getChildren().add(btn);
	}

	public void run() {
		launch();
	}

	public void setController(Controller c)
	{
		control =c;
	}
}
