package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;

public class ActivityDiveLogsList extends AppCompatActivity {
   private static String serial_number;
   private Activity activity;
   private AdapterDiveLogsList adapter = null;
   private Context context;
   private ListView listView;
   private TextView warning;

   private void UI_init() {
      this.setContentView(2131427358);
      this.context = this;
      this.activity = this;
      this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      this.getSupportActionBar().setHomeAsUpIndicator(2131165422);
      AppBase.setTitleAndColor(this, this.getResources().getString(2131689599), 2131034179);
      this.warning = (TextView)this.findViewById(2131231445);
      this.listView = (ListView)this.findViewById(2131231133);
      init_dive_logs();
      if (WatchOp.dive_logs != null && WatchOp.dive_logs.length() > 0) {
         this.warning.setText("");
      } else {
         this.warning.setText(2131689746);
      }

      AdapterDiveLogsList var1 = new AdapterDiveLogsList(this);
      this.adapter = var1;
      this.listView.setAdapter(var1);
   }

   private static void init_dive_logs() {
      if (WatchOp.dive_logs != null) {
         WatchOp.dive_logs = null;
      }

      WatchOp.dive_logs = new DataStruct.DiveLogs(AppBase.dbOp.getDiveLogsBySerialNumber(serial_number));
   }

   private void resume_init() {
   }

   public void exit_activity() {
      this.finish();
   }

   public void onBackPressed() {
      this.exit_activity();
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.UI_init();
      this.resume_init();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      serial_number = this.getIntent().getStringExtra("serial_number");
      this.UI_init();
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      if (var1.getItemId() == 16908332) {
         this.exit_activity();
      }

      return super.onOptionsItemSelected(var1);
   }

   protected void onResume() {
      super.onResume();
      this.resume_init();
   }
}
