package com.crest.divestory.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.crest.divestory.AppBase;
import com.crest.divestory.DbOp;
import com.crest.divestory.FragmentPage;
import com.syntak.library.FileOp;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import com.syntak.library.ui.ShowHiddenVIewByClick;

public class FragmentAppSettings extends FragmentPage {
   private static Activity activity;
   private static Context context;
   TextView app_version;
   TextView app_version_name;
   TextView backup;
   Button close_app_version;
   TextView feedback;
   TextView language;
   RadioButton language_Chinese;
   RadioButton language_English;
   RadioGroup language_back;
   RelativeLayout screen_app_version;
   LinearLayout screen_terms;
   LinearLayout sign_processing;
   TextView terms;
   TextView unit;
   RadioGroup unit_back;
   RadioButton unit_imperial;
   RadioButton unit_metric;

   public static FragmentAppSettings newInstance(int var0) {
      FragmentAppSettings var1 = new FragmentAppSettings();
      var1.page = var0;
      return var1;
   }

   public void click_app_version(View var1) {
      this.screen_app_version.setVisibility(0);
   }

   public void click_backup(View var1) {
      this.sign_processing.setVisibility(0);
      String var2 = AppBase.folder_app_external;
      DbOp var3 = AppBase.dbOp;
      var2 = FileOp.combinePath(var2, "divestory.db");
      FileOp.copyFile(AppBase.dbOp.dbPath, var2);
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" ");
      var4.append(this.getResources().getString(2131689533));
      String var5 = var4.toString();
      UiOp.toast_message(context, var5, false);
      this.sign_processing.setVisibility(8);
   }

   public void click_close_app_version(View var1) {
      this.screen_app_version.setVisibility(8);
   }

   public void click_close_terms(View var1) {
      this.screen_terms.setVisibility(8);
   }

   public void click_feedback(View var1) {
      StringBuilder var3 = new StringBuilder();
      var3.append("mailto:info@EOPI.co?subject=APP_Feedback_");
      var3.append(TimeOp.MsToYearMonthDay(TimeOp.getNow()));
      String var2 = var3.toString();
      Intent var4 = new Intent("android.intent.action.SENDTO");
      var4.setData(Uri.parse(var2));
      this.startActivity(var4);
   }

   public void click_terms(View var1) {
      this.screen_terms.setVisibility(0);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      context = this.getContext();
      View var4 = var1.inflate(2131427390, var2, false);
      this.unit = (TextView)var4.findViewById(2131231429);
      this.unit_back = (RadioGroup)var4.findViewById(2131231430);
      this.unit_metric = (RadioButton)var4.findViewById(2131231432);
      this.unit_imperial = (RadioButton)var4.findViewById(2131231431);
      this.language = (TextView)var4.findViewById(2131231115);
      this.language_back = (RadioGroup)var4.findViewById(2131231118);
      this.language_Chinese = (RadioButton)var4.findViewById(2131231116);
      this.language_English = (RadioButton)var4.findViewById(2131231117);
      this.app_version = (TextView)var4.findViewById(2131230814);
      this.feedback = (TextView)var4.findViewById(2131231016);
      this.terms = (TextView)var4.findViewById(2131231373);
      LinearLayout var5 = (LinearLayout)var4.findViewById(2131231303);
      this.sign_processing = var5;
      var5.setVisibility(8);
      RelativeLayout var6 = (RelativeLayout)var4.findViewById(2131231272);
      this.screen_app_version = var6;
      var6.setVisibility(8);
      this.app_version_name = (TextView)var4.findViewById(2131230815);
      this.close_app_version = (Button)var4.findViewById(2131230926);
      var5 = (LinearLayout)var4.findViewById(2131231275);
      this.screen_terms = var5;
      var5.setVisibility(8);
      String var7 = UiOp.get_PackageVersionName(context);
      this.app_version_name.setText(var7);
      new ShowHiddenVIewByClick(this.unit, this.unit_back);
      this.unit_back.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            switch(var2) {
            case 2131231431:
               AppBase.display_unit = AppBase.UNITS.imperial;
               break;
            case 2131231432:
               AppBase.display_unit = AppBase.UNITS.metric;
            }

            AppBase.writePref(FragmentAppSettings.context);
            AppBase.notifyChangeDiveLogList();
         }
      });
      new ShowHiddenVIewByClick(this.language, this.language_back);
      this.language_back.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            switch(var2) {
            case 2131231116:
               AppBase.display_language = AppBase.LANGUAGES.Chinese;
               break;
            case 2131231117:
               AppBase.display_language = AppBase.LANGUAGES.English;
            }

            AppBase.writePref(FragmentAppSettings.context);
         }
      });
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         this.unit_metric.setChecked(true);
      } else {
         this.unit_imperial.setChecked(true);
      }

      if (AppBase.display_language == AppBase.LANGUAGES.Chinese) {
         this.language_Chinese.setChecked(true);
      } else {
         this.language_English.setChecked(true);
      }

      this.app_version.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentAppSettings.this.click_app_version(var1);
         }
      });
      this.close_app_version.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentAppSettings.this.click_close_app_version(var1);
         }
      });
      this.feedback.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentAppSettings.this.click_feedback(var1);
         }
      });
      this.terms.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentAppSettings.this.click_terms(var1);
         }
      });
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
}
