/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING;
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        String string2;
        PLATFORM_DEFAULT_ENCODING = string2 = Charset.defaultCharset().name();
        boolean bl = SHIFT_JIS.equalsIgnoreCase(string2) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING);
        ASSUME_SHIFT_JIS = bl;
    }

    private StringUtils() {
    }

    /*
     * Unable to fully structure code
     */
    public static String guessEncoding(byte[] var0, Map<DecodeHintType, ?> var1_1) {
        var2_7 /* !! */  = var0 /* !! */ ;
        if (var1_6 != null && (var1_6 = (String)var1_6.get((Object)DecodeHintType.CHARACTER_SET)) != null) {
            return var1_6;
        }
        var3_8 = var2_7 /* !! */ .length;
        var4_9 = var2_7 /* !! */ .length;
        var5_10 = 1;
        var6_11 = 0;
        var7_12 = var4_9 > 3 && var2_7 /* !! */ [0] == -17 && var2_7 /* !! */ [1] == -69 && var2_7 /* !! */ [2] == -65;
        var8_13 = 0;
        var9_14 = 1;
        var10_15 = 1;
        var12_17 = 0;
        var13_18 = 0;
        var14_19 = 0;
        var15_20 = 0;
        var16_21 = 0;
        var17_22 = 0;
        var18_23 = 0;
        var19_24 = 0;
        var20_25 = 0;
        for (var11_16 = 0; var11_16 < var3_8 && (var5_10 != 0 || var9_14 != 0 || var10_15 != 0); ++var11_16) {
            block40 : {
                block41 : {
                    block37 : {
                        block39 : {
                            block38 : {
                                block33 : {
                                    block36 : {
                                        block34 : {
                                            block35 : {
                                                var21_26 = var0 /* !! */ [var11_16] & 255;
                                                var22_27 = var10_15;
                                                var4_9 = var12_17;
                                                var23_28 = var14_19;
                                                var24_29 = var15_20;
                                                var25_30 = var16_21;
                                                if (var10_15 == 0) break block33;
                                                if (var12_17 <= 0) break block34;
                                                if ((var21_26 & 128) != 0) break block35;
                                                var4_9 = var12_17;
                                                ** GOTO lbl-1000
                                            }
                                            var4_9 = var12_17 - 1;
                                            var22_27 = var10_15;
                                            var23_28 = var14_19;
                                            var24_29 = var15_20;
                                            var25_30 = var16_21;
                                            break block33;
                                        }
                                        var22_27 = var10_15;
                                        var4_9 = var12_17;
                                        var23_28 = var14_19;
                                        var24_29 = var15_20;
                                        var25_30 = var16_21;
                                        if ((var21_26 & 128) == 0) break block33;
                                        if ((var21_26 & 64) != 0) break block36;
                                        var4_9 = var12_17;
                                        ** GOTO lbl-1000
                                    }
                                    var4_9 = var12_17 + 1;
                                    if ((var21_26 & 32) == 0) {
                                        var23_28 = var14_19 + 1;
                                        var22_27 = var10_15;
                                        var24_29 = var15_20;
                                        var25_30 = var16_21;
                                    } else {
                                        ++var4_9;
                                        if ((var21_26 & 16) == 0) {
                                            var24_29 = var15_20 + 1;
                                            var22_27 = var10_15;
                                            var23_28 = var14_19;
                                            var25_30 = var16_21;
                                        } else {
                                            var4_9 = var26_31 = var4_9 + 1;
                                            if ((var21_26 & 8) == 0) {
                                                var25_30 = var16_21 + 1;
                                                var24_29 = var15_20;
                                                var23_28 = var14_19;
                                                var4_9 = var26_31;
                                                var22_27 = var10_15;
                                            } else lbl-1000: // 3 sources:
                                            {
                                                var22_27 = 0;
                                                var23_28 = var14_19;
                                                var24_29 = var15_20;
                                                var25_30 = var16_21;
                                            }
                                        }
                                    }
                                }
                                var12_17 = var5_10;
                                var27_32 = var18_23;
                                if (var5_10 == 0) break block37;
                                if (var21_26 <= 127 || var21_26 >= 160) break block38;
                                var12_17 = 0;
                                var27_32 = var18_23;
                                break block37;
                            }
                            var12_17 = var5_10;
                            var27_32 = var18_23;
                            if (var21_26 <= 159) break block37;
                            if (var21_26 < 192 || var21_26 == 215) break block39;
                            var12_17 = var5_10;
                            var27_32 = var18_23;
                            if (var21_26 != 247) break block37;
                        }
                        var27_32 = var18_23 + 1;
                        var12_17 = var5_10;
                    }
                    var16_21 = var8_13;
                    var14_19 = var6_11;
                    var15_20 = var9_14;
                    var18_23 = var13_18;
                    var28_33 = var17_22;
                    var26_31 = var19_24;
                    var29_34 = var20_25;
                    if (var9_14 == 0) break block40;
                    if (var13_18 <= 0) break block41;
                    if (var21_26 < 64 || var21_26 == 127 || var21_26 > 252) ** GOTO lbl-1000
                    var18_23 = var13_18 - 1;
                    var16_21 = var8_13;
                    var14_19 = var6_11;
                    var15_20 = var9_14;
                    var28_33 = var17_22;
                    var26_31 = var19_24;
                    var29_34 = var20_25;
                    break block40;
                }
                if (var21_26 != 128 && var21_26 != 160 && var21_26 <= 239) {
                    if (var21_26 > 160 && var21_26 < 224) {
                        var16_21 = var8_13 + 1;
                        var29_34 = var20_25 + 1;
                        if (var29_34 > var17_22) {
                            var17_22 = var29_34;
                        }
                        var26_31 = 0;
                        var14_19 = var6_11;
                        var15_20 = var9_14;
                        var18_23 = var13_18;
                        var28_33 = var17_22;
                    } else {
                        if (var21_26 > 127) {
                            ++var13_18;
                            var10_15 = var19_24 + 1;
                            if (var10_15 > var6_11) {
                                var10_15 = var6_11 = var10_15;
                            }
                        } else {
                            var10_15 = 0;
                        }
                        var29_34 = 0;
                        var16_21 = var8_13;
                        var14_19 = var6_11;
                        var15_20 = var9_14;
                        var18_23 = var13_18;
                        var28_33 = var17_22;
                        var26_31 = var10_15;
                    }
                } else lbl-1000: // 2 sources:
                {
                    var15_20 = 0;
                    var29_34 = var20_25;
                    var26_31 = var19_24;
                    var28_33 = var17_22;
                    var18_23 = var13_18;
                    var14_19 = var6_11;
                    var16_21 = var8_13;
                }
            }
            var8_13 = var16_21;
            var5_10 = var12_17;
            var6_11 = var14_19;
            var9_14 = var15_20;
            var10_15 = var22_27;
            var12_17 = var4_9;
            var13_18 = var18_23;
            var14_19 = var23_28;
            var15_20 = var24_29;
            var16_21 = var25_30;
            var17_22 = var28_33;
            var18_23 = var27_32;
            var19_24 = var26_31;
            var20_25 = var29_34;
        }
        var4_9 = var10_15;
        if (var10_15 != 0) {
            var4_9 = var10_15;
            if (var12_17 > 0) {
                var4_9 = 0;
            }
        }
        var10_15 = var9_14;
        if (var9_14 != 0) {
            var10_15 = var9_14;
            if (var13_18 > 0) {
                var10_15 = 0;
            }
        }
        if (var4_9 != 0) {
            if (var7_12 != false) return "UTF8";
            if (var14_19 + var15_20 + var16_21 > 0) {
                return "UTF8";
            }
        }
        var1_6 = "SJIS";
        if (var10_15 != 0) {
            if (StringUtils.ASSUME_SHIFT_JIS != false) return "SJIS";
            if (var17_22 >= 3) return "SJIS";
            if (var6_11 >= 3) {
                return "SJIS";
            }
        }
        if (var5_10 != 0 && var10_15 != 0) {
            if (var17_22 == 2) {
                var0_1 = var1_6;
                if (var8_13 == 2) return var0_5;
            }
            if (var18_23 * 10 < var3_8) return var0_5;
            var0_3 = var1_6;
            return var0_5;
        }
        if (var5_10 != 0) {
            return "ISO8859_1";
        }
        if (var10_15 != 0) {
            return "SJIS";
        }
        if (var4_9 == 0) return StringUtils.PLATFORM_DEFAULT_ENCODING;
        return "UTF8";
    }
}

