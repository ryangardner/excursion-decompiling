package com.crest.divestory;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import com.syntak.library.FileOp;
import com.syntak.library.TimeOp;
import java.io.File;
import java.io.IOException;

public class DbOp {
   private static final boolean DB_COPYABLE = true;
   public static final String DB_NAME = "divestory.db";
   private static final boolean DB_UPGRADABLE = true;
   private static final int DB_VERSION = 2;
   public static final String EXT_DB = ".db";
   static final String VALUE_NULL = "null";
   public static final String columnAlarmType = "alarm_type";
   public static final String columnAuxWeight = "aux_weight";
   public static final String columnAverageDepth = "average_depth";
   public static final String columnBleDeviceName = "ble_device_name";
   public static final String columnBrand = "brand";
   public static final String columnBreathingGas = "breathing_gas";
   public static final String columnBuddy = "buddy";
   public static final String columnCNSatStart = "CNS_at_start";
   public static final String columnCeiling = "ceiling";
   public static final String columnCns = "cns";
   public static final String columnCylinderCapacity = "cylinder_capacity";
   public static final String columnDataEndAddress = "data_end_address";
   public static final String columnDataLength = "data_length";
   public static final String columnDataStartAddress = "data_start_address";
   public static final String columnDataType = "data_type";
   public static final String columnDecoModel = "deco_model";
   public static final String columnDecoState = "deco_state";
   public static final String columnDefaultValue = "default_value";
   public static final String columnDepth = "depth";
   public static final String columnDiveLogIndex = "dive_log_index";
   public static final String columnDiveType = "dive_type";
   public static final String columnDuration = "duration";
   public static final String columnEmail = "email";
   public static final String columnEndH2Compartment = "end_H2_compartment";
   public static final String columnEndN2Compartment = "end_N2_compartment";
   public static final String columnEndTime = "end_time";
   public static final String columnEndTimeForLastDownloadedLog = "end_time_for_last_downloaded_log";
   public static final String columnFirmwareFilename = "firmware_filename";
   public static final String columnFirmwareVersion = "firmware_version";
   public static final String columnFreeDiveLongestTime = "free_dive_longest_time";
   public static final String columnFreeDiveMaxDepth = "free_dive_max_depth";
   public static final String columnFreeDiveTimes = "free_dive_times";
   public static final String columnHardwareVersion = "hardware_version";
   public static final String columnIcon = "icon";
   public static final String columnId = "_id";
   public static final String columnIdBrand = "id_brand";
   public static final String columnIdDive = "id_dive";
   public static final String columnIdDiveLog = "id_dive_log";
   public static final String columnIdLog = "id_log";
   public static final String columnIdMember = "id_member";
   public static final String columnIdModel = "id_model";
   public static final String columnIsFavorite = "is_favorite";
   public static final String columnIsHighElevationDiving = "is_high_elevation_diving";
   public static final String columnLanguageCode1 = "language_code1";
   public static final String columnLanguageCode2 = "language_code2";
   public static final String columnLastDiveLogIndex = "last_dive_log_index";
   public static final String columnLastDiveTime = "last_dive_time";
   public static final String columnLastDownloadDateTime = "last_download_date_time";
   public static final String columnLastUploadDateTime = "last_upload_date_time";
   public static final String columnLatitude = "latitude";
   public static final String columnLocation = "location";
   public static final String columnLongitude = "longitude";
   public static final String columnLowestWaterTemperature = "lowest_water_temperature";
   public static final String columnMacAddress = "mac_address";
   public static final String columnMaxCNS = "max_CNS";
   public static final String columnMaxDepth = "max_depth";
   public static final String columnModelName = "model_name";
   public static final String columnModelNameToShow = "model_name_to_show";
   public static final String columnName = "name";
   public static final String columnNoDiveTime = "no_dive_time";
   public static final String columnNoFlightTime = "no_flight_time";
   public static final String columnNote = "note";
   public static final String columnO2Ratio = "O2_ratio";
   public static final String columnPPO2 = "PPO2";
   public static final String columnPassword = "password";
   public static final String columnPressureEnd = "pressure_end";
   public static final String columnPressureStart = "pressure_start";
   public static final String columnProfileDataLength = "profile_data_length";
   public static final String columnRating = "rating";
   public static final String columnSafetyFactor = "safety_factor";
   public static final String columnSamplingRate = "sampling_rate";
   public static final String columnScubaDiveMaxDepth = "scuba_dive_max_depth";
   public static final String columnScubaDiveTimes = "scuba_dive_times";
   public static final String columnScubaDiveTotalTime = "scuba_dive_total_time";
   public static final String columnSerialNumber = "serial_number";
   public static final String columnSettingName = "setting_name";
   public static final String columnStartDay = "start_day";
   public static final String columnStartHour = "start_hour";
   public static final String columnStartMinute = "start_minute";
   public static final String columnStartMonth = "start_month";
   public static final String columnStartTime = "start_time";
   public static final String columnStartTimeForLastDownloadedLog = "start_time_for_last_downloaded_log";
   public static final String columnStartYear = "start_year";
   public static final String columnStopTime = "stop_time";
   public static final String columnSurfaceATM = "surface_ATM";
   public static final String columnSurfaceTemperature = "surface_temperature";
   public static final String columnTemperature = "temperature";
   public static final String columnText1 = "text1";
   public static final String columnText2 = "text2";
   public static final String columnTime = "time";
   public static final String columnTimeElapsed = "time_elapsed";
   public static final String columnTimeStart = "time_start";
   public static final String columnUsername = "username";
   public static final String columnUtcOffset = "utc_offset";
   public static final String columnUuidCharacteristic = "uuid_characteristic";
   public static final String columnUuidService = "uuid_service";
   public static final String columnUuidShortCharacteristic = "uuid_short_characteristic";
   public static final String columnUuidShortService = "uuid_short_service";
   public static final String columnValue = "value";
   public static final String columnValueInt = "value_int";
   public static final String columnValueString = "value_string";
   public static final String columnVisibility = "visibility";
   public static final String columnWave = "wave";
   public static final String columnWeather = "weather";
   public static final String columnWind = "wind";
   private static Context context;
   private static SQLiteDatabase db;
   private static DbOp mInstance;
   public static final String tableBrands = "brands";
   public static final String tableCharacteristics = "characteristics";
   public static final String tableDictionary = "dictionary";
   public static final String tableDiveLogs = "dive_logs";
   public static final String tableDiveProfileData = "dive_profile_data";
   public static final String tableModels = "models";
   public static final String tableMyWatches = "my_watches";
   public static final String tableWatchSettings = "watch_settings";
   private String dbFolder;
   private DbOp.DbOpenHelper dbHelper = null;
   public String dbPath = null;

