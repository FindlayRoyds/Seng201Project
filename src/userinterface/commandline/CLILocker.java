package userinterface.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enumeration.Location;
import enumeration.Position;
import enumeration.Statistic;
import game.Athlete;
import game.location.GameLocation;
import game.location.GameLocker;

/**
 * The class that displays the locker screen on the cli
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public class CLILocker extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameLocker gameLocation;

	/**
	 * Constructor for the cli locker class.
	 * 
	 * @param gameLocation   The game location this cli location is linked to.
	 * @param cliEnvironment The ui environment responsible for displaying the cli.
	 */
	public CLILocker(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameLocker) gameLocation;
	}

	/**
	 * Displays a list of all team members and their current roles.
	 */
	private void displayTeam() {
		System.out.println("Active athletes:");
		Map<Position, Athlete> activeAthletes = gameLocation.getActive();
		for (Position position : Position.values()) {
			String positionName = position.name().toLowerCase();
			Athlete athlete = activeAthletes.get(position);
			String athleteName = (athlete == null ? "[No athlete]" : athlete.getName());
			System.out.println(positionName + ": " + athleteName);
		}

		List<Athlete> reserveAthletes = new ArrayList<Athlete>(gameLocation.getReserves());
		for (int i = 1; i <= gameLocation.getMaxNumberOfReserves(); ++i) {
			String athleteName = (i > reserveAthletes.size() ? "[No athlete]" : reserveAthletes.get(i - 1).getName());
			System.out.println("Reserve #" + i + ": " + athleteName);
		}
	}

	/**
	 * Displays details of the selected athlete on the cli
	 */
	private void viewAthleteDetails() {
		System.out.println("Which athlete would you like to see?");

		List<Athlete> athletes = new ArrayList<Athlete>(gameLocation.getAllAthletes());
		String[] athleteNames = new String[athletes.size()];
		for (int i = 0; i < athletes.size(); ++i) {
			athleteNames[i] = athletes.get(i).getName();
		}
		Athlete selectedAthlete = athletes.get(cliEnvironment.displayOptions(athleteNames));

		System.out.println("\nDetails for \"" + selectedAthlete.getName() + "\":");
		System.out.println("- Role: " + selectedAthlete.getRole().name());
		System.out.println("- Stamina: " + selectedAthlete.getStamina());
		for (Statistic statistic : Statistic.values()) {
			System.out.println("- " + statistic.name().toLowerCase() + ": " + selectedAthlete.getStatistic(statistic));
		}
	}

	/**
	 * Prompts the user to select an athlete, then a position to move them to. Then
	 * the selected athlete is moved to the selected position.
	 */
	private void moveAthlete() {
		System.out.println("Which athlete would you like to move");
		List<Athlete> athletes = new ArrayList<Athlete>(gameLocation.getAllAthletes());
		String[] athleteNames = new String[athletes.size()];
		for (int i = 0; i < athletes.size(); ++i) {
			athleteNames[i] = athletes.get(i).getName();
		}
		Athlete selectedAthlete = athletes.get(cliEnvironment.displayOptions(athleteNames));
		System.out.println("Athlete selected: " + selectedAthlete.getName());

		System.out.println("Where would you like to move them?");
		String[] options = new String[Position.values().length + 1];
		options[0] = "Reserve";
		int i = 0;
		for (Position position : Position.values()) {
			options[i + 1] = position.name();
			++i;
		}

		int selection = cliEnvironment.displayOptions(options);
		if (selection == 0) {
			gameLocation.moveToReserve(selectedAthlete);
		} else {
			gameLocation.moveToActive(selectedAthlete, Position.values()[selection - 1]);
		}
	}

	@Override
	public Location display() {
		while (true) {
			System.out.println("Locker Room");
			System.out.println("Team: \"" + gameLocation.getTeamName() + "\"");
			displayTeam();

			String[] options = { "Move an athlete", "View athlete details", "Exit to map" };
			int selection = cliEnvironment.displayOptions(options);
			if (selection == 0) {
				moveAthlete();
			} else if (selection == 1) {
				viewAthleteDetails();
			} else if (selection == 2) {
				return Location.MAP;
			}
		}
	}

}
