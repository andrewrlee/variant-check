package uk.co.optimisticpanda.variantcheck;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.SoftAssertions;

public class Checker<S> {
	private final Map<String, FieldAndCheck<S, ? extends Object>> fields = new LinkedHashMap<>();

	public <T> Checker<S> ensuring(Field<S, T> field, Consumer<AbstractObjectAssert<?, T>> test) {
		fields.put(field.name(), new FieldAndCheck<S, T>(field, assrt(test)));
		return this;
	}

	public <T> Checker<S> check(Field<S, T> field, Check<S, T> check) {
		fields.put(field.name(), new FieldAndCheck<S, T>(field, check));
		return this;
	}
	
	public <T> Checker<S> ignoring(Field<S, T> field) {
		fields.remove(field.name());
		return this;
	}

	public void check(Supplier<S> supplier) {
		check(supplier.get());
	}
	
	public void check(S s) {
		SoftAssertions softly = new SoftAssertions();
		fields.values().forEach(check -> {
			Object valueToCheck = check.field.extract(s);
			((FieldAndCheck<S, Object>)check).check.check(((Field<S, Object>) check.field), softly, valueToCheck);
		});
		softly.assertAll();
	}
	
	private static class FieldAndCheck<S, T> {
		private final Field<S, T> field;
		private final Check<S, T> check;

		private FieldAndCheck(Field<S, T> field, Check<S, T> check) {
			this.field = field;
			this.check = check;
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
			FieldAndCheck<S, T> other = (FieldAndCheck<S, T>) obj;
			return Objects.equals(field, other.field);
		}
	}

	public interface Check<S, T> {
		void check(Field<S, T> field, SoftAssertions softly, T value);
	}

	public static <S, T> Check<S, T> value(Consumer<T> test) {
		return new CheckValue<S, T>(test);
	}

	public static <S, T> Check<S, T> assrt(Consumer<AbstractObjectAssert<?, T>> test) {
		return new CheckObjectAssert<S, T>(test);
	}
	
	private static class CheckObjectAssert<S, T> implements Check<S, T>{
		private final Consumer<AbstractObjectAssert<?, T>> test;
		private CheckObjectAssert(Consumer<AbstractObjectAssert<?, T>> test) {
			this.test = test;
		}
		@Override
		public void check(Field<S, T> field, SoftAssertions softly, T value) {
			test.accept(softly.assertThat(value).describedAs(field.name()));
		}
	}
	
	private static class CheckValue<S, T> implements Check<S, T> {
		private final Consumer<T> test;
		private CheckValue(Consumer<T> test) {
			this.test = test;
		}
		@Override
		public void check(Field<S, T> field, SoftAssertions softly, T value) {
			test.accept(value);
		}
	}
}
