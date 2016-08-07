package uk.co.optimisticpanda.variantcheck;

import java.util.List;

public class TestDto {
	private String name;
	private int yearOfBirth;
	private List<String> possesions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public List<String> getPossesions() {
		return possesions;
	}

	public void setPossesions(List<String> possesions) {
		this.possesions = possesions;
	}

	@Override
	public String toString() {
		return "TestDto [name=" + name + ", yearOfBirth=" + yearOfBirth
				+ ", possesions=" + possesions + "]";
	}
}