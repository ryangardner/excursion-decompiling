package kotlin.internal;

import java.lang.reflect.Method;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0011"},
   d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "ReflectAddSuppressedMethod", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public class PlatformImplementations {
   public void addSuppressed(Throwable var1, Throwable var2) {
      Intrinsics.checkParameterIsNotNull(var1, "cause");
      Intrinsics.checkParameterIsNotNull(var2, "exception");
      Method var3 = PlatformImplementations.ReflectAddSuppressedMethod.method;
      if (var3 != null) {
         var3.invoke(var1, var2);
      }

   }

   public Random defaultPlatformRandom() {
      return (Random)(new FallbackThreadLocalRandom());
   }

   public MatchGroup getMatchResultNamedGroup(MatchResult var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "matchResult");
      Intrinsics.checkParameterIsNotNull(var2, "name");
      throw (Throwable)(new UnsupportedOperationException("Retrieving groups by name is not supported on this platform."));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lkotlin/internal/PlatformImplementations$ReflectAddSuppressedMethod;", "", "()V", "method", "Ljava/lang/reflect/Method;", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class ReflectAddSuppressedMethod {
      public static final PlatformImplementations.ReflectAddSuppressedMethod INSTANCE = new PlatformImplementations.ReflectAddSuppressedMethod();
      public static final Method method;

      static {
         Method[] var0 = Throwable.class.getMethods();
         Intrinsics.checkExpressionValueIsNotNull(var0, "throwableClass.methods");
         int var1 = var0.length;
         int var2 = 0;

         Method var3;
         while(true) {
            if (var2 >= var1) {
               var3 = null;
               break;
            }

            boolean var5;
            label22: {
               var3 = var0[var2];
               Intrinsics.checkExpressionValueIsNotNull(var3, "it");
               if (Intrinsics.areEqual((Object)var3.getName(), (Object)"addSuppressed")) {
                  Class[] var4 = var3.getParameterTypes();
                  Intrinsics.checkExpressionValueIsNotNull(var4, "it.parameterTypes");
                  if (Intrinsics.areEqual((Object)((Class)ArraysKt.singleOrNull(var4)), (Object)Throwable.class)) {
                     var5 = true;
                     break label22;
                  }
               }

               var5 = false;
            }

            if (var5) {
               break;
            }

            ++var2;
         }

         method = var3;
      }
   }
}
