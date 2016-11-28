package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Chien {
	private LinkedList<Card> cards;
	
	public Chien()
	{
		cards = new LinkedList<Card>();
	}
	
	public void addCard(Card c)
	{
		cards.add(c);
	}
	
	public int size()
	{
		return cards.size();
	}
	public LinkedList<Card> getCards() {
		return cards;
	}
}
