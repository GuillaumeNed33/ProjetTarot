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
		game.setController(this);
		win = w;
		win.setController(this);
	}

	public void startGame() {
		game.initGame();
	}
	
	public void syncCards(ArrayList<Card_View> cards_view,Window v) {
		game.addCardObserver(cards_view);
		game.addObserver(v);
	}

	public void triCards() {
		game.triCards();
	}

	public void distrib() {
		game.distribCard();
	}

}
