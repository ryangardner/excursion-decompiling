package com.google.api.services.drive;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import java.io.IOException;

public class DriveRequestInitializer extends CommonGoogleJsonClientRequestInitializer {
   public DriveRequestInitializer() {
   }

   public DriveRequestInitializer(String var1) {
      super(var1);
   }

   public DriveRequestInitializer(String var1, String var2) {
      super(var1, var2);
   }

   protected void initializeDriveRequest(DriveRequest<?> var1) throws IOException {
   }

   public final void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> var1) throws IOException {
      super.initializeJsonRequest(var1);
      this.initializeDriveRequest((DriveRequest)var1);
   }
}
