package com.crest.divestory;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.ui.PagerAdapterMain;
import com.crest.divestory.ui.watches.ActivityWatchesScan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syntak.library.UiOp;
import com.syntak.library.ui.EditorPassword;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   public static boolean flag_need_refresh;
   public static Handler handler;
   public static PagerAdapterMain pagerAdapter;
   public static SearchView searchView;
   static String search_text;
   Activity activity;
   float alpha_original;
   Context context;
   EditorPassword ep = null;
   boolean flag_new_handler = true;
   MenuItem itemFilter;
   MenuItem itemScan;
   ListView list;
   private List<BluetoothGattService> list_services = null;
   Button re_scan;
   int scanning_period = 5;
   RelativeLayout sign_downloading;
   RelativeLayout sign_scanning;
   BluetoothDevice target_device;
   TextView warning;

   private void exit_activity() {
      handler = null;
      EditorPassword var1 = this.ep;
      if (var1 != null) {
         var1.stop();
      }

      this.ep = null;
      WatchOp.isLoginOK = false;
      if (WatchOp.bleOp != null) {
         WatchOp.bleOp.stop();
         WatchOp.bleOp = null;
      }

      WatchOp.isExitApp = true;
      this.finish();
   }

   private void init_UI() {
      this.setContentView(2131427360);
      this.context = this;
      this.activity = this;
      ViewPager var1 = (ViewPager)this.findViewById(2131231144);
      BottomNavigationView var2 = (BottomNavigationView)this.findViewById(2131231142);
      this.init_pager(var1, var2);
      this.init_bottom_navigation(var1, var2);
      AppBase.askPermissions(this);
      AppBase.setTitleAndColor(this, 2131689733, 2131034179);
   }

   private void init_bottom_navigation(final ViewPager var1, BottomNavigationView var2) {
      var2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         public boolean onNavigationItemSelected(MenuItem var1x) {
            int var2 = var1x.getItemId();
            byte var3 = 0;
            boolean var4 = true;
            byte var5 = var3;
            boolean var6 = var4;
            switch(var2) {
            case 2131231187:
               var5 = 1;
               var6 = var4;
               break;
            case 2131231188:
               var5 = 2;
               var6 = var4;
            case 2131231189:
               break;
            default:
               var6 = false;
               var5 = var3;
            }

            var1.setCurrentItem(var5);
            MainActivity.this.updateAppBar(var5);
            return var6;
         }
      });
      var2.setItemIconTintList((ColorStateList)null);
   }

   private void init_pager(ViewPager var1, final BottomNavigationView var2) {
      PagerAdapterMain var3 = new PagerAdapterMain(this.getSupportFragmentManager());
      pagerAdapter = var3;
      AppBase.pagerAdapter = var3;
      var1.setAdapter(pagerAdapter);
      var1.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
         public void onPageSelected(int var1) {
            var2.getMenu().getItem(var1).setChecked(true);
            MainActivity.this.updateAppBar(var1);
         }
      });
   }

   private void init_resume() {
      WatchOp.isSyncDone = false;
      WatchOp.isFirmwareUpgraded = false;
      WatchOp.isTimeout = false;
      AppBase.open_db(this);
      WatchOp.init_my_watches();
      if (handler == null) {
         this.start_handler();
         this.flag_new_handler = true;
      }

      if (!AppBase.flag_checking_server) {
         if (this.flag_new_handler) {
            this.flag_new_handler = false;
            if (WatchOp.isLoginOK) {
               handler.postDelayed(new Runnable() {
                  public void run() {
                     MainActivity.handler.obtainMessage(WatchOp.TASK.LOGIN_OK.ordinal()).sendToTarget();
                  }
               }, 500L);
            } else {
               handler.postDelayed(new Runnable() {
                  public void run() {
                     MainActivity.handler.obtainMessage(WatchOp.TASK.LOGIN.ordinal()).sendToTarget();
                  }
               }, 500L);
            }
         } else if (!WatchOp.isLoginOK) {
            handler.obtainMessage(WatchOp.TASK.LOGIN.ordinal()).sendToTarget();
         }
      }

   }

   private void start_handler() {
      handler = new Handler() {
         public void handleMessage(Message var1) {
            WatchOp.TASK var2 = WatchOp.TASK.values()[var1.what];
            if (null.$SwitchMap$com$crest$divestory$WatchOp$TASK[var2.ordinal()] == 1) {
               MainActivity.this.sign_downloading.setVisibility(4);
               UiOp.toast_message(MainActivity.this.context, MainActivity.this.getResources().getString((Integer)var1.obj), true);
            }

         }
      };
   }

   private void updateAppBar(int var1) {
      this.itemScan.setVisible(false);
      this.itemFilter.setVisible(false);
      String var2 = this.getResources().getString(2131689522);
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 == 2) {
               var2 = this.getResources().getString(2131689523);
            }
         } else {
            this.itemFilter.setVisible(true);
            var2 = this.getResources().getString(2131689599);
         }
      } else {
         this.itemScan.setVisible(true);
         var2 = this.getResources().getString(2131689733);
      }

      AppBase.setTitleAndColor(this, var2, 2131034179);
   }

   public void cancel_filter_screen(View var1) {
      AppBase.fragmentDiveLogsList.cancel_filter_screen();
      this.updateAppBar(1);
   }

   public void clear_filter(View var1) {
      AppBase.fragmentDiveLogsList.clear_filter();
   }

   public void click_close_app_version(View var1) {
      AppBase.fragmentAppSettings.click_close_app_version(var1);
   }

   public void click_close_terms(View var1) {
      AppBase.fragmentAppSettings.click_close_terms(var1);
   }

   public void date_picker_end(View var1) {
      AppBase.fragmentDiveLogsList.date_picker_end(var1);
   }

   public void date_picker_start(View var1) {
      AppBase.fragmentDiveLogsList.date_picker_start(var1);
   }

   public void go_scan(View var1) {
      AppBase.fragmentSyncedWatchesList.hide_reminder_screen(var1);
      if (WatchOp.isPermissionGranted) {
         Intent var2 = new Intent();
         var2.setClass(this, ActivityWatchesScan.class);
         this.startActivity(var2);
      } else {
         UiOp.toast_message(this, "Permissions not granted", false);
      }

   }

   public void hide_reminder_screen(View var1) {
      AppBase.fragmentSyncedWatchesList.hide_reminder_screen(var1);
   }

   public void onBackPressed() {
      this.exit_activity();
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.init_UI();
      this.init_resume();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.init_UI();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131492866, var1);
      this.itemScan = var1.findItem(2131230799);
      this.itemFilter = var1.findItem(2131230791);
      return true;
   }

   protected void onDestroy() {
      AppBase.close_db();
      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      int var2 = var1.getItemId();
      if (var2 != 2131230791) {
         if (var2 == 2131230799) {
            WatchOp.mac_address_to_scan = null;
            AppBase.fragmentSyncedWatchesList.show_reminder_screen();
         }
      } else {
         AppBase.fragmentDiveLogsList.show_filter_screen();
         AppBase.setTitleAndColor(this, this.getResources().getString(2131689809), 2131034179);
      }

      return super.onOptionsItemSelected(var1);
   }

   protected void onPause() {
      super.onPause();
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      if (var1 == 9999) {
         int var4 = var3.length;
         int var5 = 0;

         boolean var7;
         for(var7 = true; var5 < var4; ++var5) {
            int var6 = var3[var5];
            if (var7 && var6 == 0) {
               var7 = true;
            } else {
               var7 = false;
            }
         }

         if (var7) {
            WatchOp.isPermissionGranted = true;
         }
      }

   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   protected void onStop() {
      super.onStop();
   }

   public void start_filter(View var1) {
      AppBase.fragmentDiveLogsList.start_filter();
   }
}
