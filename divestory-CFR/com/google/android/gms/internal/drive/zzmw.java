/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzlq;
import java.util.List;

public final class zzmw
extends RuntimeException {
    private final List<String> zzvq = null;

    public zzmw(zzlq zzlq2) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }
}

