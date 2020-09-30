package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import java.io.IOException;

public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T> {
   private final Object jsonContent;

   protected AbstractGoogleJsonClientRequest(AbstractGoogleJsonClient var1, String var2, String var3, Object var4, Class<T> var5) {
      String var6 = null;
      JsonHttpContent var7 = null;
      JsonHttpContent var8;
      if (var4 == null) {
         var8 = var7;
      } else {
         var7 = new JsonHttpContent(var1.getJsonFactory(), var4);
         if (!var1.getObjectParser().getWrapperKeys().isEmpty()) {
            var6 = "data";
         }

         var8 = var7.setWrapperKey(var6);
      }

      super(var1, var2, var3, var8, var5);
      this.jsonContent = var4;
   }

   public AbstractGoogleJsonClient getAbstractGoogleClient() {
      return (AbstractGoogleJsonClient)super.getAbstractGoogleClient();
   }

   public Object getJsonContent() {
      return this.jsonContent;
   }

   protected GoogleJsonResponseException newExceptionOnError(HttpResponse var1) {
      return GoogleJsonResponseException.from(this.getAbstractGoogleClient().getJsonFactory(), var1);
   }

   public final void queue(BatchRequest var1, JsonBatchCallback<T> var2) throws IOException {
      super.queue(var1, GoogleJsonErrorContainer.class, var2);
   }

   public AbstractGoogleJsonClientRequest<T> set(String var1, Object var2) {
      return (AbstractGoogleJsonClientRequest)super.set(var1, var2);
   }

   public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(boolean var1) {
      return (AbstractGoogleJsonClientRequest)super.setDisableGZipContent(var1);
   }

   public AbstractGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders var1) {
      return (AbstractGoogleJsonClientRequest)super.setRequestHeaders(var1);
   }
}
