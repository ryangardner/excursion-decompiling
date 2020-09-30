package com.fasterxml.jackson.core;

public class JsonGenerationException extends JsonProcessingException {
   private static final long serialVersionUID = 123L;
   protected transient JsonGenerator _processor;

   @Deprecated
   public JsonGenerationException(String var1) {
      super(var1, (JsonLocation)null);
   }

   public JsonGenerationException(String var1, JsonGenerator var2) {
      super(var1, (JsonLocation)null);
      this._processor = var2;
   }

   @Deprecated
   public JsonGenerationException(String var1, Throwable var2) {
      super(var1, (JsonLocation)null, var2);
   }

   public JsonGenerationException(String var1, Throwable var2, JsonGenerator var3) {
      super(var1, (JsonLocation)null, var2);
      this._processor = var3;
   }

   @Deprecated
   public JsonGenerationException(Throwable var1) {
      super(var1);
   }

   public JsonGenerationException(Throwable var1, JsonGenerator var2) {
      super(var1);
      this._processor = var2;
   }

   public JsonGenerator getProcessor() {
      return this._processor;
   }

   public JsonGenerationException withGenerator(JsonGenerator var1) {
      this._processor = var1;
      return this;
   }
}
