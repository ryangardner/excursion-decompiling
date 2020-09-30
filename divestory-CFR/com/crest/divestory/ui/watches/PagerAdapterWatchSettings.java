/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.ui.watches.FragmentWatchSettingsBasic;
import com.crest.divestory.ui.watches.FragmentWatchSettingsFreeDive;
import com.crest.divestory.ui.watches.FragmentWatchSettingsScuba;

public class PagerAdapterWatchSettings
extends FragmentStatePagerAdapter {
    Context context;
    private int pages_count = 3;

    public PagerAdapterWatchSettings(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public void destroyItem(View view, int n, Object object) {
        ((ViewPager)view).removeView((View)object);
    }

    @Override
    public int getCount() {
        return this.pages_count;
    }

    @Override
    public Fragment getItem(int n) {
        if (n == 0) {
            return FragmentWatchSettingsBasic.newInstance(n);
        }
        if (n == 1) {
            return FragmentWatchSettingsScuba.newInstance(n);
        }
        if (n == 2) return FragmentWatchSettingsFreeDive.newInstance(n);
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object != null) return super.getItemPosition(object);
        return -2;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        return super.instantiateItem(viewGroup, n);
    }

    public void set_para(Context context) {
        this.context = context;
    }
}

