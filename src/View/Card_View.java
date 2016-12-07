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
	final static Double START_X = 460.;
	final static Double START_Y = 200.;
	final static Double BIG_POS_X = 150.;
	final static Double BIG_POS_Y = 200.;
	private Double objX;
	private Double objY;
	Double originX;
	Double originY;

	private boolean arrived;
	private int id;
	private int idOwner;

	private Image image_front;
	private static Image image_back = new Image("file:./ressources/cards/cachee.jpg");
	private ImageView card_back = new ImageView();
	private ImageView card_front = new ImageView();
	private Rectangle card_shape = new Rectangle(START_X, START_Y, W_CARD, H_CARD);
	static long halfFlipDuration = 100;
	static long halfDistribDuration = 350;

	public Card_View() {
		arrived = false;
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
				transformeToBig(card_front);
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

	public void setX(Double x) {
		card_back.setX(x);
		card_front.setX(x);
		card_shape.setX(x);
	}

	public void setY(Double y) {
		card_back.setY(y);
		card_front.setY(y);
		card_shape.setY(y);
	}

	public Collection<Node> getNodes() {
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		al.add(card_shape);
		return al;
	}

	public Node getBackCard() {
		return card_back;
	}

	public Node getFrontCard() {
		return card_front;
	}

	public Transition flip() {
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

	public TranslateTransition createMoveAnimation(ImageView iV, long halfDurationMove) {
		final TranslateTransition move = new TranslateTransition(Duration.millis(halfDurationMove), iV);
		move.setToX(objX - iV.getX());
		move.setToY(objY - iV.getY());
		return move;
	}

	public Transition moveAnimation(long halfDurationMove) {

		return new ParallelTransition(createMoveAnimation(card_back, halfDurationMove),
				createMoveAnimation(card_front, halfDurationMove));
	}

	public boolean isArrived() {
		return arrived;
	}

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

	public int getIdOwner() {

		return idOwner;
	}

	public Double getX() {
		return card_back.getX();
	}

	public Double getY() {
		return card_back.getY();
	}

	private void transformeToBig(ImageView node) {
		final ScaleTransition zoomFront = new ScaleTransition(Duration.millis(halfFlipDuration), node);
		zoomFront.setToX(2.);
		zoomFront.setToY(2.);
		final TranslateTransition moveFront = new TranslateTransition(Duration.millis(halfFlipDuration), node);
		moveFront.setToX(BIG_POS_X - node.getX());
		moveFront.setToY(BIG_POS_Y - node.getY());
		final ParallelTransition master = new ParallelTransition(moveFront, zoomFront);
		master.play();
	}

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

	public void setObjective(Pair<Double, Double> pair) {
		objX = pair.getKey();
		objY = pair.getValue();
		card_shape.setX(objX);
		card_shape.setY(objY);
	}

	public void setToFrontCard() {
		card_back.toBack();
		card_front.toFront();
		card_shape.toFront();
	}

	public void allowZoom(boolean isAllowed) {
		card_shape.setVisible(isAllowed);
		if (isAllowed) {
			setToFrontCard();
		} else {
			card_back.toFront();
		}
	}

	public void openDragAndDrop(Rectangle dropTarget, ArrayList<Card_View> new_Chien, ArrayList<Card_View> playerCards) {
		originX = card_front.getBoundsInParent().getMinX();
		originY = card_front.getBoundsInParent().getMinY();
		card_front.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (me.isPrimaryButtonDown()) {
					Double shiftX = me.getSceneX() - originX;
					Double shiftY = me.getSceneY() - originY;

					card_front.setOnMouseDragged((new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent me) {
							objX = me.getSceneX()-shiftX;
							objY = me.getSceneY()-shiftY;
							moveAnimation(1).play();;
							me.consume();
						}
					}));
					me.consume();
				}
			}
		});

		card_front.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Point2D mousePos = new Point2D(event.getSceneX(), event.getSceneY());
				System.out.println("Dans le release : " + originX + " : " + originY);
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
				event.consume();
			}
		});
	}

	public void cancelCardShift() {
		objX = originX;
		objY = originY;
		moveAnimation(100).play();
	}

	public boolean AuthorizedToChien(ArrayList<Integer> NoAuthorize) {

		return !NoAuthorize.contains(id);
	}

}
