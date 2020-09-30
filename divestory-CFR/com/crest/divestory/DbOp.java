/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.database.Cursor
 *  android.database.DatabaseErrorHandler
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.util.Log
 */
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
import android.util.Log;
import com.crest.divestory.DataStruct;
import com.syntak.library.FileOp;
import com.syntak.library.TimeOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private DbOpenHelper dbHelper = null;
    public String dbPath = null;

    DbOp(Context object) {
        context = object;
        object = object.getDatabasePath(DB_NAME).getPath();
        this.dbPath = object;
        this.dbFolder = FileOp.getFolderChainFromPath((String)object);
    }

    private void backupDB(String string2, String string3) throws IOException {
        FileOp.copyFile(string2, string3);
    }

    public static DbOp getInstance(Context context) {
        if (mInstance != null) return mInstance;
        mInstance = new DbOp(context.getApplicationContext());
        return mInstance;
    }

    public long addCharacteristics(ContentValues contentValues) {
        return db.insert(tableCharacteristics, null, contentValues);
    }

    public String checkDictionary(String string2, String string3, String string4) {
        Object object = db.query(tableDictionary, null, "language_code1 = ? AND text1 = ?  AND language_code2 = ? ", new String[]{string2, string3, string4}, null, null, null);
        object = object != null && object.moveToFirst() ? object.getString(object.getColumnIndex(columnText2)) : null;
        Object object2 = object;
        if (object != null) return object2;
        string2 = db.query(tableDictionary, null, "language_code2 = ? AND text2 = ?  AND language_code1 = ? ", new String[]{string2, string3, string4}, null, null, null);
        object2 = object;
        if (string2 == null) return object2;
        object2 = object;
        if (!string2.moveToFirst()) return object2;
        return string2.getString(string2.getColumnIndex(columnText1));
    }

    public void close() {
        DbOpenHelper dbOpenHelper = this.dbHelper;
        if (dbOpenHelper == null) {
            return;
        }
        dbOpenHelper.close();
        this.dbHelper = null;
    }

    public void createDB(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS android_metadata ( locale TEXT DEFAULT 'en_US')");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS my_watches (_id INTEGER PRIMARY KEY AUTOINCREMENT , id_model INTEGER , mac_address TEXT , model_name TEXT , model_name_to_show TEXT , ble_device_name TEXT , serial_number TEXT , hardware_version TEXT , firmware_version TEXT , last_dive_log_index INTEGER , last_dive_time INTEGER , scuba_dive_total_time INTEGER , scuba_dive_max_depth INTEGER , scuba_dive_times INTEGER , free_dive_longest_time INTEGER , free_dive_max_depth INTEGER , free_dive_times INTEGER , no_flight_time INTEGER , no_dive_time INTEGER , start_time_for_last_downloaded_log INTEGER DEFAULT 0  , end_time_for_last_downloaded_log INTEGER DEFAULT 0  ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS models (_id INTEGER PRIMARY KEY AUTOINCREMENT , model_name TEXT , hardware_version TEXT , firmware_version TEXT , last_download_date_time TEXT , icon TEXT , firmware_filename TEXT ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS watch_settings (_id INTEGER PRIMARY KEY AUTOINCREMENT , id_model TEXT , setting_name TEXT , data_type INTEGER , data_length INTEGER , value_int INTEGER , value_string TEXT , default_value TEXT ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS dive_logs (_id INTEGER PRIMARY KEY AUTOINCREMENT , serial_number INTEGER , dive_log_index INTEGER , dive_type INTEGER , profile_data_length INTEGER , start_time INTEGER , end_time INTEGER , duration INTEGER , sampling_rate INTEGER , max_depth INTEGER , lowest_water_temperature INTEGER , average_depth INTEGER , data_start_address INTEGER , data_end_address INTEGER , firmware_version TEXT , surface_ATM INTEGER , is_high_elevation_diving INTEGER , safety_factor INTEGER , PPO2 INTEGER , CNS_at_start INTEGER , max_CNS INTEGER , deco_model INTEGER , end_N2_compartment TEXT , end_H2_compartment TEXT , no_flight_time INTEGER , no_dive_time INTEGER , rating INTEGER , note TEXT , is_favorite INTEGER , location TEXT , breathing_gas TEXT , cylinder_capacity INTEGER , O2_ratio INTEGER , pressure_start INTEGER , pressure_end INTEGER , aux_weight INTEGER , visibility INTEGER , surface_temperature INTEGER , weather TEXT , wind TEXT , wave TEXT , buddy TEXT ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS dive_profile_data (_id INTEGER PRIMARY KEY AUTOINCREMENT , serial_number TEXT , dive_log_index INTEGER , start_time INTEGER , data_type INTEGER , depth INTEGER , temperature INTEGER , time_elapsed INTEGER , cns INTEGER , deco_state INTEGER , ceiling INTEGER , stop_time INTEGER , alarm_type INTEGER , time INTEGER , value INTEGER ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS brands (_id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , icon TEXT ) ");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS characteristics (_id INTEGER PRIMARY KEY AUTOINCREMENT ,mac_address TEXT  ,uuid_short_service TEXT  ,uuid_short_characteristic TEXT  ,uuid_service TEXT  ,uuid_characteristic TEXT )");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS dictionary (_id INTEGER PRIMARY KEY AUTOINCREMENT , language_code1 TEXT , text1 TEXT , language_code2 TEXT , text2 TEXT ) ");
            return;
        }
        catch (Exception exception) {
            Log.e((String)"Error", (String)"Error", (Throwable)exception);
            if (sQLiteDatabase == null) return;
            sQLiteDatabase.close();
        }
    }

    public void deleteAllCharacteristics() {
        db.delete(tableCharacteristics, null, null);
    }

    public void deleteDiveLogByStartTime(String string2, long l) {
        db.delete(tableDiveLogs, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)});
    }

    public void deleteDiveProfileDataByStartTime(String string2, long l) {
        db.delete(tableDiveProfileData, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)});
    }

    public void deleteMyWatchByMacAddress(String string2) {
        db.delete(tableMyWatches, "mac_address = ? ", new String[]{string2});
    }

    public void deleteMyWatchBySerialNumber(String string2) {
        db.delete(tableMyWatches, "serial_number = ? ", new String[]{string2});
    }

    public Cursor getBrand(long l) {
        Cursor cursor = null;
        if (l < 0L) {
            return null;
        }
        Cursor cursor2 = db.query(tableBrands, null, "_id = ?", new String[]{String.valueOf(l)}, null, null, null);
        if (cursor2 == null) return cursor2;
        if (cursor2.moveToFirst()) return cursor2;
        return cursor;
    }

    public Cursor getCharacteristic(String string2, String string3) {
        string2 = string3 = db.query(tableCharacteristics, null, "mac_address = ?  AND uuid_short_characteristic = ? ", new String[]{string2, string3}, null, null, null);
        if (string3 == null) return string2;
        string2 = string3;
        if (string3.moveToFirst()) return string2;
        return null;
    }

    public String getDbPath() {
        return this.dbPath;
    }

    public Cursor getDiveLogs() {
        Cursor cursor;
        Cursor cursor2 = cursor = db.query(tableDiveLogs, null, null, null, null, null, null);
        if (cursor == null) return cursor2;
        cursor2 = cursor;
        if (cursor.moveToFirst()) return cursor2;
        return null;
    }

    public Cursor getDiveLogsBySerialNumber(String string2) {
        Cursor cursor = db.query(tableDiveLogs, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor == null) return string2;
        string2 = cursor;
        if (cursor.moveToFirst()) return string2;
        return null;
    }

    public Cursor getDiveProfileData(String string2, long l) {
        Cursor cursor = db.query(tableDiveProfileData, null, "serial_number = ? AND start_time = ? ", new String[]{string2, String.valueOf(l)}, null, null, null);
        string2 = cursor;
        if (cursor == null) return string2;
        string2 = cursor;
        if (cursor.moveToFirst()) return string2;
        return null;
    }

    public String getFirmwareFilename(long l) {
        Cursor cursor = db.query(tableModels, null, "_id = ?", new String[]{String.valueOf(l)}, null, null, null);
        String string2 = null;
        Cursor cursor2 = cursor;
        if (cursor != null) {
            cursor2 = cursor;
            if (!cursor.moveToFirst()) {
                cursor2 = null;
            }
        }
        if (cursor2 == null) return string2;
        return cursor2.getString(cursor2.getColumnIndex(columnFirmwareFilename));
    }

    public String getFirmwareVersionFromModelName(String string2) {
        Cursor cursor = db.query(tableModels, null, "model_name = ?", new String[]{string2}, null, null, null);
        String string3 = null;
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return string3;
        return string2.getString(string2.getColumnIndex(columnFirmwareVersion));
    }

    public long getIdModelFromModelName(String string2) {
        Cursor cursor = db.query(tableModels, null, "model_name = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                return -1L;
            }
        }
        if (string2 == null) return -1L;
        return string2.getLong(string2.getColumnIndex(columnId));
    }

    public String getModelName(long l) {
        Cursor cursor = db.query(tableModels, null, "_id = ?", new String[]{String.valueOf(l)}, null, null, null);
        String string2 = null;
        Cursor cursor2 = cursor;
        if (cursor != null) {
            cursor2 = cursor;
            if (!cursor.moveToFirst()) {
                cursor2 = null;
            }
        }
        if (cursor2 == null) return string2;
        return cursor2.getString(cursor2.getColumnIndex(columnModelName));
    }

    public Cursor getMyWatch(String string2) {
        Object var2_2 = null;
        if (string2 == null) {
            return null;
        }
        String[] arrstring = new String[]{string2};
        string2 = db.query(tableMyWatches, null, "serial_number = ?", arrstring, null, null, null);
        if (string2 == null) return string2;
        if (string2.moveToFirst()) return string2;
        return var2_2;
    }

    public long getMyWatchEndTimeForLastDownloadedLog(String string2) {
        long l = -1L;
        if (string2 == null) {
            return -1L;
        }
        Cursor cursor = db.query(tableMyWatches, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return l;
        return string2.getLong(string2.getColumnIndex(columnEndTimeForLastDownloadedLog));
    }

    public String getMyWatchFirmwareVersion(String string2) {
        String string3 = null;
        if (string2 == null) {
            return null;
        }
        Cursor cursor = db.query(tableMyWatches, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return string3;
        return string2.getString(string2.getColumnIndex(columnFirmwareVersion));
    }

    public int getMyWatchLastDiveLogIndex(String string2) {
        int n = -1;
        if (string2 == null) {
            return -1;
        }
        Cursor cursor = db.query(tableMyWatches, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return n;
        return string2.getInt(string2.getColumnIndex(columnLastDiveLogIndex));
    }

    public long getMyWatchStartTimeForLastDownloadedLog(String string2) {
        long l = -1L;
        if (string2 == null) {
            return -1L;
        }
        Cursor cursor = db.query(tableMyWatches, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor != null) {
            string2 = cursor;
            if (!cursor.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return l;
        return string2.getLong(string2.getColumnIndex(columnStartTimeForLastDownloadedLog));
    }

    public Cursor getMyWatches() {
        Cursor cursor;
        Cursor cursor2 = cursor = db.query(tableMyWatches, null, null, null, null, null, null);
        if (cursor == null) return cursor2;
        cursor2 = cursor;
        if (cursor.moveToFirst()) return cursor2;
        return null;
    }

    public long insertDictionary(DataStruct.Dictionary dictionary) {
        Cursor cursor = db.query(tableDictionary, null, "language_code1 = ? AND text1 = ? ", new String[]{dictionary.languageCode1, dictionary.text1}, null, null, null);
        if (cursor == null) return db.insert(tableDictionary, null, dictionary.toContentValues());
        if (!cursor.moveToFirst()) return db.insert(tableDictionary, null, dictionary.toContentValues());
        return -1L;
    }

    public long insertDiveLog(DataStruct.DiveLog diveLog) {
        return db.insert(tableDiveLogs, null, diveLog.watchContentValues());
    }

    public void insertDiveProfileData(DataStruct.DiveProfileData diveProfileData) {
        if (diveProfileData.length() <= 0) return;
        int n = 0;
        while (n < diveProfileData.length()) {
            this.insertDiveProfileDatum(diveProfileData.list.get(n));
            ++n;
        }
    }

    public long insertDiveProfileDatum(DataStruct.DiveProfileDatum diveProfileDatum) {
        return db.insert(tableDiveProfileData, null, diveProfileDatum.toContentValues());
    }

    public long insertModel(DataStruct.Model model) {
        return db.insert(tableModels, null, model.toContentValues());
    }

    public long insertMyWatch(DataStruct.MyWatch myWatch) {
        return db.insert(tableMyWatches, null, myWatch.toContentValues());
    }

    public void insertMyWatches(DataStruct.MyWatches myWatches) {
        if (myWatches.length() <= 0) return;
        int n = 0;
        while (n < myWatches.length()) {
            if (!this.isMyWatchLogged(myWatches.list.get((int)n).serial_number)) {
                this.insertMyWatch(myWatches.list.get(n));
            }
            ++n;
        }
    }

    public boolean isAnyCharacteristic(String string2) {
        if (this.readCharacteristics(string2) == null) return false;
        return true;
    }

    public boolean isDiveLogExisted(String string2, long l) {
        SQLiteDatabase sQLiteDatabase = db;
        boolean bl = false;
        sQLiteDatabase = sQLiteDatabase.query(tableDiveLogs, null, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)}, null, null, null);
        string2 = sQLiteDatabase;
        if (sQLiteDatabase != null) {
            string2 = sQLiteDatabase;
            if (!sQLiteDatabase.moveToFirst()) {
                string2 = null;
            }
        }
        if (string2 == null) return bl;
        return true;
    }

    public boolean isMyWatchLogged(String string2) {
        SQLiteDatabase sQLiteDatabase = db;
        boolean bl = true;
        sQLiteDatabase = sQLiteDatabase.query(tableMyWatches, null, "serial_number = ?", new String[]{string2}, null, null, null);
        string2 = sQLiteDatabase;
        if (sQLiteDatabase != null) {
            string2 = sQLiteDatabase;
            if (!sQLiteDatabase.moveToFirst()) {
                return false;
            }
        }
        if (string2 == null) return false;
        return bl;
    }

    public void open() throws SQLException {
        DbOpenHelper dbOpenHelper;
        this.dbHelper = dbOpenHelper = new DbOpenHelper(context, DB_NAME, 2);
        db = dbOpenHelper.getWritableDatabase();
    }

    public Cursor readBrands() {
        Cursor cursor;
        Cursor cursor2 = cursor = db.query(tableBrands, null, null, null, null, null, null);
        if (cursor == null) return cursor2;
        cursor2 = cursor;
        if (cursor.moveToFirst()) return cursor2;
        return null;
    }

    public Cursor readCharacteristics(String string2) {
        Cursor cursor = db.query(tableCharacteristics, null, "mac_address= ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor == null) return string2;
        string2 = cursor;
        if (cursor.moveToFirst()) return string2;
        return null;
    }

    public Cursor readModelByModelName(String string2) {
        Cursor cursor = db.query(tableModels, null, "model_name = ?", new String[]{string2}, null, null, null);
        string2 = cursor;
        if (cursor == null) return string2;
        string2 = cursor;
        if (cursor.moveToFirst()) return string2;
        return null;
    }

    public boolean updateDiveLog(DataStruct.DiveLog diveLog) {
        String string2 = diveLog.watch_serial_number;
        long l = diveLog.start_time;
        diveLog = diveLog.editContentValues();
        if ((long)db.update(tableDiveLogs, (ContentValues)diveLog, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)}) < 0L) return false;
        return true;
    }

    public boolean updateDiveLogFavorite(String string2, long l, boolean bl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnIsFavorite, Integer.valueOf((int)bl));
        if ((long)db.update(tableDiveLogs, contentValues, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)}) < 0L) return false;
        return true;
    }

    public boolean updateDiveLogFromProfile(DataStruct.DiveLog diveLog) {
        String string2 = diveLog.watch_serial_number;
        long l = diveLog.start_time;
        diveLog = diveLog.profileContentValues();
        if ((long)db.update(tableDiveLogs, (ContentValues)diveLog, "serial_number = ?  AND start_time = ? ", new String[]{string2, String.valueOf(l)}) < 0L) return false;
        return true;
    }

    public boolean updateMyWatch(DataStruct.MyWatch myWatch) {
        if ((long)db.update(tableMyWatches, myWatch.toContentValues(), "serial_number = ? ", new String[]{myWatch.serial_number}) < 0L) return false;
        return true;
    }

    public boolean updateMyWatchLastDiveLogIndex(String string2, long l) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnLastDiveLogIndex, Long.valueOf(l));
        if ((long)db.update(tableMyWatches, contentValues, "serial_number = ? ", new String[]{string2}) < 0L) return false;
        return true;
    }

    public boolean updateMyWatchTimeForLastDownloadedLog(String string2, long l, long l2) {
        if (l <= this.getMyWatchStartTimeForLastDownloadedLog(string2)) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnStartTimeForLastDownloadedLog, Long.valueOf(l));
        contentValues.put(columnEndTimeForLastDownloadedLog, Long.valueOf(l + l2));
        if ((long)db.update(tableMyWatches, contentValues, "serial_number = ? ", new String[]{string2}) < 0L) return false;
        return true;
    }

    public void upgradeDB(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                return;
            }
        } else {
            try {
                sQLiteDatabase.execSQL("ALTER TABLE my_watches ADD COLUMN end_time_for_last_downloaded_log INTEGER default 0");
            }
            catch (SQLiteException sQLiteException) {
                Log.w((String)"Upgrade DB 1", (String)sQLiteException.getMessage());
            }
        }
        try {
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN start_year INTEGER default 0");
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN start_month INTEGER default 0");
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN start_day INTEGER default 0");
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN start_hour INTEGER default 0");
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN start_minute INTEGER default 0");
            sQLiteDatabase.execSQL("ALTER TABLE dive_logs ADD COLUMN utc_offset INTEGER default 0");
            n = TimeOp.get_UTC_offset();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE dive_logs SET utc_offset = ");
            stringBuilder.append(n);
            sQLiteDatabase.execSQL(stringBuilder.toString());
            return;
        }
        catch (SQLiteException sQLiteException) {
            Log.w((String)"Upgrade DB 3", (String)sQLiteException.getMessage());
        }
    }

    class DatabaseContext
    extends ContextWrapper {
        private static final String DEBUG_CONTEXT = "DatabaseContext";
        String folder;

        public DatabaseContext(Context context, String string2) {
            super(context);
            this.folder = string2;
        }

        public File getDatabasePath(String object) {
            boolean bl;
            if (this.folder == null) {
                return super.getDatabasePath((String)object);
            }
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.folder);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append((String)object);
            charSequence = ((StringBuilder)charSequence).toString();
            object = charSequence;
            if (!((String)charSequence).endsWith(DbOp.EXT_DB)) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(DbOp.EXT_DB);
                object = ((StringBuilder)object).toString();
            }
            boolean bl2 = false;
            if (!((File)(object = new File((String)object))).getParentFile().exists()) {
                ((File)object).getParentFile().mkdirs();
            }
            if (!((File)object).exists()) {
                try {
                    bl = ((File)object).createNewFile();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    bl = bl2;
                }
            } else {
                bl = true;
            }
            if (!bl) return null;
            return object;
        }

        public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
            return SQLiteDatabase.openOrCreateDatabase((File)this.getDatabasePath(string2), null);
        }
    }

    public class DbOpenHelper
    extends SQLiteOpenHelper {
        public DbOpenHelper(Context context, String string2, int n) {
            super(context, string2, null, n);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            DbOp.this.createDB(sQLiteDatabase);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            DbOp.this.upgradeDB(sQLiteDatabase, n, n2);
        }
    }

}

