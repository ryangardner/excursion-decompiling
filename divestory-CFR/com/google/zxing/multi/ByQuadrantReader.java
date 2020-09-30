/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Map;

public final class ByQuadrantReader
implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader reader) {
        this.delegate = reader;
    }

    private static void makeAbsolute(ResultPoint[] arrresultPoint, int n, int n2) {
        if (arrresultPoint == null) return;
        int n3 = 0;
        while (n3 < arrresultPoint.length) {
            ResultPoint resultPoint = arrresultPoint[n3];
            arrresultPoint[n3] = new ResultPoint(resultPoint.getX() + (float)n, resultPoint.getY() + (float)n2);
            ++n3;
        }
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int n = ((BinaryBitmap)object).getWidth();
        int n2 = ((BinaryBitmap)object).getHeight();
        n /= 2;
        n2 /= 2;
        try {
            return this.delegate.decode(((BinaryBitmap)object).crop(0, 0, n, n2), map);
        }
        catch (NotFoundException notFoundException) {
            try {
                Result result = this.delegate.decode(((BinaryBitmap)object).crop(n, 0, n, n2), map);
                ByQuadrantReader.makeAbsolute(result.getResultPoints(), n, 0);
                return result;
            }
            catch (NotFoundException notFoundException2) {
                try {
                    Result result = this.delegate.decode(((BinaryBitmap)object).crop(0, n2, n, n2), map);
                    ByQuadrantReader.makeAbsolute(result.getResultPoints(), 0, n2);
                    return result;
                }
                catch (NotFoundException notFoundException3) {
                    try {
                        Result result = this.delegate.decode(((BinaryBitmap)object).crop(n, n2, n, n2), map);
                        ByQuadrantReader.makeAbsolute(result.getResultPoints(), n, n2);
                        return result;
                    }
                    catch (NotFoundException notFoundException4) {
                        int n3 = n / 2;
                        int n4 = n2 / 2;
                        object = ((BinaryBitmap)object).crop(n3, n4, n, n2);
                        object = this.delegate.decode((BinaryBitmap)object, map);
                        ByQuadrantReader.makeAbsolute(((Result)object).getResultPoints(), n3, n4);
                        return object;
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        this.delegate.reset();
    }
}

