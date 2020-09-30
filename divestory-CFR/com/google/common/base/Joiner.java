/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Joiner {
    private final String separator;

    private Joiner(Joiner joiner) {
        this.separator = joiner.separator;
    }

    private Joiner(String string2) {
        this.separator = Preconditions.checkNotNull(string2);
    }

    private static Iterable<Object> iterable(final Object object, final Object object2, final Object[] arrobject) {
        Preconditions.checkNotNull(arrobject);
        return new AbstractList<Object>(){

            @Override
            public Object get(int n) {
                if (n == 0) return object;
                if (n == 1) return object2;
                return arrobject[n - 2];
            }

            @Override
            public int size() {
                return arrobject.length + 2;
            }
        };
    }

    public static Joiner on(char c) {
        return new Joiner(String.valueOf(c));
    }

    public static Joiner on(String string2) {
        return new Joiner(string2);
    }

    public <A extends Appendable> A appendTo(A a, Iterable<?> iterable) throws IOException {
        return this.appendTo(a, iterable.iterator());
    }

    public final <A extends Appendable> A appendTo(A a, @NullableDecl Object object, @NullableDecl Object object2, Object ... arrobject) throws IOException {
        return this.appendTo(a, Joiner.iterable(object, object2, arrobject));
    }

    public <A extends Appendable> A appendTo(A a, Iterator<?> iterator2) throws IOException {
        Preconditions.checkNotNull(a);
        if (!iterator2.hasNext()) return a;
        a.append(this.toString(iterator2.next()));
        while (iterator2.hasNext()) {
            a.append(this.separator);
            a.append(this.toString(iterator2.next()));
        }
        return a;
    }

    public final <A extends Appendable> A appendTo(A a, Object[] arrobject) throws IOException {
        return this.appendTo(a, Arrays.asList(arrobject));
    }

    public final StringBuilder appendTo(StringBuilder stringBuilder, Iterable<?> iterable) {
        return this.appendTo(stringBuilder, iterable.iterator());
    }

    public final StringBuilder appendTo(StringBuilder stringBuilder, @NullableDecl Object object, @NullableDecl Object object2, Object ... arrobject) {
        return this.appendTo(stringBuilder, (Iterable<?>)Joiner.iterable(object, object2, arrobject));
    }

    public final StringBuilder appendTo(StringBuilder stringBuilder, Iterator<?> iterator2) {
        try {
            this.appendTo((A)stringBuilder, iterator2);
            return stringBuilder;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    public final StringBuilder appendTo(StringBuilder stringBuilder, Object[] arrobject) {
        return this.appendTo(stringBuilder, (Iterable<?>)Arrays.asList(arrobject));
    }

    public final String join(Iterable<?> iterable) {
        return this.join(iterable.iterator());
    }

    public final String join(@NullableDecl Object object, @NullableDecl Object object2, Object ... arrobject) {
        return this.join(Joiner.iterable(object, object2, arrobject));
    }

    public final String join(Iterator<?> iterator2) {
        return this.appendTo(new StringBuilder(), iterator2).toString();
    }

    public final String join(Object[] arrobject) {
        return this.join(Arrays.asList(arrobject));
    }

    public Joiner skipNulls() {
        return new Joiner(this){

            @Override
            public <A extends Appendable> A appendTo(A a, Iterator<?> iterator2) throws IOException {
                Object obj;
                Preconditions.checkNotNull(a, "appendable");
                Preconditions.checkNotNull(iterator2, "parts");
                while (iterator2.hasNext()) {
                    obj = iterator2.next();
                    if (obj == null) continue;
                    a.append(Joiner.this.toString(obj));
                    break;
                }
                while (iterator2.hasNext()) {
                    obj = iterator2.next();
                    if (obj == null) continue;
                    a.append(Joiner.this.separator);
                    a.append(Joiner.this.toString(obj));
                }
                return a;
            }

            @Override
            public Joiner useForNull(String string2) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            @Override
            public MapJoiner withKeyValueSeparator(String string2) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    CharSequence toString(Object object) {
        Preconditions.checkNotNull(object);
        if (!(object instanceof CharSequence)) return object.toString();
        return (CharSequence)object;
    }

    public Joiner useForNull(final String string2) {
        Preconditions.checkNotNull(string2);
        return new Joiner(this){

            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            @Override
            CharSequence toString(@NullableDecl Object object) {
                if (object != null) return Joiner.this.toString(object);
                return string2;
            }

            @Override
            public Joiner useForNull(String string22) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char c) {
        return this.withKeyValueSeparator(String.valueOf(c));
    }

    public MapJoiner withKeyValueSeparator(String string2) {
        return new MapJoiner(this, string2);
    }

    public static final class MapJoiner {
        private final Joiner joiner;
        private final String keyValueSeparator;

        private MapJoiner(Joiner joiner, String string2) {
            this.joiner = joiner;
            this.keyValueSeparator = Preconditions.checkNotNull(string2);
        }

        public <A extends Appendable> A appendTo(A a, Iterable<? extends Map.Entry<?, ?>> iterable) throws IOException {
            return this.appendTo(a, iterable.iterator());
        }

        public <A extends Appendable> A appendTo(A a, Iterator<? extends Map.Entry<?, ?>> iterator2) throws IOException {
            Preconditions.checkNotNull(a);
            if (!iterator2.hasNext()) return a;
            Map.Entry<?, ?> entry = iterator2.next();
            a.append(this.joiner.toString(entry.getKey()));
            a.append(this.keyValueSeparator);
            a.append(this.joiner.toString(entry.getValue()));
            while (iterator2.hasNext()) {
                a.append(this.joiner.separator);
                entry = iterator2.next();
                a.append(this.joiner.toString(entry.getKey()));
                a.append(this.keyValueSeparator);
                a.append(this.joiner.toString(entry.getValue()));
            }
            return a;
        }

        public <A extends Appendable> A appendTo(A a, Map<?, ?> map) throws IOException {
            return this.appendTo(a, map.entrySet());
        }

        public StringBuilder appendTo(StringBuilder stringBuilder, Iterable<? extends Map.Entry<?, ?>> iterable) {
            return this.appendTo(stringBuilder, iterable.iterator());
        }

        public StringBuilder appendTo(StringBuilder stringBuilder, Iterator<? extends Map.Entry<?, ?>> iterator2) {
            try {
                this.appendTo((A)stringBuilder, iterator2);
                return stringBuilder;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        public StringBuilder appendTo(StringBuilder stringBuilder, Map<?, ?> map) {
            return this.appendTo(stringBuilder, (Iterable<? extends Map.Entry<?, ?>>)map.entrySet());
        }

        public String join(Iterable<? extends Map.Entry<?, ?>> iterable) {
            return this.join(iterable.iterator());
        }

        public String join(Iterator<? extends Map.Entry<?, ?>> iterator2) {
            return this.appendTo(new StringBuilder(), iterator2).toString();
        }

        public String join(Map<?, ?> map) {
            return this.join(map.entrySet());
        }

        public MapJoiner useForNull(String string2) {
            return new MapJoiner(this.joiner.useForNull(string2), this.keyValueSeparator);
        }
    }

}

