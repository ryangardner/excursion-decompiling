/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.FragmentWatchesScan;
import java.util.HashMap;
import java.util.List;

public class AdapterWatchesScannedListRecyclerView
extends RecyclerView.Adapter<ViewHolder> {
    private final List<DataStruct.BleDevice> bleDevices;
    Context context;
    private final FragmentWatchesScan.OnWatchAddFragmentInteractionListener mListener;

    public AdapterWatchesScannedListRecyclerView(Context context, List<DataStruct.BleDevice> list, FragmentWatchesScan.OnWatchAddFragmentInteractionListener onWatchAddFragmentInteractionListener) {
        this.bleDevices = list;
        this.mListener = onWatchAddFragmentInteractionListener;
        this.context = context;
    }

    void collect_watch_info(View object, String string2) {
        String string3 = WatchOp.devices_scanned_map.get((Object)string2).serial_number;
        object = new DataStruct.MyWatch(string2, WatchOp.devices_scanned_map.get((Object)string2).name, string3);
        WatchOp.myWatches.add((DataStruct.MyWatch)object);
        WatchOp.watches_map.put(string3, (DataStruct.MyWatch)object);
        WatchOp.readInfo(string2, (byte)1);
    }

    @Override
    public int getItemCount() {
        return this.bleDevices.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        DataStruct.BleDevice bleDevice;
        viewHolder.bleDevice = bleDevice = this.bleDevices.get(n);
        viewHolder.name.setText((CharSequence)bleDevice.name);
        viewHolder.rssi.setText(bleDevice.rssi);
        viewHolder.row_new_watch.setTag((Object)bleDevice.mac_address);
        viewHolder.row_new_watch.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AdapterWatchesScannedListRecyclerView.this.collect_watch_info(view, (String)view.getTag());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2131427455, viewGroup, false));
    }

    public class ViewHolder
    extends RecyclerView.ViewHolder {
        public String address;
        public DataStruct.BleDevice bleDevice;
        public final TextView name;
        public final View row_new_watch;
        public final TextView rssi;

        public ViewHolder(View view) {
            super(view);
            this.row_new_watch = view;
            this.name = (TextView)view.findViewById(2131231185);
            this.rssi = (TextView)view.findViewById(2131231257);
        }
    }

}

