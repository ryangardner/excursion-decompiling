/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.os.ConfigurationCompat;
import com.google.android.gms.base.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.R;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.Locale;

public final class zac {
    private static final SimpleArrayMap<String, String> zaa = new SimpleArrayMap();
    private static Locale zab;

    public static String zaa(Context context) {
        return context.getResources().getString(R.string.common_google_play_services_notification_channel_name);
    }

    public static String zaa(Context object, int n) {
        Resources resources = object.getResources();
        switch (n) {
            default: {
                object = new StringBuilder(33);
                ((StringBuilder)object).append("Unexpected error code ");
                ((StringBuilder)object).append(n);
                Log.e((String)"GoogleApiAvailability", (String)((StringBuilder)object).toString());
                return null;
            }
            case 20: {
                Log.e((String)"GoogleApiAvailability", (String)"The current user profile is restricted and could not use authenticated features.");
                return zac.zaa((Context)object, "common_google_play_services_restricted_profile_title");
            }
            case 17: {
                Log.e((String)"GoogleApiAvailability", (String)"The specified account could not be signed in.");
                return zac.zaa((Context)object, "common_google_play_services_sign_in_failed_title");
            }
            case 16: {
                Log.e((String)"GoogleApiAvailability", (String)"One of the API components you attempted to connect to is not available.");
                return null;
            }
            case 11: {
                Log.e((String)"GoogleApiAvailability", (String)"The application is not licensed to the user.");
                return null;
            }
            case 10: {
                Log.e((String)"GoogleApiAvailability", (String)"Developer error occurred. Please see logs for detailed information");
                return null;
            }
            case 9: {
                Log.e((String)"GoogleApiAvailability", (String)"Google Play services is invalid. Cannot recover.");
                return null;
            }
            case 8: {
                Log.e((String)"GoogleApiAvailability", (String)"Internal error occurred. Please see logs for detailed information");
                return null;
            }
            case 7: {
                Log.e((String)"GoogleApiAvailability", (String)"Network error occurred. Please retry request later.");
                return zac.zaa((Context)object, "common_google_play_services_network_error_title");
            }
            case 5: {
                Log.e((String)"GoogleApiAvailability", (String)"An invalid account was specified when connecting. Please provide a valid account.");
                return zac.zaa((Context)object, "common_google_play_services_invalid_account_title");
            }
            case 4: 
            case 6: 
            case 18: {
                return null;
            }
            case 3: {
                return resources.getString(R.string.common_google_play_services_enable_title);
            }
            case 2: {
                return resources.getString(R.string.common_google_play_services_update_title);
            }
            case 1: 
        }
        return resources.getString(R.string.common_google_play_services_install_title);
    }

    private static String zaa(Context object, String string2) {
        SimpleArrayMap<String, String> simpleArrayMap = zaa;
        synchronized (simpleArrayMap) {
            Object object2 = ConfigurationCompat.getLocales(object.getResources().getConfiguration()).get(0);
            if (!((Locale)object2).equals(zab)) {
                zaa.clear();
                zab = object2;
            }
            if ((object2 = zaa.get(string2)) != null) {
                return object2;
            }
            if ((object = GooglePlayServicesUtil.getRemoteResource((Context)object)) == null) {
                return null;
            }
            int n = object.getIdentifier(string2, "string", "com.google.android.gms");
            if (n == 0) {
                object = String.valueOf(string2);
                object = ((String)object).length() != 0 ? "Missing resource: ".concat((String)object) : new String("Missing resource: ");
                Log.w((String)"GoogleApiAvailability", (String)object);
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence)(object = object.getString(n)))) {
                zaa.put(string2, (String)object);
                return object;
            }
            object = String.valueOf(string2);
            object = ((String)object).length() != 0 ? "Got empty resource: ".concat((String)object) : new String("Got empty resource: ");
            Log.w((String)"GoogleApiAvailability", (String)object);
            return null;
        }
    }

    private static String zaa(Context object, String string2, String string3) {
        Resources resources = object.getResources();
        string2 = zac.zaa(object, string2);
        object = string2;
        if (string2 != null) return String.format(resources.getConfiguration().locale, (String)object, string3);
        object = resources.getString(R.string.common_google_play_services_unknown_issue);
        return String.format(resources.getConfiguration().locale, (String)object, string3);
    }

    private static String zab(Context object) {
        String string2 = object.getPackageName();
        try {
            return Wrappers.packageManager(object).getApplicationLabel(string2).toString();
        }
        catch (PackageManager.NameNotFoundException | NullPointerException throwable) {
            object = object.getApplicationInfo().name;
            if (!TextUtils.isEmpty((CharSequence)object)) return object;
            return string2;
        }
    }

    public static String zab(Context context, int n) {
        String string2 = n == 6 ? zac.zaa(context, "common_google_play_services_resolution_required_title") : zac.zaa(context, n);
        String string3 = string2;
        if (string2 != null) return string3;
        return context.getResources().getString(R.string.common_google_play_services_notification_ticker);
    }

    public static String zac(Context context, int n) {
        Resources resources = context.getResources();
        String string2 = zac.zab(context);
        if (n == 1) {
            return resources.getString(R.string.common_google_play_services_install_text, new Object[]{string2});
        }
        if (n != 2) {
            if (n == 3) {
                return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{string2});
            }
            if (n == 5) return zac.zaa(context, "common_google_play_services_invalid_account_text", string2);
            if (n == 7) return zac.zaa(context, "common_google_play_services_network_error_text", string2);
            if (n == 9) {
                return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{string2});
            }
            if (n == 20) return zac.zaa(context, "common_google_play_services_restricted_profile_text", string2);
            switch (n) {
                default: {
                    return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{string2});
                }
                case 18: {
                    return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{string2});
                }
                case 17: {
                    return zac.zaa(context, "common_google_play_services_sign_in_failed_text", string2);
                }
                case 16: 
            }
            return zac.zaa(context, "common_google_play_services_api_unavailable_text", string2);
        }
        if (!DeviceProperties.isWearableWithoutPlayStore(context)) return resources.getString(R.string.common_google_play_services_update_text, new Object[]{string2});
        return resources.getString(R.string.common_google_play_services_wear_update_text);
    }

    public static String zad(Context context, int n) {
        if (n == 6) return zac.zaa(context, "common_google_play_services_resolution_required_text", zac.zab(context));
        if (n != 19) return zac.zac(context, n);
        return zac.zaa(context, "common_google_play_services_resolution_required_text", zac.zab(context));
    }

    public static String zae(Context context, int n) {
        context = context.getResources();
        if (n == 1) return context.getString(R.string.common_google_play_services_install_button);
        if (n == 2) return context.getString(R.string.common_google_play_services_update_button);
        if (n == 3) return context.getString(R.string.common_google_play_services_enable_button);
        return context.getString(17039370);
    }
}

