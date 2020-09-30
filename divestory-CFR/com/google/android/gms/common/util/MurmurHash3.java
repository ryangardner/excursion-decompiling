/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

public class MurmurHash3 {
    private MurmurHash3() {
    }

    public static int murmurhash3_x86_32(byte[] arrby, int n, int n2, int n3) {
        block5 : {
            int n4;
            block3 : {
                block4 : {
                    int n5;
                    n4 = (n2 & -4) + n;
                    while (n < n4) {
                        n5 = (arrby[n] & 255 | (arrby[n + 1] & 255) << 8 | (arrby[n + 2] & 255) << 16 | arrby[n + 3] << 24) * -862048943;
                        n3 ^= (n5 << 15 | n5 >>> 17) * 461845907;
                        n3 = (n3 >>> 19 | n3 << 13) * 5 - 430675100;
                        n += 4;
                    }
                    n = 0;
                    n5 = 0;
                    int n6 = n2 & 3;
                    if (n6 == 1) break block3;
                    n = n5;
                    if (n6 == 2) break block4;
                    if (n6 != 3) break block5;
                    n = (arrby[n4 + 2] & 255) << 16;
                }
                n |= (arrby[n4 + 1] & 255) << 8;
            }
            n = (arrby[n4] & 255 | n) * -862048943;
            n3 ^= (n >>> 17 | n << 15) * 461845907;
        }
        n = n3 ^ n2;
        n = (n ^ n >>> 16) * -2048144789;
        n = (n ^ n >>> 13) * -1028477387;
        return n ^ n >>> 16;
    }
}

