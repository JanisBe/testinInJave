package examples;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.assertj.core.groups.Tuple.tuple;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertJ {

	Person frodo = new Person("Frodo", 32);
	Person aragorn = new Person("Aragorn", 62);
	Person sam = new Person("Sam", 36);
	List<Person> fellowship = List.of(frodo, aragorn, sam);

	@Test
	public void testBasicsOfAssertJ() {
		assertThat(frodo).as("check %s's age", frodo.getName())
			.isNotEqualTo(aragorn)
			.isIn(fellowship)
			.matches(person -> person.age > 50); //change age

		assertThat(fellowship)
			.hasSize(3)
			.contains(aragorn, frodo)
			.contains(frodo, atIndex(0))
			.isNotEmpty()
			.startsWith(frodo)
			.anyMatch(p-> p.name.equals("Frodo"))
			.noneMatch(p-> p.name.equals("Sauron"))
			;
		//.contains  .allMatch;

		String xFiles = "The Truth Is Out There";
		assertThat(xFiles)
			.startsWith("The Truth")
			.contains("Is Out")
			.endsWith("There")
			.containsIgnoringCase("out there");

		assertThat(5.1)
			.isEqualTo(5, withPrecision(1d))
			.isGreaterThan(3d);

		Map<String, Integer> fellowshipMap = fellowship.stream()
			.collect(Collectors.toMap(Person::getName, Person::getAge));

		assertThat(fellowshipMap)
			.isNotEmpty()
			.containsKey("Frodo")
			.contains(entry("Sam", 36));

		assertThat(now())
			.isAfter(LocalDate.of(2002, 2, 2))
			.isBefore(LocalDate.of(2032, 2, 2));
	}

	@Test
	public void testPersonArrayListJunit5() {
		List<Person> expected = List.of(frodo, aragorn);
		List<Person> filteredList = fellowship.stream()
			.filter((character) -> character.name.contains("o"))
			.collect(Collectors.toList());
		Assertions.assertEquals(expected, filteredList);
	}


	@Test
	public void testPersonArrayListAssertJ() {
		assertThat(fellowship)
			.filteredOn(character -> character.name.contains("o"))
			.containsOnly(aragorn, frodo);

		assertThat(fellowship)
			.filteredOn(character -> character.name.contains("o"))
			.filteredOn("age", 62)
			.containsOnly(aragorn);
	}

	Condition<Person> olderPerson = new Condition<>() {
		@Override
		public boolean matches(Person person) {
			return person.age > 35;
		}
	};

	@Test
	public void testPersonWithCondition() {
		assertThat(fellowship).filteredOn(olderPerson)
			.containsOnly(sam, aragorn);
	}

	@Test
	public void testPersonWithExtraction() {
		assertThat(fellowship)
			.extracting("name")
			.contains("Aragorn", "Sam", "Frodo")
			.doesNotContain("Sauron", "Elrond");

		assertThat(fellowship)
			.extracting("name", "age")
			.contains(
				tuple("Frodo", 32),
				tuple("Aragorn", 62),
				tuple("Sam", 36)
			);
	}

	@Test
	public void testPersonWithMethodCall() {
		assertThat(fellowship).extractingResultOf("greet")
			.contains("I'm Frodo", "I'm Aragorn", "I'm Sam");
	}

	@Test
	public void testManyAssertions() {
		SoftAssertions soft = new SoftAssertions();
		soft.assertThat(aragorn.name).as("Name of Aragorn").isEqualTo("Argorn");
		soft.assertThat(aragorn.age).as("Age of Aragorn").isEqualTo(20);
		soft.assertThat(frodo.name).as("Name of Frodo").isEqualTo("Froddo");
		soft.assertAll();
	}

	@Test
	public void testException() {
		assertThatExceptionOfType(NumberFormatException.class)
			.isThrownBy(() -> Integer.parseInt("One"))
			.withMessage("For input string: \"One\"")
			.withMessageContaining("input")
			.withNoCause();
	}

	@Test
	public void testCompareFields() {
		Person frodoCloned = new Person("Frodo", 32);
		Person anotherFrodoCloned = new Person("Frodo", 36);
		assertThat(frodo).isEqualToComparingFieldByField(frodoCloned);
		assertThat(frodo).isEqualToComparingOnlyGivenFields(frodoCloned, "name");
		assertThat(frodo).isEqualToIgnoringGivenFields(sam, "name", "age");
	}
}
