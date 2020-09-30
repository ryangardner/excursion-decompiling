package com.google.android.gms.common.api.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zaj {
   private final ArrayMap<ApiKey<?>, ConnectionResult> zaa = new ArrayMap();
   private final ArrayMap<ApiKey<?>, String> zab = new ArrayMap();
   private final TaskCompletionSource<Map<ApiKey<?>, String>> zac = new TaskCompletionSource();
   private int zad;
   private boolean zae = false;

   public zaj(Iterable<? extends HasApiKey<?>> var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         HasApiKey var2 = (HasApiKey)var3.next();
         this.zaa.put(var2.getApiKey(), (Object)null);
      }

      this.zad = this.zaa.keySet().size();
   }

   public final Set<ApiKey<?>> zaa() {
      return this.zaa.keySet();
   }

   public final void zaa(ApiKey<?> var1, ConnectionResult var2, String var3) {
      this.zaa.put(var1, var2);
      this.zab.put(var1, var3);
      --this.zad;
      if (!var2.isSuccess()) {
         this.zae = true;
      }

      if (this.zad == 0) {
         if (this.zae) {
            AvailabilityException var4 = new AvailabilityException(this.zaa);
            this.zac.setException(var4);
            return;
         }

         this.zac.setResult(this.zab);
      }

   }

   public final Task<Map<ApiKey<?>, String>> zab() {
      return this.zac.getTask();
   }
}
