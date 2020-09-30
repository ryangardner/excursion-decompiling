/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.zzb;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ActivityRecognitionResult
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<ActivityRecognitionResult> CREATOR = new zzb();
    private Bundle extras;
    private List<DetectedActivity> zze;
    private long zzf;
    private long zzg;
    private int zzh;

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long l, long l2) {
        this(detectedActivity, l, l2, 0, null);
    }

    private ActivityRecognitionResult(DetectedActivity detectedActivity, long l, long l2, int n, Bundle bundle) {
        this(Collections.singletonList(detectedActivity), l, l2, 0, null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long l, long l2) {
        this(list, l, l2, 0, null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long l, long l2, int n, Bundle bundle) {
        boolean bl = true;
        boolean bl2 = list != null && list.size() > 0;
        Preconditions.checkArgument(bl2, "Must have at least 1 detected activity");
        bl2 = l > 0L && l2 > 0L ? bl : false;
        Preconditions.checkArgument(bl2, "Must set times");
        this.zze = list;
        this.zzf = l;
        this.zzg = l2;
        this.zzh = n;
        this.extras = bundle;
    }

    /*
     * Unable to fully structure code
     */
    public static ActivityRecognitionResult extractResult(Intent var0) {
        block3 : {
            if (!ActivityRecognitionResult.hasResult((Intent)var0)) ** GOTO lbl-1000
            var1_1 = var0.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
            if (!(var1_1 instanceof byte[])) break block3;
            var1_1 = SafeParcelableSerializer.deserializeFromBytes((byte[])var1_1, ActivityRecognitionResult.CREATOR);
            ** GOTO lbl-1000
        }
        if (var1_1 instanceof ActivityRecognitionResult) lbl-1000: // 2 sources:
        {
            var1_1 = (ActivityRecognitionResult)var1_1;
        } else lbl-1000: // 2 sources:
        {
            var1_1 = null;
        }
        if (var1_1 != null) {
            return var1_1;
        }
        if ((var0 = ActivityRecognitionResult.zza((Intent)var0)) == null) return null;
        if (var0.isEmpty() == false) return (ActivityRecognitionResult)var0.get(var0.size() - 1);
        return null;
    }

    public static boolean hasResult(Intent object) {
        if (object == null) {
            return false;
        }
        boolean bl = object == null ? false : object.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
        if (bl) {
            return true;
        }
        if ((object = ActivityRecognitionResult.zza((Intent)object)) == null) return false;
        if (object.isEmpty()) return false;
        return true;
    }

    private static List<ActivityRecognitionResult> zza(Intent intent) {
        boolean bl = intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
        if (bl) return SafeParcelableSerializer.deserializeIterableFromIntentExtra(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST", CREATOR);
        return null;
    }

    private static boolean zza(Bundle bundle, Bundle bundle2) {
        String string2;
        if (bundle == null && bundle2 == null) {
            return true;
        }
        if (bundle == null) {
            if (bundle2 != null) return false;
        }
        if (bundle != null && bundle2 == null) {
            return false;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        Iterator iterator2 = bundle.keySet().iterator();
        do {
            if (!iterator2.hasNext()) return true;
            string2 = (String)iterator2.next();
            if (bundle2.containsKey(string2)) continue;
            return false;
        } while (!(bundle.get(string2) == null ? bundle2.get(string2) != null : (bundle.get(string2) instanceof Bundle ? !ActivityRecognitionResult.zza(bundle.getBundle(string2), bundle2.getBundle(string2)) : !bundle.get(string2).equals(bundle2.get(string2)))));
        return false;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (ActivityRecognitionResult)object;
        if (this.zzf != ((ActivityRecognitionResult)object).zzf) return false;
        if (this.zzg != ((ActivityRecognitionResult)object).zzg) return false;
        if (this.zzh != ((ActivityRecognitionResult)object).zzh) return false;
        if (!Objects.equal(this.zze, ((ActivityRecognitionResult)object).zze)) return false;
        if (!ActivityRecognitionResult.zza(this.extras, ((ActivityRecognitionResult)object).extras)) return false;
        return true;
    }

    public int getActivityConfidence(int n) {
        DetectedActivity detectedActivity;
        Iterator<DetectedActivity> iterator2 = this.zze.iterator();
        do {
            if (!iterator2.hasNext()) return 0;
        } while ((detectedActivity = iterator2.next()).getType() != n);
        return detectedActivity.getConfidence();
    }

    public long getElapsedRealtimeMillis() {
        return this.zzg;
    }

    public DetectedActivity getMostProbableActivity() {
        return this.zze.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.zze;
    }

    public long getTime() {
        return this.zzf;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.zzf, this.zzg, this.zzh, this.zze, this.extras});
    }

    public String toString() {
        String string2 = String.valueOf(this.zze);
        long l = this.zzf;
        long l2 = this.zzg;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 124);
        stringBuilder.append("ActivityRecognitionResult [probableActivities=");
        stringBuilder.append(string2);
        stringBuilder.append(", timeMillis=");
        stringBuilder.append(l);
        stringBuilder.append(", elapsedRealtimeMillis=");
        stringBuilder.append(l2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zze, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzf);
        SafeParcelWriter.writeLong(parcel, 3, this.zzg);
        SafeParcelWriter.writeInt(parcel, 4, this.zzh);
        SafeParcelWriter.writeBundle(parcel, 5, this.extras, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

