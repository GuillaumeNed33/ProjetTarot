package Model;

import java.util.List;

import Model.CardType.Basics;

public class Game {
	
	final int NBPLAYER = 4;
	final int NBCARDS = 78;
	
	private List<Player> players;
	private List<Card> cards;
	private Chien chien;
	
	public Game()
	{
		CardType type = new CardType();
		
		for(int i=1; i<=NBCARDS; i++)
		{
			if(i == 56)
			{
				type.changeToAtout();
			}
			
			if(i%14 == 0 && type.BasicType())
			{
				type.changeBasics();
			}

			Card c = new Card(type, new CardValue());
			cards.add(c);
		}
		
		initGame();
	}
	
	public void initGame()
	{
		Player j1 = new Player();
		Player j2 = new Player();
		Player j3 = new Player();
		Player j4 = new Player();
		players.add(j1);
		players.add(j2);
		players.add(j3);
		players.add(j4);
		distribCard();
	}
	
	public void distribCard()
	{
		
	}
	
	public void nextStep()
	{
		
	}
}
