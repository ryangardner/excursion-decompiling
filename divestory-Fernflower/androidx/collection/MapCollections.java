package androidx.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

abstract class MapCollections<K, V> {
   MapCollections<K, V>.EntrySet mEntrySet;
   MapCollections<K, V>.KeySet mKeySet;
   MapCollections<K, V>.ValuesCollection mValues;

   public static <K, V> boolean containsAllHelper(Map<K, V> var0, Collection<?> var1) {
      Iterator var2 = var1.iterator();

      do {
         if (!var2.hasNext()) {
            return true;
         }
      } while(var0.containsKey(var2.next()));

      return false;
   }

   public static <T> boolean equalsSetHelper(Set<T> var0, Object var1) {
      boolean var2 = true;
      if (var0 == var1) {
         return true;
      } else if (var1 instanceof Set) {
         Set var5 = (Set)var1;

         label27: {
            boolean var3;
            try {
               if (var0.size() != var5.size()) {
                  break label27;
               }

               var3 = var0.containsAll(var5);
            } catch (ClassCastException | NullPointerException var4) {
               return false;
            }

            if (var3) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      } else {
         return false;
      }
   }

   public static <K, V> boolean removeAllHelper(Map<K, V> var0, Collection<?> var1) {
      int var2 = var0.size();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         var0.remove(var4.next());
      }

      boolean var3;
      if (var2 != var0.size()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static <K, V> boolean retainAllHelper(Map<K, V> var0, Collection<?> var1) {
      int var2 = var0.size();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         if (!var1.contains(var3.next())) {
            var3.remove();
         }
      }

      boolean var4;
      if (var2 != var0.size()) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   protected abstract void colClear();

   protected abstract Object colGetEntry(int var1, int var2);

   protected abstract Map<K, V> colGetMap();

   protected abstract int colGetSize();

   protected abstract int colIndexOfKey(Object var1);

   protected abstract int colIndexOfValue(Object var1);

   protected abstract void colPut(K var1, V var2);

   protected abstract void colRemoveAt(int var1);

   protected abstract V colSetValue(int var1, V var2);

   public Set<Entry<K, V>> getEntrySet() {
      if (this.mEntrySet == null) {
         this.mEntrySet = new MapCollections.EntrySet();
      }

      return this.mEntrySet;
   }

   public Set<K> getKeySet() {
      if (this.mKeySet == null) {
         this.mKeySet = new MapCollections.KeySet();
      }

      return this.mKeySet;
   }

   public Collection<V> getValues() {
      if (this.mValues == null) {
         this.mValues = new MapCollections.ValuesCollection();
      }

      return this.mValues;
   }

   public Object[] toArrayHelper(int var1) {
      int var2 = this.colGetSize();
      Object[] var3 = new Object[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.colGetEntry(var4, var1);
      }

      return var3;
   }

   public <T> T[] toArrayHelper(T[] var1, int var2) {
      int var3 = this.colGetSize();
      Object[] var4 = var1;
      if (var1.length < var3) {
         var4 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var3);
      }

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = this.colGetEntry(var5, var2);
      }

      if (var4.length > var3) {
         var4[var3] = null;
      }

      return var4;
   }

   final class ArrayIterator<T> implements Iterator<T> {
      boolean mCanRemove = false;
      int mIndex;
      final int mOffset;
      int mSize;

      ArrayIterator(int var2) {
         this.mOffset = var2;
         this.mSize = MapCollections.this.colGetSize();
      }

