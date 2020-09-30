/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.ActivityWatchesScan;
import com.crest.divestory.ui.watches.AdapterWatchesListRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syntak.library.UiOp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentSyncedWatchesList
extends Fragment {
    private static final String ARG_FUTURE_ACTION = "future_action";
    public static AdapterWatchesListRecyclerView adapter;
    WatchOp.ACTION future_action;
    private RecyclerView lv;
    private int mColumnCount = 1;
    RelativeLayout reminder_screen;
    MenuItem scan;
    FloatingActionButton scan_watches;
    private TextView warning;

    private void go_scan(View view) {
        if (WatchOp.isPermissionGranted) {
            view = new Intent();
            view.setClass(this.getContext(), ActivityWatchesScan.class);
            this.startActivity((Intent)view);
            return;
        }
        UiOp.toast_message(this.getContext(), "Permissions not granted", false);
    }

    public void hide_reminder_screen(View view) {
        this.reminder_screen.setVisibility(8);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() == null) return;
        this.future_action = (WatchOp.ACTION)((Object)this.getArguments().getSerializable(ARG_FUTURE_ACTION));
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle object) {
        layoutInflater = layoutInflater.inflate(2131427398, viewGroup, false);
        this.reminder_screen = (RelativeLayout)layoutInflater.findViewById(2131231242);
        this.warning = (TextView)layoutInflater.findViewById(2131231445);
        this.lv = (RecyclerView)layoutInflater.findViewById(2131231133);
        if (WatchOp.myWatches != null && WatchOp.myWatches.length() > 0) {
            this.warning.setText((CharSequence)"");
            if (!(this.lv instanceof RecyclerView)) return layoutInflater;
            object = layoutInflater.getContext();
            viewGroup = this.lv;
            viewGroup.setLayoutManager(new LinearLayoutManager((Context)object));
            object = new AdapterWatchesListRecyclerView((Context)this.getActivity(), WatchOp.myWatches.list, this.future_action);
            adapter = object;
            viewGroup.setAdapter((RecyclerView.Adapter)object);
            return layoutInflater;
        }
        this.warning.setText(2131689746);
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void show_reminder_screen() {
        this.reminder_screen.setVisibility(0);
    }
}

