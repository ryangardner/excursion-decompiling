/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

public final class NumberOutput {
    private static int BILLION = 1000000000;
    private static long BILLION_L = 1000000000L;
    private static long MAX_INT_AS_LONG = Integer.MAX_VALUE;
    private static int MILLION = 1000000;
    private static long MIN_INT_AS_LONG = Integer.MIN_VALUE;
    static final String SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
    static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
    private static final int[] TRIPLET_TO_CHARS = new int[1000];
    private static final String[] sSmallIntStrs;
    private static final String[] sSmallIntStrs2;

    static {
        int n = 0;
        int n2 = 0;
        do {
            if (n >= 10) {
                sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
                return;
            }
            for (int i = 0; i < 10; ++i) {
                for (int j = 0; j < 10; ++j, ++n2) {
                    NumberOutput.TRIPLET_TO_CHARS[n2] = n + 48 << 16 | i + 48 << 8 | j + 48;
                }
            }
            ++n;
        } while (true);
    }

    private static int _full3(int n, byte[] arrby, int n2) {
        n = TRIPLET_TO_CHARS[n];
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 16);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >> 8);
        arrby[n2] = (byte)n;
        return n2 + 1;
    }

    private static int _full3(int n, char[] arrc, int n2) {
        int n3 = TRIPLET_TO_CHARS[n];
        n = n2 + 1;
        arrc[n2] = (char)(n3 >> 16);
        n2 = n + 1;
        arrc[n] = (char)(n3 >> 8 & 127);
        arrc[n2] = (char)(n3 & 127);
        return n2 + 1;
    }

    private static int _leading3(int n, byte[] arrby, int n2) {
        int n3 = TRIPLET_TO_CHARS[n];
        int n4 = n2;
        if (n > 9) {
            n4 = n2;
            if (n > 99) {
                arrby[n2] = (byte)(n3 >> 16);
                n4 = n2 + 1;
            }
            arrby[n4] = (byte)(n3 >> 8);
            ++n4;
        }
        arrby[n4] = (byte)n3;
        return n4 + 1;
    }

    private static int _leading3(int n, char[] arrc, int n2) {
        int n3 = TRIPLET_TO_CHARS[n];
        int n4 = n2;
        if (n > 9) {
            n4 = n2;
            if (n > 99) {
                arrc[n2] = (char)(n3 >> 16);
                n4 = n2 + 1;
            }
            arrc[n4] = (char)(n3 >> 8 & 127);
            ++n4;
        }
        arrc[n4] = (char)(n3 & 127);
        return n4 + 1;
    }

    private static int _outputFullBillion(int n, byte[] arrby, int n2) {
        int n3 = n / 1000;
        int n4 = n3 / 1000;
        int[] arrn = TRIPLET_TO_CHARS;
        int n5 = arrn[n4];
        int n6 = n2 + 1;
        arrby[n2] = (byte)(n5 >> 16);
        int n7 = n6 + 1;
        arrby[n6] = (byte)(n5 >> 8);
        n2 = n7 + 1;
        arrby[n7] = (byte)n5;
        n5 = arrn[n3 - n4 * 1000];
        n7 = n2 + 1;
        arrby[n2] = (byte)(n5 >> 16);
        n4 = n7 + 1;
        arrby[n7] = (byte)(n5 >> 8);
        n2 = n4 + 1;
        arrby[n4] = (byte)n5;
        n3 = arrn[n - n3 * 1000];
        n = n2 + 1;
        arrby[n2] = (byte)(n3 >> 16);
        n2 = n + 1;
        arrby[n] = (byte)(n3 >> 8);
        arrby[n2] = (byte)n3;
        return n2 + 1;
    }

    private static int _outputFullBillion(int n, char[] arrc, int n2) {
        int n3 = n / 1000;
        int n4 = n3 / 1000;
        int[] arrn = TRIPLET_TO_CHARS;
        int n5 = arrn[n4];
        int n6 = n2 + 1;
        arrc[n2] = (char)(n5 >> 16);
        int n7 = n6 + 1;
        arrc[n6] = (char)(n5 >> 8 & 127);
        n2 = n7 + 1;
        arrc[n7] = (char)(n5 & 127);
        n5 = arrn[n3 - n4 * 1000];
        n6 = n2 + 1;
        arrc[n2] = (char)(n5 >> 16);
        n4 = n6 + 1;
        arrc[n6] = (char)(n5 >> 8 & 127);
        n2 = n4 + 1;
        arrc[n4] = (char)(n5 & 127);
        n3 = arrn[n - n3 * 1000];
        n = n2 + 1;
        arrc[n2] = (char)(n3 >> 16);
        n2 = n + 1;
        arrc[n] = (char)(n3 >> 8 & 127);
        arrc[n2] = (char)(n3 & 127);
        return n2 + 1;
    }

    private static int _outputSmallestI(byte[] arrby, int n) {
        int n2 = SMALLEST_INT.length();
        int n3 = 0;
        while (n3 < n2) {
            arrby[n] = (byte)SMALLEST_INT.charAt(n3);
            ++n3;
            ++n;
        }
        return n;
    }

    private static int _outputSmallestI(char[] arrc, int n) {
        int n2 = SMALLEST_INT.length();
        SMALLEST_INT.getChars(0, n2, arrc, n);
        return n + n2;
    }

    private static int _outputSmallestL(byte[] arrby, int n) {
        int n2 = SMALLEST_LONG.length();
        int n3 = 0;
        while (n3 < n2) {
            arrby[n] = (byte)SMALLEST_LONG.charAt(n3);
            ++n3;
            ++n;
        }
        return n;
    }

    private static int _outputSmallestL(char[] arrc, int n) {
        int n2 = SMALLEST_LONG.length();
        SMALLEST_LONG.getChars(0, n2, arrc, n);
        return n + n2;
    }

    private static int _outputUptoBillion(int n, byte[] arrby, int n2) {
        if (n >= MILLION) {
            int n3 = n / 1000;
            int n4 = n3 / 1000;
            int n5 = NumberOutput._leading3(n4, arrby, n2);
            int[] arrn = TRIPLET_TO_CHARS;
            n4 = arrn[n3 - n4 * 1000];
            n2 = n5 + 1;
            arrby[n5] = (byte)(n4 >> 16);
            n5 = n2 + 1;
            arrby[n2] = (byte)(n4 >> 8);
            n2 = n5 + 1;
            arrby[n5] = (byte)n4;
            n = arrn[n - n3 * 1000];
            n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 16);
            n2 = n3 + 1;
            arrby[n3] = (byte)(n >> 8);
            arrby[n2] = (byte)n;
            return n2 + 1;
        }
        if (n < 1000) {
            return NumberOutput._leading3(n, arrby, n2);
        }
        int n6 = n / 1000;
        return NumberOutput._outputUptoMillion(arrby, n2, n6, n - n6 * 1000);
    }

    private static int _outputUptoBillion(int n, char[] arrc, int n2) {
        if (n >= MILLION) {
            int n3 = n / 1000;
            int n4 = n3 / 1000;
            int n5 = NumberOutput._leading3(n4, arrc, n2);
            int[] arrn = TRIPLET_TO_CHARS;
            n2 = arrn[n3 - n4 * 1000];
            n4 = n5 + 1;
            arrc[n5] = (char)(n2 >> 16);
            int n6 = n4 + 1;
            arrc[n4] = (char)(n2 >> 8 & 127);
            n5 = n6 + 1;
            arrc[n6] = (char)(n2 & 127);
            n2 = arrn[n - n3 * 1000];
            n = n5 + 1;
            arrc[n5] = (char)(n2 >> 16);
            n3 = n + 1;
            arrc[n] = (char)(n2 >> 8 & 127);
            arrc[n3] = (char)(n2 & 127);
            return n3 + 1;
        }
        if (n < 1000) {
            return NumberOutput._leading3(n, arrc, n2);
        }
        int n7 = n / 1000;
        return NumberOutput._outputUptoMillion(arrc, n2, n7, n - n7 * 1000);
    }

    private static int _outputUptoMillion(byte[] arrby, int n, int n2, int n3) {
        int n4 = TRIPLET_TO_CHARS[n2];
        int n5 = n;
        if (n2 > 9) {
            n5 = n;
            if (n2 > 99) {
                arrby[n] = (byte)(n4 >> 16);
                n5 = n + 1;
            }
            arrby[n5] = (byte)(n4 >> 8);
            ++n5;
        }
        n = n5 + 1;
        arrby[n5] = (byte)n4;
        n3 = TRIPLET_TO_CHARS[n3];
        n2 = n + 1;
        arrby[n] = (byte)(n3 >> 16);
        n = n2 + 1;
        arrby[n2] = (byte)(n3 >> 8);
        arrby[n] = (byte)n3;
        return n + 1;
    }

    private static int _outputUptoMillion(char[] arrc, int n, int n2, int n3) {
        int n4 = TRIPLET_TO_CHARS[n2];
        int n5 = n;
        if (n2 > 9) {
            n5 = n;
            if (n2 > 99) {
                arrc[n] = (char)(n4 >> 16);
                n5 = n + 1;
            }
            arrc[n5] = (char)(n4 >> 8 & 127);
            ++n5;
        }
        n = n5 + 1;
        arrc[n5] = (char)(n4 & 127);
        n3 = TRIPLET_TO_CHARS[n3];
        n2 = n + 1;
        arrc[n] = (char)(n3 >> 16);
        n = n2 + 1;
        arrc[n2] = (char)(n3 >> 8 & 127);
        arrc[n] = (char)(n3 & 127);
        return n + 1;
    }

    public static boolean notFinite(double d) {
        if (Double.isNaN(d)) return true;
        if (Double.isInfinite(d)) return true;
        return false;
    }

    public static boolean notFinite(float f) {
        if (Float.isNaN(f)) return true;
        if (Float.isInfinite(f)) return true;
        return false;
    }

    public static int outputInt(int n, byte[] arrby, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return NumberOutput._outputSmallestI(arrby, n2);
            }
            arrby[n2] = (byte)45;
            n3 = -n;
            n4 = n2 + 1;
        }
        if (n3 < MILLION) {
            if (n3 >= 1000) {
                n = n3 / 1000;
                return NumberOutput._full3(n3 - n * 1000, arrby, NumberOutput._leading3(n, arrby, n4));
            }
            if (n3 >= 10) return NumberOutput._leading3(n3, arrby, n4);
            n = n4 + 1;
            arrby[n4] = (byte)(n3 + 48);
            return n;
        }
        n2 = BILLION;
        if (n3 < n2) {
            n2 = n3 / 1000;
            n = n2 / 1000;
            return NumberOutput._full3(n3 - n2 * 1000, arrby, NumberOutput._full3(n2 - n * 1000, arrby, NumberOutput._leading3(n, arrby, n4)));
        }
        n = n3 - n2;
        if (n >= n2) {
            n -= n2;
            n2 = n4 + 1;
            arrby[n4] = (byte)50;
            return NumberOutput._outputFullBillion(n, arrby, n2);
        }
        n2 = n4 + 1;
        arrby[n4] = (byte)49;
        return NumberOutput._outputFullBillion(n, arrby, n2);
    }

    public static int outputInt(int n, char[] arrc, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return NumberOutput._outputSmallestI(arrc, n2);
            }
            arrc[n2] = (char)45;
            n3 = -n;
            n4 = n2 + 1;
        }
        if (n3 < MILLION) {
            if (n3 < 1000) {
                if (n3 >= 10) return NumberOutput._leading3(n3, arrc, n4);
                arrc[n4] = (char)(n3 + 48);
                return n4 + 1;
            }
            n = n3 / 1000;
            return NumberOutput._full3(n3 - n * 1000, arrc, NumberOutput._leading3(n, arrc, n4));
        }
        n2 = BILLION;
        if (n3 < n2) {
            n = n3 / 1000;
            n2 = n / 1000;
            return NumberOutput._full3(n3 - n * 1000, arrc, NumberOutput._full3(n - n2 * 1000, arrc, NumberOutput._leading3(n2, arrc, n4)));
        }
        n = n3 - n2;
        if (n >= n2) {
            n -= n2;
            n2 = n4 + 1;
            arrc[n4] = (char)50;
            return NumberOutput._outputFullBillion(n, arrc, n2);
        }
        n2 = n4 + 1;
        arrc[n4] = (char)49;
        return NumberOutput._outputFullBillion(n, arrc, n2);
    }

    public static int outputLong(long l, byte[] arrby, int n) {
        int n2;
        long l2;
        if (l < 0L) {
            if (l > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrby, n);
            }
            if (l == Long.MIN_VALUE) {
                return NumberOutput._outputSmallestL(arrby, n);
            }
            arrby[n] = (byte)45;
            l2 = -l;
            n2 = n + 1;
        } else {
            l2 = l;
            n2 = n;
            if (l <= MAX_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrby, n);
            }
        }
        l = BILLION_L;
        long l3 = l2 / l;
        if (l3 < l) {
            n = NumberOutput._outputUptoBillion((int)l3, arrby, n2);
            return NumberOutput._outputFullBillion((int)(l2 - l3 * l), arrby, n);
        }
        long l4 = l3 / l;
        n = NumberOutput._leading3((int)l4, arrby, n2);
        n = NumberOutput._outputFullBillion((int)(l3 - l * l4), arrby, n);
        return NumberOutput._outputFullBillion((int)(l2 - l3 * l), arrby, n);
    }

    public static int outputLong(long l, char[] arrc, int n) {
        int n2;
        long l2;
        if (l < 0L) {
            if (l > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrc, n);
            }
            if (l == Long.MIN_VALUE) {
                return NumberOutput._outputSmallestL(arrc, n);
            }
            arrc[n] = (char)45;
            l2 = -l;
            n2 = n + 1;
        } else {
            l2 = l;
            n2 = n;
            if (l <= MAX_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrc, n);
            }
        }
        long l3 = BILLION_L;
        l = l2 / l3;
        if (l < l3) {
            n = NumberOutput._outputUptoBillion((int)l, arrc, n2);
            return NumberOutput._outputFullBillion((int)(l2 - l * l3), arrc, n);
        }
        long l4 = l / l3;
        n = NumberOutput._leading3((int)l4, arrc, n2);
        n = NumberOutput._outputFullBillion((int)(l - l3 * l4), arrc, n);
        return NumberOutput._outputFullBillion((int)(l2 - l * l3), arrc, n);
    }

    public static String toString(double d) {
        return Double.toString(d);
    }

    public static String toString(float f) {
        return Float.toString(f);
    }

    public static String toString(int n) {
        String[] arrstring = sSmallIntStrs;
        if (n >= arrstring.length) return Integer.toString(n);
        if (n >= 0) {
            return arrstring[n];
        }
        int n2 = -n - 1;
        arrstring = sSmallIntStrs2;
        if (n2 >= arrstring.length) return Integer.toString(n);
        return arrstring[n2];
    }

    public static String toString(long l) {
        if (l > Integer.MAX_VALUE) return Long.toString(l);
        if (l < Integer.MIN_VALUE) return Long.toString(l);
        return NumberOutput.toString((int)l);
    }
}

