/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.media;

import android.os.Bundle;

public class MediaBrowserCompatUtils {
    private MediaBrowserCompatUtils() {
    }

    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null) {
            if (bundle2.getInt("android.media.browse.extra.PAGE", -1) != -1) return false;
            if (bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) != -1) return false;
            return bl3;
        }
        if (bundle2 == null) {
            if (bundle.getInt("android.media.browse.extra.PAGE", -1) != -1) return false;
            if (bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) != -1) return false;
            return bl;
        }
        if (bundle.getInt("android.media.browse.extra.PAGE", -1) != bundle2.getInt("android.media.browse.extra.PAGE", -1)) return false;
        if (bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) != bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1)) return false;
        return bl2;
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        int n = bundle == null ? -1 : bundle.getInt("android.media.browse.extra.PAGE", -1);
        int n2 = bundle2 == null ? -1 : bundle2.getInt("android.media.browse.extra.PAGE", -1);
        int n3 = bundle == null ? -1 : bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        int n4 = bundle2 == null ? -1 : bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        int n5 = Integer.MAX_VALUE;
        boolean bl = true;
        if (n != -1 && n3 != -1) {
            n *= n3;
            n3 = n3 + n - 1;
        } else {
            n3 = Integer.MAX_VALUE;
            n = 0;
        }
        if (n2 != -1 && n4 != -1) {
            n2 *= n4;
            n4 = n4 + n2 - 1;
        } else {
            n2 = 0;
            n4 = n5;
        }
        if (n3 < n2) return false;
        if (n4 < n) return false;
        return bl;
    }
}

