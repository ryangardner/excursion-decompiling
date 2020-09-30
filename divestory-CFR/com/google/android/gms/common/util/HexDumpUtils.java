/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

public final class HexDumpUtils {
    public static String dump(byte[] arrby, int n, int n2, boolean bl) {
        if (arrby == null) return null;
        if (arrby.length == 0) return null;
        if (n < 0) return null;
        if (n2 <= 0) return null;
        if (n + n2 > arrby.length) {
            return null;
        }
        int n3 = 57;
        if (bl) {
            n3 = 75;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * ((n2 + 16 - 1) / 16));
        int n4 = n2;
        n3 = 0;
        int n5 = 0;
        while (n4 > 0) {
            int n6;
            block17 : {
                block16 : {
                    if (n3 == 0) {
                        if (n2 < 65536) {
                            stringBuilder.append(String.format("%04X:", n));
                        } else {
                            stringBuilder.append(String.format("%08X:", n));
                        }
                        n6 = n;
                    } else {
                        n6 = n5;
                        if (n3 == 8) {
                            stringBuilder.append(" -");
                            n6 = n5;
                        }
                    }
                    stringBuilder.append(String.format(" %02X", arrby[n] & 255));
                    n5 = n3 + 1;
                    if (bl && (n5 == 16 || --n4 == 0)) {
                        int n7 = 16 - n5;
                        if (n7 > 0) {
                            for (n3 = 0; n3 < n7; ++n3) {
                                stringBuilder.append("   ");
                            }
                        }
                        if (n7 >= 8) {
                            stringBuilder.append("  ");
                        }
                        stringBuilder.append("  ");
                        for (n3 = 0; n3 < n5; ++n3) {
                            n7 = (char)arrby[n6 + n3];
                            int n8 = n7 >= 32 && n7 <= 126 ? n7 : (n7 = 46);
                            stringBuilder.append((char)n8);
                        }
                    }
                    if (n5 == 16) break block16;
                    n3 = n5;
                    if (n4 != 0) break block17;
                }
                stringBuilder.append('\n');
                n3 = 0;
            }
            ++n;
            n5 = n6;
        }
        return stringBuilder.toString();
    }
}

