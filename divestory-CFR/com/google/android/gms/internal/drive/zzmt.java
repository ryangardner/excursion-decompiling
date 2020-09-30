/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzmu;

final class zzmt {
    /*
     * Unable to fully structure code
     */
    static String zzc(zzjc var0) {
        var0 = new zzmu((zzjc)var0);
        var1_1 = new StringBuilder(var0.size());
        var2_2 = 0;
        while (var2_2 < var0.size()) {
            var3_3 = var0.zzs(var2_2);
            if (var3_3 != 34) {
                if (var3_3 != 39) {
                    if (var3_3 != 92) {
                        switch (var3_3) {
                            default: {
                                if (var3_3 >= 32 && var3_3 <= 126) {
                                    var1_1.append((char)var3_3);
                                    ** break;
                                }
                                var1_1.append('\\');
                                var1_1.append((char)((var3_3 >>> 6 & 3) + 48));
                                var1_1.append((char)((var3_3 >>> 3 & 7) + 48));
                                var1_1.append((char)((var3_3 & 7) + 48));
                                ** break;
                            }
                            case 13: {
                                var1_1.append("\\r");
                                ** break;
                            }
                            case 12: {
                                var1_1.append("\\f");
                                ** break;
                            }
                            case 11: {
                                var1_1.append("\\v");
                                ** break;
                            }
                            case 10: {
                                var1_1.append("\\n");
                                ** break;
                            }
                            case 9: {
                                var1_1.append("\\t");
                                ** break;
                            }
                            case 8: {
                                var1_1.append("\\b");
                                ** break;
                            }
                            case 7: 
                        }
                        var1_1.append("\\a");
                        ** break;
lbl52: // 9 sources:
                    } else {
                        var1_1.append("\\\\");
                    }
                } else {
                    var1_1.append("\\'");
                }
            } else {
                var1_1.append("\\\"");
            }
            ++var2_2;
        }
        return var1_1.toString();
    }
}

