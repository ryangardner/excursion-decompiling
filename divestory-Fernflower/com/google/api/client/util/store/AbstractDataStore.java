package com.google.api.client.util.store;

import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractDataStore<V extends Serializable> implements DataStore<V> {
   private final DataStoreFactory dataStoreFactory;
   private final String id;

   protected AbstractDataStore(DataStoreFactory var1, String var2) {
      this.dataStoreFactory = (DataStoreFactory)Preconditions.checkNotNull(var1);
      this.id = (String)Preconditions.checkNotNull(var2);
   }

   public boolean containsKey(String var1) throws IOException {
      boolean var2;
      if (this.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(V var1) throws IOException {
      return this.values().contains(var1);
   }

   public DataStoreFactory getDataStoreFactory() {
      return this.dataStoreFactory;
   }

   public final String getId() {
      return this.id;
   }

   public boolean isEmpty() throws IOException {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int size() throws IOException {
      return this.keySet().size();
   }
}
