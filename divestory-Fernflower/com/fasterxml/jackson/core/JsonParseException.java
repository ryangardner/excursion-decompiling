package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.util.RequestPayload;

public class JsonParseException extends StreamReadException {
   private static final long serialVersionUID = 2L;

   public JsonParseException(JsonParser var1, String var2) {
      super(var1, var2);
   }

   public JsonParseException(JsonParser var1, String var2, JsonLocation var3) {
      super(var1, var2, var3);
   }

   public JsonParseException(JsonParser var1, String var2, JsonLocation var3, Throwable var4) {
      super(var2, var3, var4);
   }

   public JsonParseException(JsonParser var1, String var2, Throwable var3) {
      super(var1, var2, var3);
   }

   @Deprecated
   public JsonParseException(String var1, JsonLocation var2) {
      super((String)var1, (JsonLocation)var2, (Throwable)null);
   }

   @Deprecated
   public JsonParseException(String var1, JsonLocation var2, Throwable var3) {
      super(var1, var2, var3);
   }

   public String getMessage() {
      return super.getMessage();
   }

   public JsonParser getProcessor() {
      return super.getProcessor();
   }

   public RequestPayload getRequestPayload() {
      return super.getRequestPayload();
   }

   public String getRequestPayloadAsString() {
      return super.getRequestPayloadAsString();
   }

   public JsonParseException withParser(JsonParser var1) {
      this._processor = var1;
      return this;
   }

   public JsonParseException withRequestPayload(RequestPayload var1) {
      this._requestPayload = var1;
      return this;
   }
}
