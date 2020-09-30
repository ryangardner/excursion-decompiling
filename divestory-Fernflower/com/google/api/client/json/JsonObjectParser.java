package com.google.api.client.json;

import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonObjectParser implements ObjectParser {
   private final JsonFactory jsonFactory;
   private final Set<String> wrapperKeys;

   public JsonObjectParser(JsonFactory var1) {
      this(new JsonObjectParser.Builder(var1));
   }

   protected JsonObjectParser(JsonObjectParser.Builder var1) {
      this.jsonFactory = var1.jsonFactory;
      this.wrapperKeys = new HashSet(var1.wrapperKeys);
   }

   private void initializeParser(JsonParser var1) throws IOException {
      if (!this.wrapperKeys.isEmpty()) {
         Throwable var10000;
         label94: {
            boolean var10001;
            boolean var2;
            label93: {
               label92: {
                  try {
                     if (var1.skipToKey(this.wrapperKeys) != null && var1.getCurrentToken() != JsonToken.END_OBJECT) {
                        break label92;
                     }
                  } catch (Throwable var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label94;
                  }

                  var2 = false;
                  break label93;
               }

               var2 = true;
            }

            label83:
            try {
               Preconditions.checkArgument(var2, "wrapper key(s) not found: %s", this.wrapperKeys);
               return;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label83;
            }
         }

         Throwable var3 = var10000;
         var1.close();
         throw var3;
      }
   }

   public final JsonFactory getJsonFactory() {
      return this.jsonFactory;
   }

   public Set<String> getWrapperKeys() {
      return Collections.unmodifiableSet(this.wrapperKeys);
   }

   public <T> T parseAndClose(InputStream var1, Charset var2, Class<T> var3) throws IOException {
      return this.parseAndClose(var1, var2, (Type)var3);
   }

   public Object parseAndClose(InputStream var1, Charset var2, Type var3) throws IOException {
      JsonParser var4 = this.jsonFactory.createJsonParser(var1, var2);
      this.initializeParser(var4);
      return var4.parse(var3, true);
   }

   public <T> T parseAndClose(Reader var1, Class<T> var2) throws IOException {
      return this.parseAndClose(var1, (Type)var2);
   }

   public Object parseAndClose(Reader var1, Type var2) throws IOException {
      JsonParser var3 = this.jsonFactory.createJsonParser(var1);
      this.initializeParser(var3);
      return var3.parse(var2, true);
   }

   public static class Builder {
      final JsonFactory jsonFactory;
      Collection<String> wrapperKeys = Sets.newHashSet();

      public Builder(JsonFactory var1) {
         this.jsonFactory = (JsonFactory)Preconditions.checkNotNull(var1);
      }

      public JsonObjectParser build() {
         return new JsonObjectParser(this);
      }

      public final JsonFactory getJsonFactory() {
         return this.jsonFactory;
      }

      public final Collection<String> getWrapperKeys() {
         return this.wrapperKeys;
      }

      public JsonObjectParser.Builder setWrapperKeys(Collection<String> var1) {
         this.wrapperKeys = var1;
         return this;
      }
   }
}
