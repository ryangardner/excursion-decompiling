package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class JacksonGenerator extends JsonGenerator {
   private final JacksonFactory factory;
   private final com.fasterxml.jackson.core.JsonGenerator generator;

   JacksonGenerator(JacksonFactory var1, com.fasterxml.jackson.core.JsonGenerator var2) {
      this.factory = var1;
      this.generator = var2;
   }

   public void close() throws IOException {
      this.generator.close();
   }

   public void enablePrettyPrint() throws IOException {
      this.generator.useDefaultPrettyPrinter();
   }

   public void flush() throws IOException {
      this.generator.flush();
   }

   public JacksonFactory getFactory() {
      return this.factory;
   }

   public void writeBoolean(boolean var1) throws IOException {
      this.generator.writeBoolean(var1);
   }

   public void writeEndArray() throws IOException {
      this.generator.writeEndArray();
   }

   public void writeEndObject() throws IOException {
      this.generator.writeEndObject();
   }

   public void writeFieldName(String var1) throws IOException {
      this.generator.writeFieldName(var1);
   }

   public void writeNull() throws IOException {
      this.generator.writeNull();
   }

   public void writeNumber(double var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(float var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(int var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(long var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(String var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeNumber(BigInteger var1) throws IOException {
      this.generator.writeNumber(var1);
   }

   public void writeStartArray() throws IOException {
      this.generator.writeStartArray();
   }

   public void writeStartObject() throws IOException {
      this.generator.writeStartObject();
   }

   public void writeString(String var1) throws IOException {
      this.generator.writeString(var1);
   }
}
