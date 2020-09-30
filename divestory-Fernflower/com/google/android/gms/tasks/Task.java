package com.google.android.gms.tasks;

import android.app.Activity;
import java.util.concurrent.Executor;

public abstract class Task<TResult> {
   public Task<TResult> addOnCanceledListener(Activity var1, OnCanceledListener var2) {
      throw new UnsupportedOperationException("addOnCanceledListener is not implemented.");
   }

   public Task<TResult> addOnCanceledListener(OnCanceledListener var1) {
      throw new UnsupportedOperationException("addOnCanceledListener is not implemented.");
   }

   public Task<TResult> addOnCanceledListener(Executor var1, OnCanceledListener var2) {
      throw new UnsupportedOperationException("addOnCanceledListener is not implemented");
   }

   public Task<TResult> addOnCompleteListener(Activity var1, OnCompleteListener<TResult> var2) {
      throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
   }

   public Task<TResult> addOnCompleteListener(OnCompleteListener<TResult> var1) {
      throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
   }

   public Task<TResult> addOnCompleteListener(Executor var1, OnCompleteListener<TResult> var2) {
      throw new UnsupportedOperationException("addOnCompleteListener is not implemented");
   }

   public abstract Task<TResult> addOnFailureListener(Activity var1, OnFailureListener var2);

   public abstract Task<TResult> addOnFailureListener(OnFailureListener var1);

   public abstract Task<TResult> addOnFailureListener(Executor var1, OnFailureListener var2);

   public abstract Task<TResult> addOnSuccessListener(Activity var1, OnSuccessListener<? super TResult> var2);

   public abstract Task<TResult> addOnSuccessListener(OnSuccessListener<? super TResult> var1);

   public abstract Task<TResult> addOnSuccessListener(Executor var1, OnSuccessListener<? super TResult> var2);

   public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> var1) {
      throw new UnsupportedOperationException("continueWith is not implemented");
   }

   public <TContinuationResult> Task<TContinuationResult> continueWith(Executor var1, Continuation<TResult, TContinuationResult> var2) {
      throw new UnsupportedOperationException("continueWith is not implemented");
   }

   public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> var1) {
      throw new UnsupportedOperationException("continueWithTask is not implemented");
   }

   public <TContinuationResult> Task<TContinuationResult> continueWithTask(Executor var1, Continuation<TResult, Task<TContinuationResult>> var2) {
      throw new UnsupportedOperationException("continueWithTask is not implemented");
   }

   public abstract Exception getException();

   public abstract TResult getResult();

   public abstract <X extends Throwable> TResult getResult(Class<X> var1) throws X;

   public abstract boolean isCanceled();

   public abstract boolean isComplete();

   public abstract boolean isSuccessful();

   public <TContinuationResult> Task<TContinuationResult> onSuccessTask(SuccessContinuation<TResult, TContinuationResult> var1) {
      throw new UnsupportedOperationException("onSuccessTask is not implemented");
   }

   public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor var1, SuccessContinuation<TResult, TContinuationResult> var2) {
      throw new UnsupportedOperationException("onSuccessTask is not implemented");
   }
}
