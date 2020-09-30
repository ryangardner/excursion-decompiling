/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import java.util.StringTokenizer;
import org.apache.http.client.utils.Idn;

public class Rfc3492Idn
implements Idn {
    private static final String ACE_PREFIX = "xn--";
    private static final int base = 36;
    private static final int damp = 700;
    private static final char delimiter = '-';
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final int skew = 38;
    private static final int tmax = 26;
    private static final int tmin = 1;

    private int adapt(int n, int n2, boolean bl) {
        n = bl ? (n /= 700) : (n /= 2);
        n2 = n + n / n2;
        n = 0;
        while (n2 > 455) {
            n2 /= 35;
            n += 36;
        }
        return n + n2 * 36 / (n2 + 38);
    }

    private int digit(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 65;
        }
        if (c >= 'a' && c <= 'z') {
            return c - 97;
        }
        if (c >= '0' && c <= '9') {
            return c - 48 + 26;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal digit: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected String decode(String string2) {
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        int n = string2.lastIndexOf(45);
        int n2 = 128;
        int n3 = 72;
        String string3 = string2;
        if (n != -1) {
            stringBuilder.append(string2.subSequence(0, n));
            string3 = string2.substring(n + 1);
        }
        n = 0;
        string2 = string3;
        block0 : while (string2.length() > 0) {
            int n4 = 36;
            int n5 = n;
            int n6 = 1;
            do {
                int n7;
                block7 : {
                    block6 : {
                        if (string2.length() == 0) break block6;
                        char c = string2.charAt(0);
                        string2 = string2.substring(1);
                        int n8 = this.digit(c);
                        n7 = n5 + n8 * n6;
                        n5 = n4 <= n3 + 1 ? 1 : (n4 >= n3 + 26 ? 26 : n4 - n3);
                        if (n8 >= n5) break block7;
                        n5 = n7;
                    }
                    n3 = stringBuilder.length();
                    boolean bl = n == 0;
                    n3 = this.adapt(n5 - n, n3 + 1, bl);
                    n = n5 % (stringBuilder.length() + 1);
                    stringBuilder.insert(n, (char)(n2 += n5 / (stringBuilder.length() + 1)));
                    ++n;
                    continue block0;
                }
                n6 *= 36 - n5;
                n4 += 36;
                n5 = n7;
            } while (true);
            break;
        }
        return stringBuilder.toString();
    }

    @Override
    public String toUnicode(String string2) {
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        StringTokenizer stringTokenizer = new StringTokenizer(string2, ".");
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            if (stringBuilder.length() > 0) {
                stringBuilder.append('.');
            }
            string2 = string3;
            if (string3.startsWith(ACE_PREFIX)) {
                string2 = this.decode(string3.substring(4));
            }
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }
}

