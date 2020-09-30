/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {
    public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";
    public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";

    private DataBufferUtils() {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
        ArrayList arrayList = new ArrayList(dataBuffer.getCount());
        try {
            Iterator<E> iterator2 = dataBuffer.iterator();
            while (iterator2.hasNext()) {
                arrayList.add(((Freezable)iterator2.next()).freeze());
            }
            return arrayList;
        }
        finally {
            dataBuffer.close();
        }
    }

    public static boolean hasData(DataBuffer<?> dataBuffer) {
        if (dataBuffer == null) return false;
        if (dataBuffer.getCount() <= 0) return false;
        return true;
    }

    public static boolean hasNextPage(DataBuffer<?> bundle) {
        if ((bundle = bundle.getMetadata()) == null) return false;
        if (bundle.getString(KEY_NEXT_PAGE_TOKEN) == null) return false;
        return true;
    }

    public static boolean hasPrevPage(DataBuffer<?> bundle) {
        if ((bundle = bundle.getMetadata()) == null) return false;
        if (bundle.getString(KEY_PREV_PAGE_TOKEN) == null) return false;
        return true;
    }
}

