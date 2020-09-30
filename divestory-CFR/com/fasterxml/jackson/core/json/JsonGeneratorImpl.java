/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class JsonGeneratorImpl
extends GeneratorBase {
    protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
    protected boolean _cfgUnqNames;
    protected CharacterEscapes _characterEscapes;
    protected final IOContext _ioContext;
    protected int _maximumNonEscapedChar;
    protected int[] _outputEscapes = sOutputEscapes;
    protected SerializableString _rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;

    public JsonGeneratorImpl(IOContext iOContext, int n, ObjectCodec objectCodec) {
        super(n, objectCodec);
        this._ioContext = iOContext;
        if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(n)) {
            this._maximumNonEscapedChar = 127;
        }
        this._cfgUnqNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(n) ^ true;
    }

    @Override
    protected void _checkStdFeatureChanges(int n, int n2) {
        super._checkStdFeatureChanges(n, n2);
        this._cfgUnqNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(n) ^ true;
    }

    protected void _reportCantWriteValueExpectName(String string2) throws IOException {
        this._reportError(String.format("Can not %s, expecting field name (context: %s)", string2, this._writeContext.typeDesc()));
    }

    protected void _verifyPrettyValueWrite(String string2, int n) throws IOException {
        if (n != 0) {
            if (n == 1) {
                this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                return;
            }
            if (n == 2) {
                this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                return;
            }
            if (n == 3) {
                this._cfgPrettyPrinter.writeRootValueSeparator(this);
                return;
            }
            if (n != 5) {
                this._throwInternal();
                return;
            }
            this._reportCantWriteValueExpectName(string2);
            return;
        }
        if (this._writeContext.inArray()) {
            this._cfgPrettyPrinter.beforeArrayValues(this);
            return;
        }
        if (!this._writeContext.inObject()) return;
        this._cfgPrettyPrinter.beforeObjectEntries(this);
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        super.disable(feature);
        if (feature != JsonGenerator.Feature.QUOTE_FIELD_NAMES) return this;
        this._cfgUnqNames = true;
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        super.enable(feature);
        if (feature != JsonGenerator.Feature.QUOTE_FIELD_NAMES) return this;
        this._cfgUnqNames = false;
        return this;
    }

    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    @Override
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }

    @Override
    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        if (characterEscapes == null) {
            this._outputEscapes = sOutputEscapes;
            return this;
        }
        this._outputEscapes = characterEscapes.getEscapeCodesForAscii();
        return this;
    }

    @Override
    public JsonGenerator setHighestNonEscapedChar(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = 0;
        }
        this._maximumNonEscapedChar = n2;
        return this;
    }

    @Override
    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        this._rootValueSeparator = serializableString;
        return this;
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }

    @Override
    public final void writeStringField(String string2, String string3) throws IOException {
        this.writeFieldName(string2);
        this.writeString(string3);
    }
}

