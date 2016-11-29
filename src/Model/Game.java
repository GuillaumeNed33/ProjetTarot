package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import Controler.Controller;
import Model.CardType;

public class Game extends Observable {

	final int NBPLAYER = 4;
	final int NBCARDS = 78;

	Controller control;
	private List<Player> players;
	private List<Card> cards;
	private Chien chien;
	private Team laPrise;
	private Team laGarde;

	public Game() {
		generateCards();
		// initGame();
	}

	public void setController(Controller c) {
		control = c;
	}

	private void generateCards() {
		cards = new ArrayList<Card>();
		CardType type = new CardType();
		int val = 1;
		for (int i = 1; i < NBCARDS; i++) {
			CardType copy = new CardType(type);
			cards.add(new Card(copy, new CardValue(val), i));
			val++;
			if (i == 56) {
				type.changeToAtout();
				val = 1;
			}

			if (i % 14 == 0 && type.BasicType()) {
				val = 1;
				type.changeBasics();
			}
		}
		type.changeToExcuse();
		cards.add(new Card(type, new CardValue(0), 78));
	}

	public void initGame() {
		players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			players.add(new Player());
		}
		distribCard();
		while (testPetitSec()) {
			cards.removeAll(cards);
			System.out.println("PETIT SEC ! Taille apres suppression : " + cards.size());
			generateCards();
			distribCard();
		}
		// triCards();
		// displayCardGame();
	}

	public void triCards() {
		players.get(0).getHand().getGame().sort(new Comparator<Card>() {
	        public int compare(Card c1, Card c2)
	        {
	            return  c1.getId() - c2.getId();
	        }
	    });
		setChanged();
		notifyObservers(players.get(0).getHand());
	}

	private boolean testPetitSec() {

		for (Player p : players) {
			for (Card c : p.getHand().getGame()) {
				if ((c.getType().getSpecials() == CardType.Specials.ATOUT && c.getValue().getVal() != 1)
						|| c.getType().getSpecials() == CardType.Specials.EXCUSE) {
					return false;
				}
			}
		}
		return true;
	}

	public void distribCard() {
		chien = new Chien();
		int id_player = 0;
		Random r = new Random();
		boolean firstCard = true;
		while (!cards.isEmpty()) {
			int card = r.nextInt(cards.size());
			if (r.nextDouble() < 0.5 && chien.size() < 6 && !firstCard) {
				chien.addCard(cards.get(card));
				cards.remove(card);
			} else {
				if (firstCard)
					firstCard = false;
				Player p = players.get(id_player);
				for (int i = 0; i < 3; i++) {
					p.getHand().addCard(cards.get(card));
					if (id_player == 0) {

					}
					cards.remove(card);
					if (cards.size() > 0) {
						card = r.nextInt(cards.size());
					}
				}

				id_player = (id_player + 1) % players.size();
			}
		}

		setChanged();
		notifyObservers(players.get(0));
		setChanged();
		notifyObservers(chien);
		// testPetitSec();
	}

	public void nextStep() {

	}

	public void displayCardGame() {

		System.out.println("------------------ AFFICHAGE CARTE ------------------");
		System.out.println("Le Chien : ");
		for (Card c : chien.getCards()) {
			System.out.println(c.getValue().getVal() + " " + c.getType().toString());
		}
		for (int i = 0; i < players.size(); i++) {
			System.out.println("\nLe joueur " + i + " : ");
			for (Card c : players.get(i).getHand().getGame()) {
				System.out.println(c.getValue().getVal() + " " + c.getType().toString());
			}
		}

	}

}
