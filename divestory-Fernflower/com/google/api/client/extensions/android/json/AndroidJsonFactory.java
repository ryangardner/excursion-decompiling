package com.google.api.client.extensions.android.json;

import android.util.JsonReader;
import android.util.JsonWriter;
import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Charsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

public class AndroidJsonFactory extends JsonFactory {
   public AndroidJsonFactory() {
      AndroidUtils.checkMinimumSdkLevel(11);
   }

   public static AndroidJsonFactory getDefaultInstance() {
      return AndroidJsonFactory.InstanceHolder.INSTANCE;
   }

   public JsonGenerator createJsonGenerator(OutputStream var1, Charset var2) {
      return this.createJsonGenerator(new OutputStreamWriter(var1, var2));
   }

   public JsonGenerator createJsonGenerator(Writer var1) {
      return new AndroidJsonGenerator(this, new JsonWriter(var1));
   }

   public JsonParser createJsonParser(InputStream var1) {
      return this.createJsonParser((Reader)(new InputStreamReader(var1, Charsets.UTF_8)));
   }

   public JsonParser createJsonParser(InputStream var1, Charset var2) {
      return var2 == null ? this.createJsonParser(var1) : this.createJsonParser((Reader)(new InputStreamReader(var1, var2)));
   }

   public JsonParser createJsonParser(Reader var1) {
      return new AndroidJsonParser(this, new JsonReader(var1));
   }

   public JsonParser createJsonParser(String var1) {
      return this.createJsonParser((Reader)(new StringReader(var1)));
   }

   static class InstanceHolder {
      static final AndroidJsonFactory INSTANCE = new AndroidJsonFactory();
   }
}
