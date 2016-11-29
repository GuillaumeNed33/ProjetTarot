package Model;

public class Player {
	private int id;
	private Hand handGame;  
	
	public Player(int id) 
	{
		this.id = id;
		handGame = new Hand();
	}
	
	public Hand getHand() 
	{
		return handGame;
	}
	
	public int getId()
	{
		return id;
	}
}
