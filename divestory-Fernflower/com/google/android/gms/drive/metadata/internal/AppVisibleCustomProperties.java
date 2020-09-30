package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppVisibleCustomProperties extends AbstractSafeParcelable implements ReflectedParcelable, Iterable<zzc> {
   public static final Creator<AppVisibleCustomProperties> CREATOR = new com.google.android.gms.drive.metadata.internal.zza();
   public static final AppVisibleCustomProperties zzjb = (new AppVisibleCustomProperties.zza()).zzbb();
   private final List<zzc> zzjc;

   AppVisibleCustomProperties(Collection<zzc> var1) {
      Preconditions.checkNotNull(var1);
      this.zzjc = new ArrayList(var1);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return var1 != null && var1.getClass() == this.getClass() ? this.zzba().equals(((AppVisibleCustomProperties)var1).zzba()) : false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzjc);
   }

   public final Iterator<zzc> iterator() {
      return this.zzjc.iterator();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzjc, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final Map<CustomPropertyKey, String> zzba() {
      HashMap var1 = new HashMap(this.zzjc.size());
      Iterator var2 = this.zzjc.iterator();

      while(var2.hasNext()) {
         zzc var3 = (zzc)var2.next();
         var1.put(var3.zzje, var3.value);
      }

      return Collections.unmodifiableMap(var1);
   }

   public static final class zza {
      private final Map<CustomPropertyKey, zzc> zzjd = new HashMap();

      public final AppVisibleCustomProperties.zza zza(CustomPropertyKey var1, String var2) {
         Preconditions.checkNotNull(var1, "key");
         this.zzjd.put(var1, new zzc(var1, var2));
         return this;
      }

      public final AppVisibleCustomProperties.zza zza(zzc var1) {
         Preconditions.checkNotNull(var1, "property");
         this.zzjd.put(var1.zzje, var1);
         return this;
      }

      public final AppVisibleCustomProperties zzbb() {
         return new AppVisibleCustomProperties(this.zzjd.values());
      }
   }
}
