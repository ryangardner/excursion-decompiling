package com.google.zxing;

import java.util.List;

public enum DecodeHintType {
   ALLOWED_EAN_EXTENSIONS,
   ALLOWED_LENGTHS(int[].class),
   ASSUME_CODE_39_CHECK_DIGIT(Void.class),
   ASSUME_GS1(Void.class),
   CHARACTER_SET(String.class),
   NEED_RESULT_POINT_CALLBACK(ResultPointCallback.class),
   OTHER(Object.class),
   POSSIBLE_FORMATS(List.class),
   PURE_BARCODE(Void.class),
   RETURN_CODABAR_START_END(Void.class),
   TRY_HARDER(Void.class);

   private final Class<?> valueType;

   static {
      DecodeHintType var0 = new DecodeHintType("ALLOWED_EAN_EXTENSIONS", 10, int[].class);
      ALLOWED_EAN_EXTENSIONS = var0;
   }

   private DecodeHintType(Class<?> var3) {
      this.valueType = var3;
   }

   public Class<?> getValueType() {
      return this.valueType;
   }
}
