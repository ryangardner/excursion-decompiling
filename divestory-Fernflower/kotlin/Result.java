package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0087@\u0018\u0000 \u001e*\u0006\b\u0000\u0010\u0001 \u00012\u00060\u0002j\u0002`\u0003:\u0002\u001e\u001fB\u0016\b\u0001\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÖ\u0003J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0004\b\u0014\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00018\u0000H\u0087\b¢\u0006\u0004\b\u0017\u0010\u0007J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\u000f\u0010\u001a\u001a\u00020\u001bH\u0016¢\u0006\u0004\b\u001c\u0010\u001dR\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "},
   d2 = {"Lkotlin/Result;", "T", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "value", "", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isFailure", "", "isFailure-impl", "(Ljava/lang/Object;)Z", "isSuccess", "isSuccess-impl", "value$annotations", "()V", "equals", "other", "exceptionOrNull", "", "exceptionOrNull-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getOrNull", "getOrNull-impl", "hashCode", "", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Companion", "Failure", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Result<T> implements Serializable {
   public static final Result.Companion Companion = new Result.Companion((DefaultConstructorMarker)null);
   private final Object value;

   // $FF: synthetic method
   private Result(Object var1) {
      this.value = var1;
   }

   // $FF: synthetic method
   public static final Result box_impl/* $FF was: box-impl*/(Object var0) {
      return new Result(var0);
   }

   public static Object constructor_impl/* $FF was: constructor-impl*/(Object var0) {
      return var0;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(Object var0, Object var1) {
      return var1 instanceof Result && Intrinsics.areEqual(var0, ((Result)var1).unbox-impl());
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(Object var0, Object var1) {
      return Intrinsics.areEqual(var0, var1);
   }

   public static final Throwable exceptionOrNull_impl/* $FF was: exceptionOrNull-impl*/(Object var0) {
      Throwable var1;
      if (var0 instanceof Result.Failure) {
         var1 = ((Result.Failure)var0).exception;
      } else {
         var1 = null;
      }

      return var1;
   }

   private static final T getOrNull_impl/* $FF was: getOrNull-impl*/(Object var0) {
      Object var1 = var0;
      if (isFailure-impl(var0)) {
         var1 = null;
      }

      return var1;
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(Object var0) {
      int var1;
      if (var0 != null) {
         var1 = var0.hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public static final boolean isFailure_impl/* $FF was: isFailure-impl*/(Object var0) {
      return var0 instanceof Result.Failure;
   }

   public static final boolean isSuccess_impl/* $FF was: isSuccess-impl*/(Object var0) {
      return var0 instanceof Result.Failure ^ true;
   }

   public static String toString_impl/* $FF was: toString-impl*/(Object var0) {
      String var2;
      if (var0 instanceof Result.Failure) {
         var2 = var0.toString();
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Success(");
         var1.append(var0);
         var1.append(')');
         var2 = var1.toString();
      }

      return var2;
   }

   // $FF: synthetic method
   public static void value$annotations() {
   }

   public boolean equals(Object var1) {
      return equals-impl(this.value, var1);
   }

   public int hashCode() {
      return hashCode-impl(this.value);
   }

   public String toString() {
      return toString-impl(this.value);
   }

   // $FF: synthetic method
   public final Object unbox_impl/* $FF was: unbox-impl*/() {
      return this.value;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\bJ%\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\n\u001a\u0002H\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
      d2 = {"Lkotlin/Result$Companion;", "", "()V", "failure", "Lkotlin/Result;", "T", "exception", "", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "success", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final <T> Object failure(Throwable var1) {
         return Result.constructor-impl(ResultKt.createFailure(var1));
      }

      private final <T> Object success(T var1) {
         return Result.constructor-impl(var1);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0096\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
      d2 = {"Lkotlin/Result$Failure;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "exception", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Failure implements Serializable {
      public final Throwable exception;

      public Failure(Throwable var1) {
         Intrinsics.checkParameterIsNotNull(var1, "exception");
         super();
         this.exception = var1;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if (var1 instanceof Result.Failure && Intrinsics.areEqual((Object)this.exception, (Object)((Result.Failure)var1).exception)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int hashCode() {
         return this.exception.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Failure(");
         var1.append(this.exception);
         var1.append(')');
         return var1.toString();
      }
   }
}
