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
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
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

/**
 * 
 */
public class Window extends Application implements Observer {

	private final static Double WIDTH = 1280.;
	private final static Double HEIGHT = 720.;
	private static final long HALF_DURATION_MOVE = 400;
	private static final long HALF_DURATION_SHUFFLE = 1000;

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
	private Vector<ButtonView> choices;
	public static Data data = new Data();
	private final static String IMAGE_MENU = "file:./ressources/img/background.jpg";
	private final static String IMAGE_GAME = "file:./ressources/img/background_game.jpg";
	private final static String IMAGE_CURSOR = "file:./ressources/img/Cursor_Steampunk.png";

	private Group root;
	private Scene scene;
	private ImageView background = new ImageView();
	private ArrayList<Integer> NoAuthorize;
  
	public Window() {
		title = "Tarot NEDELEC NORMAND S3C";
		allCards = new ArrayList<Card_View>();
		player_place = new Pair<Double, Double>(250., 525.);
		player_place2 = new Pair<Double, Double>(-200., 300.);
		player_place3 = new Pair<Double, Double>(600., -200.);
		player_place4 = new Pair<Double, Double>(1400., 300.);
		chien_place = new Pair<Double, Double>(675., 100.);
		chien_player_place = new Pair<Double, Double>(1100., 500.);
		background.setFitHeight(HEIGHT + 10);
		background.setFitWidth(WIDTH + 10);

		NoAuthorize = new ArrayList<Integer>();// Tableau des Id non autoris�s �
		// mettre dans le chien
		for (int i = 30; i <= 48; i++) // Id des Atouts sans les bous
			NoAuthorize.add(i);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT, null);
		scene.setCursor(new ImageCursor(new Image(IMAGE_CURSOR)));
		initSceneWindow();

		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * <i> <b> initSceneWindow </b> </i><br>
	 * <br>
	 * <code> private void initSceneWindow() </code> <br>
	 * 
	 * <p>
	 * Permet d'initialiser la premi�re sc�ne en cr�ant les carte de la vue et
	 * en les asscociant aux model.
	 * </p>
	 */
	private void initSceneWindow() {
		allCards = new ArrayList<Card_View>();

		for (int i = 0; i < 78; i++) {
			allCards.add(new Card_View());
		}
		c.syncCards(allCards, this);
		c.startGame();

		LoadMenu();
	}

