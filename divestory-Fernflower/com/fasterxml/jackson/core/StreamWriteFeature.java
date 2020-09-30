package com.fasterxml.jackson.core;

public enum StreamWriteFeature {
   AUTO_CLOSE_CONTENT(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT),
   AUTO_CLOSE_TARGET(JsonGenerator.Feature.AUTO_CLOSE_TARGET),
   FLUSH_PASSED_TO_STREAM(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM),
   IGNORE_UNKNOWN,
   STRICT_DUPLICATE_DETECTION(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION),
   WRITE_BIGDECIMAL_AS_PLAIN(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

   private final boolean _defaultState;
   private final JsonGenerator.Feature _mappedFeature;
   private final int _mask;

   static {
      StreamWriteFeature var0 = new StreamWriteFeature("IGNORE_UNKNOWN", 5, JsonGenerator.Feature.IGNORE_UNKNOWN);
      IGNORE_UNKNOWN = var0;
   }

   private StreamWriteFeature(JsonGenerator.Feature var3) {
      this._mappedFeature = var3;
      this._mask = var3.getMask();
      this._defaultState = var3.enabledByDefault();
   }

   public static int collectDefaults() {
      StreamWriteFeature[] var0 = values();
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      int var5;
      for(var3 = 0; var2 < var1; var3 = var5) {
         StreamWriteFeature var4 = var0[var2];
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
