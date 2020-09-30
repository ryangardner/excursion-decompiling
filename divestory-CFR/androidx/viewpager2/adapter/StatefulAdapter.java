/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package androidx.viewpager2.adapter;

import android.os.Parcelable;

public interface StatefulAdapter {
    public void restoreState(Parcelable var1);

    public Parcelable saveState();
}

