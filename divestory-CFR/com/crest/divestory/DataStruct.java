/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crest.divestory;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.crest.divestory.WatchOp;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.TimeOp;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataStruct {

    public static class BleDevice {
        boolean isStored = false;
        public String mac_address;
        public String name;
        public int rssi;
        public String serial_number;

        public BleDevice(String string2, String string3, int n, boolean bl) {
            this.mac_address = string2;
            this.name = string3;
            this.rssi = n;
            this.isStored = bl;
        }

        public boolean isStored() {
            return this.isStored;
        }

        public void setStored(boolean bl) {
            this.isStored = bl;
        }
    }

    public static class BleDevices {
        public ArrayList<BleDevice> list;

        public BleDevices() {
            this.list = new ArrayList();
        }

        BleDevices(ArrayList<BleDevice> arrayList) {
            this.list = arrayList;
        }

        public void add(BleDevice bleDevice) {
            this.list.add(bleDevice);
        }

        public void delete(int n) {
            this.list.remove(n);
        }

        public void delete(String string2) {
            int n = 0;
            while (n < this.list.size()) {
                if (this.list.get((int)n).mac_address == string2) {
                    this.list.remove(n);
                }
                ++n;
            }
        }

        public int length() {
            ArrayList<BleDevice> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }

        public void update(int n, BleDevice bleDevice) {
            this.list.set(n, bleDevice);
        }
    }

    public static class Brand {
        public String icon;
        public long idBrand;
        public String name;

        Brand(long l, String string2, String string3) {
            this.idBrand = l;
            this.name = string2;
            this.icon = string3;
        }

        public Brand(Cursor cursor) {
            this.idBrand = cursor.getLong(cursor.getColumnIndex("_id"));
            this.name = cursor.getString(cursor.getColumnIndex("name"));
            this.icon = cursor.getString(cursor.getColumnIndex("icon"));
        }

        Brand(JSONObject jSONObject) {
            try {
                this.idBrand = jSONObject.getLong("idBrand");
                this.name = jSONObject.getString("name");
                this.icon = jSONObject.getString("icon");
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Brand", (String)jSONException.toString());
            }
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", this.name);
            contentValues.put("icon", this.icon);
            return contentValues;
        }
    }

    public static class Brands {
        ArrayList<Brand> list;

        Brands(ArrayList<Brand> arrayList) {
            this.list = arrayList;
        }

        Brands(JSONObject jSONObject) {
            this.list = new ArrayList();
            try {
                jSONObject = jSONObject.getJSONArray("items_array");
                int n = 0;
                while (n < jSONObject.length()) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(n);
                    ArrayList<Brand> arrayList = this.list;
                    Brand brand = new Brand(jSONObject2);
                    arrayList.add(brand);
                    ++n;
                }
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Brands", (String)jSONException.toString());
            }
        }

        public int length() {
            ArrayList<Brand> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }
    }

    public static final class CONNECTION_STATUS
    extends Enum<CONNECTION_STATUS> {
        private static final /* synthetic */ CONNECTION_STATUS[] $VALUES;
        public static final /* enum */ CONNECTION_STATUS CONNECTED;
        public static final /* enum */ CONNECTION_STATUS CONNECTING;
        public static final /* enum */ CONNECTION_STATUS DISCONNECTED;
        public static final /* enum */ CONNECTION_STATUS SCANNED;
        public static final /* enum */ CONNECTION_STATUS UNKNOWN;

        static {
            CONNECTION_STATUS cONNECTION_STATUS;
            UNKNOWN = new CONNECTION_STATUS();
            SCANNED = new CONNECTION_STATUS();
            CONNECTING = new CONNECTION_STATUS();
            CONNECTED = new CONNECTION_STATUS();
            DISCONNECTED = cONNECTION_STATUS = new CONNECTION_STATUS();
            $VALUES = new CONNECTION_STATUS[]{UNKNOWN, SCANNED, CONNECTING, CONNECTED, cONNECTION_STATUS};
        }

        public static CONNECTION_STATUS valueOf(String string2) {
            return Enum.valueOf(CONNECTION_STATUS.class, string2);
        }

        public static CONNECTION_STATUS[] values() {
            return (CONNECTION_STATUS[])$VALUES.clone();
        }
    }

    public static class Dictionary {
        String languageCode1;
        String languageCode2;
        String text1;
        String text2;

        Dictionary(String string2, String string3, String string4, String string5) {
            this.languageCode1 = string2;
            this.text1 = string3;
            this.languageCode2 = string4;
            this.text2 = string5;
        }

        Dictionary(JSONObject jSONObject) {
            try {
                this.languageCode1 = jSONObject.getString("language_code_source");
                this.text1 = jSONObject.getString("text_source");
                this.languageCode2 = jSONObject.getString("language_code_target");
                this.text2 = jSONObject.getString("text_target");
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Language", (String)jSONException.toString());
            }
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("language_code1", this.languageCode1);
            contentValues.put("text1", this.text1);
            contentValues.put("language_code2", this.languageCode2);
            contentValues.put("text2", this.text2);
            return contentValues;
        }
    }

    public static class DiveLog {
        public long CNS_at_start;
        public int O2_ratio;
        public long PPO2;
        public float aux_weight;
        public long average_depth;
        public String breathing_gas;
        public String buddy;
        public String comment;
        public float cylinder_capacity;
        public long data_end_address;
        public long data_start_address;
        public long deco_model;
        public long dive_log_index;
        public long dive_type;
        public long duration;
        public int[] end_H2_compartment = new int[16];
        public byte[] end_H2_compartment_bytes = new byte[32];
        public int[] end_N2_compartment = new int[16];
        public byte[] end_N2_compartment_bytes = new byte[32];
        public long end_time;
        public String firmware_version;
        public long id;
        public boolean isDownloaded;
        public boolean isFavorite;
        public boolean isStored;
        public long is_high_elevation_diving;
        public String location;
        public long lowest_water_temperature;
        public long max_CNS;
        public long max_depth;
        public long no_dive_time;
        public long no_fligt_fime;
        public String note;
        public int pressure_end;
        public int pressure_start;
        public long profile_data_length;
        public int rating;
        public long safety_factor;
        public long sampling_rate;
        public long start_time;
        public long surface_ATM;
        public float surface_temperature;
        public float visibility;
        public String watch_serial_number;
        public String wave;
        public String weather;
        public String wind;

        public DiveLog(Cursor cursor) {
            boolean bl = false;
            this.isFavorite = false;
            this.isStored = false;
            this.watch_serial_number = null;
            this.surface_temperature = 0.0f;
            this.location = null;
            this.breathing_gas = null;
            this.cylinder_capacity = WatchOp.DEFAULT_CYLINDER_CAPACITY;
            this.O2_ratio = 0;
            this.pressure_start = 0;
            this.pressure_end = 0;
            this.aux_weight = 0.0f;
            this.visibility = 0.0f;
            this.note = null;
            this.rating = 0;
            this.weather = null;
            this.wind = null;
            this.wave = null;
            this.buddy = null;
            this.isDownloaded = false;
            this.id = cursor.getLong(cursor.getColumnIndex("_id"));
            this.watch_serial_number = cursor.getString(cursor.getColumnIndex("serial_number"));
            this.dive_log_index = cursor.getLong(cursor.getColumnIndex("dive_log_index"));
            this.dive_type = cursor.getLong(cursor.getColumnIndex("dive_type"));
            this.profile_data_length = cursor.getLong(cursor.getColumnIndex("profile_data_length"));
            this.start_time = cursor.getLong(cursor.getColumnIndex("start_time"));
            this.end_time = cursor.getLong(cursor.getColumnIndex("end_time"));
            this.duration = cursor.getLong(cursor.getColumnIndex("duration"));
            this.sampling_rate = cursor.getLong(cursor.getColumnIndex("sampling_rate"));
            this.max_depth = cursor.getLong(cursor.getColumnIndex("max_depth"));
            this.lowest_water_temperature = cursor.getLong(cursor.getColumnIndex("lowest_water_temperature"));
            this.average_depth = cursor.getLong(cursor.getColumnIndex("average_depth"));
            this.data_start_address = cursor.getLong(cursor.getColumnIndex("data_start_address"));
            this.data_end_address = cursor.getLong(cursor.getColumnIndex("data_end_address"));
            this.firmware_version = cursor.getString(cursor.getColumnIndex("firmware_version"));
            this.surface_ATM = cursor.getLong(cursor.getColumnIndex("surface_ATM"));
            this.is_high_elevation_diving = cursor.getLong(cursor.getColumnIndex("is_high_elevation_diving"));
            this.safety_factor = cursor.getLong(cursor.getColumnIndex("safety_factor"));
            this.PPO2 = cursor.getLong(cursor.getColumnIndex("PPO2"));
            this.CNS_at_start = cursor.getLong(cursor.getColumnIndex("CNS_at_start"));
            this.max_CNS = cursor.getInt(cursor.getColumnIndex("max_CNS"));
            this.deco_model = cursor.getLong(cursor.getColumnIndex("deco_model"));
            this.no_fligt_fime = cursor.getLong(cursor.getColumnIndex("no_flight_time"));
            this.no_dive_time = cursor.getLong(cursor.getColumnIndex("no_dive_time"));
            this.end_N2_compartment_bytes = ByteOp.get_BytesArray_from_HexString(cursor.getString(cursor.getColumnIndex("end_N2_compartment")));
            this.end_H2_compartment_bytes = ByteOp.get_BytesArray_from_HexString(cursor.getString(cursor.getColumnIndex("end_H2_compartment")));
            if (cursor.getInt(cursor.getColumnIndex("is_favorite")) > 0) {
                bl = true;
            }
            this.isFavorite = bl;
            this.rating = cursor.getInt(cursor.getColumnIndex("rating"));
            this.note = cursor.getString(cursor.getColumnIndex("note"));
            this.location = cursor.getString(cursor.getColumnIndex("location"));
            this.breathing_gas = cursor.getString(cursor.getColumnIndex("breathing_gas"));
            this.cylinder_capacity = cursor.getFloat(cursor.getColumnIndex("cylinder_capacity"));
            this.O2_ratio = cursor.getInt(cursor.getColumnIndex("O2_ratio"));
            this.pressure_start = cursor.getInt(cursor.getColumnIndex("pressure_start"));
            this.pressure_end = cursor.getInt(cursor.getColumnIndex("pressure_end"));
            this.aux_weight = cursor.getFloat(cursor.getColumnIndex("aux_weight"));
            this.visibility = cursor.getFloat(cursor.getColumnIndex("visibility"));
            this.surface_temperature = cursor.getFloat(cursor.getColumnIndex("surface_temperature"));
            this.weather = cursor.getString(cursor.getColumnIndex("weather"));
            this.wind = cursor.getString(cursor.getColumnIndex("wind"));
            this.wave = cursor.getString(cursor.getColumnIndex("wave"));
            this.buddy = cursor.getString(cursor.getColumnIndex("buddy"));
        }

        public DiveLog(String arrby, byte[] arrby2) {
            long l;
            int n;
            this.isFavorite = false;
            this.isStored = false;
            this.watch_serial_number = null;
            this.surface_temperature = 0.0f;
            this.location = null;
            this.breathing_gas = null;
            this.cylinder_capacity = WatchOp.DEFAULT_CYLINDER_CAPACITY;
            this.O2_ratio = 0;
            this.pressure_start = 0;
            this.pressure_end = 0;
            this.aux_weight = 0.0f;
            this.visibility = 0.0f;
            this.note = null;
            this.rating = 0;
            this.weather = null;
            this.wind = null;
            this.wave = null;
            this.buddy = null;
            this.isDownloaded = false;
            this.watch_serial_number = arrby;
            arrby = new byte[4];
            byte[] arrby3 = new byte[2];
            byte[] arrby4 = new byte[6];
            int n2 = 0;
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.dive_log_index = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.dive_type = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.profile_data_length = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            n = ByteOp.b2ui(arrby2[n2]);
            int n3 = ByteOp.b2ui(arrby2[++n2]);
            int n4 = ByteOp.b2ui(arrby2[++n2]);
            int n5 = ByteOp.b2ui(arrby2[++n2]);
            int n6 = ByteOp.b2ui(arrby2[++n2]);
            n2 += 4;
            this.start_time = TimeOp.DateTimeToMs(n + 2000, n3 - 1, n4, n5, n6);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.duration = l = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            this.end_time = this.start_time + l * 1000L;
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.sampling_rate = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.max_depth = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.lowest_water_temperature = ByteOp.int32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.average_depth = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.data_start_address = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.data_end_address = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 6; ++n, ++n2) {
                arrby4[n] = arrby2[n2];
            }
            this.firmware_version = StringOp.ByteArrayToString(arrby4);
            n2 += 2;
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.surface_ATM = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.is_high_elevation_diving = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.safety_factor = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.PPO2 = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.CNS_at_start = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.max_CNS = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.deco_model = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            for (n = 0; n < 32; ++n) {
                this.end_N2_compartment_bytes[n] = arrby2[n2 + n];
            }
            for (n = 0; n < 16; ++n, n2 += 2) {
                arrby3[0] = arrby2[n2];
                arrby3[1] = arrby2[n2 + 1];
                this.end_N2_compartment[n] = ByteOp.uint16ToInt(arrby3, WatchOp.byteOrder);
            }
            for (n = 0; n < 32; ++n) {
                this.end_H2_compartment_bytes[n] = arrby2[n2 + n];
            }
            for (n = 0; n < 16; ++n, n2 += 2) {
                arrby3[0] = arrby2[n2];
                arrby3[1] = arrby2[n2 + 1];
                this.end_H2_compartment[n] = ByteOp.uint16ToInt(arrby3, WatchOp.byteOrder);
            }
            n3 = 0;
            for (n = 0; n < 4; ++n, ++n2) {
                arrby[n] = arrby2[n2];
            }
            this.no_fligt_fime = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
            n = n3;
            do {
                if (n >= 4) {
                    this.no_dive_time = ByteOp.uint32ToLong(arrby, WatchOp.byteOrder);
                    return;
                }
                arrby[n] = arrby2[n2];
                ++n;
                ++n2;
            } while (true);
        }

        public ContentValues editContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_favorite", Integer.valueOf((int)this.isFavorite));
            contentValues.put("rating", Integer.valueOf(this.rating));
            contentValues.put("note", this.note);
            contentValues.put("location", this.location);
            contentValues.put("breathing_gas", this.breathing_gas);
            contentValues.put("cylinder_capacity", Float.valueOf(this.cylinder_capacity));
            contentValues.put("O2_ratio", Integer.valueOf(this.O2_ratio));
            contentValues.put("pressure_start", Integer.valueOf(this.pressure_start));
            contentValues.put("pressure_end", Integer.valueOf(this.pressure_end));
            contentValues.put("aux_weight", Float.valueOf(this.aux_weight));
            contentValues.put("visibility", Float.valueOf(this.visibility));
            contentValues.put("weather", this.weather);
            contentValues.put("wind", this.wind);
            contentValues.put("wave", this.wave);
            contentValues.put("buddy", this.buddy);
            contentValues.put("surface_temperature", Float.valueOf(this.surface_temperature));
            return contentValues;
        }

        public ContentValues profileContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("surface_temperature", Float.valueOf(this.surface_temperature));
            contentValues.put("max_depth", Long.valueOf(this.max_depth));
            return contentValues;
        }

        public ContentValues watchContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serial_number", this.watch_serial_number);
            contentValues.put("dive_log_index", Long.valueOf(this.dive_log_index));
            contentValues.put("dive_type", Long.valueOf(this.dive_type));
            contentValues.put("profile_data_length", Long.valueOf(this.profile_data_length));
            contentValues.put("start_time", Long.valueOf(this.start_time));
            contentValues.put("end_time", Long.valueOf(this.end_time));
            contentValues.put("duration", Long.valueOf(this.duration));
            contentValues.put("sampling_rate", Long.valueOf(this.sampling_rate));
            contentValues.put("max_depth", Long.valueOf(this.max_depth));
            contentValues.put("lowest_water_temperature", Long.valueOf(this.lowest_water_temperature));
            contentValues.put("average_depth", Long.valueOf(this.average_depth));
            contentValues.put("data_start_address", Long.valueOf(this.data_start_address));
            contentValues.put("data_end_address", Long.valueOf(this.data_end_address));
            contentValues.put("firmware_version", this.firmware_version);
            contentValues.put("surface_ATM", Long.valueOf(this.surface_ATM));
            contentValues.put("is_high_elevation_diving", Long.valueOf(this.is_high_elevation_diving));
            contentValues.put("safety_factor", Long.valueOf(this.safety_factor));
            contentValues.put("PPO2", Long.valueOf(this.PPO2));
            contentValues.put("CNS_at_start", Long.valueOf(this.CNS_at_start));
            contentValues.put("max_CNS", Long.valueOf(this.max_CNS));
            contentValues.put("deco_model", Long.valueOf(this.deco_model));
            contentValues.put("no_flight_time", Long.valueOf(this.no_fligt_fime));
            contentValues.put("no_dive_time", Long.valueOf(this.no_dive_time));
            contentValues.put("surface_temperature", Float.valueOf(this.surface_temperature));
            contentValues.put("end_N2_compartment", ByteOp.get_HexString_from_BytesArray(this.end_N2_compartment_bytes));
            contentValues.put("end_H2_compartment", ByteOp.get_HexString_from_BytesArray(this.end_H2_compartment_bytes));
            return contentValues;
        }
    }

    public static class DiveLogs {
        public ArrayList<DiveLog> list;

        public DiveLogs() {
            this.list = new ArrayList();
        }

        public DiveLogs(Cursor cursor) {
            this.list = new ArrayList();
            if (cursor == null) return;
            if (cursor.getCount() == 0) {
                return;
            }
            int n = 0;
            while (n < cursor.getCount()) {
                this.list.add(new DiveLog(cursor));
                cursor.moveToNext();
                ++n;
            }
        }

        public DiveLogs(ArrayList<DiveLog> arrayList) {
            this.list = arrayList;
        }

        public void addDiveLog(DiveLog diveLog) {
            this.list.add(diveLog);
        }

        public void deleteDiveLog(int n) {
            this.list.remove(n);
        }

        public void deleteDiveLogByStartTime(String string2, long l) {
            this.deleteDiveLogInListByStartTime(this.list, string2, l);
        }

        public void deleteDiveLogInListByStartTime(ArrayList<DiveLog> arrayList, String string2, long l) {
            if (string2 == null) return;
            if (arrayList == null) {
                return;
            }
            int n = 0;
            while (n < arrayList.size()) {
                if (string2.equals(arrayList.get((int)n).watch_serial_number) && arrayList.get((int)n).start_time == l) {
                    arrayList.remove(n);
                    arrayList.size();
                    return;
                }
                ++n;
            }
        }

        public ArrayList<DiveLog> duplicateDiveLogsList() {
            ArrayList<DiveLog> arrayList = new ArrayList<DiveLog>();
            int n = 0;
            while (n < this.list.size()) {
                arrayList.add(this.list.get(n));
                ++n;
            }
            return arrayList;
        }

        public DiveLog get(int n) {
            return this.list.get(n);
        }

        public int length() {
            ArrayList<DiveLog> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }

        public int total_dive_profile_data_length() {
            Object object = this.list;
            int n = 0;
            int n2 = 0;
            int n3 = n;
            if (object == null) return n3;
            n3 = n;
            if (((ArrayList)object).size() <= 0) return n3;
            Iterator<DiveLog> iterator2 = this.list.iterator();
            do {
                n3 = n2;
                if (!iterator2.hasNext()) return n3;
                object = iterator2.next();
                n2 = (int)((long)n2 + ((DiveLog)object).profile_data_length);
            } while (true);
        }

        public void updateDive(int n, DiveLog diveLog) {
            this.list.set(n, diveLog);
        }

        public void updateDiveLog(DiveLog diveLog) {
            if (diveLog == null) {
                return;
            }
            long l = diveLog.id;
            String string2 = diveLog.watch_serial_number;
            l = diveLog.start_time;
            int n = 0;
            while (n < this.list.size()) {
                if (string2.equals(this.list.get((int)n).watch_serial_number) && this.list.get((int)n).start_time == l) {
                    this.list.set(n, diveLog);
                    this.list.size();
                    return;
                }
                ++n;
            }
        }
    }

    public static class DiveProfileData {
        public ArrayList<DiveProfileDatum> list;

        public DiveProfileData() {
            this.list = new ArrayList();
        }

        public DiveProfileData(Cursor cursor) {
            this.list = new ArrayList();
            if (cursor == null) {
                return;
            }
            int n = 0;
            while (n < cursor.getCount()) {
                this.list.add(new DiveProfileDatum(cursor));
                cursor.moveToNext();
                ++n;
            }
        }

        /*
         * Unable to fully structure code
         */
        public DiveProfileData(DiveLog var1_1, byte[] var2_2, String var3_3) {
            super();
            this.list = new ArrayList<E>();
            var4_4 = var1_1.firmware_version;
            var5_5 = (int)var1_1.dive_log_index;
            var6_6 = var1_1.start_time;
            var8_7 = var1_1.sampling_rate;
            if (var1_1.dive_type == 2L) {
                var8_7 = 0.5f;
            }
            var9_8 = null;
            var10_9 = new byte[2];
            var11_10 = WatchOp.isOldFirmware(var4_4);
            var12_11 = 0;
            var13_12 = 0;
            var14_13 = true;
            var15_14 = false;
            var16_15 = 0;
            var17_16 = 0;
            var18_17 = 0;
            var19_18 = 0;
            do {
                block23 : {
                    block30 : {
                        block36 : {
                            block31 : {
                                block26 : {
                                    block35 : {
                                        block32 : {
                                            block34 : {
                                                block33 : {
                                                    block20 : {
                                                        block29 : {
                                                            block21 : {
                                                                block27 : {
                                                                    block28 : {
                                                                        block24 : {
                                                                            block25 : {
                                                                                block22 : {
                                                                                    if (var2_2.length - var12_11 < 6) {
                                                                                        var1_1.average_depth = var13_12;
                                                                                        return;
                                                                                    }
                                                                                    if (var2_2.length - var12_11 <= 8) {
                                                                                        var15_14 = true;
                                                                                    }
                                                                                    if (var14_13) {
                                                                                        var9_8 = new DiveProfileDatum();
                                                                                        var9_8.watch_serial_number = var3_3;
                                                                                        var9_8.dive_log_index = var5_5;
                                                                                        var9_8.start_time = var6_6;
                                                                                        var14_13 = false;
                                                                                    }
                                                                                    var9_8.data_type = var2_2[var12_11];
                                                                                    if (!var11_10) break block20;
                                                                                    var20_19 = var9_8.data_type;
                                                                                    if (var20_19 == 1) break block21;
                                                                                    if (var20_19 == 2) break block22;
                                                                                    var20_19 = var15_14 ? var2_2.length : var12_11 + 1;
                                                                                    break block23;
                                                                                }
                                                                                var10_9[0] = var2_2[var12_11 + 2];
                                                                                var10_9[1] = var2_2[var12_11 + 3];
                                                                                var9_8.depth = ByteOp.uint16ToInt(var10_9, WatchOp.byteOrder);
                                                                                var10_9[0] = var2_2[var12_11 + 4];
                                                                                var10_9[1] = var2_2[var12_11 + 5];
                                                                                var20_19 = ByteOp.uint16ToInt(var10_9, WatchOp.byteOrder);
                                                                                if (var20_19 <= 1300) break block24;
                                                                                if (!var15_14) break block25;
                                                                                var20_19 = var2_2.length;
                                                                                break block23;
                                                                            }
                                                                            var20_19 = var12_11 + 8;
                                                                            if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 3) break block26;
                                                                            break block23;
                                                                        }
                                                                        if (var20_19 >= 10) break block27;
                                                                        if (!var15_14) break block28;
                                                                        var20_19 = var2_2.length;
                                                                        break block23;
                                                                    }
                                                                    var20_19 = var12_11 + 6;
                                                                    if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 3) break block26;
                                                                    break block23;
                                                                }
                                                                var9_8.temperature = var20_19;
                                                                if (var15_14) {
                                                                    var20_19 = var2_2.length;
                                                                } else {
                                                                    var20_19 = var12_11 + 6;
                                                                    if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 3) {
                                                                        var20_19 = var12_11 + 1;
                                                                    }
                                                                }
                                                                var17_16 = 1;
                                                                break block23;
                                                            }
                                                            if (!var15_14) break block29;
                                                            var20_19 = var2_2.length;
                                                            break block23;
                                                        }
                                                        var20_19 = var12_11 + 8;
                                                        if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 3) break block26;
                                                        break block23;
                                                    }
                                                    var20_19 = var9_8.data_type;
                                                    if (var20_19 == 1) break block30;
                                                    if (var20_19 == 2) break block31;
                                                    if (var20_19 == 3) break block32;
                                                    if (var20_19 == 4) break block33;
                                                    var20_19 = var15_14 != false ? var2_2.length : var12_11 + 1;
                                                    Log.d((String)"Error", (String)"unknown data_type");
                                                    break block23;
                                                }
                                                if (!var15_14) break block34;
                                                var20_19 = var2_2.length;
                                                break block23;
                                            }
                                            var20_19 = var12_11 + 8;
                                            if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 5) break block26;
                                            break block23;
                                        }
                                        if (!var15_14) break block35;
                                        var20_19 = var2_2.length;
                                        break block23;
                                    }
                                    var20_19 = var12_11 + 6;
                                    if (var2_2[var20_19] > 0 && var2_2[var20_19] < 5) break block23;
                                }
                                var20_19 = var12_11 + 1;
                                break block23;
                            }
                            var10_9[0] = var2_2[var12_11 + 2];
                            var10_9[1] = var2_2[var12_11 + 3];
                            var9_8.depth = ByteOp.uint16ToInt(var10_9, WatchOp.byteOrder);
                            if (var9_8.depth > 10000) {
                                var9_8.depth = 10000;
                            }
                            if (var9_8.depth < 1000) {
                                var9_8.depth = 1000;
                            }
                            var20_19 = var9_8.depth > var16_15 ? var9_8.depth : var16_15;
                            var10_9[0] = var2_2[var12_11 + 4];
                            var10_9[1] = var2_2[var12_11 + 5];
                            var9_8.temperature = ByteOp.uint16ToInt(var10_9, WatchOp.byteOrder);
                            if (var9_8.temperature > 500) {
                                var9_8.temperature = 500;
                            }
                            if (!var15_14) break block36;
                            var16_15 = var2_2.length;
                            ** GOTO lbl-1000
                        }
                        var16_15 = var12_11 + 6;
                        if (var2_2[var16_15] > 0 && var2_2[var16_15] < 5) lbl-1000: // 2 sources:
                        {
                            var17_16 = var16_15;
                        } else {
                            var17_16 = var12_11 + 1;
                        }
                        var12_11 = 1;
                        var16_15 = var20_19;
                        var20_19 = var17_16;
                        var17_16 = var12_11;
                        break block23;
                    }
                    if (var15_14) {
                        var20_19 = var2_2.length;
                    } else {
                        var20_19 = var12_11 + 8;
                        if (var2_2[var20_19] <= 0 || var2_2[var20_19] >= 5) {
                            var20_19 = var12_11 + 1;
                        }
                    }
                }
                var12_11 = var5_5;
                if (var17_16 != 0) {
                    var9_8.time_elpased = (float)var18_17 * var8_7;
                    this.list.add(var9_8);
                    var17_16 = var13_12;
                    var5_5 = var19_18;
                    if (var9_8.depth > WatchOp.EFFECTIVE_DIVING_DEPTH_MBAR) {
                        var17_16 = var9_8.depth;
                        var5_5 = var19_18 + 1;
                        var17_16 = (var13_12 * var19_18 + var17_16) / var5_5;
                    }
                    ++var18_17;
                    var14_13 = true;
                    var19_18 = 0;
                    var13_12 = var17_16;
                    var17_16 = var19_18;
                    var19_18 = var5_5;
                }
                var5_5 = var12_11;
                var12_11 = var20_19;
            } while (true);
        }

        public DiveProfileData(ArrayList<DiveProfileDatum> arrayList) {
            this.list = arrayList;
        }

        public void add(DiveProfileDatum diveProfileDatum) {
            this.list.add(diveProfileDatum);
        }

        public void delete(int n) {
            this.list.remove(n);
        }

        public int length() {
            ArrayList<DiveProfileDatum> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }

        public void update(int n, DiveProfileDatum diveProfileDatum) {
            this.list.set(n, diveProfileDatum);
        }
    }

    public static class DiveProfileDatum {
        public int alarm_type;
        public int ceiling;
        public int cns;
        public int data_type;
        public int deco_state;
        public int depth;
        public int dive_log_index;
        public long start_time;
        public int stop_time;
        public int temperature;
        public int time;
        public float time_elpased;
        public int value;
        public String watch_serial_number;

        public DiveProfileDatum() {
        }

        public DiveProfileDatum(Cursor cursor) {
            this.watch_serial_number = cursor.getString(cursor.getColumnIndex("serial_number"));
            this.dive_log_index = cursor.getInt(cursor.getColumnIndex("dive_log_index"));
            this.start_time = cursor.getLong(cursor.getColumnIndex("start_time"));
            this.data_type = cursor.getInt(cursor.getColumnIndex("data_type"));
            this.depth = cursor.getInt(cursor.getColumnIndex("depth"));
            this.temperature = cursor.getInt(cursor.getColumnIndex("temperature"));
            this.time_elpased = cursor.getFloat(cursor.getColumnIndex("time_elapsed"));
            this.cns = cursor.getInt(cursor.getColumnIndex("cns"));
            this.deco_state = cursor.getInt(cursor.getColumnIndex("deco_state"));
            this.ceiling = cursor.getInt(cursor.getColumnIndex("ceiling"));
            this.stop_time = cursor.getInt(cursor.getColumnIndex("stop_time"));
            this.alarm_type = cursor.getInt(cursor.getColumnIndex("alarm_type"));
            this.time = cursor.getInt(cursor.getColumnIndex("time"));
            this.value = cursor.getInt(cursor.getColumnIndex("value"));
        }

        public DiveProfileDatum(String string2, int n, long l, int n2, int n3, int n4, int n5) {
            this.watch_serial_number = string2;
            this.dive_log_index = n;
            this.start_time = l;
            this.data_type = n2;
            this.depth = n3;
            this.temperature = n4;
            this.time_elpased = n5;
        }

        public void clear() {
            this.watch_serial_number = null;
            this.dive_log_index = 0;
            this.start_time = 0L;
            this.data_type = 0;
            this.depth = 0;
            this.temperature = 0;
            this.time_elpased = 0.0f;
            this.cns = 0;
            this.deco_state = 0;
            this.ceiling = 0;
            this.stop_time = 0;
            this.alarm_type = 0;
            this.time = 0;
            this.value = 0;
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serial_number", this.watch_serial_number);
            contentValues.put("dive_log_index", Integer.valueOf(this.dive_log_index));
            contentValues.put("start_time", Long.valueOf(this.start_time));
            contentValues.put("data_type", Integer.valueOf(this.data_type));
            contentValues.put("depth", Integer.valueOf(this.depth));
            contentValues.put("temperature", Integer.valueOf(this.temperature));
            contentValues.put("time_elapsed", Float.valueOf(this.time_elpased));
            contentValues.put("cns", Integer.valueOf(this.cns));
            contentValues.put("deco_state", Integer.valueOf(this.deco_state));
            contentValues.put("ceiling", Integer.valueOf(this.ceiling));
            contentValues.put("stop_time", Integer.valueOf(this.stop_time));
            contentValues.put("alarm_type", Integer.valueOf(this.alarm_type));
            contentValues.put("time", Integer.valueOf(this.time));
            contentValues.put("value", Integer.valueOf(this.value));
            return contentValues;
        }
    }

    static final class FIRMWARE_FILE_VERSION
    extends Enum<FIRMWARE_FILE_VERSION> {
        private static final /* synthetic */ FIRMWARE_FILE_VERSION[] $VALUES;
        public static final /* enum */ FIRMWARE_FILE_VERSION C01_4C;
        public static final /* enum */ FIRMWARE_FILE_VERSION C01_5A_Crest;
        public static final /* enum */ FIRMWARE_FILE_VERSION C01_5A_Deep6;
        public static final /* enum */ FIRMWARE_FILE_VERSION C01_5A_Genesis;
        public static final /* enum */ FIRMWARE_FILE_VERSION C01_5A_TUSA;

        static {
            FIRMWARE_FILE_VERSION fIRMWARE_FILE_VERSION;
            C01_4C = new FIRMWARE_FILE_VERSION();
            C01_5A_Genesis = new FIRMWARE_FILE_VERSION();
            C01_5A_Deep6 = new FIRMWARE_FILE_VERSION();
            C01_5A_TUSA = new FIRMWARE_FILE_VERSION();
            C01_5A_Crest = fIRMWARE_FILE_VERSION = new FIRMWARE_FILE_VERSION();
            $VALUES = new FIRMWARE_FILE_VERSION[]{C01_4C, C01_5A_Genesis, C01_5A_Deep6, C01_5A_TUSA, fIRMWARE_FILE_VERSION};
        }

        public static FIRMWARE_FILE_VERSION valueOf(String string2) {
            return Enum.valueOf(FIRMWARE_FILE_VERSION.class, string2);
        }

        public static FIRMWARE_FILE_VERSION[] values() {
            return (FIRMWARE_FILE_VERSION[])$VALUES.clone();
        }
    }

    public static class FilterDiveLog {
        public long date_end = 0L;
        public boolean date_range = false;
        public long date_start = 0L;
        public boolean favorite = false;
        public boolean free_dive = false;
        public boolean gauge_dive = false;
        public boolean scuba_dive = false;

        public void clear() {
            this.date_range = false;
            this.date_start = 0L;
            this.scuba_dive = false;
            this.gauge_dive = false;
            this.free_dive = false;
            this.favorite = false;
        }
    }

    public static class Firmware {
        public String filename;
        public boolean is_activated = true;
        public String model_name;
        public String model_name_to_show;
        public String version;

        public Firmware(String string2, String string3, String string4, String string5) {
            this.model_name = string2;
            this.model_name_to_show = string3;
            this.version = string4;
            this.filename = string5;
        }

        public Firmware(String string2, String string3, String string4, String string5, boolean bl) {
            this.model_name = string2;
            this.model_name_to_show = string3;
            this.version = string4;
            this.filename = string5;
            this.is_activated = bl;
        }
    }

    public static class FirmwareFileHeader {
        public byte bimVer;
        public byte[] crc32 = new byte[4];
        public byte crcStat;
        public byte[] hdrlen = new byte[2];
        public byte[] imageID = new byte[8];
        public byte imgCpStat;
        public byte[] imgEndAddr = new byte[4];
        public byte imgNo;
        public byte imgType;
        public byte[] imgVld = new byte[4];
        public byte[] len = new byte[4];
        public byte metaVer;
        public byte[] prgEntry = new byte[4];
        public byte[] rfu = new byte[2];
        public byte[] softVer = new byte[4];
        public byte[] techType = new byte[2];

        public FirmwareFileHeader(byte[] arrby) {
            int n;
            int n2 = 0;
            for (n = 0; n < 8; ++n) {
                this.imageID[n] = arrby[n];
            }
            for (n = 0; n < 4; ++n) {
                this.crc32[n] = arrby[n + 8];
            }
            this.bimVer = arrby[12];
            this.metaVer = arrby[13];
            for (n = 0; n < 2; ++n) {
                this.techType[n] = arrby[n + 14];
            }
            this.imgCpStat = arrby[16];
            this.crcStat = arrby[17];
            this.imgType = arrby[18];
            this.imgNo = arrby[19];
            for (n = 0; n < 4; ++n) {
                this.imgVld[n] = arrby[n + 20];
            }
            for (n = 0; n < 4; ++n) {
                this.len[n] = arrby[n + 24];
            }
            for (n = 0; n < 4; ++n) {
                this.prgEntry[n] = arrby[n + 28];
            }
            for (n = 0; n < 4; ++n) {
                this.softVer[n] = arrby[n + 32];
            }
            for (n = 0; n < 4; ++n) {
                this.imgEndAddr[n] = arrby[n + 36];
            }
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= 2) {
                    while (n < 2) {
                        this.rfu[n] = arrby[n + 40];
                        ++n;
                    }
                    return;
                }
                this.hdrlen[n3] = arrby[n3 + 38];
                ++n3;
            } while (true);
        }
    }

    public static class FirmwareHeader {
        public byte bimVer;
        public byte crcStat;
        public byte imgCpStat;
        public byte[] imgID = new byte[8];
        public byte imgNo;
        public byte imgType;
        public byte[] len = new byte[4];
        public byte metaVer;
        public byte[] softVer = new byte[4];

        public FirmwareHeader(FirmwareFileHeader firmwareFileHeader) {
            this.imgID = firmwareFileHeader.imageID;
            this.bimVer = firmwareFileHeader.bimVer;
            this.metaVer = firmwareFileHeader.metaVer;
            this.imgCpStat = firmwareFileHeader.imgCpStat;
            this.crcStat = firmwareFileHeader.crcStat;
            this.imgType = firmwareFileHeader.imgType;
            this.imgNo = firmwareFileHeader.imgNo;
            this.len = firmwareFileHeader.len;
            this.softVer = firmwareFileHeader.softVer;
        }

        public int length() {
            return 22;
        }

        public byte[] toByteArray() {
            int n;
            byte[] arrby = new byte[this.length()];
            int n2 = 0;
            int n3 = 0;
            for (n = 0; n < 8; ++n, ++n3) {
                arrby[n3] = this.imgID[n];
            }
            n = n3 + 1;
            arrby[n3] = this.bimVer;
            n3 = n + 1;
            arrby[n] = this.metaVer;
            n = n3 + 1;
            arrby[n3] = this.imgCpStat;
            n3 = n + 1;
            arrby[n] = this.crcStat;
            n = n3 + 1;
            arrby[n3] = this.imgType;
            n3 = n + 1;
            arrby[n] = this.imgNo;
            int n4 = 0;
            do {
                int n5 = n2;
                n = n3++;
                if (n4 >= 4) {
                    while (n5 < 4) {
                        arrby[n] = this.softVer[n5];
                        ++n5;
                        ++n;
                    }
                    return arrby;
                }
                arrby[n3] = this.len[n4];
                ++n4;
            } while (true);
        }
    }

    public static class Firmwares {
        public List<Firmware> list;

        public Firmwares() {
            this.list = new ArrayList<Firmware>();
        }

        public Firmwares(List<Firmware> list) {
            this.list = list;
        }

        public boolean get_activated(String string2) {
            boolean bl = false;
            int n = 0;
            do {
                boolean bl2 = bl;
                if (n >= this.list.size()) return bl2;
                if (this.list.get((int)n).model_name.equals(string2)) {
                    return this.list.get((int)n).is_activated;
                }
                ++n;
            } while (true);
        }

        public String get_filename(String string2) {
            int n = 0;
            while (n < this.list.size()) {
                if (this.list.get((int)n).model_name.equals(string2)) {
                    return this.list.get((int)n).filename;
                }
                ++n;
            }
            return null;
        }

        public String get_model_name_to_show(String string2) {
            int n = 0;
            while (n < this.list.size()) {
                if (this.list.get((int)n).model_name.equals(string2)) {
                    return this.list.get((int)n).model_name_to_show;
                }
                ++n;
            }
            return null;
        }

        public String get_version(String string2) {
            int n = 0;
            while (n < this.list.size()) {
                if (this.list.get((int)n).model_name.equals(string2)) {
                    return this.list.get((int)n).version;
                }
                ++n;
            }
            return null;
        }

        public int length() {
            List<Firmware> list = this.list;
            if (list != null) return list.size();
            return -1;
        }
    }

    public static class Language {
        String language;
        String languageCode;

        Language(String string2, String string3) {
            this.languageCode = string2;
            this.language = string3;
        }

        Language(JSONObject jSONObject) {
            try {
                this.languageCode = jSONObject.getString("language_code");
                this.language = jSONObject.getString("language");
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Language", (String)jSONException.toString());
            }
        }
    }

    public static class Languages {
        ArrayList<Language> list;

        Languages(ArrayList<Language> arrayList) {
            this.list = arrayList;
        }

        Languages(JSONObject jSONObject) {
            this.list = new ArrayList();
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("items_array");
                int n = 0;
                while (n < jSONArray.length()) {
                    jSONObject = jSONArray.getJSONObject(n);
                    Language language = new Language(jSONObject);
                    this.list.add(language);
                    ++n;
                }
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Languages", (String)jSONException.toString());
            }
        }

        int length() {
            ArrayList<Language> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }
    }

    public static class Model {
        public String firmware_filename;
        public String firmware_version;
        public String hardware_version;
        public String icon;
        public long idModel;
        public String last_download_date_time;
        public String model_name;
        public boolean upgrade_tried = false;

        public Model() {
        }

        public Model(Cursor cursor) {
            this.idModel = cursor.getLong(cursor.getColumnIndex("_id"));
            this.model_name = cursor.getString(cursor.getColumnIndex("model_name"));
            this.hardware_version = cursor.getString(cursor.getColumnIndex("hardware_version"));
            this.firmware_version = cursor.getString(cursor.getColumnIndex("firmware_version"));
            this.firmware_filename = cursor.getString(cursor.getColumnIndex("firmware_filename"));
            this.icon = cursor.getString(cursor.getColumnIndex("icon"));
            this.last_download_date_time = cursor.getString(cursor.getColumnIndex("last_download_date_time"));
        }

        public Model(String string2, String string3, String string4) {
            this.model_name = string2;
            this.hardware_version = string3;
            this.firmware_version = string4;
        }

        public Model(JSONObject jSONObject) {
            try {
                this.model_name = jSONObject.getString("model_name");
                this.hardware_version = jSONObject.getString("hardware_version");
                this.firmware_version = jSONObject.getString("firmware_version");
                this.firmware_filename = jSONObject.getString("firmware_filename");
                this.icon = jSONObject.getString("icon");
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"WatchModel", (String)jSONException.toString());
            }
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("model_name", this.model_name);
            contentValues.put("hardware_version", this.hardware_version);
            contentValues.put("firmware_version", this.firmware_version);
            contentValues.put("firmware_filename", this.firmware_filename);
            contentValues.put("icon", this.icon);
            contentValues.put("last_download_date_time", this.last_download_date_time);
            return contentValues;
        }
    }

    public static class Models {
        public ArrayList<Model> list;

        public Models(Cursor cursor) {
            this.list = new ArrayList();
            if (cursor == null) {
                return;
            }
            int n = 0;
            while (n < cursor.getCount()) {
                this.list.add(new Model(cursor));
                cursor.moveToNext();
                ++n;
            }
        }

        Models(ArrayList<Model> arrayList) {
            this.list = arrayList;
        }

        public Models(JSONObject jSONObject) {
            this.list = new ArrayList();
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("items_array");
                int n = 0;
                while (n < jSONArray.length()) {
                    jSONObject = jSONArray.getJSONObject(n);
                    ArrayList<Model> arrayList = this.list;
                    Model model = new Model(jSONObject);
                    arrayList.add(model);
                    ++n;
                }
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"WatchModeles", (String)jSONException.toString());
            }
        }

        public void add(Model model) {
            this.list.add(model);
        }

        public Model getWatchModelById(Long l) {
            Model model;
            Iterator<Model> iterator2 = this.list.iterator();
            do {
                if (!iterator2.hasNext()) return null;
                model = iterator2.next();
            } while (model.idModel != l);
            return model;
        }

        public int length() {
            ArrayList<Model> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }

        public void update(int n, Model model) {
            this.list.set(n, model);
        }
    }

    public static class MyWatch {
        public String ble_device_name;
        public long end_time_for_last_downloaded_log;
        public String firmware_version;
        public int free_dive_longest_time;
        public int free_dive_max_depth;
        public int free_dive_times;
        public String hardware_version;
        public long idModel = -1L;
        public long idMyWatch;
        public boolean isBond = false;
        public boolean isStored = false;
        public int last_dive_log_index = 0;
        public long last_dive_time;
        public String mac_address;
        public String model_name;
        public String model_name_to_show;
        public int no_dive_time;
        public int no_flight_time;
        public int scuba_dive_max_depth;
        public int scuba_dive_times;
        public long scuba_dive_total_time;
        public String serial_number;
        public long start_time_for_last_downloaded_log;
        public CONNECTION_STATUS status = CONNECTION_STATUS.UNKNOWN;

        public MyWatch() {
        }

        public MyWatch(long l, long l2, String string2, String string3, String string4, String string5, int n, long l3, long l4, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            this.idMyWatch = l;
            this.idModel = l2;
            this.mac_address = string2;
            this.serial_number = string3;
            this.hardware_version = string4;
            this.firmware_version = string5;
            this.last_dive_log_index = n;
            this.last_dive_time = l3;
            this.scuba_dive_total_time = l4;
            this.scuba_dive_max_depth = n2;
            this.scuba_dive_times = n3;
            this.free_dive_times = n4;
            this.free_dive_max_depth = n6;
            this.free_dive_longest_time = n5;
            this.no_flight_time = n7;
            this.no_dive_time = n8;
        }

        public MyWatch(Cursor cursor) {
            this.idMyWatch = cursor.getLong(cursor.getColumnIndex("_id"));
            this.mac_address = cursor.getString(cursor.getColumnIndex("mac_address"));
            this.serial_number = cursor.getString(cursor.getColumnIndex("serial_number"));
            this.hardware_version = cursor.getString(cursor.getColumnIndex("hardware_version"));
            this.firmware_version = cursor.getString(cursor.getColumnIndex("firmware_version"));
            this.last_dive_log_index = cursor.getInt(cursor.getColumnIndex("last_dive_log_index"));
            this.last_dive_time = cursor.getLong(cursor.getColumnIndex("last_dive_time"));
            this.scuba_dive_total_time = cursor.getInt(cursor.getColumnIndex("scuba_dive_total_time"));
            this.scuba_dive_max_depth = cursor.getInt(cursor.getColumnIndex("scuba_dive_times"));
            this.scuba_dive_times = cursor.getInt(cursor.getColumnIndex("scuba_dive_times"));
            this.free_dive_times = cursor.getInt(cursor.getColumnIndex("free_dive_times"));
            this.free_dive_max_depth = cursor.getInt(cursor.getColumnIndex("free_dive_max_depth"));
            this.free_dive_longest_time = cursor.getInt(cursor.getColumnIndex("free_dive_longest_time"));
            this.no_flight_time = cursor.getInt(cursor.getColumnIndex("no_flight_time"));
            this.no_dive_time = cursor.getInt(cursor.getColumnIndex("no_dive_time"));
            this.model_name = cursor.getString(cursor.getColumnIndex("model_name"));
            this.model_name_to_show = cursor.getString(cursor.getColumnIndex("model_name_to_show"));
            this.idModel = cursor.getLong(cursor.getColumnIndex("id_model"));
            this.ble_device_name = cursor.getString(cursor.getColumnIndex("ble_device_name"));
            this.start_time_for_last_downloaded_log = cursor.getLong(cursor.getColumnIndex("start_time_for_last_downloaded_log"));
            this.end_time_for_last_downloaded_log = cursor.getLong(cursor.getColumnIndex("end_time_for_last_downloaded_log"));
            this.isStored = true;
        }

        public MyWatch(String string2, String string3, String string4) {
            this.mac_address = string2;
            this.ble_device_name = string3;
            this.serial_number = string4;
        }

        public MyWatch(JSONObject jSONObject) {
            try {
                this.model_name = jSONObject.getString("model_name");
                this.serial_number = jSONObject.getString("serial_number");
                this.hardware_version = jSONObject.getString("hardware_version");
                this.firmware_version = jSONObject.getString("firmware_version");
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Watch", (String)jSONException.toString());
            }
        }

        public String getMacAddress() {
            return this.mac_address;
        }

        public String getName() {
            return this.model_name;
        }

        public CONNECTION_STATUS getStatus() {
            return this.status;
        }

        public boolean isBond() {
            return this.isBond;
        }

        public void setBond(boolean bl) {
            this.isBond = bl;
        }

        public void setMacAddress(String string2) {
            this.mac_address = string2;
        }

        public void setName(String string2) {
            this.model_name = string2.substring(0, 2);
        }

        public void setStatus(CONNECTION_STATUS cONNECTION_STATUS) {
            this.status = cONNECTION_STATUS;
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id_model", Long.valueOf(this.idModel));
            contentValues.put("mac_address", this.mac_address);
            contentValues.put("serial_number", this.serial_number);
            contentValues.put("model_name", this.model_name);
            contentValues.put("model_name_to_show", this.model_name_to_show);
            contentValues.put("ble_device_name", this.ble_device_name);
            contentValues.put("hardware_version", this.hardware_version);
            contentValues.put("firmware_version", this.firmware_version);
            contentValues.put("last_dive_log_index", Integer.valueOf(this.last_dive_log_index));
            contentValues.put("last_dive_time", Long.valueOf(this.last_dive_time));
            contentValues.put("scuba_dive_times", Integer.valueOf(this.scuba_dive_times));
            contentValues.put("scuba_dive_max_depth", Integer.valueOf(this.scuba_dive_max_depth));
            contentValues.put("scuba_dive_total_time", Long.valueOf(this.scuba_dive_total_time));
            contentValues.put("free_dive_times", Integer.valueOf(this.free_dive_times));
            contentValues.put("free_dive_max_depth", Integer.valueOf(this.free_dive_max_depth));
            contentValues.put("free_dive_longest_time", Integer.valueOf(this.free_dive_longest_time));
            contentValues.put("no_flight_time", Integer.valueOf(this.no_flight_time));
            contentValues.put("no_dive_time", Integer.valueOf(this.no_dive_time));
            contentValues.put("start_time_for_last_downloaded_log", Long.valueOf(this.start_time_for_last_downloaded_log));
            contentValues.put("end_time_for_last_downloaded_log", Long.valueOf(this.end_time_for_last_downloaded_log));
            return contentValues;
        }

        ContentValues toInitMyWatchContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serial_number", this.serial_number);
            contentValues.put("model_name", this.model_name);
            contentValues.put("model_name_to_show", this.model_name_to_show);
            contentValues.put("hardware_version", this.hardware_version);
            contentValues.put("firmware_version", this.firmware_version);
            return contentValues;
        }
    }

    public static class MyWatches {
        public ArrayList<MyWatch> list;

        public MyWatches() {
            this.list = new ArrayList();
        }

        MyWatches(Cursor cursor) {
            this.list = new ArrayList();
            if (cursor == null) {
                return;
            }
            int n = 0;
            while (n < cursor.getCount()) {
                this.list.add(new MyWatch(cursor));
                cursor.moveToNext();
                ++n;
            }
        }

        public MyWatches(ArrayList<MyWatch> arrayList) {
            this.list = arrayList;
        }

        public MyWatches(JSONObject jSONObject) {
            this.list = new ArrayList();
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("items_array");
                for (int i = 0; i < jSONArray.length(); ++i) {
                    jSONObject = jSONArray.getJSONObject(i);
                    ArrayList<MyWatch> arrayList = this.list;
                    MyWatch myWatch = new MyWatch(jSONObject);
                    arrayList.add(myWatch);
                }
            }
            catch (JSONException jSONException) {
                Log.d((String)"Watches", (String)jSONException.toString());
            }
            this.list = this.list;
        }

        public void add(MyWatch myWatch) {
            this.list.add(myWatch);
        }

        public void deleteMyWatchByMacAddress(String string2) {
            if (string2 == null) {
                return;
            }
            int n = 0;
            while (n < this.list.size()) {
                if (string2.equals(this.list.get((int)n).mac_address)) {
                    this.list.remove(n);
                    this.list.size();
                    return;
                }
                ++n;
            }
        }

        public void deleteMyWatchBySerialNumber(String string2) {
            if (string2 == null) return;
            if (this.length() <= 0) {
                return;
            }
            int n = 0;
            while (n < this.list.size()) {
                if (string2.equals(this.list.get((int)n).serial_number)) {
                    this.list.remove(n);
                    this.list.size();
                    return;
                }
                ++n;
            }
        }

        public int length() {
            ArrayList<MyWatch> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return 0;
        }

        public void updateWatch(int n, MyWatch myWatch) {
            this.list.set(n, myWatch);
        }
    }

    public static class ReadLog {
        int command_group;
        boolean command_sent = false;
        boolean reply_received = false;
        int sub_command;

        public ReadLog(int n, int n2, boolean bl) {
            this.command_group = n;
            this.sub_command = n2;
            this.command_sent = bl;
        }

        public boolean isCommandSent() {
            return this.command_sent;
        }

        public boolean isReplyReceived() {
            return this.reply_received;
        }

        public void set_command_sent(boolean bl) {
            this.command_sent = bl;
        }

        public void set_reply_received(boolean bl) {
            this.reply_received = bl;
        }
    }

    public static class ReadLogs {
        public ArrayList<ReadLog> list = new ArrayList();

        public void addReadLog(ReadLog readLog) {
            this.list.add(readLog);
        }

        public int length() {
            ArrayList<ReadLog> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }
    }

    public static class WatchSetting {
        public int data_length = 0;
        public int data_type = 0;
        public String default_value_string = "";
        public long idWatchSetting = -1L;
        public String model_name = "";
        public String setting_name = "";
        public int value_int = 0;
        public String value_string = "";

        public WatchSetting() {
        }

        public WatchSetting(Cursor cursor) {
            this.idWatchSetting = cursor.getLong(cursor.getColumnIndex("_id"));
            this.model_name = cursor.getString(cursor.getColumnIndex("model_name"));
            this.setting_name = cursor.getString(cursor.getColumnIndex("setting_name"));
            this.data_type = cursor.getInt(cursor.getColumnIndex("data_type"));
            this.data_length = cursor.getInt(cursor.getColumnIndex("data_length"));
            this.value_int = cursor.getInt(cursor.getColumnIndex("value_int"));
            this.value_string = cursor.getString(cursor.getColumnIndex("value_string"));
        }

        public WatchSetting(JSONObject object) {
            try {
                this.model_name = object.getString("model_name");
                this.setting_name = object.getString("setting_name");
                this.data_type = object.getInt("data_type");
                this.data_length = object.getInt("data_length");
                object = object.getString("value_string");
                this.value_string = object;
                if (this.data_type != 0) return;
                this.value_int = Integer.parseInt((String)object);
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"WatchSetting", (String)jSONException.toString());
            }
        }

        ContentValues toContentValues() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("model_name", this.model_name);
            contentValues.put("setting_name", this.setting_name);
            contentValues.put("data_type", Integer.valueOf(this.data_type));
            contentValues.put("data_length", Integer.valueOf(this.data_length));
            contentValues.put("value_int", Integer.valueOf(this.value_int));
            contentValues.put("value_string", this.value_string);
            return contentValues;
        }
    }

    public static class WatchSetting_GB {
        public int G_sensor;
        public int O2_ratio;
        public int PPO2;
        public int UTC_offset;
        public int auto_dive_type;
        public int backlight;
        public int buzzer;
        public int date_format;
        public int day;
        public int display_unit;
        public int free_dive_depth_alarm;
        public int free_dive_depth_alarm_threshold_1;
        public int free_dive_depth_alarm_threshold_2;
        public int free_dive_depth_alarm_threshold_3;
        public int free_dive_surface_time_alarm;
        public int free_dive_surface_time_alarm_threshold_1;
        public int free_dive_surface_time_alarm_threshold_2;
        public int free_dive_surface_time_alarm_threshold_3;
        public int free_dive_time_alarm;
        public int free_dive_time_alarm_threshold_1;
        public int free_dive_time_alarm_threshold_2;
        public int free_dive_time_alarm_threshold_3;
        public int hour;
        public int minute;
        public int month;
        public int power_saving;
        public int safety_factor;
        public int scuba_dive_depth_alarm;
        public int scuba_dive_depth_alarm_threshold;
        public int scuba_dive_log_sampling_rate;
        public int scuba_dive_log_start_depth;
        public int scuba_dive_log_stop_time;
        public int scuba_dive_time_alarm;
        public int scuba_dive_time_alarm_threshold;
        public int second;
        public int time_format;
        public int vibrator;
        public int year;
    }

    public static class WatchSettings {
        public ArrayList<WatchSetting> list;

        public WatchSettings(Cursor cursor) {
            this.list = new ArrayList();
            if (cursor == null) {
                return;
            }
            int n = 0;
            while (n < cursor.getCount()) {
                this.list.add(new WatchSetting(cursor));
                cursor.moveToNext();
                ++n;
            }
        }

        WatchSettings(ArrayList<WatchSetting> arrayList) {
            this.list = arrayList;
        }

        public WatchSettings(JSONObject object) {
            this.list = new ArrayList();
            try {
                JSONArray jSONArray = object.getJSONArray("items_array");
                int n = 0;
                while (n < jSONArray.length()) {
                    JSONObject jSONObject = jSONArray.getJSONObject(n);
                    ArrayList<WatchSetting> arrayList = this.list;
                    super(jSONObject);
                    arrayList.add((WatchSetting)object);
                    ++n;
                }
                return;
            }
            catch (JSONException jSONException) {
                Log.d((String)"Watches", (String)jSONException.toString());
            }
        }

        public int length() {
            ArrayList<WatchSetting> arrayList = this.list;
            if (arrayList != null) return arrayList.size();
            return -1;
        }
    }

}

