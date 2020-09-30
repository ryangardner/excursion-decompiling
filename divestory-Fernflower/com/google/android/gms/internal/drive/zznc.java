package com.google.android.gms.internal.drive;

import java.util.Iterator;

final class zznc implements Iterator<String> {
   // $FF: synthetic field
   private final zzna zzvw;
   private Iterator<String> zzvx;

   zznc(zzna var1) {
      this.zzvw = var1;
      this.zzvx = zzna.zza(this.zzvw).iterator();
   }

   public final boolean hasNext() {
      return this.zzvx.hasNext();
   }

   // $FF: synthetic method
   public final Object next() {
      return (String)this.zzvx.next();
   }

   public final void remove() {
      throw new UnsupportedOperationException();
   }
}
