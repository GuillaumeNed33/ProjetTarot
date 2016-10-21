package Model;

public class Card {
	private CardType type;
	private CardValue value;
	
	public Card(CardType c, CardValue val)
	{
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
}
