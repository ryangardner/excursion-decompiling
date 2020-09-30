package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class AbstractGoogleClient {
   private static final Logger logger = Logger.getLogger(AbstractGoogleClient.class.getName());
   private final String applicationName;
   private final String batchPath;
   private final GoogleClientRequestInitializer googleClientRequestInitializer;
   private final ObjectParser objectParser;
   private final HttpRequestFactory requestFactory;
   private final String rootUrl;
   private final String servicePath;
   private final boolean suppressPatternChecks;
   private final boolean suppressRequiredParameterChecks;

   protected AbstractGoogleClient(AbstractGoogleClient.Builder var1) {
      this.googleClientRequestInitializer = var1.googleClientRequestInitializer;
      this.rootUrl = normalizeRootUrl(var1.rootUrl);
      this.servicePath = normalizeServicePath(var1.servicePath);
      this.batchPath = var1.batchPath;
      if (Strings.isNullOrEmpty(var1.applicationName)) {
         logger.warning("Application name is not set. Call Builder#setApplicationName.");
      }

      this.applicationName = var1.applicationName;
      HttpRequestFactory var2;
      if (var1.httpRequestInitializer == null) {
         var2 = var1.transport.createRequestFactory();
      } else {
         var2 = var1.transport.createRequestFactory(var1.httpRequestInitializer);
      }

      this.requestFactory = var2;
      this.objectParser = var1.objectParser;
      this.suppressPatternChecks = var1.suppressPatternChecks;
      this.suppressRequiredParameterChecks = var1.suppressRequiredParameterChecks;
   }

   static String normalizeRootUrl(String var0) {
      Preconditions.checkNotNull(var0, "root URL cannot be null.");
      String var1 = var0;
      if (!var0.endsWith("/")) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append("/");
         var1 = var2.toString();
      }

      return var1;
   }

   static String normalizeServicePath(String var0) {
      Preconditions.checkNotNull(var0, "service path cannot be null");
      String var1;
      if (var0.length() == 1) {
         Preconditions.checkArgument("/".equals(var0), "service path must equal \"/\" if it is of length 1.");
         var1 = "";
      } else {
         var1 = var0;
         if (var0.length() > 0) {
            String var2 = var0;
            if (!var0.endsWith("/")) {
               StringBuilder var3 = new StringBuilder();
               var3.append(var0);
               var3.append("/");
               var2 = var3.toString();
            }

            var1 = var2;
            if (var2.startsWith("/")) {
               var1 = var2.substring(1);
            }
         }
      }

      return var1;
   }

   public final BatchRequest batch() {
      return this.batch((HttpRequestInitializer)null);
   }

   public final BatchRequest batch(HttpRequestInitializer var1) {
      BatchRequest var3 = new BatchRequest(this.getRequestFactory().getTransport(), var1);
      StringBuilder var2;
      if (Strings.isNullOrEmpty(this.batchPath)) {
         var2 = new StringBuilder();
         var2.append(this.getRootUrl());
         var2.append("batch");
         var3.setBatchUrl(new GenericUrl(var2.toString()));
      } else {
         var2 = new StringBuilder();
         var2.append(this.getRootUrl());
         var2.append(this.batchPath);
         var3.setBatchUrl(new GenericUrl(var2.toString()));
      }

      return var3;
   }

   public final String getApplicationName() {
      return this.applicationName;
   }

   public final String getBaseUrl() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.rootUrl);
      var1.append(this.servicePath);
      return var1.toString();
   }

   public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
      return this.googleClientRequestInitializer;
   }

   public ObjectParser getObjectParser() {
      return this.objectParser;
   }

   public final HttpRequestFactory getRequestFactory() {
      return this.requestFactory;
   }

   public final String getRootUrl() {
      return this.rootUrl;
   }

   public final String getServicePath() {
      return this.servicePath;
   }

   public final boolean getSuppressPatternChecks() {
      return this.suppressPatternChecks;
   }

   public final boolean getSuppressRequiredParameterChecks() {
      return this.suppressRequiredParameterChecks;
   }

   protected void initialize(AbstractGoogleClientRequest<?> var1) throws IOException {
      if (this.getGoogleClientRequestInitializer() != null) {
         this.getGoogleClientRequestInitializer().initialize(var1);
      }

   }

   public abstract static class Builder {
      String applicationName;
      String batchPath;
      GoogleClientRequestInitializer googleClientRequestInitializer;
      HttpRequestInitializer httpRequestInitializer;
      final ObjectParser objectParser;
      String rootUrl;
      String servicePath;
      boolean suppressPatternChecks;
      boolean suppressRequiredParameterChecks;
      final HttpTransport transport;

      protected Builder(HttpTransport var1, String var2, String var3, ObjectParser var4, HttpRequestInitializer var5) {
         this.transport = (HttpTransport)Preconditions.checkNotNull(var1);
         this.objectParser = var4;
         this.setRootUrl(var2);
         this.setServicePath(var3);
         this.httpRequestInitializer = var5;
      }

      public abstract AbstractGoogleClient build();

      public final String getApplicationName() {
         return this.applicationName;
      }

      public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
         return this.googleClientRequestInitializer;
      }

      public final HttpRequestInitializer getHttpRequestInitializer() {
         return this.httpRequestInitializer;
      }

      public ObjectParser getObjectParser() {
         return this.objectParser;
      }

      public final String getRootUrl() {
         return this.rootUrl;
      }

      public final String getServicePath() {
         return this.servicePath;
      }

      public final boolean getSuppressPatternChecks() {
         return this.suppressPatternChecks;
      }

      public final boolean getSuppressRequiredParameterChecks() {
         return this.suppressRequiredParameterChecks;
      }

      public final HttpTransport getTransport() {
         return this.transport;
      }

      public AbstractGoogleClient.Builder setApplicationName(String var1) {
         this.applicationName = var1;
         return this;
      }

      public AbstractGoogleClient.Builder setBatchPath(String var1) {
         this.batchPath = var1;
         return this;
      }

      public AbstractGoogleClient.Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer var1) {
         this.googleClientRequestInitializer = var1;
         return this;
      }

      public AbstractGoogleClient.Builder setHttpRequestInitializer(HttpRequestInitializer var1) {
         this.httpRequestInitializer = var1;
         return this;
      }

      public AbstractGoogleClient.Builder setRootUrl(String var1) {
         this.rootUrl = AbstractGoogleClient.normalizeRootUrl(var1);
         return this;
      }

      public AbstractGoogleClient.Builder setServicePath(String var1) {
         this.servicePath = AbstractGoogleClient.normalizeServicePath(var1);
         return this;
      }

      public AbstractGoogleClient.Builder setSuppressAllChecks(boolean var1) {
         return this.setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
      }

      public AbstractGoogleClient.Builder setSuppressPatternChecks(boolean var1) {
         this.suppressPatternChecks = var1;
         return this;
      }

      public AbstractGoogleClient.Builder setSuppressRequiredParameterChecks(boolean var1) {
         this.suppressRequiredParameterChecks = var1;
         return this;
      }
   }
}
