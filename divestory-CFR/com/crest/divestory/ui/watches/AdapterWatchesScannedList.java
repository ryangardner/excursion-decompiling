/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.ActivityWatchSync;
import com.syntak.library.BleOp;
import java.util.ArrayList;

public class AdapterWatchesScannedList
extends BaseAdapter {
    private final DataStruct.BleDevices bleDevices;
    private Context context;
    private LayoutInflater inflater;

    public AdapterWatchesScannedList(Context context, DataStruct.BleDevices bleDevices) {
        this.context = context;
        this.bleDevices = bleDevices;
        this.inflater = LayoutInflater.from((Context)context);
    }

    public int getCount() {
        return this.bleDevices.length();
    }

    public Object getItem(int n) {
        return this.bleDevices.list.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup object) {
        if (view == null) {
            object = new ViewHolder();
            view = this.inflater.inflate(2131427455, null);
            object.row_scanned_watch = (RelativeLayout)view.findViewById(2131231255);
            object.row_scanned_watch.setOnClickListener(new View.OnClickListener(){

                public void onClick(View object) {
                    int n = ((ViewHolder)object.getTag()).position;
                    object = AdapterWatchesScannedList.this;
                    ((AdapterWatchesScannedList)((Object)object)).go_syncing(((AdapterWatchesScannedList)((Object)object)).context, n);
                }
            });
            object.name = (TextView)view.findViewById(2131231185);
            object.rssi = (TextView)view.findViewById(2131231257);
            view.setTag(object);
        } else {
            object = (ViewHolder)view.getTag();
        }
        object.position = n;
        DataStruct.BleDevice bleDevice = this.bleDevices.list.get(n);
        object.name.setText((CharSequence)bleDevice.name);
        object.rssi.setText((CharSequence)String.valueOf(bleDevice.rssi));
        return view;
    }

    void go_syncing(Context context, int n) {
        WatchOp.bleOp.stop_scanning();
        Intent intent = new Intent();
        intent.setClass(context, ActivityWatchSync.class);
        intent.putExtra("index", n);
        context.startActivity(intent);
    }

    static class ViewHolder {
        public String address;
        public TextView name;
        int position;
        public RelativeLayout row_scanned_watch;
        public TextView rssi;

        ViewHolder() {
        }
    }

}

