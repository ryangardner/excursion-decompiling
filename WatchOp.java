/**
decompiled from apk using enjarify to get a .class file and then intellij idea's decompiler
**/
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.crest.divestory;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.database.Cursor;
import android.util.Log;
import com.crest.divestory.DataStruct.BleDevices;
import com.crest.divestory.DataStruct.DiveLogs;
import com.crest.divestory.DataStruct.FilterDiveLog;
import com.crest.divestory.DataStruct.Firmware;
import com.crest.divestory.DataStruct.Firmwares;
import com.crest.divestory.DataStruct.Models;
import com.crest.divestory.DataStruct.MyWatch;
import com.crest.divestory.DataStruct.MyWatches;
import com.crest.divestory.DataStruct.WatchSetting_GB;
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
    public static final byte COMMAND_GROUP_FACTORY_TEST = 0;
    public static final byte COMMAND_GROUP_INFO = 0;
    public static final byte COMMAND_GROUP_LOGS = 0;
    public static final byte COMMAND_GROUP_LOGS_ACK = 0;
    public static final byte COMMAND_GROUP_SECURITY = 0;
    public static final byte COMMAND_GROUP_SETTING = 0;
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
    public static final byte COMMAND_RESPONSE_TIMEOUT = 0;
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
    public static HashMap devices_bond;
    public static HashMap devices_connected;
    public static BleDevices devices_scanned;
    public static HashMap devices_scanned_map;
    public static DiveLogs dive_logs;
    public static ArrayList dive_logs_list;
    public static boolean fake_firmware_version;
    public static FilterDiveLog filterDiveLog;
    public static Firmwares firmwares;
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
    public static List list_firmware;
    public static String mac_address_to_scan;
    public static float mbarATM;
    public static Models models;
    public static MyWatches myWatches;
    public static int new_last_dive_log_index;
    public static String prefSerialNumber;
    public static int selected_watch;
    public static int utc_offset;
    public static WatchSetting_GB watchSetting_gb;
    public static MyWatch watch_connected;
    public static HashMap watches_map;

    static {
        Firmware[] var0 = new Firmware[6];
        String var2 = "CENTAURI";
        Firmware var1 = new Firmware(var2, var2, "GE1-5A", "GENESIS_GE1_05A_200805_RELEASE.bin");
        var0[0] = var1;
        String var3 = "EXCURSION";
        var1 = new Firmware(var3, var3, "D01-5A", "DEEPSIX_D01_05A_200805_RELEASE.bin");
        byte var4 = 1;
        var0[var4] = var1;
        var1 = new Firmware("TC1", "TC1", "TU1-5A", "TUSA_TU1_05A_200805_RELEASE.bin", false);
        byte var5 = 2;
        var0[var5] = var1;
        String var6 = "CR-4";
        String var7 = "C01-5A";
        String var8 = "CREST_C01_05A_200805_RELEASE.bin";
        var1 = new Firmware(var6, var6, var7, var8);
        byte var9 = 3;
        var0[var9] = var1;
        String var10 = "CR-2";
        var1 = new Firmware(var10, var10, var7, var8);
        byte var11 = 4;
        var0[var11] = var1;
        var1 = new Firmware("CR-4A", var6, var7, "CREST_C01_05A_200805_RELEASE_GA.bin");
        var0[5] = var1;
        list_firmware = Arrays.asList(var0);
        List var17 = list_firmware;
        Firmwares var14 = new Firmwares(var17);
        firmwares = var14;
        fake_firmware_version = (boolean)var4;
        force_dive_profile_data_type_ceiling = false;
        ConversionMbarPerMeterWater = 100.0F;
        mbarATM = 1000.0F;
        DEFAULT_CYLINDER_CAPACITY = 0;
        UUID_SERVICE = UUID.fromString("f000ffe0-ab12-45ec-84c8-46483f4626e9");
        UUID_CHARACTERISTIC_SETTING = UUID.fromString("f000ffe1-ab12-45ec-84c8-46483f4626e9");
        UUID_CHARACTERISTIC_UPGRADE = UUID.fromString("f000ffe2-ab12-45ec-84c8-46483f4626e9");
        int[] var15 = new int[var11];
        var15[0] = 10;
        var15[1] = 20;
        var15[2] = 30;
        var15[3] = 60;
        SAMPLING_RATE = var15;
        float var12 = 0.5F;
        EFFECTIVE_DIVING_DEPTH = var12;
        EFFECTIVE_DIVING_DEPTH_MBAR = (int)convertMeterToAbsMbar(var12);
        bleOp = null;
        selected_watch = -1;
        models = null;
        myWatches = null;
        HashMap var18 = new HashMap();
        watches_map = var18;
        MyWatch var19 = new MyWatch();
        watch_connected = var19;
        DiveLogs var20 = new DiveLogs();
        dive_logs = var20;
        ArrayList var21 = new ArrayList();
        dive_logs_list = var21;
        FilterDiveLog var22 = new FilterDiveLog();
        filterDiveLog = var22;
        devices_bond = null;
        devices_connected = null;
        devices_scanned = null;
        var18 = new HashMap();
        devices_scanned_map = var18;
        WatchSetting_GB var23 = new WatchSetting_GB();
        watchSetting_gb = var23;
        byteOrder = ByteOrder.LITTLE_ENDIAN;
        prefSerialNumber = null;
        LANGUAGE_ENGLISH = 0;
        LANGUAGE_CHINESE = var4;
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
        READ_WATCH = var4;
        WRITE_WATCH = 0;
        COMMAND_GROUP_INFO = b(160);
        COMMAND_GROUP_SETTING = b(176);
        COMMAND_GROUP_LOGS = b(192);
        COMMAND_GROUP_LOGS_ACK = b(193);
        COMMAND_GROUP_FACTORY_TEST = b(208);
        COMMAND_GROUP_SECURITY = b(224);
        COMMAND_RESPONSE_TIMEOUT = b(255);
        byte[] var16 = new byte[var11];
        byte var13 = b(18);
        var16[0] = var13;
        var13 = b(52);
        var16[var4] = var13;
        var13 = b(86);
        var16[var5] = var13;
        var13 = b(120);
        var16[var9] = var13;
        COMMAND_UPGRADE_FIRMWARE = var16;
    }

    public WatchOp() {
    }

    public static void ackDiveLog(String var0, byte var1, int var2, byte[] var3) {
        byte var5 = COMMAND_GROUP_LOGS_ACK;
        byte var6 = READ_WATCH;
        WatchOp$COMMAND_SENTENCE var4 = new WatchOp$COMMAND_SENTENCE(var5, var1, var6, var2, var3);
        byte[] var7 = var4.getBytes();
        send_command(var0, var7);
    }

    public static byte b(int var0) {
        return (byte)var0;
    }

    public static String checkWatchDeviceName(String var0) {
        if (var0 == null) {
            return null;
        } else {
            String var1 = "Crest-CR4";
            boolean var2 = var0.contains(var1);
            if (var2) {
                return "CR-4";
            } else {
                var1 = "Crest-GA004";
                var2 = var0.contains(var1);
                if (var2) {
                    return "GA004";
                } else {
                    var1 = "CENTAURI";
                    var2 = var0.contains(var1);
                    if (var2) {
                        return "Genesis CENTAURI";
                    } else {
                        var1 = "EXCURSION";
                        var2 = var0.contains(var1);
                        if (var2) {
                            return "Deep6 EXCURSION";
                        } else {
                            var1 = "TC1";
                            var2 = var0.contains(var1);
                            String var3 = "TUSA TC1";
                            if (var2) {
                                return var3;
                            } else {
                                var1 = "IQ1301";
                                boolean var4 = var0.contains(var1);
                                return var4 ? var3 : null;
                            }
                        }
                    }
                }
            }
        }
    }

    public static float convertAbsMbarToMeter(float var0) {
        float var1 = mbarATM;
        var0 -= var1;
        var1 = ConversionMbarPerMeterWater;
        return var0 / var1;
    }

    public static float convertMeterToAbsMbar(float var0) {
        float var1 = ConversionMbarPerMeterWater;
        var0 *= var1;
        var1 = mbarATM;
        return var0 + var1;
    }

    public static void dump_dive_profile_data(byte[] var0, String var1) {
        var1 = FileOp.combinePath(AppBase.folder_dump, var1);
        FileOp.dumpByteArrayToTextFile(var0, var1, 10);
    }

    public static void firmware_upgrade_send_command(String var0) {
        BleOp var1 = bleOp;
        UUID var2 = UUID_SERVICE;
        UUID var3 = UUID_CHARACTERISTIC_UPGRADE;
        byte[] var4 = COMMAND_UPGRADE_FIRMWARE;
        var1.write_Characteristic(var0, var2, var3, var4);
    }

    public static void firmware_upgrade_send_data_block(String var0, byte[] var1, int var2, int var3) {
        byte[] var4 = new byte[var3];
        System.arraycopy(var1, var2, var4, 0, var3);
        BleOp var7 = bleOp;
        UUID var5 = UUID_SERVICE;
        UUID var6 = UUID_CHARACTERISTIC_UPGRADE;
        var7.write_Characteristic(var0, var5, var6, var4);
    }

    public static void firmware_upgrade_send_header(String var0, byte[] var1) {
        BleOp var2 = bleOp;
        UUID var3 = UUID_SERVICE;
        UUID var4 = UUID_CHARACTERISTIC_UPGRADE;
        var2.write_Characteristic(var0, var3, var4, var1);
    }

    public static byte getChecksum(byte var0, byte var1, byte var2, byte var3, byte[] var4) {
        int var9 = ByteOp.b2ui(var3);
        int var6 = ByteOp.b2ui(var0);
        int var5 = 0;
        var0 = (byte)(var6 + 0);
        int var7 = ByteOp.b2ui(var1);
        var0 = (byte)(var0 + var7);
        var7 = ByteOp.b2ui(var2);
        var0 = (byte)(var0 + var7);
        if (var9 > 0 && var4 != null) {
            var0 = (byte)(var0 + var9);

            for(var7 = var4.length; var5 < var7; ++var5) {
                int var8 = ByteOp.b2ui(var4[var5]);
                var0 = (byte)(var0 + var8);
            }
        }

        return (byte)(var0 ^ 255);
    }

    public static String getModelName(String var0, String var1) {
        boolean var2 = var1.contains("GA-004");
        String var3 = "CCR";
        String var4 = "CBK";
        String var5 = "TBK";
        boolean var6;
        if (var2) {
            var2 = var0.contains(var5);
            if (!var2) {
                var1 = "TCR";
                var2 = var0.contains(var1);
                if (!var2) {
                    var2 = var0.contains(var4);
                    if (!var2) {
                        var6 = var0.contains(var3);
                        if (!var6) {
                            return "Unknown Model";
                        }
                    }
                }
            }

            return "CR-4A";
        } else {
            var1 = "GBK";
            var2 = var0.contains(var1);
            if (var2) {
                return "CENTAURI";
            } else {
                var1 = "DBK";
                var2 = var0.contains(var1);
                if (var2) {
                    return "EXCURSION";
                } else {
                    var2 = var0.contains(var5);
                    if (!var2) {
                        var1 = "TWS";
                        var2 = var0.contains(var1);
                        if (!var2) {
                            var2 = var0.contains(var4);
                            if (!var2) {
                                var2 = var0.contains(var3);
                                if (!var2) {
                                    var1 = "CWS";
                                    var2 = var0.contains(var1);
                                    if (!var2) {
                                        var1 = "CYY";
                                        var2 = var0.contains(var1);
                                        if (!var2) {
                                            var1 = "CYB";
                                            var6 = var0.contains(var1);
                                            if (!var6) {
                                                return "Unknown Model";
                                            }
                                        }

                                        return "CR-2";
                                    }
                                }
                            }

                            return "CR-4";
                        }
                    }

                    return "TC1";
                }
            }
        }
    }

    public static WatchOp$WATCH_REPLY handle_received_charteristics(String var0, BluetoothGattCharacteristic var1) {
        WatchOp$TASK var7 = WatchOp$TASK.NONE;
        WatchOp$WATCH_REPLY$Error var2 = WatchOp$WATCH_REPLY$Error.NO_ERROR;
        WatchOp$WATCH_REPLY var8 = new WatchOp$WATCH_REPLY(var2);
        byte[] var12 = var1.getValue();
        UUID var9 = var1.getUuid();
        UUID var3 = UUID_CHARACTERISTIC_SETTING;
        boolean var4 = var9.equals(var3);
        String var5 = "WATCH_REPLY";
        boolean var6;
        WatchOp$WATCH_REPLY$Error var10;
        if (!var4) {
            var3 = UUID_CHARACTERISTIC_UPGRADE;
            var6 = var9.equals(var3);
            if (!var6) {
                var10 = WatchOp$WATCH_REPLY$Error.UUID;
                var8.setError(var10);
                Log.d(var5, "UUID not SETTING or UPGRADE");
                return var8;
            }
        }

        int var14 = var12.length;
        byte var13 = 2;
        if (var14 <= var13) {
            var10 = WatchOp$WATCH_REPLY$Error.VALUE_LENGTH_TOO_SHORT;
            var8.setError(var10);
            Log.d(var5, "response length<=2");
            return var8;
        } else {
            var8.setContent(var12);
            var6 = var8.isChecksumGood;
            if (!var6) {
                var10 = WatchOp$WATCH_REPLY$Error.CHECKSUM;
                var8.setError(var10);
                String var11 = "checksum error";
                Log.d(var5, var11);
            }

            return var8;
        }
    }

    public static void init_my_watches() {
        DbOp var0 = AppBase.dbOp;
        if (var0 != null) {
            MyWatches var6 = myWatches;
            if (var6 == null) {
                Cursor var1 = AppBase.dbOp.getMyWatches();
                var6 = new MyWatches(var1);
                myWatches = var6;
                int var2 = var6.length();
                if (var2 > 0) {
                    Iterator var7 = myWatches.list.iterator();

                    while(true) {
                        boolean var3 = var7.hasNext();
                        if (!var3) {
                            break;
                        }

                        MyWatch var8 = (MyWatch)var7.next();
                        HashMap var4 = watches_map;
                        String var5 = var8.mac_address;
                        var4.put(var5, var8);
                    }
                }

            }
        }
    }

    public static boolean isOldFirmware(String var0) {
        byte var1 = 4;
        byte var2 = 5;
        var0 = var0.substring(var1, var2);
        String var3 = "4C";
        int var4 = var0.compareTo(var3);
        return var4 <= 0;
    }

    public static boolean isSerialNumberValid(String var0) {
        String var1 = "TBK";
        boolean var2 = var0.contains(var1);
        boolean var3;
        if (!var2) {
            var1 = "TCR";
            var2 = var0.contains(var1);
            if (!var2) {
                var1 = "CBK";
                var2 = var0.contains(var1);
                if (!var2) {
                    var1 = "CCR";
                    var2 = var0.contains(var1);
                    if (!var2) {
                        var1 = "GBK";
                        var2 = var0.contains(var1);
                        if (!var2) {
                            var1 = "DBK";
                            var2 = var0.contains(var1);
                            if (!var2) {
                                var1 = "TWS";
                                var2 = var0.contains(var1);
                                if (!var2) {
                                    var1 = "CWS";
                                    var2 = var0.contains(var1);
                                    if (!var2) {
                                        var1 = "CYY";
                                        var2 = var0.contains(var1);
                                        if (!var2) {
                                            var1 = "CYB";
                                            var3 = var0.contains(var1);
                                            if (!var3) {
                                                var3 = false;
                                                var0 = null;
                                                return var3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        var3 = true;
        return var3;
    }

    public static float lengthFoot2Meter(float var0) {
        return (float)((double)var0 / 3.28D);
    }

    public static float lengthMeter2Foot(float var0) {
        return (float)((double)var0 * 3.28D);
    }

    public static void open_ble(Activity var0) {
        BleOp var1 = bleOp;
        if (var1 == null) {
            WatchOp$1 var2 = new WatchOp$1(var0, 10000, true);
            bleOp = var2;
        }
    }

    public static void readDiveLog(String var0, byte var1, int var2) {
        byte var4 = COMMAND_GROUP_LOGS;
        byte var5 = READ_WATCH;
        ByteOrder var6 = byteOrder;
        byte[] var7 = ByteOp.intToUint16(var2, var6);
        WatchOp$COMMAND_SENTENCE var3 = new WatchOp$COMMAND_SENTENCE(var4, var1, var5, 2, var7);
        byte[] var8 = var3.getBytes();
        send_command(var0, var8);
    }

    public static void readDiveProfileData(String var0, byte var1, int var2, long var3) {
        ByteOrder var5 = byteOrder;
        byte[] var6 = ByteOp.intToUint16(var2, var5);
        var5 = byteOrder;
        byte[] var7 = ByteOp.longToUint32(var3, var5);
        byte[][] var8 = new byte[][]{var6, var7};
        byte[] var9 = ByteOp.combineByteArray(var8);
        byte var10 = COMMAND_GROUP_LOGS;
        byte var11 = READ_WATCH;
        WatchOp$COMMAND_SENTENCE var13 = new WatchOp$COMMAND_SENTENCE(var10, var1, var11, 6, var9);
        byte[] var12 = var13.getBytes();
        send_command(var0, var12);
    }

    public static void readInfo(String var0, byte var1) {
        byte var3 = COMMAND_GROUP_INFO;
        byte var4 = READ_WATCH;
        WatchOp$COMMAND_SENTENCE var2 = new WatchOp$COMMAND_SENTENCE(var3, var1, var4, 0, (byte[])null);
        byte[] var5 = var2.getBytes();
        send_command(var0, var5);
    }

    public static void readSetting(String var0, byte var1) {
        byte var3 = COMMAND_GROUP_SETTING;
        byte var4 = READ_WATCH;
        WatchOp$COMMAND_SENTENCE var2 = new WatchOp$COMMAND_SENTENCE(var3, var1, var4, 0, (byte[])null);
        byte[] var5 = var2.getBytes();
        send_command(var0, var5);
    }

    public static void send_command(String var0, byte[] var1) {
        BleOp var2 = bleOp;
        UUID var3 = UUID_SERVICE;
        UUID var4 = UUID_CHARACTERISTIC_SETTING;
        var2.write_Characteristic(var0, var3, var4, var1);
    }

    public static void set_MTU(String var0, int var1) {
        bleOp.set_MTU(var0, var1);
    }

    public static void writeFactoryTest(String var0, byte var1, byte[] var2, int var3) {
        byte var5 = COMMAND_GROUP_FACTORY_TEST;
        byte var6 = WRITE_WATCH;
        WatchOp$COMMAND_SENTENCE var4 = new WatchOp$COMMAND_SENTENCE(var5, var1, var6, var3, var2);
        byte[] var7 = var4.getBytes();
        send_command(var0, var7);
    }

    public static void writeSetting(String var0, byte var1, byte[] var2, int var3) {
        byte var5 = COMMAND_GROUP_SETTING;
        byte var6 = WRITE_WATCH;
        WatchOp$COMMAND_SENTENCE var4 = new WatchOp$COMMAND_SENTENCE(var5, var1, var6, var3, var2);
        byte[] var7 = var4.getBytes();
        send_command(var0, var7);
    }
}
