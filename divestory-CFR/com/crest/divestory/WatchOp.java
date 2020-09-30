/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattService
 *  android.database.Cursor
 *  android.util.Log
 */
package com.crest.divestory;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.database.Cursor;
import android.util.Log;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
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
    public static float ConversionMbarPerMeterWater = 0.0f;
    public static final int DATA_TYPE_INT = 0;
    public static final int DATA_TYPE_STRING = 1;
    public static final int DATE_LENGTH = 3;
    public static int DEFAULT_CYLINDER_CAPACITY = 0;
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
    public static float EFFECTIVE_DIVING_DEPTH = 0.0f;
    public static int EFFECTIVE_DIVING_DEPTH_MBAR = 0;
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
    public static int LANGUAGE_CHINESE = 0;
    public static int LANGUAGE_ENGLISH = 0;
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
    public static byte READ_WATCH = 0;
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
    public static List<DataStruct.Firmware> list_firmware;
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
        list_firmware = Arrays.asList(new DataStruct.Firmware("CENTAURI", "CENTAURI", "GE1-5A", "GENESIS_GE1_05A_200805_RELEASE.bin"), new DataStruct.Firmware("EXCURSION", "EXCURSION", "D01-5A", "DEEPSIX_D01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("TC1", "TC1", "TU1-5A", "TUSA_TU1_05A_200805_RELEASE.bin", false), new DataStruct.Firmware("CR-4", "CR-4", "C01-5A", "CREST_C01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("CR-2", "CR-2", "C01-5A", "CREST_C01_05A_200805_RELEASE.bin"), new DataStruct.Firmware("CR-4A", "CR-4", "C01-5A", "CREST_C01_05A_200805_RELEASE_GA.bin"));
        firmwares = new DataStruct.Firmwares(list_firmware);
        fake_firmware_version = true;
        force_dive_profile_data_type_ceiling = false;
        ConversionMbarPerMeterWater = 100.0f;
        mbarATM = 1000.0f;
        DEFAULT_CYLINDER_CAPACITY = 0;
        UUID_SERVICE = UUID.fromString("f000ffe0-ab12-45ec-84c8-46483f4626e9");
        UUID_CHARACTERISTIC_SETTING = UUID.fromString("f000ffe1-ab12-45ec-84c8-46483f4626e9");
        UUID_CHARACTERISTIC_UPGRADE = UUID.fromString("f000ffe2-ab12-45ec-84c8-46483f4626e9");
        SAMPLING_RATE = new int[]{10, 20, 30, 60};
        EFFECTIVE_DIVING_DEPTH = 0.5f;
        EFFECTIVE_DIVING_DEPTH_MBAR = (int)WatchOp.convertMeterToAbsMbar(0.5f);
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
        READ_WATCH = (byte)(true ? 1 : 0);
        WRITE_WATCH = (byte)(false ? 1 : 0);
        COMMAND_GROUP_INFO = WatchOp.b(160);
        COMMAND_GROUP_SETTING = WatchOp.b(176);
        COMMAND_GROUP_LOGS = WatchOp.b(192);
        COMMAND_GROUP_LOGS_ACK = WatchOp.b(193);
        COMMAND_GROUP_FACTORY_TEST = WatchOp.b(208);
        COMMAND_GROUP_SECURITY = WatchOp.b(224);
        COMMAND_RESPONSE_TIMEOUT = WatchOp.b(255);
        COMMAND_UPGRADE_FIRMWARE = new byte[]{WatchOp.b(18), WatchOp.b(52), WatchOp.b(86), WatchOp.b(120)};
    }

    public static void ackDiveLog(String string2, byte by, int n, byte[] arrby) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_LOGS_ACK, by, READ_WATCH, n, arrby).getBytes());
    }

    public static byte b(int n) {
        return (byte)n;
    }

    public static String checkWatchDeviceName(String string2) {
        if (string2 == null) {
            return null;
        }
        if (string2.contains("Crest-CR4")) {
            return "CR-4";
        }
        if (string2.contains("Crest-GA004")) {
            return "GA004";
        }
        if (string2.contains("CENTAURI")) {
            return "Genesis CENTAURI";
        }
        if (string2.contains("EXCURSION")) {
            return "Deep6 EXCURSION";
        }
        if (string2.contains("TC1")) {
            return "TUSA TC1";
        }
        if (!string2.contains("IQ1301")) return null;
        return "TUSA TC1";
    }

    public static float convertAbsMbarToMeter(float f) {
        return (f - mbarATM) / ConversionMbarPerMeterWater;
    }

    public static float convertMeterToAbsMbar(float f) {
        return f * ConversionMbarPerMeterWater + mbarATM;
    }

    public static void dump_dive_profile_data(byte[] arrby, String string2) {
        FileOp.dumpByteArrayToTextFile(arrby, FileOp.combinePath(AppBase.folder_dump, string2), 10);
    }

    public static void firmware_upgrade_send_command(String string2) {
        bleOp.write_Characteristic(string2, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, COMMAND_UPGRADE_FIRMWARE);
    }

    public static void firmware_upgrade_send_data_block(String string2, byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        bleOp.write_Characteristic(string2, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, arrby2);
    }

    public static void firmware_upgrade_send_header(String string2, byte[] arrby) {
        bleOp.write_Characteristic(string2, UUID_SERVICE, UUID_CHARACTERISTIC_UPGRADE, arrby);
    }

    public static byte getChecksum(byte by, byte by2, byte by3, byte by4, byte[] arrby) {
        int n = ByteOp.b2ui(by4);
        int n2 = ByteOp.b2ui(by);
        int n3 = 0;
        int n4 = n2 = (int)((byte)((byte)((byte)(n2 + 0) + ByteOp.b2ui(by2)) + ByteOp.b2ui(by3)));
        if (n <= 0) return (byte)(n4 ^ 255);
        n4 = n2;
        if (arrby == null) return (byte)(n4 ^ 255);
        n2 = (byte)(n2 + n);
        n = arrby.length;
        do {
            n4 = n2;
            if (n3 >= n) return (byte)(n4 ^ 255);
            n2 = (byte)(n2 + ByteOp.b2ui(arrby[n3]));
            ++n3;
        } while (true);
    }

    public static String getModelName(String string2, String string3) {
        if (string3.contains("GA-004")) {
            if (string2.contains("TBK")) return "CR-4A";
            if (string2.contains("TCR")) return "CR-4A";
            if (string2.contains("CBK")) return "CR-4A";
            if (!string2.contains("CCR")) return UNKNOWN_MODEL;
            return "CR-4A";
        }
        if (string2.contains("GBK")) {
            return "CENTAURI";
        }
        if (string2.contains("DBK")) {
            return "EXCURSION";
        }
        if (string2.contains("TBK")) return "TC1";
        if (string2.contains("TWS")) {
            return "TC1";
        }
        if (string2.contains("CBK")) return "CR-4";
        if (string2.contains("CCR")) return "CR-4";
        if (string2.contains("CWS")) {
            return "CR-4";
        }
        if (string2.contains("CYY")) return "CR-2";
        if (!string2.contains("CYB")) return UNKNOWN_MODEL;
        return "CR-2";
    }

    public static WATCH_REPLY handle_received_charteristics(String object, BluetoothGattCharacteristic object2) {
        object = TASK.NONE;
        object = new WATCH_REPLY(WATCH_REPLY.Error.NO_ERROR);
        byte[] arrby = object2.getValue();
        if (!((UUID)(object2 = object2.getUuid())).equals(UUID_CHARACTERISTIC_SETTING) && !((UUID)object2).equals(UUID_CHARACTERISTIC_UPGRADE)) {
            ((WATCH_REPLY)object).setError(WATCH_REPLY.Error.UUID);
            Log.d((String)"WATCH_REPLY", (String)"UUID not SETTING or UPGRADE");
            return object;
        }
        if (arrby.length <= 2) {
            ((WATCH_REPLY)object).setError(WATCH_REPLY.Error.VALUE_LENGTH_TOO_SHORT);
            Log.d((String)"WATCH_REPLY", (String)"response length<=2");
            return object;
        }
        ((WATCH_REPLY)object).setContent(arrby);
        if (((WATCH_REPLY)object).isChecksumGood) return object;
        ((WATCH_REPLY)object).setError(WATCH_REPLY.Error.CHECKSUM);
        Log.d((String)"WATCH_REPLY", (String)"checksum error");
        return object;
    }

    public static void init_my_watches() {
        if (AppBase.dbOp == null) {
            return;
        }
        if (myWatches != null) {
            return;
        }
        Object object = new DataStruct.MyWatches(AppBase.dbOp.getMyWatches());
        myWatches = object;
        if (((DataStruct.MyWatches)object).length() <= 0) return;
        Iterator<DataStruct.MyWatch> iterator2 = WatchOp.myWatches.list.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            watches_map.put(((DataStruct.MyWatch)object).mac_address, (DataStruct.MyWatch)object);
        }
    }

    public static boolean isOldFirmware(String string2) {
        if (string2.substring(4, 5).compareTo(SUFFIX_FOR_OLD_FIRMWARE) > 0) return false;
        return true;
    }

    public static boolean isSerialNumberValid(String string2) {
        if (string2.contains("TBK")) return true;
        if (string2.contains("TCR")) return true;
        if (string2.contains("CBK")) return true;
        if (string2.contains("CCR")) return true;
        if (string2.contains("GBK")) return true;
        if (string2.contains("DBK")) return true;
        if (string2.contains("TWS")) return true;
        if (string2.contains("CWS")) return true;
        if (string2.contains("CYY")) return true;
        if (string2.contains("CYB")) return true;
        return false;
    }

    public static float lengthFoot2Meter(float f) {
        return (float)((double)f / 3.28);
    }

    public static float lengthMeter2Foot(float f) {
        return (float)((double)f * 3.28);
    }

    public static void open_ble(Activity activity) {
        if (bleOp != null) {
            return;
        }
        bleOp = new BleOp(activity, 10000, true){

            @Override
            public void OnBluetoothEnabled(boolean bl) {
            }

            @Override
            public void OnBluetoothSupported(boolean bl) {
            }

            @Override
            public void OnCharacteristicRead(String string2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                super.OnCharacteristicRead(string2, bluetoothGattCharacteristic);
                WatchOp.handle_received_charteristics(string2, bluetoothGattCharacteristic);
            }

            @Override
            public void OnDeviceFound(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
            }

            @Override
            public void OnServicesFound(List<BluetoothGattService> list) {
            }
        };
    }

    public static void readDiveLog(String string2, byte by, int n) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_LOGS, by, READ_WATCH, 2, ByteOp.intToUint16(n, byteOrder)).getBytes());
    }

    public static void readDiveProfileData(String string2, byte by, int n, long l) {
        byte[] arrby = ByteOp.combineByteArray(ByteOp.intToUint16(n, byteOrder), ByteOp.longToUint32(l, byteOrder));
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_LOGS, by, READ_WATCH, 6, arrby).getBytes());
    }

    public static void readInfo(String string2, byte by) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_INFO, by, READ_WATCH, 0, null).getBytes());
    }

    public static void readSetting(String string2, byte by) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_SETTING, by, READ_WATCH, 0, null).getBytes());
    }

    public static void send_command(String string2, byte[] arrby) {
        bleOp.write_Characteristic(string2, UUID_SERVICE, UUID_CHARACTERISTIC_SETTING, arrby);
    }

    public static void set_MTU(String string2, int n) {
        bleOp.set_MTU(string2, n);
    }

    public static void writeFactoryTest(String string2, byte by, byte[] arrby, int n) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_FACTORY_TEST, by, WRITE_WATCH, n, arrby).getBytes());
    }

    public static void writeSetting(String string2, byte by, byte[] arrby, int n) {
        WatchOp.send_command(string2, new COMMAND_SENTENCE(COMMAND_GROUP_SETTING, by, WRITE_WATCH, n, arrby).getBytes());
    }

    public static final class ACTION
    extends Enum<ACTION> {
        private static final /* synthetic */ ACTION[] $VALUES;
        public static final /* enum */ ACTION DELETE;
        public static final /* enum */ ACTION DOWNLOAD;
        public static final /* enum */ ACTION EDIT;
        public static final /* enum */ ACTION LIST_DIVE_LOGS;
        public static final /* enum */ ACTION SCAN;
        public static final /* enum */ ACTION SET_WATCH;
        public static final /* enum */ ACTION SYNC;
        public static final /* enum */ ACTION UPGRADE;

        static {
            ACTION aCTION;
            SCAN = new ACTION();
            EDIT = new ACTION();
            LIST_DIVE_LOGS = new ACTION();
            SYNC = new ACTION();
            UPGRADE = new ACTION();
            DOWNLOAD = new ACTION();
            SET_WATCH = new ACTION();
            DELETE = aCTION = new ACTION();
            $VALUES = new ACTION[]{SCAN, EDIT, LIST_DIVE_LOGS, SYNC, UPGRADE, DOWNLOAD, SET_WATCH, aCTION};
        }

        public static ACTION valueOf(String string2) {
            return Enum.valueOf(ACTION.class, string2);
        }

        public static ACTION[] values() {
            return (ACTION[])$VALUES.clone();
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

        COMMAND_SENTENCE(byte by, byte by2, byte by3, int n, byte[] arrby) {
            byte by4;
            this.command_group = by;
            this.sub_command = by2;
            this.direction = by3;
            this.data_len = n;
            this.data_length = by4 = ByteOp.i2b(n);
            this.data = arrby;
            this.checksum = WatchOp.getChecksum(by, by2, by3, by4, arrby);
        }

        public byte[] getBytes() {
            byte[] arrby = this.data;
            int n = 5;
            if (arrby != null) {
                n = 5 + arrby.length;
            }
            arrby = new byte[n];
            byte by = this.command_group;
            arrby[0] = by;
            arrby[1] = this.sub_command;
            arrby[2] = this.direction;
            arrby[3] = this.data_length;
            if (this.data_len > 0) {
                for (n = 0; n < this.data_len; ++n) {
                    arrby[n + 4] = this.data[n];
                }
            }
            arrby[this.data_len + 4] = this.checksum;
            return arrby;
        }
    }

    public static final class RESULT
    extends Enum<RESULT> {
        private static final /* synthetic */ RESULT[] $VALUES;
        public static final /* enum */ RESULT ADDED;
        public static final /* enum */ RESULT CANCEL;
        public static final /* enum */ RESULT DELETED;
        public static final /* enum */ RESULT DOWNLOADED;
        public static final /* enum */ RESULT SET_DONE;
        public static final /* enum */ RESULT UPGRADED;

        static {
            RESULT rESULT;
            CANCEL = new RESULT();
            ADDED = new RESULT();
            UPGRADED = new RESULT();
            DOWNLOADED = new RESULT();
            SET_DONE = new RESULT();
            DELETED = rESULT = new RESULT();
            $VALUES = new RESULT[]{CANCEL, ADDED, UPGRADED, DOWNLOADED, SET_DONE, rESULT};
        }

        public static RESULT valueOf(String string2) {
            return Enum.valueOf(RESULT.class, string2);
        }

        public static RESULT[] values() {
            return (RESULT[])$VALUES.clone();
        }
    }

    public static final class TASK
    extends Enum<TASK> {
        private static final /* synthetic */ TASK[] $VALUES;
        public static final /* enum */ TASK ASK_FIRMWARE_VERSION;
        public static final /* enum */ TASK ASK_FREE_DIVE_LONGEST_TIME;
        public static final /* enum */ TASK ASK_FREE_DIVE_MAX_DEPTH;
        public static final /* enum */ TASK ASK_FREE_DIVE_TIMES;
        public static final /* enum */ TASK ASK_HARDWARE_VERSION;
        public static final /* enum */ TASK ASK_LAST_DIVE_DATE_TIME;
        public static final /* enum */ TASK ASK_LAST_DIVE_INDEX;
        public static final /* enum */ TASK ASK_NO_DIVE_TIME;
        public static final /* enum */ TASK ASK_NO_FLIGHT_TIME;
        public static final /* enum */ TASK ASK_SCUBA_DIVE_MAX_DEPTH;
        public static final /* enum */ TASK ASK_SCUBA_DIVE_TIMES;
        public static final /* enum */ TASK ASK_SCUBA_DIVE_TOTAL_TIME;
        public static final /* enum */ TASK ASK_SERIAL_NUMBER;
        public static final /* enum */ TASK BINDING;
        public static final /* enum */ TASK CHECK_ENABLED;
        public static final /* enum */ TASK CHECK_SERVICES;
        public static final /* enum */ TASK CHECK_SUPPORTED;
        public static final /* enum */ TASK CONNECTING;
        public static final /* enum */ TASK ENABLING;
        public static final /* enum */ TASK LOGIN;
        public static final /* enum */ TASK LOGIN_OK;
        public static final /* enum */ TASK NONE;
        public static final /* enum */ TASK PROCESS_FIRMWARE_VERSION;
        public static final /* enum */ TASK PROCESS_FREE_DIVE_LONGEST_TIME;
        public static final /* enum */ TASK PROCESS_FREE_DIVE_MAX_DEPTH;
        public static final /* enum */ TASK PROCESS_FREE_DIVE_TIMES;
        public static final /* enum */ TASK PROCESS_HARDWARE_VERSION;
        public static final /* enum */ TASK PROCESS_LAST_DIVE_DATE_TIME;
        public static final /* enum */ TASK PROCESS_LAST_DIVE_INDEX;
        public static final /* enum */ TASK PROCESS_NO_DIVE_TIME;
        public static final /* enum */ TASK PROCESS_NO_FLIGHT_TIME;
        public static final /* enum */ TASK PROCESS_SCUBA_DIVE_MAX_DEPTH;
        public static final /* enum */ TASK PROCESS_SCUBA_DIVE_TIMES;
        public static final /* enum */ TASK PROCESS_SCUBA_DIVE_TOTAL_TIME;
        public static final /* enum */ TASK PROCESS_SERIAL_NUMBER;
        public static final /* enum */ TASK RE_SCANNING;
        public static final /* enum */ TASK SEND;
        public static final /* enum */ TASK SHOW_ENABLED;
        public static final /* enum */ TASK SHOW_ERROR;
        public static final /* enum */ TASK SHOW_SCANNED;
        public static final /* enum */ TASK SHOW_SCANNED_ALL;
        public static final /* enum */ TASK SHOW_SUPPORTED;
        public static final /* enum */ TASK START_SCANNING;

        static {
            TASK tASK;
            NONE = new TASK();
            CHECK_SUPPORTED = new TASK();
            SHOW_SUPPORTED = new TASK();
            CHECK_ENABLED = new TASK();
            SHOW_ENABLED = new TASK();
            ENABLING = new TASK();
            START_SCANNING = new TASK();
            RE_SCANNING = new TASK();
            SHOW_SCANNED = new TASK();
            SHOW_SCANNED_ALL = new TASK();
            CONNECTING = new TASK();
            CHECK_SERVICES = new TASK();
            ASK_SERIAL_NUMBER = new TASK();
            PROCESS_SERIAL_NUMBER = new TASK();
            ASK_HARDWARE_VERSION = new TASK();
            PROCESS_HARDWARE_VERSION = new TASK();
            ASK_FIRMWARE_VERSION = new TASK();
            PROCESS_FIRMWARE_VERSION = new TASK();
            ASK_LAST_DIVE_INDEX = new TASK();
            PROCESS_LAST_DIVE_INDEX = new TASK();
            ASK_LAST_DIVE_DATE_TIME = new TASK();
            PROCESS_LAST_DIVE_DATE_TIME = new TASK();
            ASK_SCUBA_DIVE_TIMES = new TASK();
            PROCESS_SCUBA_DIVE_TIMES = new TASK();
            ASK_SCUBA_DIVE_MAX_DEPTH = new TASK();
            PROCESS_SCUBA_DIVE_MAX_DEPTH = new TASK();
            ASK_SCUBA_DIVE_TOTAL_TIME = new TASK();
            PROCESS_SCUBA_DIVE_TOTAL_TIME = new TASK();
            ASK_FREE_DIVE_TIMES = new TASK();
            PROCESS_FREE_DIVE_TIMES = new TASK();
            ASK_FREE_DIVE_MAX_DEPTH = new TASK();
            PROCESS_FREE_DIVE_MAX_DEPTH = new TASK();
            ASK_FREE_DIVE_LONGEST_TIME = new TASK();
            PROCESS_FREE_DIVE_LONGEST_TIME = new TASK();
            ASK_NO_FLIGHT_TIME = new TASK();
            PROCESS_NO_FLIGHT_TIME = new TASK();
            ASK_NO_DIVE_TIME = new TASK();
            PROCESS_NO_DIVE_TIME = new TASK();
            SEND = new TASK();
            BINDING = new TASK();
            LOGIN = new TASK();
            LOGIN_OK = new TASK();
            SHOW_ERROR = tASK = new TASK();
            $VALUES = new TASK[]{NONE, CHECK_SUPPORTED, SHOW_SUPPORTED, CHECK_ENABLED, SHOW_ENABLED, ENABLING, START_SCANNING, RE_SCANNING, SHOW_SCANNED, SHOW_SCANNED_ALL, CONNECTING, CHECK_SERVICES, ASK_SERIAL_NUMBER, PROCESS_SERIAL_NUMBER, ASK_HARDWARE_VERSION, PROCESS_HARDWARE_VERSION, ASK_FIRMWARE_VERSION, PROCESS_FIRMWARE_VERSION, ASK_LAST_DIVE_INDEX, PROCESS_LAST_DIVE_INDEX, ASK_LAST_DIVE_DATE_TIME, PROCESS_LAST_DIVE_DATE_TIME, ASK_SCUBA_DIVE_TIMES, PROCESS_SCUBA_DIVE_TIMES, ASK_SCUBA_DIVE_MAX_DEPTH, PROCESS_SCUBA_DIVE_MAX_DEPTH, ASK_SCUBA_DIVE_TOTAL_TIME, PROCESS_SCUBA_DIVE_TOTAL_TIME, ASK_FREE_DIVE_TIMES, PROCESS_FREE_DIVE_TIMES, ASK_FREE_DIVE_MAX_DEPTH, PROCESS_FREE_DIVE_MAX_DEPTH, ASK_FREE_DIVE_LONGEST_TIME, PROCESS_FREE_DIVE_LONGEST_TIME, ASK_NO_FLIGHT_TIME, PROCESS_NO_FLIGHT_TIME, ASK_NO_DIVE_TIME, PROCESS_NO_DIVE_TIME, SEND, BINDING, LOGIN, LOGIN_OK, tASK};
        }

        public static TASK valueOf(String string2) {
            return Enum.valueOf(TASK.class, string2);
        }

        public static TASK[] values() {
            return (TASK[])$VALUES.clone();
        }
    }

    public static class WATCH_REPLY {
        public byte checksum;
        public byte command_group;
        public byte[] data;
        public int data_length;
        public byte direction;
        public Error error = Error.NO_ERROR;
        public boolean isChecksumGood = false;
        public byte[] raw_response;
        public byte sub_command;

        WATCH_REPLY(Error error) {
            this.error = error;
        }

        void setContent(byte[] arrby) {
            int n;
            this.raw_response = arrby;
            boolean bl = false;
            this.command_group = (byte)(arrby[0] - 1);
            this.sub_command = arrby[1];
            this.direction = arrby[2];
            this.data_length = n = ByteOp.b2ui(arrby[3]);
            if (n < 0) {
                this.error = Error.DATA_LENGTH_NEGATIVE;
                return;
            }
            byte[] arrby2 = new byte[n];
            this.data = arrby2;
            System.arraycopy(arrby, 4, arrby2, 0, n);
            this.checksum = arrby[this.data_length + 4];
            n = WatchOp.getChecksum(arrby[0], this.sub_command, this.direction, arrby[3], this.data);
            if (this.checksum == n) {
                bl = true;
            }
            this.isChecksumGood = bl;
        }

        void setError(Error error) {
            this.error = error;
        }

        public static final class Error
        extends Enum<Error> {
            private static final /* synthetic */ Error[] $VALUES;
            public static final /* enum */ Error CHECKSUM;
            public static final /* enum */ Error DATA_LENGTH_NEGATIVE;
            public static final /* enum */ Error NO_ERROR;
            public static final /* enum */ Error UUID;
            public static final /* enum */ Error VALUE_LENGTH_TOO_SHORT;

            static {
                Error error;
                NO_ERROR = new Error();
                UUID = new Error();
                VALUE_LENGTH_TOO_SHORT = new Error();
                DATA_LENGTH_NEGATIVE = new Error();
                CHECKSUM = error = new Error();
                $VALUES = new Error[]{NO_ERROR, UUID, VALUE_LENGTH_TOO_SHORT, DATA_LENGTH_NEGATIVE, error};
            }

            public static Error valueOf(String string2) {
                return Enum.valueOf(Error.class, string2);
            }

            public static Error[] values() {
                return (Error[])$VALUES.clone();
            }
        }

    }

    public static final class X_AXIS_UNIT
    extends Enum<X_AXIS_UNIT> {
        private static final /* synthetic */ X_AXIS_UNIT[] $VALUES;
        public static final /* enum */ X_AXIS_UNIT MINUTE;
        public static final /* enum */ X_AXIS_UNIT SECOND;

        static {
            X_AXIS_UNIT x_AXIS_UNIT;
            MINUTE = new X_AXIS_UNIT();
            SECOND = x_AXIS_UNIT = new X_AXIS_UNIT();
            $VALUES = new X_AXIS_UNIT[]{MINUTE, x_AXIS_UNIT};
        }

        public static X_AXIS_UNIT valueOf(String string2) {
            return Enum.valueOf(X_AXIS_UNIT.class, string2);
        }

        public static X_AXIS_UNIT[] values() {
            return (X_AXIS_UNIT[])$VALUES.clone();
        }
    }

}

