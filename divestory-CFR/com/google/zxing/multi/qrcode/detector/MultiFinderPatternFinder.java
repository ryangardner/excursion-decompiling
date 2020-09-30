/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

final class MultiFinderPatternFinder
extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    MultiFinderPatternFinder(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    /*
     * Exception decompiling
     */
    private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
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

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> arrfinderPattern) throws NotFoundException {
        int n;
        int n2 = 0;
        int n3 = arrfinderPattern != null && arrfinderPattern.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl = arrfinderPattern != null && arrfinderPattern.containsKey((Object)DecodeHintType.PURE_BARCODE);
        arrfinderPattern = this.getImage();
        int n4 = arrfinderPattern.getHeight();
        int n5 = arrfinderPattern.getWidth();
        int n6 = (int)((float)n4 / 228.0f * 3.0f);
        if (n6 < 3 || n3 != 0) {
            n6 = 3;
        }
        Object object = new int[5];
        for (int i = n6 - 1; i < n4; i += n6) {
            object[0] = 0;
            object[1] = 0;
            object[2] = 0;
            object[3] = 0;
            object[4] = false;
            n3 = 0;
            for (int j = 0; j < n5; ++j) {
                if (arrfinderPattern.get(j, i)) {
                    n = n3;
                    if ((n3 & 1) == 1) {
                        n = n3 + 1;
                    }
                    object[n] = object[n] + true;
                    n3 = n;
                    continue;
                }
                if ((n3 & 1) == 0) {
                    if (n3 == 4) {
                        if (MultiFinderPatternFinder.foundPatternCross((int[])object) && this.handlePossibleCenter((int[])object, i, j, bl)) {
                            object[0] = false;
                            object[1] = false;
                            object[2] = false;
                            object[3] = false;
                            object[4] = false;
                            n3 = 0;
                            continue;
                        }
                        object[0] = object[2];
                        object[1] = object[3];
                        object[2] = object[4];
                        object[3] = true;
                        object[4] = false;
                        n3 = 3;
                        continue;
                    }
                    object[++n3] = object[n3] + true;
                    continue;
                }
                object[n3] = object[n3] + true;
            }
            if (!MultiFinderPatternFinder.foundPatternCross((int[])object)) continue;
            this.handlePossibleCenter((int[])object, i, n5, bl);
        }
        arrfinderPattern = this.selectMutipleBestPatterns();
        object = new ArrayList();
        n = arrfinderPattern.length;
        n3 = n2;
        do {
            if (n3 >= n) {
                if (!object.isEmpty()) return object.toArray(new FinderPatternInfo[object.size()]);
                return EMPTY_RESULT_ARRAY;
            }
            ResultPoint[] arrresultPoint = arrfinderPattern[n3];
            ResultPoint.orderBestPatterns(arrresultPoint);
            object.add(new FinderPatternInfo((FinderPattern[])arrresultPoint));
            ++n3;
        } while (true);
    }

    private static final class ModuleSizeComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private ModuleSizeComparator() {
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            double d = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
            if (d < 0.0) {
                return -1;
            }
            if (!(d > 0.0)) return 0;
            return 1;
        }
    }

}

