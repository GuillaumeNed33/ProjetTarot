package Model;

import java.util.Observable;

public class Card extends Observable {
	private int id;
	private CardType type;
	private int value;

	/**
	 * <i> <b> Constructeur par défaut. </b> </i><br>
	 * <br>
	 * <code> public Card() </code> <br>
	 * 
	 * <p>
	 * Construis une carte vide.
	 * </p>
	 * 
	 */
	public Card() {

	}

	/**
	 * <i> <b> Constructeur paramétré. </b> </i><br>
	 * <br>
	 * <code> public Card(CardType type, int val, int id) </code> <br>
	 * 
	 * <p>
	 * Construis une carte à l'aide de son type, de sa valeur et de son id.
	 * </p>
	 * 
	 * @param type
	 *            : Type de la carte.
	 * @param val
	 *            : Valeur de la carte.
	 * @param id
	 *            : ID de la carte.
	 */
	public Card(CardType type, int val, int id) {
		this.id = id;
		this.type = type;
		value = val;
		setChanged();
		notifyObservers(id);
	}

	/**
	 * <i> <b> initCard </b> </i><br>
	 * <br>
	 * <code> public void initCard(CardType c, int val, int id) </code> <br>
	 * 
	 * <p>
	 * Initialise la carte à l'aide de son type, de sa valeur et de son id.
	 * </p>
	 * 
	 * @param type
	 *            : Type de la carte.
	 * @param val
	 *            : Valeur de la carte.
	 * @param id
	 *            : ID de la carte.
	 */
	public void initCard(CardType type, int val, int id) {
		this.id = id;
		this.type = type;
		value = val;
		setChanged();
		notifyObservers(id);
	}

	/**
	 * <i> <b> getType </b> </i><br>
	 * <br>
	 * <code> public CardType getType() </code> <br>
	 * 
	 * <p>
	 * Retourne le type de la carte.
	 * </p>
	 * 
	 * @return CardType de Card
	 */
	public CardType getType() {
		return this.type;
	}

	/**
	 * <i> <b> getValue </b> </i><br>
	 * <br>
	 * <code> public int getValue() </code> <br>
	 * 
	 * <p>
	 * Retourne la valeur de la carte.
	 * </p>
	 * 
	 * @return Value de Card
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <i> <b> getId </b> </i><br>
	 * <br>
	 * <code> public int getId() </code> <br>
	 * 
	 * <p>
	 * Retourne l'ID de la carte.
	 * </p>
	 * 
	 * @return ID de Card
	 */
	public int getId() {
		return id;
	}

	/**
	 * <i> <b> setOwner </b> </i><br>
	 * <br>
	 * <code> public void setOwner() </code> <br>
	 * 
	 * <p>
	 * Précise le possésseur de la carte au observers de celle-ci.
	 * </p>
	 * 
	 * @param p
	 *            : Joueur ou Chien qui possède la carte.
	 */
	public void setOwner(Object p) {
		setChanged();
		notifyObservers(p);
	}
}
