package com.crest.divestory;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.TimeOp;
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

      public BleDevice(String var1, String var2, int var3, boolean var4) {
         this.mac_address = var1;
         this.name = var2;
         this.rssi = var3;
         this.isStored = var4;
      }

      public boolean isStored() {
         return this.isStored;
      }

      public void setStored(boolean var1) {
         this.isStored = var1;
      }
   }

   public static class BleDevices {
      public ArrayList<DataStruct.BleDevice> list;

      public BleDevices() {
         this.list = new ArrayList();
      }

      BleDevices(ArrayList<DataStruct.BleDevice> var1) {
         this.list = var1;
      }

      public void add(DataStruct.BleDevice var1) {
         this.list.add(var1);
      }

      public void delete(int var1) {
         this.list.remove(var1);
      }

      public void delete(String var1) {
         for(int var2 = 0; var2 < this.list.size(); ++var2) {
            if (((DataStruct.BleDevice)this.list.get(var2)).mac_address == var1) {
               this.list.remove(var2);
            }
         }

      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }

      public void update(int var1, DataStruct.BleDevice var2) {
         this.list.set(var1, var2);
      }
   }

   public static class Brand {
      public String icon;
      public long idBrand;
      public String name;

      Brand(long var1, String var3, String var4) {
         this.idBrand = var1;
         this.name = var3;
         this.icon = var4;
      }

      public Brand(Cursor var1) {
         this.idBrand = var1.getLong(var1.getColumnIndex("_id"));
         this.name = var1.getString(var1.getColumnIndex("name"));
         this.icon = var1.getString(var1.getColumnIndex("icon"));
      }

      Brand(JSONObject var1) {
         try {
            this.idBrand = var1.getLong("idBrand");
            this.name = var1.getString("name");
            this.icon = var1.getString("icon");
         } catch (JSONException var2) {
            Log.d("Brand", var2.toString());
         }

      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("name", this.name);
         var1.put("icon", this.icon);
         return var1;
      }
   }

   public static class Brands {
      ArrayList<DataStruct.Brand> list;

      Brands(ArrayList<DataStruct.Brand> var1) {
         this.list = var1;
      }

      Brands(JSONObject var1) {
         this.list = new ArrayList();

         JSONException var10000;
         label32: {
            boolean var10001;
            JSONArray var8;
            try {
               var8 = var1.getJSONArray("items_array");
            } catch (JSONException var7) {
               var10000 = var7;
               var10001 = false;
               break label32;
            }

            int var2 = 0;

            while(true) {
               try {
                  if (var2 >= var8.length()) {
                     return;
                  }

                  JSONObject var3 = var8.getJSONObject(var2);
                  ArrayList var4 = this.list;
                  DataStruct.Brand var5 = new DataStruct.Brand(var3);
                  var4.add(var5);
               } catch (JSONException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }

               ++var2;
            }
         }

         JSONException var9 = var10000;
         Log.d("Brands", var9.toString());
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }
   }

   public static enum CONNECTION_STATUS {
      CONNECTED,
      CONNECTING,
      DISCONNECTED,
      SCANNED,
      UNKNOWN;

      static {
         DataStruct.CONNECTION_STATUS var0 = new DataStruct.CONNECTION_STATUS("DISCONNECTED", 4);
         DISCONNECTED = var0;
      }
   }

   public static class Dictionary {
      String languageCode1;
      String languageCode2;
      String text1;
      String text2;

      Dictionary(String var1, String var2, String var3, String var4) {
         this.languageCode1 = var1;
         this.text1 = var2;
         this.languageCode2 = var3;
         this.text2 = var4;
      }

      Dictionary(JSONObject var1) {
         try {
            this.languageCode1 = var1.getString("language_code_source");
            this.text1 = var1.getString("text_source");
            this.languageCode2 = var1.getString("language_code_target");
            this.text2 = var1.getString("text_target");
         } catch (JSONException var2) {
            Log.d("Language", var2.toString());
         }

      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("language_code1", this.languageCode1);
         var1.put("text1", this.text1);
         var1.put("language_code2", this.languageCode2);
         var1.put("text2", this.text2);
         return var1;
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

      public DiveLog(Cursor var1) {
         boolean var2 = false;
         this.isFavorite = false;
         this.isStored = false;
         this.watch_serial_number = null;
         this.surface_temperature = 0.0F;
         this.location = null;
         this.breathing_gas = null;
         this.cylinder_capacity = (float)WatchOp.DEFAULT_CYLINDER_CAPACITY;
         this.O2_ratio = 0;
         this.pressure_start = 0;
         this.pressure_end = 0;
         this.aux_weight = 0.0F;
         this.visibility = 0.0F;
         this.note = null;
         this.rating = 0;
         this.weather = null;
         this.wind = null;
         this.wave = null;
         this.buddy = null;
         this.isDownloaded = false;
         this.id = var1.getLong(var1.getColumnIndex("_id"));
         this.watch_serial_number = var1.getString(var1.getColumnIndex("serial_number"));
         this.dive_log_index = var1.getLong(var1.getColumnIndex("dive_log_index"));
         this.dive_type = var1.getLong(var1.getColumnIndex("dive_type"));
         this.profile_data_length = var1.getLong(var1.getColumnIndex("profile_data_length"));
         this.start_time = var1.getLong(var1.getColumnIndex("start_time"));
         this.end_time = var1.getLong(var1.getColumnIndex("end_time"));
         this.duration = var1.getLong(var1.getColumnIndex("duration"));
         this.sampling_rate = var1.getLong(var1.getColumnIndex("sampling_rate"));
         this.max_depth = var1.getLong(var1.getColumnIndex("max_depth"));
         this.lowest_water_temperature = var1.getLong(var1.getColumnIndex("lowest_water_temperature"));
         this.average_depth = var1.getLong(var1.getColumnIndex("average_depth"));
         this.data_start_address = var1.getLong(var1.getColumnIndex("data_start_address"));
         this.data_end_address = var1.getLong(var1.getColumnIndex("data_end_address"));
         this.firmware_version = var1.getString(var1.getColumnIndex("firmware_version"));
         this.surface_ATM = var1.getLong(var1.getColumnIndex("surface_ATM"));
         this.is_high_elevation_diving = var1.getLong(var1.getColumnIndex("is_high_elevation_diving"));
         this.safety_factor = var1.getLong(var1.getColumnIndex("safety_factor"));
         this.PPO2 = var1.getLong(var1.getColumnIndex("PPO2"));
         this.CNS_at_start = var1.getLong(var1.getColumnIndex("CNS_at_start"));
         this.max_CNS = (long)var1.getInt(var1.getColumnIndex("max_CNS"));
         this.deco_model = var1.getLong(var1.getColumnIndex("deco_model"));
         this.no_fligt_fime = var1.getLong(var1.getColumnIndex("no_flight_time"));
         this.no_dive_time = var1.getLong(var1.getColumnIndex("no_dive_time"));
         this.end_N2_compartment_bytes = ByteOp.get_BytesArray_from_HexString(var1.getString(var1.getColumnIndex("end_N2_compartment")));
         this.end_H2_compartment_bytes = ByteOp.get_BytesArray_from_HexString(var1.getString(var1.getColumnIndex("end_H2_compartment")));
         if (var1.getInt(var1.getColumnIndex("is_favorite")) > 0) {
            var2 = true;
         }

         this.isFavorite = var2;
         this.rating = var1.getInt(var1.getColumnIndex("rating"));
         this.note = var1.getString(var1.getColumnIndex("note"));
         this.location = var1.getString(var1.getColumnIndex("location"));
         this.breathing_gas = var1.getString(var1.getColumnIndex("breathing_gas"));
         this.cylinder_capacity = var1.getFloat(var1.getColumnIndex("cylinder_capacity"));
         this.O2_ratio = var1.getInt(var1.getColumnIndex("O2_ratio"));
         this.pressure_start = var1.getInt(var1.getColumnIndex("pressure_start"));
         this.pressure_end = var1.getInt(var1.getColumnIndex("pressure_end"));
         this.aux_weight = var1.getFloat(var1.getColumnIndex("aux_weight"));
         this.visibility = var1.getFloat(var1.getColumnIndex("visibility"));
         this.surface_temperature = var1.getFloat(var1.getColumnIndex("surface_temperature"));
         this.weather = var1.getString(var1.getColumnIndex("weather"));
         this.wind = var1.getString(var1.getColumnIndex("wind"));
         this.wave = var1.getString(var1.getColumnIndex("wave"));
         this.buddy = var1.getString(var1.getColumnIndex("buddy"));
      }

      public DiveLog(String var1, byte[] var2) {
         this.isFavorite = false;
         this.isStored = false;
         this.watch_serial_number = null;
         this.surface_temperature = 0.0F;
         this.location = null;
         this.breathing_gas = null;
         this.cylinder_capacity = (float)WatchOp.DEFAULT_CYLINDER_CAPACITY;
         this.O2_ratio = 0;
         this.pressure_start = 0;
         this.pressure_end = 0;
         this.aux_weight = 0.0F;
         this.visibility = 0.0F;
         this.note = null;
         this.rating = 0;
         this.weather = null;
         this.wind = null;
         this.wave = null;
         this.buddy = null;
         this.isDownloaded = false;
         this.watch_serial_number = var1;
         byte[] var13 = new byte[4];
         byte[] var3 = new byte[2];
         byte[] var4 = new byte[6];
         int var5 = 0;

         int var6;
         for(var6 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.dive_log_index = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.dive_type = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.profile_data_length = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);
         var5 = ByteOp.b2ui(var2[var6]);
         ++var6;
         int var7 = ByteOp.b2ui(var2[var6]);
         ++var6;
         int var8 = ByteOp.b2ui(var2[var6]);
         ++var6;
         int var9 = ByteOp.b2ui(var2[var6]);
         ++var6;
         int var10 = ByteOp.b2ui(var2[var6]);
         var6 += 4;
         this.start_time = TimeOp.DateTimeToMs(var5 + 2000, var7 - 1, var8, var9, var10);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         long var11 = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);
         this.duration = var11;
         this.end_time = this.start_time + var11 * 1000L;

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.sampling_rate = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.max_depth = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.lowest_water_temperature = ByteOp.int32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.average_depth = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.data_start_address = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.data_end_address = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 6; ++var6) {
            var4[var5] = var2[var6];
            ++var5;
         }

         this.firmware_version = StringOp.ByteArrayToString(var4);
         var6 += 2;

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.surface_ATM = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.is_high_elevation_diving = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.safety_factor = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.PPO2 = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.CNS_at_start = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.max_CNS = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.deco_model = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = 0; var5 < 32; ++var5) {
            this.end_N2_compartment_bytes[var5] = var2[var6 + var5];
         }

         for(var5 = 0; var5 < 16; var6 += 2) {
            var3[0] = var2[var6];
            var3[1] = var2[var6 + 1];
            this.end_N2_compartment[var5] = ByteOp.uint16ToInt(var3, WatchOp.byteOrder);
            ++var5;
         }

         for(var5 = 0; var5 < 32; ++var5) {
            this.end_H2_compartment_bytes[var5] = var2[var6 + var5];
         }

         for(var5 = 0; var5 < 16; var6 += 2) {
            var3[0] = var2[var6];
            var3[1] = var2[var6 + 1];
            this.end_H2_compartment[var5] = ByteOp.uint16ToInt(var3, WatchOp.byteOrder);
            ++var5;
         }

         byte var14 = 0;

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.no_fligt_fime = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);

         for(var5 = var14; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.no_dive_time = ByteOp.uint32ToLong(var13, WatchOp.byteOrder);
      }

      public ContentValues editContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("is_favorite", Integer.valueOf(this.isFavorite));
         var1.put("rating", this.rating);
         var1.put("note", this.note);
         var1.put("location", this.location);
         var1.put("breathing_gas", this.breathing_gas);
         var1.put("cylinder_capacity", this.cylinder_capacity);
         var1.put("O2_ratio", this.O2_ratio);
         var1.put("pressure_start", this.pressure_start);
         var1.put("pressure_end", this.pressure_end);
         var1.put("aux_weight", this.aux_weight);
         var1.put("visibility", this.visibility);
         var1.put("weather", this.weather);
         var1.put("wind", this.wind);
         var1.put("wave", this.wave);
         var1.put("buddy", this.buddy);
         var1.put("surface_temperature", this.surface_temperature);
         return var1;
      }

      public ContentValues profileContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("surface_temperature", this.surface_temperature);
         var1.put("max_depth", this.max_depth);
         return var1;
      }

      public ContentValues watchContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("serial_number", this.watch_serial_number);
         var1.put("dive_log_index", this.dive_log_index);
         var1.put("dive_type", this.dive_type);
         var1.put("profile_data_length", this.profile_data_length);
         var1.put("start_time", this.start_time);
         var1.put("end_time", this.end_time);
         var1.put("duration", this.duration);
         var1.put("sampling_rate", this.sampling_rate);
         var1.put("max_depth", this.max_depth);
         var1.put("lowest_water_temperature", this.lowest_water_temperature);
         var1.put("average_depth", this.average_depth);
         var1.put("data_start_address", this.data_start_address);
         var1.put("data_end_address", this.data_end_address);
         var1.put("firmware_version", this.firmware_version);
         var1.put("surface_ATM", this.surface_ATM);
         var1.put("is_high_elevation_diving", this.is_high_elevation_diving);
         var1.put("safety_factor", this.safety_factor);
         var1.put("PPO2", this.PPO2);
         var1.put("CNS_at_start", this.CNS_at_start);
         var1.put("max_CNS", this.max_CNS);
         var1.put("deco_model", this.deco_model);
         var1.put("no_flight_time", this.no_fligt_fime);
         var1.put("no_dive_time", this.no_dive_time);
         var1.put("surface_temperature", this.surface_temperature);
         var1.put("end_N2_compartment", ByteOp.get_HexString_from_BytesArray(this.end_N2_compartment_bytes));
         var1.put("end_H2_compartment", ByteOp.get_HexString_from_BytesArray(this.end_H2_compartment_bytes));
         return var1;
      }
   }

   public static class DiveLogs {
      public ArrayList<DataStruct.DiveLog> list;

      public DiveLogs() {
         this.list = new ArrayList();
      }

      public DiveLogs(Cursor var1) {
         this.list = new ArrayList();
         if (var1 != null && var1.getCount() != 0) {
            for(int var2 = 0; var2 < var1.getCount(); ++var2) {
               this.list.add(new DataStruct.DiveLog(var1));
               var1.moveToNext();
            }
         }

      }

      public DiveLogs(ArrayList<DataStruct.DiveLog> var1) {
         this.list = var1;
      }

      public void addDiveLog(DataStruct.DiveLog var1) {
         this.list.add(var1);
      }

      public void deleteDiveLog(int var1) {
         this.list.remove(var1);
      }

      public void deleteDiveLogByStartTime(String var1, long var2) {
         this.deleteDiveLogInListByStartTime(this.list, var1, var2);
      }

      public void deleteDiveLogInListByStartTime(ArrayList<DataStruct.DiveLog> var1, String var2, long var3) {
         if (var2 != null && var1 != null) {
            for(int var5 = 0; var5 < var1.size(); ++var5) {
               if (var2.equals(((DataStruct.DiveLog)var1.get(var5)).watch_serial_number) && ((DataStruct.DiveLog)var1.get(var5)).start_time == var3) {
                  var1.remove(var5);
                  var1.size();
                  break;
               }
            }
         }

      }

      public ArrayList<DataStruct.DiveLog> duplicateDiveLogsList() {
         ArrayList var1 = new ArrayList();

         for(int var2 = 0; var2 < this.list.size(); ++var2) {
            var1.add(this.list.get(var2));
         }

         return var1;
      }

      public DataStruct.DiveLog get(int var1) {
         return (DataStruct.DiveLog)this.list.get(var1);
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }

      public int total_dive_profile_data_length() {
         ArrayList var1 = this.list;
         byte var2 = 0;
         int var3 = 0;
         int var4 = var2;
         if (var1 != null) {
            var4 = var2;
            if (var1.size() > 0) {
               Iterator var5 = this.list.iterator();

               while(true) {
                  var4 = var3;
                  if (!var5.hasNext()) {
                     break;
                  }

                  DataStruct.DiveLog var6 = (DataStruct.DiveLog)var5.next();
                  var3 = (int)((long)var3 + var6.profile_data_length);
               }
            }
         }

         return var4;
      }

      public void updateDive(int var1, DataStruct.DiveLog var2) {
         this.list.set(var1, var2);
      }

      public void updateDiveLog(DataStruct.DiveLog var1) {
         if (var1 != null) {
            long var2 = var1.id;
            String var4 = var1.watch_serial_number;
            var2 = var1.start_time;

            for(int var5 = 0; var5 < this.list.size(); ++var5) {
               if (var4.equals(((DataStruct.DiveLog)this.list.get(var5)).watch_serial_number) && ((DataStruct.DiveLog)this.list.get(var5)).start_time == var2) {
                  this.list.set(var5, var1);
                  this.list.size();
                  break;
               }
            }

         }
      }
   }

   public static class DiveProfileData {
      public ArrayList<DataStruct.DiveProfileDatum> list;

      public DiveProfileData() {
         this.list = new ArrayList();
      }

      public DiveProfileData(Cursor var1) {
         this.list = new ArrayList();
         if (var1 != null) {
            for(int var2 = 0; var2 < var1.getCount(); ++var2) {
               this.list.add(new DataStruct.DiveProfileDatum(var1));
               var1.moveToNext();
            }

         }
      }

      public DiveProfileData(DataStruct.DiveLog var1, byte[] var2, String var3) {
         this.list = new ArrayList();
         String var4 = var1.firmware_version;
         int var5 = (int)var1.dive_log_index;
         long var6 = var1.start_time;
         float var8 = (float)var1.sampling_rate;
         if (var1.dive_type == 2L) {
            var8 = 0.5F;
         }

         DataStruct.DiveProfileDatum var9 = null;
         byte[] var10 = new byte[2];
         boolean var11 = WatchOp.isOldFirmware(var4);
         int var12 = 0;
         int var13 = 0;
         boolean var14 = true;
         boolean var15 = false;
         int var16 = 0;
         boolean var17 = false;
         int var18 = 0;

         int var20;
         for(int var19 = 0; var2.length - var12 >= 6; var12 = var20) {
            if (var2.length - var12 <= 8) {
               var15 = true;
            }

            if (var14) {
               var9 = new DataStruct.DiveProfileDatum();
               var9.watch_serial_number = var3;
               var9.dive_log_index = var5;
               var9.start_time = var6;
               var14 = false;
            }

            int var22;
            label171: {
               var9.data_type = var2[var12];
               if (var11) {
                  var20 = var9.data_type;
                  if (var20 != 1) {
                     if (var20 != 2) {
                        if (var15) {
                           var20 = var2.length;
                        } else {
                           var20 = var12 + 1;
                        }
                        break label171;
                     }

                     var10[0] = (byte)var2[var12 + 2];
                     var10[1] = (byte)var2[var12 + 3];
                     var9.depth = ByteOp.uint16ToInt(var10, WatchOp.byteOrder);
                     var10[0] = (byte)var2[var12 + 4];
                     var10[1] = (byte)var2[var12 + 5];
                     var20 = ByteOp.uint16ToInt(var10, WatchOp.byteOrder);
                     if (var20 > 1300) {
                        if (var15) {
                           var20 = var2.length;
                           break label171;
                        }

                        var20 = var12 + 8;
                        if (var2[var20] > 0 && var2[var20] < 3) {
                           break label171;
                        }
                     } else {
                        if (var20 >= 10) {
                           var9.temperature = var20;
                           if (var15) {
                              var20 = var2.length;
                           } else {
                              var20 = var12 + 6;
                              if (var2[var20] <= 0 || var2[var20] >= 3) {
                                 var20 = var12 + 1;
                              }
                           }

                           var17 = true;
                           break label171;
                        }

                        if (var15) {
                           var20 = var2.length;
                           break label171;
                        }

                        var20 = var12 + 6;
                        if (var2[var20] > 0 && var2[var20] < 3) {
                           break label171;
                        }
                     }
                  } else {
                     if (var15) {
                        var20 = var2.length;
                        break label171;
                     }

                     var20 = var12 + 8;
                     if (var2[var20] > 0 && var2[var20] < 3) {
                        break label171;
                     }
                  }
               } else {
                  var20 = var9.data_type;
                  if (var20 == 1) {
                     if (var15) {
                        var20 = var2.length;
                        break label171;
                     }

                     var20 = var12 + 8;
                     if (var2[var20] <= 0 || var2[var20] >= 5) {
                        var20 = var12 + 1;
                     }
                     break label171;
                  }

                  if (var20 == 2) {
                     var10[0] = (byte)var2[var12 + 2];
                     var10[1] = (byte)var2[var12 + 3];
                     var9.depth = ByteOp.uint16ToInt(var10, WatchOp.byteOrder);
                     if (var9.depth > 10000) {
                        var9.depth = 10000;
                     }

                     if (var9.depth < 1000) {
                        var9.depth = 1000;
                     }

                     if (var9.depth > var16) {
                        var20 = var9.depth;
                     } else {
                        var20 = var16;
                     }

                     var10[0] = (byte)var2[var12 + 4];
                     var10[1] = (byte)var2[var12 + 5];
                     var9.temperature = ByteOp.uint16ToInt(var10, WatchOp.byteOrder);
                     if (var9.temperature > 500) {
                        var9.temperature = 500;
                     }

                     label185: {
                        if (var15) {
                           var16 = var2.length;
                        } else {
                           var16 = var12 + 6;
                           if (var2[var16] <= 0 || var2[var16] >= 5) {
                              var22 = var12 + 1;
                              break label185;
                           }
                        }

                        var22 = var16;
                     }

                     boolean var21 = true;
                     var16 = var20;
                     var20 = var22;
                     var17 = var21;
                     break label171;
                  }

                  if (var20 != 3) {
                     if (var20 != 4) {
                        if (var15) {
                           var20 = var2.length;
                        } else {
                           var20 = var12 + 1;
                        }

                        Log.d("Error", "unknown data_type");
                        break label171;
                     }

                     if (var15) {
                        var20 = var2.length;
                        break label171;
                     }

                     var20 = var12 + 8;
                     if (var2[var20] > 0 && var2[var20] < 5) {
                        break label171;
                     }
                  } else {
                     if (var15) {
                        var20 = var2.length;
                        break label171;
                     }

                     var20 = var12 + 6;
                     if (var2[var20] > 0 && var2[var20] < 5) {
                        break label171;
                     }
                  }
               }

               var20 = var12 + 1;
            }

            if (var17) {
               var9.time_elpased = (float)var18 * var8;
               this.list.add(var9);
               var22 = var13;
               var5 = var19;
               if (var9.depth > WatchOp.EFFECTIVE_DIVING_DEPTH_MBAR) {
                  var22 = var9.depth;
                  var5 = var19 + 1;
                  var22 = (var13 * var19 + var22) / var5;
               }

               ++var18;
               var14 = true;
               boolean var23 = false;
               var13 = var22;
               var17 = var23;
               var19 = var5;
            }

            var5 = var5;
         }

         var1.average_depth = (long)var13;
      }

      public DiveProfileData(ArrayList<DataStruct.DiveProfileDatum> var1) {
         this.list = var1;
      }

      public void add(DataStruct.DiveProfileDatum var1) {
         this.list.add(var1);
      }

      public void delete(int var1) {
         this.list.remove(var1);
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }

      public void update(int var1, DataStruct.DiveProfileDatum var2) {
         this.list.set(var1, var2);
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

      public DiveProfileDatum(Cursor var1) {
         this.watch_serial_number = var1.getString(var1.getColumnIndex("serial_number"));
         this.dive_log_index = var1.getInt(var1.getColumnIndex("dive_log_index"));
         this.start_time = var1.getLong(var1.getColumnIndex("start_time"));
         this.data_type = var1.getInt(var1.getColumnIndex("data_type"));
         this.depth = var1.getInt(var1.getColumnIndex("depth"));
         this.temperature = var1.getInt(var1.getColumnIndex("temperature"));
         this.time_elpased = var1.getFloat(var1.getColumnIndex("time_elapsed"));
         this.cns = var1.getInt(var1.getColumnIndex("cns"));
         this.deco_state = var1.getInt(var1.getColumnIndex("deco_state"));
         this.ceiling = var1.getInt(var1.getColumnIndex("ceiling"));
         this.stop_time = var1.getInt(var1.getColumnIndex("stop_time"));
         this.alarm_type = var1.getInt(var1.getColumnIndex("alarm_type"));
         this.time = var1.getInt(var1.getColumnIndex("time"));
         this.value = var1.getInt(var1.getColumnIndex("value"));
      }

      public DiveProfileDatum(String var1, int var2, long var3, int var5, int var6, int var7, int var8) {
         this.watch_serial_number = var1;
         this.dive_log_index = var2;
         this.start_time = var3;
         this.data_type = var5;
         this.depth = var6;
         this.temperature = var7;
         this.time_elpased = (float)var8;
      }

      public void clear() {
         this.watch_serial_number = null;
         this.dive_log_index = 0;
         this.start_time = 0L;
         this.data_type = 0;
         this.depth = 0;
         this.temperature = 0;
         this.time_elpased = 0.0F;
         this.cns = 0;
         this.deco_state = 0;
         this.ceiling = 0;
         this.stop_time = 0;
         this.alarm_type = 0;
         this.time = 0;
         this.value = 0;
      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("serial_number", this.watch_serial_number);
         var1.put("dive_log_index", this.dive_log_index);
         var1.put("start_time", this.start_time);
         var1.put("data_type", this.data_type);
         var1.put("depth", this.depth);
         var1.put("temperature", this.temperature);
         var1.put("time_elapsed", this.time_elpased);
         var1.put("cns", this.cns);
         var1.put("deco_state", this.deco_state);
         var1.put("ceiling", this.ceiling);
         var1.put("stop_time", this.stop_time);
         var1.put("alarm_type", this.alarm_type);
         var1.put("time", this.time);
         var1.put("value", this.value);
         return var1;
      }
   }

   static enum FIRMWARE_FILE_VERSION {
      C01_4C,
      C01_5A_Crest,
      C01_5A_Deep6,
      C01_5A_Genesis,
      C01_5A_TUSA;

      static {
         DataStruct.FIRMWARE_FILE_VERSION var0 = new DataStruct.FIRMWARE_FILE_VERSION("C01_5A_Crest", 4);
         C01_5A_Crest = var0;
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

      public Firmware(String var1, String var2, String var3, String var4) {
         this.model_name = var1;
         this.model_name_to_show = var2;
         this.version = var3;
         this.filename = var4;
      }

      public Firmware(String var1, String var2, String var3, String var4, boolean var5) {
         this.model_name = var1;
         this.model_name_to_show = var2;
         this.version = var3;
         this.filename = var4;
         this.is_activated = var5;
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

      public FirmwareFileHeader(byte[] var1) {
         byte var2 = 0;

         int var3;
         for(var3 = 0; var3 < 8; ++var3) {
            this.imageID[var3] = (byte)var1[var3];
         }

         for(var3 = 0; var3 < 4; ++var3) {
            this.crc32[var3] = (byte)var1[var3 + 8];
         }

         this.bimVer = (byte)var1[12];
         this.metaVer = (byte)var1[13];

         for(var3 = 0; var3 < 2; ++var3) {
            this.techType[var3] = (byte)var1[var3 + 14];
         }

         this.imgCpStat = (byte)var1[16];
         this.crcStat = (byte)var1[17];
         this.imgType = (byte)var1[18];
         this.imgNo = (byte)var1[19];

         for(var3 = 0; var3 < 4; ++var3) {
            this.imgVld[var3] = (byte)var1[var3 + 20];
         }

         for(var3 = 0; var3 < 4; ++var3) {
            this.len[var3] = (byte)var1[var3 + 24];
         }

         for(var3 = 0; var3 < 4; ++var3) {
            this.prgEntry[var3] = (byte)var1[var3 + 28];
         }

         for(var3 = 0; var3 < 4; ++var3) {
            this.softVer[var3] = (byte)var1[var3 + 32];
         }

         for(var3 = 0; var3 < 4; ++var3) {
            this.imgEndAddr[var3] = (byte)var1[var3 + 36];
         }

         int var4 = 0;

         while(true) {
            var3 = var2;
            if (var4 >= 2) {
               while(var3 < 2) {
                  this.rfu[var3] = (byte)var1[var3 + 40];
                  ++var3;
               }

               return;
            }

            this.hdrlen[var4] = (byte)var1[var4 + 38];
            ++var4;
         }
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

      public FirmwareHeader(DataStruct.FirmwareFileHeader var1) {
         this.imgID = var1.imageID;
         this.bimVer = (byte)var1.bimVer;
         this.metaVer = (byte)var1.metaVer;
         this.imgCpStat = (byte)var1.imgCpStat;
         this.crcStat = (byte)var1.crcStat;
         this.imgType = (byte)var1.imgType;
         this.imgNo = (byte)var1.imgNo;
         this.len = var1.len;
         this.softVer = var1.softVer;
      }

      public int length() {
         return 22;
      }

      public byte[] toByteArray() {
         byte[] var1 = new byte[this.length()];
         byte var2 = 0;
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < 8; ++var4) {
            var1[var4] = (byte)this.imgID[var3];
            ++var3;
         }

         var3 = var4 + 1;
         var1[var4] = (byte)this.bimVer;
         var4 = var3 + 1;
         var1[var3] = (byte)this.metaVer;
         var3 = var4 + 1;
         var1[var4] = (byte)this.imgCpStat;
         var4 = var3 + 1;
         var1[var3] = (byte)this.crcStat;
         var3 = var4 + 1;
         var1[var4] = (byte)this.imgType;
         var4 = var3 + 1;
         var1[var3] = (byte)this.imgNo;
         int var5 = 0;

         while(true) {
            int var6 = var2;
            var3 = var4;
            if (var5 >= 4) {
               while(var6 < 4) {
                  var1[var3] = (byte)this.softVer[var6];
                  ++var6;
                  ++var3;
               }

               return var1;
            }

            var1[var4] = (byte)this.len[var5];
            ++var5;
            ++var4;
         }
      }
   }

   public static class Firmwares {
      public List<DataStruct.Firmware> list;

      public Firmwares() {
         this.list = new ArrayList();
      }

      public Firmwares(List<DataStruct.Firmware> var1) {
         this.list = var1;
      }

      public boolean get_activated(String var1) {
         boolean var2 = false;
         int var3 = 0;

         boolean var4;
         while(true) {
            var4 = var2;
            if (var3 >= this.list.size()) {
               break;
            }

            if (((DataStruct.Firmware)this.list.get(var3)).model_name.equals(var1)) {
               var4 = ((DataStruct.Firmware)this.list.get(var3)).is_activated;
               break;
            }

            ++var3;
         }

         return var4;
      }

      public String get_filename(String var1) {
         int var2 = 0;

         while(true) {
            if (var2 >= this.list.size()) {
               var1 = null;
               break;
            }

            if (((DataStruct.Firmware)this.list.get(var2)).model_name.equals(var1)) {
               var1 = ((DataStruct.Firmware)this.list.get(var2)).filename;
               break;
            }

            ++var2;
         }

         return var1;
      }

      public String get_model_name_to_show(String var1) {
         int var2 = 0;

         while(true) {
            if (var2 >= this.list.size()) {
               var1 = null;
               break;
            }

            if (((DataStruct.Firmware)this.list.get(var2)).model_name.equals(var1)) {
               var1 = ((DataStruct.Firmware)this.list.get(var2)).model_name_to_show;
               break;
            }

            ++var2;
         }

         return var1;
      }

      public String get_version(String var1) {
         int var2 = 0;

         while(true) {
            if (var2 >= this.list.size()) {
               var1 = null;
               break;
            }

            if (((DataStruct.Firmware)this.list.get(var2)).model_name.equals(var1)) {
               var1 = ((DataStruct.Firmware)this.list.get(var2)).version;
               break;
            }

            ++var2;
         }

         return var1;
      }

      public int length() {
         List var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }
   }

   public static class Language {
      String language;
      String languageCode;

      Language(String var1, String var2) {
         this.languageCode = var1;
         this.language = var2;
      }

      Language(JSONObject var1) {
         try {
            this.languageCode = var1.getString("language_code");
            this.language = var1.getString("language");
         } catch (JSONException var2) {
            Log.d("Language", var2.toString());
         }

      }
   }

   public static class Languages {
      ArrayList<DataStruct.Language> list;

      Languages(ArrayList<DataStruct.Language> var1) {
         this.list = var1;
      }

      Languages(JSONObject var1) {
         this.list = new ArrayList();

         JSONException var10000;
         label32: {
            boolean var10001;
            JSONArray var2;
            try {
               var2 = var1.getJSONArray("items_array");
            } catch (JSONException var6) {
               var10000 = var6;
               var10001 = false;
               break label32;
            }

            int var3 = 0;

            while(true) {
               try {
                  if (var3 >= var2.length()) {
                     return;
                  }

                  var1 = var2.getJSONObject(var3);
                  DataStruct.Language var4 = new DataStruct.Language(var1);
                  this.list.add(var4);
               } catch (JSONException var5) {
                  var10000 = var5;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         JSONException var7 = var10000;
         Log.d("Languages", var7.toString());
      }

      int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
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

      public Model(Cursor var1) {
         this.idModel = var1.getLong(var1.getColumnIndex("_id"));
         this.model_name = var1.getString(var1.getColumnIndex("model_name"));
         this.hardware_version = var1.getString(var1.getColumnIndex("hardware_version"));
         this.firmware_version = var1.getString(var1.getColumnIndex("firmware_version"));
         this.firmware_filename = var1.getString(var1.getColumnIndex("firmware_filename"));
         this.icon = var1.getString(var1.getColumnIndex("icon"));
         this.last_download_date_time = var1.getString(var1.getColumnIndex("last_download_date_time"));
      }

      public Model(String var1, String var2, String var3) {
         this.model_name = var1;
         this.hardware_version = var2;
         this.firmware_version = var3;
      }

      public Model(JSONObject var1) {
         try {
            this.model_name = var1.getString("model_name");
            this.hardware_version = var1.getString("hardware_version");
            this.firmware_version = var1.getString("firmware_version");
            this.firmware_filename = var1.getString("firmware_filename");
            this.icon = var1.getString("icon");
         } catch (JSONException var2) {
            Log.d("WatchModel", var2.toString());
         }

      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("model_name", this.model_name);
         var1.put("hardware_version", this.hardware_version);
         var1.put("firmware_version", this.firmware_version);
         var1.put("firmware_filename", this.firmware_filename);
         var1.put("icon", this.icon);
         var1.put("last_download_date_time", this.last_download_date_time);
         return var1;
      }
   }

   public static class Models {
      public ArrayList<DataStruct.Model> list;

      public Models(Cursor var1) {
         this.list = new ArrayList();
         if (var1 != null) {
            for(int var2 = 0; var2 < var1.getCount(); ++var2) {
               this.list.add(new DataStruct.Model(var1));
               var1.moveToNext();
            }

         }
      }

      Models(ArrayList<DataStruct.Model> var1) {
         this.list = var1;
      }

      public Models(JSONObject var1) {
         this.list = new ArrayList();

         JSONException var10000;
         label32: {
            boolean var10001;
            JSONArray var2;
            try {
               var2 = var1.getJSONArray("items_array");
            } catch (JSONException var7) {
               var10000 = var7;
               var10001 = false;
               break label32;
            }

            int var3 = 0;

            while(true) {
               try {
                  if (var3 >= var2.length()) {
                     return;
                  }

                  var1 = var2.getJSONObject(var3);
                  ArrayList var4 = this.list;
                  DataStruct.Model var5 = new DataStruct.Model(var1);
                  var4.add(var5);
               } catch (JSONException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         JSONException var8 = var10000;
         Log.d("WatchModeles", var8.toString());
      }

      public void add(DataStruct.Model var1) {
         this.list.add(var1);
      }

      public DataStruct.Model getWatchModelById(Long var1) {
         Iterator var2 = this.list.iterator();

         DataStruct.Model var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = (DataStruct.Model)var2.next();
         } while(var3.idModel != var1);

         return var3;
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }

      public void update(int var1, DataStruct.Model var2) {
         this.list.set(var1, var2);
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
      public DataStruct.CONNECTION_STATUS status;

      public MyWatch() {
         this.status = DataStruct.CONNECTION_STATUS.UNKNOWN;
      }

      public MyWatch(long var1, long var3, String var5, String var6, String var7, String var8, int var9, long var10, long var12, int var14, int var15, int var16, int var17, int var18, int var19, int var20) {
         this.status = DataStruct.CONNECTION_STATUS.UNKNOWN;
         this.idMyWatch = var1;
         this.idModel = var3;
         this.mac_address = var5;
         this.serial_number = var6;
         this.hardware_version = var7;
         this.firmware_version = var8;
         this.last_dive_log_index = var9;
         this.last_dive_time = var10;
         this.scuba_dive_total_time = var12;
         this.scuba_dive_max_depth = var14;
         this.scuba_dive_times = var15;
         this.free_dive_times = var16;
         this.free_dive_max_depth = var18;
         this.free_dive_longest_time = var17;
         this.no_flight_time = var19;
         this.no_dive_time = var20;
      }

      public MyWatch(Cursor var1) {
         this.status = DataStruct.CONNECTION_STATUS.UNKNOWN;
         this.idMyWatch = var1.getLong(var1.getColumnIndex("_id"));
         this.mac_address = var1.getString(var1.getColumnIndex("mac_address"));
         this.serial_number = var1.getString(var1.getColumnIndex("serial_number"));
         this.hardware_version = var1.getString(var1.getColumnIndex("hardware_version"));
         this.firmware_version = var1.getString(var1.getColumnIndex("firmware_version"));
         this.last_dive_log_index = var1.getInt(var1.getColumnIndex("last_dive_log_index"));
         this.last_dive_time = var1.getLong(var1.getColumnIndex("last_dive_time"));
         this.scuba_dive_total_time = (long)var1.getInt(var1.getColumnIndex("scuba_dive_total_time"));
         this.scuba_dive_max_depth = var1.getInt(var1.getColumnIndex("scuba_dive_times"));
         this.scuba_dive_times = var1.getInt(var1.getColumnIndex("scuba_dive_times"));
         this.free_dive_times = var1.getInt(var1.getColumnIndex("free_dive_times"));
         this.free_dive_max_depth = var1.getInt(var1.getColumnIndex("free_dive_max_depth"));
         this.free_dive_longest_time = var1.getInt(var1.getColumnIndex("free_dive_longest_time"));
         this.no_flight_time = var1.getInt(var1.getColumnIndex("no_flight_time"));
         this.no_dive_time = var1.getInt(var1.getColumnIndex("no_dive_time"));
         this.model_name = var1.getString(var1.getColumnIndex("model_name"));
         this.model_name_to_show = var1.getString(var1.getColumnIndex("model_name_to_show"));
         this.idModel = var1.getLong(var1.getColumnIndex("id_model"));
         this.ble_device_name = var1.getString(var1.getColumnIndex("ble_device_name"));
         this.start_time_for_last_downloaded_log = var1.getLong(var1.getColumnIndex("start_time_for_last_downloaded_log"));
         this.end_time_for_last_downloaded_log = var1.getLong(var1.getColumnIndex("end_time_for_last_downloaded_log"));
         this.isStored = true;
      }

      public MyWatch(String var1, String var2, String var3) {
         this.status = DataStruct.CONNECTION_STATUS.UNKNOWN;
         this.mac_address = var1;
         this.ble_device_name = var2;
         this.serial_number = var3;
      }

      public MyWatch(JSONObject var1) {
         this.status = DataStruct.CONNECTION_STATUS.UNKNOWN;

         try {
            this.model_name = var1.getString("model_name");
            this.serial_number = var1.getString("serial_number");
            this.hardware_version = var1.getString("hardware_version");
            this.firmware_version = var1.getString("firmware_version");
         } catch (JSONException var2) {
            Log.d("Watch", var2.toString());
         }

      }

      public String getMacAddress() {
         return this.mac_address;
      }

      public String getName() {
         return this.model_name;
      }

      public DataStruct.CONNECTION_STATUS getStatus() {
         return this.status;
      }

      public boolean isBond() {
         return this.isBond;
      }

      public void setBond(boolean var1) {
         this.isBond = var1;
      }

      public void setMacAddress(String var1) {
         this.mac_address = var1;
      }

      public void setName(String var1) {
         this.model_name = var1.substring(0, 2);
      }

      public void setStatus(DataStruct.CONNECTION_STATUS var1) {
         this.status = var1;
      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("id_model", this.idModel);
         var1.put("mac_address", this.mac_address);
         var1.put("serial_number", this.serial_number);
         var1.put("model_name", this.model_name);
         var1.put("model_name_to_show", this.model_name_to_show);
         var1.put("ble_device_name", this.ble_device_name);
         var1.put("hardware_version", this.hardware_version);
         var1.put("firmware_version", this.firmware_version);
         var1.put("last_dive_log_index", this.last_dive_log_index);
         var1.put("last_dive_time", this.last_dive_time);
         var1.put("scuba_dive_times", this.scuba_dive_times);
         var1.put("scuba_dive_max_depth", this.scuba_dive_max_depth);
         var1.put("scuba_dive_total_time", this.scuba_dive_total_time);
         var1.put("free_dive_times", this.free_dive_times);
         var1.put("free_dive_max_depth", this.free_dive_max_depth);
         var1.put("free_dive_longest_time", this.free_dive_longest_time);
         var1.put("no_flight_time", this.no_flight_time);
         var1.put("no_dive_time", this.no_dive_time);
         var1.put("start_time_for_last_downloaded_log", this.start_time_for_last_downloaded_log);
         var1.put("end_time_for_last_downloaded_log", this.end_time_for_last_downloaded_log);
         return var1;
      }

      ContentValues toInitMyWatchContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("serial_number", this.serial_number);
         var1.put("model_name", this.model_name);
         var1.put("model_name_to_show", this.model_name_to_show);
         var1.put("hardware_version", this.hardware_version);
         var1.put("firmware_version", this.firmware_version);
         return var1;
      }
   }

   public static class MyWatches {
      public ArrayList<DataStruct.MyWatch> list;

      public MyWatches() {
         this.list = new ArrayList();
      }

      MyWatches(Cursor var1) {
         this.list = new ArrayList();
         if (var1 != null) {
            for(int var2 = 0; var2 < var1.getCount(); ++var2) {
               this.list.add(new DataStruct.MyWatch(var1));
               var1.moveToNext();
            }

         }
      }

      public MyWatches(ArrayList<DataStruct.MyWatch> var1) {
         this.list = var1;
      }

      public MyWatches(JSONObject var1) {
         this.list = new ArrayList();

         label28: {
            JSONException var10000;
            label32: {
               boolean var10001;
               JSONArray var2;
               try {
                  var2 = var1.getJSONArray("items_array");
               } catch (JSONException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label32;
               }

               int var3 = 0;

               while(true) {
                  try {
                     if (var3 >= var2.length()) {
                        break label28;
                     }

                     var1 = var2.getJSONObject(var3);
                     ArrayList var4 = this.list;
                     DataStruct.MyWatch var5 = new DataStruct.MyWatch(var1);
                     var4.add(var5);
                  } catch (JSONException var6) {
                     var10000 = var6;
                     var10001 = false;
                     break;
                  }

                  ++var3;
               }
            }

            JSONException var8 = var10000;
            Log.d("Watches", var8.toString());
         }

         this.list = this.list;
      }

      public void add(DataStruct.MyWatch var1) {
         this.list.add(var1);
      }

      public void deleteMyWatchByMacAddress(String var1) {
         if (var1 != null) {
            for(int var2 = 0; var2 < this.list.size(); ++var2) {
               if (var1.equals(((DataStruct.MyWatch)this.list.get(var2)).mac_address)) {
                  this.list.remove(var2);
                  this.list.size();
                  break;
               }
            }

         }
      }

      public void deleteMyWatchBySerialNumber(String var1) {
         if (var1 != null && this.length() > 0) {
            for(int var2 = 0; var2 < this.list.size(); ++var2) {
               if (var1.equals(((DataStruct.MyWatch)this.list.get(var2)).serial_number)) {
                  this.list.remove(var2);
                  this.list.size();
                  break;
               }
            }
         }

      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? 0 : var1.size();
      }

      public void updateWatch(int var1, DataStruct.MyWatch var2) {
         this.list.set(var1, var2);
      }
   }

   public static class ReadLog {
      int command_group;
      boolean command_sent = false;
      boolean reply_received = false;
      int sub_command;

      public ReadLog(int var1, int var2, boolean var3) {
         this.command_group = var1;
         this.sub_command = var2;
         this.command_sent = var3;
      }

      public boolean isCommandSent() {
         return this.command_sent;
      }

      public boolean isReplyReceived() {
         return this.reply_received;
      }

      public void set_command_sent(boolean var1) {
         this.command_sent = var1;
      }

      public void set_reply_received(boolean var1) {
         this.reply_received = var1;
      }
   }

   public static class ReadLogs {
      public ArrayList<DataStruct.ReadLog> list = new ArrayList();

      public void addReadLog(DataStruct.ReadLog var1) {
         this.list.add(var1);
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
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

      public WatchSetting(Cursor var1) {
         this.idWatchSetting = var1.getLong(var1.getColumnIndex("_id"));
         this.model_name = var1.getString(var1.getColumnIndex("model_name"));
         this.setting_name = var1.getString(var1.getColumnIndex("setting_name"));
         this.data_type = var1.getInt(var1.getColumnIndex("data_type"));
         this.data_length = var1.getInt(var1.getColumnIndex("data_length"));
         this.value_int = var1.getInt(var1.getColumnIndex("value_int"));
         this.value_string = var1.getString(var1.getColumnIndex("value_string"));
      }

      public WatchSetting(JSONObject var1) {
         try {
            this.model_name = var1.getString("model_name");
            this.setting_name = var1.getString("setting_name");
            this.data_type = var1.getInt("data_type");
            this.data_length = var1.getInt("data_length");
            String var3 = var1.getString("value_string");
            this.value_string = var3;
            if (this.data_type == 0) {
               this.value_int = Integer.parseInt(var3);
            }
         } catch (JSONException var2) {
            Log.d("WatchSetting", var2.toString());
         }

      }

      ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         var1.put("model_name", this.model_name);
         var1.put("setting_name", this.setting_name);
         var1.put("data_type", this.data_type);
         var1.put("data_length", this.data_length);
         var1.put("value_int", this.value_int);
         var1.put("value_string", this.value_string);
         return var1;
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
      public ArrayList<DataStruct.WatchSetting> list;

      public WatchSettings(Cursor var1) {
         this.list = new ArrayList();
         if (var1 != null) {
            for(int var2 = 0; var2 < var1.getCount(); ++var2) {
               this.list.add(new DataStruct.WatchSetting(var1));
               var1.moveToNext();
            }

         }
      }

      WatchSettings(ArrayList<DataStruct.WatchSetting> var1) {
         this.list = var1;
      }

      public WatchSettings(JSONObject var1) {
         this.list = new ArrayList();

         JSONException var10000;
         label32: {
            boolean var10001;
            JSONArray var2;
            try {
               var2 = var1.getJSONArray("items_array");
            } catch (JSONException var7) {
               var10000 = var7;
               var10001 = false;
               break label32;
            }

            int var3 = 0;

            while(true) {
               try {
                  if (var3 >= var2.length()) {
                     return;
                  }

                  JSONObject var4 = var2.getJSONObject(var3);
                  ArrayList var5 = this.list;
                  DataStruct.WatchSetting var9 = new DataStruct.WatchSetting(var4);
                  var5.add(var9);
               } catch (JSONException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         JSONException var8 = var10000;
         Log.d("Watches", var8.toString());
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }
   }
}
