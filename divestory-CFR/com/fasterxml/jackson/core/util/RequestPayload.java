/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import java.io.IOException;
import java.io.Serializable;

public class RequestPayload
implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String _charset;
    protected byte[] _payloadAsBytes;
    protected CharSequence _payloadAsText;

    public RequestPayload(CharSequence charSequence) {
        if (charSequence == null) throw new IllegalArgumentException();
        this._payloadAsText = charSequence;
    }

    public RequestPayload(byte[] object, String string2) {
        block3 : {
            block2 : {
                if (object == null) throw new IllegalArgumentException();
                this._payloadAsBytes = object;
                if (string2 == null) break block2;
                object = string2;
                if (!string2.isEmpty()) break block3;
            }
            object = "UTF-8";
        }
        this._charset = object;
    }

    public Object getRawPayload() {
        byte[] arrby = this._payloadAsBytes;
        if (arrby == null) return this._payloadAsText;
        return arrby;
    }

    public String toString() {
        if (this._payloadAsBytes == null) return this._payloadAsText.toString();
        try {
            return new String(this._payloadAsBytes, this._charset);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

