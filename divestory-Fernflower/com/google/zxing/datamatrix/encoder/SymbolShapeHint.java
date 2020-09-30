package com.google.zxing.datamatrix.encoder;

public enum SymbolShapeHint {
   FORCE_NONE,
   FORCE_RECTANGLE,
   FORCE_SQUARE;

   static {
      SymbolShapeHint var0 = new SymbolShapeHint("FORCE_RECTANGLE", 2);
      FORCE_RECTANGLE = var0;
   }
}
