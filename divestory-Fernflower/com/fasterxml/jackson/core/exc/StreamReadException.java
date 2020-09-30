package com.fasterxml.jackson.core.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;

public abstract class StreamReadException extends JsonProcessingException {
   static final long serialVersionUID = 1L;
   protected transient JsonParser _processor;
   protected RequestPayload _requestPayload;

   public StreamReadException(JsonParser var1, String var2) {
      JsonLocation var3;
      if (var1 == null) {
         var3 = null;
      } else {
         var3 = var1.getCurrentLocation();
      }

      super(var2, var3);
      this._processor = var1;
   }

   public StreamReadException(JsonParser var1, String var2, JsonLocation var3) {
      super(var2, var3, (Throwable)null);
      this._processor = var1;
   }

   public StreamReadException(JsonParser var1, String var2, Throwable var3) {
      JsonLocation var4;
      if (var1 == null) {
         var4 = null;
      } else {
         var4 = var1.getCurrentLocation();
      }

      super(var2, var4, var3);
      this._processor = var1;
   }

   protected StreamReadException(String var1, JsonLocation var2, Throwable var3) {
      super(var1);
      if (var3 != null) {
         this.initCause(var3);
      }

      this._location = var2;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      String var2 = var1;
      if (this._requestPayload != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         var3.append("\nRequest payload : ");
         var3.append(this._requestPayload.toString());
         var2 = var3.toString();
      }

      return var2;
   }

   public JsonParser getProcessor() {
      return this._processor;
   }

   public RequestPayload getRequestPayload() {
      return this._requestPayload;
   }

   public String getRequestPayloadAsString() {
      RequestPayload var1 = this._requestPayload;
      String var2;
      if (var1 != null) {
         var2 = var1.toString();
      } else {
         var2 = null;
      }

      return var2;
   }

   public abstract StreamReadException withParser(JsonParser var1);

   public abstract StreamReadException withRequestPayload(RequestPayload var1);
}
