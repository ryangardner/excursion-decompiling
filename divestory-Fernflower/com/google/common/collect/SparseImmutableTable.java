package com.google.common.collect;

import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

@Immutable(
   containerOf = {"R", "C", "V"}
)
final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
   static final ImmutableTable<Object, Object, Object> EMPTY = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
   private final int[] cellColumnInRowIndices;
   private final int[] cellRowIndices;
   private final ImmutableMap<C, ImmutableMap<R, V>> columnMap;
   private final ImmutableMap<R, ImmutableMap<C, V>> rowMap;

   SparseImmutableTable(ImmutableList<Table.Cell<R, C, V>> var1, ImmutableSet<R> var2, ImmutableSet<C> var3) {
      ImmutableMap var4 = Maps.indexMap(var2);
      LinkedHashMap var5 = Maps.newLinkedHashMap();
      UnmodifiableIterator var13 = var2.iterator();

      while(var13.hasNext()) {
         var5.put(var13.next(), new LinkedHashMap());
      }

      LinkedHashMap var14 = Maps.newLinkedHashMap();
      UnmodifiableIterator var16 = var3.iterator();

      while(var16.hasNext()) {
         var14.put(var16.next(), new LinkedHashMap());
      }

      int[] var6 = new int[var1.size()];
      int[] var7 = new int[var1.size()];

      for(int var8 = 0; var8 < var1.size(); ++var8) {
         Table.Cell var9 = (Table.Cell)var1.get(var8);
         Object var17 = var9.getRowKey();
         Object var10 = var9.getColumnKey();
         Object var11 = var9.getValue();
         var6[var8] = (Integer)var4.get(var17);
         Map var21 = (Map)var5.get(var17);
         var7[var8] = var21.size();
         this.checkNoDuplicate(var17, var10, var21.put(var10, var11), var11);
         ((Map)var14.get(var10)).put(var17, var11);
      }

      this.cellRowIndices = var6;
      this.cellColumnInRowIndices = var7;
      ImmutableMap.Builder var12 = new ImmutableMap.Builder(var5.size());
      Iterator var18 = var5.entrySet().iterator();

      while(var18.hasNext()) {
         Entry var20 = (Entry)var18.next();
         var12.put(var20.getKey(), ImmutableMap.copyOf((Map)var20.getValue()));
      }

      this.rowMap = var12.build();
      var12 = new ImmutableMap.Builder(var14.size());
      Iterator var15 = var14.entrySet().iterator();

      while(var15.hasNext()) {
         Entry var19 = (Entry)var15.next();
         var12.put(var19.getKey(), ImmutableMap.copyOf((Map)var19.getValue()));
      }

      this.columnMap = var12.build();
   }

   public ImmutableMap<C, Map<R, V>> columnMap() {
      return ImmutableMap.copyOf((Map)this.columnMap);
   }

   ImmutableTable.SerializedForm createSerializedForm() {
      ImmutableMap var1 = Maps.indexMap(this.columnKeySet());
      int[] var2 = new int[this.cellSet().size()];
      UnmodifiableIterator var3 = this.cellSet().iterator();

      for(int var4 = 0; var3.hasNext(); ++var4) {
         var2[var4] = (Integer)var1.get(((Table.Cell)var3.next()).getColumnKey());
      }

      return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, var2);
   }

   Table.Cell<R, C, V> getCell(int var1) {
      int var2 = this.cellRowIndices[var1];
      Entry var3 = (Entry)this.rowMap.entrySet().asList().get(var2);
      ImmutableMap var4 = (ImmutableMap)var3.getValue();
      var1 = this.cellColumnInRowIndices[var1];
      Entry var5 = (Entry)var4.entrySet().asList().get(var1);
      return cellOf(var3.getKey(), var5.getKey(), var5.getValue());
   }

   V getValue(int var1) {
      int var2 = this.cellRowIndices[var1];
      ImmutableMap var3 = (ImmutableMap)this.rowMap.values().asList().get(var2);
      var1 = this.cellColumnInRowIndices[var1];
      return var3.values().asList().get(var1);
   }

   public ImmutableMap<R, Map<C, V>> rowMap() {
      return ImmutableMap.copyOf((Map)this.rowMap);
   }

   public int size() {
      return this.cellRowIndices.length;
   }
}
