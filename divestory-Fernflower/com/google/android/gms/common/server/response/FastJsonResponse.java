package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class FastJsonResponse {
   protected static <O, I> I zaa(FastJsonResponse.Field<I, O> var0, Object var1) {
      return var0.zak != null ? var0.zab(var1) : var1;
   }

   private static <O> void zaa(String var0) {
      if (Log.isLoggable("FastJsonResponse", 6)) {
         StringBuilder var1 = new StringBuilder(String.valueOf(var0).length() + 58);
         var1.append("Output field (");
         var1.append(var0);
         var1.append(") has a null value, but expected a primitive");
         Log.e("FastJsonResponse", var1.toString());
      }

   }

   private static void zaa(StringBuilder var0, FastJsonResponse.Field var1, Object var2) {
      if (var1.zaa == 11) {
         Class var3 = var1.zag;
         Preconditions.checkNotNull(var3);
         var0.append(((FastJsonResponse)var3.cast(var2)).toString());
      } else if (var1.zaa == 7) {
         var0.append("\"");
         var0.append(JsonUtils.escapeString((String)var2));
         var0.append("\"");
      } else {
         var0.append(var2);
      }
   }

   private final <I, O> void zab(FastJsonResponse.Field<I, O> var1, I var2) {
      String var3 = var1.zae;
      var2 = var1.zaa(var2);
      switch(var1.zac) {
      case 0:
         if (var2 != null) {
            this.setIntegerInternal(var1, var3, (Integer)var2);
            return;
         }

         zaa(var3);
         return;
      case 1:
         this.zaa(var1, var3, (BigInteger)var2);
         return;
      case 2:
         if (var2 != null) {
            this.setLongInternal(var1, var3, (Long)var2);
            return;
         }

         zaa(var3);
         return;
      case 3:
      default:
         int var4 = var1.zac;
         StringBuilder var5 = new StringBuilder(44);
         var5.append("Unsupported type for conversion: ");
         var5.append(var4);
         throw new IllegalStateException(var5.toString());
      case 4:
         if (var2 != null) {
            this.zaa(var1, var3, (Double)var2);
            return;
         }

         zaa(var3);
         return;
      case 5:
         this.zaa(var1, var3, (BigDecimal)var2);
         return;
      case 6:
         if (var2 != null) {
            this.setBooleanInternal(var1, var3, (Boolean)var2);
            return;
         }

         zaa(var3);
         return;
      case 7:
         this.setStringInternal(var1, var3, (String)var2);
         return;
      case 8:
      case 9:
         if (var2 != null) {
            this.setDecodedBytesInternal(var1, var3, (byte[])var2);
         } else {
            zaa(var3);
         }
      }
   }

   public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<T> var3) {
      throw new UnsupportedOperationException("Concrete type array not supported");
   }

   public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> var1, String var2, T var3) {
      throw new UnsupportedOperationException("Concrete type not supported");
   }

   public abstract Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings();

   protected Object getFieldValue(FastJsonResponse.Field var1) {
      String var2 = var1.zae;
      if (var1.zag != null) {
         boolean var3;
         if (this.getValueObject(var1.zae) == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "Concrete field shouldn't be value object: %s", var1.zae);
         var3 = var1.zad;

         try {
            char var4 = Character.toUpperCase(var2.charAt(0));
            String var7 = var2.substring(1);
            int var5 = String.valueOf(var7).length();
            StringBuilder var9 = new StringBuilder(var5 + 4);
            var9.append("get");
            var9.append(var4);
            var9.append(var7);
            var7 = var9.toString();
            Object var8 = this.getClass().getMethod(var7).invoke(this);
            return var8;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      } else {
         return this.getValueObject(var1.zae);
      }
   }

   protected abstract Object getValueObject(String var1);

   protected boolean isFieldSet(FastJsonResponse.Field var1) {
      if (var1.zac == 11) {
         String var2;
         if (var1.zad) {
            var2 = var1.zae;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
         } else {
            var2 = var1.zae;
            throw new UnsupportedOperationException("Concrete types not supported");
         }
      } else {
         return this.isPrimitiveFieldSet(var1.zae);
      }
   }

   protected abstract boolean isPrimitiveFieldSet(String var1);

   protected void setBooleanInternal(FastJsonResponse.Field<?, ?> var1, String var2, boolean var3) {
      throw new UnsupportedOperationException("Boolean not supported");
   }

   protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> var1, String var2, byte[] var3) {
      throw new UnsupportedOperationException("byte[] not supported");
   }

   protected void setIntegerInternal(FastJsonResponse.Field<?, ?> var1, String var2, int var3) {
      throw new UnsupportedOperationException("Integer not supported");
   }

   protected void setLongInternal(FastJsonResponse.Field<?, ?> var1, String var2, long var3) {
      throw new UnsupportedOperationException("Long not supported");
   }

   protected void setStringInternal(FastJsonResponse.Field<?, ?> var1, String var2, String var3) {
      throw new UnsupportedOperationException("String not supported");
   }

   protected void setStringMapInternal(FastJsonResponse.Field<?, ?> var1, String var2, Map<String, String> var3) {
      throw new UnsupportedOperationException("String map not supported");
   }

   protected void setStringsInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<String> var3) {
      throw new UnsupportedOperationException("String list not supported");
   }

   public String toString() {
      Map var1 = this.getFieldMappings();
      StringBuilder var2 = new StringBuilder(100);
      Iterator var3 = var1.keySet().iterator();

      while(true) {
         while(true) {
            String var4;
            FastJsonResponse.Field var5;
            do {
               if (!var3.hasNext()) {
                  if (var2.length() > 0) {
                     var2.append("}");
                  } else {
                     var2.append("{}");
                  }

                  return var2.toString();
               }

               var4 = (String)var3.next();
               var5 = (FastJsonResponse.Field)var1.get(var4);
            } while(!this.isFieldSet(var5));

            Object var6 = zaa(var5, this.getFieldValue(var5));
            if (var2.length() == 0) {
               var2.append("{");
            } else {
               var2.append(",");
            }

            var2.append("\"");
            var2.append(var4);
            var2.append("\":");
            if (var6 == null) {
               var2.append("null");
            } else {
               int var7;
               int var8;
               ArrayList var10;
               switch(var5.zac) {
               case 8:
                  var2.append("\"");
                  var2.append(Base64Utils.encode((byte[])var6));
                  var2.append("\"");
                  continue;
               case 9:
                  var2.append("\"");
                  var2.append(Base64Utils.encodeUrlSafe((byte[])var6));
                  var2.append("\"");
                  continue;
               case 10:
                  MapUtils.writeStringMapToJson(var2, (HashMap)var6);
                  continue;
               default:
                  if (!var5.zab) {
                     zaa(var2, var5, var6);
                     continue;
                  }

                  var10 = (ArrayList)var6;
                  var2.append("[");
                  var7 = 0;
                  var8 = var10.size();
               }

               for(; var7 < var8; ++var7) {
                  if (var7 > 0) {
                     var2.append(",");
                  }

                  Object var9 = var10.get(var7);
                  if (var9 != null) {
                     zaa(var2, var5, var9);
                  }
               }

               var2.append("]");
            }
         }
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Double, O> var1, double var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Float, O> var1, float var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Integer, O> var1, int var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setIntegerInternal(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Long, O> var1, long var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setLongInternal(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<String, O> var1, String var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setStringInternal(var1, var1.zae, var2);
      }
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, double var3) {
      throw new UnsupportedOperationException("Double not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, float var3) {
      throw new UnsupportedOperationException("Float not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigDecimal var3) {
      throw new UnsupportedOperationException("BigDecimal not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigInteger var3) {
      throw new UnsupportedOperationException("BigInteger not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Integer> var3) {
      throw new UnsupportedOperationException("Integer list not supported");
   }

   public final <O> void zaa(FastJsonResponse.Field<BigDecimal, O> var1, BigDecimal var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<BigInteger, O> var1, BigInteger var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<ArrayList<Integer>, O> var1, ArrayList<Integer> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Map<String, String>, O> var1, Map<String, String> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setStringMapInternal(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<Boolean, O> var1, boolean var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setBooleanInternal(var1, var1.zae, var2);
      }
   }

   public final <O> void zaa(FastJsonResponse.Field<byte[], O> var1, byte[] var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setDecodedBytesInternal(var1, var1.zae, var2);
      }
   }

   protected void zab(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigInteger> var3) {
      throw new UnsupportedOperationException("BigInteger list not supported");
   }

   public final <O> void zab(FastJsonResponse.Field<ArrayList<BigInteger>, O> var1, ArrayList<BigInteger> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zab(var1, var1.zae, var2);
      }
   }

   protected void zac(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Long> var3) {
      throw new UnsupportedOperationException("Long list not supported");
   }

   public final <O> void zac(FastJsonResponse.Field<ArrayList<Long>, O> var1, ArrayList<Long> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zac(var1, var1.zae, var2);
      }
   }

   protected void zad(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Float> var3) {
      throw new UnsupportedOperationException("Float list not supported");
   }

   public final <O> void zad(FastJsonResponse.Field<ArrayList<Float>, O> var1, ArrayList<Float> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zad(var1, var1.zae, var2);
      }
   }

   protected void zae(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Double> var3) {
      throw new UnsupportedOperationException("Double list not supported");
   }

   public final <O> void zae(FastJsonResponse.Field<ArrayList<Double>, O> var1, ArrayList<Double> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zae(var1, var1.zae, var2);
      }
   }

   protected void zaf(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigDecimal> var3) {
      throw new UnsupportedOperationException("BigDecimal list not supported");
   }

   public final <O> void zaf(FastJsonResponse.Field<ArrayList<BigDecimal>, O> var1, ArrayList<BigDecimal> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zaf(var1, var1.zae, var2);
      }
   }

   protected void zag(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Boolean> var3) {
      throw new UnsupportedOperationException("Boolean list not supported");
   }

   public final <O> void zag(FastJsonResponse.Field<ArrayList<Boolean>, O> var1, ArrayList<Boolean> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.zag(var1, var1.zae, var2);
      }
   }

   public final <O> void zah(FastJsonResponse.Field<ArrayList<String>, O> var1, ArrayList<String> var2) {
      if (var1.zak != null) {
         this.zab(var1, (Object)var2);
      } else {
         this.setStringsInternal(var1, var1.zae, var2);
      }
   }

   public static class Field<I, O> extends AbstractSafeParcelable {
      public static final zaj CREATOR = new zaj();
      protected final int zaa;
      protected final boolean zab;
      protected final int zac;
      protected final boolean zad;
      protected final String zae;
      protected final int zaf;
      protected final Class<? extends FastJsonResponse> zag;
      private final int zah;
      private final String zai;
      private zal zaj;
      private FastJsonResponse.FieldConverter<I, O> zak;

      Field(int var1, int var2, boolean var3, int var4, boolean var5, String var6, int var7, String var8, com.google.android.gms.common.server.converter.zaa var9) {
         this.zah = var1;
         this.zaa = var2;
         this.zab = var3;
         this.zac = var4;
         this.zad = var5;
         this.zae = var6;
         this.zaf = var7;
         if (var8 == null) {
            this.zag = null;
            this.zai = null;
         } else {
            this.zag = SafeParcelResponse.class;
            this.zai = var8;
         }

         if (var9 == null) {
            this.zak = null;
         } else {
            this.zak = var9.zaa();
         }
      }

      private Field(int var1, boolean var2, int var3, boolean var4, String var5, int var6, Class<? extends FastJsonResponse> var7, FastJsonResponse.FieldConverter<I, O> var8) {
         this.zah = 1;
         this.zaa = var1;
         this.zab = var2;
         this.zac = var3;
         this.zad = var4;
         this.zae = var5;
         this.zaf = var6;
         this.zag = var7;
         if (var7 == null) {
            this.zai = null;
         } else {
            this.zai = var7.getCanonicalName();
         }

         this.zak = var8;
      }

      public static FastJsonResponse.Field<byte[], byte[]> forBase64(String var0, int var1) {
         return new FastJsonResponse.Field(8, false, 8, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<Boolean, Boolean> forBoolean(String var0, int var1) {
         return new FastJsonResponse.Field(6, false, 6, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static <T extends FastJsonResponse> FastJsonResponse.Field<T, T> forConcreteType(String var0, int var1, Class<T> var2) {
         return new FastJsonResponse.Field(11, false, 11, false, var0, var1, var2, (FastJsonResponse.FieldConverter)null);
      }

      public static <T extends FastJsonResponse> FastJsonResponse.Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String var0, int var1, Class<T> var2) {
         return new FastJsonResponse.Field(11, true, 11, true, var0, var1, var2, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<Double, Double> forDouble(String var0, int var1) {
         return new FastJsonResponse.Field(4, false, 4, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<Float, Float> forFloat(String var0, int var1) {
         return new FastJsonResponse.Field(3, false, 3, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<Integer, Integer> forInteger(String var0, int var1) {
         return new FastJsonResponse.Field(0, false, 0, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<Long, Long> forLong(String var0, int var1) {
         return new FastJsonResponse.Field(2, false, 2, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<String, String> forString(String var0, int var1) {
         return new FastJsonResponse.Field(7, false, 7, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<HashMap<String, String>, HashMap<String, String>> forStringMap(String var0, int var1) {
         return new FastJsonResponse.Field(10, false, 10, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field<ArrayList<String>, ArrayList<String>> forStrings(String var0, int var1) {
         return new FastJsonResponse.Field(7, true, 7, true, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      public static FastJsonResponse.Field withConverter(String var0, int var1, FastJsonResponse.FieldConverter<?, ?> var2, boolean var3) {
         return new FastJsonResponse.Field(var2.zaa(), var3, var2.zab(), false, var0, var1, (Class)null, var2);
      }

      private final String zae() {
         String var1 = this.zai;
         String var2 = var1;
         if (var1 == null) {
            var2 = null;
         }

         return var2;
      }

      private final com.google.android.gms.common.server.converter.zaa zaf() {
         FastJsonResponse.FieldConverter var1 = this.zak;
         return var1 == null ? null : com.google.android.gms.common.server.converter.zaa.zaa(var1);
      }

      public int getSafeParcelableFieldId() {
         return this.zaf;
      }

      public String toString() {
         Objects.ToStringHelper var1 = Objects.toStringHelper(this).add("versionCode", this.zah).add("typeIn", this.zaa).add("typeInArray", this.zab).add("typeOut", this.zac).add("typeOutArray", this.zad).add("outputFieldName", this.zae).add("safeParcelFieldId", this.zaf).add("concreteTypeName", this.zae());
         Class var2 = this.zag;
         if (var2 != null) {
            var1.add("concreteType.class", var2.getCanonicalName());
         }

         FastJsonResponse.FieldConverter var3 = this.zak;
         if (var3 != null) {
            var1.add("converterName", var3.getClass().getCanonicalName());
         }

         return var1.toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         int var3 = SafeParcelWriter.beginObjectHeader(var1);
         SafeParcelWriter.writeInt(var1, 1, this.zah);
         SafeParcelWriter.writeInt(var1, 2, this.zaa);
         SafeParcelWriter.writeBoolean(var1, 3, this.zab);
         SafeParcelWriter.writeInt(var1, 4, this.zac);
         SafeParcelWriter.writeBoolean(var1, 5, this.zad);
         SafeParcelWriter.writeString(var1, 6, this.zae, false);
         SafeParcelWriter.writeInt(var1, 7, this.getSafeParcelableFieldId());
         SafeParcelWriter.writeString(var1, 8, this.zae(), false);
         SafeParcelWriter.writeParcelable(var1, 9, this.zaf(), var2, false);
         SafeParcelWriter.finishObjectHeader(var1, var3);
      }

      public final FastJsonResponse.Field<I, O> zaa() {
         return new FastJsonResponse.Field(this.zah, this.zaa, this.zab, this.zac, this.zad, this.zae, this.zaf, this.zai, this.zaf());
      }

      public final O zaa(I var1) {
         Preconditions.checkNotNull(this.zak);
         return Preconditions.checkNotNull(this.zak.zab(var1));
      }

      public final void zaa(zal var1) {
         this.zaj = var1;
      }

      public final I zab(O var1) {
         Preconditions.checkNotNull(this.zak);
         return this.zak.zaa(var1);
      }

      public final boolean zab() {
         return this.zak != null;
      }

      public final FastJsonResponse zac() throws InstantiationException, IllegalAccessException {
         Preconditions.checkNotNull(this.zag);
         Class var1 = this.zag;
         if (var1 == SafeParcelResponse.class) {
            Preconditions.checkNotNull(this.zai);
            Preconditions.checkNotNull(this.zaj, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zaj, this.zai);
         } else {
            return (FastJsonResponse)var1.newInstance();
         }
      }

      public final Map<String, FastJsonResponse.Field<?, ?>> zad() {
         Preconditions.checkNotNull(this.zai);
         Preconditions.checkNotNull(this.zaj);
         return (Map)Preconditions.checkNotNull(this.zaj.zaa(this.zai));
      }
   }

   public interface FieldConverter<I, O> {
      int zaa();

      I zaa(O var1);

      int zab();

      O zab(I var1);
   }
}
