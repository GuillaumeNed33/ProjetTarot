package View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import Model.CardValue.Value;
import Model.Chien;
import Model.Player;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.Pair;

public class Card_View implements Observer {
	/// Constantes
	final static int H_CARD = 85;
	final static int W_CARD = 48;
	final static Double START_X = 460.;
	final static Double START_Y = 200.;
	final static Double SPEED = 100.;

	private Double speed_X;
	private Double speed_Y;
	private Double objX;
	private Double objY;
	
	private boolean arrived;
	private boolean value_set;
	private int id;
	private int idOwner;
	
	private Image image_front;
	private static Image image_back = new Image("file:./ressources/cards/cachee.jpg");
	private ImageView card_back = new ImageView();
	private ImageView card_front = new ImageView();
	static long halfFlipDuration = 100;

	public Card_View() {
		arrived = false;
		value_set=false;
		card_back.setImage(image_back);
		card_front.setVisible(false);
		card_back.setFitWidth(W_CARD);
		card_back.setFitHeight(H_CARD);
		card_back.setX(START_X);
		card_back.setY(START_Y);

	}
	public void setX(Double x) {
		card_back.setX(x);
		card_front.setX(x);

	}

	public void setY(Double y) {
		card_back.setY(y);
		card_front.setY(y);
	}

	public Collection<Node> getNodes() {
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		return al;
	}

	public Node getBackCard() {
		return card_back;
	}
	
	public Node getFrontCard() {
		return card_front;
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
		arrived = false;
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
	
	public boolean isValueSet() {
		return value_set;
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
			image_front = new Image("file:./ressources/cards/" + Window.data.getImage(this.id));
			card_front.setImage(image_front);
			card_front.setFitWidth(W_CARD);
			card_front.setFitHeight(H_CARD);
		} else if(ob instanceof Player) {
			idOwner = ((Player) ob).getId();
		} else if(ob instanceof Chien) {
			idOwner = 5;
		}
	}
	public int getIdOwner() {
		
		return idOwner;
	}
	
	public Double getObjX() {
		return objX;
	}
	public Double getObjY() {
		return objY;
	}
	public void actualiseRotate(int nb_carte) {
		Double remY = card_back.getY();
		System.out.println("DEPART : " + remY);
		int angle = -34+(4*(nb_carte));
		Double shift = remY+(W_CARD*Math.sin(Math.toRadians(Math.abs(angle))));
		System.out.println("ANGLE : " + angle);
		System.out.println("ARRIVER : " + shift);
		card_back.setRotate(angle);
		card_back.setY(shift);
	}
}
