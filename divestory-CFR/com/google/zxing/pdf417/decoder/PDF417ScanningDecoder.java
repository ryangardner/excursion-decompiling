/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DecodedBitStreamParser;
import com.google.zxing.pdf417.decoder.DetectionResult;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import com.google.zxing.pdf417.decoder.PDF417CodewordDecoder;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.util.ArrayList;
import java.util.Formatter;

public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) throws NotFoundException, FormatException {
        int n;
        if (detectionResultRowIndicatorColumn == null) {
            return null;
        }
        int[] arrn = detectionResultRowIndicatorColumn.getRowHeights();
        if (arrn == null) {
            return null;
        }
        int n2 = PDF417ScanningDecoder.getMax(arrn);
        int n3 = arrn.length;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        do {
            n = n6;
            if (n5 >= n3) break;
            n = arrn[n5];
            n6 += n2 - n;
            if (n > 0) {
                n = n6;
                break;
            }
            ++n5;
        } while (true);
        Codeword[] arrcodeword = detectionResultRowIndicatorColumn.getCodewords();
        n6 = 0;
        for (n5 = n; n5 > 0 && arrcodeword[n6] == null; --n5, ++n6) {
        }
        n3 = arrn.length - 1;
        n = n4;
        do {
            n6 = n;
            if (n3 < 0) break;
            n6 = n + (n2 - arrn[n3]);
            if (arrn[n3] > 0) break;
            --n3;
            n = n6;
        } while (true);
        n3 = arrcodeword.length - 1;
        n = n6;
        n6 = n3;
        while (n > 0) {
            if (arrcodeword[n6] != null) return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(n5, n, detectionResultRowIndicatorColumn.isLeft());
            --n;
            --n6;
        }
        return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(n5, n, detectionResultRowIndicatorColumn.isLeft());
    }

    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] arrbarcodeValue) throws NotFoundException {
        int[] arrn = arrbarcodeValue[0][1].getValue();
        int n = detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount() - PDF417ScanningDecoder.getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
        if (arrn.length == 0) {
            if (n < 1) throw NotFoundException.getNotFoundInstance();
            if (n > 928) throw NotFoundException.getNotFoundInstance();
            arrbarcodeValue[0][1].setValue(n);
            return;
        }
        if (arrn[0] == n) return;
        arrbarcodeValue[0][1].setValue(n);
    }

    private static int adjustCodewordStartColumn(BitMatrix bitMatrix, int n, int n2, boolean bl, int n3, int n4) {
        int n5 = bl ? -1 : 1;
        int n6 = 0;
        int n7 = n3;
        while (n6 < 2) {
            while ((bl && n7 >= n || !bl && n7 < n2) && bl == bitMatrix.get(n7, n4)) {
                if (Math.abs(n3 - n7) > 2) {
                    return n3;
                }
                n7 += n5;
            }
            n5 = -n5;
            bl ^= true;
            ++n6;
        }
        return n7;
    }

    private static boolean checkCodewordSkew(int n, int n2, int n3) {
        if (n2 - 2 > n) return false;
        if (n > n3 + 2) return false;
        return true;
    }

    private static int correctErrors(int[] arrn, int[] arrn2, int n) throws ChecksumException {
        if (arrn2 != null) {
            if (arrn2.length > n / 2 + 3) throw ChecksumException.getChecksumInstance();
        }
        if (n < 0) throw ChecksumException.getChecksumInstance();
        if (n > 512) throw ChecksumException.getChecksumInstance();
        return errorCorrection.decode(arrn, n, arrn2);
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult arrdetectionResultColumn) throws FormatException {
        int n2;
        int n;
        BarcodeValue[][] arrbarcodeValue = new BarcodeValue[arrdetectionResultColumn.getBarcodeRowCount()][arrdetectionResultColumn.getBarcodeColumnCount() + 2];
        for (n = 0; n < arrbarcodeValue.length; ++n) {
            for (n2 = 0; n2 < arrbarcodeValue[n].length; ++n2) {
                arrbarcodeValue[n][n2] = new BarcodeValue();
            }
        }
        arrdetectionResultColumn = arrdetectionResultColumn.getDetectionResultColumns();
        int n3 = arrdetectionResultColumn.length;
        n2 = 0;
        n = 0;
        while (n2 < n3) {
            DetectionResultColumn detectionResultColumn = arrdetectionResultColumn[n2];
            if (detectionResultColumn != null) {
                for (Codeword codeword : detectionResultColumn.getCodewords()) {
                    int n4;
                    if (codeword == null || (n4 = codeword.getRowNumber()) < 0) continue;
                    if (n4 >= arrbarcodeValue.length) throw FormatException.getFormatInstance();
                    arrbarcodeValue[n4][n].setValue(codeword.getValue());
                }
            }
            ++n;
            ++n2;
        }
        return arrbarcodeValue;
    }

    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws FormatException, ChecksumException, NotFoundException {
        int n;
        int n2;
        Object object = PDF417ScanningDecoder.createBarcodeMatrix(detectionResult);
        PDF417ScanningDecoder.adjustCodewordCount(detectionResult, object);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int[] arrn = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
        ArrayList<BarcodeValue[]> arrayList2 = new ArrayList<BarcodeValue[]>();
        ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
        int n3 = 0;
        for (n = 0; n < detectionResult.getBarcodeRowCount(); ++n) {
            n2 = 0;
            while (n2 < detectionResult.getBarcodeColumnCount()) {
                Object[] arrobject = object[n];
                int n4 = n2 + 1;
                arrobject = arrobject[n4].getValue();
                n2 = detectionResult.getBarcodeColumnCount() * n + n2;
                if (arrobject.length == 0) {
                    arrayList.add(n2);
                } else if (arrobject.length == 1) {
                    arrn[n2] = (int)arrobject[0];
                } else {
                    arrayList3.add(n2);
                    arrayList2.add((BarcodeValue[])arrobject);
                }
                n2 = n4;
            }
        }
        n2 = arrayList2.size();
        object = new int[n2][];
        n = n3;
        while (n < n2) {
            object[n] = (int[])arrayList2.get(n);
            ++n;
        }
        return PDF417ScanningDecoder.createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), arrn, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), (int[][])object);
    }

    private static DecoderResult createDecoderResultFromAmbiguousValues(int n, int[] arrn, int[] arrn2, int[] arrn3, int[][] arrn4) throws FormatException, ChecksumException {
        int n2 = arrn3.length;
        int[] arrn5 = new int[n2];
        int n3 = 100;
        while (n3 > 0) {
            int n4;
            for (n4 = 0; n4 < n2; ++n4) {
                arrn[arrn3[n4]] = arrn4[n4][arrn5[n4]];
            }
            try {
                return PDF417ScanningDecoder.decodeCodewords(arrn, n, arrn2);
            }
            catch (ChecksumException checksumException) {
                if (n2 == 0) throw ChecksumException.getChecksumInstance();
                for (n4 = 0; n4 < n2; ++n4) {
                    if (arrn5[n4] < arrn4[n4].length - 1) {
                        arrn5[n4] = arrn5[n4] + 1;
                        break;
                    }
                    arrn5[n4] = 0;
                    if (n4 == n2 - 1) throw ChecksumException.getChecksumInstance();
                }
                --n3;
            }
        }
        throw ChecksumException.getChecksumInstance();
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint object, ResultPoint object2, ResultPoint resultPoint, ResultPoint object3, int n, int n2) throws NotFoundException, FormatException, ChecksumException {
        int n3;
        Object object4;
        Object object5;
        BoundingBox boundingBox;
        Object object6;
        block11 : {
            boundingBox = new BoundingBox(bitMatrix, (ResultPoint)object, (ResultPoint)object2, resultPoint, (ResultPoint)object3);
            object3 = null;
            object6 = object2 = null;
            n3 = 0;
            do {
                object5 = object3;
                object4 = object2;
                if (n3 >= 2) break block11;
                if (object != null) {
                    object3 = PDF417ScanningDecoder.getRowIndicatorColumn(bitMatrix, boundingBox, (ResultPoint)object, true, n, n2);
                }
                if (resultPoint != null) {
                    object2 = PDF417ScanningDecoder.getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint, false, n, n2);
                }
                if ((object6 = PDF417ScanningDecoder.merge((DetectionResultRowIndicatorColumn)object3, (DetectionResultRowIndicatorColumn)object2)) == null) throw NotFoundException.getNotFoundInstance();
                if (n3 != 0 || ((DetectionResult)object6).getBoundingBox() == null || ((DetectionResult)object6).getBoundingBox().getMinY() >= boundingBox.getMinY() && ((DetectionResult)object6).getBoundingBox().getMaxY() <= boundingBox.getMaxY()) break;
                boundingBox = ((DetectionResult)object6).getBoundingBox();
                ++n3;
            } while (true);
            ((DetectionResult)object6).setBoundingBox(boundingBox);
            object5 = object3;
            object4 = object2;
        }
        int n4 = ((DetectionResult)object6).getBarcodeColumnCount() + 1;
        ((DetectionResult)object6).setDetectionResultColumn(0, (DetectionResultColumn)object5);
        ((DetectionResult)object6).setDetectionResultColumn(n4, (DetectionResultColumn)object4);
        boolean bl = object5 != null;
        n3 = n2;
        n2 = 1;
        int n5 = n;
        while (n2 <= n4) {
            int n6;
            int n7;
            block13 : {
                int n8;
                block12 : {
                    n8 = bl ? n2 : n4 - n2;
                    if (((DetectionResult)object6).getDetectionResultColumn(n8) == null) break block12;
                    n6 = n5;
                    n7 = n3;
                    break block13;
                }
                if (n8 != 0 && n8 != n4) {
                    object = new DetectionResultColumn(boundingBox);
                } else {
                    boolean bl2 = n8 == 0;
                    object = new DetectionResultRowIndicatorColumn(boundingBox, bl2);
                }
                ((DetectionResult)object6).setDetectionResultColumn(n8, (DetectionResultColumn)object);
                int n9 = boundingBox.getMinY();
                n = -1;
                do {
                    block15 : {
                        block14 : {
                            n6 = n5;
                            n7 = n3;
                            if (n9 > boundingBox.getMaxY()) break;
                            n7 = PDF417ScanningDecoder.getStartColumn((DetectionResult)object6, n8, n9, bl);
                            if (n7 >= 0 && n7 <= boundingBox.getMaxX()) break block14;
                            if (n == -1) break block15;
                            n7 = n;
                        }
                        if ((object2 = PDF417ScanningDecoder.detectCodeword(bitMatrix, boundingBox.getMinX(), boundingBox.getMaxX(), bl, n7, n9, n5, n3)) != null) {
                            ((DetectionResultColumn)object).setCodeword(n9, (Codeword)object2);
                            n5 = Math.min(n5, ((Codeword)object2).getWidth());
                            n3 = Math.max(n3, ((Codeword)object2).getWidth());
                            n = n7;
                        }
                    }
                    ++n9;
                } while (true);
            }
            ++n2;
            n5 = n6;
            n3 = n7;
        }
        return PDF417ScanningDecoder.createDecoderResult((DetectionResult)object6);
    }

    private static DecoderResult decodeCodewords(int[] object, int n, int[] arrn) throws FormatException, ChecksumException {
        if (((int[])object).length == 0) throw FormatException.getFormatInstance();
        int n2 = 1 << n + 1;
        int n3 = PDF417ScanningDecoder.correctErrors((int[])object, arrn, n2);
        PDF417ScanningDecoder.verifyCodewordCount((int[])object, n2);
        object = DecodedBitStreamParser.decode((int[])object, String.valueOf(n));
        ((DecoderResult)object).setErrorsCorrected(n3);
        ((DecoderResult)object).setErasures(arrn.length);
        return object;
    }

    private static Codeword detectCodeword(BitMatrix arrn, int n, int n2, boolean bl, int n3, int n4, int n5, int n6) {
        n3 = PDF417ScanningDecoder.adjustCodewordStartColumn((BitMatrix)arrn, n, n2, bl, n3, n4);
        if ((arrn = PDF417ScanningDecoder.getModuleBitCount((BitMatrix)arrn, n, n2, bl, n3, n4)) == null) {
            return null;
        }
        n4 = PDF417Common.getBitCountSum(arrn);
        if (bl) {
            n = n3 + n4;
        } else {
            for (n = 0; n < arrn.length / 2; ++n) {
                n2 = arrn[n];
                arrn[n] = arrn[arrn.length - 1 - n];
                arrn[arrn.length - 1 - n] = n2;
            }
            n2 = n3 - n4;
            n = n3;
            n3 = n2;
        }
        if (!PDF417ScanningDecoder.checkCodewordSkew(n4, n5, n6)) {
            return null;
        }
        n4 = PDF417CodewordDecoder.getDecodedValue(arrn);
        n2 = PDF417Common.getCodeword(n4);
        if (n2 != -1) return new Codeword(n3, n, PDF417ScanningDecoder.getCodewordBucketNumber(n4), n2);
        return null;
    }

    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn object, DetectionResultRowIndicatorColumn object2) {
        Object var2_2 = null;
        if (object != null && (object = ((DetectionResultRowIndicatorColumn)object).getBarcodeMetadata()) != null) {
            if (object2 == null) return object;
            if ((object2 = ((DetectionResultRowIndicatorColumn)object2).getBarcodeMetadata()) == null) {
                return object;
            }
            if (((BarcodeMetadata)object).getColumnCount() == ((BarcodeMetadata)object2).getColumnCount()) return object;
            if (((BarcodeMetadata)object).getErrorCorrectionLevel() == ((BarcodeMetadata)object2).getErrorCorrectionLevel()) return object;
            if (((BarcodeMetadata)object).getRowCount() == ((BarcodeMetadata)object2).getRowCount()) return object;
            return null;
        }
        if (object2 != null) return ((DetectionResultRowIndicatorColumn)object2).getBarcodeMetadata();
        return var2_2;
    }

    private static int[] getBitCountForCodeword(int n) {
        int[] arrn = new int[8];
        int n2 = 0;
        int n3 = 7;
        do {
            int n4 = n & 1;
            int n5 = n2;
            int n6 = n3;
            if (n4 != n2) {
                n6 = n3 - 1;
                if (n6 < 0) {
                    return arrn;
                }
                n5 = n4;
            }
            arrn[n6] = arrn[n6] + 1;
            n >>= 1;
            n2 = n5;
            n3 = n6;
        } while (true);
    }

    private static int getCodewordBucketNumber(int n) {
        return PDF417ScanningDecoder.getCodewordBucketNumber(PDF417ScanningDecoder.getBitCountForCodeword(n));
    }

    private static int getCodewordBucketNumber(int[] arrn) {
        return (arrn[0] - arrn[2] + arrn[4] - arrn[6] + 9) % 9;
    }

    private static int getMax(int[] arrn) {
        int n = arrn.length;
        int n2 = -1;
        int n3 = 0;
        while (n3 < n) {
            n2 = Math.max(n2, arrn[n3]);
            ++n3;
        }
        return n2;
    }

    private static int[] getModuleBitCount(BitMatrix bitMatrix, int n, int n2, boolean bl, int n3, int n4) {
        int[] arrn = new int[8];
        int n5 = bl ? 1 : -1;
        int n6 = 0;
        boolean bl2 = bl;
        while ((bl && n3 < n2 || !bl && n3 >= n) && n6 < 8) {
            if (bitMatrix.get(n3, n4) == bl2) {
                arrn[n6] = arrn[n6] + 1;
                n3 += n5;
                continue;
            }
            ++n6;
            bl2 ^= true;
        }
        if (n6 == 8) return arrn;
        if (!bl || n3 != n2) {
            if (bl) return null;
            if (n3 != n) return null;
        }
        if (n6 != 7) return null;
        return arrn;
    }

    private static int getNumberOfECCodeWords(int n) {
        return 2 << n;
    }

    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean bl, int n, int n2) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, bl);
        int n3 = 0;
        while (n3 < 2) {
            int n4 = n3 == 0 ? 1 : -1;
            int n5 = (int)resultPoint.getX();
            for (int i = (int)resultPoint.getY(); i <= boundingBox.getMaxY() && i >= boundingBox.getMinY(); i += n4) {
                Codeword codeword = PDF417ScanningDecoder.detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), bl, n5, i, n, n2);
                if (codeword == null) continue;
                detectionResultRowIndicatorColumn.setCodeword(i, codeword);
                n5 = bl ? codeword.getStartX() : codeword.getEndX();
            }
            ++n3;
        }
        return detectionResultRowIndicatorColumn;
    }

    private static int getStartColumn(DetectionResult object, int n, int n2, boolean bl) {
        int n3 = bl ? 1 : -1;
        Codeword codeword = null;
        int n4 = n - n3;
        if (PDF417ScanningDecoder.isValidBarcodeColumn((DetectionResult)object, n4)) {
            codeword = ((DetectionResult)object).getDetectionResultColumn(n4).getCodeword(n2);
        }
        if (codeword != null) {
            if (!bl) return codeword.getStartX();
            return codeword.getEndX();
        }
        codeword = ((DetectionResult)object).getDetectionResultColumn(n).getCodewordNearby(n2);
        if (codeword != null) {
            if (!bl) return codeword.getEndX();
            return codeword.getStartX();
        }
        if (PDF417ScanningDecoder.isValidBarcodeColumn((DetectionResult)object, n4)) {
            codeword = ((DetectionResult)object).getDetectionResultColumn(n4).getCodewordNearby(n2);
        }
        if (codeword != null) {
            if (!bl) return codeword.getStartX();
            return codeword.getEndX();
        }
        n4 = 0;
        n2 = n;
        n = n4;
        block0 : do {
            Codeword[] arrcodeword;
            int n5;
            if (PDF417ScanningDecoder.isValidBarcodeColumn((DetectionResult)object, n4 = n2 - n3)) {
                arrcodeword = ((DetectionResult)object).getDetectionResultColumn(n4).getCodewords();
                n5 = arrcodeword.length;
            } else {
                object = ((DetectionResult)object).getBoundingBox();
                if (!bl) return ((BoundingBox)object).getMaxX();
                return ((BoundingBox)object).getMinX();
            }
            for (n2 = 0; n2 < n5; ++n2) {
                codeword = arrcodeword[n2];
                if (codeword == null) continue;
                if (!bl) break block0;
                n2 = codeword.getEndX();
                return n2 + n3 * n * (codeword.getEndX() - codeword.getStartX());
            }
            ++n;
            n2 = n4;
        } while (true);
        n2 = codeword.getStartX();
        return n2 + n3 * n * (codeword.getEndX() - codeword.getStartX());
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int n) {
        boolean bl = true;
        if (n < 0) return false;
        if (n > detectionResult.getBarcodeColumnCount() + 1) return false;
        return bl;
    }

    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) throws NotFoundException, FormatException {
        if (detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = PDF417ScanningDecoder.getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2);
        if (barcodeMetadata != null) return new DetectionResult(barcodeMetadata, BoundingBox.merge(PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn), PDF417ScanningDecoder.adjustBoundingBox(detectionResultRowIndicatorColumn2)));
        return null;
    }

    public static String toString(BarcodeValue[][] object) {
        Formatter formatter = new Formatter();
        int n = 0;
        do {
            if (n >= ((BarcodeValue[][])object).length) {
                object = formatter.toString();
                formatter.close();
                return object;
            }
            formatter.format("Row %2d: ", n);
            for (int i = 0; i < object[n].length; ++i) {
                BarcodeValue barcodeValue = object[n][i];
                if (barcodeValue.getValue().length == 0) {
                    formatter.format("        ", (Object[])null);
                    continue;
                }
                formatter.format("%4d(%2d)", barcodeValue.getValue()[0], barcodeValue.getConfidence(barcodeValue.getValue()[0]));
            }
            formatter.format("%n", new Object[0]);
            ++n;
        } while (true);
    }

    private static void verifyCodewordCount(int[] arrn, int n) throws FormatException {
        if (arrn.length < 4) throw FormatException.getFormatInstance();
        int n2 = arrn[0];
        if (n2 > arrn.length) throw FormatException.getFormatInstance();
        if (n2 != 0) return;
        if (n >= arrn.length) throw FormatException.getFormatInstance();
        arrn[0] = arrn.length - n;
    }
}

