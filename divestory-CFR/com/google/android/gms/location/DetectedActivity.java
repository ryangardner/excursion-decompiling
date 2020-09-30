/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzh;
import com.google.android.gms.location.zzi;
import java.util.Comparator;

public class DetectedActivity
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<DetectedActivity> CREATOR;
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    private static final Comparator<DetectedActivity> zzo;
    private static final int[] zzp;
    private static final int[] zzq;
    private static final int[] zzr;
    private int zzi;
    private int zzs;

    static {
        zzo = new zzh();
        zzp = new int[]{9, 10};
        zzq = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17, 18, 19};
        zzr = new int[]{0, 1, 2, 3, 7, 8, 16, 17};
        CREATOR = new zzi();
    }

    public DetectedActivity(int n, int n2) {
        this.zzi = n;
        this.zzs = n2;
    }

    public static void zzb(int n) {
        Object object = zzr;
        int n2 = ((int[])object).length;
        int n3 = 0;
        boolean bl = false;
        do {
            if (n3 >= n2) {
                if (bl) return;
                object = new StringBuilder(81);
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" is not a valid DetectedActivity supported by Activity Transition API.");
                Log.w((String)"DetectedActivity", (String)((StringBuilder)object).toString());
                return;
            }
            if (object[n3] == n) {
                bl = true;
            }
            ++n3;
        } while (true);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (DetectedActivity)object;
        if (this.zzi != ((DetectedActivity)object).zzi) return false;
        if (this.zzs != ((DetectedActivity)object).zzs) return false;
        return true;
    }

    public int getConfidence() {
        return this.zzs;
    }

    public int getType() {
        int n = this.zzi;
        if (n > 19) return 4;
        if (n >= 0) return n;
        return 4;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzi, this.zzs);
    }

    /*
     * Unable to fully structure code
     */
    public String toString() {
        var1_1 = this.getType();
        if (var1_1 != 0) {
            if (var1_1 != 1) {
                if (var1_1 != 2) {
                    if (var1_1 != 3) {
                        if (var1_1 != 4) {
                            if (var1_1 != 5) {
                                if (var1_1 != 7) {
                                    if (var1_1 != 8) {
                                        switch (var1_1) {
                                            default: {
                                                var2_2 = Integer.toString(var1_1);
                                                ** break;
                                            }
                                            case 19: {
                                                var2_2 = "IN_FOUR_WHEELER_VEHICLE";
                                                ** break;
                                            }
                                            case 18: {
                                                var2_2 = "IN_TWO_WHEELER_VEHICLE";
                                                ** break;
                                            }
                                            case 17: {
                                                var2_2 = "IN_RAIL_VEHICLE";
                                                ** break;
                                            }
                                            case 16: 
                                        }
                                        var2_2 = "IN_ROAD_VEHICLE";
                                        ** break;
lbl26: // 5 sources:
                                    } else {
                                        var2_2 = "RUNNING";
                                    }
                                } else {
                                    var2_2 = "WALKING";
                                }
                            } else {
                                var2_2 = "TILTING";
                            }
                        } else {
                            var2_2 = "UNKNOWN";
                        }
                    } else {
                        var2_2 = "STILL";
                    }
                } else {
                    var2_2 = "ON_FOOT";
                }
            } else {
                var2_2 = "ON_BICYCLE";
            }
        } else {
            var2_2 = "IN_VEHICLE";
        }
        var1_1 = this.zzs;
        var3_3 = new StringBuilder(String.valueOf(var2_2).length() + 48);
        var3_3.append("DetectedActivity [type=");
        var3_3.append(var2_2);
        var3_3.append(", confidence=");
        var3_3.append(var1_1);
        var3_3.append("]");
        return var3_3.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzi);
        SafeParcelWriter.writeInt(parcel, 2, this.zzs);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

