package com.google.api.client.extensions.android.json;

import android.util.JsonWriter;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class AndroidJsonGenerator extends JsonGenerator {
   private final AndroidJsonFactory factory;
   private final JsonWriter writer;

   AndroidJsonGenerator(AndroidJsonFactory var1, JsonWriter var2) {
      this.factory = var1;
      this.writer = var2;
      var2.setLenient(true);
   }

   public void close() throws IOException {
      this.writer.close();
   }

   public void enablePrettyPrint() throws IOException {
      this.writer.setIndent("  ");
   }

   public void flush() throws IOException {
      this.writer.flush();
   }

   public JsonFactory getFactory() {
      return this.factory;
   }

   public void writeBoolean(boolean var1) throws IOException {
      this.writer.value(var1);
   }

   public void writeEndArray() throws IOException {
      this.writer.endArray();
   }

   public void writeEndObject() throws IOException {
      this.writer.endObject();
   }

   public void writeFieldName(String var1) throws IOException {
      this.writer.name(var1);
   }

   public void writeNull() throws IOException {
      this.writer.nullValue();
   }

   public void writeNumber(double var1) throws IOException {
      this.writer.value(var1);
   }

   public void writeNumber(float var1) throws IOException {
      this.writer.value((double)var1);
   }

   public void writeNumber(int var1) throws IOException {
      this.writer.value((long)var1);
   }

   public void writeNumber(long var1) throws IOException {
      this.writer.value(var1);
   }

   public void writeNumber(String var1) throws IOException {
      this.writer.value(new AndroidJsonGenerator.StringNumber(var1));
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      this.writer.value(var1);
   }

   public void writeNumber(BigInteger var1) throws IOException {
      this.writer.value(var1);
   }

   public void writeStartArray() throws IOException {
      this.writer.beginArray();
   }

   public void writeStartObject() throws IOException {
      this.writer.beginObject();
   }

   public void writeString(String var1) throws IOException {
      this.writer.value(var1);
   }

   static final class StringNumber extends Number {
      private static final long serialVersionUID = 1L;
      private final String encodedValue;

      StringNumber(String var1) {
         this.encodedValue = var1;
      }

      public double doubleValue() {
         return 0.0D;
      }

      public float floatValue() {
         return 0.0F;
      }

      public int intValue() {
         return 0;
      }

      public long longValue() {
         return 0L;
      }

      public String toString() {
         return this.encodedValue;
      }
   }
}
