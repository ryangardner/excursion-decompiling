/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.Pair;
import com.google.zxing.oned.rss.RSSUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSS14Reader
extends AbstractRSSReader {
    private static final int[][] FINDER_PATTERNS;
    private static final int[] INSIDE_GSUM;
    private static final int[] INSIDE_ODD_TOTAL_SUBSET;
    private static final int[] INSIDE_ODD_WIDEST;
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET;
    private static final int[] OUTSIDE_GSUM;
    private static final int[] OUTSIDE_ODD_WIDEST;
    private final List<Pair> possibleLeftPairs = new ArrayList<Pair>();
    private final List<Pair> possibleRightPairs = new ArrayList<Pair>();

    static {
        OUTSIDE_EVEN_TOTAL_SUBSET = new int[]{1, 10, 34, 70, 126};
        INSIDE_ODD_TOTAL_SUBSET = new int[]{4, 20, 48, 81};
        OUTSIDE_GSUM = new int[]{0, 161, 961, 2015, 2715};
        INSIDE_GSUM = new int[]{0, 336, 1036, 1516};
        OUTSIDE_ODD_WIDEST = new int[]{8, 6, 4, 3, 1};
        INSIDE_ODD_WIDEST = new int[]{2, 4, 6, 8};
        int[] arrn = new int[]{3, 8, 2, 1};
        int[] arrn2 = new int[]{3, 1, 9, 1};
        int[] arrn3 = new int[]{2, 5, 6, 1};
        int[] arrn4 = new int[]{2, 3, 8, 1};
        int[] arrn5 = new int[]{1, 3, 9, 1};
        FINDER_PATTERNS = new int[][]{arrn, {3, 5, 5, 1}, {3, 3, 7, 1}, arrn2, {2, 7, 4, 1}, arrn3, arrn4, {1, 5, 7, 1}, arrn5};
    }

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        boolean bl;
        block2 : {
            Pair pair2;
            if (pair == null) {
                return;
            }
            boolean bl2 = false;
            Iterator<Pair> iterator2 = collection.iterator();
            do {
                bl = bl2;
                if (!iterator2.hasNext()) break block2;
            } while ((pair2 = iterator2.next()).getValue() != pair.getValue());
            pair2.incrementCount();
            bl = true;
        }
        if (bl) return;
        collection.add(pair);
    }

    /*
     * Unable to fully structure code
     */
    private void adjustOddEvenCounts(boolean var1_1, int var2_2) throws NotFoundException {
        block21 : {
            block22 : {
                block19 : {
                    block20 : {
                        var3_3 = RSS14Reader.count(this.getOddCounts());
                        var4_4 = RSS14Reader.count(this.getEvenCounts());
                        var5_5 = var3_3 + var4_4 - var2_2;
                        var6_6 = 0;
                        var7_7 = 1;
                        var8_8 = 1;
                        var9_9 = (var3_3 & 1) == var1_1;
                        var10_10 = (var4_4 & 1) == 1;
                        if (var1_1 == 0) break block19;
                        if (var3_3 > 12) {
                            var11_11 = 0;
                            var2_2 = 1;
                        } else {
                            var11_11 = var3_3 < 4 ? 1 : 0;
                            var2_2 = 0;
                        }
                        if (var4_4 <= 12) break block20;
                        var12_12 = var11_11;
                        var11_11 = var2_2;
                        ** GOTO lbl36
                    }
                    var13_13 = var11_11;
                    var14_14 = var2_2;
                    if (var4_4 >= 4) ** GOTO lbl-1000
                    var12_12 = var11_11;
                    var11_11 = var2_2;
                    ** GOTO lbl45
                }
                if (var3_3 > 11) {
                    var2_2 = 0;
                    var11_11 = 1;
                } else {
                    var2_2 = var3_3 < 5 ? 1 : 0;
                    var11_11 = 0;
                }
                if (var4_4 > 10) {
                    var12_12 = var2_2;
lbl36: // 2 sources:
                    var14_15 = 1;
                    var13_13 = var6_6;
                    var2_2 = var12_12;
                    var12_12 = var14_15;
                } else {
                    var13_13 = var2_2;
                    var14_14 = var11_11;
                    if (var4_4 < 4) {
                        var12_12 = var2_2;
lbl45: // 2 sources:
                        var14_14 = 0;
                        var13_13 = 1;
                        var2_2 = var12_12;
                        var12_12 = var14_14;
                    } else lbl-1000: // 2 sources:
                    {
                        var12_12 = 0;
                        var11_11 = var14_14;
                        var2_2 = var13_13;
                        var13_13 = var6_6;
                    }
                }
                if (var5_5 != 1) break block21;
                if (!var9_9) break block22;
                if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
                ** GOTO lbl78
            }
            if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
            ** GOTO lbl75
        }
        if (var5_5 == -1) {
            if (var9_9) {
                if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
                var2_2 = var7_7;
            } else {
                if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
                var13_13 = 1;
            }
        } else {
            if (var5_5 != 0) throw NotFoundException.getNotFoundInstance();
            if (var9_9) {
                if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
                if (var3_3 < var4_4) {
                    var2_2 = var8_8;
lbl75: // 2 sources:
                    var12_12 = 1;
                } else {
                    var13_13 = 1;
lbl78: // 2 sources:
                    var11_11 = 1;
                }
            } else if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
        }
        if (var2_2 != 0) {
            if (var11_11 != 0) throw NotFoundException.getNotFoundInstance();
            RSS14Reader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var11_11 != 0) {
            RSS14Reader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var13_13 != 0) {
            if (var12_12 != 0) throw NotFoundException.getNotFoundInstance();
            RSS14Reader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (var12_12 == 0) return;
        RSS14Reader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int n;
        int n2 = pair.getChecksumPortion();
        int n3 = pair2.getChecksumPortion();
        int n4 = n = pair.getFinderPattern().getValue() * 9 + pair2.getFinderPattern().getValue();
        if (n > 72) {
            n4 = n - 1;
        }
        n = n4;
        if (n4 > 8) {
            n = n4 - 1;
        }
        if ((n2 + n3 * 16) % 79 != n) return false;
        return true;
    }

    private static Result constructResult(Pair object, Pair object2) {
        int n;
        int n2;
        Object object3 = String.valueOf((long)((DataCharacter)object).getValue() * 4537077L + (long)((DataCharacter)object2).getValue());
        Object object4 = new StringBuilder(14);
        for (n = 13 - object3.length(); n > 0; --n) {
            ((StringBuilder)object4).append('0');
        }
        ((StringBuilder)object4).append((String)object3);
        int n3 = 0;
        for (n = 0; n < 13; n3 += n2, ++n) {
            int n4;
            n2 = n4 = ((StringBuilder)object4).charAt(n) - 48;
            if ((n & 1) != 0) continue;
            n2 = n4 * 3;
        }
        n = n3 = 10 - n3 % 10;
        if (n3 == 10) {
            n = 0;
        }
        ((StringBuilder)object4).append(n);
        Object object5 = ((Pair)object).getFinderPattern().getResultPoints();
        object3 = ((Pair)object2).getFinderPattern().getResultPoints();
        object2 = String.valueOf(((StringBuilder)object4).toString());
        object = object5[0];
        object5 = object5[1];
        object4 = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_14;
        return new Result((String)object2, null, new ResultPoint[]{object, object5, object4, object3}, barcodeFormat);
    }

    private DataCharacter decodeDataCharacter(BitArray arrn, FinderPattern arrn2, boolean bl) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4;
        int[] arrn3 = this.getDataCharacterCounters();
        arrn3[0] = 0;
        arrn3[1] = 0;
        arrn3[2] = 0;
        arrn3[3] = 0;
        arrn3[4] = 0;
        arrn3[5] = 0;
        arrn3[6] = 0;
        arrn3[7] = 0;
        if (bl) {
            RSS14Reader.recordPatternInReverse((BitArray)arrn, arrn2.getStartEnd()[0], arrn3);
        } else {
            RSS14Reader.recordPattern((BitArray)arrn, arrn2.getStartEnd()[1] + 1, arrn3);
            n4 = arrn3.length - 1;
            for (n3 = 0; n3 < n4; ++n3, --n4) {
                n2 = arrn3[n3];
                arrn3[n3] = arrn3[n4];
                arrn3[n4] = n2;
            }
        }
        n4 = bl ? 16 : 15;
        float f = (float)RSS14Reader.count(arrn3) / (float)n4;
        arrn = this.getOddCounts();
        arrn2 = this.getEvenCounts();
        float[] arrf = this.getOddRoundingErrors();
        float[] arrf2 = this.getEvenRoundingErrors();
        for (n2 = 0; n2 < arrn3.length; ++n2) {
            float f2 = (float)arrn3[n2] / f;
            n = (int)(0.5f + f2);
            if (n < 1) {
                n3 = 1;
            } else {
                n3 = n;
                if (n > 8) {
                    n3 = 8;
                }
            }
            n = n2 / 2;
            if ((n2 & 1) == 0) {
                arrn[n] = n3;
                arrf[n] = f2 - (float)n3;
                continue;
            }
            arrn2[n] = n3;
            arrf2[n] = f2 - (float)n3;
        }
        this.adjustOddEvenCounts(bl, n4);
        n4 = 0;
        n3 = 0;
        for (n2 = arrn.length - 1; n2 >= 0; n3 += arrn[n2], --n2) {
            n4 = n4 * 9 + arrn[n2];
        }
        int n5 = 0;
        n2 = 0;
        for (n = arrn2.length - 1; n >= 0; n2 += arrn2[n], --n) {
            n5 = n5 * 9 + arrn2[n];
        }
        n4 += n5 * 3;
        if (bl) {
            if ((n3 & 1) != 0) throw NotFoundException.getNotFoundInstance();
            if (n3 > 12) throw NotFoundException.getNotFoundInstance();
            if (n3 < 4) throw NotFoundException.getNotFoundInstance();
            n2 = (12 - n3) / 2;
            n = OUTSIDE_ODD_WIDEST[n2];
            n3 = RSSUtils.getRSSvalue(arrn, n, false);
            n = RSSUtils.getRSSvalue(arrn2, 9 - n, true);
            return new DataCharacter(n3 * OUTSIDE_EVEN_TOTAL_SUBSET[n2] + n + OUTSIDE_GSUM[n2], n4);
        }
        if ((n2 & 1) != 0) throw NotFoundException.getNotFoundInstance();
        if (n2 > 10) throw NotFoundException.getNotFoundInstance();
        if (n2 < 4) throw NotFoundException.getNotFoundInstance();
        n3 = (10 - n2) / 2;
        n = INSIDE_ODD_WIDEST[n3];
        n2 = RSSUtils.getRSSvalue(arrn, n, true);
        return new DataCharacter(RSSUtils.getRSSvalue(arrn2, 9 - n, false) * INSIDE_ODD_TOTAL_SUBSET[n3] + n2 + INSIDE_GSUM[n3], n4);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private Pair decodePair(BitArray var1_1, boolean var2_3, int var3_4, Map<DecodeHintType, ?> var4_5) {
        try {
            var5_6 = this.findFinderPattern((BitArray)var1_1, 0, var2_3);
            var6_8 = this.parseFoundFinderPattern((BitArray)var1_1, var3_4, var2_3, var5_6);
            var4_5 = var4_5 == null ? null : (ResultPointCallback)var4_5.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (var4_5 == null) ** GOTO lbl14
        }
        catch (NotFoundException var1_2) {
            return null;
        }
        var8_10 = var7_9 = (float)(var5_6[0] + var5_6[1]) / 2.0f;
        if (!var2_3) ** GOTO lbl12
        var8_10 = (float)(var1_1.getSize() - 1) - var7_9;
lbl12: // 2 sources:
        var5_7 = new ResultPoint(var8_10, var3_4);
        var4_5.foundPossibleResultPoint(var5_7);
lbl14: // 2 sources:
        var4_5 = this.decodeDataCharacter((BitArray)var1_1, var6_8, true);
        var1_1 = this.decodeDataCharacter((BitArray)var1_1, var6_8, false);
        return new Pair(var4_5.getValue() * 1597 + var1_1.getValue(), var4_5.getChecksumPortion() + var1_1.getChecksumPortion() * 4, var6_8);
    }

    private int[] findFinderPattern(BitArray bitArray, int n, boolean bl) throws NotFoundException {
        int[] arrn = this.getDecodeFinderCounters();
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        int n2 = bitArray.getSize();
        boolean bl2 = false;
        while (n < n2 && bl != (bl2 = bitArray.get(n) ^ true)) {
            ++n;
        }
        int n3 = n;
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n5 < n2) {
            if (bitArray.get(n5) ^ bl2) {
                arrn[n] = arrn[n] + 1;
            } else {
                if (n == 3) {
                    if (RSS14Reader.isFinderPattern(arrn)) {
                        return new int[]{n3, n5};
                    }
                    n3 += arrn[0] + arrn[1];
                    arrn[0] = arrn[2];
                    arrn[1] = arrn[3];
                    arrn[2] = 0;
                    arrn[3] = 0;
                    --n;
                } else {
                    ++n;
                }
                arrn[n] = 1;
                bl2 ^= true;
            }
            ++n5;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int n, boolean bl, int[] arrn) throws NotFoundException {
        int n2;
        int n3;
        boolean bl2 = bitArray.get(arrn[0]);
        for (n3 = arrn[0] - 1; n3 >= 0 && bitArray.get(n3) ^ bl2; --n3) {
        }
        int n4 = n3 + 1;
        n3 = arrn[0];
        int[] arrn2 = this.getDecodeFinderCounters();
        System.arraycopy(arrn2, 0, arrn2, 1, arrn2.length - 1);
        arrn2[0] = n3 - n4;
        int n5 = RSS14Reader.parseFinderValue(arrn2, FINDER_PATTERNS);
        n3 = arrn[1];
        if (bl) {
            n2 = bitArray.getSize();
            n3 = bitArray.getSize() - 1 - n3;
            n2 = n2 - 1 - n4;
            return new FinderPattern(n5, new int[]{n4, arrn[1]}, n2, n3, n);
        } else {
            n2 = n4;
        }
        return new FinderPattern(n5, new int[]{n4, arrn[1]}, n2, n3, n);
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException {
        Pair pair = this.decodePair((BitArray)object, false, n, (Map<DecodeHintType, ?>)object2);
        RSS14Reader.addOrTally(this.possibleLeftPairs, pair);
        ((BitArray)object).reverse();
        object2 = this.decodePair((BitArray)object, true, n, (Map<DecodeHintType, ?>)object2);
        RSS14Reader.addOrTally(this.possibleRightPairs, (Pair)object2);
        ((BitArray)object).reverse();
        int n2 = this.possibleLeftPairs.size();
        n = 0;
        while (n < n2) {
            object2 = this.possibleLeftPairs.get(n);
            if (((Pair)object2).getCount() > 1) {
                int n3 = this.possibleRightPairs.size();
                for (int i = 0; i < n3; ++i) {
                    object = this.possibleRightPairs.get(i);
                    if (((Pair)object).getCount() <= 1 || !RSS14Reader.checkChecksum((Pair)object2, (Pair)object)) continue;
                    return RSS14Reader.constructResult((Pair)object2, (Pair)object);
                }
            }
            ++n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}

