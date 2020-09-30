package com.google.android.gms.internal.drive;

import java.util.NoSuchElementException;

final class zzjd extends zzjf {
   private final int limit;
   private int position;
   // $FF: synthetic field
   private final zzjc zznu;

   zzjd(zzjc var1) {
      this.zznu = var1;
      this.position = 0;
      this.limit = this.zznu.size();
   }

   public final boolean hasNext() {
      return this.position < this.limit;
   }

   public final byte nextByte() {
      int var1 = this.position;
      if (var1 < this.limit) {
         this.position = var1 + 1;
         return this.zznu.zzt(var1);
      } else {
         throw new NoSuchElementException();
      }
   }
}
