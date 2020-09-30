package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzid;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzin;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzf {
   private static final Map<String, MetadataField<?>> zzjf = new HashMap();
   private static final Map<String, zzg> zzjg = new HashMap();

   static {
      zzb(zzhs.zzjl);
      zzb(zzhs.zzkr);
      zzb(zzhs.zzki);
      zzb(zzhs.zzkp);
      zzb(zzhs.zzks);
      zzb(zzhs.zzjy);
      zzb(zzhs.zzjx);
      zzb(zzhs.zzjz);
      zzb(zzhs.zzka);
      zzb(zzhs.zzkb);
      zzb(zzhs.zzjv);
      zzb(zzhs.zzkd);
      zzb(zzhs.zzke);
      zzb(zzhs.zzkf);
      zzb(zzhs.zzkn);
      zzb(zzhs.zzjm);
      zzb(zzhs.zzkk);
      zzb(zzhs.zzjo);
      zzb(zzhs.zzjw);
      zzb(zzhs.zzjp);
      zzb(zzhs.zzjq);
      zzb(zzhs.zzjr);
      zzb(zzhs.zzjs);
      zzb(zzhs.zzkh);
      zzb(zzhs.zzkc);
      zzb(zzhs.zzkj);
      zzb(zzhs.zzkl);
      zzb(zzhs.zzkm);
      zzb(zzhs.zzko);
      zzb(zzhs.zzkt);
      zzb(zzhs.zzku);
      zzb(zzhs.zzju);
      zzb(zzhs.zzjt);
      zzb(zzhs.zzkq);
      zzb(zzhs.zzkg);
      zzb(zzhs.zzjn);
      zzb(zzhs.zzkv);
      zzb(zzhs.zzkw);
      zzb(zzhs.zzkx);
      zzb(zzhs.zzky);
      zzb(zzhs.zzkz);
      zzb(zzhs.zzla);
      zzb(zzhs.zzlb);
      zzb(zzif.zzld);
      zzb(zzif.zzlf);
      zzb(zzif.zzlg);
      zzb(zzif.zzlh);
      zzb(zzif.zzle);
      zzb(zzif.zzli);
      zzb(zzin.zzlk);
      zzb(zzin.zzll);
      zza(zzo.zzjk);
      zza(zzid.zzlc);
   }

   public static void zza(DataHolder var0) {
      Iterator var1 = zzjg.values().iterator();

      while(var1.hasNext()) {
         ((zzg)var1.next()).zzb(var0);
      }

   }

   private static void zza(zzg var0) {
      if (zzjg.put(var0.zzbd(), var0) != null) {
         String var1 = var0.zzbd();
         StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 46);
         var2.append("A cleaner for key ");
         var2.append(var1);
         var2.append(" has already been registered");
         throw new IllegalStateException(var2.toString());
      }
   }

   private static void zzb(MetadataField<?> var0) {
      if (zzjf.containsKey(var0.getName())) {
         String var1 = String.valueOf(var0.getName());
         if (var1.length() != 0) {
            var1 = "Duplicate field name registered: ".concat(var1);
         } else {
            var1 = new String("Duplicate field name registered: ");
         }

         throw new IllegalArgumentException(var1);
      } else {
         zzjf.put(var0.getName(), var0);
      }
   }

   public static Collection<MetadataField<?>> zzbc() {
      return Collections.unmodifiableCollection(zzjf.values());
   }

   public static MetadataField<?> zzf(String var0) {
      return (MetadataField)zzjf.get(var0);
   }
}
