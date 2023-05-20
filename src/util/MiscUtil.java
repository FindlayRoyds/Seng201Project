package util;

import java.util.Arrays;

/**
 * A class for miscellaneous utility functions
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class MiscUtil {
	/**
	 * Clamp an integer to the range: [lowerBound, upperBound]
	 * 
	 * @param Value      The value to be clamped
	 * @param lowerBound The inclusive lower bound to clamp to
	 * @param upperBound The inclusive upper bound to clamp to
	 * @return The value clamped in range: [lowerBound, upperBound]
	 */
	public static int clampValue(int value, int lowerBound, int upperBound) {
		return Math.max(lowerBound, Math.min(upperBound, value));
	}

	/**
	 * Clamp an integer between the values 0 and 100 (inclusive) Used for setting an
	 * athletes statistic or stamina. Overloads the clampValue method.
	 * 
	 * @param value the integer value to be clamped
	 * @return the integer value clamped between 0 and 100
	 */
	public static int clampValue(int value) {
		return clampValue(value, 0, 100);
	}

	/**
	 * generates an array of names for an enumeration
	 * 
	 * @author https://stackoverflow.com/users/256196/bohemian
	 * @param e The class of the enumeration
	 * @return an array of strings of the names of the enum
	 */
	public static String[] getEnumerationNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}
}
