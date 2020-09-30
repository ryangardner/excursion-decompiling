/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.logs.AdapterDiveLogsList;

public class ActivityDiveLogsList
extends AppCompatActivity {
    private static String serial_number;
    private Activity activity;
    private AdapterDiveLogsList adapter = null;
    private Context context;
    private ListView listView;
    private TextView warning;

    private void UI_init() {
        AdapterDiveLogsList adapterDiveLogsList;
        this.setContentView(2131427358);
        this.context = this;
        this.activity = this;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(2131165422);
        AppBase.setTitleAndColor((Activity)this, this.getResources().getString(2131689599), 2131034179);
        this.warning = (TextView)this.findViewById(2131231445);
        this.listView = (ListView)this.findViewById(2131231133);
        ActivityDiveLogsList.init_dive_logs();
        if (WatchOp.dive_logs != null && WatchOp.dive_logs.length() > 0) {
            this.warning.setText((CharSequence)"");
        } else {
            this.warning.setText(2131689746);
        }
        this.adapter = adapterDiveLogsList = new AdapterDiveLogsList((Context)this);
        this.listView.setAdapter((ListAdapter)adapterDiveLogsList);
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

    @Override
    public void onBackPressed() {
        this.exit_activity();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.UI_init();
        this.resume_init();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        serial_number = this.getIntent().getStringExtra("serial_number");
        this.UI_init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.exit_activity();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.resume_init();
    }
}

