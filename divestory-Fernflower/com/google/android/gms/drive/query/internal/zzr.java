package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzr extends zza {
   public static final Creator<zzr> CREATOR = new zzs();
   private List<Filter> zzls;
   private final zzx zzlz;
   private final List<FilterHolder> zzmo;

   public zzr(zzx var1, Filter var2, Filter... var3) {
      this.zzlz = var1;
      ArrayList var6 = new ArrayList(var3.length + 1);
      this.zzmo = var6;
      var6.add(new FilterHolder(var2));
      var6 = new ArrayList(var3.length + 1);
      this.zzls = var6;
      var6.add(var2);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Filter var7 = var3[var5];
         this.zzmo.add(new FilterHolder(var7));
         this.zzls.add(var7);
      }

   }

   public zzr(zzx var1, Iterable<Filter> var2) {
      this.zzlz = var1;
      this.zzls = new ArrayList();
      this.zzmo = new ArrayList();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Filter var4 = (Filter)var3.next();
         this.zzls.add(var4);
         this.zzmo.add(new FilterHolder(var4));
      }

   }

   zzr(zzx var1, List<FilterHolder> var2) {
      this.zzlz = var1;
      this.zzmo = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzlz, var2, false);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzmo, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final <T> T zza(zzj<T> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.zzmo.iterator();

      while(var3.hasNext()) {
         var2.add(((FilterHolder)var3.next()).getFilter().zza(var1));
      }

      return var1.zza((zzx)this.zzlz, (List)var2);
   }
}
