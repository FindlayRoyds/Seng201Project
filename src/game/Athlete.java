package game;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import enumeration.Position;
import enumeration.Statistic;
import util.MiscUtil;
import util.NameGenerator;

/**
 * This class implements an athlete
 * An athlete belongs to a team,
 * And has several statistics to gauge their performance
 * 
 * @author Findlay Royds, Jake van Keulen
 * @version 1.1, May 2023.
 */
public class Athlete extends Purchasable {	
	/**
	 * The name of the athlete
	 */
	private String name;
	
	/**
	 * The position the athlete will get a bonus for playing in
	 */
	private Position role;
	
	/**
	 * Map of statistic types (from the Statistic enum) to an integer,
	 * ranging from 0 to 100 (inclusive)
	 */
	private HashMap<Statistic, Integer> statistics;
	
	/**
	 * The remaining stamina of the athlete
	 * Depletes after playing in a match
	 * Will cause an injury if it reaches zero
	 */
	private int stamina;
	
	/**
	 * The team the athlete plays in matches for
	 */
	private Team team;
	
	/**
	 * The game environment of the current game
	 * Required to show a pop up message when an athlete is purchased
	 */
	private GameEnvironment gameEnvironment;

	/**
	 * Whether or not the athlete has had a steroid item applied
	 */
	private boolean hasUsedSteroids;
	
