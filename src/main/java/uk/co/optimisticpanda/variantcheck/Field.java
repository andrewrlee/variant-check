package uk.co.optimisticpanda.variantcheck;

import java.util.Objects;
import java.util.function.Function;

public class Field<S, T> {
	
	private final String name;
	private final Function<S, T> extractor;
	
	public Field(String name, Function<S, T> extractor) {
		this.name = name;
		this.extractor = extractor;
	}
	
	public String name() {
		return name;
	}
	
	public T extract(S s) {
		return extractor.apply(s);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field<?, ?> other = (Field<?, ?>) obj;
		return Objects.equals(name, other.name);
	}
}
