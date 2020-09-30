/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crest.divestory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class FragmentPage
extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public boolean needRefresh = false;
    public int page = -1;

    public static FragmentPage newInstance(String string2, String string3) {
        FragmentPage fragmentPage = new FragmentPage();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, string2);
        bundle.putString(ARG_PARAM2, string3);
        fragmentPage.setArguments(bundle);
        return fragmentPage;
    }

    public boolean askNeedRefresh() {
        return this.needRefresh;
    }

    public int getPage() {
        return this.page;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() == null) return;
        this.mParam1 = this.getArguments().getString(ARG_PARAM1);
        this.mParam2 = this.getArguments().getString(ARG_PARAM2);
    }

    public void setNeedRefresh(boolean bl) {
        this.needRefresh = bl;
    }

    public void setPage(int n) {
        this.page = n;
    }
}

