package Model;

import java.util.Observable;

public class Card extends Observable{
	private int id;
	private CardType type;
	private CardValue value;
	Player owner;
	public Card() {
		
	}
	public void initCard(CardType c, CardValue val, int id)
	{
		this.id = id;
		type = c;
		value = val;
		setChanged();
		notifyObservers(id);
	}
	
	public CardType getType()
	{
		return this.type;
	}
	
	public CardValue getValue()
	{
		return value;
	}

	public int getId() {
		return id;
	}
	public void setOwner(Object p) {
		setChanged();
		if(p instanceof Player) {
			this.owner = (Player) p;
		}
		notifyObservers(p);
	}
}
