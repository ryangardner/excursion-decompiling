package io.opencensus.trace;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;

public abstract class AttributeValue {
   AttributeValue() {
   }

   public static AttributeValue booleanAttributeValue(boolean var0) {
      return AttributeValue.AttributeValueBoolean.create(var0);
   }

   public static AttributeValue doubleAttributeValue(double var0) {
      return AttributeValue.AttributeValueDouble.create(var0);
   }

   public static AttributeValue longAttributeValue(long var0) {
      return AttributeValue.AttributeValueLong.create(var0);
   }

   public static AttributeValue stringAttributeValue(String var0) {
      return AttributeValue.AttributeValueString.create(var0);
   }

   @Deprecated
   public abstract <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4);

   public abstract <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5);

   abstract static class AttributeValueBoolean extends AttributeValue {
      static AttributeValue create(Boolean var0) {
         return new AutoValue_AttributeValue_AttributeValueBoolean((Boolean)Utils.checkNotNull(var0, "booleanValue"));
      }

      abstract Boolean getBooleanValue();

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4) {
         return var2.apply(this.getBooleanValue());
      }

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5) {
         return var2.apply(this.getBooleanValue());
      }
   }

   abstract static class AttributeValueDouble extends AttributeValue {
      static AttributeValue create(Double var0) {
         return new AutoValue_AttributeValue_AttributeValueDouble((Double)Utils.checkNotNull(var0, "doubleValue"));
      }

      abstract Double getDoubleValue();

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4) {
         return var4.apply(this.getDoubleValue());
      }

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5) {
         return var4.apply(this.getDoubleValue());
      }
   }

   abstract static class AttributeValueLong extends AttributeValue {
      static AttributeValue create(Long var0) {
         return new AutoValue_AttributeValue_AttributeValueLong((Long)Utils.checkNotNull(var0, "longValue"));
      }

      abstract Long getLongValue();

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4) {
         return var3.apply(this.getLongValue());
      }

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5) {
         return var3.apply(this.getLongValue());
      }
   }

   abstract static class AttributeValueString extends AttributeValue {
      static AttributeValue create(String var0) {
         return new AutoValue_AttributeValue_AttributeValueString((String)Utils.checkNotNull(var0, "stringValue"));
      }

      abstract String getStringValue();

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4) {
         return var1.apply(this.getStringValue());
      }

      public final <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5) {
         return var1.apply(this.getStringValue());
      }
   }
}
