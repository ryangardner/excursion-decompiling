package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.util.List;

public class AdapterWatchesListRecyclerView extends RecyclerView.Adapter<AdapterWatchesListRecyclerView.ViewHolder> {
   private final Context context;
   WatchOp.ACTION future_action;
   private final List<DataStruct.MyWatch> myWatches;
   private float width_button_R;

   public AdapterWatchesListRecyclerView(Context var1, List<DataStruct.MyWatch> var2, WatchOp.ACTION var3) {
      this.myWatches = var2;
      this.context = var1;
      this.future_action = var3;
   }

   void delete(String var1) {
      AppBase.dbOp.deleteMyWatchBySerialNumber(var1);
      WatchOp.myWatches.deleteMyWatchBySerialNumber(var1);
      this.notifyDataSetChanged();
   }

   void edit_watch(View var1, String var2) {
   }

   public int getItemCount() {
      return this.myWatches.size();
   }

   public void onBindViewHolder(AdapterWatchesListRecyclerView.ViewHolder var1, int var2) {
      DataStruct.MyWatch var3 = (DataStruct.MyWatch)this.myWatches.get(var2);
      var1.myWatch = var3;
      var1.model_name.setText(var3.model_name_to_show);
      var1.tv_serial_number.setText(var3.serial_number.substring(0, 11));
      var1.hardware_version.setText(var3.hardware_version);
      var1.firmware_version.setText(var3.firmware_version);
      var1.isStored = var3.isStored;
      var1.isBond = var3.isBond;
      var1.status = var3.status;
      var1.main_content.setTag(var3.mac_address);
      var1.button_R.setTag(var3.serial_number);
      boolean var4 = var1.isStored;
      var4 = var1.isBond;
      var2 = null.$SwitchMap$com$crest$divestory$DataStruct$CONNECTION_STATUS[var1.status.ordinal()];
   }

   public AdapterWatchesListRecyclerView.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
      return new AdapterWatchesListRecyclerView.ViewHolder(LayoutInflater.from(var1.getContext()).inflate(2131427456, var1, false));
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      public ImageView button_R;
      public final TextView firmware_version;
      public final TextView hardware_version;
      public boolean isBond = false;
      public boolean isStored = false;
      public String mac_address;
      public final LinearLayout main_content;
      public final TextView model_name;
      public DataStruct.MyWatch myWatch;
      public final ImageView picture;
      public final View row_watch;
      public String serial_number;
      public DataStruct.CONNECTION_STATUS status;
      public final TextView tv_serial_number;

      public ViewHolder(View var2) {
         super(var2);
         this.status = DataStruct.CONNECTION_STATUS.SCANNED;
         this.row_watch = var2;
         this.picture = (ImageView)var2.findViewById(2131231218);
         this.model_name = (TextView)var2.findViewById(2131231155);
         this.tv_serial_number = (TextView)var2.findViewById(2131231297);
         this.hardware_version = (TextView)var2.findViewById(2131231051);
         this.firmware_version = (TextView)var2.findViewById(2131231022);
         this.main_content = (LinearLayout)var2.findViewById(2131231143);
         this.button_R = (ImageView)var2.findViewById(2131230863);
         (new SlideViewByClick(this.main_content, this.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD) {
            public void OnFrontClick(View var1) {
               super.OnFrontClick(var1);
               WatchOp.mac_address_to_scan = (String)var1.getTag();
               AppBase.fragmentSyncedWatchesList.show_reminder_screen();
            }
         }).setAutoSizing(true).start();
         this.button_R.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               final String var2 = (String)var1.getTag();
               ConfirmDialog var10001 = new ConfirmDialog(AdapterWatchesListRecyclerView.this.context, AdapterWatchesListRecyclerView.this.context.getString(2131689588), AdapterWatchesListRecyclerView.this.context.getString(2131689571), AdapterWatchesListRecyclerView.this.context.getString(2131689536)) {
                  public void OnConfirmed() {
                     super.OnConfirmed();
                     AdapterWatchesListRecyclerView.this.delete(var2);
                  }
               };
            }
         });
      }
   }
}
