package test.utiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import enumeration.Position;
import util.MiscUtil;

class MiscUtilTest {

	@Test
	void clampValueTest() {
		assertEquals(57, MiscUtil.clampValue(57));
		assertEquals(0, MiscUtil.clampValue(0));
		assertEquals(0, MiscUtil.clampValue(-1));
		assertEquals(1, MiscUtil.clampValue(1));
		assertEquals(100, MiscUtil.clampValue(100));
		assertEquals(100, MiscUtil.clampValue(101));
		assertEquals(99, MiscUtil.clampValue(99));

		assertEquals(64, MiscUtil.clampValue(64, 37, 78));
		assertEquals(37, MiscUtil.clampValue(37, 37, 78));
		assertEquals(37, MiscUtil.clampValue(36, 37, 78));
		assertEquals(38, MiscUtil.clampValue(38, 37, 78));
		assertEquals(78, MiscUtil.clampValue(78, 37, 78));
		assertEquals(78, MiscUtil.clampValue(79, 37, 78));
		assertEquals(77, MiscUtil.clampValue(77, 37, 78));
	}

	@Test
	void enumerationNamesTest() {
		List<String> result = Arrays.asList(MiscUtil.getEnumerationNames(Position.class));
		assertTrue(result.contains("dunker"));
		assertTrue(result.contains("short shooter"));
		assertTrue(result.contains("dribbler"));
		assertTrue(result.contains("defender"));
		assertTrue(result.contains("long shooter"));
		assertEquals(5, result.size());
	}

}
