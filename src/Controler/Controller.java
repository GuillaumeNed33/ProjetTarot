package Controler;

import java.util.ArrayList;

import Model.Game;
import View.Card_View;
import View.Window;

public class Controller {
	private Game game;
	private Window win;
	
	/**
	 * <i> <b> Constructeur du controller </b> </i><br>
	 * <br>
	 * <code> public Controller(Game g, Window w) </code> <br>
	 * 
	 * <p>
	 * Construit un controlleur avec un model et une vue.
	 * </p>
	 * 
	 * @param g : Model
	 * @param w : Vue
	 */
	public Controller(Game g, Window w)
	{
		game = g;
		win = w;
		win.setController(this);
	}

	/**
	 * <i> <b> startGame </b> </i><br>
	 * <br>
	 * <code> public void startGame() </code> <br>
	 * 
	 * <p>
	 * Lance la génération des cartes du modèle.
	 * </p>
	 * 
	 */
	public void startGame() {
		game.generateCards();
	}
	
	/**
	 * <i> <b> startGame </b> </i><br>
	 * <br>
	 * <code> public void syncCards(ArrayList cards_view,Window v)  </code> <br>
	 * 
	 * <p>
	 * Permet la synchronisation de l'architecture observer/Oservable entre la vue et le modèle.
	 * </p>
	 * 
	 * @param cards_view : Observers des cartes.
	 * @param v : Observer du model.
	 * 
	 */
	public void syncCards(ArrayList<Card_View> cards_view,Window v) {
		game.addCardObserver(cards_view);
		game.addObserver(v);
	}

	/**
	 * <i> <b> triCards </b> </i><br>
	 * <br>
	 * <code> public void triCards() </code> <br>
	 * 
	 * <p>
	 * Lance le tri des cartes du joueur dans le modele.
	 * </p>
	 * 
	 */
	public void triCards() {
		game.triCards();
	}
	
	/**
	 * <i> <b> distrib </b> </i><br>
	 * <br>
	 * <code> public void distrib() </code> <br>
	 * 
	 * <p>
	 * Lance la distribution des carte dans le modèle.
	 * </p>
	 * 
	 */
	public void distrib() {
		game.distribCard();
	}
	
	/**
	 * <i> <b> testPetitSec </b> </i><br>
	 * <br>
	 * <code> public boolean testPetitSec() </code> <br>
	 * 
	 * <p>
	 * Permet, grâce au model, de savoir si le petit est sec.
	 * </p>
	 * 
	 */
	public boolean testPetitSec() {
		return game.testPetitSec();
	}

	/**
	 * <i> <b> resetGame </b> </i><br>
	 * <br>
	 * <code> public void resetGame() </code> <br>
	 * 
	 * <p>
	 * Permet la réinitialisation du modèle.
	 * </p>
	 * 
	 */
	public void resetGame() {
		game.reset();		
	}

}
