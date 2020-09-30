package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
   static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet(ImmutableList.of(), Ordering.natural());
   final transient ImmutableList<E> elements;

   RegularImmutableSortedSet(ImmutableList<E> var1, Comparator<? super E> var2) {
      super(var2);
      this.elements = var1;
   }

   private int unsafeBinarySearch(Object var1) throws ClassCastException {
      return Collections.binarySearch(this.elements, var1, this.unsafeComparator());
   }

   public ImmutableList<E> asList() {
      return this.elements;
   }

   public E ceiling(E var1) {
      int var2 = this.tailIndex(var1, true);
      if (var2 == this.size()) {
         var1 = null;
      } else {
         var1 = this.elements.get(var2);
      }

      return var1;
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 != null) {
         int var4;
         try {
            var4 = this.unsafeBinarySearch(var1);
         } catch (ClassCastException var5) {
            var3 = var2;
            return var3;
         }

         var3 = var2;
         if (var4 >= 0) {
            var3 = true;
         }
      }

      return var3;
   }

   public boolean containsAll(Collection<?> var1) {
      Object var2 = var1;
      if (var1 instanceof Multiset) {
         var2 = ((Multiset)var1).elementSet();
      }

      if (SortedIterables.hasSameComparator(this.comparator(), (Iterable)var2) && ((Collection)var2).size() > 1) {
         UnmodifiableIterator var3 = this.iterator();
         Iterator var4 = ((Collection)var2).iterator();
         if (!var3.hasNext()) {
            return false;
         } else {
            var2 = var4.next();
            Object var11 = var3.next();

            while(true) {
               boolean var10001;
               int var5;
               try {
                  var5 = this.unsafeCompare(var11, var2);
               } catch (ClassCastException | NullPointerException var8) {
                  var10001 = false;
                  break;
               }

               if (var5 < 0) {
                  try {
                     if (!var3.hasNext()) {
                        return false;
                     }
                  } catch (ClassCastException | NullPointerException var9) {
                     var10001 = false;
                     break;
                  }

                  try {
                     var11 = var3.next();
                  } catch (ClassCastException | NullPointerException var7) {
                     var10001 = false;
                     break;
                  }
               } else if (var5 != 0) {
                  if (var5 <= 0) {
                     continue;
                  }
                  break;
               } else {
                  try {
                     if (!var4.hasNext()) {
                        return true;
                     }
                  } catch (ClassCastException | NullPointerException var10) {
                     var10001 = false;
                     break;
                  }

                  try {
                     var2 = var4.next();
                  } catch (ClassCastException | NullPointerException var6) {
                     var10001 = false;
                     break;
                  }
               }
            }

            return false;
         }
      } else {
         return super.containsAll((Collection)var2);
      }
   }

   int copyIntoArray(Object[] var1, int var2) {
      return this.elements.copyIntoArray(var1, var2);
   }

   ImmutableSortedSet<E> createDescendingSet() {
      Comparator var1 = Collections.reverseOrder(this.comparator);
      RegularImmutableSortedSet var2;
      if (this.isEmpty()) {
         var2 = emptySet(var1);
      } else {
         var2 = new RegularImmutableSortedSet(this.elements.reverse(), var1);
      }

      return var2;
   }

   public UnmodifiableIterator<E> descendingIterator() {
      return this.elements.reverse().iterator();
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Set)) {
         return false;
      } else {
         Set var9 = (Set)var1;
         if (this.size() != var9.size()) {
            return false;
         } else if (this.isEmpty()) {
            return true;
         } else if (SortedIterables.hasSameComparator(this.comparator, var9)) {
            Iterator var10 = var9.iterator();

            boolean var10001;
            UnmodifiableIterator var2;
            try {
               var2 = this.iterator();
            } catch (NoSuchElementException | ClassCastException var8) {
               var10001 = false;
               return false;
            }

            while(true) {
               Object var3;
               Object var4;
               try {
                  if (!var2.hasNext()) {
                     return true;
                  }

                  var3 = var2.next();
                  var4 = var10.next();
               } catch (NoSuchElementException | ClassCastException var7) {
                  var10001 = false;
                  break;
               }

               if (var4 != null) {
                  int var5;
                  try {
                     var5 = this.unsafeCompare(var3, var4);
                  } catch (NoSuchElementException | ClassCastException var6) {
                     var10001 = false;
                     break;
                  }

                  if (var5 == 0) {
                     continue;
                  }
               }

               return false;
            }

            return false;
         } else {
            return this.containsAll(var9);
         }
      }
   }

   public E first() {
      if (!this.isEmpty()) {
         return this.elements.get(0);
      } else {
         throw new NoSuchElementException();
      }
   }

   public E floor(E var1) {
      int var2 = this.headIndex(var1, true) - 1;
      if (var2 == -1) {
         var1 = null;
      } else {
         var1 = this.elements.get(var2);
      }

      return var1;
   }

   RegularImmutableSortedSet<E> getSubSet(int var1, int var2) {
      if (var1 == 0 && var2 == this.size()) {
         return this;
      } else {
         return var1 < var2 ? new RegularImmutableSortedSet(this.elements.subList(var1, var2), this.comparator) : emptySet(this.comparator);
      }
   }

   int headIndex(E var1, boolean var2) {
      int var3 = Collections.binarySearch(this.elements, Preconditions.checkNotNull(var1), this.comparator());
      if (var3 >= 0) {
         int var4 = var3;
         if (var2) {
            var4 = var3 + 1;
         }

         return var4;
      } else {
         return var3;
      }
   }

   ImmutableSortedSet<E> headSetImpl(E var1, boolean var2) {
      return this.getSubSet(0, this.headIndex(var1, var2));
   }

   public E higher(E var1) {
      int var2 = this.tailIndex(var1, false);
      if (var2 == this.size()) {
         var1 = null;
      } else {
         var1 = this.elements.get(var2);
      }

      return var1;
   }

   int indexOf(@NullableDecl Object var1) {
      int var2 = -1;
      if (var1 == null) {
         return -1;
      } else {
         int var3;
         try {
            var3 = Collections.binarySearch(this.elements, var1, this.unsafeComparator());
         } catch (ClassCastException var4) {
            return var2;
         }

         if (var3 >= 0) {
            var2 = var3;
         }

         return var2;
      }
   }

   Object[] internalArray() {
      return this.elements.internalArray();
   }

   int internalArrayEnd() {
      return this.elements.internalArrayEnd();
   }

   int internalArrayStart() {
      return this.elements.internalArrayStart();
   }

   boolean isPartialView() {
      return this.elements.isPartialView();
   }

   public UnmodifiableIterator<E> iterator() {
      return this.elements.iterator();
   }

   public E last() {
      if (!this.isEmpty()) {
         return this.elements.get(this.size() - 1);
      } else {
         throw new NoSuchElementException();
      }
   }

   public E lower(E var1) {
      int var2 = this.headIndex(var1, false) - 1;
      if (var2 == -1) {
         var1 = null;
      } else {
         var1 = this.elements.get(var2);
      }

      return var1;
   }

   public int size() {
      return this.elements.size();
   }

   ImmutableSortedSet<E> subSetImpl(E var1, boolean var2, E var3, boolean var4) {
      return this.tailSetImpl(var1, var2).headSetImpl(var3, var4);
   }

   int tailIndex(E var1, boolean var2) {
      int var3 = Collections.binarySearch(this.elements, Preconditions.checkNotNull(var1), this.comparator());
      if (var3 >= 0) {
         if (!var2) {
            ++var3;
         }

         return var3;
      } else {
         return var3;
      }
   }

   ImmutableSortedSet<E> tailSetImpl(E var1, boolean var2) {
      return this.getSubSet(this.tailIndex(var1, var2), this.size());
   }

   Comparator<Object> unsafeComparator() {
      return this.comparator;
   }
}
