/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.DatePicker
 *  android.widget.RadioButton
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 *  android.widget.Switch
 *  android.widget.TextView
 *  android.widget.TimePicker
 */
package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;

public class FragmentWatchSettingsBasic
extends FragmentPage {
    private static Activity activity;
    private static Context context;
    TextView date;
    TextView time;

    public static FragmentWatchSettingsBasic newInstance(int n) {
        FragmentWatchSettingsBasic fragmentWatchSettingsBasic = new FragmentWatchSettingsBasic();
        fragmentWatchSettingsBasic.page = n;
        return fragmentWatchSettingsBasic;
    }

    public void date_picker(final View view) {
        int n = WatchOp.watchSetting_gb.year;
        int n2 = WatchOp.watchSetting_gb.month;
        int n3 = WatchOp.watchSetting_gb.day;
        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker object, int n, int n2, int n3) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.valueOf(n));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(++n2));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(n3));
                object = ((StringBuilder)object).toString();
                ((TextView)view).setText((CharSequence)object);
                WatchOp.watchSetting_gb.year = n;
                WatchOp.watchSetting_gb.month = n2;
                WatchOp.watchSetting_gb.day = n3;
                byte by = (byte)(n - 2000);
                byte by2 = (byte)n2;
                byte by3 = (byte)n3;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)1, new byte[]{by, by2, by3}, 3);
            }
        }, n, n2 - 1, n3).show();
    }

    public void init_UTC_offset(View view) {
        final TextView textView = (TextView)view.findViewById(2131230742);
        Button button = (Button)view.findViewById(2131230870);
        view = (Button)view.findViewById(2131230883);
        textView.setText((CharSequence)String.format("%1$+02d", WatchOp.watchSetting_gb.UTC_offset - 12));
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                int n = WatchOp.watchSetting_gb.UTC_offset - 12;
                if (n <= -12) return;
                object = WatchOp.watchSetting_gb;
                --object.UTC_offset;
                textView.setText((CharSequence)String.format("%1$+02d", n - 1));
                byte by = (byte)WatchOp.watchSetting_gb.UTC_offset;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)31, new byte[]{by}, 1);
            }
        });
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                int n = WatchOp.watchSetting_gb.UTC_offset - 12;
                if (n >= 12) return;
                object = WatchOp.watchSetting_gb;
                ++object.UTC_offset;
                textView.setText((CharSequence)String.format("%1$+02d", n + 1));
                byte by = (byte)WatchOp.watchSetting_gb.UTC_offset;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)31, new byte[]{by}, 1);
            }
        });
    }

    public void init_auto_run_for(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230988);
        RadioButton radioButton = (RadioButton)view.findViewById(2131230985);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131230986);
        view = (RadioButton)view.findViewById(2131230987);
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
                    case 2131230987: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131230986: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131230985: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl13: // 4 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)5, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.auto_dive_type;
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

    public void init_backlight(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230828);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231123);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131231124);
        RadioButton radioButton3 = (RadioButton)view.findViewById(2131231125);
        RadioButton radioButton4 = (RadioButton)view.findViewById(2131231126);
        view = (RadioButton)view.findViewById(2131231127);
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
                    case 2131231127: {
                        var1_1[0] = (byte)4;
                        ** break;
                    }
                    case 2131231126: {
                        var1_1[0] = (byte)3;
                        ** break;
                    }
                    case 2131231125: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131231124: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131231123: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl19: // 6 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)33, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.backlight;
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

    public void init_buzzer(View view) {
        view = (Switch)view.findViewById(2131230898);
        boolean bl = WatchOp.watchSetting_gb.buzzer == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.buzzer = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.buzzer;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)32, new byte[]{by}, 1);
            }
        });
    }

    public void init_data_format(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230950);
        RadioButton radioButton = (RadioButton)view.findViewById(2131230947);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131230948);
        view = (RadioButton)view.findViewById(2131230949);
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
                    case 2131230949: {
                        var1_1[0] = (byte)2;
                        FragmentWatchSettingsBasic.this.show_date(DATE_FORMAT.DDMMYY);
                        ** break;
                    }
                    case 2131230948: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        FragmentWatchSettingsBasic.this.show_date(DATE_FORMAT.MMDDYY);
                        ** break;
                    }
                    case 2131230947: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
                FragmentWatchSettingsBasic.this.show_date(DATE_FORMAT.YYMMDD);
