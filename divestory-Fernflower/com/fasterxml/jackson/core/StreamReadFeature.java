package com.fasterxml.jackson.core;

public enum StreamReadFeature {
   AUTO_CLOSE_SOURCE(JsonParser.Feature.AUTO_CLOSE_SOURCE),
   IGNORE_UNDEFINED(JsonParser.Feature.IGNORE_UNDEFINED),
   INCLUDE_SOURCE_IN_LOCATION,
   STRICT_DUPLICATE_DETECTION(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);

   private final boolean _defaultState;
   private final JsonParser.Feature _mappedFeature;
   private final int _mask;

   static {
      StreamReadFeature var0 = new StreamReadFeature("INCLUDE_SOURCE_IN_LOCATION", 3, JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);
      INCLUDE_SOURCE_IN_LOCATION = var0;
   }

   private StreamReadFeature(JsonParser.Feature var3) {
      this._mappedFeature = var3;
      this._mask = var3.getMask();
      this._defaultState = var3.enabledByDefault();
   }

   public static int collectDefaults() {
      StreamReadFeature[] var0 = values();
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      int var5;
      for(var3 = 0; var2 < var1; var3 = var5) {
         StreamReadFeature var4 = var0[var2];
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
