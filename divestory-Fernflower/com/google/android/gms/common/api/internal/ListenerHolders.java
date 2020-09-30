package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class ListenerHolders {
   private final Set<ListenerHolder<?>> zaa = Collections.newSetFromMap(new WeakHashMap());

   public static <L> ListenerHolder<L> createListenerHolder(L var0, Looper var1, String var2) {
      Preconditions.checkNotNull(var0, "Listener must not be null");
      Preconditions.checkNotNull(var1, "Looper must not be null");
      Preconditions.checkNotNull(var2, "Listener type must not be null");
      return new ListenerHolder(var1, var0, var2);
   }

   public static <L> ListenerHolder.ListenerKey<L> createListenerKey(L var0, String var1) {
      Preconditions.checkNotNull(var0, "Listener must not be null");
      Preconditions.checkNotNull(var1, "Listener type must not be null");
      Preconditions.checkNotEmpty(var1, "Listener type must not be empty");
      return new ListenerHolder.ListenerKey(var0, var1);
   }

   public final <L> ListenerHolder<L> zaa(L var1, Looper var2, String var3) {
      ListenerHolder var4 = createListenerHolder(var1, var2, var3);
      this.zaa.add(var4);
      return var4;
   }

   public final void zaa() {
      Iterator var1 = this.zaa.iterator();

      while(var1.hasNext()) {
         ((ListenerHolder)var1.next()).clear();
      }

      this.zaa.clear();
   }
}
