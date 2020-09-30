package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"},
   d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__IndentKt extends StringsKt__AppendableKt {
   public StringsKt__IndentKt() {
   }

   private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(final String var0) {
      boolean var1;
      if (((CharSequence)var0).length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Function1 var2;
      if (var1) {
         var2 = (Function1)null.INSTANCE;
      } else {
         var2 = (Function1)(new Function1<String, String>() {
            public final String invoke(String var1) {
               Intrinsics.checkParameterIsNotNull(var1, "line");
               StringBuilder var2 = new StringBuilder();
               var2.append(var0);
               var2.append(var1);
               return var2.toString();
            }
         });
      }

      return var2;
   }

   private static final int indentWidth$StringsKt__IndentKt(String var0) {
      CharSequence var1 = (CharSequence)var0;
      int var2 = var1.length();
      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            var3 = -1;
            break;
         }

         if (CharsKt.isWhitespace(var1.charAt(var3)) ^ true) {
            break;
         }

         ++var3;
      }

      var2 = var3;
      if (var3 == -1) {
         var2 = var0.length();
      }

      return var2;
   }

   public static final String prependIndent(String var0, final String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$prependIndent");
      Intrinsics.checkParameterIsNotNull(var1, "indent");
      return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)var0), (Function1)(new Function1<String, String>() {
         public final String invoke(String var1x) {
            Intrinsics.checkParameterIsNotNull(var1x, "it");
            String var2;
            if (StringsKt.isBlank((CharSequence)var1x)) {
               var2 = var1x;
               if (var1x.length() < var1.length()) {
                  var2 = var1;
               }
            } else {
               StringBuilder var3 = new StringBuilder();
               var3.append(var1);
               var3.append(var1x);
               var2 = var3.toString();
            }

            return var2;
         }
      })), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   // $FF: synthetic method
   public static String prependIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "    ";
      }

      return StringsKt.prependIndent(var0, var1);
   }

   private static final String reindent$StringsKt__IndentKt(List<String> var0, int var1, Function1<? super String, String> var2, Function1<? super String, String> var3) {
      int var4 = CollectionsKt.getLastIndex(var0);
      Iterable var10 = (Iterable)var0;
      Collection var5 = (Collection)(new ArrayList());
      Iterator var6 = var10.iterator();

      String var12;
      for(int var7 = 0; var6.hasNext(); ++var7) {
         Object var11 = var6.next();
         if (var7 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw (Throwable)(new ArithmeticException("Index overflow has happened."));
            }

            CollectionsKt.throwIndexOverflow();
         }

         String var8 = (String)var11;
         if ((var7 == 0 || var7 == var4) && StringsKt.isBlank((CharSequence)var8)) {
            var12 = null;
         } else {
            String var9 = (String)var3.invoke(var8);
            var12 = var8;
            if (var9 != null) {
               var9 = (String)var2.invoke(var9);
               var12 = var8;
               if (var9 != null) {
                  var12 = var9;
               }
            }
         }

         if (var12 != null) {
            var5.add(var12);
         }
      }

      var12 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var5), (Appendable)(new StringBuilder(var1)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkExpressionValueIsNotNull(var12, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var12;
   }

   public static final String replaceIndent(String var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceIndent");
      Intrinsics.checkParameterIsNotNull(var1, "newIndent");
      List var2 = StringsKt.lines((CharSequence)var0);
      Iterable var3 = (Iterable)var2;
      Collection var4 = (Collection)(new ArrayList());
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         if (StringsKt.isBlank((CharSequence)((String)var6)) ^ true) {
            var4.add(var6);
         }
      }

      Iterable var16 = (Iterable)((List)var4);
      Collection var18 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var16, 10)));
      Iterator var17 = var16.iterator();

      while(var17.hasNext()) {
         var18.add(indentWidth$StringsKt__IndentKt((String)var17.next()));
      }

      Integer var19 = (Integer)CollectionsKt.min((Iterable)((List)var18));
      int var7 = 0;
      int var8;
      if (var19 != null) {
         var8 = var19;
      } else {
         var8 = 0;
      }

      int var9 = var0.length();
      int var10 = var1.length();
      int var11 = var2.size();
      Function1 var20 = getIndentFunction$StringsKt__IndentKt(var1);
      int var12 = CollectionsKt.getLastIndex(var2);
      Collection var14 = (Collection)(new ArrayList());

      for(var17 = var3.iterator(); var17.hasNext(); ++var7) {
         Object var13 = var17.next();
         if (var7 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var1 = (String)var13;
         if ((var7 == 0 || var7 == var12) && StringsKt.isBlank((CharSequence)var1)) {
            var0 = null;
         } else {
            String var15 = StringsKt.drop(var1, var8);
            var0 = var1;
            if (var15 != null) {
               var15 = (String)var20.invoke(var15);
               var0 = var1;
               if (var15 != null) {
                  var0 = var15;
               }
            }
         }

         if (var0 != null) {
            var14.add(var0);
         }
      }

      var0 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var14), (Appendable)(new StringBuilder(var9 + var10 * var11)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkExpressionValueIsNotNull(var0, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var0;
   }

   // $FF: synthetic method
   public static String replaceIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "";
      }

      return StringsKt.replaceIndent(var0, var1);
   }

   public static final String replaceIndentByMargin(String var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceIndentByMargin");
      Intrinsics.checkParameterIsNotNull(var1, "newIndent");
      Intrinsics.checkParameterIsNotNull(var2, "marginPrefix");
      if (!(StringsKt.isBlank((CharSequence)var2) ^ true)) {
         throw (Throwable)(new IllegalArgumentException("marginPrefix must be non-blank string.".toString()));
      } else {
         List var3 = StringsKt.lines((CharSequence)var0);
         int var4 = var0.length();
         int var5 = var1.length();
         int var6 = var3.size();
         Function1 var7 = getIndentFunction$StringsKt__IndentKt(var1);
         int var8 = CollectionsKt.getLastIndex(var3);
         Iterable var14 = (Iterable)var3;
         Collection var9 = (Collection)(new ArrayList());
         Iterator var10 = var14.iterator();

         for(int var11 = 0; var10.hasNext(); ++var11) {
            Object var15 = var10.next();
            if (var11 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            String var17 = (String)var15;
            var1 = null;
            if ((var11 == 0 || var11 == var8) && StringsKt.isBlank((CharSequence)var17)) {
               var0 = null;
            } else {
               CharSequence var16 = (CharSequence)var17;
               int var12 = var16.length();
               int var13 = 0;

               while(true) {
                  if (var13 >= var12) {
                     var13 = -1;
                     break;
                  }

                  if (CharsKt.isWhitespace(var16.charAt(var13)) ^ true) {
                     break;
                  }

                  ++var13;
               }

               if (var13 != -1 && StringsKt.startsWith$default(var17, var2, var13, false, 4, (Object)null)) {
                  var12 = var2.length();
                  if (var17 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var1 = var17.substring(var13 + var12);
                  Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               }

               var0 = var17;
               if (var1 != null) {
                  var1 = (String)var7.invoke(var1);
                  var0 = var17;
                  if (var1 != null) {
                     var0 = var1;
                  }
               }
            }

            if (var0 != null) {
               var9.add(var0);
            }
         }

         var0 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)var9), (Appendable)(new StringBuilder(var4 + var5 * var6)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
         Intrinsics.checkExpressionValueIsNotNull(var0, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
         return var0;
      }
   }

   // $FF: synthetic method
   public static String replaceIndentByMargin$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = "";
      }

      if ((var3 & 2) != 0) {
         var2 = "|";
      }

      return StringsKt.replaceIndentByMargin(var0, var1, var2);
   }

   public static final String trimIndent(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimIndent");
      return StringsKt.replaceIndent(var0, "");
   }

   public static final String trimMargin(String var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimMargin");
      Intrinsics.checkParameterIsNotNull(var1, "marginPrefix");
      return StringsKt.replaceIndentByMargin(var0, "", var1);
   }

   // $FF: synthetic method
   public static String trimMargin$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "|";
      }

      return StringsKt.trimMargin(var0, var1);
   }
}