	/**
	 * <i> <b> LoadMenu </b> </i><br>
	 * <br>
	 * <code> private void LoadMenu() </code> <br>
	 * 
	 * <p>
	 * Permet cr�er le menu du projet.
	 * </p>
	 */
	private void LoadMenu() {
		root.getChildren().clear();
		background.setImage(new Image(IMAGE_MENU));

		root.getChildren().add(background);

		Text mainTitle = new Text(220, 220, "SteamPunk Tarot");
		mainTitle.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 125.));
		root.getChildren().add(mainTitle);

		addButtonToMenu();
	}

	/**
	 * <i> <b> addButtonToMenu </b> </i><br>
	 * <br>
	 * <code> private void addButtonToMenu() </code> <br>
	 * 
	 * <p>
	 * Permet d'ajouter le bouton Quit et le bouton Play au menu.
	 * </p>
	 */
	private void addButtonToMenu() {
		ButtonView btn = new ButtonView(500., 350., 100., 300., "Play Game", 30.);

		btn.setOnAction(event -> {
			StartGame();
		});
		root.getChildren().add(btn);

		ButtonView btnQuit = new ButtonView(500., 500., 50., 300., "Quit", 30.);
		btnQuit.setOnAction(event -> {
			System.exit(0);
		});
		root.getChildren().add(btnQuit);
	}

	/**
	 * <i> <b> StartGame </b> </i><br>
	 * <br>
	 * <code> private void StartGame() </code> <br>
	 * 
	 * <p>
	 * Permet de d�marrer le jeu et de s'occuper du traitement de toute les
	 * animation.
	 * </p>
	 */
	private void StartGame() {
		root.getChildren().clear();
		background.setImage(new Image(IMAGE_GAME));
		root.getChildren().add(background);

		ParallelTransition shuffle = goAway();
		shuffle.setOnFinished(event -> {
			ParallelTransition back = comeBack();
			back.setOnFinished(back_event -> {
				distribCard();
			});
			back.play();
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

	/**
	 * <i> <b> distribCard </b> </i><br>
	 * <br>
	 * <code> private void distribCard() </code> <br>
	 * 
	 * <p>
	 * Permet de lancer l'animation de la distribution des cartes et de tester
	 * si le Petit est Sec.
	 * </p>
	 */
	private void distribCard() {
		SequentialTransition masterDistrib = new SequentialTransition();
		masterDistrib.getChildren().addAll(animeDistrib(), lookCard(playerCards));
		masterDistrib.setOnFinished(event -> {
			c.triCards();
			triCardsView().play();

			if (c.testPetitSec()) {
				resetGame();
			} else {
				for (ButtonView b : choices) {
					b.setVisible(true);
				}
			}
		});
		masterDistrib.play();
	}

	/**
	 * <i> <b> resetGame </b> </i><br>
	 * <br>
	 * <code> private void resetGame() </code> <br>
	 * 
	 * <p>
	 * Permet de relancer la game lorsque le Petit est Sec.
	 * </p>
	 */
	private void resetGame() {
		Text info = new Text(400, 475, "Le Petit est sec !");
		info.setFill(Color.WHITE);
		info.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 75.));
		root.getChildren().add(info);

		for (Card_View cV : playerCards) {
			cV.flipToBack().play();
		}
		c.resetGame();
		ParallelTransition goToInitialPos = comeBack();
		goToInitialPos.setOnFinished(event -> {
			initSceneWindow();
			StartGame();
		});
		goToInitialPos.play();
	}

	/**
	 * <i> <b> addChoicesButtons </b> </i><br>
	 * <br>
	 * <code> private void addChoicesButtons() </code> <br>
	 * 
	 * <p>
	 * Permet d'ajouter les boutons pour le choix de la mise.<br>
	 * (Prise / Garde / Garde sans le chien / Garde contre le chien / Passe)
	 * </p>
	 */
	private void addChoicesButtons() {
		choices = new Vector<ButtonView>();
		for (int i = 0; i < 5; i++) {
			ButtonView btn = null;
			switch (i) {
			case 0:
				btn = new ButtonView(300 * i + 100., 15., 30., 150., "La prise", 30.);
				btn.setOnAction(event -> {
					disabledButton();
					priseAndGuardAction();
				});
				break;
			case 1:
				btn = new ButtonView(250 * i + 100., 15., 40., 150., "La Garde", 30.);
				btn.setOnAction(event -> {
					disabledButton();
					priseAndGuardAction();
				});
				break;
			case 2:
				btn = new ButtonView(250 * i + 100., 10., 60., 150., "La garde\nsans le chien", 20.);
				btn.setOnAction(event -> {
					disabledButton();
					gardAgainstChien().play();
					ending();

				});
				break;
			case 3:
				btn = new ButtonView(250 * i + 100., 10., 60., 150., "La garde \ncontre le chien", 20.);
				btn.setOnAction(event -> {
					disabledButton();
					goToEnemyAnim().play();
					ending();

				});
				break;
			case 4:
				btn = new ButtonView(250 * i + 100., 15., 35., 150., "Passe", 30.);
				btn.setOnAction(event -> {
					disabledButton();
					lookCard(chienCards).play();
					ending();
				});
				break;
			}
			choices.add(btn);
		}
		for (ButtonView b : choices) {
			b.setVisible(false);
			root.getChildren().add(b);
		}
	}

	/**
	 * <i> <b> priseAndGuardAction </b> </i><br>
	 * <br>
	 * <code> private void priseAndGuardAction() </code> <br>
	 * 
	 * <p>
	 * Permet de gerer le changement d'ecran lors de la prise et de la garde
	 * afin de pouvoir constituer l'ecart.
	 * </p>
	 */
	private void priseAndGuardAction() {
		SequentialTransition look = lookCard(chienCards);
		look.setOnFinished(event -> {
			SequentialTransition master = new SequentialTransition();
			master.getChildren().addAll(goToMyHand(), triCardsView());
			master.play();
			master.setOnFinished(event2 -> {
				constituteShift();
			});
		});
		look.play();
	}

	/**
	 * <i> <b> comeBack </b> </i><br>
	 * <br>
	 * <code> private void comeBack() </code> <br>
	 * 
	 * <p>
	 * Permet de faire revenir les cartes a leur point central apr�s l'animation
	 * du m�lange.
	 * </p>
	 * 
	 * @return {@link ParallelTransition} de l'animation.
	 */
	private ParallelTransition comeBack() {
		for (Card_View cV : allCards) {
			Double X = 0.;
			Double Y = 0.;
			cV.setObjective(new Pair<Double, Double>(X, Y));
			cV.setObjective(new Pair<Double, Double>(Card_View.START_X, Card_View.START_Y));
		}
		return moveCardsToObjParal(HALF_DURATION_SHUFFLE);
	}

	/**
	 * <i> <b> goAway </b> </i><br>
	 * <br>
	 * <code> private void goAway() </code> <br>
	 * 
	 * <p>
	 * Permet de faire faire partir les cartes a une position al�atoire pour
	 * simuler un m�lange.
	 * </p>
	 * 
	 * @return {@link ParallelTransition} de l'animation.
	 */
	private ParallelTransition goAway() {
		for (Card_View cV : allCards) {
			Double X = Math.random() * (WIDTH - 0);
			Double Y = Math.random() * (HEIGHT - 0);
			cV.setObjective(new Pair<Double, Double>(X, Y));
		}
		return moveCardsToObjParal(HALF_DURATION_SHUFFLE);
	}

	/**
	 * <i> <b> animeDistrib </b> </i><br>
	 * <br>
	 * <code> private SequentialTransition animeDistrib() </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�erer l'animation s�quentiel de la distribution qui envoie les carte a chaque joueur 3 par 3. 
	 * </p>
	 * 
	 * @return {@link SequentialTransition} de l'animation.
	 */
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
		return moveCardsToObjSeq(HALF_DURATION_MOVE);
	}

	/**
	 * <i> <b> moveCardsToObjSeq </b> </i><br>
	 * <br>
	 * <code> private void moveCardsToObjSeq()long halfDuration </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�rer l'animation s�quentielle de mouvement des cartes vers un objectif.
	 * </p>
	 * 
	 * @param halfDuration : Correspond � la dur�e d'un mouvement de carte.
	 * @return {@link SequentialTransition} de l'animation.
	 */
	private SequentialTransition moveCardsToObjSeq(long halfDuration) {
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

	/**
	 * <i> <b> moveCardsToObjParal </b> </i><br>
	 * <br>
	 * <code> private void moveCardsToObjParal(long halfDuration) </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�rer l'animation parallele du mouvement des cartes vers un objectif.
	 * </p>
	 * 
	 * @param halfDuration : Correspond � la dur�e de l'animation.
	 * @return {@link ParallelTransition} de l'animation.
	 */
	private ParallelTransition moveCardsToObjParal(long halfDuration) {
		ParallelTransition master = new ParallelTransition();
		for (Card_View cV : allCards) {
			master.getChildren().add(cV.moveAnimation(halfDuration));
		}
		return master;
	}

	/**
	 * <i> <b> lookCard </b> </i><br>
	 * <br>
	 * <code> private SequentialTransition lookCard(ArrayList cards) </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�rer la transition séquentielle du retournement une liste de carte afin d'afficher la carte de devant.
	 * </p>
	 * 
	 * @param cards : Liste des cartes a retourner.
	 * @return {@link SequentialTransition} de l'animation.
	 */
	private SequentialTransition lookCard(ArrayList<Card_View> cards) {
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
		return moveCardsToObjParal(HALF_DURATION_MOVE);
	}

	private ParallelTransition goToEnemyAnim() {
		for (Card_View cV : chienCards) {
			cV.setObjective(new Pair<Double, Double>(1100., 50.));
		}
		return moveCardsToObjParal(HALF_DURATION_MOVE);
	}

	private ParallelTransition gardAgainstChien() {
		for (Card_View cV : chienCards) {
			cV.setObjective(chien_player_place);
		}
		return moveCardsToObjParal(HALF_DURATION_MOVE);
	}

	private ParallelTransition goToMyHand() {
		Double X = player_place.getKey() + playerCards.size() * (Card_View.W_CARD / 2);
		Double Y = player_place.getValue();
		for (Card_View cV : chienCards) {
			cV.setObjective(new Pair<Double, Double>(X, Y));
			X += Card_View.W_CARD / 2;
			playerCards.add(cV);

		}
		return moveCardsToObjParal(HALF_DURATION_MOVE);
	}

	private void allowDragAndDrop() {
		int nbAtoutAcceptOnShift = allowAtoutToBeDrag();
		for (Card_View cV : playerCards) {
			if (!(cV.getId() == 13 || cV.getId() == 27 || cV.getId() == 28 || cV.getId() == 29 || cV.getId() == 49
					|| cV.getId() == 63 || cV.getId() == 77)) {
				if (NoAuthorize.contains(cV.getId()) && nbAtoutAcceptOnShift > 0) {
					cV.openDragAndDrop(dropTarget, chienCards, playerCards);
				} else if (!NoAuthorize.contains(cV.getId())) {
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

	private void constituteShift() {
		root.getChildren().clear();

		dropTarget = new Rectangle(850, 200, 300, 300);
		dropTarget.getStrokeDashArray().add(10.);
		dropTarget.setStrokeLineJoin(StrokeLineJoin.ROUND);
		dropTarget.setStrokeLineCap(StrokeLineCap.ROUND);
		dropTarget.setStrokeWidth(5.);
		dropTarget.setFill(Color.TRANSPARENT);
		dropTarget.setStroke(Color.WHITE);

		initSceneToConstituteShift();
		ParallelTransition move = setPosCardsForShift();
		move.setOnFinished(event -> {
			allowDragAndDrop();
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
		return moveCardsToObjParal(HALF_DURATION_MOVE);
	}

	private void initSceneToConstituteShift() {
		chienCards.clear();
		Text title = new Text(150., 100., "Constitute the shift.");
		title.setFill(Color.WHITE);
		title.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 55.));

		ButtonView btnCancel = new ButtonView(1025., 540., 50., 150., "Cancel", 30.);
		btnCancel.setOnAction(event -> {
			if (chienCards.size() > 0) {
				chienCards.get(chienCards.size() - 1).cancelCardShift();
				playerCards.add(chienCards.get(chienCards.size() - 1));
				chienCards.remove(chienCards.size() - 1);
			}
		});

		ButtonView btnConfirm = new ButtonView(825., 540., 50., 150., "Confirm", 30.);
		btnConfirm.setOnAction(event -> {
			lockChien();
			if (chienCards.size() == 6) {
				root.getChildren().remove(title);
				root.getChildren().remove(btnConfirm);
				root.getChildren().remove(btnCancel);
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
		Text info = new Text(770., 150., "Too few Cards in the Chien.");
		info.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 40.));
		info.setFill(Color.WHITE);

		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread.sleep(1000);
				info.setVisible(false);
				return null;
			}
		};

		if (chienCards.size() < 6) {
			info.setVisible(true);
			new Thread(task).start();

		} else {
			info.setVisible(false);
			restoreView();
		}
		root.getChildren().add(info);

	}

	private void restoreView() {
		int nb_carte = 0;
		for (Card_View c : playerCards) {
			Double X = 0.;
			Double Y = 0.;
			X = player_place.getKey() + (nb_carte * (Card_View.W_CARD / 2));
			Y = player_place.getValue();
			c.setObjective(new Pair<Double, Double>(X, Y));
			c.setToFrontCard();
			nb_carte++;
		}
		for (Card_View cV : chienCards) {
			cV.flipToBack().play();
		}

		gardAgainstChien().play();
		moveCardsToObjSeq(HALF_DURATION_MOVE).play();
		dropTarget.setVisible(false);
		ending();
	}

	private void disabledButton() {
		for (ButtonView b : choices) {
			b.setVisible(false);
		}
		choices.clear();
	}

	private void ending() {
		Text end = new Text(230., 300., "The game begin !");
		end.setFill(Color.WHITE);
		end.setFont(Font.loadFont("file:./ressources/font/Steampunk.otf", 120.));
		root.getChildren().add(end);

		ButtonView btn = new ButtonView(500., 15., 50., 300., "Back to Menu", 30.);
		btn.setOnAction(event -> {
			c.resetGame();
			initSceneWindow();
		});
		root.getChildren().add(btn);
	}
}
