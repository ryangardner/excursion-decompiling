/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.core.util.Separators;
import java.io.IOException;
import java.io.Serializable;

public class DefaultPrettyPrinter
implements PrettyPrinter,
Instantiatable<DefaultPrettyPrinter>,
Serializable {
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    private static final long serialVersionUID = 1L;
    protected Indenter _arrayIndenter;
    protected transient int _nesting;
    protected String _objectFieldValueSeparatorWithSpaces;
    protected Indenter _objectIndenter;
    protected final SerializableString _rootSeparator;
    protected Separators _separators;
    protected boolean _spacesInObjectEntries;

    public DefaultPrettyPrinter() {
        this(DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public DefaultPrettyPrinter(SerializableString serializableString) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._rootSeparator = serializableString;
        this.withSeparators(DEFAULT_SEPARATORS);
    }

    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter) {
        this(defaultPrettyPrinter, defaultPrettyPrinter._rootSeparator);
    }

    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter, SerializableString serializableString) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
        this._objectIndenter = defaultPrettyPrinter._objectIndenter;
        this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
        this._nesting = defaultPrettyPrinter._nesting;
        this._separators = defaultPrettyPrinter._separators;
        this._objectFieldValueSeparatorWithSpaces = defaultPrettyPrinter._objectFieldValueSeparatorWithSpaces;
        this._rootSeparator = serializableString;
    }

    public DefaultPrettyPrinter(String object) {
        object = object == null ? null : new SerializedString((String)object);
        this((SerializableString)object);
    }

    protected DefaultPrettyPrinter _withSpaces(boolean bl) {
        if (this._spacesInObjectEntries == bl) {
            return this;
        }
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
        defaultPrettyPrinter._spacesInObjectEntries = bl;
        return defaultPrettyPrinter;
    }

    @Override
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        if (this.getClass() == DefaultPrettyPrinter.class) {
            return new DefaultPrettyPrinter(this);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed `createInstance()`: ");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" does not override method; it has to");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void indentArraysWith(Indenter indenter) {
        Indenter indenter2 = indenter;
        if (indenter == null) {
            indenter2 = NopIndenter.instance;
        }
        this._arrayIndenter = indenter2;
    }

    public void indentObjectsWith(Indenter indenter) {
        Indenter indenter2 = indenter;
        if (indenter == null) {
            indenter2 = NopIndenter.instance;
        }
        this._objectIndenter = indenter2;
    }

    public DefaultPrettyPrinter withArrayIndenter(Indenter object) {
        Indenter indenter = object;
        if (object == null) {
            indenter = NopIndenter.instance;
        }
        if (this._arrayIndenter == indenter) {
            return this;
        }
        object = new DefaultPrettyPrinter(this);
        ((DefaultPrettyPrinter)object)._arrayIndenter = indenter;
        return object;
    }

    public DefaultPrettyPrinter withObjectIndenter(Indenter object) {
        Indenter indenter = object;
        if (object == null) {
            indenter = NopIndenter.instance;
        }
        if (this._objectIndenter == indenter) {
            return this;
        }
        object = new DefaultPrettyPrinter(this);
        ((DefaultPrettyPrinter)object)._objectIndenter = indenter;
        return object;
    }

    public DefaultPrettyPrinter withRootSeparator(SerializableString serializableString) {
        SerializableString serializableString2 = this._rootSeparator;
        if (serializableString2 == serializableString) return this;
        if (serializableString == null) return new DefaultPrettyPrinter(this, serializableString);
        if (!serializableString.equals(serializableString2)) return new DefaultPrettyPrinter(this, serializableString);
        return this;
    }

    public DefaultPrettyPrinter withRootSeparator(String object) {
        if (object == null) {
            object = null;
            return this.withRootSeparator((SerializableString)object);
        }
        object = new SerializedString((String)object);
        return this.withRootSeparator((SerializableString)object);
    }

    public DefaultPrettyPrinter withSeparators(Separators separators) {
        this._separators = separators;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(separators.getObjectFieldValueSeparator());
        stringBuilder.append(" ");
        this._objectFieldValueSeparatorWithSpaces = stringBuilder.toString();
        return this;
    }

    public DefaultPrettyPrinter withSpacesInObjectEntries() {
        return this._withSpaces(true);
    }

    public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
        return this._withSpaces(false);
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._separators.getArrayValueSeparator());
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void writeEndArray(JsonGenerator jsonGenerator, int n) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            --this._nesting;
        }
        if (n > 0) {
            this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw(']');
    }

    @Override
    public void writeEndObject(JsonGenerator jsonGenerator, int n) throws IOException {
        if (!this._objectIndenter.isInline()) {
            --this._nesting;
        }
        if (n > 0) {
            this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw('}');
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._separators.getObjectEntrySeparator());
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        if (this._spacesInObjectEntries) {
            jsonGenerator.writeRaw(this._objectFieldValueSeparatorWithSpaces);
            return;
        }
        jsonGenerator.writeRaw(this._separators.getObjectFieldValueSeparator());
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        SerializableString serializableString = this._rootSeparator;
        if (serializableString == null) return;
        jsonGenerator.writeRaw(serializableString);
    }

    @Override
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            ++this._nesting;
        }
        jsonGenerator.writeRaw('[');
    }

    @Override
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw('{');
        if (this._objectIndenter.isInline()) return;
        ++this._nesting;
    }

    public static class FixedSpaceIndenter
    extends NopIndenter {
        public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();

        @Override
        public boolean isInline() {
            return true;
        }

        @Override
        public void writeIndentation(JsonGenerator jsonGenerator, int n) throws IOException {
            jsonGenerator.writeRaw(' ');
        }
    }

    public static interface Indenter {
        public boolean isInline();

        public void writeIndentation(JsonGenerator var1, int var2) throws IOException;
    }

    public static class NopIndenter
    implements Indenter,
    Serializable {
        public static final NopIndenter instance = new NopIndenter();

        @Override
        public boolean isInline() {
            return true;
        }

        @Override
        public void writeIndentation(JsonGenerator jsonGenerator, int n) throws IOException {
        }
    }

}

