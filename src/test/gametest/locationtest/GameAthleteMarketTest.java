package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import game.location.GameMarket;
import userinterface.graphical.GUIEnvironment;

class GameAthleteMarketTest {
	private GameEnvironment gameEnvironment;
	private GameMarket gameMarket;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeasonLength(5);
		gameEnvironment.setSeed(0);
		gameMarket = (GameMarket) gameEnvironment.getGameLocation(Location.ATHLETE_MARKET);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		new GameMarket(gameEnvironment, Athlete.generateAthlete, gameEnvironment.getPlayer().getTeam().getAllPurchasables, false, 8);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMarket.update(i);
		assertFalse(gameMarket.getAvailablePurchasables().isEmpty());
	}

	@Test
	void getMoneyTest() {
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameMarket.getPlayerMoney());
		gameEnvironment.getPlayer().giveMoney(100);
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameMarket.getPlayerMoney());
		gameEnvironment.getPlayer().giveMoney(234);
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameMarket.getPlayerMoney());
	}

	@Test
	void ownedAndAllowedTest() {
		for (Purchasable purchasable : gameMarket.getOwnedAndAllowed()) {
			assertEquals(true, purchasable.getIsLegal());
			assertTrue(gameEnvironment.getPlayer().getTeam().getAllAthletes().contains(purchasable));
		}
	}

	@Test
	void transactionTest() {
		gameEnvironment.getPlayer().giveMoney(100000);
		gameMarket.update(1);
		assertFalse(gameMarket.getAvailablePurchasables().isEmpty());

		Purchasable transactionPurchasable = new ArrayList<Purchasable>(gameMarket.getAvailablePurchasables()).get(0);
		gameMarket.purchase(transactionPurchasable);
		assertFalse(gameMarket.getAvailablePurchasables().contains(transactionPurchasable));
		assertEquals(1, gameEnvironment.getPlayer().getTeam().getAllAthletes().size());
		assertTrue(gameEnvironment.getPlayer().getTeam().getAllAthletes().contains(transactionPurchasable));

		gameMarket.sell(transactionPurchasable);
		assertTrue(gameMarket.getAvailablePurchasables().contains(transactionPurchasable));
		assertEquals(0, gameEnvironment.getPlayer().getTeam().getAllAthletes().size());
		assertFalse(gameEnvironment.getPlayer().getTeam().getAllAthletes().contains(transactionPurchasable));
	}
}
