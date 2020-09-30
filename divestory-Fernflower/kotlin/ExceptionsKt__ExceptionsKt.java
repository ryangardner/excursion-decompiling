package kotlin;

import java.io.PrintStream;
import java.io.PrintWriter;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "stackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "addSuppressed", "", "exception", "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/ExceptionsKt"
)
class ExceptionsKt__ExceptionsKt {
   public ExceptionsKt__ExceptionsKt() {
   }

   public static final void addSuppressed(Throwable var0, Throwable var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$addSuppressed");
      Intrinsics.checkParameterIsNotNull(var1, "exception");
      PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(var0, var1);
   }

   public static final StackTraceElement[] getStackTrace(Throwable var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$stackTrace");
      StackTraceElement[] var1 = var0.getStackTrace();
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   private static final void printStackTrace(Throwable var0) {
      if (var0 != null) {
         var0.printStackTrace();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
      }
   }

   private static final void printStackTrace(Throwable var0, PrintStream var1) {
      if (var0 != null) {
         var0.printStackTrace(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
      }
   }

   private static final void printStackTrace(Throwable var0, PrintWriter var1) {
      if (var0 != null) {
         var0.printStackTrace(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
      }
   }

   // $FF: synthetic method
   public static void stackTrace$annotations(Throwable var0) {
   }
}