lbl16: // 4 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)2, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.date_format;
        if (n == 0) {
            radioButton.toggle();
            this.show_date(DATE_FORMAT.YYMMDD);
            return;
        }
        if (n == 1) {
            radioButton2.toggle();
            this.show_date(DATE_FORMAT.MMDDYY);
            return;
        }
        if (n != 2) {
            return;
        }
        view.toggle();
        this.show_date(DATE_FORMAT.DDMMYY);
    }

    public void init_date(View view) {
        view = (TextView)view.findViewById(2131230944);
        this.date = view;
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentWatchSettingsBasic.this.date_picker(view);
            }
        });
    }

    public void init_display_unit(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230979);
        RadioButton radioButton = (RadioButton)view.findViewById(2131230977);
        view = (RadioButton)view.findViewById(2131230978);
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
                    case 2131230978: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131230977: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl10: // 3 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)29, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.display_unit;
        if (n == 0) {
            radioButton.toggle();
            return;
        }
        if (n != 1) {
            return;
        }
        view.toggle();
    }

    public void init_power_saving(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131230806);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231152);
        RadioButton radioButton2 = (RadioButton)view.findViewById(2131231153);
        view = (RadioButton)view.findViewById(2131231154);
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
                    case 2131231154: {
                        var1_1[0] = (byte)2;
                        ** break;
                    }
                    case 2131231153: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        ** break;
                    }
                    case 2131231152: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
lbl13: // 4 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)30, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.power_saving;
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

    public void init_time(View view) {
        view = (TextView)view.findViewById(2131231403);
        this.time = view;
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FragmentWatchSettingsBasic.this.time_picker(view);
            }
        });
    }

    public void init_time_format(View view) {
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131231408);
        RadioButton radioButton = (RadioButton)view.findViewById(2131231406);
        view = (RadioButton)view.findViewById(2131231407);
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
                    case 2131231407: {
                        var1_1[0] = (byte)(true ? 1 : 0);
                        FragmentWatchSettingsBasic.this.show_time(TIME_FORMAT.H24);
                        ** break;
                    }
                    case 2131231406: 
                }
                var1_1[0] = (byte)(false ? 1 : 0);
                FragmentWatchSettingsBasic.this.show_time(TIME_FORMAT.H12);
