/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.filter.TokenFilterContext;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FilteringGeneratorDelegate
extends JsonGeneratorDelegate {
    protected boolean _allowMultipleMatches;
    protected TokenFilterContext _filterContext;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected boolean _includePath;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    protected TokenFilter rootFilter;

    public FilteringGeneratorDelegate(JsonGenerator jsonGenerator, TokenFilter tokenFilter, boolean bl, boolean bl2) {
        super(jsonGenerator, false);
        this.rootFilter = tokenFilter;
        this._itemFilter = tokenFilter;
        this._filterContext = TokenFilterContext.createRootContext(tokenFilter);
        this._includePath = bl;
        this._allowMultipleMatches = bl2;
    }

    protected boolean _checkBinaryWrite() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return false;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (!this._itemFilter.includeBinary()) return false;
        this._checkParentPath();
        return true;
    }

    protected void _checkParentPath() throws IOException {
        ++this._matchCount;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        }
        if (this._allowMultipleMatches) return;
        this._filterContext.skipParentChecks();
    }

    protected void _checkPropertyParentPath() throws IOException {
        ++this._matchCount;
        if (this._includePath) {
            this._filterContext.writePath(this.delegate);
        } else if (this._includeImmediateParent) {
            this._filterContext.writeImmediatePath(this.delegate);
        }
        if (this._allowMultipleMatches) return;
        this._filterContext.skipParentChecks();
    }

    protected boolean _checkRawValueWrite() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return false;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            return true;
        }
        if (!this._itemFilter.includeRawValue()) return false;
        this._checkParentPath();
        return true;
    }

    public TokenFilter getFilter() {
        return this.rootFilter;
    }

    public JsonStreamContext getFilterContext() {
        return this._filterContext;
    }

    public int getMatchCount() {
        return this._matchCount;
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return this._filterContext;
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream2, int n) throws IOException {
        if (!this._checkBinaryWrite()) return -1;
        return this.delegate.writeBinary(base64Variant, inputStream2, n);
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException {
        if (!this._checkBinaryWrite()) return;
        this.delegate.writeBinary(base64Variant, arrby, n, n2);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeBoolean(bl)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeBoolean(bl);
    }

    @Override
    public void writeEndArray() throws IOException {
        TokenFilterContext tokenFilterContext;
        this._filterContext = tokenFilterContext = this._filterContext.closeArray(this.delegate);
        if (tokenFilterContext == null) return;
        this._itemFilter = tokenFilterContext.getFilter();
    }

    @Override
    public void writeEndObject() throws IOException {
        TokenFilterContext tokenFilterContext;
        this._filterContext = tokenFilterContext = this._filterContext.closeObject(this.delegate);
        if (tokenFilterContext == null) return;
        this._itemFilter = tokenFilterContext.getFilter();
    }

    @Override
    public void writeFieldId(long l) throws IOException {
        this.writeFieldName(Long.toString(l));
    }

    @Override
    public void writeFieldName(SerializableString object) throws IOException {
        TokenFilter tokenFilter = this._filterContext.setFieldName(object.getValue());
        if (tokenFilter == null) {
            this._itemFilter = null;
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = tokenFilter;
            this.delegate.writeFieldName((SerializableString)object);
            return;
        }
        this._itemFilter = object = tokenFilter.includeProperty(object.getValue());
        if (object != TokenFilter.INCLUDE_ALL) return;
        this._checkPropertyParentPath();
    }

    @Override
    public void writeFieldName(String object) throws IOException {
        TokenFilter tokenFilter = this._filterContext.setFieldName((String)object);
        if (tokenFilter == null) {
            this._itemFilter = null;
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._itemFilter = tokenFilter;
            this.delegate.writeFieldName((String)object);
            return;
        }
        this._itemFilter = object = tokenFilter.includeProperty((String)object);
        if (object != TokenFilter.INCLUDE_ALL) return;
        this._checkPropertyParentPath();
    }

    @Override
    public void writeNull() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNull()) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(d)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(d);
    }

    @Override
    public void writeNumber(float f) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(f)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(f);
    }

    @Override
    public void writeNumber(int n) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(n)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(n);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(l)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(l);
    }

    @Override
    public void writeNumber(String string2) throws IOException, UnsupportedOperationException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeRawValue()) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(bigDecimal)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(bigInteger)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(bigInteger);
    }

    @Override
    public void writeNumber(short s) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeNumber(s)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeNumber(s);
    }

    @Override
    public void writeObjectId(Object object) throws IOException {
        if (this._itemFilter == null) return;
        this.delegate.writeObjectId(object);
    }

    @Override
    public void writeObjectRef(Object object) throws IOException {
        if (this._itemFilter == null) return;
        this.delegate.writeObjectRef(object);
    }

    @Override
    public void writeOmittedField(String string2) throws IOException {
        if (this._itemFilter == null) return;
        this.delegate.writeOmittedField(string2);
    }

    @Override
    public void writeRaw(char c) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRaw(c);
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRaw(serializableString);
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRaw(string2);
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRaw(string2, n, n2);
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRaw(arrc, n, n2);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRawUTF8String(arrby, n, n2);
    }

    @Override
    public void writeRawValue(String string2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRawValue(string2);
    }

    @Override
    public void writeRawValue(String string2, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRawValue(string2, n, n2);
    }

    @Override
    public void writeRawValue(char[] arrc, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeRawValue(arrc, n, n2);
    }

    @Override
    public void writeStartArray() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray();
            return;
        }
        this._itemFilter = tokenFilter = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray();
            return;
        }
        this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
    }

    @Override
    public void writeStartArray(int n) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(n);
            return;
        }
        this._itemFilter = tokenFilter = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(n);
            return;
        }
        this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
    }

    @Override
    public void writeStartArray(Object object) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(object);
            return;
        }
        this._itemFilter = tokenFilter = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(object);
            return;
        }
        this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
    }

    @Override
    public void writeStartArray(Object object, int n) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(object, n);
            return;
        }
        this._itemFilter = tokenFilter = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildArrayContext(null, false);
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            this._itemFilter = this._itemFilter.filterStartArray();
        }
        if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
            this.delegate.writeStartArray(object, n);
            return;
        }
        this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
    }

    @Override
    public void writeStartObject() throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject();
            return;
        }
        TokenFilter tokenFilter2 = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter2 == null) {
            return;
        }
        tokenFilter = tokenFilter2;
        if (tokenFilter2 != TokenFilter.INCLUDE_ALL) {
            tokenFilter = tokenFilter2.filterStartObject();
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, true);
            this.delegate.writeStartObject();
            return;
        }
        this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
    }

    @Override
    public void writeStartObject(Object object) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject(object);
            return;
        }
        TokenFilter tokenFilter2 = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter2 == null) {
            return;
        }
        tokenFilter = tokenFilter2;
        if (tokenFilter2 != TokenFilter.INCLUDE_ALL) {
            tokenFilter = tokenFilter2.filterStartObject();
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, true);
            this.delegate.writeStartObject(object);
            return;
        }
        this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
    }

    @Override
    public void writeStartObject(Object object, int n) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
            return;
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
            this.delegate.writeStartObject(object, n);
            return;
        }
        TokenFilter tokenFilter2 = this._filterContext.checkValue(this._itemFilter);
        if (tokenFilter2 == null) {
            return;
        }
        tokenFilter = tokenFilter2;
        if (tokenFilter2 != TokenFilter.INCLUDE_ALL) {
            tokenFilter = tokenFilter2.filterStartObject();
        }
        if (tokenFilter == TokenFilter.INCLUDE_ALL) {
            this._checkParentPath();
            this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, true);
            this.delegate.writeStartObject(object, n);
            return;
        }
        this._filterContext = this._filterContext.createChildObjectContext(tokenFilter, false);
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeString(serializableString.getValue())) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(serializableString);
    }

    @Override
    public void writeString(String string2) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeString(string2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(string2);
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException {
        TokenFilter tokenFilter = this._itemFilter;
        if (tokenFilter == null) {
            return;
        }
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            String string2 = new String(arrc, n, n2);
            tokenFilter = this._filterContext.checkValue(this._itemFilter);
            if (tokenFilter == null) {
                return;
            }
            if (tokenFilter != TokenFilter.INCLUDE_ALL && !tokenFilter.includeString(string2)) {
                return;
            }
            this._checkParentPath();
        }
        this.delegate.writeString(arrc, n, n2);
    }

    @Override
    public void writeTypeId(Object object) throws IOException {
        if (this._itemFilter == null) return;
        this.delegate.writeTypeId(object);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException {
        if (!this._checkRawValueWrite()) return;
        this.delegate.writeUTF8String(arrby, n, n2);
    }
}