   DbOp(Context var1) {
      context = var1;
      String var2 = var1.getDatabasePath("divestory.db").getPath();
      this.dbPath = var2;
      this.dbFolder = FileOp.getFolderChainFromPath(var2);
   }

   private void backupDB(String var1, String var2) throws IOException {
      FileOp.copyFile(var1, var2);
   }

   public static DbOp getInstance(Context var0) {
      if (mInstance == null) {
         mInstance = new DbOp(var0.getApplicationContext());
      }

      return mInstance;
   }

   public long addCharacteristics(ContentValues var1) {
      return db.insert("characteristics", (String)null, var1);
   }

   public String checkDictionary(String var1, String var2, String var3) {
      Cursor var4 = db.query("dictionary", (String[])null, "language_code1 = ? AND text1 = ?  AND language_code2 = ? ", new String[]{var1, var2, var3}, (String)null, (String)null, (String)null);
      String var7;
      if (var4 != null && var4.moveToFirst()) {
         var7 = var4.getString(var4.getColumnIndex("text2"));
      } else {
         var7 = null;
      }

      String var5 = var7;
      if (var7 == null) {
         Cursor var6 = db.query("dictionary", (String[])null, "language_code2 = ? AND text2 = ?  AND language_code1 = ? ", new String[]{var1, var2, var3}, (String)null, (String)null, (String)null);
         var5 = var7;
         if (var6 != null) {
            var5 = var7;
            if (var6.moveToFirst()) {
               var5 = var6.getString(var6.getColumnIndex("text1"));
            }
         }
      }

      return var5;
   }

