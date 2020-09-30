package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ListenableFutureTask<V> extends FutureTask<V> implements ListenableFuture<V> {
   private final ExecutionList executionList = new ExecutionList();

   ListenableFutureTask(Runnable var1, @NullableDecl V var2) {
      super(var1, var2);
   }

   ListenableFutureTask(Callable<V> var1) {
      super(var1);
   }

   public static <V> ListenableFutureTask<V> create(Runnable var0, @NullableDecl V var1) {
      return new ListenableFutureTask(var0, var1);
   }

   public static <V> ListenableFutureTask<V> create(Callable<V> var0) {
      return new ListenableFutureTask(var0);
   }

   public void addListener(Runnable var1, Executor var2) {
      this.executionList.add(var1, var2);
   }

   protected void done() {
      this.executionList.execute();
   }
}
