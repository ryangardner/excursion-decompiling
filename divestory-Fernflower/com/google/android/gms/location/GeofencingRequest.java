package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzbh;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingRequest extends AbstractSafeParcelable {
   public static final Creator<GeofencingRequest> CREATOR = new zzq();
   public static final int INITIAL_TRIGGER_DWELL = 4;
   public static final int INITIAL_TRIGGER_ENTER = 1;
   public static final int INITIAL_TRIGGER_EXIT = 2;
   private final String tag;
   private final List<zzbh> zzap;
   private final int zzaq;

   GeofencingRequest(List<zzbh> var1, int var2, String var3) {
      this.zzap = var1;
      this.zzaq = var2;
      this.tag = var3;
   }

   public List<Geofence> getGeofences() {
      ArrayList var1 = new ArrayList();
      var1.addAll(this.zzap);
      return var1;
   }

   public int getInitialTrigger() {
      return this.zzaq;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("GeofencingRequest[");
      var1.append("geofences=");
      var1.append(this.zzap);
      int var2 = this.zzaq;
      StringBuilder var3 = new StringBuilder(30);
      var3.append(", initialTrigger=");
      var3.append(var2);
      var3.append(", ");
      var1.append(var3.toString());
      String var4 = String.valueOf(this.tag);
      if (var4.length() != 0) {
         var4 = "tag=".concat(var4);
      } else {
         var4 = new String("tag=");
      }

      var1.append(var4);
      var1.append("]");
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.zzap, false);
      SafeParcelWriter.writeInt(var1, 2, this.getInitialTrigger());
      SafeParcelWriter.writeString(var1, 3, this.tag, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public static final class Builder {
      private String tag = "";
      private final List<zzbh> zzap = new ArrayList();
      private int zzaq = 5;

      public final GeofencingRequest.Builder addGeofence(Geofence var1) {
         Preconditions.checkNotNull(var1, "geofence can't be null.");
         Preconditions.checkArgument(var1 instanceof zzbh, "Geofence must be created using Geofence.Builder.");
         this.zzap.add((zzbh)var1);
         return this;
      }

      public final GeofencingRequest.Builder addGeofences(List<Geofence> var1) {
         if (var1 != null && !var1.isEmpty()) {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               Geofence var3 = (Geofence)var2.next();
               if (var3 != null) {
                  this.addGeofence(var3);
               }
            }
         }

         return this;
      }

      public final GeofencingRequest build() {
         Preconditions.checkArgument(this.zzap.isEmpty() ^ true, "No geofence has been added to this request.");
         return new GeofencingRequest(this.zzap, this.zzaq, this.tag);
      }

      public final GeofencingRequest.Builder setInitialTrigger(int var1) {
         this.zzaq = var1 & 7;
         return this;
      }
   }
}
