package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import java.io.IOException;

public class CommonGoogleJsonClientRequestInitializer extends CommonGoogleClientRequestInitializer {
   @Deprecated
   public CommonGoogleJsonClientRequestInitializer() {
   }

   @Deprecated
   public CommonGoogleJsonClientRequestInitializer(String var1) {
      super(var1);
   }

   @Deprecated
   public CommonGoogleJsonClientRequestInitializer(String var1, String var2) {
      super(var1, var2);
   }

   public final void initialize(AbstractGoogleClientRequest<?> var1) throws IOException {
      super.initialize(var1);
      this.initializeJsonRequest((AbstractGoogleJsonClientRequest)var1);
   }

   protected void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> var1) throws IOException {
   }

   public static class Builder extends CommonGoogleClientRequestInitializer.Builder {
      protected CommonGoogleJsonClientRequestInitializer.Builder self() {
         return this;
      }
   }
}
