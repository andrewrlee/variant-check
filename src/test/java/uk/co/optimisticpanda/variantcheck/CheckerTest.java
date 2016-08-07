package uk.co.optimisticpanda.variantcheck;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.NAME;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.POSESSIONS;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.YEAR_OF_BIRTH;

import org.junit.Test;
import static uk.co.optimisticpanda.variantcheck.Checker.*;

public class CheckerTest {

	@Test
	public void test() {
		
		// Check default object passes all tests in default suite: 
		allChecks().check(defaultObjectUnderTest());
	
		// Create a variant of the object under test with a different field:
		Generator<TestDto> dto = defaultObjectUnderTest().with(YEAR_OF_BIRTH, 1906);
		
		// default test suite does not pass on the new variant and throws an assertion error:
		assertThatThrownBy(
				() -> allChecks().check(dto))
				.isInstanceOf(AssertionError.class).hasMessage( 
					"\nThe following assertion failed:\n" + 
					"1) [yearOfBirth] expected:<190[5]> but was:<190[6]>\n");

		// Can replace an existing test on a field which will  to pass for the new variant:
		allChecks()
			.ensuring(YEAR_OF_BIRTH, yob -> yob.isEqualTo(1906))
			.check(dto);
		
		// ensuring allows operations on Consumer<AbstractObjectAssert>, check allows type-safe custom checks to be registered,
		// e.g: -> `Checker.value(Consumer<T>)`:
		allChecks()
			.ensuring(YEAR_OF_BIRTH, yob -> yob.isEqualTo(1906))
			.check(NAME, value(name -> name.equalsIgnoreCase("BOB")))
			// .check(NAME, Checker.<TestDto, Integer>value(yob -> yob  == 1906)) --> does not compile due to type safety
			.check(dto);
	
		
		// ...Or can disable the existing test entirely:
		allChecks().ignoring(YEAR_OF_BIRTH).check(dto);
	
		// If multiple tests fail, errors are reported for each failed test:
		assertThatThrownBy(
				() -> allChecks().check(defaultObjectUnderTest()
						.with(NAME, "Charles")
						.with(YEAR_OF_BIRTH, 1945)
						.with(POSESSIONS, asList("Wrist Watch", "Pelican"))))
				.isInstanceOf(AssertionError.class).hasMessage( 
					"\nThe following 3 assertions failed:\n" + 
					"1) [name] expected:<\"[Bob]\"> but was:<\"[Charles]\">\n" + 
					"2) [yearOfBirth] expected:<19[0]5> but was:<19[4]5>\n" + 
					"3) [possessions] expected:<[\"[Hat\", \"Bowie]\"]> but was:<[\"[Wrist Watch\", \"Pelican]\"]>\n");
	}

	private Generator<TestDto> defaultObjectUnderTest() {
		return new Generator<>(TestDto::new)
				.with(NAME, "Bob")
				.with(YEAR_OF_BIRTH, 1905)
				.with(POSESSIONS, asList("Hat", "Bowie"));
	}

	private Checker<TestDto> allChecks() {
		return new Checker<TestDto>()
				.ensuring(NAME, name -> name.isEqualTo("Bob"))
				.ensuring(YEAR_OF_BIRTH, yob -> yob.isEqualTo(1905))
				.ensuring(POSESSIONS, possessions -> possessions.isEqualTo(asList("Hat", "Bowie")));
	}
}