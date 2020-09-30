/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.Window
 */
package com.crest.divestory;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.ActivityAgreement;
import com.crest.divestory.AppBase;
import com.crest.divestory.MainActivity;
import com.crest.divestory.WatchOp;
import com.syntak.library.UiOp;

public class ActivityLogo
extends AppCompatActivity {
    Handler handler = null;

    private void exit_activity() {
        if (this.handler != null) {
            this.handler = null;
        }
        WatchOp.isLogoShown = false;
        this.finish();
    }

    private void init_UI() {
        if (WatchOp.isLogoShown) {
            this.exit_activity();
        }
        UiOp.hideActionBar(this);
        this.setContentView(2131427359);
        AppBase.readPref((Context)this);
        this.getWindow().setFlags(512, 512);
        if (this.handler == null) {
            this.handler = new Handler();
        }
        this.handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                ActivityLogo.this.startNextActivity();
            }
        }, 200L);
    }

    private void init_resume() {
        if (WatchOp.isLogoShown || WatchOp.isExitApp) {
            this.exit_activity();
        }
        WatchOp.isLogoShown = true;
    }

    private void startNextActivity() {
        Intent intent = new Intent();
        if (WatchOp.isAgreementGranted) {
            intent.setClass((Context)this, MainActivity.class);
        } else {
            intent.setClass((Context)this, ActivityAgreement.class);
        }
        this.startActivity(intent);
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
        if (this.handler == null) return;
        this.handler = null;
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

