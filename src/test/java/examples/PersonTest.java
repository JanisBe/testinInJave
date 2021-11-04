package examples;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasProperty.hasProperty;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	@DisplayName("does it have fields")
	public void shouldHaveFields() {
		Person person = new Person("Janis", "Blatsios");
		assertThat(person, hasProperty("surname"));
	}
}