/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Arrays;
import java.util.List;

public final class Decoder {
    private static final String[] DIGIT_TABLE;
    private static final String[] LOWER_TABLE;
    private static final String[] MIXED_TABLE;
    private static final String[] PUNCT_TABLE;
    private static final String[] UPPER_TABLE;
    private AztecDetectorResult ddata;

    static {
        UPPER_TABLE = new String[]{"CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        LOWER_TABLE = new String[]{"CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        MIXED_TABLE = new String[]{"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
        PUNCT_TABLE = new String[]{"", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
        DIGIT_TABLE = new String[]{"CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
    }

    private boolean[] correctBits(boolean[] object) throws FormatException {
        GenericGF genericGF;
        int n;
        int n2;
        int n3 = this.ddata.getNbLayers();
        int n4 = 8;
        if (n3 <= 2) {
            n4 = 6;
            genericGF = GenericGF.AZTEC_DATA_6;
        } else if (this.ddata.getNbLayers() <= 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
        } else if (this.ddata.getNbLayers() <= 22) {
            n4 = 10;
            genericGF = GenericGF.AZTEC_DATA_10;
        } else {
            n4 = 12;
            genericGF = GenericGF.AZTEC_DATA_12;
        }
        int n5 = this.ddata.getNbDatablocks();
        int n6 = ((boolean[])object).length / n4;
        if (n6 < n5) throw FormatException.getFormatInstance();
        int n7 = ((boolean[])object).length % n4;
        int[] arrn = new int[n6];
        for (n3 = 0; n3 < n6; ++n3, n7 += n4) {
            arrn[n3] = Decoder.readCode((boolean[])object, n7, n4);
        }
        try {
            object = new ReedSolomonDecoder(genericGF);
            ((ReedSolomonDecoder)object).decode(arrn, n6 - n5);
            n2 = (1 << n4) - 1;
            n6 = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw FormatException.getFormatInstance(reedSolomonException);
        }
        for (n7 = 0; n7 < n5; ++n7) {
            block18 : {
                block17 : {
                    n = arrn[n7];
                    if (n == 0) throw FormatException.getFormatInstance();
                    if (n == n2) throw FormatException.getFormatInstance();
                    if (n == 1) break block17;
                    n3 = n6;
                    if (n != n2 - 1) break block18;
                }
                n3 = n6 + 1;
            }
            n6 = n3;
        }
        object = new boolean[n5 * n4 - n6];
        n6 = 0;
        n3 = 0;
        while (n6 < n5) {
            boolean bl;
            int n8 = arrn[n6];
            if (n8 == 1 || n8 == n2 - 1) {
                bl = n8 > 1;
                Arrays.fill((boolean[])object, n3, n3 + n4 - 1, bl);
                n7 = n3 + (n4 - 1);
            } else {
                n = n4 - 1;
                do {
                    n7 = ++n3;
                    if (n < 0) break;
                    bl = (1 << n & n8) != 0;
                    object[n3] = bl;
                    --n;
                } while (true);
            }
            ++n6;
            n3 = n7;
        }
        return object;
    }

    private static String getCharacter(Table table, int n) {
        int n2 = 1.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[table.ordinal()];
        if (n2 == 1) return UPPER_TABLE[n];
        if (n2 == 2) return LOWER_TABLE[n];
        if (n2 == 3) return MIXED_TABLE[n];
        if (n2 == 4) return PUNCT_TABLE[n];
        if (n2 != 5) throw new IllegalStateException("Bad table");
        return DIGIT_TABLE[n];
    }

    private static String getEncodedData(boolean[] arrbl) {
        int n = arrbl.length;
        Table table = Table.UPPER;
        Object object = Table.UPPER;
        StringBuilder stringBuilder = new StringBuilder(20);
        int n2 = 0;
        while (n2 < n) {
            Object object2;
            block11 : {
                int n3;
                int n4;
                int n5;
                block10 : {
                    block9 : {
                        if (object != Table.BINARY) break block9;
                        if (n - n2 < 5) {
                            return stringBuilder.toString();
                        }
                        n4 = Decoder.readCode(arrbl, n2, 5);
                        n3 = n2 + 5;
                        n5 = n4;
                        n2 = n3;
                        if (n4 == 0) {
                            if (n - n3 < 11) {
                                return stringBuilder.toString();
                            }
                            n5 = Decoder.readCode(arrbl, n3, 11) + 31;
                            n2 = n3 + 11;
                        }
                        break block10;
                    }
                    n3 = object == Table.DIGIT ? 4 : 5;
                    if (n - n2 < n3) {
                        return stringBuilder.toString();
                    }
                    n5 = Decoder.readCode(arrbl, n2, n3);
                    n2 += n3;
                    object2 = Decoder.getCharacter(object, n5);
                    if (((String)object2).startsWith("CTRL_")) {
                        object = Decoder.getTable(((String)object2).charAt(5));
                        if (((String)object2).charAt(6) != 'L') continue;
                    } else {
                        stringBuilder.append((String)object2);
                        object = table;
                    }
                    break block11;
                }
                n4 = 0;
                n3 = n2;
                do {
                    object = table;
                    n2 = n3;
                    if (n4 >= n5) break;
                    if (n - n3 < 8) {
                        n2 = n;
                        object = table;
                        break;
                    }
                    stringBuilder.append((char)Decoder.readCode(arrbl, n3, 8));
                    n3 += 8;
                    ++n4;
                } while (true);
            }
            object2 = object;
            table = object;
            object = object2;
        }
        return stringBuilder.toString();
    }

    private static Table getTable(char c) {
        if (c == 'B') return Table.BINARY;
        if (c == 'D') return Table.DIGIT;
        if (c == 'P') return Table.PUNCT;
        if (c == 'L') return Table.LOWER;
        if (c == 'M') return Table.MIXED;
        return Table.UPPER;
    }

    public static String highLevelDecode(boolean[] arrbl) {
        return Decoder.getEncodedData(arrbl);
    }

    private static int readCode(boolean[] arrbl, int n, int n2) {
        int n3 = 0;
        int n4 = n;
        while (n4 < n + n2) {
            int n5;
            n3 = n5 = n3 << 1;
            if (arrbl[n4]) {
                n3 = n5 | 1;
            }
            ++n4;
        }
        return n3;
    }

    private static int totalBitsInLayer(int n, boolean bl) {
        int n2;
        if (bl) {
            n2 = 88;
            return (n2 + n * 16) * n;
        }
        n2 = 112;
        return (n2 + n * 16) * n;
    }

    public DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        this.ddata = aztecDetectorResult;
        return new DecoderResult(null, Decoder.getEncodedData(this.correctBits(this.extractBits(aztecDetectorResult.getBits()))), null, null);
    }

    boolean[] extractBits(BitMatrix bitMatrix) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = this.ddata.isCompact();
        int n5 = this.ddata.getNbLayers();
        int n6 = bl ? n5 * 4 + 11 : n5 * 4 + 14;
        int[] arrn = new int[n6];
        boolean[] arrbl = new boolean[Decoder.totalBitsInLayer(n5, bl)];
        if (bl) {
            for (n2 = 0; n2 < n6; ++n2) {
                arrn[n2] = n2;
            }
        } else {
            n4 = n6 / 2;
            n3 = (n6 + 1 + (n4 - 1) / 15 * 2) / 2;
            for (n2 = 0; n2 < n4; ++n2) {
                n = n2 / 15 + n2;
                arrn[n4 - n2 - 1] = n3 - n - 1;
                arrn[n4 + n2] = n + n3 + 1;
            }
        }
        n2 = 0;
        n4 = 0;
        while (n2 < n5) {
            n3 = (n5 - n2) * 4;
            n3 = bl ? (n3 += 9) : (n3 += 12);
            int n7 = n2 * 2;
            int n8 = n6 - 1 - n7;
            for (n = 0; n < n3; ++n) {
                int n9 = n * 2;
                for (int i = 0; i < 2; ++i) {
                    int n10 = n7 + i;
                    int n11 = arrn[n10];
                    int n12 = n7 + n;
                    arrbl[n4 + n9 + i] = bitMatrix.get(n11, arrn[n12]);
                    n11 = arrn[n12];
                    n12 = n8 - i;
                    arrbl[n3 * 2 + n4 + n9 + i] = bitMatrix.get(n11, arrn[n12]);
                    n11 = arrn[n12];
                    n12 = n8 - n;
                    arrbl[n3 * 4 + n4 + n9 + i] = bitMatrix.get(n11, arrn[n12]);
                    arrbl[n3 * 6 + n4 + n9 + i] = bitMatrix.get(arrn[n12], arrn[n10]);
                }
            }
            n4 += n3 * 8;
            ++n2;
        }
        return arrbl;
    }

    private static final class Table
    extends Enum<Table> {
        private static final /* synthetic */ Table[] $VALUES;
        public static final /* enum */ Table BINARY;
        public static final /* enum */ Table DIGIT;
        public static final /* enum */ Table LOWER;
        public static final /* enum */ Table MIXED;
        public static final /* enum */ Table PUNCT;
        public static final /* enum */ Table UPPER;

        static {
            Table table;
            UPPER = new Table();
            LOWER = new Table();
            MIXED = new Table();
            DIGIT = new Table();
            PUNCT = new Table();
            BINARY = table = new Table();
            $VALUES = new Table[]{UPPER, LOWER, MIXED, DIGIT, PUNCT, table};
        }

        public static Table valueOf(String string2) {
            return Enum.valueOf(Table.class, string2);
        }

        public static Table[] values() {
            return (Table[])$VALUES.clone();
        }
    }

}

