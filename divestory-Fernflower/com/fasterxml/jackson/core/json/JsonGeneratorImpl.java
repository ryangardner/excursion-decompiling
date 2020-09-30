package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class JsonGeneratorImpl extends GeneratorBase {
   protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
   protected boolean _cfgUnqNames;
   protected CharacterEscapes _characterEscapes;
   protected final IOContext _ioContext;
   protected int _maximumNonEscapedChar;
   protected int[] _outputEscapes;
   protected SerializableString _rootValueSeparator;

   public JsonGeneratorImpl(IOContext var1, int var2, ObjectCodec var3) {
      super(var2, var3);
      this._outputEscapes = sOutputEscapes;
      this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
      this._ioContext = var1;
      if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(var2)) {
         this._maximumNonEscapedChar = 127;
      }

      this._cfgUnqNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(var2) ^ true;
   }

   protected void _checkStdFeatureChanges(int var1, int var2) {
      super._checkStdFeatureChanges(var1, var2);
      this._cfgUnqNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(var1) ^ true;
   }

   protected void _reportCantWriteValueExpectName(String var1) throws IOException {
      this._reportError(String.format("Can not %s, expecting field name (context: %s)", var1, this._writeContext.typeDesc()));
   }

   protected void _verifyPrettyValueWrite(String var1, int var2) throws IOException {
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  if (var2 != 5) {
                     this._throwInternal();
                  } else {
                     this._reportCantWriteValueExpectName(var1);
                  }
               } else {
                  this._cfgPrettyPrinter.writeRootValueSeparator(this);
               }
            } else {
               this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
            }
         } else {
            this._cfgPrettyPrinter.writeArrayValueSeparator(this);
         }
      } else if (this._writeContext.inArray()) {
         this._cfgPrettyPrinter.beforeArrayValues(this);
      } else if (this._writeContext.inObject()) {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

   }

   public JsonGenerator disable(JsonGenerator.Feature var1) {
      super.disable(var1);
      if (var1 == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
         this._cfgUnqNames = true;
      }

      return this;
   }

   public JsonGenerator enable(JsonGenerator.Feature var1) {
      super.enable(var1);
      if (var1 == JsonGenerator.Feature.QUOTE_FIELD_NAMES) {
         this._cfgUnqNames = false;
      }

      return this;
   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public int getHighestEscapedChar() {
      return this._maximumNonEscapedChar;
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      if (var1 == null) {
         this._outputEscapes = sOutputEscapes;
      } else {
         this._outputEscapes = var1.getEscapeCodesForAscii();
      }

      return this;
   }

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      int var2 = var1;
      if (var1 < 0) {
         var2 = 0;
      }

      this._maximumNonEscapedChar = var2;
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      this._rootValueSeparator = var1;
      return this;
   }

   public Version version() {
      return VersionUtil.versionFor(this.getClass());
   }

   public final void writeStringField(String var1, String var2) throws IOException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }
}
