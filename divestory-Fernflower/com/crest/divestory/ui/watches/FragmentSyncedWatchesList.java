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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.WatchOp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syntak.library.UiOp;

public class FragmentSyncedWatchesList extends Fragment {
   private static final String ARG_FUTURE_ACTION = "future_action";
   public static AdapterWatchesListRecyclerView adapter;
   WatchOp.ACTION future_action;
   private RecyclerView lv;
   private int mColumnCount = 1;
   RelativeLayout reminder_screen;
   MenuItem scan;
   FloatingActionButton scan_watches;
   private TextView warning;

   private void go_scan(View var1) {
      if (WatchOp.isPermissionGranted) {
         Intent var2 = new Intent();
         var2.setClass(this.getContext(), ActivityWatchesScan.class);
         this.startActivity(var2);
      } else {
         UiOp.toast_message(this.getContext(), "Permissions not granted", false);
      }

   }

   public void hide_reminder_screen(View var1) {
      this.reminder_screen.setVisibility(8);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (this.getArguments() != null) {
         this.future_action = (WatchOp.ACTION)this.getArguments().getSerializable("future_action");
      }

   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = var1.inflate(2131427398, var2, false);
      this.reminder_screen = (RelativeLayout)var4.findViewById(2131231242);
      this.warning = (TextView)var4.findViewById(2131231445);
      this.lv = (RecyclerView)var4.findViewById(2131231133);
      if (WatchOp.myWatches != null && WatchOp.myWatches.length() > 0) {
         this.warning.setText("");
         if (this.lv instanceof RecyclerView) {
            Context var6 = var4.getContext();
            RecyclerView var5 = this.lv;
            var5.setLayoutManager(new LinearLayoutManager(var6));
            AdapterWatchesListRecyclerView var7 = new AdapterWatchesListRecyclerView(this.getActivity(), WatchOp.myWatches.list, this.future_action);
            adapter = var7;
            var5.setAdapter(var7);
         }
      } else {
         this.warning.setText(2131689746);
      }

      return var4;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public void onDestroyView() {
      super.onDestroyView();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
   }

   public void show_reminder_screen() {
      this.reminder_screen.setVisibility(0);
   }
}
