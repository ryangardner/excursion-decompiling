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
import android.widget.Switch;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;
import com.syntak.library.ByteOp;
import java.nio.ByteOrder;

public class FragmentWatchSettingsFreeDive
extends FragmentPage {
    private static Activity activity;
    private static Context context;

    public static FragmentWatchSettingsFreeDive newInstance(int n) {
        FragmentWatchSettingsFreeDive fragmentWatchSettingsFreeDive = new FragmentWatchSettingsFreeDive();
        fragmentWatchSettingsFreeDive.page = n;
        return fragmentWatchSettingsFreeDive;
    }

    public void init_free_dive_depth_alarm(View view) {
        view = (Switch)view.findViewById(2131231030);
        boolean bl = WatchOp.watchSetting_gb.free_dive_depth_alarm == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.free_dive_depth_alarm = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)18, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_depth_alarm_threshold_1(View view) {
        final TextView textView = (TextView)view.findViewById(2131231031);
        Button button = (Button)view.findViewById(2131230872);
        view = (Button)view.findViewById(2131230885);
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 < 0) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 > 99) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = 99;
        }
        textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.free_dive_depth_alarm_threshold_1;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)23, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 >= 99) return;
                object = WatchOp.watchSetting_gb;
                ++object.free_dive_depth_alarm_threshold_1;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)23, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_depth_alarm_threshold_2(View view) {
        final TextView textView = (TextView)view.findViewById(2131231032);
        Button button = (Button)view.findViewById(2131230873);
        view = (Button)view.findViewById(2131230886);
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 < 0) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 > 99) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = 99;
        }
        textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.free_dive_depth_alarm_threshold_2;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)24, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 >= 99) return;
                object = WatchOp.watchSetting_gb;
                ++object.free_dive_depth_alarm_threshold_2;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)24, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_depth_alarm_threshold_3(View view) {
        final TextView textView = (TextView)view.findViewById(2131231033);
        Button button = (Button)view.findViewById(2131230874);
        view = (Button)view.findViewById(2131230887);
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 < 0) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 > 99) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = 99;
        }
        textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.free_dive_depth_alarm_threshold_3;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)25, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 >= 99) return;
                object = WatchOp.watchSetting_gb;
                ++object.free_dive_depth_alarm_threshold_3;
                textView.setText((CharSequence)AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)25, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_surface_time_alarm(View view) {
        view = (Switch)view.findViewById(2131231034);
        boolean bl = WatchOp.watchSetting_gb.free_dive_surface_time_alarm == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.free_dive_surface_time_alarm = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)19, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_surface_time_alarm_threshold_1(View view) {
        final TextView textView = (TextView)view.findViewById(2131231035);
        Button button = (Button)view.findViewById(2131230875);
        view = (Button)view.findViewById(2131230888);
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 < 0) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 > 60) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = 60;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
        stringBuilder.append(" min");
        textView.setText((CharSequence)stringBuilder.toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --((DataStruct.WatchSetting_GB)object).free_dive_surface_time_alarm_threshold_1;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
                ((StringBuilder)object).append(" min");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)26, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 >= 60) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).free_dive_surface_time_alarm_threshold_1;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
                ((StringBuilder)object).append(" min");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)26, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_surface_time_alarm_threshold_2(View view) {
        final TextView textView = (TextView)view.findViewById(2131231036);
        Button button = (Button)view.findViewById(2131230876);
        view = (Button)view.findViewById(2131230889);
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 < 0) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 > 60) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = 60;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
        stringBuilder.append(" min");
        textView.setText((CharSequence)stringBuilder.toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --((DataStruct.WatchSetting_GB)object).free_dive_surface_time_alarm_threshold_2;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
                ((StringBuilder)object).append(" min");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)27, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 >= 60) return;
                object = WatchOp.watchSetting_gb;
                ++object.free_dive_surface_time_alarm_threshold_2;
                object = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
                stringBuilder.append(" min");
                object.setText((CharSequence)stringBuilder.toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)27, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_surface_time_alarm_threshold_3(View object) {
        final TextView textView = (TextView)object.findViewById(2131231037);
        Button button = (Button)object.findViewById(2131230877);
        Button button2 = (Button)object.findViewById(2131230890);
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 < 0) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 > 60) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = 60;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
        ((StringBuilder)object).append(" min");
        textView.setText((CharSequence)((StringBuilder)object).toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --object.free_dive_surface_time_alarm_threshold_3;
                object = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
                stringBuilder.append(" min");
                object.setText((CharSequence)stringBuilder.toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)28, new byte[]{by}, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 >= 60) return;
                object = WatchOp.watchSetting_gb;
                ++object.free_dive_surface_time_alarm_threshold_3;
                object = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
                stringBuilder.append(" min");
                object.setText((CharSequence)stringBuilder.toString());
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)28, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_time_alarm(View view) {
        view = (Switch)view.findViewById(2131231038);
        boolean bl = WatchOp.watchSetting_gb.free_dive_time_alarm == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.free_dive_time_alarm = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.free_dive_time_alarm;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)17, new byte[]{by}, 1);
            }
        });
    }

    public void init_free_dive_time_alarm_threshold_1(View object) {
        final TextView textView = (TextView)object.findViewById(2131231039);
        Button button = (Button)object.findViewById(2131230878);
        Button button2 = (Button)object.findViewById(2131230891);
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 < 0) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 > 360) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = 360;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
        ((StringBuilder)object).append(" sec");
        textView.setText((CharSequence)((StringBuilder)object).toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View arrby) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 <= 0) return;
                arrby = WatchOp.watchSetting_gb;
                --arrby.free_dive_time_alarm_threshold_1;
                arrby = textView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
                stringBuilder.append(" sec");
                arrby.setText((CharSequence)stringBuilder.toString());
                arrby = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)20, arrby, arrby.length);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 >= 360) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).free_dive_time_alarm_threshold_1;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
                ((StringBuilder)object).append(" sec");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                object = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)20, (byte[])object, ((Object)object).length);
            }
        });
    }

    public void init_free_dive_time_alarm_threshold_2(View view) {
        final TextView textView = (TextView)view.findViewById(2131231040);
        Button button = (Button)view.findViewById(2131230879);
        view = (Button)view.findViewById(2131230892);
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 < 0) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 > 360) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = 360;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
        stringBuilder.append(" sec");
        textView.setText((CharSequence)stringBuilder.toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --((DataStruct.WatchSetting_GB)object).free_dive_time_alarm_threshold_2;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
                ((StringBuilder)object).append(" sec");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                object = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)21, (byte[])object, ((Object)object).length);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 >= 360) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).free_dive_time_alarm_threshold_2;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
                ((StringBuilder)object).append(" sec");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                object = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)21, (byte[])object, ((Object)object).length);
            }
        });
    }

    public void init_free_dive_time_alarm_threshold_3(View object) {
        final TextView textView = (TextView)object.findViewById(2131231041);
        Button button = (Button)object.findViewById(2131230880);
        Button button2 = (Button)object.findViewById(2131230893);
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 < 0) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = 0;
        }
        if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 > 360) {
            WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = 360;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
        ((StringBuilder)object).append(" sec");
        textView.setText((CharSequence)((StringBuilder)object).toString());
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 <= 0) return;
                object = WatchOp.watchSetting_gb;
                --((DataStruct.WatchSetting_GB)object).free_dive_time_alarm_threshold_3;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
                ((StringBuilder)object).append(" sec");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                object = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)22, (byte[])object, ((Object)object).length);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 >= 360) return;
                object = WatchOp.watchSetting_gb;
                ++((DataStruct.WatchSetting_GB)object).free_dive_time_alarm_threshold_3;
                TextView textView2 = textView;
                object = new StringBuilder();
                ((StringBuilder)object).append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
                ((StringBuilder)object).append(" sec");
                textView2.setText((CharSequence)((StringBuilder)object).toString());
                object = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3, WatchOp.byteOrder);
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)22, (byte[])object, ((Object)object).length);
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
        layoutInflater = layoutInflater.inflate(2131427400, viewGroup, false);
        this.init_free_dive_time_alarm((View)layoutInflater);
        this.init_free_dive_depth_alarm((View)layoutInflater);
        this.init_free_dive_surface_time_alarm((View)layoutInflater);
        this.init_free_dive_time_alarm_threshold_1((View)layoutInflater);
        this.init_free_dive_time_alarm_threshold_2((View)layoutInflater);
        this.init_free_dive_time_alarm_threshold_3((View)layoutInflater);
        this.init_free_dive_depth_alarm_threshold_1((View)layoutInflater);
        this.init_free_dive_depth_alarm_threshold_2((View)layoutInflater);
        this.init_free_dive_depth_alarm_threshold_3((View)layoutInflater);
        this.init_free_dive_surface_time_alarm_threshold_1((View)layoutInflater);
        this.init_free_dive_surface_time_alarm_threshold_2((View)layoutInflater);
        this.init_free_dive_surface_time_alarm_threshold_3((View)layoutInflater);
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

