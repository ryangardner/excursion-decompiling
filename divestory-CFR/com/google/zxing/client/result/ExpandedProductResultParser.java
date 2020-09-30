/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.HashMap;
import java.util.Map;

public final class ExpandedProductResultParser
extends ResultParser {
    private static String findAIvalue(int n, String charSequence) {
        if (((String)charSequence).charAt(n) != '(') {
            return null;
        }
        String string2 = ((String)charSequence).substring(n + 1);
        charSequence = new StringBuilder();
        n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c == ')') {
                return ((StringBuilder)charSequence).toString();
            }
            if (c < '0') return null;
            if (c > '9') return null;
            ((StringBuilder)charSequence).append(c);
            ++n;
        }
        return ((StringBuilder)charSequence).toString();
    }

    private static String findValue(int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        string2 = string2.substring(n);
        n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c == '(') {
                if (ExpandedProductResultParser.findAIvalue(n, string2) != null) return stringBuilder.toString();
                stringBuilder.append('(');
            } else {
                stringBuilder.append(c);
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public ExpandedProductParsedResult parse(Result var1_1) {
        if (var1_1.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        var2_2 = ExpandedProductResultParser.getMassagedText((Result)var1_1);
        var3_3 = new HashMap<String, String>();
        var15_15 = var14_14 = (var13_13 = (var12_12 = (var11_11 = (var1_1 = (var10_10 = (var9_9 = (var8_8 = (var7_7 = (var6_6 = (var5_5 = (var4_4 = null)))))))))));
        var16_16 = 0;
        var17_17 = var11_11;
        var18_18 = var1_1;
        var11_11 = var4_4;
        while (var16_16 < var2_2.length()) {
            block93 : {
                block92 : {
                    block91 : {
                        block90 : {
                            block83 : {
                                block84 : {
                                    block85 : {
                                        block86 : {
                                            block87 : {
                                                block88 : {
                                                    block89 : {
                                                        var4_4 = ExpandedProductResultParser.findAIvalue(var16_16, var2_2);
                                                        if (var4_4 == null) {
                                                            return null;
                                                        }
                                                        var19_19 = var4_4.length();
                                                        var20_20 = 2;
                                                        var19_19 = var16_16 + (var19_19 + 2);
                                                        var1_1 = ExpandedProductResultParser.findValue(var19_19, var2_2);
                                                        var21_21 = var1_1.length();
                                                        var16_16 = var4_4.hashCode();
                                                        if (var16_16 == 1536) break block83;
                                                        if (var16_16 == 1537) break block84;
                                                        if (var16_16 == 1567) break block85;
                                                        if (var16_16 == 1568) break block86;
                                                        if (var16_16 == 1570) break block87;
                                                        if (var16_16 == 1572) break block88;
                                                        if (var16_16 == 1574) break block89;
                                                        switch (var16_16) {
                                                            default: {
                                                                switch (var16_16) {
                                                                    default: {
                                                                        switch (var16_16) {
                                                                            default: {
                                                                                switch (var16_16) {
                                                                                    default: {
                                                                                        ** GOTO lbl-1000
                                                                                    }
                                                                                    case 1575750: {
                                                                                        if (var4_4.equals("3933")) {
                                                                                            var16_16 = 34;
                                                                                            ** break;
                                                                                        }
                                                                                        ** GOTO lbl-1000
                                                                                    }
                                                                                    case 1575749: {
                                                                                        if (var4_4.equals("3932")) {
                                                                                            var16_16 = 33;
                                                                                            ** break;
                                                                                        }
                                                                                        ** GOTO lbl-1000
                                                                                    }
                                                                                    case 1575748: {
                                                                                        if (var4_4.equals("3931")) {
                                                                                            var16_16 = 32;
                                                                                            ** break;
                                                                                        }
                                                                                        ** GOTO lbl-1000
                                                                                    }
                                                                                    case 1575747: {
                                                                                        if (var4_4.equals("3930")) {
                                                                                            var16_16 = 31;
                                                                                            ** break;
                                                                                        }
                                                                                        ** GOTO lbl-1000
lbl56: // 4 sources:
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                break;
                                                                            }
                                                                            case 1575719: {
                                                                                if (var4_4.equals("3923")) {
                                                                                    var16_16 = 30;
                                                                                    ** break;
                                                                                }
                                                                                ** GOTO lbl-1000
                                                                            }
                                                                            case 1575718: {
                                                                                if (var4_4.equals("3922")) {
                                                                                    var16_16 = 29;
                                                                                    ** break;
                                                                                }
                                                                                ** GOTO lbl-1000
                                                                            }
                                                                            case 1575717: {
                                                                                if (var4_4.equals("3921")) {
                                                                                    var16_16 = 28;
                                                                                    ** break;
                                                                                }
                                                                                ** GOTO lbl-1000
                                                                            }
                                                                            case 1575716: {
                                                                                if (var4_4.equals("3920")) {
                                                                                    var16_16 = 27;
                                                                                    ** break;
                                                                                }
                                                                                ** GOTO lbl-1000
lbl78: // 4 sources:
                                                                                break;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                    case 1568936: {
                                                                        if (var4_4.equals("3209")) {
                                                                            var16_16 = 26;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568935: {
                                                                        if (var4_4.equals("3208")) {
                                                                            var16_16 = 25;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568934: {
                                                                        if (var4_4.equals("3207")) {
                                                                            var16_16 = 24;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568933: {
                                                                        if (var4_4.equals("3206")) {
                                                                            var16_16 = 23;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568932: {
                                                                        if (var4_4.equals("3205")) {
                                                                            var16_16 = 22;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568931: {
                                                                        if (var4_4.equals("3204")) {
                                                                            var16_16 = 21;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568930: {
                                                                        if (var4_4.equals("3203")) {
                                                                            var16_16 = 20;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568929: {
                                                                        if (var4_4.equals("3202")) {
                                                                            var16_16 = 19;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568928: {
                                                                        if (var4_4.equals("3201")) {
                                                                            var16_16 = 18;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
                                                                    }
                                                                    case 1568927: {
                                                                        if (var4_4.equals("3200")) {
                                                                            var16_16 = 17;
                                                                            ** break;
                                                                        }
                                                                        ** GOTO lbl-1000
lbl130: // 10 sources:
                                                                        break;
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                            case 1567975: {
                                                                if (var4_4.equals("3109")) {
                                                                    var16_16 = 16;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567974: {
                                                                if (var4_4.equals("3108")) {
                                                                    var16_16 = 15;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567973: {
                                                                if (var4_4.equals("3107")) {
                                                                    var16_16 = 14;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567972: {
                                                                if (var4_4.equals("3106")) {
                                                                    var16_16 = 13;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567971: {
                                                                if (var4_4.equals("3105")) {
                                                                    var16_16 = 12;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567970: {
                                                                if (var4_4.equals("3104")) {
                                                                    var16_16 = 11;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567969: {
                                                                if (var4_4.equals("3103")) {
                                                                    var16_16 = 10;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567968: {
                                                                if (var4_4.equals("3102")) {
                                                                    var16_16 = 9;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567967: {
                                                                if (var4_4.equals("3101")) {
                                                                    var16_16 = 8;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
                                                            }
                                                            case 1567966: {
                                                                if (var4_4.equals("3100")) {
                                                                    var16_16 = 7;
                                                                    ** break;
                                                                }
                                                                ** GOTO lbl-1000
lbl182: // 10 sources:
                                                                break;
                                                            }
                                                        }
                                                        break block90;
                                                    }
                                                    if (!var4_4.equals("17")) ** GOTO lbl-1000
                                                    var16_16 = 6;
                                                    break block90;
                                                }
                                                if (!var4_4.equals("15")) ** GOTO lbl-1000
                                                var16_16 = 5;
                                                break block90;
                                            }
                                            if (!var4_4.equals("13")) ** GOTO lbl-1000
                                            var16_16 = 4;
                                            break block90;
                                        }
                                        if (!var4_4.equals("11")) ** GOTO lbl-1000
                                        var16_16 = 3;
                                        break block90;
                                    }
                                    if (!var4_4.equals("10")) ** GOTO lbl-1000
                                    var16_16 = var20_20;
                                    break block90;
                                }
                                if (!var4_4.equals("01")) ** GOTO lbl-1000
                                var16_16 = 1;
                                break block90;
                            }
                            if (var4_4.equals("00")) {
                                var16_16 = 0;
                            } else lbl-1000: // 36 sources:
                            {
                                var16_16 = -1;
                            }
                        }
                        switch (var16_16) {
                            default: {
                                var3_3.put((String)var4_4, (String)var1_1);
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 31: 
                            case 32: 
                            case 33: 
                            case 34: {
                                if (var1_1.length() < 4) {
                                    return null;
                                }
                                var14_14 = var1_1.substring(3);
                                var15_15 = var1_1.substring(0, 3);
                                var4_4 = var4_4.substring(3);
                                var13_13 = var10_10;
                                var1_1 = var14_14;
                                var10_10 = var4_4;
                                break block92;
                            }
                            case 27: 
                            case 28: 
                            case 29: 
                            case 30: {
                                var14_14 = var4_4.substring(3);
                                var13_13 = var10_10;
                                var10_10 = var14_14;
                                break block92;
                            }
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                            case 22: 
                            case 23: 
                            case 24: 
                            case 25: 
                            case 26: {
                                var12_12 = var4_4.substring(3);
                                var4_4 = "LB";
                                ** GOTO lbl242
                            }
                            case 7: 
                            case 8: 
                            case 9: 
                            case 10: 
                            case 11: 
                            case 12: 
                            case 13: 
                            case 14: 
                            case 15: 
                            case 16: {
                                var12_12 = var4_4.substring(3);
                                var4_4 = "KG";
lbl242: // 2 sources:
                                var18_18 = var1_1;
                                var1_1 = var13_13;
                                break block93;
                            }
                            case 6: {
                                var4_4 = var1_1;
                                break block91;
                            }
                            case 5: {
                                var9_9 = var1_1;
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 4: {
                                var8_8 = var1_1;
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 3: {
                                var7_7 = var1_1;
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 2: {
                                var6_6 = var1_1;
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 1: {
                                var11_11 = var1_1;
                                var4_4 = var10_10;
                                break block91;
                            }
                            case 0: 
                        }
                        var4_4 = var10_10;
                        var5_5 = var1_1;
                    }
                    var1_1 = var13_13;
                    var10_10 = var14_14;
                    var13_13 = var4_4;
                }
                var14_14 = var10_10;
                var4_4 = var17_17;
                var10_10 = var13_13;
            }
            var16_16 = var19_19 + var21_21;
            var17_17 = var4_4;
            var13_13 = var1_1;
        }
        return new ExpandedProductParsedResult(var2_2, (String)var11_11, (String)var5_5, (String)var6_6, (String)var7_7, (String)var8_8, (String)var9_9, (String)var10_10, (String)var18_18, (String)var17_17, (String)var12_12, (String)var13_13, (String)var14_14, (String)var15_15, var3_3);
    }
}

