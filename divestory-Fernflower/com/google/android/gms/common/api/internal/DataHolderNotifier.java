package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.data.DataHolder;

public abstract class DataHolderNotifier<L> implements ListenerHolder.Notifier<L> {
   private final DataHolder zaa;

   protected DataHolderNotifier(DataHolder var1) {
      this.zaa = var1;
   }

   public final void notifyListener(L var1) {
      this.notifyListener(var1, this.zaa);
   }

   protected abstract void notifyListener(L var1, DataHolder var2);

   public void onNotifyListenerFailed() {
      DataHolder var1 = this.zaa;
      if (var1 != null) {
         var1.close();
      }

   }
}
