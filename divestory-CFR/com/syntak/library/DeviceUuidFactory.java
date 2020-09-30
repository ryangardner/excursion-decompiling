/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 */
package com.syntak.library;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class DeviceUuidFactory {
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static final String PREFS_FILE = "device_id.xml";
    protected static UUID uuid;

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public DeviceUuidFactory(Context object) {
        Object object2;
        block7 : {
            if (uuid != null) return;
            // MONITORENTER : com.syntak.library.DeviceUuidFactory.class
            if (uuid != null) {
                // MONITOREXIT : com.syntak.library.DeviceUuidFactory.class
                return;
            }
            object2 = object.getSharedPreferences(PREFS_FILE, 0);
            String string2 = object2.getString(PREFS_DEVICE_ID, null);
            if (string2 != null) {
                uuid = UUID.fromString(string2);
                return;
            }
            string2 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id");
            try {
                if (!"9774d56d682e549c".equals(string2)) {
                    uuid = UUID.nameUUIDFromBytes(string2.getBytes("utf8"));
                    break block7;
                }
                object = (object = ((TelephonyManager)object.getSystemService("phone")).getDeviceId()) != null ? UUID.nameUUIDFromBytes(((String)object).getBytes("utf8")) : UUID.randomUUID();
                uuid = object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                super(unsupportedEncodingException);
                throw object2;
            }
        }
        object2.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
    }

    public UUID getDeviceUuid() {
        return uuid;
    }
}

