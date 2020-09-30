package com.google.api.client.googleapis.batch.json;

import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.http.HttpHeaders;
import java.io.IOException;

public abstract class JsonBatchCallback<T> implements BatchCallback<T, GoogleJsonErrorContainer> {
   public abstract void onFailure(GoogleJsonError var1, HttpHeaders var2) throws IOException;

   public final void onFailure(GoogleJsonErrorContainer var1, HttpHeaders var2) throws IOException {
      this.onFailure(var1.getError(), var2);
   }
}
