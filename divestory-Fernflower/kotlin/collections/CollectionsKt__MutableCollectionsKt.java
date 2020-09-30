package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000^\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0087\b¢\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u001d\u0010\u001e\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010 \u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001d\u0010!\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010\"\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a-\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010#\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010$\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0002\b%\u001a \u0010&\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010'\u001a\u00020(H\u0007\u001a&\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00020*\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010'\u001a\u00020(H\u0007¨\u0006+"},
   d2 = {"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "removeFirst", "(Ljava/util/List;)Ljava/lang/Object;", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "shuffle", "random", "Lkotlin/random/Random;", "shuffled", "", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
   public CollectionsKt__MutableCollectionsKt() {
   }

   public static final <T> boolean addAll(Collection<? super T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$addAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      if (var1 instanceof Collection) {
         return var0.addAll((Collection)var1);
      } else {
         boolean var2 = false;
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            if (var0.add(var3.next())) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public static final <T> boolean addAll(Collection<? super T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$addAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Iterator var3 = var1.iterator();
      boolean var2 = false;

      while(var3.hasNext()) {
         if (var0.add(var3.next())) {
            var2 = true;
         }
      }

      return var2;
   }

   public static final <T> boolean addAll(Collection<? super T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$addAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      return var0.addAll((Collection)ArraysKt.asList(var1));
   }

   private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable<? extends T> var0, Function1<? super T, Boolean> var1, boolean var2) {
      Iterator var4 = var0.iterator();
      boolean var3 = false;

      while(var4.hasNext()) {
         if ((Boolean)var1.invoke(var4.next()) == var2) {
            var4.remove();
            var3 = true;
         }
      }

      return var3;
   }

   private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List<T> var0, Function1<? super T, Boolean> var1, boolean var2) {
      if (!(var0 instanceof RandomAccess)) {
         if (var0 != null) {
            return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(var0), var1, var2);
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableIterable<T>");
         }
      } else {
         int var3 = CollectionsKt.getLastIndex(var0);
         int var5;
         int var7;
         if (var3 >= 0) {
            int var4 = 0;
            var5 = 0;

            while(true) {
               Object var6 = var0.get(var4);
               if ((Boolean)var1.invoke(var6) != var2) {
                  if (var5 != var4) {
                     var0.set(var5, var6);
                  }

                  ++var5;
               }

               var7 = var5;
               if (var4 == var3) {
                  break;
               }

               ++var4;
            }
         } else {
            var7 = 0;
         }

         if (var7 >= var0.size()) {
            return false;
         } else {
            var5 = CollectionsKt.getLastIndex(var0);
            if (var5 >= var7) {
               while(true) {
                  var0.remove(var5);
                  if (var5 == var7) {
                     break;
                  }

                  --var5;
               }
            }

            return true;
         }
      }
   }

   private static final <T> void minusAssign(Collection<? super T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll(var0, var1);
   }

   private static final <T> void minusAssign(Collection<? super T> var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      var0.remove(var1);
   }

   private static final <T> void minusAssign(Collection<? super T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll(var0, var1);
   }

   private static final <T> void minusAssign(Collection<? super T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll(var0, var1);
   }

   private static final <T> void plusAssign(Collection<? super T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      CollectionsKt.addAll(var0, var1);
   }

   private static final <T> void plusAssign(Collection<? super T> var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      var0.add(var1);
   }

   private static final <T> void plusAssign(Collection<? super T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      CollectionsKt.addAll(var0, var1);
   }

   private static final <T> void plusAssign(Collection<? super T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      CollectionsKt.addAll(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "Use removeAt(index) instead.",
      replaceWith = @ReplaceWith(
   expression = "removeAt(index)",
   imports = {}
)
   )
   private static final <T> T remove(List<T> var0, int var1) {
      return var0.remove(var1);
   }

   private static final <T> boolean remove(Collection<? extends T> var0, T var1) {
      if (var0 != null) {
         return TypeIntrinsics.asMutableCollection(var0).remove(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
      }
   }

   public static final <T> boolean removeAll(Iterable<? extends T> var0, Function1<? super T, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeAll");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   public static final <T> boolean removeAll(Collection<? super T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Collection var2 = CollectionsKt.convertToSetForSetOperationWith(var1, (Iterable)var0);
      return TypeIntrinsics.asMutableCollection(var0).removeAll(var2);
   }

   private static final <T> boolean removeAll(Collection<? extends T> var0, Collection<? extends T> var1) {
      if (var0 != null) {
         return TypeIntrinsics.asMutableCollection(var0).removeAll(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
      }
   }

   public static final <T> boolean removeAll(Collection<? super T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Collection var4 = (Collection)SequencesKt.toHashSet(var1);
      boolean var2 = var4.isEmpty();
      boolean var3 = true;
      if (!(var2 ^ true) || !var0.removeAll(var4)) {
         var3 = false;
      }

      return var3;
   }

   public static final <T> boolean removeAll(Collection<? super T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      int var2 = var1.length;
      boolean var3 = false;
      boolean var5;
      if (var2 == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var4 = var3;
      if (var5 ^ true) {
         var4 = var3;
         if (var0.removeAll((Collection)ArraysKt.toHashSet(var1))) {
            var4 = true;
         }
      }

      return var4;
   }

   public static final <T> boolean removeAll(List<T> var0, Function1<? super T, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeAll");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   public static final <T> T removeFirst(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeFirst");
      if (!var0.isEmpty()) {
         return var0.remove(0);
      } else {
         throw (Throwable)(new NoSuchElementException("List is empty."));
      }
   }

   public static final <T> T removeFirstOrNull(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeFirstOrNull");
      Object var1;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.remove(0);
      }

      return var1;
   }

   public static final <T> T removeLast(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeLast");
      if (!var0.isEmpty()) {
         return var0.remove(CollectionsKt.getLastIndex(var0));
      } else {
         throw (Throwable)(new NoSuchElementException("List is empty."));
      }
   }

   public static final <T> T removeLastOrNull(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeLastOrNull");
      Object var1;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.remove(CollectionsKt.getLastIndex(var0));
      }

      return var1;
   }

   public static final <T> boolean retainAll(Iterable<? extends T> var0, Function1<? super T, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$retainAll");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   public static final <T> boolean retainAll(Collection<? super T> var0, Iterable<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$retainAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Collection var2 = CollectionsKt.convertToSetForSetOperationWith(var1, (Iterable)var0);
      return TypeIntrinsics.asMutableCollection(var0).retainAll(var2);
   }

   private static final <T> boolean retainAll(Collection<? extends T> var0, Collection<? extends T> var1) {
      if (var0 != null) {
         return TypeIntrinsics.asMutableCollection(var0).retainAll(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
      }
   }

   public static final <T> boolean retainAll(Collection<? super T> var0, Sequence<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$retainAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Collection var2 = (Collection)SequencesKt.toHashSet(var1);
      return var2.isEmpty() ^ true ? var0.retainAll(var2) : retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   public static final <T> boolean retainAll(Collection<? super T> var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$retainAll");
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      boolean var2;
      if (var1.length == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2 ^ true ? var0.retainAll((Collection)ArraysKt.toHashSet(var1)) : retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   public static final <T> boolean retainAll(List<T> var0, Function1<? super T, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$retainAll");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection<?> var0) {
      boolean var1 = var0.isEmpty();
      var0.clear();
      return var1 ^ true;
   }

   public static final <T> void shuffle(List<T> var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$shuffle");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      for(int var2 = CollectionsKt.getLastIndex(var0); var2 >= 1; --var2) {
         int var3 = var1.nextInt(var2 + 1);
         Object var4 = var0.get(var2);
         var0.set(var2, var0.get(var3));
         var0.set(var3, var4);
      }

   }

   public static final <T> List<T> shuffled(Iterable<? extends T> var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$shuffled");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      List var2 = CollectionsKt.toMutableList(var0);
      CollectionsKt.shuffle(var2, var1);
      return var2;
   }
}
