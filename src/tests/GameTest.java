package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import Model.Card;
import Model.CardType;
import Model.Game;
import Model.Player;


public class GameTest {
	public final static int NBCARDS = 78;
	public final static int NBATOUT = 22; //21 Atouts + Excuse
	public final static int NB_CARD_COLOR = 14;


	@Test
	public void testGenerateCards() {
		int nb_pique = 0;
		int nb_trefle = 0;
		int nb_coeur = 0;
		int nb_carreaux = 0;
		int nb_atout= 0;

		Game g = new Game();
		g.initGame();

		for(Card c : g.getCards())
		{
			switch(c.getType()) {
			case ATOUT:
				nb_atout++;	
				break;
			case PIQUE:
				nb_pique++;
				break;
			case TREFLE:
				nb_trefle++;
				break;
			case CARREAUX:
				nb_carreaux++;
				break;
			case COEUR:
				nb_coeur++;
				break;
			}
		}

		assertEquals(nb_pique, NB_CARD_COLOR);
		assertEquals(nb_trefle, NB_CARD_COLOR);
		assertEquals(nb_coeur, NB_CARD_COLOR);
		assertEquals(nb_carreaux, NB_CARD_COLOR);
		assertEquals(nb_atout, NBATOUT);
		assertEquals(nb_pique+nb_coeur+nb_trefle+nb_atout+nb_carreaux, NBCARDS);
	}

	@Test
	public void testDistribution(){
		Game g = new Game();
		g.initGame();
		g.distribCard();
		
		for(Player p : g.getPlayers())
		{
			assertEquals(p.getHand().getGame().size(),18);
		}
		assertEquals(g.getChien().size(),6);
	}

	@Test
	public void testPetitSec(){
		Game g = new Game();
		g.initGame();

		for(int i = 0; i < g.getPlayers().size(); i++)
		{
			for(int j=0; j<18; j++)
			{
				Card c = new Card();
				c.initCard(CardType.PIQUE,1, 1);
				g.getPlayers().get(i).getHand().addCard(c);
			}
		}

		g.getPlayers().get(0).getHand().getGame().remove(0);
		g.getPlayers().get(0).getHand().addCard(new Card(CardType.ATOUT,1,1));

		assertEquals(g.testPetitSec(), true);


		Game g2 = new Game();
		g2.initGame();

		for(int j=0; j<17; j++)
		{
			Card c = new Card();
			c.initCard(CardType.ATOUT,10, 1);
			g2.getPlayers().get(0).getHand().addCard(c);
			g2.getPlayers().get(1).getHand().addCard(c);
			g2.getPlayers().get(2).getHand().addCard(c);
			g2.getPlayers().get(3).getHand().addCard(c);
		}

		g2.getPlayers().get(0).getHand().addCard(new Card(CardType.ATOUT,1,1));
		assertEquals(g2.testPetitSec(), false);
	}
}