package Model;

import java.util.ArrayList;

public class Chien {
	private ArrayList<Card> cards;

	/**
	 * <i> <b> Constructeur de la classe Chien </b> </i><br>
	 * <br>
	 * <code> public Chien() </code> <br>
	 * 
	 * <p>
	 * Construit un Chien vide.
	 * </p>
	 * 
	 */
	public Chien() {
		cards = new ArrayList<Card>();
	}

	/**
	 * <i> <b> addCard </b> </i><br>
	 * <br>
	 * <code> public void addCard(Card c) </code> <br>
	 * 
	 * <p>
	 * Ajoute une carte au Chien.
	 * </p>
	 * 
	 * @param c
	 *            : Card à ajouter.
	 * 
	 */
	public void addCard(Card c) {
		cards.add(c);
	}

	/**
	 * <i> <b> size </b> </i><br>
	 * <br>
	 * <code> public int size() </code> <br>
	 * 
	 * <p>
	 * Renvois le nombre de carte du Chien.
	 * </p>
	 * 
	 * @return Nombre de Card.
	 * 
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * <i> <b> getCards </b> </i><br>
	 * <br>
	 * <code> public ArrayList getCards() </code> <br>
	 * 
	 * <p>
	 * Retourne la liste de carte du chien
	 * </p>
	 * 
	 * @return ArrayList de Card.
	 * 
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
}
