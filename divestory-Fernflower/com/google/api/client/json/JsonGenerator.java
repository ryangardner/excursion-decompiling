package com.google.api.client.json;

import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class JsonGenerator implements Closeable, Flushable {
   private void serialize(boolean var1, Object var2) throws IOException {
      if (var2 != null) {
         Class var3 = var2.getClass();
         if (Data.isNull(var2)) {
            this.writeNull();
         } else if (var2 instanceof String) {
            this.writeString((String)var2);
         } else {
            boolean var4 = var2 instanceof Number;
            boolean var5 = true;
            boolean var6 = true;
            if (var4) {
               if (var1) {
                  this.writeString(var2.toString());
               } else if (var2 instanceof BigDecimal) {
                  this.writeNumber((BigDecimal)var2);
               } else if (var2 instanceof BigInteger) {
                  this.writeNumber((BigInteger)var2);
               } else if (var2 instanceof Long) {
                  this.writeNumber((Long)var2);
               } else if (var2 instanceof Float) {
                  float var7 = ((Number)var2).floatValue();
                  if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                     var1 = var6;
                  } else {
                     var1 = false;
                  }

                  Preconditions.checkArgument(var1);
                  this.writeNumber(var7);
               } else if (!(var2 instanceof Integer) && !(var2 instanceof Short) && !(var2 instanceof Byte)) {
                  double var8 = ((Number)var2).doubleValue();
                  if (!Double.isInfinite(var8) && !Double.isNaN(var8)) {
                     var1 = var5;
                  } else {
                     var1 = false;
                  }

                  Preconditions.checkArgument(var1);
                  this.writeNumber(var8);
               } else {
                  this.writeNumber(((Number)var2).intValue());
               }
            } else if (var2 instanceof Boolean) {
               this.writeBoolean((Boolean)var2);
            } else if (var2 instanceof DateTime) {
               this.writeString(((DateTime)var2).toStringRfc3339());
            } else if ((var2 instanceof Iterable || var3.isArray()) && !(var2 instanceof Map) && !(var2 instanceof GenericData)) {
               this.writeStartArray();
               Iterator var15 = Types.iterableOf(var2).iterator();

               while(var15.hasNext()) {
                  this.serialize(var1, var15.next());
               }

               this.writeEndArray();
            } else if (var3.isEnum()) {
               String var14 = FieldInfo.of((Enum)var2).getName();
               if (var14 == null) {
                  this.writeNull();
               } else {
                  this.writeString(var14);
               }
            } else {
               this.writeStartObject();
               boolean var10;
               if (var2 instanceof Map && !(var2 instanceof GenericData)) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               ClassInfo var16;
               if (var10) {
                  var16 = null;
               } else {
                  var16 = ClassInfo.of(var3);
               }

               Iterator var11 = Data.mapOf(var2).entrySet().iterator();

               while(true) {
                  Entry var12;
                  do {
                     if (!var11.hasNext()) {
                        this.writeEndObject();
                        return;
                     }

                     var12 = (Entry)var11.next();
                     var2 = var12.getValue();
                  } while(var2 == null);

                  String var17 = (String)var12.getKey();
                  if (var10) {
                     var6 = var1;
                  } else {
                     Field var13 = var16.getField(var17);
                     if (var13 != null && var13.getAnnotation(JsonString.class) != null) {
                        var6 = true;
                     } else {
                        var6 = false;
                     }
                  }

                  this.writeFieldName(var17);
                  this.serialize(var6, var2);
               }
            }
         }

      }
   }

   public abstract void close() throws IOException;

   public void enablePrettyPrint() throws IOException {
   }

   public abstract void flush() throws IOException;

   public abstract JsonFactory getFactory();

   public final void serialize(Object var1) throws IOException {
      this.serialize(false, var1);
   }

   public abstract void writeBoolean(boolean var1) throws IOException;

   public abstract void writeEndArray() throws IOException;

   public abstract void writeEndObject() throws IOException;

   public abstract void writeFieldName(String var1) throws IOException;

   public abstract void writeNull() throws IOException;

   public abstract void writeNumber(double var1) throws IOException;

   public abstract void writeNumber(float var1) throws IOException;

   public abstract void writeNumber(int var1) throws IOException;

   public abstract void writeNumber(long var1) throws IOException;

   public abstract void writeNumber(String var1) throws IOException;

   public abstract void writeNumber(BigDecimal var1) throws IOException;

   public abstract void writeNumber(BigInteger var1) throws IOException;

   public abstract void writeStartArray() throws IOException;

   public abstract void writeStartObject() throws IOException;

   public abstract void writeString(String var1) throws IOException;
}
