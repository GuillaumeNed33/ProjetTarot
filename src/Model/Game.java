package Model;

import java.util.ArrayList;
import java.util.List;

import Model.CardType;

public class Game {

	final int NBPLAYER = 4;
	final int NBCARDS = 78;

	private List<Player> players;
	private List<Card> cards;
	private Chien chien;

	public Game()
	{
		generateCards();
		initGame();
	}

	private void generateCards() {
		cards = new ArrayList<Card>();
		CardType type = new CardType();
		int val = 1;

		for(int i=1; i<NBCARDS; i++)
		{
			CardType copy = new CardType(type);
			
			cards.add(new Card(copy, new CardValue(val)));
			val++;

			if(i == 56)
			{
				type.changeToAtout();
				val=1;
			}

			if(i%14 == 0 && type.BasicType())
			{
				val = 1;
				type.changeBasics();
			}			
		}

		type.changeToExcuse();
		cards.add(new Card(type, new CardValue(0)));	
		
		/*for(Card e : cards)
		{
			System.out.println(e.getType().toString() + ", " + e.getValue().getVal());
		}*/
	}

	public void initGame()
	{
		players = new ArrayList<Player>();		
		for(int i=0;i<4;i++) {
			players.add(new Player());
		}
		distribCard();
	}

	public void distribCard()
	{
		System.out.println("====== DISTRIBUTION DES CARTES ======");
	}

	public void nextStep()
	{

	}
}
