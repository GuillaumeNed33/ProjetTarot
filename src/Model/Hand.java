package Model;

import java.util.ArrayList;

/**
 *  <i> <b> Hand </b> </i><br>
 * 
 * Classe correspondant a la main d'un joueur. Cette classe contient une {@link ArrayList} de {@link Card}
 */
public class Hand {
	private ArrayList<Card> playerGame;
	
	/**
	 * <i> <b> Constructeur de Hand </b> </i><br>
	 * <br>
	 * <code> public Hand() </code> <br>
	 * 
	 * <p>
	 * Créer une main vide.
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
	 * <p> Récupère la carte à la position index.
	 * </p>
	 * @param index : Index de la carte dans la Main.
	 * @return Card à l'index.
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
	 * @param c : Card à ajouter.
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
