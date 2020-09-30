package com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class ForwardingTable<R, C, V> extends ForwardingObject implements Table<R, C, V> {
   protected ForwardingTable() {
   }

   public Set<Table.Cell<R, C, V>> cellSet() {
      return this.delegate().cellSet();
   }

   public void clear() {
      this.delegate().clear();
   }

   public Map<R, V> column(C var1) {
      return this.delegate().column(var1);
   }

   public Set<C> columnKeySet() {
      return this.delegate().columnKeySet();
   }

   public Map<C, Map<R, V>> columnMap() {
      return this.delegate().columnMap();
   }

   public boolean contains(Object var1, Object var2) {
      return this.delegate().contains(var1, var2);
   }

   public boolean containsColumn(Object var1) {
      return this.delegate().containsColumn(var1);
   }

   public boolean containsRow(Object var1) {
      return this.delegate().containsRow(var1);
   }

   public boolean containsValue(Object var1) {
      return this.delegate().containsValue(var1);
   }

   protected abstract Table<R, C, V> delegate();

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public V get(Object var1, Object var2) {
      return this.delegate().get(var1, var2);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public V put(R var1, C var2, V var3) {
      return this.delegate().put(var1, var2, var3);
   }

   public void putAll(Table<? extends R, ? extends C, ? extends V> var1) {
      this.delegate().putAll(var1);
   }

   public V remove(Object var1, Object var2) {
      return this.delegate().remove(var1, var2);
   }

   public Map<C, V> row(R var1) {
      return this.delegate().row(var1);
   }

   public Set<R> rowKeySet() {
      return this.delegate().rowKeySet();
   }

   public Map<R, Map<C, V>> rowMap() {
      return this.delegate().rowMap();
   }

   public int size() {
      return this.delegate().size();
   }

   public Collection<V> values() {
      return this.delegate().values();
   }
}
