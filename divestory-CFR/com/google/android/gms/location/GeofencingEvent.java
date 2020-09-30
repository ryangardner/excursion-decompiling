/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.location.Location
 *  android.os.Parcelable
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcelable;
import com.google.android.gms.internal.location.zzbh;
import com.google.android.gms.location.Geofence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeofencingEvent {
    private final int errorCode;
    private final int zzam;
    private final List<Geofence> zzan;
    private final Location zzao;

    private GeofencingEvent(int n, int n2, List<Geofence> list, Location location) {
        this.errorCode = n;
        this.zzam = n2;
        this.zzan = list;
        this.zzao = location;
    }

    public static GeofencingEvent fromIntent(Intent intent) {
        int n;
        int n2;
        int n3;
        int n4;
        ArrayList arrayList;
        ArrayList<zzbh> arrayList2;
        block6 : {
            block7 : {
                arrayList2 = null;
                if (intent == null) {
                    return null;
                }
                n2 = -1;
                n4 = intent.getIntExtra("gms_error_code", -1);
                n = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
                n3 = n2;
                if (n == -1) break block6;
                if (n == 1 || n == 2) break block7;
                n3 = n2;
                if (n != 4) break block6;
            }
            n3 = n;
        }
        if ((arrayList = (ArrayList)intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list")) == null) {
            return new GeofencingEvent(n4, n3, arrayList2, (Location)intent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
        }
        ArrayList<zzbh> arrayList3 = new ArrayList<zzbh>(arrayList.size());
        n2 = arrayList.size();
        n = 0;
        do {
            arrayList2 = arrayList3;
            if (n >= n2) return new GeofencingEvent(n4, n3, arrayList2, (Location)intent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
            arrayList2 = arrayList.get(n);
            ++n;
            arrayList3.add(zzbh.zza((byte[])arrayList2));
        } while (true);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getGeofenceTransition() {
        return this.zzam;
    }

    public List<Geofence> getTriggeringGeofences() {
        return this.zzan;
    }

    public Location getTriggeringLocation() {
        return this.zzao;
    }

    public boolean hasError() {
        if (this.errorCode == -1) return false;
        return true;
    }
}

