package com.crest.divestory;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityAgreement extends AppCompatActivity {
   Button button_continue;
   RadioButton radio_agree;
   RadioButton radio_disagree;

   private void exit_activity() {
      this.finish();
   }

   private void init_UI() {
      this.setContentView(2131427356);
      AppBase.readPref(this);
      if (WatchOp.isExitApp) {
         this.exit_activity();
      } else {
         if (WatchOp.isAgreementGranted) {
            this.startMainActivity();
         }

         this.init_action_bar();
         TextView var1 = (TextView)this.findViewById(2131230807);
         this.radio_agree = (RadioButton)this.findViewById(2131231232);
         this.button_continue = (Button)this.findViewById(2131230866);
         this.radio_agree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton var1, boolean var2) {
               ActivityAgreement.this.button_continue.setEnabled(var2);
            }
         });
      }

   }

   private void init_action_bar() {
      AppBase.setTitleAndColor(this, this.getResources().getString(2131689835), 2131034179);
   }

   private void init_resume() {
      if (WatchOp.isExitApp) {
         this.exit_activity();
      }

   }

   private void startMainActivity() {
      Intent var1 = new Intent();
      var1.setClass(this, MainActivity.class);
      this.startActivity(var1);
   }

   public void click_continue(View var1) {
      if (this.radio_agree.isChecked()) {
         WatchOp.isAgreementGranted = true;
         AppBase.writePref(this);
         this.startMainActivity();
      } else {
         this.exit_activity();
      }

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
