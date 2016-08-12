package uk.co.optimisticpanda.variantcheck;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

// Populates simple mutable object
public class Generator<S> implements Supplier<S> {

	private final Map<String, FieldAndValue<S, ? extends Object>> fields = new LinkedHashMap<>();
	private final Supplier<S> factory;
	
	public Generator(Supplier<S> factory) {
		this.factory = factory;
	}
	
	public <T> Generator<S> with(MutableField<S, T> field, T val) {
		fields.put(field.name(), new FieldAndValue<S, T>(field, val));
		return this;
	}
	
	@Override
	public S get() {
		S s = factory.get();
		fields.values().forEach(value ->
			((MutableField<S, Object>)value.field).apply(s, value.value));
		return s;
	}
	
	private static class FieldAndValue<S, T> {
		private final MutableField<S, T> field;
		private final T value;

		private FieldAndValue(MutableField<S, T> field, T value) {
			this.field = field;
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(field);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FieldAndValue<S, ?> other = (FieldAndValue<S, ?>) obj;
			return Objects.equals(field, other.field);
		}
	}
}
