/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ResultPoint;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;

final class DetectionResultRowIndicatorColumn
extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean bl) {
        super(boundingBox);
        this.isLeft = bl;
    }

    private void removeIncorrectCodewords(Codeword[] arrcodeword, BarcodeMetadata barcodeMetadata) {
        int n = 0;
        while (n < arrcodeword.length) {
            Codeword codeword = arrcodeword[n];
            if (arrcodeword[n] != null) {
                int n2 = codeword.getValue() % 30;
                int n3 = codeword.getRowNumber();
                if (n3 > barcodeMetadata.getRowCount()) {
                    arrcodeword[n] = null;
                } else {
                    int n4 = n3;
                    if (!this.isLeft) {
                        n4 = n3 + 2;
                    }
                    if ((n4 %= 3) != 0) {
                        if (n4 != 1) {
                            if (n4 == 2 && n2 + 1 != barcodeMetadata.getColumnCount()) {
                                arrcodeword[n] = null;
                            }
                        } else if (n2 / 3 != barcodeMetadata.getErrorCorrectionLevel() || n2 % 3 != barcodeMetadata.getRowCountLowerPart()) {
                            arrcodeword[n] = null;
                        }
                    } else if (n2 * 3 + 1 != barcodeMetadata.getRowCountUpperPart()) {
                        arrcodeword[n] = null;
                    }
                }
            }
            ++n;
        }
    }

    /*
     * Unable to fully structure code
     */
    int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata var1_1) {
        var2_2 = this.getCodewords();
        this.setRowNumbers();
        this.removeIncorrectCodewords(var2_2, var1_1);
        var3_3 = this.getBoundingBox();
        var4_4 = this.isLeft != false ? var3_3.getTopLeft() : var3_3.getTopRight();
        var3_3 = this.isLeft != false ? var3_3.getBottomLeft() : var3_3.getBottomRight();
        var5_5 = this.imageRowToCodewordIndex((int)var4_4.getY());
        var6_6 = this.imageRowToCodewordIndex((int)var3_3.getY());
        var7_7 = (float)(var6_6 - var5_5) / (float)var1_1.getRowCount();
        var8_8 = -1;
        var9_9 = 0;
        var10_10 = 1;
        while (var5_5 < var6_6) {
            block6 : {
                block12 : {
                    block10 : {
                        block11 : {
                            block9 : {
                                block8 : {
                                    block7 : {
                                        if (var2_2[var5_5] != null) break block7;
                                        var11_11 = var9_9;
                                        break block6;
                                    }
                                    var4_4 = var2_2[var5_5];
                                    var11_11 = var4_4.getRowNumber() - var8_8;
                                    if (var11_11 != 0) break block8;
                                    var11_11 = var9_9 + 1;
                                    break block6;
                                }
                                if (var11_11 != 1) break block9;
                                var10_10 = Math.max(var10_10, var9_9);
                                var11_11 = var4_4.getRowNumber();
                                break block10;
                            }
                            if (var11_11 < 0 || var4_4.getRowNumber() >= var1_1.getRowCount() || var11_11 > var5_5) break block11;
                            var12_12 = var11_11;
                            if (var10_10 > 2) {
                                var12_12 = var11_11 * (var10_10 - 2);
                            }
                            var11_11 = var12_12 >= var5_5 ? 1 : 0;
                            break block12;
                        }
                        var2_2[var5_5] = null;
                        var11_11 = var9_9;
                        break block6;
                    }
lbl40: // 2 sources:
                    do {
                        var9_9 = 1;
                        var8_8 = var11_11;
                        var11_11 = var9_9;
                        break block6;
                        break;
                    } while (true);
                }
                for (var13_13 = 1; var13_13 <= var12_12 && var11_11 == 0; ++var13_13) {
                    var11_11 = var2_2[var5_5 - var13_13] != null ? 1 : 0;
                }
                if (var11_11 != 0) {
                    var2_2[var5_5] = null;
                    var11_11 = var9_9;
                } else {
                    var11_11 = var4_4.getRowNumber();
                    ** continue;
                }
            }
            ++var5_5;
            var9_9 = var11_11;
        }
        return (int)((double)var7_7 + 0.5);
    }

    /*
     * Unable to fully structure code
     */
    int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata var1_1) {
        var2_2 = this.getBoundingBox();
        var3_7 = this.isLeft != false ? var2_2.getTopLeft() : var2_2.getTopRight();
        if (this.isLeft) {
            var2_3 = var2_2.getBottomLeft();
        } else {
            var2_4 = var2_2.getBottomRight();
        }
        var4_8 = this.imageRowToCodewordIndex((int)var3_7.getY());
        var5_9 = this.imageRowToCodewordIndex((int)var2_5.getY());
        var6_10 = (float)(var5_9 - var4_8) / (float)var1_1.getRowCount();
        var2_6 = this.getCodewords();
        var7_11 = -1;
        var8_12 = 0;
        var9_13 = 1;
        while (var4_8 < var5_9) {
            block5 : {
                block7 : {
                    block6 : {
                        if (var2_6[var4_8] == null) break block5;
                        var3_7 = var2_6[var4_8];
                        var3_7.setRowNumberAsRowIndicatorColumn();
                        var10_14 = var3_7.getRowNumber() - var7_11;
                        if (var10_14 != 0) break block6;
                        ++var8_12;
                        break block5;
                    }
                    if (var10_14 != 1) break block7;
                    var9_13 = Math.max(var9_13, var8_12);
                    var8_12 = var3_7.getRowNumber();
                    ** GOTO lbl32
                }
                if (var3_7.getRowNumber() >= var1_1.getRowCount()) {
                    var2_6[var4_8] = null;
                } else {
                    var8_12 = var3_7.getRowNumber();
lbl32: // 2 sources:
                    var10_14 = 1;
                    var7_11 = var8_12;
                    var8_12 = var10_14;
                }
            }
            ++var4_8;
        }
        return (int)((double)var6_10 + 0.5);
    }

    BarcodeMetadata getBarcodeMetadata() {
        Codeword[] arrcodeword = this.getCodewords();
        Object object = new BarcodeValue();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        int n = arrcodeword.length;
        for (int i = 0; i < n; ++i) {
            int n2;
            Codeword codeword = arrcodeword[i];
            if (codeword == null) continue;
            codeword.setRowNumberAsRowIndicatorColumn();
            int n3 = codeword.getValue() % 30;
            int n4 = n2 = codeword.getRowNumber();
            if (!this.isLeft) {
                n4 = n2 + 2;
            }
            if ((n4 %= 3) != 0) {
                if (n4 != 1) {
                    if (n4 != 2) continue;
                    ((BarcodeValue)object).setValue(n3 + 1);
                    continue;
                }
                barcodeValue3.setValue(n3 / 3);
                barcodeValue2.setValue(n3 % 3);
                continue;
            }
            barcodeValue.setValue(n3 * 3 + 1);
        }
        if (((BarcodeValue)object).getValue().length == 0) return null;
        if (barcodeValue.getValue().length == 0) return null;
        if (barcodeValue2.getValue().length == 0) return null;
        if (barcodeValue3.getValue().length == 0) return null;
        if (((BarcodeValue)object).getValue()[0] < 1) return null;
        if (barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] < 3) return null;
        if (barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] > 90) {
            return null;
        }
        object = new BarcodeMetadata(((BarcodeValue)object).getValue()[0], barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0]);
        this.removeIncorrectCodewords(arrcodeword, (BarcodeMetadata)object);
        return object;
    }

    int[] getRowHeights() throws FormatException {
        int[] arrn = this.getBarcodeMetadata();
        if (arrn == null) {
            return null;
        }
        this.adjustIncompleteIndicatorColumnRowNumbers((BarcodeMetadata)arrn);
        int n = arrn.getRowCount();
        arrn = new int[n];
        Codeword[] arrcodeword = this.getCodewords();
        int n2 = arrcodeword.length;
        int n3 = 0;
        while (n3 < n2) {
            Codeword codeword = arrcodeword[n3];
            if (codeword != null) {
                int n4 = codeword.getRowNumber();
                if (n4 >= n) throw FormatException.getFormatInstance();
                arrn[n4] = arrn[n4] + 1;
            }
            ++n3;
        }
        return arrn;
    }

    boolean isLeft() {
        return this.isLeft;
    }

    void setRowNumbers() {
        Codeword[] arrcodeword = this.getCodewords();
        int n = arrcodeword.length;
        int n2 = 0;
        while (n2 < n) {
            Codeword codeword = arrcodeword[n2];
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
            }
            ++n2;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IsLeft: ");
        stringBuilder.append(this.isLeft);
        stringBuilder.append('\n');
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