	/**
	 * How much effect each statistic has in a match based on the athlete's position.
	 * The weight of each statistic is in the range: [0, 100].
	 * The greater the weight the more important the statistic is to that position
	 */
	private final static Map<Position, Map<Statistic, Integer>> MATCH_POSITION_STATISTIC_WEIGHTS = Map.of(
			Position.DEFENDER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 10,
					Statistic.FITNESS, 30,
					Statistic.HEIGHT, 50,
					Statistic.JUMPING, 60,
					Statistic.OFFENCE, 10,
					Statistic.SHOOTING_ACCURACY, 20,
					Statistic.SHOOTING_POWER, 30
					),
			Position.DRIBBLER, Map.of(
					Statistic.DEFENCE, 70,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 80,
					Statistic.HEIGHT, 10,
					Statistic.JUMPING, 10,
					Statistic.OFFENCE, 70,
					Statistic.SHOOTING_ACCURACY, 20,
					Statistic.SHOOTING_POWER, 30
					),
			Position.DUNKER, Map.of(
					Statistic.DEFENCE, 20,
					Statistic.DRIBBLING, 70,
					Statistic.FITNESS, 800,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 90,
					Statistic.SHOOTING_ACCURACY, 10,
					Statistic.SHOOTING_POWER, 10
					),
			Position.LONG_SHOOTER, Map.of(
					Statistic.DEFENCE, 20,
					Statistic.DRIBBLING, 10,
					Statistic.FITNESS, 10,
					Statistic.HEIGHT, 60,
					Statistic.JUMPING, 40,
					Statistic.OFFENCE, 10,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					),
			Position.SHORT_SHOOTER, Map.of(
					Statistic.DEFENCE, 30,
					Statistic.DRIBBLING, 80,
					Statistic.FITNESS, 40,
					Statistic.HEIGHT, 80,
					Statistic.JUMPING, 40,
					Statistic.OFFENCE, 90,
					Statistic.SHOOTING_ACCURACY, 80,
					Statistic.SHOOTING_POWER, 30
					)
			);
	
	/**
	 * The constructor for the athlete class
	 * 
	 * @param name					The athlete's name
	 * @param role					The position the athlete gets a boost for playing in
	 * @param stamina				How much stamina the athlete has left
	 * @param gameEnvironment		The game environment the athlete is being created in
	 * @param price					The price it costs to purchase the athlete from a market
	 */
	public Athlete(String name, Position role, int stamina, GameEnvironment gameEnvironment, int price) {
		super(price);

		this.name = name;
		this.role = role;
		this.stamina = stamina;
		this.gameEnvironment = gameEnvironment;

		// Initialize all statistics to 0;
		for (Statistic statistic: Statistic.values()) {
			statistics.put(statistic, 0);
		}
	}
	
	/**
	 * Set a specific statistic to an integer value
	 * 
	 * @param statisticToSet		The statistic to set the value of
	 * @param value					The value to set the statistic to
	 */
	public void setStatistic(Statistic statisticToSet, int value) {
		statistics.put(statisticToSet, value);
	}
	
	/**
	 * Set the athlete's stamina to an integer value between 0 and 100
	 * 
	 * @param value					The value to set the athlete's stamina to
	 */
	
	public void setStamina(int value) {
		stamina = MiscUtil.clampValue(value);
	}
	
	/**
	 * Attempts to charge the player the price of the athlete
	 * If the player can afford it, the athlete is added to the player's team
	 * 
	 * @param player				The player attempting to purchase the athlete
	 * @return						Whether or not the purchase was successful
	 */
	public boolean purchase(Player player) {
		boolean chargeSuccess = player.chargeMoney(price);
		
		// Add to player's team if charge successful
		if (chargeSuccess) {
			Team playerTeam = player.getTeam();
			//HashMap<Position, Athlete> activeAthletes = playerTeam.getActiveAthletes();
			
			// Give player option to add athlete to active or reserve team
			String popupMessage = "Please select where to add the athlete";
			List<String> popupOptions = Arrays.asList("Active Team", "Reserve Team");
			boolean addToActive = gameEnvironment.getUIEnvironment().displayPopup(
					popupMessage, popupOptions) == 0;
			
			if (addToActive) {
				// Create a popup with a button for each position
				List<String> positionNames = MiscUtil.getEnumerationNames(Position.class);
				popupMessage = "What role should the athlete be placed into?";
				int selectedIndex = gameEnvironment.getUIEnvironment().displayPopup(
						popupMessage, positionNames);
				Position selectedPosition = Position.values()[selectedIndex];
				
				playerTeam.addAthleteToActive(this, selectedPosition);
			} else {
				playerTeam.addAthleteToReserve(this);
			}
		}
		return chargeSuccess;
	}
	
	
	/**
	 * Gets the Athlete's skill value for a given statistic.
	 * This value is an integer in the range [0, 100]
	 * 
	 * @param statisticToGet		The statistic to get.
	 * @return						The integer value of the given statistic.
	 */
	public int getStatistic(Statistic statisticToGet) {
		return statistics.get(statisticToGet);
	}
	
	/**
	 * @return		The Team the Athlete is on.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * determines a value representing the athlete's effectiveness in a certain position
	 * 
	 * @param playedPosition		the position the athlete is playing
	 * @return						a numerical value for the athlete's effectiveness
	 */
	public int getMatchScore(Position playedPosition) {
		int totalScore = 0;
		Map<Statistic, Integer> statisticWeights = MATCH_POSITION_STATISTIC_WEIGHTS.get(playedPosition);
		
		//add up the athlete's statistics * the statistic's weight for the position
		for (Statistic statistic: Statistic.values()) {
			int statisticWeight = statisticWeights.get(statistic);
			totalScore += getStatistic(statistic) * statisticWeight;
		}
		
		//apply a 20% bonus if the athlete's role matches the position being played 
		if (role == playedPosition) {
			totalScore *= 1.2;
		}
		
		return totalScore;
	}
	
	/**
	 * gets the name of the athlete
	 * 
	 * @return 						the name of the athlete
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the position the athlete will get a bonus for playing in
	 * 
	 * @return						the athlete's role
	 */
	public Position getRole() {
		return role;
	}
	
	/**
	 * gets the athlete's remaining stamina
	 * 
	 * @return 						the athlete's remaining stamina
	 */
	public int getStamina() {
		return stamina;
	}
	
	/**
	 * gets whether or not the athlete is injured
	 * an athlete is injured when their stamina is 0
	 * 
	 * @return 						whether or not the athlete is injured
	 */
	public boolean isInjured() {
		return stamina == 0;
	}
	
	/**
	 * Update's the Athlete's team property.
	 * 
	 * @param newTeam		The team to assign the athlete to.
	 */
	public void setTeam(Team newTeam) {
		team = newTeam;
	}
	
	/**
	 * Generates and returns an Athlete with pseudo-random properties.
	 * 
	 * @param qualityLevel			The "quality" of the Athlete's statistics.
	 * 								A higher quality level makes higher statistics more likely to be generated.
	 * @param rng					An instance of Random; Used to generate random stats, roles, and names
	 * @param team					The team to which the Athlete belongs.
	 * @param gameEnvironment		The GameEnvironment object the Athlete belongs to.
	 * @return						A randomly generated Athlete.
	 */
	public static Athlete generateAthlete(int qualityLevel, GameEnvironment gameEnvironment) {
		Random rng = gameEnvironment.getRng();
		String name = NameGenerator.generateName("playerFirstNames", "playerLastNames", rng);
		
		Position[] positions = Position.values();
		Position role = positions[rng.nextInt(positions.length)];

		int stamina = rng.nextInt(100);
		int price = rng.nextInt(1000);
		
		Athlete resultingAthlete = new Athlete(name, role, stamina, gameEnvironment, price);
		for (Statistic statistic: Statistic.values()) {
			resultingAthlete.statistics.put(statistic, rng.nextInt(qualityLevel));
		}
		
		return resultingAthlete;
	}
	
	
	/**
	 * @return		Whether or not the athlete has used steroids
	 */
	public boolean getHasUsedSteroids() {
		return hasUsedSteroids;
	}
	
	/**
	 * @param		Whether or not the athlete has used steroids
	 */
	public void setHasUsedSteroids(boolean usedSteroids) {
		hasUsedSteroids = usedSteroids;
	}
}