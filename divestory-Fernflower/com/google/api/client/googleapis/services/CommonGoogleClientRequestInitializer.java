package com.google.api.client.googleapis.services;

import java.io.IOException;

public class CommonGoogleClientRequestInitializer implements GoogleClientRequestInitializer {
   private static final String REQUEST_REASON_HEADER_NAME = "X-Goog-Request-Reason";
   private static final String USER_PROJECT_HEADER_NAME = "X-Goog-User-Project";
   private final String key;
   private final String requestReason;
   private final String userAgent;
   private final String userIp;
   private final String userProject;

   @Deprecated
   public CommonGoogleClientRequestInitializer() {
      this(newBuilder());
   }

   protected CommonGoogleClientRequestInitializer(CommonGoogleClientRequestInitializer.Builder var1) {
      this.key = var1.getKey();
      this.userIp = var1.getUserIp();
      this.userAgent = var1.getUserAgent();
      this.requestReason = var1.getRequestReason();
      this.userProject = var1.getUserProject();
   }

   @Deprecated
   public CommonGoogleClientRequestInitializer(String var1) {
      this(var1, (String)null);
   }

   @Deprecated
   public CommonGoogleClientRequestInitializer(String var1, String var2) {
      this(newBuilder().setKey(var1).setUserIp(var2));
   }

   public static CommonGoogleClientRequestInitializer.Builder newBuilder() {
      return new CommonGoogleClientRequestInitializer.Builder();
   }

   public final String getKey() {
      return this.key;
   }

   public final String getRequestReason() {
      return this.requestReason;
   }

   public final String getUserAgent() {
      return this.userAgent;
   }

   public final String getUserIp() {
      return this.userIp;
   }

   public final String getUserProject() {
      return this.userProject;
   }

   public void initialize(AbstractGoogleClientRequest<?> var1) throws IOException {
      String var2 = this.key;
      if (var2 != null) {
         var1.put("key", var2);
      }

      var2 = this.userIp;
      if (var2 != null) {
         var1.put("userIp", var2);
      }

      if (this.userAgent != null) {
         var1.getRequestHeaders().setUserAgent(this.userAgent);
      }

      if (this.requestReason != null) {
         var1.getRequestHeaders().set("X-Goog-Request-Reason", this.requestReason);
      }

      if (this.userProject != null) {
         var1.getRequestHeaders().set("X-Goog-User-Project", this.userProject);
      }

   }

   public static class Builder {
      private String key;
      private String requestReason;
      private String userAgent;
      private String userIp;
      private String userProject;

      protected Builder() {
      }

      public CommonGoogleClientRequestInitializer build() {
         return new CommonGoogleClientRequestInitializer(this);
      }

      public String getKey() {
         return this.key;
      }

      public String getRequestReason() {
         return this.requestReason;
      }

      public String getUserAgent() {
         return this.userAgent;
      }

      public String getUserIp() {
         return this.userIp;
      }

      public String getUserProject() {
         return this.userProject;
      }

      protected CommonGoogleClientRequestInitializer.Builder self() {
         return this;
      }

      public CommonGoogleClientRequestInitializer.Builder setKey(String var1) {
         this.key = var1;
         return this.self();
      }

      public CommonGoogleClientRequestInitializer.Builder setRequestReason(String var1) {
         this.requestReason = var1;
         return this.self();
      }

      public CommonGoogleClientRequestInitializer.Builder setUserAgent(String var1) {
         this.userAgent = var1;
         return this.self();
      }

      public CommonGoogleClientRequestInitializer.Builder setUserIp(String var1) {
         this.userIp = var1;
         return this.self();
      }

      public CommonGoogleClientRequestInitializer.Builder setUserProject(String var1) {
         this.userProject = var1;
         return this.self();
      }
   }
}
