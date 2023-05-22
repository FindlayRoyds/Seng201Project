package test.gametest.itemtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.Player;
import game.item.Bandaid;
import userinterface.graphical.GUIEnvironment;

class BandaidTest {
	private GameEnvironment ge;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		Bandaid bandaid = new Bandaid("Test", 0, ge);
		assertEquals("Test", bandaid.getDescription());
		assertTrue(bandaid.getIsLegal());
		assertEquals(0, bandaid.getPrice());
		bandaid = new Bandaid("", 100, ge);
		assertEquals("", bandaid.getDescription());
		assertEquals(100, bandaid.getPrice());
	}

	@Test
	void generatorTest() {
		Bandaid bandaid = (Bandaid) Bandaid.generateBandaid(100, ge);
		assertTrue(bandaid instanceof Bandaid);
	}

	@Test
	void applyTest() {
		Athlete athlete = new Athlete("", Position.DUNKER, 0, ge, 0);
		Bandaid bandaid = (Bandaid) Bandaid.generateBandaid(100, ge);
		assertTrue(athlete.isInjured());
		bandaid.applyItem(athlete);
		assertFalse(athlete.isInjured());
	}

	@Test
	void purchaseTest() {
		Bandaid bandaid = new Bandaid("Test", 100, ge);
		Player player = ge.getPlayer();
		player.giveMoney(99);
		assertFalse(bandaid.purchase(player));
		player.giveMoney(1);
		assertTrue(bandaid.purchase(player));
		assertEquals(0, player.getMoney());
	}

	@Test
	void sellTest() {
		Bandaid bandaid = new Bandaid("Test", 100, ge);
		Player player = ge.getPlayer();
		bandaid.sell(player);
		assertEquals(100, player.getMoney());
		bandaid.sell(player);
		assertEquals(200, player.getMoney());
	}

	@Test
	void detailsTest() {
		Bandaid bandaid = (Bandaid) Bandaid.generateBandaid(100, ge);
		assertTrue(bandaid.getDetails() != null);
		assertTrue(bandaid.getDetails() instanceof String);
	}
}
