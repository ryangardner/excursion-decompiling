/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import java.util.Formatter;

final class DetectionResult {
    private static final int ADJUST_ROW_NUMBER_SKIP = 2;
    private final int barcodeColumnCount;
    private final BarcodeMetadata barcodeMetadata;
    private BoundingBox boundingBox;
    private final DetectionResultColumn[] detectionResultColumns;

    DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        int n;
        this.barcodeMetadata = barcodeMetadata;
        this.barcodeColumnCount = n = barcodeMetadata.getColumnCount();
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[n + 2];
    }

    private void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn == null) return;
        ((DetectionResultRowIndicatorColumn)detectionResultColumn).adjustCompleteIndicatorColumnRowNumbers(this.barcodeMetadata);
    }

    private static boolean adjustRowNumber(Codeword codeword, Codeword codeword2) {
        if (codeword2 == null) {
            return false;
        }
        if (!codeword2.hasValidRowNumber()) return false;
        if (codeword2.getBucket() != codeword.getBucket()) return false;
        codeword.setRowNumber(codeword2.getRowNumber());
        return true;
    }

    private static int adjustRowNumberIfValid(int n, int n2, Codeword codeword) {
        if (codeword == null) {
            return n2;
        }
        int n3 = n2;
        if (codeword.hasValidRowNumber()) return n3;
        if (!codeword.isValidRowNumber(n)) return n2 + 1;
        codeword.setRowNumber(n);
        return 0;
    }

    private int adjustRowNumbers() {
        int n = this.adjustRowNumbersByRow();
        if (n == 0) {
            return 0;
        }
        int n2 = 1;
        while (n2 < this.barcodeColumnCount + 1) {
            Codeword[] arrcodeword = this.detectionResultColumns[n2].getCodewords();
            for (int i = 0; i < arrcodeword.length; ++i) {
                if (arrcodeword[i] == null || arrcodeword[i].hasValidRowNumber()) continue;
                this.adjustRowNumbers(n2, i, arrcodeword);
            }
            ++n2;
        }
        return n;
    }

    private void adjustRowNumbers(int n, int n2, Codeword[] arrcodeword) {
        Codeword codeword = arrcodeword[n2];
        Codeword[] arrcodeword2 = this.detectionResultColumns[n - 1].getCodewords();
        Object[] arrobject = this.detectionResultColumns;
        arrobject = arrobject[++n] != null ? arrobject[n].getCodewords() : arrcodeword2;
        Codeword[] arrcodeword3 = new Codeword[14];
        arrcodeword3[2] = arrcodeword2[n2];
        arrcodeword3[3] = arrobject[n2];
        int n3 = 0;
        if (n2 > 0) {
            n = n2 - 1;
            arrcodeword3[0] = arrcodeword[n];
            arrcodeword3[4] = arrcodeword2[n];
            arrcodeword3[5] = arrobject[n];
        }
        if (n2 > 1) {
            n = n2 - 2;
            arrcodeword3[8] = arrcodeword[n];
            arrcodeword3[10] = arrcodeword2[n];
            arrcodeword3[11] = arrobject[n];
        }
        if (n2 < arrcodeword.length - 1) {
            n = n2 + 1;
            arrcodeword3[1] = arrcodeword[n];
            arrcodeword3[6] = arrcodeword2[n];
            arrcodeword3[7] = arrobject[n];
        }
        n = n3;
        if (n2 < arrcodeword.length - 2) {
            n = n2 + 2;
            arrcodeword3[9] = arrcodeword[n];
            arrcodeword3[12] = arrcodeword2[n];
            arrcodeword3[13] = arrobject[n];
            n = n3;
        }
        while (n < 14) {
            if (DetectionResult.adjustRowNumber(codeword, arrcodeword3[n])) {
                return;
            }
            ++n;
        }
    }

    private int adjustRowNumbersByRow() {
        this.adjustRowNumbersFromBothRI();
        return this.adjustRowNumbersFromLRI() + this.adjustRowNumbersFromRRI();
    }

    private void adjustRowNumbersFromBothRI() {
        Object[] arrobject = this.detectionResultColumns;
        int n = 0;
        if (arrobject[0] == null) return;
        if (arrobject[this.barcodeColumnCount + 1] == null) {
            return;
        }
        arrobject = arrobject[0].getCodewords();
        Codeword[] arrcodeword = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
        while (n < arrobject.length) {
            if (arrobject[n] != null && arrcodeword[n] != null && ((Codeword)arrobject[n]).getRowNumber() == arrcodeword[n].getRowNumber()) {
                for (int i = 1; i <= this.barcodeColumnCount; ++i) {
                    Codeword codeword = this.detectionResultColumns[i].getCodewords()[n];
                    if (codeword == null) continue;
                    codeword.setRowNumber(((Codeword)arrobject[n]).getRowNumber());
                    if (codeword.hasValidRowNumber()) continue;
                    this.detectionResultColumns[i].getCodewords()[n] = null;
                }
            }
            ++n;
        }
    }

    private int adjustRowNumbersFromLRI() {
        Object object = this.detectionResultColumns;
        if (object[0] == null) {
            return 0;
        }
        Codeword[] arrcodeword = object[0].getCodewords();
        int n = 0;
        int n2 = 0;
        while (n < arrcodeword.length) {
            int n3;
            if (arrcodeword[n] == null) {
                n3 = n2;
            } else {
                int n4 = arrcodeword[n].getRowNumber();
                int n5 = 1;
                int n6 = 0;
                do {
                    n3 = n2;
                    if (n5 >= this.barcodeColumnCount + 1) break;
                    n3 = n2;
                    if (n6 >= 2) break;
                    object = this.detectionResultColumns[n5].getCodewords()[n];
                    int n7 = n2;
                    n3 = n6;
                    if (object != null) {
                        n6 = DetectionResult.adjustRowNumberIfValid(n4, n6, (Codeword)object);
                        n7 = n2;
                        n3 = n6;
                        if (!((Codeword)object).hasValidRowNumber()) {
                            n7 = n2 + 1;
                            n3 = n6;
                        }
                    }
                    ++n5;
                    n2 = n7;
                    n6 = n3;
                } while (true);
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    private int adjustRowNumbersFromRRI() {
        Object[] arrobject = this.detectionResultColumns;
        int n = this.barcodeColumnCount;
        if (arrobject[n + 1] == null) {
            return 0;
        }
        arrobject = arrobject[n + 1].getCodewords();
        int n2 = 0;
        n = 0;
        while (n2 < arrobject.length) {
            int n3;
            if (arrobject[n2] == null) {
                n3 = n;
            } else {
                int n4 = ((Codeword)arrobject[n2]).getRowNumber();
                int n5 = this.barcodeColumnCount + 1;
                int n6 = 0;
                do {
                    n3 = n;
                    if (n5 <= 0) break;
                    n3 = n;
                    if (n6 >= 2) break;
                    Codeword codeword = this.detectionResultColumns[n5].getCodewords()[n2];
                    int n7 = n;
                    n3 = n6;
                    if (codeword != null) {
                        n6 = DetectionResult.adjustRowNumberIfValid(n4, n6, codeword);
                        n7 = n;
                        n3 = n6;
                        if (!codeword.hasValidRowNumber()) {
                            n7 = n + 1;
                            n3 = n6;
                        }
                    }
                    --n5;
                    n = n7;
                    n6 = n3;
                } while (true);
            }
            ++n2;
            n = n3;
        }
        return n;
    }

    int getBarcodeColumnCount() {
        return this.barcodeColumnCount;
    }

    int getBarcodeECLevel() {
        return this.barcodeMetadata.getErrorCorrectionLevel();
    }

    int getBarcodeRowCount() {
        return this.barcodeMetadata.getRowCount();
    }

    BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    DetectionResultColumn getDetectionResultColumn(int n) {
        return this.detectionResultColumns[n];
    }

    DetectionResultColumn[] getDetectionResultColumns() {
        int n;
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
        int n2 = 928;
        while ((n = this.adjustRowNumbers()) > 0) {
            if (n >= n2) {
                return this.detectionResultColumns;
            }
            n2 = n;
        }
        return this.detectionResultColumns;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    void setDetectionResultColumn(int n, DetectionResultColumn detectionResultColumn) {
        this.detectionResultColumns[n] = detectionResultColumn;
    }

    public String toString() {
        Object object;
        Object object2 = this.detectionResultColumns;
        Object object3 = object = object2[0];
        if (object == null) {
            object3 = object2[this.barcodeColumnCount + 1];
        }
        object = new Formatter();
        int n = 0;
        do {
            if (n >= ((DetectionResultColumn)object3).getCodewords().length) {
                object3 = ((Formatter)object).toString();
                ((Formatter)object).close();
                return object3;
            }
            ((Formatter)object).format("CW %3d:", n);
            for (int i = 0; i < this.barcodeColumnCount + 2; ++i) {
                object2 = this.detectionResultColumns;
                if (object2[i] == null) {
                    ((Formatter)object).format("    |   ", new Object[0]);
                    continue;
                }
                if ((object2 = object2[i].getCodewords()[n]) == null) {
                    ((Formatter)object).format("    |   ", new Object[0]);
                    continue;
                }
                ((Formatter)object).format(" %3d|%3d", ((Codeword)object2).getRowNumber(), ((Codeword)object2).getValue());
            }
            ((Formatter)object).format("%n", new Object[0]);
            ++n;
        } while (true);
    }
}

