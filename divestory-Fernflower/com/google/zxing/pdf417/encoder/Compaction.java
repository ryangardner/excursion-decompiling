package com.google.zxing.pdf417.encoder;

public enum Compaction {
   AUTO,
   BYTE,
   NUMERIC,
   TEXT;

   static {
      Compaction var0 = new Compaction("NUMERIC", 3);
      NUMERIC = var0;
   }
}
