/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.aztec.AztecReader;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.maxicode.MaxiCodeReader;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatReader
implements Reader {
    private Map<DecodeHintType, ?> hints;
    private Reader[] readers;

    private Result decodeInternal(BinaryBitmap binaryBitmap) throws NotFoundException {
        Reader[] arrreader = this.readers;
        if (arrreader == null) throw NotFoundException.getNotFoundInstance();
        int n = arrreader.length;
        int n2 = 0;
        while (n2 < n) {
            Reader reader = arrreader[n2];
            try {
                return reader.decode(binaryBitmap, this.hints);
            }
            catch (ReaderException readerException) {
                ++n2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException {
        this.setHints(null);
        return this.decodeInternal(binaryBitmap);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        this.setHints(map);
        return this.decodeInternal(binaryBitmap);
    }

    public Result decodeWithState(BinaryBitmap binaryBitmap) throws NotFoundException {
        if (this.readers != null) return this.decodeInternal(binaryBitmap);
        this.setHints(null);
        return this.decodeInternal(binaryBitmap);
    }

    @Override
    public void reset() {
        Reader[] arrreader = this.readers;
        if (arrreader == null) return;
        int n = arrreader.length;
        int n2 = 0;
        while (n2 < n) {
            arrreader[n2].reset();
            ++n2;
        }
    }

    public void setHints(Map<DecodeHintType, ?> map) {
        this.hints = map;
        boolean bl = true;
        boolean bl2 = map != null && map.containsKey((Object)DecodeHintType.TRY_HARDER);
        Collection collection = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        ArrayList<Reader> arrayList = new ArrayList<Reader>();
        if (collection != null) {
            boolean bl3 = bl;
            if (!collection.contains((Object)BarcodeFormat.UPC_A)) {
                bl3 = bl;
                if (!collection.contains((Object)BarcodeFormat.UPC_E)) {
                    bl3 = bl;
                    if (!collection.contains((Object)BarcodeFormat.EAN_13)) {
                        bl3 = bl;
                        if (!collection.contains((Object)BarcodeFormat.EAN_8)) {
                            bl3 = bl;
                            if (!collection.contains((Object)BarcodeFormat.CODABAR)) {
                                bl3 = bl;
                                if (!collection.contains((Object)BarcodeFormat.CODE_39)) {
                                    bl3 = bl;
                                    if (!collection.contains((Object)BarcodeFormat.CODE_93)) {
                                        bl3 = bl;
                                        if (!collection.contains((Object)BarcodeFormat.CODE_128)) {
                                            bl3 = bl;
                                            if (!collection.contains((Object)BarcodeFormat.ITF)) {
                                                bl3 = bl;
                                                if (!collection.contains((Object)BarcodeFormat.RSS_14)) {
                                                    bl3 = collection.contains((Object)BarcodeFormat.RSS_EXPANDED) ? bl : false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (bl3 && !bl2) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            if (collection.contains((Object)BarcodeFormat.QR_CODE)) {
                arrayList.add(new QRCodeReader());
            }
            if (collection.contains((Object)BarcodeFormat.DATA_MATRIX)) {
                arrayList.add(new DataMatrixReader());
            }
            if (collection.contains((Object)BarcodeFormat.AZTEC)) {
                arrayList.add(new AztecReader());
            }
            if (collection.contains((Object)BarcodeFormat.PDF_417)) {
                arrayList.add(new PDF417Reader());
            }
            if (collection.contains((Object)BarcodeFormat.MAXICODE)) {
                arrayList.add(new MaxiCodeReader());
            }
            if (bl3 && bl2) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        if (arrayList.isEmpty()) {
            if (!bl2) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            arrayList.add(new QRCodeReader());
            arrayList.add(new DataMatrixReader());
            arrayList.add(new AztecReader());
            arrayList.add(new PDF417Reader());
            arrayList.add(new MaxiCodeReader());
            if (bl2) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        this.readers = arrayList.toArray(new Reader[arrayList.size()]);
    }
}

