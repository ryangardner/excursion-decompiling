package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbstractMemoryDataStore<V extends Serializable> extends AbstractDataStore<V> {
   protected HashMap<String, byte[]> keyValueMap = Maps.newHashMap();
   private final Lock lock = new ReentrantLock();

   protected AbstractMemoryDataStore(DataStoreFactory var1, String var2) {
      super(var1, var2);
   }

   public final DataStore<V> clear() throws IOException {
      this.lock.lock();

      try {
         this.keyValueMap.clear();
         this.save();
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public boolean containsKey(String var1) throws IOException {
      if (var1 == null) {
         return false;
      } else {
         this.lock.lock();

         boolean var2;
         try {
            var2 = this.keyValueMap.containsKey(var1);
         } finally {
            this.lock.unlock();
         }

         return var2;
      }
   }

   public boolean containsValue(V var1) throws IOException {
      if (var1 == null) {
         return false;
      } else {
         this.lock.lock();

         label94: {
            Throwable var10000;
            label93: {
               boolean var10001;
               byte[] var2;
               Iterator var10;
               try {
                  var2 = IOUtils.serialize(var1);
                  var10 = this.keyValueMap.values().iterator();
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label93;
               }

               while(true) {
                  boolean var3;
                  try {
                     if (!var10.hasNext()) {
                        break label94;
                     }

                     var3 = Arrays.equals(var2, (byte[])var10.next());
                  } catch (Throwable var8) {
                     var10000 = var8;
                     var10001 = false;
                     break;
                  }

                  if (var3) {
                     this.lock.unlock();
                     return true;
                  }
               }
            }

            Throwable var11 = var10000;
            this.lock.unlock();
            throw var11;
         }

         this.lock.unlock();
         return false;
      }
   }

   public DataStore<V> delete(String var1) throws IOException {
      if (var1 == null) {
         return this;
      } else {
         this.lock.lock();

         try {
            this.keyValueMap.remove(var1);
            this.save();
         } finally {
            this.lock.unlock();
         }

         return this;
      }
   }

   public final V get(String var1) throws IOException {
      if (var1 == null) {
         return null;
      } else {
         this.lock.lock();

         Serializable var4;
         try {
            var4 = IOUtils.deserialize((byte[])this.keyValueMap.get(var1));
         } finally {
            this.lock.unlock();
         }

         return var4;
      }
   }

   public boolean isEmpty() throws IOException {
      this.lock.lock();

      boolean var1;
      try {
         var1 = this.keyValueMap.isEmpty();
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public final Set<String> keySet() throws IOException {
      this.lock.lock();

      Set var1;
      try {
         var1 = Collections.unmodifiableSet(this.keyValueMap.keySet());
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public void save() throws IOException {
   }

   public final DataStore<V> set(String var1, V var2) throws IOException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      this.lock.lock();

      try {
         this.keyValueMap.put(var1, IOUtils.serialize(var2));
         this.save();
      } finally {
         this.lock.unlock();
      }

      return this;
   }

   public int size() throws IOException {
      this.lock.lock();

      int var1;
      try {
         var1 = this.keyValueMap.size();
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public String toString() {
      return DataStoreUtils.toString(this);
   }

   public final Collection<V> values() throws IOException {
      this.lock.lock();

      List var16;
      label133: {
         Throwable var10000;
         label132: {
            ArrayList var1;
            boolean var10001;
            Iterator var2;
            try {
               var1 = Lists.newArrayList();
               var2 = this.keyValueMap.values().iterator();
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label132;
            }

            while(true) {
               try {
                  if (var2.hasNext()) {
                     var1.add(IOUtils.deserialize((byte[])var2.next()));
                     continue;
                  }
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break;
               }

               try {
                  var16 = Collections.unmodifiableList(var1);
                  break label133;
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var15 = var10000;
         this.lock.unlock();
         throw var15;
      }

      this.lock.unlock();
      return var16;
   }
}
