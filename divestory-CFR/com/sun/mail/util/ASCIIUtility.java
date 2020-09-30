/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASCIIUtility {
    private ASCIIUtility() {
    }

    public static byte[] getBytes(InputStream arrby) throws IOException {
        if (arrby instanceof ByteArrayInputStream) {
            int n = arrby.available();
            byte[] arrby2 = new byte[n];
            arrby.read(arrby2, 0, n);
            return arrby2;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby3 = new byte[1024];
        int n;
        while ((n = arrby.read(arrby3, 0, 1024)) != -1) {
            byteArrayOutputStream.write(arrby3, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getBytes(String arrc) {
        arrc = arrc.toCharArray();
        int n = arrc.length;
        byte[] arrby = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            arrby[n2] = (byte)arrc[n2];
            ++n2;
        }
        return arrby;
    }

    public static int parseInt(byte[] arrby, int n, int n2) throws NumberFormatException {
        return ASCIIUtility.parseInt(arrby, n, n2, 10);
    }

    public static int parseInt(byte[] arrby, int n, int n2, int n3) throws NumberFormatException {
        boolean bl;
        int n4;
        int n5;
        if (arrby == null) throw new NumberFormatException("null");
        if (n2 <= n) throw new NumberFormatException("illegal number");
        int n6 = arrby[n];
        int n7 = 0;
        if (n6 == 45) {
            n5 = Integer.MIN_VALUE;
            n4 = n + 1;
            bl = true;
        } else {
            n5 = -2147483647;
            n4 = n;
            bl = false;
        }
        int n8 = n5 / n3;
        n6 = n4;
        if (n4 < n2) {
            n6 = Character.digit((char)arrby[n4], n3);
            if (n6 < 0) {
                StringBuilder stringBuilder = new StringBuilder("illegal number: ");
                stringBuilder.append(ASCIIUtility.toString(arrby, n, n2));
                throw new NumberFormatException(stringBuilder.toString());
            }
            n7 = -n6;
            n6 = n4 + 1;
        }
        do {
            if (n6 >= n2) {
                if (!bl) return -n7;
                if (n6 <= n + 1) throw new NumberFormatException("illegal number");
                return n7;
            }
            n4 = Character.digit((char)arrby[n6], n3);
            if (n4 < 0) throw new NumberFormatException("illegal number");
            if (n7 < n8) throw new NumberFormatException("illegal number");
            if ((n7 *= n3) < n5 + n4) throw new NumberFormatException("illegal number");
            n7 -= n4;
            ++n6;
        } while (true);
    }

    public static long parseLong(byte[] arrby, int n, int n2) throws NumberFormatException {
        return ASCIIUtility.parseLong(arrby, n, n2, 10);
    }

    public static long parseLong(byte[] arrby, int n, int n2, int n3) throws NumberFormatException {
        long l;
        boolean bl;
        int n4 = n;
        int n5 = n2;
        if (arrby == null) throw new NumberFormatException("null");
        long l2 = 0L;
        if (n5 <= n4) throw new NumberFormatException("illegal number");
        if (arrby[n4] == 45) {
            ++n4;
            l = Long.MIN_VALUE;
            bl = true;
        } else {
            l = -9223372036854775807L;
            bl = false;
        }
        long l3 = n3;
        long l4 = l / l3;
        int n6 = n4;
        if (n4 < n5) {
            n6 = Character.digit((char)arrby[n4], n3);
            if (n6 < 0) {
                StringBuilder stringBuilder = new StringBuilder("illegal number: ");
                stringBuilder.append(ASCIIUtility.toString(arrby, n, n2));
                throw new NumberFormatException(stringBuilder.toString());
            }
            l2 = -n6;
            n6 = n4 + 1;
        }
        do {
            if (n6 >= n2) {
                if (!bl) return -l2;
                if (n6 <= n + 1) throw new NumberFormatException("illegal number");
                return l2;
            }
            n4 = Character.digit((char)arrby[n6], n3);
            if (n4 < 0) throw new NumberFormatException("illegal number");
            if (l2 < l4) throw new NumberFormatException("illegal number");
            long l5 = n4;
            if ((l2 *= l3) < l + l5) throw new NumberFormatException("illegal number");
            l2 -= l5;
            ++n6;
        } while (true);
    }

    public static String toString(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.available();
        char[] arrc = new char[n];
        byte[] arrby = new byte[n];
        int n2 = 0;
        byteArrayInputStream.read(arrby, 0, n);
        while (n2 < n) {
            arrc[n2] = (char)(arrby[n2] & 255);
            ++n2;
        }
        return new String(arrc);
    }

    public static String toString(byte[] arrby, int n, int n2) {
        int n3 = n2 - n;
        char[] arrc = new char[n3];
        n2 = 0;
        while (n2 < n3) {
            arrc[n2] = (char)(arrby[n] & 255);
            ++n2;
            ++n;
        }
        return new String(arrc);
    }
}

