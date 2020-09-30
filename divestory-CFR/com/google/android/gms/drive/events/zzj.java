/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

public final class zzj {
    public static boolean zza(int n, DriveId driveId) {
        if (n != 1) {
            if (n != 4 && n != 7) {
                if (n != 8) {
                    return false;
                }
            } else {
                if (driveId != null) return false;
                return true;
            }
        }
        if (driveId == null) return false;
        return true;
    }
}

