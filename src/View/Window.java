package View;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Window extends Application implements Observer {

	protected String title;
	protected Controller c;
	private AnimationTimer loop_G;
	
	private HashMap<Card, Card_View>player_cards;
	private HashMap<Card, Card_View>chien_cards;

	private Vector<Card_View> playerCards;
	private Vector<Card_View> chienCards;
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> player_place2;
	private Pair<Double, Double> player_place3;
	private Pair<Double, Double> player_place4;

	private Pair<Double, Double> chien_place;
	private Data data;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		data = new Data();
		player_place = new Pair<Double, Double>(244., 450.);
		player_place2 = new Pair<Double, Double>(-200., 300.);
		player_place3 = new Pair<Double, Double>(460., -200.);
		player_place4 = new Pair<Double, Double>(1200., 300.);

		chien_place = new Pair<Double, Double>(525., 30.);
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
		Game m_tmp = new Game();
		m_tmp.addObserver(this);
		m_tmp.initGame();
		root.getChildren().clear();
		scene.setFill(Color.RED);
		
		Vector<Card_View> cards = new Vector<Card_View>();
		chienCards = new Vector<Card_View>();
		playerCards = new Vector<Card_View>();
		
		player_cards = new HashMap<Card, Card_View>();
		chien_cards = new HashMap<Card, Card_View>();
		
		animeDistrib(cards);
		
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
				loop_G.stop();
				loop_G = new AnimationTimer() {
					@Override
					public void handle(long now) {
						for (Card_View cV : cards) {
							cV.move();
						}
					}
				};
				loop_G.start();
			}
		});
		for (Card_View cV : cards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(btn);
		root.getChildren().add(btnTri);

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

	private void animeDistrib(Vector<Card_View> cards) {
		int id_player = 0;
		int nb_carte = 0;
		int nbChienCards = 0;

		for (int i = 0; i < 78; i++) {
			Card_View carte = new Card_View();
			Double X = 0.;
			Double Y = 0.;

			switch (id_player) {
			case 0:
				for (int j = 0; j < 3; j++) {
					carte = new Card_View();
					X = player_place.getKey() + ((nb_carte % 9) * (Card_View.W_CARD + 10));
					Y = player_place.getValue();

					if (nb_carte > 8) {
						Y += Card_View.H_CARD + 10;
					}
					carte.setObjective(new Pair<Double, Double>(X, Y));
					cards.add(carte);
					playerCards.add(carte);

					nb_carte++;
					i++;
				}
				i--;
				break;

			case 1:
				for (int j = 0; j < 3; j++) {
					carte = new Card_View();
					X = player_place2.getKey();
					Y = player_place2.getValue();
					carte.setObjective(new Pair<Double, Double>(X, Y));
					cards.add(carte);
					i++;
				}
				i--;

				break;
			case 4:
				for (int j = 0; j < 3; j++) {
					carte = new Card_View();
					X = player_place3.getKey();
					Y = player_place3.getValue();
					carte.setObjective(new Pair<Double, Double>(X, Y));
					cards.add(carte);
					i++;
				}
				i--;

				break;
			case 3:
				for (int j = 0; j < 3; j++) {
					carte = new Card_View();
					X = player_place4.getKey();
					Y = player_place4.getValue();
					carte.setObjective(new Pair<Double, Double>(X, Y));
					cards.add(carte);
					i++;
				}
				i--;
				break;
			case 2:
				if (nbChienCards < 6) {
					carte = new Card_View();
					X = chien_place.getKey() + (nbChienCards * (Card_View.W_CARD + 10));
					Y = chien_place.getValue();
					carte.setObjective(new Pair<Double, Double>(X, Y));
					chienCards.add(carte);
					cards.add(carte);
					nbChienCards++;
				} else
					i--;
				break;

			default:
				break;
			}
			id_player = (id_player + 1) % 5;
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
		if (o instanceof Game) {
			if (ob instanceof Player) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for (Card c : ((Player) ob).getHand().getGame()) {
							for (Card_View cV : playerCards) {
								if (!cV.isValueSet()) {
									cV.identify(data.getImage(c.getType(), c.getValue()));
									break;
								}
							}
						}
					}
				});
			} else if (ob instanceof Chien) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for (Card c : ((Chien) ob).getCards()) {
							for (Card_View cV : chienCards) {
								if (!cV.isValueSet()) {
									cV.identify(data.getImage(c.getType(), c.getValue()));
									break;
								}
							}
						}
					}
				});
			} else if (ob instanceof Hand) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						int i = 0;
						for (Card c : ((Hand) ob).getGame()) {
							for (Card_View cV : playerCards) {
								if (cV.getImageName().equalsIgnoreCase(data.getImage(c.getType(), c.getValue()))) {
									Double X = player_place.getKey() + ((i % 9) * (Card_View.W_CARD + 10));
									Double Y = player_place.getValue();

									if (i > 8) {
										Y += Card_View.H_CARD + 10;
									}
									cV.setObjective(new Pair<Double, Double>(X, Y));
								}
							}
							i++;
						}
					}
				});
			}
		}
	}
}
