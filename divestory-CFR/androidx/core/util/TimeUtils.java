/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.util;

import java.io.PrintWriter;

public final class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr;
    private static final Object sFormatSync;

    static {
        sFormatSync = new Object();
        sFormatStr = new char[24];
    }

    private TimeUtils() {
    }

    private static int accumField(int n, int n2, boolean bl, int n3) {
        if (n > 99) return n2 + 3;
        if (bl && n3 >= 3) {
            return n2 + 3;
        }
        if (n > 9) return n2 + 2;
        if (bl && n3 >= 2) {
            return n2 + 2;
        }
        if (bl) return n2 + 1;
        if (n <= 0) return 0;
        return n2 + 1;
    }

    public static void formatDuration(long l, long l2, PrintWriter printWriter) {
        if (l == 0L) {
            printWriter.print("--");
            return;
        }
        TimeUtils.formatDuration(l - l2, printWriter, 0);
    }

    public static void formatDuration(long l, PrintWriter printWriter) {
        TimeUtils.formatDuration(l, printWriter, 0);
    }

    public static void formatDuration(long l, PrintWriter printWriter, int n) {
        Object object = sFormatSync;
        synchronized (object) {
            n = TimeUtils.formatDurationLocked(l, n);
            String string2 = new String(sFormatStr, 0, n);
            printWriter.print(string2);
            return;
        }
    }

    public static void formatDuration(long l, StringBuilder stringBuilder) {
        Object object = sFormatSync;
        synchronized (object) {
            int n = TimeUtils.formatDurationLocked(l, 0);
            stringBuilder.append(sFormatStr, 0, n);
            return;
        }
    }

    private static int formatDurationLocked(long l, int n) {
        long l2;
        int n2;
        int n3;
        boolean bl;
        int n4;
        int n5;
        int n6;
        if (sFormatStr.length < n) {
            sFormatStr = new char[n];
        }
        char[] arrc = sFormatStr;
        long l3 = l LCMP 0L;
        if (l3 == false) {
            do {
                if (n - 1 <= 0) {
                    arrc[0] = (char)48;
                    return 1;
                }
                arrc[0] = (char)32;
            } while (true);
        }
        if (l3 > 0) {
            n4 = 43;
        } else {
            n4 = 45;
            l = -l;
        }
        int n7 = (int)(l % 1000L);
        l3 = (int)Math.floor(l / 1000L);
        if (l3 > 86400) {
            n2 = l3 / 86400;
            l3 -= 86400 * n2;
        } else {
            n2 = 0;
        }
        if (l3 > 3600) {
            n6 = l3 / 3600;
            l3 -= n6 * 3600;
        } else {
            n6 = 0;
        }
        if (l3 > 60) {
            n5 = l3 / 60;
            l2 = l3 - n5 * 60;
        } else {
            n5 = 0;
            l2 = l3;
        }
        if (n != 0) {
            l3 = TimeUtils.accumField(n2, 1, false, 0);
            bl = l3 > 0;
            bl = (l3 += TimeUtils.accumField(n6, 1, bl, 2)) > 0;
            bl = (l3 += TimeUtils.accumField(n5, 1, bl, 2)) > 0;
            long l4 = l3 + TimeUtils.accumField((int)l2, 1, bl, 2);
            l3 = l4 > 0 ? (long)3 : (long)0;
            l4 += TimeUtils.accumField(n7, 2, true, (int)l3) + 1;
            l3 = 0;
            do {
                n3 = (int)l3;
                if (l4 < n) {
                    arrc[l3] = (char)32;
                    ++l3;
                    ++l4;
                    continue;
                }
                break;
            } while (true);
        } else {
            n3 = 0;
        }
        arrc[n3] = (char)n4;
        n4 = n3 + 1;
        n = n != 0 ? 1 : 0;
        n2 = TimeUtils.printField(arrc, n2, 'd', n4, false, 0);
        bl = n2 != n4;
        l3 = n != 0 ? (long)2 : (long)0;
        n2 = TimeUtils.printField(arrc, n6, 'h', n2, bl, (int)l3);
        bl = n2 != n4;
        l3 = n != 0 ? (long)2 : (long)0;
        n2 = TimeUtils.printField(arrc, n5, 'm', n2, bl, (int)l3);
        bl = n2 != n4;
        l3 = n != 0 ? (long)2 : (long)0;
        l3 = TimeUtils.printField(arrc, (int)l2, 's', n2, bl, (int)l3);
        n = n != 0 && l3 != n4 ? 3 : 0;
        n = TimeUtils.printField(arrc, n7, 'm', (int)l3, true, n);
        arrc[n] = (char)115;
        return n + 1;
    }

    private static int printField(char[] arrc, int n, char c, int n2, boolean bl, int n3) {
        int n4;
        block7 : {
            int n5;
            block6 : {
                if (!bl) {
                    n5 = n2;
                    if (n <= 0) return n5;
                }
                if (bl && n3 >= 3 || n > 99) {
                    n4 = n / 100;
                    arrc[n2] = (char)(n4 + 48);
                    n5 = n2 + 1;
                    n -= n4 * 100;
                } else {
                    n5 = n2;
                }
                if (bl && n3 >= 2 || n > 9) break block6;
                n4 = n5;
                n3 = n;
                if (n2 == n5) break block7;
            }
            n2 = n / 10;
            arrc[n5] = (char)(n2 + 48);
            n4 = n5 + 1;
            n3 = n - n2 * 10;
        }
        arrc[n4] = (char)(n3 + 48);
        n = n4 + 1;
        arrc[n] = c;
        return n + 1;
    }
}

