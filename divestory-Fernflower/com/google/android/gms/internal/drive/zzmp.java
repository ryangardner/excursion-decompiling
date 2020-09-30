package com.google.android.gms.internal.drive;

import java.util.Map.Entry;

final class zzmp implements Comparable<zzmp>, Entry<K, V> {
   private V value;
   // $FF: synthetic field
   private final zzmi zzvk;
   private final K zzvn;

   zzmp(zzmi var1, Comparable var2, Object var3) {
      this.zzvk = var1;
      this.zzvn = var2;
      this.value = var3;
   }

   zzmp(zzmi var1, Entry var2) {
      this(var1, (Comparable)var2.getKey(), var2.getValue());
   }

   private static boolean equals(Object var0, Object var1) {
      if (var0 == null) {
         return var1 == null;
      } else {
         return var0.equals(var1);
      }
   }

   // $FF: synthetic method
   public final int compareTo(Object var1) {
      zzmp var2 = (zzmp)var1;
      return ((Comparable)this.getKey()).compareTo((Comparable)var2.getKey());
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Entry)) {
         return false;
      } else {
         Entry var2 = (Entry)var1;
         return equals(this.zzvn, var2.getKey()) && equals(this.value, var2.getValue());
      }
   }

   // $FF: synthetic method
   public final Object getKey() {
      return this.zzvn;
   }

   public final V getValue() {
      return this.value;
   }

   public final int hashCode() {
      Comparable var1 = this.zzvn;
      int var2 = 0;
      int var3;
      if (var1 == null) {
         var3 = 0;
      } else {
         var3 = var1.hashCode();
      }

      Object var4 = this.value;
      if (var4 != null) {
         var2 = var4.hashCode();
      }

      return var3 ^ var2;
   }

   public final V setValue(V var1) {
      zzmi.zza(this.zzvk);
      Object var2 = this.value;
      this.value = var1;
      return var2;
   }

   public final String toString() {
      String var1 = String.valueOf(this.zzvn);
      String var2 = String.valueOf(this.value);
      StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 1 + String.valueOf(var2).length());
      var3.append(var1);
      var3.append("=");
      var3.append(var2);
      return var3.toString();
   }
}
