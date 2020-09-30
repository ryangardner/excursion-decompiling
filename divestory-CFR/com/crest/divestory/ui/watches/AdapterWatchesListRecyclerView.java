/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.util.List;

public class AdapterWatchesListRecyclerView
extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    WatchOp.ACTION future_action;
    private final List<DataStruct.MyWatch> myWatches;
    private float width_button_R;

    public AdapterWatchesListRecyclerView(Context context, List<DataStruct.MyWatch> list, WatchOp.ACTION aCTION) {
        this.myWatches = list;
        this.context = context;
        this.future_action = aCTION;
    }

    void delete(String string2) {
        AppBase.dbOp.deleteMyWatchBySerialNumber(string2);
        WatchOp.myWatches.deleteMyWatchBySerialNumber(string2);
        this.notifyDataSetChanged();
    }

    void edit_watch(View view, String string2) {
    }

    @Override
    public int getItemCount() {
        return this.myWatches.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        DataStruct.MyWatch myWatch;
        viewHolder.myWatch = myWatch = this.myWatches.get(n);
        viewHolder.model_name.setText((CharSequence)myWatch.model_name_to_show);
        viewHolder.tv_serial_number.setText((CharSequence)myWatch.serial_number.substring(0, 11));
        viewHolder.hardware_version.setText((CharSequence)myWatch.hardware_version);
        viewHolder.firmware_version.setText((CharSequence)myWatch.firmware_version);
        viewHolder.isStored = myWatch.isStored;
        viewHolder.isBond = myWatch.isBond;
        viewHolder.status = myWatch.status;
        viewHolder.main_content.setTag((Object)myWatch.mac_address);
        viewHolder.button_R.setTag((Object)myWatch.serial_number);
        boolean bl = viewHolder.isStored;
        bl = viewHolder.isBond;
        n = 1.$SwitchMap$com$crest$divestory$DataStruct$CONNECTION_STATUS[viewHolder.status.ordinal()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2131427456, viewGroup, false));
    }

    public class ViewHolder
    extends RecyclerView.ViewHolder {
        public ImageView button_R;
        public final TextView firmware_version;
        public final TextView hardware_version;
        public boolean isBond;
        public boolean isStored;
        public String mac_address;
        public final LinearLayout main_content;
        public final TextView model_name;
        public DataStruct.MyWatch myWatch;
        public final ImageView picture;
        public final View row_watch;
        public String serial_number;
        public DataStruct.CONNECTION_STATUS status;
        public final TextView tv_serial_number;

        public ViewHolder(View view) {
            super(view);
            this.isStored = false;
            this.isBond = false;
            this.status = DataStruct.CONNECTION_STATUS.SCANNED;
            this.row_watch = view;
            this.picture = (ImageView)view.findViewById(2131231218);
            this.model_name = (TextView)view.findViewById(2131231155);
            this.tv_serial_number = (TextView)view.findViewById(2131231297);
            this.hardware_version = (TextView)view.findViewById(2131231051);
            this.firmware_version = (TextView)view.findViewById(2131231022);
            this.main_content = (LinearLayout)view.findViewById(2131231143);
            this.button_R = (ImageView)view.findViewById(2131230863);
            new SlideViewByClick((View)this.main_content, (View)this.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD){

                @Override
                public void OnFrontClick(View view) {
                    super.OnFrontClick(view);
                    WatchOp.mac_address_to_scan = (String)view.getTag();
                    AppBase.fragmentSyncedWatchesList.show_reminder_screen();
                }
            }.setAutoSizing(true).start();
            this.button_R.setOnClickListener(new View.OnClickListener(){

                public void onClick(View object) {
                    object = (String)object.getTag();
                    new ConfirmDialog(AdapterWatchesListRecyclerView.this.context, AdapterWatchesListRecyclerView.this.context.getString(2131689588), AdapterWatchesListRecyclerView.this.context.getString(2131689571), AdapterWatchesListRecyclerView.this.context.getString(2131689536), (String)object){
                        final /* synthetic */ String val$mac_address;
                        {
                            this.val$mac_address = string5;
                            super(context, string2, string3, string4);
                        }

                        @Override
                        public void OnConfirmed() {
                            super.OnConfirmed();
                            AdapterWatchesListRecyclerView.this.delete(this.val$mac_address);
                        }
                    };
                }

            });
        }

    }

}

