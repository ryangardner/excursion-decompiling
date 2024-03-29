package com.google.android.gms.tasks;

public interface Continuation<TResult, TContinuationResult> {
   TContinuationResult then(Task<TResult> var1) throws Exception;
}
