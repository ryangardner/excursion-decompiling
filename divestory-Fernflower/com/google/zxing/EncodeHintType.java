package com.google.zxing;

public enum EncodeHintType {
   AZTEC_LAYERS,
   CHARACTER_SET,
   DATA_MATRIX_SHAPE,
   ERROR_CORRECTION,
   MARGIN,
   @Deprecated
   MAX_SIZE,
   @Deprecated
   MIN_SIZE,
   PDF417_COMPACT,
   PDF417_COMPACTION,
   PDF417_DIMENSIONS;

   static {
      EncodeHintType var0 = new EncodeHintType("AZTEC_LAYERS", 9);
      AZTEC_LAYERS = var0;
   }
}
