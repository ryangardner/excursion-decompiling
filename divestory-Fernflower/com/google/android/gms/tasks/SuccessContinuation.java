package com.google.android.gms.tasks;

public interface SuccessContinuation<TResult, TContinuationResult> {
   Task<TContinuationResult> then(TResult var1) throws Exception;
}
