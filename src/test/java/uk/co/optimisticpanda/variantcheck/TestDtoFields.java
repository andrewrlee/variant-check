package uk.co.optimisticpanda.variantcheck;

import java.util.List;
public class TestDtoFields {

	public static final Field<TestDto, String> NAME = new Field<>(
			"name", TestDto::getName, TestDto::setName);
	
	public static final Field<TestDto, Integer> YEAR_OF_BIRTH = new Field<>(
			"yearOfBirth", TestDto::getYearOfBirth, TestDto::setYearOfBirth);
	
	public static final Field<TestDto,List<String>> POSESSIONS = new Field<>(
			"possessions", TestDto::getPossesions, TestDto::setPossesions);
		
}
