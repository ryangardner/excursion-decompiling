/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Arrays;

final class BigendianEncoding {
    private static final String ALPHABET = "0123456789abcdef";
    private static final int ASCII_CHARACTERS = 128;
    static final int BYTE_BASE16 = 2;
    private static final byte[] DECODING;
    private static final char[] ENCODING;
    static final int LONG_BASE16 = 16;
    static final int LONG_BYTES = 8;

    static {
        ENCODING = BigendianEncoding.buildEncodingArray();
        DECODING = BigendianEncoding.buildDecodingArray();
    }

    private BigendianEncoding() {
    }

    private static byte[] buildDecodingArray() {
        byte[] arrby = new byte[128];
        Arrays.fill(arrby, (byte)-1);
        int n = 0;
        while (n < 16) {
            arrby["0123456789abcdef".charAt((int)n)] = (byte)n;
            ++n;
        }
        return arrby;
    }

    private static char[] buildEncodingArray() {
        char[] arrc = new char[512];
        int n = 0;
        while (n < 256) {
            arrc[n] = ALPHABET.charAt(n >>> 4);
            arrc[n | 256] = ALPHABET.charAt(n & 15);
            ++n;
        }
        return arrc;
    }

    static byte byteFromBase16String(CharSequence charSequence, int n) {
        boolean bl = charSequence.length() >= n + 2;
        Utils.checkArgument(bl, "chars too small");
        return BigendianEncoding.decodeByte(charSequence.charAt(n), charSequence.charAt(n + 1));
    }

    private static void byteToBase16(byte by, char[] arrc, int n) {
        by = (byte)(by & 255);
        char[] arrc2 = ENCODING;
        arrc[n] = arrc2[by];
        arrc[n + 1] = arrc2[by | 256];
    }

    static void byteToBase16String(byte by, char[] arrc, int n) {
        BigendianEncoding.byteToBase16(by, arrc, n);
    }

    private static byte decodeByte(char c, char c2) {
        boolean bl = true;
        boolean bl2 = c2 < '' && DECODING[c2] != -1;
        byte[] arrby = new StringBuilder();
        arrby.append("invalid character ");
        arrby.append(c2);
        Utils.checkArgument(bl2, arrby.toString());
        bl2 = c < '' && DECODING[c] != -1 ? bl : false;
        arrby = new StringBuilder();
        arrby.append("invalid character ");
        arrby.append(c);
        Utils.checkArgument(bl2, arrby.toString());
        arrby = DECODING;
        return (byte)(arrby[c] << 4 | arrby[c2]);
    }

    static long longFromBase16String(CharSequence charSequence, int n) {
        boolean bl = charSequence.length() >= n + 16;
        Utils.checkArgument(bl, "chars too small");
        long l = BigendianEncoding.decodeByte(charSequence.charAt(n), charSequence.charAt(n + 1));
        long l2 = BigendianEncoding.decodeByte(charSequence.charAt(n + 2), charSequence.charAt(n + 3));
        long l3 = BigendianEncoding.decodeByte(charSequence.charAt(n + 4), charSequence.charAt(n + 5));
        long l4 = BigendianEncoding.decodeByte(charSequence.charAt(n + 6), charSequence.charAt(n + 7));
        long l5 = BigendianEncoding.decodeByte(charSequence.charAt(n + 8), charSequence.charAt(n + 9));
        long l6 = BigendianEncoding.decodeByte(charSequence.charAt(n + 10), charSequence.charAt(n + 11));
        long l7 = BigendianEncoding.decodeByte(charSequence.charAt(n + 12), charSequence.charAt(n + 13));
        return (long)BigendianEncoding.decodeByte(charSequence.charAt(n + 14), charSequence.charAt(n + 15)) & 255L | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40 | (l4 & 255L) << 32 | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8);
    }

    static long longFromByteArray(byte[] arrby, int n) {
        boolean bl = arrby.length >= n + 8;
        Utils.checkArgument(bl, "array too small");
        long l = arrby[n];
        long l2 = arrby[n + 1];
        long l3 = arrby[n + 2];
        long l4 = arrby[n + 3];
        long l5 = arrby[n + 4];
        long l6 = arrby[n + 5];
        long l7 = arrby[n + 6];
        return (long)arrby[n + 7] & 255L | ((l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40 | (l4 & 255L) << 32 | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8);
    }

    static void longToBase16String(long l, char[] arrc, int n) {
        BigendianEncoding.byteToBase16((byte)(l >> 56 & 255L), arrc, n);
        BigendianEncoding.byteToBase16((byte)(l >> 48 & 255L), arrc, n + 2);
        BigendianEncoding.byteToBase16((byte)(l >> 40 & 255L), arrc, n + 4);
        BigendianEncoding.byteToBase16((byte)(l >> 32 & 255L), arrc, n + 6);
        BigendianEncoding.byteToBase16((byte)(l >> 24 & 255L), arrc, n + 8);
        BigendianEncoding.byteToBase16((byte)(l >> 16 & 255L), arrc, n + 10);
        BigendianEncoding.byteToBase16((byte)(l >> 8 & 255L), arrc, n + 12);
        BigendianEncoding.byteToBase16((byte)(l & 255L), arrc, n + 14);
    }

    static void longToByteArray(long l, byte[] arrby, int n) {
        boolean bl = arrby.length >= n + 8;
        Utils.checkArgument(bl, "array too small");
        arrby[n + 7] = (byte)(l & 255L);
        arrby[n + 6] = (byte)(l >> 8 & 255L);
        arrby[n + 5] = (byte)(l >> 16 & 255L);
        arrby[n + 4] = (byte)(l >> 24 & 255L);
        arrby[n + 3] = (byte)(l >> 32 & 255L);
        arrby[n + 2] = (byte)(l >> 40 & 255L);
        arrby[n + 1] = (byte)(l >> 48 & 255L);
        arrby[n] = (byte)(l >> 56 & 255L);
    }
}

