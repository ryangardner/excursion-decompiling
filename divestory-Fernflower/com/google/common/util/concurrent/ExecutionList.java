package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ExecutionList {
   private static final Logger log = Logger.getLogger(ExecutionList.class.getName());
   private boolean executed;
   @NullableDecl
   private ExecutionList.RunnableExecutorPair runnables;

   private static void executeListener(Runnable var0, Executor var1) {
      try {
         var1.execute(var0);
      } catch (RuntimeException var6) {
         Logger var3 = log;
         Level var4 = Level.SEVERE;
         StringBuilder var5 = new StringBuilder();
         var5.append("RuntimeException while executing runnable ");
         var5.append(var0);
         var5.append(" with executor ");
         var5.append(var1);
         var3.log(var4, var5.toString(), var6);
      }

   }

   public void add(Runnable var1, Executor var2) {
      Preconditions.checkNotNull(var1, "Runnable was null.");
      Preconditions.checkNotNull(var2, "Executor was null.");
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (!this.executed) {
               ExecutionList.RunnableExecutorPair var3 = new ExecutionList.RunnableExecutorPair(var1, var2, this.runnables);
               this.runnables = var3;
               return;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label137;
         }

         try {
            ;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         executeListener(var1, var2);
         return;
      }

      while(true) {
         Throwable var16 = var10000;

         try {
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   public void execute() {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label276: {
         try {
            if (this.executed) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label276;
         }

         ExecutionList.RunnableExecutorPair var1;
         try {
            this.executed = true;
            var1 = this.runnables;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label276;
         }

         ExecutionList.RunnableExecutorPair var2 = null;

         try {
            this.runnables = null;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label276;
         }

         while(true) {
            ExecutionList.RunnableExecutorPair var3 = var2;
            if (var1 == null) {
               while(var3 != null) {
                  executeListener(var3.runnable, var3.executor);
                  var3 = var3.next;
               }

               return;
            }

            var3 = var1.next;
            var1.next = var2;
            var2 = var1;
            var1 = var3;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   private static final class RunnableExecutorPair {
      final Executor executor;
      @NullableDecl
      ExecutionList.RunnableExecutorPair next;
      final Runnable runnable;

      RunnableExecutorPair(Runnable var1, Executor var2, ExecutionList.RunnableExecutorPair var3) {
         this.runnable = var1;
         this.executor = var2;
         this.next = var3;
      }
   }
}