   public void close() {
      DbOp.DbOpenHelper var1 = this.dbHelper;
      if (var1 != null) {
         var1.close();
         this.dbHelper = null;
      }
   }

   public void createDB(SQLiteDatabase var1) {
      try {
         var1.execSQL("CREATE TABLE IF NOT EXISTS android_metadata ( locale TEXT DEFAULT 'en_US')");
         var1.execSQL("CREATE TABLE IF NOT EXISTS my_watches (_id INTEGER PRIMARY KEY AUTOINCREMENT , id_model INTEGER , mac_address TEXT , model_name TEXT , model_name_to_show TEXT , ble_device_name TEXT , serial_number TEXT , hardware_version TEXT , firmware_version TEXT , last_dive_log_index INTEGER , last_dive_time INTEGER , scuba_dive_total_time INTEGER , scuba_dive_max_depth INTEGER , scuba_dive_times INTEGER , free_dive_longest_time INTEGER , free_dive_max_depth INTEGER , free_dive_times INTEGER , no_flight_time INTEGER , no_dive_time INTEGER , start_time_for_last_downloaded_log INTEGER DEFAULT 0  , end_time_for_last_downloaded_log INTEGER DEFAULT 0  ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS models (_id INTEGER PRIMARY KEY AUTOINCREMENT , model_name TEXT , hardware_version TEXT , firmware_version TEXT , last_download_date_time TEXT , icon TEXT , firmware_filename TEXT ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS watch_settings (_id INTEGER PRIMARY KEY AUTOINCREMENT , id_model TEXT , setting_name TEXT , data_type INTEGER , data_length INTEGER , value_int INTEGER , value_string TEXT , default_value TEXT ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS dive_logs (_id INTEGER PRIMARY KEY AUTOINCREMENT , serial_number INTEGER , dive_log_index INTEGER , dive_type INTEGER , profile_data_length INTEGER , start_time INTEGER , end_time INTEGER , duration INTEGER , sampling_rate INTEGER , max_depth INTEGER , lowest_water_temperature INTEGER , average_depth INTEGER , data_start_address INTEGER , data_end_address INTEGER , firmware_version TEXT , surface_ATM INTEGER , is_high_elevation_diving INTEGER , safety_factor INTEGER , PPO2 INTEGER , CNS_at_start INTEGER , max_CNS INTEGER , deco_model INTEGER , end_N2_compartment TEXT , end_H2_compartment TEXT , no_flight_time INTEGER , no_dive_time INTEGER , rating INTEGER , note TEXT , is_favorite INTEGER , location TEXT , breathing_gas TEXT , cylinder_capacity INTEGER , O2_ratio INTEGER , pressure_start INTEGER , pressure_end INTEGER , aux_weight INTEGER , visibility INTEGER , surface_temperature INTEGER , weather TEXT , wind TEXT , wave TEXT , buddy TEXT ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS dive_profile_data (_id INTEGER PRIMARY KEY AUTOINCREMENT , serial_number TEXT , dive_log_index INTEGER , start_time INTEGER , data_type INTEGER , depth INTEGER , temperature INTEGER , time_elapsed INTEGER , cns INTEGER , deco_state INTEGER , ceiling INTEGER , stop_time INTEGER , alarm_type INTEGER , time INTEGER , value INTEGER ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS brands (_id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , icon TEXT ) ");
         var1.execSQL("CREATE TABLE IF NOT EXISTS characteristics (_id INTEGER PRIMARY KEY AUTOINCREMENT ,mac_address TEXT  ,uuid_short_service TEXT  ,uuid_short_characteristic TEXT  ,uuid_service TEXT  ,uuid_characteristic TEXT )");
         var1.execSQL("CREATE TABLE IF NOT EXISTS dictionary (_id INTEGER PRIMARY KEY AUTOINCREMENT , language_code1 TEXT , text1 TEXT , language_code2 TEXT , text2 TEXT ) ");
      } catch (Exception var3) {
         Log.e("Error", "Error", var3);
         if (var1 != null) {
            var1.close();
         }
      }

   }

   public void deleteAllCharacteristics() {
      db.delete("characteristics", (String)null, (String[])null);
   }

