package View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import Controler.Controller;
import Model.Card;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class Window extends Application implements Observer {

	final static Double WIDTH = 1280.;
	final static Double HEIGHT = 720.;

	private String title;
	private static Controller c;
	private ArrayList<Card_View> allCards;
	private Vector<Card_View> playerCards;
	private Vector<Card_View> chienCards;
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> player_place2;
	private Pair<Double, Double> player_place3;
	private Pair<Double, Double> player_place4;
	private Pair<Double, Double> chien_place;
	private Pair<Double, Double> chien_player_place;
	private Rectangle dropTarget;
	Vector<Button> choices;
	public static Data data;

	static String imageMenu = "file:./ressources/img/background.jpg";
	static String imageGame = "file:./ressources/img/background2.jpg";

	Group root;
	Scene scene;

	private ImageView background = new ImageView();

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		data = new Data();
		allCards = new ArrayList<Card_View>();
		player_place = new Pair<Double, Double>(150., 450.);
		player_place2 = new Pair<Double, Double>(-200., 300.);
		player_place3 = new Pair<Double, Double>(460., -200.);
		player_place4 = new Pair<Double, Double>(1400., 300.);
		chien_place = new Pair<Double, Double>(500., 45.);
		chien_player_place = new Pair<Double, Double>(1100., 500.);
		background.setFitHeight(HEIGHT);
		background.setFitWidth(WIDTH);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT, null);
		primaryStage.setTitle(title);
		allCards = new ArrayList<Card_View>();
		dropTarget = new Rectangle(800, 50, 300, 300);
		dropTarget.getStrokeDashArray().add(10.);
		dropTarget.setStrokeLineJoin(StrokeLineJoin.ROUND);
		dropTarget.setStrokeLineCap(StrokeLineCap.ROUND);
		dropTarget.setStrokeWidth(5.);
		dropTarget.setFill(Color.TRANSPARENT);
		dropTarget.setStroke(Color.BLACK);
		for (int i = 0; i < 78; i++) {
			allCards.add(new Card_View());
		}
		LoadMenu(root, scene);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void LoadMenu(Group root, Scene scene) {
		background.setImage(new Image(imageMenu));
		root.getChildren().add(background);

		Text mainTitle = new Text(120, 200, "SteamPunk Tarot");
		mainTitle.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 125.));
		root.getChildren().add(mainTitle);

		Button btn = new Button();
		btn.setLayoutX(500);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				StartGame(root, scene);
			}
		});
		root.getChildren().add(btn);

		Button btnQuit = new Button();
		btnQuit.setLayoutX(500);
		btnQuit.setLayoutY(500);
		btnQuit.setPrefSize(250, 50);
		btnQuit.setText("QUIT");
		btnQuit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		root.getChildren().add(btnQuit);
	}

	private void StartGame(Group root, Scene scene) {
		root.getChildren().clear();
		background.setImage(new Image(imageGame));
		root.getChildren().add(background);
		c.syncCards(allCards, this);
		c.startGame();
		c.distrib();
		chienCards = new Vector<Card_View>();
		playerCards = new Vector<Card_View>();

		choices = new Vector<Button>();
		for (int i = 0; i < 5; i++) {
			Button btn = new Button();
			btn.setLayoutX(175 * i + 100);
			btn.setLayoutY(10);
			btn.setPrefSize(150, 25);
			btn.setVisible(false);
			switch (i) {
			case 0:
				btn.setText("La prise");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						SequentialTransition look = lookCard(chienCards);
						look.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								SequentialTransition theGard = new SequentialTransition();
								theGard.getChildren().addAll(goToMyHand(), triCardsView());
								theGard.play();
								theGard.setOnFinished(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										constituteShift();
									}
								});
							}
						});
						look.play();
					}
				});
				break;
			case 1:
				btn.setText("La garde");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						SequentialTransition look = lookCard(chienCards);
						look.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								SequentialTransition theGard = new SequentialTransition();
								theGard.getChildren().addAll(goToMyHand(), triCardsView());
								theGard.play();
							}
						});
						look.play();
						allowDragAndDrop();
					}
				});
				break;
			case 2:
				btn.setText("La garde sans le chien");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						gardAgainstChien().play();
					}

				});
				break;
			case 3:
				btn.setText("La garde contre le chien");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						goToEnemyAnim().play();
					}

				});
				break;
			case 4:
				btn.setText("Passe");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						lookCard(chienCards).play();
					}
				});
				break;
			}
			choices.add(btn);
		}
		for (Card_View cV : allCards) {
			root.getChildren().addAll(cV.getNodes());
		}
		for (Button b : choices) {
			root.getChildren().add(b);
		}

		SequentialTransition masterDistrib = new SequentialTransition();
		masterDistrib.getChildren().addAll(animeDistrib(), lookCard(playerCards));

		masterDistrib.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				c.triCards();
				triCardsView().play();
				if (c.testPetitSec()) {
					Text info = new Text(130, 415, "Le Petit est sec !");
					info.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 100.));
					root.getChildren().add(info);
					try {
						Thread.sleep(1500L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					backToCenter().play();
					c.resetGame();
 
				} else {
					for (Button b : choices) {
						b.setVisible(true);
					}

				}
			}
		});
		masterDistrib.play();

	}

	private ParallelTransition backToCenter() {
		for (Card_View cV : allCards) {
			cV.setObjective(new Pair<Double, Double>(Card_View.START_X, Card_View.START_Y));
		}
		ParallelTransition master = moveCardsToObjParal();
		master.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				StartGame(root, scene);
			}
		});
		return master;
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
		int i = 0;
		while (i < allCards.size()) {
			if (allCards.get(i).getIdOwner() != 5) {
				ParallelTransition cards = new ParallelTransition();
				for (int j = 0; j < 3; j++) {
					cards.getChildren().add(allCards.get(i + j).moveAnimation());
				}
				i += 3;
				master.getChildren().add(cards);
			} else {
				master.getChildren().add(allCards.get(i).moveAnimation());
				i++;
			}
		}
		return master;
	}

	public ParallelTransition moveCardsToObjParal() {
		ParallelTransition master = new ParallelTransition();
		for (Card_View cV : allCards) {
			master.getChildren().add(cV.moveAnimation());
		}
		return master;
	}

	protected SequentialTransition lookCard(Vector<Card_View> cards) {
		SequentialTransition master = new SequentialTransition();
		for (Card_View cV : cards) {
			master.getChildren().add(cV.flip());
		}
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

	private ParallelTransition triCardsView() {
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
			playerCards.get(i).setToFrontCard();
		}
		return moveCardsToObjParal();
	}

	private ParallelTransition goToEnemyAnim() {
		for (Card_View cV : chienCards) {
			cV.setObjective(player_place3);
		}
		return moveCardsToObjParal();
	}

	private ParallelTransition gardAgainstChien() {
		for (Card_View cV : chienCards) {
			cV.setObjective(chien_player_place);
		}
		return moveCardsToObjParal();
	}

	private ParallelTransition goToMyHand() {
		Double X = player_place.getKey() + playerCards.size() * (Card_View.W_CARD / 2);
		Double Y = player_place.getValue();
		for (Card_View cV : chienCards) {
			cV.setObjective(new Pair<Double, Double>(X, Y));
			X += Card_View.W_CARD / 2;
			playerCards.add(cV);

		}
		return moveCardsToObjParal();
	}

	/**
	 * 
	 */
	private void allowDragAndDrop() {
		for (Card_View cV : playerCards) {
			System.out.println(cV.getId());
			if (!(cV.getId() == 13 || cV.getId() == 27 || cV.getId() == 28 || cV.getId() == 29 || cV.getId() == 30
					|| cV.getId() == 49 || cV.getId() == 63 || cV.getId() == 77)) {
				cV.openDragAndDrop(dropTarget);
			}
			cV.blockZoom();
		}
	}

	/**
	 * 
	 */
	public void constituteShift() {
		root.getChildren().clear();
		initSceneToConstituteShift();
		ParallelTransition move = setPosCardsForShift();
		move.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				allowDragAndDrop();
			}
		});
		move.play();
	}

	private ParallelTransition setPosCardsForShift() {
		Double firstCard_X = 50.;
		Double firstCard_Y = 150.; 
		for(int i=0;i<playerCards.size();i++) {
			playerCards.get(i).setObjective(new Pair<Double, Double>((i%8)*(Card_View.W_CARD+10)+ firstCard_X,
					(Math.floorDiv(i, 8)*(Card_View.H_CARD+10)) + firstCard_Y));
		}
		return moveCardsToObjParal();
	}

	private void initSceneToConstituteShift() {
		Text title = new Text(100., 100., "Constitute the shift.");
		title.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 50.));
		Button btn = new Button("Confirm");
		btn.autosize();
		btn.setLayoutX(900.);
		btn.setLayoutY(600.);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				System.out.println("fini");
			}
		});
		root.getChildren().add(background);
		root.getChildren().add(dropTarget);
		for (Card_View cV : playerCards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(title);
		root.getChildren().add(btn);
	}
}
