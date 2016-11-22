package View;

import javafx.application.Application;

import javafx.stage.Stage;

public class Window extends Application{

	private String title;
	
	public Window(String titre)
	{
		title = titre;
	}

	@Override
	public void start(Stage fenetre) throws Exception {
		fenetre.setTitle(title);
		fenetre.show(); 
	}
	
	public void run(String[] args)
	{
		launch(args);
	}
}
