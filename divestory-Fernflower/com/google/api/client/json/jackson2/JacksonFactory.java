package com.google.api.client.json.jackson2;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public final class JacksonFactory extends JsonFactory {
   private final com.fasterxml.jackson.core.JsonFactory factory;

   public JacksonFactory() {
      com.fasterxml.jackson.core.JsonFactory var1 = new com.fasterxml.jackson.core.JsonFactory();
      this.factory = var1;
      var1.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
   }

   static JsonToken convert(com.fasterxml.jackson.core.JsonToken var0) {
      if (var0 == null) {
         return null;
      } else {
         switch(null.$SwitchMap$com$fasterxml$jackson$core$JsonToken[var0.ordinal()]) {
         case 1:
            return JsonToken.END_ARRAY;
         case 2:
            return JsonToken.START_ARRAY;
         case 3:
            return JsonToken.END_OBJECT;
         case 4:
            return JsonToken.START_OBJECT;
         case 5:
            return JsonToken.VALUE_FALSE;
         case 6:
            return JsonToken.VALUE_TRUE;
         case 7:
            return JsonToken.VALUE_NULL;
         case 8:
            return JsonToken.VALUE_STRING;
         case 9:
            return JsonToken.VALUE_NUMBER_FLOAT;
         case 10:
            return JsonToken.VALUE_NUMBER_INT;
         case 11:
            return JsonToken.FIELD_NAME;
         default:
            return JsonToken.NOT_AVAILABLE;
         }
      }
   }

   public static JacksonFactory getDefaultInstance() {
      return JacksonFactory.InstanceHolder.INSTANCE;
   }

   public com.google.api.client.json.JsonGenerator createJsonGenerator(OutputStream var1, Charset var2) throws IOException {
      return new JacksonGenerator(this, this.factory.createJsonGenerator(var1, JsonEncoding.UTF8));
   }

   public com.google.api.client.json.JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      return new JacksonGenerator(this, this.factory.createJsonGenerator(var1));
   }

   public JsonParser createJsonParser(InputStream var1) throws IOException {
      Preconditions.checkNotNull(var1);
      return new JacksonParser(this, this.factory.createJsonParser(var1));
   }

   public JsonParser createJsonParser(InputStream var1, Charset var2) throws IOException {
      Preconditions.checkNotNull(var1);
      return new JacksonParser(this, this.factory.createJsonParser(var1));
   }

   public JsonParser createJsonParser(Reader var1) throws IOException {
      Preconditions.checkNotNull(var1);
      return new JacksonParser(this, this.factory.createJsonParser(var1));
   }

   public JsonParser createJsonParser(String var1) throws IOException {
      Preconditions.checkNotNull(var1);
      return new JacksonParser(this, this.factory.createJsonParser(var1));
   }

   static class InstanceHolder {
      static final JacksonFactory INSTANCE = new JacksonFactory();
   }
}
