package uk.co.optimisticpanda.variantcheck;

import java.util.List;
public class ImmutableTestDtoFields {

	public static final Field<ImmutableTestDto, String> NAME = new Field<>("name", ImmutableTestDto::getName);
	
	public static final Field<ImmutableTestDto, Integer> YEAR_OF_BIRTH = new Field<>("yearOfBirth", ImmutableTestDto::getYearOfBirth);
	
	public static final Field<ImmutableTestDto,List<String>> POSESSIONS = new Field<>("possessions", ImmutableTestDto::getPossesions);
		
}
