package Model;

public class Player {
	private int id;
	private Hand handGame;  
	
	/**
	 * <i> <b> Constructeur de Player </b> </i><br>
	 * <br>
	 * <code> public Player(int id) </code> <br>
	 * 
	 * <p> Créer un joueur à partir de son identifiant.
	 * </p>
	 * @param id : Identifiant du joueur
	 */
	public Player(int id) 
	{
		this.id = id;
		handGame = new Hand();
	}
	
	/**
	 * <i> <b> getHand </b> </i><br>
	 * <br>
	 * <code> public Hand getHand() </code> <br>
	 * 
	 * <p> Retourne la main du joueur.
	 * </p>
	 * 
	 * @return Hand du Player.
	 */
	public Hand getHand() 
	{
		return handGame;
	}
	
	/**
	 * <i> <b> getId </b> </i><br>
	 * <br>
	 * <code> public int getId() </code> <br>
	 * 
	 * <p> Retourne l'ID du Joueur.
	 * </p>
	 * 
	 * @return ID de Player
	 */
	public int getId()
	{
		return id;
	}
}
