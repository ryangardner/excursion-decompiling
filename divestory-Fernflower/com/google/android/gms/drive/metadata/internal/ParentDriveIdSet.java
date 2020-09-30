package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.List;

public class ParentDriveIdSet extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<ParentDriveIdSet> CREATOR = new zzn();
   final List<zzq> zzjj;

   public ParentDriveIdSet() {
      this(new ArrayList());
   }

   ParentDriveIdSet(List<zzq> var1) {
      this.zzjj = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzjj, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
