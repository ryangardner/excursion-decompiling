/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.BitArrayBuilder;
import com.google.zxing.oned.rss.expanded.ExpandedPair;
import com.google.zxing.oned.rss.expanded.ExpandedRow;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSSExpandedReader
extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET;
    private static final int[][] FINDER_PATTERNS;
    private static final int[][] FINDER_PATTERN_SEQUENCES;
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM;
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST;
    private static final int[][] WEIGHTS;
    private final List<ExpandedPair> pairs = new ArrayList<ExpandedPair>(11);
    private final List<ExpandedRow> rows = new ArrayList<ExpandedRow>();
    private final int[] startEnd = new int[2];
    private boolean startFromEven;

    static {
        SYMBOL_WIDEST = new int[]{7, 5, 4, 3, 1};
        EVEN_TOTAL_SUBSET = new int[]{4, 20, 52, 104, 204};
        GSUM = new int[]{0, 348, 1388, 2948, 3988};
        int[] arrn = new int[]{3, 6, 4, 1};
        int[] arrn2 = new int[]{3, 2, 8, 1};
        FINDER_PATTERNS = new int[][]{{1, 8, 4, 1}, arrn, {3, 4, 6, 1}, arrn2, {2, 6, 5, 1}, {2, 2, 9, 1}};
        arrn = new int[]{20, 60, 180, 118, 143, 7, 21, 63};
        arrn2 = new int[]{193, 157, 49, 147, 19, 57, 171, 91};
        int[] arrn3 = new int[]{113, 128, 173, 97, 80, 29, 87, 50};
        int[] arrn4 = new int[]{46, 138, 203, 187, 139, 206, 196, 166};
        int[] arrn5 = new int[]{76, 17, 51, 153, 37, 111, 122, 155};
        int[] arrn6 = new int[]{134, 191, 151, 31, 93, 68, 204, 190};
        int[] arrn7 = new int[]{6, 18, 54, 162, 64, 192, 154, 40};
        int[] arrn8 = new int[]{45, 135, 194, 160, 58, 174, 100, 89};
        WEIGHTS = new int[][]{{1, 3, 9, 27, 81, 32, 96, 77}, arrn, {189, 145, 13, 39, 117, 140, 209, 205}, arrn2, {62, 186, 136, 197, 169, 85, 44, 132}, {185, 133, 188, 142, 4, 12, 36, 108}, arrn3, {150, 28, 84, 41, 123, 158, 52, 156}, arrn4, arrn5, {43, 129, 176, 106, 107, 110, 119, 146}, {16, 48, 144, 10, 30, 90, 59, 177}, {109, 116, 137, 200, 178, 112, 125, 164}, {70, 210, 208, 202, 184, 130, 179, 115}, arrn6, {148, 22, 66, 198, 172, 94, 71, 2}, arrn7, {120, 149, 25, 75, 14, 42, 126, 167}, {79, 26, 78, 23, 69, 207, 199, 175}, {103, 98, 83, 38, 114, 131, 182, 124}, {161, 61, 183, 127, 170, 88, 53, 159}, {55, 165, 73, 8, 24, 72, 5, 15}, arrn8};
        arrn = new int[]{0, 0};
        arrn2 = new int[]{0, 1, 1};
        arrn3 = new int[]{0, 4, 1, 3, 2};
        arrn4 = new int[]{0, 0, 1, 1, 2, 2, 3, 3};
        arrn5 = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5};
        FINDER_PATTERN_SEQUENCES = new int[][]{arrn, arrn2, {0, 2, 1, 3}, arrn3, {0, 4, 1, 3, 3, 5}, {0, 4, 1, 3, 4, 5, 5}, arrn4, {0, 0, 1, 1, 2, 2, 3, 4, 4}, {0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, arrn5};
    }

    /*
     * Unable to fully structure code
     */
    private void adjustOddEvenCounts(int var1_1) throws NotFoundException {
        block16 : {
            block17 : {
                var2_2 = RSSExpandedReader.count(this.getOddCounts());
                var3_3 = RSSExpandedReader.count(this.getEvenCounts());
                var4_4 = var2_2 + var3_3 - var1_1;
                var5_5 = false;
                var6_6 = false;
                var7_7 = 1;
                var8_8 = 1;
                var9_9 = (var2_2 & 1) == 1;
                var10_10 = (var3_3 & 1) == 0;
                if (var2_2 > 13) {
                    var1_1 = 0;
                    var11_11 = true;
                } else {
                    var1_1 = var2_2 < 4 ? 1 : 0;
                    var11_11 = false;
                }
                if (var3_3 > 13) {
                    var6_6 = true;
                } else {
                    var5_5 = var6_6;
                    if (var3_3 < 4) {
                        var5_5 = true;
                    }
                    var6_6 = false;
                }
                if (var4_4 != 1) break block16;
                if (!var9_9) break block17;
                if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
                ** GOTO lbl47
            }
            if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
            ** GOTO lbl44
        }
        if (var4_4 == -1) {
            if (var9_9) {
                if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
                var1_1 = var7_7;
            } else {
                if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
                var5_5 = true;
            }
        } else {
            if (var4_4 != 0) throw NotFoundException.getNotFoundInstance();
            if (var9_9) {
                if (var10_10 == false) throw NotFoundException.getNotFoundInstance();
                if (var2_2 < var3_3) {
                    var1_1 = var8_8;
lbl44: // 2 sources:
                    var6_6 = true;
                } else {
                    var5_5 = true;
lbl47: // 2 sources:
                    var11_11 = true;
                }
            } else if (var10_10 != false) throw NotFoundException.getNotFoundInstance();
        }
        if (var1_1 != 0) {
            if (var11_11 != false) throw NotFoundException.getNotFoundInstance();
            RSSExpandedReader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var11_11) {
            RSSExpandedReader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (var5_5) {
            if (var6_6 != false) throw NotFoundException.getNotFoundInstance();
            RSSExpandedReader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (var6_6 == false) return;
        RSSExpandedReader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
    }

    private boolean checkChecksum() {
        Object object = this.pairs;
        boolean bl = false;
        Object object2 = object.get(0);
        object = ((ExpandedPair)object2).getLeftChar();
        if ((object2 = ((ExpandedPair)object2).getRightChar()) == null) {
            return false;
        }
        int n = ((DataCharacter)object2).getChecksumPortion();
        int n2 = 2;
        int n3 = 1;
        do {
            if (n3 >= this.pairs.size()) {
                if ((n2 - 4) * 211 + n % 211 != ((DataCharacter)object).getValue()) return bl;
                return true;
            }
            object2 = this.pairs.get(n3);
            int n4 = n + ((ExpandedPair)object2).getLeftChar().getChecksumPortion();
            int n5 = n2 + 1;
            object2 = ((ExpandedPair)object2).getRightChar();
            n = n4;
            n2 = n5;
            if (object2 != null) {
                n = n4 + ((DataCharacter)object2).getChecksumPortion();
                n2 = n5 + 1;
            }
            ++n3;
        } while (true);
    }

    private List<ExpandedPair> checkRows(List<ExpandedRow> list, int n) throws NotFoundException {
        while (n < this.rows.size()) {
            ExpandedRow expandedRow = this.rows.get(n);
            this.pairs.clear();
            int n2 = list.size();
            for (int i = 0; i < n2; ++i) {
                this.pairs.addAll(list.get(i).getPairs());
            }
            this.pairs.addAll(expandedRow.getPairs());
            if (RSSExpandedReader.isValidSequence(this.pairs)) {
                if (this.checkChecksum()) {
                    return this.pairs;
                }
                List<Object> list2 = new ArrayList<ExpandedRow>();
                list2.addAll(list);
                list2.add(expandedRow);
                try {
                    return this.checkRows(list2, n + 1);
                }
                catch (NotFoundException notFoundException) {}
            }
            ++n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private List<ExpandedPair> checkRows(boolean bl) {
        int n = this.rows.size();
        List<Object> list = null;
        if (n > 25) {
            this.rows.clear();
            return null;
        }
        this.pairs.clear();
        if (bl) {
            Collections.reverse(this.rows);
        }
        try {
            List<Object> list2 = new List<Object>();
            list = list2 = this.checkRows(list2, 0);
        }
        catch (NotFoundException notFoundException) {
            // empty catch block
        }
        if (!bl) return list;
        Collections.reverse(this.rows);
        return list;
    }

    static Result constructResult(List<ExpandedPair> object) throws NotFoundException, FormatException {
        String string2 = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(object)).parseInformation();
        Object object2 = object.get(0).getFinderPattern().getResultPoints();
        Object object3 = object.get(object.size() - 1).getFinderPattern().getResultPoints();
        object = object2[0];
        ResultPoint resultPoint = object2[1];
        object2 = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_EXPANDED;
        return new Result(string2, null, new ResultPoint[]{object, resultPoint, object2, object3}, barcodeFormat);
    }

    private void findNextPair(BitArray arrn, List<ExpandedPair> list, int n) throws NotFoundException {
        int[] arrn2 = this.getDecodeFinderCounters();
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n2 = arrn.getSize();
        if (n < 0) {
            n = list.isEmpty() ? 0 : list.get(list.size() - 1).getFinderPattern().getStartEnd()[1];
        }
        int n3 = list.size() % 2 != 0 ? 1 : 0;
        int n4 = n3;
        if (this.startFromEven) {
            n4 = n3 ^ true;
        }
        n3 = 0;
        while (n < n2 && (n3 = arrn.get(n) ^ true) != 0) {
            ++n;
        }
        int n5 = n3;
        n3 = 0;
        int n6 = n;
        int n7 = n;
        n = n6;
        while (n7 < n2) {
            if (arrn.get(n7) ^ n5) {
                arrn2[n3] = arrn2[n3] + 1;
            } else {
                if (n3 == 3) {
                    if (n4 != 0) {
                        RSSExpandedReader.reverseCounters(arrn2);
                    }
                    if (RSSExpandedReader.isFinderPattern(arrn2)) {
                        arrn = this.startEnd;
                        arrn[0] = n;
                        arrn[1] = n7;
                        return;
                    }
                    if (n4 != 0) {
                        RSSExpandedReader.reverseCounters(arrn2);
                    }
                    n += arrn2[0] + arrn2[1];
                    arrn2[0] = arrn2[2];
                    arrn2[1] = arrn2[3];
                    arrn2[2] = 0;
                    arrn2[3] = 0;
                    --n3;
                } else {
                    ++n3;
                }
                arrn2[n3] = 1;
                n5 ^= 1;
            }
            ++n7;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int getNextSecondBar(BitArray bitArray, int n) {
        if (!bitArray.get(n)) return bitArray.getNextUnset(bitArray.getNextSet(n));
        return bitArray.getNextSet(bitArray.getNextUnset(n));
    }

    private static boolean isNotA1left(FinderPattern finderPattern, boolean bl, boolean bl2) {
        if (finderPattern.getValue() != 0) return true;
        if (!bl) return true;
        if (!bl2) return true;
        return false;
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> iterable, Iterable<ExpandedRow> object) {
        boolean bl;
        Iterator<ExpandedRow> iterator2 = object.iterator();
        do {
            boolean bl2 = iterator2.hasNext();
            boolean bl3 = false;
            if (!bl2) return false;
            ExpandedRow expandedRow = iterator2.next();
            object = iterable.iterator();
            block1 : do {
                if (!object.hasNext()) return true;
                ExpandedPair expandedPair = (ExpandedPair)object.next();
                Iterator<ExpandedPair> iterator3 = expandedRow.getPairs().iterator();
                while (iterator3.hasNext()) {
                    if (!expandedPair.equals(iterator3.next())) continue;
                    bl = true;
                    continue block1;
                }
                bl = false;
            } while (bl);
            bl = bl3;
        } while (!bl);
        return true;
    }

    private static boolean isValidSequence(List<ExpandedPair> list) {
        int[][] arrn = FINDER_PATTERN_SEQUENCES;
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            int[] arrn2 = arrn[n2];
            if (list.size() <= arrn2.length) {
                int n3;
                block5 : {
                    n3 = 0;
                    while (n3 < list.size()) {
                        if (list.get(n3).getFinderPattern().getValue() == arrn2[n3]) {
                            ++n3;
                            continue;
                        }
                        break block5;
                    }
                    return true;
                }
                n3 = 0;
                if (n3 != 0) {
                    return true;
                }
            }
            ++n2;
        }
        return false;
    }

    private FinderPattern parseFoundFinderPattern(BitArray arrn, int n, boolean bl) {
        int n2;
        int n3;
        int n4;
        if (bl) {
            for (n2 = this.startEnd[0] - 1; n2 >= 0 && !arrn.get(n2); --n2) {
            }
            arrn = this.startEnd;
            n4 = arrn[0] - ++n2;
            n3 = arrn[1];
        } else {
            int[] arrn2 = this.startEnd;
            n2 = arrn2[0];
            n4 = arrn.getNextUnset(arrn2[1] + 1);
            int n5 = this.startEnd[1];
            n3 = n4;
            n4 -= n5;
        }
        arrn = this.getDecodeFinderCounters();
        System.arraycopy(arrn, 0, arrn, 1, arrn.length - 1);
        arrn[0] = n4;
        try {
            n4 = RSSExpandedReader.parseFinderValue(arrn, FINDER_PATTERNS);
        }
        catch (NotFoundException notFoundException) {
            return null;
        }
        return new FinderPattern(n4, new int[]{n2, n3}, n2, n3, n);
    }

    private static void removePartialRows(List<ExpandedPair> list, List<ExpandedRow> object) {
        object = object.iterator();
        while (object.hasNext()) {
            boolean bl;
            block4 : {
                block3 : {
                    boolean bl2;
                    Object object2 = (ExpandedRow)object.next();
                    if (((ExpandedRow)object2).getPairs().size() == list.size()) continue;
                    Iterator<ExpandedPair> iterator2 = ((ExpandedRow)object2).getPairs().iterator();
                    block1 : do {
                        boolean bl3 = iterator2.hasNext();
                        bl2 = false;
                        bl = true;
                        if (!bl3) break block3;
                        object2 = iterator2.next();
                        Iterator<ExpandedPair> iterator3 = list.iterator();
                        while (iterator3.hasNext()) {
                            if (!((ExpandedPair)object2).equals(iterator3.next())) continue;
                            continue block1;
                        }
                        bl = false;
                    } while (bl);
                    bl = bl2;
                    break block4;
                }
                bl = true;
            }
            if (!bl) continue;
            object.remove();
        }
    }

    private static void reverseCounters(int[] arrn) {
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n / 2) {
            int n3 = arrn[n2];
            int n4 = n - n2 - 1;
            arrn[n2] = arrn[n4];
            arrn[n4] = n3;
            ++n2;
        }
    }

    private void storeRow(int n, boolean bl) {
        boolean bl2;
        boolean bl3 = false;
        int n2 = 0;
        boolean bl4 = false;
        do {
            bl2 = bl3;
            if (n2 >= this.rows.size()) break;
            ExpandedRow expandedRow = this.rows.get(n2);
            if (expandedRow.getRowNumber() > n) {
                bl2 = expandedRow.isEquivalent(this.pairs);
                break;
            }
            bl4 = expandedRow.isEquivalent(this.pairs);
            ++n2;
        } while (true);
        if (bl2) return;
        if (bl4) {
            return;
        }
        if (RSSExpandedReader.isPartialRow(this.pairs, this.rows)) {
            return;
        }
        this.rows.add(n2, new ExpandedRow(this.pairs, n, bl));
        RSSExpandedReader.removePartialRows(this.pairs, this.rows);
    }

    DataCharacter decodeDataCharacter(BitArray arrn, FinderPattern finderPattern, boolean bl, boolean bl2) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4;
        int[] arrn2 = this.getDataCharacterCounters();
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        arrn2[4] = 0;
        arrn2[5] = 0;
        arrn2[6] = 0;
        arrn2[7] = 0;
        if (bl2) {
            RSSExpandedReader.recordPatternInReverse((BitArray)arrn, finderPattern.getStartEnd()[0], arrn2);
        } else {
            RSSExpandedReader.recordPattern((BitArray)arrn, finderPattern.getStartEnd()[1], arrn2);
            n3 = arrn2.length - 1;
            for (n = 0; n < n3; ++n, --n3) {
                n4 = arrn2[n];
                arrn2[n] = arrn2[n3];
                arrn2[n3] = n4;
            }
        }
        float f = (float)RSSExpandedReader.count(arrn2) / (float)17;
        float f2 = (float)(finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f;
        if (Math.abs(f - f2) / f2 > 0.3f) throw NotFoundException.getNotFoundInstance();
        int[] arrn3 = this.getOddCounts();
        arrn = this.getEvenCounts();
        float[] arrf = this.getOddRoundingErrors();
        float[] arrf2 = this.getEvenRoundingErrors();
        for (n = 0; n < arrn2.length; ++n) {
            f2 = (float)arrn2[n] * 1.0f / f;
            n4 = (int)(0.5f + f2);
            if (n4 < 1) {
                if (f2 < 0.3f) throw NotFoundException.getNotFoundInstance();
                n3 = 1;
            } else {
                n3 = n4;
                if (n4 > 8) {
                    if (f2 > 8.7f) throw NotFoundException.getNotFoundInstance();
                    n3 = 8;
                }
            }
            n4 = n / 2;
            if ((n & 1) == 0) {
                arrn3[n4] = n3;
                arrf[n4] = f2 - (float)n3;
                continue;
            }
            arrn[n4] = n3;
            arrf2[n4] = f2 - (float)n3;
        }
        this.adjustOddEvenCounts(17);
        n = finderPattern.getValue();
        n3 = bl ? 0 : 2;
        int n5 = n * 4 + n3 + (bl2 ^ true) - 1;
        n3 = 0;
        n = 0;
        for (n4 = arrn3.length - 1; n4 >= 0; n += arrn3[n4], --n4) {
            n2 = n3;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl, bl2)) {
                n2 = WEIGHTS[n5][n4 * 2];
                n2 = n3 + arrn3[n4] * n2;
            }
            n3 = n2;
        }
        n2 = arrn.length - 1;
        n4 = 0;
        do {
            int n6;
            if (n2 < 0) {
                if ((n & 1) != 0) throw NotFoundException.getNotFoundInstance();
                if (n > 13) throw NotFoundException.getNotFoundInstance();
                if (n < 4) throw NotFoundException.getNotFoundInstance();
                n = (13 - n) / 2;
                n6 = SYMBOL_WIDEST[n];
                n2 = RSSUtils.getRSSvalue(arrn3, n6, true);
                n6 = RSSUtils.getRSSvalue(arrn, 9 - n6, false);
                return new DataCharacter(n2 * EVEN_TOTAL_SUBSET[n] + n6 + GSUM[n], n3 + n4);
            }
            n6 = n4;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl, bl2)) {
                n6 = WEIGHTS[n5][n2 * 2 + 1];
                n6 = n4 + arrn[n2] * n6;
            }
            --n2;
            n4 = n6;
        } while (true);
    }

    @Override
    public Result decodeRow(int n, BitArray bitArray, Map<DecodeHintType, ?> object) throws NotFoundException, FormatException {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            return RSSExpandedReader.constructResult(this.decodeRow2pairs(n, bitArray));
        }
        catch (NotFoundException notFoundException) {
            this.pairs.clear();
            this.startFromEven = true;
            return RSSExpandedReader.constructResult(this.decodeRow2pairs(n, bitArray));
        }
    }

    List<ExpandedPair> decodeRow2pairs(int n, BitArray bitArray) throws NotFoundException {
        try {
            do {
                ExpandedPair expandedPair = this.retrieveNextPair(bitArray, this.pairs, n);
                this.pairs.add(expandedPair);
            } while (true);
        }
        catch (NotFoundException notFoundException) {
            if (this.pairs.isEmpty()) throw notFoundException;
            if (this.checkChecksum()) {
                return this.pairs;
            }
            boolean bl = this.rows.isEmpty();
            this.storeRow(n, false);
            if (!(bl ^ true)) throw NotFoundException.getNotFoundInstance();
            List<ExpandedPair> list = this.checkRows(false);
            if (list != null) {
                return list;
            }
            list = this.checkRows(true);
            if (list == null) throw NotFoundException.getNotFoundInstance();
            return list;
        }
    }

    List<ExpandedRow> getRows() {
        return this.rows;
    }

    @Override
    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    ExpandedPair retrieveNextPair(BitArray object, List<ExpandedPair> list, int n) throws NotFoundException {
        boolean bl;
        FinderPattern finderPattern;
        boolean bl2 = list.size() % 2 == 0;
        boolean bl3 = bl2;
        if (this.startFromEven) {
            bl3 = bl2 ^ true;
        }
        int n2 = -1;
        boolean bl4 = true;
        do {
            this.findNextPair((BitArray)object, list, n2);
            finderPattern = this.parseFoundFinderPattern((BitArray)object, n, bl3);
            if (finderPattern == null) {
                n2 = RSSExpandedReader.getNextSecondBar((BitArray)object, this.startEnd[0]);
                bl = bl4;
            } else {
                bl = false;
            }
            bl4 = bl;
        } while (bl);
        DataCharacter dataCharacter = this.decodeDataCharacter((BitArray)object, finderPattern, bl3, true);
        if (!list.isEmpty()) {
            if (list.get(list.size() - 1).mustBeLast()) throw NotFoundException.getNotFoundInstance();
        }
        try {
            object = this.decodeDataCharacter((BitArray)object, finderPattern, bl3, false);
            return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
        }
        catch (NotFoundException notFoundException) {
            object = null;
        }
        return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
    }
}

