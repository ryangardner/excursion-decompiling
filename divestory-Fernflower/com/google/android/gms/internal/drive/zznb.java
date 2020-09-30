package com.google.android.gms.internal.drive;

import java.util.ListIterator;

final class zznb implements ListIterator<String> {
   private ListIterator<String> zzvu;
   // $FF: synthetic field
   private final int zzvv;
   // $FF: synthetic field
   private final zzna zzvw;

   zznb(zzna var1, int var2) {
      this.zzvw = var1;
      this.zzvv = var2;
      this.zzvu = zzna.zza(this.zzvw).listIterator(this.zzvv);
   }

   // $FF: synthetic method
   public final void add(Object var1) {
      throw new UnsupportedOperationException();
   }

   public final boolean hasNext() {
      return this.zzvu.hasNext();
   }

   public final boolean hasPrevious() {
      return this.zzvu.hasPrevious();
   }

   // $FF: synthetic method
   public final Object next() {
      return (String)this.zzvu.next();
   }

   public final int nextIndex() {
      return this.zzvu.nextIndex();
   }

   // $FF: synthetic method
   public final Object previous() {
      return (String)this.zzvu.previous();
   }

   public final int previousIndex() {
      return this.zzvu.previousIndex();
   }

   public final void remove() {
      throw new UnsupportedOperationException();
   }

   // $FF: synthetic method
   public final void set(Object var1) {
      throw new UnsupportedOperationException();
   }
}
