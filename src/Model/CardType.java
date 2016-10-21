package Model;

public class CardType {

	public enum Specials{ATOUT, EXCUSE}
	public enum Basics{TREFLE, COEUR, CARREAUX, PIQUE}

	Basics b = null;
	Specials s = null;

	public CardType()
	{
		b = Basics.TREFLE;
	}

	public void changeBasics() {
		if(b == Basics.TREFLE)
		{
			b = Basics.COEUR;
		}
		if(b == Basics.COEUR)
		{
			b = Basics.CARREAUX;
		}
		if(b == Basics.CARREAUX)
		{
			b = Basics.PIQUE;
		}
		if(b == Basics.PIQUE)
		{
			b = Basics.TREFLE;
		}
	}

	public boolean BasicType() {
		return (b == Basics.TREFLE || b == Basics.COEUR || b == Basics.CARREAUX || b == Basics.PIQUE);
	}
	
	public boolean SpecialType() {
		return (s == Specials.ATOUT || s == Specials.EXCUSE);
	}

	public void changeToAtout() {
		b = null;
		s = Specials.ATOUT;
	}
	
	public void changeToExcuse() {
		b = null;
		s = Specials.EXCUSE;
	}
}