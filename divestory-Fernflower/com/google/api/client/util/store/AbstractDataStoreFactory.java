package com.google.api.client.util.store;

import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public abstract class AbstractDataStoreFactory implements DataStoreFactory {
   private static final Pattern ID_PATTERN = Pattern.compile("\\w{1,30}");
   private final Map<String, DataStore<? extends Serializable>> dataStoreMap = Maps.newHashMap();
   private final Lock lock = new ReentrantLock();

   protected abstract <V extends Serializable> DataStore<V> createDataStore(String var1) throws IOException;

   public final <V extends Serializable> DataStore<V> getDataStore(String var1) throws IOException {
      Preconditions.checkArgument(ID_PATTERN.matcher(var1).matches(), "%s does not match pattern %s", var1, ID_PATTERN);
      this.lock.lock();

      DataStore var3;
      label71: {
         Throwable var10000;
         label75: {
            boolean var10001;
            DataStore var2;
            try {
               var2 = (DataStore)this.dataStoreMap.get(var1);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label75;
            }

            var3 = var2;
            if (var2 != null) {
               break label71;
            }

            label66:
            try {
               var3 = this.createDataStore(var1);
               this.dataStoreMap.put(var1, var3);
               break label71;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label66;
            }
         }

         Throwable var10 = var10000;
         this.lock.unlock();
         throw var10;
      }

      this.lock.unlock();
      return var3;
   }
}
