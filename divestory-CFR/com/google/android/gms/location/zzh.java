/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.location;

import com.google.android.gms.location.DetectedActivity;
import java.util.Comparator;

final class zzh
implements Comparator<DetectedActivity> {
    zzh() {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        object = (DetectedActivity)object;
        int n = Integer.valueOf(((DetectedActivity)(object2 = (DetectedActivity)object2)).getConfidence()).compareTo(((DetectedActivity)object).getConfidence());
        if (n != 0) return n;
        return Integer.valueOf(((DetectedActivity)object).getType()).compareTo(((DetectedActivity)object2).getType());
    }
}

