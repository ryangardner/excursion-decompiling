package com.google.api.client.googleapis.batch;

import com.google.api.client.http.HttpHeaders;
import java.io.IOException;

public interface BatchCallback<T, E> {
   void onFailure(E var1, HttpHeaders var2) throws IOException;

   void onSuccess(T var1, HttpHeaders var2) throws IOException;
}
