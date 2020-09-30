package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

public class DataBufferResponse<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
   public DataBufferResponse() {
   }

   public DataBufferResponse(R var1) {
      super(var1);
   }

   public void close() {
      ((AbstractDataBuffer)this.getResult()).close();
   }

   public T get(int var1) {
      return ((AbstractDataBuffer)this.getResult()).get(var1);
   }

   public int getCount() {
      return ((AbstractDataBuffer)this.getResult()).getCount();
   }

   public Bundle getMetadata() {
      return ((AbstractDataBuffer)this.getResult()).getMetadata();
   }

   public boolean isClosed() {
      return ((AbstractDataBuffer)this.getResult()).isClosed();
   }

   public Iterator<T> iterator() {
      return ((AbstractDataBuffer)this.getResult()).iterator();
   }

   public void release() {
      ((AbstractDataBuffer)this.getResult()).release();
   }

   public Iterator<T> singleRefIterator() {
      return ((AbstractDataBuffer)this.getResult()).singleRefIterator();
   }
}
