/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.Tracestate;

final class AutoValue_Tracestate_Entry
extends Tracestate.Entry {
    private final String key;
    private final String value;

    AutoValue_Tracestate_Entry(String string2, String string3) {
        if (string2 == null) throw new NullPointerException("Null key");
        this.key = string2;
        if (string3 == null) throw new NullPointerException("Null value");
        this.value = string3;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Tracestate.Entry)) return false;
        if (!this.key.equals(((Tracestate.Entry)(object = (Tracestate.Entry)object)).getKey())) return false;
        if (!this.value.equals(((Tracestate.Entry)object).getValue())) return false;
        return bl;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return (this.key.hashCode() ^ 1000003) * 1000003 ^ this.value.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entry{key=");
        stringBuilder.append(this.key);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

