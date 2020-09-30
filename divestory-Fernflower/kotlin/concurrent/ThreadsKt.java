package kotlin.concurrent;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aJ\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a0\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0087\b¢\u0006\u0002\u0010\u0013¨\u0006\u0014"},
   d2 = {"thread", "Ljava/lang/Thread;", "start", "", "isDaemon", "contextClassLoader", "Ljava/lang/ClassLoader;", "name", "", "priority", "", "block", "Lkotlin/Function0;", "", "getOrSet", "T", "", "Ljava/lang/ThreadLocal;", "default", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ThreadsKt {
   private static final <T> T getOrSet(ThreadLocal<T> var0, Function0<? extends T> var1) {
      Object var2 = var0.get();
      Object var3;
      if (var2 != null) {
         var3 = var2;
      } else {
         Object var4 = var1.invoke();
         var0.set(var4);
         var3 = var4;
      }

      return var3;
   }

   public static final Thread thread(boolean var0, boolean var1, ClassLoader var2, String var3, int var4, final Function0<Unit> var5) {
      Intrinsics.checkParameterIsNotNull(var5, "block");
      Thread var6 = new Thread() {
         public void run() {
            var5.invoke();
         }
      };
      if (var1) {
         var6.setDaemon(true);
      }

      if (var4 > 0) {
         var6.setPriority(var4);
      }

      if (var3 != null) {
         var6.setName(var3);
      }

      if (var2 != null) {
         var6.setContextClassLoader(var2);
      }

      if (var0) {
         var6.start();
      }

      return (Thread)var6;
   }

   // $FF: synthetic method
   public static Thread thread$default(boolean var0, boolean var1, ClassLoader var2, String var3, int var4, Function0 var5, int var6, Object var7) {
      if ((var6 & 1) != 0) {
         var0 = true;
      }

      if ((var6 & 2) != 0) {
         var1 = false;
      }

      if ((var6 & 4) != 0) {
         var2 = (ClassLoader)null;
      }

      if ((var6 & 8) != 0) {
         var3 = (String)null;
      }

      if ((var6 & 16) != 0) {
         var4 = -1;
      }

      return thread(var0, var1, var2, var3, var4, var5);
   }
}
