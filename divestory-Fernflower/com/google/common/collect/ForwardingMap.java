package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {
   protected ForwardingMap() {
   }

   public void clear() {
      this.delegate().clear();
   }

   public boolean containsKey(@NullableDecl Object var1) {
      return this.delegate().containsKey(var1);
   }

   public boolean containsValue(@NullableDecl Object var1) {
      return this.delegate().containsValue(var1);
   }

   protected abstract Map<K, V> delegate();

   public Set<Entry<K, V>> entrySet() {
      return this.delegate().entrySet();
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public V get(@NullableDecl Object var1) {
      return this.delegate().get(var1);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public Set<K> keySet() {
      return this.delegate().keySet();
   }

   public V put(K var1, V var2) {
      return this.delegate().put(var1, var2);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      this.delegate().putAll(var1);
   }

   public V remove(Object var1) {
      return this.delegate().remove(var1);
   }

   public int size() {
      return this.delegate().size();
   }

   protected void standardClear() {
      Iterators.clear(this.entrySet().iterator());
   }

   protected boolean standardContainsKey(@NullableDecl Object var1) {
      return Maps.containsKeyImpl(this, var1);
   }

   protected boolean standardContainsValue(@NullableDecl Object var1) {
      return Maps.containsValueImpl(this, var1);
   }

   protected boolean standardEquals(@NullableDecl Object var1) {
      return Maps.equalsImpl(this, var1);
   }

   protected int standardHashCode() {
      return Sets.hashCodeImpl(this.entrySet());
   }

   protected boolean standardIsEmpty() {
      return this.entrySet().iterator().hasNext() ^ true;
   }

   protected void standardPutAll(Map<? extends K, ? extends V> var1) {
      Maps.putAllImpl(this, var1);
   }

   protected V standardRemove(@NullableDecl Object var1) {
      Iterator var2 = this.entrySet().iterator();

      Entry var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Entry)var2.next();
      } while(!Objects.equal(var3.getKey(), var1));

      var1 = var3.getValue();
      var2.remove();
      return var1;
   }

   protected String standardToString() {
      return Maps.toStringImpl(this);
   }

   public Collection<V> values() {
      return this.delegate().values();
   }

   protected abstract class StandardEntrySet extends Maps.EntrySet<K, V> {
      public StandardEntrySet() {
      }

      Map<K, V> map() {
         return ForwardingMap.this;
      }
   }

   protected class StandardKeySet extends Maps.KeySet<K, V> {
      public StandardKeySet() {
         super(ForwardingMap.this);
      }
   }

   protected class StandardValues extends Maps.Values<K, V> {
      public StandardValues() {
         super(ForwardingMap.this);
      }
   }
}
