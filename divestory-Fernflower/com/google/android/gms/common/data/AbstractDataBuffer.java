package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
   protected final DataHolder mDataHolder;

   protected AbstractDataBuffer(DataHolder var1) {
      this.mDataHolder = var1;
   }

   public final void close() {
      this.release();
   }

   public abstract T get(int var1);

   public int getCount() {
      DataHolder var1 = this.mDataHolder;
      return var1 == null ? 0 : var1.getCount();
   }

   public Bundle getMetadata() {
      DataHolder var1 = this.mDataHolder;
      return var1 == null ? null : var1.getMetadata();
   }

   @Deprecated
   public boolean isClosed() {
      DataHolder var1 = this.mDataHolder;
      return var1 == null || var1.isClosed();
   }

   public Iterator<T> iterator() {
      return new DataBufferIterator(this);
   }

   public void release() {
      DataHolder var1 = this.mDataHolder;
      if (var1 != null) {
         var1.close();
      }

   }

   public Iterator<T> singleRefIterator() {
      return new SingleRefDataBufferIterator(this);
   }
}
