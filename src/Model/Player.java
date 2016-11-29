package Model;

import java.util.ArrayList;

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
	
	public void setGame(ArrayList<Card> cards)
	{
		handGame.replace(cards);
	}
}
