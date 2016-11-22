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
	}

	public void startGame() {
		game.initGame();
	}
}
