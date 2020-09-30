package com.crest.divestory.ui.watches;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;

public class AdapterWatchesScannedList extends BaseAdapter {
   private final DataStruct.BleDevices bleDevices;
   private Context context;
   private LayoutInflater inflater;

   public AdapterWatchesScannedList(Context var1, DataStruct.BleDevices var2) {
      this.context = var1;
      this.bleDevices = var2;
      this.inflater = LayoutInflater.from(var1);
   }

   public int getCount() {
      return this.bleDevices.length();
   }

   public Object getItem(int var1) {
      return this.bleDevices.list.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      AdapterWatchesScannedList.ViewHolder var5;
      if (var2 == null) {
         var5 = new AdapterWatchesScannedList.ViewHolder();
         var2 = this.inflater.inflate(2131427455, (ViewGroup)null);
         var5.row_scanned_watch = (RelativeLayout)var2.findViewById(2131231255);
         var5.row_scanned_watch.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               int var2 = ((AdapterWatchesScannedList.ViewHolder)var1.getTag()).position;
               AdapterWatchesScannedList var3 = AdapterWatchesScannedList.this;
               var3.go_syncing(var3.context, var2);
            }
         });
         var5.name = (TextView)var2.findViewById(2131231185);
         var5.rssi = (TextView)var2.findViewById(2131231257);
         var2.setTag(var5);
      } else {
         var5 = (AdapterWatchesScannedList.ViewHolder)var2.getTag();
      }

      var5.position = var1;
      DataStruct.BleDevice var4 = (DataStruct.BleDevice)this.bleDevices.list.get(var1);
      var5.name.setText(var4.name);
      var5.rssi.setText(String.valueOf(var4.rssi));
      return var2;
   }

   void go_syncing(Context var1, int var2) {
      WatchOp.bleOp.stop_scanning();
      Intent var3 = new Intent();
      var3.setClass(var1, ActivityWatchSync.class);
      var3.putExtra("index", var2);
      var1.startActivity(var3);
   }

   static class ViewHolder {
      public String address;
      public TextView name;
      int position;
      public RelativeLayout row_scanned_watch;
      public TextView rssi;
   }
}
