package com.google.api.client.googleapis.json;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleJsonError extends GenericJson {
   @Key
   private int code;
   @Key
   private List<GoogleJsonError.ErrorInfo> errors;
   @Key
   private String message;

   static {
      Data.nullOf(GoogleJsonError.ErrorInfo.class);
   }

   public static GoogleJsonError parse(JsonFactory var0, HttpResponse var1) throws IOException {
      return (GoogleJsonError)(new JsonObjectParser.Builder(var0)).setWrapperKeys(Collections.singleton("error")).build().parseAndClose(var1.getContent(), var1.getContentCharset(), GoogleJsonError.class);
   }

   public GoogleJsonError clone() {
      return (GoogleJsonError)super.clone();
   }

   public final int getCode() {
      return this.code;
   }

   public final List<GoogleJsonError.ErrorInfo> getErrors() {
      return this.errors;
   }

   public final String getMessage() {
      return this.message;
   }

   public GoogleJsonError set(String var1, Object var2) {
      return (GoogleJsonError)super.set(var1, var2);
   }

   public final void setCode(int var1) {
      this.code = var1;
   }

   public final void setErrors(List<GoogleJsonError.ErrorInfo> var1) {
      this.errors = var1;
   }

   public final void setMessage(String var1) {
      this.message = var1;
   }

   public static class ErrorInfo extends GenericJson {
      @Key
      private String domain;
      @Key
      private String location;
      @Key
      private String locationType;
      @Key
      private String message;
      @Key
      private String reason;

      public GoogleJsonError.ErrorInfo clone() {
         return (GoogleJsonError.ErrorInfo)super.clone();
      }

      public final String getDomain() {
         return this.domain;
      }

      public final String getLocation() {
         return this.location;
      }

      public final String getLocationType() {
         return this.locationType;
      }

      public final String getMessage() {
         return this.message;
      }

      public final String getReason() {
         return this.reason;
      }

      public GoogleJsonError.ErrorInfo set(String var1, Object var2) {
         return (GoogleJsonError.ErrorInfo)super.set(var1, var2);
      }

      public final void setDomain(String var1) {
         this.domain = var1;
      }

      public final void setLocation(String var1) {
         this.location = var1;
      }

      public final void setLocationType(String var1) {
         this.locationType = var1;
      }

      public final void setMessage(String var1) {
         this.message = var1;
      }

      public final void setReason(String var1) {
         this.reason = var1;
      }
   }
}
