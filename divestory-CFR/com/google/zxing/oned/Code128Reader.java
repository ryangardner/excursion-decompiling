/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class Code128Reader
extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    static {
        int[] arrn = new int[]{2, 2, 1, 2, 1, 3};
        int[] arrn2 = new int[]{2, 2, 1, 3, 1, 2};
        int[] arrn3 = new int[]{2, 3, 1, 2, 1, 2};
        int[] arrn4 = new int[]{1, 2, 2, 2, 3, 1};
        int[] arrn5 = new int[]{1, 1, 3, 2, 2, 2};
        int[] arrn6 = new int[]{1, 2, 3, 2, 2, 1};
        int[] arrn7 = new int[]{2, 2, 3, 2, 1, 1};
        int[] arrn8 = new int[]{2, 2, 1, 1, 3, 2};
        int[] arrn9 = new int[]{3, 1, 2, 2, 1, 2};
        int[] arrn10 = new int[]{3, 2, 2, 1, 1, 2};
        int[] arrn11 = new int[]{2, 1, 2, 3, 2, 1};
        int[] arrn12 = new int[]{2, 3, 2, 1, 2, 1};
        int[] arrn13 = new int[]{1, 1, 1, 3, 2, 3};
        int[] arrn14 = new int[]{1, 1, 2, 3, 1, 3};
        int[] arrn15 = new int[]{1, 3, 2, 1, 1, 3};
        int[] arrn16 = new int[]{1, 3, 2, 3, 1, 1};
        int[] arrn17 = new int[]{2, 1, 1, 3, 1, 3};
        int[] arrn18 = new int[]{2, 3, 1, 1, 1, 3};
        int[] arrn19 = new int[]{2, 3, 1, 3, 1, 1};
        int[] arrn20 = new int[]{1, 1, 2, 1, 3, 3};
        int[] arrn21 = new int[]{1, 3, 2, 1, 3, 1};
        int[] arrn22 = new int[]{3, 1, 3, 1, 2, 1};
        int[] arrn23 = new int[]{2, 1, 3, 1, 1, 3};
        int[] arrn24 = new int[]{2, 1, 3, 3, 1, 1};
        int[] arrn25 = new int[]{2, 1, 3, 1, 3, 1};
        int[] arrn26 = new int[]{3, 1, 1, 3, 2, 1};
        int[] arrn27 = new int[]{3, 1, 2, 1, 1, 3};
        int[] arrn28 = new int[]{3, 3, 2, 1, 1, 1};
        int[] arrn29 = new int[]{3, 1, 4, 1, 1, 1};
        int[] arrn30 = new int[]{2, 2, 1, 4, 1, 1};
        int[] arrn31 = new int[]{1, 1, 1, 2, 2, 4};
        int[] arrn32 = new int[]{1, 1, 1, 4, 2, 2};
        int[] arrn33 = new int[]{1, 4, 1, 2, 2, 1};
        int[] arrn34 = new int[]{1, 2, 2, 1, 1, 4};
        int[] arrn35 = new int[]{1, 2, 2, 4, 1, 1};
        int[] arrn36 = new int[]{1, 4, 2, 2, 1, 1};
        int[] arrn37 = new int[]{2, 4, 1, 2, 1, 1};
        int[] arrn38 = new int[]{4, 1, 3, 1, 1, 1};
        int[] arrn39 = new int[]{1, 3, 4, 1, 1, 1};
        int[] arrn40 = new int[]{1, 2, 1, 1, 4, 2};
        int[] arrn41 = new int[]{1, 1, 4, 2, 1, 2};
        int[] arrn42 = new int[]{4, 2, 1, 1, 1, 2};
        int[] arrn43 = new int[]{2, 1, 4, 1, 2, 1};
        int[] arrn44 = new int[]{1, 1, 1, 3, 4, 1};
        int[] arrn45 = new int[]{1, 1, 4, 3, 1, 1};
        int[] arrn46 = new int[]{4, 1, 1, 1, 1, 3};
        int[] arrn47 = new int[]{4, 1, 1, 3, 1, 1};
        int[] arrn48 = new int[]{1, 1, 3, 1, 4, 1};
        int[] arrn49 = new int[]{4, 1, 1, 1, 3, 1};
        int[] arrn50 = new int[]{2, 1, 1, 2, 3, 2};
        CODE_PATTERNS = new int[][]{{2, 1, 2, 2, 2, 2}, {2, 2, 2, 1, 2, 2}, {2, 2, 2, 2, 2, 1}, {1, 2, 1, 2, 2, 3}, {1, 2, 1, 3, 2, 2}, {1, 3, 1, 2, 2, 2}, {1, 2, 2, 2, 1, 3}, {1, 2, 2, 3, 1, 2}, {1, 3, 2, 2, 1, 2}, arrn, arrn2, arrn3, {1, 1, 2, 2, 3, 2}, {1, 2, 2, 1, 3, 2}, arrn4, arrn5, {1, 2, 3, 1, 2, 2}, arrn6, arrn7, arrn8, {2, 2, 1, 2, 3, 1}, {2, 1, 3, 2, 1, 2}, {2, 2, 3, 1, 1, 2}, {3, 1, 2, 1, 3, 1}, {3, 1, 1, 2, 2, 2}, {3, 2, 1, 1, 2, 2}, {3, 2, 1, 2, 2, 1}, arrn9, arrn10, {3, 2, 2, 2, 1, 1}, {2, 1, 2, 1, 2, 3}, arrn11, arrn12, arrn13, {1, 3, 1, 1, 2, 3}, {1, 3, 1, 3, 2, 1}, arrn14, arrn15, arrn16, arrn17, arrn18, arrn19, arrn20, {1, 1, 2, 3, 3, 1}, arrn21, {1, 1, 3, 1, 2, 3}, {1, 1, 3, 3, 2, 1}, {1, 3, 3, 1, 2, 1}, arrn22, {2, 1, 1, 3, 3, 1}, {2, 3, 1, 1, 3, 1}, arrn23, arrn24, arrn25, {3, 1, 1, 1, 2, 3}, arrn26, {3, 3, 1, 1, 2, 1}, arrn27, {3, 1, 2, 3, 1, 1}, arrn28, arrn29, arrn30, {4, 3, 1, 1, 1, 1}, arrn31, arrn32, {1, 2, 1, 1, 2, 4}, {1, 2, 1, 4, 2, 1}, {1, 4, 1, 1, 2, 2}, arrn33, {1, 1, 2, 2, 1, 4}, {1, 1, 2, 4, 1, 2}, arrn34, arrn35, {1, 4, 2, 1, 1, 2}, arrn36, arrn37, {2, 2, 1, 1, 1, 4}, arrn38, {2, 4, 1, 1, 1, 2}, arrn39, {1, 1, 1, 2, 4, 2}, arrn40, {1, 2, 1, 2, 4, 1}, arrn41, {1, 2, 4, 1, 1, 2}, {1, 2, 4, 2, 1, 1}, {4, 1, 1, 2, 1, 2}, arrn42, {4, 2, 1, 2, 1, 1}, {2, 1, 2, 1, 4, 1}, arrn43, {4, 1, 2, 1, 2, 1}, {1, 1, 1, 1, 4, 3}, arrn44, {1, 3, 1, 1, 4, 1}, {1, 1, 4, 1, 1, 3}, arrn45, arrn46, arrn47, arrn48, {1, 1, 4, 1, 3, 1}, {3, 1, 1, 1, 4, 1}, arrn49, {2, 1, 1, 4, 1, 2}, {2, 1, 1, 2, 1, 4}, arrn50, {2, 3, 3, 1, 1, 1, 2}};
    }

    private static int decodeCode(BitArray arrn, int[] arrn2, int n) throws NotFoundException {
        Code128Reader.recordPattern((BitArray)arrn, n, arrn2);
        float f = 0.25f;
        int n2 = -1;
        n = 0;
        do {
            if (n >= (arrn = CODE_PATTERNS).length) {
                if (n2 < 0) throw NotFoundException.getNotFoundInstance();
                return n2;
            }
            float f2 = Code128Reader.patternMatchVariance(arrn2, arrn[n], 0.7f);
            float f3 = f;
            if (f2 < f) {
                n2 = n;
                f3 = f2;
            }
            ++n;
            f = f3;
        } while (true);
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int n = bitArray.getSize();
        int n2 = bitArray.getNextSet(0);
        int[] arrn = new int[6];
        int n3 = n2;
        boolean bl = false;
        int n4 = 0;
        while (n2 < n) {
            int n5;
            if (bitArray.get(n2) ^ bl) {
                arrn[n4] = arrn[n4] + 1;
                n5 = n4;
            } else {
                if (n4 == 5) {
                    float f = 0.25f;
                    int n6 = -1;
                    for (n5 = 103; n5 <= 105; ++n5) {
                        float f2 = Code128Reader.patternMatchVariance(arrn, CODE_PATTERNS[n5], 0.7f);
                        float f3 = f;
                        if (f2 < f) {
                            n6 = n5;
                            f3 = f2;
                        }
                        f = f3;
                    }
                    if (n6 >= 0 && bitArray.isRange(Math.max(0, n3 - (n2 - n3) / 2), n3, false)) {
                        return new int[]{n3, n2, n6};
                    }
                    n3 += arrn[0] + arrn[1];
                    System.arraycopy(arrn, 2, arrn, 0, 4);
                    arrn[4] = 0;
                    arrn[5] = 0;
                    n5 = n4 - 1;
                } else {
                    n5 = n4 + 1;
                }
                arrn[n5] = 1;
                bl ^= true;
            }
            ++n2;
            n4 = n5;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Exception decompiling
     */
    @Override
    public Result decodeRow(int var1_1, BitArray var2_2, Map<DecodeHintType, ?> var3_3) throws NotFoundException, FormatException, ChecksumException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CASE]], but top level block is 8[SWITCH]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }
}

