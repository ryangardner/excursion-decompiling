/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.UPCAReader;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatUPCEANReader
extends OneDReader {
    private final UPCEANReader[] readers;

    public MultiFormatUPCEANReader(Map<DecodeHintType, ?> object) {
        object = object == null ? null : (Collection)object.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        ArrayList<UPCEANReader> arrayList = new ArrayList<UPCEANReader>();
        if (object != null) {
            if (object.contains((Object)BarcodeFormat.EAN_13)) {
                arrayList.add(new EAN13Reader());
            } else if (object.contains((Object)BarcodeFormat.UPC_A)) {
                arrayList.add(new UPCAReader());
            }
            if (object.contains((Object)BarcodeFormat.EAN_8)) {
                arrayList.add(new EAN8Reader());
            }
            if (object.contains((Object)BarcodeFormat.UPC_E)) {
                arrayList.add(new UPCEReader());
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new EAN13Reader());
            arrayList.add(new EAN8Reader());
            arrayList.add(new UPCEReader());
        }
        this.readers = arrayList.toArray(new UPCEANReader[arrayList.size()]);
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> map) throws NotFoundException {
        int[] arrn = UPCEANReader.findStartGuardPattern((BitArray)object);
        UPCEANReader[] arruPCEANReader = this.readers;
        int n2 = arruPCEANReader.length;
        int n3 = 0;
        int n4 = 0;
        while (n4 < n2) {
            Object object2;
            block6 : {
                block5 : {
                    object2 = arruPCEANReader[n4];
                    try {
                        object2 = ((UPCEANReader)object2).decodeRow(n, (BitArray)object, arrn, map);
                        n = ((Result)object2).getBarcodeFormat() == BarcodeFormat.EAN_13 && ((Result)object2).getText().charAt(0) == '0' ? 1 : 0;
                        object = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
                    }
                    catch (ReaderException readerException) {
                        ++n4;
                        continue;
                    }
                    if (object == null) break block5;
                    n4 = n3;
                    if (!object.contains((Object)BarcodeFormat.UPC_A)) break block6;
                }
                n4 = 1;
            }
            if (n == 0) return object2;
            if (n4 == 0) return object2;
            object = new Result(((Result)object2).getText().substring(1), ((Result)object2).getRawBytes(), ((Result)object2).getResultPoints(), BarcodeFormat.UPC_A);
            ((Result)object).putAllMetadata(((Result)object2).getResultMetadata());
            return object;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        UPCEANReader[] arruPCEANReader = this.readers;
        int n = arruPCEANReader.length;
        int n2 = 0;
        while (n2 < n) {
            arruPCEANReader[n2].reset();
            ++n2;
        }
    }
}

