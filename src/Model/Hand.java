package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Hand {
	private LinkedList<Card> playerGame;
	
	public Hand()
	{
		playerGame = new LinkedList<Card>();
	}
	
	public LinkedList<Card> getGame()
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

	public void replace(LinkedList<Card> cards) {
		playerGame = cards;
	}
}
