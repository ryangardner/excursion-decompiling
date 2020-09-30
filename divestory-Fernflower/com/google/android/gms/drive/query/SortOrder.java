package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.query.internal.zzf;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SortOrder extends AbstractSafeParcelable {
   public static final Creator<SortOrder> CREATOR = new zzc();
   private final List<zzf> zzlw;
   private final boolean zzlx;

   SortOrder(List<zzf> var1, boolean var2) {
      this.zzlw = var1;
      this.zzlx = var2;
   }

   public String toString() {
      return String.format(Locale.US, "SortOrder[%s, %s]", TextUtils.join(",", this.zzlw), this.zzlx);
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.zzlw, false);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzlx);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public static class Builder {
      private final List<zzf> zzlw = new ArrayList();
      private boolean zzlx = false;

      public SortOrder.Builder addSortAscending(SortableMetadataField var1) {
         this.zzlw.add(new zzf(var1.getName(), true));
         return this;
      }

      public SortOrder.Builder addSortDescending(SortableMetadataField var1) {
         this.zzlw.add(new zzf(var1.getName(), false));
         return this;
      }

      public SortOrder build() {
         return new SortOrder(this.zzlw, false);
      }
   }
}