   public void deleteDiveLogByStartTime(String var1, long var2) {
      db.delete("dive_logs", "serial_number = ?  AND start_time = ? ", new String[]{var1, String.valueOf(var2)});
   }

   public void deleteDiveProfileDataByStartTime(String var1, long var2) {
      db.delete("dive_profile_data", "serial_number = ?  AND start_time = ? ", new String[]{var1, String.valueOf(var2)});
   }

   public void deleteMyWatchByMacAddress(String var1) {
      db.delete("my_watches", "mac_address = ? ", new String[]{var1});
   }

   public void deleteMyWatchBySerialNumber(String var1) {
      db.delete("my_watches", "serial_number = ? ", new String[]{var1});
   }

   public Cursor getBrand(long var1) {
      Cursor var3 = null;
      if (var1 < 0L) {
         return null;
      } else {
         Cursor var4 = db.query("brands", (String[])null, "_id = ?", new String[]{String.valueOf(var1)}, (String)null, (String)null, (String)null);
         if (var4 == null || var4.moveToFirst()) {
            var3 = var4;
         }

         return var3;
      }
   }

   public Cursor getCharacteristic(String var1, String var2) {
      Cursor var4 = db.query("characteristics", (String[])null, "mac_address = ?  AND uuid_short_characteristic = ? ", new String[]{var1, var2}, (String)null, (String)null, (String)null);
      Cursor var3 = var4;
      if (var4 != null) {
         var3 = var4;
         if (!var4.moveToFirst()) {
            var3 = null;
         }
      }

      return var3;
   }

   public String getDbPath() {
      return this.dbPath;
   }

   public Cursor getDiveLogs() {
      Cursor var1 = db.query("dive_logs", (String[])null, (String)null, (String[])null, (String)null, (String)null, (String)null);
      Cursor var2 = var1;
      if (var1 != null) {
         var2 = var1;
         if (!var1.moveToFirst()) {
            var2 = null;
         }
      }

      return var2;
   }

