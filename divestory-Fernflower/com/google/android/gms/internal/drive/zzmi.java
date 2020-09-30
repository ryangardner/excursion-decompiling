package com.google.android.gms.internal.drive;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

class zzmi<K extends Comparable<K>, V> extends AbstractMap<K, V> {
   private boolean zzot;
   private final int zzvd;
   private List<zzmp> zzve;
   private Map<K, V> zzvf;
   private volatile zzmr zzvg;
   private Map<K, V> zzvh;
   private volatile zzml zzvi;

   private zzmi(int var1) {
      this.zzvd = var1;
      this.zzve = Collections.emptyList();
      this.zzvf = Collections.emptyMap();
      this.zzvh = Collections.emptyMap();
   }

   // $FF: synthetic method
   zzmi(int var1, zzmj var2) {
      this(var1);
   }

   private final int zza(K var1) {
      int var2 = this.zzve.size() - 1;
      int var3;
      if (var2 >= 0) {
         var3 = var1.compareTo((Comparable)((zzmp)this.zzve.get(var2)).getKey());
         if (var3 > 0) {
            return -(var2 + 2);
         }

         if (var3 == 0) {
            return var2;
         }
      }

      var3 = 0;

      while(var3 <= var2) {
         int var4 = (var3 + var2) / 2;
         int var5 = var1.compareTo((Comparable)((zzmp)this.zzve.get(var4)).getKey());
         if (var5 < 0) {
            var2 = var4 - 1;
         } else {
            if (var5 <= 0) {
               return var4;
            }

            var3 = var4 + 1;
         }
      }

      return -(var3 + 1);
   }

   // $FF: synthetic method
   static Object zza(zzmi var0, int var1) {
      return var0.zzax(var1);
   }

   // $FF: synthetic method
   static void zza(zzmi var0) {
      var0.zzeu();
   }

   static <FieldDescriptorType extends zzkd<FieldDescriptorType>> zzmi<FieldDescriptorType, Object> zzav(int var0) {
      return new zzmj(var0);
   }

   private final V zzax(int var1) {
      this.zzeu();
      Object var2 = ((zzmp)this.zzve.remove(var1)).getValue();
      if (!this.zzvf.isEmpty()) {
         Iterator var3 = this.zzev().entrySet().iterator();
         this.zzve.add(new zzmp(this, (Entry)var3.next()));
         var3.remove();
      }

      return var2;
   }

   // $FF: synthetic method
   static List zzb(zzmi var0) {
      return var0.zzve;
   }

   // $FF: synthetic method
   static Map zzc(zzmi var0) {
      return var0.zzvf;
   }

   // $FF: synthetic method
   static Map zzd(zzmi var0) {
      return var0.zzvh;
   }

   private final void zzeu() {
      if (this.zzot) {
         throw new UnsupportedOperationException();
      }
   }

   private final SortedMap<K, V> zzev() {
      this.zzeu();
      if (this.zzvf.isEmpty() && !(this.zzvf instanceof TreeMap)) {
         TreeMap var1 = new TreeMap();
         this.zzvf = var1;
         this.zzvh = ((TreeMap)var1).descendingMap();
      }

      return (SortedMap)this.zzvf;
   }

   public void clear() {
      this.zzeu();
      if (!this.zzve.isEmpty()) {
         this.zzve.clear();
      }

      if (!this.zzvf.isEmpty()) {
         this.zzvf.clear();
      }

   }

   public boolean containsKey(Object var1) {
      Comparable var2 = (Comparable)var1;
      return this.zza(var2) >= 0 || this.zzvf.containsKey(var2);
   }

   public Set<Entry<K, V>> entrySet() {
      if (this.zzvg == null) {
         this.zzvg = new zzmr(this, (zzmj)null);
      }

      return this.zzvg;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzmi)) {
         return super.equals(var1);
      } else {
         zzmi var5 = (zzmi)var1;
         int var2 = this.size();
         if (var2 != var5.size()) {
            return false;
         } else {
            int var3 = this.zzer();
            if (var3 != var5.zzer()) {
               return this.entrySet().equals(var5.entrySet());
            } else {
               for(int var4 = 0; var4 < var3; ++var4) {
                  if (!this.zzaw(var4).equals(var5.zzaw(var4))) {
                     return false;
                  }
               }

               if (var3 != var2) {
                  return this.zzvf.equals(var5.zzvf);
               } else {
                  return true;
               }
            }
         }
      }
   }

   public V get(Object var1) {
      Comparable var3 = (Comparable)var1;
      int var2 = this.zza(var3);
      return var2 >= 0 ? ((zzmp)this.zzve.get(var2)).getValue() : this.zzvf.get(var3);
   }

   public int hashCode() {
      int var1 = this.zzer();
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += ((zzmp)this.zzve.get(var2)).hashCode();
      }

      var2 = var3;
      if (this.zzvf.size() > 0) {
         var2 = var3 + this.zzvf.hashCode();
      }

      return var2;
   }

   public final boolean isImmutable() {
      return this.zzot;
   }

   // $FF: synthetic method
   public Object put(Object var1, Object var2) {
      return this.zza((Comparable)var1, var2);
   }

   public V remove(Object var1) {
      this.zzeu();
      Comparable var3 = (Comparable)var1;
      int var2 = this.zza(var3);
      if (var2 >= 0) {
         return this.zzax(var2);
      } else {
         return this.zzvf.isEmpty() ? null : this.zzvf.remove(var3);
      }
   }

   public int size() {
      return this.zzve.size() + this.zzvf.size();
   }

   public final V zza(K var1, V var2) {
      this.zzeu();
      int var3 = this.zza(var1);
      if (var3 >= 0) {
         return ((zzmp)this.zzve.get(var3)).setValue(var2);
      } else {
         this.zzeu();
         if (this.zzve.isEmpty() && !(this.zzve instanceof ArrayList)) {
            this.zzve = new ArrayList(this.zzvd);
         }

         int var4 = -(var3 + 1);
         if (var4 >= this.zzvd) {
            return this.zzev().put(var1, var2);
         } else {
            int var5 = this.zzve.size();
            var3 = this.zzvd;
            if (var5 == var3) {
               zzmp var6 = (zzmp)this.zzve.remove(var3 - 1);
               this.zzev().put((Comparable)var6.getKey(), var6.getValue());
            }

            this.zzve.add(var4, new zzmp(this, var1, var2));
            return null;
         }
      }
   }

   public final Entry<K, V> zzaw(int var1) {
      return (Entry)this.zzve.get(var1);
   }

   public void zzbp() {
      if (!this.zzot) {
         Map var1;
         if (this.zzvf.isEmpty()) {
            var1 = Collections.emptyMap();
         } else {
            var1 = Collections.unmodifiableMap(this.zzvf);
         }

         this.zzvf = var1;
         if (this.zzvh.isEmpty()) {
            var1 = Collections.emptyMap();
         } else {
            var1 = Collections.unmodifiableMap(this.zzvh);
         }

         this.zzvh = var1;
         this.zzot = true;
      }

   }

   public final int zzer() {
      return this.zzve.size();
   }

   public final Iterable<Entry<K, V>> zzes() {
      return (Iterable)(this.zzvf.isEmpty() ? zzmm.zzex() : this.zzvf.entrySet());
   }

   final Set<Entry<K, V>> zzet() {
      if (this.zzvi == null) {
         this.zzvi = new zzml(this, (zzmj)null);
      }

      return this.zzvi;
   }
}
