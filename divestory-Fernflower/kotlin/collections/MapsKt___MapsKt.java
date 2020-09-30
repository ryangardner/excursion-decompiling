package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000h\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001aG\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001aG\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aY\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b\u001ar\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001aG\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b\u001aS\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001aY\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b\u001ar\u0010\u001e\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001al\u0010\u001f\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001ae\u0010 \u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0087\b\u001ai\u0010#\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&H\u0087\b\u001ae\u0010'\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001af\u0010(\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&\u001a$\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aV\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010+*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002H+2\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b¢\u0006\u0002\u0010,\u001a6\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030.0\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004¨\u0006/"},
   d2 = {"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "flatMap", "", "R", "transform", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "forEach", "", "action", "map", "mapNotNull", "", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "minBy", "minWith", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/MapsKt"
)
class MapsKt___MapsKt extends MapsKt__MapsKt {
   public MapsKt___MapsKt() {
   }

   public static final <K, V> boolean all(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$all");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      if (var0.isEmpty()) {
         return true;
      } else {
         Iterator var2 = var0.entrySet().iterator();

         do {
            if (!var2.hasNext()) {
               return true;
            }
         } while((Boolean)var1.invoke((Entry)var2.next()));

         return false;
      }
   }

   public static final <K, V> boolean any(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$any");
      return var0.isEmpty() ^ true;
   }

   public static final <K, V> boolean any(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$any");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      if (var0.isEmpty()) {
         return false;
      } else {
         Iterator var2 = var0.entrySet().iterator();

         do {
            if (!var2.hasNext()) {
               return false;
            }
         } while(!(Boolean)var1.invoke((Entry)var2.next()));

         return true;
      }
   }

   private static final <K, V> Iterable<Entry<K, V>> asIterable(Map<? extends K, ? extends V> var0) {
      return (Iterable)var0.entrySet();
   }

   public static final <K, V> Sequence<Entry<K, V>> asSequence(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asSequence");
      return CollectionsKt.asSequence((Iterable)var0.entrySet());
   }

   private static final <K, V> int count(Map<? extends K, ? extends V> var0) {
      return var0.size();
   }

   public static final <K, V> int count(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$count");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      boolean var2 = var0.isEmpty();
      int var3 = 0;
      if (var2) {
         return 0;
      } else {
         Iterator var4 = var0.entrySet().iterator();

         while(var4.hasNext()) {
            if ((Boolean)var1.invoke((Entry)var4.next())) {
               ++var3;
            }
         }

         return var3;
      }
   }

   public static final <K, V, R> List<R> flatMap(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatMap");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList());
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         CollectionsKt.addAll(var2, (Iterable)var1.invoke((Entry)var3.next()));
      }

      return (List)var2;
   }

   public static final <K, V, R, C extends Collection<? super R>> C flatMapTo(Map<? extends K, ? extends V> var0, C var1, Function1<? super Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatMapTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         CollectionsKt.addAll(var1, (Iterable)var2.invoke((Entry)var3.next()));
      }

      return var1;
   }

   public static final <K, V> void forEach(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEach");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      Iterator var2 = var0.entrySet().iterator();

      while(var2.hasNext()) {
         var1.invoke((Entry)var2.next());
      }

   }

   public static final <K, V, R> List<R> map(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$map");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList(var0.size()));
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         var2.add(var1.invoke((Entry)var3.next()));
      }

      return (List)var2;
   }

   public static final <K, V, R> List<R> mapNotNull(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapNotNull");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList());
      Iterator var4 = var0.entrySet().iterator();

      while(var4.hasNext()) {
         Object var3 = var1.invoke((Entry)var4.next());
         if (var3 != null) {
            var2.add(var3);
         }
      }

      return (List)var2;
   }

   public static final <K, V, R, C extends Collection<? super R>> C mapNotNullTo(Map<? extends K, ? extends V> var0, C var1, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapNotNullTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Object var4 = var2.invoke((Entry)var3.next());
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public static final <K, V, R, C extends Collection<? super R>> C mapTo(Map<? extends K, ? extends V> var0, C var1, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         var1.add(var2.invoke((Entry)var3.next()));
      }

      return var1;
   }

   private static final <K, V, R extends Comparable<? super R>> Entry<K, V> maxBy(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Iterator var2 = ((Iterable)var0.entrySet()).iterator();
      Object var8;
      if (!var2.hasNext()) {
         var8 = null;
      } else {
         var8 = var2.next();
         if (var2.hasNext()) {
            Comparable var3 = (Comparable)var1.invoke(var8);
            Object var4 = var8;

            do {
               Object var5 = var2.next();
               Comparable var6 = (Comparable)var1.invoke(var5);
               var8 = var4;
               Comparable var7 = var3;
               if (var3.compareTo(var6) < 0) {
                  var8 = var5;
                  var7 = var6;
               }

               var4 = var8;
               var3 = var7;
            } while(var2.hasNext());
         }
      }

      return (Entry)var8;
   }

   private static final <K, V> Entry<K, V> maxWith(Map<? extends K, ? extends V> var0, Comparator<? super Entry<? extends K, ? extends V>> var1) {
      return (Entry)CollectionsKt.maxWith((Iterable)var0.entrySet(), var1);
   }

   public static final <K, V, R extends Comparable<? super R>> Entry<K, V> minBy(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minBy");
      Intrinsics.checkParameterIsNotNull(var1, "selector");
      Iterator var2 = ((Iterable)var0.entrySet()).iterator();
      Object var8;
      if (!var2.hasNext()) {
         var8 = null;
      } else {
         var8 = var2.next();
         if (var2.hasNext()) {
            Comparable var3 = (Comparable)var1.invoke(var8);
            Object var4 = var8;

            do {
               Object var5 = var2.next();
               Comparable var6 = (Comparable)var1.invoke(var5);
               var8 = var4;
               Comparable var7 = var3;
               if (var3.compareTo(var6) > 0) {
                  var8 = var5;
                  var7 = var6;
               }

               var4 = var8;
               var3 = var7;
            } while(var2.hasNext());
         }
      }

      return (Entry)var8;
   }

   public static final <K, V> Entry<K, V> minWith(Map<? extends K, ? extends V> var0, Comparator<? super Entry<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minWith");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      return (Entry)CollectionsKt.minWith((Iterable)var0.entrySet(), var1);
   }

   public static final <K, V> boolean none(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$none");
      return var0.isEmpty();
   }

   public static final <K, V> boolean none(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$none");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      if (var0.isEmpty()) {
         return true;
      } else {
         Iterator var2 = var0.entrySet().iterator();

         do {
            if (!var2.hasNext()) {
               return true;
            }
         } while(!(Boolean)var1.invoke((Entry)var2.next()));

         return false;
      }
   }

   public static final <K, V, M extends Map<? extends K, ? extends V>> M onEach(M var0, Function1<? super Entry<? extends K, ? extends V>, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$onEach");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      Iterator var2 = var0.entrySet().iterator();

      while(var2.hasNext()) {
         var1.invoke((Entry)var2.next());
      }

      return var0;
   }

   public static final <K, V> List<Pair<K, V>> toList(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toList");
      if (var0.size() == 0) {
         return CollectionsKt.emptyList();
      } else {
         Iterator var1 = var0.entrySet().iterator();
         if (!var1.hasNext()) {
            return CollectionsKt.emptyList();
         } else {
            Entry var2 = (Entry)var1.next();
            if (!var1.hasNext()) {
               return CollectionsKt.listOf(new Pair(var2.getKey(), var2.getValue()));
            } else {
               ArrayList var3 = new ArrayList(var0.size());
               var3.add(new Pair(var2.getKey(), var2.getValue()));

               do {
                  var2 = (Entry)var1.next();
                  var3.add(new Pair(var2.getKey(), var2.getValue()));
               } while(var1.hasNext());

               return (List)var3;
            }
         }
      }
   }
}
