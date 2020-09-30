package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonParser;

public enum JsonReadFeature implements FormatFeature {
   ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false, JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER),
   ALLOW_JAVA_COMMENTS(false, JsonParser.Feature.ALLOW_COMMENTS),
   ALLOW_LEADING_ZEROS_FOR_NUMBERS(false, JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS),
   ALLOW_MISSING_VALUES(false, JsonParser.Feature.ALLOW_MISSING_VALUES),
   ALLOW_NON_NUMERIC_NUMBERS(false, JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS),
   ALLOW_SINGLE_QUOTES(false, JsonParser.Feature.ALLOW_SINGLE_QUOTES),
   ALLOW_TRAILING_COMMA,
   ALLOW_UNESCAPED_CONTROL_CHARS(false, JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS),
   ALLOW_UNQUOTED_FIELD_NAMES(false, JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES),
   ALLOW_YAML_COMMENTS(false, JsonParser.Feature.ALLOW_YAML_COMMENTS);

   private final boolean _defaultState;
   private final JsonParser.Feature _mappedFeature;
   private final int _mask;

   static {
      JsonReadFeature var0 = new JsonReadFeature("ALLOW_TRAILING_COMMA", 9, false, JsonParser.Feature.ALLOW_TRAILING_COMMA);
      ALLOW_TRAILING_COMMA = var0;
   }

   private JsonReadFeature(boolean var3, JsonParser.Feature var4) {
      this._defaultState = var3;
      this._mask = 1 << this.ordinal();
      this._mappedFeature = var4;
   }

   public static int collectDefaults() {
      JsonReadFeature[] var0 = values();
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      int var5;
      for(var3 = 0; var2 < var1; var3 = var5) {
         JsonReadFeature var4 = var0[var2];
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

   public JsonParser.Feature mappedFeature() {
      return this._mappedFeature;
   }
}
