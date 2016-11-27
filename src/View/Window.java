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
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> chien_place;
	// boolean menu = true;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		player_place = new Pair<Double, Double>(100., 450.);
		chien_place = new Pair<Double, Double>(350., 550.);
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
		StartGame(root, scene);

	}

	private void StartGame(Group root, Scene scene) {
		root.getChildren().clear();
		scene.setFill(Color.RED);

		Vector<Card_View> cards = new Vector<Card_View>();
		int id_player = 0;
		int nb_carte = 0;
		for (int i = 0; i < 72; i++) {
			if (id_player == 0) {
				Card_View carte = new Card_View(nb_carte + 1);
				Double X = player_place.getKey() + ((nb_carte % 9) * (Card_View.W_CARD + 10));
				Double Y = player_place.getValue();
				if (nb_carte > 8) {
					Y += Card_View.H_CARD + 10;
				}

				carte.setObjective(new Pair<Double, Double>(X, Y));
				cards.add(carte);
				nb_carte++;
			}
			id_player = (id_player + 1) % 4;
		}
		Button btn = new Button();
		btn.setLayoutX(350);
		btn.setLayoutY(300);
		btn.setPrefSize(250, 100);
		btn.setText("Look your cards");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				lookCard(cards);
			}
		});
		for (Card_View cV : cards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(btn);

		loop_G = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (Card_View cV : cards) {
					if (cV.isArrived() == false) {
						cV.move();
						return;
					}
				}
			}
		};
		loop_G.start();

	}

	protected void lookCard(Vector<Card_View> cards) {
		for (Card_View cV : cards) {
			cV.flip().play();
		}
	}

	public void run() {
		launch();
	}

	public void setController(Controller c) {
		this.c = c;
	}

	public void distribAnim() {

	}
}
