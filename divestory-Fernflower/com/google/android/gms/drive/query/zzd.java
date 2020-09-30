package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.Iterator;
import java.util.List;

public final class zzd implements zzj<String> {
   // $FF: synthetic method
   public final Object zza(com.google.android.gms.drive.metadata.zzb var1, Object var2) {
      return String.format("contains(%s,%s)", var1.getName(), var2);
   }

   // $FF: synthetic method
   public final Object zza(zzx var1, MetadataField var2, Object var3) {
      return String.format("cmp(%s,%s,%s)", var1.getTag(), var2.getName(), var3);
   }

   // $FF: synthetic method
   public final Object zza(zzx var1, List var2) {
      StringBuilder var3 = new StringBuilder(String.valueOf(var1.getTag()).concat("("));
      Iterator var6 = var2.iterator();

      for(String var5 = ""; var6.hasNext(); var5 = ",") {
         String var4 = (String)var6.next();
         var3.append(var5);
         var3.append(var4);
      }

      var3.append(")");
      return var3.toString();
   }

   // $FF: synthetic method
   public final Object zza(Object var1) {
      return String.format("not(%s)", (String)var1);
   }

   // $FF: synthetic method
   public final Object zzbj() {
      return "ownedByMe()";
   }

   // $FF: synthetic method
   public final Object zzbk() {
      return "all()";
   }

   // $FF: synthetic method
   public final Object zzc(MetadataField var1, Object var2) {
      return String.format("has(%s,%s)", var1.getName(), var2);
   }

   // $FF: synthetic method
   public final Object zze(MetadataField var1) {
      return String.format("fieldOnly(%s)", var1.getName());
   }

   // $FF: synthetic method
   public final Object zzi(String var1) {
      return String.format("fullTextSearch(%s)", var1);
   }
}
