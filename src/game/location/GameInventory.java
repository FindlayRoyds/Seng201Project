package game.location;

import java.util.ArrayList;
import java.util.Set;

import game.Athlete;
import game.GameEnvironment;
import game.Team;
import game.item.Item;

/**
 * A class for defining the Inventory location. From the inventory location, a
 * Player can view all the Items currently in their inventory. There is also the
 * option to use an Item, in which case the UIEnvironment is used to ask for
 * which Athlete to use the Item on.
 * 
 * @author Jake van Keulen
 * @version 1.1
 *
 */
public class GameInventory extends GameLocation {
	/**
	 * Constructor for Inventory
	 * 
	 * @param gameEnvironment The game environment to which the inventory location
	 *                        belongs.
	 */
	public GameInventory(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
	}

	/**
	 * Gets the Set of Items that are currently in the Player's inventory. The
	 * Player object is accessed through the GameEnvironment.
	 * 
	 * @return The Set of Items in the Player's inventory.
	 */
	public Set<Item> getItems() {
		return getGameEnvironment().getPlayer().getInventory();
	}

	/**
	 * This method allows for using an item from the inventory with the following
	 * steps: 1. Use the UIEnvironment to display a popup prompting the Player to
	 * select an Athlete from their team. 2. Apply the item given in the item
	 * parameter to the selected player
	 * 
	 * @param item The Item to be used.
	 */
	public void useItem(Item item) {
		// Make a list to store all the Athletes in the Player's Team.
		Team team = getGameEnvironment().getPlayer().getTeam();
		ArrayList<Athlete> athletesInTeam = new ArrayList<Athlete>(team.getAllAthletes());

		// Ask the Player which Athlete in their team to use the item on.
		String[] popupOptions = new String[athletesInTeam.size()];
		for (int i = 0; i < athletesInTeam.size(); i++) {
			popupOptions[i] = athletesInTeam.get(i).getName();
		}
		String popupMessage = "Which athlete would you like to apply the item to?";
		int selectedIndex = getGameEnvironment().getUIEnvironment().displayPopup(popupMessage, popupOptions);

		// Use the item on the selected Athlete.
		item.applyItem(athletesInTeam.get(selectedIndex));
	}
}
