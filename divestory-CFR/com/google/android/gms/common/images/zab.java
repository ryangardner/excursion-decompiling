/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.zaa;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zaj;

public abstract class zab {
    final zaa zaa;
    protected int zab = 0;
    private int zac = 0;
    private boolean zad = false;
    private boolean zae = true;
    private boolean zaf = false;
    private boolean zag = true;

    public zab(Uri uri, int n) {
        this.zaa = new zaa(uri);
        this.zab = n;
    }

    final void zaa(Context context, Bitmap bitmap, boolean bl) {
        Asserts.checkNotNull((Object)bitmap);
        this.zaa((Drawable)new BitmapDrawable(context.getResources(), bitmap), bl, false, true);
    }

    final void zaa(Context context, zaj zaj2) {
        if (!this.zag) return;
        this.zaa(null, false, true, false);
    }

    final void zaa(Context object, zaj zaj2, boolean bl) {
        int n = this.zab;
        object = n != 0 ? object.getResources().getDrawable(n) : null;
        this.zaa((Drawable)object, bl, false, false);
    }

    protected abstract void zaa(Drawable var1, boolean var2, boolean var3, boolean var4);

    protected final boolean zaa(boolean bl, boolean bl2) {
        if (!this.zae) return false;
        if (bl2) return false;
        if (bl) return false;
        return true;
    }
}

