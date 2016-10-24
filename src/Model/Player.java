package Model;

public class Player {
	private Hand handGame;  
	
	public Player() 
	{
		handGame = new Hand();
	}
	
	public Hand getHand() 
	{
		return handGame;
	}
}
