/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.DupDetector;

public final class JsonReadContext
extends JsonStreamContext {
    protected JsonReadContext _child;
    protected int _columnNr;
    protected String _currentName;
    protected Object _currentValue;
    protected DupDetector _dups;
    protected int _lineNr;
    protected final JsonReadContext _parent;

    public JsonReadContext(JsonReadContext jsonReadContext, DupDetector dupDetector, int n, int n2, int n3) {
        this._parent = jsonReadContext;
        this._dups = dupDetector;
        this._type = n;
        this._lineNr = n2;
        this._columnNr = n3;
        this._index = -1;
    }

    private void _checkDup(DupDetector object, String string2) throws JsonProcessingException {
        if (!((DupDetector)object).isDup(string2)) return;
        object = (object = ((DupDetector)object).getSource()) instanceof JsonParser ? (JsonParser)object : null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Duplicate field '");
        stringBuilder.append(string2);
        stringBuilder.append("'");
        throw new JsonParseException((JsonParser)object, stringBuilder.toString());
    }

    public static JsonReadContext createRootContext(int n, int n2, DupDetector dupDetector) {
        return new JsonReadContext(null, dupDetector, 0, n, n2);
    }

    public static JsonReadContext createRootContext(DupDetector dupDetector) {
        return new JsonReadContext(null, dupDetector, 0, 1, 0);
    }

    public JsonReadContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }

    public JsonReadContext createChildArrayContext(int n, int n2) {
        Object object = this._child;
        if (object != null) {
            ((JsonReadContext)object).reset(1, n, n2);
            return object;
        }
        object = this._dups;
        object = object == null ? null : ((DupDetector)object).child();
        this._child = object = new JsonReadContext(this, (DupDetector)object, 1, n, n2);
        return object;
    }

    public JsonReadContext createChildObjectContext(int n, int n2) {
        Object object = this._child;
        if (object != null) {
            ((JsonReadContext)object).reset(2, n, n2);
            return object;
        }
        object = this._dups;
        object = object == null ? null : ((DupDetector)object).child();
        this._child = object = new JsonReadContext(this, (DupDetector)object, 2, n, n2);
        return object;
    }

    public boolean expectComma() {
        int n = this._index;
        boolean bl = true;
        this._index = ++n;
        if (this._type == 0) return false;
        if (n <= 0) return false;
        return bl;
    }

    @Override
    public String getCurrentName() {
        return this._currentName;
    }

    @Override
    public Object getCurrentValue() {
        return this._currentValue;
    }

    public DupDetector getDupDetector() {
        return this._dups;
    }

    @Override
    public JsonReadContext getParent() {
        return this._parent;
    }

    @Override
    public JsonLocation getStartLocation(Object object) {
        return new JsonLocation(object, -1L, this._lineNr, this._columnNr);
    }

    @Override
    public boolean hasCurrentName() {
        if (this._currentName == null) return false;
        return true;
    }

    protected void reset(int n, int n2, int n3) {
        this._type = n;
        this._index = -1;
        this._lineNr = n2;
        this._columnNr = n3;
        this._currentName = null;
        this._currentValue = null;
        DupDetector dupDetector = this._dups;
        if (dupDetector == null) return;
        dupDetector.reset();
    }

    public void setCurrentName(String string2) throws JsonProcessingException {
        this._currentName = string2;
        DupDetector dupDetector = this._dups;
        if (dupDetector == null) return;
        this._checkDup(dupDetector, string2);
    }

    @Override
    public void setCurrentValue(Object object) {
        this._currentValue = object;
    }

    public JsonReadContext withDupDetector(DupDetector dupDetector) {
        this._dups = dupDetector;
        return this;
    }
}

