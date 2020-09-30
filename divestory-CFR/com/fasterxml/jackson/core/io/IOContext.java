/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;

public class IOContext {
    protected byte[] _base64Buffer;
    protected final BufferRecycler _bufferRecycler;
    protected char[] _concatCBuffer;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected char[] _nameCopyBuffer;
    protected byte[] _readIOBuffer;
    protected final Object _sourceRef;
    protected char[] _tokenCBuffer;
    protected byte[] _writeEncodingBuffer;

    public IOContext(BufferRecycler bufferRecycler, Object object, boolean bl) {
        this._bufferRecycler = bufferRecycler;
        this._sourceRef = object;
        this._managedResource = bl;
    }

    private IllegalArgumentException wrongBuf() {
        return new IllegalArgumentException("Trying to release buffer smaller than original");
    }

    protected final void _verifyAlloc(Object object) {
        if (object != null) throw new IllegalStateException("Trying to call same allocXxx() method second time");
    }

    protected final void _verifyRelease(byte[] arrby, byte[] arrby2) {
        if (arrby == arrby2) return;
        if (arrby.length < arrby2.length) throw this.wrongBuf();
    }

    protected final void _verifyRelease(char[] arrc, char[] arrc2) {
        if (arrc == arrc2) return;
        if (arrc.length < arrc2.length) throw this.wrongBuf();
    }

    public byte[] allocBase64Buffer() {
        this._verifyAlloc(this._base64Buffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(3);
        this._base64Buffer = arrby;
        return arrby;
    }

    public byte[] allocBase64Buffer(int n) {
        this._verifyAlloc(this._base64Buffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(3, n);
        this._base64Buffer = arrby;
        return arrby;
    }

    public char[] allocConcatBuffer() {
        this._verifyAlloc(this._concatCBuffer);
        char[] arrc = this._bufferRecycler.allocCharBuffer(1);
        this._concatCBuffer = arrc;
        return arrc;
    }

    public char[] allocNameCopyBuffer(int n) {
        this._verifyAlloc(this._nameCopyBuffer);
        char[] arrc = this._bufferRecycler.allocCharBuffer(3, n);
        this._nameCopyBuffer = arrc;
        return arrc;
    }

    public byte[] allocReadIOBuffer() {
        this._verifyAlloc(this._readIOBuffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(0);
        this._readIOBuffer = arrby;
        return arrby;
    }

    public byte[] allocReadIOBuffer(int n) {
        this._verifyAlloc(this._readIOBuffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(0, n);
        this._readIOBuffer = arrby;
        return arrby;
    }

    public char[] allocTokenBuffer() {
        this._verifyAlloc(this._tokenCBuffer);
        char[] arrc = this._bufferRecycler.allocCharBuffer(0);
        this._tokenCBuffer = arrc;
        return arrc;
    }

    public char[] allocTokenBuffer(int n) {
        this._verifyAlloc(this._tokenCBuffer);
        char[] arrc = this._bufferRecycler.allocCharBuffer(0, n);
        this._tokenCBuffer = arrc;
        return arrc;
    }

    public byte[] allocWriteEncodingBuffer() {
        this._verifyAlloc(this._writeEncodingBuffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(1);
        this._writeEncodingBuffer = arrby;
        return arrby;
    }

    public byte[] allocWriteEncodingBuffer(int n) {
        this._verifyAlloc(this._writeEncodingBuffer);
        byte[] arrby = this._bufferRecycler.allocByteBuffer(1, n);
        this._writeEncodingBuffer = arrby;
        return arrby;
    }

    public TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }

    public JsonEncoding getEncoding() {
        return this._encoding;
    }

    public Object getSourceReference() {
        return this._sourceRef;
    }

    public boolean isResourceManaged() {
        return this._managedResource;
    }

    public void releaseBase64Buffer(byte[] arrby) {
        if (arrby == null) return;
        this._verifyRelease(arrby, this._base64Buffer);
        this._base64Buffer = null;
        this._bufferRecycler.releaseByteBuffer(3, arrby);
    }

    public void releaseConcatBuffer(char[] arrc) {
        if (arrc == null) return;
        this._verifyRelease(arrc, this._concatCBuffer);
        this._concatCBuffer = null;
        this._bufferRecycler.releaseCharBuffer(1, arrc);
    }

    public void releaseNameCopyBuffer(char[] arrc) {
        if (arrc == null) return;
        this._verifyRelease(arrc, this._nameCopyBuffer);
        this._nameCopyBuffer = null;
        this._bufferRecycler.releaseCharBuffer(3, arrc);
    }

    public void releaseReadIOBuffer(byte[] arrby) {
        if (arrby == null) return;
        this._verifyRelease(arrby, this._readIOBuffer);
        this._readIOBuffer = null;
        this._bufferRecycler.releaseByteBuffer(0, arrby);
    }

    public void releaseTokenBuffer(char[] arrc) {
        if (arrc == null) return;
        this._verifyRelease(arrc, this._tokenCBuffer);
        this._tokenCBuffer = null;
        this._bufferRecycler.releaseCharBuffer(0, arrc);
    }

    public void releaseWriteEncodingBuffer(byte[] arrby) {
        if (arrby == null) return;
        this._verifyRelease(arrby, this._writeEncodingBuffer);
        this._writeEncodingBuffer = null;
        this._bufferRecycler.releaseByteBuffer(1, arrby);
    }

    public void setEncoding(JsonEncoding jsonEncoding) {
        this._encoding = jsonEncoding;
    }

    public IOContext withEncoding(JsonEncoding jsonEncoding) {
        this._encoding = jsonEncoding;
        return this;
    }
}

