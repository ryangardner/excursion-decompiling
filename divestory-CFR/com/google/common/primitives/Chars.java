/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Chars {
    public static final int BYTES = 2;

    private Chars() {
    }

    public static List<Character> asList(char ... arrc) {
        if (arrc.length != 0) return new CharArrayAsList(arrc);
        return Collections.emptyList();
    }

    public static char checkedCast(long l) {
        char c = (char)l;
        boolean bl = (long)c == l;
        Preconditions.checkArgument(bl, "Out of range: %s", l);
        return c;
    }

    public static int compare(char c, char c2) {
        return c - c2;
    }

    public static char[] concat(char[] ... arrc) {
        int n;
        int n2 = arrc.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrc[n].length, ++n) {
        }
        char[] arrc2 = new char[n3];
        n2 = arrc.length;
        n3 = 0;
        n = 0;
        while (n3 < n2) {
            char[] arrc3 = arrc[n3];
            System.arraycopy(arrc3, 0, arrc2, n, arrc3.length);
            n += arrc3.length;
            ++n3;
        }
        return arrc2;
    }

    public static char constrainToRange(char c, char c2, char c3) {
        boolean bl = c2 <= c3;
        Preconditions.checkArgument(bl, "min (%s) must be less than or equal to max (%s)", c2, c3);
        if (c < c2) {
            c = c2;
            return c;
        }
        if (c >= c3) return c = c3;
        return c;
    }

    public static boolean contains(char[] arrc, char c) {
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrc[n2] == c) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static char[] ensureCapacity(char[] arrc, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        char[] arrc2 = arrc;
        if (arrc.length >= n) return arrc2;
        return Arrays.copyOf(arrc, n + n2);
    }

    public static char fromByteArray(byte[] arrby) {
        boolean bl = arrby.length >= 2;
        Preconditions.checkArgument(bl, "array too small: %s < %s", arrby.length, 2);
        return Chars.fromBytes(arrby[0], arrby[1]);
    }

    public static char fromBytes(byte by, byte by2) {
        return (char)(by << 8 | by2 & 255);
    }

    public static int hashCode(char c) {
        return c;
    }

    public static int indexOf(char[] arrc, char c) {
        return Chars.indexOf(arrc, c, 0, arrc.length);
    }

    private static int indexOf(char[] arrc, char c, int n, int n2) {
        while (n < n2) {
            if (arrc[n] == c) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(char[] arrc, char[] arrc2) {
        Preconditions.checkNotNull(arrc, "array");
        Preconditions.checkNotNull(arrc2, "target");
        if (arrc2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrc.length - arrc2.length + 1) {
            int n2 = 0;
            while (n2 < arrc2.length) {
                if (arrc[n + n2] != arrc2[n2]) {
                    ++n;
                    continue block0;
                }
                ++n2;
            }
            return n;
            break;
        }
        return -1;
    }

    public static String join(String string2, char ... arrc) {
        Preconditions.checkNotNull(string2);
        int n = arrc.length;
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string2.length() * (n - 1) + n);
        stringBuilder.append(arrc[0]);
        int n2 = 1;
        while (n2 < n) {
            stringBuilder.append(string2);
            stringBuilder.append(arrc[n2]);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(char[] arrc, char c) {
        return Chars.lastIndexOf(arrc, c, 0, arrc.length);
    }

    private static int lastIndexOf(char[] arrc, char c, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrc[n2] == c) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static Comparator<char[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static char max(char ... arrc) {
        char c = arrc.length;
        int n = 1;
        boolean bl = c > '\u0000';
        Preconditions.checkArgument(bl);
        char c2 = c = arrc[0];
        while (n < arrc.length) {
            c = c2;
            if (arrc[n] > c2) {
                c = arrc[n];
            }
            ++n;
            c2 = c;
        }
        return c2;
    }

    public static char min(char ... arrc) {
        char c = arrc.length;
        int n = 1;
        boolean bl = c > '\u0000';
        Preconditions.checkArgument(bl);
        char c2 = c = arrc[0];
        while (n < arrc.length) {
            c = c2;
            if (arrc[n] < c2) {
                c = arrc[n];
            }
            ++n;
            c2 = c;
        }
        return c2;
    }

    public static void reverse(char[] arrc) {
        Preconditions.checkNotNull(arrc);
        Chars.reverse(arrc, 0, arrc.length);
    }

    public static void reverse(char[] arrc, int n, int n2) {
        Preconditions.checkNotNull(arrc);
        Preconditions.checkPositionIndexes(n, n2, arrc.length);
        --n2;
        while (n < n2) {
            char c = arrc[n];
            arrc[n] = arrc[n2];
            arrc[n2] = c;
            ++n;
            --n2;
        }
    }

    public static char saturatedCast(long l) {
        if (l > 65535L) {
            return '\uffff';
        }
        if (l >= 0L) return (char)l;
        return '\u0000';
    }

    public static void sortDescending(char[] arrc) {
        Preconditions.checkNotNull(arrc);
        Chars.sortDescending(arrc, 0, arrc.length);
    }

    public static void sortDescending(char[] arrc, int n, int n2) {
        Preconditions.checkNotNull(arrc);
        Preconditions.checkPositionIndexes(n, n2, arrc.length);
        Arrays.sort(arrc, n, n2);
        Chars.reverse(arrc, n, n2);
    }

    public static char[] toArray(Collection<Character> arrc) {
        if (arrc instanceof CharArrayAsList) {
            return ((CharArrayAsList)arrc).toCharArray();
        }
        Object[] arrobject = arrc.toArray();
        int n = arrobject.length;
        arrc = new char[n];
        int n2 = 0;
        while (n2 < n) {
            arrc[n2] = ((Character)Preconditions.checkNotNull(arrobject[n2])).charValue();
            ++n2;
        }
        return arrc;
    }

    public static byte[] toByteArray(char c) {
        return new byte[]{(byte)(c >> 8), (byte)c};
    }

    private static class CharArrayAsList
    extends AbstractList<Character>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final char[] array;
        final int end;
        final int start;

        CharArrayAsList(char[] arrc) {
            this(arrc, 0, arrc.length);
        }

        CharArrayAsList(char[] arrc, int n, int n2) {
            this.array = arrc;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Character)) return false;
            if (Chars.indexOf(this.array, ((Character)object).charValue(), this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof CharArrayAsList)) return super.equals(object);
            object = (CharArrayAsList)object;
            int n = this.size();
            if (((CharArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((CharArrayAsList)object).array[((CharArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.array[this.start + n]);
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Chars.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Character)) return -1;
            int n = Chars.indexOf(this.array, ((Character)object).charValue(), this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Character)) return -1;
            int n = Chars.lastIndexOf(this.array, ((Character)object).charValue(), this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Character set(int n, Character c) {
            Preconditions.checkElementIndex(n, this.size());
            char[] arrc = this.array;
            int n2 = this.start;
            char c2 = arrc[n2 + n];
            arrc[n2 + n] = Preconditions.checkNotNull(c).charValue();
            return Character.valueOf(c2);
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Character> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            char[] arrc = this.array;
            int n3 = this.start;
            return new CharArrayAsList(arrc, n + n3, n3 + n2);
        }

        char[] toCharArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 3);
            stringBuilder.append('[');
            stringBuilder.append(this.array[this.start]);
            int n = this.start;
            do {
                if (++n >= this.end) {
                    stringBuilder.append(']');
                    return stringBuilder.toString();
                }
                stringBuilder.append(", ");
                stringBuilder.append(this.array[n]);
            } while (true);
        }
    }

    private static final class LexicographicalComparator
    extends Enum<LexicographicalComparator>
    implements Comparator<char[]> {
        private static final /* synthetic */ LexicographicalComparator[] $VALUES;
        public static final /* enum */ LexicographicalComparator INSTANCE;

        static {
            LexicographicalComparator lexicographicalComparator;
            INSTANCE = lexicographicalComparator = new LexicographicalComparator();
            $VALUES = new LexicographicalComparator[]{lexicographicalComparator};
        }

        public static LexicographicalComparator valueOf(String string2) {
            return Enum.valueOf(LexicographicalComparator.class, string2);
        }

        public static LexicographicalComparator[] values() {
            return (LexicographicalComparator[])$VALUES.clone();
        }

        @Override
        public int compare(char[] arrc, char[] arrc2) {
            int n = Math.min(arrc.length, arrc2.length);
            int n2 = 0;
            while (n2 < n) {
                int n3 = Chars.compare(arrc[n2], arrc2[n2]);
                if (n3 != 0) {
                    return n3;
                }
                ++n2;
            }
            return arrc.length - arrc2.length;
        }

        public String toString() {
            return "Chars.lexicographicalComparator()";
        }
    }

}

