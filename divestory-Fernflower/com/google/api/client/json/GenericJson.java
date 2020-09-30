package com.google.api.client.json;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Throwables;
import java.io.IOException;

public class GenericJson extends GenericData implements Cloneable {
   private JsonFactory jsonFactory;

   public GenericJson clone() {
      return (GenericJson)super.clone();
   }

   public final JsonFactory getFactory() {
      return this.jsonFactory;
   }

   public GenericJson set(String var1, Object var2) {
      return (GenericJson)super.set(var1, var2);
   }

   public final void setFactory(JsonFactory var1) {
      this.jsonFactory = var1;
   }

   public String toPrettyString() throws IOException {
      JsonFactory var1 = this.jsonFactory;
      return var1 != null ? var1.toPrettyString(this) : super.toString();
   }

   public String toString() {
      JsonFactory var1 = this.jsonFactory;
      if (var1 != null) {
         try {
            String var3 = var1.toString(this);
            return var3;
         } catch (IOException var2) {
            throw Throwables.propagate(var2);
         }
      } else {
         return super.toString();
      }
   }
}
