package Model;

public class CardValue {

	public enum Value {
		AS, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, CAVALIER, DAME, ROI;
	}

	//private Value [] tab = new Value[14];
	private int value = 0;
	
	public CardValue(int i)
	{
		value = i;
	}
	
	public int getVal()
	{
		return value;
	}

}
