package kotlin.text;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u001a5\u0010\u0000\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u0003*\u0002H\u00012\u0016\u0010\u0004\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u0005\"\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007\u001a9\u0010\b\u001a\u00020\t\"\u0004\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\n\u001a\u0002H\u00012\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fH\u0000¢\u0006\u0002\u0010\r\u001a9\u0010\u000e\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u0003*\u0002H\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0012¨\u0006\u0013"},
   d2 = {"append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "appendElement", "", "element", "transform", "Lkotlin/Function1;", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "appendRange", "startIndex", "", "endIndex", "(Ljava/lang/Appendable;Ljava/lang/CharSequence;II)Ljava/lang/Appendable;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__AppendableKt {
   public StringsKt__AppendableKt() {
   }

   public static final <T extends Appendable> T append(T var0, CharSequence... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$append");
      Intrinsics.checkParameterIsNotNull(var1, "value");
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
      }

      return var0;
   }

   public static final <T> void appendElement(Appendable var0, T var1, Function1<? super T, ? extends CharSequence> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendElement");
      if (var2 != null) {
         var0.append((CharSequence)var2.invoke(var1));
      } else {
         boolean var3;
         if (var1 != null) {
            var3 = var1 instanceof CharSequence;
         } else {
            var3 = true;
         }

         if (var3) {
            var0.append((CharSequence)var1);
         } else if (var1 instanceof Character) {
            var0.append((Character)var1);
         } else {
            var0.append((CharSequence)String.valueOf(var1));
         }
      }

   }

   public static final <T extends Appendable> T appendRange(T var0, CharSequence var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendRange");
      var0 = var0.append(var1, var2, var3);
      if (var0 != null) {
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type T");
      }
   }
}
