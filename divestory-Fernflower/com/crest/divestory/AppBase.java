package com.crest.divestory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import com.crest.divestory.ui.PagerAdapterMain;
import com.crest.divestory.ui.logs.AdapterDiveLogsList;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.crest.divestory.ui.settings.FragmentAppSettings;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;
import com.syntak.library.FileOp;
import com.syntak.library.PermissionsOp;
import com.syntak.library.time.TimerResetable;
import java.util.Arrays;
import java.util.Locale;

public class AppBase extends Application {
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
   public static String DISPLAY_UNIT;
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
   public static AppBase.LANGUAGES display_language;
   public static AppBase.UNITS display_unit;
   public static final String engineer_password_upgrade = "3674";
   public static final String filePref = "DiveStoryPref";
   public static boolean flag_checking_login;
   public static boolean flag_checking_server;
   public static boolean flag_need_connect;
   public static boolean flag_server_available;
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
      display_unit = AppBase.UNITS.metric;
      DISPLAY_LANGUAGE = "display_language";
      display_language = AppBase.LANGUAGES.Chinese;
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

   public static void askPermissions(Activity var0) {
      if (!isPermissionChecked) {
         WatchOp.isPermissionGranted = (new PermissionsOp(var0, Arrays.asList("android.permission.ACCESS_FINE_LOCATION", "android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"))).start();
         isPermissionChecked = true;
      }

   }

   public static void check_folders(Context var0) {
      folder_app_internal = var0.getFilesDir().getPath();
      String var1 = FileOp.combinePath(Environment.getExternalStorageDirectory().getPath(), "divestory");
      folder_app_external = var1;
      if (!FileOp.checkDirExist(var1)) {
         FileOp.makeDir(folder_app_external);
      }

      var1 = FileOp.combinePath(folder_app_external, "/dump");
      folder_dump = var1;
      if (!FileOp.checkDirExist(var1)) {
         FileOp.makeDir(folder_dump);
      }

      folder_icon = getLocalFolder("icon");
      folder_firmware = getLocalFolder("firmware");
      folder_download = FileOp.combinePath(Environment.getExternalStorageDirectory().getPath(), "download");
   }

   public static void close_db() {
      DbOp var0 = dbOp;
      if (var0 != null) {
         var0.close();
      }

      dbOp = null;
   }

   private static String getLocalFolder(String var0) {
      var0 = FileOp.combinePath(folder_app_internal, var0);
      if (!FileOp.checkDirExist(var0)) {
         FileOp.makeDir(var0);
      }

      return var0;
   }

   public static String get_String_of_Length(int var0) {
      String var1;
      if (display_unit == AppBase.UNITS.imperial) {
         var0 = (int)WatchOp.lengthMeter2Foot((float)var0);
         var1 = " ft";
      } else {
         var1 = " M";
      }

      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static void notifyChangeDiveLogList() {
      AdapterDiveLogsList var0 = adapterDiveLogsList;
      if (var0 != null) {
         var0.notifyDataSetChanged();
      }

   }

   public static void open_db(Context var0) {
      if (dbOp == null) {
         DbOp var1 = DbOp.getInstance(var0);
         dbOp = var1;
         var1.open();
      }

   }

   public static void readPref(Context var0) {
      SharedPreferences var1 = var0.getSharedPreferences("DiveStoryPref", 0);
      WatchOp.isAgreementGranted = var1.getBoolean("pref_agreement", false);
      display_unit = AppBase.UNITS.values()[var1.getInt(DISPLAY_UNIT, 0)];
      display_language = AppBase.LANGUAGES.values()[var1.getInt(DISPLAY_LANGUAGE, 0)];
   }

   public static void readPrefSerialNumber(Context var0) {
      WatchOp.prefSerialNumber = var0.getSharedPreferences("DiveStoryPref", 0).getString("pref_serial_number", (String)null);
   }

   public static void setTitleAndColor(Activity var0, int var1, int var2) {
      var2 = var0.getResources().getColor(var2);
      String var3 = var0.getString(var1);
      String var4 = String.format(Locale.US, "#%06X", Color.argb(0, Color.red(var2), Color.green(var2), Color.blue(var2)) & 16777215);
      StringBuilder var5 = new StringBuilder();
      var5.append("<font color=");
      var5.append(var4);
      var5.append(">");
      var5.append(var3);
      var5.append("</font>");
      var0.setTitle(Html.fromHtml(var5.toString()));
   }

   public static void setTitleAndColor(Activity var0, String var1, int var2) {
      var2 = var0.getResources().getColor(var2);
      String var3 = String.format(Locale.US, "#%06X", Color.argb(0, Color.red(var2), Color.green(var2), Color.blue(var2)) & 16777215);
      StringBuilder var4 = new StringBuilder();
      var4.append("<font color=");
      var4.append(var3);
      var4.append(">");
      var4.append(var1);
      var4.append("</font>");
      var0.setTitle(Html.fromHtml(var4.toString()));
   }

   public static void writePref(Context var0) {
      var0.getSharedPreferences("DiveStoryPref", 0).edit().putBoolean("pref_agreement", WatchOp.isAgreementGranted).putInt(DISPLAY_UNIT, display_unit.ordinal()).putInt(DISPLAY_LANGUAGE, display_language.ordinal()).apply();
   }

   public static void writePrefSerialNumber(Context var0) {
      var0.getSharedPreferences("DiveStoryPref", 0).edit().putString("pref_serial_number", WatchOp.prefSerialNumber).apply();
   }

   public void onCreate() {
      super.onCreate();
      Context var1 = this.getApplicationContext();
      context = var1;
      check_folders(var1);
   }

   public static enum LANGUAGES {
      Chinese,
      English;

      static {
         AppBase.LANGUAGES var0 = new AppBase.LANGUAGES("English", 1);
         English = var0;
      }
   }

   public static enum UNITS {
      imperial,
      metric;

      static {
         AppBase.UNITS var0 = new AppBase.UNITS("imperial", 1);
         imperial = var0;
      }
   }
}
