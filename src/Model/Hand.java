package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Hand {
	private ArrayList<Card> playerGame;
	
	/**
	 * <i> <b> Constructeur de Hand </b> </i><br>
	 * <br>
	 * <code> public Hand() </code> <br>
	 * 
	 * <p> Cr�er une main vide.
	 * </p>
	 * 
	 */
	public Hand()
	{
		playerGame = new ArrayList<Card>();
	}
	
	/**
	 * <i> <b> getGame </b> </i><br>
	 * <br>
	 * <code> public ArrayList getGame() </code> <br>
	 * 
	 * <p> Retourne la jeu de carte de la main.
	 * </p>
	 * @return ArrayList de Card.
	 * 
	 */
	public ArrayList<Card> getGame()
	{
		return playerGame;
	}
	
	/**
	 * <i> <b> getCard </b> </i><br>
	 * <br>
	 * <code> public Card getCard(int index) </code> <br>
	 * 
	 * <p> R�cup�re la carte � la position index.
	 * </p>
	 * @param index : Index de la carte dans la Main.
	 * @return Card � l'index.
	 */
	public Card getCard(int index)
	{
		return playerGame.get(index);
	}
	
	/**
	 * <i> <b> addCard </b> </i><br>
	 * <br>
	 * <code> public void addCard(Card c) </code> <br>
	 * 
	 * <p> Ajoute une carte a la main.
	 * </p>
	 * @param c : Card � ajouter.
	 * 
	 */
	public void addCard(Card c)
	{
		playerGame.add(c);
	}
	
	/**
	 * <i> <b> size </b> </i><br>
	 * <br>
	 * <code> public int size() </code> <br>
	 * 
	 * <p> Retourne le nombre de carte de la main.
	 * </p>
	 * @return Nombre de Card.
	 * 
	 */
	public int size()
	{
		return playerGame.size();
	}
}
