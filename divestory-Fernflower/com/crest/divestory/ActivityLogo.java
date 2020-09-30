package com.crest.divestory;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.syntak.library.UiOp;

public class ActivityLogo extends AppCompatActivity {
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
      AppBase.readPref(this);
      this.getWindow().setFlags(512, 512);
      if (this.handler == null) {
         this.handler = new Handler();
      }

      this.handler.postDelayed(new Runnable() {
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
      Intent var1 = new Intent();
      if (WatchOp.isAgreementGranted) {
         var1.setClass(this, MainActivity.class);
      } else {
         var1.setClass(this, ActivityAgreement.class);
      }

      this.startActivity(var1);
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

   protected void onDestroy() {
      super.onDestroy();
      if (this.handler != null) {
         this.handler = null;
      }

   }

   protected void onPause() {
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   protected void onStop() {
      super.onStop();
   }
}
