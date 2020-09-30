/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.images.zab;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.base.zae;
import com.google.android.gms.internal.base.zak;
import java.lang.ref.WeakReference;

public final class zad
extends zab {
    private WeakReference<ImageView> zac;

    public zad(ImageView imageView, int n) {
        super(Uri.EMPTY, n);
        Asserts.checkNotNull((Object)imageView);
        this.zac = new WeakReference<ImageView>(imageView);
    }

    public zad(ImageView imageView, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull((Object)imageView);
        this.zac = new WeakReference<ImageView>(imageView);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zad)) {
            return false;
        }
        zad zad2 = (zad)object;
        object = (ImageView)this.zac.get();
        zad2 = (ImageView)zad2.zac.get();
        if (zad2 == null) return false;
        if (object == null) return false;
        if (!Objects.equal(zad2, object)) return false;
        return true;
    }

    public final int hashCode() {
        return 0;
    }

    @Override
    protected final void zaa(Drawable object, boolean bl, boolean bl2, boolean bl3) {
        Object object2;
        ImageView imageView = (ImageView)this.zac.get();
        if (imageView == null) return;
        int n = 0;
        boolean bl4 = !bl2 && !bl3;
        if (bl4 && imageView instanceof zak) {
            object2 = (zak)imageView;
            int n2 = zak.zaa();
            if (this.zab != 0) {
                if (n2 == this.zab) return;
            }
        }
        bl = this.zaa(bl, bl2);
        object2 = object;
        if (bl) {
            Drawable drawable2 = imageView.getDrawable();
            if (drawable2 != null) {
                object2 = drawable2;
                if (drawable2 instanceof zae) {
                    object2 = ((zae)drawable2).zaa();
                }
            } else {
                object2 = null;
            }
            object2 = new zae((Drawable)object2, (Drawable)object);
        }
        imageView.setImageDrawable((Drawable)object2);
        if (imageView instanceof zak) {
            object = (zak)imageView;
            object = bl3 ? this.zaa.zaa : Uri.EMPTY;
            zak.zaa((Uri)object);
            if (bl4) {
                n = this.zab;
            }
            zak.zaa(n);
        }
        if (object2 == null) return;
        if (!bl) return;
        ((zae)((Object)object2)).zaa(250);
    }
}

