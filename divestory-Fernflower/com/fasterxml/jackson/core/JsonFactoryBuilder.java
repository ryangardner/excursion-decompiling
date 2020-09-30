package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

public class JsonFactoryBuilder extends TSFBuilder<JsonFactory, JsonFactoryBuilder> {
   protected CharacterEscapes _characterEscapes;
   protected int _maximumNonEscapedChar;
   protected char _quoteChar = (char)34;
   protected SerializableString _rootValueSeparator;

   public JsonFactoryBuilder() {
      this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
      this._maximumNonEscapedChar = 0;
   }

   public JsonFactoryBuilder(JsonFactory var1) {
      super(var1);
      this._characterEscapes = var1.getCharacterEscapes();
      this._rootValueSeparator = var1._rootValueSeparator;
      this._maximumNonEscapedChar = var1._maximumNonEscapedChar;
   }

   public JsonFactory build() {
      return new JsonFactory(this);
   }

   public JsonFactoryBuilder characterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      return this;
   }

   public CharacterEscapes characterEscapes() {
      return this._characterEscapes;
   }

   public JsonFactoryBuilder configure(JsonReadFeature var1, boolean var2) {
      JsonFactoryBuilder var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public JsonFactoryBuilder configure(JsonWriteFeature var1, boolean var2) {
      JsonFactoryBuilder var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public JsonFactoryBuilder disable(JsonReadFeature var1) {
      this._legacyDisable(var1.mappedFeature());
      return this;
   }

   public JsonFactoryBuilder disable(JsonReadFeature var1, JsonReadFeature... var2) {
      this._legacyDisable(var1.mappedFeature());
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this._legacyEnable(var2[var4].mappedFeature());
      }

      return this;
   }

   public JsonFactoryBuilder disable(JsonWriteFeature var1) {
      this._legacyDisable(var1.mappedFeature());
      return this;
   }

   public JsonFactoryBuilder disable(JsonWriteFeature var1, JsonWriteFeature... var2) {
      this._legacyDisable(var1.mappedFeature());
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this._legacyDisable(var2[var4].mappedFeature());
      }

      return this;
   }

   public JsonFactoryBuilder enable(JsonReadFeature var1) {
      this._legacyEnable(var1.mappedFeature());
      return this;
   }

   public JsonFactoryBuilder enable(JsonReadFeature var1, JsonReadFeature... var2) {
      this._legacyEnable(var1.mappedFeature());
      this.enable(var1);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this._legacyEnable(var2[var4].mappedFeature());
      }

      return this;
   }

   public JsonFactoryBuilder enable(JsonWriteFeature var1) {
      JsonGenerator.Feature var2 = var1.mappedFeature();
      if (var2 != null) {
         this._legacyEnable(var2);
      }

      return this;
   }

   public JsonFactoryBuilder enable(JsonWriteFeature var1, JsonWriteFeature... var2) {
      this._legacyEnable(var1.mappedFeature());
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this._legacyEnable(var2[var4].mappedFeature());
      }

      return this;
   }

   public int highestNonEscapedChar() {
      return this._maximumNonEscapedChar;
   }

   public JsonFactoryBuilder highestNonEscapedChar(int var1) {
      if (var1 <= 0) {
         var1 = 0;
      } else {
         var1 = Math.max(127, var1);
      }

      this._maximumNonEscapedChar = var1;
      return this;
   }

   public char quoteChar() {
      return this._quoteChar;
   }

   public JsonFactoryBuilder quoteChar(char var1) {
      if (var1 <= 127) {
         this._quoteChar = (char)var1;
         return this;
      } else {
         throw new IllegalArgumentException("Can only use Unicode characters up to 0x7F as quote characters");
      }
   }

   public JsonFactoryBuilder rootValueSeparator(SerializableString var1) {
      this._rootValueSeparator = var1;
      return this;
   }

   public JsonFactoryBuilder rootValueSeparator(String var1) {
      SerializedString var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      this._rootValueSeparator = var2;
      return this;
   }

   public SerializableString rootValueSeparator() {
      return this._rootValueSeparator;
   }
}
