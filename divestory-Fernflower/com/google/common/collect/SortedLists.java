package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SortedLists {
   private SortedLists() {
   }

   public static <E, K extends Comparable> int binarySearch(List<E> var0, Function<? super E, K> var1, @NullableDecl K var2, SortedLists.KeyPresentBehavior var3, SortedLists.KeyAbsentBehavior var4) {
      return binarySearch(var0, var1, var2, Ordering.natural(), var3, var4);
   }

   public static <E, K> int binarySearch(List<E> var0, Function<? super E, K> var1, @NullableDecl K var2, Comparator<? super K> var3, SortedLists.KeyPresentBehavior var4, SortedLists.KeyAbsentBehavior var5) {
      return binarySearch(Lists.transform(var0, var1), var2, var3, var4, var5);
   }

   public static <E extends Comparable> int binarySearch(List<? extends E> var0, E var1, SortedLists.KeyPresentBehavior var2, SortedLists.KeyAbsentBehavior var3) {
      Preconditions.checkNotNull(var1);
      return binarySearch(var0, (Object)var1, (Comparator)Ordering.natural(), var2, var3);
   }

   public static <E> int binarySearch(List<? extends E> var0, @NullableDecl E var1, Comparator<? super E> var2, SortedLists.KeyPresentBehavior var3, SortedLists.KeyAbsentBehavior var4) {
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var3);
      Preconditions.checkNotNull(var4);
      Object var5 = var0;
      if (!(var0 instanceof RandomAccess)) {
         var5 = Lists.newArrayList((Iterable)var0);
      }

      int var6 = 0;
      int var7 = ((List)var5).size() - 1;

      while(var6 <= var7) {
         int var8 = var6 + var7 >>> 1;
         int var9 = var2.compare(var1, ((List)var5).get(var8));
         if (var9 < 0) {
            var7 = var8 - 1;
         } else {
            if (var9 <= 0) {
               return var6 + var3.resultIndex(var2, var1, ((List)var5).subList(var6, var7 + 1), var8 - var6);
            }

            var6 = var8 + 1;
         }
      }

      return var4.resultIndex(var6);
   }

   static enum KeyAbsentBehavior {
      INVERTED_INSERTION_INDEX,
      NEXT_HIGHER {
         public int resultIndex(int var1) {
            return var1;
         }
      },
      NEXT_LOWER {
         int resultIndex(int var1) {
            return var1 - 1;
         }
      };

      static {
         SortedLists.KeyAbsentBehavior var0 = new SortedLists.KeyAbsentBehavior("INVERTED_INSERTION_INDEX", 2) {
            public int resultIndex(int var1) {
               return var1;
            }
         };
         INVERTED_INSERTION_INDEX = var0;
      }

      private KeyAbsentBehavior() {
      }

      // $FF: synthetic method
      KeyAbsentBehavior(Object var3) {
         this();
      }

      abstract int resultIndex(int var1);
   }

   static enum KeyPresentBehavior {
      ANY_PRESENT {
         <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4) {
            return var4;
         }
      },
      FIRST_AFTER {
         public <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4) {
            return LAST_PRESENT.resultIndex(var1, var2, var3, var4) + 1;
         }
      },
      FIRST_PRESENT {
         <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4) {
            int var5 = 0;

            while(var5 < var4) {
               int var6 = var5 + var4 >>> 1;
               if (var1.compare(var3.get(var6), var2) < 0) {
                  var5 = var6 + 1;
               } else {
                  var4 = var6;
               }
            }

            return var5;
         }
      },
      LAST_BEFORE,
      LAST_PRESENT {
         <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4) {
            int var5 = var3.size() - 1;

            while(var4 < var5) {
               int var6 = var4 + var5 + 1 >>> 1;
               if (var1.compare(var3.get(var6), var2) > 0) {
                  var5 = var6 - 1;
               } else {
                  var4 = var6;
               }
            }

            return var4;
         }
      };

      static {
         SortedLists.KeyPresentBehavior var0 = new SortedLists.KeyPresentBehavior("LAST_BEFORE", 4) {
            public <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4) {
               return FIRST_PRESENT.resultIndex(var1, var2, var3, var4) - 1;
            }
         };
         LAST_BEFORE = var0;
      }

      private KeyPresentBehavior() {
      }

      // $FF: synthetic method
      KeyPresentBehavior(Object var3) {
         this();
      }

      abstract <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4);
   }
}
