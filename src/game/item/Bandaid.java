package game.item;

import java.util.Random;

import enumeration.Statistic;
import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import util.MiscUtil;

/**
 * The class for a bandaid item. Restores an athlete's stamina if they're
 * injured
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class Bandaid extends Item {
	/**
	 * An array of default descriptions for a bandaid item
	 */
	private final static String[] DESCRIPTIONS = {
			"Fixes cuts, scrapes, broken bones, spinal cord trauma, and all other injuries known to human kind",
			"Doctors hate this one simple trick", "I've run out of description ideas sorry" };

	/**
	 * The constructor for Bandaid
	 * 
	 * @param description     Text describing the item
	 * @param price           The cost of purchasing the item
	 * @param gameEnvironment The game environment the bandaid exists in
	 */
	public Bandaid(String description, int price, GameEnvironment gameEnvironment) {
		super("Bandaid", true, description, price, gameEnvironment);
	}

	/**
	 * Generates a bandaid item with random properties.
	 * 
	 * @param qualityLevel    The quality level of the item. Influences randomness
	 *                        of generation
	 * @param gameEnvironment The game environment the game is being created in
	 * @return The randomly generated bandaid purchasable item
	 */
	public static Purchasable generateBandaid(int qualityLevel, GameEnvironment gameEnvironment) {
		Random rng = gameEnvironment.getRng();
		int difficulty = gameEnvironment.getDifficulty();

		// Clamp the quality level in range [1, 100]
		qualityLevel = MiscUtil.clampValue(qualityLevel, 1, 100);

		String randomDescription = DESCRIPTIONS[rng.nextInt(DESCRIPTIONS.length)];

		int priceOffset = 1 * difficulty;
		int randomPrice = MiscUtil.nextIntBounds(qualityLevel * priceOffset * 3 / 4, qualityLevel * priceOffset, rng);
		return new Bandaid(randomDescription, randomPrice, gameEnvironment);
	}

	/**
	 * Applies the bandaid to an athlete. If the athlete is injured their stamina
	 * will be restored and the bandaid consumed.
	 * 
	 * @param athlete The athlete to whom the affect is being applied
	 */
	@Override
	public void applyItem(Athlete athlete) {
		athlete.setStamina(athlete.getStatistic(Statistic.FITNESS));
		this.consume();
	}

	@Override
	public String getDetails() {
		return "Restores an athlete's stamina back to the level of their fitness.";
	}
}
