/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {
    private static final byte[] HB;
    private static final char[] HC;
    private static final int[] sHexValues;
    private static final int[] sInputCodes;
    private static final int[] sInputCodesComment;
    private static final int[] sInputCodesJsNames;
    private static final int[] sInputCodesUTF8;
    private static final int[] sInputCodesUtf8JsNames;
    private static final int[] sInputCodesWS;
    private static final int[] sOutputEscapes128;

    static {
        int n;
        char[] arrc = "0123456789ABCDEF".toCharArray();
        HC = arrc;
        int n2 = arrc.length;
        HB = new byte[n2];
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            CharTypes.HB[n] = (byte)HC[n];
        }
        arrc = new int[256];
        for (n = 0; n < 32; ++n) {
            arrc[n] = -1;
        }
        arrc[34] = '\u0001';
        arrc[92] = '\u0001';
        sInputCodes = arrc;
        n = arrc.length;
        int[] arrn = new int[n];
        System.arraycopy(arrc, 0, arrn, 0, n);
        for (n2 = 128; n2 < 256; ++n2) {
            n = (n2 & 224) == 192 ? 2 : ((n2 & 240) == 224 ? 3 : ((n2 & 248) == 240 ? 4 : -1));
            arrn[n2] = n;
        }
        sInputCodesUTF8 = arrn;
        arrn = new int[256];
        Arrays.fill(arrn, -1);
        for (n = 33; n < 256; ++n) {
            if (!Character.isJavaIdentifierPart((char)n)) continue;
            arrn[n] = 0;
        }
        arrn[64] = 0;
        arrn[35] = 0;
        arrn[42] = 0;
        arrn[45] = 0;
        arrn[43] = 0;
        sInputCodesJsNames = arrn;
        arrc = new int[256];
        System.arraycopy(arrn, 0, arrc, 0, 256);
        Arrays.fill((int[])arrc, 128, 128, 0);
        sInputCodesUtf8JsNames = arrc;
        arrc = new int[256];
        System.arraycopy(sInputCodesUTF8, 128, arrc, 128, 128);
        Arrays.fill((int[])arrc, 0, 32, -1);
        arrc[9] = '\u0000';
        arrc[10] = 10;
        arrc[13] = 13;
        arrc[42] = 42;
        sInputCodesComment = arrc;
        arrc = new int[256];
        System.arraycopy(sInputCodesUTF8, 128, arrc, 128, 128);
        Arrays.fill((int[])arrc, 0, 32, -1);
        arrc[32] = '\u0001';
        arrc[9] = '\u0001';
        arrc[10] = 10;
        arrc[13] = 13;
        arrc[47] = 47;
        arrc[35] = 35;
        sInputCodesWS = arrc;
        arrc = new int[128];
        for (n = 0; n < 32; ++n) {
            arrc[n] = -1;
        }
        arrc[34] = 34;
        arrc[92] = 92;
        arrc[8] = 98;
        arrc[9] = 116;
        arrc[12] = 102;
        arrc[10] = 110;
        arrc[13] = 114;
        sOutputEscapes128 = arrc;
        arrc = new int[256];
        sHexValues = arrc;
        Arrays.fill((int[])arrc, -1);
        n2 = 0;
        do {
            n = n3;
            if (n2 >= 10) {
                while (n < 6) {
                    arrc = sHexValues;
                    arrc[n + 97] = n2 = n + 10;
                    arrc[n + 65] = n2;
                    ++n;
                }
                return;
            }
            CharTypes.sHexValues[n2 + 48] = n2;
            ++n2;
        } while (true);
    }

    public static void appendQuoted(StringBuilder stringBuilder, String string2) {
        int[] arrn = sOutputEscapes128;
        int n = arrn.length;
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            char c = string2.charAt(n3);
            if (c < n && arrn[c] != 0) {
                stringBuilder.append('\\');
                int n4 = arrn[c];
                if (n4 < 0) {
                    stringBuilder.append('u');
                    stringBuilder.append('0');
                    stringBuilder.append('0');
                    stringBuilder.append(HC[c >> 4]);
                    stringBuilder.append(HC[c & 15]);
                } else {
                    stringBuilder.append((char)n4);
                }
            } else {
                stringBuilder.append(c);
            }
            ++n3;
        }
    }

    public static int charToHex(int n) {
        return sHexValues[n & 255];
    }

    public static byte[] copyHexBytes() {
        return (byte[])HB.clone();
    }

    public static char[] copyHexChars() {
        return (char[])HC.clone();
    }

    public static int[] get7BitOutputEscapes() {
        return sOutputEscapes128;
    }

    public static int[] get7BitOutputEscapes(int n) {
        if (n != 34) return AltEscapes.instance.escapesFor(n);
        return sOutputEscapes128;
    }

    public static int[] getInputCodeComment() {
        return sInputCodesComment;
    }

    public static int[] getInputCodeLatin1() {
        return sInputCodes;
    }

    public static int[] getInputCodeLatin1JsNames() {
        return sInputCodesJsNames;
    }

    public static int[] getInputCodeUtf8() {
        return sInputCodesUTF8;
    }

    public static int[] getInputCodeUtf8JsNames() {
        return sInputCodesUtf8JsNames;
    }

    public static int[] getInputCodeWS() {
        return sInputCodesWS;
    }

    private static class AltEscapes {
        public static final AltEscapes instance = new AltEscapes();
        private int[][] _altEscapes = new int[128][];

        private AltEscapes() {
        }

        public int[] escapesFor(int n) {
            int[] arrn;
            int[] arrn2 = arrn = this._altEscapes[n];
            if (arrn != null) return arrn2;
            arrn2 = Arrays.copyOf(sOutputEscapes128, 128);
            if (arrn2[n] == 0) {
                arrn2[n] = -1;
            }
            this._altEscapes[n] = arrn2;
            return arrn2;
        }
    }

}

