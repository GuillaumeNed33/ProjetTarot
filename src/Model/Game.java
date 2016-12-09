package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Model.CardType;
import View.Card_View;

/**
 * <i> <b> Game </b> </i><br>
 * 
 * Class correspondant au Model. Permet de gérer toute les actions sur les carte et sur les joueur. <br>
 * Notamment La distribution et le Test du PetitSec. <br>
 * Hérite de la classe {@link Observable}.
 *
 */
public class Game extends Observable {
	//Constantes
	public final static int NBPLAYER = 4;
	public final static int NBCARDS = 78;
	public final static int IDPLAYER = 1;
	public final static int NBATOUT = 22;
	public final static int NB_CARD_COLOR = 14;

	private List<Player> players;
	private List<Card> cards;
	private Chien chien;

	/**
	 * <i> <b> Constructeur </b> </i><br>
	 * <br>
	 * <code> public Game() </code> <br>
	 * 
	 * <p> Permet la construction du model en initialisant les Cartes et les Joueurs.</p>
	 * 
	 */
	public Game() {
		cards = new ArrayList<Card>();
		for (int i = 0; i < NBCARDS; i++) {
			cards.add(new Card());
		}
		players = new ArrayList<Player>();
		for (int i = 0; i < NBPLAYER; i++) {
			players.add(new Player(i + 1));
		}
	}

	/**
	 * <i> <b> generateCards </b> </i><br>
	 * <br>
	 * <code> public void generateCards() </code> <br>
	 * 
	 * <p> Permet la génération des 78 Cartes du Tarot. Chaque carte est construite avec un ID unique Correspondant à sa valeur.<br>
	 * ID de 0 à 13 correspond aux Piques (de As à Roi). <br>
	 * ID de 14 à 27 correpond aux Coeurs (de As à Roi). <br>
	 * ID de 28 à 49 correspond aux Atouts (de l'Excuse au 21). <br>
	 * ID de 50 à 63 correspond aux Carreaux (de As à Roi). <br>
	 * ID de 64 à 77 correspond aux Trefles (de As à Roi). <br>
	 * </p>
	 * 
	 */
	public void generateCards() {
		for (int i = 0; i < NBCARDS; i++) {
			if (i >= 0 && i < NB_CARD_COLOR) {
				cards.get(i).initCard(CardType.PIQUE, i + 1, i);
			} else if (i >= NB_CARD_COLOR && i < NB_CARD_COLOR * 2) {
				cards.get(i).initCard(CardType.COEUR, (i % NB_CARD_COLOR) + 1, i);
			} else if (i >= NB_CARD_COLOR * 2 && i < NBATOUT + (NB_CARD_COLOR * 2)) {
				cards.get(i).initCard(CardType.ATOUT, (i - (NB_CARD_COLOR * 2)), i);
			} else if (i >= NBATOUT + (NB_CARD_COLOR * 2) && i < NBATOUT + (NB_CARD_COLOR * 3)) {
				cards.get(i).initCard(CardType.CARREAUX, ((i - NBATOUT) % 14) + 1, i);
			} else {
				cards.get(i).initCard(CardType.TREFLE, ((i - NBATOUT) % 14) + 1, i);
			}
		}
	}

	/**
	 * <i> <b> triCards </b> </i><br>
	 * <br>
	 * <code> public void triCards() </code> <br>
	 * 
	 * <p> Permet le tri des cartes du Joueur principal (d'ID 0). <br>
	 * Le tri se fait à l'aide de l'id des cartes.</p>
	 * 
	 */
	public void triCards() {
		players.get(0).getHand().getGame().sort(new Comparator<Card>() {
			public int compare(Card c1, Card c2) {
				return c1.getId() - c2.getId();
			}
		});
	}

	/**
	 * <i> <b> testPetitSec </b> </i><br>
	 * <br>
	 * <code> public boolean testPetitSec() </code> <br>
	 * 
	 * <p> Permet de savoir si le petit est sec. <br>
	 * Cela se passe lorsque l'un des joueurs ne possède que l'Atout 1 (Le Petit).
	 * </p>
	 * @return Retourne vrai si le petit est sec et faux sinon.
	 */
	public boolean testPetitSec() { 
		boolean sec = false;
		
		int i = 0;
		while (i < players.size() && !sec) {
			int j = 0;
			boolean gameSec = true;
			while (j < players.get(i).getHand().getGame().size() && gameSec) {
				Card c = players.get(i).getHand().getCard(j);
				if ((c.getType() == CardType.ATOUT && c.getValue() != 1)) {
					gameSec = false;
				}
				j++;
			}
			sec = gameSec;
			i++;
		}
		return sec;
	}

	/**
	 * <i> <b> distribCard </b> </i><br>
	 * <br>
	 * <code> public void distribCard() </code> <br>
	 * 
	 * <p> Gère la distribution des cartes aux joueurs (3 par 3) et des 6 cartes au Chien. <br>
	 * Procède a un <code> Collections.shuffle() </code> qui mélange la listes des carte<br>puis les distribue aux Joueur
	 * </p>
	 * 
	 */
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

	/**
	 * <i> <b> addCardObserver </b> </i><br>
	 * <br>
	 * <code> public void addCardObserver(ArrayList cards_view) </code> <br>
	 * 
	 * <p> Ajoute à chaque Carte de la classe Model un observer de la Classe Vue.
	 * </p>
	 * 
	 * @param Correspond à la liste des {@link Observer} à ajouter.
	 * 
	 */
	public void addCardObserver(ArrayList<Card_View> cards_view) {
		int i = 0;
		for (Card c : cards) {
			c.addObserver(cards_view.get(i));
			i++;
		}
	}

	/**
	 * <i> <b> getCards </b> </i><br>
	 * <br>
	 * <code> public List getCards() </code> <br>
	 * 
	 * <p> Récupères la liste des cartes.
	 * </p>
	 * @return List de {@link Card}
	 * 
	 */
	public List<Card> getCards()
	{
		return cards;
	}

	/**
	 * <i> <b> getPlayers </b> </i><br>
	 * <br>
	 * <code> public List getPlayers() </code> <br>
	 * 
	 * <p> Récupères la liste des joueurs.
	 * </p>
	 * @return {@link List} de Player.
	 * 
	 */
	public List<Player> getPlayers()
	{
		return players;
	}

	/**
	 * <i> <b> getChien </b> </i><br>
	 * <br>
	 * <code> public Chien getChien() </code> <br>
	 * 
	 * <p>
	 * Récupères le chien du model.
	 * </p>
	 * 
	 * @return {@link Chien}
	 * 
	 */
	public Chien getChien()
	{
		return chien;
	}

	/**
	 * <i> <b> reset </b> </i><br>
	 * <br>
	 * <code> public void reset() </code> <br>
	 * 
	 * <p> Réinitialise la partie et supprime la totalité du jeux avant de le relancer.
	 * </p>
	 * 
	 */
	public void reset()
	{
		cards.removeAll(cards);
		for (int i = 0; i < NBCARDS; i++) {
			cards.add(new Card());
		}
		players.removeAll(players);
		for (int i = 0; i < 4; i++) {
			players.add(new Player(i + 1));
		}
		chien.getCards().removeAll(chien.getCards());
		generateCards();
		distribCard();
	}
}
