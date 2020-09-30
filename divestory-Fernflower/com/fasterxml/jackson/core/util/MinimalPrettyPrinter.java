package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import java.io.IOException;
import java.io.Serializable;

public class MinimalPrettyPrinter implements PrettyPrinter, Serializable {
   private static final long serialVersionUID = 1L;
   protected String _rootValueSeparator;
   protected Separators _separators;

   public MinimalPrettyPrinter() {
      this(DEFAULT_ROOT_VALUE_SEPARATOR.toString());
   }

   public MinimalPrettyPrinter(String var1) {
      this._rootValueSeparator = var1;
      this._separators = DEFAULT_SEPARATORS;
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException {
   }

   public void beforeObjectEntries(JsonGenerator var1) throws IOException {
   }

   public void setRootValueSeparator(String var1) {
      this._rootValueSeparator = var1;
   }

   public MinimalPrettyPrinter setSeparators(Separators var1) {
      this._separators = var1;
      return this;
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getArrayValueSeparator());
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException {
      var1.writeRaw(']');
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException {
      var1.writeRaw('}');
   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getObjectEntrySeparator());
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getObjectFieldValueSeparator());
   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException {
      String var2 = this._rootValueSeparator;
      if (var2 != null) {
         var1.writeRaw(var2);
      }

   }

   public void writeStartArray(JsonGenerator var1) throws IOException {
      var1.writeRaw('[');
   }

   public void writeStartObject(JsonGenerator var1) throws IOException {
      var1.writeRaw('{');
   }
}
