package com.fasterxml.jackson.core;

public enum JsonEncoding {
   UTF16_BE("UTF-16BE", true, 16),
   UTF16_LE("UTF-16LE", false, 16),
   UTF32_BE("UTF-32BE", true, 32),
   UTF32_LE,
   UTF8("UTF-8", false, 8);

   private final boolean _bigEndian;
   private final int _bits;
   private final String _javaName;

   static {
      JsonEncoding var0 = new JsonEncoding("UTF32_LE", 4, "UTF-32LE", false, 32);
      UTF32_LE = var0;
   }

   private JsonEncoding(String var3, boolean var4, int var5) {
      this._javaName = var3;
      this._bigEndian = var4;
      this._bits = var5;
   }

   public int bits() {
      return this._bits;
   }

   public String getJavaName() {
      return this._javaName;
   }

   public boolean isBigEndian() {
      return this._bigEndian;
   }
}
