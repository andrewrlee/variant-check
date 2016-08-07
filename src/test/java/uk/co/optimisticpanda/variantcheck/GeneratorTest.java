package uk.co.optimisticpanda.variantcheck;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.NAME;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.POSESSIONS;
import static uk.co.optimisticpanda.variantcheck.TestDtoFields.YEAR_OF_BIRTH;

import org.junit.Test;

public class GeneratorTest {

	@Test
	public void test() {
		TestDto dto1 = defaultGenerator().get();
		
		assertThat(dto1.getName()).isEqualTo("Bob");
		assertThat(dto1.getYearOfBirth()).isEqualTo(1905);
		assertThat(dto1.getPossesions()).isEqualTo(asList("Hat", "Bowie"));
		
		TestDto dto2 = defaultGenerator().with(YEAR_OF_BIRTH, 1906).get();

		assertThat(dto2.getName()).isEqualTo("Bob");
		assertThat(dto2.getYearOfBirth()).isEqualTo(1906);
		assertThat(dto2.getPossesions()).isEqualTo(asList("Hat", "Bowie"));
	}

	private Generator<TestDto> defaultGenerator() {
		return new Generator<>(TestDto::new)
				.with(NAME, "Bob")
				.with(YEAR_OF_BIRTH, 1905)
				.with(POSESSIONS, asList("Hat", "Bowie"));
	}
}
