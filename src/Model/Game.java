package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import Controler.Controller;
import Model.CardType;
import View.Card_View;

public class Game extends Observable {

	
	public final static int NBPLAYER = 4;
	public final static int NBCARDS = 78;
	public final static int IDPLAYER = 1;

	Controller control;
	private List<Player> players;
	private List<Card> cards;
	private Chien chien;

	public Game() {
		cards = new ArrayList<Card>();
		for (int i = 0; i < NBCARDS; i++) {
			cards.add(new Card());
		}
		players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			players.add(new Player(i + 1));
		}
	}

	public void setController(Controller c) {
		control = c;
	}

	private void generateCards() {
		CardType type = new CardType();
		int val = 1;
		for (int i = 0; i < NBCARDS - 1; i++) {
			CardType copy = new CardType(type);
			cards.get(i).initCard(copy, new CardValue(val), i);
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
		cards.get(77).initCard(type, new CardValue(0), 77);
	}

	public void initGame() {
		generateCards();
		// displayCardGame();
	}

	public void triCards() {
		players.get(0).getHand().getGame().sort(new Comparator<Card>() {
			public int compare(Card c1, Card c2) {
				return c1.getId() - c2.getId();
			}
		});
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
		int i = 0;
		Collections.shuffle(cards);
		while (i < cards.size()) {
			switch (id_player) {
			case 3:
				if (chien.size() < 6) {
					cards.get(i).setOwner(chien);
					chien.addCard(cards.get(i));
					i++;
				}
				break;
			case 4:
				Player p = players.get(3);
				for (int j = 0; j < 3; j++) {
					cards.get(i).setOwner(p);
					p.getHand().addCard(cards.get(i));
					i++;
				}
				break;

			default:
				Player player = players.get(id_player);
				for (int j = 0; j < 3; j++) {
					cards.get(i).setOwner(player);
					player.getHand().addCard(cards.get(i));
					i++;
				}

				break;
			}
			id_player = (id_player + 1) % (players.size() + 1);
		}
		setChanged();
		notifyObservers(cards);
	}

	public void displayCardGame() {

		System.out.println("------------------ AFFICHAGE CARTE ------------------");
		System.out.println("Le Chien : " + chien.size());
		for (Card c : chien.getCards()) {
			System.out.println(c.getValue().getVal() + " " + c.getType().toString());
		}
		for (int i = 0; i < players.size(); i++) {
			System.out.println("\nLe joueur " + i + " : " + players.get(i).getHand().getGame().size());
			for (Card c : players.get(i).getHand().getGame()) {
				System.out.println(c.getValue().getVal() + " " + c.getType().toString());
			}
		}

	}

	public void addCardObserver(ArrayList<Card_View> cards_view) {
		int i = 0;
		for (Card c : cards) {
			c.addObserver(cards_view.get(i));
			i++;
		}
	}
}
