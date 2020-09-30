/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.util.SparseBooleanArray
 *  android.util.SparseIntArray
 *  android.util.SparseLongArray
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class SafeParcelWriter {
    private SafeParcelWriter() {
    }

    public static int beginObjectHeader(Parcel parcel) {
        return SafeParcelWriter.zza(parcel, 20293);
    }

    public static void finishObjectHeader(Parcel parcel, int n) {
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeBigDecimal(Parcel parcel, int n, BigDecimal bigDecimal, boolean bl) {
        if (bigDecimal == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeByteArray(bigDecimal.unscaledValue().toByteArray());
        parcel.writeInt(bigDecimal.scale());
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeBigDecimalArray(Parcel parcel, int n, BigDecimal[] arrbigDecimal, boolean bl) {
        int n2 = 0;
        if (arrbigDecimal == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = arrbigDecimal.length;
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeByteArray(arrbigDecimal[n].unscaledValue().toByteArray());
            parcel.writeInt(arrbigDecimal[n].scale());
            ++n;
        } while (true);
    }

    public static void writeBigInteger(Parcel parcel, int n, BigInteger bigInteger, boolean bl) {
        if (bigInteger == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeByteArray(bigInteger.toByteArray());
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeBigIntegerArray(Parcel parcel, int n, BigInteger[] arrbigInteger, boolean bl) {
        int n2 = 0;
        if (arrbigInteger == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = arrbigInteger.length;
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeByteArray(arrbigInteger[n].toByteArray());
            ++n;
        } while (true);
    }

    public static void writeBoolean(Parcel parcel, int n, boolean bl) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt((int)bl);
    }

    public static void writeBooleanArray(Parcel parcel, int n, boolean[] arrbl, boolean bl) {
        if (arrbl == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeBooleanArray(arrbl);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeBooleanList(Parcel parcel, int n, List<Boolean> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt((int)list.get(n).booleanValue());
            ++n;
        } while (true);
    }

    public static void writeBooleanObject(Parcel parcel, int n, Boolean bl, boolean bl2) {
        if (bl == null) {
            if (!bl2) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt((int)bl.booleanValue());
    }

    public static void writeBundle(Parcel parcel, int n, Bundle bundle, boolean bl) {
        if (bundle == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeBundle(bundle);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeByte(Parcel parcel, int n, byte by) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt((int)by);
    }

    public static void writeByteArray(Parcel parcel, int n, byte[] arrby, boolean bl) {
        if (arrby == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeByteArray(arrby);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeByteArrayArray(Parcel parcel, int n, byte[][] arrby, boolean bl) {
        int n2 = 0;
        if (arrby == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = arrby.length;
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeByteArray(arrby[n]);
            ++n;
        } while (true);
    }

    public static void writeByteArraySparseArray(Parcel parcel, int n, SparseArray<byte[]> sparseArray, boolean bl) {
        int n2 = 0;
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            parcel.writeByteArray((byte[])sparseArray.valueAt(n));
            ++n;
        } while (true);
    }

    public static void writeChar(Parcel parcel, int n, char c) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt((int)c);
    }

    public static void writeCharArray(Parcel parcel, int n, char[] arrc, boolean bl) {
        if (arrc == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeCharArray(arrc);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeDouble(Parcel parcel, int n, double d) {
        SafeParcelWriter.zza(parcel, n, 8);
        parcel.writeDouble(d);
    }

    public static void writeDoubleArray(Parcel parcel, int n, double[] arrd, boolean bl) {
        if (arrd == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeDoubleArray(arrd);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeDoubleList(Parcel parcel, int n, List<Double> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeDouble(list.get(n).doubleValue());
            ++n;
        } while (true);
    }

    public static void writeDoubleObject(Parcel parcel, int n, Double d, boolean bl) {
        if (d == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        SafeParcelWriter.zza(parcel, n, 8);
        parcel.writeDouble(d.doubleValue());
    }

    public static void writeDoubleSparseArray(Parcel parcel, int n, SparseArray<Double> sparseArray, boolean bl) {
        int n2 = 0;
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            parcel.writeDouble(((Double)sparseArray.valueAt(n)).doubleValue());
            ++n;
        } while (true);
    }

    public static void writeFloat(Parcel parcel, int n, float f) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeFloat(f);
    }

    public static void writeFloatArray(Parcel parcel, int n, float[] arrf, boolean bl) {
        if (arrf == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeFloatArray(arrf);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeFloatList(Parcel parcel, int n, List<Float> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeFloat(list.get(n).floatValue());
            ++n;
        } while (true);
    }

    public static void writeFloatObject(Parcel parcel, int n, Float f, boolean bl) {
        if (f == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeFloat(f.floatValue());
    }

    public static void writeFloatSparseArray(Parcel parcel, int n, SparseArray<Float> sparseArray, boolean bl) {
        int n2 = 0;
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            parcel.writeFloat(((Float)sparseArray.valueAt(n)).floatValue());
            ++n;
        } while (true);
    }

    public static void writeIBinder(Parcel parcel, int n, IBinder iBinder, boolean bl) {
        if (iBinder == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeStrongBinder(iBinder);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeIBinderArray(Parcel parcel, int n, IBinder[] arriBinder, boolean bl) {
        if (arriBinder == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeBinderArray(arriBinder);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeIBinderList(Parcel parcel, int n, List<IBinder> list, boolean bl) {
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeBinderList(list);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeIBinderSparseArray(Parcel parcel, int n, SparseArray<IBinder> sparseArray, boolean bl) {
        int n2 = 0;
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            parcel.writeStrongBinder((IBinder)sparseArray.valueAt(n));
            ++n;
        } while (true);
    }

    public static void writeInt(Parcel parcel, int n, int n2) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt(n2);
    }

    public static void writeIntArray(Parcel parcel, int n, int[] arrn, boolean bl) {
        if (arrn == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeIntArray(arrn);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeIntegerList(Parcel parcel, int n, List<Integer> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(list.get(n).intValue());
            ++n;
        } while (true);
    }

    public static void writeIntegerObject(Parcel parcel, int n, Integer n2, boolean bl) {
        if (n2 == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt(n2.intValue());
    }

    public static void writeList(Parcel parcel, int n, List list, boolean bl) {
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeList(list);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeLong(Parcel parcel, int n, long l) {
        SafeParcelWriter.zza(parcel, n, 8);
        parcel.writeLong(l);
    }

    public static void writeLongArray(Parcel parcel, int n, long[] arrl, boolean bl) {
        if (arrl == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeLongArray(arrl);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeLongList(Parcel parcel, int n, List<Long> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeLong(list.get(n).longValue());
            ++n;
        } while (true);
    }

    public static void writeLongObject(Parcel parcel, int n, Long l, boolean bl) {
        if (l == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        SafeParcelWriter.zza(parcel, n, 8);
        parcel.writeLong(l.longValue());
    }

    public static void writeParcel(Parcel parcel, int n, Parcel parcel2, boolean bl) {
        if (parcel2 == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeParcelArray(Parcel parcel, int n, Parcel[] arrparcel, boolean bl) {
        if (arrparcel == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n2 = SafeParcelWriter.zza(parcel, n);
        int n3 = arrparcel.length;
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                SafeParcelWriter.zzb(parcel, n2);
                return;
            }
            Parcel parcel2 = arrparcel[n];
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
            ++n;
        } while (true);
    }

    public static void writeParcelList(Parcel parcel, int n, List<Parcel> list, boolean bl) {
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n2 = SafeParcelWriter.zza(parcel, n);
        int n3 = list.size();
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                SafeParcelWriter.zzb(parcel, n2);
                return;
            }
            Parcel parcel2 = list.get(n);
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
            ++n;
        } while (true);
    }

    public static void writeParcelSparseArray(Parcel parcel, int n, SparseArray<Parcel> sparseArray, boolean bl) {
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n2 = SafeParcelWriter.zza(parcel, n);
        int n3 = sparseArray.size();
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                SafeParcelWriter.zzb(parcel, n2);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            Parcel parcel2 = (Parcel)sparseArray.valueAt(n);
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
            ++n;
        } while (true);
    }

    public static void writeParcelable(Parcel parcel, int n, Parcelable parcelable, int n2, boolean bl) {
        if (parcelable == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcelable.writeToParcel(parcel, n2);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeShort(Parcel parcel, int n, short s) {
        SafeParcelWriter.zza(parcel, n, 4);
        parcel.writeInt((int)s);
    }

    public static void writeSparseBooleanArray(Parcel parcel, int n, SparseBooleanArray sparseBooleanArray, boolean bl) {
        if (sparseBooleanArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeSparseBooleanArray(sparseBooleanArray);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeSparseIntArray(Parcel parcel, int n, SparseIntArray sparseIntArray, boolean bl) {
        int n2 = 0;
        if (sparseIntArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseIntArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseIntArray.keyAt(n));
            parcel.writeInt(sparseIntArray.valueAt(n));
            ++n;
        } while (true);
    }

    public static void writeSparseLongArray(Parcel parcel, int n, SparseLongArray sparseLongArray, boolean bl) {
        int n2 = 0;
        if (sparseLongArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseLongArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseLongArray.keyAt(n));
            parcel.writeLong(sparseLongArray.valueAt(n));
            ++n;
        } while (true);
    }

    public static void writeString(Parcel parcel, int n, String string2, boolean bl) {
        if (string2 == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeString(string2);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeStringArray(Parcel parcel, int n, String[] arrstring, boolean bl) {
        if (arrstring == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeStringArray(arrstring);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeStringList(Parcel parcel, int n, List<String> list, boolean bl) {
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        n = SafeParcelWriter.zza(parcel, n);
        parcel.writeStringList(list);
        SafeParcelWriter.zzb(parcel, n);
    }

    public static void writeStringSparseArray(Parcel parcel, int n, SparseArray<String> sparseArray, boolean bl) {
        int n2 = 0;
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = sparseArray.size();
        parcel.writeInt(n4);
        n = n2;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            parcel.writeString((String)sparseArray.valueAt(n));
            ++n;
        } while (true);
    }

    public static <T extends Parcelable> void writeTypedArray(Parcel parcel, int n, T[] arrT, int n2, boolean bl) {
        if (arrT == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n3 = SafeParcelWriter.zza(parcel, n);
        int n4 = arrT.length;
        parcel.writeInt(n4);
        n = 0;
        do {
            if (n >= n4) {
                SafeParcelWriter.zzb(parcel, n3);
                return;
            }
            T t = arrT[n];
            if (t == null) {
                parcel.writeInt(0);
            } else {
                SafeParcelWriter.zza(parcel, t, n2);
            }
            ++n;
        } while (true);
    }

    public static <T extends Parcelable> void writeTypedList(Parcel parcel, int n, List<T> list, boolean bl) {
        if (list == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n2 = SafeParcelWriter.zza(parcel, n);
        int n3 = list.size();
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                SafeParcelWriter.zzb(parcel, n2);
                return;
            }
            Parcelable parcelable = (Parcelable)list.get(n);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                SafeParcelWriter.zza(parcel, parcelable, 0);
            }
            ++n;
        } while (true);
    }

    public static <T extends Parcelable> void writeTypedSparseArray(Parcel parcel, int n, SparseArray<T> sparseArray, boolean bl) {
        if (sparseArray == null) {
            if (!bl) return;
            SafeParcelWriter.zza(parcel, n, 0);
            return;
        }
        int n2 = SafeParcelWriter.zza(parcel, n);
        int n3 = sparseArray.size();
        parcel.writeInt(n3);
        n = 0;
        do {
            if (n >= n3) {
                SafeParcelWriter.zzb(parcel, n2);
                return;
            }
            parcel.writeInt(sparseArray.keyAt(n));
            Parcelable parcelable = (Parcelable)sparseArray.valueAt(n);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                SafeParcelWriter.zza(parcel, parcelable, 0);
            }
            ++n;
        } while (true);
    }

    private static int zza(Parcel parcel, int n) {
        parcel.writeInt(n | -65536);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zza(Parcel parcel, int n, int n2) {
        if (n2 >= 65535) {
            parcel.writeInt(n | -65536);
            parcel.writeInt(n2);
            return;
        }
        parcel.writeInt(n | n2 << 16);
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t, int n) {
        int n2 = parcel.dataPosition();
        parcel.writeInt(1);
        int n3 = parcel.dataPosition();
        t.writeToParcel(parcel, n);
        n = parcel.dataPosition();
        parcel.setDataPosition(n2);
        parcel.writeInt(n - n3);
        parcel.setDataPosition(n);
    }

    private static void zzb(Parcel parcel, int n) {
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n - 4);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }
}

