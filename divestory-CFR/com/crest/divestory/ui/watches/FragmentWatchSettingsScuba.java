/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.RadioButton
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 *  android.widget.Switch
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;

public class FragmentWatchSettingsScuba
extends FragmentPage {
    private static Activity activity;
    private static Context context;

    public static FragmentWatchSettingsScuba newInstance(int n) {
        FragmentWatchSettingsScuba fragmentWatchSettingsScuba = new FragmentWatchSettingsScuba();
        fragmentWatchSettingsScuba.page = n;
        return fragmentWatchSettingsScuba;
    }

    public void init_G_sensor(View view) {
        view = (Switch)view.findViewById(2131230725);
        boolean bl = WatchOp.watchSetting_gb.G_sensor == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.G_sensor = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.G_sensor;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)16, new byte[]{by}, 1);
            }
        });
    }

    public void init_O2_ratio(View object) {
        final TextView textView = (TextView)object.findViewById(2131230728);
        Button button = (Button)object.findViewById(2131230871);
        Button button2 = (Button)object.findViewById(2131230884);
        if (WatchOp.watchSetting_gb.O2_ratio < 21) {
            WatchOp.watchSetting_gb.O2_ratio = 21;
        }
        if (WatchOp.watchSetting_gb.O2_ratio > 56) {
            WatchOp.watchSetting_gb.O2_ratio = 56;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(WatchOp.watchSetting_gb.O2_ratio);
        ((StringBuilder)object).append("%");
        textView.setText((CharSequence)((StringBuilder)object).toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.O2_ratio <= 21) return;
                object = WatchOp.watchSetting_gb;
                --object.O2_ratio;
                object = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.O2_ratio);
                stringBuilder.append("%");
                object.setText((CharSequence)stringBuilder.toString());
                byte by = (byte)WatchOp.watchSetting_gb.O2_ratio;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)6, new byte[]{by}, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.O2_ratio >= 56) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).O2_ratio;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.O2_ratio);
                ((StringBuilder)object).append("%");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                byte by = (byte)WatchOp.watchSetting_gb.O2_ratio;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)6, new byte[]{by}, 1);
            }
        });
    }

    public void init_PPO2(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230734);
        RadioButton radioButton = (RadioButton)view.findViewById(2131230729);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131230730);
        RadioButton radioButton3 = (RadioButton)view.findViewById(2131230731);
        RadioButton radioButton4 = (RadioButton)view.findViewById(2131230732);
        view = (RadioButton)view.findViewById(2131230733);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                var1_1 = new byte[1];
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131230733: {
                        var1_1[0] = (byte)4;
                        ** break;
                    }
                    case 2131230732: {
                        var1_1[0] = (byte)3;
                        ** break;
                    }
                    case 2131230731: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131230730: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131230729: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl19: // 6 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)7, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.PPO2;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            return;
        }
        if (n == 2) {
            radioButton3.toggle();
            return;
        }
        if (n == 3) {
            radioButton4.toggle();
            return;
        }
        if (n != 4) {
            return;
        }
        view.toggle();
    }

    public void init_log_sampling_rate(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131231138);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231262);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131231263);
        RadioButton radioButton3 = (RadioButton)view.findViewById(2131231264);
        view = (RadioButton)view.findViewById(2131231265);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                var1_1 = new byte[1];
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131231265: {
                        var1_1[0] = (byte)3;
                        ** break;
                    }
                    case 2131231264: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131231263: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131231262: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl16: // 5 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)13, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.scuba_dive_log_sampling_rate;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            return;
        }
        if (n == 2) {
            radioButton3.toggle();
            return;
        }
        if (n != 3) {
            return;
        }
        view.toggle();
    }

    public void init_log_start_depth(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131231139);
        RadioButton radioButton = (RadioButton)view.findViewById(2131230960);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131230961);
        RadioButton radioButton3 = (RadioButton)view.findViewById(2131230962);
        RadioButton radioButton4 = (RadioButton)view.findViewById(2131230963);
        view = (RadioButton)view.findViewById(2131230964);
        if (AppBase.display_unit == AppBase.UNITS.imperial) {
            radioButton.setText((CharSequence)"3.3 ft");
            radioButton2.setText((CharSequence)"4.9 ft");
            radioButton3.setText((CharSequence)"6.5 ft");
            radioButton4.setText((CharSequence)"8.2 ft");
            view.setText((CharSequence)"9.8 ft");
        }
        if (WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
            radioButton.setVisibility(8);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                var1_1 = new byte[1];
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131230964: {
                        var1_1[0] = (byte)4;
                        ** break;
                    }
                    case 2131230963: {
                        var1_1[0] = (byte)3;
                        ** break;
                    }
                    case 2131230962: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131230961: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131230960: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl19: // 6 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)14, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.scuba_dive_log_start_depth;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            return;
        }
        if (n == 2) {
            radioButton3.toggle();
            return;
        }
        if (n == 3) {
            radioButton4.toggle();
            return;
        }
        if (n != 4) {
            return;
        }
        view.toggle();
    }

    public void init_log_stop_time(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131231140);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231336);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131231337);
        RadioButton radioButton3 = (RadioButton)view.findViewById(2131231338);
        view = (RadioButton)view.findViewById(2131231339);
        if (WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
            view.setVisibility(8);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                var1_1 = new byte[1];
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131231339: {
                        var1_1[0] = (byte)3;
                        ** break;
                    }
                    case 2131231338: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131231337: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131231336: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl16: // 5 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)15, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.scuba_dive_log_stop_time;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            return;
        }
        if (n == 2) {
            radioButton3.toggle();
            return;
        }
        if (n != 3) {
            return;
        }
        view.toggle();
    }

    public void init_safety_factor(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131231261);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231258);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131231259);
        view = (RadioButton)view.findViewById(2131231260);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            /*
             * Unable to fully structure code
             */
            public void onCheckedChanged(RadioGroup var1_1, int var2_2) {
                var1_1 = new byte[1];
                switch (var2_2) {
                    default: {
                        ** break;
                    }
                    case 2131231260: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131231259: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131231258: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl13: // 4 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)8, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.safety_factor;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            return;
        }
        if (n != 2) {
            return;
        }
        view.toggle();
    }

    public void init_scuba_depth_alarm(View view) {
        view = (Switch)view.findViewById(2131231281);
        boolean bl = WatchOp.watchSetting_gb.scuba_dive_depth_alarm == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.scuba_dive_depth_alarm = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)9, new byte[]{by}, 1);
            }
        });
    }

    public void init_scuba_depth_alarm_threshold(View view) {
        final TextView textView = (TextView)view.findViewById(2131231282);
        Button button = (Button)view.findViewById(2131230881);
        view = (Button)view.findViewById(2131230894);
        if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold < 0) {
            WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = 0;
        }
        if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold > 99) {
            WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = 99;
        }
        textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.scuba_dive_depth_alarm_threshold;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)11, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold >= 99) return;
                object = WatchOp.watchSetting_gb;
                ++object.scuba_dive_depth_alarm_threshold;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)11, new byte[]{by}, 1);
            }
        });
    }

    public void init_scuba_time_alarm(View view) {
        view = (Switch)view.findViewById(2131231283);
        boolean bl = WatchOp.watchSetting_gb.scuba_dive_time_alarm == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.scuba_dive_time_alarm = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)10, new byte[]{by}, 1);
            }
        });
    }

    public void init_scuba_time_alarm_threshold(View object) {
        final TextView textView = (TextView)object.findViewById(2131231284);
        Button button = (Button)object.findViewById(2131230882);
        Button button2 = (Button)object.findViewById(2131230895);
        if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold < 0) {
            WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = 0;
        }
        if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold > 90) {
            WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = 90;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
        ((StringBuilder)object).append(" min");
        textView.setText((CharSequence)((StringBuilder)object).toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.scuba_dive_time_alarm_threshold;
                object = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
                stringBuilder.append(" min");
                object.setText((CharSequence)stringBuilder.toString());
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)12, new byte[]{by}, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold >= 90) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).scuba_dive_time_alarm_threshold;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
                ((StringBuilder)object).append(" min");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                byte by = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)12, new byte[]{by}, 1);
            }
        });
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        context = this.getContext();
        layoutInflater = layoutInflater.inflate(2131427401, viewGroup, false);
        this.init_O2_ratio((View)layoutInflater);
        this.init_PPO2((View)layoutInflater);
        this.init_safety_factor((View)layoutInflater);
        this.init_scuba_depth_alarm((View)layoutInflater);
        this.init_scuba_time_alarm((View)layoutInflater);
        this.init_scuba_depth_alarm_threshold((View)layoutInflater);
        this.init_scuba_time_alarm_threshold((View)layoutInflater);
        this.init_log_sampling_rate((View)layoutInflater);
        this.init_log_start_depth((View)layoutInflater);
        this.init_log_stop_time((View)layoutInflater);
        this.init_G_sensor((View)layoutInflater);
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

