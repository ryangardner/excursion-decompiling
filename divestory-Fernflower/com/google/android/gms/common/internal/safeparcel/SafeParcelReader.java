package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SafeParcelReader {
   private SafeParcelReader() {
   }

   public static BigDecimal createBigDecimal(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         byte[] var3 = var0.createByteArray();
         int var4 = var0.readInt();
         var0.setDataPosition(var2 + var1);
         return new BigDecimal(new BigInteger(var3), var4);
      }
   }

   public static BigDecimal[] createBigDecimalArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         BigDecimal[] var5 = new BigDecimal[var4];

         for(var1 = 0; var1 < var4; ++var1) {
            byte[] var6 = var0.createByteArray();
            int var7 = var0.readInt();
            var5[var1] = new BigDecimal(new BigInteger(var6), var7);
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static BigInteger createBigInteger(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         byte[] var3 = var0.createByteArray();
         var0.setDataPosition(var1 + var2);
         return new BigInteger(var3);
      }
   }

   public static BigInteger[] createBigIntegerArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         BigInteger[] var5 = new BigInteger[var4];

         for(var1 = 0; var1 < var4; ++var1) {
            var5[var1] = new BigInteger(var0.createByteArray());
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static boolean[] createBooleanArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         boolean[] var3 = var0.createBooleanArray();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static ArrayList<Boolean> createBooleanList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            boolean var6;
            if (var0.readInt() != 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            var4.add(var6);
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static Bundle createBundle(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         Bundle var3 = var0.readBundle();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static byte[] createByteArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         byte[] var3 = var0.createByteArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static byte[][] createByteArrayArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         byte[][] var5 = new byte[var4][];

         for(var1 = 0; var1 < var4; ++var1) {
            var5[var1] = var0.createByteArray();
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static SparseArray<byte[]> createByteArraySparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         SparseArray var5 = new SparseArray(var4);

         for(var1 = 0; var1 < var4; ++var1) {
            var5.append(var0.readInt(), var0.createByteArray());
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static char[] createCharArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         char[] var3 = var0.createCharArray();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static double[] createDoubleArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         double[] var3 = var0.createDoubleArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static ArrayList<Double> createDoubleList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.add(var0.readDouble());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static SparseArray<Double> createDoubleSparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         SparseArray var4 = new SparseArray();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.append(var0.readInt(), var0.readDouble());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static float[] createFloatArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         float[] var3 = var0.createFloatArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static ArrayList<Float> createFloatList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.add(var0.readFloat());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static SparseArray<Float> createFloatSparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         SparseArray var4 = new SparseArray();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.append(var0.readInt(), var0.readFloat());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static IBinder[] createIBinderArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         IBinder[] var3 = var0.createBinderArray();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static ArrayList<IBinder> createIBinderList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var3 = var0.createBinderArrayList();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static SparseArray<IBinder> createIBinderSparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         SparseArray var5 = new SparseArray(var4);

         for(var1 = 0; var1 < var4; ++var1) {
            var5.append(var0.readInt(), var0.readStrongBinder());
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static int[] createIntArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         int[] var3 = var0.createIntArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static ArrayList<Integer> createIntegerList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.add(var0.readInt());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static long[] createLongArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         long[] var3 = var0.createLongArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static ArrayList<Long> createLongList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.add(var0.readLong());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static Parcel createParcel(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         Parcel var3 = Parcel.obtain();
         var3.appendFrom(var0, var2, var1);
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static Parcel[] createParcelArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         Parcel[] var5 = new Parcel[var4];

         for(var1 = 0; var1 < var4; ++var1) {
            int var6 = var0.readInt();
            if (var6 != 0) {
               int var7 = var0.dataPosition();
               Parcel var8 = Parcel.obtain();
               var8.appendFrom(var0, var7, var6);
               var5[var1] = var8;
               var0.setDataPosition(var7 + var6);
            } else {
               var5[var1] = null;
            }
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static ArrayList<Parcel> createParcelList(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         ArrayList var5 = new ArrayList();

         for(var1 = 0; var1 < var4; ++var1) {
            int var6 = var0.readInt();
            if (var6 != 0) {
               int var7 = var0.dataPosition();
               Parcel var8 = Parcel.obtain();
               var8.appendFrom(var0, var7, var6);
               var5.add(var8);
               var0.setDataPosition(var7 + var6);
            } else {
               var5.add((Object)null);
            }
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static SparseArray<Parcel> createParcelSparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         int var4 = var0.readInt();
         SparseArray var5 = new SparseArray();

         for(var1 = 0; var1 < var4; ++var1) {
            int var6 = var0.readInt();
            int var7 = var0.readInt();
            if (var7 != 0) {
               int var8 = var0.dataPosition();
               Parcel var9 = Parcel.obtain();
               var9.appendFrom(var0, var8, var7);
               var5.append(var6, var9);
               var0.setDataPosition(var8 + var7);
            } else {
               var5.append(var6, (Object)null);
            }
         }

         var0.setDataPosition(var3 + var2);
         return var5;
      }
   }

   public static <T extends Parcelable> T createParcelable(Parcel var0, int var1, Creator<T> var2) {
      int var3 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var3 == 0) {
         return null;
      } else {
         Parcelable var4 = (Parcelable)var2.createFromParcel(var0);
         var0.setDataPosition(var1 + var3);
         return var4;
      }
   }

   public static SparseBooleanArray createSparseBooleanArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         SparseBooleanArray var3 = var0.readSparseBooleanArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static SparseIntArray createSparseIntArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         SparseIntArray var4 = new SparseIntArray();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.append(var0.readInt(), var0.readInt());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static SparseLongArray createSparseLongArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         SparseLongArray var4 = new SparseLongArray();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.append(var0.readInt(), var0.readLong());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static String createString(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         String var3 = var0.readString();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static String[] createStringArray(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         String[] var3 = var0.createStringArray();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static ArrayList<String> createStringList(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      int var2 = var0.dataPosition();
      if (var1 == 0) {
         return null;
      } else {
         ArrayList var3 = var0.createStringArrayList();
         var0.setDataPosition(var2 + var1);
         return var3;
      }
   }

   public static SparseArray<String> createStringSparseArray(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         SparseArray var4 = new SparseArray();
         int var5 = var0.readInt();

         for(var1 = 0; var1 < var5; ++var1) {
            var4.append(var0.readInt(), var0.readString());
         }

         var0.setDataPosition(var3 + var2);
         return var4;
      }
   }

   public static <T> T[] createTypedArray(Parcel var0, int var1, Creator<T> var2) {
      int var3 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var3 == 0) {
         return null;
      } else {
         Object[] var4 = var0.createTypedArray(var2);
         var0.setDataPosition(var1 + var3);
         return var4;
      }
   }

   public static <T> ArrayList<T> createTypedList(Parcel var0, int var1, Creator<T> var2) {
      int var3 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var3 == 0) {
         return null;
      } else {
         ArrayList var4 = var0.createTypedArrayList(var2);
         var0.setDataPosition(var1 + var3);
         return var4;
      }
   }

   public static <T> SparseArray<T> createTypedSparseArray(Parcel var0, int var1, Creator<T> var2) {
      int var3 = readSize(var0, var1);
      int var4 = var0.dataPosition();
      if (var3 == 0) {
         return null;
      } else {
         int var5 = var0.readInt();
         SparseArray var6 = new SparseArray();

         for(var1 = 0; var1 < var5; ++var1) {
            int var7 = var0.readInt();
            Object var8;
            if (var0.readInt() != 0) {
               var8 = var2.createFromParcel(var0);
            } else {
               var8 = null;
            }

            var6.append(var7, var8);
         }

         var0.setDataPosition(var4 + var3);
         return var6;
      }
   }

   public static void ensureAtEnd(Parcel var0, int var1) {
      if (var0.dataPosition() != var1) {
         StringBuilder var2 = new StringBuilder(37);
         var2.append("Overread allowed size end=");
         var2.append(var1);
         throw new SafeParcelReader.ParseException(var2.toString(), var0);
      }
   }

   public static int getFieldId(int var0) {
      return var0 & '\uffff';
   }

   public static boolean readBoolean(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return var0.readInt() != 0;
   }

   public static Boolean readBooleanObject(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      if (var2 == 0) {
         return null;
      } else {
         zza(var0, var1, var2, 4);
         boolean var3;
         if (var0.readInt() != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public static byte readByte(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return (byte)var0.readInt();
   }

   public static char readChar(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return (char)var0.readInt();
   }

   public static double readDouble(Parcel var0, int var1) {
      zza(var0, var1, 8);
      return var0.readDouble();
   }

   public static Double readDoubleObject(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      if (var2 == 0) {
         return null;
      } else {
         zza(var0, var1, var2, 8);
         return var0.readDouble();
      }
   }

   public static float readFloat(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return var0.readFloat();
   }

   public static Float readFloatObject(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      if (var2 == 0) {
         return null;
      } else {
         zza(var0, var1, var2, 4);
         return var0.readFloat();
      }
   }

   public static int readHeader(Parcel var0) {
      return var0.readInt();
   }

   public static IBinder readIBinder(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      var1 = var0.dataPosition();
      if (var2 == 0) {
         return null;
      } else {
         IBinder var3 = var0.readStrongBinder();
         var0.setDataPosition(var1 + var2);
         return var3;
      }
   }

   public static int readInt(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return var0.readInt();
   }

   public static Integer readIntegerObject(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      if (var2 == 0) {
         return null;
      } else {
         zza(var0, var1, var2, 4);
         return var0.readInt();
      }
   }

   public static void readList(Parcel var0, int var1, List var2, ClassLoader var3) {
      var1 = readSize(var0, var1);
      int var4 = var0.dataPosition();
      if (var1 != 0) {
         var0.readList(var2, var3);
         var0.setDataPosition(var4 + var1);
      }
   }

   public static long readLong(Parcel var0, int var1) {
      zza(var0, var1, 8);
      return var0.readLong();
   }

   public static Long readLongObject(Parcel var0, int var1) {
      int var2 = readSize(var0, var1);
      if (var2 == 0) {
         return null;
      } else {
         zza(var0, var1, var2, 8);
         return var0.readLong();
      }
   }

   public static short readShort(Parcel var0, int var1) {
      zza(var0, var1, 4);
      return (short)var0.readInt();
   }

   public static int readSize(Parcel var0, int var1) {
      return (var1 & -65536) != -65536 ? var1 >> 16 & '\uffff' : var0.readInt();
   }

   public static void skipUnknownField(Parcel var0, int var1) {
      var1 = readSize(var0, var1);
      var0.setDataPosition(var0.dataPosition() + var1);
   }

   public static int validateObjectHeader(Parcel var0) {
      int var1 = readHeader(var0);
      int var2 = readSize(var0, var1);
      int var3 = var0.dataPosition();
      if (getFieldId(var1) != 20293) {
         String var5 = String.valueOf(Integer.toHexString(var1));
         if (var5.length() != 0) {
            var5 = "Expected object header. Got 0x".concat(var5);
         } else {
            var5 = new String("Expected object header. Got 0x");
         }

         throw new SafeParcelReader.ParseException(var5, var0);
      } else {
         var1 = var2 + var3;
         if (var1 >= var3 && var1 <= var0.dataSize()) {
            return var1;
         } else {
            StringBuilder var4 = new StringBuilder(54);
            var4.append("Size read is invalid start=");
            var4.append(var3);
            var4.append(" end=");
            var4.append(var1);
            throw new SafeParcelReader.ParseException(var4.toString(), var0);
         }
      }
   }

   private static void zza(Parcel var0, int var1, int var2) {
      var1 = readSize(var0, var1);
      if (var1 != var2) {
         String var3 = Integer.toHexString(var1);
         StringBuilder var4 = new StringBuilder(String.valueOf(var3).length() + 46);
         var4.append("Expected size ");
         var4.append(var2);
         var4.append(" got ");
         var4.append(var1);
         var4.append(" (0x");
         var4.append(var3);
         var4.append(")");
         throw new SafeParcelReader.ParseException(var4.toString(), var0);
      }
   }

   private static void zza(Parcel var0, int var1, int var2, int var3) {
      if (var2 != var3) {
         String var4 = Integer.toHexString(var2);
         StringBuilder var5 = new StringBuilder(String.valueOf(var4).length() + 46);
         var5.append("Expected size ");
         var5.append(var3);
         var5.append(" got ");
         var5.append(var2);
         var5.append(" (0x");
         var5.append(var4);
         var5.append(")");
         throw new SafeParcelReader.ParseException(var5.toString(), var0);
      }
   }

   public static class ParseException extends RuntimeException {
      public ParseException(String var1, Parcel var2) {
         int var3 = var2.dataPosition();
         int var4 = var2.dataSize();
         StringBuilder var5 = new StringBuilder(String.valueOf(var1).length() + 41);
         var5.append(var1);
         var5.append(" Parcel: pos=");
         var5.append(var3);
         var5.append(" size=");
         var5.append(var4);
         super(var5.toString());
      }
   }
}
