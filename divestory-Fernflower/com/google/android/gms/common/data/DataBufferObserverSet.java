package com.google.android.gms.common.data;

import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet implements DataBufferObserver, DataBufferObserver.Observable {
   private HashSet<DataBufferObserver> zaa = new HashSet();

   public final void addObserver(DataBufferObserver var1) {
      this.zaa.add(var1);
   }

   public final void clear() {
      this.zaa.clear();
   }

   public final boolean hasObservers() {
      return !this.zaa.isEmpty();
   }

   public final void onDataChanged() {
      Iterator var1 = this.zaa.iterator();

      while(var1.hasNext()) {
         ((DataBufferObserver)var1.next()).onDataChanged();
      }

   }

   public final void onDataRangeChanged(int var1, int var2) {
      Iterator var3 = this.zaa.iterator();

      while(var3.hasNext()) {
         ((DataBufferObserver)var3.next()).onDataRangeChanged(var1, var2);
      }

   }

   public final void onDataRangeInserted(int var1, int var2) {
      Iterator var3 = this.zaa.iterator();

      while(var3.hasNext()) {
         ((DataBufferObserver)var3.next()).onDataRangeInserted(var1, var2);
      }

   }

   public final void onDataRangeMoved(int var1, int var2, int var3) {
      Iterator var4 = this.zaa.iterator();

      while(var4.hasNext()) {
         ((DataBufferObserver)var4.next()).onDataRangeMoved(var1, var2, var3);
      }

   }

   public final void onDataRangeRemoved(int var1, int var2) {
      Iterator var3 = this.zaa.iterator();

      while(var3.hasNext()) {
         ((DataBufferObserver)var3.next()).onDataRangeRemoved(var1, var2);
      }

   }

   public final void removeObserver(DataBufferObserver var1) {
      this.zaa.remove(var1);
   }
}
