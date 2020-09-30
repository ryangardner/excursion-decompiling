/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.filter.TokenFilterContext;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FilteringParserDelegate
extends JsonParserDelegate {
    protected boolean _allowMultipleMatches;
    protected JsonToken _currToken;
    protected TokenFilterContext _exposedContext;
    protected TokenFilterContext _headContext;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected boolean _includePath;
    protected TokenFilter _itemFilter;
    protected JsonToken _lastClearedToken;
    protected int _matchCount;
    protected TokenFilter rootFilter;

    public FilteringParserDelegate(JsonParser jsonParser, TokenFilter tokenFilter, boolean bl, boolean bl2) {
        super(jsonParser);
        this.rootFilter = tokenFilter;
        this._itemFilter = tokenFilter;
        this._headContext = TokenFilterContext.createRootContext(tokenFilter);
        this._includePath = bl;
        this._allowMultipleMatches = bl2;
    }

    private JsonToken _nextBuffered(TokenFilterContext tokenFilterContext) throws IOException {
        this._exposedContext = tokenFilterContext;
        JsonToken jsonToken = tokenFilterContext.nextTokenToRead();
        if (jsonToken != null) {
            return jsonToken;
        }
        do {
            if (tokenFilterContext == this._headContext) throw this._constructError("Internal error: failed to locate expected buffered tokens");
            this._exposedContext = tokenFilterContext = this._exposedContext.findChildOf(tokenFilterContext);
            if (tokenFilterContext == null) throw this._constructError("Unexpected problem: chain of filtered context broken");
        } while ((jsonToken = tokenFilterContext.nextTokenToRead()) == null);
        return jsonToken;
    }

    private final boolean _verifyAllowedMatches() throws IOException {
        if (this._matchCount != 0) {
            if (!this._allowMultipleMatches) return false;
        }
        ++this._matchCount;
        return true;
    }

    protected JsonStreamContext _filterContext() {
        TokenFilterContext tokenFilterContext = this._exposedContext;
        if (tokenFilterContext == null) return this._headContext;
        return tokenFilterContext;
    }

    protected final JsonToken _nextToken2() throws IOException {
        Object object;
        do {
            Object object2;
            JsonToken jsonToken;
            if ((jsonToken = this.delegate.nextToken()) == null) {
                this._currToken = jsonToken;
                return jsonToken;
            }
            int n = jsonToken.id();
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                object = this._itemFilter;
                                if (object == TokenFilter.INCLUDE_ALL) {
                                    this._currToken = jsonToken;
                                    return jsonToken;
                                }
                                if (object == null || (object = this._headContext.checkValue((TokenFilter)object)) != TokenFilter.INCLUDE_ALL && (object == null || !((TokenFilter)object).includeValue(this.delegate)) || !this._verifyAllowedMatches()) continue;
                                this._currToken = jsonToken;
                                return jsonToken;
                            }
                            object2 = this.delegate.getCurrentName();
                            object = this._headContext.setFieldName((String)object2);
                            if (object == TokenFilter.INCLUDE_ALL) {
                                this._itemFilter = object;
                                this._currToken = jsonToken;
                                return jsonToken;
                            }
                            if (object == null) {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                                continue;
                            }
                            if ((object = ((TokenFilter)object).includeProperty((String)object2)) == null) {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                                continue;
                            }
                            this._itemFilter = object;
                            if (object == TokenFilter.INCLUDE_ALL) {
                                if (!this._verifyAllowedMatches() || !this._includePath) continue;
                                this._currToken = jsonToken;
                                return jsonToken;
                            }
                            if (!this._includePath || (object = this._nextTokenWithBuffering(this._headContext)) == null) continue;
                            this._currToken = object;
                            return object;
                        }
                    } else {
                        object = this._itemFilter;
                        if (object == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, true);
                            this._currToken = jsonToken;
                            return jsonToken;
                        }
                        if (object == null) {
                            this.delegate.skipChildren();
                            continue;
                        }
                        object2 = this._headContext.checkValue((TokenFilter)object);
                        if (object2 == null) {
                            this.delegate.skipChildren();
                            continue;
                        }
                        object = object2;
                        if (object2 != TokenFilter.INCLUDE_ALL) {
                            object = ((TokenFilter)object2).filterStartArray();
                        }
                        this._itemFilter = object;
                        if (object == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, true);
                            this._currToken = jsonToken;
                            return jsonToken;
                        }
                        this._headContext = object = this._headContext.createChildArrayContext((TokenFilter)object, false);
                        if (!this._includePath || (object = this._nextTokenWithBuffering((TokenFilterContext)object)) == null) continue;
                        this._currToken = object;
                        return object;
                    }
                }
                boolean bl = this._headContext.isStartHandled();
                object = this._headContext.getFilter();
                if (object != null && object != TokenFilter.INCLUDE_ALL) {
                    ((TokenFilter)object).filterFinishArray();
                }
                this._headContext = object = this._headContext.getParent();
                this._itemFilter = ((TokenFilterContext)object).getFilter();
                if (!bl) continue;
                this._currToken = jsonToken;
                return jsonToken;
            }
            object = this._itemFilter;
            if (object == TokenFilter.INCLUDE_ALL) {
                this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, true);
                this._currToken = jsonToken;
                return jsonToken;
            }
            if (object == null) {
                this.delegate.skipChildren();
                continue;
            }
            object2 = this._headContext.checkValue((TokenFilter)object);
            if (object2 == null) {
                this.delegate.skipChildren();
                continue;
            }
            object = object2;
            if (object2 != TokenFilter.INCLUDE_ALL) {
                object = ((TokenFilter)object2).filterStartObject();
            }
            this._itemFilter = object;
            if (object == TokenFilter.INCLUDE_ALL) {
                this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, true);
                this._currToken = jsonToken;
                return jsonToken;
            }
            this._headContext = object = this._headContext.createChildObjectContext((TokenFilter)object, false);
            if (this._includePath && (object = this._nextTokenWithBuffering((TokenFilterContext)object)) != null) break;
        } while (true);
        this._currToken = object;
        return object;
    }

    protected final JsonToken _nextTokenWithBuffering(TokenFilterContext tokenFilterContext) throws IOException {
        Object object;
        while ((object = this.delegate.nextToken()) != null) {
            Object object2;
            int n = ((JsonToken)((Object)object)).id();
            int n2 = 0;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                object = this._itemFilter;
                                if (object == TokenFilter.INCLUDE_ALL) {
                                    return this._nextBuffered(tokenFilterContext);
                                }
                                if (object == null || (object = this._headContext.checkValue((TokenFilter)object)) != TokenFilter.INCLUDE_ALL && (object == null || !((TokenFilter)object).includeValue(this.delegate)) || !this._verifyAllowedMatches()) continue;
                                return this._nextBuffered(tokenFilterContext);
                            }
                            object = this.delegate.getCurrentName();
                            object2 = this._headContext.setFieldName((String)object);
                            if (object2 == TokenFilter.INCLUDE_ALL) {
                                this._itemFilter = object2;
                                return this._nextBuffered(tokenFilterContext);
                            }
                            if (object2 == null) {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                                continue;
                            }
                            if ((object2 = ((TokenFilter)object2).includeProperty((String)object)) == null) {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                                continue;
                            }
                            this._itemFilter = object2;
                            if (object2 != TokenFilter.INCLUDE_ALL) continue;
                            if (this._verifyAllowedMatches()) {
                                return this._nextBuffered(tokenFilterContext);
                            }
                            this._itemFilter = this._headContext.setFieldName((String)object);
                            continue;
                        }
                    } else {
                        object2 = this._headContext.checkValue(this._itemFilter);
                        if (object2 == null) {
                            this.delegate.skipChildren();
                            continue;
                        }
                        object = object2;
                        if (object2 != TokenFilter.INCLUDE_ALL) {
                            object = ((TokenFilter)object2).filterStartArray();
                        }
                        this._itemFilter = object;
                        if (object == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, true);
                            return this._nextBuffered(tokenFilterContext);
                        }
                        this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, false);
                        continue;
                    }
                }
                if ((object2 = this._headContext.getFilter()) != null && object2 != TokenFilter.INCLUDE_ALL) {
                    ((TokenFilter)object2).filterFinishArray();
                }
                boolean bl = this._headContext == tokenFilterContext;
                n = n2;
                if (bl) {
                    n = n2;
                    if (this._headContext.isStartHandled()) {
                        n = 1;
                    }
                }
                this._headContext = object2 = this._headContext.getParent();
                this._itemFilter = ((TokenFilterContext)object2).getFilter();
                if (n == 0) continue;
                return object;
            }
            object2 = this._itemFilter;
            if (object2 == TokenFilter.INCLUDE_ALL) {
                this._headContext = this._headContext.createChildObjectContext((TokenFilter)object2, true);
                return object;
            }
            if (object2 == null) {
                this.delegate.skipChildren();
                continue;
            }
            if ((object2 = this._headContext.checkValue((TokenFilter)object2)) == null) {
                this.delegate.skipChildren();
                continue;
            }
            object = object2;
            if (object2 != TokenFilter.INCLUDE_ALL) {
                object = ((TokenFilter)object2).filterStartObject();
            }
            this._itemFilter = object;
            if (object == TokenFilter.INCLUDE_ALL) {
                this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, true);
                return this._nextBuffered(tokenFilterContext);
            }
            this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, false);
        }
        return object;
    }

    @Override
    public void clearCurrentToken() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == null) return;
        this._lastClearedToken = jsonToken;
        this._currToken = null;
    }

    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }

    @Override
    public final int currentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) return jsonToken.id();
        return 0;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        return this.delegate.getBinaryValue(base64Variant);
    }

    @Override
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }

    @Override
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }

    @Override
    public String getCurrentName() throws IOException {
        Object object = this._filterContext();
        if (this._currToken != JsonToken.START_OBJECT) {
            if (this._currToken != JsonToken.START_ARRAY) return ((JsonStreamContext)object).getCurrentName();
        }
        if ((object = ((JsonStreamContext)object).getParent()) != null) return ((JsonStreamContext)object).getCurrentName();
        return null;
    }

    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    @Override
    public final int getCurrentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) return jsonToken.id();
        return 0;
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }

    @Override
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }

    public TokenFilter getFilter() {
        return this.rootFilter;
    }

    @Override
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }

    @Override
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }

    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    @Override
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }

    public int getMatchCount() {
        return this._matchCount;
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }

    @Override
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return this._filterContext();
    }

    @Override
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }

    @Override
    public String getText() throws IOException {
        return this.delegate.getText();
    }

    @Override
    public char[] getTextCharacters() throws IOException {
        return this.delegate.getTextCharacters();
    }

    @Override
    public int getTextLength() throws IOException {
        return this.delegate.getTextLength();
    }

    @Override
    public int getTextOffset() throws IOException {
        return this.delegate.getTextOffset();
    }

    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }

    @Override
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }

    @Override
    public boolean getValueAsBoolean(boolean bl) throws IOException {
        return this.delegate.getValueAsBoolean(bl);
    }

    @Override
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }

    @Override
    public double getValueAsDouble(double d) throws IOException {
        return this.delegate.getValueAsDouble(d);
    }

    @Override
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }

    @Override
    public int getValueAsInt(int n) throws IOException {
        return this.delegate.getValueAsInt(n);
    }

    @Override
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }

    @Override
    public long getValueAsLong(long l) throws IOException {
        return this.delegate.getValueAsLong(l);
    }

    @Override
    public String getValueAsString() throws IOException {
        return this.delegate.getValueAsString();
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        return this.delegate.getValueAsString(string2);
    }

    @Override
    public boolean hasCurrentToken() {
        if (this._currToken == null) return false;
        return true;
    }

    @Override
    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }

    @Override
    public final boolean hasToken(JsonToken jsonToken) {
        if (this._currToken != jsonToken) return false;
        return true;
    }

    @Override
    public boolean hasTokenId(int n) {
        JsonToken jsonToken = this._currToken;
        boolean bl = true;
        boolean bl2 = true;
        if (jsonToken == null) {
            if (n != 0) return false;
            return bl2;
        }
        if (jsonToken.id() != n) return false;
        return bl;
    }

    @Override
    public boolean isExpectedStartArrayToken() {
        if (this._currToken != JsonToken.START_ARRAY) return false;
        return true;
    }

    @Override
    public boolean isExpectedStartObjectToken() {
        if (this._currToken != JsonToken.START_OBJECT) return false;
        return true;
    }

    @Override
    public JsonToken nextToken() throws IOException {
        Object object;
        Object object2;
        if (!this._allowMultipleMatches && (object = this._currToken) != null && this._exposedContext == null && object.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._currToken = null;
            return null;
        }
        object = this._exposedContext;
        if (object != null) {
            do {
                if ((object2 = ((TokenFilterContext)object).nextTokenToRead()) != null) {
                    this._currToken = object2;
                    return object2;
                }
                object2 = this._headContext;
                if (object == object2) {
                    this._exposedContext = null;
                    if (!((JsonStreamContext)object).inArray()) break;
                    this._currToken = object = this.delegate.getCurrentToken();
                    return object;
                }
                object = ((TokenFilterContext)object2).findChildOf((TokenFilterContext)object);
                this._exposedContext = object;
                if (object == null) throw this._constructError("Unexpected problem: chain of filtered context broken");
            } while (true);
        }
        if ((object2 = this.delegate.nextToken()) == null) {
            this._currToken = object2;
            return object2;
        }
        int n = ((JsonToken)((Object)object2)).id();
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            object = this._itemFilter;
                            if (object == TokenFilter.INCLUDE_ALL) {
                                this._currToken = object2;
                                return object2;
                            }
                            if (object == null) return this._nextToken2();
                            if ((object = this._headContext.checkValue((TokenFilter)object)) != TokenFilter.INCLUDE_ALL) {
                                if (object == null) return this._nextToken2();
                                if (!((TokenFilter)object).includeValue(this.delegate)) return this._nextToken2();
                            }
                            if (!this._verifyAllowedMatches()) return this._nextToken2();
                            this._currToken = object2;
                            return object2;
                        }
                        String string2 = this.delegate.getCurrentName();
                        object = this._headContext.setFieldName(string2);
                        if (object == TokenFilter.INCLUDE_ALL) {
                            this._itemFilter = object;
                            object = object2;
                            if (!this._includePath) {
                                object = object2;
                                if (this._includeImmediateParent) {
                                    object = object2;
                                    if (!this._headContext.isStartHandled()) {
                                        object = this._headContext.nextTokenToRead();
                                        this._exposedContext = this._headContext;
                                    }
                                }
                            }
                            this._currToken = object;
                            return object;
                        }
                        if (object == null) {
                            this.delegate.nextToken();
                            this.delegate.skipChildren();
                            return this._nextToken2();
                        }
                        if ((object = ((TokenFilter)object).includeProperty(string2)) == null) {
                            this.delegate.nextToken();
                            this.delegate.skipChildren();
                            return this._nextToken2();
                        }
                        this._itemFilter = object;
                        if (object == TokenFilter.INCLUDE_ALL) {
                            if (this._verifyAllowedMatches()) {
                                if (this._includePath) {
                                    this._currToken = object2;
                                    return object2;
                                }
                            } else {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                            }
                        }
                        if (!this._includePath) return this._nextToken2();
                        object = this._nextTokenWithBuffering(this._headContext);
                        if (object == null) return this._nextToken2();
                        this._currToken = object;
                        return object;
                    }
                } else {
                    object = this._itemFilter;
                    if (object == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, true);
                        this._currToken = object2;
                        return object2;
                    }
                    if (object == null) {
                        this.delegate.skipChildren();
                        return this._nextToken2();
                    }
                    TokenFilter tokenFilter = this._headContext.checkValue((TokenFilter)object);
                    if (tokenFilter == null) {
                        this.delegate.skipChildren();
                        return this._nextToken2();
                    }
                    object = tokenFilter;
                    if (tokenFilter != TokenFilter.INCLUDE_ALL) {
                        object = tokenFilter.filterStartArray();
                    }
                    this._itemFilter = object;
                    if (object == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext((TokenFilter)object, true);
                        this._currToken = object2;
                        return object2;
                    }
                    object = this._headContext.createChildArrayContext((TokenFilter)object, false);
                    this._headContext = object;
                    if (!this._includePath) return this._nextToken2();
                    if ((object = this._nextTokenWithBuffering((TokenFilterContext)object)) == null) return this._nextToken2();
                    this._currToken = object;
                    return object;
                }
            }
            boolean bl = this._headContext.isStartHandled();
            object = this._headContext.getFilter();
            if (object != null && object != TokenFilter.INCLUDE_ALL) {
                ((TokenFilter)object).filterFinishArray();
            }
            object = this._headContext.getParent();
            this._headContext = object;
            this._itemFilter = ((TokenFilterContext)object).getFilter();
            if (!bl) return this._nextToken2();
            this._currToken = object2;
            return object2;
        }
        object = this._itemFilter;
        if (object == TokenFilter.INCLUDE_ALL) {
            this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, true);
            this._currToken = object2;
            return object2;
        }
        if (object == null) {
            this.delegate.skipChildren();
            return this._nextToken2();
        }
        TokenFilter tokenFilter = this._headContext.checkValue((TokenFilter)object);
        if (tokenFilter == null) {
            this.delegate.skipChildren();
            return this._nextToken2();
        }
        object = tokenFilter;
        if (tokenFilter != TokenFilter.INCLUDE_ALL) {
            object = tokenFilter.filterStartObject();
        }
        this._itemFilter = object;
        if (object == TokenFilter.INCLUDE_ALL) {
            this._headContext = this._headContext.createChildObjectContext((TokenFilter)object, true);
            this._currToken = object2;
            return object2;
        }
        object = this._headContext.createChildObjectContext((TokenFilter)object, false);
        this._headContext = object;
        if (!this._includePath) return this._nextToken2();
        if ((object = this._nextTokenWithBuffering((TokenFilterContext)object)) == null) return this._nextToken2();
        this._currToken = object;
        return object;
    }

    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = this.nextToken();
        if (jsonToken != JsonToken.FIELD_NAME) return jsonToken2;
        return this.nextToken();
    }

    @Override
    public void overrideCurrentName(String string2) {
        throw new UnsupportedOperationException("Can not currently override name during filtering read");
    }

    @Override
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream2) throws IOException {
        return this.delegate.readBinaryValue(base64Variant, outputStream2);
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int n = 1;
        JsonToken jsonToken;
        while ((jsonToken = this.nextToken()) != null) {
            int n2;
            if (jsonToken.isStructStart()) {
                ++n;
                continue;
            }
            if (!jsonToken.isStructEnd()) continue;
            n = n2 = n - 1;
            if (n2 == 0) return this;
        }
        return this;
    }
}

