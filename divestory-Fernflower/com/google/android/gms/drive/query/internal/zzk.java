package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.query.Filter;
import java.util.List;

public final class zzk implements zzj<Boolean> {
   private Boolean zzmm = false;

   private zzk() {
   }

   public static boolean zza(Filter var0) {
      return var0 == null ? false : (Boolean)var0.zza(new zzk());
   }

   // $FF: synthetic method
   public final Object zza(com.google.android.gms.drive.metadata.zzb var1, Object var2) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zza(zzx var1, MetadataField var2, Object var3) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zza(zzx var1, List var2) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zza(Object var1) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zzbj() {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zzbk() {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zzc(MetadataField var1, Object var2) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zze(MetadataField var1) {
      return this.zzmm;
   }

   // $FF: synthetic method
   public final Object zzi(String var1) {
      if (!var1.isEmpty()) {
         this.zzmm = true;
      }

      return this.zzmm;
   }
}
