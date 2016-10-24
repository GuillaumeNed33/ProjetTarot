package Model;

import java.util.ArrayList;
import java.util.List;

public class Chien {
	private List<Card> cards;
	
	public Chien()
	{
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card c)
	{
		cards.add(c);
	}
	
	public int size()
	{
		return cards.size();
	}
}
