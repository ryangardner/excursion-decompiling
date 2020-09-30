/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VINParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VINResultParser
extends ResultParser {
    private static final Pattern AZ09;
    private static final Pattern IOQ;

    static {
        IOQ = Pattern.compile("[IOQ]");
        AZ09 = Pattern.compile("[A-Z0-9]{17}");
    }

    private static char checkChar(int n) {
        if (n < 10) {
            return (char)(n + 48);
        }
        if (n != 10) throw new IllegalArgumentException();
        return 'X';
    }

    private static boolean checkChecksum(CharSequence charSequence) {
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        do {
            if (n >= charSequence.length()) {
                if (charSequence.charAt(8) != VINResultParser.checkChar(n2 % 11)) return bl;
                return true;
            }
            int n3 = n + 1;
            n2 += VINResultParser.vinPositionWeight(n3) * VINResultParser.vinCharValue(charSequence.charAt(n));
            n = n3;
        } while (true);
    }

    private static String countryCode(CharSequence charSequence) {
        char c = charSequence.charAt(0);
        char c2 = charSequence.charAt(1);
        if (c != '9') {
            if (c != 'S') {
                if (c == 'Z') {
                    if (c2 < 'A') return null;
                    if (c2 > 'R') return null;
                    return "IT";
                }
                switch (c) {
                    default: {
                        switch (c) {
                            default: {
                                switch (c) {
                                    default: {
                                        return null;
                                    }
                                    case 'X': {
                                        if (c2 == '0') return "RU";
                                        if (c2 < '3') return null;
                                        if (c2 > '9') return null;
                                        return "RU";
                                    }
                                    case 'W': {
                                        return "DE";
                                    }
                                    case 'V': 
                                }
                                if (c2 >= 'F' && c2 <= 'R') {
                                    return "FR";
                                }
                                if (c2 < 'S') return null;
                                if (c2 > 'W') return null;
                                return "ES";
                            }
                            case 'M': {
                                if (c2 < 'A') return null;
                                if (c2 > 'E') return null;
                                return "IN";
                            }
                            case 'L': {
                                return "CN";
                            }
                            case 'K': {
                                if (c2 < 'L') return null;
                                if (c2 > 'R') return null;
                                return "KO";
                            }
                            case 'J': 
                        }
                        if (c2 < 'A') return null;
                        if (c2 > 'T') return null;
                        return "JP";
                    }
                    case '3': {
                        if (c2 < 'A') return null;
                        if (c2 > 'W') return null;
                        return "MX";
                    }
                    case '2': {
                        return "CA";
                    }
                    case '1': 
                    case '4': 
                    case '5': 
                }
                return "US";
            }
            if (c2 >= 'A' && c2 <= 'M') {
                return "UK";
            }
            if (c2 < 'N') return null;
            if (c2 > 'T') return null;
            return "DE";
        }
        if (c2 >= 'A') {
            if (c2 <= 'E') return "BR";
        }
        if (c2 < '3') return null;
        if (c2 > '9') return null;
        return "BR";
    }

    private static int modelYear(char c) {
        if (c >= 'E' && c <= 'H') {
            return c - 69 + 1984;
        }
        if (c >= 'J' && c <= 'N') {
            return c - 74 + 1988;
        }
        if (c == 'P') {
            return 1993;
        }
        if (c >= 'R' && c <= 'T') {
            return c - 82 + 1994;
        }
        if (c >= 'V' && c <= 'Y') {
            return c - 86 + 1997;
        }
        if (c >= '1' && c <= '9') {
            return c - 49 + 2001;
        }
        if (c < 'A') throw new IllegalArgumentException();
        if (c > 'D') throw new IllegalArgumentException();
        return c - 65 + 2010;
    }

    private static int vinCharValue(char c) {
        if (c >= 'A' && c <= 'I') {
            return c - 65 + 1;
        }
        if (c >= 'J' && c <= 'R') {
            return c - 74 + 1;
        }
        if (c >= 'S' && c <= 'Z') {
            return c - 83 + 2;
        }
        if (c < '0') throw new IllegalArgumentException();
        if (c > '9') throw new IllegalArgumentException();
        return c - 48;
    }

    private static int vinPositionWeight(int n) {
        if (n >= 1 && n <= 7) {
            return 9 - n;
        }
        if (n == 8) {
            return 10;
        }
        if (n == 9) {
            return 0;
        }
        if (n < 10) throw new IllegalArgumentException();
        if (n > 17) throw new IllegalArgumentException();
        return 19 - n;
    }

    @Override
    public VINParsedResult parse(Result object) {
        if (((Result)object).getBarcodeFormat() != BarcodeFormat.CODE_39) {
            return null;
        }
        String string2 = IOQ.matcher((CharSequence)(object = ((Result)object).getText())).replaceAll("").trim();
        if (!AZ09.matcher(string2).matches()) {
            return null;
        }
        try {
            if (!VINResultParser.checkChecksum(string2)) {
                return null;
            }
            object = string2.substring(0, 3);
            return new VINParsedResult(string2, (String)object, string2.substring(3, 9), string2.substring(9, 17), VINResultParser.countryCode((CharSequence)object), string2.substring(3, 8), VINResultParser.modelYear(string2.charAt(9)), string2.charAt(10), string2.substring(11));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }
}

