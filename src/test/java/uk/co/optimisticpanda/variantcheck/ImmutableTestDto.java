package uk.co.optimisticpanda.variantcheck;

import java.util.ArrayList;
import java.util.List;

public class ImmutableTestDto {
	private final String name;
	private final int yearOfBirth;
	private final List<String> possesions;

	private ImmutableTestDto(String name, int yearOfBirth, List<String> possesions) {
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.possesions = possesions;
	}

	public static Builder build() {
		return new Builder();
	}
	
	public String getName() {
		return name;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public List<String> getPossesions() {
		return possesions;
	}

	@Override
	public String toString() {
		return "ImmutableTestDto [name=" + name + ", yearOfBirth=" + yearOfBirth
				+ ", possesions=" + possesions + "]";
	}
	
	public static class Builder {
		private String name;
		private int yearOfBirth;
		private List<String> possesions = new ArrayList<>();
		
		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withYearOfBirth(int yearOfBirth) {
			this.yearOfBirth = yearOfBirth;
			return this;
		}

		public Builder withPossesions(List<String> possesions) {
			this.possesions = possesions;
			return this;
		}
		
		public ImmutableTestDto create() {
			return new ImmutableTestDto(name, yearOfBirth, possesions);
		}
		
	}
}