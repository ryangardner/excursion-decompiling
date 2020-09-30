package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V> {
   protected ForwardingSortedMap() {
   }

   private int unsafeCompare(Object var1, Object var2) {
      Comparator var3 = this.comparator();
      return var3 == null ? ((Comparable)var1).compareTo(var2) : var3.compare(var1, var2);
   }

   public Comparator<? super K> comparator() {
      return this.delegate().comparator();
   }

   protected abstract SortedMap<K, V> delegate();

   public K firstKey() {
      return this.delegate().firstKey();
   }

   public SortedMap<K, V> headMap(K var1) {
      return this.delegate().headMap(var1);
   }

   public K lastKey() {
      return this.delegate().lastKey();
   }

   protected boolean standardContainsKey(@NullableDecl Object var1) {
      boolean var2 = false;

      int var3;
      try {
         var3 = this.unsafeCompare(this.tailMap(var1).firstKey(), var1);
      } catch (NoSuchElementException | NullPointerException | ClassCastException var4) {
         return var2;
      }

      if (var3 == 0) {
         var2 = true;
      }

      return var2;
   }

   protected SortedMap<K, V> standardSubMap(K var1, K var2) {
      boolean var3;
      if (this.unsafeCompare(var1, var2) <= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "fromKey must be <= toKey");
      return this.tailMap(var1).headMap(var2);
   }

   public SortedMap<K, V> subMap(K var1, K var2) {
      return this.delegate().subMap(var1, var2);
   }

   public SortedMap<K, V> tailMap(K var1) {
      return this.delegate().tailMap(var1);
   }

   protected class StandardKeySet extends Maps.SortedKeySet<K, V> {
      public StandardKeySet() {
         super(ForwardingSortedMap.this);
      }
   }
}
