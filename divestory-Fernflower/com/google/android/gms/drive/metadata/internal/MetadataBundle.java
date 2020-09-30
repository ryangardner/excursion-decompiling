package com.google.android.gms.drive.metadata.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.drive.zzhs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MetadataBundle extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<MetadataBundle> CREATOR = new zzj();
   private static final GmsLogger zzbz = new GmsLogger("MetadataBundle", "");
   private final Bundle zzjh;

   MetadataBundle(Bundle var1) {
      var1 = (Bundle)Preconditions.checkNotNull(var1);
      this.zzjh = var1;
      var1.setClassLoader(this.getClass().getClassLoader());
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.zzjh.keySet().iterator();

      while(true) {
         boolean var4 = var3.hasNext();
         int var5 = 0;
         if (!var4) {
            ArrayList var8 = (ArrayList)var2;
            int var6 = var8.size();

            while(var5 < var6) {
               Object var9 = var8.get(var5);
               ++var5;
               String var10 = (String)var9;
               this.zzjh.remove(var10);
            }

            return;
         }

         String var7 = (String)var3.next();
         if (zzf.zzf(var7) == null) {
            var2.add(var7);
            zzbz.wfmt("MetadataBundle", "Ignored unknown metadata field in bundle: %s", var7);
         }
      }
   }

   public static <T> MetadataBundle zza(MetadataField<T> var0, T var1) {
      MetadataBundle var2 = zzbe();
      var2.zzb(var0, var1);
      return var2;
   }

   public static MetadataBundle zzbe() {
      return new MetadataBundle(new Bundle());
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && var1.getClass() == this.getClass()) {
         MetadataBundle var4 = (MetadataBundle)var1;
         Set var2 = this.zzjh.keySet();
         if (!var2.equals(var4.zzjh.keySet())) {
            return false;
         } else {
            Iterator var5 = var2.iterator();

            String var3;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               var3 = (String)var5.next();
            } while(Objects.equal(this.zzjh.get(var3), var4.zzjh.get(var3)));

            return false;
         }
      } else {
         return false;
      }
   }

   public final int hashCode() {
      Iterator var1 = this.zzjh.keySet().iterator();

      int var2;
      String var3;
      for(var2 = 1; var1.hasNext(); var2 = var2 * 31 + this.zzjh.get(var3).hashCode()) {
         var3 = (String)var1.next();
      }

      return var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBundle(var1, 2, this.zzjh, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final <T> T zza(MetadataField<T> var1) {
      return var1.zza(this.zzjh);
   }

   public final void zza(Context var1) {
      BitmapTeleporter var2 = (BitmapTeleporter)this.zza(zzhs.zzkq);
      if (var2 != null) {
         var2.setTempDir(var1.getCacheDir());
      }

   }

   public final <T> void zzb(MetadataField<T> var1, T var2) {
      if (zzf.zzf(var1.getName()) == null) {
         String var3 = String.valueOf(var1.getName());
         if (var3.length() != 0) {
            var3 = "Unregistered field: ".concat(var3);
         } else {
            var3 = new String("Unregistered field: ");
         }

         throw new IllegalArgumentException(var3);
      } else {
         var1.zza(var2, this.zzjh);
      }
   }

   public final MetadataBundle zzbf() {
      return new MetadataBundle(new Bundle(this.zzjh));
   }

   public final Set<MetadataField<?>> zzbg() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.zzjh.keySet().iterator();

      while(var2.hasNext()) {
         var1.add(zzf.zzf((String)var2.next()));
      }

      return var1;
   }

   public final <T> T zzc(MetadataField<T> var1) {
      Object var2 = this.zza(var1);
      this.zzjh.remove(var1.getName());
      return var2;
   }

   public final boolean zzd(MetadataField<?> var1) {
      return this.zzjh.containsKey(var1.getName());
   }
}
