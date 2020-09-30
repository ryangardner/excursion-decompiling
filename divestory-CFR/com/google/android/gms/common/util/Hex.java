/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

public class Hex {
    private static final char[] zza = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] zzb = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String bytesToStringLowercase(byte[] arrby) {
        char[] arrc = new char[arrby.length << 1];
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = arrby[n] & 255;
            int n4 = n2 + 1;
            char[] arrc2 = zzb;
            arrc[n2] = arrc2[n3 >>> 4];
            n2 = n4 + 1;
            arrc[n4] = arrc2[n3 & 15];
            ++n;
        }
        return new String(arrc);
    }

    public static String bytesToStringUppercase(byte[] arrby) {
        return Hex.bytesToStringUppercase(arrby, false);
    }

    public static String bytesToStringUppercase(byte[] arrby, boolean bl) {
        int n = arrby.length;
        StringBuilder stringBuilder = new StringBuilder(n << 1);
        int n2 = 0;
        while (n2 < n) {
            if (bl && n2 == n - 1) {
                if ((arrby[n2] & 255) == 0) return stringBuilder.toString();
            }
            stringBuilder.append(zza[(arrby[n2] & 240) >>> 4]);
            stringBuilder.append(zza[arrby[n2] & 15]);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public static byte[] stringToBytes(String string2) throws IllegalArgumentException {
        int n = string2.length();
        if (n % 2 != 0) throw new IllegalArgumentException("Hex string has odd number of characters");
        byte[] arrby = new byte[n / 2];
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 / 2;
            int n4 = n2 + 2;
            arrby[n3] = (byte)Integer.parseInt(string2.substring(n2, n4), 16);
            n2 = n4;
        }
        return arrby;
    }
}

