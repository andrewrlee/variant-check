
package uk.co.optimisticpanda.variantcheck;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static uk.co.optimisticpanda.variantcheck.ImmutableTestDtoFields.NAME;
import static uk.co.optimisticpanda.variantcheck.ImmutableTestDtoFields.POSESSIONS;
import static uk.co.optimisticpanda.variantcheck.ImmutableTestDtoFields.YEAR_OF_BIRTH;

import java.util.function.Consumer;

import org.assertj.core.api.IntegerAssert;
import org.junit.Test;

import uk.co.optimisticpanda.variantcheck.Checker.Check;
import uk.co.optimisticpanda.variantcheck.ImmutableTestDto.Builder;

public class CheckerTest {

    @Test
    public void test() {
        
        // Check default object passes all tests in default suite: 
        allChecks().check(defaultObjectUnderTest().create());
    
        // Create a variant of the object under test with a different field:
        ImmutableTestDto dto = defaultObjectUnderTest().withYearOfBirth(1906).create();
        
        // default test suite does not pass on the new variant and throws an assertion error:
        assertThatThrownBy(
                () -> allChecks().check(dto))
                .isInstanceOf(AssertionError.class).hasMessage( 
                    "\nThe following assertion failed:\n" + 
                    "1) [yearOfBirth] expected:<190[5]> but was:<190[6]>\n");

        // Can replace an existing test on a field with `ensuring` which will then pass for the new variant:
        allChecks()
            .ensuring(YEAR_OF_BIRTH, yob -> yob.isEqualTo(1906))
            .check(dto);
        
        // ...Or can disable the existing test entirely:
        allChecks().ignoring(YEAR_OF_BIRTH).check(dto);
    
        // If multiple tests fail, errors are reported for each failed test:
        assertThatThrownBy(
                () -> allChecks().check(defaultObjectUnderTest()
                        .withName("Charles")
                        .withYearOfBirth(1945)
                        .withPossesions(asList("Wrist Watch", "Pelican")).create()))
                .isInstanceOf(AssertionError.class).hasMessage( 
                    "\nThe following 3 assertions failed:\n" + 
                    "1) [name] expected:<\"[Bob]\"> but was:<\"[Charles]\">\n" + 
                    "2) [yearOfBirth] expected:<19[0]5> but was:<19[4]5>\n" + 
                    "3) [possessions] expected:<[\"[Hat\", \"Bowie]\"]> but was:<[\"[Wrist Watch\", \"Pelican]\"]>\n");
    }

    
    private Check<ImmutableTestDto, Integer> intCheck(Consumer<IntegerAssert> consumer) { 
    	return (field, softly, value) -> consumer.accept(softly.assertThat(value));
    }
    
    @Test
    public void customTypeCheck() {
    	
        ImmutableTestDto dto = defaultObjectUnderTest().withYearOfBirth(1906).create();
        
        allChecks()
            .check(YEAR_OF_BIRTH, intCheck(yob -> yob
            		.isNotZero()
            		.isGreaterThanOrEqualTo(1906)))
         // The following does not compile because type safety
         // .check(NAME, intCheck(name -> name.isNotZero()))		
            .check(dto);
    }
    
    private Builder defaultObjectUnderTest() {
        return ImmutableTestDto.build()
                .withName("Bob")
                .withYearOfBirth(1905)
                .withPossesions(asList("Hat", "Bowie"));
    }

    private Checker<ImmutableTestDto> allChecks() {
        return new Checker<ImmutableTestDto>()
                .ensuring(NAME, name -> name.isEqualTo("Bob"))
                .ensuring(YEAR_OF_BIRTH, yob -> yob.isEqualTo(1905))
                .ensuring(POSESSIONS, possessions -> possessions.isEqualTo(asList("Hat", "Bowie")));
    }
}