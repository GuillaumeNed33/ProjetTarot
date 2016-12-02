package Controler;

import java.util.ArrayList;

import Model.Game;
import View.Card_View;
import View.Window;

public class Controller {
	private Game game;
	private Window win;
	
	public Controller(Game g, Window w)
	{
		game = g;
		win = w;
		game.addObserver(win);
	}

	public void startGame() {
		System.out.println("Je lance le jeu");
		game.initGame();
	}
	
	public void syncCards(ArrayList<Card_View> cards_view) {
		game.addCardObserver(cards_view);
	}

	public void triCards() {
		game.triCards();
	}

	public void distrib() {
		game.distribCard();
	}

}
