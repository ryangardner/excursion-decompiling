package com.fasterxml.jackson.core.type;

import com.fasterxml.jackson.core.JsonToken;

public class WritableTypeId {
   public String asProperty;
   public Object extra;
   public Object forValue;
   public Class<?> forValueType;
   public Object id;
   public WritableTypeId.Inclusion include;
   public JsonToken valueShape;
   public boolean wrapperWritten;

   public WritableTypeId() {
   }

   public WritableTypeId(Object var1, JsonToken var2) {
      this(var1, (JsonToken)var2, (Object)null);
   }

   public WritableTypeId(Object var1, JsonToken var2, Object var3) {
      this.forValue = var1;
      this.id = var3;
      this.valueShape = var2;
   }

   public WritableTypeId(Object var1, Class<?> var2, JsonToken var3) {
      this(var1, (JsonToken)var3, (Object)null);
      this.forValueType = var2;
   }

   public static enum Inclusion {
      METADATA_PROPERTY,
      PARENT_PROPERTY,
      PAYLOAD_PROPERTY,
      WRAPPER_ARRAY,
      WRAPPER_OBJECT;

      static {
         WritableTypeId.Inclusion var0 = new WritableTypeId.Inclusion("PARENT_PROPERTY", 4);
         PARENT_PROPERTY = var0;
      }

      public boolean requiresObjectContext() {
         boolean var1;
         if (this != METADATA_PROPERTY && this != PAYLOAD_PROPERTY) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }
   }
}
