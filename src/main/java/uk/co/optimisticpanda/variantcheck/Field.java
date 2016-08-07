package uk.co.optimisticpanda.variantcheck;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Field<S, T> {
	
	private final String name;
	private final Function<S, T> extractor;
	private final BiConsumer<S, T> applier;
	
	public Field(String name, Function<S, T> extractor, BiConsumer<S, T> applier) {
		this.name = name;
		this.extractor = extractor;
		this.applier = applier;
	}
	
	public String name() {
		return name;
	}
	
	public T extract(S s) {
		return extractor.apply(s);
	}
	
	public void apply(S s, T t) {
		applier.accept(s, t);
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
		Field<S, T> other = (Field<S, T>) obj;
		return Objects.equals(name, other.name);
	}
}
