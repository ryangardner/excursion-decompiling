package com.company;

import java.nio.ByteOrder;
import java.util.*;

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
    public static ByteOrder byteOrder;
    public static HashMap<String, String> devices_bond;
    public static HashMap<String, String> devices_connected;

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
    public static int new_last_dive_log_index;
    public static String prefSerialNumber;
    public static int selected_watch;
    public static int utc_offset;
    public static DataStruct.WatchSetting_GB watchSetting_gb;

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
        EFFECTIVE_DIVING_DEPTH_MBAR = (int) WatchOp.convertMeterToAbsMbar(0.5f);
        selected_watch = -1;
        dive_logs = new DataStruct.DiveLogs();
        dive_logs_list = new ArrayList();
        filterDiveLog = new DataStruct.FilterDiveLog();
        devices_bond = null;
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
        READ_WATCH = (byte) (true ? 1 : 0);
        WRITE_WATCH = (byte) (false ? 1 : 0);
        COMMAND_GROUP_INFO = WatchOp.b(160);
        COMMAND_GROUP_SETTING = WatchOp.b(176);
        COMMAND_GROUP_LOGS = WatchOp.b(192);
        COMMAND_GROUP_LOGS_ACK = WatchOp.b(193);
        COMMAND_GROUP_FACTORY_TEST = WatchOp.b(208);
        COMMAND_GROUP_SECURITY = WatchOp.b(224);
        COMMAND_RESPONSE_TIMEOUT = WatchOp.b(255);
        COMMAND_UPGRADE_FIRMWARE = new byte[]{WatchOp.b(18), WatchOp.b(52), WatchOp.b(86), WatchOp.b(120)};
    }

    public static byte b(int n) {
        return (byte) n;
    }


    public static float convertAbsMbarToMeter(float f) {
        return (f - mbarATM) / ConversionMbarPerMeterWater;
    }

    public static float convertMeterToAbsMbar(float f) {
        return f * ConversionMbarPerMeterWater + mbarATM;
    }
}