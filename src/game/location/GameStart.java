package game.location;

import java.util.HashSet;
import java.util.Set;

import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;

/**
 * A class defining the Start location. This location is used at the start of
 * the game to set up the game using user input.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
public class GameStart extends GameLocation {
	/**
	 * The starting athletes that the player can choose from to create their team.
	 */
	private Set<Athlete> startingAthletes;

	/**
	 * @return The starting athletes that the player can choose from to create their
	 *         team.
	 */
	public Set<Athlete> getStartingAthletes() {
		return startingAthletes;
	}

	/**
	 * Choose an athlete from the starting athletes to add to the player's team.
	 * This athlete is then removed from the set of available starting athletes.
	 * 
	 * @param athlete  The Athlete to be chosen.
	 * @param position The Position to place the Athlete in the Team.
	 */
	public void chooseAthlete(Athlete athlete, Position position) {
		getGameEnvironment().getPlayer().getTeam().addAthleteToActive(athlete, position);
		startingAthletes.remove(athlete);
	}

	/**
	 * Generates a set of 10 athletes for the user to choose from at the start of
	 * the game.
	 * 
	 * @return
	 */
	private void generateStartingAthletes() {
		Set<Athlete> athletes = new HashSet<Athlete>();
		int qualityLevel = 100 - getGameEnvironment().getDifficulty() * 20;
		for (int i = 0; i < 10; ++i) {
			Athlete athlete = (Athlete) Athlete.generateAthlete.apply(qualityLevel, getGameEnvironment());
			athletes.add(athlete);
		}
		startingAthletes = athletes;
	}

	/**
	 * Constructor for Start
	 */
	public GameStart(GameEnvironment gameEnvironment) {
		super(gameEnvironment);
		generateStartingAthletes();
	}

	/**
	 * Update the week to a given week. Does nothing in this location. Included for
	 * consistency with other location classes.
	 */
	@Override
	public void update(int week) {
		// nothing needs to happen here
	}

	public void setTeamName(String name) {
		getGameEnvironment().getPlayer().getTeam().setName(name);
	}

	/**
	 * Sets the number of weeks that the game can run for.
	 * 
	 * @param length The length of the game in weeks.
	 */
	public void setSeasonLength(int length) {
		getGameEnvironment().setSeasonLength(length);
	}

	/**
	 * Set the game environment's seed, for use in random number generation.
	 * 
	 * @param seed The seed to set.
	 */
	public void setSeed(int seed) {
		getGameEnvironment().setSeed(seed);
	}

	/**
	 * Set the game environment's difficulty setting.
	 * 
	 * @param difficulty An int representing the game difficulty. Can be 1, 2, or 3.
	 */
	public void setDifficulty(int difficulty) {
		getGameEnvironment().setDifficulty(difficulty);
	}

	/**
	 * Instructs the game environment to increment the current week and update all
	 * its game locations.
	 */
	public void progressWeek() {
		getGameEnvironment().progressWeek();
	}

}
