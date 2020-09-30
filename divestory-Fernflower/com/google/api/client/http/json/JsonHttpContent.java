package com.google.api.client.http.json;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

public class JsonHttpContent extends AbstractHttpContent {
   private final Object data;
   private final JsonFactory jsonFactory;
   private String wrapperKey;

   public JsonHttpContent(JsonFactory var1, Object var2) {
      super("application/json; charset=UTF-8");
      this.jsonFactory = (JsonFactory)Preconditions.checkNotNull(var1);
      this.data = Preconditions.checkNotNull(var2);
   }

   public final Object getData() {
      return this.data;
   }

   public final JsonFactory getJsonFactory() {
      return this.jsonFactory;
   }

   public final String getWrapperKey() {
      return this.wrapperKey;
   }

   public JsonHttpContent setMediaType(HttpMediaType var1) {
      super.setMediaType(var1);
      return this;
   }

   public JsonHttpContent setWrapperKey(String var1) {
      this.wrapperKey = var1;
      return this;
   }

   public void writeTo(OutputStream var1) throws IOException {
      JsonGenerator var2 = this.jsonFactory.createJsonGenerator(var1, this.getCharset());
      if (this.wrapperKey != null) {
         var2.writeStartObject();
         var2.writeFieldName(this.wrapperKey);
      }

      var2.serialize(this.data);
      if (this.wrapperKey != null) {
         var2.writeEndObject();
      }

      var2.flush();
   }
}
