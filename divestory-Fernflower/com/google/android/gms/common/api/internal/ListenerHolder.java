package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.Preconditions;

public final class ListenerHolder<L> {
   private final ListenerHolder.zaa zaa;
   private volatile L zab;
   private volatile ListenerHolder.ListenerKey<L> zac;

   ListenerHolder(Looper var1, L var2, String var3) {
      this.zaa = new ListenerHolder.zaa(var1);
      this.zab = Preconditions.checkNotNull(var2, "Listener must not be null");
      this.zac = new ListenerHolder.ListenerKey(var2, Preconditions.checkNotEmpty(var3));
   }

   public final void clear() {
      this.zab = null;
      this.zac = null;
   }

   public final ListenerHolder.ListenerKey<L> getListenerKey() {
      return this.zac;
   }

   public final boolean hasListener() {
      return this.zab != null;
   }

   public final void notifyListener(ListenerHolder.Notifier<? super L> var1) {
      Preconditions.checkNotNull(var1, "Notifier must not be null");
      Message var2 = this.zaa.obtainMessage(1, var1);
      this.zaa.sendMessage(var2);
   }

   final void notifyListenerInternal(ListenerHolder.Notifier<? super L> var1) {
      Object var2 = this.zab;
      if (var2 == null) {
         var1.onNotifyListenerFailed();
      } else {
         try {
            var1.notifyListener(var2);
         } catch (RuntimeException var3) {
            var1.onNotifyListenerFailed();
            throw var3;
         }
      }
   }

   public static final class ListenerKey<L> {
      private final L zaa;
      private final String zab;

      ListenerKey(L var1, String var2) {
         this.zaa = var1;
         this.zab = var2;
      }

      public final boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ListenerHolder.ListenerKey)) {
            return false;
         } else {
            ListenerHolder.ListenerKey var2 = (ListenerHolder.ListenerKey)var1;
            return this.zaa == var2.zaa && this.zab.equals(var2.zab);
         }
      }

      public final int hashCode() {
         return System.identityHashCode(this.zaa) * 31 + this.zab.hashCode();
      }
   }

   public interface Notifier<L> {
      void notifyListener(L var1);

      void onNotifyListenerFailed();
   }

   private final class zaa extends com.google.android.gms.internal.base.zap {
      public zaa(Looper var2) {
         super(var2);
      }

      public final void handleMessage(Message var1) {
         int var2 = var1.what;
         boolean var3 = true;
         if (var2 != 1) {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         ListenerHolder.Notifier var4 = (ListenerHolder.Notifier)var1.obj;
         ListenerHolder.this.notifyListenerInternal(var4);
      }
   }
}
