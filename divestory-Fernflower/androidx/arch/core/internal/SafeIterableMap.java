package androidx.arch.core.internal;

import java.util.Iterator;
import java.util.WeakHashMap;

public class SafeIterableMap<K, V> implements Iterable<java.util.Map.Entry<K, V>> {
   private SafeIterableMap.Entry<K, V> mEnd;
   private WeakHashMap<SafeIterableMap.SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap();
   private int mSize = 0;
   SafeIterableMap.Entry<K, V> mStart;

   public Iterator<java.util.Map.Entry<K, V>> descendingIterator() {
      SafeIterableMap.DescendingIterator var1 = new SafeIterableMap.DescendingIterator(this.mEnd, this.mStart);
      this.mIterators.put(var1, false);
      return var1;
   }

   public java.util.Map.Entry<K, V> eldest() {
      return this.mStart;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SafeIterableMap)) {
         return false;
      } else {
         SafeIterableMap var3 = (SafeIterableMap)var1;
         if (this.size() != var3.size()) {
            return false;
         } else {
            Iterator var6 = this.iterator();
            Iterator var4 = var3.iterator();

            while(true) {
               if (var6.hasNext() && var4.hasNext()) {
                  java.util.Map.Entry var7 = (java.util.Map.Entry)var6.next();
                  Object var5 = var4.next();
                  if ((var7 != null || var5 == null) && (var7 == null || var7.equals(var5))) {
                     continue;
                  }

                  return false;
               }

               if (var6.hasNext() || var4.hasNext()) {
                  var2 = false;
               }

               return var2;
            }
         }
      }
   }

   protected SafeIterableMap.Entry<K, V> get(K var1) {
      SafeIterableMap.Entry var2;
      for(var2 = this.mStart; var2 != null && !var2.mKey.equals(var1); var2 = var2.mNext) {
      }

      return var2;
   }

   public int hashCode() {
      Iterator var1 = this.iterator();

      int var2;
      for(var2 = 0; var1.hasNext(); var2 += ((java.util.Map.Entry)var1.next()).hashCode()) {
      }

      return var2;
   }

   public Iterator<java.util.Map.Entry<K, V>> iterator() {
      SafeIterableMap.AscendingIterator var1 = new SafeIterableMap.AscendingIterator(this.mStart, this.mEnd);
      this.mIterators.put(var1, false);
      return var1;
   }

   public SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions() {
      SafeIterableMap.IteratorWithAdditions var1 = new SafeIterableMap.IteratorWithAdditions();
      this.mIterators.put(var1, false);
      return var1;
   }

   public java.util.Map.Entry<K, V> newest() {
      return this.mEnd;
   }

   protected SafeIterableMap.Entry<K, V> put(K var1, V var2) {
      SafeIterableMap.Entry var3 = new SafeIterableMap.Entry(var1, var2);
      ++this.mSize;
      SafeIterableMap.Entry var4 = this.mEnd;
      if (var4 == null) {
         this.mStart = var3;
         this.mEnd = var3;
         return var3;
      } else {
         var4.mNext = var3;
         var3.mPrevious = this.mEnd;
         this.mEnd = var3;
         return var3;
      }
   }

   public V putIfAbsent(K var1, V var2) {
      SafeIterableMap.Entry var3 = this.get(var1);
      if (var3 != null) {
         return var3.mValue;
      } else {
         this.put(var1, var2);
         return null;
      }
   }

   public V remove(K var1) {
      SafeIterableMap.Entry var3 = this.get(var1);
      if (var3 == null) {
         return null;
      } else {
         --this.mSize;
         if (!this.mIterators.isEmpty()) {
            Iterator var2 = this.mIterators.keySet().iterator();

            while(var2.hasNext()) {
               ((SafeIterableMap.SupportRemove)var2.next()).supportRemove(var3);
            }
         }

         if (var3.mPrevious != null) {
            var3.mPrevious.mNext = var3.mNext;
         } else {
            this.mStart = var3.mNext;
         }

         if (var3.mNext != null) {
            var3.mNext.mPrevious = var3.mPrevious;
         } else {
            this.mEnd = var3.mPrevious;
         }

         var3.mNext = null;
         var3.mPrevious = null;
         return var3.mValue;
      }
   }

   public int size() {
      return this.mSize;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[");
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         var1.append(((java.util.Map.Entry)var2.next()).toString());
         if (var2.hasNext()) {
            var1.append(", ");
         }
      }

      var1.append("]");
      return var1.toString();
   }

   static class AscendingIterator<K, V> extends SafeIterableMap.ListIterator<K, V> {
      AscendingIterator(SafeIterableMap.Entry<K, V> var1, SafeIterableMap.Entry<K, V> var2) {
         super(var1, var2);
      }

      SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> var1) {
         return var1.mPrevious;
      }

      SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> var1) {
         return var1.mNext;
      }
   }

   private static class DescendingIterator<K, V> extends SafeIterableMap.ListIterator<K, V> {
      DescendingIterator(SafeIterableMap.Entry<K, V> var1, SafeIterableMap.Entry<K, V> var2) {
         super(var1, var2);
      }

      SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> var1) {
         return var1.mNext;
      }

      SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> var1) {
         return var1.mPrevious;
      }
   }

   static class Entry<K, V> implements java.util.Map.Entry<K, V> {
      final K mKey;
      SafeIterableMap.Entry<K, V> mNext;
      SafeIterableMap.Entry<K, V> mPrevious;
      final V mValue;

      Entry(K var1, V var2) {
         this.mKey = var1;
         this.mValue = var2;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof SafeIterableMap.Entry)) {
            return false;
         } else {
            SafeIterableMap.Entry var3 = (SafeIterableMap.Entry)var1;
            if (!this.mKey.equals(var3.mKey) || !this.mValue.equals(var3.mValue)) {
               var2 = false;
            }

            return var2;
         }
      }

      public K getKey() {
         return this.mKey;
      }

      public V getValue() {
         return this.mValue;
      }

      public int hashCode() {
         return this.mKey.hashCode() ^ this.mValue.hashCode();
      }

      public V setValue(V var1) {
         throw new UnsupportedOperationException("An entry modification is not supported");
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.mKey);
         var1.append("=");
         var1.append(this.mValue);
         return var1.toString();
      }
   }

   private class IteratorWithAdditions implements Iterator<java.util.Map.Entry<K, V>>, SafeIterableMap.SupportRemove<K, V> {
      private boolean mBeforeStart = true;
      private SafeIterableMap.Entry<K, V> mCurrent;

      IteratorWithAdditions() {
      }

      public boolean hasNext() {
         boolean var1 = this.mBeforeStart;
         boolean var2 = true;
         boolean var3 = true;
         if (var1) {
            if (SafeIterableMap.this.mStart == null) {
               var3 = false;
            }

            return var3;
         } else {
            SafeIterableMap.Entry var4 = this.mCurrent;
            if (var4 != null && var4.mNext != null) {
               var3 = var2;
            } else {
               var3 = false;
            }

            return var3;
         }
      }

      public java.util.Map.Entry<K, V> next() {
         if (this.mBeforeStart) {
            this.mBeforeStart = false;
            this.mCurrent = SafeIterableMap.this.mStart;
         } else {
            SafeIterableMap.Entry var1 = this.mCurrent;
            if (var1 != null) {
               var1 = var1.mNext;
            } else {
               var1 = null;
            }

            this.mCurrent = var1;
         }

         return this.mCurrent;
      }

      public void supportRemove(SafeIterableMap.Entry<K, V> var1) {
         SafeIterableMap.Entry var2 = this.mCurrent;
         if (var1 == var2) {
            var1 = var2.mPrevious;
            this.mCurrent = var1;
            boolean var3;
            if (var1 == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            this.mBeforeStart = var3;
         }

      }
   }

   private abstract static class ListIterator<K, V> implements Iterator<java.util.Map.Entry<K, V>>, SafeIterableMap.SupportRemove<K, V> {
      SafeIterableMap.Entry<K, V> mExpectedEnd;
      SafeIterableMap.Entry<K, V> mNext;

      ListIterator(SafeIterableMap.Entry<K, V> var1, SafeIterableMap.Entry<K, V> var2) {
         this.mExpectedEnd = var2;
         this.mNext = var1;
      }

      private SafeIterableMap.Entry<K, V> nextNode() {
         SafeIterableMap.Entry var1 = this.mNext;
         SafeIterableMap.Entry var2 = this.mExpectedEnd;
         return var1 != var2 && var2 != null ? this.forward(var1) : null;
      }

      abstract SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> var1);

      abstract SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> var1);

      public boolean hasNext() {
         boolean var1;
         if (this.mNext != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public java.util.Map.Entry<K, V> next() {
         SafeIterableMap.Entry var1 = this.mNext;
         this.mNext = this.nextNode();
         return var1;
      }

      public void supportRemove(SafeIterableMap.Entry<K, V> var1) {
         if (this.mExpectedEnd == var1 && var1 == this.mNext) {
            this.mNext = null;
            this.mExpectedEnd = null;
         }

         SafeIterableMap.Entry var2 = this.mExpectedEnd;
         if (var2 == var1) {
            this.mExpectedEnd = this.backward(var2);
         }

         if (this.mNext == var1) {
            this.mNext = this.nextNode();
         }

      }
   }

   interface SupportRemove<K, V> {
      void supportRemove(SafeIterableMap.Entry<K, V> var1);
   }
}
