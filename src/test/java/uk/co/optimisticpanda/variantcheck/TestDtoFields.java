package uk.co.optimisticpanda.variantcheck;

import java.util.List;
public class TestDtoFields {

	public static final MutableField<TestDto, String> NAME = new MutableField<>(
			"name", TestDto::getName, TestDto::setName);
	
	public static final MutableField<TestDto, Integer> YEAR_OF_BIRTH = new MutableField<>(
			"yearOfBirth", TestDto::getYearOfBirth, TestDto::setYearOfBirth);
	
	public static final MutableField<TestDto,List<String>> POSESSIONS = new MutableField<>(
			"possessions", TestDto::getPossesions, TestDto::setPossesions);
		
}
