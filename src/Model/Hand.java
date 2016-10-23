package Model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> playerGame;
	
	public Hand()
	{
		playerGame = new ArrayList<Card>();
	}
	
	public List<Card> getGame()
	{
		return playerGame;
	}
	
	public Card getCard(int index)
	{
		return playerGame.get(index);
	}
	
	public void addCard(Card c)
	{
		playerGame.add(c);
	}
	
	public int size()
	{
		return playerGame.size();
	}
}
