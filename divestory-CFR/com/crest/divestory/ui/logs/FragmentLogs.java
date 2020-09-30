/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ListView
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.ActivityWatchesScan;
import com.crest.divestory.ui.watches.AdapterWatchesListRecyclerView;
import com.syntak.library.UiOp;
import java.util.ArrayList;
import java.util.List;

public class FragmentLogs
extends Fragment {
    WatchOp.ACTION future_action = WatchOp.ACTION.LIST_DIVE_LOGS;
    private ListView lv;
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

    public static FragmentLogs newInstance() {
        return new FragmentLogs();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
        bundle = object.inflate(2131427395, viewGroup, false);
        this.warning = (TextView)bundle.findViewById(2131231445);
        if (WatchOp.myWatches != null && WatchOp.myWatches.length() > 0) {
            this.warning.setText((CharSequence)"");
            if (!(bundle instanceof RecyclerView)) return bundle;
            viewGroup = bundle.getContext();
            object = (RecyclerView)bundle;
            ((RecyclerView)object).setLayoutManager(new LinearLayoutManager((Context)viewGroup));
            ((RecyclerView)object).setAdapter(new AdapterWatchesListRecyclerView((Context)this.getActivity(), WatchOp.myWatches.list, this.future_action));
            return bundle;
        }
        this.warning.setText(2131689746);
        return bundle;
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
}

