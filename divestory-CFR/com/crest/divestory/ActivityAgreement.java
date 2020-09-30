/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.RadioButton
 *  android.widget.TextView
 */
package com.crest.divestory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.MainActivity;
import com.crest.divestory.WatchOp;

public class ActivityAgreement
extends AppCompatActivity {
    Button button_continue;
    RadioButton radio_agree;
    RadioButton radio_disagree;

    private void exit_activity() {
        this.finish();
    }

    private void init_UI() {
        this.setContentView(2131427356);
        AppBase.readPref((Context)this);
        if (WatchOp.isExitApp) {
            this.exit_activity();
            return;
        }
        if (WatchOp.isAgreementGranted) {
            this.startMainActivity();
        }
        this.init_action_bar();
        TextView textView = (TextView)this.findViewById(2131230807);
        this.radio_agree = (RadioButton)this.findViewById(2131231232);
        this.button_continue = (Button)this.findViewById(2131230866);
        this.radio_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                ActivityAgreement.this.button_continue.setEnabled(bl);
            }
        });
    }

    private void init_action_bar() {
        AppBase.setTitleAndColor((Activity)this, this.getResources().getString(2131689835), 2131034179);
    }

    private void init_resume() {
        if (!WatchOp.isExitApp) return;
        this.exit_activity();
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass((Context)this, MainActivity.class);
        this.startActivity(intent);
    }

    public void click_continue(View view) {
        if (this.radio_agree.isChecked()) {
            WatchOp.isAgreementGranted = true;
            AppBase.writePref((Context)this);
            this.startMainActivity();
            return;
        }
        this.exit_activity();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

}

