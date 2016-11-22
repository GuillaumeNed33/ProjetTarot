import Model.Game;
import View.Window;

public class App {

	private static Game game;
	private static Window win;
	
	public App()
	{
		game = new Game();
		win = new Window("Tarot NEDELEC NORMAND S3C");
	}
	
	public void run()
	{
		
	}

	public static void main(String[] args) {
		new App();
		win.run(args);
		//Game test = new Game();
	}

}
