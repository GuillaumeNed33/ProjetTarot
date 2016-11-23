package View;

import Controler.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	protected String title;
	protected Controller control;
	protected Scene scene;
	protected Group root;
	Image image;
	ImageView carte;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(title);	
		Menu menu = new Menu(primaryStage);
		menu.run();
		
		/*root = new Group();
		scene = new Scene(root, 1000, 750, Color.GREEN);
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//GameView game = new GameView(primaryStage, Mainargs);
			}
		});
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.show();*/
	}

	public void run() {
		launch();
	}

	public void setController(Controller c)
	{
		control =c;
	}
}