lbl12: // 3 sources:
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)4, var1_1, 1);
            }
        });
        int n = WatchOp.watchSetting_gb.time_format;
        if (n == 0) {
            radioButton.toggle();
            this.show_time(TIME_FORMAT.H12);
            return;
        }
        if (n != 1) {
            return;
        }
        view.toggle();
        this.show_time(TIME_FORMAT.H24);
    }

    public void init_vibrator(View view) {
        view = (Switch)view.findViewById(2131231438);
        boolean bl = WatchOp.watchSetting_gb.vibrator == 0;
        view.setChecked(bl);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                WatchOp.watchSetting_gb.vibrator = bl ^ true;
                byte by = (byte)WatchOp.watchSetting_gb.vibrator;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)34, new byte[]{by}, 1);
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
        layoutInflater = layoutInflater.inflate(2131427399, viewGroup, false);
        this.init_date((View)layoutInflater);
        this.init_data_format((View)layoutInflater);
        this.init_time((View)layoutInflater);
        this.init_time_format((View)layoutInflater);
        this.init_auto_run_for((View)layoutInflater);
        this.init_display_unit((View)layoutInflater);
        this.init_power_saving((View)layoutInflater);
        this.init_UTC_offset((View)layoutInflater);
        this.init_buzzer((View)layoutInflater);
        this.init_backlight((View)layoutInflater);
        this.init_vibrator((View)layoutInflater);
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

    public void show_date(DATE_FORMAT dATE_FORMAT) {
        int n = 15.$SwitchMap$com$crest$divestory$ui$watches$FragmentWatchSettingsBasic$DATE_FORMAT[dATE_FORMAT.ordinal()];
        if (n == 1) {
            this.date.setText((CharSequence)String.format(AppBase.DATE_STRING_FORMAT_YYYYMMDD, WatchOp.watchSetting_gb.year, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.day));
            return;
        }
        if (n == 2) {
            this.date.setText((CharSequence)String.format(AppBase.DATE_STRING_FORMAT_MMDDYYYY, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.day, WatchOp.watchSetting_gb.year));
            return;
        }
        if (n != 3) {
            return;
        }
        this.date.setText((CharSequence)String.format(AppBase.DATE_STRING_FORMAT_MMDDYYYY, WatchOp.watchSetting_gb.day, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.year));
    }

    public void show_time(TIME_FORMAT object) {
        int n = 15.$SwitchMap$com$crest$divestory$ui$watches$FragmentWatchSettingsBasic$TIME_FORMAT[((Enum)object).ordinal()];
        if (n != 1) {
            if (n != 2) {
                return;
            }
            this.time.setText((CharSequence)String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour, WatchOp.watchSetting_gb.minute));
            return;
        }
        if (WatchOp.watchSetting_gb.hour < 12) {
            object = this.time;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("A");
            stringBuilder.append(String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour, WatchOp.watchSetting_gb.minute));
            object.setText((CharSequence)stringBuilder.toString());
            return;
        }
        TextView textView = this.time;
        object = new StringBuilder();
        ((StringBuilder)object).append("P");
        ((StringBuilder)object).append(String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour - 12, WatchOp.watchSetting_gb.minute));
        textView.setText((CharSequence)((StringBuilder)object).toString());
    }

    public void time_picker(final View view) {
        int n = WatchOp.watchSetting_gb.hour;
        int n2 = WatchOp.watchSetting_gb.minute;
        new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener(){

            public void onTimeSet(TimePicker object, int n, int n2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.valueOf(n));
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(String.valueOf(n2));
                object = ((StringBuilder)object).toString();
                ((TextView)view).setText((CharSequence)object);
                WatchOp.watchSetting_gb.hour = n;
                WatchOp.watchSetting_gb.minute = n2;
                byte by = (byte)n;
                byte by2 = (byte)n2;
                WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)3, new byte[]{by, by2, 0}, 3);
            }
        }, n, n2, true).show();
    }

    static final class DATE_FORMAT
    extends Enum<DATE_FORMAT> {
        private static final /* synthetic */ DATE_FORMAT[] $VALUES;
        public static final /* enum */ DATE_FORMAT DDMMYY;
        public static final /* enum */ DATE_FORMAT MMDDYY;
        public static final /* enum */ DATE_FORMAT YYMMDD;

        static {
            DATE_FORMAT dATE_FORMAT;
            YYMMDD = new DATE_FORMAT();
            MMDDYY = new DATE_FORMAT();
            DDMMYY = dATE_FORMAT = new DATE_FORMAT();
            $VALUES = new DATE_FORMAT[]{YYMMDD, MMDDYY, dATE_FORMAT};
        }

        public static DATE_FORMAT valueOf(String string2) {
            return Enum.valueOf(DATE_FORMAT.class, string2);
        }

        public static DATE_FORMAT[] values() {
            return (DATE_FORMAT[])$VALUES.clone();
        }
    }

    static final class TIME_FORMAT
    extends Enum<TIME_FORMAT> {
        private static final /* synthetic */ TIME_FORMAT[] $VALUES;
        public static final /* enum */ TIME_FORMAT H12;
        public static final /* enum */ TIME_FORMAT H24;

        static {
            TIME_FORMAT tIME_FORMAT;
            H12 = new TIME_FORMAT();
            H24 = tIME_FORMAT = new TIME_FORMAT();
            $VALUES = new TIME_FORMAT[]{H12, tIME_FORMAT};
        }

        public static TIME_FORMAT valueOf(String string2) {
            return Enum.valueOf(TIME_FORMAT.class, string2);
        }

        public static TIME_FORMAT[] values() {
            return (TIME_FORMAT[])$VALUES.clone();
        }
    }

}

