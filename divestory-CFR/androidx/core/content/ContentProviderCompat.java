/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.Context
 */
package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat {
    private ContentProviderCompat() {
    }

    public static Context requireContext(ContentProvider contentProvider) {
        if ((contentProvider = contentProvider.getContext()) == null) throw new IllegalStateException("Cannot find context from the provider.");
        return contentProvider;
    }
}

