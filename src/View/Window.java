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
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Window extends Application implements Observer {

	protected String title;
	protected Controller c;
	private AnimationTimer loop_G;

	private ArrayList<Card_View> allCards;
	private Vector<Card_View> playerCards;
	private Vector<Card_View> chienCards;
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> player_place2;
	private Pair<Double, Double> player_place3;
	private Pair<Double, Double> player_place4;

	private Pair<Double, Double> chien_place;
	public static Data data;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		data = new Data();
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
		root.setId("root");
		primaryStage.setTitle(title);
		
		scene.getStylesheets().add(Window.class.getResource("application.css").toExternalForm());
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
		//scene.getStylesheets().add(Window.class.getResource("application.css").toExternalForm());

		root.getChildren().add(btn);
	}

	private void StartGame(Group root, Scene scene) {
		allCards = new ArrayList<Card_View>();
		for (int i = 0; i < 78; i++) {
			allCards.add(new Card_View());
		}
		Game m_tmp = new Game();
		m_tmp.addCardObserver(allCards);
		m_tmp.addObserver(this);
		m_tmp.initGame();

		root.getChildren().clear();
		scene.setFill(Color.RED);

		chienCards = new Vector<Card_View>();
		playerCards = new Vector<Card_View>();
		animeDistrib();

		Button btnTri = new Button();
		btnTri.setLayoutX(700);
		btnTri.setLayoutY(660);
		btnTri.setPrefSize(100, 30);
		btnTri.setText("TRI");
		btnTri.setVisible(false);

		Button btn = new Button();
		btn.setLayoutX(400);
		btn.setLayoutY(660);
		btn.setPrefSize(150, 30);
		btn.setText("Look your cards");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				lookCard(playerCards);
				lookCard(chienCards);
				btnTri.setVisible(true);
			}
		});
		btnTri.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				m_tmp.triCards();
				triCardsView();
			}
		});
		for (Card_View cV : allCards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(btn);
		root.getChildren().add(btnTri);

		loop_G = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (Card_View cV : allCards) {
					if (cV.isArrived() == false) {
						cV.move();
						return;
					}
				}/*
				for(int i=0;i<playerCards.size();i++) {
					playerCards.get(i).actualiseRotate(i);
				}
				*/
				this.stop();
			}
		};
		loop_G.start();

	}

	private void animeDistrib() {
		int nb_carte = 0;
		int nbChienCards = 0;

		for (Card_View cV : allCards) {
			Double X = 0.;
			Double Y = 0.;
			switch (cV.getIdOwner()) {
			case 1:
				X = player_place.getKey() + (nb_carte%9  * (Card_View.W_CARD+10));
				Y = player_place.getValue();
				if (nb_carte > 8) {
					Y += Card_View.H_CARD + 10;
				}
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


	}

	protected void lookCard(Vector<Card_View> cards) {
		SequentialTransition master = new SequentialTransition();
		for (Card_View cV : cards) {
			master.getChildren().add(cV.flip());
			cV.setFrontVisible(true);
		}
		master.play();
	}

	public void run() {
		launch();
	}

	public void setController(Controller c) {
		this.c = c;
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
			Double X = player_place.getKey() + ((i % 9) * (Card_View.W_CARD + 10));
			Double Y = player_place.getValue();
			if (i > 8) {
				Y += Card_View.H_CARD + 10;
			}
			playerCards.get(i).setObjective(new Pair<Double,Double>(X,Y));
		}
	}
}
