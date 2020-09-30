package com.google.api.client.googleapis.json;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class GoogleJsonErrorContainer extends GenericJson {
   @Key
   private GoogleJsonError error;

   public GoogleJsonErrorContainer clone() {
      return (GoogleJsonErrorContainer)super.clone();
   }

   public final GoogleJsonError getError() {
      return this.error;
   }

   public GoogleJsonErrorContainer set(String var1, Object var2) {
      return (GoogleJsonErrorContainer)super.set(var1, var2);
   }

   public final void setError(GoogleJsonError var1) {
      this.error = var1;
   }
}
