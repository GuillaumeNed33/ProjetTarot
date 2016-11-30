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

	final int NBPLAYER = 4;
	final int NBCARDS = 78;

	Controller control;
	private List<Player> players;
	private List<Card> cards;
	private Chien chien;

	public Game() {
		cards = new ArrayList<Card>();
		for(int i=0;i<NBCARDS;i++) {
			cards.add(new Card());
		}
		players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			players.add(new Player(i+1));
		}
	}

	public void setController(Controller c) {
		control = c;
	}

	private void generateCards() {
		CardType type = new CardType();
		int val = 1;
		for (int i = 0; i < NBCARDS-1; i++) {
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
		distribCard();
		if (testPetitSec()) {
			cards.removeAll(cards);
			System.out.println("PETIT SEC ! Taille apres suppression : " + cards.size());
			generateCards();
			distribCard();
		}
		//triCards();
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
		notifyObservers(players.get(0));
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
		while(!cards.isEmpty()) {
			switch(id_player) {
			case 3:
				if(chien.size()<6) {
					cards.get(0).setOwner(chien);
					chien.addCard(cards.get(0));
					cards.remove(0);
				}
				break;
			case 4:
				Player p = players.get(3);
				for (int j = 0; j < 3; j++) {
					cards.get(0).setOwner(p);
					p.getHand().addCard(cards.get(0));
					cards.remove(0);
				}
				break;

			default:				
				Player player = players.get(id_player);
				for (int j = 0; j < 3; j++) {
					player.getHand().addCard(cards.get(0));
					cards.get(0).setOwner(player);
					cards.remove(0);
				}
				break;
			}

			id_player = (id_player + 1) % (players.size()+1);
		}
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
		for(Card c : cards) {
			c.addObserver(cards_view.get(i));
			i++;
		}
	}
}
