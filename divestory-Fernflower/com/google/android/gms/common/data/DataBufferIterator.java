package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DataBufferIterator<T> implements Iterator<T> {
   protected final DataBuffer<T> zaa;
   protected int zab;

   public DataBufferIterator(DataBuffer<T> var1) {
      this.zaa = (DataBuffer)Preconditions.checkNotNull(var1);
      this.zab = -1;
   }

   public boolean hasNext() {
      return this.zab < this.zaa.getCount() - 1;
   }

   public T next() {
      int var2;
      if (this.hasNext()) {
         DataBuffer var3 = this.zaa;
         var2 = this.zab + 1;
         this.zab = var2;
         return var3.get(var2);
      } else {
         var2 = this.zab;
         StringBuilder var1 = new StringBuilder(46);
         var1.append("Cannot advance the iterator beyond ");
         var1.append(var2);
         throw new NoSuchElementException(var1.toString());
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
   }
}
