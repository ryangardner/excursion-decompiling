/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crest.divestory.ui;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.AppBase;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.crest.divestory.ui.settings.FragmentAppSettings;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;

public class PagerAdapterMain
extends FragmentStatePagerAdapter {
    final int PAGES_COUNT;

    public PagerAdapterMain(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.PAGES_COUNT = 3;
    }

    @Override
    public void destroyItem(View view, int n, Object object) {
        ((ViewPager)view).removeView((View)object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int n) {
        Fragment fragment;
        if (n == 0) {
            AppBase.fragmentSyncedWatchesList = fragment = new FragmentSyncedWatchesList();
            return fragment;
        }
        if (n == 1) {
            fragment = new FragmentDiveLogsList();
            AppBase.fragmentDiveLogsList = (FragmentDiveLogsList)fragment;
            return fragment;
        }
        if (n != 2) {
            return null;
        }
        fragment = new FragmentAppSettings();
        AppBase.fragmentAppSettings = (FragmentAppSettings)fragment;
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        return super.instantiateItem(viewGroup, n);
    }
}

