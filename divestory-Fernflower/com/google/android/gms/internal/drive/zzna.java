package com.google.android.gms.internal.drive;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzna extends AbstractList<String> implements zzkz, RandomAccess {
   private final zzkz zzvt;

   public zzna(zzkz var1) {
      this.zzvt = var1;
   }

   // $FF: synthetic method
   static zzkz zza(zzna var0) {
      return var0.zzvt;
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      return (String)this.zzvt.get(var1);
   }

   public final Iterator<String> iterator() {
      return new zznc(this);
   }

   public final ListIterator<String> listIterator(int var1) {
      return new zznb(this, var1);
   }

   public final int size() {
      return this.zzvt.size();
   }

   public final Object zzao(int var1) {
      return this.zzvt.zzao(var1);
   }

   public final List<?> zzdr() {
      return this.zzvt.zzdr();
   }

   public final zzkz zzds() {
      return this;
   }
}
