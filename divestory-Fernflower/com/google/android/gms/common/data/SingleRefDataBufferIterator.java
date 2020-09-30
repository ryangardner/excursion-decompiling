package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Preconditions;
import java.util.NoSuchElementException;

public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
   private T zac;

   public SingleRefDataBufferIterator(DataBuffer<T> var1) {
      super(var1);
   }

   public T next() {
      StringBuilder var1;
      if (this.hasNext()) {
         ++this.zab;
         if (this.zab == 0) {
            Object var4 = Preconditions.checkNotNull(this.zaa.get(0));
            this.zac = var4;
            if (!(var4 instanceof DataBufferRef)) {
               String var2 = String.valueOf(this.zac.getClass());
               var1 = new StringBuilder(String.valueOf(var2).length() + 44);
               var1.append("DataBuffer reference of type ");
               var1.append(var2);
               var1.append(" is not movable");
               throw new IllegalStateException(var1.toString());
            }
         } else {
            ((DataBufferRef)Preconditions.checkNotNull(this.zac)).zaa(this.zab);
         }

         return this.zac;
      } else {
         int var3 = this.zab;
         var1 = new StringBuilder(46);
         var1.append("Cannot advance the iterator beyond ");
         var1.append(var3);
         throw new NoSuchElementException(var1.toString());
      }
   }
}
