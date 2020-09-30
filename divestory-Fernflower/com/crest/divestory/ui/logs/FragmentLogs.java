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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.ActivityWatchesScan;
import com.crest.divestory.ui.watches.AdapterWatchesListRecyclerView;
import com.syntak.library.UiOp;

public class FragmentLogs extends Fragment {
   WatchOp.ACTION future_action;
   private ListView lv;
   private TextView warning;

   public FragmentLogs() {
      this.future_action = WatchOp.ACTION.LIST_DIVE_LOGS;
   }

   private void go_scan(View var1) {
      if (WatchOp.isPermissionGranted) {
         Intent var2 = new Intent();
         var2.setClass(this.getContext(), ActivityWatchesScan.class);
         this.startActivity(var2);
      } else {
         UiOp.toast_message(this.getContext(), "Permissions not granted", false);
      }

   }

   public static FragmentLogs newInstance() {
      return new FragmentLogs();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var6 = var1.inflate(2131427395, var2, false);
      this.warning = (TextView)var6.findViewById(2131231445);
      if (WatchOp.myWatches != null && WatchOp.myWatches.length() > 0) {
         this.warning.setText("");
         if (var6 instanceof RecyclerView) {
            Context var5 = var6.getContext();
            RecyclerView var4 = (RecyclerView)var6;
            var4.setLayoutManager(new LinearLayoutManager(var5));
            var4.setAdapter(new AdapterWatchesListRecyclerView(this.getActivity(), WatchOp.myWatches.list, this.future_action));
         }
      } else {
         this.warning.setText(2131689746);
      }

      return var6;
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
}
