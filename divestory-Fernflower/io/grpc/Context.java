package io.grpc;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {
   static final int CONTEXT_DEPTH_WARN_THRESH = 1000;
   private static final PersistentHashArrayMappedTrie<Context.Key<?>, Object> EMPTY_ENTRIES = new PersistentHashArrayMappedTrie();
   public static final Context ROOT;
   static final Logger log = Logger.getLogger(Context.class.getName());
   final Context.CancellableContext cancellableAncestor;
   final int generation;
   final PersistentHashArrayMappedTrie<Context.Key<?>, Object> keyValueEntries;
   private ArrayList<Context.ExecutableListener> listeners;
   private Context.CancellationListener parentListener;

   static {
      ROOT = new Context((Context)null, EMPTY_ENTRIES);
   }

   private Context(Context var1, PersistentHashArrayMappedTrie<Context.Key<?>, Object> var2) {
      this.parentListener = new Context.ParentListener();
      this.cancellableAncestor = cancellableAncestor(var1);
      this.keyValueEntries = var2;
      int var3;
      if (var1 == null) {
         var3 = 0;
      } else {
         var3 = var1.generation + 1;
      }

      this.generation = var3;
      validateGeneration(var3);
   }

   // $FF: synthetic method
   Context(Context var1, PersistentHashArrayMappedTrie var2, Object var3) {
      this(var1, var2);
   }

   private Context(PersistentHashArrayMappedTrie<Context.Key<?>, Object> var1, int var2) {
      this.parentListener = new Context.ParentListener();
      this.cancellableAncestor = null;
      this.keyValueEntries = var1;
      this.generation = var2;
      validateGeneration(var2);
   }

   static Context.CancellableContext cancellableAncestor(Context var0) {
      if (var0 == null) {
         return null;
      } else {
         return var0 instanceof Context.CancellableContext ? (Context.CancellableContext)var0 : var0.cancellableAncestor;
      }
   }

   static <T> T checkNotNull(T var0, Object var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(String.valueOf(var1));
      }
   }

   public static Context current() {
      Context var0 = storage().current();
      Context var1 = var0;
      if (var0 == null) {
         var1 = ROOT;
      }

      return var1;
   }

   public static Executor currentContextExecutor(Executor var0) {
      return new Context$1CurrentContextExecutor(var0);
   }

   public static <T> Context.Key<T> key(String var0) {
      return new Context.Key(var0);
   }

   public static <T> Context.Key<T> keyWithDefault(String var0, T var1) {
      return new Context.Key(var0, var1);
   }

   static Context.Storage storage() {
      return Context.LazyStorage.storage;
   }

   private static void validateGeneration(int var0) {
      if (var0 == 1000) {
         log.log(Level.SEVERE, "Context ancestry chain length is abnormally long. This suggests an error in application code. Length exceeded: 1000", new Exception());
      }

   }

   public void addListener(Context.CancellationListener var1, Executor var2) {
      checkNotNull(var1, "cancellationListener");
      checkNotNull(var2, "executor");
      if (this.canBeCancelled()) {
         Context.ExecutableListener var35 = new Context.ExecutableListener(var2, var1);
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label361: {
            label371: {
               try {
                  if (this.isCancelled()) {
                     var35.deliver();
                     break label371;
                  }
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label361;
               }

               try {
                  if (this.listeners == null) {
                     ArrayList var33 = new ArrayList();
                     this.listeners = var33;
                     var33.add(var35);
                     if (this.cancellableAncestor != null) {
                        this.cancellableAncestor.addListener(this.parentListener, Context.DirectExecutor.INSTANCE);
                     }
                     break label371;
                  }
               } catch (Throwable var31) {
                  var10000 = var31;
                  var10001 = false;
                  break label361;
               }

               try {
                  this.listeners.add(var35);
               } catch (Throwable var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label361;
               }
            }

            label342:
            try {
               return;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               break label342;
            }
         }

         while(true) {
            Throwable var34 = var10000;

            try {
               throw var34;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public Context attach() {
      Context var1 = storage().doAttach(this);
      Context var2 = var1;
      if (var1 == null) {
         var2 = ROOT;
      }

      return var2;
   }

   public <V> V call(Callable<V> var1) throws Exception {
      Context var2 = this.attach();

      Object var5;
      try {
         var5 = var1.call();
      } finally {
         this.detach(var2);
      }

      return var5;
   }

   boolean canBeCancelled() {
      boolean var1;
      if (this.cancellableAncestor != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Throwable cancellationCause() {
      Context.CancellableContext var1 = this.cancellableAncestor;
      return var1 == null ? null : var1.cancellationCause();
   }

   public void detach(Context var1) {
      checkNotNull(var1, "toAttach");
      storage().detach(this, var1);
   }

   public Executor fixedContextExecutor(Executor var1) {
      return new Context$1FixedContextExecutor(this, var1);
   }

   public Context fork() {
      return new Context(this.keyValueEntries, this.generation + 1);
   }

   public Deadline getDeadline() {
      Context.CancellableContext var1 = this.cancellableAncestor;
      return var1 == null ? null : var1.getDeadline();
   }

   public boolean isCancelled() {
      Context.CancellableContext var1 = this.cancellableAncestor;
      return var1 == null ? false : var1.isCancelled();
   }

   boolean isCurrent() {
      boolean var1;
      if (current() == this) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   int listenerCount() {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label178: {
         int var1;
         label177: {
            label176: {
               try {
                  if (this.listeners == null) {
                     break label176;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label178;
               }

               try {
                  var1 = this.listeners.size();
                  break label177;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label178;
               }
            }

            var1 = 0;
         }

         label169:
         try {
            return var1;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label169;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   Object lookup(Context.Key<?> var1) {
      return this.keyValueEntries.get(var1);
   }

   void notifyAndClearListeners() {
      if (this.canBeCancelled()) {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label255: {
            try {
               if (this.listeners == null) {
                  return;
               }
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label255;
            }

            ArrayList var17;
            try {
               var17 = this.listeners;
               this.listeners = null;
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label255;
            }

            byte var2 = 0;
            int var3 = 0;

            while(true) {
               int var4 = var2;
               if (var3 >= var17.size()) {
                  for(; var4 < var17.size(); ++var4) {
                     if (((Context.ExecutableListener)var17.get(var4)).listener instanceof Context.ParentListener) {
                        ((Context.ExecutableListener)var17.get(var4)).deliver();
                     }
                  }

                  Context.CancellableContext var18 = this.cancellableAncestor;
                  if (var18 != null) {
                     var18.removeListener(this.parentListener);
                  }

                  return;
               }

               if (!(((Context.ExecutableListener)var17.get(var3)).listener instanceof Context.ParentListener)) {
                  ((Context.ExecutableListener)var17.get(var3)).deliver();
               }

               ++var3;
            }
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public void removeListener(Context.CancellationListener var1) {
      if (this.canBeCancelled()) {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label499: {
            label505: {
               int var2;
               try {
                  if (this.listeners == null) {
                     break label505;
                  }

                  var2 = this.listeners.size() - 1;
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break label499;
               }

               for(; var2 >= 0; --var2) {
                  try {
                     if (((Context.ExecutableListener)this.listeners.get(var2)).listener == var1) {
                        this.listeners.remove(var2);
                        break;
                     }
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break label499;
                  }
               }

               try {
                  if (!this.listeners.isEmpty()) {
                     break label505;
                  }

                  if (this.cancellableAncestor != null) {
                     this.cancellableAncestor.removeListener(this.parentListener);
                  }
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break label499;
               }

               try {
                  this.listeners = null;
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break label499;
               }
            }

            label473:
            try {
               return;
            } catch (Throwable var40) {
               var10000 = var40;
               var10001 = false;
               break label473;
            }
         }

         while(true) {
            Throwable var45 = var10000;

            try {
               throw var45;
            } catch (Throwable var39) {
               var10000 = var39;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public void run(Runnable var1) {
      Context var2 = this.attach();

      try {
         var1.run();
      } finally {
         this.detach(var2);
      }

   }

   public Context.CancellableContext withCancellation() {
      return new Context.CancellableContext(this);
   }

   public Context.CancellableContext withDeadline(Deadline var1, ScheduledExecutorService var2) {
      checkNotNull(var1, "deadline");
      checkNotNull(var2, "scheduler");
      return new Context.CancellableContext(this, var1, var2);
   }

   public Context.CancellableContext withDeadlineAfter(long var1, TimeUnit var3, ScheduledExecutorService var4) {
      return this.withDeadline(Deadline.after(var1, var3), var4);
   }

   public <V> Context withValue(Context.Key<V> var1, V var2) {
      return new Context(this, this.keyValueEntries.put(var1, var2));
   }

   public <V1, V2> Context withValues(Context.Key<V1> var1, V1 var2, Context.Key<V2> var3, V2 var4) {
      return new Context(this, this.keyValueEntries.put(var1, var2).put(var3, var4));
   }

   public <V1, V2, V3> Context withValues(Context.Key<V1> var1, V1 var2, Context.Key<V2> var3, V2 var4, Context.Key<V3> var5, V3 var6) {
      return new Context(this, this.keyValueEntries.put(var1, var2).put(var3, var4).put(var5, var6));
   }

   public <V1, V2, V3, V4> Context withValues(Context.Key<V1> var1, V1 var2, Context.Key<V2> var3, V2 var4, Context.Key<V3> var5, V3 var6, Context.Key<V4> var7, V4 var8) {
      return new Context(this, this.keyValueEntries.put(var1, var2).put(var3, var4).put(var5, var6).put(var7, var8));
   }

   public Runnable wrap(final Runnable var1) {
      return new Runnable() {
         public void run() {
            Context var1x = Context.this.attach();

            try {
               var1.run();
            } finally {
               Context.this.detach(var1x);
            }

         }
      };
   }

   public <C> Callable<C> wrap(final Callable<C> var1) {
      return new Callable<C>() {
         public C call() throws Exception {
            Context var1x = Context.this.attach();

            Object var2;
            try {
               var2 = var1.call();
            } finally {
               Context.this.detach(var1x);
            }

            return var2;
         }
      };
   }

   @interface CanIgnoreReturnValue {
   }

   public static final class CancellableContext extends Context implements Closeable {
      private Throwable cancellationCause;
      private boolean cancelled;
      private final Deadline deadline;
      private ScheduledFuture<?> pendingDeadline;
      private final Context uncancellableSurrogate;

      private CancellableContext(Context var1) {
         super(var1, var1.keyValueEntries, null);
         this.deadline = var1.getDeadline();
         this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
      }

      // $FF: synthetic method
      CancellableContext(Context var1, Object var2) {
         this(var1);
      }

      private CancellableContext(Context var1, Deadline var2, ScheduledExecutorService var3) {
         super(var1, var1.keyValueEntries, null);
         Deadline var4 = var1.getDeadline();
         if (var4 == null || var4.compareTo(var2) > 0) {
            if (!var2.isExpired()) {
               this.pendingDeadline = var2.runOnExpiration(new Runnable() {
                  public void run() {
                     try {
                        Context.CancellableContext var1 = CancellableContext.this;
                        TimeoutException var2 = new TimeoutException("context timed out");
                        var1.cancel(var2);
                     } catch (Throwable var4) {
                        Context.log.log(Level.SEVERE, "Cancel threw an exception, which should not happen", var4);
                        return;
                     }

                  }
               }, var3);
               var4 = var2;
            } else {
               this.cancel(new TimeoutException("context timed out"));
               var4 = var2;
            }
         }

         this.deadline = var4;
         this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
      }

      // $FF: synthetic method
      CancellableContext(Context var1, Deadline var2, ScheduledExecutorService var3, Object var4) {
         this(var1, var2, var3);
      }

      public Context attach() {
         return this.uncancellableSurrogate.attach();
      }

      boolean canBeCancelled() {
         return true;
      }

      public boolean cancel(Throwable var1) {
         synchronized(this){}

         boolean var3;
         label322: {
            Throwable var10000;
            boolean var10001;
            label323: {
               boolean var2;
               try {
                  var2 = this.cancelled;
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label323;
               }

               var3 = true;
               if (!var2) {
                  try {
                     this.cancelled = true;
                     if (this.pendingDeadline != null) {
                        this.pendingDeadline.cancel(false);
                        this.pendingDeadline = null;
                     }
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label323;
                  }

                  try {
                     this.cancellationCause = var1;
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label323;
                  }
               } else {
                  var3 = false;
               }

               label304:
               try {
                  break label322;
               } catch (Throwable var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label304;
               }
            }

            while(true) {
               var1 = var10000;

               try {
                  throw var1;
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  continue;
               }
            }
         }

         if (var3) {
            this.notifyAndClearListeners();
         }

         return var3;
      }

      public Throwable cancellationCause() {
         return this.isCancelled() ? this.cancellationCause : null;
      }

      public void close() {
         this.cancel((Throwable)null);
      }

      public void detach(Context var1) {
         this.uncancellableSurrogate.detach(var1);
      }

      public void detachAndCancel(Context var1, Throwable var2) {
         try {
            this.detach(var1);
         } finally {
            this.cancel(var2);
         }

      }

      public Deadline getDeadline() {
         return this.deadline;
      }

      public boolean isCancelled() {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label153: {
            try {
               if (this.cancelled) {
                  return true;
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label153;
            }

            try {
               ;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label153;
            }

            if (super.isCancelled()) {
               this.cancel(super.cancellationCause());
               return true;
            }

            return false;
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      }

      @Deprecated
      public boolean isCurrent() {
         return this.uncancellableSurrogate.isCurrent();
      }
   }

   public interface CancellationListener {
      void cancelled(Context var1);
   }

   @interface CheckReturnValue {
   }

   private static enum DirectExecutor implements Executor {
      INSTANCE;

      static {
         Context.DirectExecutor var0 = new Context.DirectExecutor("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void execute(Runnable var1) {
         var1.run();
      }

      public String toString() {
         return "Context.DirectExecutor";
      }
   }

   private final class ExecutableListener implements Runnable {
      private final Executor executor;
      final Context.CancellationListener listener;

      ExecutableListener(Executor var2, Context.CancellationListener var3) {
         this.executor = var2;
         this.listener = var3;
      }

      void deliver() {
         try {
            this.executor.execute(this);
         } catch (Throwable var3) {
            Context.log.log(Level.INFO, "Exception notifying context listener", var3);
            return;
         }

      }

      public void run() {
         this.listener.cancelled(Context.this);
      }
   }

   public static final class Key<T> {
      private final T defaultValue;
      private final String name;

      Key(String var1) {
         this(var1, (Object)null);
      }

      Key(String var1, T var2) {
         this.name = (String)Context.checkNotNull(var1, "name");
         this.defaultValue = var2;
      }

      public T get() {
         return this.get(Context.current());
      }

      public T get(Context var1) {
         Object var2 = var1.lookup(this);
         Object var3 = var2;
         if (var2 == null) {
            var3 = this.defaultValue;
         }

         return var3;
      }

      public String toString() {
         return this.name;
      }
   }

   private static final class LazyStorage {
      static final Context.Storage storage;

      static {
         AtomicReference var0 = new AtomicReference();
         storage = createStorage(var0);
         Throwable var1 = (Throwable)var0.get();
         if (var1 != null) {
            Context.log.log(Level.FINE, "Storage override doesn't exist. Using default", var1);
         }

      }

      private static Context.Storage createStorage(AtomicReference<? super ClassNotFoundException> var0) {
         try {
            Context.Storage var1 = (Context.Storage)Class.forName("io.grpc.override.ContextStorageOverride").asSubclass(Context.Storage.class).getConstructor().newInstance();
            return var1;
         } catch (ClassNotFoundException var2) {
            var0.set(var2);
            return new ThreadLocalContextStorage();
         } catch (Exception var3) {
            throw new RuntimeException("Storage override failed to initialize", var3);
         }
      }
   }

   private final class ParentListener implements Context.CancellationListener {
      private ParentListener() {
      }

      // $FF: synthetic method
      ParentListener(Object var2) {
         this();
      }

      public void cancelled(Context var1) {
         Context var2 = Context.this;
         if (var2 instanceof Context.CancellableContext) {
            ((Context.CancellableContext)var2).cancel(var1.cancellationCause());
         } else {
            var2.notifyAndClearListeners();
         }

      }
   }

   public abstract static class Storage {
      @Deprecated
      public void attach(Context var1) {
         throw new UnsupportedOperationException("Deprecated. Do not call.");
      }

      public abstract Context current();

      public abstract void detach(Context var1, Context var2);

      public Context doAttach(Context var1) {
         Context var2 = this.current();
         this.attach(var1);
         return var2;
      }
   }
}
