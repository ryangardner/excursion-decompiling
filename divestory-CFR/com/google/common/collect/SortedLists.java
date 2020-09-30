/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SortedLists {
    private SortedLists() {
    }

    public static <E, K extends Comparable> int binarySearch(List<E> list, Function<? super E, K> function, @NullableDecl K k, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return SortedLists.binarySearch(list, function, k, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E, K> int binarySearch(List<E> list, Function<? super E, K> function, @NullableDecl K k, Comparator<? super K> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return SortedLists.binarySearch(Lists.transform(list, function), k, comparator, keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E extends Comparable> int binarySearch(List<? extends E> list, E e, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(e);
        return SortedLists.binarySearch(list, e, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E> int binarySearch(List<? extends E> list, @NullableDecl E e, Comparator<? super E> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(keyPresentBehavior);
        Preconditions.checkNotNull(keyAbsentBehavior);
        List<E> list2 = list;
        if (!(list instanceof RandomAccess)) {
            list2 = Lists.newArrayList(list);
        }
        int n = 0;
        int n2 = list2.size() - 1;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = comparator.compare(e, list2.get(n3));
            if (n4 < 0) {
                n2 = n3 - 1;
                continue;
            }
            if (n4 <= 0) return n + keyPresentBehavior.resultIndex(comparator, e, list2.subList(n, n2 + 1), n3 - n);
            n = n3 + 1;
        }
        return keyAbsentBehavior.resultIndex(n);
    }

    static abstract class KeyAbsentBehavior
    extends Enum<KeyAbsentBehavior> {
        private static final /* synthetic */ KeyAbsentBehavior[] $VALUES;
        public static final /* enum */ KeyAbsentBehavior INVERTED_INSERTION_INDEX;
        public static final /* enum */ KeyAbsentBehavior NEXT_HIGHER;
        public static final /* enum */ KeyAbsentBehavior NEXT_LOWER;

        static {
            KeyAbsentBehavior keyAbsentBehavior;
            NEXT_LOWER = new KeyAbsentBehavior(){

                @Override
                int resultIndex(int n) {
                    return n - 1;
                }
            };
            NEXT_HIGHER = new KeyAbsentBehavior(){

                @Override
                public int resultIndex(int n) {
                    return n;
                }
            };
            INVERTED_INSERTION_INDEX = keyAbsentBehavior = new KeyAbsentBehavior(){

                @Override
                public int resultIndex(int n) {
                    return n;
                }
            };
            $VALUES = new KeyAbsentBehavior[]{NEXT_LOWER, NEXT_HIGHER, keyAbsentBehavior};
        }

        public static KeyAbsentBehavior valueOf(String string2) {
            return Enum.valueOf(KeyAbsentBehavior.class, string2);
        }

        public static KeyAbsentBehavior[] values() {
            return (KeyAbsentBehavior[])$VALUES.clone();
        }

        abstract int resultIndex(int var1);

    }

    static abstract class KeyPresentBehavior
    extends Enum<KeyPresentBehavior> {
        private static final /* synthetic */ KeyPresentBehavior[] $VALUES;
        public static final /* enum */ KeyPresentBehavior ANY_PRESENT;
        public static final /* enum */ KeyPresentBehavior FIRST_AFTER;
        public static final /* enum */ KeyPresentBehavior FIRST_PRESENT;
        public static final /* enum */ KeyPresentBehavior LAST_BEFORE;
        public static final /* enum */ KeyPresentBehavior LAST_PRESENT;

        static {
            KeyPresentBehavior keyPresentBehavior;
            ANY_PRESENT = new KeyPresentBehavior(){

                @Override
                <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                    return n;
                }
            };
            LAST_PRESENT = new KeyPresentBehavior(){

                @Override
                <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                    int n2 = list.size() - 1;
                    while (n < n2) {
                        int n3 = n + n2 + 1 >>> 1;
                        if (comparator.compare(list.get(n3), e) > 0) {
                            n2 = n3 - 1;
                            continue;
                        }
                        n = n3;
                    }
                    return n;
                }
            };
            FIRST_PRESENT = new KeyPresentBehavior(){

                @Override
                <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                    int n2 = 0;
                    while (n2 < n) {
                        int n3 = n2 + n >>> 1;
                        if (comparator.compare(list.get(n3), e) < 0) {
                            n2 = n3 + 1;
                            continue;
                        }
                        n = n3;
                    }
                    return n2;
                }
            };
            FIRST_AFTER = new KeyPresentBehavior(){

                @Override
                public <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                    return LAST_PRESENT.resultIndex(comparator, e, list, n) + 1;
                }
            };
            LAST_BEFORE = keyPresentBehavior = new KeyPresentBehavior(){

                @Override
                public <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                    return FIRST_PRESENT.resultIndex(comparator, e, list, n) - 1;
                }
            };
            $VALUES = new KeyPresentBehavior[]{ANY_PRESENT, LAST_PRESENT, FIRST_PRESENT, FIRST_AFTER, keyPresentBehavior};
        }

        public static KeyPresentBehavior valueOf(String string2) {
            return Enum.valueOf(KeyPresentBehavior.class, string2);
        }

        public static KeyPresentBehavior[] values() {
            return (KeyPresentBehavior[])$VALUES.clone();
        }

        abstract <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4);

    }

}

