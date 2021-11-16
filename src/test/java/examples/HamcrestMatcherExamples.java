package examples;

import static examples.IsDivisibleBy.divisibleBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

public class HamcrestMatcherExamples {

	@Test
	public void testMatchers() {
		assertThat(9, is(not(divisibleBy(4))));
		assertThat(1024, is(divisibleBy(256)));
	}
}

class IsDivisibleBy extends TypeSafeMatcher<Integer> {

	private Integer divider;

	public IsDivisibleBy(Integer divider) {
		this.divider = divider;
	}

	@Override
	protected boolean matchesSafely(Integer dividend) {
		if (divider == 0) {
			return false;
		}
		return ((dividend % divider) == 0);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("divisible by " + divider);
	}

	public static Matcher<Integer> divisibleBy(Integer divider) {
		return new IsDivisibleBy(divider);
	}
}
