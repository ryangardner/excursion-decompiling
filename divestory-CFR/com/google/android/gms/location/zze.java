/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.location;

import com.google.android.gms.location.ActivityTransition;
import java.util.Comparator;

final class zze
implements Comparator<ActivityTransition> {
    zze() {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        int n;
        object = (ActivityTransition)object;
        object2 = (ActivityTransition)object2;
        int n2 = ((ActivityTransition)object).getActivityType();
        if (n2 != (n = ((ActivityTransition)object2).getActivityType())) {
            if (n2 >= n) return 1;
            return -1;
        }
        n2 = ((ActivityTransition)object).getTransitionType();
        if (n2 == (n = ((ActivityTransition)object2).getTransitionType())) {
            return 0;
        }
        if (n2 >= n) return 1;
        return -1;
    }
}

