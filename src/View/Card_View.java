package View;

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

public class Card_View {
	/// Constantes
	final static int H_CARD = 85;
	final static int W_CARD = 48;
	final static Double START_X = 460.;
	final static Double START_Y = 200.;
	final static Double SPEED = 10.;

	private Double speed_X;
	private Double speed_Y;
	private Double objX;
	private Double objY;
	boolean arrived;

	private Image image_front;
	private static Image image_back = new Image("file:./ressources/cards/cache.jpg");
	private ImageView card_back = new ImageView();
	private ImageView card_front = new ImageView();
	static long halfFlipDuration = 100;

	public Card_View(int i) {
		image_front = new Image("file:./ressources/cards/"+i+".jpg");
		arrived = false;
		card_back.setImage(image_back);
		card_back.setFitWidth(W_CARD);
		card_back.setFitHeight(H_CARD);
		card_back.setX(START_X);
		card_back.setY(START_Y);
		card_front.setImage(image_front);
		card_front.setFitWidth(W_CARD);
		card_front.setFitHeight(H_CARD);
		card_front.setX(START_X);
		card_front.setY(START_Y);

	}
	
	public Card_View() {
		//image_front = new Image("file:./ressources/cards/"+i+".jpg");
		arrived = false;
		card_back.setImage(image_back);
		card_back.setFitWidth(W_CARD);
		card_back.setFitHeight(H_CARD);
		card_back.setX(START_X);
		card_back.setY(START_Y);
		
		//card_front.setImage(image_front);
		//card_front.setFitWidth(W_CARD);
		//card_front.setFitHeight(H_CARD);
		//card_front.setX(START_X);
		//card_front.setY(START_Y);

	}

	public void setX(Double x) {
		card_back.setX(x);
		card_front.setX(x);

	}

	public void setY(Double y) {
		card_back.setY(y);
		card_front.setY(y);
		card_back.setCache(true);
	}

	public Collection<Node> getNodes() {
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		return al;
	}

	public Node getcard() {
		return card_back;
	}

	public void identify(String file) {
		image_front = new Image("file:./ressources/cards/" + file);
		card_front.setImage(image_front);
		card_front.setFitWidth(W_CARD);
		card_front.setFitHeight(H_CARD);
	}

	public void move() {
		Double next_pos_X = card_back.getX() + speed_X;
		Double next_pos_Y = card_back.getY() + speed_Y;
		if (Math.abs(next_pos_X - objX) > Math.abs(speed_X))
			card_back.setX(next_pos_X);
		if (Math.abs(next_pos_Y - objY) > Math.abs(speed_Y))
			card_back.setY(next_pos_Y);
		if (Math.abs(next_pos_X - objX) <= Math.abs(speed_X) && Math.abs(next_pos_Y - objY) <= Math.abs(speed_Y)) {
			this.setX(objX);
			this.setY(objY);
			arrived = true;
		}
	}

	public Transition flip() {
		final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(halfFlipDuration), card_back);
		rotateOutFront.setInterpolator(Interpolator.LINEAR);
		rotateOutFront.setAxis(Rotate.Y_AXIS);
		rotateOutFront.setFromAngle(0);
		rotateOutFront.setToAngle(90);
		//
		final RotateTransition rotateInBack = new RotateTransition(Duration.millis(halfFlipDuration), card_front);
		rotateInBack.setInterpolator(Interpolator.LINEAR);
		rotateInBack.setAxis(Rotate.Y_AXIS);
		rotateInBack.setFromAngle(-90);
		rotateInBack.setToAngle(0);
		//
		return new SequentialTransition(rotateOutFront, rotateInBack);
	}

	public void setObjective(Pair<Double, Double> obj) {
		Double pos_X = card_back.getX();
		Double pos_Y = card_back.getY();
		objX = obj.getKey();
		objY = obj.getValue();
		Double dist_X = Math.sqrt(Math.pow(pos_X, 2) + Math.pow(objX, 2));
		Double dist_Y = Math.sqrt(Math.pow(pos_Y, 2) + Math.pow(objY, 2));

		if (objX > pos_X)
			speed_X = SPEED;
		else
			speed_X = -SPEED;
		if (objY > pos_Y)
			speed_Y = (dist_Y / dist_X) * SPEED;
		else if (objY < pos_Y)
			speed_Y = (dist_Y / dist_X) * -SPEED;
		else
			speed_Y = 0.;

	}

	public boolean isArrived() {
		return arrived;
	}
}
