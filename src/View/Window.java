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
		root = new Group();
		primaryStage.setScene(new Menu(root,this));
		primaryStage.show();
	}

	public void run() {
		launch();
	}

	public void setController(Controller c)
	{
		control =c;
	}

	public void changeToGameView() {
		System.out.println("Ici je suis dans la merde en fait MDR !! ");
		System.out.println("Faut faire un truc avec le primary stage je pense.");
	}
}
