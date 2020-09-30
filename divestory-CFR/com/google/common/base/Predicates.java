/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.CommonMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.Function;
import com.google.common.base.JdkPattern;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Predicates {
    private Predicates() {
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new AndPredicate(Predicates.asList(Preconditions.checkNotNull(predicate), Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> iterable) {
        return new AndPredicate(Predicates.defensiveCopy(iterable));
    }

    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<? super T> ... arrpredicate) {
        return new AndPredicate(Predicates.defensiveCopy(arrpredicate));
    }

    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return Arrays.asList(predicate, predicate2);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function);
    }

    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(new JdkPattern(pattern));
    }

    public static Predicate<CharSequence> containsPattern(String string2) {
        return new ContainsPatternFromStringPredicate(string2);
    }

    static <T> List<T> defensiveCopy(Iterable<T> object) {
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(Preconditions.checkNotNull(object.next()));
        }
        return arrayList;
    }

    private static <T> List<T> defensiveCopy(T ... arrT) {
        return Predicates.defensiveCopy(Arrays.asList(arrT));
    }

    public static <T> Predicate<T> equalTo(@NullableDecl T object) {
        if (object != null) return new IsEqualToPredicate(object);
        return Predicates.isNull();
    }

    public static <T> Predicate<T> in(Collection<? extends T> collection) {
        return new InPredicate(collection);
    }

    public static Predicate<Object> instanceOf(Class<?> class_) {
        return new InstanceOfPredicate(class_);
    }

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<T>(predicate);
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new OrPredicate(Predicates.asList(Preconditions.checkNotNull(predicate), Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> iterable) {
        return new OrPredicate(Predicates.defensiveCopy(iterable));
    }

    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<? super T> ... arrpredicate) {
        return new OrPredicate(Predicates.defensiveCopy(arrpredicate));
    }

    public static Predicate<Class<?>> subtypeOf(Class<?> class_) {
        return new SubtypeOfPredicate(class_);
    }

    private static String toStringHelper(String object, Iterable<?> iterable) {
        StringBuilder stringBuilder = new StringBuilder("Predicates.");
        stringBuilder.append((String)object);
        stringBuilder.append('(');
        object = iterable.iterator();
        boolean bl = true;
        do {
            if (!object.hasNext()) {
                stringBuilder.append(')');
                return stringBuilder.toString();
            }
            iterable = object.next();
            if (!bl) {
                stringBuilder.append(',');
            }
            stringBuilder.append(iterable);
            bl = false;
        } while (true);
    }

    private static class AndPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final List<? extends Predicate<? super T>> components;

        private AndPredicate(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override
        public boolean apply(@NullableDecl T t) {
            int n = 0;
            while (n < this.components.size()) {
                if (!this.components.get(n).apply(t)) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof AndPredicate)) return false;
            object = (AndPredicate)object;
            return this.components.equals(((AndPredicate)object).components);
        }

        public int hashCode() {
            return this.components.hashCode() + 306654252;
        }

        public String toString() {
            return Predicates.toStringHelper("and", this.components);
        }
    }

    private static class CompositionPredicate<A, B>
    implements Predicate<A>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Function<A, ? extends B> f;
        final Predicate<B> p;

        private CompositionPredicate(Predicate<B> predicate, Function<A, ? extends B> function) {
            this.p = Preconditions.checkNotNull(predicate);
            this.f = Preconditions.checkNotNull(function);
        }

        @Override
        public boolean apply(@NullableDecl A a) {
            return this.p.apply(this.f.apply(a));
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof CompositionPredicate;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (CompositionPredicate)object;
            bl3 = bl;
            if (!this.f.equals(((CompositionPredicate)object).f)) return bl3;
            bl3 = bl;
            if (!this.p.equals(((CompositionPredicate)object).p)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.p.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.p);
            stringBuilder.append("(");
            stringBuilder.append(this.f);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ContainsPatternFromStringPredicate
    extends ContainsPatternPredicate {
        private static final long serialVersionUID = 0L;

        ContainsPatternFromStringPredicate(String string2) {
            super(Platform.compilePattern(string2));
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.containsPattern(");
            stringBuilder.append(this.pattern.pattern());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ContainsPatternPredicate
    implements Predicate<CharSequence>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final CommonPattern pattern;

        ContainsPatternPredicate(CommonPattern commonPattern) {
            this.pattern = Preconditions.checkNotNull(commonPattern);
        }

        @Override
        public boolean apply(CharSequence charSequence) {
            return this.pattern.matcher(charSequence).find();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof ContainsPatternPredicate;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (ContainsPatternPredicate)object;
            bl3 = bl;
            if (!Objects.equal(this.pattern.pattern(), ((ContainsPatternPredicate)object).pattern.pattern())) return bl3;
            bl3 = bl;
            if (this.pattern.flags() != ((ContainsPatternPredicate)object).pattern.flags()) return bl3;
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.pattern.pattern(), this.pattern.flags());
        }

        public String toString() {
            String string2 = MoreObjects.toStringHelper(this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.contains(");
            stringBuilder.append(string2);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class InPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Collection<?> target;

        private InPredicate(Collection<?> collection) {
            this.target = Preconditions.checkNotNull(collection);
        }

        @Override
        public boolean apply(@NullableDecl T t) {
            try {
                return this.target.contains(t);
            }
            catch (ClassCastException | NullPointerException runtimeException) {
                return false;
            }
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof InPredicate)) return false;
            object = (InPredicate)object;
            return this.target.equals(((InPredicate)object).target);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.in(");
            stringBuilder.append(this.target);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class InstanceOfPredicate
    implements Predicate<Object>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Class<?> clazz;

        private InstanceOfPredicate(Class<?> class_) {
            this.clazz = Preconditions.checkNotNull(class_);
        }

        @Override
        public boolean apply(@NullableDecl Object object) {
            return this.clazz.isInstance(object);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof InstanceOfPredicate;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (InstanceOfPredicate)object;
            bl3 = bl;
            if (this.clazz != ((InstanceOfPredicate)object).clazz) return bl3;
            return true;
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.instanceOf(");
            stringBuilder.append(this.clazz.getName());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class IsEqualToPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final T target;

        private IsEqualToPredicate(T t) {
            this.target = t;
        }

        @Override
        public boolean apply(T t) {
            return this.target.equals(t);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof IsEqualToPredicate)) return false;
            object = (IsEqualToPredicate)object;
            return this.target.equals(((IsEqualToPredicate)object).target);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.equalTo(");
            stringBuilder.append(this.target);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class NotPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Predicate<T> predicate;

        NotPredicate(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public boolean apply(@NullableDecl T t) {
            return this.predicate.apply(t) ^ true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof NotPredicate)) return false;
            object = (NotPredicate)object;
            return this.predicate.equals(((NotPredicate)object).predicate);
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.not(");
            stringBuilder.append(this.predicate);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static abstract class ObjectPredicate
    extends Enum<ObjectPredicate>
    implements Predicate<Object> {
        private static final /* synthetic */ ObjectPredicate[] $VALUES;
        public static final /* enum */ ObjectPredicate ALWAYS_FALSE;
        public static final /* enum */ ObjectPredicate ALWAYS_TRUE;
        public static final /* enum */ ObjectPredicate IS_NULL;
        public static final /* enum */ ObjectPredicate NOT_NULL;

        static {
            ObjectPredicate objectPredicate;
            ALWAYS_TRUE = new ObjectPredicate(){

                @Override
                public boolean apply(@NullableDecl Object object) {
                    return true;
                }

                public String toString() {
                    return "Predicates.alwaysTrue()";
                }
            };
            ALWAYS_FALSE = new ObjectPredicate(){

                @Override
                public boolean apply(@NullableDecl Object object) {
                    return false;
                }

                public String toString() {
                    return "Predicates.alwaysFalse()";
                }
            };
            IS_NULL = new ObjectPredicate(){

                @Override
                public boolean apply(@NullableDecl Object object) {
                    if (object != null) return false;
                    return true;
                }

                public String toString() {
                    return "Predicates.isNull()";
                }
            };
            NOT_NULL = objectPredicate = new ObjectPredicate(){

                @Override
                public boolean apply(@NullableDecl Object object) {
                    if (object == null) return false;
                    return true;
                }

                public String toString() {
                    return "Predicates.notNull()";
                }
            };
            $VALUES = new ObjectPredicate[]{ALWAYS_TRUE, ALWAYS_FALSE, IS_NULL, objectPredicate};
        }

        public static ObjectPredicate valueOf(String string2) {
            return Enum.valueOf(ObjectPredicate.class, string2);
        }

        public static ObjectPredicate[] values() {
            return (ObjectPredicate[])$VALUES.clone();
        }

        <T> Predicate<T> withNarrowedType() {
            return this;
        }

    }

    private static class OrPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final List<? extends Predicate<? super T>> components;

        private OrPredicate(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override
        public boolean apply(@NullableDecl T t) {
            int n = 0;
            while (n < this.components.size()) {
                if (this.components.get(n).apply(t)) {
                    return true;
                }
                ++n;
            }
            return false;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof OrPredicate)) return false;
            object = (OrPredicate)object;
            return this.components.equals(((OrPredicate)object).components);
        }

        public int hashCode() {
            return this.components.hashCode() + 87855567;
        }

        public String toString() {
            return Predicates.toStringHelper("or", this.components);
        }
    }

    private static class SubtypeOfPredicate
    implements Predicate<Class<?>>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Class<?> clazz;

        private SubtypeOfPredicate(Class<?> class_) {
            this.clazz = Preconditions.checkNotNull(class_);
        }

        @Override
        public boolean apply(Class<?> class_) {
            return this.clazz.isAssignableFrom(class_);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof SubtypeOfPredicate;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (SubtypeOfPredicate)object;
            bl3 = bl;
            if (this.clazz != ((SubtypeOfPredicate)object).clazz) return bl3;
            return true;
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Predicates.subtypeOf(");
            stringBuilder.append(this.clazz.getName());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

