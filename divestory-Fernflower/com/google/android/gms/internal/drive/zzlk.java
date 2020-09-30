package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class zzlk<K, V> extends LinkedHashMap<K, V> {
   private static final zzlk zzty;
   private boolean zznh = true;

   static {
      zzlk var0 = new zzlk();
      zzty = var0;
      var0.zznh = false;
   }

   private zzlk() {
   }

   private zzlk(Map<K, V> var1) {
      super(var1);
   }

   public static <K, V> zzlk<K, V> zzdw() {
      return zzty;
   }

   private final void zzdy() {
      if (!this.zznh) {
         throw new UnsupportedOperationException();
      }
   }

   private static int zzg(Object var0) {
      if (var0 instanceof byte[]) {
         return zzkm.hashCode((byte[])var0);
      } else if (!(var0 instanceof zzkn)) {
         return var0.hashCode();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public final void clear() {
      this.zzdy();
      super.clear();
   }

   public final Set<Entry<K, V>> entrySet() {
      return this.isEmpty() ? Collections.emptySet() : super.entrySet();
   }

   public final boolean equals(Object var1) {
      if (var1 instanceof Map) {
         boolean var2;
         label44: {
            label51: {
               Map var7 = (Map)var1;
               if (this != var7) {
                  if (this.size() != var7.size()) {
                     break label51;
                  }

                  Iterator var3 = this.entrySet().iterator();

                  while(var3.hasNext()) {
                     Entry var4 = (Entry)var3.next();
                     if (!var7.containsKey(var4.getKey())) {
                        break label51;
                     }

                     Object var5 = var4.getValue();
                     Object var8 = var7.get(var4.getKey());
                     boolean var6;
                     if (var5 instanceof byte[] && var8 instanceof byte[]) {
                        var6 = Arrays.equals((byte[])var5, (byte[])var8);
                     } else {
                        var6 = var5.equals(var8);
                     }

                     if (!var6) {
                        break label51;
                     }
                  }
               }

               var2 = true;
               break label44;
            }

            var2 = false;
         }

         if (var2) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      Iterator var1 = this.entrySet().iterator();

      int var2;
      Entry var3;
      int var4;
      for(var2 = 0; var1.hasNext(); var2 += zzg(var3.getValue()) ^ var4) {
         var3 = (Entry)var1.next();
         var4 = zzg(var3.getKey());
      }

      return var2;
   }

   public final boolean isMutable() {
      return this.zznh;
   }

   public final V put(K var1, V var2) {
      this.zzdy();
      zzkm.checkNotNull(var1);
      zzkm.checkNotNull(var2);
      return super.put(var1, var2);
   }

   public final void putAll(Map<? extends K, ? extends V> var1) {
      this.zzdy();
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         zzkm.checkNotNull(var3);
         zzkm.checkNotNull(var1.get(var3));
      }

      super.putAll(var1);
   }

   public final V remove(Object var1) {
      this.zzdy();
      return super.remove(var1);
   }

   public final void zza(zzlk<K, V> var1) {
      this.zzdy();
      if (!var1.isEmpty()) {
         this.putAll(var1);
      }

   }

   public final void zzbp() {
      this.zznh = false;
   }

   public final zzlk<K, V> zzdx() {
      return this.isEmpty() ? new zzlk() : new zzlk(this);
   }
}
