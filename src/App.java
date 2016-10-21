import Model.Game;
import View.Window;

public class App {

	private Game game;
	private Window win;
	
	public App()
	{
		game = new Game();
		win = new Window("Tarot NEDELEC NORMAND S3C");
	}
	
	public void run()
	{
		
	}

	public static void main(String[] args) {
		/*App a = new App();
		a.run();*/
		
		Game test = new Game();
	}

}
