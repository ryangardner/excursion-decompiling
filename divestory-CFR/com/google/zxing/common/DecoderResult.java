/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private Integer erasures;
    private Integer errorsCorrected;
    private Object other;
    private final byte[] rawBytes;
    private final int structuredAppendParity;
    private final int structuredAppendSequenceNumber;
    private final String text;

    public DecoderResult(byte[] arrby, String string2, List<byte[]> list, String string3) {
        this(arrby, string2, list, string3, -1, -1);
    }

    public DecoderResult(byte[] arrby, String string2, List<byte[]> list, String string3, int n, int n2) {
        this.rawBytes = arrby;
        this.text = string2;
        this.byteSegments = list;
        this.ecLevel = string3;
        this.structuredAppendParity = n2;
        this.structuredAppendSequenceNumber = n;
    }

    public List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public String getECLevel() {
        return this.ecLevel;
    }

    public Integer getErasures() {
        return this.erasures;
    }

    public Integer getErrorsCorrected() {
        return this.errorsCorrected;
    }

    public Object getOther() {
        return this.other;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public int getStructuredAppendParity() {
        return this.structuredAppendParity;
    }

    public int getStructuredAppendSequenceNumber() {
        return this.structuredAppendSequenceNumber;
    }

    public String getText() {
        return this.text;
    }

    public boolean hasStructuredAppend() {
        if (this.structuredAppendParity < 0) return false;
        if (this.structuredAppendSequenceNumber < 0) return false;
        return true;
    }

    public void setErasures(Integer n) {
        this.erasures = n;
    }

    public void setErrorsCorrected(Integer n) {
        this.errorsCorrected = n;
    }

    public void setOther(Object object) {
        this.other = object;
    }
}

