package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever;", "", "()V", "cache", "Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "notOnJava9", "buildCache", "continuation", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getModuleName", "", "Cache", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class ModuleNameRetriever {
   public static final ModuleNameRetriever INSTANCE = new ModuleNameRetriever();
   public static ModuleNameRetriever.Cache cache;
   private static final ModuleNameRetriever.Cache notOnJava9 = new ModuleNameRetriever.Cache((Method)null, (Method)null, (Method)null);

   private ModuleNameRetriever() {
   }

   private final ModuleNameRetriever.Cache buildCache(BaseContinuationImpl var1) {
      ModuleNameRetriever.Cache var6;
      try {
         Method var2 = Class.class.getDeclaredMethod("getModule");
         Method var3 = var1.getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor");
         Method var4 = var1.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name");
         var6 = new ModuleNameRetriever.Cache(var2, var3, var4);
         cache = var6;
         return var6;
      } catch (Exception var5) {
         var6 = notOnJava9;
         cache = var6;
         return var6;
      }
   }

   public final String getModuleName(BaseContinuationImpl var1) {
      Intrinsics.checkParameterIsNotNull(var1, "continuation");
      ModuleNameRetriever.Cache var2 = cache;
      if (var2 == null) {
         var2 = this.buildCache(var1);
      }

      ModuleNameRetriever.Cache var3 = notOnJava9;
      Object var4 = null;
      Object var5 = null;
      if (var2 == var3) {
         return null;
      } else {
         Method var6 = var2.getModuleMethod;
         String var9 = (String)var4;
         if (var6 != null) {
            Object var7 = var6.invoke(var1.getClass());
            var9 = (String)var4;
            if (var7 != null) {
               var6 = var2.getDescriptorMethod;
               var9 = (String)var4;
               if (var6 != null) {
                  var7 = var6.invoke(var7);
                  var9 = (String)var4;
                  if (var7 != null) {
                     Method var8 = var2.nameMethod;
                     if (var8 != null) {
                        var7 = var8.invoke(var7);
                     } else {
                        var7 = null;
                     }

                     if (!(var7 instanceof String)) {
                        var7 = var5;
                     }

                     var9 = (String)var7;
                  }
               }
            }
         }

         return var9;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "", "getModuleMethod", "Ljava/lang/reflect/Method;", "getDescriptorMethod", "nameMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Cache {
      public final Method getDescriptorMethod;
      public final Method getModuleMethod;
      public final Method nameMethod;

      public Cache(Method var1, Method var2, Method var3) {
         this.getModuleMethod = var1;
         this.getDescriptorMethod = var2;
         this.nameMethod = var3;
      }
   }
}
