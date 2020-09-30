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
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Bytes {
    private Bytes() {
    }

    public static List<Byte> asList(byte ... arrby) {
        if (arrby.length != 0) return new ByteArrayAsList(arrby);
        return Collections.emptyList();
    }

    public static byte[] concat(byte[] ... arrby) {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrby[n].length, ++n) {
        }
        byte[] arrby2 = new byte[n3];
        n2 = arrby.length;
        n3 = 0;
        n = 0;
        while (n3 < n2) {
            byte[] arrby3 = arrby[n3];
            System.arraycopy(arrby3, 0, arrby2, n, arrby3.length);
            n += arrby3.length;
            ++n3;
        }
        return arrby2;
    }

    public static boolean contains(byte[] arrby, byte by) {
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrby[n2] == by) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static byte[] ensureCapacity(byte[] arrby, int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Invalid minLength: %s", n);
        bl2 = n2 >= 0 ? bl : false;
        Preconditions.checkArgument(bl2, "Invalid padding: %s", n2);
        byte[] arrby2 = arrby;
        if (arrby.length >= n) return arrby2;
        return Arrays.copyOf(arrby, n + n2);
    }

    public static int hashCode(byte by) {
        return by;
    }

    public static int indexOf(byte[] arrby, byte by) {
        return Bytes.indexOf(arrby, by, 0, arrby.length);
    }

    private static int indexOf(byte[] arrby, byte by, int n, int n2) {
        while (n < n2) {
            if (arrby[n] == by) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int indexOf(byte[] arrby, byte[] arrby2) {
        Preconditions.checkNotNull(arrby, "array");
        Preconditions.checkNotNull(arrby2, "target");
        if (arrby2.length == 0) {
            return 0;
        }
        int n = 0;
        block0 : while (n < arrby.length - arrby2.length + 1) {
            int n2 = 0;
            while (n2 < arrby2.length) {
                if (arrby[n + n2] != arrby2[n2]) {
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

    public static int lastIndexOf(byte[] arrby, byte by) {
        return Bytes.lastIndexOf(arrby, by, 0, arrby.length);
    }

    private static int lastIndexOf(byte[] arrby, byte by, int n, int n2) {
        --n2;
        while (n2 >= n) {
            if (arrby[n2] == by) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static void reverse(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        Bytes.reverse(arrby, 0, arrby.length);
    }

    public static void reverse(byte[] arrby, int n, int n2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkPositionIndexes(n, n2, arrby.length);
        --n2;
        while (n < n2) {
            byte by = arrby[n];
            arrby[n] = arrby[n2];
            arrby[n2] = by;
            ++n;
            --n2;
        }
    }

    public static byte[] toArray(Collection<? extends Number> arrobject) {
        if (arrobject instanceof ByteArrayAsList) {
            return ((ByteArrayAsList)arrobject).toByteArray();
        }
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        byte[] arrby = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            arrby[n2] = ((Number)Preconditions.checkNotNull(arrobject[n2])).byteValue();
            ++n2;
        }
        return arrby;
    }

    private static class ByteArrayAsList
    extends AbstractList<Byte>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final byte[] array;
        final int end;
        final int start;

        ByteArrayAsList(byte[] arrby) {
            this(arrby, 0, arrby.length);
        }

        ByteArrayAsList(byte[] arrby, int n, int n2) {
            this.array = arrby;
            this.start = n;
            this.end = n2;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Byte)) return false;
            if (Bytes.indexOf(this.array, (Byte)object, this.start, this.end) == -1) return false;
            return true;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ByteArrayAsList)) return super.equals(object);
            object = (ByteArrayAsList)object;
            int n = this.size();
            if (((ByteArrayAsList)object).size() != n) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                if (this.array[this.start + n2] != ((ByteArrayAsList)object).array[((ByteArrayAsList)object).start + n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }

        @Override
        public Byte get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public int hashCode() {
            int n = this.start;
            int n2 = 1;
            while (n < this.end) {
                n2 = n2 * 31 + Bytes.hashCode(this.array[n]);
                ++n;
            }
            return n2;
        }

        @Override
        public int indexOf(Object object) {
            if (!(object instanceof Byte)) return -1;
            int n = Bytes.indexOf(this.array, (Byte)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (!(object instanceof Byte)) return -1;
            int n = Bytes.lastIndexOf(this.array, (Byte)object, this.start, this.end);
            if (n < 0) return -1;
            return n - this.start;
        }

        @Override
        public Byte set(int n, Byte by) {
            Preconditions.checkElementIndex(n, this.size());
            byte[] arrby = this.array;
            int n2 = this.start;
            byte by2 = arrby[n2 + n];
            arrby[n2 + n] = Preconditions.checkNotNull(by);
            return by2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public List<Byte> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            byte[] arrby = this.array;
            int n3 = this.start;
            return new ByteArrayAsList(arrby, n + n3, n3 + n2);
        }

        byte[] toByteArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 5);
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

}

