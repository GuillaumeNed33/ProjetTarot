package Model;

public class CardValue {
	private int value;

	/**
	 * <i> <b> Constructeur de CardValue </b> </i><br>
	 * <br>
	 * <code> CardValue(int v) </code> <br>
	 * 
	 * <p>
	 * Construit CardValue à l'aide d'une valeur.
	 * </p>
	 * 
	 * @param v
	 *            : value de CardValue
	 */
	CardValue(int v) {
		this.value = v;
	}

	/**
	 * <i> <b> getValue </b> </i><br>
	 * <br>
	 * <code> public int getValue() </code> <br>
	 * 
	 * <p>
	 * Retourne la valeur de CardValue.
	 * </p>
	 * 
	 * @return Value de CardValue
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <i> <b> setValue </b> </i><br>
	 * <br>
	 * <code> public void setValue(int v) </code> <br>
	 * 
	 * <p>
	 * Permet de préciser la valeur de CardValue.
	 * </p>
	 * 
	 * @param v
	 *            : value de CardValue
	 */
	public void setValue(int v) {
		this.value = v;
	}
}
