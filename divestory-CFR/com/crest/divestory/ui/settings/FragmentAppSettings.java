/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.RadioButton
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DbOp;
import com.crest.divestory.FragmentPage;
import com.syntak.library.FileOp;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import com.syntak.library.ui.ShowHiddenVIewByClick;

public class FragmentAppSettings
extends FragmentPage {
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

    static /* synthetic */ Context access$000() {
        return context;
    }

    public static FragmentAppSettings newInstance(int n) {
        FragmentAppSettings fragmentAppSettings = new FragmentAppSettings();
        fragmentAppSettings.page = n;
        return fragmentAppSettings;
    }

    public void click_app_version(View view) {
        this.screen_app_version.setVisibility(0);
    }

    public void click_backup(View object) {
        this.sign_processing.setVisibility(0);
        String string2 = AppBase.folder_app_external;
        object = AppBase.dbOp;
        string2 = FileOp.combinePath(string2, "divestory.db");
        FileOp.copyFile(AppBase.dbOp.dbPath, string2);
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(this.getResources().getString(2131689533));
        object = ((StringBuilder)object).toString();
        UiOp.toast_message(context, (String)object, false);
        this.sign_processing.setVisibility(8);
    }

    public void click_close_app_version(View view) {
        this.screen_app_version.setVisibility(8);
    }

    public void click_close_terms(View view) {
        this.screen_terms.setVisibility(8);
    }

    public void click_feedback(View object) {
        object = new StringBuilder();
        ((StringBuilder)object).append("mailto:info@EOPI.co?subject=APP_Feedback_");
        ((StringBuilder)object).append(TimeOp.MsToYearMonthDay(TimeOp.getNow()));
        String string2 = ((StringBuilder)object).toString();
        object = new Intent("android.intent.action.SENDTO");
        object.setData(Uri.parse((String)string2));
        this.startActivity((Intent)object);
    }

    public void click_terms(View view) {
        this.screen_terms.setVisibility(0);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        context = this.getContext();
        layoutInflater = layoutInflater.inflate(2131427390, object, false);
        this.unit = (TextView)layoutInflater.findViewById(2131231429);
        this.unit_back = (RadioGroup)layoutInflater.findViewById(2131231430);
        this.unit_metric = (RadioButton)layoutInflater.findViewById(2131231432);
        this.unit_imperial = (RadioButton)layoutInflater.findViewById(2131231431);
        this.language = (TextView)layoutInflater.findViewById(2131231115);
        this.language_back = (RadioGroup)layoutInflater.findViewById(2131231118);
        this.language_Chinese = (RadioButton)layoutInflater.findViewById(2131231116);
        this.language_English = (RadioButton)layoutInflater.findViewById(2131231117);
        this.app_version = (TextView)layoutInflater.findViewById(2131230814);
        this.feedback = (TextView)layoutInflater.findViewById(2131231016);
        this.terms = (TextView)layoutInflater.findViewById(2131231373);
        object = (LinearLayout)layoutInflater.findViewById(2131231303);
        this.sign_processing = object;
        object.setVisibility(8);
        object = (RelativeLayout)layoutInflater.findViewById(2131231272);
        this.screen_app_version = object;
        object.setVisibility(8);
        this.app_version_name = (TextView)layoutInflater.findViewById(2131230815);
        this.close_app_version = (Button)layoutInflater.findViewById(2131230926);
        object = (LinearLayout)layoutInflater.findViewById(2131231275);
        this.screen_terms = object;
        object.setVisibility(8);
        object = UiOp.get_PackageVersionName(context);
        this.app_version_name.setText((CharSequence)object);
        new ShowHiddenVIewByClick((View)this.unit, (View)this.unit_back);
        this.unit_back.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131231432: {
                        AppBase.display_unit = AppBase.UNITS.metric;
                        ** break;
                    }
                    case 2131231431: 
                }
                AppBase.display_unit = AppBase.UNITS.imperial;
lbl9: // 3 sources:
                AppBase.writePref(FragmentAppSettings.access$000());
                AppBase.notifyChangeDiveLogList();
            }
        });
        new ShowHiddenVIewByClick((View)this.language, (View)this.language_back);
        this.language_back.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131231117: {
                        AppBase.display_language = AppBase.LANGUAGES.English;
                        ** break;
                    }
                    case 2131231116: 
                }
                AppBase.display_language = AppBase.LANGUAGES.Chinese;
lbl9: // 3 sources:
                AppBase.writePref(FragmentAppSettings.access$000());
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
        this.app_version.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentAppSettings.this.click_app_version(view);
            }
        });
        this.close_app_version.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentAppSettings.this.click_close_app_version(view);
            }
        });
        this.feedback.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentAppSettings.this.click_feedback(view);
            }
        });
        this.terms.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentAppSettings.this.click_terms(view);
            }
        });
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

}

