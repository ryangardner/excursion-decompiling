/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.Tracestate;
import java.util.List;

final class AutoValue_Tracestate
extends Tracestate {
    private final List<Tracestate.Entry> entries;

    AutoValue_Tracestate(List<Tracestate.Entry> list) {
        if (list == null) throw new NullPointerException("Null entries");
        this.entries = list;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Tracestate)) return false;
        object = (Tracestate)object;
        return this.entries.equals(((Tracestate)object).getEntries());
    }

    @Override
    public List<Tracestate.Entry> getEntries() {
        return this.entries;
    }

    public int hashCode() {
        return this.entries.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tracestate{entries=");
        stringBuilder.append(this.entries);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

