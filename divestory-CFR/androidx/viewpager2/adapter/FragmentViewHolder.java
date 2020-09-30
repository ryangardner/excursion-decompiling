/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 */
package androidx.viewpager2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public final class FragmentViewHolder
extends RecyclerView.ViewHolder {
    private FragmentViewHolder(FrameLayout frameLayout) {
        super((View)frameLayout);
    }

    static FragmentViewHolder create(ViewGroup viewGroup) {
        viewGroup = new FrameLayout(viewGroup.getContext());
        viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        viewGroup.setId(ViewCompat.generateViewId());
        viewGroup.setSaveEnabled(false);
        return new FragmentViewHolder((FrameLayout)viewGroup);
    }

    FrameLayout getContainer() {
        return (FrameLayout)this.itemView;
    }
}

