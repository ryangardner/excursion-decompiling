package com.google.api.client.json;

public enum JsonToken {
   END_ARRAY,
   END_OBJECT,
   FIELD_NAME,
   NOT_AVAILABLE,
   START_ARRAY,
   START_OBJECT,
   VALUE_FALSE,
   VALUE_NULL,
   VALUE_NUMBER_FLOAT,
   VALUE_NUMBER_INT,
   VALUE_STRING,
   VALUE_TRUE;

   static {
      JsonToken var0 = new JsonToken("NOT_AVAILABLE", 11);
      NOT_AVAILABLE = var0;
   }
}
