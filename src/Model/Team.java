package Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private List<Player>players;
	private int score = 0;
	
	public Team()
	{
		players = new ArrayList<Player>();
	}
	
	public Player getPlayer(int index)
	{
		return players.get(index);
	}
	
	public void addToTeam(Player p)
	{
		players.add(p);
	}

}
