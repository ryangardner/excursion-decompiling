package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
   public static final Creator<SafeParcelResponse> CREATOR = new zap();
   private final int zaa;
   private final Parcel zab;
   private final int zac;
   private final zal zad;
   private final String zae;
   private int zaf;
   private int zag;

   SafeParcelResponse(int var1, Parcel var2, zal var3) {
      this.zaa = var1;
      this.zab = (Parcel)Preconditions.checkNotNull(var2);
      this.zac = 2;
      this.zad = var3;
      if (var3 == null) {
         this.zae = null;
      } else {
         this.zae = var3.zac();
      }

      this.zaf = 2;
   }

   private SafeParcelResponse(SafeParcelable var1, zal var2, String var3) {
      this.zaa = 1;
      Parcel var4 = Parcel.obtain();
      this.zab = var4;
      var1.writeToParcel(var4, 0);
      this.zac = 1;
      this.zad = (zal)Preconditions.checkNotNull(var2);
      this.zae = (String)Preconditions.checkNotNull(var3);
      this.zaf = 2;
   }

   public SafeParcelResponse(zal var1, String var2) {
      this.zaa = 1;
      this.zab = Parcel.obtain();
      this.zac = 0;
      this.zad = (zal)Preconditions.checkNotNull(var1);
      this.zae = (String)Preconditions.checkNotNull(var2);
      this.zaf = 0;
   }

   public static <T extends FastJsonResponse & SafeParcelable> SafeParcelResponse from(T var0) {
      String var1 = (String)Preconditions.checkNotNull(var0.getClass().getCanonicalName());
      zal var2 = new zal(var0.getClass());
      zaa(var2, var0);
      var2.zab();
      var2.zaa();
      return new SafeParcelResponse((SafeParcelable)var0, var2, var1);
   }

   private final Parcel zaa() {
      int var1 = this.zaf;
      if (var1 != 0) {
         if (var1 != 1) {
            return this.zab;
         }
      } else {
         this.zag = SafeParcelWriter.beginObjectHeader(this.zab);
      }

      SafeParcelWriter.finishObjectHeader(this.zab, this.zag);
      this.zaf = 2;
      return this.zab;
   }

   private final void zaa(FastJsonResponse.Field<?, ?> var1) {
      boolean var2;
      if (var1.zaf != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         Parcel var3 = this.zab;
         if (var3 != null) {
            int var4 = this.zaf;
            if (var4 != 0) {
               if (var4 != 1) {
                  if (var4 != 2) {
                     throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
                  } else {
                     throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
                  }
               }
            } else {
               this.zag = SafeParcelWriter.beginObjectHeader(var3);
               this.zaf = 1;
            }
         } else {
            throw new IllegalStateException("Internal Parcel object is null.");
         }
      } else {
         throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
      }
   }

   private static void zaa(zal var0, FastJsonResponse var1) {
      java.lang.Class var2 = var1.getClass();
      if (!var0.zaa(var2)) {
         Map var3 = var1.getFieldMappings();
         var0.zaa(var2, var3);
         Iterator var4 = var3.keySet().iterator();

         while(var4.hasNext()) {
            FastJsonResponse.Field var8 = (FastJsonResponse.Field)var3.get((String)var4.next());
            var2 = var8.zag;
            if (var2 != null) {
               String var7;
               try {
                  zaa(var0, (FastJsonResponse)var2.newInstance());
               } catch (InstantiationException var5) {
                  var7 = String.valueOf(((java.lang.Class)Preconditions.checkNotNull(var8.zag)).getCanonicalName());
                  if (var7.length() != 0) {
                     var7 = "Could not instantiate an object of type ".concat(var7);
                  } else {
                     var7 = new String("Could not instantiate an object of type ");
                  }

                  throw new IllegalStateException(var7, var5);
               } catch (IllegalAccessException var6) {
                  var7 = String.valueOf(((java.lang.Class)Preconditions.checkNotNull(var8.zag)).getCanonicalName());
                  if (var7.length() != 0) {
                     var7 = "Could not access object of type ".concat(var7);
                  } else {
                     var7 = new String("Could not access object of type ");
                  }

                  throw new IllegalStateException(var7, var6);
               }
            }
         }
      }

   }

   private static void zaa(StringBuilder var0, int var1, Object var2) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
         var0.append(var2);
         return;
      case 7:
         var0.append("\"");
         var0.append(JsonUtils.escapeString(Preconditions.checkNotNull(var2).toString()));
         var0.append("\"");
         return;
      case 8:
         var0.append("\"");
         var0.append(Base64Utils.encode((byte[])var2));
         var0.append("\"");
         return;
      case 9:
         var0.append("\"");
         var0.append(Base64Utils.encodeUrlSafe((byte[])var2));
         var0.append("\"");
         return;
      case 10:
         MapUtils.writeStringMapToJson(var0, (HashMap)Preconditions.checkNotNull(var2));
         return;
      case 11:
         throw new IllegalArgumentException("Method does not accept concrete type.");
      default:
         var0 = new StringBuilder(26);
         var0.append("Unknown type = ");
         var0.append(var1);
         throw new IllegalArgumentException(var0.toString());
      }
   }

   private static void zaa(StringBuilder var0, FastJsonResponse.Field<?, ?> var1, Object var2) {
      if (var1.zab) {
         ArrayList var5 = (ArrayList)var2;
         var0.append("[");
         int var3 = var5.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            if (var4 != 0) {
               var0.append(",");
            }

            zaa(var0, var1.zaa, var5.get(var4));
         }

         var0.append("]");
      } else {
         zaa(var0, var1.zaa, var2);
      }
   }

   private final void zaa(StringBuilder var1, Map<String, FastJsonResponse.Field<?, ?>> var2, Parcel var3) {
      SparseArray var4 = new SparseArray();
      Iterator var5 = var2.entrySet().iterator();

      Entry var12;
      while(var5.hasNext()) {
         var12 = (Entry)var5.next();
         var4.put(((FastJsonResponse.Field)var12.getValue()).getSafeParcelableFieldId(), var12);
      }

      var1.append('{');
      int var6 = SafeParcelReader.validateObjectHeader(var3);
      boolean var7 = false;

      while(true) {
         int var8;
         do {
            if (var3.dataPosition() >= var6) {
               if (var3.dataPosition() == var6) {
                  var1.append('}');
                  return;
               }

               var1 = new StringBuilder(37);
               var1.append("Overread allowed size end=");
               var1.append(var6);
               throw new SafeParcelReader.ParseException(var1.toString(), var3);
            }

            var8 = SafeParcelReader.readHeader(var3);
            var12 = (Entry)var4.get(SafeParcelReader.getFieldId(var8));
         } while(var12 == null);

         if (var7) {
            var1.append(",");
         }

         String var17 = (String)var12.getKey();
         FastJsonResponse.Field var13 = (FastJsonResponse.Field)var12.getValue();
         var1.append("\"");
         var1.append(var17);
         var1.append("\":");
         int var21;
         if (var13.zab()) {
            switch(var13.zac) {
            case 0:
               zaa(var1, var13, zaa(var13, SafeParcelReader.readInt(var3, var8)));
               break;
            case 1:
               zaa(var1, var13, zaa(var13, SafeParcelReader.createBigInteger(var3, var8)));
               break;
            case 2:
               zaa(var1, var13, zaa(var13, SafeParcelReader.readLong(var3, var8)));
               break;
            case 3:
               zaa(var1, var13, zaa(var13, SafeParcelReader.readFloat(var3, var8)));
               break;
            case 4:
               zaa(var1, var13, zaa(var13, SafeParcelReader.readDouble(var3, var8)));
               break;
            case 5:
               zaa(var1, var13, zaa(var13, SafeParcelReader.createBigDecimal(var3, var8)));
               break;
            case 6:
               zaa(var1, var13, zaa(var13, SafeParcelReader.readBoolean(var3, var8)));
               break;
            case 7:
               zaa(var1, var13, zaa(var13, SafeParcelReader.createString(var3, var8)));
               break;
            case 8:
            case 9:
               zaa(var1, var13, zaa(var13, SafeParcelReader.createByteArray(var3, var8)));
               break;
            case 10:
               Bundle var22 = SafeParcelReader.createBundle(var3, var8);
               HashMap var10 = new HashMap();
               Iterator var11 = var22.keySet().iterator();

               while(var11.hasNext()) {
                  var17 = (String)var11.next();
                  var10.put(var17, (String)Preconditions.checkNotNull(var22.getString(var17)));
               }

               zaa(var1, var13, zaa(var13, var10));
               break;
            case 11:
               throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
               var21 = var13.zac;
               var1 = new StringBuilder(36);
               var1.append("Unknown field out type = ");
               var1.append(var21);
               throw new IllegalArgumentException(var1.toString());
            }
         } else if (!var13.zad) {
            byte[] var15;
            switch(var13.zac) {
            case 0:
               var1.append(SafeParcelReader.readInt(var3, var8));
               break;
            case 1:
               var1.append(SafeParcelReader.createBigInteger(var3, var8));
               break;
            case 2:
               var1.append(SafeParcelReader.readLong(var3, var8));
               break;
            case 3:
               var1.append(SafeParcelReader.readFloat(var3, var8));
               break;
            case 4:
               var1.append(SafeParcelReader.readDouble(var3, var8));
               break;
            case 5:
               var1.append(SafeParcelReader.createBigDecimal(var3, var8));
               break;
            case 6:
               var1.append(SafeParcelReader.readBoolean(var3, var8));
               break;
            case 7:
               String var16 = SafeParcelReader.createString(var3, var8);
               var1.append("\"");
               var1.append(JsonUtils.escapeString(var16));
               var1.append("\"");
               break;
            case 8:
               var15 = SafeParcelReader.createByteArray(var3, var8);
               var1.append("\"");
               var1.append(Base64Utils.encode(var15));
               var1.append("\"");
               break;
            case 9:
               var15 = SafeParcelReader.createByteArray(var3, var8);
               var1.append("\"");
               var1.append(Base64Utils.encodeUrlSafe(var15));
               var1.append("\"");
               break;
            case 10:
               Bundle var14 = SafeParcelReader.createBundle(var3, var8);
               Set var20 = var14.keySet();
               var1.append("{");
               var5 = var20.iterator();

               for(var7 = true; var5.hasNext(); var7 = false) {
                  String var9 = (String)var5.next();
                  if (!var7) {
                     var1.append(",");
                  }

                  var1.append("\"");
                  var1.append(var9);
                  var1.append("\"");
                  var1.append(":");
                  var1.append("\"");
                  var1.append(JsonUtils.escapeString(var14.getString(var9)));
                  var1.append("\"");
               }

               var1.append("}");
               break;
            case 11:
               Parcel var19 = SafeParcelReader.createParcel(var3, var8);
               var19.setDataPosition(0);
               this.zaa(var1, var13.zad(), var19);
               break;
            default:
               throw new IllegalStateException("Unknown field type out");
            }
         } else {
            var1.append("[");
            label109:
            switch(var13.zac) {
            case 0:
               ArrayUtils.writeArray(var1, SafeParcelReader.createIntArray(var3, var8));
               break;
            case 1:
               ArrayUtils.writeArray(var1, (Object[])SafeParcelReader.createBigIntegerArray(var3, var8));
               break;
            case 2:
               ArrayUtils.writeArray(var1, SafeParcelReader.createLongArray(var3, var8));
               break;
            case 3:
               ArrayUtils.writeArray(var1, SafeParcelReader.createFloatArray(var3, var8));
               break;
            case 4:
               ArrayUtils.writeArray(var1, SafeParcelReader.createDoubleArray(var3, var8));
               break;
            case 5:
               ArrayUtils.writeArray(var1, (Object[])SafeParcelReader.createBigDecimalArray(var3, var8));
               break;
            case 6:
               ArrayUtils.writeArray(var1, SafeParcelReader.createBooleanArray(var3, var8));
               break;
            case 7:
               ArrayUtils.writeStringArray(var1, SafeParcelReader.createStringArray(var3, var8));
               break;
            case 8:
            case 9:
            case 10:
               throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
            case 11:
               Parcel[] var18 = SafeParcelReader.createParcelArray(var3, var8);
               var8 = var18.length;
               var21 = 0;

               while(true) {
                  if (var21 >= var8) {
                     break label109;
                  }

                  if (var21 > 0) {
                     var1.append(",");
                  }

                  var18[var21].setDataPosition(0);
                  this.zaa(var1, var13.zad(), var18[var21]);
                  ++var21;
               }
            default:
               throw new IllegalStateException("Unknown field type out.");
            }

            var1.append("]");
         }

         var7 = true;
      }
   }

   public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<T> var3) {
      this.zaa(var1);
      ArrayList var7 = new ArrayList();
      ((ArrayList)Preconditions.checkNotNull(var3)).size();
      var3 = (ArrayList)var3;
      int var4 = var3.size();
      int var5 = 0;

      while(var5 < var4) {
         Object var6 = var3.get(var5);
         ++var5;
         var7.add(((SafeParcelResponse)((FastJsonResponse)var6)).zaa());
      }

      SafeParcelWriter.writeParcelList(this.zab, var1.getSafeParcelableFieldId(), var7, true);
   }

   public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> var1, String var2, T var3) {
      this.zaa(var1);
      Parcel var4 = ((SafeParcelResponse)var3).zaa();
      SafeParcelWriter.writeParcel(this.zab, var1.getSafeParcelableFieldId(), var4, true);
   }

   public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
      zal var1 = this.zad;
      return var1 == null ? null : var1.zaa((String)Preconditions.checkNotNull(this.zae));
   }

   public Object getValueObject(String var1) {
      throw new UnsupportedOperationException("Converting to JSON does not require this method.");
   }

   public boolean isPrimitiveFieldSet(String var1) {
      throw new UnsupportedOperationException("Converting to JSON does not require this method.");
   }

   protected void setBooleanInternal(FastJsonResponse.Field<?, ?> var1, String var2, boolean var3) {
      this.zaa(var1);
      SafeParcelWriter.writeBoolean(this.zab, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> var1, String var2, byte[] var3) {
      this.zaa(var1);
      SafeParcelWriter.writeByteArray(this.zab, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected void setIntegerInternal(FastJsonResponse.Field<?, ?> var1, String var2, int var3) {
      this.zaa(var1);
      SafeParcelWriter.writeInt(this.zab, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setLongInternal(FastJsonResponse.Field<?, ?> var1, String var2, long var3) {
      this.zaa(var1);
      SafeParcelWriter.writeLong(this.zab, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setStringInternal(FastJsonResponse.Field<?, ?> var1, String var2, String var3) {
      this.zaa(var1);
      SafeParcelWriter.writeString(this.zab, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected void setStringMapInternal(FastJsonResponse.Field<?, ?> var1, String var2, Map<String, String> var3) {
      this.zaa(var1);
      Bundle var6 = new Bundle();
      Iterator var4 = ((Map)Preconditions.checkNotNull(var3)).keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var6.putString(var5, (String)var3.get(var5));
      }

      SafeParcelWriter.writeBundle(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected void setStringsInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<String> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      String[] var6 = new String[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (String)var3.get(var5);
      }

      SafeParcelWriter.writeStringArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   public String toString() {
      Preconditions.checkNotNull(this.zad, "Cannot convert to JSON on client side.");
      Parcel var1 = this.zaa();
      var1.setDataPosition(0);
      StringBuilder var2 = new StringBuilder(100);
      this.zaa(var2, (Map)Preconditions.checkNotNull(this.zad.zaa((String)Preconditions.checkNotNull(this.zae))), var1);
      return var2.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcel(var1, 2, this.zaa(), false);
      int var4 = this.zac;
      zal var5;
      if (var4 != 0) {
         if (var4 != 1) {
            if (var4 != 2) {
               var2 = this.zac;
               StringBuilder var6 = new StringBuilder(34);
               var6.append("Invalid creation type: ");
               var6.append(var2);
               throw new IllegalStateException(var6.toString());
            }

            var5 = this.zad;
         } else {
            var5 = this.zad;
         }
      } else {
         var5 = null;
      }

      SafeParcelWriter.writeParcelable(var1, 3, var5, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, double var3) {
      this.zaa(var1);
      SafeParcelWriter.writeDouble(this.zab, var1.getSafeParcelableFieldId(), var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, float var3) {
      this.zaa(var1);
      SafeParcelWriter.writeFloat(this.zab, var1.getSafeParcelableFieldId(), var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigDecimal var3) {
      this.zaa(var1);
      SafeParcelWriter.writeBigDecimal(this.zab, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigInteger var3) {
      this.zaa(var1);
      SafeParcelWriter.writeBigInteger(this.zab, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Integer> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      int[] var6 = new int[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (Integer)var3.get(var5);
      }

      SafeParcelWriter.writeIntArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zab(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigInteger> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      BigInteger[] var6 = new BigInteger[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (BigInteger)var3.get(var5);
      }

      SafeParcelWriter.writeBigIntegerArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zac(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Long> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      long[] var6 = new long[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (Long)var3.get(var5);
      }

      SafeParcelWriter.writeLongArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zad(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Float> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      float[] var6 = new float[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (Float)var3.get(var5);
      }

      SafeParcelWriter.writeFloatArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zae(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Double> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      double[] var6 = new double[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (Double)var3.get(var5);
      }

      SafeParcelWriter.writeDoubleArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zaf(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigDecimal> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      BigDecimal[] var6 = new BigDecimal[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (BigDecimal)var3.get(var5);
      }

      SafeParcelWriter.writeBigDecimalArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zag(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Boolean> var3) {
      this.zaa(var1);
      int var4 = ((ArrayList)Preconditions.checkNotNull(var3)).size();
      boolean[] var6 = new boolean[var4];

      for(int var5 = 0; var5 < var4; ++var5) {
         var6[var5] = (Boolean)var3.get(var5);
      }

      SafeParcelWriter.writeBooleanArray(this.zab, var1.getSafeParcelableFieldId(), var6, true);
   }
}
