package View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import Controler.Controller;
import Model.Card;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Window extends Application implements Observer {

	final static Double WIDTH = 1280.;
	final static Double HEIGHT = 720.;
	private static final long HalfDurationMove = 400;
	private static final long HalfDurationShuffle = 1000;

	private String title;
	private static Controller c;
	private ArrayList<Card_View> allCards;
	private ArrayList<Card_View> playerCards;
	private ArrayList<Card_View> chienCards;
	private Pair<Double, Double> player_place;
	private Pair<Double, Double> player_place2;
	private Pair<Double, Double> player_place3;
	private Pair<Double, Double> player_place4;
	private Pair<Double, Double> chien_place;
	private Pair<Double, Double> chien_player_place;
	private Rectangle dropTarget;
	Vector<Button> choices;
	public static Data data = new Data();
	static String imageMenu = "file:./ressources/img/background.jpg";
	static String imageGame = "file:./ressources/img/background2.jpg";

	Group root;
	Scene scene;

	private ImageView background = new ImageView();
	ArrayList<Integer> NoAuthorize;

	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		allCards = new ArrayList<Card_View>();
		player_place = new Pair<Double, Double>(275., 450.);
		player_place2 = new Pair<Double, Double>(-200., 300.);
		player_place3 = new Pair<Double, Double>(600., -200.);
		player_place4 = new Pair<Double, Double>(1400., 300.);
		chien_place = new Pair<Double, Double>(735., 50.);
		chien_player_place = new Pair<Double, Double>(1100., 500.);
		background.setFitHeight(HEIGHT+10);
		background.setFitWidth(WIDTH+10);

		NoAuthorize = new ArrayList<Integer>();// Tableau des Id non autorisés à
		// mettre dans le chien
		for (int i = 30; i <= 48; i++) // Id des Atouts sans les bous
			NoAuthorize.add(i);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		

		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT, null);
		scene.getStylesheets().add("file:./ressources/style.css");
		initSceneWindow();
		
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
	    primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void initSceneWindow() {
		allCards = new ArrayList<Card_View>();

		for (int i = 0; i < 78; i++) {
			allCards.add(new Card_View());
		}
		c.syncCards(allCards, this);
		c.startGame();

		LoadMenu(root, scene);
	}

	private void LoadMenu(Group root, Scene scene) {
		root.getChildren().clear();
		background.setImage(new Image(imageMenu));
		root.getChildren().add(background);

		Text mainTitle = new Text(220, 220, "SteamPunk Tarot");
		mainTitle.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 125.));
		root.getChildren().add(mainTitle);

		addButtonToMenu();
	}

	private void addButtonToMenu() {
		Button btn = new Button();
		btn.setLayoutX(500);
		btn.setLayoutY(350);
		btn.setPrefSize(250, 100);
		btn.setText("LET'S PLAY TAROT !");
		btn.getStyleClass().add("buttonMenu");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				StartGame(root, scene);
				event.consume();
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
				event.consume();
				System.exit(0);
			}
		});
		root.getChildren().add(btnQuit);
	}

	private void StartGame(Group root, Scene scene) {
		root.getChildren().clear();
		background.setImage(new Image(imageGame));
		root.getChildren().add(background);

		ParallelTransition shuffle = goAway();
		shuffle.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ParallelTransition back = comeBack();
				back.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						distribCard();
					}
				});
				back.play();
			}
		});
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				shuffle.play();
			}
		});

		c.distrib();
		chienCards = new ArrayList<Card_View>();
		playerCards = new ArrayList<Card_View>();
		addChoicesButtons();

		for (Card_View cV : allCards) {
			root.getChildren().addAll(cV.getNodes());
		}
	}

	private void distribCard() {
		SequentialTransition masterDistrib = new SequentialTransition();
		masterDistrib.getChildren().addAll(animeDistrib(), lookCard(playerCards));
		masterDistrib.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				c.triCards();
				triCardsView().play();

				if (c.testPetitSec()) {
					resetGame();
				} else {
					for (Button b : choices) {
						b.setVisible(true);
					}
				}
			}
		});
		masterDistrib.play();
	}

	private void resetGame() {
		Text info = new Text(130, 415, "Le Petit est sec !");
		info.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 100.));
		root.getChildren().add(info);

		for(Card_View cV : playerCards) {
			cV.flipToBack().play();
		}
		c.resetGame();
		ParallelTransition goToInitialPos = comeBack();
		goToInitialPos.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				arg0.consume();
				StartGame(root, scene);
			}
		});
		goToInitialPos.play();
	}

	private void addChoicesButtons() {
		choices = new Vector<Button>();
		for (int i = 0; i < 5; i++) {
			Button btn = new Button();
			btn.setLayoutX(250 * i + 100);
			btn.setLayoutY(10);
			btn.setPrefSize(150, 25);
			btn.setVisible(false);
			switch (i) {
			case 0:
				btn.setText("La prise");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						event.consume();
						disabledButton();
						priseAndGuardAction();
					}
				});
				break;
			case 1:
				btn.setText("La garde");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						event.consume();
						disabledButton();
						priseAndGuardAction();
					}
				});
				break;
			case 2:
				btn.setText("La garde sans le chien");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						event.consume();
						disabledButton();
						gardAgainstChien().play();
						ending();
					}

				});
				break;
			case 3:
				btn.setText("La garde contre le chien");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						event.consume();
						disabledButton();
						goToEnemyAnim().play();
						ending();
					}

				});
				break;
			case 4:
				btn.setText("Passe");
				btn.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						disabledButton();
						lookCard(chienCards).play();
						ending();
					}
				});
				break;
			}
			choices.add(btn);
		}
		for (Button b : choices) {
			root.getChildren().add(b);
		}
	}

	private void priseAndGuardAction() {
		SequentialTransition look = lookCard(chienCards);
		look.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SequentialTransition master = new SequentialTransition();
				master.getChildren().addAll(goToMyHand(), triCardsView());
				master.play();
				master.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						event.consume();
						constituteShift();
					}
				});
			}
		});
		look.play();
	}

	private ParallelTransition comeBack() {
		for (Card_View cV : allCards) {
			Double X = 0.;
			Double Y = 0.;
			cV.setObjective(new Pair<Double, Double>(X, Y));
			cV.setObjective(new Pair<Double, Double>(Card_View.START_X, Card_View.START_Y));
		}
		return moveCardsToObjParal(HalfDurationShuffle);
	}

	private ParallelTransition goAway() {
		for (Card_View cV : allCards) {
			Double X = Math.random() * (WIDTH - 0);
			Double Y = Math.random() * (HEIGHT - 0);
			cV.setObjective(new Pair<Double, Double>(X, Y));
		}
		return moveCardsToObjParal(HalfDurationShuffle);
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
		return moveCardsToObjSeq(HalfDurationMove);
	}

	public SequentialTransition moveCardsToObjSeq(long halfDuration) {
		SequentialTransition master = new SequentialTransition();
		int i = 0;
		while (i < allCards.size()) {
			if (allCards.get(i).getIdOwner() != 5) {
				ParallelTransition cards = new ParallelTransition();
				for (int j = 0; j < 3; j++) {
					cards.getChildren().add(allCards.get(i + j).moveAnimation(halfDuration));
				}
				i += 3;
				master.getChildren().add(cards);
			} else {
				master.getChildren().add(allCards.get(i).moveAnimation(halfDuration));
				i++;
			}
		}
		return master;
	}

	public ParallelTransition moveCardsToObjParal(long halfDuration) {
		ParallelTransition master = new ParallelTransition();
		for (Card_View cV : allCards) {
			master.getChildren().add(cV.moveAnimation(halfDuration));
		}
		return master;
	}

	protected SequentialTransition lookCard(ArrayList<Card_View> cards) {
		SequentialTransition master = new SequentialTransition();
		for (Card_View cV : cards) {
			Transition flipCard = cV.flipToFront();
			master.getChildren().add(flipCard);
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
			playerCards.get(i).allowZoom(true);
		}
		return moveCardsToObjParal(HalfDurationMove);
	}

	private ParallelTransition goToEnemyAnim() {
		for (Card_View cV : chienCards) {
			cV.setObjective(new Pair<Double, Double>(1100., 50.));
		}
		return moveCardsToObjParal(HalfDurationMove);
	}

	private ParallelTransition gardAgainstChien() {
		for (Card_View cV : chienCards) {
			cV.setObjective(chien_player_place);
		}
		return moveCardsToObjParal(HalfDurationMove);
	}

	private ParallelTransition goToMyHand() {
		Double X = player_place.getKey() + playerCards.size() * (Card_View.W_CARD / 2);
		Double Y = player_place.getValue();
		for (Card_View cV : chienCards) {
			cV.setObjective(new Pair<Double, Double>(X, Y));
			X += Card_View.W_CARD / 2;
			playerCards.add(cV);

		}
		return moveCardsToObjParal(HalfDurationMove);
	}

	private void allowDragAndDrop() {
		int nbAtoutAcceptOnShift =  allowAtoutToBeDrag();
		for (Card_View cV : playerCards) {
			if (!(cV.getId() == 13 || cV.getId() == 27 || cV.getId() == 28 || cV.getId() == 29
					|| cV.getId() == 49 || cV.getId() == 63 || cV.getId() == 77)) {
				if(NoAuthorize.contains(cV.getId()) && nbAtoutAcceptOnShift > 0)
				{
					cV.openDragAndDrop(dropTarget, chienCards, playerCards);
				}
				else if(!NoAuthorize.contains(cV.getId()))
				{
					cV.openDragAndDrop(dropTarget, chienCards, playerCards);
				}
			}			
			cV.allowZoom(false);
		}
	}

	private int allowAtoutToBeDrag() {
		int AuthorizedCards = 0;
		int i = 0;
		while (AuthorizedCards < 6 && i < playerCards.size()) {
			if (playerCards.get(i).AuthorizedToChien(NoAuthorize)) {
				AuthorizedCards++;
			}
			i++;
		}
		return 6 - AuthorizedCards;
	}

	public void constituteShift() {
		root.getChildren().clear();

		dropTarget = new Rectangle(850, 150, 300, 300);
		dropTarget.getStrokeDashArray().add(10.);
		dropTarget.setStrokeLineJoin(StrokeLineJoin.ROUND);
		dropTarget.setStrokeLineCap(StrokeLineCap.ROUND);
		dropTarget.setStrokeWidth(5.);
		dropTarget.setFill(Color.TRANSPARENT);
		dropTarget.setStroke(Color.BLACK);

		initSceneToConstituteShift();
		ParallelTransition move = setPosCardsForShift();
		move.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				event.consume();
				allowDragAndDrop();
			}
		});
		move.play();
	}

	private ParallelTransition setPosCardsForShift() {
		Double firstCard_X = 50.;
		Double firstCard_Y = 150.;
		for (int i = 0; i < playerCards.size(); i++) {
			playerCards.get(i).setObjective(new Pair<Double, Double>((i % 8) * (Card_View.W_CARD + 10) + firstCard_X,
					(Math.floorDiv(i, 8) * (Card_View.H_CARD + 10)) + firstCard_Y));
		}
		return moveCardsToObjParal(HalfDurationMove);
	}

	private void initSceneToConstituteShift() {
		chienCards.clear();
		Text title = new Text(100., 100., "Constitute the shift.");
		title.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 50.));

		Button btnCancel = new Button("Cancel");
		btnCancel.autosize();
		btnCancel.setLayoutX(1100.);
		btnCancel.setLayoutY(600.);
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				event.consume();
				if(chienCards.size() >0) {
					chienCards.get(chienCards.size()-1).cancelCardShift();
					playerCards.add(chienCards.get(chienCards.size()-1));
					chienCards.remove(chienCards.size()-1);
				}
			}
		});

		Button btnConfirm = new Button("Confirm");
		btnConfirm.autosize();
		btnConfirm.setLayoutX(900.);
		btnConfirm.setLayoutY(600.);
		btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				event.consume();
				lockChien();
				if(chienCards.size() == 6) {
					root.getChildren().remove(title);
					root.getChildren().remove(btnConfirm);
					root.getChildren().remove(btnCancel);
				}
			}
		});

		root.getChildren().add(background);
		root.getChildren().add(dropTarget);
		for (Card_View cV : playerCards) {
			root.getChildren().addAll(cV.getNodes());
		}
		root.getChildren().add(title);
		root.getChildren().add(btnConfirm);
		root.getChildren().add(btnCancel);
	}

	private void lockChien() {
		Text info = new Text(770., 540., "Too few Cards in the Chien.");
		info.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 40.));
		final Task task = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	        	Thread.sleep(1000);
	        	info.setVisible(false);
	            return null;
	        }
	    };

		if (chienCards.size()<6) {
			info.setVisible(true);
			new Thread(task).start();

		} 
		else
		{
			info.setVisible(false);
			restoreView();
		}
		root.getChildren().add(info);
		
	}

	private void restoreView() {
		int nb_carte = 0;
		for(Card_View c : playerCards)		
		{
			Double X = 0.;
			Double Y = 0.;
			X = player_place.getKey() + (nb_carte * (Card_View.W_CARD / 2));
			Y = player_place.getValue();
			c.setObjective(new Pair<Double, Double>(X, Y));
			nb_carte++;
		}
		for(Card_View cV : chienCards) {
			cV.flipToBack().play();
		}

		gardAgainstChien().play();
		moveCardsToObjSeq(HalfDurationMove).play();
		dropTarget.setVisible(false);
		ending();
	}

	private void disabledButton() {
		for(Button b : choices)
		{
			b.setVisible(false);
		}
		choices.clear();
	}
	
	private void ending()
	{
		Text end = new Text(175., 300., "Let s start the Game !");
		end.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 120.));
		root.getChildren().add(end);
		
		Button btn = new Button("Back to Menu");
		btn.autosize();
		btn.setLayoutX(575.);
		btn.setLayoutY(10);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for(Card_View c : allCards)
					c.flipToBack().play();
				
				c.resetGame();
				initSceneWindow();
				event.consume();
			}
		});
		root.getChildren().add(btn);
	}
} 
