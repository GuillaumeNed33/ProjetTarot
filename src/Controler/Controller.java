package Controler;

import Model.Game;
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

}
