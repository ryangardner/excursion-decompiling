/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.Code93Reader;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.MultiFormatUPCEANReader;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.RSS14Reader;
import com.google.zxing.oned.rss.expanded.RSSExpandedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatOneDReader
extends OneDReader {
    private final OneDReader[] readers;

    public MultiFormatOneDReader(Map<DecodeHintType, ?> map) {
        Collection collection = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        boolean bl = map != null && map.get((Object)DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) != null;
        ArrayList<OneDReader> arrayList = new ArrayList<OneDReader>();
        if (collection != null) {
            if (collection.contains((Object)BarcodeFormat.EAN_13) || collection.contains((Object)BarcodeFormat.UPC_A) || collection.contains((Object)BarcodeFormat.EAN_8) || collection.contains((Object)BarcodeFormat.UPC_E)) {
                arrayList.add(new MultiFormatUPCEANReader(map));
            }
            if (collection.contains((Object)BarcodeFormat.CODE_39)) {
                arrayList.add(new Code39Reader(bl));
            }
            if (collection.contains((Object)BarcodeFormat.CODE_93)) {
                arrayList.add(new Code93Reader());
            }
            if (collection.contains((Object)BarcodeFormat.CODE_128)) {
                arrayList.add(new Code128Reader());
            }
            if (collection.contains((Object)BarcodeFormat.ITF)) {
                arrayList.add(new ITFReader());
            }
            if (collection.contains((Object)BarcodeFormat.CODABAR)) {
                arrayList.add(new CodaBarReader());
            }
            if (collection.contains((Object)BarcodeFormat.RSS_14)) {
                arrayList.add(new RSS14Reader());
            }
            if (collection.contains((Object)BarcodeFormat.RSS_EXPANDED)) {
                arrayList.add(new RSSExpandedReader());
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new MultiFormatUPCEANReader(map));
            arrayList.add(new Code39Reader());
            arrayList.add(new CodaBarReader());
            arrayList.add(new Code93Reader());
            arrayList.add(new Code128Reader());
            arrayList.add(new ITFReader());
            arrayList.add(new RSS14Reader());
            arrayList.add(new RSSExpandedReader());
        }
        this.readers = arrayList.toArray(new OneDReader[arrayList.size()]);
    }

    @Override
    public Result decodeRow(int n, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        OneDReader[] arroneDReader = this.readers;
        int n2 = arroneDReader.length;
        int n3 = 0;
        while (n3 < n2) {
            OneDReader oneDReader = arroneDReader[n3];
            try {
                return oneDReader.decodeRow(n, bitArray, map);
            }
            catch (ReaderException readerException) {
                ++n3;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        OneDReader[] arroneDReader = this.readers;
        int n = arroneDReader.length;
        int n2 = 0;
        while (n2 < n) {
            arroneDReader[n2].reset();
            ++n2;
        }
    }
}

