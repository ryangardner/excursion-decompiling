package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class PendingResultUtil {
   private static final PendingResultUtil.zaa zaa = new zan();

   public static <R extends Result, T extends Response<R>> Task<T> toResponseTask(PendingResult<R> var0, T var1) {
      return toTask(var0, new zao(var1));
   }

   public static <R extends Result, T> Task<T> toTask(PendingResult<R> var0, PendingResultUtil.ResultConverter<R, T> var1) {
      PendingResultUtil.zaa var2 = zaa;
      TaskCompletionSource var3 = new TaskCompletionSource();
      var0.addStatusListener(new zap(var0, var3, var1, var2));
      return var3.getTask();
   }

   public static <R extends Result> Task<Void> toVoidTask(PendingResult<R> var0) {
      return toTask(var0, new zaq());
   }

   public interface ResultConverter<R extends Result, T> {
      T convert(R var1);
   }

   public interface zaa {
      ApiException zaa(Status var1);
   }
}
