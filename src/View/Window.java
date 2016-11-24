package View;

import java.util.Vector;

import Controler.Controller;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Window extends Application {

	protected String title;
	protected Controller c;
	private AnimationTimer loop_G;
	private Vector<Pair<Double,Double>> players_place;
	boolean menu = true;
	
	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		players_place = new Vector<Pair<Double,Double>>();
		initPlayerPlace();
	}
	
	private void initPlayerPlace() {
		players_place.add(new Pair<Double,Double>(500.,0.));
		players_place.add(new Pair<Double,Double>(0.,350.));
		players_place.add(new Pair<Double,Double>(500.,1000.));
		players_place.add(new Pair<Double,Double>(700.,350.));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 700, Color.GREEN);
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
		scene.setFill(Color.RED);
		
		Vector<Card_View> cards = new Vector<Card_View>();
		int id_player=0;
		for(int i=0 ; i<78; i++) {
			Card_View carte = new Card_View();
			carte.setObjective(players_place.get(id_player));
			cards.add(carte);
			id_player=(id_player+1)%4;
		}
        loop_G = new AnimationTimer() {
			@Override
            public void handle(long now) {
				for (Card_View cV : cards) {
					if(cV.isArrived() == false) {
						cV.move();
						return;
					}
				}
			}
		};
		for (Card_View cV : cards) {
	        root.getChildren().addAll(cV.getNodes());
		}
		loop_G.start();
	}

	public void run() {
		launch();
	}

	public void setController(Controller c)
	{
		this.c =c;
	}
	
	public void distribAnim() {
		
	}
}
