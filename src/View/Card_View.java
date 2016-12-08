package View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import Model.Chien;
import Model.Player;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

public class Card_View implements Observer {
	/// Constantes
	final static int H_CARD = 112;// 85
	final static int W_CARD = 80;// 48
	final static Double START_X = 600.;
	final static Double START_Y = 275.;
	final static Double BIG_POS_X = 150.;
	final static Double BIG_POS_Y = 200.;
	private Double objX;
	private Double objY;
	Double originX;
	Double originY;

	//private boolean arrived;
	private int id;
	private int idOwner;

	private Image image_front;
	private static Image image_back = new Image("file:./ressources/img/Cartes/back.jpg");
	private ImageView card_back = new ImageView();
	private ImageView card_front = new ImageView();
	private Rectangle card_shape = new Rectangle(START_X, START_Y, W_CARD, H_CARD);
	static long halfFlipDuration = 100;
	static long halfDistribDuration = 350;

	/**
	 * 
	 */
	public Card_View() {
		card_back.setImage(image_back);
		card_shape.setFill(Color.TRANSPARENT);
		card_front.setFitWidth(W_CARD);
		card_front.setFitHeight(H_CARD);
		card_back.setFitWidth(W_CARD);
		card_back.setFitHeight(H_CARD);
		card_back.toFront();
		allowZoom(false);
		this.setX(START_X);
		this.setY(START_Y);

		card_shape.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				event.consume();
				transformeToBig();
			}
		});
		card_shape.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				event.consume();
				restoreByDefault();
			}
		});
	}

	/**
	 * <i> <b> setX </b> </i><br>
	 * <br>
	 * <code> public void setX(Double x) </code> <br>
	 * 
	 * <p>
	 * Modifie la valeur de la position de la carte en X.
	 * </p>
	 * 
	 * @param x
	 *            : Valeur de la postition x a mettre.
	 */
	public void setX(Double x) {
		card_back.setX(x);
		card_front.setX(x);
		card_shape.setX(x);
	}

	/**
	 * <i> <b> setY </b> </i><br>
	 * <br>
	 * <code> public void setY(Double y) </code> <br>
	 * 
	 * <p>
	 * Modifie la valeur de la position de la carte en Y.
	 * </p>
	 * 
	 * @param y
	 *            : Valeur de la postition y a mettre.
	 */
	public void setY(Double y) {
		card_back.setY(y);
		card_front.setY(y);
		card_shape.setY(y);
	}

	/**
	 * <i> <b> getNodes </b> </i><br>
	 * <br>
	 * <code> public Collection getNodes() </code> <br>
	 * 
	 * <p>
	 * Retourne l'ensemble des Node de Card_View
	 * </p>
	 * 
	 * @return Collection de Node.
	 */
	public Collection<Node> getNodes() {
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		al.add(card_shape);
		return al;
	}

	/**
	 * <i> <b> getBackCard </b> </i><br>
	 * <br>
	 * <code> public Node getBackCard()  </code> <br>
	 * 
	 * <p>
	 * Retourne la Node correspondant � la carte arri�re.
	 * </p>
	 * 
	 * @return Node de la carte arri�re.
	 */
	public Node getBackCard() {
		return card_back;
	}

	/**
	 * <i> <b> getFrontCard </b> </i><br>
	 * <br>
	 * <code> public Node getFrontCard() </code> <br>
	 * 
	 * <p>
	 * Retourne la Node correspondant � la carte avant.
	 * </p>
	 * 
	 * @return Node de la carte avant.
	 */
	public Node getFrontCard() {
		return card_front;
	}

	/**
	 * <i> <b> flipToFront </b> </i><br>
	 * <br>
	 * <code> public Transition flipToFront() </code> <br>
	 * 
	 * <p>
	 * Retourne une transition permettant de retourner la carte. La transition passe de la carte avant � la carte arri�re
	 * </p>
	 * 
	 * @return La transition du retournement.
	 */
	public Transition flipToFront() {
		final RotateTransition rotateOutBack = new RotateTransition(Duration.millis(halfFlipDuration), card_back);
		rotateOutBack.setInterpolator(Interpolator.LINEAR);
		rotateOutBack.setAxis(Rotate.Y_AXIS);
		rotateOutBack.setFromAngle(0);
		rotateOutBack.setToAngle(-90);

		final RotateTransition rotateInFront = new RotateTransition(Duration.millis(halfFlipDuration), card_front);
		rotateInFront.setInterpolator(Interpolator.LINEAR);
		rotateInFront.setAxis(Rotate.Y_AXIS);
		rotateInFront.setFromAngle(90);
		rotateInFront.setToAngle(0);

		return new SequentialTransition(rotateOutBack, rotateInFront);
	}

	/**
	 * <i> <b> flipToBack </b> </i><br>
	 * <br>
	 * <code> public Transition flipToBack() </code> <br>
	 * 
	 * <p>
	 * Retourne une transition permettant de retourner la carte. La transition passe de la carte arri�re � la carte avant
	 * </p>
	 * 
	 * @return La transition du retournement.
	 */
	public Transition flipToBack() {
		final RotateTransition rotateOutBack = new RotateTransition(Duration.millis(halfFlipDuration), card_front);
		rotateOutBack.setInterpolator(Interpolator.LINEAR);
		rotateOutBack.setAxis(Rotate.Y_AXIS);
		rotateOutBack.setFromAngle(0);
		rotateOutBack.setToAngle(-90);

		final RotateTransition rotateInFront = new RotateTransition(Duration.millis(halfFlipDuration), card_back);
		rotateInFront.setInterpolator(Interpolator.LINEAR);
		rotateInFront.setAxis(Rotate.Y_AXIS);
		rotateInFront.setFromAngle(90);
		rotateInFront.setToAngle(0);

		return new SequentialTransition(rotateOutBack, rotateInFront);
	}

	/**
	 * <i> <b> createMoveAnimation </b> </i><br>
	 * <br>
	 * <code> public TranslateTransition createMoveAnimation(ImageView iV, long halfDurationMove) </code> <br>
	 * 
	 * <p>
	 * Permet de cr�er une Transition de d�placement pour d�placer une image vers son objectif.
	 * </p>
	 * @param iV : ImageView � d�placer
	 * @param halfDurationMove : Dur�e de l'animation
	 * @return La transition du d�placement vers l'objectif.
	 */
	public TranslateTransition createMoveAnimation(ImageView iV, long halfDurationMove) {
		final TranslateTransition move = new TranslateTransition(Duration.millis(halfDurationMove), iV);
		move.setToX(objX - iV.getX());
		move.setToY(objY - iV.getY());
		return move;
	}

	/**
	 * <i> <b> moveAnimation </b> </i><br>
	 * <br>
	 * <code> public ParallelTransition moveAnimation(long halfDurationMove) </code> <br>
	 * 
	 * <p>
	 * Permet de cr�er une Transition parall�le compos� du mouvement de tout les �l�ments de la carte vers l'objectif.
	 * </p>
	 * 
	 * @param halfDurationMove : Dur�e de l'animation
	 * @return La ParallelTransition de tout les �l�ments de la Card_View.
	 */
	public ParallelTransition moveAnimation(long halfDurationMove) {

		return new ParallelTransition(createMoveAnimation(card_back, halfDurationMove),
				createMoveAnimation(card_front, halfDurationMove));
	}

	/**
	 * <i> <b> getId </b> </i><br>
	 * <br>
	 * <code> public int getId() </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�rer l'id de la carte correspondant � sa valeur.
	 * </p>
	 * 
	 * @return l'ID de Card_View.
	 */
	public int getId() {
		return id;
	}

	@Override
	public void update(Observable o, Object ob) {
		if (ob instanceof Integer) {
			id = (int) ob;
			image_front = new Image("file:" + Window.data.getImage(this.id));
			card_front.setImage(image_front);
		} else if (ob instanceof Player) {
			idOwner = ((Player) ob).getId();
		} else if (ob instanceof Chien) {
			idOwner = 5;
		}
	}


	/**
	 * <i> <b> getIdOwner </b> </i><br>
	 * <br>
	 * <code> public int getIdOwner() </code> <br>
	 * 
	 * <p>
	 * Permet de r�cup�rer l'identifiant correspondant au joueur (ou Chien) poss�dant la carte.
	 * </p>
	 * 
	 * @return l'ID du poss�sseur de la Card_View.
	 * 
	 */
	public int getIdOwner() {

		return idOwner;
	}

	/**
	 * <i> <b> transformeToBig </b> </i><br>
	 * <br>
	 * <code> private void transformeToBig() </code> <br>
	 * 
	 * <p>
	 * Permet de faire l'animation du zoom sur la carte afin d'avoir une meilleur vision de la carte. <br>
	 * Cette animation la d�place �galement vers une position pr�d�fini.
	 * </p>
	 * 
	 */
	private void transformeToBig() {
		final ScaleTransition zoomFront = new ScaleTransition(Duration.millis(halfFlipDuration), card_front);
		zoomFront.setToX(2.);
		zoomFront.setToY(2.);
		final TranslateTransition moveFront = new TranslateTransition(Duration.millis(halfFlipDuration), card_front);
		moveFront.setToX(BIG_POS_X - card_front.getX());
		moveFront.setToY(BIG_POS_Y - card_front.getY());
		final ParallelTransition master = new ParallelTransition(moveFront, zoomFront);
		master.play();
	}

	/**
	 * <i> <b> restoreByDefault </b> </i><br>
	 * <br>
	 * <code> private void restoreByDefault() </code> <br>
	 * 
	 * <p>
	 * Permet de faire revenir la carte a ses param�tre par d�faut.<br>
	 * Utilis� apr�s l'utilisation de <code> tranformToBig() <code>
	 * </p>
	 * 
	 */
	private void restoreByDefault() {
		final ScaleTransition zoomFront = new ScaleTransition(Duration.millis(halfFlipDuration), card_front);
		zoomFront.setToX(1.);
		zoomFront.setToY(1.);
		final TranslateTransition moveFront = new TranslateTransition(Duration.millis(halfFlipDuration), card_front);
		moveFront.setToX(objX - card_front.getX());
		moveFront.setToY(objY - card_front.getY());
		final ParallelTransition master = new ParallelTransition(moveFront, zoomFront);
		master.play();
	}

	/**
	 * <i> <b> setObjective </b> </i><br>
	 * <br>
	 * <code> public void setObjective(Pair objective) </code> <br>
	 * 
	 * <p>
	 * Permet de mettre a jour la valeur de l'objectif de la carte.<br>
	 *  Cette objectif est utilis� pour faire le d�placement de la carte.
	 * </p>
	 * 
	 * @param objectve : Pair de Double correspondant aux coordonn�e de l'objectif (X,Y).
	 */
	public void setObjective(Pair<Double, Double> objective) {
		objX = objective.getKey();
		objY = objective.getValue();
		card_shape.setX(objX);
		card_shape.setY(objY);
	}
	
	/**
	 * <i> <b> setToFrontCard </b> </i><br>
	 * <br>
	 * <code> public void setToFrontCard() </code> <br>
	 * 
	 * <p>
	 * Modifie l'affichage des diff�rents objets de la classe sur l'axe Z. <br>
	 * Carte arri�re : Z = 0 <br>
	 * Carte avant : Z = 1 <br>
	 * Rectangle de la carte : Z = 2 <br>
	 * </p>
	 * 
	 */
	public void setToFrontCard() {
		card_back.toBack();
		card_front.toFront();
		card_shape.toFront();
	}

	/**
	 * <i> <b> setToFrontCard </b> </i><br>
	 * <br>
	 * <code> public void setToFrontCard() </code> <br>
	 * 
	 * <p>
	 * Permet d'accepter le zoom sur la carte en fonction du param�tre.
	 * </p>
	 * 
	 * @param isAllowed : Boolean Si vrai : accepte le zoom. Si faux : refuse le zoom.
	 */
	public void allowZoom(boolean isAllowed) {
		card_shape.setVisible(isAllowed);
		if (isAllowed) {
			setToFrontCard();
		} else {
			card_back.toFront();
		}
	}

	/**
	 * <i> <b> openDragAndDrop </b> </i><br>
	 * <br>
	 * <code> public void openDragAndDrop(Rectangle dropTarget, ArrayList new_Chien,ArrayList playerCards) </code> <br>
	 * 
	 * <p>
	 * Permet d'accepter le zoom sur la carte en fonction du param�tre.
	 * </p>
	 * 
	 * @param isAllowed : Boolean Si vrai : accepte le zoom. Si faux : refuse le zoom.
	 */
	public void openDragAndDrop(Rectangle dropTarget, ArrayList<Card_View> new_Chien,
			ArrayList<Card_View> playerCards) {
		originX = card_front.getBoundsInParent().getMinX();
		originY = card_front.getBoundsInParent().getMinY();
		card_front.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (me.isPrimaryButtonDown()) {
					if (!dropTarget.contains(me.getSceneX(), me.getSceneY())) {
						Double shiftX = me.getSceneX() - originX;
						Double shiftY = me.getSceneY() - originY;
						card_front.setOnMouseDragged((new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent me) {
								objX = me.getSceneX() - shiftX;
								objY = me.getSceneY() - shiftY;
								moveAnimation(1).play();
								me.consume();
							}
						}));
					} else {
						card_front.setOnMouseDragged(null);
					}
					me.consume();
				}
			}
		});

		card_front.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Point2D mousePos = new Point2D(event.getSceneX(), event.getSceneY());
				if (card_front.getOnMouseDragged() != null) {
					if (dropTarget.contains(mousePos) && new_Chien.size() < 6) {
						objX = dropTarget.getX() + 30 + ((new_Chien.size() % 3) * W_CARD);
						objY = dropTarget.getY() + 30 + ((new_Chien.size() / 3) * H_CARD);
						moveAnimation(350).play();
						new_Chien.add(Card_View.this);
						playerCards.remove(Card_View.this);

					} else {
						objX = originX;
						objY = originY;
						moveAnimation(100).play();
					}
					card_front.setOnMouseDragged(null);
				}
				event.consume();
			}
		});
	}

	/**
	 * <i> <b> cancelCardShift </b> </i><br>
	 * <br>
	 * <code> public void cancelCardShift() </code> <br>
	 * 
	 * <p>
	 * Deplace la carte a sa position d'origine (dans le jeu du joueur)
	 * </p> 
	 */
	public void cancelCardShift() {
		objX = originX;
		objY = originY;
		moveAnimation(100).play();
	}

	/**
	 * 
	 * <i> <b> AuthorizedToChien </b> </i><br>
	 * <br>
	 * <code> public void AuthorizedToChien(ArrayList<Integer> NoAuthorize) </code> <br>
	 * 
	 * <p>
	 * Retourne Si l'id de la carte est autoris� � �tre mis dans le chien (ce n'est pas un atout)
	 * </p>
	 * 
	 * @param NoAuthorize : Liste des identifiants des atouts du jeu (sans les bous).
	 * @return true si l'id n'est pas un atout, sinon false.
	 * 
	 */ 
	public boolean AuthorizedToChien(ArrayList<Integer> NoAuthorize) {

		return !NoAuthorize.contains(id);
	}

}
