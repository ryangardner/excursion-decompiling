package com.google.api.client.googleapis.json;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleJsonResponseException extends HttpResponseException {
   private static final long serialVersionUID = 409811126989994864L;
   private final transient GoogleJsonError details;

   public GoogleJsonResponseException(HttpResponseException.Builder var1, GoogleJsonError var2) {
      super(var1);
      this.details = var2;
   }

   public static HttpResponse execute(JsonFactory var0, HttpRequest var1) throws GoogleJsonResponseException, IOException {
      Preconditions.checkNotNull(var0);
      boolean var2 = var1.getThrowExceptionOnExecuteError();
      if (var2) {
         var1.setThrowExceptionOnExecuteError(false);
      }

      HttpResponse var3 = var1.execute();
      var1.setThrowExceptionOnExecuteError(var2);
      if (var2 && !var3.isSuccessStatusCode()) {
         throw from(var0, var3);
      } else {
         return var3;
      }
   }

   public static GoogleJsonResponseException from(JsonFactory param0, HttpResponse param1) {
      // $FF: Couldn't be decompiled
   }

   public final GoogleJsonError getDetails() {
      return this.details;
   }
}
