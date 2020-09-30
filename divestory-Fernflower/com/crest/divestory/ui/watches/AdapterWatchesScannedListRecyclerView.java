package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import java.util.List;

public class AdapterWatchesScannedListRecyclerView extends RecyclerView.Adapter<AdapterWatchesScannedListRecyclerView.ViewHolder> {
   private final List<DataStruct.BleDevice> bleDevices;
   Context context;
   private final FragmentWatchesScan.OnWatchAddFragmentInteractionListener mListener;

   public AdapterWatchesScannedListRecyclerView(Context var1, List<DataStruct.BleDevice> var2, FragmentWatchesScan.OnWatchAddFragmentInteractionListener var3) {
      this.bleDevices = var2;
      this.mListener = var3;
      this.context = var1;
   }

   void collect_watch_info(View var1, String var2) {
      String var3 = ((DataStruct.BleDevice)WatchOp.devices_scanned_map.get(var2)).serial_number;
      DataStruct.MyWatch var4 = new DataStruct.MyWatch(var2, ((DataStruct.BleDevice)WatchOp.devices_scanned_map.get(var2)).name, var3);
      WatchOp.myWatches.add(var4);
      WatchOp.watches_map.put(var3, var4);
      WatchOp.readInfo(var2, (byte)1);
   }

   public int getItemCount() {
      return this.bleDevices.size();
   }

   public void onBindViewHolder(AdapterWatchesScannedListRecyclerView.ViewHolder var1, int var2) {
      DataStruct.BleDevice var3 = (DataStruct.BleDevice)this.bleDevices.get(var2);
      var1.bleDevice = var3;
      var1.name.setText(var3.name);
      var1.rssi.setText(var3.rssi);
      var1.row_new_watch.setTag(var3.mac_address);
      var1.row_new_watch.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            AdapterWatchesScannedListRecyclerView.this.collect_watch_info(var1, (String)var1.getTag());
         }
      });
   }

   public AdapterWatchesScannedListRecyclerView.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
      return new AdapterWatchesScannedListRecyclerView.ViewHolder(LayoutInflater.from(var1.getContext()).inflate(2131427455, var1, false));
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      public String address;
      public DataStruct.BleDevice bleDevice;
      public final TextView name;
      public final View row_new_watch;
      public final TextView rssi;

      public ViewHolder(View var2) {
         super(var2);
         this.row_new_watch = var2;
         this.name = (TextView)var2.findViewById(2131231185);
         this.rssi = (TextView)var2.findViewById(2131231257);
      }
   }
}
