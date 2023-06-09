package game.item;

import game.Athlete;
import game.GameEnvironment;
import game.Player;
import game.Purchasable;
import util.Function3;

/**
 * This class is the abstract class for items in game. It implements purchasing
 * an item, generating items, and determining whether an item is legal
 * 
 * @author Findlay Royds
 * @version 1.2, May 2023.
 */
public abstract class Item extends Purchasable {
	/**
	 * The name describing the item
	 */
	private String name;

	/**
	 * The game environment the item was created in
	 */
	private GameEnvironment gameEnvironment;

	/**
	 * A text description of the item
	 */
	private String description;

	/**
	 * Randomly generates a stat boost item or a bandaid item,
	 * 
	 * qualityLevel: The quality of the item in range: [0, 100] GameEnvironment: The
	 * game environment to which the item belongs. Used for random number
	 * generation.
	 * 
	 * returns A randomly generated legal item
	 */
	public static Function3<Integer, GameEnvironment, Purchasable> generateLegalItem = (qualityLevel,
			gameEnvironment) -> {
		int randomInteger = gameEnvironment.getRng().nextInt(10);

		if (randomInteger == 0) {
			return Bandaid.generateBandaid(qualityLevel, gameEnvironment);
		}
		return StatisticBoost.generateStatisticBoost(qualityLevel, gameEnvironment);
	};

	/**
	 * The constructor for Item.
	 * 
	 * @param name            The name of the item
	 * @param ssLegal         Whether the item is legal or illegal
	 * @param description     Text describing the item
	 * @param price           The cost of purchasing the item
	 * @param gameEnvironment The game environment the item exists in
	 */
	public Item(String name, boolean isLegal, String description, int price, GameEnvironment gameEnvironment) {
		super(price, isLegal);
		this.name = name;
		this.description = description;
		this.gameEnvironment = gameEnvironment;
	}

	/**
	 * The abstract apply item method. Calling this method with any item will apply
	 * the item's effects to the given athlete.
	 * 
	 * @param athlete The athlete to whom the affect is being applied
	 */
	public abstract void applyItem(Athlete athlete);

	/**
	 * Purchase the item and put it into the player's inventory.
	 * 
	 * @param player The player who is purchasing the item
	 * @return Whether or not the purchase was successful
	 */
	@Override
	public boolean purchase(Player player) {
		boolean chargeSuccess = player.chargeMoney(price);

		if (chargeSuccess) {
			player.addToInventory(this);

			// Change price of athlete to their resell price
			// Base resell price on the difficulty
			// Easy: 80%, Medium: 60%, Hard: 40%
			price = (int) (price * ((5 - gameEnvironment.getDifficulty()) / 5f));
		}
		return chargeSuccess;
	}

	@Override
	public void sell(Player player) {
		// Base resell price on the difficulty
		// Easy: 80%, Medium: 60%, Hard: 40%
		int resellPrice = (int) (price * ((5 - gameEnvironment.getDifficulty()) / 5f));

		player.removeFromInventory(this);
		player.giveMoney(resellPrice);
	}

	/**
	 * Removes the item from the player's inventory
	 */
	public void consume() {
		gameEnvironment.getPlayer().removeFromInventory(this);
	}

	/**
	 * Get the name of the item
	 * 
	 * @return The name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the text description of the item
	 * 
	 * @return A text description of the item
	 */
	public String getDescription() {
		return description;
	}
}
