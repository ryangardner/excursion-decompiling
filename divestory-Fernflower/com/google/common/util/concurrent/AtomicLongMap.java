package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public final class AtomicLongMap<K> implements Serializable {
   @MonotonicNonNullDecl
   private transient Map<K, Long> asMap;
   private final ConcurrentHashMap<K, AtomicLong> map;

   private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> var1) {
      this.map = (ConcurrentHashMap)Preconditions.checkNotNull(var1);
   }

   public static <K> AtomicLongMap<K> create() {
      return new AtomicLongMap(new ConcurrentHashMap());
   }

   public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> var0) {
      AtomicLongMap var1 = create();
      var1.putAll(var0);
      return var1;
   }

   private Map<K, Long> createAsMap() {
      return Collections.unmodifiableMap(Maps.transformValues((Map)this.map, new Function<AtomicLong, Long>() {
         public Long apply(AtomicLong var1) {
            return var1.get();
         }
      }));
   }

   public long addAndGet(K var1, long var2) {
      AtomicLong var5;
      label23:
      do {
         AtomicLong var4 = (AtomicLong)this.map.get(var1);
         var5 = var4;
         if (var4 == null) {
            var4 = (AtomicLong)this.map.putIfAbsent(var1, new AtomicLong(var2));
            var5 = var4;
            if (var4 == null) {
               return var2;
            }
         }

         long var6;
         long var8;
         do {
            var6 = var5.get();
            if (var6 == 0L) {
               continue label23;
            }

            var8 = var6 + var2;
         } while(!var5.compareAndSet(var6, var8));

         return var8;
      } while(!this.map.replace(var1, var5, new AtomicLong(var2)));

      return var2;
   }

   public Map<K, Long> asMap() {
      Map var1 = this.asMap;
      Map var2 = var1;
      if (var1 == null) {
         var2 = this.createAsMap();
         this.asMap = var2;
      }

      return var2;
   }

   public void clear() {
      this.map.clear();
   }

   public boolean containsKey(Object var1) {
      return this.map.containsKey(var1);
   }

   public long decrementAndGet(K var1) {
      return this.addAndGet(var1, -1L);
   }

   public long get(K var1) {
      AtomicLong var4 = (AtomicLong)this.map.get(var1);
      long var2;
      if (var4 == null) {
         var2 = 0L;
      } else {
         var2 = var4.get();
      }

      return var2;
   }

   public long getAndAdd(K var1, long var2) {
      AtomicLong var5;
      label23:
      do {
         AtomicLong var4 = (AtomicLong)this.map.get(var1);
         var5 = var4;
         if (var4 == null) {
            var4 = (AtomicLong)this.map.putIfAbsent(var1, new AtomicLong(var2));
            var5 = var4;
            if (var4 == null) {
               return 0L;
            }
         }

         long var6;
         do {
            var6 = var5.get();
            if (var6 == 0L) {
               continue label23;
            }
         } while(!var5.compareAndSet(var6, var6 + var2));

         return var6;
      } while(!this.map.replace(var1, var5, new AtomicLong(var2)));

      return 0L;
   }

   public long getAndDecrement(K var1) {
      return this.getAndAdd(var1, -1L);
   }

   public long getAndIncrement(K var1) {
      return this.getAndAdd(var1, 1L);
   }

   public long incrementAndGet(K var1) {
      return this.addAndGet(var1, 1L);
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public long put(K var1, long var2) {
      AtomicLong var5;
      label23:
      do {
         AtomicLong var4 = (AtomicLong)this.map.get(var1);
         var5 = var4;
         if (var4 == null) {
            var4 = (AtomicLong)this.map.putIfAbsent(var1, new AtomicLong(var2));
            var5 = var4;
            if (var4 == null) {
               return 0L;
            }
         }

         long var6;
         do {
            var6 = var5.get();
            if (var6 == 0L) {
               continue label23;
            }
         } while(!var5.compareAndSet(var6, var2));

         return var6;
      } while(!this.map.replace(var1, var5, new AtomicLong(var2)));

      return 0L;
   }

   public void putAll(Map<? extends K, ? extends Long> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.put(var3.getKey(), (Long)var3.getValue());
      }

   }

   long putIfAbsent(K var1, long var2) {
      while(true) {
         AtomicLong var4 = (AtomicLong)this.map.get(var1);
         AtomicLong var5 = var4;
         if (var4 == null) {
            var4 = (AtomicLong)this.map.putIfAbsent(var1, new AtomicLong(var2));
            var5 = var4;
            if (var4 == null) {
               return 0L;
            }
         }

         long var6 = var5.get();
         if (var6 == 0L) {
            if (!this.map.replace(var1, var5, new AtomicLong(var2))) {
               continue;
            }

            return 0L;
         }

         return var6;
      }
   }

   public long remove(K var1) {
      AtomicLong var2 = (AtomicLong)this.map.get(var1);
      if (var2 == null) {
         return 0L;
      } else {
         long var3;
         do {
            var3 = var2.get();
         } while(var3 != 0L && !var2.compareAndSet(var3, 0L));

         this.map.remove(var1, var2);
         return var3;
      }
   }

   boolean remove(K var1, long var2) {
      AtomicLong var4 = (AtomicLong)this.map.get(var1);
      if (var4 == null) {
         return false;
      } else {
         long var5 = var4.get();
         if (var5 != var2) {
            return false;
         } else if (var5 != 0L && !var4.compareAndSet(var5, 0L)) {
            return false;
         } else {
            this.map.remove(var1, var4);
            return true;
         }
      }
   }

   public void removeAllZeros() {
      Iterator var1 = this.map.entrySet().iterator();

      while(var1.hasNext()) {
         AtomicLong var2 = (AtomicLong)((Entry)var1.next()).getValue();
         if (var2 != null && var2.get() == 0L) {
            var1.remove();
         }
      }

   }

   public boolean removeIfZero(K var1) {
      return this.remove(var1, 0L);
   }

   boolean replace(K var1, long var2, long var4) {
      boolean var6 = false;
      boolean var7 = false;
      if (var2 == 0L) {
         if (this.putIfAbsent(var1, var4) == 0L) {
            var7 = true;
         }

         return var7;
      } else {
         AtomicLong var8 = (AtomicLong)this.map.get(var1);
         if (var8 == null) {
            var7 = var6;
         } else {
            var7 = var8.compareAndSet(var2, var4);
         }

         return var7;
      }
   }

   public int size() {
      return this.map.size();
   }

   public long sum() {
      Iterator var1 = this.map.values().iterator();

      long var2;
      for(var2 = 0L; var1.hasNext(); var2 += ((AtomicLong)var1.next()).get()) {
      }

      return var2;
   }

   public String toString() {
      return this.map.toString();
   }
}
