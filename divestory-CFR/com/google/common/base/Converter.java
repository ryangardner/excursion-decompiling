/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Converter<A, B>
implements Function<A, B> {
    private final boolean handleNullAutomatically;
    @LazyInit
    @MonotonicNonNullDecl
    private transient Converter<B, A> reverse;

    protected Converter() {
        this(true);
    }

    Converter(boolean bl) {
        this.handleNullAutomatically = bl;
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return this.doAndThen(converter);
    }

    @Deprecated
    @NullableDecl
    @Override
    public final B apply(@NullableDecl A a) {
        return this.convert(a);
    }

    @NullableDecl
    public final B convert(@NullableDecl A a) {
        return this.correctedDoForward(a);
    }

    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>(){

            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>(){
                    private final Iterator<? extends A> fromIterator;
                    {
                        this.fromIterator = iterable.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    @Override
                    public B next() {
                        return Converter.this.convert(this.fromIterator.next());
                    }

                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }

        };
    }

    @NullableDecl
    A correctedDoBackward(@NullableDecl B object) {
        if (!this.handleNullAutomatically) return this.doBackward(object);
        if (object == null) {
            object = null;
            return (A)object;
        }
        object = Preconditions.checkNotNull(this.doBackward(object));
        return (A)object;
    }

    @NullableDecl
    B correctedDoForward(@NullableDecl A object) {
        if (!this.handleNullAutomatically) return this.doForward(object);
        if (object == null) {
            object = null;
            return (B)object;
        }
        object = Preconditions.checkNotNull(this.doForward(object));
        return (B)object;
    }

    <C> Converter<A, C> doAndThen(Converter<B, C> converter) {
        return new ConverterComposition(this, Preconditions.checkNotNull(converter));
    }

    protected abstract A doBackward(B var1);

    protected abstract B doForward(A var1);

    @Override
    public boolean equals(@NullableDecl Object object) {
        return super.equals(object);
    }

    public Converter<B, A> reverse() {
        Converter<A, B> converter;
        Converter<A, B> converter2 = converter = this.reverse;
        if (converter != null) return converter2;
        converter2 = new ReverseConverter<A, B>(this);
        this.reverse = converter2;
        return converter2;
    }

    private static final class ConverterComposition<A, B, C>
    extends Converter<A, C>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Converter<A, B> first;
        final Converter<B, C> second;

        ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.first = converter;
            this.second = converter2;
        }

        @NullableDecl
        @Override
        A correctedDoBackward(@NullableDecl C c) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }

        @NullableDecl
        @Override
        C correctedDoForward(@NullableDecl A a) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a));
        }

        @Override
        protected A doBackward(C c) {
            throw new AssertionError();
        }

        @Override
        protected C doForward(A a) {
            throw new AssertionError();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof ConverterComposition;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (ConverterComposition)object;
            bl3 = bl;
            if (!this.first.equals(((ConverterComposition)object).first)) return bl3;
            bl3 = bl;
            if (!this.second.equals(((ConverterComposition)object).second)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.first.hashCode() * 31 + this.second.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.first);
            stringBuilder.append(".andThen(");
            stringBuilder.append(this.second);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class FunctionBasedConverter<A, B>
    extends Converter<A, B>
    implements Serializable {
        private final Function<? super B, ? extends A> backwardFunction;
        private final Function<? super A, ? extends B> forwardFunction;

        private FunctionBasedConverter(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.forwardFunction = Preconditions.checkNotNull(function);
            this.backwardFunction = Preconditions.checkNotNull(function2);
        }

        @Override
        protected A doBackward(B b) {
            return this.backwardFunction.apply(b);
        }

        @Override
        protected B doForward(A a) {
            return this.forwardFunction.apply(a);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof FunctionBasedConverter;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (FunctionBasedConverter)object;
            bl3 = bl;
            if (!this.forwardFunction.equals(((FunctionBasedConverter)object).forwardFunction)) return bl3;
            bl3 = bl;
            if (!this.backwardFunction.equals(((FunctionBasedConverter)object).backwardFunction)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Converter.from(");
            stringBuilder.append(this.forwardFunction);
            stringBuilder.append(", ");
            stringBuilder.append(this.backwardFunction);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class IdentityConverter<T>
    extends Converter<T, T>
    implements Serializable {
        static final IdentityConverter<?> INSTANCE = new IdentityConverter<T>();
        private static final long serialVersionUID = 0L;

        private IdentityConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        <S> Converter<T, S> doAndThen(Converter<T, S> converter) {
            return Preconditions.checkNotNull(converter, "otherConverter");
        }

        @Override
        protected T doBackward(T t) {
            return t;
        }

        @Override
        protected T doForward(T t) {
            return t;
        }

        public IdentityConverter<T> reverse() {
            return this;
        }

        public String toString() {
            return "Converter.identity()";
        }
    }

    private static final class ReverseConverter<A, B>
    extends Converter<B, A>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Converter<A, B> original;

        ReverseConverter(Converter<A, B> converter) {
            this.original = converter;
        }

        @NullableDecl
        @Override
        B correctedDoBackward(@NullableDecl A a) {
            return this.original.correctedDoForward(a);
        }

        @NullableDecl
        @Override
        A correctedDoForward(@NullableDecl B b) {
            return this.original.correctedDoBackward(b);
        }

        @Override
        protected B doBackward(A a) {
            throw new AssertionError();
        }

        @Override
        protected A doForward(B b) {
            throw new AssertionError();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof ReverseConverter)) return false;
            object = (ReverseConverter)object;
            return this.original.equals(((ReverseConverter)object).original);
        }

        public int hashCode() {
            return this.original.hashCode();
        }

        @Override
        public Converter<A, B> reverse() {
            return this.original;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.original);
            stringBuilder.append(".reverse()");
            return stringBuilder.toString();
        }
    }

}

