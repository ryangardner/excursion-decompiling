package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aY\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\t\u001aW\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a;\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aW\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a-\u0010\r\u001a\u00020\u000e\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0010\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0011\u001a>\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b¢\u0006\u0002\u0010\u0013\u001aY\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0014\u001aZ\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b¢\u0006\u0002\u0010\u0015\u001aG\u0010\u0016\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022 \u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\bH\u0002¢\u0006\u0004\b\u0017\u0010\u0014\u001a&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a-\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a-\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a0\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\u001aO\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004\u001aO\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001aO\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001am\u0010!\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u000328\b\u0004\u0010\"\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000e0#H\u0087\b\u001aO\u0010&\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004¨\u0006'"},
   d2 = {"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt__ComparisonsKt {
   public ComparisonsKt__ComparisonsKt() {
   }

   private static final <T, K> Comparator<T> compareBy(final Comparator<? super K> var0, final Function1<? super T, ? extends K> var1) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            return var0.compare(var1.invoke(var1x), var1.invoke(var2));
         }
      });
   }

   private static final <T> Comparator<T> compareBy(final Function1<? super T, ? extends Comparable<?>> var0) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1, T var2) {
            return ComparisonsKt.compareValues((Comparable)var0.invoke(var1), (Comparable)var0.invoke(var2));
         }
      });
   }

   public static final <T> Comparator<T> compareBy(final Function1<? super T, ? extends Comparable<?>>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "selectors");
      boolean var1;
      if (var0.length > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return (Comparator)(new Comparator<T>() {
            public final int compare(T var1, T var2) {
               return ComparisonsKt__ComparisonsKt.compareValuesByImpl$ComparisonsKt__ComparisonsKt(var1, var2, var0);
            }
         });
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }

   private static final <T, K> Comparator<T> compareByDescending(final Comparator<? super K> var0, final Function1<? super T, ? extends K> var1) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            return var0.compare(var1.invoke(var2), var1.invoke(var1x));
         }
      });
   }

   private static final <T> Comparator<T> compareByDescending(final Function1<? super T, ? extends Comparable<?>> var0) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1, T var2) {
            return ComparisonsKt.compareValues((Comparable)var0.invoke(var2), (Comparable)var0.invoke(var1));
         }
      });
   }

   public static final <T extends Comparable<?>> int compareValues(T var0, T var1) {
      if (var0 == var1) {
         return 0;
      } else if (var0 == null) {
         return -1;
      } else {
         return var1 == null ? 1 : var0.compareTo(var1);
      }
   }

   private static final <T, K> int compareValuesBy(T var0, T var1, Comparator<? super K> var2, Function1<? super T, ? extends K> var3) {
      return var2.compare(var3.invoke(var0), var3.invoke(var1));
   }

   private static final <T> int compareValuesBy(T var0, T var1, Function1<? super T, ? extends Comparable<?>> var2) {
      return ComparisonsKt.compareValues((Comparable)var2.invoke(var0), (Comparable)var2.invoke(var1));
   }

   public static final <T> int compareValuesBy(T var0, T var1, Function1<? super T, ? extends Comparable<?>>... var2) {
      Intrinsics.checkParameterIsNotNull(var2, "selectors");
      boolean var3;
      if (var2.length > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return compareValuesByImpl$ComparisonsKt__ComparisonsKt(var0, var1, var2);
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }

   private static final <T> int compareValuesByImpl$ComparisonsKt__ComparisonsKt(T var0, T var1, Function1<? super T, ? extends Comparable<?>>[] var2) {
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Function1 var5 = var2[var4];
         int var6 = ComparisonsKt.compareValues((Comparable)var5.invoke(var0), (Comparable)var5.invoke(var1));
         if (var6 != 0) {
            return var6;
         }
      }

      return 0;
   }

   public static final <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
      NaturalOrderComparator var0 = NaturalOrderComparator.INSTANCE;
      if (var0 != null) {
         return (Comparator)var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      }
   }

   private static final <T extends Comparable<? super T>> Comparator<T> nullsFirst() {
      return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
   }

   public static final <T> Comparator<T> nullsFirst(final Comparator<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1, T var2) {
            int var3;
            if (var1 == var2) {
               var3 = 0;
            } else if (var1 == null) {
               var3 = -1;
            } else if (var2 == null) {
               var3 = 1;
            } else {
               var3 = var0.compare(var1, var2);
            }

            return var3;
         }
      });
   }

   private static final <T extends Comparable<? super T>> Comparator<T> nullsLast() {
      return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
   }

   public static final <T> Comparator<T> nullsLast(final Comparator<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1, T var2) {
            int var3;
            if (var1 == var2) {
               var3 = 0;
            } else if (var1 == null) {
               var3 = 1;
            } else if (var2 == null) {
               var3 = -1;
            } else {
               var3 = var0.compare(var1, var2);
            }

            return var3;
         }
      });
   }

   public static final <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
      ReverseOrderComparator var0 = ReverseOrderComparator.INSTANCE;
      if (var0 != null) {
         return (Comparator)var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      }
   }

   public static final <T> Comparator<T> reversed(Comparator<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      if (var0 instanceof ReversedComparator) {
         var0 = ((ReversedComparator)var0).getComparator();
      } else if (Intrinsics.areEqual((Object)var0, (Object)NaturalOrderComparator.INSTANCE)) {
         ReverseOrderComparator var1 = ReverseOrderComparator.INSTANCE;
         if (var1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var0 = (Comparator)var1;
      } else if (Intrinsics.areEqual((Object)var0, (Object)ReverseOrderComparator.INSTANCE)) {
         NaturalOrderComparator var2 = NaturalOrderComparator.INSTANCE;
         if (var2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var0 = (Comparator)var2;
      } else {
         var0 = (Comparator)(new ReversedComparator(var0));
      }

      return var0;
   }

   public static final <T> Comparator<T> then(final Comparator<T> var0, final Comparator<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$then");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            int var3 = var0.compare(var1x, var2);
            if (var3 == 0) {
               var3 = var1.compare(var1x, var2);
            }

            return var3;
         }
      });
   }

   private static final <T, K> Comparator<T> thenBy(final Comparator<T> var0, final Comparator<? super K> var1, final Function1<? super T, ? extends K> var2) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2x) {
            int var3 = var0.compare(var1x, var2x);
            if (var3 == 0) {
               var3 = var1.compare(var2.invoke(var1x), var2.invoke(var2x));
            }

            return var3;
         }
      });
   }

   private static final <T> Comparator<T> thenBy(final Comparator<T> var0, final Function1<? super T, ? extends Comparable<?>> var1) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            int var3 = var0.compare(var1x, var2);
            if (var3 == 0) {
               var3 = ComparisonsKt.compareValues((Comparable)var1.invoke(var1x), (Comparable)var1.invoke(var2));
            }

            return var3;
         }
      });
   }

   private static final <T, K> Comparator<T> thenByDescending(final Comparator<T> var0, final Comparator<? super K> var1, final Function1<? super T, ? extends K> var2) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2x) {
            int var3 = var0.compare(var1x, var2x);
            if (var3 == 0) {
               var3 = var1.compare(var2.invoke(var2x), var2.invoke(var1x));
            }

            return var3;
         }
      });
   }

   private static final <T> Comparator<T> thenByDescending(final Comparator<T> var0, final Function1<? super T, ? extends Comparable<?>> var1) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            int var3 = var0.compare(var1x, var2);
            if (var3 == 0) {
               var3 = ComparisonsKt.compareValues((Comparable)var1.invoke(var2), (Comparable)var1.invoke(var1x));
            }

            return var3;
         }
      });
   }

   private static final <T> Comparator<T> thenComparator(final Comparator<T> var0, final Function2<? super T, ? super T, Integer> var1) {
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            int var3 = var0.compare(var1x, var2);
            if (var3 == 0) {
               var3 = ((Number)var1.invoke(var1x, var2)).intValue();
            }

            return var3;
         }
      });
   }

   public static final <T> Comparator<T> thenDescending(final Comparator<T> var0, final Comparator<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$thenDescending");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      return (Comparator)(new Comparator<T>() {
         public final int compare(T var1x, T var2) {
            int var3 = var0.compare(var1x, var2);
            if (var3 == 0) {
               var3 = var1.compare(var2, var1x);
            }

            return var3;
         }
      });
   }
}
