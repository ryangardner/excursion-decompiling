/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zzg;
import com.google.android.gms.common.zzi;
import com.google.android.gms.common.zzl;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
public class GoogleSignatureVerifier {
    @Nullable
    private static GoogleSignatureVerifier zza;
    private final Context zzb;
    private volatile String zzc;

    private GoogleSignatureVerifier(Context context) {
        this.zzb = context.getApplicationContext();
    }

    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            GoogleSignatureVerifier googleSignatureVerifier;
            if (zza != null) return zza;
            zzc.zza(context);
            zza = googleSignatureVerifier = new GoogleSignatureVerifier(context);
            return zza;
        }
    }

    @Nullable
    private static zzd zza(PackageInfo object, zzd ... arrzzd) {
        if (object.signatures == null) {
            return null;
        }
        if (object.signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return null;
        }
        object = object.signatures;
        int n = 0;
        object = new zzg(object[0].toByteArray());
        while (n < arrzzd.length) {
            if (arrzzd[n].equals(object)) {
                return arrzzd[n];
            }
            ++n;
        }
        return null;
    }

    private final zzl zza(String string2, boolean bl, boolean bl2) {
        zzl zzl2;
        PackageInfo packageInfo;
        if (string2 == null) {
            return zzl.zza("null pkg");
        }
        if (string2.equals(this.zzc)) {
            return zzl.zza();
        }
        try {
            packageInfo = this.zzb.getPackageManager().getPackageInfo(string2, 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            string2 = String.valueOf(string2);
            if (string2.length() != 0) {
                string2 = "no pkg ".concat(string2);
                return zzl.zza(string2, nameNotFoundException);
            }
            string2 = new String("no pkg ");
            return zzl.zza(string2, nameNotFoundException);
        }
        bl = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
        if (packageInfo == null) {
            zzl2 = zzl.zza("null pkg");
        } else if (packageInfo.signatures != null && packageInfo.signatures.length == 1) {
            zzg zzg2 = new zzg(packageInfo.signatures[0].toByteArray());
            String string3 = packageInfo.packageName;
            zzl2 = zzc.zza(string3, zzg2, bl, false);
            if (zzl2.zza && packageInfo.applicationInfo != null && (packageInfo.applicationInfo.flags & 2) != 0 && zzc.zza((String)string3, (zzd)zzg2, (boolean)false, (boolean)true).zza) {
                zzl2 = zzl.zza("debuggable release cert app rejected");
            }
        } else {
            zzl2 = zzl.zza("single cert required");
        }
        if (!zzl2.zza) return zzl2;
        this.zzc = string2;
        return zzl2;
    }

    public static boolean zza(PackageInfo object, boolean bl) {
        if (object == null) return false;
        if (object.signatures == null) return false;
        object = bl ? GoogleSignatureVerifier.zza(object, zzi.zza) : GoogleSignatureVerifier.zza(object, new zzd[]{zzi.zza[0]});
        if (object == null) return false;
        return true;
    }

    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (GoogleSignatureVerifier.zza(packageInfo, false)) {
            return true;
        }
        if (!GoogleSignatureVerifier.zza(packageInfo, true)) return false;
        if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
            return true;
        }
        Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        return false;
    }

    public boolean isPackageGoogleSigned(String object) {
        object = this.zza((String)object, false, false);
        ((zzl)object).zzc();
        return ((zzl)object).zza;
    }

    public boolean isUidGoogleSigned(int n) {
        zzl zzl2;
        block4 : {
            String[] arrstring = this.zzb.getPackageManager().getPackagesForUid(n);
            if (arrstring == null || arrstring.length == 0) {
                zzl2 = zzl.zza("no pkgs");
            } else {
                zzl2 = null;
                int n2 = arrstring.length;
                for (n = 0; n < n2; ++n) {
                    zzl2 = this.zza(arrstring[n], false, false);
                    if (!zzl2.zza) {
                        continue;
                    }
                    break block4;
                }
                zzl2 = Preconditions.checkNotNull(zzl2);
            }
        }
        zzl2.zzc();
        return zzl2.zza;
    }
}

