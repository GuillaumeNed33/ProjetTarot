package Model;

import java.util.LinkedList;

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
	
	public void setGame(LinkedList<Card> cards)
	{
		handGame.replace(cards);
	}
}
