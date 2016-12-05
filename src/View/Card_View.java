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
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

public class Card_View implements Observer {
	/// Constantes
	final static int H_CARD = 112;//85
	final static int W_CARD = 80;//48
	final static Double START_X = 460.;
	final static Double START_Y = 200.;
	final static Double BIG_POS_X = 150.;
	final static Double BIG_POS_Y = 200.;
	private Double objX;
	private Double objY;
	
	private boolean arrived;
	private int id;
	private int idOwner;
	
	private Image image_front;
	private static Image image_back = new Image("file:./ressources/cards/cachee.jpg");
	private ImageView card_back = new ImageView();
	private ImageView card_front = new ImageView();
	private ImageView card_big = new ImageView();
	static long halfFlipDuration = 100;
	static long halfDistribDuration = 350;
 

	public Card_View() {
		arrived = false;
		card_back.setImage(image_back);
		card_big.setVisible(false);
		card_big.setFitHeight(H_CARD);
		card_big.setFitWidth(W_CARD);
		card_big.toBack();
		card_front.setFitWidth(W_CARD);
		card_front.setFitHeight(H_CARD);
		card_back.setFitWidth(W_CARD);
		card_back.setFitHeight(H_CARD);
		this.setX(START_X);
		this.setY(START_Y);
		
		card_front.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				transformeToBig(card_big);
			}
		});
		card_front.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				restoreByDefault();
			}
			
		});

	}
	public void setX(Double x) {
		card_back.setX(x);
		card_front.setX(x);
		card_big.setX(x);
	}

	public void setY(Double y) {
		card_back.setY(y);
		card_front.setY(y);
		card_big.setY(y);
	}

	public Collection<Node> getNodes() {
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		al.add(card_big);
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

	public TranslateTransition createMoveAnimation(ImageView iV) {
		final TranslateTransition move = new TranslateTransition(Duration.millis(halfDistribDuration),iV);
		move.setToX(objX-iV.getX());
		move.setToY(objY-iV.getY());
		return move;
	}
	public Transition moveAnimation() {
	
		return new ParallelTransition(createMoveAnimation(card_back),createMoveAnimation(card_front),
				createMoveAnimation(card_big));
	}

	public boolean isArrived() {
		return arrived;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setFrontVisible(boolean b) {
		card_front.setVisible(b);
	}
	
	@Override
	public void update(Observable o, Object ob) {
		if(ob instanceof Integer) {
			id = (int) ob;
			image_front = new Image("file:" + Window.data.getImage(this.id+1));
			card_front.setImage(image_front);
			card_big.setImage(image_front);
		} else if(ob instanceof Player) {
			idOwner = ((Player) ob).getId();
		} else if(ob instanceof Chien) {
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
	public void actualiseRotate(int nb_carte) {
		Double remY = card_back.getY();
		System.out.println("DEPART : " + remY);
		int angle = -51+(6*(nb_carte));
		Double shift = remY+(W_CARD*Math.sin(Math.toRadians(Math.abs(angle))));
		System.out.println("ANGLE : " + angle);
		System.out.println("ARRIVER : " + shift);
		card_back.setRotate(angle);
		card_back.setY(shift);
		card_front.setVisible(true);
	}
	private void transformeToBig(ImageView node) {
		card_big.setVisible(true);
		final ScaleTransition zoomFront = new ScaleTransition(Duration.millis(halfFlipDuration),node);
		zoomFront.setToX(2.);
		zoomFront.setToY(2.);
		final TranslateTransition moveFront = new TranslateTransition(Duration.millis(halfFlipDuration),node);
		moveFront.setToX(BIG_POS_X-node.getX());
		moveFront.setToY(BIG_POS_Y-node.getY());
		final ParallelTransition master = new ParallelTransition(moveFront,zoomFront);
		master.play();
	}
	private void restoreByDefault() {
		card_big.setVisible(false);

		final ScaleTransition zoomFront = new ScaleTransition(Duration.millis(halfFlipDuration),card_big);
		zoomFront.setToX(1.);
		zoomFront.setToY(1.);
		final TranslateTransition moveFront = new TranslateTransition(Duration.millis(halfFlipDuration),card_big);
		moveFront.setToX(objX-card_big.getX());
		moveFront.setToY(objY-card_big.getY());
		final ParallelTransition master = new ParallelTransition(moveFront,zoomFront);
		master.play();
	}
	public void setObjective(Pair<Double, Double> pair) {
		objX = pair.getKey();
		objY = pair.getValue();
	}
	
}
