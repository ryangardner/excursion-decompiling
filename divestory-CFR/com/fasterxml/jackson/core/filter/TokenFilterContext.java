/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.filter.TokenFilter;
import java.io.IOException;

public class TokenFilterContext
extends JsonStreamContext {
    protected TokenFilterContext _child;
    protected String _currentName;
    protected TokenFilter _filter;
    protected boolean _needToHandleName;
    protected final TokenFilterContext _parent;
    protected boolean _startHandled;

    protected TokenFilterContext(int n, TokenFilterContext tokenFilterContext, TokenFilter tokenFilter, boolean bl) {
        this._type = n;
        this._parent = tokenFilterContext;
        this._filter = tokenFilter;
        this._index = -1;
        this._startHandled = bl;
        this._needToHandleName = false;
    }

    private void _writePath(JsonGenerator jsonGenerator) throws IOException {
        Object object = this._filter;
        if (object == null) return;
        if (object == TokenFilter.INCLUDE_ALL) {
            return;
        }
        object = this._parent;
        if (object != null) {
            TokenFilterContext.super._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (!this._needToHandleName) return;
            this._needToHandleName = false;
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        this._startHandled = true;
        if (this._type == 2) {
            jsonGenerator.writeStartObject();
            if (!this._needToHandleName) return;
            this._needToHandleName = false;
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        if (this._type != 1) return;
        jsonGenerator.writeStartArray();
    }

    public static TokenFilterContext createRootContext(TokenFilter tokenFilter) {
        return new TokenFilterContext(0, null, tokenFilter, true);
    }

    protected void appendDesc(StringBuilder stringBuilder) {
        TokenFilterContext tokenFilterContext = this._parent;
        if (tokenFilterContext != null) {
            tokenFilterContext.appendDesc(stringBuilder);
        }
        if (this._type == 2) {
            stringBuilder.append('{');
            if (this._currentName != null) {
                stringBuilder.append('\"');
                stringBuilder.append(this._currentName);
                stringBuilder.append('\"');
            } else {
                stringBuilder.append('?');
            }
            stringBuilder.append('}');
            return;
        }
        if (this._type == 1) {
            stringBuilder.append('[');
            stringBuilder.append(this.getCurrentIndex());
            stringBuilder.append(']');
            return;
        }
        stringBuilder.append("/");
    }

    public TokenFilter checkValue(TokenFilter tokenFilter) {
        int n;
        if (this._type == 2) {
            return tokenFilter;
        }
        this._index = n = this._index + 1;
        if (this._type != 1) return tokenFilter.includeRootValue(n);
        return tokenFilter.includeElement(n);
    }

    public TokenFilterContext closeArray(JsonGenerator object) throws IOException {
        if (this._startHandled) {
            ((JsonGenerator)object).writeEndArray();
        }
        if ((object = this._filter) == null) return this._parent;
        if (object == TokenFilter.INCLUDE_ALL) return this._parent;
        this._filter.filterFinishArray();
        return this._parent;
    }

    public TokenFilterContext closeObject(JsonGenerator object) throws IOException {
        if (this._startHandled) {
            ((JsonGenerator)object).writeEndObject();
        }
        if ((object = this._filter) == null) return this._parent;
        if (object == TokenFilter.INCLUDE_ALL) return this._parent;
        this._filter.filterFinishObject();
        return this._parent;
    }

    public TokenFilterContext createChildArrayContext(TokenFilter object, boolean bl) {
        TokenFilterContext tokenFilterContext = this._child;
        if (tokenFilterContext != null) return tokenFilterContext.reset(1, (TokenFilter)object, bl);
        this._child = object = new TokenFilterContext(1, this, (TokenFilter)object, bl);
        return object;
    }

    public TokenFilterContext createChildObjectContext(TokenFilter object, boolean bl) {
        TokenFilterContext tokenFilterContext = this._child;
        if (tokenFilterContext != null) return tokenFilterContext.reset(2, (TokenFilter)object, bl);
        this._child = object = new TokenFilterContext(2, this, (TokenFilter)object, bl);
        return object;
    }

    public TokenFilterContext findChildOf(TokenFilterContext tokenFilterContext) {
        TokenFilterContext tokenFilterContext2;
        TokenFilterContext tokenFilterContext3 = tokenFilterContext2 = this._parent;
        if (tokenFilterContext2 == tokenFilterContext) {
            return this;
        }
        while (tokenFilterContext3 != null) {
            tokenFilterContext2 = tokenFilterContext3._parent;
            if (tokenFilterContext2 == tokenFilterContext) {
                return tokenFilterContext3;
            }
            tokenFilterContext3 = tokenFilterContext2;
        }
        return null;
    }

    @Override
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override
    public Object getCurrentValue() {
        return null;
    }

    public TokenFilter getFilter() {
        return this._filter;
    }

    @Override
    public final TokenFilterContext getParent() {
        return this._parent;
    }

    @Override
    public boolean hasCurrentName() {
        if (this._currentName == null) return false;
        return true;
    }

    public boolean isStartHandled() {
        return this._startHandled;
    }

    public JsonToken nextTokenToRead() {
        if (!this._startHandled) {
            this._startHandled = true;
            if (this._type != 2) return JsonToken.START_ARRAY;
            return JsonToken.START_OBJECT;
        }
        if (!this._needToHandleName) return null;
        if (this._type != 2) return null;
        this._needToHandleName = false;
        return JsonToken.FIELD_NAME;
    }

    protected TokenFilterContext reset(int n, TokenFilter tokenFilter, boolean bl) {
        this._type = n;
        this._filter = tokenFilter;
        this._index = -1;
        this._currentName = null;
        this._startHandled = bl;
        this._needToHandleName = false;
        return this;
    }

    @Override
    public void setCurrentValue(Object object) {
    }

    public TokenFilter setFieldName(String string2) throws JsonProcessingException {
        this._currentName = string2;
        this._needToHandleName = true;
        return this._filter;
    }

    public void skipParentChecks() {
        this._filter = null;
        TokenFilterContext tokenFilterContext = this._parent;
        while (tokenFilterContext != null) {
            this._parent._filter = null;
            tokenFilterContext = tokenFilterContext._parent;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        this.appendDesc(stringBuilder);
        return stringBuilder.toString();
    }

    public void writeImmediatePath(JsonGenerator jsonGenerator) throws IOException {
        TokenFilter tokenFilter = this._filter;
        if (tokenFilter == null) return;
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._startHandled) {
            if (!this._needToHandleName) return;
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        this._startHandled = true;
        if (this._type == 2) {
            jsonGenerator.writeStartObject();
            if (!this._needToHandleName) return;
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        if (this._type != 1) return;
        jsonGenerator.writeStartArray();
    }

    public void writePath(JsonGenerator jsonGenerator) throws IOException {
        Object object = this._filter;
        if (object == null) return;
        if (object == TokenFilter.INCLUDE_ALL) {
            return;
        }
        object = this._parent;
        if (object != null) {
            TokenFilterContext.super._writePath(jsonGenerator);
        }
        if (this._startHandled) {
            if (!this._needToHandleName) return;
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        this._startHandled = true;
        if (this._type == 2) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName(this._currentName);
            return;
        }
        if (this._type != 1) return;
        jsonGenerator.writeStartArray();
    }
}

