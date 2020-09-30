package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.CharArrayReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;

public class JsonFactory extends TokenStreamFactory implements Versioned, Serializable {
   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = JsonFactory.Feature.collectDefaults();
   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
   public static final char DEFAULT_QUOTE_CHAR = '"';
   public static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
   public static final String FORMAT_NAME_JSON = "JSON";
   private static final long serialVersionUID = 2L;
   protected final transient ByteQuadsCanonicalizer _byteSymbolCanonicalizer;
   protected CharacterEscapes _characterEscapes;
   protected int _factoryFeatures;
   protected int _generatorFeatures;
   protected InputDecorator _inputDecorator;
   protected int _maximumNonEscapedChar;
   protected ObjectCodec _objectCodec;
   protected OutputDecorator _outputDecorator;
   protected int _parserFeatures;
   protected final char _quoteChar;
   protected final transient CharsToNameCanonicalizer _rootCharSymbols;
   protected SerializableString _rootValueSeparator;

   static {
      DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
   }

   public JsonFactory() {
      this((ObjectCodec)null);
   }

   protected JsonFactory(JsonFactory var1, ObjectCodec var2) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = var2;
      this._factoryFeatures = var1._factoryFeatures;
      this._parserFeatures = var1._parserFeatures;
      this._generatorFeatures = var1._generatorFeatures;
      this._inputDecorator = var1._inputDecorator;
      this._outputDecorator = var1._outputDecorator;
      this._characterEscapes = var1._characterEscapes;
      this._rootValueSeparator = var1._rootValueSeparator;
      this._maximumNonEscapedChar = var1._maximumNonEscapedChar;
      this._quoteChar = (char)var1._quoteChar;
   }

   public JsonFactory(JsonFactoryBuilder var1) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = null;
      this._factoryFeatures = var1._factoryFeatures;
      this._parserFeatures = var1._streamReadFeatures;
      this._generatorFeatures = var1._streamWriteFeatures;
      this._inputDecorator = var1._inputDecorator;
      this._outputDecorator = var1._outputDecorator;
      this._characterEscapes = var1._characterEscapes;
      this._rootValueSeparator = var1._rootValueSeparator;
      this._maximumNonEscapedChar = var1._maximumNonEscapedChar;
      this._quoteChar = (char)var1._quoteChar;
   }

   public JsonFactory(ObjectCodec var1) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = var1;
      this._quoteChar = (char)34;
   }

   protected JsonFactory(TSFBuilder<?, ?> var1, boolean var2) {
      this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
      this._byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
      this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
      this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
      this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
      this._objectCodec = null;
      this._factoryFeatures = var1._factoryFeatures;
      this._parserFeatures = var1._streamReadFeatures;
      this._generatorFeatures = var1._streamWriteFeatures;
      this._inputDecorator = var1._inputDecorator;
      this._outputDecorator = var1._outputDecorator;
      this._characterEscapes = null;
      this._rootValueSeparator = null;
      this._maximumNonEscapedChar = 0;
      this._quoteChar = (char)34;
   }

   private final boolean _isJSONFactory() {
      boolean var1;
      if (this.getFormatName() == "JSON") {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final void _requireJSONFactory(String var1) {
      if (!this._isJSONFactory()) {
         throw new UnsupportedOperationException(String.format(var1, this.getFormatName()));
      }
   }

   public static TSFBuilder<?, ?> builder() {
      return new JsonFactoryBuilder();
   }

   protected void _checkInvalidCopy(Class<?> var1) {
      if (this.getClass() != var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Failed copy(): ");
         var2.append(this.getClass().getName());
         var2.append(" (version: ");
         var2.append(this.version());
         var2.append(") does not override copy(); it has to");
         throw new IllegalStateException(var2.toString());
      }
   }

   protected IOContext _createContext(Object var1, boolean var2) {
      return new IOContext(this._getBufferRecycler(), var1, var2);
   }

   protected JsonGenerator _createGenerator(Writer var1, IOContext var2) throws IOException {
      WriterBasedJsonGenerator var4 = new WriterBasedJsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1, this._quoteChar);
      int var3 = this._maximumNonEscapedChar;
      if (var3 > 0) {
         var4.setHighestNonEscapedChar(var3);
      }

      CharacterEscapes var5 = this._characterEscapes;
      if (var5 != null) {
         var4.setCharacterEscapes(var5);
      }

      SerializableString var6 = this._rootValueSeparator;
      if (var6 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var4.setRootValueSeparator(var6);
      }

      return var4;
   }

   protected IOContext _createNonBlockingContext(Object var1) {
      return new IOContext(this._getBufferRecycler(), var1, false);
   }

   protected JsonParser _createParser(DataInput var1, IOContext var2) throws IOException {
      this._requireJSONFactory("InputData source not (yet?) supported for this format (%s)");
      int var3 = ByteSourceJsonBootstrapper.skipUTF8BOM(var1);
      ByteQuadsCanonicalizer var4 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
      return new UTF8DataInputJsonParser(var2, this._parserFeatures, var1, this._objectCodec, var4, var3);
   }

   protected JsonParser _createParser(InputStream var1, IOContext var2) throws IOException {
      return (new ByteSourceJsonBootstrapper(var2, var1)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
   }

   protected JsonParser _createParser(Reader var1, IOContext var2) throws IOException {
      return new ReaderBasedJsonParser(var2, this._parserFeatures, var1, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
   }

   protected JsonParser _createParser(byte[] var1, int var2, int var3, IOContext var4) throws IOException {
      return (new ByteSourceJsonBootstrapper(var4, var1, var2, var3)).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
   }

   protected JsonParser _createParser(char[] var1, int var2, int var3, IOContext var4, boolean var5) throws IOException {
      return new ReaderBasedJsonParser(var4, this._parserFeatures, (Reader)null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), var1, var2, var2 + var3, var5);
   }

   protected JsonGenerator _createUTF8Generator(OutputStream var1, IOContext var2) throws IOException {
      UTF8JsonGenerator var4 = new UTF8JsonGenerator(var2, this._generatorFeatures, this._objectCodec, var1, this._quoteChar);
      int var3 = this._maximumNonEscapedChar;
      if (var3 > 0) {
         var4.setHighestNonEscapedChar(var3);
      }

      CharacterEscapes var5 = this._characterEscapes;
      if (var5 != null) {
         var4.setCharacterEscapes(var5);
      }

      SerializableString var6 = this._rootValueSeparator;
      if (var6 != DEFAULT_ROOT_VALUE_SEPARATOR) {
         var4.setRootValueSeparator(var6);
      }

      return var4;
   }

   protected Writer _createWriter(OutputStream var1, JsonEncoding var2, IOContext var3) throws IOException {
      return (Writer)(var2 == JsonEncoding.UTF8 ? new UTF8Writer(var3, var1) : new OutputStreamWriter(var1, var2.getJavaName()));
   }

   protected final DataInput _decorate(DataInput var1, IOContext var2) throws IOException {
      InputDecorator var3 = this._inputDecorator;
      if (var3 != null) {
         DataInput var4 = var3.decorate(var2, var1);
         if (var4 != null) {
            return var4;
         }
      }

      return var1;
   }

   protected final InputStream _decorate(InputStream var1, IOContext var2) throws IOException {
      InputDecorator var3 = this._inputDecorator;
      if (var3 != null) {
         InputStream var4 = var3.decorate(var2, var1);
         if (var4 != null) {
            return var4;
         }
      }

      return var1;
   }

   protected final OutputStream _decorate(OutputStream var1, IOContext var2) throws IOException {
      OutputDecorator var3 = this._outputDecorator;
      if (var3 != null) {
         OutputStream var4 = var3.decorate(var2, var1);
         if (var4 != null) {
            return var4;
         }
      }

      return var1;
   }

   protected final Reader _decorate(Reader var1, IOContext var2) throws IOException {
      InputDecorator var3 = this._inputDecorator;
      if (var3 != null) {
         Reader var4 = var3.decorate(var2, var1);
         if (var4 != null) {
            return var4;
         }
      }

      return var1;
   }

   protected final Writer _decorate(Writer var1, IOContext var2) throws IOException {
      OutputDecorator var3 = this._outputDecorator;
      if (var3 != null) {
         Writer var4 = var3.decorate(var2, var1);
         if (var4 != null) {
            return var4;
         }
      }

      return var1;
   }

   public BufferRecycler _getBufferRecycler() {
      return JsonFactory.Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING.enabledIn(this._factoryFeatures) ? BufferRecyclers.getBufferRecycler() : new BufferRecycler();
   }

   public boolean canHandleBinaryNatively() {
      return false;
   }

   public boolean canParseAsync() {
      return this._isJSONFactory();
   }

   public boolean canUseCharArrays() {
      return true;
   }

   public boolean canUseSchema(FormatSchema var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         String var3 = this.getFormatName();
         boolean var4 = var2;
         if (var3 != null) {
            var4 = var2;
            if (var3.equals(var1.getSchemaType())) {
               var4 = true;
            }
         }

         return var4;
      }
   }

   @Deprecated
   public final JsonFactory configure(JsonFactory.Feature var1, boolean var2) {
      JsonFactory var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public final JsonFactory configure(JsonGenerator.Feature var1, boolean var2) {
      JsonFactory var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public final JsonFactory configure(JsonParser.Feature var1, boolean var2) {
      JsonFactory var3;
      if (var2) {
         var3 = this.enable(var1);
      } else {
         var3 = this.disable(var1);
      }

      return var3;
   }

   public JsonFactory copy() {
      this._checkInvalidCopy(JsonFactory.class);
      return new JsonFactory(this, (ObjectCodec)null);
   }

   public JsonGenerator createGenerator(DataOutput var1) throws IOException {
      return this.createGenerator(this._createDataOutputWrapper(var1), JsonEncoding.UTF8);
   }

   public JsonGenerator createGenerator(DataOutput var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(this._createDataOutputWrapper(var1), var2);
   }

   public JsonGenerator createGenerator(File var1, JsonEncoding var2) throws IOException {
      FileOutputStream var4 = new FileOutputStream(var1);
      IOContext var3 = this._createContext(var4, true);
      var3.setEncoding(var2);
      return var2 == JsonEncoding.UTF8 ? this._createUTF8Generator(this._decorate((OutputStream)var4, var3), var3) : this._createGenerator(this._decorate(this._createWriter(var4, var2, var3), var3), var3);
   }

   public JsonGenerator createGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   public JsonGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      IOContext var3 = this._createContext(var1, false);
      var3.setEncoding(var2);
      return var2 == JsonEncoding.UTF8 ? this._createUTF8Generator(this._decorate(var1, var3), var3) : this._createGenerator(this._decorate(this._createWriter(var1, var2, var3), var3), var3);
   }

   public JsonGenerator createGenerator(Writer var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createGenerator(this._decorate(var1, var2), var2);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1) throws IOException {
      return this.createGenerator(var1, JsonEncoding.UTF8);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(OutputStream var1, JsonEncoding var2) throws IOException {
      return this.createGenerator(var1, var2);
   }

   @Deprecated
   public JsonGenerator createJsonGenerator(Writer var1) throws IOException {
      return this.createGenerator(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(File var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(InputStream var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(Reader var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(String var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(URL var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(byte[] var1) throws IOException, JsonParseException {
      return this.createParser(var1);
   }

   @Deprecated
   public JsonParser createJsonParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      return this.createParser(var1, var2, var3);
   }

   public JsonParser createNonBlockingByteArrayParser() throws IOException {
      this._requireJSONFactory("Non-blocking source not (yet?) supported for this format (%s)");
      IOContext var1 = this._createNonBlockingContext((Object)null);
      ByteQuadsCanonicalizer var2 = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
      return new NonBlockingJsonParser(var1, this._parserFeatures, var2);
   }

   public JsonParser createParser(DataInput var1) throws IOException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createParser(File var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      return this._createParser(this._decorate((InputStream)(new FileInputStream(var1)), var2), var2);
   }

   public JsonParser createParser(InputStream var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createParser(Reader var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, false);
      return this._createParser(this._decorate(var1, var2), var2);
   }

   public JsonParser createParser(String var1) throws IOException, JsonParseException {
      int var2 = var1.length();
      if (this._inputDecorator == null && var2 <= 32768 && this.canUseCharArrays()) {
         IOContext var3 = this._createContext(var1, true);
         char[] var4 = var3.allocTokenBuffer(var2);
         var1.getChars(0, var2, var4, 0);
         return this._createParser(var4, 0, var2, var3, true);
      } else {
         return this.createParser((Reader)(new StringReader(var1)));
      }
   }

   public JsonParser createParser(URL var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      return this._createParser(this._decorate(this._optimizedStreamFromURL(var1), var2), var2);
   }

   public JsonParser createParser(byte[] var1) throws IOException, JsonParseException {
      IOContext var2 = this._createContext(var1, true);
      InputDecorator var3 = this._inputDecorator;
      if (var3 != null) {
         InputStream var4 = var3.decorate(var2, var1, 0, var1.length);
         if (var4 != null) {
            return this._createParser(var4, var2);
         }
      }

      return this._createParser(var1, 0, var1.length, var2);
   }

   public JsonParser createParser(byte[] var1, int var2, int var3) throws IOException, JsonParseException {
      IOContext var4 = this._createContext(var1, true);
      InputDecorator var5 = this._inputDecorator;
      if (var5 != null) {
         InputStream var6 = var5.decorate(var4, var1, var2, var3);
         if (var6 != null) {
            return this._createParser(var6, var4);
         }
      }

      return this._createParser(var1, var2, var3, var4);
   }

   public JsonParser createParser(char[] var1) throws IOException {
      return this.createParser((char[])var1, 0, var1.length);
   }

   public JsonParser createParser(char[] var1, int var2, int var3) throws IOException {
      return this._inputDecorator != null ? this.createParser((Reader)(new CharArrayReader(var1, var2, var3))) : this._createParser(var1, var2, var3, this._createContext(var1, true), false);
   }

   @Deprecated
   public JsonFactory disable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = var1.getMask() & var2;
      return this;
   }

   public JsonFactory disable(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      this._generatorFeatures = var1.getMask() & var2;
      return this;
   }

   public JsonFactory disable(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      this._parserFeatures = var1.getMask() & var2;
      return this;
   }

   @Deprecated
   public JsonFactory enable(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      this._factoryFeatures = var1.getMask() | var2;
      return this;
   }

   public JsonFactory enable(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      this._generatorFeatures = var1.getMask() | var2;
      return this;
   }

   public JsonFactory enable(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      this._parserFeatures = var1.getMask() | var2;
      return this;
   }

   public CharacterEscapes getCharacterEscapes() {
      return this._characterEscapes;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public int getFormatGeneratorFeatures() {
      return 0;
   }

   public String getFormatName() {
      return this.getClass() == JsonFactory.class ? "JSON" : null;
   }

   public int getFormatParserFeatures() {
      return 0;
   }

   public Class<? extends FormatFeature> getFormatReadFeatureType() {
      return null;
   }

   public Class<? extends FormatFeature> getFormatWriteFeatureType() {
      return null;
   }

   public final int getGeneratorFeatures() {
      return this._generatorFeatures;
   }

   public InputDecorator getInputDecorator() {
      return this._inputDecorator;
   }

   public OutputDecorator getOutputDecorator() {
      return this._outputDecorator;
   }

   public final int getParserFeatures() {
      return this._parserFeatures;
   }

   public String getRootValueSeparator() {
      SerializableString var1 = this._rootValueSeparator;
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getValue();
      }

      return var2;
   }

   public MatchStrength hasFormat(InputAccessor var1) throws IOException {
      return this.getClass() == JsonFactory.class ? this.hasJSONFormat(var1) : null;
   }

   protected MatchStrength hasJSONFormat(InputAccessor var1) throws IOException {
      return ByteSourceJsonBootstrapper.hasJSONFormat(var1);
   }

   public final boolean isEnabled(JsonFactory.Feature var1) {
      int var2 = this._factoryFeatures;
      boolean var3;
      if ((var1.getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isEnabled(JsonGenerator.Feature var1) {
      int var2 = this._generatorFeatures;
      boolean var3;
      if ((var1.getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isEnabled(JsonParser.Feature var1) {
      int var2 = this._parserFeatures;
      boolean var3;
      if ((var1.getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isEnabled(StreamReadFeature var1) {
      int var2 = this._parserFeatures;
      boolean var3;
      if ((var1.mappedFeature().getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isEnabled(StreamWriteFeature var1) {
      int var2 = this._generatorFeatures;
      boolean var3;
      if ((var1.mappedFeature().getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   protected Object readResolve() {
      return new JsonFactory(this, this._objectCodec);
   }

   public TSFBuilder<?, ?> rebuild() {
      this._requireJSONFactory("Factory implementation for format (%s) MUST override `rebuild()` method");
      return new JsonFactoryBuilder(this);
   }

   public boolean requiresCustomCodec() {
      return false;
   }

   public boolean requiresPropertyOrdering() {
      return false;
   }

   public JsonFactory setCharacterEscapes(CharacterEscapes var1) {
      this._characterEscapes = var1;
      return this;
   }

   public JsonFactory setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   @Deprecated
   public JsonFactory setInputDecorator(InputDecorator var1) {
      this._inputDecorator = var1;
      return this;
   }

   @Deprecated
   public JsonFactory setOutputDecorator(OutputDecorator var1) {
      this._outputDecorator = var1;
      return this;
   }

   public JsonFactory setRootValueSeparator(String var1) {
      SerializedString var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      this._rootValueSeparator = var2;
      return this;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public static enum Feature {
      CANONICALIZE_FIELD_NAMES(true),
      FAIL_ON_SYMBOL_HASH_OVERFLOW(true),
      INTERN_FIELD_NAMES(true),
      USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING;

      private final boolean _defaultState;

      static {
         JsonFactory.Feature var0 = new JsonFactory.Feature("USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING", 3, true);
         USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING = var0;
      }

      private Feature(boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         JsonFactory.Feature[] var0 = values();
         int var1 = var0.length;
         int var2 = 0;

         int var3;
         int var5;
         for(var3 = 0; var2 < var1; var3 = var5) {
            JsonFactory.Feature var4 = var0[var2];
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
         if ((var1 & this.getMask()) != 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int getMask() {
         return 1 << this.ordinal();
      }
   }
}
