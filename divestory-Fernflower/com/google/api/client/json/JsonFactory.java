package com.google.api.client.json;

import com.google.api.client.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class JsonFactory {
   private ByteArrayOutputStream toByteStream(Object var1, boolean var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      JsonGenerator var4 = this.createJsonGenerator(var3, Charsets.UTF_8);
      if (var2) {
         var4.enablePrettyPrint();
      }

      var4.serialize(var1);
      var4.flush();
      return var3;
   }

   private String toString(Object var1, boolean var2) throws IOException {
      return this.toByteStream(var1, var2).toString("UTF-8");
   }

   public abstract JsonGenerator createJsonGenerator(OutputStream var1, Charset var2) throws IOException;

   public abstract JsonGenerator createJsonGenerator(Writer var1) throws IOException;

   public final JsonObjectParser createJsonObjectParser() {
      return new JsonObjectParser(this);
   }

   public abstract JsonParser createJsonParser(InputStream var1) throws IOException;

   public abstract JsonParser createJsonParser(InputStream var1, Charset var2) throws IOException;

   public abstract JsonParser createJsonParser(Reader var1) throws IOException;

   public abstract JsonParser createJsonParser(String var1) throws IOException;

   public final <T> T fromInputStream(InputStream var1, Class<T> var2) throws IOException {
      return this.createJsonParser(var1).parseAndClose(var2);
   }

   public final <T> T fromInputStream(InputStream var1, Charset var2, Class<T> var3) throws IOException {
      return this.createJsonParser(var1, var2).parseAndClose(var3);
   }

   public final <T> T fromReader(Reader var1, Class<T> var2) throws IOException {
      return this.createJsonParser(var1).parseAndClose(var2);
   }

   public final <T> T fromString(String var1, Class<T> var2) throws IOException {
      return this.createJsonParser(var1).parse(var2);
   }

   public final byte[] toByteArray(Object var1) throws IOException {
      return this.toByteStream(var1, false).toByteArray();
   }

   public final String toPrettyString(Object var1) throws IOException {
      return this.toString(var1, true);
   }

   public final String toString(Object var1) throws IOException {
      return this.toString(var1, false);
   }
}
