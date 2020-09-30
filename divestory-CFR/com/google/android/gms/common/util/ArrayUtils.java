/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> T[] appendToArray(T[] arrobject, T t) {
        if (arrobject == null) {
            if (t == null) throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
        }
        arrobject = arrobject == null ? (Object[])Array.newInstance(t.getClass(), 1) : Arrays.copyOf(arrobject, arrobject.length + 1);
        arrobject[arrobject.length - 1] = t;
        return arrobject;
    }

    public static <T> T[] concat(T[] ... arrT) {
        int n;
        if (arrT.length == 0) {
            return (Object[])Array.newInstance(arrT.getClass(), 0);
        }
        int n2 = 0;
        for (n = 0; n < arrT.length; n2 += arrT[n].length, ++n) {
        }
        T[] arrT2 = Arrays.copyOf(arrT[0], n2);
        n2 = arrT[0].length;
        n = 1;
        while (n < arrT.length) {
            T[] arrT3 = arrT[n];
            System.arraycopy(arrT3, 0, arrT2, n2, arrT3.length);
            n2 += arrT3.length;
            ++n;
        }
        return arrT2;
    }

    public static byte[] concatByteArrays(byte[] ... arrby) {
        int n;
        if (arrby.length == 0) {
            return new byte[0];
        }
        int n2 = 0;
        for (n = 0; n < arrby.length; n2 += arrby[n].length, ++n) {
        }
        byte[] arrby2 = Arrays.copyOf(arrby[0], n2);
        n = arrby[0].length;
        n2 = 1;
        while (n2 < arrby.length) {
            byte[] arrby3 = arrby[n2];
            System.arraycopy(arrby3, 0, arrby2, n, arrby3.length);
            n += arrby3.length;
            ++n2;
        }
        return arrby2;
    }

    public static boolean contains(int[] arrn, int n) {
        if (arrn == null) {
            return false;
        }
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrn[n3] == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public static <T> boolean contains(T[] arrT, T t) {
        int n;
        block2 : {
            int n2 = arrT != null ? arrT.length : 0;
            for (n = 0; n < n2; ++n) {
                if (!Objects.equal(arrT[n], t)) {
                    continue;
                }
                break block2;
            }
            n = -1;
        }
        if (n < 0) return false;
        return true;
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <T> T[] removeAll(T[] arrobject, T ... arrT) {
        int n;
        int n2;
        if (arrobject == null) {
            return null;
        }
        if (arrT == null) return Arrays.copyOf(arrobject, arrobject.length);
        if (arrT.length == 0) {
            return Arrays.copyOf(arrobject, arrobject.length);
        }
        Object[] arrobject2 = (Object[])Array.newInstance(arrT.getClass().getComponentType(), arrobject.length);
        int n3 = arrT.length;
        if (n3 == 1) {
            int n4 = arrobject.length;
            n = 0;
            n3 = 0;
            do {
                n2 = n3;
                if (n < n4) {
                    T t = arrobject[n];
                    n2 = n3;
                    if (!Objects.equal(arrT[0], t)) {
                        arrobject2[n3] = t;
                        n2 = n3 + 1;
                    }
                    ++n;
                    n3 = n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            int n5 = arrobject.length;
            n3 = 0;
            for (n = 0; n < n5; ++n) {
                T t = arrobject[n];
                n2 = n3;
                if (!ArrayUtils.contains(arrT, t)) {
                    arrobject2[n3] = t;
                    n2 = n3 + 1;
                }
                n3 = n2;
            }
            n2 = n3;
        }
        if (arrobject2 == null) {
            return null;
        }
        arrobject = arrobject2;
        if (n2 == arrobject2.length) return arrobject;
        return Arrays.copyOf(arrobject2, n2);
    }

    public static <T> ArrayList<T> toArrayList(T[] arrT) {
        int n = arrT.length;
        ArrayList<T> arrayList = new ArrayList<T>(n);
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(arrT[n2]);
            ++n2;
        }
        return arrayList;
    }

    public static int[] toPrimitiveArray(Collection<Integer> object) {
        int n = 0;
        if (object == null) return new int[0];
        if (object.size() == 0) {
            return new int[0];
        }
        int[] arrn = new int[object.size()];
        object = object.iterator();
        while (object.hasNext()) {
            arrn[n] = (Integer)object.next();
            ++n;
        }
        return arrn;
    }

    public static Integer[] toWrapperArray(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        int n = arrn.length;
        Integer[] arrinteger = new Integer[n];
        int n2 = 0;
        while (n2 < n) {
            arrinteger[n2] = arrn[n2];
            ++n2;
        }
        return arrinteger;
    }

    public static void writeArray(StringBuilder stringBuilder, double[] arrd) {
        int n = arrd.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(arrd[n2]));
            ++n2;
        }
    }

    public static void writeArray(StringBuilder stringBuilder, float[] arrf) {
        int n = arrf.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(arrf[n2]));
            ++n2;
        }
    }

    public static void writeArray(StringBuilder stringBuilder, int[] arrn) {
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(arrn[n2]));
            ++n2;
        }
    }

    public static void writeArray(StringBuilder stringBuilder, long[] arrl) {
        int n = arrl.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(arrl[n2]));
            ++n2;
        }
    }

    public static <T> void writeArray(StringBuilder stringBuilder, T[] arrT) {
        int n = arrT.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(arrT[n2]);
            ++n2;
        }
    }

    public static void writeArray(StringBuilder stringBuilder, boolean[] arrbl) {
        int n = arrbl.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(arrbl[n2]));
            ++n2;
        }
    }

    public static void writeStringArray(StringBuilder stringBuilder, String[] arrstring) {
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"");
            stringBuilder.append(arrstring[n2]);
            stringBuilder.append("\"");
            ++n2;
        }
    }
}