   public Cursor getDiveLogsBySerialNumber(String var1) {
      Cursor var2 = db.query("dive_logs", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
      Cursor var3 = var2;
      if (var2 != null) {
         var3 = var2;
         if (!var2.moveToFirst()) {
            var3 = null;
         }
      }

      return var3;
   }

   public Cursor getDiveProfileData(String var1, long var2) {
      Cursor var4 = db.query("dive_profile_data", (String[])null, "serial_number = ? AND start_time = ? ", new String[]{var1, String.valueOf(var2)}, (String)null, (String)null, (String)null);
      Cursor var5 = var4;
      if (var4 != null) {
         var5 = var4;
         if (!var4.moveToFirst()) {
            var5 = null;
         }
      }

      return var5;
   }

   public String getFirmwareFilename(long var1) {
      Cursor var3 = db.query("models", (String[])null, "_id = ?", new String[]{String.valueOf(var1)}, (String)null, (String)null, (String)null);
      String var4 = null;
      Cursor var5 = var3;
      if (var3 != null) {
         var5 = var3;
         if (!var3.moveToFirst()) {
            var5 = null;
         }
      }

      if (var5 != null) {
         var4 = var5.getString(var5.getColumnIndex("firmware_filename"));
      }

      return var4;
   }

   public String getFirmwareVersionFromModelName(String var1) {
      Cursor var2 = db.query("models", (String[])null, "model_name = ?", new String[]{var1}, (String)null, (String)null, (String)null);
      String var3 = null;
      Cursor var4 = var2;
      if (var2 != null) {
         var4 = var2;
         if (!var2.moveToFirst()) {
            var4 = null;
         }
      }

      if (var4 != null) {
         var3 = var4.getString(var4.getColumnIndex("firmware_version"));
      }

      return var3;
   }

   public long getIdModelFromModelName(String var1) {
      Cursor var2 = db.query("models", (String[])null, "model_name = ?", new String[]{var1}, (String)null, (String)null, (String)null);
      Cursor var5 = var2;
      if (var2 != null) {
         var5 = var2;
         if (!var2.moveToFirst()) {
            var5 = null;
         }
      }

      long var3;
      if (var5 != null) {
         var3 = var5.getLong(var5.getColumnIndex("_id"));
      } else {
         var3 = -1L;
      }

      return var3;
   }

   public String getModelName(long var1) {
      Cursor var3 = db.query("models", (String[])null, "_id = ?", new String[]{String.valueOf(var1)}, (String)null, (String)null, (String)null);
      String var4 = null;
      Cursor var5 = var3;
      if (var3 != null) {
         var5 = var3;
         if (!var3.moveToFirst()) {
            var5 = null;
         }
      }

      if (var5 != null) {
         var4 = var5.getString(var5.getColumnIndex("model_name"));
      }

      return var4;
   }

   public Cursor getMyWatch(String var1) {
      Object var2 = null;
      if (var1 == null) {
         return null;
      } else {
         Cursor var3 = db.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
         if (var3 != null && !var3.moveToFirst()) {
            var3 = (Cursor)var2;
         }

         return var3;
      }
   }

   public long getMyWatchEndTimeForLastDownloadedLog(String var1) {
      long var2 = -1L;
      if (var1 == null) {
         return -1L;
      } else {
         Cursor var4 = db.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
         Cursor var5 = var4;
         if (var4 != null) {
            var5 = var4;
            if (!var4.moveToFirst()) {
               var5 = null;
            }
         }

         if (var5 != null) {
            var2 = var5.getLong(var5.getColumnIndex("end_time_for_last_downloaded_log"));
         }

         return var2;
      }
   }

   public String getMyWatchFirmwareVersion(String var1) {
      String var2 = null;
      if (var1 == null) {
         return null;
      } else {
         Cursor var3 = db.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
         Cursor var4 = var3;
         if (var3 != null) {
            var4 = var3;
            if (!var3.moveToFirst()) {
               var4 = null;
            }
         }

         if (var4 != null) {
            var2 = var4.getString(var4.getColumnIndex("firmware_version"));
         }

         return var2;
      }
   }

   public int getMyWatchLastDiveLogIndex(String var1) {
      int var2 = -1;
      if (var1 == null) {
         return -1;
      } else {
         Cursor var3 = db.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
         Cursor var4 = var3;
         if (var3 != null) {
            var4 = var3;
            if (!var3.moveToFirst()) {
               var4 = null;
            }
         }

         if (var4 != null) {
            var2 = var4.getInt(var4.getColumnIndex("last_dive_log_index"));
         }

         return var2;
      }
   }

   public long getMyWatchStartTimeForLastDownloadedLog(String var1) {
      long var2 = -1L;
      if (var1 == null) {
         return -1L;
      } else {
         Cursor var4 = db.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
         Cursor var5 = var4;
         if (var4 != null) {
            var5 = var4;
            if (!var4.moveToFirst()) {
               var5 = null;
            }
         }

         if (var5 != null) {
            var2 = var5.getLong(var5.getColumnIndex("start_time_for_last_downloaded_log"));
         }

         return var2;
      }
   }

   public Cursor getMyWatches() {
      Cursor var1 = db.query("my_watches", (String[])null, (String)null, (String[])null, (String)null, (String)null, (String)null);
      Cursor var2 = var1;
      if (var1 != null) {
         var2 = var1;
         if (!var1.moveToFirst()) {
            var2 = null;
         }
      }

      return var2;
   }

   public long insertDictionary(DataStruct.Dictionary var1) {
      Cursor var2 = db.query("dictionary", (String[])null, "language_code1 = ? AND text1 = ? ", new String[]{var1.languageCode1, var1.text1}, (String)null, (String)null, (String)null);
      long var3;
      if (var2 != null && var2.moveToFirst()) {
         var3 = -1L;
      } else {
         var3 = db.insert("dictionary", (String)null, var1.toContentValues());
      }

      return var3;
   }

   public long insertDiveLog(DataStruct.DiveLog var1) {
      return db.insert("dive_logs", (String)null, var1.watchContentValues());
   }

   public void insertDiveProfileData(DataStruct.DiveProfileData var1) {
      if (var1.length() > 0) {
         for(int var2 = 0; var2 < var1.length(); ++var2) {
            this.insertDiveProfileDatum((DataStruct.DiveProfileDatum)var1.list.get(var2));
         }
      }

   }

   public long insertDiveProfileDatum(DataStruct.DiveProfileDatum var1) {
      return db.insert("dive_profile_data", (String)null, var1.toContentValues());
   }

   public long insertModel(DataStruct.Model var1) {
      return db.insert("models", (String)null, var1.toContentValues());
   }

   public long insertMyWatch(DataStruct.MyWatch var1) {
      return db.insert("my_watches", (String)null, var1.toContentValues());
   }

   public void insertMyWatches(DataStruct.MyWatches var1) {
      if (var1.length() > 0) {
         for(int var2 = 0; var2 < var1.length(); ++var2) {
            if (!this.isMyWatchLogged(((DataStruct.MyWatch)var1.list.get(var2)).serial_number)) {
               this.insertMyWatch((DataStruct.MyWatch)var1.list.get(var2));
            }
         }
      }

   }

   public boolean isAnyCharacteristic(String var1) {
      boolean var2;
      if (this.readCharacteristics(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isDiveLogExisted(String var1, long var2) {
      SQLiteDatabase var4 = db;
      boolean var5 = false;
      Cursor var7 = var4.query("dive_logs", (String[])null, "serial_number = ?  AND start_time = ? ", new String[]{var1, String.valueOf(var2)}, (String)null, (String)null, (String)null);
      Cursor var6 = var7;
      if (var7 != null) {
         var6 = var7;
         if (!var7.moveToFirst()) {
            var6 = null;
         }
      }

      if (var6 != null) {
         var5 = true;
      }

      return var5;
   }

   public boolean isMyWatchLogged(String var1) {
      SQLiteDatabase var2 = db;
      boolean var3 = true;
      Cursor var5 = var2.query("my_watches", (String[])null, "serial_number = ?", new String[]{var1}, (String)null, (String)null, (String)null);
      Cursor var4 = var5;
      if (var5 != null) {
         var4 = var5;
         if (!var5.moveToFirst()) {
            var4 = null;
         }
      }

      if (var4 == null) {
         var3 = false;
      }

      return var3;
   }

   public void open() throws SQLException {
      DbOp.DbOpenHelper var1 = new DbOp.DbOpenHelper(context, "divestory.db", 2);
      this.dbHelper = var1;
      db = var1.getWritableDatabase();
   }

   public Cursor readBrands() {
      Cursor var1 = db.query("brands", (String[])null, (String)null, (String[])null, (String)null, (String)null, (String)null);
      Cursor var2 = var1;
      if (var1 != null) {
         var2 = var1;
         if (!var1.moveToFirst()) {
            var2 = null;
         }
      }

      return var2;
   }

   public Cursor readCharacteristics(String var1) {
      Cursor var2 = db.query("characteristics", (String[])null, "mac_address= ?", new String[]{var1}, (String)null, (String)null, (String)null);
      Cursor var3 = var2;
      if (var2 != null) {
         var3 = var2;
         if (!var2.moveToFirst()) {
            var3 = null;
         }
      }

      return var3;
   }

   public Cursor readModelByModelName(String var1) {
      Cursor var2 = db.query("models", (String[])null, "model_name = ?", new String[]{var1}, (String)null, (String)null, (String)null);
      Cursor var3 = var2;
      if (var2 != null) {
         var3 = var2;
         if (!var2.moveToFirst()) {
            var3 = null;
         }
      }

      return var3;
   }

   public boolean updateDiveLog(DataStruct.DiveLog var1) {
      String var2 = var1.watch_serial_number;
      long var3 = var1.start_time;
      ContentValues var5 = var1.editContentValues();
      return (long)db.update("dive_logs", var5, "serial_number = ?  AND start_time = ? ", new String[]{var2, String.valueOf(var3)}) >= 0L;
   }

   public boolean updateDiveLogFavorite(String var1, long var2, boolean var4) {
      ContentValues var5 = new ContentValues();
      var5.put("is_favorite", Integer.valueOf(var4));
      return (long)db.update("dive_logs", var5, "serial_number = ?  AND start_time = ? ", new String[]{var1, String.valueOf(var2)}) >= 0L;
   }

   public boolean updateDiveLogFromProfile(DataStruct.DiveLog var1) {
      String var2 = var1.watch_serial_number;
      long var3 = var1.start_time;
      ContentValues var5 = var1.profileContentValues();
      return (long)db.update("dive_logs", var5, "serial_number = ?  AND start_time = ? ", new String[]{var2, String.valueOf(var3)}) >= 0L;
   }

   public boolean updateMyWatch(DataStruct.MyWatch var1) {
      return (long)db.update("my_watches", var1.toContentValues(), "serial_number = ? ", new String[]{var1.serial_number}) >= 0L;
   }

   public boolean updateMyWatchLastDiveLogIndex(String var1, long var2) {
      ContentValues var4 = new ContentValues();
      var4.put("last_dive_log_index", var2);
      return (long)db.update("my_watches", var4, "serial_number = ? ", new String[]{var1}) >= 0L;
   }

   public boolean updateMyWatchTimeForLastDownloadedLog(String var1, long var2, long var4) {
      if (var2 <= this.getMyWatchStartTimeForLastDownloadedLog(var1)) {
         return false;
      } else {
         ContentValues var6 = new ContentValues();
         var6.put("start_time_for_last_downloaded_log", var2);
         var6.put("end_time_for_last_downloaded_log", var2 + var4);
         return (long)db.update("my_watches", var6, "serial_number = ? ", new String[]{var1}) >= 0L;
      }
   }

   public void upgradeDB(SQLiteDatabase var1, int var2, int var3) {
      if (var2 != 1) {
         if (var2 != 2) {
            return;
         }
      } else {
         try {
            var1.execSQL("ALTER TABLE my_watches ADD COLUMN end_time_for_last_downloaded_log INTEGER default 0");
         } catch (SQLiteException var6) {
            Log.w("Upgrade DB 1", var6.getMessage());
         }
      }

      try {
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN start_year INTEGER default 0");
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN start_month INTEGER default 0");
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN start_day INTEGER default 0");
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN start_hour INTEGER default 0");
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN start_minute INTEGER default 0");
         var1.execSQL("ALTER TABLE dive_logs ADD COLUMN utc_offset INTEGER default 0");
         var2 = TimeOp.get_UTC_offset();
         StringBuilder var4 = new StringBuilder();
         var4.append("UPDATE dive_logs SET utc_offset = ");
         var4.append(var2);
         var1.execSQL(var4.toString());
      } catch (SQLiteException var5) {
         Log.w("Upgrade DB 3", var5.getMessage());
      }

   }

   class DatabaseContext extends ContextWrapper {
      private static final String DEBUG_CONTEXT = "DatabaseContext";
      String folder;

      public DatabaseContext(Context var2, String var3) {
         super(var2);
         this.folder = var3;
      }

      public File getDatabasePath(String var1) {
         if (this.folder == null) {
            return super.getDatabasePath(var1);
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append(this.folder);
            var2.append("/");
            var2.append(var1);
            String var7 = var2.toString();
            var1 = var7;
            if (!var7.endsWith(".db")) {
               StringBuilder var6 = new StringBuilder();
               var6.append(var7);
               var6.append(".db");
               var1 = var6.toString();
            }

            boolean var3 = false;
            File var8 = new File(var1);
            if (!var8.getParentFile().exists()) {
               var8.getParentFile().mkdirs();
            }

            boolean var4;
            if (!var8.exists()) {
               try {
                  var4 = var8.createNewFile();
               } catch (IOException var5) {
                  var5.printStackTrace();
                  var4 = var3;
               }
            } else {
               var4 = true;
            }

            return var4 ? var8 : null;
         }
      }

      public SQLiteDatabase openOrCreateDatabase(String var1, int var2, CursorFactory var3, DatabaseErrorHandler var4) {
         return SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath(var1), (CursorFactory)null);
      }
   }

   public class DbOpenHelper extends SQLiteOpenHelper {
      public DbOpenHelper(Context var2, String var3, int var4) {
         super(var2, var3, (CursorFactory)null, var4);
      }

      public void onCreate(SQLiteDatabase var1) {
         DbOp.this.createDB(var1);
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         DbOp.this.upgradeDB(var1, var2, var3);
      }
   }
}
