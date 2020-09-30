package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Finalizer implements Runnable {
   private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
   @NullableDecl
   private static final Constructor<Thread> bigThreadConstructor;
   @NullableDecl
   private static final Field inheritableThreadLocals;
   private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
   private final WeakReference<Class<?>> finalizableReferenceClassReference;
   private final PhantomReference<Object> frqReference;
   private final ReferenceQueue<Object> queue;

   static {
      Constructor var0 = getBigThreadConstructor();
      bigThreadConstructor = var0;
      Field var1;
      if (var0 == null) {
         var1 = getInheritableThreadLocalsField();
      } else {
         var1 = null;
      }

      inheritableThreadLocals = var1;
   }

   private Finalizer(Class<?> var1, ReferenceQueue<Object> var2, PhantomReference<Object> var3) {
      this.queue = var2;
      this.finalizableReferenceClassReference = new WeakReference(var1);
      this.frqReference = var3;
   }

   private boolean cleanUp(Reference<?> var1) {
      Method var2 = this.getFinalizeReferentMethod();
      if (var2 == null) {
         return false;
      } else {
         Reference var3;
         do {
            var1.clear();
            if (var1 == this.frqReference) {
               return false;
            }

            label44:
            try {
               var2.invoke(var1);
            } catch (Throwable var5) {
               logger.log(Level.SEVERE, "Error cleaning up after reference.", var5);
               break label44;
            }

            var3 = this.queue.poll();
            var1 = var3;
         } while(var3 != null);

         return true;
      }
   }

   @NullableDecl
   private static Constructor<Thread> getBigThreadConstructor() {
      try {
         Constructor var0 = Thread.class.getConstructor(ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE);
         return var0;
      } finally {
         ;
      }
   }

   @NullableDecl
   private Method getFinalizeReferentMethod() {
      Class var1 = (Class)this.finalizableReferenceClassReference.get();
      if (var1 == null) {
         return null;
      } else {
         try {
            Method var3 = var1.getMethod("finalizeReferent");
            return var3;
         } catch (NoSuchMethodException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   @NullableDecl
   private static Field getInheritableThreadLocalsField() {
      try {
         Field var0 = Thread.class.getDeclaredField("inheritableThreadLocals");
         var0.setAccessible(true);
         return var0;
      } finally {
         logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
         return null;
      }
   }

   public static void startFinalizer(Class<?> var0, ReferenceQueue<Object> var1, PhantomReference<Object> var2) {
      if (!var0.getName().equals("com.google.common.base.FinalizableReference")) {
         throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
      } else {
         String var3;
         Thread var11;
         Finalizer var13;
         label98: {
            var13 = new Finalizer(var0, var1, var2);
            var3 = Finalizer.class.getName();
            Constructor var10 = bigThreadConstructor;
            if (var10 != null) {
               label96:
               try {
                  var11 = (Thread)var10.newInstance((ThreadGroup)null, var13, var3, 0L, false);
                  break label98;
               } catch (Throwable var9) {
                  logger.log(Level.INFO, "Failed to create a thread without inherited thread-local values", var9);
                  break label96;
               }
            }

            var11 = null;
         }

         Thread var12 = var11;
         if (var11 == null) {
            var12 = new Thread((ThreadGroup)null, var13, var3);
         }

         var12.setDaemon(true);

         label89:
         try {
            if (inheritableThreadLocals != null) {
               inheritableThreadLocals.set(var12, (Object)null);
            }
         } catch (Throwable var8) {
            logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", var8);
            break label89;
         }

         var12.start();
      }
   }

   public void run() {
      while(true) {
         boolean var1;
         try {
            var1 = this.cleanUp(this.queue.remove());
         } catch (InterruptedException var3) {
            continue;
         }

         if (!var1) {
            return;
         }
      }
   }
}
