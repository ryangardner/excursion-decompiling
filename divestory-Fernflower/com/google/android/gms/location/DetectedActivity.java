package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Comparator;

public class DetectedActivity extends AbstractSafeParcelable {
   public static final Creator<DetectedActivity> CREATOR = new zzi();
   public static final int IN_VEHICLE = 0;
   public static final int ON_BICYCLE = 1;
   public static final int ON_FOOT = 2;
   public static final int RUNNING = 8;
   public static final int STILL = 3;
   public static final int TILTING = 5;
   public static final int UNKNOWN = 4;
   public static final int WALKING = 7;
   private static final Comparator<DetectedActivity> zzo = new zzh();
   private static final int[] zzp = new int[]{9, 10};
   private static final int[] zzq = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17, 18, 19};
   private static final int[] zzr = new int[]{0, 1, 2, 3, 7, 8, 16, 17};
   private int zzi;
   private int zzs;

   public DetectedActivity(int var1, int var2) {
      this.zzi = var1;
      this.zzs = var2;
   }

   public static void zzb(int var0) {
      int[] var1 = zzr;
      int var2 = var1.length;
      int var3 = 0;

      boolean var4;
      for(var4 = false; var3 < var2; ++var3) {
         if (var1[var3] == var0) {
            var4 = true;
         }
      }

      if (!var4) {
         StringBuilder var5 = new StringBuilder(81);
         var5.append(var0);
         var5.append(" is not a valid DetectedActivity supported by Activity Transition API.");
         Log.w("DetectedActivity", var5.toString());
      }

   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && this.getClass() == var1.getClass()) {
            DetectedActivity var2 = (DetectedActivity)var1;
            if (this.zzi == var2.zzi && this.zzs == var2.zzs) {
               return true;
            }
         }

         return false;
      }
   }

   public int getConfidence() {
      return this.zzs;
   }

   public int getType() {
      int var1 = this.zzi;
      return var1 <= 19 && var1 >= 0 ? var1 : 4;
   }

   public int hashCode() {
      return Objects.hashCode(this.zzi, this.zzs);
   }

   public String toString() {
      int var1 = this.getType();
      String var2;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  if (var1 != 4) {
                     if (var1 != 5) {
                        if (var1 != 7) {
                           if (var1 != 8) {
                              switch(var1) {
                              case 16:
                                 var2 = "IN_ROAD_VEHICLE";
                                 break;
                              case 17:
                                 var2 = "IN_RAIL_VEHICLE";
                                 break;
                              case 18:
                                 var2 = "IN_TWO_WHEELER_VEHICLE";
                                 break;
                              case 19:
                                 var2 = "IN_FOUR_WHEELER_VEHICLE";
                                 break;
                              default:
                                 var2 = Integer.toString(var1);
                              }
                           } else {
                              var2 = "RUNNING";
                           }
                        } else {
                           var2 = "WALKING";
                        }
                     } else {
                        var2 = "TILTING";
                     }
                  } else {
                     var2 = "UNKNOWN";
                  }
               } else {
                  var2 = "STILL";
               }
            } else {
               var2 = "ON_FOOT";
            }
         } else {
            var2 = "ON_BICYCLE";
         }
      } else {
         var2 = "IN_VEHICLE";
      }

      var1 = this.zzs;
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 48);
      var3.append("DetectedActivity [type=");
      var3.append(var2);
      var3.append(", confidence=");
      var3.append(var1);
      var3.append("]");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzi);
      SafeParcelWriter.writeInt(var1, 2, this.zzs);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
