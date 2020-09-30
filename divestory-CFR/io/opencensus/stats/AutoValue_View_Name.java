/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.View;

final class AutoValue_View_Name
extends View.Name {
    private final String asString;

    AutoValue_View_Name(String string2) {
        if (string2 == null) throw new NullPointerException("Null asString");
        this.asString = string2;
    }

    @Override
    public String asString() {
        return this.asString;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof View.Name)) return false;
        object = (View.Name)object;
        return this.asString.equals(((View.Name)object).asString());
    }

    public int hashCode() {
        return this.asString.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name{asString=");
        stringBuilder.append(this.asString);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

