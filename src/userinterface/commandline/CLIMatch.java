package userinterface.commandline;

import java.util.Map;

import game.location.GameMatch;
import enumeration.Location;
import enumeration.Position;
import game.location.GameLocation;
import game.Athlete;
import game.Team;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIMatch extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMatch gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLIMatch(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMatch) gameLocation;
	}

	@Override
	public Location display() {
		System.out.println(gameLocation.getTeam1().getName() +  " vs " + gameLocation.getTeam2().getName());
		cliEnvironment.displayOptions(new String[] {"Continue"});
		
		// Play athletes in each position against each other
		Map<Position, Athlete> team1Athletes = gameLocation.getTeam1().getActiveAthletes();
		Map<Position, Athlete> team2Athletes = gameLocation.getTeam2().getActiveAthletes();
		
		for (Position position : Position.values()) {
			Athlete athlete1 = team1Athletes.get(position);
			Athlete athlete2 = team2Athletes.get(position);
			Athlete winner = gameLocation.getWinningAthlete(athlete1, athlete2);
			
			cliEnvironment.clearScreen();
			System.out.println("---     " + position.name() + " MATCHUP---\n");
			System.out.println(athlete1.getName() + " vs " + athlete2.getName());
			cliEnvironment.displayOptions(new String[] {"PLAY!"});
			System.out.println(winner.getName() + " Wins!");
			cliEnvironment.displayOptions(new String[] {"Continue"});
		}
		
		// Display final winner
		Team winningTeam = gameLocation.getWinningTeam();
		System.out.println("Winning team: " + winningTeam.getName() + "!");
		cliEnvironment.displayOptions(new String[] {"Exit"});
		
		return null;
	}

}
