package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractGoogleJsonClient extends AbstractGoogleClient {
   protected AbstractGoogleJsonClient(AbstractGoogleJsonClient.Builder var1) {
      super(var1);
   }

   public final JsonFactory getJsonFactory() {
      return this.getObjectParser().getJsonFactory();
   }

   public JsonObjectParser getObjectParser() {
      return (JsonObjectParser)super.getObjectParser();
   }

   public abstract static class Builder extends AbstractGoogleClient.Builder {
      protected Builder(HttpTransport var1, JsonFactory var2, String var3, String var4, HttpRequestInitializer var5, boolean var6) {
         JsonObjectParser.Builder var7 = new JsonObjectParser.Builder(var2);
         Object var8;
         if (var6) {
            var8 = Arrays.asList("data", "error");
         } else {
            var8 = Collections.emptySet();
         }

         super(var1, var3, var4, var7.setWrapperKeys((Collection)var8).build(), var5);
      }

      public abstract AbstractGoogleJsonClient build();

      public final JsonFactory getJsonFactory() {
         return this.getObjectParser().getJsonFactory();
      }

      public final JsonObjectParser getObjectParser() {
         return (JsonObjectParser)super.getObjectParser();
      }

      public AbstractGoogleJsonClient.Builder setApplicationName(String var1) {
         return (AbstractGoogleJsonClient.Builder)super.setApplicationName(var1);
      }

      public AbstractGoogleJsonClient.Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer var1) {
         return (AbstractGoogleJsonClient.Builder)super.setGoogleClientRequestInitializer(var1);
      }

      public AbstractGoogleJsonClient.Builder setHttpRequestInitializer(HttpRequestInitializer var1) {
         return (AbstractGoogleJsonClient.Builder)super.setHttpRequestInitializer(var1);
      }

      public AbstractGoogleJsonClient.Builder setRootUrl(String var1) {
         return (AbstractGoogleJsonClient.Builder)super.setRootUrl(var1);
      }

      public AbstractGoogleJsonClient.Builder setServicePath(String var1) {
         return (AbstractGoogleJsonClient.Builder)super.setServicePath(var1);
      }

      public AbstractGoogleJsonClient.Builder setSuppressAllChecks(boolean var1) {
         return (AbstractGoogleJsonClient.Builder)super.setSuppressAllChecks(var1);
      }

      public AbstractGoogleJsonClient.Builder setSuppressPatternChecks(boolean var1) {
         return (AbstractGoogleJsonClient.Builder)super.setSuppressPatternChecks(var1);
      }

      public AbstractGoogleJsonClient.Builder setSuppressRequiredParameterChecks(boolean var1) {
         return (AbstractGoogleJsonClient.Builder)super.setSuppressRequiredParameterChecks(var1);
      }
   }
}
