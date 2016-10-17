package Model;

import java.util.List;

public class Game {
	
	final int NBPLAYER = 4;
	final int NBCARDS = 78;
	
	private List<Player> players;
	private List<Card> cards;
	private Chien chien;
	
	public Game()
	{
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
