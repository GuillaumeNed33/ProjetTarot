package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Controler.Controller;
import Model.CardType;

public class Game {

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
		initGame();
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
			cards.add(new Card(copy, new CardValue(val)));
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
		cards.add(new Card(type, new CardValue(0)));

		/*
		 * for(Card e : cards) { System.out.println(e.getType().toString() +
		 * ", " + e.getValue().getVal()); }
		 */
	}

	public void initGame() {
		players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			players.add(new Player());
		}
		distribCard();
	}

	public void distribCard() {
		chien = new Chien();
		int id_player = 0;
		Random r = new Random();
		while (!cards.isEmpty()) {
			int card = r.nextInt(cards.size());
			if (r.nextDouble() < 0.5 && chien.size() < 6) {
				chien.addCard(cards.get(card));
			} else {
				Player p = players.get(id_player);
				for (int i = 0; i < 3; i++) {
					p.getHand().addCard(cards.get(card));
					cards.remove(card);
					if (cards.size() > 0) {
						card = r.nextInt(cards.size());
					}
				}
				id_player = (id_player + 1) % players.size();
			}
		}
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
			System.out.println("Le joueur " + i + " : ");
			for (Card c : players.get(i).getHand().getGame()) {
				System.out.println(c.getValue().getVal() + " " + c.getType().toString());
			}
		}

	}
}
