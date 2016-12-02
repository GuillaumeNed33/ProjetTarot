package View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import Controler.Controller;
import Model.Card;
import Model.Chien;
import Model.Game;
import Model.Hand;
import Model.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.shape.*;

public class Window extends Application implements Observer {

	protected String title;
	private static Controller c;
	private ArrayList<Card_View> allCards;
	private Vector<Card_View> playerCards;
	private Vector<Card_View> chienCards;
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> player_place2;
	private Pair<Double, Double> player_place3;
	private Pair<Double, Double> player_place4;
	private Pair<Double, Double> chien_place;
	Button btnLookCards;
	Button btnTriCards;
	public static Data data;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		data = new Data();
		allCards = new ArrayList<Card_View>();
		player_place = new Pair<Double, Double>(150., 450.);
		player_place2 = new Pair<Double, Double>(-200., 300.);
		player_place3 = new Pair<Double, Double>(460., -200.);
		player_place4 = new Pair<Double, Double>(1200., 300.);
		chien_place = new Pair<Double, Double>(525., 30.);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 700, null);
		scene.getStylesheets().add(Window.class.getResource("application.css").toExternalForm());
		root.setId("root");
		root.getStyleClass().add("my_root");

		Rectangle rect = new Rectangle(1000, 700);
		rect.setLayoutY(0);
		rect.setLayoutX(0);
		rect.getStyleClass().add("my-rect");

		root.getChildren().add(rect);
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
		allCards = new ArrayList<Card_View>();
		for (int i = 0; i < 78; i++) {
			allCards.add(new Card_View());
		}
		c.syncCards(allCards);
		c.startGame();
		root.getChildren().clear();
		scene.setFill(Color.RED);
		c.distrib();
		chienCards = new Vector<Card_View>();
		playerCards = new Vector<Card_View>();
		animeDistrib().play();
		btnTriCards = new Button();
		btnTriCards.setLayoutX(700);
		btnTriCards.setLayoutY(660);
		btnTriCards.setPrefSize(100, 30);
		btnTriCards.setText("TRI");
		btnTriCards.setVisible(false);

		btnLookCards = new Button();
		btnLookCards.setLayoutX(400);
		btnLookCards.setLayoutY(660);
		btnLookCards.setPrefSize(150, 30);
		btnLookCards.setText("Look your cards");
		btnLookCards.setVisible(false);
		btnLookCards.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				lookCard(playerCards).play();
			}
		});
		btnTriCards.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				c.triCards();
				triCardsView();
			}
		});
		for (Card_View cV : allCards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(btnLookCards);
		root.getChildren().add(btnTriCards);

	}

	private SequentialTransition animeDistrib() {
		int nb_carte = 0;
		int nbChienCards = 0;

		for (Card_View cV : allCards) {
			Double X = 0.;
			Double Y = 0.;
			switch (cV.getIdOwner()) {
			case 1:
				X = player_place.getKey() + (nb_carte * (Card_View.W_CARD / 2));
				Y = player_place.getValue();
				cV.setObjective(new Pair<Double, Double>(X, Y));
				playerCards.add(cV);
				nb_carte++;
				break;
			case 2:
				X = player_place2.getKey();
				Y = player_place2.getValue();
				cV.setObjective(new Pair<Double, Double>(X, Y));
				break;
			case 3:
				X = player_place3.getKey();
				Y = player_place3.getValue();
				cV.setObjective(new Pair<Double, Double>(X, Y));
				break;
			case 4:
				X = player_place4.getKey();
				Y = player_place4.getValue();
				cV.setObjective(new Pair<Double, Double>(X, Y));
				break;
			case 5:
				X = chien_place.getKey() + (nbChienCards * (Card_View.W_CARD + 10));
				Y = chien_place.getValue();
				cV.setObjective(new Pair<Double, Double>(X, Y));
				chienCards.add(cV);
				nbChienCards++;
				break;
			default:
				break;
			}
		}
		return moveCardsToObjSeq();
	}

	public SequentialTransition moveCardsToObjSeq() {
		SequentialTransition master = new SequentialTransition();
		for (Card_View cV : allCards) {
			master.getChildren().add(cV.moveAnimation());
		}
		master.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				btnLookCards.setVisible(true);
			}
		});
		return master;
	}

	public void moveCardsToObjParal() {
		ParallelTransition master = new ParallelTransition();
		for (Card_View cV : allCards) {
			master.getChildren().add(cV.moveAnimation());
		}
		master.play();
	}

	protected SequentialTransition lookCard(Vector<Card_View> cards) {
		SequentialTransition master = new SequentialTransition();
		for (Card_View cV : cards) {
			master.getChildren().add(cV.flip());
			cV.setFrontVisible(true);
		}
		master.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				btnTriCards.setVisible(true);
				btnLookCards.setVisible(false);
			}
		});
		return master;
	}

	public void run() {
		launch();
	}

	public void setController(Controller c) {
		Window.c = c;
	}

	@Override
	public void update(Observable o, Object ob) {
		if (ob instanceof ArrayList) {
			ArrayList<Card_View> tmp = new ArrayList<Card_View>();
			for (int i = 0; i < ((ArrayList<?>) ob).size(); i++) {
				
				tmp.add(allCards.get(((Card) ((ArrayList<?>) ob).get(i)).getId()));
			}
			allCards = tmp;
		}
	}

	private void triCardsView() {
		playerCards.sort(new Comparator<Card_View>() {
			@Override
			public int compare(Card_View cv1, Card_View cv2) {
				return cv1.getId() - cv2.getId();
			}
		});
		for (int i = 0; i < playerCards.size(); i++) {
			Double X = player_place.getKey() + (i * (Card_View.W_CARD / 2));
			Double Y = player_place.getValue();
			playerCards.get(i).setObjective(new Pair<Double, Double>(X, Y));
			playerCards.get(i).getFrontCard().toBack();
		}
		this.moveCardsToObjParal();
	}
}
