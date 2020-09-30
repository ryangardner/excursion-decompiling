/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.pdf417.encoder.Compaction;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED;
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION;
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW;
    private static final byte[] TEXT_PUNCTUATION_RAW;

    static {
        int n;
        byte[] arrby;
        TEXT_MIXED_RAW = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
        TEXT_PUNCTUATION_RAW = new byte[]{59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
        MIXED = new byte[128];
        PUNCTUATION = new byte[128];
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        Arrays.fill(MIXED, (byte)-1);
        int n2 = 0;
        for (n = 0; n < (arrby = TEXT_MIXED_RAW).length; n = (int)((byte)(n + 1))) {
            byte by = arrby[n];
            if (by <= 0) continue;
            PDF417HighLevelEncoder.MIXED[by] = (byte)n;
        }
        Arrays.fill(PUNCTUATION, (byte)-1);
        n = n2;
        while (n < (arrby = TEXT_PUNCTUATION_RAW).length) {
            n2 = arrby[n];
            if (n2 > 0) {
                PDF417HighLevelEncoder.PUNCTUATION[n2] = (byte)n;
            }
            n = (byte)(n + 1);
        }
    }

    private PDF417HighLevelEncoder() {
    }

    private static int determineConsecutiveBinaryCount(String charSequence, int n, Charset object) throws WriterException {
        object = ((Charset)object).newEncoder();
        int n2 = ((String)charSequence).length();
        int n3 = n;
        while (n3 < n2) {
            char c = ((String)charSequence).charAt(n3);
            char c2 = '\u0000';
            char c3 = c;
            do {
                c = c2;
                if (c2 >= '\r') break;
                c = c2++;
                if (!PDF417HighLevelEncoder.isDigit(c3)) break;
                c = n3 + c2;
                if (c >= n2) {
                    c = c2;
                    break;
                }
                c3 = c = (char)((String)charSequence).charAt(c);
            } while (true);
            if (c >= '\r') {
                return n3 - n;
            }
            c3 = ((String)charSequence).charAt(n3);
            if (!((CharsetEncoder)object).canEncode(c3)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Non-encodable character detected: ");
                ((StringBuilder)charSequence).append(c3);
                ((StringBuilder)charSequence).append(" (Unicode: ");
                ((StringBuilder)charSequence).append((int)c3);
                ((StringBuilder)charSequence).append(')');
                throw new WriterException(((StringBuilder)charSequence).toString());
            }
            ++n3;
        }
        return n3 - n;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        int n4 = 0;
        if (n >= n2) return n3;
        n3 = charSequence.charAt(n);
        int n5 = n;
        int n6 = n3;
        n = n4;
        do {
            n3 = n;
            if (!PDF417HighLevelEncoder.isDigit((char)n6)) return n3;
            n3 = n;
            if (n5 >= n2) return n3;
            n3 = n + 1;
            n4 = n5 + 1;
            n = n3;
            n5 = n4;
            if (n4 >= n2) continue;
            n5 = charSequence.charAt(n4);
            n = n3;
            n6 = n5;
            n5 = n4;
        } while (true);
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = n;
        do {
            int n4 = n3;
            if (n3 >= n2) return n4 - n;
            int n5 = charSequence.charAt(n3);
            n4 = 0;
            int n6 = n5;
            while (n4 < 13 && PDF417HighLevelEncoder.isDigit((char)n6) && n3 < n2) {
                int n7 = n4 + 1;
                n3 = n5 = n3 + 1;
                n4 = n7;
                if (n5 >= n2) continue;
                n4 = charSequence.charAt(n5);
                n3 = n5;
                n6 = n4;
                n4 = n7;
            }
            if (n4 >= 13) {
                return n3 - n - n4;
            }
            if (n4 > 0) continue;
            if (!PDF417HighLevelEncoder.isText(charSequence.charAt(n3))) {
                n4 = n3;
                return n4 - n;
            }
            ++n3;
        } while (true);
    }

    private static void encodeBinary(byte[] arrby, int n, int n2, int n3, StringBuilder stringBuilder) {
        int n4 = 1;
        if (n2 == 1 && n3 == 0) {
            stringBuilder.append('\u0391');
        } else {
            n3 = n2 % 6 == 0 ? n4 : 0;
            if (n3 != 0) {
                stringBuilder.append('\u039c');
            } else {
                stringBuilder.append('\u0385');
            }
        }
        if (n2 < 6) {
            n4 = n;
        } else {
            char[] arrc = new char[5];
            n3 = n;
            do {
                n4 = n3;
                if (n + n2 - n3 < 6) break;
                long l = 0L;
                for (n4 = 0; n4 < 6; ++n4) {
                    l = (l << 8) + (long)(arrby[n3 + n4] & 255);
                }
                for (n4 = 0; n4 < 5; l /= 900L, ++n4) {
                    arrc[n4] = (char)(l % 900L);
                }
                for (n4 = 4; n4 >= 0; --n4) {
                    stringBuilder.append(arrc[n4]);
                }
                n3 += 6;
            } while (true);
        }
        while (n4 < n + n2) {
            stringBuilder.append((char)(arrby[n4] & 255));
            ++n4;
        }
    }

    static String encodeHighLevel(String arrby, Compaction arrby2, Charset charset) throws WriterException {
        Charset charset2;
        StringBuilder stringBuilder = new StringBuilder(arrby.length());
        if (charset == null) {
            charset2 = DEFAULT_ENCODING;
        } else {
            charset2 = charset;
            if (!DEFAULT_ENCODING.equals(charset)) {
                CharacterSetECI characterSetECI = CharacterSetECI.getCharacterSetECIByName(charset.name());
                charset2 = charset;
                if (characterSetECI != null) {
                    PDF417HighLevelEncoder.encodingECI(characterSetECI.getValue(), stringBuilder);
                    charset2 = charset;
                }
            }
        }
        int n = arrby.length();
        if (arrby2 == Compaction.TEXT) {
            PDF417HighLevelEncoder.encodeText((CharSequence)arrby, 0, n, stringBuilder, 0);
            return stringBuilder.toString();
        }
        if (arrby2 == Compaction.BYTE) {
            arrby = arrby.getBytes(charset2);
            PDF417HighLevelEncoder.encodeBinary(arrby, 0, arrby.length, 1, stringBuilder);
            return stringBuilder.toString();
        }
        if (arrby2 == Compaction.NUMERIC) {
            stringBuilder.append('\u0386');
            PDF417HighLevelEncoder.encodeNumeric((String)arrby, 0, n, stringBuilder);
            return stringBuilder.toString();
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (n2 < n) {
            int n5 = PDF417HighLevelEncoder.determineConsecutiveDigitCount((CharSequence)arrby, n2);
            if (n5 >= 13) {
                stringBuilder.append('\u0386');
                PDF417HighLevelEncoder.encodeNumeric((String)arrby, n2, n5, stringBuilder);
                n2 += n5;
                n3 = 0;
                n4 = 2;
                continue;
            }
            int n6 = PDF417HighLevelEncoder.determineConsecutiveTextCount((CharSequence)arrby, n2);
            if (n6 < 5 && n5 != n) {
                n5 = n6 = PDF417HighLevelEncoder.determineConsecutiveBinaryCount((String)arrby, n2, charset2);
                if (n6 == 0) {
                    n5 = 1;
                }
                if ((arrby2 = arrby.substring(n2, n5 += n2).getBytes(charset2)).length == 1 && n4 == 0) {
                    PDF417HighLevelEncoder.encodeBinary(arrby2, 0, 1, 0, stringBuilder);
                } else {
                    PDF417HighLevelEncoder.encodeBinary(arrby2, 0, arrby2.length, n4, stringBuilder);
                    n3 = 0;
                    n4 = 1;
                }
                n2 = n5;
                continue;
            }
            n5 = n4;
            if (n4 != 0) {
                stringBuilder.append('\u0384');
                n3 = 0;
                n5 = 0;
            }
            n3 = PDF417HighLevelEncoder.encodeText((CharSequence)arrby, n2, n6, stringBuilder, n3);
            n2 += n6;
            n4 = n5;
        }
        return stringBuilder.toString();
    }

    private static void encodeNumeric(String string2, int n, int n2, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(n2 / 3 + 1);
        BigInteger bigInteger = BigInteger.valueOf(900L);
        BigInteger bigInteger2 = BigInteger.valueOf(0L);
        int n3 = 0;
        while (n3 < n2) {
            BigInteger bigInteger3;
            stringBuilder2.setLength(0);
            int n4 = Math.min(44, n2 - n3);
            Comparable<StringBuilder> comparable = new StringBuilder();
            ((StringBuilder)comparable).append('1');
            int n5 = n + n3;
            ((StringBuilder)comparable).append(string2.substring(n5, n5 + n4));
            comparable = new BigInteger(((StringBuilder)comparable).toString());
            do {
                stringBuilder2.append((char)((BigInteger)comparable).mod(bigInteger).intValue());
                bigInteger3 = ((BigInteger)comparable).divide(bigInteger);
                comparable = bigInteger3;
            } while (!bigInteger3.equals(bigInteger2));
            for (n5 = stringBuilder2.length() - 1; n5 >= 0; --n5) {
                stringBuilder.append(stringBuilder2.charAt(n5));
            }
            n3 += n4;
        }
    }

    private static int encodeText(CharSequence charSequence, int n, int n2, StringBuilder stringBuilder, int n3) {
        char c;
        int n4;
        StringBuilder stringBuilder2 = new StringBuilder(n2);
        int n5 = 0;
        do {
            block23 : {
                block35 : {
                    block32 : {
                        block34 : {
                            block28 : {
                                block33 : {
                                    block19 : {
                                        block31 : {
                                            block30 : {
                                                block29 : {
                                                    block20 : {
                                                        block27 : {
                                                            block26 : {
                                                                block24 : {
                                                                    block25 : {
                                                                        block21 : {
                                                                            block22 : {
                                                                                n4 = n + n5;
                                                                                c = charSequence.charAt(n4);
                                                                                if (n3 == 0) break block19;
                                                                                if (n3 == 1) break block20;
                                                                                if (n3 == 2) break block21;
                                                                                if (!PDF417HighLevelEncoder.isPunctuation(c)) break block22;
                                                                                stringBuilder2.append((char)PUNCTUATION[c]);
                                                                                break block23;
                                                                            }
                                                                            stringBuilder2.append('\u001d');
                                                                            break block24;
                                                                        }
                                                                        if (!PDF417HighLevelEncoder.isMixed(c)) break block25;
                                                                        stringBuilder2.append((char)MIXED[c]);
                                                                        break block23;
                                                                    }
                                                                    if (!PDF417HighLevelEncoder.isAlphaUpper(c)) break block26;
                                                                    stringBuilder2.append('\u001c');
                                                                }
                                                                n3 = 0;
                                                                continue;
                                                            }
                                                            if (!PDF417HighLevelEncoder.isAlphaLower(c)) break block27;
                                                            stringBuilder2.append('\u001b');
                                                            break block28;
                                                        }
                                                        if (++n4 < n2 && PDF417HighLevelEncoder.isPunctuation(charSequence.charAt(n4))) {
                                                            n3 = 3;
                                                            stringBuilder2.append('\u0019');
                                                            continue;
                                                        }
                                                        stringBuilder2.append('\u001d');
                                                        stringBuilder2.append((char)PUNCTUATION[c]);
                                                        break block23;
                                                    }
                                                    if (!PDF417HighLevelEncoder.isAlphaLower(c)) break block29;
                                                    if (c == ' ') {
                                                        stringBuilder2.append('\u001a');
                                                    } else {
                                                        stringBuilder2.append((char)(c - 97));
                                                    }
                                                    break block23;
                                                }
                                                if (!PDF417HighLevelEncoder.isAlphaUpper(c)) break block30;
                                                stringBuilder2.append('\u001b');
                                                stringBuilder2.append((char)(c - 65));
                                                break block23;
                                            }
                                            if (!PDF417HighLevelEncoder.isMixed(c)) break block31;
                                            stringBuilder2.append('\u001c');
                                            break block32;
                                        }
                                        stringBuilder2.append('\u001d');
                                        stringBuilder2.append((char)PUNCTUATION[c]);
                                        break block23;
                                    }
                                    if (!PDF417HighLevelEncoder.isAlphaUpper(c)) break block33;
                                    if (c == ' ') {
                                        stringBuilder2.append('\u001a');
                                    } else {
                                        stringBuilder2.append((char)(c - 65));
                                    }
                                    break block23;
                                }
                                if (!PDF417HighLevelEncoder.isAlphaLower(c)) break block34;
                                stringBuilder2.append('\u001b');
                            }
                            n3 = 1;
                            continue;
                        }
                        if (!PDF417HighLevelEncoder.isMixed(c)) break block35;
                        stringBuilder2.append('\u001c');
                    }
                    n3 = 2;
                    continue;
                }
                stringBuilder2.append('\u001d');
                stringBuilder2.append((char)PUNCTUATION[c]);
            }
            n5 = n4 = n5 + 1;
            if (n4 >= n2) break;
        } while (true);
        n4 = stringBuilder2.length();
        n = 0;
        n2 = 0;
        do {
            if (n >= n4) {
                if (n4 % 2 == 0) return n3;
                stringBuilder.append((char)(n2 * 30 + 29));
                return n3;
            }
            n5 = n % 2 != 0 ? 1 : 0;
            if (n5 != 0) {
                c = (char)(n2 * 30 + stringBuilder2.charAt(n));
                stringBuilder.append(c);
                n2 = c;
            } else {
                n2 = stringBuilder2.charAt(n);
            }
            ++n;
        } while (true);
    }

    private static void encodingECI(int n, StringBuilder stringBuilder) throws WriterException {
        if (n >= 0 && n < 900) {
            stringBuilder.append('\u039f');
            stringBuilder.append((char)n);
            return;
        }
        if (n < 810900) {
            stringBuilder.append('\u039e');
            stringBuilder.append((char)(n / 900 - 1));
            stringBuilder.append((char)(n % 900));
            return;
        }
        if (n < 811800) {
            stringBuilder.append('\u039d');
            stringBuilder.append((char)(810900 - n));
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("ECI number not in valid range from 0..811799, but was ");
        stringBuilder.append(n);
        throw new WriterException(stringBuilder.toString());
    }

    private static boolean isAlphaLower(char c) {
        if (c == ' ') return true;
        if (c < 'a') return false;
        if (c > 'z') return false;
        return true;
    }

    private static boolean isAlphaUpper(char c) {
        if (c == ' ') return true;
        if (c < 'A') return false;
        if (c > 'Z') return false;
        return true;
    }

    private static boolean isDigit(char c) {
        if (c < '0') return false;
        if (c > '9') return false;
        return true;
    }

    private static boolean isMixed(char c) {
        if (MIXED[c] == -1) return false;
        return true;
    }

    private static boolean isPunctuation(char c) {
        if (PUNCTUATION[c] == -1) return false;
        return true;
    }

    private static boolean isText(char c) {
        if (c == '\t') return true;
        if (c == '\n') return true;
        if (c == '\r') return true;
        if (c < ' ') return false;
        if (c > '~') return false;
        return true;
    }
}