      public boolean hasNext() {
         boolean var1;
         if (this.mIndex < this.mSize) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public T next() {
         if (this.hasNext()) {
            Object var1 = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
            ++this.mIndex;
            this.mCanRemove = true;
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         if (this.mCanRemove) {
            int var1 = this.mIndex - 1;
            this.mIndex = var1;
            --this.mSize;
            this.mCanRemove = false;
            MapCollections.this.colRemoveAt(var1);
         } else {
            throw new IllegalStateException();
         }
      }
   }

   final class EntrySet implements Set<Entry<K, V>> {
      public boolean add(Entry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends Entry<K, V>> var1) {
         int var2 = MapCollections.this.colGetSize();
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            Entry var3 = (Entry)var5.next();
            MapCollections.this.colPut(var3.getKey(), var3.getValue());
         }

         boolean var4;
         if (var2 != MapCollections.this.colGetSize()) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      public void clear() {
         MapCollections.this.colClear();
      }

      public boolean contains(Object var1) {
         if (!(var1 instanceof Entry)) {
            return false;
         } else {
            Entry var3 = (Entry)var1;
            int var2 = MapCollections.this.colIndexOfKey(var3.getKey());
            return var2 < 0 ? false : ContainerHelpers.equal(MapCollections.this.colGetEntry(var2, 1), var3.getValue());
         }
      }

      public boolean containsAll(Collection<?> var1) {
         Iterator var2 = var1.iterator();

         do {
            if (!var2.hasNext()) {
               return true;
            }
         } while(this.contains(var2.next()));

         return false;
      }

      public boolean equals(Object var1) {
         return MapCollections.equalsSetHelper(this, var1);
      }

      public int hashCode() {
         int var1 = MapCollections.this.colGetSize() - 1;

         int var2;
         for(var2 = 0; var1 >= 0; --var1) {
            Object var3 = MapCollections.this.colGetEntry(var1, 0);
            Object var4 = MapCollections.this.colGetEntry(var1, 1);
            int var5;
            if (var3 == null) {
               var5 = 0;
            } else {
               var5 = var3.hashCode();
            }

            int var6;
            if (var4 == null) {
               var6 = 0;
            } else {
               var6 = var4.hashCode();
            }

            var2 += var5 ^ var6;
         }

         return var2;
      }

      public boolean isEmpty() {
         boolean var1;
         if (MapCollections.this.colGetSize() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Iterator<Entry<K, V>> iterator() {
         return MapCollections.this.new MapIterator();
      }

      public boolean remove(Object var1) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return MapCollections.this.colGetSize();
      }

      public Object[] toArray() {
         throw new UnsupportedOperationException();
      }

      public <T> T[] toArray(T[] var1) {
         throw new UnsupportedOperationException();
      }
   }

   final class KeySet implements Set<K> {
      public boolean add(K var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends K> var1) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         MapCollections.this.colClear();
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (MapCollections.this.colIndexOfKey(var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean containsAll(Collection<?> var1) {
         return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), var1);
      }

      public boolean equals(Object var1) {
         return MapCollections.equalsSetHelper(this, var1);
      }

      public int hashCode() {
         int var1 = MapCollections.this.colGetSize() - 1;

         int var2;
         for(var2 = 0; var1 >= 0; --var1) {
            Object var3 = MapCollections.this.colGetEntry(var1, 0);
            int var4;
            if (var3 == null) {
               var4 = 0;
            } else {
               var4 = var3.hashCode();
            }

            var2 += var4;
         }

         return var2;
      }

      public boolean isEmpty() {
         boolean var1;
         if (MapCollections.this.colGetSize() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Iterator<K> iterator() {
         return MapCollections.this.new ArrayIterator(0);
      }

      public boolean remove(Object var1) {
         int var2 = MapCollections.this.colIndexOfKey(var1);
         if (var2 >= 0) {
            MapCollections.this.colRemoveAt(var2);
            return true;
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), var1);
      }

      public int size() {
         return MapCollections.this.colGetSize();
      }

      public Object[] toArray() {
         return MapCollections.this.toArrayHelper(0);
      }

      public <T> T[] toArray(T[] var1) {
         return MapCollections.this.toArrayHelper(var1, 0);
      }
   }

   final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
      int mEnd = MapCollections.this.colGetSize() - 1;
      boolean mEntryValid = false;
      int mIndex = -1;

      public boolean equals(Object var1) {
         if (this.mEntryValid) {
            boolean var2 = var1 instanceof Entry;
            boolean var3 = false;
            if (!var2) {
               return false;
            } else {
               Entry var4 = (Entry)var1;
               var2 = var3;
               if (ContainerHelpers.equal(var4.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))) {
                  var2 = var3;
                  if (ContainerHelpers.equal(var4.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1))) {
                     var2 = true;
                  }
               }

               return var2;
            }
         } else {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }
      }

      public K getKey() {
         if (this.mEntryValid) {
            return MapCollections.this.colGetEntry(this.mIndex, 0);
         } else {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }
      }

      public V getValue() {
         if (this.mEntryValid) {
            return MapCollections.this.colGetEntry(this.mIndex, 1);
         } else {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }
      }

      public boolean hasNext() {
         boolean var1;
         if (this.mIndex < this.mEnd) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public int hashCode() {
         if (this.mEntryValid) {
            MapCollections var1 = MapCollections.this;
            int var2 = this.mIndex;
            int var3 = 0;
            Object var5 = var1.colGetEntry(var2, 0);
            Object var4 = MapCollections.this.colGetEntry(this.mIndex, 1);
            if (var5 == null) {
               var2 = 0;
            } else {
               var2 = var5.hashCode();
            }

            if (var4 != null) {
               var3 = var4.hashCode();
            }

            return var2 ^ var3;
         } else {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }
      }

      public Entry<K, V> next() {
         if (this.hasNext()) {
            ++this.mIndex;
            this.mEntryValid = true;
            return this;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         if (this.mEntryValid) {
            MapCollections.this.colRemoveAt(this.mIndex);
            --this.mIndex;
            --this.mEnd;
            this.mEntryValid = false;
         } else {
            throw new IllegalStateException();
         }
      }

      public V setValue(V var1) {
         if (this.mEntryValid) {
            return MapCollections.this.colSetValue(this.mIndex, var1);
         } else {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.getKey());
         var1.append("=");
         var1.append(this.getValue());
         return var1.toString();
      }
   }

   final class ValuesCollection implements Collection<V> {
      public boolean add(V var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends V> var1) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         MapCollections.this.colClear();
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (MapCollections.this.colIndexOfValue(var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean containsAll(Collection<?> var1) {
         Iterator var2 = var1.iterator();

         do {
            if (!var2.hasNext()) {
               return true;
            }
         } while(this.contains(var2.next()));

         return false;
      }

      public boolean isEmpty() {
         boolean var1;
         if (MapCollections.this.colGetSize() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Iterator<V> iterator() {
         return MapCollections.this.new ArrayIterator(1);
      }

      public boolean remove(Object var1) {
         int var2 = MapCollections.this.colIndexOfValue(var1);
         if (var2 >= 0) {
            MapCollections.this.colRemoveAt(var2);
            return true;
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         int var2 = MapCollections.this.colGetSize();
         int var3 = 0;

         boolean var4;
         int var5;
         for(var4 = false; var3 < var2; var2 = var5) {
            var5 = var2;
            int var6 = var3;
            if (var1.contains(MapCollections.this.colGetEntry(var3, 1))) {
               MapCollections.this.colRemoveAt(var3);
               var6 = var3 - 1;
               var5 = var2 - 1;
               var4 = true;
            }

            var3 = var6 + 1;
         }

         return var4;
      }

      public boolean retainAll(Collection<?> var1) {
         int var2 = MapCollections.this.colGetSize();
         int var3 = 0;

         boolean var4;
         int var5;
         for(var4 = false; var3 < var2; var2 = var5) {
            var5 = var2;
            int var6 = var3;
            if (!var1.contains(MapCollections.this.colGetEntry(var3, 1))) {
               MapCollections.this.colRemoveAt(var3);
               var6 = var3 - 1;
               var5 = var2 - 1;
               var4 = true;
            }

            var3 = var6 + 1;
         }

         return var4;
      }

      public int size() {
         return MapCollections.this.colGetSize();
      }

      public Object[] toArray() {
         return MapCollections.this.toArrayHelper(1);
      }

      public <T> T[] toArray(T[] var1) {
         return MapCollections.this.toArrayHelper(var1, 1);
      }
   }
}
