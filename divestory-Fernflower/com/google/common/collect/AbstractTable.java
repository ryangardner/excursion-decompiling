package com.google.common.collect;

import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
   @LazyInit
   @MonotonicNonNullDecl
   private transient Set<Table.Cell<R, C, V>> cellSet;
   @LazyInit
   @MonotonicNonNullDecl
   private transient Collection<V> values;

   abstract Iterator<Table.Cell<R, C, V>> cellIterator();

   public Set<Table.Cell<R, C, V>> cellSet() {
      Set var1 = this.cellSet;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createCellSet();
         this.cellSet = var2;
      }

      return var2;
   }

   public void clear() {
      Iterators.clear(this.cellSet().iterator());
   }

   public Set<C> columnKeySet() {
      return this.columnMap().keySet();
   }

   public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2) {
      Map var4 = (Map)Maps.safeGet(this.rowMap(), var1);
      boolean var3;
      if (var4 != null && Maps.safeContainsKey(var4, var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsColumn(@NullableDecl Object var1) {
      return Maps.safeContainsKey(this.columnMap(), var1);
   }

   public boolean containsRow(@NullableDecl Object var1) {
      return Maps.safeContainsKey(this.rowMap(), var1);
   }

   public boolean containsValue(@NullableDecl Object var1) {
      Iterator var2 = this.rowMap().values().iterator();

      do {
         if (!var2.hasNext()) {
            return false;
         }
      } while(!((Map)var2.next()).containsValue(var1));

      return true;
   }

   Set<Table.Cell<R, C, V>> createCellSet() {
      return new AbstractTable.CellSet();
   }

   Collection<V> createValues() {
      return new AbstractTable.Values();
   }

   public boolean equals(@NullableDecl Object var1) {
      return Tables.equalsImpl(this, var1);
   }

   public V get(@NullableDecl Object var1, @NullableDecl Object var2) {
      Map var3 = (Map)Maps.safeGet(this.rowMap(), var1);
      if (var3 == null) {
         var1 = null;
      } else {
         var1 = Maps.safeGet(var3, var2);
      }

      return var1;
   }

   public int hashCode() {
      return this.cellSet().hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public V put(R var1, C var2, V var3) {
      return this.row(var1).put(var2, var3);
   }

   public void putAll(Table<? extends R, ? extends C, ? extends V> var1) {
      Iterator var3 = var1.cellSet().iterator();

      while(var3.hasNext()) {
         Table.Cell var2 = (Table.Cell)var3.next();
         this.put(var2.getRowKey(), var2.getColumnKey(), var2.getValue());
      }

   }

   public V remove(@NullableDecl Object var1, @NullableDecl Object var2) {
      Map var3 = (Map)Maps.safeGet(this.rowMap(), var1);
      if (var3 == null) {
         var1 = null;
      } else {
         var1 = Maps.safeRemove(var3, var2);
      }

      return var1;
   }

   public Set<R> rowKeySet() {
      return this.rowMap().keySet();
   }

   public String toString() {
      return this.rowMap().toString();
   }

   public Collection<V> values() {
      Collection var1 = this.values;
      Collection var2 = var1;
      if (var1 == null) {
         var2 = this.createValues();
         this.values = var2;
      }

      return var2;
   }

   Iterator<V> valuesIterator() {
      return new TransformedIterator<Table.Cell<R, C, V>, V>(this.cellSet().iterator()) {
         V transform(Table.Cell<R, C, V> var1) {
            return var1.getValue();
         }
      };
   }

   class CellSet extends AbstractSet<Table.Cell<R, C, V>> {
      public void clear() {
         AbstractTable.this.clear();
      }

      public boolean contains(Object var1) {
         boolean var2 = var1 instanceof Table.Cell;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Table.Cell var5 = (Table.Cell)var1;
            Map var6 = (Map)Maps.safeGet(AbstractTable.this.rowMap(), var5.getRowKey());
            var4 = var3;
            if (var6 != null) {
               var4 = var3;
               if (Collections2.safeContains(var6.entrySet(), Maps.immutableEntry(var5.getColumnKey(), var5.getValue()))) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public Iterator<Table.Cell<R, C, V>> iterator() {
         return AbstractTable.this.cellIterator();
      }

      public boolean remove(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Table.Cell;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Table.Cell var5 = (Table.Cell)var1;
            Map var6 = (Map)Maps.safeGet(AbstractTable.this.rowMap(), var5.getRowKey());
            var4 = var3;
            if (var6 != null) {
               var4 = var3;
               if (Collections2.safeRemove(var6.entrySet(), Maps.immutableEntry(var5.getColumnKey(), var5.getValue()))) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int size() {
         return AbstractTable.this.size();
      }
   }

   class Values extends AbstractCollection<V> {
      public void clear() {
         AbstractTable.this.clear();
      }

      public boolean contains(Object var1) {
         return AbstractTable.this.containsValue(var1);
      }

      public Iterator<V> iterator() {
         return AbstractTable.this.valuesIterator();
      }

      public int size() {
         return AbstractTable.this.size();
      }
   }
}
