/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.util.Log
 */
package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser {
    private static final String LOGTAG = "PathParser";

    private PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] arrf) {
        arrayList.add(new PathDataNode(c, arrf));
    }

    public static boolean canMorph(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        if (arrpathDataNode == null) return false;
        if (arrpathDataNode2 == null) {
            return false;
        }
        if (arrpathDataNode.length != arrpathDataNode2.length) {
            return false;
        }
        int n = 0;
        while (n < arrpathDataNode.length) {
            if (arrpathDataNode[n].mType != arrpathDataNode2[n].mType) return false;
            if (arrpathDataNode[n].mParams.length != arrpathDataNode2[n].mParams.length) {
                return false;
            }
            ++n;
        }
        return true;
    }

    static float[] copyOfRange(float[] arrf, int n, int n2) {
        if (n > n2) throw new IllegalArgumentException();
        int n3 = arrf.length;
        if (n < 0) throw new ArrayIndexOutOfBoundsException();
        if (n > n3) throw new ArrayIndexOutOfBoundsException();
        n3 = Math.min(n2 -= n, n3 - n);
        float[] arrf2 = new float[n2];
        System.arraycopy(arrf, n, arrf2, 0, n3);
        return arrf2;
    }

    public static PathDataNode[] createNodesFromPathData(String string2) {
        if (string2 == null) {
            return null;
        }
        ArrayList<PathDataNode> arrayList = new ArrayList<PathDataNode>();
        int n = 1;
        int n2 = 0;
        do {
            if (n >= string2.length()) {
                if (n - n2 != 1) return arrayList.toArray(new PathDataNode[arrayList.size()]);
                if (n2 >= string2.length()) return arrayList.toArray(new PathDataNode[arrayList.size()]);
                PathParser.addNode(arrayList, string2.charAt(n2), new float[0]);
                return arrayList.toArray(new PathDataNode[arrayList.size()]);
            }
            String string3 = string2.substring(n2, n = PathParser.nextStart(string2, n)).trim();
            if (string3.length() > 0) {
                float[] arrf = PathParser.getFloats(string3);
                PathParser.addNode(arrayList, string3.charAt(0), arrf);
            }
            n2 = n++;
        } while (true);
    }

    public static Path createPathFromPathData(String string2) {
        Object object = new Path();
        PathDataNode[] arrpathDataNode = PathParser.createNodesFromPathData(string2);
        if (arrpathDataNode == null) return null;
        try {
            PathDataNode.nodesToPath(arrpathDataNode, (Path)object);
            return object;
        }
        catch (RuntimeException runtimeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error in parsing ");
            ((StringBuilder)object).append(string2);
            throw new RuntimeException(((StringBuilder)object).toString(), runtimeException);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] arrpathDataNode) {
        if (arrpathDataNode == null) {
            return null;
        }
        PathDataNode[] arrpathDataNode2 = new PathDataNode[arrpathDataNode.length];
        int n = 0;
        while (n < arrpathDataNode.length) {
            arrpathDataNode2[n] = new PathDataNode(arrpathDataNode[n]);
            ++n;
        }
        return arrpathDataNode2;
    }

    /*
     * Unable to fully structure code
     */
    private static void extract(String var0, int var1_1, ExtractFloatResult var2_2) {
        var2_2.mEndWithNegOrDot = false;
        var4_4 = false;
        var5_5 = false;
        var6_6 = false;
        for (var3_3 = var1_1; var3_3 < var0.length(); ++var3_3) {
            block8 : {
                block7 : {
                    var7_7 = var0.charAt(var3_3);
                    if (var7_7 == ' ') break block7;
                    if (var7_7 == 'E' || var7_7 == 'e') ** GOTO lbl25
                    switch (var7_7) {
                        default: {
                            ** GOTO lbl23
                        }
                        case '.': {
                            if (var5_5) ** GOTO lbl17
                            var4_4 = false;
                            var5_5 = true;
                            break block8;
lbl17: // 1 sources:
                            var2_2.mEndWithNegOrDot = true;
                            break;
                        }
                        case '-': {
                            if (var3_3 != var1_1 && !var4_4) {
                                var2_2.mEndWithNegOrDot = true;
                                break;
                            }
lbl23: // 3 sources:
                            var4_4 = false;
                            break block8;
                        }
lbl25: // 1 sources:
                        var4_4 = true;
                        break block8;
                        case ',': 
                    }
                }
                var4_4 = false;
                var6_6 = true;
            }
            if (var6_6) break;
        }
        var2_2.mEndPosition = var3_3;
    }

    private static float[] getFloats(String string2) {
        if (string2.charAt(0) == 'z') return new float[0];
        if (string2.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] arrf = new float[string2.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int n = string2.length();
            int n2 = 1;
            int n3 = 0;
            do {
                if (n2 >= n) {
                    return PathParser.copyOfRange(arrf, 0, n3);
                }
                PathParser.extract(string2, n2, extractFloatResult);
                int n4 = extractFloatResult.mEndPosition;
                int n5 = n3;
                if (n2 < n4) {
                    arrf[n3] = Float.parseFloat(string2.substring(n2, n4));
                    n5 = n3 + 1;
                }
                if (extractFloatResult.mEndWithNegOrDot) {
                    n2 = n4;
                    n3 = n5;
                    continue;
                }
                n2 = n4 + 1;
                n3 = n5;
            } while (true);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error in parsing \"");
            stringBuilder.append(string2);
            stringBuilder.append("\"");
            throw new RuntimeException(stringBuilder.toString(), numberFormatException);
        }
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2, PathDataNode[] arrpathDataNode3, float f) {
        if (arrpathDataNode == null) throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        if (arrpathDataNode2 == null) throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        if (arrpathDataNode3 == null) throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        if (arrpathDataNode.length != arrpathDataNode2.length) throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        if (arrpathDataNode2.length != arrpathDataNode3.length) throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        boolean bl = PathParser.canMorph(arrpathDataNode2, arrpathDataNode3);
        int n = 0;
        if (!bl) {
            return false;
        }
        while (n < arrpathDataNode.length) {
            arrpathDataNode[n].interpolatePathDataNode(arrpathDataNode2[n], arrpathDataNode3[n], f);
            ++n;
        }
        return true;
    }

    private static int nextStart(String string2, int n) {
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (((c - 65) * (c - 90) <= 0 || (c - 97) * (c - 122) <= 0) && c != 'e' && c != 'E') {
                return n;
            }
            ++n;
        }
        return n;
    }

    public static void updateNodes(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        int n = 0;
        while (n < arrpathDataNode2.length) {
            arrpathDataNode[n].mType = arrpathDataNode2[n].mType;
            for (int i = 0; i < arrpathDataNode2[n].mParams.length; ++i) {
                arrpathDataNode[n].mParams[i] = arrpathDataNode2[n].mParams[i];
            }
            ++n;
        }
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char c, float[] arrf) {
            this.mType = c;
            this.mParams = arrf;
        }

        PathDataNode(PathDataNode arrf) {
            this.mType = arrf.mType;
            arrf = arrf.mParams;
            this.mParams = PathParser.copyOfRange(arrf, 0, arrf.length);
        }

        /*
         * Unable to fully structure code
         */
        private static void addCommand(Path var0, float[] var1_1, char var2_2, char var3_3, float[] var4_4) {
            var5_5 = var1_1[0];
            var6_6 = var1_1[1];
            var7_7 = var1_1[2];
            var8_8 = var1_1[3];
            var9_9 = var1_1[4];
            var10_10 = var1_1[5];
            var11_11 = var5_5;
            var12_12 = var6_6;
            var13_13 = var7_7;
            var14_14 = var8_8;
            switch (var3_3) {
                default: {
                    var14_14 = var8_8;
                    var13_13 = var7_7;
                    var12_12 = var6_6;
                    var11_11 = var5_5;
                    ** GOTO lbl23
                }
                case 90: 
                case 122: {
                    var0.close();
                    var0.moveTo(var9_9, var10_10);
                    var13_13 = var11_11 = var9_9;
                    var14_14 = var12_12 = var10_10;
                }
lbl23: // 3 sources:
                case 76: 
                case 77: 
                case 84: 
                case 108: 
                case 109: 
                case 116: {
                    var15_15 = 2;
                    ** break;
                }
                case 81: 
                case 83: 
                case 113: 
                case 115: {
                    var15_15 = 4;
                    var11_11 = var5_5;
                    var12_12 = var6_6;
                    var13_13 = var7_7;
                    var14_14 = var8_8;
                    ** break;
                }
                case 72: 
                case 86: 
                case 104: 
                case 118: {
                    var15_15 = 1;
                    var11_11 = var5_5;
                    var12_12 = var6_6;
                    var13_13 = var7_7;
                    var14_14 = var8_8;
                    ** break;
                }
                case 67: 
                case 99: {
                    var15_15 = 6;
                    var11_11 = var5_5;
                    var12_12 = var6_6;
                    var13_13 = var7_7;
                    var14_14 = var8_8;
                    ** break;
                }
                case 65: 
                case 97: 
            }
            var15_15 = 7;
            var14_14 = var8_8;
            var13_13 = var7_7;
            var12_12 = var6_6;
            var11_11 = var5_5;
lbl53: // 5 sources:
            var16_16 = 0;
            var17_17 = var2_2;
            var7_7 = var10_10;
            var8_8 = var9_9;
            var2_2 = var16_16;
            do {
                block42 : {
                    block51 : {
                        block25 : {
                            block26 : {
                                block27 : {
                                    block28 : {
                                        block29 : {
                                            block30 : {
                                                block50 : {
                                                    block43 : {
                                                        block31 : {
                                                            block32 : {
                                                                block33 : {
                                                                    block45 : {
                                                                        block34 : {
                                                                            block35 : {
                                                                                block36 : {
                                                                                    block49 : {
                                                                                        block48 : {
                                                                                            block37 : {
                                                                                                block47 : {
                                                                                                    block46 : {
                                                                                                        block38 : {
                                                                                                            block39 : {
                                                                                                                block44 : {
                                                                                                                    block40 : {
                                                                                                                        block41 : {
                                                                                                                            var16_16 = var3_3;
                                                                                                                            if (var2_2 >= var4_4.length) {
                                                                                                                                var1_1[0] = var11_11;
                                                                                                                                var1_1[1] = var12_12;
                                                                                                                                var1_1[2] = var13_13;
                                                                                                                                var1_1[3] = var14_14;
                                                                                                                                var1_1[4] = var8_8;
                                                                                                                                var1_1[5] = var7_7;
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            if (var16_16 == 65) break block25;
                                                                                                                            if (var16_16 == 67) break block26;
                                                                                                                            if (var16_16 == 72) break block27;
                                                                                                                            if (var16_16 == 81) break block28;
                                                                                                                            if (var16_16 == 86) break block29;
                                                                                                                            if (var16_16 == 97) break block30;
                                                                                                                            if (var16_16 == 99) break block31;
                                                                                                                            if (var16_16 == 104) break block32;
                                                                                                                            if (var16_16 == 113) break block33;
                                                                                                                            if (var16_16 == 118) break block34;
                                                                                                                            if (var16_16 == 76) break block35;
                                                                                                                            if (var16_16 == 77) break block36;
                                                                                                                            if (var16_16 == 83) break block37;
                                                                                                                            if (var16_16 == 84) break block38;
                                                                                                                            if (var16_16 == 108) break block39;
                                                                                                                            if (var16_16 == 109) break block40;
                                                                                                                            if (var16_16 == 115) break block41;
                                                                                                                            if (var16_16 == 116) {
                                                                                                                                if (var17_17 != 113 && var17_17 != 116 && var17_17 != 81 && var17_17 != 84) {
                                                                                                                                    var14_14 = 0.0f;
                                                                                                                                    var13_13 = 0.0f;
                                                                                                                                } else {
                                                                                                                                    var13_13 = var11_11 - var13_13;
                                                                                                                                    var14_14 = var12_12 - var14_14;
                                                                                                                                }
                                                                                                                                var16_16 = var2_2 + 0;
                                                                                                                                var10_10 = var4_4[var16_16];
                                                                                                                                var17_17 = var2_2 + 1;
                                                                                                                                var0.rQuadTo(var13_13, var14_14, var10_10, var4_4[var17_17]);
                                                                                                                                var10_10 = var11_11 + var4_4[var16_16];
                                                                                                                                var9_9 = var12_12 + var4_4[var17_17];
                                                                                                                                var14_14 += var12_12;
                                                                                                                                var13_13 += var11_11;
                                                                                                                                var12_12 = var9_9;
                                                                                                                                var11_11 = var10_10;
                                                                                                                            }
                                                                                                                            break block42;
                                                                                                                        }
                                                                                                                        if (var17_17 != 99 && var17_17 != 115 && var17_17 != 67 && var17_17 != 83) {
                                                                                                                            var14_14 = 0.0f;
                                                                                                                            var13_13 = 0.0f;
                                                                                                                        } else {
                                                                                                                            var10_10 = var12_12 - var14_14;
                                                                                                                            var14_14 = var11_11 - var13_13;
                                                                                                                            var13_13 = var10_10;
                                                                                                                        }
                                                                                                                        var18_18 = var2_2 + 0;
                                                                                                                        var9_9 = var4_4[var18_18];
                                                                                                                        var17_17 = var2_2 + 1;
                                                                                                                        var6_6 = var4_4[var17_17];
                                                                                                                        var19_19 = var2_2 + 2;
                                                                                                                        var10_10 = var4_4[var19_19];
                                                                                                                        var16_16 = var2_2 + 3;
                                                                                                                        var0.rCubicTo(var14_14, var13_13, var9_9, var6_6, var10_10, var4_4[var16_16]);
                                                                                                                        var9_9 = var4_4[var18_18] + var11_11;
                                                                                                                        var13_13 = var4_4[var17_17] + var12_12;
                                                                                                                        var14_14 = var11_11 + var4_4[var19_19];
                                                                                                                        var10_10 = var4_4[var16_16];
                                                                                                                        var11_11 = var9_9;
                                                                                                                        break block43;
                                                                                                                    }
                                                                                                                    var17_17 = var2_2 + 0;
                                                                                                                    var11_11 += var4_4[var17_17];
                                                                                                                    var16_16 = var2_2 + 1;
                                                                                                                    var12_12 += var4_4[var16_16];
                                                                                                                    if (var2_2 <= 0) break block44;
                                                                                                                    var0.rLineTo(var4_4[var17_17], var4_4[var16_16]);
                                                                                                                    break block42;
                                                                                                                }
                                                                                                                var0.rMoveTo(var4_4[var17_17], var4_4[var16_16]);
                                                                                                                ** GOTO lbl193
                                                                                                            }
                                                                                                            var16_16 = var2_2 + 0;
                                                                                                            var10_10 = var4_4[var16_16];
                                                                                                            var17_17 = var2_2 + 1;
                                                                                                            var0.rLineTo(var10_10, var4_4[var17_17]);
                                                                                                            var11_11 += var4_4[var16_16];
                                                                                                            var10_10 = var4_4[var17_17];
                                                                                                            break block45;
                                                                                                        }
                                                                                                        if (var17_17 == 113 || var17_17 == 116 || var17_17 == 81) break block46;
                                                                                                        var9_9 = var12_12;
                                                                                                        var10_10 = var11_11;
                                                                                                        if (var17_17 != 84) break block47;
                                                                                                    }
                                                                                                    var10_10 = var11_11 * 2.0f - var13_13;
                                                                                                    var9_9 = var12_12 * 2.0f - var14_14;
                                                                                                }
                                                                                                var17_17 = var2_2 + 0;
                                                                                                var11_11 = var4_4[var17_17];
                                                                                                var16_16 = var2_2 + 1;
                                                                                                var0.quadTo(var10_10, var9_9, var11_11, var4_4[var16_16]);
                                                                                                var11_11 = var4_4[var17_17];
                                                                                                var12_12 = var4_4[var16_16];
                                                                                                var14_14 = var9_9;
                                                                                                var13_13 = var10_10;
                                                                                                break block42;
                                                                                            }
                                                                                            if (var17_17 == 99 || var17_17 == 115 || var17_17 == 67) break block48;
                                                                                            var9_9 = var12_12;
                                                                                            var10_10 = var11_11;
                                                                                            if (var17_17 != 83) break block49;
                                                                                        }
                                                                                        var10_10 = var11_11 * 2.0f - var13_13;
                                                                                        var9_9 = var12_12 * 2.0f - var14_14;
                                                                                    }
                                                                                    var18_18 = var2_2 + 0;
                                                                                    var12_12 = var4_4[var18_18];
                                                                                    var17_17 = var2_2 + 1;
                                                                                    var13_13 = var4_4[var17_17];
                                                                                    var19_19 = var2_2 + 2;
                                                                                    var11_11 = var4_4[var19_19];
                                                                                    var16_16 = var2_2 + 3;
                                                                                    var0.cubicTo(var10_10, var9_9, var12_12, var13_13, var11_11, var4_4[var16_16]);
                                                                                    var11_11 = var4_4[var18_18];
                                                                                    var13_13 = var4_4[var17_17];
                                                                                    var10_10 = var4_4[var19_19];
                                                                                    var12_12 = var4_4[var16_16];
                                                                                    break block50;
                                                                                }
                                                                                var16_16 = var2_2 + 0;
                                                                                var11_11 = var4_4[var16_16];
                                                                                var17_17 = var2_2 + 1;
                                                                                var12_12 = var4_4[var17_17];
                                                                                if (var2_2 > 0) {
                                                                                    var0.lineTo(var4_4[var16_16], var4_4[var17_17]);
                                                                                } else {
                                                                                    var0.moveTo(var4_4[var16_16], var4_4[var17_17]);
lbl193: // 2 sources:
                                                                                    var7_7 = var12_12;
                                                                                    var8_8 = var11_11;
                                                                                }
                                                                                break block42;
                                                                            }
                                                                            var17_17 = var2_2 + 0;
                                                                            var11_11 = var4_4[var17_17];
                                                                            var16_16 = var2_2 + 1;
                                                                            var0.lineTo(var11_11, var4_4[var16_16]);
                                                                            var11_11 = var4_4[var17_17];
                                                                            var12_12 = var4_4[var16_16];
                                                                            break block42;
                                                                        }
                                                                        var17_17 = var2_2 + 0;
                                                                        var0.rLineTo(0.0f, var4_4[var17_17]);
                                                                        var10_10 = var4_4[var17_17];
                                                                    }
                                                                    var12_12 += var10_10;
                                                                    break block42;
                                                                }
                                                                var16_16 = var2_2 + 0;
                                                                var10_10 = var4_4[var16_16];
                                                                var18_18 = var2_2 + 1;
                                                                var13_13 = var4_4[var18_18];
                                                                var17_17 = var2_2 + 2;
                                                                var14_14 = var4_4[var17_17];
                                                                var19_19 = var2_2 + 3;
                                                                var0.rQuadTo(var10_10, var13_13, var14_14, var4_4[var19_19]);
                                                                var9_9 = var4_4[var16_16] + var11_11;
                                                                var13_13 = var4_4[var18_18] + var12_12;
                                                                var14_14 = var11_11 + var4_4[var17_17];
                                                                var10_10 = var4_4[var19_19];
                                                                var11_11 = var9_9;
                                                                break block43;
                                                            }
                                                            var17_17 = var2_2 + 0;
                                                            var0.rLineTo(var4_4[var17_17], 0.0f);
                                                            var11_11 += var4_4[var17_17];
                                                            break block42;
                                                        }
                                                        var9_9 = var4_4[var2_2 + 0];
                                                        var6_6 = var4_4[var2_2 + 1];
                                                        var17_17 = var2_2 + 2;
                                                        var14_14 = var4_4[var17_17];
                                                        var16_16 = var2_2 + 3;
                                                        var13_13 = var4_4[var16_16];
                                                        var18_18 = var2_2 + 4;
                                                        var10_10 = var4_4[var18_18];
                                                        var19_19 = var2_2 + 5;
                                                        var0.rCubicTo(var9_9, var6_6, var14_14, var13_13, var10_10, var4_4[var19_19]);
                                                        var9_9 = var4_4[var17_17] + var11_11;
                                                        var13_13 = var4_4[var16_16] + var12_12;
                                                        var14_14 = var11_11 + var4_4[var18_18];
                                                        var10_10 = var4_4[var19_19];
                                                        var11_11 = var9_9;
                                                    }
                                                    var12_12 += var10_10;
                                                    var10_10 = var14_14;
                                                }
                                                var14_14 = var13_13;
                                                var13_13 = var11_11;
                                                var11_11 = var10_10;
                                                break block42;
                                            }
                                            var17_17 = var2_2 + 5;
                                            var9_9 = var4_4[var17_17];
                                            var16_16 = var2_2 + 6;
                                            var10_10 = var4_4[var16_16];
                                            var14_14 = var4_4[var2_2 + 0];
                                            var6_6 = var4_4[var2_2 + 1];
                                            var13_13 = var4_4[var2_2 + 2];
                                            var20_20 = var4_4[var2_2 + 3] != 0.0f;
                                            var21_21 = var4_4[var2_2 + 4] != 0.0f;
                                            PathDataNode.drawArc(var0, var11_11, var12_12, var9_9 + var11_11, var10_10 + var12_12, var14_14, var6_6, var13_13, var20_20, var21_21);
                                            var11_11 += var4_4[var17_17];
                                            var12_12 += var4_4[var16_16];
                                            break block51;
                                        }
                                        var17_17 = var2_2 + 0;
                                        var0.lineTo(var11_11, var4_4[var17_17]);
                                        var12_12 = var4_4[var17_17];
                                        break block42;
                                    }
                                    var17_17 = var2_2;
                                    var19_19 = var17_17 + 0;
                                    var12_12 = var4_4[var19_19];
                                    var16_16 = var17_17 + 1;
                                    var11_11 = var4_4[var16_16];
                                    var18_18 = var17_17 + 2;
                                    var13_13 = var4_4[var18_18];
                                    var0.quadTo(var12_12, var11_11, var13_13, var4_4[var17_17 += 3]);
                                    var13_13 = var4_4[var19_19];
                                    var14_14 = var4_4[var16_16];
                                    var11_11 = var4_4[var18_18];
                                    var12_12 = var4_4[var17_17];
                                    break block42;
                                }
                                var17_17 = var2_2 + 0;
                                var0.lineTo(var4_4[var17_17], var12_12);
                                var11_11 = var4_4[var17_17];
                                break block42;
                            }
                            var17_17 = var2_2;
                            var12_12 = var4_4[var17_17 + 0];
                            var14_14 = var4_4[var17_17 + 1];
                            var16_16 = var17_17 + 2;
                            var10_10 = var4_4[var16_16];
                            var18_18 = var17_17 + 3;
                            var11_11 = var4_4[var18_18];
                            var19_19 = var17_17 + 4;
                            var13_13 = var4_4[var19_19];
                            var0.cubicTo(var12_12, var14_14, var10_10, var11_11, var13_13, var4_4[var17_17 += 5]);
                            var11_11 = var4_4[var19_19];
                            var12_12 = var4_4[var17_17];
                            var13_13 = var4_4[var16_16];
                            var14_14 = var4_4[var18_18];
                            break block42;
                        }
                        var17_17 = var2_2;
                        var19_19 = var17_17 + 5;
                        var9_9 = var4_4[var19_19];
                        var16_16 = var17_17 + 6;
                        var13_13 = var4_4[var16_16];
                        var10_10 = var4_4[var17_17 + 0];
                        var14_14 = var4_4[var17_17 + 1];
                        var6_6 = var4_4[var17_17 + 2];
                        var20_20 = var4_4[var17_17 + 3] != 0.0f;
                        var21_21 = var4_4[var17_17 + 4] != 0.0f;
                        PathDataNode.drawArc(var0, var11_11, var12_12, var9_9, var13_13, var10_10, var14_14, var6_6, var20_20, var21_21);
                        var11_11 = var4_4[var19_19];
                        var12_12 = var4_4[var16_16];
                    }
                    var14_14 = var12_12;
                    var13_13 = var11_11;
                }
                var2_2 += var15_15;
                var17_17 = var3_3;
            } while (true);
        }

        private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            int n = (int)Math.ceil(Math.abs(d9 * 4.0 / 3.141592653589793));
            double d10 = Math.cos(d7);
            double d11 = Math.sin(d7);
            double d12 = Math.cos(d8);
            double d13 = Math.sin(d8);
            d7 = -d3;
            double d14 = d7 * d10;
            double d15 = d4 * d11;
            double d16 = d4 * d10;
            double d17 = d13 * (d7 *= d11) + d12 * d16;
            d4 = d14 * d13 - d15 * d12;
            int n2 = 0;
            d12 = d5;
            d5 = d4;
            d13 = d8;
            d8 = d6;
            d4 = d7;
            d7 = d9 /= (double)n;
            d6 = d11;
            d9 = d10;
            do {
                d10 = d3;
                if (n2 >= n) return;
                double d18 = d13 + d7;
                double d19 = Math.sin(d18);
                double d20 = Math.cos(d18);
                double d21 = d + d10 * d9 * d20 - d15 * d19;
                d10 = d2 + d10 * d6 * d20 + d16 * d19;
                d11 = d14 * d19 - d15 * d20;
                d20 = d19 * d4 + d20 * d16;
                d19 = d18 - d13;
                d13 = Math.tan(d19 / 2.0);
                d13 = Math.sin(d19) * (Math.sqrt(d13 * 3.0 * d13 + 4.0) - 1.0) / 3.0;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float)(d12 + d5 * d13), (float)(d8 + d17 * d13), (float)(d21 - d13 * d11), (float)(d10 - d13 * d20), (float)d21, (float)d10);
                ++n2;
                d12 = d21;
                d13 = d18;
                d17 = d20;
                d5 = d11;
                d8 = d10;
            } while (true);
        }

        private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean bl, boolean bl2) {
            double d = Math.toRadians(f7);
            double d2 = Math.cos(d);
            double d3 = Math.sin(d);
            double d4 = f;
            double d5 = f2;
            double d6 = f5;
            double d7 = (d4 * d2 + d5 * d3) / d6;
            double d8 = -f;
            double d9 = f6;
            double d10 = (d8 * d3 + d5 * d2) / d9;
            double d11 = f3;
            d8 = f4;
            double d12 = (d11 * d2 + d8 * d3) / d6;
            double d13 = ((double)(-f3) * d3 + d8 * d2) / d9;
            double d14 = d7 - d12;
            double d15 = d10 - d13;
            d11 = (d7 + d12) / 2.0;
            d8 = (d10 + d13) / 2.0;
            double d16 = d14 * d14 + d15 * d15;
            if (d16 == 0.0) {
                Log.w((String)PathParser.LOGTAG, (String)" Points are coincident");
                return;
            }
            double d17 = 1.0 / d16 - 0.25;
            if (d17 < 0.0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Points are too far apart ");
                stringBuilder.append(d16);
                Log.w((String)PathParser.LOGTAG, (String)stringBuilder.toString());
                float f8 = (float)(Math.sqrt(d16) / 1.99999);
                PathDataNode.drawArc(path, f, f2, f3, f4, f5 * f8, f6 * f8, f7, bl, bl2);
                return;
            }
            d16 = Math.sqrt(d17);
            d14 *= d16;
            d15 = d16 * d15;
            if (bl == bl2) {
                d11 -= d15;
                d8 += d14;
            } else {
                d11 += d15;
                d8 -= d14;
            }
            d10 = Math.atan2(d10 - d8, d7 - d11);
            d12 = Math.atan2(d13 - d8, d12 - d11) - d10;
            double d18 = d12 DCMPL 0.0;
            bl = d18 >= 0;
            d7 = d12;
            if (bl2 != bl) {
                d7 = d18 > 0 ? d12 - 6.283185307179586 : d12 + 6.283185307179586;
            }
            PathDataNode.arcToBezier(path, (d11 *= d6) * d2 - (d8 *= d9) * d3, d11 * d3 + d8 * d2, d6, d9, d4, d5, d, d10, d7);
        }

        public static void nodesToPath(PathDataNode[] arrpathDataNode, Path path) {
            float[] arrf = new float[6];
            char c = 'm';
            int n = 0;
            char c2 = c;
            while (n < arrpathDataNode.length) {
                PathDataNode.addCommand(path, arrf, c2, arrpathDataNode[n].mType, arrpathDataNode[n].mParams);
                c = arrpathDataNode[n].mType;
                ++n;
                c2 = c;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            float[] arrf;
            this.mType = pathDataNode.mType;
            int n = 0;
            while (n < (arrf = pathDataNode.mParams).length) {
                this.mParams[n] = arrf[n] * (1.0f - f) + pathDataNode2.mParams[n] * f;
                ++n;
            }
        }
    }

}

