/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS;
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    static {
        BigInteger[] arrbigInteger;
        PUNCT_CHARS = new char[]{';', '<', '>', '@', '[', '\\', ']', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', '-', '.', '$', '/', '\"', '|', '*', '(', ')', '?', '{', '}', '\''};
        MIXED_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', '-', '.', '$', '/', '+', '%', '*', '=', '^'};
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        Object object = new BigInteger[16];
        EXP900 = object;
        object[0] = BigInteger.ONE;
        DecodedBitStreamParser.EXP900[1] = object = BigInteger.valueOf(900L);
        int n = 2;
        while (n < (arrbigInteger = EXP900).length) {
            arrbigInteger[n] = arrbigInteger[n - 1].multiply((BigInteger)object);
            ++n;
        }
    }

    private DecodedBitStreamParser() {
    }

    private static int byteCompaction(int n, int[] arrn, Charset charset, int n2, StringBuilder stringBuilder) {
        ByteArrayOutputStream byteArrayOutputStream;
        block15 : {
            int n3;
            int n4;
            long l;
            block17 : {
                int n5;
                block16 : {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    n4 = 922;
                    n3 = 923;
                    n5 = 0;
                    if (n == 901) break block16;
                    if (n != 924) break block15;
                    n = n2;
                    n4 = 0;
                    n2 = 0;
                    l = 0L;
                    break block17;
                }
                int[] arrn2 = new int[6];
                int n6 = arrn[n2];
                n = n2 + 1;
                boolean bl = false;
                n2 = n4;
                block0 : do {
                    n4 = 0;
                    long l2 = 0L;
                    while (n < arrn[0] && !bl) {
                        int n7 = n4 + 1;
                        arrn2[n4] = n6;
                        l2 = l2 * 900L + (long)n6;
                        n4 = n + 1;
                        n6 = arrn[n];
                        if (n6 != 900 && n6 != 901 && n6 != 902 && n6 != 924 && n6 != 928 && n6 != n3 && n6 != n2) {
                            if (n7 % 5 == 0 && n7 > 0) {
                                for (n = 0; n < 6; ++n) {
                                    byteArrayOutputStream.write((byte)(l2 >> (5 - n) * 8));
                                    n2 = 922;
                                    n3 = 923;
                                }
                                n = n4;
                                continue block0;
                            }
                            n = n4;
                            n4 = n7;
                            n2 = 922;
                            n3 = 923;
                            continue;
                        }
                        n = n4 - 1;
                        n4 = n7;
                        n2 = 922;
                        n3 = 923;
                        bl = true;
                    }
                    break;
                } while (true);
                if (n == arrn[0] && n6 < 900) {
                    n3 = n4 + 1;
                    arrn2[n4] = n6;
                    n4 = n5;
                } else {
                    n3 = n4;
                    n4 = n5;
                }
                do {
                    n2 = n;
                    if (n4 < n3) {
                        byteArrayOutputStream.write((byte)arrn2[n4]);
                        ++n4;
                        continue;
                    }
                    break block15;
                    break;
                } while (true);
            }
            while (n < arrn[0] && n4 == 0) {
                long l3;
                n3 = n + 1;
                if ((n = arrn[n]) < 900) {
                    l3 = l * 900L + (long)n;
                    n = n3;
                    n3 = ++n2;
                } else if (n != 900 && n != 901 && n != 902 && n != 924 && n != 928 && n != 923 && n != 922) {
                    n = n3;
                    n3 = n2;
                    l3 = l;
                } else {
                    n = n3 - 1;
                    n4 = 1;
                    l3 = l;
                    n3 = n2;
                }
                n2 = n3;
                l = l3;
                if (n3 % 5 != 0) continue;
                n2 = n3;
                l = l3;
                if (n3 <= 0) continue;
                for (n2 = 0; n2 < 6; ++n2) {
                    byteArrayOutputStream.write((byte)(l3 >> (5 - n2) * 8));
                }
                n2 = 0;
                l = 0L;
            }
            n2 = n;
        }
        stringBuilder.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return n2;
    }

    /*
     * Exception decompiling
     */
    static DecoderResult decode(int[] var0, String var1_1) throws FormatException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    private static String decodeBase900toBase10(int[] object, int n) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = bigInteger.toString();
                if (((String)object).charAt(0) != '1') throw FormatException.getFormatInstance();
                return ((String)object).substring(1);
            }
            bigInteger = bigInteger.add(EXP900[n - n2 - 1].multiply(BigInteger.valueOf((long)object[n2])));
            ++n2;
        } while (true);
    }

    private static int decodeMacroBlock(int[] arrn, int n, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        int n2;
        if (n + 2 > arrn[0]) throw FormatException.getFormatInstance();
        Object object = new int[2];
        for (n2 = 0; n2 < 2; ++n2, ++n) {
            object[n2] = arrn[n];
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(DecodedBitStreamParser.decodeBase900toBase10((int[])object, 2)));
        object = new StringBuilder();
        n2 = DecodedBitStreamParser.textCompaction(arrn, n, (StringBuilder)object);
        pDF417ResultMetadata.setFileId(((StringBuilder)object).toString());
        if (arrn[n2] != 923) {
            n = n2;
            if (arrn[n2] != 922) return n;
            pDF417ResultMetadata.setLastSegment(true);
            return n2 + 1;
        }
        n = n2 + 1;
        object = new int[arrn[0] - n];
        boolean bl = false;
        n2 = 0;
        while (n < arrn[0] && !bl) {
            int n3 = n + 1;
            if ((n = arrn[n]) < 900) {
                object[n2] = n;
                n = n3;
                ++n2;
                continue;
            }
            if (n != 922) throw FormatException.getFormatInstance();
            pDF417ResultMetadata.setLastSegment(true);
            n = n3 + 1;
            bl = true;
        }
        pDF417ResultMetadata.setOptionalData(Arrays.copyOf((int[])object, n2));
        return n;
    }

    /*
     * Unable to fully structure code
     */
    private static void decodeTextCompaction(int[] var0, int[] var1_1, int var2_2, StringBuilder var3_3) {
        var4_4 = Mode.ALPHA;
        var5_5 = Mode.ALPHA;
        var6_6 = 0;
        while (var6_6 < var2_2) {
            block28 : {
                block33 : {
                    block32 : {
                        block31 : {
                            block30 : {
                                block29 : {
                                    var7_7 = var0[var6_6];
                                    var8_8 = 1.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[var4_4.ordinal()];
                                    var9_9 = ' ';
                                    switch (var8_8) {
                                        default: {
                                            var10_10 = var4_4;
                                            ** GOTO lbl150
                                        }
                                        case 6: {
                                            if (var7_7 >= 29) ** GOTO lbl16
                                            var9_9 = DecodedBitStreamParser.PUNCT_CHARS[var7_7];
                                            ** GOTO lbl31
lbl16: // 1 sources:
                                            if (var7_7 != 29) ** GOTO lbl19
                                            var10_10 = Mode.ALPHA;
                                            ** GOTO lbl150
lbl19: // 1 sources:
                                            if (var7_7 != 913) ** GOTO lbl23
                                            var3_3.append((char)var1_1[var6_6]);
                                            ** GOTO lbl-1000
lbl23: // 1 sources:
                                            if (var7_7 != 900) ** GOTO lbl-1000
                                            var10_10 = Mode.ALPHA;
                                            ** GOTO lbl150
                                        }
                                        case 5: {
                                            if (var7_7 >= 26) ** GOTO lbl30
                                            var9_9 = (char)(var7_7 + 65);
                                            ** GOTO lbl31
lbl30: // 1 sources:
                                            if (var7_7 != 26) ** GOTO lbl34
lbl31: // 3 sources:
                                            var10_10 = var5_5;
                                            var11_11 = var9_9;
                                            break block28;
lbl34: // 1 sources:
                                            if (var7_7 == 900) {
                                                var10_10 = Mode.ALPHA;
                                            } else lbl-1000: // 3 sources:
                                            {
                                                var10_10 = var5_5;
                                            }
                                            ** GOTO lbl150
                                        }
                                        case 4: {
                                            if (var7_7 >= 29) ** GOTO lbl45
                                            var9_9 = DecodedBitStreamParser.PUNCT_CHARS[var7_7];
                                            var10_10 = var4_4;
                                            var11_11 = var9_9;
                                            break block28;
lbl45: // 1 sources:
                                            if (var7_7 == 29) {
                                                var10_10 = Mode.ALPHA;
                                            } else if (var7_7 == 913) {
                                                var3_3.append((char)var1_1[var6_6]);
                                                var10_10 = var4_4;
                                            } else {
                                                var10_10 = var4_4;
                                                if (var7_7 == 900) {
                                                    var10_10 = Mode.ALPHA;
                                                }
                                            }
                                            ** GOTO lbl150
                                        }
                                        case 3: {
                                            if (var7_7 >= 25) ** GOTO lbl63
                                            var9_9 = DecodedBitStreamParser.MIXED_CHARS[var7_7];
                                            var10_10 = var4_4;
                                            var11_11 = var9_9;
                                            break block28;
lbl63: // 1 sources:
                                            if (var7_7 != 25) ** GOTO lbl66
                                            var10_10 = Mode.PUNCT;
                                            ** GOTO lbl150
lbl66: // 1 sources:
                                            if (var7_7 != 26) ** GOTO lbl70
                                            var10_10 = var4_4;
                                            var11_11 = var9_9;
                                            break block28;
lbl70: // 1 sources:
                                            if (var7_7 != 27) ** GOTO lbl73
                                            var10_10 = Mode.LOWER;
                                            ** GOTO lbl150
lbl73: // 1 sources:
                                            if (var7_7 != 28) ** GOTO lbl76
                                            var10_10 = Mode.ALPHA;
                                            ** GOTO lbl150
lbl76: // 1 sources:
                                            if (var7_7 != 29) ** GOTO lbl79
                                            var10_10 = Mode.PUNCT_SHIFT;
                                            ** GOTO lbl138
lbl79: // 1 sources:
                                            if (var7_7 == 913) {
                                                var3_3.append((char)var1_1[var6_6]);
                                                var10_10 = var4_4;
                                            } else {
                                                var10_10 = var4_4;
                                                if (var7_7 == 900) {
                                                    var10_10 = Mode.ALPHA;
                                                }
                                            }
                                            ** GOTO lbl150
                                        }
                                        case 2: {
                                            if (var7_7 >= 26) ** GOTO lbl92
                                            var9_9 = var7_7 + 97;
                                            break block29;
lbl92: // 1 sources:
                                            if (var7_7 != 26) ** GOTO lbl96
                                            var10_10 = var4_4;
                                            var11_11 = var9_9;
                                            break block28;
lbl96: // 1 sources:
                                            if (var7_7 != 27) ** GOTO lbl99
                                            var10_10 = Mode.ALPHA_SHIFT;
                                            ** GOTO lbl138
lbl99: // 1 sources:
                                            if (var7_7 != 28) ** GOTO lbl102
                                            var10_10 = Mode.MIXED;
                                            ** GOTO lbl150
lbl102: // 1 sources:
                                            if (var7_7 != 29) ** GOTO lbl105
                                            var10_10 = Mode.PUNCT_SHIFT;
                                            ** GOTO lbl138
lbl105: // 1 sources:
                                            if (var7_7 == 913) {
                                                var3_3.append((char)var1_1[var6_6]);
                                                var10_10 = var4_4;
                                            } else {
                                                var10_10 = var4_4;
                                                if (var7_7 == 900) {
                                                    var10_10 = Mode.ALPHA;
                                                }
                                            }
                                            ** GOTO lbl150
                                        }
                                        case 1: 
                                    }
                                    if (var7_7 >= 26) break block30;
                                    var9_9 = var7_7 + 65;
                                }
                                var9_9 = var9_9;
                                var10_10 = var4_4;
                                var11_11 = var9_9;
                                break block28;
                            }
                            if (var7_7 != 26) break block31;
                            var10_10 = var4_4;
                            var11_11 = var9_9;
                            break block28;
                        }
                        if (var7_7 != 27) break block32;
                        var10_10 = Mode.LOWER;
                        ** GOTO lbl150
                    }
                    if (var7_7 != 28) break block33;
                    var10_10 = Mode.MIXED;
                    ** GOTO lbl150
                }
                if (var7_7 == 29) {
                    var10_10 = Mode.PUNCT_SHIFT;
lbl138: // 4 sources:
                    var9_9 = '\u0000';
                    var5_5 = var4_4;
                    var11_11 = var9_9;
                } else {
                    if (var7_7 == 913) {
                        var3_3.append((char)var1_1[var6_6]);
                        var10_10 = var4_4;
                    } else {
                        var10_10 = var4_4;
                        if (var7_7 == 900) {
                            var10_10 = Mode.ALPHA;
                        }
                    }
lbl150: // 22 sources:
                    var11_11 = var9_9 = '\u0000';
                }
            }
            if (var11_11 != '\u0000') {
                var3_3.append(var11_11);
            }
            ++var6_6;
            var4_4 = var10_10;
        }
    }

    private static int numericCompaction(int[] arrn, int n, StringBuilder stringBuilder) throws FormatException {
        int[] arrn2 = new int[15];
        boolean bl = false;
        int n2 = 0;
        int n3 = n;
        n = n2;
        while (n3 < arrn[0]) {
            int n4;
            block7 : {
                int n5;
                block8 : {
                    block6 : {
                        if (bl) return n3;
                        n5 = n3 + 1;
                        n4 = arrn[n3];
                        if (n5 == arrn[0]) {
                            bl = true;
                        }
                        if (n4 >= 900) break block6;
                        arrn2[n] = n4;
                        n2 = n + 1;
                        n3 = n5;
                        break block7;
                    }
                    if (n4 == 900 || n4 == 901 || n4 == 924 || n4 == 928 || n4 == 923) break block8;
                    n2 = n;
                    n3 = n5;
                    if (n4 != 922) break block7;
                }
                n3 = n5 - 1;
                bl = true;
                n2 = n;
            }
            if (n2 % 15 != 0 && n4 != 902) {
                n = n2;
                if (!bl) continue;
            }
            n = n2;
            if (n2 <= 0) continue;
            stringBuilder.append(DecodedBitStreamParser.decodeBase900toBase10(arrn2, n2));
            n = 0;
        }
        return n3;
    }

    /*
     * Exception decompiling
     */
    private static int textCompaction(int[] var0, int var1_1, StringBuilder var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    private static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* enum */ Mode ALPHA;
        public static final /* enum */ Mode ALPHA_SHIFT;
        public static final /* enum */ Mode LOWER;
        public static final /* enum */ Mode MIXED;
        public static final /* enum */ Mode PUNCT;
        public static final /* enum */ Mode PUNCT_SHIFT;

        static {
            Mode mode;
            ALPHA = new Mode();
            LOWER = new Mode();
            MIXED = new Mode();
            PUNCT = new Mode();
            ALPHA_SHIFT = new Mode();
            PUNCT_SHIFT = mode = new Mode();
            $VALUES = new Mode[]{ALPHA, LOWER, MIXED, PUNCT, ALPHA_SHIFT, mode};
        }

        public static Mode valueOf(String string2) {
            return Enum.valueOf(Mode.class, string2);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

}

