package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class zza<T> implements MetadataField<T> {
   private final String fieldName;
   private final Set<String> zziw;
   private final Set<String> zzix;
   private final int zziy;

   protected zza(String var1, int var2) {
      this.fieldName = (String)Preconditions.checkNotNull(var1, "fieldName");
      this.zziw = Collections.singleton(var1);
      this.zzix = Collections.emptySet();
      this.zziy = var2;
   }

   protected zza(String var1, Collection<String> var2, Collection<String> var3, int var4) {
      this.fieldName = (String)Preconditions.checkNotNull(var1, "fieldName");
      this.zziw = Collections.unmodifiableSet(new HashSet(var2));
      this.zzix = Collections.unmodifiableSet(new HashSet(var3));
      this.zziy = var4;
   }

   public final String getName() {
      return this.fieldName;
   }

   public String toString() {
      return this.fieldName;
   }

   public final T zza(Bundle var1) {
      Preconditions.checkNotNull(var1, "bundle");
      return var1.get(this.fieldName) != null ? this.zzb(var1) : null;
   }

   public final T zza(DataHolder var1, int var2, int var3) {
      return this.zzb(var1, var2, var3) ? this.zzc(var1, var2, var3) : null;
   }

   protected abstract void zza(Bundle var1, T var2);

   public final void zza(DataHolder var1, MetadataBundle var2, int var3, int var4) {
      Preconditions.checkNotNull(var1, "dataHolder");
      Preconditions.checkNotNull(var2, "bundle");
      if (this.zzb(var1, var3, var4)) {
         var2.zzb(this, this.zzc(var1, var3, var4));
      }

   }

   public final void zza(T var1, Bundle var2) {
      Preconditions.checkNotNull(var2, "bundle");
      if (var1 == null) {
         var2.putString(this.fieldName, (String)null);
      } else {
         this.zza(var2, var1);
      }
   }

   public final Collection<String> zzaz() {
      return this.zziw;
   }

   protected abstract T zzb(Bundle var1);

   protected boolean zzb(DataHolder var1, int var2, int var3) {
      Iterator var4 = this.zziw.iterator();

      String var5;
      do {
         if (!var4.hasNext()) {
            return true;
         }

         var5 = (String)var4.next();
      } while(!var1.isClosed() && var1.hasColumn(var5) && !var1.hasNull(var5, var2, var3));

      return false;
   }

   protected abstract T zzc(DataHolder var1, int var2, int var3);
}
