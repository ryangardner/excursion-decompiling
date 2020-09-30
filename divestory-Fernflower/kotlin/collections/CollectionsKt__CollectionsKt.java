package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u0019\u001aK\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e¢\u0006\u0002\b\u001fH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aC\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e¢\u0006\u0002\b\u001fH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u0012\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\"\u001a%\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\b\u0010%\u001a\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010&\u001a3\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010\"\u001a\u0015\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\"\u001a%\u0010(\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002¢\u0006\u0002\b+\u001a\b\u0010,\u001a\u00020\u001eH\u0001\u001a\b\u0010-\u001a\u00020\u001eH\u0001\u001a%\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000¢\u0006\u0002\u0010/\u001aS\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010%\u001a\u0002H\u00072\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000702j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`32\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006¢\u0006\u0002\u00104\u001a>\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u00100\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000706*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010%\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006¢\u0006\u0002\u00107\u001ad\u00108\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00109*\b\u0012\u0004\u0012\u0002H906*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u0010:\u001a\u0004\u0018\u0001H92\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0016\b\u0004\u0010;\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H90\u000eH\u0086\b¢\u0006\u0002\u0010<\u001a,\u0010=\u001a\u00020>\"\t\b\u0000\u0010\u0007¢\u0006\u0002\b?*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a8\u0010@\u001a\u0002HA\"\u0010\b\u0000\u0010B*\u0006\u0012\u0002\b\u00030\u0002*\u0002HA\"\u0004\b\u0001\u0010A*\u0002HB2\f\u0010C\u001a\b\u0012\u0004\u0012\u0002HA0DH\u0087\b¢\u0006\u0002\u0010E\u001a\u0019\u0010F\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010G\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001e\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006J"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "buildList", "E", "capacity", "builderAction", "", "Lkotlin/ExtensionFunctionType;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
   public CollectionsKt__CollectionsKt() {
   }

   private static final <T> List<T> List(int var0, Function1<? super Integer, ? extends T> var1) {
      ArrayList var2 = new ArrayList(var0);

      for(int var3 = 0; var3 < var0; ++var3) {
         var2.add(var1.invoke(var3));
      }

      return (List)var2;
   }

   private static final <T> List<T> MutableList(int var0, Function1<? super Integer, ? extends T> var1) {
      ArrayList var2 = new ArrayList(var0);

      for(int var3 = 0; var3 < var0; ++var3) {
         var2.add(var1.invoke(var3));
      }

      return (List)var2;
   }

   private static final <T> ArrayList<T> arrayListOf() {
      return new ArrayList();
   }

   public static final <T> ArrayList<T> arrayListOf(T... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "elements");
      ArrayList var1;
      if (var0.length == 0) {
         var1 = new ArrayList();
      } else {
         var1 = new ArrayList((Collection)(new ArrayAsCollection(var0, true)));
      }

      return var1;
   }

   public static final <T> Collection<T> asCollection(T[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asCollection");
      return (Collection)(new ArrayAsCollection(var0, false));
   }

   public static final <T> int binarySearch(List<? extends T> var0, int var1, int var2, Function1<? super T, Integer> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearch");
      Intrinsics.checkParameterIsNotNull(var3, "comparison");
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var1, var2);
      --var2;

      while(var1 <= var2) {
         int var4 = var1 + var2 >>> 1;
         int var5 = ((Number)var3.invoke(var0.get(var4))).intValue();
         if (var5 < 0) {
            var1 = var4 + 1;
         } else {
            if (var5 <= 0) {
               return var4;
            }

            var2 = var4 - 1;
         }
      }

      return -(var1 + 1);
   }

   public static final <T extends Comparable<? super T>> int binarySearch(List<? extends T> var0, T var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearch");
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var2, var3);
      --var3;

      while(var2 <= var3) {
         int var4 = var2 + var3 >>> 1;
         int var5 = ComparisonsKt.compareValues((Comparable)var0.get(var4), var1);
         if (var5 < 0) {
            var2 = var4 + 1;
         } else {
            if (var5 <= 0) {
               return var4;
            }

            var3 = var4 - 1;
         }
      }

      return -(var2 + 1);
   }

   public static final <T> int binarySearch(List<? extends T> var0, T var1, Comparator<? super T> var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearch");
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var3, var4);
      --var4;

      while(var3 <= var4) {
         int var5 = var3 + var4 >>> 1;
         int var6 = var2.compare(var0.get(var5), var1);
         if (var6 < 0) {
            var3 = var5 + 1;
         } else {
            if (var6 <= 0) {
               return var5;
            }

            var4 = var5 - 1;
         }
      }

      return -(var3 + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, int var1, int var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Comparable var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Object var1, Comparator var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3, var4);
   }

   public static final <T, K extends Comparable<? super K>> int binarySearchBy(List<? extends T> var0, K var1, int var2, int var3, Function1<? super T, ? extends K> var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearchBy");
      Intrinsics.checkParameterIsNotNull(var4, "selector");
      return CollectionsKt.binarySearch(var0, var2, var3, (Function1)(new Function1<T, Integer>() {
         public final int invoke(T var1x) {
            return ComparisonsKt.compareValues((Comparable)var4.invoke(var1x), var1);
         }
      }));
   }

   // $FF: synthetic method
   public static int binarySearchBy$default(List var0, final Comparable var1, int var2, int var3, final Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = var0.size();
      }

      Intrinsics.checkParameterIsNotNull(var0, "$this$binarySearchBy");
      Intrinsics.checkParameterIsNotNull(var4, "selector");
      return CollectionsKt.binarySearch(var0, var2, var3, (Function1)(new Function1<T, Integer>() {
         public final int invoke(T var1x) {
            return ComparisonsKt.compareValues((Comparable)var4.invoke(var1x), var1);
         }
      }));
   }

   private static final <E> List<E> buildList(int var0, Function1<? super List<E>, Unit> var1) {
      ArrayList var2 = new ArrayList(var0);
      var1.invoke(var2);
      return (List)var2;
   }

   private static final <E> List<E> buildList(Function1<? super List<E>, Unit> var0) {
      ArrayList var1 = new ArrayList();
      var0.invoke(var1);
      return (List)var1;
   }

   private static final <T> boolean containsAll(Collection<? extends T> var0, Collection<? extends T> var1) {
      return var0.containsAll(var1);
   }

   public static final <T> List<T> emptyList() {
      return (List)EmptyList.INSTANCE;
   }

   public static final IntRange getIndices(Collection<?> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indices");
      return new IntRange(0, var0.size() - 1);
   }

   public static final <T> int getLastIndex(List<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndex");
      return var0.size() - 1;
   }

   private static final <C extends Collection<?> & R, R> R ifEmpty(C var0, Function0<? extends R> var1) {
      Object var2 = var0;
      if (var0.isEmpty()) {
         var2 = var1.invoke();
      }

      return var2;
   }

   private static final <T> boolean isNotEmpty(Collection<? extends T> var0) {
      return var0.isEmpty() ^ true;
   }

   private static final <T> boolean isNullOrEmpty(Collection<? extends T> var0) {
      boolean var1;
      if (var0 != null && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static final <T> List<T> listOf() {
      return CollectionsKt.emptyList();
   }

   public static final <T> List<T> listOf(T... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "elements");
      List var1;
      if (var0.length > 0) {
         var1 = ArraysKt.asList(var0);
      } else {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   public static final <T> List<T> listOfNotNull(T var0) {
      List var1;
      if (var0 != null) {
         var1 = CollectionsKt.listOf(var0);
      } else {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   public static final <T> List<T> listOfNotNull(T... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "elements");
      return ArraysKt.filterNotNull(var0);
   }

   private static final <T> List<T> mutableListOf() {
      return (List)(new ArrayList());
   }

   public static final <T> List<T> mutableListOf(T... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "elements");
      List var1;
      if (var0.length == 0) {
         var1 = (List)(new ArrayList());
      } else {
         var1 = (List)(new ArrayList((Collection)(new ArrayAsCollection(var0, true))));
      }

      return var1;
   }

   public static final <T> List<T> optimizeReadOnlyList(List<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$optimizeReadOnlyList");
      int var1 = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            var0 = CollectionsKt.listOf(var0.get(0));
         }
      } else {
         var0 = CollectionsKt.emptyList();
      }

      return var0;
   }

   private static final <T> Collection<T> orEmpty(Collection<? extends T> var0) {
      if (var0 == null) {
         var0 = (Collection)CollectionsKt.emptyList();
      }

      return var0;
   }

   private static final <T> List<T> orEmpty(List<? extends T> var0) {
      if (var0 == null) {
         var0 = CollectionsKt.emptyList();
      }

      return var0;
   }

   private static final void rangeCheck$CollectionsKt__CollectionsKt(int var0, int var1, int var2) {
      StringBuilder var3;
      if (var1 <= var2) {
         if (var1 >= 0) {
            if (var2 > var0) {
               var3 = new StringBuilder();
               var3.append("toIndex (");
               var3.append(var2);
               var3.append(") is greater than size (");
               var3.append(var0);
               var3.append(").");
               throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
            }
         } else {
            var3 = new StringBuilder();
            var3.append("fromIndex (");
            var3.append(var1);
            var3.append(") is less than zero.");
            throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
         }
      } else {
         var3 = new StringBuilder();
         var3.append("fromIndex (");
         var3.append(var1);
         var3.append(") is greater than toIndex (");
         var3.append(var2);
         var3.append(").");
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final void throwCountOverflow() {
      throw (Throwable)(new ArithmeticException("Count overflow has happened."));
   }

   public static final void throwIndexOverflow() {
      throw (Throwable)(new ArithmeticException("Index overflow has happened."));
   }
}
