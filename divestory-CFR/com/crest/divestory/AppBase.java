/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Color
 *  android.os.Environment
 *  android.os.Handler
 *  android.text.Html
 *  android.view.Menu
 */
package com.crest.divestory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.PagerAdapterMain;
import com.crest.divestory.ui.logs.AdapterDiveLogsList;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.crest.divestory.ui.settings.FragmentAppSettings;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;
import com.syntak.library.FileOp;
import com.syntak.library.PermissionsOp;
import com.syntak.library.time.TimerResetable;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AppBase
extends Application {
    public static final String APP_NAME = "divestory";
    public static final String APP_VERSION = "1.0.1";
    public static final String ARG_DIVE_LOG_INDEX = "dive_log_index";
    public static final String ARG_INDEX = "index";
    public static final String ARG_SERIAL_NUMBER = "serial_number";
    public static String DATE_STRING_FORMAT_DDMMYYYY;
    public static String DATE_STRING_FORMAT_MMDDYYYY;
    public static String DATE_STRING_FORMAT_YYYYMMDD;
    public static String DATE_TIME_STRING_FORMAT;
    private static final boolean DEBUG = true;
    public static String DISPLAY_LANGUAGE;
    public static String DISPLAY_UNIT = "display_unit";
    public static final String FIRMWARE = "firmware";
    public static final String FOLDER_DUMP = "/dump";
    public static final String FOLDER_PHP = "/php";
    public static final String FOLDER_PHP_MGT = "/php_mgt";
    public static final String GOOGLE_STATIC_MAP_URL = "https://maps.google.com/maps/api/staticmap";
    public static final String HIFI = "hifi";
    public static final String HTTPS = "https://";
    public static final String ICON = "icon";
    static final int LEAST_TIME_TO_LOGIN_APP = 180000;
    static final int LoginExpiredPeriod = 7776000;
    public static final String MAP_DECRIPTION = "decription.map";
    public static final String MAP_ENCRIPTION = "encription.map";
    public static final String MEDIA = "media";
    public static final String PHP_CHECK_DICTIONARY = "check_dictionary.php";
    public static final String PHP_CHECK_SERVER = "check_server.php";
    public static final String PHP_COMMODITY = "commodity_purchase.php";
    public static final String PHP_DB_READ = "db_read.php";
    public static final String PHP_DB_UPDATE = "db_update.php";
    public static final String PHP_MANAGE = "manage.php";
    public static final String PHP_MEDIA_DETAIL = "media_detail.php";
    public static final String PREF_AGREEMENT = "pref_agreement";
    public static final String PREF_SERIAL_NUMBER = "pref_serial_number";
    public static final int RC_ADD_EDIT = 100;
    public static final String SAMPLE = "sample";
    public static final String STATUS_ACCOUNT_INVALID = "account_invalid";
    public static final String STATUS_LOGIN_ERROR = "login_error";
    public static final String STATUS_LOGIN_OK = "login_OK";
    public static final String TAG_ITEMS_ARRAY = "items_array";
    public static String TIME_STRING_FORMAT;
    public static final String URL_WEBSITE = "syntak.net";
    static final int WAIT_HTTP_RESPONSE_SECONDS = 10000;
    public static AdapterDiveLogsList adapterDiveLogsList;
    public static final boolean check_UI_only = false;
    public static Context context;
    public static Cursor cursor_watches;
    public static DbOp dbOp;
    public static LANGUAGES display_language;
    public static UNITS display_unit;
    public static final String engineer_password_upgrade = "3674";
    public static final String filePref = "DiveStoryPref";
    public static boolean flag_checking_login = false;
    public static boolean flag_checking_server = false;
    public static boolean flag_need_connect = false;
    public static boolean flag_server_available = false;
    public static String folder_app_external;
    public static String folder_app_internal;
    public static String folder_download;
    public static String folder_dump;
    public static String folder_firmware;
    public static String folder_icon;
    public static final boolean force_create_db = false;
    public static final boolean force_dump_dive_profile_data = false;
    public static final boolean force_firmware_upgrade = false;
    public static FragmentAppSettings fragmentAppSettings;
    public static FragmentDiveLogsList fragmentDiveLogsList;
    public static FragmentSyncedWatchesList fragmentSyncedWatchesList;
    public static Handler handler;
    public static boolean isExitSync;
    public static boolean isPermissionChecked;
    public static Menu mainMenu;
    public static PagerAdapterMain pagerAdapter;
    public static PermissionsOp permissionsOp;
    public static int screen_height_dp;
    public static int screen_height_px;
    public static int screen_width_dp;
    public static int screen_width_px;
    public static TimerResetable timerResetable;
    public boolean isEnabled = false;
    public boolean isSupported = false;

    static {
        display_unit = UNITS.metric;
        DISPLAY_LANGUAGE = "display_language";
        display_language = LANGUAGES.Chinese;
        DATE_STRING_FORMAT_YYYYMMDD = "%1$4d/%2$02d/%3$02d";
        DATE_STRING_FORMAT_MMDDYYYY = "%1$02d/%2$02d/%3$4d";
        DATE_STRING_FORMAT_DDMMYYYY = "%1$02d/%2$02d/%3$4d";
        TIME_STRING_FORMAT = "%1$02d:%2$02d";
        DATE_TIME_STRING_FORMAT = "20%1$02d/%2$02d/%3$02d %4$02d:%5$02d";
        context = null;
        handler = null;
        mainMenu = null;
        dbOp = null;
        permissionsOp = null;
        timerResetable = null;
        cursor_watches = null;
        folder_app_internal = null;
        folder_app_external = null;
        folder_dump = null;
        folder_icon = null;
        folder_firmware = null;
        folder_download = null;
        flag_need_connect = true;
        flag_checking_login = false;
        flag_checking_server = false;
        flag_server_available = false;
        isExitSync = false;
        isPermissionChecked = false;
    }

    public static void askPermissions(Activity activity) {
        if (isPermissionChecked) return;
        WatchOp.isPermissionGranted = new PermissionsOp(activity, Arrays.asList("android.permission.ACCESS_FINE_LOCATION", "android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")).start();
        isPermissionChecked = true;
    }

    public static void check_folders(Context object) {
        folder_app_internal = object.getFilesDir().getPath();
        object = FileOp.combinePath(Environment.getExternalStorageDirectory().getPath(), APP_NAME);
        folder_app_external = object;
        if (!FileOp.checkDirExist((String)object)) {
            FileOp.makeDir(folder_app_external);
        }
        object = FileOp.combinePath(folder_app_external, FOLDER_DUMP);
        folder_dump = object;
        if (!FileOp.checkDirExist((String)object)) {
            FileOp.makeDir(folder_dump);
        }
        folder_icon = AppBase.getLocalFolder(ICON);
        folder_firmware = AppBase.getLocalFolder(FIRMWARE);
        folder_download = FileOp.combinePath(Environment.getExternalStorageDirectory().getPath(), "download");
    }

    public static void close_db() {
        DbOp dbOp = AppBase.dbOp;
        if (dbOp != null) {
            dbOp.close();
        }
        AppBase.dbOp = null;
    }

    private static String getLocalFolder(String string2) {
        if (FileOp.checkDirExist(string2 = FileOp.combinePath(folder_app_internal, string2))) return string2;
        FileOp.makeDir(string2);
        return string2;
    }

    public static String get_String_of_Length(int n) {
        String string2;
        if (display_unit == UNITS.imperial) {
            n = (int)WatchOp.lengthMeter2Foot(n);
            string2 = " ft";
        } else {
            string2 = " M";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public static void notifyChangeDiveLogList() {
        AdapterDiveLogsList adapterDiveLogsList = AppBase.adapterDiveLogsList;
        if (adapterDiveLogsList == null) return;
        adapterDiveLogsList.notifyDataSetChanged();
    }

    public static void open_db(Context object) {
        if (dbOp != null) return;
        object = DbOp.getInstance((Context)object);
        dbOp = object;
        ((DbOp)object).open();
    }

    public static void readPref(Context context) {
        context = context.getSharedPreferences(filePref, 0);
        WatchOp.isAgreementGranted = context.getBoolean(PREF_AGREEMENT, false);
        display_unit = UNITS.values()[context.getInt(DISPLAY_UNIT, 0)];
        display_language = LANGUAGES.values()[context.getInt(DISPLAY_LANGUAGE, 0)];
    }

    public static void readPrefSerialNumber(Context context) {
        WatchOp.prefSerialNumber = context.getSharedPreferences(filePref, 0).getString(PREF_SERIAL_NUMBER, null);
    }

    public static void setTitleAndColor(Activity activity, int n, int n2) {
        n2 = activity.getResources().getColor(n2);
        String string2 = activity.getString(n);
        String string3 = String.format(Locale.US, "#%06X", Color.argb((int)0, (int)Color.red((int)n2), (int)Color.green((int)n2), (int)Color.blue((int)n2)) & 16777215);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font color=");
        stringBuilder.append(string3);
        stringBuilder.append(">");
        stringBuilder.append(string2);
        stringBuilder.append("</font>");
        activity.setTitle((CharSequence)Html.fromHtml((String)stringBuilder.toString()));
    }

    public static void setTitleAndColor(Activity activity, String string2, int n) {
        n = activity.getResources().getColor(n);
        String string3 = String.format(Locale.US, "#%06X", Color.argb((int)0, (int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n)) & 16777215);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font color=");
        stringBuilder.append(string3);
        stringBuilder.append(">");
        stringBuilder.append(string2);
        stringBuilder.append("</font>");
        activity.setTitle((CharSequence)Html.fromHtml((String)stringBuilder.toString()));
    }

    public static void writePref(Context context) {
        context.getSharedPreferences(filePref, 0).edit().putBoolean(PREF_AGREEMENT, WatchOp.isAgreementGranted).putInt(DISPLAY_UNIT, display_unit.ordinal()).putInt(DISPLAY_LANGUAGE, display_language.ordinal()).apply();
    }

    public static void writePrefSerialNumber(Context context) {
        context.getSharedPreferences(filePref, 0).edit().putString(PREF_SERIAL_NUMBER, WatchOp.prefSerialNumber).apply();
    }

    public void onCreate() {
        Context context;
        super.onCreate();
        AppBase.context = context = this.getApplicationContext();
        AppBase.check_folders(context);
    }

    public static final class LANGUAGES
    extends Enum<LANGUAGES> {
        private static final /* synthetic */ LANGUAGES[] $VALUES;
        public static final /* enum */ LANGUAGES Chinese;
        public static final /* enum */ LANGUAGES English;

        static {
            LANGUAGES lANGUAGES;
            Chinese = new LANGUAGES();
            English = lANGUAGES = new LANGUAGES();
            $VALUES = new LANGUAGES[]{Chinese, lANGUAGES};
        }

        public static LANGUAGES valueOf(String string2) {
            return Enum.valueOf(LANGUAGES.class, string2);
        }

        public static LANGUAGES[] values() {
            return (LANGUAGES[])$VALUES.clone();
        }
    }

    public static final class UNITS
    extends Enum<UNITS> {
        private static final /* synthetic */ UNITS[] $VALUES;
        public static final /* enum */ UNITS imperial;
        public static final /* enum */ UNITS metric;

        static {
            UNITS uNITS;
            metric = new UNITS();
            imperial = uNITS = new UNITS();
            $VALUES = new UNITS[]{metric, uNITS};
        }

        public static UNITS valueOf(String string2) {
            return Enum.valueOf(UNITS.class, string2);
        }

        public static UNITS[] values() {
            return (UNITS[])$VALUES.clone();
        }
    }

}

