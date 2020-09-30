/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.content.Context
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.DatePicker
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.logs.AdapterDiveLogsList;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import java.io.Serializable;
import java.util.ArrayList;

public class FragmentDiveLogsList
extends Fragment {
    private static final String ARG_FUTURE_ACTION = "future_action";
    static Context context;
    boolean anyFilter = false;
    TextView button_date_end;
    TextView button_date_start;
    Button cancel_filter;
    Button clear_filter;
    TextView date_end;
    TextView date_start;
    WatchOp.ACTION future_action;
    boolean isFilterChanged = false;
    private ListView lv;
    private int mColumnCount = 1;
    private OnDiveLogsFragmentInteractionListener mListener;
    CheckBox radioDateRange;
    CheckBox radioFavorite;
    CheckBox radioFreeDive;
    CheckBox radioGaugeDive;
    CheckBox radioScubaDive;
    RecyclerView recyclerView;
    RelativeLayout screen_filter;
    RelativeLayout screen_list;
    Button start_filter;
    private TextView warning;

    private void do_filter() {
        if (WatchOp.dive_logs.length() <= 0) {
            return;
        }
        boolean bl = this.isAnyFilter();
        ArrayList<DataStruct.DiveLog> arrayList = new ArrayList<DataStruct.DiveLog>();
        int n = 0;
        do {
            if (n >= WatchOp.dive_logs.length()) {
                WatchOp.dive_logs_list = arrayList;
                AppBase.notifyChangeDiveLogList();
                return;
            }
            DataStruct.DiveLog diveLog = WatchOp.dive_logs.get(n);
            if (!bl || this.isConformed(diveLog)) {
                arrayList.add(diveLog);
            }
            ++n;
        } while (true);
    }

    private void hide_filter_screen() {
        this.screen_filter.setVisibility(8);
        this.screen_list.setVisibility(0);
        RelativeLayout relativeLayout = this.screen_filter;
        relativeLayout.setTranslationY((float)(-relativeLayout.getHeight()));
    }

    private void init_date_filter() {
        long l = TimeOp.getNow();
        int n = TimeOp.getYear(l);
        int n2 = TimeOp.getMonth(l);
        int n3 = TimeOp.getDay(l);
        WatchOp.filterDiveLog.date_start = TimeOp.DateTimeToMs(n, n2, n3, 0, 0);
        WatchOp.filterDiveLog.date_end = TimeOp.DateTimeToMs(n, n2, n3, 23, 59);
        String string2 = TimeOp.MsToYearMonthDay(l);
        this.date_start.setText((CharSequence)string2);
        this.date_end.setText((CharSequence)string2);
    }

    private static void init_dive_logs() {
        if (WatchOp.dive_logs != null) {
            WatchOp.dive_logs = null;
        }
        AppBase.open_db(context);
        WatchOp.dive_logs = new DataStruct.DiveLogs(AppBase.dbOp.getDiveLogs());
        WatchOp.dive_logs_list = WatchOp.dive_logs.duplicateDiveLogsList();
    }

    private boolean isAnyFilter() {
        DataStruct.FilterDiveLog filterDiveLog = WatchOp.filterDiveLog;
        boolean bl = false;
        if (filterDiveLog == null) {
            return false;
        }
        WatchOp.filterDiveLog.date_range = this.radioDateRange.isChecked();
        WatchOp.filterDiveLog.scuba_dive = this.radioScubaDive.isChecked();
        WatchOp.filterDiveLog.free_dive = this.radioFreeDive.isChecked();
        WatchOp.filterDiveLog.gauge_dive = this.radioGaugeDive.isChecked();
        WatchOp.filterDiveLog.favorite = this.radioFavorite.isChecked();
        if (WatchOp.filterDiveLog.date_range) return true;
        if (WatchOp.filterDiveLog.scuba_dive) return true;
        if (WatchOp.filterDiveLog.free_dive) return true;
        if (WatchOp.filterDiveLog.gauge_dive) return true;
        if (!WatchOp.filterDiveLog.favorite) return bl;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private boolean isConformed(DataStruct.DiveLog var1_1) {
        var2_2 = WatchOp.filterDiveLog.date_range;
        var3_3 = false;
        var4_4 = !var2_2 || var1_1.start_time >= WatchOp.filterDiveLog.date_start && var1_1.start_time <= WatchOp.filterDiveLog.date_end;
        var5_5 = WatchOp.filterDiveLog.scuba_dive || WatchOp.filterDiveLog.free_dive || WatchOp.filterDiveLog.gauge_dive;
        if (!var5_5) ** GOTO lbl-1000
        var5_5 = WatchOp.filterDiveLog.scuba_dive != false && var1_1.dive_type == 0L;
        var6_6 = WatchOp.filterDiveLog.free_dive != false && var1_1.dive_type == 2L;
        var7_7 = WatchOp.filterDiveLog.gauge_dive != false && var1_1.dive_type == 1L;
        if (!(var5_5 || var6_6 || var7_7)) {
            var5_5 = false;
        } else lbl-1000: // 2 sources:
        {
            var5_5 = true;
        }
        var8_8 = WatchOp.filterDiveLog.favorite != false ? var1_1.isFavorite : true;
        var2_2 = var3_3;
        if (var4_4 == false) return var2_2;
        var2_2 = var3_3;
        if (var5_5 == false) return var2_2;
        var2_2 = var3_3;
        if (var8_8 == false) return var2_2;
        return true;
    }

    public static FragmentDiveLogsList newInstance(WatchOp.ACTION aCTION) {
        FragmentDiveLogsList fragmentDiveLogsList = new FragmentDiveLogsList();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_FUTURE_ACTION, (Serializable)((Object)aCTION));
        fragmentDiveLogsList.setArguments(bundle);
        return fragmentDiveLogsList;
    }

    public void cancel_filter_screen() {
        UiOp.viewAnimation((View)this.screen_filter, UiOp.TRANSLATION_AXIS.Y, -this.screen_filter.getHeight(), 500, UiOp.INTERPOLATION_TYPE.ACCELARATION, false);
        this.screen_list.setVisibility(0);
    }

    public void clear_filter() {
        WatchOp.filterDiveLog.clear();
        this.init_date_filter();
        this.radioDateRange.setChecked(false);
        this.radioScubaDive.setChecked(false);
        this.radioFreeDive.setChecked(false);
        this.radioGaugeDive.setChecked(false);
        this.radioFavorite.setChecked(false);
        this.do_filter();
    }

    public void date_picker_end(View view) {
        long l;
        long l2 = l = WatchOp.filterDiveLog.date_end;
        if (l == 0L) {
            l2 = TimeOp.getNow();
        }
        int n = TimeOp.getYear(l2);
        int n2 = TimeOp.getMonth(l2);
        int n3 = TimeOp.getDay(l2);
        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker object, int n, int n2, int n3) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.valueOf(n));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(n2 + 1));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(n3));
                object = ((StringBuilder)object).toString();
                FragmentDiveLogsList.this.date_end.setText((CharSequence)object);
                WatchOp.filterDiveLog.date_end = TimeOp.DateTimeToMs(n, n2, n3, 23, 59);
            }
        }, n, n2, n3).show();
    }

    public void date_picker_start(View view) {
        long l;
        long l2 = l = WatchOp.filterDiveLog.date_start;
        if (l == 0L) {
            l2 = TimeOp.getNow();
        }
        int n = TimeOp.getYear(l2);
        int n2 = TimeOp.getMonth(l2);
        int n3 = TimeOp.getDay(l2);
        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker object, int n, int n2, int n3) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.valueOf(n));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(n2 + 1));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.valueOf(n3));
                object = ((StringBuilder)object).toString();
                FragmentDiveLogsList.this.date_start.setText((CharSequence)object);
                WatchOp.filterDiveLog.date_start = TimeOp.DateTimeToMs(n, n2, n3, 0, 0);
            }
        }, n, n2, n3).show();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() != null) {
            this.future_action = (WatchOp.ACTION)((Object)this.getArguments().getSerializable(ARG_FUTURE_ACTION));
        }
        context = this.getContext();
        FragmentDiveLogsList.init_dive_logs();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427394, viewGroup, false);
        this.warning = (TextView)layoutInflater.findViewById(2131231445);
        this.lv = (ListView)layoutInflater.findViewById(2131231133);
        if (WatchOp.dive_logs_list != null && WatchOp.dive_logs_list.size() > 0) {
            this.warning.setText((CharSequence)"");
            AppBase.adapterDiveLogsList = new AdapterDiveLogsList(context);
            this.lv.setAdapter((ListAdapter)AppBase.adapterDiveLogsList);
        } else {
            this.warning.setText(2131689746);
        }
        this.screen_list = (RelativeLayout)layoutInflater.findViewById(2131231274);
        this.screen_filter = (RelativeLayout)layoutInflater.findViewById(2131231273);
        this.hide_filter_screen();
        this.button_date_start = (TextView)layoutInflater.findViewById(2131230868);
        this.button_date_end = (TextView)layoutInflater.findViewById(2131230867);
        this.date_start = (TextView)layoutInflater.findViewById(2131230952);
        this.date_end = (TextView)layoutInflater.findViewById(2131230945);
        this.date_start = (TextView)layoutInflater.findViewById(2131230952);
        this.date_end = (TextView)layoutInflater.findViewById(2131230945);
        this.radioDateRange = (CheckBox)layoutInflater.findViewById(2131231233);
        this.radioScubaDive = (CheckBox)layoutInflater.findViewById(2131231237);
        this.radioFreeDive = (CheckBox)layoutInflater.findViewById(2131231235);
        this.radioGaugeDive = (CheckBox)layoutInflater.findViewById(2131231236);
        this.radioFavorite = (CheckBox)layoutInflater.findViewById(2131231234);
        this.init_date_filter();
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
    public void onResume() {
        super.onResume();
        if (!WatchOp.is_new_divelogs_downloaded) return;
        WatchOp.is_new_divelogs_downloaded = false;
        if (AppBase.adapterDiveLogsList == null) return;
        FragmentDiveLogsList.init_dive_logs();
        AppBase.adapterDiveLogsList.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void setListenToWatchList(OnDiveLogsFragmentInteractionListener onDiveLogsFragmentInteractionListener) {
        this.mListener = onDiveLogsFragmentInteractionListener;
    }

    public void show_filter_screen() {
        UiOp.viewAnimation((View)this.screen_filter, UiOp.TRANSLATION_AXIS.Y, 0.0f, 500, UiOp.INTERPOLATION_TYPE.DECELATION, true);
        this.screen_list.setVisibility(8);
    }

    public void start_filter() {
        this.do_filter();
        this.screen_list.setVisibility(0);
        UiOp.viewAnimation((View)this.screen_filter, UiOp.TRANSLATION_AXIS.Y, -this.screen_filter.getHeight(), 500, UiOp.INTERPOLATION_TYPE.ACCELARATION, false);
    }

    public static interface OnDiveLogsFragmentInteractionListener {
        public void onWatchesFragmentInteraction(String var1);
    }

}

