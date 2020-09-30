/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class BASE64MailboxDecoder {
    static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
    private static final byte[] pem_convert_array = new byte[256];

    static {
        int n = 0;
        int n2 = 0;
        do {
            if (n2 >= 255) break;
            BASE64MailboxDecoder.pem_convert_array[n2] = (byte)-1;
            ++n2;
        } while (true);
        n2 = n;
        char[] arrc;
        while (n2 < (arrc = pem_array).length) {
            BASE64MailboxDecoder.pem_convert_array[arrc[n2]] = (byte)n2;
            ++n2;
        }
        return;
    }

    protected static int base64decode(char[] arrc, int n, CharacterIterator characterIterator) {
        int n2 = 1;
        int n3 = n;
        n = n2;
        block0 : do {
            n2 = -1;
            int n4 = n;
            do {
                byte by;
                if ((by = (byte)characterIterator.next()) == -1) {
                    return n3;
                }
                if (by == 45) {
                    n = n3;
                    if (n4 == 0) return n;
                    arrc[n3] = (char)38;
                    return n3 + 1;
                }
                int n5 = 0;
                n4 = 0;
                byte by2 = (byte)characterIterator.next();
                n = n3;
                if (by2 == -1) return n;
                if (by2 == 45) {
                    return n3;
                }
                byte[] arrby = pem_convert_array;
                n = arrby[by & 255];
                by2 = arrby[by2 & 255];
                n = (byte)(n << 2 & 252 | by2 >>> 4 & 3);
                if (n2 != -1) {
                    arrc[n3] = (char)(n2 << 8 | n & 255);
                    ++n3;
                    n2 = -1;
                } else {
                    n2 = n & 255;
                }
                by = (byte)characterIterator.next();
                if (by == 61) {
                    n4 = n5;
                    continue;
                }
                n = n3;
                if (by == -1) return n;
                if (by == 45) {
                    return n3;
                }
                by = pem_convert_array[by & 255];
                n = (byte)(by2 << 4 & 240 | by >>> 2 & 15);
                if (n2 != -1) {
                    arrc[n3] = (char)(n2 << 8 | n & 255);
                    ++n3;
                    n2 = -1;
                } else {
                    n2 = n & 255;
                }
                by2 = (byte)characterIterator.next();
                if (by2 == 61) {
                    n4 = n5;
                    continue;
                }
                n = n3;
                if (by2 == -1) return n;
                if (by2 == 45) {
                    return n3;
                }
                n = (byte)(by << 6 & 192 | pem_convert_array[by2 & 255] & 63);
                if (n2 != -1) {
                    arrc[n3] = (char)(n2 << 8 | n & 255);
                    ++n3;
                    n = n4;
                    continue block0;
                }
                n2 = n & 255;
                n4 = n5;
            } while (true);
            break;
        } while (true);
    }

    public static String decode(String string2) {
        if (string2 == null) return string2;
        if (string2.length() == 0) {
            return string2;
        }
        char[] arrc = new char[string2.length()];
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(string2);
        char c = stringCharacterIterator.first();
        boolean bl = false;
        int n = 0;
        do {
            if (c == '\uffff') {
                if (!bl) return string2;
                return new String(arrc, 0, n);
            }
            if (c == '&') {
                n = BASE64MailboxDecoder.base64decode(arrc, n, stringCharacterIterator);
                bl = true;
            } else {
                arrc[n] = c;
                ++n;
            }
            c = stringCharacterIterator.next();
        } while (true);
    }
}

