/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.TokenStreamFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
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
import java.io.Closeable;
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

public class JsonFactory
extends TokenStreamFactory
implements Versioned,
Serializable {
    protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
    protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected static final int DEFAULT_PARSER_FEATURE_FLAGS;
    public static final char DEFAULT_QUOTE_CHAR = '\"';
    public static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
    public static final String FORMAT_NAME_JSON = "JSON";
    private static final long serialVersionUID = 2L;
    protected final transient ByteQuadsCanonicalizer _byteSymbolCanonicalizer = ByteQuadsCanonicalizer.createRoot();
    protected CharacterEscapes _characterEscapes;
    protected int _factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
    protected int _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected InputDecorator _inputDecorator;
    protected int _maximumNonEscapedChar;
    protected ObjectCodec _objectCodec;
    protected OutputDecorator _outputDecorator;
    protected int _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
    protected final char _quoteChar;
    protected final transient CharsToNameCanonicalizer _rootCharSymbols = CharsToNameCanonicalizer.createRoot();
    protected SerializableString _rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;

    static {
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
        DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
    }

    public JsonFactory() {
        this((ObjectCodec)null);
    }

    protected JsonFactory(JsonFactory jsonFactory, ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        this._factoryFeatures = jsonFactory._factoryFeatures;
        this._parserFeatures = jsonFactory._parserFeatures;
        this._generatorFeatures = jsonFactory._generatorFeatures;
        this._inputDecorator = jsonFactory._inputDecorator;
        this._outputDecorator = jsonFactory._outputDecorator;
        this._characterEscapes = jsonFactory._characterEscapes;
        this._rootValueSeparator = jsonFactory._rootValueSeparator;
        this._maximumNonEscapedChar = jsonFactory._maximumNonEscapedChar;
        this._quoteChar = jsonFactory._quoteChar;
    }

    public JsonFactory(JsonFactoryBuilder jsonFactoryBuilder) {
        this._objectCodec = null;
        this._factoryFeatures = jsonFactoryBuilder._factoryFeatures;
        this._parserFeatures = jsonFactoryBuilder._streamReadFeatures;
        this._generatorFeatures = jsonFactoryBuilder._streamWriteFeatures;
        this._inputDecorator = jsonFactoryBuilder._inputDecorator;
        this._outputDecorator = jsonFactoryBuilder._outputDecorator;
        this._characterEscapes = jsonFactoryBuilder._characterEscapes;
        this._rootValueSeparator = jsonFactoryBuilder._rootValueSeparator;
        this._maximumNonEscapedChar = jsonFactoryBuilder._maximumNonEscapedChar;
        this._quoteChar = jsonFactoryBuilder._quoteChar;
    }

    public JsonFactory(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        this._quoteChar = (char)34;
    }

    protected JsonFactory(TSFBuilder<?, ?> tSFBuilder, boolean bl) {
        this._objectCodec = null;
        this._factoryFeatures = tSFBuilder._factoryFeatures;
        this._parserFeatures = tSFBuilder._streamReadFeatures;
        this._generatorFeatures = tSFBuilder._streamWriteFeatures;
        this._inputDecorator = tSFBuilder._inputDecorator;
        this._outputDecorator = tSFBuilder._outputDecorator;
        this._characterEscapes = null;
        this._rootValueSeparator = null;
        this._maximumNonEscapedChar = 0;
        this._quoteChar = (char)34;
    }

    private final boolean _isJSONFactory() {
        if (this.getFormatName() != FORMAT_NAME_JSON) return false;
        return true;
    }

    private final void _requireJSONFactory(String string2) {
        if (!this._isJSONFactory()) throw new UnsupportedOperationException(String.format(string2, this.getFormatName()));
    }

    public static TSFBuilder<?, ?> builder() {
        return new JsonFactoryBuilder();
    }

    protected void _checkInvalidCopy(Class<?> serializable) {
        if (this.getClass() == serializable) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Failed copy(): ");
        ((StringBuilder)serializable).append(this.getClass().getName());
        ((StringBuilder)serializable).append(" (version: ");
        ((StringBuilder)serializable).append(this.version());
        ((StringBuilder)serializable).append(") does not override copy(); it has to");
        throw new IllegalStateException(((StringBuilder)serializable).toString());
    }

    protected IOContext _createContext(Object object, boolean bl) {
        return new IOContext(this._getBufferRecycler(), object, bl);
    }

    protected JsonGenerator _createGenerator(Writer closeable, IOContext object) throws IOException {
        closeable = new WriterBasedJsonGenerator((IOContext)object, this._generatorFeatures, this._objectCodec, (Writer)closeable, this._quoteChar);
        int n = this._maximumNonEscapedChar;
        if (n > 0) {
            ((JsonGeneratorImpl)closeable).setHighestNonEscapedChar(n);
        }
        if ((object = this._characterEscapes) != null) {
            ((JsonGeneratorImpl)closeable).setCharacterEscapes((CharacterEscapes)object);
        }
        if ((object = this._rootValueSeparator) == DEFAULT_ROOT_VALUE_SEPARATOR) return closeable;
        ((JsonGeneratorImpl)closeable).setRootValueSeparator((SerializableString)object);
        return closeable;
    }

    protected IOContext _createNonBlockingContext(Object object) {
        return new IOContext(this._getBufferRecycler(), object, false);
    }

    protected JsonParser _createParser(DataInput dataInput, IOContext iOContext) throws IOException {
        this._requireJSONFactory("InputData source not (yet?) supported for this format (%s)");
        int n = ByteSourceJsonBootstrapper.skipUTF8BOM(dataInput);
        ByteQuadsCanonicalizer byteQuadsCanonicalizer = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
        return new UTF8DataInputJsonParser(iOContext, this._parserFeatures, dataInput, this._objectCodec, byteQuadsCanonicalizer, n);
    }

    protected JsonParser _createParser(InputStream inputStream2, IOContext iOContext) throws IOException {
        return new ByteSourceJsonBootstrapper(iOContext, inputStream2).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
    }

    protected JsonParser _createParser(Reader reader, IOContext iOContext) throws IOException {
        return new ReaderBasedJsonParser(iOContext, this._parserFeatures, reader, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
    }

    protected JsonParser _createParser(byte[] arrby, int n, int n2, IOContext iOContext) throws IOException {
        return new ByteSourceJsonBootstrapper(iOContext, arrby, n, n2).constructParser(this._parserFeatures, this._objectCodec, this._byteSymbolCanonicalizer, this._rootCharSymbols, this._factoryFeatures);
    }

    protected JsonParser _createParser(char[] arrc, int n, int n2, IOContext iOContext, boolean bl) throws IOException {
        return new ReaderBasedJsonParser(iOContext, this._parserFeatures, null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), arrc, n, n + n2, bl);
    }

    protected JsonGenerator _createUTF8Generator(OutputStream closeable, IOContext object) throws IOException {
        closeable = new UTF8JsonGenerator((IOContext)object, this._generatorFeatures, this._objectCodec, (OutputStream)closeable, this._quoteChar);
        int n = this._maximumNonEscapedChar;
        if (n > 0) {
            ((JsonGeneratorImpl)closeable).setHighestNonEscapedChar(n);
        }
        if ((object = this._characterEscapes) != null) {
            ((JsonGeneratorImpl)closeable).setCharacterEscapes((CharacterEscapes)object);
        }
        if ((object = this._rootValueSeparator) == DEFAULT_ROOT_VALUE_SEPARATOR) return closeable;
        ((JsonGeneratorImpl)closeable).setRootValueSeparator((SerializableString)object);
        return closeable;
    }

    protected Writer _createWriter(OutputStream outputStream2, JsonEncoding jsonEncoding, IOContext iOContext) throws IOException {
        if (jsonEncoding != JsonEncoding.UTF8) return new OutputStreamWriter(outputStream2, jsonEncoding.getJavaName());
        return new UTF8Writer(iOContext, outputStream2);
    }

    protected final DataInput _decorate(DataInput dataInput, IOContext object) throws IOException {
        InputDecorator inputDecorator = this._inputDecorator;
        if (inputDecorator == null) return dataInput;
        if ((object = inputDecorator.decorate((IOContext)object, dataInput)) == null) return dataInput;
        return object;
    }

    protected final InputStream _decorate(InputStream inputStream2, IOContext object) throws IOException {
        InputDecorator inputDecorator = this._inputDecorator;
        if (inputDecorator == null) return inputStream2;
        if ((object = inputDecorator.decorate((IOContext)object, inputStream2)) == null) return inputStream2;
        return object;
    }

    protected final OutputStream _decorate(OutputStream outputStream2, IOContext object) throws IOException {
        OutputDecorator outputDecorator = this._outputDecorator;
        if (outputDecorator == null) return outputStream2;
        if ((object = outputDecorator.decorate((IOContext)object, outputStream2)) == null) return outputStream2;
        return object;
    }

    protected final Reader _decorate(Reader reader, IOContext object) throws IOException {
        InputDecorator inputDecorator = this._inputDecorator;
        if (inputDecorator == null) return reader;
        if ((object = inputDecorator.decorate((IOContext)object, reader)) == null) return reader;
        return object;
    }

    protected final Writer _decorate(Writer writer, IOContext object) throws IOException {
        OutputDecorator outputDecorator = this._outputDecorator;
        if (outputDecorator == null) return writer;
        if ((object = outputDecorator.decorate((IOContext)object, writer)) == null) return writer;
        return object;
    }

    public BufferRecycler _getBufferRecycler() {
        if (!Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING.enabledIn(this._factoryFeatures)) return new BufferRecycler();
        return BufferRecyclers.getBufferRecycler();
    }

    @Override
    public boolean canHandleBinaryNatively() {
        return false;
    }

    @Override
    public boolean canParseAsync() {
        return this._isJSONFactory();
    }

    public boolean canUseCharArrays() {
        return true;
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        boolean bl = false;
        if (formatSchema == null) {
            return false;
        }
        String string2 = this.getFormatName();
        boolean bl2 = bl;
        if (string2 == null) return bl2;
        bl2 = bl;
        if (!string2.equals(formatSchema.getSchemaType())) return bl2;
        return true;
    }

    @Deprecated
    public final JsonFactory configure(Feature object, boolean bl) {
        if (!bl) return this.disable((Feature)((Object)object));
        return this.enable((Feature)((Object)object));
    }

    public final JsonFactory configure(JsonGenerator.Feature object, boolean bl) {
        if (!bl) return this.disable((JsonGenerator.Feature)((Object)object));
        return this.enable((JsonGenerator.Feature)((Object)object));
    }

    public final JsonFactory configure(JsonParser.Feature object, boolean bl) {
        if (!bl) return this.disable((JsonParser.Feature)((Object)object));
        return this.enable((JsonParser.Feature)((Object)object));
    }

    public JsonFactory copy() {
        this._checkInvalidCopy(JsonFactory.class);
        return new JsonFactory(this, null);
    }

    @Override
    public JsonGenerator createGenerator(DataOutput dataOutput) throws IOException {
        return this.createGenerator(this._createDataOutputWrapper(dataOutput), JsonEncoding.UTF8);
    }

    @Override
    public JsonGenerator createGenerator(DataOutput dataOutput, JsonEncoding jsonEncoding) throws IOException {
        return this.createGenerator(this._createDataOutputWrapper(dataOutput), jsonEncoding);
    }

    @Override
    public JsonGenerator createGenerator(File object, JsonEncoding jsonEncoding) throws IOException {
        object = new FileOutputStream((File)object);
        IOContext iOContext = this._createContext(object, true);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding != JsonEncoding.UTF8) return this._createGenerator(this._decorate(this._createWriter((OutputStream)object, jsonEncoding, iOContext), iOContext), iOContext);
        return this._createUTF8Generator(this._decorate((OutputStream)object, iOContext), iOContext);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream outputStream2) throws IOException {
        return this.createGenerator(outputStream2, JsonEncoding.UTF8);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream outputStream2, JsonEncoding jsonEncoding) throws IOException {
        IOContext iOContext = this._createContext(outputStream2, false);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding != JsonEncoding.UTF8) return this._createGenerator(this._decorate(this._createWriter(outputStream2, jsonEncoding, iOContext), iOContext), iOContext);
        return this._createUTF8Generator(this._decorate(outputStream2, iOContext), iOContext);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) throws IOException {
        IOContext iOContext = this._createContext(writer, false);
        return this._createGenerator(this._decorate(writer, iOContext), iOContext);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(OutputStream outputStream2) throws IOException {
        return this.createGenerator(outputStream2, JsonEncoding.UTF8);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(OutputStream outputStream2, JsonEncoding jsonEncoding) throws IOException {
        return this.createGenerator(outputStream2, jsonEncoding);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return this.createGenerator(writer);
    }

    @Deprecated
    public JsonParser createJsonParser(File file) throws IOException, JsonParseException {
        return this.createParser(file);
    }

    @Deprecated
    public JsonParser createJsonParser(InputStream inputStream2) throws IOException, JsonParseException {
        return this.createParser(inputStream2);
    }

    @Deprecated
    public JsonParser createJsonParser(Reader reader) throws IOException, JsonParseException {
        return this.createParser(reader);
    }

    @Deprecated
    public JsonParser createJsonParser(String string2) throws IOException, JsonParseException {
        return this.createParser(string2);
    }

    @Deprecated
    public JsonParser createJsonParser(URL uRL) throws IOException, JsonParseException {
        return this.createParser(uRL);
    }

    @Deprecated
    public JsonParser createJsonParser(byte[] arrby) throws IOException, JsonParseException {
        return this.createParser(arrby);
    }

    @Deprecated
    public JsonParser createJsonParser(byte[] arrby, int n, int n2) throws IOException, JsonParseException {
        return this.createParser(arrby, n, n2);
    }

    @Override
    public JsonParser createNonBlockingByteArrayParser() throws IOException {
        this._requireJSONFactory("Non-blocking source not (yet?) supported for this format (%s)");
        IOContext iOContext = this._createNonBlockingContext(null);
        ByteQuadsCanonicalizer byteQuadsCanonicalizer = this._byteSymbolCanonicalizer.makeChild(this._factoryFeatures);
        return new NonBlockingJsonParser(iOContext, this._parserFeatures, byteQuadsCanonicalizer);
    }

    @Override
    public JsonParser createParser(DataInput dataInput) throws IOException {
        IOContext iOContext = this._createContext(dataInput, false);
        return this._createParser(this._decorate(dataInput, iOContext), iOContext);
    }

    @Override
    public JsonParser createParser(File file) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(file, true);
        return this._createParser(this._decorate(new FileInputStream(file), iOContext), iOContext);
    }

    @Override
    public JsonParser createParser(InputStream inputStream2) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(inputStream2, false);
        return this._createParser(this._decorate(inputStream2, iOContext), iOContext);
    }

    @Override
    public JsonParser createParser(Reader reader) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(reader, false);
        return this._createParser(this._decorate(reader, iOContext), iOContext);
    }

    @Override
    public JsonParser createParser(String string2) throws IOException, JsonParseException {
        int n = string2.length();
        if (this._inputDecorator != null) return this.createParser(new StringReader(string2));
        if (n > 32768) return this.createParser(new StringReader(string2));
        if (!this.canUseCharArrays()) {
            return this.createParser(new StringReader(string2));
        }
        IOContext iOContext = this._createContext(string2, true);
        char[] arrc = iOContext.allocTokenBuffer(n);
        string2.getChars(0, n, arrc, 0);
        return this._createParser(arrc, 0, n, iOContext, true);
    }

    @Override
    public JsonParser createParser(URL uRL) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(uRL, true);
        return this._createParser(this._decorate(this._optimizedStreamFromURL(uRL), iOContext), iOContext);
    }

    @Override
    public JsonParser createParser(byte[] arrby) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(arrby, true);
        Object object = this._inputDecorator;
        if (object == null) return this._createParser(arrby, 0, arrby.length, iOContext);
        if ((object = ((InputDecorator)object).decorate(iOContext, arrby, 0, arrby.length)) == null) return this._createParser(arrby, 0, arrby.length, iOContext);
        return this._createParser((InputStream)object, iOContext);
    }

    @Override
    public JsonParser createParser(byte[] arrby, int n, int n2) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(arrby, true);
        Object object = this._inputDecorator;
        if (object == null) return this._createParser(arrby, n, n2, iOContext);
        if ((object = ((InputDecorator)object).decorate(iOContext, arrby, n, n2)) == null) return this._createParser(arrby, n, n2, iOContext);
        return this._createParser((InputStream)object, iOContext);
    }

    @Override
    public JsonParser createParser(char[] arrc) throws IOException {
        return this.createParser(arrc, 0, arrc.length);
    }

    @Override
    public JsonParser createParser(char[] arrc, int n, int n2) throws IOException {
        if (this._inputDecorator == null) return this._createParser(arrc, n, n2, this._createContext(arrc, true), false);
        return this.createParser(new CharArrayReader(arrc, n, n2));
    }

    @Deprecated
    public JsonFactory disable(Feature feature) {
        int n = this._factoryFeatures;
        this._factoryFeatures = feature.getMask() & n;
        return this;
    }

    public JsonFactory disable(JsonGenerator.Feature feature) {
        int n = this._generatorFeatures;
        this._generatorFeatures = feature.getMask() & n;
        return this;
    }

    public JsonFactory disable(JsonParser.Feature feature) {
        int n = this._parserFeatures;
        this._parserFeatures = feature.getMask() & n;
        return this;
    }

    @Deprecated
    public JsonFactory enable(Feature feature) {
        int n = this._factoryFeatures;
        this._factoryFeatures = feature.getMask() | n;
        return this;
    }

    public JsonFactory enable(JsonGenerator.Feature feature) {
        int n = this._generatorFeatures;
        this._generatorFeatures = feature.getMask() | n;
        return this;
    }

    public JsonFactory enable(JsonParser.Feature feature) {
        int n = this._parserFeatures;
        this._parserFeatures = feature.getMask() | n;
        return this;
    }

    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public int getFormatGeneratorFeatures() {
        return 0;
    }

    @Override
    public String getFormatName() {
        if (this.getClass() != JsonFactory.class) return null;
        return FORMAT_NAME_JSON;
    }

    @Override
    public int getFormatParserFeatures() {
        return 0;
    }

    @Override
    public Class<? extends FormatFeature> getFormatReadFeatureType() {
        return null;
    }

    @Override
    public Class<? extends FormatFeature> getFormatWriteFeatureType() {
        return null;
    }

    @Override
    public final int getGeneratorFeatures() {
        return this._generatorFeatures;
    }

    public InputDecorator getInputDecorator() {
        return this._inputDecorator;
    }

    public OutputDecorator getOutputDecorator() {
        return this._outputDecorator;
    }

    @Override
    public final int getParserFeatures() {
        return this._parserFeatures;
    }

    public String getRootValueSeparator() {
        SerializableString serializableString = this._rootValueSeparator;
        if (serializableString != null) return serializableString.getValue();
        return null;
    }

    public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
        if (this.getClass() != JsonFactory.class) return null;
        return this.hasJSONFormat(inputAccessor);
    }

    protected MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        return ByteSourceJsonBootstrapper.hasJSONFormat(inputAccessor);
    }

    public final boolean isEnabled(Feature feature) {
        int n = this._factoryFeatures;
        if ((feature.getMask() & n) == 0) return false;
        return true;
    }

    @Override
    public final boolean isEnabled(JsonGenerator.Feature feature) {
        int n = this._generatorFeatures;
        if ((feature.getMask() & n) == 0) return false;
        return true;
    }

    @Override
    public final boolean isEnabled(JsonParser.Feature feature) {
        int n = this._parserFeatures;
        if ((feature.getMask() & n) == 0) return false;
        return true;
    }

    public final boolean isEnabled(StreamReadFeature streamReadFeature) {
        int n = this._parserFeatures;
        if ((streamReadFeature.mappedFeature().getMask() & n) == 0) return false;
        return true;
    }

    public final boolean isEnabled(StreamWriteFeature streamWriteFeature) {
        int n = this._generatorFeatures;
        if ((streamWriteFeature.mappedFeature().getMask() & n) == 0) return false;
        return true;
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

    @Override
    public boolean requiresPropertyOrdering() {
        return false;
    }

    public JsonFactory setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        return this;
    }

    public JsonFactory setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Deprecated
    public JsonFactory setInputDecorator(InputDecorator inputDecorator) {
        this._inputDecorator = inputDecorator;
        return this;
    }

    @Deprecated
    public JsonFactory setOutputDecorator(OutputDecorator outputDecorator) {
        this._outputDecorator = outputDecorator;
        return this;
    }

    public JsonFactory setRootValueSeparator(String object) {
        object = object == null ? null : new SerializedString((String)object);
        this._rootValueSeparator = object;
        return this;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public static final class Feature
    extends Enum<Feature> {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature CANONICALIZE_FIELD_NAMES;
        public static final /* enum */ Feature FAIL_ON_SYMBOL_HASH_OVERFLOW;
        public static final /* enum */ Feature INTERN_FIELD_NAMES;
        public static final /* enum */ Feature USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING;
        private final boolean _defaultState;

        static {
            Feature feature;
            INTERN_FIELD_NAMES = new Feature(true);
            CANONICALIZE_FIELD_NAMES = new Feature(true);
            FAIL_ON_SYMBOL_HASH_OVERFLOW = new Feature(true);
            USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING = feature = new Feature(true);
            $VALUES = new Feature[]{INTERN_FIELD_NAMES, CANONICALIZE_FIELD_NAMES, FAIL_ON_SYMBOL_HASH_OVERFLOW, feature};
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
        }

        public static int collectDefaults() {
            Feature[] arrfeature = Feature.values();
            int n = arrfeature.length;
            int n2 = 0;
            int n3 = 0;
            while (n2 < n) {
                Feature feature = arrfeature[n2];
                int n4 = n3;
                if (feature.enabledByDefault()) {
                    n4 = n3 | feature.getMask();
                }
                ++n2;
                n3 = n4;
            }
            return n3;
        }

        public static Feature valueOf(String string2) {
            return Enum.valueOf(Feature.class, string2);
        }

        public static Feature[] values() {
            return (Feature[])$VALUES.clone();
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int n) {
            if ((n & this.getMask()) == 0) return false;
            return true;
        }

        public int getMask() {
            return 1 << this.ordinal();
        }
    }

}

