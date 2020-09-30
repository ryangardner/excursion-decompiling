/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGattService
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Button
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.SearchView
 *  android.widget.TextView
 */
package com.crest.divestory;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.AppBase;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.PagerAdapterMain;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.crest.divestory.ui.settings.FragmentAppSettings;
import com.crest.divestory.ui.watches.ActivityWatchesScan;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syntak.library.BleOp;
import com.syntak.library.UiOp;
import com.syntak.library.ui.EditorPassword;
import java.util.List;

public class MainActivity
extends AppCompatActivity {
    public static boolean flag_need_refresh = false;
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
        EditorPassword editorPassword = this.ep;
        if (editorPassword != null) {
            editorPassword.stop();
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
        ViewPager viewPager = (ViewPager)((Object)this.findViewById(2131231144));
        BottomNavigationView bottomNavigationView = (BottomNavigationView)((Object)this.findViewById(2131231142));
        this.init_pager(viewPager, bottomNavigationView);
        this.init_bottom_navigation(viewPager, bottomNavigationView);
        AppBase.askPermissions(this);
        AppBase.setTitleAndColor((Activity)this, 2131689733, 2131034179);
    }

    private void init_bottom_navigation(final ViewPager viewPager, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int n = menuItem.getItemId();
                int n2 = 0;
                boolean bl = true;
                int n3 = n2;
                boolean bl2 = bl;
                switch (n) {
                    default: {
                        bl2 = false;
                        n3 = n2;
                        break;
                    }
                    case 2131231188: {
                        n3 = 2;
                        bl2 = bl;
                        break;
                    }
                    case 2131231187: {
                        n3 = 1;
                        bl2 = bl;
                    }
                    case 2131231189: 
                }
                viewPager.setCurrentItem(n3);
                MainActivity.this.updateAppBar(n3);
                return bl2;
            }
        });
        bottomNavigationView.setItemIconTintList(null);
    }

    private void init_pager(ViewPager viewPager, final BottomNavigationView bottomNavigationView) {
        PagerAdapterMain pagerAdapterMain;
        pagerAdapter = pagerAdapterMain = new PagerAdapterMain(this.getSupportFragmentManager());
        AppBase.pagerAdapter = pagerAdapterMain;
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int n) {
                bottomNavigationView.getMenu().getItem(n).setChecked(true);
                MainActivity.this.updateAppBar(n);
            }
        });
    }

    private void init_resume() {
        WatchOp.isSyncDone = false;
        WatchOp.isFirmwareUpgraded = false;
        WatchOp.isTimeout = false;
        AppBase.open_db((Context)this);
        WatchOp.init_my_watches();
        if (handler == null) {
            this.start_handler();
            this.flag_new_handler = true;
        }
        if (AppBase.flag_checking_server) return;
        if (!this.flag_new_handler) {
            if (WatchOp.isLoginOK) return;
            handler.obtainMessage(WatchOp.TASK.LOGIN.ordinal()).sendToTarget();
            return;
        }
        this.flag_new_handler = false;
        if (WatchOp.isLoginOK) {
            handler.postDelayed(new Runnable(){

                @Override
                public void run() {
                    handler.obtainMessage(WatchOp.TASK.LOGIN_OK.ordinal()).sendToTarget();
                }
            }, 500L);
            return;
        }
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                handler.obtainMessage(WatchOp.TASK.LOGIN.ordinal()).sendToTarget();
            }
        }, 500L);
    }

    private void start_handler() {
        handler = new Handler(){

            public void handleMessage(Message message) {
                WatchOp.TASK tASK = WatchOp.TASK.values()[message.what];
                if (6.$SwitchMap$com$crest$divestory$WatchOp$TASK[tASK.ordinal()] != 1) {
                    return;
                }
                MainActivity.this.sign_downloading.setVisibility(4);
                UiOp.toast_message(MainActivity.this.context, MainActivity.this.getResources().getString(((Integer)message.obj).intValue()), true);
            }
        };
    }

    private void updateAppBar(int n) {
        this.itemScan.setVisible(false);
        this.itemFilter.setVisible(false);
        String string2 = this.getResources().getString(2131689522);
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    string2 = this.getResources().getString(2131689523);
                }
            } else {
                this.itemFilter.setVisible(true);
                string2 = this.getResources().getString(2131689599);
            }
        } else {
            this.itemScan.setVisible(true);
            string2 = this.getResources().getString(2131689733);
        }
        AppBase.setTitleAndColor((Activity)this, string2, 2131034179);
    }

    public void cancel_filter_screen(View view) {
        AppBase.fragmentDiveLogsList.cancel_filter_screen();
        this.updateAppBar(1);
    }

    public void clear_filter(View view) {
        AppBase.fragmentDiveLogsList.clear_filter();
    }

    public void click_close_app_version(View view) {
        AppBase.fragmentAppSettings.click_close_app_version(view);
    }

    public void click_close_terms(View view) {
        AppBase.fragmentAppSettings.click_close_terms(view);
    }

    public void date_picker_end(View view) {
        AppBase.fragmentDiveLogsList.date_picker_end(view);
    }

    public void date_picker_start(View view) {
        AppBase.fragmentDiveLogsList.date_picker_start(view);
    }

    public void go_scan(View view) {
        AppBase.fragmentSyncedWatchesList.hide_reminder_screen(view);
        if (WatchOp.isPermissionGranted) {
            view = new Intent();
            view.setClass((Context)this, ActivityWatchesScan.class);
            this.startActivity((Intent)view);
            return;
        }
        UiOp.toast_message((Context)this, "Permissions not granted", false);
    }

    public void hide_reminder_screen(View view) {
        AppBase.fragmentSyncedWatchesList.hide_reminder_screen(view);
    }

    @Override
    public void onBackPressed() {
        this.exit_activity();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init_UI();
        this.init_resume();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.init_UI();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131492866, menu2);
        this.itemScan = menu2.findItem(2131230799);
        this.itemFilter = menu2.findItem(2131230791);
        return true;
    }

    @Override
    protected void onDestroy() {
        AppBase.close_db();
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int n = menuItem.getItemId();
        if (n == 2131230791) {
            AppBase.fragmentDiveLogsList.show_filter_screen();
            AppBase.setTitleAndColor((Activity)this, this.getResources().getString(2131689809), 2131034179);
            return super.onOptionsItemSelected(menuItem);
        }
        if (n != 2131230799) {
            return super.onOptionsItemSelected(menuItem);
        }
        WatchOp.mac_address_to_scan = null;
        AppBase.fragmentSyncedWatchesList.show_reminder_screen();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        if (n != 9999) {
            return;
        }
        int n2 = arrn.length;
        int n3 = 0;
        n = 1;
        do {
            if (n3 >= n2) {
                if (n == 0) return;
                WatchOp.isPermissionGranted = true;
                return;
            }
            int n4 = arrn[n3];
            n = n != 0 && n4 == 0 ? 1 : 0;
            ++n3;
        } while (true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.init_resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void start_filter(View view) {
        AppBase.fragmentDiveLogsList.start_filter();
    }

}

