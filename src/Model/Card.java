package Model;

public class Card {
	private int id;
	private CardType type;
	private CardValue value;
	
	public Card(CardType c, CardValue val, int id)
	{
		this.id = id;
		type = c;
		value = val; 
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
}
