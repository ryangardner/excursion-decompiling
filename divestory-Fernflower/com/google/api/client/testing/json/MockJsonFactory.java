package com.google.api.client.testing.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class MockJsonFactory extends JsonFactory {
   public JsonGenerator createJsonGenerator(OutputStream var1, Charset var2) throws IOException {
      return new MockJsonGenerator(this);
   }

   public JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      return new MockJsonGenerator(this);
   }

   public JsonParser createJsonParser(InputStream var1) throws IOException {
      return new MockJsonParser(this);
   }

   public JsonParser createJsonParser(InputStream var1, Charset var2) throws IOException {
      return new MockJsonParser(this);
   }

   public JsonParser createJsonParser(Reader var1) throws IOException {
      return new MockJsonParser(this);
   }

   public JsonParser createJsonParser(String var1) throws IOException {
      return new MockJsonParser(this);
   }
}
