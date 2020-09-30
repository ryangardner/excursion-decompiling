package com.google.common.base;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class FinalizableReferenceQueue implements Closeable {
   private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
   private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
   private static final Method startFinalizer = getStartFinalizer(loadFinalizer(new FinalizableReferenceQueue.SystemLoader(), new FinalizableReferenceQueue.DecoupledLoader(), new FinalizableReferenceQueue.DirectLoader()));
   final PhantomReference<Object> frqRef;
   final ReferenceQueue<Object> queue = new ReferenceQueue();
   final boolean threadStarted;

   public FinalizableReferenceQueue() {
      PhantomReference var1 = new PhantomReference(this, this.queue);
      this.frqRef = var1;
      boolean var2 = true;

      label35: {
         IllegalAccessException var7;
         try {
            try {
               startFinalizer.invoke((Object)null, FinalizableReference.class, this.queue, var1);
               break label35;
            } catch (IllegalAccessException var5) {
               var7 = var5;
            }
         } catch (Throwable var6) {
            logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", var6);
            var2 = false;
            break label35;
         }

         throw new AssertionError(var7);
      }

      this.threadStarted = var2;
   }

   static Method getStartFinalizer(Class<?> var0) {
      try {
         Method var2 = var0.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
         return var2;
      } catch (NoSuchMethodException var1) {
         throw new AssertionError(var1);
      }
   }

   private static Class<?> loadFinalizer(FinalizableReferenceQueue.FinalizerLoader... var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Class var3 = var0[var2].loadFinalizer();
         if (var3 != null) {
            return var3;
         }
      }

      throw new AssertionError();
   }

   void cleanUp() {
      if (!this.threadStarted) {
         while(true) {
            Reference var1 = this.queue.poll();
            if (var1 == null) {
               return;
            }

            var1.clear();

            try {
               ((FinalizableReference)var1).finalizeReferent();
            } catch (Throwable var3) {
               logger.log(Level.SEVERE, "Error cleaning up after reference.", var3);
               continue;
            }
         }
      }
   }

   public void close() {
      this.frqRef.enqueue();
      this.cleanUp();
   }

   static class DecoupledLoader implements FinalizableReferenceQueue.FinalizerLoader {
      private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

      URL getBaseUrl() throws IOException {
         StringBuilder var1 = new StringBuilder();
         var1.append("com.google.common.base.internal.Finalizer".replace('.', '/'));
         var1.append(".class");
         String var2 = var1.toString();
         URL var3 = this.getClass().getClassLoader().getResource(var2);
         if (var3 != null) {
            String var4 = var3.toString();
            if (var4.endsWith(var2)) {
               return new URL(var3, var4.substring(0, var4.length() - var2.length()));
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("Unsupported path style: ");
               var5.append(var4);
               throw new IOException(var5.toString());
            }
         } else {
            throw new FileNotFoundException(var2);
         }
      }

      @NullableDecl
      public Class<?> loadFinalizer() {
         try {
            Class var1 = this.newLoader(this.getBaseUrl()).loadClass("com.google.common.base.internal.Finalizer");
            return var1;
         } catch (Exception var2) {
            FinalizableReferenceQueue.logger.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.", var2);
            return null;
         }
      }

      URLClassLoader newLoader(URL var1) {
         return new URLClassLoader(new URL[]{var1}, (ClassLoader)null);
      }
   }

   static class DirectLoader implements FinalizableReferenceQueue.FinalizerLoader {
      public Class<?> loadFinalizer() {
         try {
            Class var1 = Class.forName("com.google.common.base.internal.Finalizer");
            return var1;
         } catch (ClassNotFoundException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   interface FinalizerLoader {
      @NullableDecl
      Class<?> loadFinalizer();
   }

   static class SystemLoader implements FinalizableReferenceQueue.FinalizerLoader {
      static boolean disabled;

      @NullableDecl
      public Class<?> loadFinalizer() {
         if (disabled) {
            return null;
         } else {
            ClassLoader var1;
            try {
               var1 = ClassLoader.getSystemClassLoader();
            } catch (SecurityException var2) {
               FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
               return null;
            }

            if (var1 != null) {
               try {
                  Class var4 = var1.loadClass("com.google.common.base.internal.Finalizer");
                  return var4;
               } catch (ClassNotFoundException var3) {
               }
            }

            return null;
         }
      }
   }
}
