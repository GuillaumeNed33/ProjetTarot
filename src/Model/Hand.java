package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Hand {
	private ArrayList<Card> playerGame;
	
	public Hand()
	{
		playerGame = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getGame()
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

	public void replace(ArrayList<Card> cards) {
		playerGame = cards;
	}
}
