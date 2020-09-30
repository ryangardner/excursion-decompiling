/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    private static int combins(int n, int n2) {
        int n3;
        int n4 = n3 = n - n2;
        int n5 = n2;
        if (n3 > n2) {
            n5 = n3;
            n4 = n2;
        }
        n2 = 1;
        int n6 = 1;
        n3 = n;
        n = n6;
        do {
            int n7 = n2;
            n6 = n;
            if (n3 <= n5) {
                while (n6 <= n4) {
                    n7 /= n6;
                    ++n6;
                }
                return n7;
            }
            n2 = n7 = n2 * n3;
            n6 = n;
            if (n <= n4) {
                n2 = n7 / n;
                n6 = n + 1;
            }
            --n3;
            n = n6;
        } while (true);
    }

    public static int getRSSvalue(int[] arrn, int n, boolean bl) {
        int n2;
        int n3;
        int[] arrn2 = arrn;
        int n4 = arrn2.length;
        int n5 = arrn2.length;
        int n6 = 0;
        for (n3 = 0; n3 < n5; n6 += arrn2[n3], ++n3) {
        }
        int n7 = 0;
        int n8 = 0;
        n3 = 0;
        int n9 = n6;
        while (n7 < (n2 = n4 - 1)) {
            int n10;
            int n11 = 1 << n7;
            n6 = n3 | n11;
            for (n10 = 1; n10 < arrn[n7]; n8 += n5, ++n10, n6 &= n11) {
                int n12 = n9 - n10;
                int n13 = n4 - n7;
                int n14 = n13 - 2;
                n3 = n5 = RSSUtils.combins(n12 - 1, n14);
                if (bl) {
                    n3 = n5;
                    if (n6 == 0) {
                        int n15 = n13 - 1;
                        n3 = n5;
                        if (n12 - n15 >= n15) {
                            n3 = n5 - RSSUtils.combins(n12 - n13, n14);
                        }
                    }
                }
                if (n13 - 1 > 1) {
                    n5 = 0;
                    for (n14 = n12 - n14; n14 > n; n5 += RSSUtils.combins((int)(n12 - n14 - 1), (int)(n13 - 3)), --n14) {
                    }
                    n5 = n3 - n5 * (n2 - n7);
                    continue;
                }
                n5 = n3;
                if (n12 <= n) continue;
                n5 = n3 - 1;
            }
            n9 -= n10;
            ++n7;
            n3 = n6;
        }
        return n8;
    }
}

