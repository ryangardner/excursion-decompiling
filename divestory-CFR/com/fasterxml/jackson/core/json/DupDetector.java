/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.util.HashSet;

public class DupDetector {
    protected String _firstName;
    protected String _secondName;
    protected HashSet<String> _seen;
    protected final Object _source;

    private DupDetector(Object object) {
        this._source = object;
    }

    public static DupDetector rootDetector(JsonGenerator jsonGenerator) {
        return new DupDetector(jsonGenerator);
    }

    public static DupDetector rootDetector(JsonParser jsonParser) {
        return new DupDetector(jsonParser);
    }

    public DupDetector child() {
        return new DupDetector(this._source);
    }

    public JsonLocation findLocation() {
        Object object = this._source;
        if (!(object instanceof JsonParser)) return null;
        return ((JsonParser)object).getCurrentLocation();
    }

    public Object getSource() {
        return this._source;
    }

    public boolean isDup(String string2) throws JsonParseException {
        Object object = this._firstName;
        if (object == null) {
            this._firstName = string2;
            return false;
        }
        if (string2.equals(object)) {
            return true;
        }
        object = this._secondName;
        if (object == null) {
            this._secondName = string2;
            return false;
        }
        if (string2.equals(object)) {
            return true;
        }
        if (this._seen != null) return this._seen.add(string2) ^ true;
        this._seen = object = new HashSet(16);
        ((HashSet)object).add(this._firstName);
        this._seen.add(this._secondName);
        return this._seen.add(string2) ^ true;
    }

    public void reset() {
        this._firstName = null;
        this._secondName = null;
        this._seen = null;
    }
}

