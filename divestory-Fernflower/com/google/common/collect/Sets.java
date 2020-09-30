package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Sets {
   private Sets() {
   }

   public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> var0) {
      return Sets.CartesianSet.create(var0);
   }

   @SafeVarargs
   public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... var0) {
      return cartesianProduct(Arrays.asList(var0));
   }

   public static <E> Set<Set<E>> combinations(Set<E> var0, final int var1) {
      final ImmutableMap var3 = Maps.indexMap(var0);
      CollectPreconditions.checkNonnegative(var1, "size");
      boolean var2;
      if (var1 <= var3.size()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "size (%s) must be <= set.size() (%s)", var1, var3.size());
      if (var1 == 0) {
         return ImmutableSet.of(ImmutableSet.of());
      } else {
         return (Set)(var1 == var3.size() ? ImmutableSet.of(var3.keySet()) : new AbstractSet<Set<E>>() {
            public boolean contains(@NullableDecl Object var1x) {
               boolean var2 = var1x instanceof Set;
               boolean var3x = false;
               boolean var4 = var3x;
               if (var2) {
                  Set var5 = (Set)var1x;
                  var4 = var3x;
                  if (var5.size() == var1) {
                     var4 = var3x;
                     if (var3.keySet().containsAll(var5)) {
                        var4 = true;
                     }
                  }
               }

               return var4;
            }

            public Iterator<Set<E>> iterator() {
               return new AbstractIterator<Set<E>>() {
                  final BitSet bits = new BitSet(var3.size());

                  protected Set<E> computeNext() {
                     if (this.bits.isEmpty()) {
                        this.bits.set(0, var1);
                     } else {
                        int var1x = this.bits.nextSetBit(0);
                        int var2 = this.bits.nextClearBit(var1x);
                        if (var2 == var3.size()) {
                           return (Set)this.endOfData();
                        }

                        BitSet var3x = this.bits;
                        var1x = var2 - var1x - 1;
                        var3x.set(0, var1x);
                        this.bits.clear(var1x, var2);
                        this.bits.set(var2);
                     }

                     return new AbstractSet<E>((BitSet)this.bits.clone()) {
                        // $FF: synthetic field
                        final BitSet val$copy;

                        {
                           this.val$copy = var2;
                        }

                        public boolean contains(@NullableDecl Object var1x) {
                           Integer var3x = (Integer)var3.get(var1x);
                           boolean var2;
                           if (var3x != null && this.val$copy.get(var3x)) {
                              var2 = true;
                           } else {
                              var2 = false;
                           }

                           return var2;
                        }

                        public Iterator<E> iterator() {
                           return new AbstractIterator<E>() {
                              int i = -1;

                              protected E computeNext() {
                                 int var1x = val$copy.nextSetBit(this.i + 1);
                                 this.i = var1x;
                                 return var1x == -1 ? this.endOfData() : var3.keySet().asList().get(this.i);
                              }
                           };
                        }

                        public int size() {
                           return var1;
                        }
                     };
                  }
               };
            }

            public int size() {
               return IntMath.binomial(var3.size(), var1);
            }

            public String toString() {
               StringBuilder var1x = new StringBuilder();
               var1x.append("Sets.combinations(");
               var1x.append(var3.keySet());
               var1x.append(", ");
               var1x.append(var1);
               var1x.append(")");
               return var1x.toString();
            }
         });
      }
   }

   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> var0) {
      if (var0 instanceof EnumSet) {
         return EnumSet.complementOf((EnumSet)var0);
      } else {
         Preconditions.checkArgument(var0.isEmpty() ^ true, "collection is empty; use the other version of this method");
         return makeComplementByHand(var0, ((Enum)var0.iterator().next()).getDeclaringClass());
      }
   }

   public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> var0, Class<E> var1) {
      Preconditions.checkNotNull(var0);
      EnumSet var2;
      if (var0 instanceof EnumSet) {
         var2 = EnumSet.complementOf((EnumSet)var0);
      } else {
         var2 = makeComplementByHand(var0, var1);
      }

      return var2;
   }

   public static <E> Sets.SetView<E> difference(final Set<E> var0, final Set<?> var1) {
      Preconditions.checkNotNull(var0, "set1");
      Preconditions.checkNotNull(var1, "set2");
      return new Sets.SetView<E>() {
         public boolean contains(Object var1x) {
            boolean var2;
            if (var0.contains(var1x) && !var1.contains(var1x)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean isEmpty() {
            return var1.containsAll(var0);
         }

         public UnmodifiableIterator<E> iterator() {
            return new AbstractIterator<E>() {
               final Iterator<E> itr = var0.iterator();

               protected E computeNext() {
                  while(true) {
                     if (this.itr.hasNext()) {
                        Object var1x = this.itr.next();
                        if (var1.contains(var1x)) {
                           continue;
                        }

                        return var1x;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            Iterator var1x = var0.iterator();
            int var2 = 0;

            while(var1x.hasNext()) {
               Object var3 = var1x.next();
               if (!var1.contains(var3)) {
                  ++var2;
               }
            }

            return var2;
         }
      };
   }

   static boolean equalsImpl(Set<?> var0, @NullableDecl Object var1) {
      boolean var2 = true;
      if (var0 == var1) {
         return true;
      } else if (var1 instanceof Set) {
         Set var5 = (Set)var1;

         label27: {
            boolean var3;
            try {
               if (var0.size() != var5.size()) {
                  break label27;
               }

               var3 = var0.containsAll(var5);
            } catch (ClassCastException | NullPointerException var4) {
               return false;
            }

            if (var3) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      } else {
         return false;
      }
   }

   public static <E> NavigableSet<E> filter(NavigableSet<E> var0, Predicate<? super E> var1) {
      if (var0 instanceof Sets.FilteredSet) {
         Sets.FilteredSet var2 = (Sets.FilteredSet)var0;
         var1 = Predicates.and(var2.predicate, var1);
         return new Sets.FilteredNavigableSet((NavigableSet)var2.unfiltered, var1);
      } else {
         return new Sets.FilteredNavigableSet((NavigableSet)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1));
      }
   }

   public static <E> Set<E> filter(Set<E> var0, Predicate<? super E> var1) {
      if (var0 instanceof SortedSet) {
         return filter((SortedSet)var0, var1);
      } else if (var0 instanceof Sets.FilteredSet) {
         Sets.FilteredSet var2 = (Sets.FilteredSet)var0;
         var1 = Predicates.and(var2.predicate, var1);
         return new Sets.FilteredSet((Set)var2.unfiltered, var1);
      } else {
         return new Sets.FilteredSet((Set)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1));
      }
   }

   public static <E> SortedSet<E> filter(SortedSet<E> var0, Predicate<? super E> var1) {
      if (var0 instanceof Sets.FilteredSet) {
         Sets.FilteredSet var2 = (Sets.FilteredSet)var0;
         var1 = Predicates.and(var2.predicate, var1);
         return new Sets.FilteredSortedSet((SortedSet)var2.unfiltered, var1);
      } else {
         return new Sets.FilteredSortedSet((SortedSet)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1));
      }
   }

   static int hashCodeImpl(Set<?> var0) {
      Iterator var1 = var0.iterator();

      int var2;
      int var3;
      for(var2 = 0; var1.hasNext(); var2 += var3) {
         Object var4 = var1.next();
         if (var4 != null) {
            var3 = var4.hashCode();
         } else {
            var3 = 0;
         }
      }

      return var2;
   }

   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E var0, E... var1) {
      return ImmutableEnumSet.asImmutable(EnumSet.of(var0, var1));
   }

   public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> var0) {
      if (var0 instanceof ImmutableEnumSet) {
         return (ImmutableEnumSet)var0;
      } else if (var0 instanceof Collection) {
         Collection var3 = (Collection)var0;
         return var3.isEmpty() ? ImmutableSet.of() : ImmutableEnumSet.asImmutable(EnumSet.copyOf(var3));
      } else {
         Iterator var1 = var0.iterator();
         if (var1.hasNext()) {
            EnumSet var2 = EnumSet.of((Enum)var1.next());
            Iterators.addAll(var2, var1);
            return ImmutableEnumSet.asImmutable(var2);
         } else {
            return ImmutableSet.of();
         }
      }
   }

   public static <E> Sets.SetView<E> intersection(final Set<E> var0, final Set<?> var1) {
      Preconditions.checkNotNull(var0, "set1");
      Preconditions.checkNotNull(var1, "set2");
      return new Sets.SetView<E>() {
         public boolean contains(Object var1x) {
            boolean var2;
            if (var0.contains(var1x) && var1.contains(var1x)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean containsAll(Collection<?> var1x) {
            boolean var2;
            if (var0.containsAll(var1x) && var1.containsAll(var1x)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean isEmpty() {
            return Collections.disjoint(var1, var0);
         }

         public UnmodifiableIterator<E> iterator() {
            return new AbstractIterator<E>() {
               final Iterator<E> itr = var0.iterator();

               protected E computeNext() {
                  while(true) {
                     if (this.itr.hasNext()) {
                        Object var1x = this.itr.next();
                        if (!var1.contains(var1x)) {
                           continue;
                        }

                        return var1x;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         public int size() {
            Iterator var1x = var0.iterator();
            int var2 = 0;

            while(var1x.hasNext()) {
               Object var3 = var1x.next();
               if (var1.contains(var3)) {
                  ++var2;
               }
            }

            return var2;
         }
      };
   }

   private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> var0, Class<E> var1) {
      EnumSet var2 = EnumSet.allOf(var1);
      var2.removeAll(var0);
      return var2;
   }

   public static <E> Set<E> newConcurrentHashSet() {
      return Collections.newSetFromMap(new ConcurrentHashMap());
   }

   public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> var0) {
      Set var1 = newConcurrentHashSet();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
      return new CopyOnWriteArraySet();
   }

   public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> var0) {
      Object var1;
      if (var0 instanceof Collection) {
         var1 = Collections2.cast(var0);
      } else {
         var1 = Lists.newArrayList(var0);
      }

      return new CopyOnWriteArraySet((Collection)var1);
   }

   public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> var0, Class<E> var1) {
      EnumSet var2 = EnumSet.noneOf(var1);
      Iterables.addAll(var2, var0);
      return var2;
   }

   public static <E> HashSet<E> newHashSet() {
      return new HashSet();
   }

   public static <E> HashSet<E> newHashSet(Iterable<? extends E> var0) {
      HashSet var1;
      if (var0 instanceof Collection) {
         var1 = new HashSet(Collections2.cast(var0));
      } else {
         var1 = newHashSet(var0.iterator());
      }

      return var1;
   }

   public static <E> HashSet<E> newHashSet(Iterator<? extends E> var0) {
      HashSet var1 = newHashSet();
      Iterators.addAll(var1, var0);
      return var1;
   }

   public static <E> HashSet<E> newHashSet(E... var0) {
      HashSet var1 = newHashSetWithExpectedSize(var0.length);
      Collections.addAll(var1, var0);
      return var1;
   }

   public static <E> HashSet<E> newHashSetWithExpectedSize(int var0) {
      return new HashSet(Maps.capacity(var0));
   }

   public static <E> Set<E> newIdentityHashSet() {
      return Collections.newSetFromMap(Maps.newIdentityHashMap());
   }

   public static <E> LinkedHashSet<E> newLinkedHashSet() {
      return new LinkedHashSet();
   }

   public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> var0) {
      if (var0 instanceof Collection) {
         return new LinkedHashSet(Collections2.cast(var0));
      } else {
         LinkedHashSet var1 = newLinkedHashSet();
         Iterables.addAll(var1, var0);
         return var1;
      }
   }

   public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int var0) {
      return new LinkedHashSet(Maps.capacity(var0));
   }

   @Deprecated
   public static <E> Set<E> newSetFromMap(Map<E, Boolean> var0) {
      return Collections.newSetFromMap(var0);
   }

   public static <E extends Comparable> TreeSet<E> newTreeSet() {
      return new TreeSet();
   }

   public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> var0) {
      TreeSet var1 = newTreeSet();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> var0) {
      return new TreeSet((Comparator)Preconditions.checkNotNull(var0));
   }

   public static <E> Set<Set<E>> powerSet(Set<E> var0) {
      return new Sets.PowerSet(var0);
   }

   static boolean removeAllImpl(Set<?> var0, Collection<?> var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = var1;
      if (var1 instanceof Multiset) {
         var2 = ((Multiset)var1).elementSet();
      }

      return var2 instanceof Set && ((Collection)var2).size() > var0.size() ? Iterators.removeAll(var0.iterator(), (Collection)var2) : removeAllImpl(var0, ((Collection)var2).iterator());
   }

   static boolean removeAllImpl(Set<?> var0, Iterator<?> var1) {
      boolean var2;
      for(var2 = false; var1.hasNext(); var2 |= var0.remove(var1.next())) {
      }

      return var2;
   }

   public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> var0, Range<K> var1) {
      Comparator var2 = var0.comparator();
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var6;
      if (var2 != null && var0.comparator() != Ordering.natural() && var1.hasLowerBound() && var1.hasUpperBound()) {
         if (var0.comparator().compare(var1.lowerEndpoint(), var1.upperEndpoint()) <= 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "set is using a custom comparator which is inconsistent with the natural ordering.");
      }

      Comparable var8;
      if (var1.hasLowerBound() && var1.hasUpperBound()) {
         Comparable var7 = var1.lowerEndpoint();
         if (var1.lowerBoundType() == BoundType.CLOSED) {
            var6 = true;
         } else {
            var6 = false;
         }

         var8 = var1.upperEndpoint();
         if (var1.upperBoundType() != BoundType.CLOSED) {
            var5 = false;
         }

         return var0.subSet(var7, var6, var8, var5);
      } else if (var1.hasLowerBound()) {
         var8 = var1.lowerEndpoint();
         if (var1.lowerBoundType() == BoundType.CLOSED) {
            var6 = var3;
         } else {
            var6 = false;
         }

         return var0.tailSet(var8, var6);
      } else if (var1.hasUpperBound()) {
         var8 = var1.upperEndpoint();
         if (var1.upperBoundType() == BoundType.CLOSED) {
            var6 = var4;
         } else {
            var6 = false;
         }

         return var0.headSet(var8, var6);
      } else {
         return (NavigableSet)Preconditions.checkNotNull(var0);
      }
   }

   public static <E> Sets.SetView<E> symmetricDifference(final Set<? extends E> var0, final Set<? extends E> var1) {
      Preconditions.checkNotNull(var0, "set1");
      Preconditions.checkNotNull(var1, "set2");
      return new Sets.SetView<E>() {
         public boolean contains(Object var1x) {
            boolean var2 = var0.contains(var1x);
            return var1.contains(var1x) ^ var2;
         }

         public boolean isEmpty() {
            return var0.equals(var1);
         }

         public UnmodifiableIterator<E> iterator() {
            return new AbstractIterator<E>(var0.iterator(), var1.iterator()) {
               // $FF: synthetic field
               final Iterator val$itr1;
               // $FF: synthetic field
               final Iterator val$itr2;

               {
                  this.val$itr1 = var2;
                  this.val$itr2 = var3;
               }

               public E computeNext() {
                  while(true) {
                     Object var1x;
                     if (this.val$itr1.hasNext()) {
                        var1x = this.val$itr1.next();
                        if (var1.contains(var1x)) {
                           continue;
                        }

                        return var1x;
                     }

                     do {
                        if (!this.val$itr2.hasNext()) {
                           return this.endOfData();
                        }

                        var1x = this.val$itr2.next();
                     } while(var0.contains(var1x));

                     return var1x;
                  }
               }
            };
         }

         public int size() {
            Iterator var1x = var0.iterator();
            int var2 = 0;

            Object var3;
            while(var1x.hasNext()) {
               var3 = var1x.next();
               if (!var1.contains(var3)) {
                  ++var2;
               }
            }

            var1x = var1.iterator();

            while(var1x.hasNext()) {
               var3 = var1x.next();
               if (!var0.contains(var3)) {
                  ++var2;
               }
            }

            return var2;
         }
      };
   }

   public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> var0) {
      return Synchronized.navigableSet(var0);
   }

   public static <E> Sets.SetView<E> union(final Set<? extends E> var0, final Set<? extends E> var1) {
      Preconditions.checkNotNull(var0, "set1");
      Preconditions.checkNotNull(var1, "set2");
      return new Sets.SetView<E>() {
         public boolean contains(Object var1x) {
            boolean var2;
            if (!var0.contains(var1x) && !var1.contains(var1x)) {
               var2 = false;
            } else {
               var2 = true;
            }

            return var2;
         }

         public <S extends Set<E>> S copyInto(S var1x) {
            var1x.addAll(var0);
            var1x.addAll(var1);
            return var1x;
         }

         public ImmutableSet<E> immutableCopy() {
            return (new ImmutableSet.Builder()).addAll((Iterable)var0).addAll((Iterable)var1).build();
         }

         public boolean isEmpty() {
            boolean var1x;
            if (var0.isEmpty() && var1.isEmpty()) {
               var1x = true;
            } else {
               var1x = false;
            }

            return var1x;
         }

         public UnmodifiableIterator<E> iterator() {
            return new AbstractIterator<E>() {
               final Iterator<? extends E> itr1 = var0.iterator();
               final Iterator<? extends E> itr2 = var1.iterator();

               protected E computeNext() {
                  if (this.itr1.hasNext()) {
                     return this.itr1.next();
                  } else {
                     Object var1x;
                     do {
                        if (!this.itr2.hasNext()) {
                           return this.endOfData();
                        }

                        var1x = this.itr2.next();
                     } while(var0.contains(var1x));

                     return var1x;
                  }
               }
            };
         }

         public int size() {
            int var1x = var0.size();
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               Object var3 = var2.next();
               if (!var0.contains(var3)) {
                  ++var1x;
               }
            }

            return var1x;
         }
      };
   }

   public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> var0) {
      return (NavigableSet)(!(var0 instanceof ImmutableCollection) && !(var0 instanceof Sets.UnmodifiableNavigableSet) ? new Sets.UnmodifiableNavigableSet(var0) : var0);
   }

   private static final class CartesianSet<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
      private final transient ImmutableList<ImmutableSet<E>> axes;
      private final transient CartesianList<E> delegate;

      private CartesianSet(ImmutableList<ImmutableSet<E>> var1, CartesianList<E> var2) {
         this.axes = var1;
         this.delegate = var2;
      }

      static <E> Set<List<E>> create(List<? extends Set<? extends E>> var0) {
         ImmutableList.Builder var1 = new ImmutableList.Builder(var0.size());
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            ImmutableSet var3 = ImmutableSet.copyOf((Collection)((Set)var2.next()));
            if (var3.isEmpty()) {
               return ImmutableSet.of();
            }

            var1.add((Object)var3);
         }

         final ImmutableList var4 = var1.build();
         return new Sets.CartesianSet(var4, new CartesianList(new ImmutableList<List<E>>() {
            public List<E> get(int var1) {
               return ((ImmutableSet)var4.get(var1)).asList();
            }

            boolean isPartialView() {
               return true;
            }

            public int size() {
               return var4.size();
            }
         }));
      }

      protected Collection<List<E>> delegate() {
         return this.delegate;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Sets.CartesianSet) {
            Sets.CartesianSet var2 = (Sets.CartesianSet)var1;
            return this.axes.equals(var2.axes);
         } else {
            return super.equals(var1);
         }
      }

      public int hashCode() {
         int var1 = this.size();
         byte var2 = 1;
         --var1;

         int var3;
         for(var3 = 0; var3 < this.axes.size(); ++var3) {
            var1 *= 31;
         }

         UnmodifiableIterator var4 = this.axes.iterator();

         Set var5;
         for(var3 = var2; var4.hasNext(); var3 = var3 * 31 + this.size() / var5.size() * var5.hashCode()) {
            var5 = (Set)var4.next();
         }

         return var3 + var1;
      }
   }

   static class DescendingSet<E> extends ForwardingNavigableSet<E> {
      private final NavigableSet<E> forward;

      DescendingSet(NavigableSet<E> var1) {
         this.forward = var1;
      }

      private static <T> Ordering<T> reverse(Comparator<T> var0) {
         return Ordering.from(var0).reverse();
      }

      public E ceiling(E var1) {
         return this.forward.floor(var1);
      }

      public Comparator<? super E> comparator() {
         Comparator var1 = this.forward.comparator();
         return var1 == null ? Ordering.natural().reverse() : reverse(var1);
      }

      protected NavigableSet<E> delegate() {
         return this.forward;
      }

      public Iterator<E> descendingIterator() {
         return this.forward.iterator();
      }

      public NavigableSet<E> descendingSet() {
         return this.forward;
      }

      public E first() {
         return this.forward.last();
      }

      public E floor(E var1) {
         return this.forward.ceiling(var1);
      }

      public NavigableSet<E> headSet(E var1, boolean var2) {
         return this.forward.tailSet(var1, var2).descendingSet();
      }

      public SortedSet<E> headSet(E var1) {
         return this.standardHeadSet(var1);
      }

      public E higher(E var1) {
         return this.forward.lower(var1);
      }

      public Iterator<E> iterator() {
         return this.forward.descendingIterator();
      }

      public E last() {
         return this.forward.first();
      }

      public E lower(E var1) {
         return this.forward.higher(var1);
      }

      public E pollFirst() {
         return this.forward.pollLast();
      }

      public E pollLast() {
         return this.forward.pollFirst();
      }

      public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
         return this.forward.subSet(var3, var4, var1, var2).descendingSet();
      }

      public SortedSet<E> subSet(E var1, E var2) {
         return this.standardSubSet(var1, var2);
      }

      public NavigableSet<E> tailSet(E var1, boolean var2) {
         return this.forward.headSet(var1, var2).descendingSet();
      }

      public SortedSet<E> tailSet(E var1) {
         return this.standardTailSet(var1);
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public <T> T[] toArray(T[] var1) {
         return this.standardToArray(var1);
      }

      public String toString() {
         return this.standardToString();
      }
   }

   private static class FilteredNavigableSet<E> extends Sets.FilteredSortedSet<E> implements NavigableSet<E> {
      FilteredNavigableSet(NavigableSet<E> var1, Predicate<? super E> var2) {
         super(var1, var2);
      }

      public E ceiling(E var1) {
         return Iterables.find(this.unfiltered().tailSet(var1, true), this.predicate, (Object)null);
      }

      public Iterator<E> descendingIterator() {
         return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
      }

      public NavigableSet<E> descendingSet() {
         return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
      }

      @NullableDecl
      public E floor(E var1) {
         return Iterators.find(this.unfiltered().headSet(var1, true).descendingIterator(), this.predicate, (Object)null);
      }

      public NavigableSet<E> headSet(E var1, boolean var2) {
         return Sets.filter(this.unfiltered().headSet(var1, var2), this.predicate);
      }

      public E higher(E var1) {
         return Iterables.find(this.unfiltered().tailSet(var1, false), this.predicate, (Object)null);
      }

      public E last() {
         return Iterators.find(this.unfiltered().descendingIterator(), this.predicate);
      }

      @NullableDecl
      public E lower(E var1) {
         return Iterators.find(this.unfiltered().headSet(var1, false).descendingIterator(), this.predicate, (Object)null);
      }

      public E pollFirst() {
         return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
      }

      public E pollLast() {
         return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
      }

      public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
         return Sets.filter(this.unfiltered().subSet(var1, var2, var3, var4), this.predicate);
      }

      public NavigableSet<E> tailSet(E var1, boolean var2) {
         return Sets.filter(this.unfiltered().tailSet(var1, var2), this.predicate);
      }

      NavigableSet<E> unfiltered() {
         return (NavigableSet)this.unfiltered;
      }
   }

   private static class FilteredSet<E> extends Collections2.FilteredCollection<E> implements Set<E> {
      FilteredSet(Set<E> var1, Predicate<? super E> var2) {
         super(var1, var2);
      }

      public boolean equals(@NullableDecl Object var1) {
         return Sets.equalsImpl(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   private static class FilteredSortedSet<E> extends Sets.FilteredSet<E> implements SortedSet<E> {
      FilteredSortedSet(SortedSet<E> var1, Predicate<? super E> var2) {
         super(var1, var2);
      }

      public Comparator<? super E> comparator() {
         return ((SortedSet)this.unfiltered).comparator();
      }

      public E first() {
         return Iterators.find(this.unfiltered.iterator(), this.predicate);
      }

      public SortedSet<E> headSet(E var1) {
         return new Sets.FilteredSortedSet(((SortedSet)this.unfiltered).headSet(var1), this.predicate);
      }

      public E last() {
         SortedSet var1 = (SortedSet)this.unfiltered;

         while(true) {
            Object var2 = var1.last();
            if (this.predicate.apply(var2)) {
               return var2;
            }

            var1 = var1.headSet(var2);
         }
      }

      public SortedSet<E> subSet(E var1, E var2) {
         return new Sets.FilteredSortedSet(((SortedSet)this.unfiltered).subSet(var1, var2), this.predicate);
      }

      public SortedSet<E> tailSet(E var1) {
         return new Sets.FilteredSortedSet(((SortedSet)this.unfiltered).tailSet(var1), this.predicate);
      }
   }

   abstract static class ImprovedAbstractSet<E> extends AbstractSet<E> {
      public boolean removeAll(Collection<?> var1) {
         return Sets.removeAllImpl(this, (Collection)var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return super.retainAll((Collection)Preconditions.checkNotNull(var1));
      }
   }

   private static final class PowerSet<E> extends AbstractSet<Set<E>> {
      final ImmutableMap<E, Integer> inputSet;

      PowerSet(Set<E> var1) {
         boolean var2;
         if (var1.size() <= 30) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "Too many elements to create power set: %s > 30", var1.size());
         this.inputSet = Maps.indexMap(var1);
      }

      public boolean contains(@NullableDecl Object var1) {
         if (var1 instanceof Set) {
            Set var2 = (Set)var1;
            return this.inputSet.keySet().containsAll(var2);
         } else {
            return false;
         }
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Sets.PowerSet) {
            Sets.PowerSet var2 = (Sets.PowerSet)var1;
            return this.inputSet.equals(var2.inputSet);
         } else {
            return super.equals(var1);
         }
      }

      public int hashCode() {
         return this.inputSet.keySet().hashCode() << this.inputSet.size() - 1;
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator<Set<E>> iterator() {
         return new AbstractIndexedListIterator<Set<E>>(this.size()) {
            protected Set<E> get(int var1) {
               return new Sets.SubSet(PowerSet.this.inputSet, var1);
            }
         };
      }

      public int size() {
         return 1 << this.inputSet.size();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("powerSet(");
         var1.append(this.inputSet);
         var1.append(")");
         return var1.toString();
      }
   }

   public abstract static class SetView<E> extends AbstractSet<E> {
      private SetView() {
      }

      // $FF: synthetic method
      SetView(Object var1) {
         this();
      }

      @Deprecated
      public final boolean add(E var1) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      public final boolean addAll(Collection<? extends E> var1) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      public final void clear() {
         throw new UnsupportedOperationException();
      }

      public <S extends Set<E>> S copyInto(S var1) {
         var1.addAll(this);
         return var1;
      }

      public ImmutableSet<E> immutableCopy() {
         return ImmutableSet.copyOf((Collection)this);
      }

      public abstract UnmodifiableIterator<E> iterator();

      @Deprecated
      public final boolean remove(Object var1) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      public final boolean removeAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      public final boolean retainAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }
   }

   private static final class SubSet<E> extends AbstractSet<E> {
      private final ImmutableMap<E, Integer> inputSet;
      private final int mask;

      SubSet(ImmutableMap<E, Integer> var1, int var2) {
         this.inputSet = var1;
         this.mask = var2;
      }

      public boolean contains(@NullableDecl Object var1) {
         Integer var4 = (Integer)this.inputSet.get(var1);
         boolean var2 = true;
         if (var4 != null) {
            int var3 = this.mask;
            if ((1 << var4 & var3) != 0) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }

      public Iterator<E> iterator() {
         return new UnmodifiableIterator<E>() {
            final ImmutableList<E> elements;
            int remainingSetBits;

            {
               this.elements = SubSet.this.inputSet.keySet().asList();
               this.remainingSetBits = SubSet.this.mask;
            }

            public boolean hasNext() {
               boolean var1;
               if (this.remainingSetBits != 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               return var1;
            }

            public E next() {
               int var1 = Integer.numberOfTrailingZeros(this.remainingSetBits);
               if (var1 != 32) {
                  this.remainingSetBits &= 1 << var1;
                  return this.elements.get(var1);
               } else {
                  throw new NoSuchElementException();
               }
            }
         };
      }

      public int size() {
         return Integer.bitCount(this.mask);
      }
   }

   static final class UnmodifiableNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E>, Serializable {
      private static final long serialVersionUID = 0L;
      private final NavigableSet<E> delegate;
      @MonotonicNonNullDecl
      private transient Sets.UnmodifiableNavigableSet<E> descendingSet;
      private final SortedSet<E> unmodifiableDelegate;

      UnmodifiableNavigableSet(NavigableSet<E> var1) {
         this.delegate = (NavigableSet)Preconditions.checkNotNull(var1);
         this.unmodifiableDelegate = Collections.unmodifiableSortedSet(var1);
      }

      public E ceiling(E var1) {
         return this.delegate.ceiling(var1);
      }

      protected SortedSet<E> delegate() {
         return this.unmodifiableDelegate;
      }

      public Iterator<E> descendingIterator() {
         return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
      }

      public NavigableSet<E> descendingSet() {
         Sets.UnmodifiableNavigableSet var1 = this.descendingSet;
         Sets.UnmodifiableNavigableSet var2 = var1;
         if (var1 == null) {
            var2 = new Sets.UnmodifiableNavigableSet(this.delegate.descendingSet());
            this.descendingSet = var2;
            var2.descendingSet = this;
         }

         return var2;
      }

      public E floor(E var1) {
         return this.delegate.floor(var1);
      }

      public NavigableSet<E> headSet(E var1, boolean var2) {
         return Sets.unmodifiableNavigableSet(this.delegate.headSet(var1, var2));
      }

      public E higher(E var1) {
         return this.delegate.higher(var1);
      }

      public E lower(E var1) {
         return this.delegate.lower(var1);
      }

      public E pollFirst() {
         throw new UnsupportedOperationException();
      }

      public E pollLast() {
         throw new UnsupportedOperationException();
      }

      public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
         return Sets.unmodifiableNavigableSet(this.delegate.subSet(var1, var2, var3, var4));
      }

      public NavigableSet<E> tailSet(E var1, boolean var2) {
         return Sets.unmodifiableNavigableSet(this.delegate.tailSet(var1, var2));
      }
   }
}
