package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class TaskUtil {
   public static void setResultOrApiException(Status var0, TaskCompletionSource<Void> var1) {
      setResultOrApiException(var0, (Object)null, var1);
   }

   public static <TResult> void setResultOrApiException(Status var0, TResult var1, TaskCompletionSource<TResult> var2) {
      if (var0.isSuccess()) {
         var2.setResult(var1);
      } else {
         var2.setException(new ApiException(var0));
      }
   }

   @Deprecated
   public static Task<Void> toVoidTaskThatFailsOnFalse(Task<Boolean> var0) {
      return var0.continueWith(new zaci());
   }

   public static <ResultT> boolean trySetResultOrApiException(Status var0, ResultT var1, TaskCompletionSource<ResultT> var2) {
      return var0.isSuccess() ? var2.trySetResult(var1) : var2.trySetException(new ApiException(var0));
   }
}
