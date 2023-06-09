package game;

/**
 * An abstract class for everything sold at markets in-game.
 * 
 * @author Findlay Royds
 * @version 1.4, May 2023.
 */
public abstract class Purchasable {
	/**
	 * The amount of money the player is charged when purchasing from a market
	 */
	protected int price;

	/**
	 * Whether the item is legal or illegal
	 */
	private boolean isLegal;

	/**
	 * A constructor for for Purchasable
	 * 
	 * @param price   The price of the purchasable
	 * @param isLegal Whether the purchasable is legel (affacts what markets it is
	 *                displayed in)
	 */
	public Purchasable(int price, boolean isLegal) {
		this.price = price;
		this.isLegal = isLegal;
	}

	/**
	 * @param player The player attempting to make the purchase
	 * @return If the purchase was successful
	 */
	public abstract boolean purchase(Player player);

	/**
	 * @param player The player making the sale.
	 */
	public abstract void sell(Player player);

	/**
	 * @return The name of the Purchasable.
	 */
	public abstract String getName();

	/**
	 * @return A short text description of the purchasable
	 */
	public abstract String getDescription();

	/**
	 * @return A longer detailed description of the purchasable that includes
	 *         important details.
	 */
	public abstract String getDetails();

	/**
	 * get the price of the purchasable
	 * 
	 * @return The price of the purchasable
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price The new price of the Purchasable in dollars.
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Get whether the item is legal or illegal
	 * 
	 * @return Whether the item is legal or illegal
	 */
	public boolean getIsLegal() {
		return isLegal;
	}
}
