/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import java.math.BigDecimal;

public final class NumberInput {
    static final long L_BILLION = 1000000000L;
    static final String MAX_LONG_STR;
    static final String MIN_LONG_STR_NO_SIGN;
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    static {
        MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
        MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
    }

    private static NumberFormatException _badBD(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value \"");
        stringBuilder.append(string2);
        stringBuilder.append("\" can not be represented as BigDecimal");
        return new NumberFormatException(stringBuilder.toString());
    }

    public static boolean inLongRange(String string2, boolean bl) {
        String string3 = bl ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n = string3.length();
        int n2 = string2.length();
        bl = true;
        if (n2 < n) {
            return true;
        }
        if (n2 > n) {
            return false;
        }
        n2 = 0;
        while (n2 < n) {
            int n3 = string2.charAt(n2) - string3.charAt(n2);
            if (n3 != 0) {
                if (n3 >= 0) return false;
                return bl;
            }
            ++n2;
        }
        return true;
    }

    public static boolean inLongRange(char[] arrc, int n, int n2, boolean bl) {
        String string2 = bl ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n3 = string2.length();
        bl = true;
        if (n2 < n3) {
            return true;
        }
        if (n2 > n3) {
            return false;
        }
        n2 = 0;
        while (n2 < n3) {
            int n4 = arrc[n + n2] - string2.charAt(n2);
            if (n4 != 0) {
                if (n4 >= 0) return false;
                return bl;
            }
            ++n2;
        }
        return true;
    }

    public static double parseAsDouble(String string2, double d) {
        if (string2 == null) {
            return d;
        }
        if ((string2 = string2.trim()).length() == 0) {
            return d;
        }
        try {
            return NumberInput.parseDouble(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return d;
        }
    }

    public static int parseAsInt(String string2, int n) {
        if (string2 == null) {
            return n;
        }
        String string3 = string2.trim();
        int n2 = string3.length();
        if (n2 == 0) {
            return n;
        }
        int n3 = 0;
        int n4 = n2;
        int n5 = n3;
        string2 = string3;
        if (n2 > 0) {
            char c = string3.charAt(0);
            if (c == '+') {
                string2 = string3.substring(1);
                n4 = string2.length();
                n5 = n3;
            } else {
                n4 = n2;
                n5 = n3;
                string2 = string3;
                if (c == '-') {
                    n5 = 1;
                    string2 = string3;
                    n4 = n2;
                }
            }
        }
        while (n5 < n4) {
            double d;
            n2 = string2.charAt(n5);
            if (n2 <= 57 && n2 >= 48) {
                ++n5;
                continue;
            }
            try {
                d = NumberInput.parseDouble(string2);
            }
            catch (NumberFormatException numberFormatException) {
                return n;
            }
            return (int)d;
        }
        try {
            return Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static long parseAsLong(String string2, long l) {
        if (string2 == null) {
            return l;
        }
        String string3 = string2.trim();
        int n = string3.length();
        if (n == 0) {
            return l;
        }
        int n2 = 0;
        int n3 = n;
        int n4 = n2;
        string2 = string3;
        if (n > 0) {
            char c = string3.charAt(0);
            if (c == '+') {
                string2 = string3.substring(1);
                n3 = string2.length();
                n4 = n2;
            } else {
                n3 = n;
                n4 = n2;
                string2 = string3;
                if (c == '-') {
                    n4 = 1;
                    string2 = string3;
                    n3 = n;
                }
            }
        }
        while (n4 < n3) {
            double d;
            n = string2.charAt(n4);
            if (n <= 57 && n >= 48) {
                ++n4;
                continue;
            }
            try {
                d = NumberInput.parseDouble(string2);
            }
            catch (NumberFormatException numberFormatException) {
                return l;
            }
            return (long)d;
        }
        try {
            return Long.parseLong(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    public static BigDecimal parseBigDecimal(String string2) throws NumberFormatException {
        try {
            return new BigDecimal(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw NumberInput._badBD(string2);
        }
    }

    public static BigDecimal parseBigDecimal(char[] arrc) throws NumberFormatException {
        return NumberInput.parseBigDecimal(arrc, 0, arrc.length);
    }

    public static BigDecimal parseBigDecimal(char[] arrc, int n, int n2) throws NumberFormatException {
        try {
            return new BigDecimal(arrc, n, n2);
        }
        catch (NumberFormatException numberFormatException) {
            throw NumberInput._badBD(new String(arrc, n, n2));
        }
    }

    public static double parseDouble(String string2) throws NumberFormatException {
        if (!NASTY_SMALL_DOUBLE.equals(string2)) return Double.parseDouble(string2);
        return Double.MIN_VALUE;
    }

    public static int parseInt(String string2) {
        int n;
        boolean bl = false;
        int n2 = string2.charAt(0);
        int n3 = string2.length();
        int n4 = 1;
        if (n2 == 45) {
            bl = true;
        }
        if (bl) {
            if (n3 == 1) return Integer.parseInt(string2);
            if (n3 > 10) {
                return Integer.parseInt(string2);
            }
            n2 = string2.charAt(1);
            n4 = 2;
        } else if (n3 > 9) {
            return Integer.parseInt(string2);
        }
        if (n2 > 57) return Integer.parseInt(string2);
        if (n2 < 48) {
            return Integer.parseInt(string2);
        }
        n2 = n = n2 - 48;
        if (n4 < n3) {
            int n5 = n4 + 1;
            n2 = string2.charAt(n4);
            if (n2 > 57) return Integer.parseInt(string2);
            if (n2 < 48) {
                return Integer.parseInt(string2);
            }
            n2 = n = n * 10 + (n2 - 48);
            if (n5 < n3) {
                n4 = n5 + 1;
                n2 = string2.charAt(n5);
                if (n2 > 57) return Integer.parseInt(string2);
                if (n2 < 48) {
                    return Integer.parseInt(string2);
                }
                n2 = n = n * 10 + (n2 - 48);
                if (n4 < n3) {
                    n2 = n;
                    do {
                        n = n4 + 1;
                        if ((n4 = (int)string2.charAt(n4)) > 57) return Integer.parseInt(string2);
                        if (n4 < 48) {
                            return Integer.parseInt(string2);
                        }
                        n2 = n2 * 10 + (n4 - 48);
                        if (n >= n3) break;
                        n4 = n;
                    } while (true);
                }
            }
        }
        n4 = n2;
        if (!bl) return n4;
        return -n2;
    }

    public static int parseInt(char[] arrc, int n, int n2) {
        int n3;
        int n4 = n3 = arrc[n + n2 - 1] - 48;
        int n5 = n;
        int n6 = n3;
        int n7 = n;
        int n8 = n3;
        int n9 = n;
        int n10 = n3;
        int n11 = n;
        int n12 = n3;
        int n13 = n;
        int n14 = n3;
        int n15 = n;
        int n16 = n3;
        int n17 = n;
        switch (n2) {
            default: {
                return n3;
            }
            case 9: {
                n4 = n3 + (arrc[n] - 48) * 100000000;
                n5 = n + 1;
            }
            case 8: {
                n6 = n4 + (arrc[n5] - 48) * 10000000;
                n7 = n5 + 1;
            }
            case 7: {
                n8 = n6 + (arrc[n7] - 48) * 1000000;
                n9 = n7 + 1;
            }
            case 6: {
                n10 = n8 + (arrc[n9] - 48) * 100000;
                n11 = n9 + 1;
            }
            case 5: {
                n12 = n10 + (arrc[n11] - 48) * 10000;
                n13 = n11 + 1;
            }
            case 4: {
                n14 = n12 + (arrc[n13] - 48) * 1000;
                n15 = n13 + 1;
            }
            case 3: {
                n16 = n14 + (arrc[n15] - 48) * 100;
                n17 = n15 + 1;
            }
            case 2: 
        }
        return n16 + (arrc[n17] - 48) * 10;
    }

    public static long parseLong(String string2) {
        if (string2.length() > 9) return Long.parseLong(string2);
        return NumberInput.parseInt(string2);
    }

    public static long parseLong(char[] arrc, int n, int n2) {
        return (long)NumberInput.parseInt(arrc, n, n2 -= 9) * 1000000000L + (long)NumberInput.parseInt(arrc, n + n2, 9);
    }
}

