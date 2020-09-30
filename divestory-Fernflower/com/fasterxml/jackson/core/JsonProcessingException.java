package com.fasterxml.jackson.core;

import java.io.IOException;

public class JsonProcessingException extends IOException {
   static final long serialVersionUID = 123L;
   protected JsonLocation _location;

   protected JsonProcessingException(String var1) {
      super(var1);
   }

   protected JsonProcessingException(String var1, JsonLocation var2) {
      this(var1, var2, (Throwable)null);
   }

   protected JsonProcessingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1);
      if (var3 != null) {
         this.initCause(var3);
      }

      this._location = var2;
   }

   protected JsonProcessingException(String var1, Throwable var2) {
      this(var1, (JsonLocation)null, var2);
   }

   protected JsonProcessingException(Throwable var1) {
      this((String)null, (JsonLocation)null, var1);
   }

   public void clearLocation() {
      this._location = null;
   }

   public JsonLocation getLocation() {
      return this._location;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      String var2 = var1;
      if (var1 == null) {
         var2 = "N/A";
      }

      JsonLocation var3 = this.getLocation();
      String var4 = this.getMessageSuffix();
      if (var3 == null) {
         var1 = var2;
         if (var4 == null) {
            return var1;
         }
      }

      StringBuilder var5 = new StringBuilder(100);
      var5.append(var2);
      if (var4 != null) {
         var5.append(var4);
      }

      if (var3 != null) {
         var5.append('\n');
         var5.append(" at ");
         var5.append(var3.toString());
      }

      var1 = var5.toString();
      return var1;
   }

   protected String getMessageSuffix() {
      return null;
   }

   public String getOriginalMessage() {
      return super.getMessage();
   }

   public Object getProcessor() {
      return null;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getName());
      var1.append(": ");
      var1.append(this.getMessage());
      return var1.toString();
   }
}
