/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import java.util.EnumMap;
import java.util.Map;

public final class Result {
    private final BarcodeFormat format;
    private final byte[] rawBytes;
    private Map<ResultMetadataType, Object> resultMetadata;
    private ResultPoint[] resultPoints;
    private final String text;
    private final long timestamp;

    public Result(String string2, byte[] arrby, ResultPoint[] arrresultPoint, BarcodeFormat barcodeFormat) {
        this(string2, arrby, arrresultPoint, barcodeFormat, System.currentTimeMillis());
    }

    public Result(String string2, byte[] arrby, ResultPoint[] arrresultPoint, BarcodeFormat barcodeFormat, long l) {
        this.text = string2;
        this.rawBytes = arrby;
        this.resultPoints = arrresultPoint;
        this.format = barcodeFormat;
        this.resultMetadata = null;
        this.timestamp = l;
    }

    public void addResultPoints(ResultPoint[] arrresultPoint) {
        ResultPoint[] arrresultPoint2 = this.resultPoints;
        if (arrresultPoint2 == null) {
            this.resultPoints = arrresultPoint;
            return;
        }
        if (arrresultPoint == null) return;
        if (arrresultPoint.length <= 0) return;
        ResultPoint[] arrresultPoint3 = new ResultPoint[arrresultPoint2.length + arrresultPoint.length];
        System.arraycopy(arrresultPoint2, 0, arrresultPoint3, 0, arrresultPoint2.length);
        System.arraycopy(arrresultPoint, 0, arrresultPoint3, arrresultPoint2.length, arrresultPoint.length);
        this.resultPoints = arrresultPoint3;
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.format;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.resultMetadata;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public String getText() {
        return this.text;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void putAllMetadata(Map<ResultMetadataType, Object> map) {
        if (map == null) return;
        Map<ResultMetadataType, Object> map2 = this.resultMetadata;
        if (map2 == null) {
            this.resultMetadata = map;
            return;
        }
        map2.putAll(map);
    }

    public void putMetadata(ResultMetadataType resultMetadataType, Object object) {
        if (this.resultMetadata == null) {
            this.resultMetadata = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        }
        this.resultMetadata.put(resultMetadataType, object);
    }

    public String toString() {
        return this.text;
    }
}

