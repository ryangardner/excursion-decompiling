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

   public static int beginObjectHeader(Parcel var0) {
      return zza(var0, 20293);
   }

   public static void finishObjectHeader(Parcel var0, int var1) {
      zzb(var0, var1);
   }

   public static void writeBigDecimal(Parcel var0, int var1, BigDecimal var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeByteArray(var2.unscaledValue().toByteArray());
         var0.writeInt(var2.scale());
         zzb(var0, var1);
      }
   }

   public static void writeBigDecimalArray(Parcel var0, int var1, BigDecimal[] var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.length;
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeByteArray(var2[var1].unscaledValue().toByteArray());
            var0.writeInt(var2[var1].scale());
         }

         zzb(var0, var5);
      }
   }

   public static void writeBigInteger(Parcel var0, int var1, BigInteger var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeByteArray(var2.toByteArray());
         zzb(var0, var1);
      }
   }

   public static void writeBigIntegerArray(Parcel var0, int var1, BigInteger[] var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.length;
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeByteArray(var2[var1].toByteArray());
         }

         zzb(var0, var5);
      }
   }

   public static void writeBoolean(Parcel var0, int var1, boolean var2) {
      zza(var0, var1, 4);
      var0.writeInt(var2);
   }

   public static void writeBooleanArray(Parcel var0, int var1, boolean[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeBooleanArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeBooleanList(Parcel var0, int var1, List<Boolean> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt((Boolean)var2.get(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeBooleanObject(Parcel var0, int var1, Boolean var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         zza(var0, var1, 4);
         var0.writeInt(var2);
      }
   }

   public static void writeBundle(Parcel var0, int var1, Bundle var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeBundle(var2);
         zzb(var0, var1);
      }
   }

   public static void writeByte(Parcel var0, int var1, byte var2) {
      zza(var0, var1, 4);
      var0.writeInt(var2);
   }

   public static void writeByteArray(Parcel var0, int var1, byte[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeByteArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeByteArrayArray(Parcel var0, int var1, byte[][] var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.length;
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeByteArray(var2[var1]);
         }

         zzb(var0, var5);
      }
   }

   public static void writeByteArraySparseArray(Parcel var0, int var1, SparseArray<byte[]> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeByteArray((byte[])var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeChar(Parcel var0, int var1, char var2) {
      zza(var0, var1, 4);
      var0.writeInt(var2);
   }

   public static void writeCharArray(Parcel var0, int var1, char[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeCharArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeDouble(Parcel var0, int var1, double var2) {
      zza(var0, var1, 8);
      var0.writeDouble(var2);
   }

   public static void writeDoubleArray(Parcel var0, int var1, double[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeDoubleArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeDoubleList(Parcel var0, int var1, List<Double> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeDouble((Double)var2.get(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeDoubleObject(Parcel var0, int var1, Double var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         zza(var0, var1, 8);
         var0.writeDouble(var2);
      }
   }

   public static void writeDoubleSparseArray(Parcel var0, int var1, SparseArray<Double> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeDouble((Double)var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeFloat(Parcel var0, int var1, float var2) {
      zza(var0, var1, 4);
      var0.writeFloat(var2);
   }

   public static void writeFloatArray(Parcel var0, int var1, float[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeFloatArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeFloatList(Parcel var0, int var1, List<Float> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeFloat((Float)var2.get(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeFloatObject(Parcel var0, int var1, Float var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         zza(var0, var1, 4);
         var0.writeFloat(var2);
      }
   }

   public static void writeFloatSparseArray(Parcel var0, int var1, SparseArray<Float> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeFloat((Float)var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeIBinder(Parcel var0, int var1, IBinder var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeStrongBinder(var2);
         zzb(var0, var1);
      }
   }

   public static void writeIBinderArray(Parcel var0, int var1, IBinder[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeBinderArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeIBinderList(Parcel var0, int var1, List<IBinder> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeBinderList(var2);
         zzb(var0, var1);
      }
   }

   public static void writeIBinderSparseArray(Parcel var0, int var1, SparseArray<IBinder> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeStrongBinder((IBinder)var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeInt(Parcel var0, int var1, int var2) {
      zza(var0, var1, 4);
      var0.writeInt(var2);
   }

   public static void writeIntArray(Parcel var0, int var1, int[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeIntArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeIntegerList(Parcel var0, int var1, List<Integer> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt((Integer)var2.get(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeIntegerObject(Parcel var0, int var1, Integer var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         zza(var0, var1, 4);
         var0.writeInt(var2);
      }
   }

   public static void writeList(Parcel var0, int var1, List var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeList(var2);
         zzb(var0, var1);
      }
   }

   public static void writeLong(Parcel var0, int var1, long var2) {
      zza(var0, var1, 8);
      var0.writeLong(var2);
   }

   public static void writeLongArray(Parcel var0, int var1, long[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeLongArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeLongList(Parcel var0, int var1, List<Long> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeLong((Long)var2.get(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeLongObject(Parcel var0, int var1, Long var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         zza(var0, var1, 8);
         var0.writeLong(var2);
      }
   }

   public static void writeParcel(Parcel var0, int var1, Parcel var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.appendFrom(var2, 0, var2.dataSize());
         zzb(var0, var1);
      }
   }

   public static void writeParcelArray(Parcel var0, int var1, Parcel[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var4 = zza(var0, var1);
         int var5 = var2.length;
         var0.writeInt(var5);

         for(var1 = 0; var1 < var5; ++var1) {
            Parcel var6 = var2[var1];
            if (var6 != null) {
               var0.writeInt(var6.dataSize());
               var0.appendFrom(var6, 0, var6.dataSize());
            } else {
               var0.writeInt(0);
            }
         }

         zzb(var0, var4);
      }
   }

   public static void writeParcelList(Parcel var0, int var1, List<Parcel> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var4 = zza(var0, var1);
         int var5 = var2.size();
         var0.writeInt(var5);

         for(var1 = 0; var1 < var5; ++var1) {
            Parcel var6 = (Parcel)var2.get(var1);
            if (var6 != null) {
               var0.writeInt(var6.dataSize());
               var0.appendFrom(var6, 0, var6.dataSize());
            } else {
               var0.writeInt(0);
            }
         }

         zzb(var0, var4);
      }
   }

   public static void writeParcelSparseArray(Parcel var0, int var1, SparseArray<Parcel> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var4 = zza(var0, var1);
         int var5 = var2.size();
         var0.writeInt(var5);

         for(var1 = 0; var1 < var5; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            Parcel var6 = (Parcel)var2.valueAt(var1);
            if (var6 != null) {
               var0.writeInt(var6.dataSize());
               var0.appendFrom(var6, 0, var6.dataSize());
            } else {
               var0.writeInt(0);
            }
         }

         zzb(var0, var4);
      }
   }

   public static void writeParcelable(Parcel var0, int var1, Parcelable var2, int var3, boolean var4) {
      if (var2 == null) {
         if (var4) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var2.writeToParcel(var0, var3);
         zzb(var0, var1);
      }
   }

   public static void writeShort(Parcel var0, int var1, short var2) {
      zza(var0, var1, 4);
      var0.writeInt(var2);
   }

   public static void writeSparseBooleanArray(Parcel var0, int var1, SparseBooleanArray var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeSparseBooleanArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeSparseIntArray(Parcel var0, int var1, SparseIntArray var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeInt(var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeSparseLongArray(Parcel var0, int var1, SparseLongArray var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeLong(var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static void writeString(Parcel var0, int var1, String var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeString(var2);
         zzb(var0, var1);
      }
   }

   public static void writeStringArray(Parcel var0, int var1, String[] var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeStringArray(var2);
         zzb(var0, var1);
      }
   }

   public static void writeStringList(Parcel var0, int var1, List<String> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         var1 = zza(var0, var1);
         var0.writeStringList(var2);
         zzb(var0, var1);
      }
   }

   public static void writeStringSparseArray(Parcel var0, int var1, SparseArray<String> var2, boolean var3) {
      byte var4 = 0;
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.size();
         var0.writeInt(var6);

         for(var1 = var4; var1 < var6; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            var0.writeString((String)var2.valueAt(var1));
         }

         zzb(var0, var5);
      }
   }

   public static <T extends Parcelable> void writeTypedArray(Parcel var0, int var1, T[] var2, int var3, boolean var4) {
      if (var2 == null) {
         if (var4) {
            zza(var0, var1, 0);
         }

      } else {
         int var5 = zza(var0, var1);
         int var6 = var2.length;
         var0.writeInt(var6);

         for(var1 = 0; var1 < var6; ++var1) {
            Parcelable var7 = var2[var1];
            if (var7 == null) {
               var0.writeInt(0);
            } else {
               zza(var0, var7, var3);
            }
         }

         zzb(var0, var5);
      }
   }

   public static <T extends Parcelable> void writeTypedList(Parcel var0, int var1, List<T> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var4 = zza(var0, var1);
         int var5 = var2.size();
         var0.writeInt(var5);

         for(var1 = 0; var1 < var5; ++var1) {
            Parcelable var6 = (Parcelable)var2.get(var1);
            if (var6 == null) {
               var0.writeInt(0);
            } else {
               zza(var0, var6, 0);
            }
         }

         zzb(var0, var4);
      }
   }

   public static <T extends Parcelable> void writeTypedSparseArray(Parcel var0, int var1, SparseArray<T> var2, boolean var3) {
      if (var2 == null) {
         if (var3) {
            zza(var0, var1, 0);
         }

      } else {
         int var4 = zza(var0, var1);
         int var5 = var2.size();
         var0.writeInt(var5);

         for(var1 = 0; var1 < var5; ++var1) {
            var0.writeInt(var2.keyAt(var1));
            Parcelable var6 = (Parcelable)var2.valueAt(var1);
            if (var6 == null) {
               var0.writeInt(0);
            } else {
               zza(var0, var6, 0);
            }
         }

         zzb(var0, var4);
      }
   }

   private static int zza(Parcel var0, int var1) {
      var0.writeInt(var1 | -65536);
      var0.writeInt(0);
      return var0.dataPosition();
   }

   private static void zza(Parcel var0, int var1, int var2) {
      if (var2 >= 65535) {
         var0.writeInt(var1 | -65536);
         var0.writeInt(var2);
      } else {
         var0.writeInt(var1 | var2 << 16);
      }
   }

   private static <T extends Parcelable> void zza(Parcel var0, T var1, int var2) {
      int var3 = var0.dataPosition();
      var0.writeInt(1);
      int var4 = var0.dataPosition();
      var1.writeToParcel(var0, var2);
      var2 = var0.dataPosition();
      var0.setDataPosition(var3);
      var0.writeInt(var2 - var4);
      var0.setDataPosition(var2);
   }

   private static void zzb(Parcel var0, int var1) {
      int var2 = var0.dataPosition();
      var0.setDataPosition(var1 - 4);
      var0.writeInt(var2 - var1);
      var0.setDataPosition(var2);
   }
}
