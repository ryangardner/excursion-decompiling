/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import java.io.Serializable;
import java.nio.charset.Charset;

public class JsonLocation
implements Serializable {
    public static final int MAX_CONTENT_SNIPPET = 500;
    public static final JsonLocation NA = new JsonLocation(null, -1L, -1L, -1, -1);
    private static final long serialVersionUID = 1L;
    protected final int _columnNr;
    protected final int _lineNr;
    final transient Object _sourceRef;
    protected final long _totalBytes;
    protected final long _totalChars;

    public JsonLocation(Object object, long l, int n, int n2) {
        this(object, -1L, l, n, n2);
    }

    public JsonLocation(Object object, long l, long l2, int n, int n2) {
        this._sourceRef = object;
        this._totalBytes = l;
        this._totalChars = l2;
        this._lineNr = n;
        this._columnNr = n2;
    }

    private int _append(StringBuilder stringBuilder, String string2) {
        stringBuilder.append('\"');
        stringBuilder.append(string2);
        stringBuilder.append('\"');
        return string2.length();
    }

    /*
     * Unable to fully structure code
     */
    protected StringBuilder _appendSourceDesc(StringBuilder var1_1) {
        block11 : {
            var2_2 = this._sourceRef;
            if (var2_2 == null) {
                var1_1.append("UNKNOWN");
                return var1_1;
            }
            if (var2_2 instanceof Class) {
                var3_3 = (Class)var2_2;
            } else {
                var3_4 = var2_2.getClass();
            }
            var4_15 = var3_5.getName();
            if (var4_15.startsWith("java.")) {
                var3_6 = var3_5.getSimpleName();
            } else if (var2_2 instanceof byte[]) {
                var3_7 = "byte[]";
            } else {
                var3_8 = var4_15;
                if (var2_2 instanceof char[]) {
                    var3_9 = "char[]";
                }
            }
            var1_1.append('(');
            var1_1.append((String)var3_10);
            var1_1.append(')');
            var5_16 = var2_2 instanceof CharSequence;
            var6_17 = 0;
            var3_11 = " chars";
            if (!var5_16) break block11;
            var4_15 = (CharSequence)var2_2;
            var6_17 = var4_15.length();
            var7_18 = this._append(var1_1, var4_15.subSequence(0, Math.min(var6_17, 500)).toString());
            ** GOTO lbl39
        }
        if (var2_2 instanceof char[]) {
            var4_15 = (char[])var2_2;
            var6_17 = ((CharSequence)var4_15).length;
            var7_18 = this._append(var1_1, new String((char[])var4_15, 0, Math.min(var6_17, 500)));
lbl39: // 2 sources:
            var6_17 -= var7_18;
        } else if (var2_2 instanceof byte[]) {
            var3_12 = (byte[])var2_2;
            var6_17 = Math.min(var3_12.length, 500);
            this._append(var1_1, new String(var3_12, 0, var6_17, Charset.forName("UTF-8")));
            var6_17 = var3_12.length - var6_17;
            var3_13 = " bytes";
        }
        if (var6_17 <= 0) return var1_1;
        var1_1.append("[truncated ");
        var1_1.append(var6_17);
        var1_1.append((String)var3_14);
        var1_1.append(']');
        return var1_1;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof JsonLocation)) {
            return false;
        }
        JsonLocation jsonLocation = (JsonLocation)object;
        object = this._sourceRef;
        if (object == null ? jsonLocation._sourceRef != null : !object.equals(jsonLocation._sourceRef)) {
            return false;
        }
        if (this._lineNr != jsonLocation._lineNr) return false;
        if (this._columnNr != jsonLocation._columnNr) return false;
        if (this._totalChars != jsonLocation._totalChars) return false;
        if (this.getByteOffset() != jsonLocation.getByteOffset()) return false;
        return bl;
    }

    public long getByteOffset() {
        return this._totalBytes;
    }

    public long getCharOffset() {
        return this._totalChars;
    }

    public int getColumnNr() {
        return this._columnNr;
    }

    public int getLineNr() {
        return this._lineNr;
    }

    public Object getSourceRef() {
        return this._sourceRef;
    }

    public int hashCode() {
        int n;
        Object object = this._sourceRef;
        if (object == null) {
            n = 1;
            return ((n ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
        }
        n = object.hashCode();
        return ((n ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
    }

    public String sourceDescription() {
        return this._appendSourceDesc(new StringBuilder(100)).toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("[Source: ");
        this._appendSourceDesc(stringBuilder);
        stringBuilder.append("; line: ");
        stringBuilder.append(this._lineNr);
        stringBuilder.append(", column: ");
        stringBuilder.append(this._columnNr);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

