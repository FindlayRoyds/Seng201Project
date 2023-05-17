package game;

import java.util.HashMap;
import java.util.Set;

import enumeration.Location;
import game.location.GameLocation;
import game.randomEvent.RandomEvent;
import userInterface.UIEnvironment;

/**
 * This class implements the game environment.
 * It handles setting up and running a game.
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public class GameEnvironment {
	/**
	 * The player who is playing the game.
	 */
	private Player player;

	/**
	 * The location the player is currently at.
	 */
	private Location currentLocation;

	/**
	 * Map of location types (from the Location enum) to GameLocation
	 * classes that implement the given locations.
	 */
	private HashMap<Location, GameLocation> locations;

	/**
	 * The number of the current week in the season.
	 * Starts from 1.
	 */
	private int currentWeek;

	/**
	 * The length of the season in weeks.
	 */
	private int seasonLength;

	/**
	 * Seed used for generating random events and match outcomes.
	 */
	private int gameSeed;

	/**
	 * All the random events that can happen.
	 * These have a chance of occurring each week.
	 */
	private Set<RandomEvent> randomEvents;

	/**
	 * Environment used for controlling the UI.
	 */
	private UIEnvironment uiEnvironment;
	
	/**
	 * @return The GameEnvironment's current location.
	 */
	Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Change the current game location.
	 * 
	 * @param newLocation		The location to change to.
	 */
	public void changeLocation(Location newLocation) {
		this.currentLocation = newLocation;
		this.uiEnvironment.changeLocation(locations.get(getCurrentLocation()));
	}

	/**
	 * Decides if the game is over or not.
	 * Depends on whether there are any weeks left and if the player is able to make a full team.
	 * 
	 * @return		Whether or not the game is over.
	 */
	public boolean hasEnded() {
		if (currentWeek > seasonLength) return true;
		// TODO: return true if a player can't make a full team
		return false;
	}

	/**
	 * Gets the player who is playing the game.
	 * 
	 * @return 		The game's player object.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the UI environment used by the game for I/O.
	 * @return 		A UIEnvironment object.
	 */
	public UIEnvironment getUIEnvironment() {
		return this.uiEnvironment;
	}
}
