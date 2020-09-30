/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.io.CharTypes;

public abstract class JsonStreamContext {
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected static final int TYPE_ROOT = 0;
    protected int _index;
    protected int _type;

    protected JsonStreamContext() {
    }

    protected JsonStreamContext(int n, int n2) {
        this._type = n;
        this._index = n2;
    }

    protected JsonStreamContext(JsonStreamContext jsonStreamContext) {
        this._type = jsonStreamContext._type;
        this._index = jsonStreamContext._index;
    }

    public final int getCurrentIndex() {
        int n;
        int n2 = n = this._index;
        if (n >= 0) return n2;
        return 0;
    }

    public abstract String getCurrentName();

    public Object getCurrentValue() {
        return null;
    }

    public final int getEntryCount() {
        return this._index + 1;
    }

    public abstract JsonStreamContext getParent();

    public JsonLocation getStartLocation(Object object) {
        return JsonLocation.NA;
    }

    @Deprecated
    public final String getTypeDesc() {
        int n = this._type;
        if (n == 0) return "ROOT";
        if (n == 1) return "ARRAY";
        if (n == 2) return "OBJECT";
        return "?";
    }

    public boolean hasCurrentIndex() {
        if (this._index < 0) return false;
        return true;
    }

    public boolean hasCurrentName() {
        if (this.getCurrentName() == null) return false;
        return true;
    }

    public boolean hasPathSegment() {
        int n = this._type;
        if (n == 2) {
            return this.hasCurrentName();
        }
        if (n != 1) return false;
        return this.hasCurrentIndex();
    }

    public final boolean inArray() {
        int n = this._type;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public final boolean inObject() {
        if (this._type != 2) return false;
        return true;
    }

    public final boolean inRoot() {
        if (this._type != 0) return false;
        return true;
    }

    public JsonPointer pathAsPointer() {
        return JsonPointer.forPath(this, false);
    }

    public JsonPointer pathAsPointer(boolean bl) {
        return JsonPointer.forPath(this, bl);
    }

    public void setCurrentValue(Object object) {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        int n = this._type;
        if (n == 0) {
            stringBuilder.append("/");
            return stringBuilder.toString();
        }
        if (n == 1) {
            stringBuilder.append('[');
            stringBuilder.append(this.getCurrentIndex());
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
        stringBuilder.append('{');
        String string2 = this.getCurrentName();
        if (string2 != null) {
            stringBuilder.append('\"');
            CharTypes.appendQuoted(stringBuilder, string2);
            stringBuilder.append('\"');
        } else {
            stringBuilder.append('?');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public String typeDesc() {
        int n = this._type;
        if (n == 0) return "root";
        if (n == 1) return "Array";
        if (n == 2) return "Object";
        return "?";
    }
}

