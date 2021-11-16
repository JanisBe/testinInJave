package examples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class JUnit5Test {
	@Test
	@DisplayName(value = "test \u26C4")
	void test1(TestInfo info) {
		System.out.println("test1");
		Assertions.fail();
	}

	@Test
	@DisplayName("MessageSuppliere example")
	@RepeatedTest(3)
	public void testMessageSupplier() {
		Person person = new Person("Janis", 38);
		assertEquals("Janiss", person.name,
			() -> String.format("Name isn't the same: %s", person.name));
	}

	@Test
	void testExpectedException() {
		NumberFormatException thrown = assertThrows(NumberFormatException.class, () -> Integer.parseInt("One"), "NumberFormatException was expected");
		assertEquals("For input sting: \"One\"", thrown.getMessage());
	}

	@ParameterizedTest(name = "for text {0} should pass or not")
	@ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
	void palindromes(String candidate) {
		assertTrue(candidate.equals("racecar"));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3})
	void testWithValueSource(int argument) {
		assertTrue(argument > 0 && argument < 4);
	}

	@ParameterizedTest(name = "For src {0} value is {1}")
	@MethodSource("stringProvider")
	void testWithExplicitLocalMethodSource(String argument) {
		assertNotNull(argument);
	}

	static Stream<String> stringProvider() {
		return Stream.of("apple", "banana");
	}

	@ParameterizedTest
	@CsvSource({
		"apple,         1",
		"banana,        2",
		"'lemon, lime', 0xF1",
		"strawberry,    700000"
	})
//	@CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
	void testWithCsvSource(String fruit, int rank) {
		assertNotNull(fruit);
		assertNotEquals(0, rank);
	}

	@Test
	@Disabled
	void disabledTest() {
		assertTrue(false);
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@DisabledOnOs(OS.LINUX)
	@EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_12)
//	@DisabledIf()
//	@DisabledIfEnvironmentVariable()
	void OSDependantTest() {
		assertTrue(false);
	}

	@Test
	void groupAssertions() {
		int[] numbers = {0, 1, 2, 3, 4};
		assertAll("numbers",
			() -> assertEquals(numbers[0], 1),
			() -> assertEquals(numbers[1], 3),
			() -> assertEquals(numbers[3], 3),
			() -> assertEquals(numbers[4], 1));
	}
}