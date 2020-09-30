/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 */
package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.images.zab;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

public final class zac
extends zab {
    private WeakReference<ImageManager.OnImageLoadedListener> zac;

    public zac(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(onImageLoadedListener);
        this.zac = new WeakReference<ImageManager.OnImageLoadedListener>(onImageLoadedListener);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zac)) {
            return false;
        }
        zac zac2 = (zac)object;
        object = (ImageManager.OnImageLoadedListener)this.zac.get();
        ImageManager.OnImageLoadedListener onImageLoadedListener = (ImageManager.OnImageLoadedListener)zac2.zac.get();
        if (onImageLoadedListener == null) return false;
        if (object == null) return false;
        if (!Objects.equal(onImageLoadedListener, object)) return false;
        if (!Objects.equal(zac2.zaa, this.zaa)) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zaa);
    }

    @Override
    protected final void zaa(Drawable drawable2, boolean bl, boolean bl2, boolean bl3) {
        if (bl2) return;
        ImageManager.OnImageLoadedListener onImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zac.get();
        if (onImageLoadedListener == null) return;
        onImageLoadedListener.onImageLoaded(this.zaa.zaa, drawable2, bl3);
    }
}

