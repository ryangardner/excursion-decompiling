package com.google.api.client.googleapis.util;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public final class Utils {
   private Utils() {
   }

   public static JsonFactory getDefaultJsonFactory() {
      return Utils.JsonFactoryInstanceHolder.INSTANCE;
   }

   public static HttpTransport getDefaultTransport() {
      return Utils.TransportInstanceHolder.INSTANCE;
   }

   private static class JsonFactoryInstanceHolder {
      static final JsonFactory INSTANCE = new JacksonFactory();
   }

   private static class TransportInstanceHolder {
      static final HttpTransport INSTANCE = new NetHttpTransport();
   }
}
