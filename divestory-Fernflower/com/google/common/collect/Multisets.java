package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Multisets {
   private Multisets() {
   }

   private static <E> boolean addAllImpl(Multiset<E> var0, AbstractMapBasedMultiset<? extends E> var1) {
      if (var1.isEmpty()) {
         return false;
      } else {
         var1.addTo(var0);
         return true;
      }
   }

   private static <E> boolean addAllImpl(Multiset<E> var0, Multiset<? extends E> var1) {
      if (var1 instanceof AbstractMapBasedMultiset) {
         return addAllImpl(var0, (AbstractMapBasedMultiset)var1);
      } else if (var1.isEmpty()) {
         return false;
      } else {
         Iterator var3 = var1.entrySet().iterator();

         while(var3.hasNext()) {
            Multiset.Entry var2 = (Multiset.Entry)var3.next();
            var0.add(var2.getElement(), var2.getCount());
         }

         return true;
      }
   }

   static <E> boolean addAllImpl(Multiset<E> var0, Collection<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      if (var1 instanceof Multiset) {
         return addAllImpl(var0, cast(var1));
      } else {
         return var1.isEmpty() ? false : Iterators.addAll(var0, var1.iterator());
      }
   }

   static <T> Multiset<T> cast(Iterable<T> var0) {
      return (Multiset)var0;
   }

   public static boolean containsOccurrences(Multiset<?> var0, Multiset<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Iterator var2 = var1.entrySet().iterator();

      Multiset.Entry var3;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         var3 = (Multiset.Entry)var2.next();
      } while(var0.count(var3.getElement()) >= var3.getCount());

      return false;
   }

   public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> var0) {
      Multiset.Entry[] var1 = (Multiset.Entry[])var0.entrySet().toArray(new Multiset.Entry[0]);
      Arrays.sort(var1, Multisets.DecreasingCount.INSTANCE);
      return ImmutableMultiset.copyFromEntries(Arrays.asList(var1));
   }

   public static <E> Multiset<E> difference(final Multiset<E> var0, final Multiset<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Multisets.ViewMultiset<E>() {
         public void clear() {
            throw new UnsupportedOperationException();
         }

         public int count(@NullableDecl Object var1x) {
            int var2 = var0.count(var1x);
            int var3 = 0;
            if (var2 != 0) {
               var3 = Math.max(0, var2 - var1.count(var1x));
            }

            return var3;
         }

         int distinctElements() {
            return Iterators.size(this.entryIterator());
         }

         Iterator<E> elementIterator() {
            return new AbstractIterator<E>(var0.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$iterator1;

               {
                  this.val$iterator1 = var2;
               }

               protected E computeNext() {
                  while(true) {
                     if (this.val$iterator1.hasNext()) {
                        Multiset.Entry var1x = (Multiset.Entry)this.val$iterator1.next();
                        Object var2 = var1x.getElement();
                        if (var1x.getCount() <= var1.count(var2)) {
                           continue;
                        }

                        return var2;
                     }

                     return this.endOfData();
                  }
               }
            };
         }

         Iterator<Multiset.Entry<E>> entryIterator() {
            return new AbstractIterator<Multiset.Entry<E>>(var0.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$iterator1;

               {
                  this.val$iterator1 = var2;
               }

               protected Multiset.Entry<E> computeNext() {
                  while(true) {
                     if (this.val$iterator1.hasNext()) {
                        Multiset.Entry var1x = (Multiset.Entry)this.val$iterator1.next();
                        Object var2 = var1x.getElement();
                        int var3 = var1x.getCount() - var1.count(var2);
                        if (var3 <= 0) {
                           continue;
                        }

                        return Multisets.immutableEntry(var2, var3);
                     }

                     return (Multiset.Entry)this.endOfData();
                  }
               }
            };
         }
      };
   }

   static <E> Iterator<E> elementIterator(Iterator<Multiset.Entry<E>> var0) {
      return new TransformedIterator<Multiset.Entry<E>, E>(var0) {
         E transform(Multiset.Entry<E> var1) {
            return var1.getElement();
         }
      };
   }

   static boolean equalsImpl(Multiset<?> var0, @NullableDecl Object var1) {
      if (var1 == var0) {
         return true;
      } else {
         if (var1 instanceof Multiset) {
            Multiset var3 = (Multiset)var1;
            if (var0.size() == var3.size() && var0.entrySet().size() == var3.entrySet().size()) {
               Iterator var4 = var3.entrySet().iterator();

               Multiset.Entry var2;
               do {
                  if (!var4.hasNext()) {
                     return true;
                  }

                  var2 = (Multiset.Entry)var4.next();
               } while(var0.count(var2.getElement()) == var2.getCount());

               return false;
            }
         }

         return false;
      }
   }

   public static <E> Multiset<E> filter(Multiset<E> var0, Predicate<? super E> var1) {
      if (var0 instanceof Multisets.FilteredMultiset) {
         Multisets.FilteredMultiset var2 = (Multisets.FilteredMultiset)var0;
         var1 = Predicates.and(var2.predicate, var1);
         return new Multisets.FilteredMultiset(var2.unfiltered, var1);
      } else {
         return new Multisets.FilteredMultiset(var0, var1);
      }
   }

   public static <E> Multiset.Entry<E> immutableEntry(@NullableDecl E var0, int var1) {
      return new Multisets.ImmutableEntry(var0, var1);
   }

   static int inferDistinctElements(Iterable<?> var0) {
      return var0 instanceof Multiset ? ((Multiset)var0).elementSet().size() : 11;
   }

   public static <E> Multiset<E> intersection(final Multiset<E> var0, final Multiset<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Multisets.ViewMultiset<E>() {
         public int count(Object var1x) {
            int var2 = var0.count(var1x);
            if (var2 == 0) {
               var2 = 0;
            } else {
               var2 = Math.min(var2, var1.count(var1x));
            }

            return var2;
         }

         Set<E> createElementSet() {
            return Sets.intersection(var0.elementSet(), var1.elementSet());
         }

         Iterator<E> elementIterator() {
            throw new AssertionError("should never be called");
         }

         Iterator<Multiset.Entry<E>> entryIterator() {
            return new AbstractIterator<Multiset.Entry<E>>(var0.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$iterator1;

               {
                  this.val$iterator1 = var2;
               }

               protected Multiset.Entry<E> computeNext() {
                  while(true) {
                     if (this.val$iterator1.hasNext()) {
                        Multiset.Entry var1x = (Multiset.Entry)this.val$iterator1.next();
                        Object var2 = var1x.getElement();
                        int var3 = Math.min(var1x.getCount(), var1.count(var2));
                        if (var3 <= 0) {
                           continue;
                        }

                        return Multisets.immutableEntry(var2, var3);
                     }

                     return (Multiset.Entry)this.endOfData();
                  }
               }
            };
         }
      };
   }

   static <E> Iterator<E> iteratorImpl(Multiset<E> var0) {
      return new Multisets.MultisetIteratorImpl(var0, var0.entrySet().iterator());
   }

   static int linearTimeSizeImpl(Multiset<?> var0) {
      Iterator var3 = var0.entrySet().iterator();

      long var1;
      for(var1 = 0L; var3.hasNext(); var1 += (long)((Multiset.Entry)var3.next()).getCount()) {
      }

      return Ints.saturatedCast(var1);
   }

   static boolean removeAllImpl(Multiset<?> var0, Collection<?> var1) {
      Object var2 = var1;
      if (var1 instanceof Multiset) {
         var2 = ((Multiset)var1).elementSet();
      }

      return var0.elementSet().removeAll((Collection)var2);
   }

   public static boolean removeOccurrences(Multiset<?> var0, Multiset<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Iterator var2 = var0.entrySet().iterator();
      boolean var3 = false;

      while(true) {
         while(true) {
            if (!var2.hasNext()) {
               return var3;
            }

            Multiset.Entry var4 = (Multiset.Entry)var2.next();
            int var5 = var1.count(var4.getElement());
            if (var5 >= var4.getCount()) {
               var2.remove();
               break;
            }

            if (var5 > 0) {
               var0.remove(var4.getElement(), var5);
               break;
            }
         }

         var3 = true;
      }
   }

   public static boolean removeOccurrences(Multiset<?> var0, Iterable<?> var1) {
      if (var1 instanceof Multiset) {
         return removeOccurrences(var0, (Multiset)var1);
      } else {
         Preconditions.checkNotNull(var0);
         Preconditions.checkNotNull(var1);
         boolean var2 = false;

         for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 |= var0.remove(var3.next())) {
         }

         return var2;
      }
   }

   static boolean retainAllImpl(Multiset<?> var0, Collection<?> var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = var1;
      if (var1 instanceof Multiset) {
         var2 = ((Multiset)var1).elementSet();
      }

      return var0.elementSet().retainAll((Collection)var2);
   }

   public static boolean retainOccurrences(Multiset<?> var0, Multiset<?> var1) {
      return retainOccurrencesImpl(var0, var1);
   }

   private static <E> boolean retainOccurrencesImpl(Multiset<E> var0, Multiset<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Iterator var2 = var0.entrySet().iterator();
      boolean var3 = false;

      while(true) {
         while(true) {
            if (!var2.hasNext()) {
               return var3;
            }

            Multiset.Entry var4 = (Multiset.Entry)var2.next();
            int var5 = var1.count(var4.getElement());
            if (var5 == 0) {
               var2.remove();
               break;
            }

            if (var5 < var4.getCount()) {
               var0.setCount(var4.getElement(), var5);
               break;
            }
         }

         var3 = true;
      }
   }

   static <E> int setCountImpl(Multiset<E> var0, E var1, int var2) {
      CollectPreconditions.checkNonnegative(var2, "count");
      int var3 = var0.count(var1);
      var2 -= var3;
      if (var2 > 0) {
         var0.add(var1, var2);
      } else if (var2 < 0) {
         var0.remove(var1, -var2);
      }

      return var3;
   }

   static <E> boolean setCountImpl(Multiset<E> var0, E var1, int var2, int var3) {
      CollectPreconditions.checkNonnegative(var2, "oldCount");
      CollectPreconditions.checkNonnegative(var3, "newCount");
      if (var0.count(var1) == var2) {
         var0.setCount(var1, var3);
         return true;
      } else {
         return false;
      }
   }

   public static <E> Multiset<E> sum(final Multiset<? extends E> var0, final Multiset<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Multisets.ViewMultiset<E>() {
         public boolean contains(@NullableDecl Object var1x) {
            boolean var2;
            if (!var0.contains(var1x) && !var1.contains(var1x)) {
               var2 = false;
            } else {
               var2 = true;
            }

            return var2;
         }

         public int count(Object var1x) {
            return var0.count(var1x) + var1.count(var1x);
         }

         Set<E> createElementSet() {
            return Sets.union(var0.elementSet(), var1.elementSet());
         }

         Iterator<E> elementIterator() {
            throw new AssertionError("should never be called");
         }

         Iterator<Multiset.Entry<E>> entryIterator() {
            return new AbstractIterator<Multiset.Entry<E>>(var0.entrySet().iterator(), var1.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$iterator1;
               // $FF: synthetic field
               final Iterator val$iterator2;

               {
                  this.val$iterator1 = var2;
                  this.val$iterator2 = var3;
               }

               protected Multiset.Entry<E> computeNext() {
                  Multiset.Entry var1x;
                  Object var2;
                  if (this.val$iterator1.hasNext()) {
                     var1x = (Multiset.Entry)this.val$iterator1.next();
                     var2 = var1x.getElement();
                     return Multisets.immutableEntry(var2, var1x.getCount() + var1.count(var2));
                  } else {
                     do {
                        if (!this.val$iterator2.hasNext()) {
                           return (Multiset.Entry)this.endOfData();
                        }

                        var1x = (Multiset.Entry)this.val$iterator2.next();
                        var2 = var1x.getElement();
                     } while(var0.contains(var2));

                     return Multisets.immutableEntry(var2, var1x.getCount());
                  }
               }
            };
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

         public int size() {
            return IntMath.saturatedAdd(var0.size(), var1.size());
         }
      };
   }

   public static <E> Multiset<E> union(final Multiset<? extends E> var0, final Multiset<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Multisets.ViewMultiset<E>() {
         public boolean contains(@NullableDecl Object var1x) {
            boolean var2;
            if (!var0.contains(var1x) && !var1.contains(var1x)) {
               var2 = false;
            } else {
               var2 = true;
            }

            return var2;
         }

         public int count(Object var1x) {
            return Math.max(var0.count(var1x), var1.count(var1x));
         }

         Set<E> createElementSet() {
            return Sets.union(var0.elementSet(), var1.elementSet());
         }

         Iterator<E> elementIterator() {
            throw new AssertionError("should never be called");
         }

         Iterator<Multiset.Entry<E>> entryIterator() {
            return new AbstractIterator<Multiset.Entry<E>>(var0.entrySet().iterator(), var1.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$iterator1;
               // $FF: synthetic field
               final Iterator val$iterator2;

               {
                  this.val$iterator1 = var2;
                  this.val$iterator2 = var3;
               }

               protected Multiset.Entry<E> computeNext() {
                  if (this.val$iterator1.hasNext()) {
                     Multiset.Entry var3 = (Multiset.Entry)this.val$iterator1.next();
                     Object var4 = var3.getElement();
                     return Multisets.immutableEntry(var4, Math.max(var3.getCount(), var1.count(var4)));
                  } else {
                     Object var1x;
                     Multiset.Entry var2;
                     do {
                        if (!this.val$iterator2.hasNext()) {
                           return (Multiset.Entry)this.endOfData();
                        }

                        var2 = (Multiset.Entry)this.val$iterator2.next();
                        var1x = var2.getElement();
                     } while(var0.contains(var1x));

                     return Multisets.immutableEntry(var1x, var2.getCount());
                  }
               }
            };
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
      };
   }

   @Deprecated
   public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> var0) {
      return (Multiset)Preconditions.checkNotNull(var0);
   }

   public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> var0) {
      return (Multiset)(!(var0 instanceof Multisets.UnmodifiableMultiset) && !(var0 instanceof ImmutableMultiset) ? new Multisets.UnmodifiableMultiset((Multiset)Preconditions.checkNotNull(var0)) : var0);
   }

   public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> var0) {
      return new UnmodifiableSortedMultiset((SortedMultiset)Preconditions.checkNotNull(var0));
   }

   abstract static class AbstractEntry<E> implements Multiset.Entry<E> {
      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Multiset.Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Multiset.Entry var5 = (Multiset.Entry)var1;
            var4 = var3;
            if (this.getCount() == var5.getCount()) {
               var4 = var3;
               if (Objects.equal(this.getElement(), var5.getElement())) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         Object var1 = this.getElement();
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.hashCode();
         }

         return var2 ^ this.getCount();
      }

      public String toString() {
         String var1 = String.valueOf(this.getElement());
         int var2 = this.getCount();
         if (var2 != 1) {
            StringBuilder var3 = new StringBuilder();
            var3.append(var1);
            var3.append(" x ");
            var3.append(var2);
            var1 = var3.toString();
         }

         return var1;
      }
   }

   private static final class DecreasingCount implements Comparator<Multiset.Entry<?>> {
      static final Multisets.DecreasingCount INSTANCE = new Multisets.DecreasingCount();

      public int compare(Multiset.Entry<?> var1, Multiset.Entry<?> var2) {
         return var2.getCount() - var1.getCount();
      }
   }

   abstract static class ElementSet<E> extends Sets.ImprovedAbstractSet<E> {
      public void clear() {
         this.multiset().clear();
      }

      public boolean contains(Object var1) {
         return this.multiset().contains(var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return this.multiset().containsAll(var1);
      }

      public boolean isEmpty() {
         return this.multiset().isEmpty();
      }

      public abstract Iterator<E> iterator();

      abstract Multiset<E> multiset();

      public boolean remove(Object var1) {
         boolean var2;
         if (this.multiset().remove(var1, Integer.MAX_VALUE) > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int size() {
         return this.multiset().entrySet().size();
      }
   }

   abstract static class EntrySet<E> extends Sets.ImprovedAbstractSet<Multiset.Entry<E>> {
      public void clear() {
         this.multiset().clear();
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Multiset.Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Multiset.Entry var5 = (Multiset.Entry)var1;
            if (var5.getCount() <= 0) {
               return false;
            }

            var4 = var3;
            if (this.multiset().count(var5.getElement()) == var5.getCount()) {
               var4 = true;
            }
         }

         return var4;
      }

      abstract Multiset<E> multiset();

      public boolean remove(Object var1) {
         if (var1 instanceof Multiset.Entry) {
            Multiset.Entry var2 = (Multiset.Entry)var1;
            var1 = var2.getElement();
            int var3 = var2.getCount();
            if (var3 != 0) {
               return this.multiset().setCount(var1, var3, 0);
            }
         }

         return false;
      }
   }

   private static final class FilteredMultiset<E> extends Multisets.ViewMultiset<E> {
      final Predicate<? super E> predicate;
      final Multiset<E> unfiltered;

      FilteredMultiset(Multiset<E> var1, Predicate<? super E> var2) {
         super(null);
         this.unfiltered = (Multiset)Preconditions.checkNotNull(var1);
         this.predicate = (Predicate)Preconditions.checkNotNull(var2);
      }

      public int add(@NullableDecl E var1, int var2) {
         Preconditions.checkArgument(this.predicate.apply(var1), "Element %s does not match predicate %s", var1, this.predicate);
         return this.unfiltered.add(var1, var2);
      }

      public int count(@NullableDecl Object var1) {
         int var2 = this.unfiltered.count(var1);
         if (var2 > 0) {
            if (!this.predicate.apply(var1)) {
               var2 = 0;
            }

            return var2;
         } else {
            return 0;
         }
      }

      Set<E> createElementSet() {
         return Sets.filter(this.unfiltered.elementSet(), this.predicate);
      }

      Set<Multiset.Entry<E>> createEntrySet() {
         return Sets.filter(this.unfiltered.entrySet(), new Predicate<Multiset.Entry<E>>() {
            public boolean apply(Multiset.Entry<E> var1) {
               return FilteredMultiset.this.predicate.apply(var1.getElement());
            }
         });
      }

      Iterator<E> elementIterator() {
         throw new AssertionError("should never be called");
      }

      Iterator<Multiset.Entry<E>> entryIterator() {
         throw new AssertionError("should never be called");
      }

      public UnmodifiableIterator<E> iterator() {
         return Iterators.filter(this.unfiltered.iterator(), this.predicate);
      }

      public int remove(@NullableDecl Object var1, int var2) {
         CollectPreconditions.checkNonnegative(var2, "occurrences");
         if (var2 == 0) {
            return this.count(var1);
         } else {
            if (this.contains(var1)) {
               var2 = this.unfiltered.remove(var1, var2);
            } else {
               var2 = 0;
            }

            return var2;
         }
      }
   }

   static class ImmutableEntry<E> extends Multisets.AbstractEntry<E> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final int count;
      @NullableDecl
      private final E element;

      ImmutableEntry(@NullableDecl E var1, int var2) {
         this.element = var1;
         this.count = var2;
         CollectPreconditions.checkNonnegative(var2, "count");
      }

      public final int getCount() {
         return this.count;
      }

      @NullableDecl
      public final E getElement() {
         return this.element;
      }

      public Multisets.ImmutableEntry<E> nextInBucket() {
         return null;
      }
   }

   static final class MultisetIteratorImpl<E> implements Iterator<E> {
      private boolean canRemove;
      @MonotonicNonNullDecl
      private Multiset.Entry<E> currentEntry;
      private final Iterator<Multiset.Entry<E>> entryIterator;
      private int laterCount;
      private final Multiset<E> multiset;
      private int totalCount;

      MultisetIteratorImpl(Multiset<E> var1, Iterator<Multiset.Entry<E>> var2) {
         this.multiset = var1;
         this.entryIterator = var2;
      }

      public boolean hasNext() {
         boolean var1;
         if (this.laterCount <= 0 && !this.entryIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         if (this.hasNext()) {
            if (this.laterCount == 0) {
               Multiset.Entry var1 = (Multiset.Entry)this.entryIterator.next();
               this.currentEntry = var1;
               int var2 = var1.getCount();
               this.laterCount = var2;
               this.totalCount = var2;
            }

            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.canRemove);
         if (this.totalCount == 1) {
            this.entryIterator.remove();
         } else {
            this.multiset.remove(this.currentEntry.getElement());
         }

         --this.totalCount;
         this.canRemove = false;
      }
   }

   static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
      private static final long serialVersionUID = 0L;
      final Multiset<? extends E> delegate;
      @MonotonicNonNullDecl
      transient Set<E> elementSet;
      @MonotonicNonNullDecl
      transient Set<Multiset.Entry<E>> entrySet;

      UnmodifiableMultiset(Multiset<? extends E> var1) {
         this.delegate = var1;
      }

      public int add(E var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean add(E var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends E> var1) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      Set<E> createElementSet() {
         return Collections.unmodifiableSet(this.delegate.elementSet());
      }

      protected Multiset<E> delegate() {
         return this.delegate;
      }

      public Set<E> elementSet() {
         Set var1 = this.elementSet;
         Set var2 = var1;
         if (var1 == null) {
            var2 = this.createElementSet();
            this.elementSet = var2;
         }

         return var2;
      }

      public Set<Multiset.Entry<E>> entrySet() {
         Set var1 = this.entrySet;
         Set var2 = var1;
         if (var1 == null) {
            var2 = Collections.unmodifiableSet(this.delegate.entrySet());
            this.entrySet = var2;
         }

         return var2;
      }

      public Iterator<E> iterator() {
         return Iterators.unmodifiableIterator(this.delegate.iterator());
      }

      public int remove(Object var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object var1) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public int setCount(E var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean setCount(E var1, int var2, int var3) {
         throw new UnsupportedOperationException();
      }
   }

   private abstract static class ViewMultiset<E> extends AbstractMultiset<E> {
      private ViewMultiset() {
      }

      // $FF: synthetic method
      ViewMultiset(Object var1) {
         this();
      }

      public void clear() {
         this.elementSet().clear();
      }

      int distinctElements() {
         return this.elementSet().size();
      }

      public Iterator<E> iterator() {
         return Multisets.iteratorImpl(this);
      }

      public int size() {
         return Multisets.linearTimeSizeImpl(this);
      }
   }
}
