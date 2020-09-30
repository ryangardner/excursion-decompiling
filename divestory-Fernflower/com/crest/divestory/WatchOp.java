package com.crest.divestory;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import com.syntak.library.BleOp;
import com.syntak.library.ByteOp;
import com.syntak.library.FileOp;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class WatchOp {
   public static final int BLE_RESPONSE_TIMEOUT = 60000;
   public static final byte COMMAND_FACTORY_SERIAL_NUMBER = 2;
   public static final byte COMMAND_FACTORY_TEST = 1;
   public static final byte COMMAND_GROUP_FACTORY_TEST;
   public static final byte COMMAND_GROUP_INFO;
   public static final byte COMMAND_GROUP_LOGS;
   public static final byte COMMAND_GROUP_LOGS_ACK;
   public static final byte COMMAND_GROUP_SECURITY;
   public static final byte COMMAND_GROUP_SETTING;
   public static final byte COMMAND_INFO_CONNECTING = 0;
   public static final byte COMMAND_INFO_FIRMWARE_VERSION = 2;
   public static final byte COMMAND_INFO_FREE_DIVE_LONGEST_TIME = 10;
   public static final byte COMMAND_INFO_FREE_DIVE_MAX_DEPTH = 12;
   public static final byte COMMAND_INFO_FREE_DIVE_TIMES = 9;
   public static final byte COMMAND_INFO_HARDWARE_VERSION = 1;
   public static final byte COMMAND_INFO_LAST_DIVE_DATE_TIME = 5;
   public static final byte COMMAND_INFO_LAST_DIVE_LOG_INDEX = 4;
   public static final byte COMMAND_INFO_NO_DIVE_TIME = 13;
   public static final byte COMMAND_INFO_NO_FLIGHT_TIME = 14;
   public static final byte COMMAND_INFO_SCUBA_DIVE_MAX_DEPTH = 7;
   public static final byte COMMAND_INFO_SCUBA_DIVE_TIMES = 8;
   public static final byte COMMAND_INFO_SCUBA_DIVE_TOTAL_TIME = 6;
   public static final byte COMMAND_INFO_SERIAL_NUMBER = 3;
   public static final byte COMMAND_LOGS_COMPACT_HEADER = 1;
   public static final byte COMMAND_LOGS_FULL_HEADER = 2;
   public static final byte COMMAND_LOGS_PROFILE_DATA = 3;
   public static final byte COMMAND_RESPONSE_TIMEOUT;
   public static final byte COMMAND_SECURITY_GET_ENCODED_TEXT = 1;
   public static final byte COMMAND_SECURITY_SET_ENCODED_TEXT = 2;
   public static final byte COMMAND_SETTING_AUTO_DIVE_TYPE = 5;
   public static final byte COMMAND_SETTING_BACKLIGHT = 33;
   public static final byte COMMAND_SETTING_BUZZER = 32;
   public static final byte COMMAND_SETTING_CLEAR_LOG = 36;
   public static final byte COMMAND_SETTING_CLEAR_TISSUE_COMPARTMENT = 35;
   public static final byte COMMAND_SETTING_DATE = 1;
   public static final byte COMMAND_SETTING_DATE_FORMAT = 2;
   public static final byte COMMAND_SETTING_DISPLAY_UNIT = 29;
   public static final byte COMMAND_SETTING_FREE_DIVE_DEPTH_ALARM = 18;
   public static final byte COMMAND_SETTING_FREE_DIVE_DEPTH_ALARM_THRESHOLD_1 = 23;
   public static final byte COMMAND_SETTING_FREE_DIVE_DEPTH_ALARM_THRESHOLD_2 = 24;
   public static final byte COMMAND_SETTING_FREE_DIVE_DEPTH_ALARM_THRESHOLD_3 = 25;
   public static final byte COMMAND_SETTING_FREE_DIVE_SURFACE_TIME_ALARM = 19;
   public static final byte COMMAND_SETTING_FREE_DIVE_SURFACE_TIME_ALARM_THRESHOLD_1 = 26;
   public static final byte COMMAND_SETTING_FREE_DIVE_SURFACE_TIME_ALARM_THRESHOLD_2 = 27;
   public static final byte COMMAND_SETTING_FREE_DIVE_SURFACE_TIME_ALARM_THRESHOLD_3 = 28;
   public static final byte COMMAND_SETTING_FREE_DIVE_TIME_ALARM = 17;
   public static final byte COMMAND_SETTING_FREE_DIVE_TIME_ALARM_THRESHOLD_1 = 20;
   public static final byte COMMAND_SETTING_FREE_DIVE_TIME_ALARM_THRESHOLD_2 = 21;
   public static final byte COMMAND_SETTING_FREE_DIVE_TIME_ALARM_THRESHOLD_3 = 22;
   public static final byte COMMAND_SETTING_G_SENSOR = 16;
   public static final byte COMMAND_SETTING_O2_RATIO = 6;
   public static final byte COMMAND_SETTING_POWER_SAVING = 30;
   public static final byte COMMAND_SETTING_PPO2 = 7;
   public static final byte COMMAND_SETTING_RESET_SYSTEM = 37;
   public static final byte COMMAND_SETTING_RESTORE_FACTORY_SETTING = 38;
   public static final byte COMMAND_SETTING_SAFETY_FACTOR = 8;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_DEPTH_ALARM = 9;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_DEPTH_ALARM_THRESHOLD = 11;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_LOG_SAMPLING_RATE = 13;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_LOG_START_DEPTH = 14;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_LOG_STOP_TIME = 15;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_TIME_ALARM = 10;
   public static final byte COMMAND_SETTING_SCUBA_DIVE_TIME_ALARM_THRESHOLD = 12;
   public static final byte COMMAND_SETTING_SYNC_STORE_SETTING = 39;
   public static final byte COMMAND_SETTING_SYSTEM_READ_FROM_FLASH = 40;
   public static final byte COMMAND_SETTING_TIME = 3;
   public static final byte COMMAND_SETTING_TIME_FORMAT = 4;
   public static final byte COMMAND_SETTING_UTC_OFFSET = 31;
   public static final byte COMMAND_SETTING_VIBRATOR = 34;
   public static final byte[] COMMAND_UPGRADE_FIRMWARE;
   public static final int COMPACT_HEADER_LENGTH = 36;
   public static float ConversionMbarPerMeterWater;
   public static final int DATA_TYPE_INT = 0;
   public static final int DATA_TYPE_STRING = 1;
   public static final int DATE_LENGTH = 3;
   public static int DEFAULT_CYLINDER_CAPACITY;
   public static final int DIVE_DATE_TIME_LENGTH = 5;
   public static final int DIVE_LOG_INDEX_LENGTH = 2;
   public static final int DIVE_TIMES_LENGTH = 2;
   public static final int DIVE_TYPE_FREE = 2;
   public static final int DIVE_TYPE_GAUGE = 1;
   public static final int DIVE_TYPE_SCUBA = 0;
   public static final int DIVISOR_ALARM = 1;
   public static final int DIVISOR_CEILING = 4;
   public static final int DIVISOR_CNS = 5;
   public static final int DIVISOR_DECO = 3;
   public static final int DIVISOR_TEMP = 2;
   public static float EFFECTIVE_DIVING_DEPTH;
   public static int EFFECTIVE_DIVING_DEPTH_MBAR;
   public static final int ENCODED_TEXT_LENGTH = 8;
   public static final String FEEDBACK_EMAIL = "info@EOPI.co";
   public static final String FIRMWARE_VERSION_FORCE_DATA_TYPE_CEILING = "C01-4C";
   public static final int FREE_DIVE_DEPTH_ALARM_THRESHOLD_MAX = 99;
   public static final int FREE_DIVE_DEPTH_ALARM_THRESHOLD_MIN = 0;
   public static final int FREE_DIVE_DEPTH_LENGTH = 2;
   public static final int FREE_DIVE_SURFACE_TIME_ALARM_THRESHOLD_MAX = 60;
   public static final int FREE_DIVE_SURFACE_TIME_ALARM_THRESHOLD_MIN = 0;
   public static final int FREE_DIVE_TIME_ALARM_THRESHOLD_MAX = 360;
   public static final int FREE_DIVE_TIME_ALARM_THRESHOLD_MIN = 0;
   public static final int FREE_DIVE_TIME_LENGTH = 2;
   public static final int FULL_HEADER_LENGTH = 156;
   public static final int FW_VERSION_LENGTH = 6;
   public static final int HEADER_SIZE_DIVE_LOG_COMPACT = 36;
   public static final int HEADER_SIZE_DIVE_LOG_FULL = 156;
   public static final int HEADER_SIZE_FIRMWARE = 22;
   public static final int HW_VERSION_LENGTH = 6;
   public static int LANGUAGE_CHINESE;
   public static int LANGUAGE_ENGLISH;
   public static final int MBAR_PER_METER = 150;
   public static final int MODEL_LENGTH = 2;
   public static final int MTU_DATA_SIZE = 244;
   public static final int MTU_SIZE = 248;
   public static final int NO_DIVE_TIME_LENGTH = 2;
   public static final int NO_FLIGHT_TIME_LENGTH = 2;
   public static final int O2_RATIO_LENGTH = 1;
   public static final int O2_RATIO_MAX = 56;
   public static final int O2_RATIO_MIN = 21;
   public static final int OPTION_LENGTH = 1;
   public static byte READ_WATCH;
   public static final int[] SAMPLING_RATE;
   public static final int SCUBA_DEPTH_ALARM_THRESHOLD_MAX = 99;
   public static final int SCUBA_DEPTH_ALARM_THRESHOLD_MIN = 0;
   public static final int SCUBA_DIVE_DEPTH_LENGTH = 4;
   public static final int SCUBA_DIVE_TIME_LENGTH = 4;
   public static final int SCUBA_TIME_ALARM_THRESHOLD_MAX = 90;
   public static final int SCUBA_TIME_ALARM_THRESHOLD_MIN = 0;
   public static final int SERIAL_NUMBER_DISPLAY_LENGTH = 11;
   public static final int SERIAL_NUMBER_LENGTH = 12;
   public static final String SUBJECT_PREFIX = "APP_Feedback_";
   public static final String SUFFIX_FOR_OLD_FIRMWARE = "4C";
   public static final int THRESHOLD_LENGTH = 1;
   public static final int TIMEOUT_BLE_ENABLE = 10000;
   public static final int TIMEOUT_CONNECTING = 30000;
   public static final int TIMEOUT_DEVICE_SCAN = 10000;
   public static final int TIMEOUT_READ_DIVE_LOGS = 10000;
   public static final int TIMEOUT_READ_INFO = 10000;
   public static final int TIMEOUT_WRITE_ACK = 15000;
   public static final int TIME_FOR_LOGO = 200;
   public static final int TIME_LENGTH = 3;
   public static final int TRANSACTION_LENGTH = 128;
   public static final String UNKNOWN_MODEL = "Unknown Model";
   public static final int UTC_0_VALUE = 12;
   public static final int UTC_HOURS_OFFSET_LENGTH = 1;
   public static final UUID UUID_CHARACTERISTIC_SETTING;
   public static final UUID UUID_CHARACTERISTIC_UPGRADE;
   public static final UUID UUID_SERVICE;
   public static byte WRITE_WATCH;
   public static BleOp bleOp;
   public static ByteOrder byteOrder;
   public static HashMap<String, String> devices_bond;
   public static HashMap<String, String> devices_connected;
   public static DataStruct.BleDevices devices_scanned;
   public static HashMap<String, DataStruct.BleDevice> devices_scanned_map;
   public static DataStruct.DiveLogs dive_logs;
   public static ArrayList<DataStruct.DiveLog> dive_logs_list;
   public static boolean fake_firmware_version;
   public static DataStruct.FilterDiveLog filterDiveLog;
   public static DataStruct.Firmwares firmwares;
   public static boolean force_dive_profile_data_type_ceiling;
   public static boolean isAgreementGranted;
   public static boolean isExitApp;
   public static boolean isFirmwareUpgraded;
   public static boolean isLoginOK;
   public static boolean isLogoShown;
   public static boolean isPermissionGranted;
   public static boolean isRescanning;
   public static boolean isSyncDone;
   public static boolean isTimeout;
   public static boolean is_new_divelogs_downloaded;
   public static List<DataStruct.Firmware> list_firmware = Arrays.asList(new DataStruct.Firmware("CENTAURI", "CENTAURI", "GE1-5A", "GENESIS_GE1_05A_200805_RELEASE.bin"), new DataStruct.Firmware("EXCURSION", "EXCURSION", "D01-5A", "DEEPSIX_D01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("TC1", "TC1", "TU1-5A", "TUSA_TU1_05A_200805_RELEASE.bin", false), new DataStruct.Firmware("CR-4", "CR-4", "C01-5A", "CREST_C01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("CR-2", "CR-2", "C01-5A", "CREST_C01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("CR-4A", "CR-4", "C01-5A", "CREST_C01_05A_200805_RELEASE_GA.bin"));
   public static String mac_address_to_scan;
   public static float mbarATM;
   public static DataStruct.Models models;
   public static DataStruct.MyWatches myWatches;
   public static int new_last_dive_log_index;
   public static String prefSerialNumber;
   public static int selected_watch;
   public static int utc_offset;
   public static DataStruct.WatchSetting_GB watchSetting_gb;
   public static DataStruct.MyWatch watch_connected;
   public static HashMap<String, DataStruct.MyWatch> watches_map;

   static {
      firmwares = new DataStruct.Firmwares(list_firmware);
      fake_firmware_version = true;
      force_dive_profile_data_type_ceiling = false;
      ConversionMbarPerMeterWater = 100.0F;
      mbarATM = 1000.0F;
      DEFAULT_CYLINDER_CAPACITY = 0;
      UUID_SERVICE = UUID.fromString("f000ffe0-ab12-45ec-84c8-46483f4626e9");
      UUID_CHARACTERISTIC_SETTING = UUID.fromString("f000ffe1-ab12-45ec-84c8-46483f4626e9");
      UUID_CHARACTERISTIC_UPGRADE = UUID.fromString("f000ffe2-ab12-45ec-84c8-46483f4626e9");
      SAMPLING_RATE = new int[]{10, 20, 30, 60};
      EFFECTIVE_DIVING_DEPTH = 0.5F;
      EFFECTIVE_DIVING_DEPTH_MBAR = (int)convertMeterToAbsMbar(0.5F);
      bleOp = null;
      selected_watch = -1;
      models = null;
      myWatches = null;
      watches_map = new HashMap();
      watch_connected = new DataStruct.MyWatch();
      dive_logs = new DataStruct.DiveLogs();
      dive_logs_list = new ArrayList();
      filterDiveLog = new DataStruct.FilterDiveLog();
      devices_bond = null;
      devices_connected = null;
      devices_scanned = null;
      devices_scanned_map = new HashMap();
      watchSetting_gb = new DataStruct.WatchSetting_GB();
      byteOrder = ByteOrder.LITTLE_ENDIAN;
      prefSerialNumber = null;
      LANGUAGE_ENGLISH = 0;
      LANGUAGE_CHINESE = 1;
      mac_address_to_scan = null;
      new_last_dive_log_index = 0;
      utc_offset = 0;
      isLogoShown = false;
      isAgreementGranted = false;
      isLoginOK = false;
      isPermissionGranted = false;
      isExitApp = false;
      isSyncDone = false;
      isFirmwareUpgraded = false;
      is_new_divelogs_downloaded = false;
      isTimeout = false;
      isRescanning = false;
      READ_WATCH = (byte)1;
      WRITE_WATCH = (byte)0;
      COMMAND_GROUP_INFO = b(160);
      COMMAND_GROUP_SETTING = b(176);
      COMMAND_GROUP_LOGS = b(192);
      COMMAND_GROUP_LOGS_ACK = b(193);
      COMMAND_GROUP_FACTORY_TEST = b(208);
      COMMAND_GROUP_SECURITY = b(224);
      COMMAND_RESPONSE_TIMEOUT = b(255);
      COMMAND_UPGRADE_FIRMWARE = new byte[]{b(18), b(52), b(86), b(120)};
   }

   public static void ackDiveLog(String var0, byte var1, int var2, byte[] var3) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_LOGS_ACK, var1, READ_WATCH, var2, var3)).getBytes());
   }

   public static byte b(int var0) {
      return (byte)var0;
   }

   public static String checkWatchDeviceName(String var0) {
      if (var0 == null) {
         return null;
      } else if (var0.contains("Crest-CR4")) {
         return "CR-4";
      } else if (var0.contains("Crest-GA004")) {
         return "GA004";
      } else if (var0.contains("CENTAURI")) {
         return "Genesis CENTAURI";
      } else if (var0.contains("EXCURSION")) {
         return "Deep6 EXCURSION";
      } else if (var0.contains("TC1")) {
         return "TUSA TC1";
      } else {
         return var0.contains("IQ1301") ? "TUSA TC1" : null;
      }
   }

   public static float convertAbsMbarToMeter(float var0) {
      return (var0 - mbarATM) / ConversionMbarPerMeterWater;
   }

   public static float convertMeterToAbsMbar(float var0) {
      return var0 * ConversionMbarPerMeterWater + mbarATM;
   }

   public static void dump_dive_profile_data(byte[] var0, String var1) {
      FileOp.dumpByteArrayToTextFile(var0, FileOp.combinePath(AppBase.folder_dump, var1), 10);
   }

   public static void firmware_upgrade_send_command(String var0) {
      bleOp.write_Characteristic(var0, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, COMMAND_UPGRADE_FIRMWARE);
   }

   public static void firmware_upgrade_send_data_block(String var0, byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      System.arraycopy(var1, var2, var4, 0, var3);
      bleOp.write_Characteristic(var0, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, var4);
   }

   public static void firmware_upgrade_send_header(String var0, byte[] var1) {
      bleOp.write_Characteristic(var0, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, var1);
   }

   public static byte getChecksum(byte var0, byte var1, byte var2, byte var3, byte[] var4) {
      int var5 = ByteOp.b2ui(var3);
      int var6 = ByteOp.b2ui(var0);
      int var7 = 0;
      byte var9 = (byte)((byte)((byte)(var6 + 0) + ByteOp.b2ui(var1)) + ByteOp.b2ui(var2));
      byte var8 = var9;
      if (var5 > 0) {
         var8 = var9;
         if (var4 != null) {
            var9 = (byte)(var9 + var5);
            var5 = var4.length;

            while(true) {
               var8 = var9;
               if (var7 >= var5) {
                  break;
               }

               var9 = (byte)(var9 + ByteOp.b2ui(var4[var7]));
               ++var7;
            }
         }
      }

      return (byte)(var8 ^ 255);
   }

   public static String getModelName(String var0, String var1) {
      if (var1.contains("GA-004")) {
         if (var0.contains("TBK") || var0.contains("TCR") || var0.contains("CBK") || var0.contains("CCR")) {
            return "CR-4A";
         } else {
            return "Unknown Model";
         }
      } else if (var0.contains("GBK")) {
         return "CENTAURI";
      } else if (var0.contains("DBK")) {
         return "EXCURSION";
      } else if (!var0.contains("TBK") && !var0.contains("TWS")) {
         if (!var0.contains("CBK") && !var0.contains("CCR") && !var0.contains("CWS")) {
            if (!var0.contains("CYY") && !var0.contains("CYB")) {
               return "Unknown Model";
            } else {
               return "CR-2";
            }
         } else {
            return "CR-4";
         }
      } else {
         return "TC1";
      }
   }

   public static WatchOp.WATCH_REPLY handle_received_charteristics(String var0, BluetoothGattCharacteristic var1) {
      WatchOp.TASK var3 = WatchOp.TASK.NONE;
      WatchOp.WATCH_REPLY var4 = new WatchOp.WATCH_REPLY(WatchOp.WATCH_REPLY.Error.NO_ERROR);
      byte[] var2 = var1.getValue();
      UUID var5 = var1.getUuid();
      if (!var5.equals(UUID_CHARACTERISTIC_SETTING) && !var5.equals(UUID_CHARACTERISTIC_UPGRADE)) {
         var4.setError(WatchOp.WATCH_REPLY.Error.UUID);
         Log.d("WATCH_REPLY", "UUID not SETTING or UPGRADE");
         return var4;
      } else if (var2.length <= 2) {
         var4.setError(WatchOp.WATCH_REPLY.Error.VALUE_LENGTH_TOO_SHORT);
         Log.d("WATCH_REPLY", "response length<=2");
         return var4;
      } else {
         var4.setContent(var2);
         if (!var4.isChecksumGood) {
            var4.setError(WatchOp.WATCH_REPLY.Error.CHECKSUM);
            Log.d("WATCH_REPLY", "checksum error");
         }

         return var4;
      }
   }

   public static void init_my_watches() {
      if (AppBase.dbOp != null) {
         if (myWatches == null) {
            DataStruct.MyWatches var0 = new DataStruct.MyWatches(AppBase.dbOp.getMyWatches());
            myWatches = var0;
            if (var0.length() > 0) {
               Iterator var1 = myWatches.list.iterator();

               while(var1.hasNext()) {
                  DataStruct.MyWatch var2 = (DataStruct.MyWatch)var1.next();
                  watches_map.put(var2.mac_address, var2);
               }
            }

         }
      }
   }

   public static boolean isOldFirmware(String var0) {
      return var0.substring(4, 5).compareTo("4C") <= 0;
   }

   public static boolean isSerialNumberValid(String var0) {
      boolean var1;
      if (!var0.contains("TBK") && !var0.contains("TCR") && !var0.contains("CBK") && !var0.contains("CCR") && !var0.contains("GBK") && !var0.contains("DBK") && !var0.contains("TWS") && !var0.contains("CWS") && !var0.contains("CYY") && !var0.contains("CYB")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static float lengthFoot2Meter(float var0) {
      return (float)((double)var0 / 3.28D);
   }

   public static float lengthMeter2Foot(float var0) {
      return (float)((double)var0 * 3.28D);
   }

   public static void open_ble(Activity var0) {
      if (bleOp == null) {
         bleOp = new BleOp(var0, 10000, true) {
            public void OnBluetoothEnabled(boolean var1) {
            }

            public void OnBluetoothSupported(boolean var1) {
            }

            public void OnCharacteristicRead(String var1, BluetoothGattCharacteristic var2) {
               super.OnCharacteristicRead(var1, var2);
               WatchOp.handle_received_charteristics(var1, var2);
            }

            public void OnDeviceFound(BluetoothDevice var1, int var2, byte[] var3) {
            }

            public void OnServicesFound(List<BluetoothGattService> var1) {
            }
         };
      }
   }

   public static void readDiveLog(String var0, byte var1, int var2) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_LOGS, var1, READ_WATCH, 2, ByteOp.intToUint16(var2, byteOrder))).getBytes());
   }

   public static void readDiveProfileData(String var0, byte var1, int var2, long var3) {
      byte[] var5 = ByteOp.combineByteArray(ByteOp.intToUint16(var2, byteOrder), ByteOp.longToUint32(var3, byteOrder));
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_LOGS, var1, READ_WATCH, 6, var5)).getBytes());
   }

   public static void readInfo(String var0, byte var1) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_INFO, var1, READ_WATCH, 0, (byte[])null)).getBytes());
   }

   public static void readSetting(String var0, byte var1) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_SETTING, var1, READ_WATCH, 0, (byte[])null)).getBytes());
   }

   public static void send_command(String var0, byte[] var1) {
      bleOp.write_Characteristic(var0, UUID_SERVICE, UUID_CHARACTERISTIC_SETTING, var1);
   }

   public static void set_MTU(String var0, int var1) {
      bleOp.set_MTU(var0, var1);
   }

   public static void writeFactoryTest(String var0, byte var1, byte[] var2, int var3) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_FACTORY_TEST, var1, WRITE_WATCH, var3, var2)).getBytes());
   }

   public static void writeSetting(String var0, byte var1, byte[] var2, int var3) {
      send_command(var0, (new WatchOp.COMMAND_SENTENCE(COMMAND_GROUP_SETTING, var1, WRITE_WATCH, var3, var2)).getBytes());
   }

   public static enum ACTION {
      DELETE,
      DOWNLOAD,
      EDIT,
      LIST_DIVE_LOGS,
      SCAN,
      SET_WATCH,
      SYNC,
      UPGRADE;

      static {
         WatchOp.ACTION var0 = new WatchOp.ACTION("DELETE", 7);
         DELETE = var0;
      }
   }

   public static class COMMAND_SENTENCE {
      byte checksum;
      byte command_group;
      byte[] data = null;
      int data_len;
      byte data_length;
      byte direction;
      byte sub_command;

      COMMAND_SENTENCE(byte var1, byte var2, byte var3, int var4, byte[] var5) {
         this.command_group = var1;
         this.sub_command = var2;
         this.direction = var3;
         this.data_len = var4;
         byte var6 = ByteOp.i2b(var4);
         this.data_length = var6;
         this.data = var5;
         this.checksum = WatchOp.getChecksum(var1, var2, var3, var6, var5);
      }

      public byte[] getBytes() {
         byte[] var1 = this.data;
         int var2 = 5;
         if (var1 != null) {
            var2 = 5 + var1.length;
         }

         var1 = new byte[var2];
         byte var3 = this.command_group;
         var2 = 0;
         var1[0] = (byte)var3;
         var1[1] = (byte)this.sub_command;
         var1[2] = (byte)this.direction;
         var1[3] = (byte)this.data_length;
         if (this.data_len > 0) {
            while(var2 < this.data_len) {
               var1[var2 + 4] = (byte)this.data[var2];
               ++var2;
            }
         }

         var1[this.data_len + 4] = (byte)this.checksum;
         return var1;
      }
   }

   public static enum RESULT {
      ADDED,
      CANCEL,
      DELETED,
      DOWNLOADED,
      SET_DONE,
      UPGRADED;

      static {
         WatchOp.RESULT var0 = new WatchOp.RESULT("DELETED", 5);
         DELETED = var0;
      }
   }

   public static enum TASK {
      ASK_FIRMWARE_VERSION,
      ASK_FREE_DIVE_LONGEST_TIME,
      ASK_FREE_DIVE_MAX_DEPTH,
      ASK_FREE_DIVE_TIMES,
      ASK_HARDWARE_VERSION,
      ASK_LAST_DIVE_DATE_TIME,
      ASK_LAST_DIVE_INDEX,
      ASK_NO_DIVE_TIME,
      ASK_NO_FLIGHT_TIME,
      ASK_SCUBA_DIVE_MAX_DEPTH,
      ASK_SCUBA_DIVE_TIMES,
      ASK_SCUBA_DIVE_TOTAL_TIME,
      ASK_SERIAL_NUMBER,
      BINDING,
      CHECK_ENABLED,
      CHECK_SERVICES,
      CHECK_SUPPORTED,
      CONNECTING,
      ENABLING,
      LOGIN,
      LOGIN_OK,
      NONE,
      PROCESS_FIRMWARE_VERSION,
      PROCESS_FREE_DIVE_LONGEST_TIME,
      PROCESS_FREE_DIVE_MAX_DEPTH,
      PROCESS_FREE_DIVE_TIMES,
      PROCESS_HARDWARE_VERSION,
      PROCESS_LAST_DIVE_DATE_TIME,
      PROCESS_LAST_DIVE_INDEX,
      PROCESS_NO_DIVE_TIME,
      PROCESS_NO_FLIGHT_TIME,
      PROCESS_SCUBA_DIVE_MAX_DEPTH,
      PROCESS_SCUBA_DIVE_TIMES,
      PROCESS_SCUBA_DIVE_TOTAL_TIME,
      PROCESS_SERIAL_NUMBER,
      RE_SCANNING,
      SEND,
      SHOW_ENABLED,
      SHOW_ERROR,
      SHOW_SCANNED,
      SHOW_SCANNED_ALL,
      SHOW_SUPPORTED,
      START_SCANNING;

      static {
         WatchOp.TASK var0 = new WatchOp.TASK("SHOW_ERROR", 42);
         SHOW_ERROR = var0;
      }
   }

   public static class WATCH_REPLY {
      public byte checksum;
      public byte command_group;
      public byte[] data;
      public int data_length;
      public byte direction;
      public WatchOp.WATCH_REPLY.Error error;
      public boolean isChecksumGood;
      public byte[] raw_response;
      public byte sub_command;

      WATCH_REPLY(WatchOp.WATCH_REPLY.Error var1) {
         this.error = WatchOp.WATCH_REPLY.Error.NO_ERROR;
         this.isChecksumGood = false;
         this.error = var1;
      }

      void setContent(byte[] var1) {
         this.raw_response = var1;
         boolean var2 = false;
         this.command_group = (byte)((byte)(var1[0] - 1));
         this.sub_command = var1[1];
         this.direction = var1[2];
         int var3 = ByteOp.b2ui(var1[3]);
         this.data_length = var3;
         if (var3 >= 0) {
            byte[] var4 = new byte[var3];
            this.data = var4;
            System.arraycopy(var1, 4, var4, 0, var3);
            this.checksum = var1[this.data_length + 4];
            byte var5 = WatchOp.getChecksum(var1[0], this.sub_command, this.direction, var1[3], this.data);
            if (this.checksum == var5) {
               var2 = true;
            }

            this.isChecksumGood = var2;
         } else {
            this.error = WatchOp.WATCH_REPLY.Error.DATA_LENGTH_NEGATIVE;
         }

      }

      void setError(WatchOp.WATCH_REPLY.Error var1) {
         this.error = var1;
      }

      public static enum Error {
         CHECKSUM,
         DATA_LENGTH_NEGATIVE,
         NO_ERROR,
         UUID,
         VALUE_LENGTH_TOO_SHORT;

         static {
            WatchOp.WATCH_REPLY.Error var0 = new WatchOp.WATCH_REPLY.Error("CHECKSUM", 4);
            CHECKSUM = var0;
         }
      }
   }

   public static enum X_AXIS_UNIT {
      MINUTE,
      SECOND;

      static {
         WatchOp.X_AXIS_UNIT var0 = new WatchOp.X_AXIS_UNIT("SECOND", 1);
         SECOND = var0;
      }
   }
}
