/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializedString
implements SerializableString,
Serializable {
    private static final JsonStringEncoder JSON_ENCODER = JsonStringEncoder.getInstance();
    private static final long serialVersionUID = 1L;
    protected transient String _jdkSerializeValue;
    protected char[] _quotedChars;
    protected byte[] _quotedUTF8Ref;
    protected byte[] _unquotedUTF8Ref;
    protected final String _value;

    public SerializedString(String string2) {
        if (string2 == null) throw new IllegalStateException("Null String illegal for SerializedString");
        this._value = string2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this._jdkSerializeValue = objectInputStream.readUTF();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this._value);
    }

    @Override
    public int appendQuoted(char[] arrc, int n) {
        char[] arrc2;
        int n2;
        char[] arrc3 = arrc2 = this._quotedChars;
        if (arrc2 == null) {
            this._quotedChars = arrc3 = JSON_ENCODER.quoteAsString(this._value);
        }
        if (n + (n2 = arrc3.length) > arrc.length) {
            return -1;
        }
        System.arraycopy(arrc3, 0, arrc, n, n2);
        return n2;
    }

    @Override
    public int appendQuotedUTF8(byte[] arrby, int n) {
        int n2;
        byte[] arrby2;
        byte[] arrby3 = arrby2 = this._quotedUTF8Ref;
        if (arrby2 == null) {
            this._quotedUTF8Ref = arrby3 = JSON_ENCODER.quoteAsUTF8(this._value);
        }
        if (n + (n2 = arrby3.length) > arrby.length) {
            return -1;
        }
        System.arraycopy(arrby3, 0, arrby, n, n2);
        return n2;
    }

    @Override
    public int appendUnquoted(char[] arrc, int n) {
        String string2 = this._value;
        int n2 = string2.length();
        if (n + n2 > arrc.length) {
            return -1;
        }
        string2.getChars(0, n2, arrc, n);
        return n2;
    }

    @Override
    public int appendUnquotedUTF8(byte[] arrby, int n) {
        int n2;
        byte[] arrby2;
        byte[] arrby3 = arrby2 = this._unquotedUTF8Ref;
        if (arrby2 == null) {
            this._unquotedUTF8Ref = arrby3 = JSON_ENCODER.encodeAsUTF8(this._value);
        }
        if (n + (n2 = arrby3.length) > arrby.length) {
            return -1;
        }
        System.arraycopy(arrby3, 0, arrby, n, n2);
        return n2;
    }

    @Override
    public final char[] asQuotedChars() {
        char[] arrc;
        char[] arrc2 = arrc = this._quotedChars;
        if (arrc != null) return arrc2;
        this._quotedChars = arrc2 = JSON_ENCODER.quoteAsString(this._value);
        return arrc2;
    }

    @Override
    public final byte[] asQuotedUTF8() {
        byte[] arrby;
        byte[] arrby2 = arrby = this._quotedUTF8Ref;
        if (arrby != null) return arrby2;
        this._quotedUTF8Ref = arrby2 = JSON_ENCODER.quoteAsUTF8(this._value);
        return arrby2;
    }

    @Override
    public final byte[] asUnquotedUTF8() {
        byte[] arrby;
        byte[] arrby2 = arrby = this._unquotedUTF8Ref;
        if (arrby != null) return arrby2;
        this._unquotedUTF8Ref = arrby2 = JSON_ENCODER.encodeAsUTF8(this._value);
        return arrby2;
    }

    @Override
    public final int charLength() {
        return this._value.length();
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (SerializedString)object;
        return this._value.equals(((SerializedString)object)._value);
    }

    @Override
    public final String getValue() {
        return this._value;
    }

    public final int hashCode() {
        return this._value.hashCode();
    }

    @Override
    public int putQuotedUTF8(ByteBuffer byteBuffer) {
        int n;
        byte[] arrby;
        byte[] arrby2 = arrby = this._quotedUTF8Ref;
        if (arrby == null) {
            this._quotedUTF8Ref = arrby2 = JSON_ENCODER.quoteAsUTF8(this._value);
        }
        if ((n = arrby2.length) > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(arrby2, 0, n);
        return n;
    }

    @Override
    public int putUnquotedUTF8(ByteBuffer byteBuffer) {
        int n;
        byte[] arrby;
        byte[] arrby2 = arrby = this._unquotedUTF8Ref;
        if (arrby == null) {
            this._unquotedUTF8Ref = arrby2 = JSON_ENCODER.encodeAsUTF8(this._value);
        }
        if ((n = arrby2.length) > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(arrby2, 0, n);
        return n;
    }

    protected Object readResolve() {
        return new SerializedString(this._jdkSerializeValue);
    }

    public final String toString() {
        return this._value;
    }

    @Override
    public int writeQuotedUTF8(OutputStream outputStream2) throws IOException {
        byte[] arrby;
        byte[] arrby2 = arrby = this._quotedUTF8Ref;
        if (arrby == null) {
            this._quotedUTF8Ref = arrby2 = JSON_ENCODER.quoteAsUTF8(this._value);
        }
        int n = arrby2.length;
        outputStream2.write(arrby2, 0, n);
        return n;
    }

    @Override
    public int writeUnquotedUTF8(OutputStream outputStream2) throws IOException {
        byte[] arrby;
        byte[] arrby2 = arrby = this._unquotedUTF8Ref;
        if (arrby == null) {
            this._unquotedUTF8Ref = arrby2 = JSON_ENCODER.encodeAsUTF8(this._value);
        }
        int n = arrby2.length;
        outputStream2.write(arrby2, 0, n);
        return n;
    }
}

