package uk.co.optimisticpanda.variantcheck;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class MutableField<S, T> extends Field<S, T> {
	
	private final BiConsumer<S, T> applier;
	
	public MutableField(String name, Function<S, T> extractor, BiConsumer<S, T> applier) {
		super(name, extractor);
		this.applier = applier;
	}
	
	public void apply(S s, T t) {
		applier.accept(s, t);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((applier == null) ? 0 : applier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MutableField<?, ?> other = (MutableField<?, ?>) obj;
		if (applier == null) {
			if (other.applier != null)
				return false;
		} else if (!applier.equals(other.applier))
			return false;
		return true;
	}

	
	
}