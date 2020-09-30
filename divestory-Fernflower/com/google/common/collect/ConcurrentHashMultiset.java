package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
   private static final long serialVersionUID = 1L;
   private final transient ConcurrentMap<E, AtomicInteger> countMap;

   ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> var1) {
      Preconditions.checkArgument(var1.isEmpty(), "the backing map (%s) must be empty", (Object)var1);
      this.countMap = var1;
   }

   public static <E> ConcurrentHashMultiset<E> create() {
      return new ConcurrentHashMultiset(new ConcurrentHashMap());
   }

   public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> var0) {
      ConcurrentHashMultiset var1 = create();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> var0) {
      return new ConcurrentHashMultiset(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      ConcurrentMap var2 = (ConcurrentMap)var1.readObject();
      ConcurrentHashMultiset.FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, var2);
   }

   private List<E> snapshot() {
      ArrayList var1 = Lists.newArrayListWithExpectedSize(this.size());
      Iterator var2 = this.entrySet().iterator();

      while(var2.hasNext()) {
         Multiset.Entry var3 = (Multiset.Entry)var2.next();
         Object var4 = var3.getElement();

         for(int var5 = var3.getCount(); var5 > 0; --var5) {
            var1.add(var4);
         }
      }

      return var1;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.countMap);
   }

   public int add(E var1, int var2) {
      Preconditions.checkNotNull(var1);
      if (var2 == 0) {
         return this.count(var1);
      } else {
         CollectPreconditions.checkPositive(var2, "occurences");

         AtomicInteger var3;
         AtomicInteger var4;
         label37:
         do {
            var3 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
            var4 = var3;
            if (var3 == null) {
               var3 = (AtomicInteger)this.countMap.putIfAbsent(var1, new AtomicInteger(var2));
               var4 = var3;
               if (var3 == null) {
                  return 0;
               }
            }

            int var5;
            boolean var6;
            do {
               var5 = var4.get();
               if (var5 == 0) {
                  var3 = new AtomicInteger(var2);
                  continue label37;
               }

               try {
                  var6 = var4.compareAndSet(var5, IntMath.checkedAdd(var5, var2));
               } catch (ArithmeticException var7) {
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Overflow adding ");
                  var8.append(var2);
                  var8.append(" occurrences to a count of ");
                  var8.append(var5);
                  throw new IllegalArgumentException(var8.toString());
               }
            } while(!var6);

            return var5;
         } while(this.countMap.putIfAbsent(var1, var3) != null && !this.countMap.replace(var1, var4, var3));

         return 0;
      }
   }

   public void clear() {
      this.countMap.clear();
   }

   public int count(@NullableDecl Object var1) {
      AtomicInteger var3 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
      int var2;
      if (var3 == null) {
         var2 = 0;
      } else {
         var2 = var3.get();
      }

      return var2;
   }

   Set<E> createElementSet() {
      return new ForwardingSet<E>(this.countMap.keySet()) {
         // $FF: synthetic field
         final Set val$delegate;

         {
            this.val$delegate = var2;
         }

         public boolean contains(@NullableDecl Object var1) {
            boolean var2;
            if (var1 != null && Collections2.safeContains(this.val$delegate, var1)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean containsAll(Collection<?> var1) {
            return this.standardContainsAll(var1);
         }

         protected Set<E> delegate() {
            return this.val$delegate;
         }

         public boolean remove(Object var1) {
            boolean var2;
            if (var1 != null && Collections2.safeRemove(this.val$delegate, var1)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean removeAll(Collection<?> var1) {
            return this.standardRemoveAll(var1);
         }
      };
   }

   @Deprecated
   public Set<Multiset.Entry<E>> createEntrySet() {
      return new ConcurrentHashMultiset.EntrySet();
   }

   int distinctElements() {
      return this.countMap.size();
   }

   Iterator<E> elementIterator() {
      throw new AssertionError("should never be called");
   }

   Iterator<Multiset.Entry<E>> entryIterator() {
      return new ForwardingIterator<Multiset.Entry<E>>(new AbstractIterator<Multiset.Entry<E>>() {
         private final Iterator<java.util.Map.Entry<E, AtomicInteger>> mapEntries;

         {
            this.mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
         }

         protected Multiset.Entry<E> computeNext() {
            java.util.Map.Entry var1;
            int var2;
            do {
               if (!this.mapEntries.hasNext()) {
                  return (Multiset.Entry)this.endOfData();
               }

               var1 = (java.util.Map.Entry)this.mapEntries.next();
               var2 = ((AtomicInteger)var1.getValue()).get();
            } while(var2 == 0);

            return Multisets.immutableEntry(var1.getKey(), var2);
         }
      }) {
         @NullableDecl
         private Multiset.Entry<E> last;
         // $FF: synthetic field
         final Iterator val$readOnlyIterator;

         {
            this.val$readOnlyIterator = var2;
         }

         protected Iterator<Multiset.Entry<E>> delegate() {
            return this.val$readOnlyIterator;
         }

         public Multiset.Entry<E> next() {
            Multiset.Entry var1 = (Multiset.Entry)super.next();
            this.last = var1;
            return var1;
         }

         public void remove() {
            boolean var1;
            if (this.last != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
            this.last = null;
         }
      };
   }

   public boolean isEmpty() {
      return this.countMap.isEmpty();
   }

   public Iterator<E> iterator() {
      return Multisets.iteratorImpl(this);
   }

   public int remove(@NullableDecl Object var1, int var2) {
      if (var2 == 0) {
         return this.count(var1);
      } else {
         CollectPreconditions.checkPositive(var2, "occurences");
         AtomicInteger var3 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
         if (var3 == null) {
            return 0;
         } else {
            int var4;
            int var5;
            do {
               var4 = var3.get();
               if (var4 == 0) {
                  return 0;
               }

               var5 = Math.max(0, var4 - var2);
            } while(!var3.compareAndSet(var4, var5));

            if (var5 == 0) {
               this.countMap.remove(var1, var3);
            }

            return var4;
         }
      }
   }

   public boolean removeExactly(@NullableDecl Object var1, int var2) {
      if (var2 == 0) {
         return true;
      } else {
         CollectPreconditions.checkPositive(var2, "occurences");
         AtomicInteger var3 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
         if (var3 == null) {
            return false;
         } else {
            int var4;
            int var5;
            do {
               var4 = var3.get();
               if (var4 < var2) {
                  return false;
               }

               var5 = var4 - var2;
            } while(!var3.compareAndSet(var4, var5));

            if (var5 == 0) {
               this.countMap.remove(var1, var3);
            }

            return true;
         }
      }
   }

   public int setCount(E var1, int var2) {
      Preconditions.checkNotNull(var1);
      CollectPreconditions.checkNonnegative(var2, "count");

      AtomicInteger var3;
      AtomicInteger var4;
      label39:
      do {
         var3 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
         var4 = var3;
         if (var3 == null) {
            if (var2 == 0) {
               return 0;
            }

            var3 = (AtomicInteger)this.countMap.putIfAbsent(var1, new AtomicInteger(var2));
            var4 = var3;
            if (var3 == null) {
               return 0;
            }
         }

         int var5;
         do {
            var5 = var4.get();
            if (var5 == 0) {
               if (var2 == 0) {
                  return 0;
               }

               var3 = new AtomicInteger(var2);
               continue label39;
            }
         } while(!var4.compareAndSet(var5, var2));

         if (var2 == 0) {
            this.countMap.remove(var1, var4);
         }

         return var5;
      } while(this.countMap.putIfAbsent(var1, var3) != null && !this.countMap.replace(var1, var4, var3));

      return 0;
   }

   public boolean setCount(E var1, int var2, int var3) {
      Preconditions.checkNotNull(var1);
      CollectPreconditions.checkNonnegative(var2, "oldCount");
      CollectPreconditions.checkNonnegative(var3, "newCount");
      AtomicInteger var4 = (AtomicInteger)Maps.safeGet(this.countMap, var1);
      boolean var5 = false;
      boolean var6 = false;
      if (var4 == null) {
         if (var2 != 0) {
            return false;
         } else if (var3 == 0) {
            return true;
         } else {
            if (this.countMap.putIfAbsent(var1, new AtomicInteger(var3)) == null) {
               var6 = true;
            }

            return var6;
         }
      } else {
         int var7 = var4.get();
         if (var7 == var2) {
            if (var7 == 0) {
               if (var3 == 0) {
                  this.countMap.remove(var1, var4);
                  return true;
               }

               AtomicInteger var8 = new AtomicInteger(var3);
               if (this.countMap.putIfAbsent(var1, var8) != null) {
                  var6 = var5;
                  if (!this.countMap.replace(var1, var4, var8)) {
                     return var6;
                  }
               }

               var6 = true;
               return var6;
            }

            if (var4.compareAndSet(var7, var3)) {
               if (var3 == 0) {
                  this.countMap.remove(var1, var4);
               }

               return true;
            }
         }

         return false;
      }
   }

   public int size() {
      Iterator var1 = this.countMap.values().iterator();

      long var2;
      for(var2 = 0L; var1.hasNext(); var2 += (long)((AtomicInteger)var1.next()).get()) {
      }

      return Ints.saturatedCast(var2);
   }

   public Object[] toArray() {
      return this.snapshot().toArray();
   }

   public <T> T[] toArray(T[] var1) {
      return this.snapshot().toArray(var1);
   }

   private class EntrySet extends AbstractMultiset<E>.EntrySet {
      private EntrySet() {
         super();
      }

      // $FF: synthetic method
      EntrySet(Object var2) {
         this();
      }

      private List<Multiset.Entry<E>> snapshot() {
         ArrayList var1 = Lists.newArrayListWithExpectedSize(this.size());
         Iterators.addAll(var1, this.iterator());
         return var1;
      }

      ConcurrentHashMultiset<E> multiset() {
         return ConcurrentHashMultiset.this;
      }

      public Object[] toArray() {
         return this.snapshot().toArray();
      }

      public <T> T[] toArray(T[] var1) {
         return this.snapshot().toArray(var1);
      }
   }

   private static class FieldSettersHolder {
      static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
   }
}
