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

	private Double speed_X;
	private Double speed_Y;
	private Double objX;
	private Double objY;
	
	
	private Image image_front;
	private static Image image_back = new Image("file:./ressources/cards/cache.jpg"); 
	private ImageView card_back = new ImageView();	
	private ImageView card_front = new ImageView();	
	static long halfFlipDuration = 1000;



	public Card_View() {
		//image_front = new Image("file:./ressources/cards/"+fichier+".jpg"); 
		//ImageView card_front = new ImageView();
		
		speed_X=2.;
		speed_Y=2.;
		card_back.setImage(image_back);
		card_back.setX(10);
		card_back.setY(30);
		//card_front.setImage(image_front);

	}

	public void setX(int val)
	{
		card_back.setX(val);
		card_front.setX(val);

	}
	public void setY(int val)
	{
		card_back.setY(val);
		card_front.setY(val);
		card_back.setCache(true);
	}

	public Collection<Node> getNodes()
	{
		ArrayList<Node> al = new ArrayList<>();
		al.add(card_front);
		al.add(card_back);
		return al;
	}

	public Node getcard() {
		return card_back;
	}

	public void identify(String file) {
		image_front = new Image("file:./ressources/cards/"+file); 
		card_front.setImage(image_front);
	}

	public void move() {
		Double next_pos_X=card_back.getX()+speed_X;
		Double next_pos_Y=card_back.getY()+speed_Y;
		if(next_pos_X < objX)
			card_back.setX(next_pos_X);
		if(next_pos_Y < objY)
			card_back.setY(next_pos_Y);
	}
	
	public Transition flip() {
		final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(halfFlipDuration), card_front); 
	    rotateOutFront.setInterpolator(Interpolator.LINEAR); 
	    rotateOutFront.setAxis(Rotate.Y_AXIS); 
	    rotateOutFront.setFromAngle(0); 
	    rotateOutFront.setToAngle(90); 
	    // 
	    final RotateTransition rotateInBack = new RotateTransition(Duration.millis(halfFlipDuration), card_back); 
	    rotateInBack.setInterpolator(Interpolator.LINEAR); 
	    rotateInBack.setAxis(Rotate.Y_AXIS); 
	    rotateInBack.setFromAngle(-90); 
	    rotateInBack.setToAngle(0); 
	    //
	    return new SequentialTransition(rotateOutFront, rotateInBack); 
	}

	public void setObjective(Pair<Double, Double> obj) {
		objX=obj.getKey();
		objY=obj.getValue();
	}
}
