package Model;

public class CardValue {

	public enum Value {
		AS(1), DEUX(2), TROIS(3), QUATRE(4), CINQ(5), SIX(6), SEPT(7),
		HUIT(8), NEUF(9), DIX(10), VALET(11), CAVALIER(12), DAME(13), ROI(14);

		private int id;
		
		Value(int i) {
			this.id = i;
		}
		
		public int getID() {
			return id;
		}
		
	}

	// private Value [] tab = new Value[14];
	private int value = 0;

	public CardValue(int i) {
		value = i;
	}

	public int getVal() {
		return value;
	}

}
