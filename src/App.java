import Controler.Controller;
import Model.Game;
import View.Window;

public class App {

	private Game game;
	private Window win;
	private Controller control;
	
	public App()
	{
		game = new Game();
		win = new Window();
		control = new Controller(game, win);
		game.setController(control);
		win.setController(control);
		
	}
	
	public void run(String[] args)
	{
		win.run(args);
	}

	public static void main(String[] args) {
		new App().run(args);;
	}
}
