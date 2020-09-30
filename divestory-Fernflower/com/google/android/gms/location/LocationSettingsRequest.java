package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationSettingsRequest extends AbstractSafeParcelable {
   public static final Creator<LocationSettingsRequest> CREATOR = new zzag();
   private final List<LocationRequest> zzbg;
   private final boolean zzbh;
   private final boolean zzbi;
   private zzae zzbj;

   LocationSettingsRequest(List<LocationRequest> var1, boolean var2, boolean var3, zzae var4) {
      this.zzbg = var1;
      this.zzbh = var2;
      this.zzbi = var3;
      this.zzbj = var4;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, Collections.unmodifiableList(this.zzbg), false);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzbh);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzbi);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzbj, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public static final class Builder {
      private boolean zzbh = false;
      private boolean zzbi = false;
      private zzae zzbj = null;
      private final ArrayList<LocationRequest> zzbk = new ArrayList();

      public final LocationSettingsRequest.Builder addAllLocationRequests(Collection<LocationRequest> var1) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            LocationRequest var2 = (LocationRequest)var3.next();
            if (var2 != null) {
               this.zzbk.add(var2);
            }
         }

         return this;
      }

      public final LocationSettingsRequest.Builder addLocationRequest(LocationRequest var1) {
         if (var1 != null) {
            this.zzbk.add(var1);
         }

         return this;
      }

      public final LocationSettingsRequest build() {
         return new LocationSettingsRequest(this.zzbk, this.zzbh, this.zzbi, (zzae)null);
      }

      public final LocationSettingsRequest.Builder setAlwaysShow(boolean var1) {
         this.zzbh = var1;
         return this;
      }

      public final LocationSettingsRequest.Builder setNeedBle(boolean var1) {
         this.zzbi = var1;
         return this;
      }
   }
}
