/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;

public final class CodaBarWriter
extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS;
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED;
    private static final char DEFAULT_GUARD;
    private static final char[] START_END_CHARS;

    static {
        char[] arrc;
        char[] arrc2 = arrc = new char[4];
        arrc2[0] = 65;
        arrc2[1] = 66;
        arrc2[2] = 67;
        arrc2[3] = 68;
        START_END_CHARS = arrc;
        ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
        CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
        DEFAULT_GUARD = arrc[0];
    }

    @Override
    public boolean[] encode(String string2) {
        boolean bl;
        Object object;
        int n;
        if (string2.length() < 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append(DEFAULT_GUARD);
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(DEFAULT_GUARD);
            string2 = ((StringBuilder)object).toString();
        } else {
            char c = Character.toUpperCase(string2.charAt(0));
            char c2 = Character.toUpperCase(string2.charAt(string2.length() - 1));
            boolean bl2 = CodaBarReader.arrayContains(START_END_CHARS, c);
            bl = CodaBarReader.arrayContains(START_END_CHARS, c2);
            boolean bl3 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c);
            boolean bl4 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c2);
            if (bl2) {
                if (!bl) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid start/end guards: ");
                    stringBuilder.append(string2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else if (bl3) {
                if (!bl4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid start/end guards: ");
                    stringBuilder.append(string2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else {
                if (bl || bl4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid start/end guards: ");
                    stringBuilder.append(string2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(DEFAULT_GUARD);
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(DEFAULT_GUARD);
                string2 = ((StringBuilder)object).toString();
            }
        }
        int n2 = 20;
        for (n = 1; n < string2.length() - 1; ++n) {
            if (!Character.isDigit(string2.charAt(n)) && string2.charAt(n) != '-' && string2.charAt(n) != '$') {
                if (!CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, string2.charAt(n))) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Cannot encode : '");
                    ((StringBuilder)object).append(string2.charAt(n));
                    ((StringBuilder)object).append('\'');
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                n2 += 10;
                continue;
            }
            n2 += 9;
        }
        object = new boolean[n2 + (string2.length() - 1)];
        int n3 = 0;
        n = 0;
        while (n3 < string2.length()) {
            int n4;
            block23 : {
                block25 : {
                    block24 : {
                        n4 = Character.toUpperCase(string2.charAt(n3));
                        if (n3 == 0) break block24;
                        n2 = n4;
                        if (n3 != string2.length() - 1) break block25;
                    }
                    n2 = n4 != 42 ? (n4 != 69 ? (n4 != 78 ? (n4 != 84 ? (int)n4 : 65) : 66) : 68) : 67;
                }
                for (n4 = 0; n4 < CodaBarReader.ALPHABET.length; ++n4) {
                    if (n2 != CodaBarReader.ALPHABET[n4]) continue;
                    n4 = CodaBarReader.CHARACTER_ENCODINGS[n4];
                    break block23;
                }
                n4 = 0;
            }
            int n5 = 0;
            bl = true;
            n2 = n;
            block3 : do {
                n = 0;
                while (n5 < 7) {
                    object[n2] = bl;
                    ++n2;
                    if ((n4 >> 6 - n5 & 1) != 0 && n != 1) {
                        ++n;
                        continue;
                    }
                    bl ^= true;
                    ++n5;
                    continue block3;
                }
                break;
            } while (true);
            n = n2;
            if (n3 < string2.length() - 1) {
                object[n2] = false;
                n = n2 + 1;
            }
            ++n3;
        }
        return object;
    }
}

