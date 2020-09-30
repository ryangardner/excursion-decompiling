package com.google.common.collect;

public final class LinkedHashMultiset<E> extends AbstractMapBasedMultiset<E> {
   LinkedHashMultiset(int var1) {
      super(var1);
   }

   public static <E> LinkedHashMultiset<E> create() {
      return create(3);
   }

   public static <E> LinkedHashMultiset<E> create(int var0) {
      return new LinkedHashMultiset(var0);
   }

   public static <E> LinkedHashMultiset<E> create(Iterable<? extends E> var0) {
      LinkedHashMultiset var1 = create(Multisets.inferDistinctElements(var0));
      Iterables.addAll(var1, var0);
      return var1;
   }

   void init(int var1) {
      this.backingMap = new ObjectCountLinkedHashMap(var1);
   }
}
