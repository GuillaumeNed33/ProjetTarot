package Model;

public class CardType {

	public enum Specials{ATOUT, EXCUSE}
	public enum Basics{TREFLE, COEUR, CARREAUX, PIQUE}

	private Basics b = null;
	private Specials s = null;

	public CardType()
	{
		b = Basics.TREFLE;
		s = null;
	}

	public CardType(CardType type) {
		b = type.getBasics();
		s = type.getSpecials();
	}

	private Specials getSpecials() {
		return s;
	}

	private Basics getBasics() {
		return b;
	}

	public void changeBasics() {
		if(b == Basics.TREFLE)
		{
			b = Basics.COEUR;
		}
		else if(b == Basics.COEUR)
		{
			b = Basics.CARREAUX;
		}
		else if(b == Basics.CARREAUX)
		{
			b = Basics.PIQUE;
		}
		else if(b == Basics.PIQUE)
		{
			b = Basics.TREFLE;
		}
	}

	public boolean BasicType() {
		return ((b == Basics.TREFLE || b == Basics.COEUR || b == Basics.CARREAUX || b == Basics.PIQUE) && s == null);
	}

	public boolean SpecialType() {
		return (s == Specials.ATOUT || s == Specials.EXCUSE)&& b == null;
	}

	public void changeToAtout() {
		b = null;
		s = Specials.ATOUT;
	}

	public void changeToExcuse() {
		b = null;
		s = Specials.EXCUSE;
	}

	public String toString()
	{
		if(b==null)
			return s.toString();
		else
			return b.toString();
	}
}