package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonGenerator;

public enum JsonWriteFeature implements FormatFeature {
   ESCAPE_NON_ASCII,
   QUOTE_FIELD_NAMES(true, JsonGenerator.Feature.QUOTE_FIELD_NAMES),
   WRITE_NAN_AS_STRINGS(true, JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS),
   WRITE_NUMBERS_AS_STRINGS(false, JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);

   private final boolean _defaultState;
   private final JsonGenerator.Feature _mappedFeature;
   private final int _mask;

   static {
      JsonWriteFeature var0 = new JsonWriteFeature("ESCAPE_NON_ASCII", 3, false, JsonGenerator.Feature.ESCAPE_NON_ASCII);
      ESCAPE_NON_ASCII = var0;
   }

   private JsonWriteFeature(boolean var3, JsonGenerator.Feature var4) {
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
      this._mappedFeature = var4;
   }

   public static int collectDefaults() {
      JsonWriteFeature[] var0 = values();
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      int var5;
      for(var3 = 0; var2 < var1; var3 = var5) {
         JsonWriteFeature var4 = var0[var2];
         var5 = var3;
         if (var4.enabledByDefault()) {
            var5 = var3 | var4.getMask();
         }

         ++var2;
      }

      return var3;
   }

   public boolean enabledByDefault() {
      return this._defaultState;
   }

   public boolean enabledIn(int var1) {
      boolean var2;
      if ((var1 & this._mask) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int getMask() {
      return this._mask;
   }

   public JsonGenerator.Feature mappedFeature() {
      return this._mappedFeature;
   }
}
