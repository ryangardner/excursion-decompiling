/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public abstract class OneDReader
implements Reader {
    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private Result doDecode(BinaryBitmap var1_1, Map<DecodeHintType, ?> var2_2) throws NotFoundException {
        var3_7 = var1_1.getWidth();
        var4_8 = var1_1.getHeight();
        var5_9 = new BitArray(var3_7);
        var6_10 = var2_2 != null && var2_2.containsKey((Object)DecodeHintType.TRY_HARDER) != false ? 1 : 0;
        var7_11 = var6_10 != 0 ? 8 : 5;
        var8_12 = Math.max(1, var4_8 >> var7_11);
        var9_13 = var6_10 != 0 ? var4_8 : 15;
        var7_11 = 0;
        var6_10 = var3_7;
        block10 : do {
            if (var7_11 >= var9_13) throw NotFoundException.getNotFoundInstance();
            var10_14 = var7_11 + 1;
            var3_7 = var10_14 / 2;
            var7_11 = (var7_11 & 1) == 0 ? 1 : 0;
            var7_11 = var7_11 != 0 ? var3_7 : -var3_7;
            var11_15 = var7_11 * var8_12 + (var4_8 >> 1);
            if (var11_15 < 0) throw NotFoundException.getNotFoundInstance();
            if (var11_15 >= var4_8) throw NotFoundException.getNotFoundInstance();
            try {
                var12_16 = var1_1.getBlackRow(var11_15, (BitArray)var5_9);
                var3_7 = 0;
            }
            catch (NotFoundException var13_18) {
                var13_17 = var2_2;
                var7_11 = var6_10;
                ** GOTO lbl62
            }
            do {
                block15 : {
                    var13_17 = var2_2;
                    var7_11 = var6_10;
                    var5_9 = var12_16;
                    if (var3_7 >= 2) break block15;
                    var5_9 = var2_2;
                    if (var3_7 == 1) {
                        var12_16.reverse();
                        var5_9 = var2_2;
                        if (var2_2 != null) {
                            var5_9 = var2_2;
                            if (var2_2.containsKey((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                                var5_9 = new EnumMap<DecodeHintType, V>(DecodeHintType.class);
                                var5_9.putAll(var2_2);
                                var5_9.remove((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                            }
                        }
                    }
                    var13_17 = this.decodeRow(var11_15, var12_16, (Map<DecodeHintType, ?>)var5_9);
                    if (var3_7 != 1) return var13_17;
                    var13_17.putMetadata(ResultMetadataType.ORIENTATION, 180);
                    var2_2 = var13_17.getResultPoints();
                    if (var2_2 == null) return var13_17;
                    var15_20 = var6_10;
                    try {
                        var16_21 = var2_2[0].getX();
                    }
                    catch (ReaderException var2_4) {
                        ** GOTO lbl-1000
                    }
                    var2_2[0] = var14_19 = new ResultPoint(var15_20 - var16_21 - 1.0f, var2_2[0].getY());
                    try {
                        var2_2[1] = new ResultPoint(var15_20 - var2_2[1].getX() - 1.0f, var2_2[1].getY());
                        return var13_17;
                    }
                    catch (ReaderException var2_6) {}
                    catch (ReaderException var2_3) {}
                    ** GOTO lbl-1000
                }
                var3_7 = var10_14;
                var6_10 = var7_11;
                var2_2 = var13_17;
                var7_11 = var3_7;
                continue block10;
                catch (ReaderException var2_5) {}
                {
                }
lbl-1000: // 4 sources:
                {
                    ++var3_7;
                    var2_2 = var5_9;
                    continue;
                }
                break;
            } while (true);
            break;
        } while (true);
    }

    protected static float patternMatchVariance(int[] arrn, int[] arrn2, float f) {
        int n;
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (n = 0; n < n2; n4 += arrn[n], n5 += arrn2[n], ++n) {
        }
        if (n4 < n5) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = n4;
        float f3 = f2 / (float)n5;
        float f4 = 0.0f;
        n5 = n3;
        while (n5 < n2) {
            n = arrn[n5];
            float f5 = n;
            float f6 = (float)arrn2[n5] * f3;
            f6 = f5 > f6 ? f5 - f6 : (f6 -= f5);
            if (f6 > f * f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f6;
            ++n5;
        }
        return f4 / f2;
    }

    protected static void recordPattern(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2 = arrn.length;
        int n3 = 0;
        Arrays.fill(arrn, 0, n2, 0);
        int n4 = bitArray.getSize();
        if (n >= n4) throw NotFoundException.getNotFoundInstance();
        boolean bl = bitArray.get(n) ^ true;
        int n5 = n;
        n = n3;
        do {
            n3 = n++;
            if (n5 >= n4) break;
            if (bitArray.get(n5) ^ bl) {
                arrn[n] = arrn[n] + 1;
            } else {
                if (n == n2) {
                    n3 = n;
                    break;
                }
                arrn[n] = 1;
                bl ^= true;
            }
            ++n5;
        } while (true);
        if (n3 == n2) return;
        if (n3 != n2 - 1) throw NotFoundException.getNotFoundInstance();
        if (n5 != n4) throw NotFoundException.getNotFoundInstance();
    }

    protected static void recordPatternInReverse(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2 = arrn.length;
        boolean bl = bitArray.get(n);
        while (n > 0 && n2 >= 0) {
            int n3;
            n = n3 = n - 1;
            if (bitArray.get(n3) == bl) continue;
            --n2;
            bl ^= true;
            n = n3;
        }
        if (n2 >= 0) throw NotFoundException.getNotFoundInstance();
        OneDReader.recordPattern(bitArray, n + 1, arrn);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> object) throws NotFoundException, FormatException {
        try {
            return this.doDecode(binaryBitmap, (Map<DecodeHintType, ?>)object);
        }
        catch (NotFoundException notFoundException) {
            int n;
            int n2 = 0;
            int n3 = object != null && object.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
            if (n3 == 0) throw notFoundException;
            if (!binaryBitmap.isRotateSupported()) throw notFoundException;
            binaryBitmap = binaryBitmap.rotateCounterClockwise();
            object = this.doDecode(binaryBitmap, (Map<DecodeHintType, ?>)object);
            ResultPoint[] arrresultPoint = ((Result)object).getResultMetadata();
            n3 = n = 270;
            if (arrresultPoint != null) {
                n3 = n;
                if (arrresultPoint.containsKey((Object)ResultMetadataType.ORIENTATION)) {
                    n3 = ((Integer)arrresultPoint.get((Object)ResultMetadataType.ORIENTATION) + 270) % 360;
                }
            }
            ((Result)object).putMetadata(ResultMetadataType.ORIENTATION, n3);
            arrresultPoint = ((Result)object).getResultPoints();
            if (arrresultPoint == null) return object;
            n = binaryBitmap.getHeight();
            n3 = n2;
            while (n3 < arrresultPoint.length) {
                arrresultPoint[n3] = new ResultPoint((float)n - arrresultPoint[n3].getY() - 1.0f, arrresultPoint[n3].getX());
                ++n3;
            }
            return object;
        }
    }

    public abstract Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException;

    @Override
    public void reset() {
    }
}

