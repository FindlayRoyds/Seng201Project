package game;

import java.util.HashSet;
import game.item.Item;

/**
 * This class defines a player and the things they own and can do in the game.
 * 
 * @author Jake van Keulen
 * @version 1.2, May 2023.
 */
public class Player {
	/**
	 * The team the Player owns.
	 */
	private Team team;

	/**
	 * The Player's inventory.
	 * Contains all their items.
	 */
	private HashSet<Item> inventory;

	/**
	 * The Player's score.
	 */
	private int score;

	/**
	 * The amount of money in dollars that the player has.
	 */
	private int money;

	/**
	 * Whether the player has purchased an illegal item
	 */
	private boolean hasUsedSteroids;

	/**
	 * Adds an item to the Player's inventory.
	 * 
	 * @param item		The item to be added.
	 */
	public void addToInventory(Item item) {
		inventory.add(item);
	}

	/**
	 * Removes a given amount from the Player's money.
	 * 
	 * @param amountOfMoney		The amount of money to be removed.
	 * @return					If the charge was successful
	 */
	public boolean chargeMoney(float amountOfMoney) {
		if (money >= amountOfMoney) {
			money -= amountOfMoney;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes and item from the player's inventory
	 * 
	 * @param item				The item to remove from the inventory
	 */
	public void removeFromInventory(Item item) {
		inventory.remove(item);
	}

	/**
	 * Gets and returns the team owned by the player.
	 * 
	 * @return		The Player's team.
	 */
	public Team getTeam() {
		return team;
	}
	
	/**
	 * @return		The amount of money in dollars that the Player has.
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @return		The Player's score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets and returns the set of items owned by the player.
	 * 
	 * @return		A HashSet of Items owned by the player
	 */
	public HashSet<Item> getInventory() {
		return inventory;
	}

	/**
	 * Gets and returns whether the player has purchased an illegal item
	 * 
	 * @return					Whether the player is law abiding
	 */
	public boolean getHasUsedSteroids() {
		return hasUsedSteroids;
	}
}
