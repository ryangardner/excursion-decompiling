package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

public abstract class TSFBuilder<F extends JsonFactory, B extends TSFBuilder<F, B>> {
   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = JsonFactory.Feature.collectDefaults();
   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
   protected int _factoryFeatures;
   protected InputDecorator _inputDecorator;
   protected OutputDecorator _outputDecorator;
   protected int _streamReadFeatures;
   protected int _streamWriteFeatures;

   protected TSFBuilder() {
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._streamReadFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._streamWriteFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._inputDecorator = null;
      this._outputDecorator = null;
   }

   protected TSFBuilder(int var1, int var2, int var3) {
      this._factoryFeatures = var1;
      this._streamReadFeatures = var2;
      this._streamWriteFeatures = var3;
   }

   protected TSFBuilder(JsonFactory var1) {
      this(var1._factoryFeatures, var1._parserFeatures, var1._generatorFeatures);
   }

   private B _failNonJSON(Object var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Feature ");
      var2.append(var1.getClass().getName());
      var2.append("#");
      var2.append(var1.toString());
      var2.append(" not supported for non-JSON backend");
      throw new IllegalArgumentException(var2.toString());
   }

   protected void _legacyDisable(JsonGenerator.Feature var1) {
      int var2 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.getMask() & var2;
   }

   protected void _legacyDisable(JsonParser.Feature var1) {
      int var2 = this._streamReadFeatures;
      this._streamReadFeatures = var1.getMask() & var2;
   }

   protected void _legacyEnable(JsonGenerator.Feature var1) {
      int var2 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.getMask() | var2;
   }

   protected void _legacyEnable(JsonParser.Feature var1) {
      int var2 = this._streamReadFeatures;
      this._streamReadFeatures = var1.getMask() | var2;
   }

   protected final B _this() {
      return this;
   }

   public abstract F build();

   public B configure(JsonFactory.Feature var1, boolean var2) {
      TSFBuilder var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public B configure(StreamReadFeature var1, boolean var2) {
      TSFBuilder var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public B configure(StreamWriteFeature var1, boolean var2) {
      TSFBuilder var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public B configure(JsonReadFeature var1, boolean var2) {
      return this._failNonJSON(var1);
   }

   public B configure(JsonWriteFeature var1, boolean var2) {
      return this._failNonJSON(var1);
   }

   public B disable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = var1.getMask() & var2;
      return this._this();
   }

   public B disable(StreamReadFeature var1) {
      int var2 = this._streamReadFeatures;
      this._streamReadFeatures = var1.mappedFeature().getMask() & var2;
      return this._this();
   }

   public B disable(StreamReadFeature var1, StreamReadFeature... var2) {
      int var3 = this._streamReadFeatures;
      this._streamReadFeatures = var1.mappedFeature().getMask() & var3;
      int var4 = var2.length;

      for(var3 = 0; var3 < var4; ++var3) {
         var1 = var2[var3];
         int var5 = this._streamReadFeatures;
         this._streamReadFeatures = var1.mappedFeature().getMask() & var5;
      }

      return this._this();
   }

   public B disable(StreamWriteFeature var1) {
      int var2 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.mappedFeature().getMask() & var2;
      return this._this();
   }

   public B disable(StreamWriteFeature var1, StreamWriteFeature... var2) {
      int var3 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.mappedFeature().getMask() & var3;
      int var4 = var2.length;

      for(var3 = 0; var3 < var4; ++var3) {
         var1 = var2[var3];
         int var5 = this._streamWriteFeatures;
         this._streamWriteFeatures = var1.mappedFeature().getMask() & var5;
      }

      return this._this();
   }

   public B disable(JsonReadFeature var1) {
      return this._failNonJSON(var1);
   }

   public B disable(JsonReadFeature var1, JsonReadFeature... var2) {
      return this._failNonJSON(var1);
   }

   public B disable(JsonWriteFeature var1) {
      return this._failNonJSON(var1);
   }

   public B disable(JsonWriteFeature var1, JsonWriteFeature... var2) {
      return this._failNonJSON(var1);
   }

   public B enable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = var1.getMask() | var2;
      return this._this();
   }

   public B enable(StreamReadFeature var1) {
      int var2 = this._streamReadFeatures;
      this._streamReadFeatures = var1.mappedFeature().getMask() | var2;
      return this._this();
   }

   public B enable(StreamReadFeature var1, StreamReadFeature... var2) {
      int var3 = this._streamReadFeatures;
      this._streamReadFeatures = var1.mappedFeature().getMask() | var3;
      int var4 = var2.length;

      for(var3 = 0; var3 < var4; ++var3) {
         var1 = var2[var3];
         int var5 = this._streamReadFeatures;
         this._streamReadFeatures = var1.mappedFeature().getMask() | var5;
      }

      return this._this();
   }

   public B enable(StreamWriteFeature var1) {
      int var2 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.mappedFeature().getMask() | var2;
      return this._this();
   }

   public B enable(StreamWriteFeature var1, StreamWriteFeature... var2) {
      int var3 = this._streamWriteFeatures;
      this._streamWriteFeatures = var1.mappedFeature().getMask() | var3;
      int var4 = var2.length;

      for(var3 = 0; var3 < var4; ++var3) {
         var1 = var2[var3];
         int var5 = this._streamWriteFeatures;
         this._streamWriteFeatures = var1.mappedFeature().getMask() | var5;
      }

      return this._this();
   }

   public B enable(JsonReadFeature var1) {
      return this._failNonJSON(var1);
   }

   public B enable(JsonReadFeature var1, JsonReadFeature... var2) {
      return this._failNonJSON(var1);
   }

   public B enable(JsonWriteFeature var1) {
      return this._failNonJSON(var1);
   }

   public B enable(JsonWriteFeature var1, JsonWriteFeature... var2) {
      return this._failNonJSON(var1);
   }

   public int factoryFeaturesMask() {
      return this._factoryFeatures;
   }

   public B inputDecorator(InputDecorator var1) {
      this._inputDecorator = var1;
      return this._this();
   }

   public InputDecorator inputDecorator() {
      return this._inputDecorator;
   }

   public B outputDecorator(OutputDecorator var1) {
      this._outputDecorator = var1;
      return this._this();
   }

   public OutputDecorator outputDecorator() {
      return this._outputDecorator;
   }

   public int streamReadFeatures() {
      return this._streamReadFeatures;
   }

   public int streamWriteFeatures() {
      return this._streamWriteFeatures;
   }
}
