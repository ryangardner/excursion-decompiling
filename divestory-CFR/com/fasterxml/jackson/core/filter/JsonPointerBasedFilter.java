/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.filter.TokenFilter;

public class JsonPointerBasedFilter
extends TokenFilter {
    protected final JsonPointer _pathToMatch;

    public JsonPointerBasedFilter(JsonPointer jsonPointer) {
        this._pathToMatch = jsonPointer;
    }

    public JsonPointerBasedFilter(String string2) {
        this(JsonPointer.compile(string2));
    }

    @Override
    protected boolean _includeScalar() {
        return this._pathToMatch.matches();
    }

    @Override
    public TokenFilter filterStartArray() {
        return this;
    }

    @Override
    public TokenFilter filterStartObject() {
        return this;
    }

    @Override
    public TokenFilter includeElement(int n) {
        JsonPointer jsonPointer = this._pathToMatch.matchElement(n);
        if (jsonPointer == null) {
            return null;
        }
        if (!jsonPointer.matches()) return new JsonPointerBasedFilter(jsonPointer);
        return TokenFilter.INCLUDE_ALL;
    }

    @Override
    public TokenFilter includeProperty(String object) {
        if ((object = this._pathToMatch.matchProperty((String)object)) == null) {
            return null;
        }
        if (!((JsonPointer)object).matches()) return new JsonPointerBasedFilter((JsonPointer)object);
        return TokenFilter.INCLUDE_ALL;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[JsonPointerFilter at: ");
        stringBuilder.append(this._pathToMatch);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

