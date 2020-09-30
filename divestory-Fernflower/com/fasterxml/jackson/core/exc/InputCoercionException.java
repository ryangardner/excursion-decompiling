package com.fasterxml.jackson.core.exc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.RequestPayload;

public class InputCoercionException extends StreamReadException {
   private static final long serialVersionUID = 1L;
   protected final JsonToken _inputType;
   protected final Class<?> _targetType;

   public InputCoercionException(JsonParser var1, String var2, JsonToken var3, Class<?> var4) {
      super(var1, var2);
      this._inputType = var3;
      this._targetType = var4;
   }

   public JsonToken getInputType() {
      return this._inputType;
   }

   public Class<?> getTargetType() {
      return this._targetType;
   }

   public InputCoercionException withParser(JsonParser var1) {
      this._processor = var1;
      return this;
   }

   public InputCoercionException withRequestPayload(RequestPayload var1) {
      this._requestPayload = var1;
      return this;
   }
}
