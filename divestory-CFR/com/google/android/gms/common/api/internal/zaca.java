/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.util.concurrent.NumberedThreadFactory;
import com.google.android.gms.internal.base.zal;
import com.google.android.gms.internal.base.zaq;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public final class zaca {
    private static final ExecutorService zaa = zal.zaa().zaa(new NumberedThreadFactory("GAC_Transform"), zaq.zaa);

    public static ExecutorService zaa() {
        return zaa;
    }
}

