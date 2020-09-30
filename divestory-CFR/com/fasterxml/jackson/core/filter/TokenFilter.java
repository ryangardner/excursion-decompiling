/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TokenFilter {
    public static final TokenFilter INCLUDE_ALL = new TokenFilter();

    protected TokenFilter() {
    }

    protected boolean _includeScalar() {
        return true;
    }

    public void filterFinishArray() {
    }

    public void filterFinishObject() {
    }

    public TokenFilter filterStartArray() {
        return this;
    }

    public TokenFilter filterStartObject() {
        return this;
    }

    public boolean includeBinary() {
        return this._includeScalar();
    }

    public boolean includeBoolean(boolean bl) {
        return this._includeScalar();
    }

    public TokenFilter includeElement(int n) {
        return this;
    }

    public boolean includeEmbeddedValue(Object object) {
        return this._includeScalar();
    }

    public boolean includeNull() {
        return this._includeScalar();
    }

    public boolean includeNumber(double d) {
        return this._includeScalar();
    }

    public boolean includeNumber(float f) {
        return this._includeScalar();
    }

    public boolean includeNumber(int n) {
        return this._includeScalar();
    }

    public boolean includeNumber(long l) {
        return this._includeScalar();
    }

    public boolean includeNumber(BigDecimal bigDecimal) {
        return this._includeScalar();
    }

    public boolean includeNumber(BigInteger bigInteger) {
        return this._includeScalar();
    }

    public TokenFilter includeProperty(String string2) {
        return this;
    }

    public boolean includeRawValue() {
        return this._includeScalar();
    }

    public TokenFilter includeRootValue(int n) {
        return this;
    }

    public boolean includeString(String string2) {
        return this._includeScalar();
    }

    public boolean includeValue(JsonParser jsonParser) throws IOException {
        return this._includeScalar();
    }

    public String toString() {
        if (this != INCLUDE_ALL) return super.toString();
        return "TokenFilter.INCLUDE_ALL";
    }
}

