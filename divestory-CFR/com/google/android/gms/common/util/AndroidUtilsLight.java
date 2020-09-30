/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.common.zzl;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AndroidUtilsLight {
    private static volatile int zza = -1;

    @Deprecated
    public static Context getDeviceProtectedStorageContext(Context context) {
        Context context2 = context;
        if (!zzl.zza()) return context2;
        return zzl.zza(context);
    }

    @Deprecated
    public static byte[] getPackageCertificateHashBytes(Context context, String object) throws PackageManager.NameNotFoundException {
        context = Wrappers.packageManager(context).getPackageInfo((String)object, 64);
        if (context.signatures == null) return null;
        if (context.signatures.length != 1) return null;
        object = AndroidUtilsLight.zza("SHA1");
        if (object == null) return null;
        return ((MessageDigest)object).digest(context.signatures[0].toByteArray());
    }

    public static MessageDigest zza(String string2) {
        int n = 0;
        while (n < 2) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(string2);
                if (messageDigest != null) {
                    return messageDigest;
                }
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
            ++n;
        }
        return null;
    }
}

