package userinterface.commandline;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameMap;

/**
 * The CLI map location. Responsible for displaying locations the player can
 * visit. Also displays the current money the player has, the current week, the
 * weeks left in the season, and allows the player to take a bye.
 * 
 * @author Findlay Royds
 * @version 1.1, May 2023.
 */
public class CLIMap extends CLILocation {
	/**
	 * The map game location the cli map location is linked to.
	 */
	private GameMap gameLocation;

	/**
	 * Locations the user can travel to from the map
	 */
	private Location[] accessibleLocations = new Location[] { Location.MATCH_SELECTION, Location.INVENTORY,
			Location.LOCKER_ROOM, Location.ATHLETE_MARKET, Location.ITEM_MARKET, Location.BLACK_MARKET };

	/**
	 * Constructor for the map CLI location.
	 * 
	 * @param gameLocation   The game location this cli location is linked to.
	 * @param cliEnvironment The ui environment responsible for displaying the cli.
	 */
	public CLIMap(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMap) gameLocation;
	}

	@Override
	public Location display() {
		System.out.println("Map");
		System.out.println("Money: " + gameLocation.getMoney());
		System.out.println("Current week: " + gameLocation.getWeek());
		System.out.println("Weeks remaining: " + gameLocation.getWeeksRemaining());

		String[] options = new String[] { "Stadium", "Inventory", "Locker Room", "Athlete Market", "Item Market",
				"Black Market", "Take a bye" };
		int selectedOption = cliEnvironment.displayOptions(options);

		// Detect if user selected to take a bye
		if (selectedOption == options.length - 1) {
			gameLocation.takeABye();
			return Location.MAP;
		}
		return accessibleLocations[selectedOption];
	}

}
