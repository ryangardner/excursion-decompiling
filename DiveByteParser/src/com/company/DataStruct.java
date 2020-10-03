package com.company;

import com.company.ByteOp;
import com.company.TimeOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.nio.ByteOrder;

public class DataStruct {

   public static class DiveLog {
      private static final Float DEFAULT_CYLINDER_CAPACITY = 0.2f;
      private final ByteOrder WATCH_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
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

      public DiveLog(String var1, byte[] var2) {
         this.isFavorite = false;
         this.isStored = false;
         this.watch_serial_number = null;
         this.surface_temperature = 0.0F;
         this.location = null;
         this.breathing_gas = null;
         this.cylinder_capacity = (float)DEFAULT_CYLINDER_CAPACITY;
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

         this.dive_log_index = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.dive_type = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.profile_data_length = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);
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

         long var11 = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);
         this.duration = var11;
         this.end_time = this.start_time + var11 * 1000L;

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.sampling_rate = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.max_depth = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.lowest_water_temperature = ByteOp.int32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.average_depth = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.data_start_address = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.data_end_address = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

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

         this.surface_ATM = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.is_high_elevation_diving = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.safety_factor = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.PPO2 = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.CNS_at_start = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.max_CNS = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.deco_model = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = 0; var5 < 32; ++var5) {
            this.end_N2_compartment_bytes[var5] = var2[var6 + var5];
         }

         for(var5 = 0; var5 < 16; var6 += 2) {
            var3[0] = var2[var6];
            var3[1] = var2[var6 + 1];
            this.end_N2_compartment[var5] = ByteOp.uint16ToInt(var3, WATCH_BYTE_ORDER);
            ++var5;
         }

         for(var5 = 0; var5 < 32; ++var5) {
            this.end_H2_compartment_bytes[var5] = var2[var6 + var5];
         }

         for(var5 = 0; var5 < 16; var6 += 2) {
            var3[0] = var2[var6];
            var3[1] = var2[var6 + 1];
            this.end_H2_compartment[var5] = ByteOp.uint16ToInt(var3, WATCH_BYTE_ORDER);
            ++var5;
         }

         byte var14 = 0;

         for(var5 = 0; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.no_fligt_fime = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);

         for(var5 = var14; var5 < 4; ++var6) {
            var13[var5] = var2[var6];
            ++var5;
         }

         this.no_dive_time = ByteOp.uint32ToLong(var13, WATCH_BYTE_ORDER);
      }


      @Override
      public String toString() {
         return "DiveLog{" +
                 "WATCH_BYTE_ORDER=" + WATCH_BYTE_ORDER +
                 ", CNS_at_start=" + CNS_at_start +
                 ", O2_ratio=" + O2_ratio +
                 ", PPO2=" + PPO2 +
                 ", aux_weight=" + aux_weight +
                 ", average_depth=" + average_depth +
                 ", breathing_gas='" + breathing_gas + '\'' +
                 ", buddy='" + buddy + '\'' +
                 ", comment='" + comment + '\'' +
                 ", cylinder_capacity=" + cylinder_capacity +
                 ", data_end_address=" + data_end_address +
                 ", data_start_address=" + data_start_address +
                 ", deco_model=" + deco_model +
                 ", dive_log_index=" + dive_log_index +
                 ", dive_type=" + dive_type +
                 ", duration=" + duration +
                 ", end_H2_compartment=" + Arrays.toString(end_H2_compartment) +
                 ", end_H2_compartment_bytes=" + Arrays.toString(end_H2_compartment_bytes) +
                 ", end_N2_compartment=" + Arrays.toString(end_N2_compartment) +
                 ", end_N2_compartment_bytes=" + Arrays.toString(end_N2_compartment_bytes) +
                 ", end_time=" + end_time +
                 ", firmware_version='" + firmware_version + '\'' +
                 ", id=" + id +
                 ", isDownloaded=" + isDownloaded +
                 ", isFavorite=" + isFavorite +
                 ", isStored=" + isStored +
                 ", is_high_elevation_diving=" + is_high_elevation_diving +
                 ", location='" + location + '\'' +
                 ", lowest_water_temperature=" + lowest_water_temperature +
                 ", max_CNS=" + max_CNS +
                 ", max_depth=" + max_depth +
                 ", no_dive_time=" + no_dive_time +
                 ", no_fligt_fime=" + no_fligt_fime +
                 ", note='" + note + '\'' +
                 ", pressure_end=" + pressure_end +
                 ", pressure_start=" + pressure_start +
                 ", profile_data_length=" + profile_data_length +
                 ", rating=" + rating +
                 ", safety_factor=" + safety_factor +
                 ", sampling_rate=" + sampling_rate +
                 ", start_time=" + start_time +
                 ", surface_ATM=" + surface_ATM +
                 ", surface_temperature=" + surface_temperature +
                 ", visibility=" + visibility +
                 ", watch_serial_number='" + watch_serial_number + '\'' +
                 ", wave='" + wave + '\'' +
                 ", weather='" + weather + '\'' +
                 ", wind='" + wind + '\'' +
                 '}';
      }
   }

   public static class DiveLogs {
      public ArrayList<DataStruct.DiveLog> list;

      public DiveLogs() {
         this.list = new ArrayList();
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
      private final ByteOrder WATCH_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

      public ArrayList<DataStruct.DiveProfileDatum> list;

      public DiveProfileData() {
         this.list = new ArrayList();
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
         boolean oldFirmware = false; // WatchOp.isOldFirmware(var4);
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
               if (oldFirmware) {
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
                     var9.depth = ByteOp.uint16ToInt(var10, WATCH_BYTE_ORDER);
                     var10[0] = (byte)var2[var12 + 4];
                     var10[1] = (byte)var2[var12 + 5];
                     var20 = ByteOp.uint16ToInt(var10, WATCH_BYTE_ORDER);
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
                     var9.depth = ByteOp.uint16ToInt(var10, WATCH_BYTE_ORDER);
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
                     var9.temperature = ByteOp.uint16ToInt(var10, WATCH_BYTE_ORDER);
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

                        //Log.d("Error", "unknown data_type");
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

   }

   static enum FIRMWARE_FILE_VERSION {
      C01_4C,
      C01_5A_Crest,
      C01_5A_Deep6,
      C01_5A_Genesis,
      C01_5A_TUSA;

//      static {
//         DataStruct.FIRMWARE_FILE_VERSION var0 = new DataStruct.FIRMWARE_FILE_VERSION("C01_5A_Crest", 4);
//         C01_5A_Crest = var0;
//      }
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
//
//   public static class Language {
//      String language;
//      String languageCode;
//
//      Language(String var1, String var2) {
//         this.languageCode = var1;
//         this.language = var2;
//      }
//
//      Language(JSONObject var1) {
//         try {
//            this.languageCode = var1.getString("language_code");
//            this.language = var1.getString("language");
//         } catch (JSONException var2) {
//            Log.d("Language", var2.toString());
//         }
//
//      }
//   }
//
//   public static class Languages {
//      ArrayList<DataStruct.Language> list;
//
//      Languages(ArrayList<DataStruct.Language> var1) {
//         this.list = var1;
//      }
//
//      Languages(JSONObject var1) {
//         this.list = new ArrayList();
//
//         JSONException var10000;
//         label32: {
//            boolean var10001;
//            JSONArray var2;
//            try {
//               var2 = var1.getJSONArray("items_array");
//            } catch (JSONException var6) {
//               var10000 = var6;
//               var10001 = false;
//               break label32;
//            }
//
//            int var3 = 0;
//
//            while(true) {
//               try {
//                  if (var3 >= var2.length()) {
//                     return;
//                  }
//
//                  var1 = var2.getJSONObject(var3);
//                  DataStruct.Language var4 = new DataStruct.Language(var1);
//                  this.list.add(var4);
//               } catch (JSONException var5) {
//                  var10000 = var5;
//                  var10001 = false;
//                  break;
//               }
//
//               ++var3;
//            }
//         }
//
//         JSONException var7 = var10000;
//         Log.d("Languages", var7.toString());
//      }
//
//      int length() {
//         ArrayList var1 = this.list;
//         return var1 == null ? -1 : var1.size();
//      }
//   }

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

      public Model(String var1, String var2, String var3) {
         this.model_name = var1;
         this.hardware_version = var2;
         this.firmware_version = var3;
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



      WatchSettings(ArrayList<DataStruct.WatchSetting> var1) {
         this.list = var1;
      }

      public int length() {
         ArrayList var1 = this.list;
         return var1 == null ? -1 : var1.size();
      }
   }
}
