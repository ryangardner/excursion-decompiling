/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.DupDetector;

public class JsonWriteContext
extends JsonStreamContext {
    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS = 0;
    protected JsonWriteContext _child;
    protected String _currentName;
    protected Object _currentValue;
    protected DupDetector _dups;
    protected boolean _gotName;
    protected final JsonWriteContext _parent;

    protected JsonWriteContext(int n, JsonWriteContext jsonWriteContext, DupDetector dupDetector) {
        this._type = n;
        this._parent = jsonWriteContext;
        this._dups = dupDetector;
        this._index = -1;
    }

    protected JsonWriteContext(int n, JsonWriteContext jsonWriteContext, DupDetector dupDetector, Object object) {
        this._type = n;
        this._parent = jsonWriteContext;
        this._dups = dupDetector;
        this._index = -1;
        this._currentValue = object;
    }

    private final void _checkDup(DupDetector object, String string2) throws JsonProcessingException {
        if (!((DupDetector)object).isDup(string2)) return;
        object = ((DupDetector)object).getSource();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Duplicate field '");
        stringBuilder.append(string2);
        stringBuilder.append("'");
        string2 = stringBuilder.toString();
        if (object instanceof JsonGenerator) {
            object = (JsonGenerator)object;
            throw new JsonGenerationException(string2, (JsonGenerator)object);
        }
        object = null;
        throw new JsonGenerationException(string2, (JsonGenerator)object);
    }

    @Deprecated
    public static JsonWriteContext createRootContext() {
        return JsonWriteContext.createRootContext(null);
    }

    public static JsonWriteContext createRootContext(DupDetector dupDetector) {
        return new JsonWriteContext(0, null, dupDetector);
    }

    public JsonWriteContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }

    public JsonWriteContext createChildArrayContext() {
        Object object = this._child;
        if (object != null) return ((JsonWriteContext)object).reset(1);
        object = this._dups;
        object = object == null ? null : ((DupDetector)object).child();
        this._child = object = new JsonWriteContext(1, this, (DupDetector)object);
        return object;
    }

    public JsonWriteContext createChildArrayContext(Object object) {
        Object object2 = this._child;
        if (object2 != null) return ((JsonWriteContext)object2).reset(1, object);
        object2 = this._dups;
        object2 = object2 == null ? null : ((DupDetector)object2).child();
        this._child = object = new JsonWriteContext(1, this, (DupDetector)object2, object);
        return object;
    }

    public JsonWriteContext createChildObjectContext() {
        Object object = this._child;
        if (object != null) return ((JsonWriteContext)object).reset(2);
        object = this._dups;
        object = object == null ? null : ((DupDetector)object).child();
        this._child = object = new JsonWriteContext(2, this, (DupDetector)object);
        return object;
    }

    public JsonWriteContext createChildObjectContext(Object object) {
        Object object2 = this._child;
        if (object2 != null) return ((JsonWriteContext)object2).reset(2, object);
        object2 = this._dups;
        object2 = object2 == null ? null : ((DupDetector)object2).child();
        this._child = object = new JsonWriteContext(2, this, (DupDetector)object2, object);
        return object;
    }

    @Override
    public final String getCurrentName() {
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
    public final JsonWriteContext getParent() {
        return this._parent;
    }

    @Override
    public boolean hasCurrentName() {
        if (this._currentName == null) return false;
        return true;
    }

    protected JsonWriteContext reset(int n) {
        this._type = n;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        this._currentValue = null;
        DupDetector dupDetector = this._dups;
        if (dupDetector == null) return this;
        dupDetector.reset();
        return this;
    }

    protected JsonWriteContext reset(int n, Object object) {
        this._type = n;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        this._currentValue = object;
        object = this._dups;
        if (object == null) return this;
        ((DupDetector)object).reset();
        return this;
    }

    @Override
    public void setCurrentValue(Object object) {
        this._currentValue = object;
    }

    public JsonWriteContext withDupDetector(DupDetector dupDetector) {
        this._dups = dupDetector;
        return this;
    }

    public int writeFieldName(String string2) throws JsonProcessingException {
        if (this._type != 2) return 4;
        if (this._gotName) {
            return 4;
        }
        int n = 1;
        this._gotName = true;
        this._currentName = string2;
        DupDetector dupDetector = this._dups;
        if (dupDetector != null) {
            this._checkDup(dupDetector, string2);
        }
        if (this._index >= 0) return n;
        return 0;
    }

    public int writeValue() {
        int n = this._type;
        int n2 = 0;
        int n3 = 0;
        if (n == 2) {
            if (!this._gotName) {
                return 5;
            }
            this._gotName = false;
            ++this._index;
            return 2;
        }
        if (this._type == 1) {
            if ((n2 = this._index++) >= 0) return 1;
            return n3;
        }
        ++this._index;
        if (this._index != 0) return 3;
        return n2;
    }
}

