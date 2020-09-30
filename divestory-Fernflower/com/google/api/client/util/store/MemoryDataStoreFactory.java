package com.google.api.client.util.store;

import java.io.IOException;
import java.io.Serializable;

public class MemoryDataStoreFactory extends AbstractDataStoreFactory {
   public static MemoryDataStoreFactory getDefaultInstance() {
      return MemoryDataStoreFactory.InstanceHolder.INSTANCE;
   }

   protected <V extends Serializable> DataStore<V> createDataStore(String var1) throws IOException {
      return new MemoryDataStoreFactory.MemoryDataStore(this, var1);
   }

   static class InstanceHolder {
      static final MemoryDataStoreFactory INSTANCE = new MemoryDataStoreFactory();
   }

   static class MemoryDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {
      MemoryDataStore(MemoryDataStoreFactory var1, String var2) {
         super(var1, var2);
      }

      public MemoryDataStoreFactory getDataStoreFactory() {
         return (MemoryDataStoreFactory)super.getDataStoreFactory();
      }
   }
}
