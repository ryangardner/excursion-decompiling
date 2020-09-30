package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b¢\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000¢\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000¢\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u001d\u0010#\u001a\u00020\u00042\u0006\u0010>\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\u0000¢\u0006\u0002\b?J\u0010\u0010@\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u0010A\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u0016\u0010B\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010C\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010.J\u000b\u0010D\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010E\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u000b\u0010F\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010G\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010H\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010I\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010JR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006K"},
   d2 = {"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "oldCapacity", "newCapacity$kotlin_stdlib", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ArrayDeque<E> extends AbstractMutableList<E> {
   private Object[] elementData;
   private int head;
   private int size;

   public ArrayDeque() {
      this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
   }

   public ArrayDeque(int var1) {
      Object[] var2;
      if (var1 == 0) {
         var2 = ArrayDequeKt.access$getEmptyElementData$p();
      } else {
         if (var1 <= 0) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Illegal Capacity: ");
            var3.append(var1);
            throw (Throwable)(new IllegalArgumentException(var3.toString()));
         }

         var2 = new Object[var1];
      }

      this.elementData = var2;
   }

   public ArrayDeque(Collection<? extends E> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      super();
      boolean var2 = false;
      Object[] var3 = var1.toArray(new Object[0]);
      if (var3 != null) {
         this.elementData = var3;
         this.size = var3.length;
         if (var3.length == 0) {
            var2 = true;
         }

         if (var2) {
            this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
         }

      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   // $FF: synthetic method
   public static final Object[] access$getElementData$p(ArrayDeque var0) {
      return var0.elementData;
   }

   // $FF: synthetic method
   public static final int access$getHead$p(ArrayDeque var0) {
      return var0.head;
   }

   // $FF: synthetic method
   public static final int access$getSize$p(ArrayDeque var0) {
      return var0.size();
   }

   // $FF: synthetic method
   public static final int access$incremented(ArrayDeque var0, int var1) {
      return var0.incremented(var1);
   }

   // $FF: synthetic method
   public static final int access$negativeMod(ArrayDeque var0, int var1) {
      return var0.negativeMod(var1);
   }

   // $FF: synthetic method
   public static final int access$positiveMod(ArrayDeque var0, int var1) {
      return var0.positiveMod(var1);
   }

   // $FF: synthetic method
   public static final void access$setElementData$p(ArrayDeque var0, Object[] var1) {
      var0.elementData = var1;
   }

   // $FF: synthetic method
   public static final void access$setHead$p(ArrayDeque var0, int var1) {
      var0.head = var1;
   }

   // $FF: synthetic method
   public static final void access$setSize$p(ArrayDeque var0, int var1) {
      var0.size = var1;
   }

   private final void copyCollectionElements(int var1, Collection<? extends E> var2) {
      Iterator var3 = var2.iterator();

      int var4;
      for(var4 = this.elementData.length; var1 < var4 && var3.hasNext(); ++var1) {
         this.elementData[var1] = var3.next();
      }

      var1 = 0;

      for(var4 = this.head; var1 < var4 && var3.hasNext(); ++var1) {
         this.elementData[var1] = var3.next();
      }

      this.size = this.size() + var2.size();
   }

   private final void copyElements(int var1) {
      Object[] var2 = new Object[var1];
      Object[] var3 = this.elementData;
      ArraysKt.copyInto(var3, var2, 0, this.head, var3.length);
      var3 = this.elementData;
      var1 = var3.length;
      int var4 = this.head;
      ArraysKt.copyInto(var3, var2, var1 - var4, 0, var4);
      this.head = 0;
      this.elementData = var2;
   }

   private final int decremented(int var1) {
      if (var1 == 0) {
         var1 = ArraysKt.getLastIndex(this.elementData);
      } else {
         --var1;
      }

      return var1;
   }

   private final void ensureCapacity(int var1) {
      if (var1 >= 0) {
         Object[] var2 = this.elementData;
         if (var1 > var2.length) {
            if (var2 == ArrayDequeKt.access$getEmptyElementData$p()) {
               this.elementData = new Object[RangesKt.coerceAtLeast(var1, 10)];
            } else {
               this.copyElements(this.newCapacity$kotlin_stdlib(this.elementData.length, var1));
            }
         }
      } else {
         throw (Throwable)(new IllegalStateException("Deque is too big."));
      }
   }

   private final boolean filterInPlace(Function1<? super E, Boolean> var1) {
      boolean var2 = this.isEmpty();
      byte var3 = 0;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = var4;
      if (!var2) {
         boolean var7;
         if (access$getElementData$p(this).length == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var6 = var4;
         } else {
            int var13 = this.size();
            int var8 = access$positiveMod(this, access$getHead$p(this) + var13);
            int var9 = access$getHead$p(this);
            Object var10;
            if (access$getHead$p(this) < var8) {
               int var12 = access$getHead$p(this);

               for(var13 = var9; var12 < var8; ++var12) {
                  var10 = access$getElementData$p(this)[var12];
                  if ((Boolean)var1.invoke(var10)) {
                     access$getElementData$p(this)[var13] = var10;
                     ++var13;
                  } else {
                     var5 = true;
                  }
               }

               ArraysKt.fill(access$getElementData$p(this), (Object)null, var13, var8);
            } else {
               var13 = access$getHead$p(this);
               int var11 = access$getElementData$p(this).length;

               for(var5 = false; var13 < var11; ++var13) {
                  var10 = access$getElementData$p(this)[var13];
                  access$getElementData$p(this)[var13] = null;
                  if ((Boolean)var1.invoke(var10)) {
                     access$getElementData$p(this)[var9] = var10;
                     ++var9;
                  } else {
                     var5 = true;
                  }
               }

               var13 = access$positiveMod(this, var9);

               for(var9 = var3; var9 < var8; ++var9) {
                  var10 = access$getElementData$p(this)[var9];
                  access$getElementData$p(this)[var9] = null;
                  if ((Boolean)var1.invoke(var10)) {
                     access$getElementData$p(this)[var13] = var10;
                     var13 = access$incremented(this, var13);
                  } else {
                     var5 = true;
                  }
               }
            }

            var6 = var5;
            if (var5) {
               access$setSize$p(this, access$negativeMod(this, var13 - access$getHead$p(this)));
               var6 = var5;
            }
         }
      }

      return var6;
   }

   private final int incremented(int var1) {
      if (var1 == ArraysKt.getLastIndex(this.elementData)) {
         var1 = 0;
      } else {
         ++var1;
      }

      return var1;
   }

   private final E internalGet(int var1) {
      return access$getElementData$p(this)[var1];
   }

   private final int internalIndex(int var1) {
      return access$positiveMod(this, access$getHead$p(this) + var1);
   }

   private final int negativeMod(int var1) {
      int var2 = var1;
      if (var1 < 0) {
         var2 = var1 + this.elementData.length;
      }

      return var2;
   }

   private final int positiveMod(int var1) {
      Object[] var2 = this.elementData;
      int var3 = var1;
      if (var1 >= var2.length) {
         var3 = var1 - var2.length;
      }

      return var3;
   }

   public void add(int var1, E var2) {
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.size());
      if (var1 == this.size()) {
         this.addLast(var2);
      } else if (var1 == 0) {
         this.addFirst(var2);
      } else {
         this.ensureCapacity(this.size() + 1);
         int var3 = access$positiveMod(this, access$getHead$p(this) + var1);
         Object[] var5;
         if (var1 < this.size() + 1 >> 1) {
            var1 = this.decremented(var3);
            int var4 = this.decremented(this.head);
            var3 = this.head;
            if (var1 >= var3) {
               var5 = this.elementData;
               var5[var4] = var5[var3];
               ArraysKt.copyInto(var5, var5, var3, var3 + 1, var1 + 1);
            } else {
               var5 = this.elementData;
               ArraysKt.copyInto(var5, var5, var3 - 1, var3, var5.length);
               var5 = this.elementData;
               var5[var5.length - 1] = var5[0];
               ArraysKt.copyInto(var5, var5, 0, 1, var1 + 1);
            }

            this.elementData[var1] = var2;
            this.head = var4;
         } else {
            var1 = this.size();
            var1 = access$positiveMod(this, access$getHead$p(this) + var1);
            if (var3 < var1) {
               var5 = this.elementData;
               ArraysKt.copyInto(var5, var5, var3 + 1, var3, var1);
            } else {
               var5 = this.elementData;
               ArraysKt.copyInto(var5, var5, 1, 0, var1);
               var5 = this.elementData;
               var5[0] = var5[var5.length - 1];
               ArraysKt.copyInto(var5, var5, var3 + 1, var3, var5.length - 1);
            }

            this.elementData[var3] = var2;
         }

         this.size = this.size() + 1;
      }
   }

   public boolean add(E var1) {
      this.addLast(var1);
      return true;
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "elements");
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.size());
      if (var2.isEmpty()) {
         return false;
      } else if (var1 == this.size()) {
         return this.addAll(var2);
      } else {
         this.ensureCapacity(this.size() + var2.size());
         int var3 = this.size();
         int var4 = access$positiveMod(this, access$getHead$p(this) + var3);
         var3 = access$positiveMod(this, access$getHead$p(this) + var1);
         int var5 = var2.size();
         Object[] var6;
         if (var1 < this.size() + 1 >> 1) {
            var4 = this.head;
            var1 = var4 - var5;
            if (var3 >= var4) {
               if (var1 >= 0) {
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, var1, var4, var3);
               } else {
                  var6 = this.elementData;
                  var1 += var6.length;
                  int var7 = var6.length - var1;
                  if (var7 >= var3 - var4) {
                     ArraysKt.copyInto(var6, var6, var1, var4, var3);
                  } else {
                     ArraysKt.copyInto(var6, var6, var1, var4, var4 + var7);
                     var6 = this.elementData;
                     ArraysKt.copyInto(var6, var6, 0, this.head + var7, var3);
                  }
               }
            } else {
               var6 = this.elementData;
               ArraysKt.copyInto(var6, var6, var1, var4, var6.length);
               if (var5 >= var3) {
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, var6.length - var5, 0, var3);
               } else {
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, var6.length - var5, 0, var5);
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, 0, var5, var3);
               }
            }

            this.head = var1;
            this.copyCollectionElements(this.negativeMod(var3 - var5), var2);
         } else {
            var1 = var3 + var5;
            if (var3 < var4) {
               var5 += var4;
               var6 = this.elementData;
               if (var5 <= var6.length) {
                  ArraysKt.copyInto(var6, var6, var1, var3, var4);
               } else if (var1 >= var6.length) {
                  ArraysKt.copyInto(var6, var6, var1 - var6.length, var3, var4);
               } else {
                  var5 = var4 - (var5 - var6.length);
                  ArraysKt.copyInto(var6, var6, 0, var5, var4);
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, var1, var3, var5);
               }
            } else {
               var6 = this.elementData;
               ArraysKt.copyInto(var6, var6, var5, 0, var4);
               var6 = this.elementData;
               if (var1 >= var6.length) {
                  ArraysKt.copyInto(var6, var6, var1 - var6.length, var3, var6.length);
               } else {
                  ArraysKt.copyInto(var6, var6, 0, var6.length - var5, var6.length);
                  var6 = this.elementData;
                  ArraysKt.copyInto(var6, var6, var1, var3, var6.length - var5);
               }
            }

            this.copyCollectionElements(var3, var2);
         }

         return true;
      }
   }

   public boolean addAll(Collection<? extends E> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      if (var1.isEmpty()) {
         return false;
      } else {
         this.ensureCapacity(this.size() + var1.size());
         int var2 = this.size();
         this.copyCollectionElements(access$positiveMod(this, access$getHead$p(this) + var2), var1);
         return true;
      }
   }

   public final void addFirst(E var1) {
      this.ensureCapacity(this.size() + 1);
      int var2 = this.decremented(this.head);
      this.head = var2;
      this.elementData[var2] = var1;
      this.size = this.size() + 1;
   }

   public final void addLast(E var1) {
      this.ensureCapacity(this.size() + 1);
      Object[] var2 = this.elementData;
      int var3 = this.size();
      var2[access$positiveMod(this, access$getHead$p(this) + var3)] = var1;
      this.size = this.size() + 1;
   }

   public void clear() {
      int var1 = this.size();
      int var2 = access$positiveMod(this, access$getHead$p(this) + var1);
      var1 = this.head;
      if (var1 < var2) {
         ArraysKt.fill(this.elementData, (Object)null, var1, var2);
      } else if (this.isEmpty() ^ true) {
         Object[] var3 = this.elementData;
         ArraysKt.fill(var3, (Object)null, this.head, var3.length);
         ArraysKt.fill(this.elementData, (Object)null, 0, var2);
      }

      this.head = 0;
      this.size = 0;
   }

   public boolean contains(Object var1) {
      boolean var2;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final E first() {
      if (!this.isEmpty()) {
         int var1 = this.head;
         return access$getElementData$p(this)[var1];
      } else {
         throw (Throwable)(new NoSuchElementException("ArrayDeque is empty."));
      }
   }

   public final E firstOrNull() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         int var2 = this.head;
         var1 = access$getElementData$p(this)[var2];
      }

      return var1;
   }

   public E get(int var1) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      var1 = access$positiveMod(this, access$getHead$p(this) + var1);
      return access$getElementData$p(this)[var1];
   }

   public int getSize() {
      return this.size;
   }

   public int indexOf(Object var1) {
      int var2 = this.size();
      int var3 = access$positiveMod(this, access$getHead$p(this) + var2);
      var2 = this.head;
      if (var2 < var3) {
         while(true) {
            if (var2 >= var3) {
               return -1;
            }

            if (Intrinsics.areEqual(var1, this.elementData[var2])) {
               var3 = this.head;
               break;
            }

            ++var2;
         }
      } else {
         if (var2 < var3) {
            return -1;
         }

         int var4 = this.elementData.length;

         while(true) {
            if (var2 >= var4) {
               for(var2 = 0; var2 < var3; ++var2) {
                  if (Intrinsics.areEqual(var1, this.elementData[var2])) {
                     var2 += this.elementData.length;
                     var3 = this.head;
                     return var2 - var3;
                  }
               }

               return -1;
            }

            if (Intrinsics.areEqual(var1, this.elementData[var2])) {
               var3 = this.head;
               break;
            }

            ++var2;
         }
      }

      return var2 - var3;
   }

   public final void internalStructure$kotlin_stdlib(Function2<? super Integer, ? super Object[], Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "structure");
      int var2 = this.size();
      int var3 = access$positiveMod(this, access$getHead$p(this) + var2);
      if (this.isEmpty()) {
         var1.invoke(this.head, new Object[0]);
      } else {
         Object[] var4 = new Object[this.size()];
         var2 = this.head;
         if (var2 < var3) {
            ArraysKt.copyInto$default(this.elementData, var4, 0, var2, var3, 2, (Object)null);
            var1.invoke(this.head, var4);
         } else {
            ArraysKt.copyInto$default(this.elementData, var4, 0, var2, 0, 10, (Object)null);
            Object[] var5 = this.elementData;
            ArraysKt.copyInto(var5, var4, var5.length - this.head, 0, var3);
            var1.invoke(this.head - this.elementData.length, var4);
         }

      }
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final E last() {
      if (!this.isEmpty()) {
         int var1 = CollectionsKt.getLastIndex(this);
         var1 = access$positiveMod(this, access$getHead$p(this) + var1);
         return access$getElementData$p(this)[var1];
      } else {
         throw (Throwable)(new NoSuchElementException("ArrayDeque is empty."));
      }
   }

   public int lastIndexOf(Object var1) {
      int var2 = this.size();
      var2 = access$positiveMod(this, access$getHead$p(this) + var2);
      int var3 = this.head;
      if (var3 < var2) {
         --var2;
         if (var2 < var3) {
            return -1;
         }

         while(!Intrinsics.areEqual(var1, this.elementData[var2])) {
            if (var2 == var3) {
               return -1;
            }

            --var2;
         }

         var3 = this.head;
      } else {
         if (var3 <= var2) {
            return -1;
         }

         --var2;

         while(true) {
            if (var2 < 0) {
               var2 = ArraysKt.getLastIndex(this.elementData);
               var3 = this.head;
               if (var2 < var3) {
                  return -1;
               }

               while(!Intrinsics.areEqual(var1, this.elementData[var2])) {
                  if (var2 == var3) {
                     return -1;
                  }

                  --var2;
               }

               var3 = this.head;
               break;
            }

            if (Intrinsics.areEqual(var1, this.elementData[var2])) {
               var2 += this.elementData.length;
               var3 = this.head;
               break;
            }

            --var2;
         }
      }

      return var2 - var3;
   }

   public final E lastOrNull() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         int var2 = CollectionsKt.getLastIndex(this);
         var2 = access$positiveMod(this, access$getHead$p(this) + var2);
         var1 = access$getElementData$p(this)[var2];
      }

      return var1;
   }

   public final int newCapacity$kotlin_stdlib(int var1, int var2) {
      int var3 = var1 + (var1 >> 1);
      var1 = var3;
      if (var3 - var2 < 0) {
         var1 = var2;
      }

      var3 = var1;
      if (var1 - 2147483639 > 0) {
         if (var2 > 2147483639) {
            var3 = Integer.MAX_VALUE;
         } else {
            var3 = 2147483639;
         }
      }

      return var3;
   }

   public boolean remove(Object var1) {
      int var2 = this.indexOf(var1);
      if (var2 == -1) {
         return false;
      } else {
         this.remove(var2);
         return true;
      }
   }

   public boolean removeAll(Collection<? extends Object> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      boolean var2 = this.isEmpty();
      byte var3 = 0;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = var4;
      if (!var2) {
         boolean var7;
         if (access$getElementData$p(this).length == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var6 = var4;
         } else {
            int var13 = this.size();
            int var8 = access$positiveMod(this, access$getHead$p(this) + var13);
            int var9 = access$getHead$p(this);
            Object var10;
            if (access$getHead$p(this) < var8) {
               int var12 = access$getHead$p(this);

               for(var13 = var9; var12 < var8; ++var12) {
                  var10 = access$getElementData$p(this)[var12];
                  if (var1.contains(var10) ^ true) {
                     access$getElementData$p(this)[var13] = var10;
                     ++var13;
                  } else {
                     var5 = true;
                  }
               }

               ArraysKt.fill(access$getElementData$p(this), (Object)null, var13, var8);
            } else {
               var13 = access$getHead$p(this);
               int var11 = access$getElementData$p(this).length;

               for(var5 = false; var13 < var11; ++var13) {
                  var10 = access$getElementData$p(this)[var13];
                  access$getElementData$p(this)[var13] = null;
                  if (var1.contains(var10) ^ true) {
                     access$getElementData$p(this)[var9] = var10;
                     ++var9;
                  } else {
                     var5 = true;
                  }
               }

               var13 = access$positiveMod(this, var9);

               for(var9 = var3; var9 < var8; ++var9) {
                  var10 = access$getElementData$p(this)[var9];
                  access$getElementData$p(this)[var9] = null;
                  if (var1.contains(var10) ^ true) {
                     access$getElementData$p(this)[var13] = var10;
                     var13 = access$incremented(this, var13);
                  } else {
                     var5 = true;
                  }
               }
            }

            var6 = var5;
            if (var5) {
               access$setSize$p(this, access$negativeMod(this, var13 - access$getHead$p(this)));
               var6 = var5;
            }
         }
      }

      return var6;
   }

   public E removeAt(int var1) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      if (var1 == CollectionsKt.getLastIndex(this)) {
         return this.removeLast();
      } else if (var1 == 0) {
         return this.removeFirst();
      } else {
         int var2 = access$positiveMod(this, access$getHead$p(this) + var1);
         Object var3 = access$getElementData$p(this)[var2];
         Object[] var4;
         if (var1 < this.size() >> 1) {
            var1 = this.head;
            if (var2 >= var1) {
               var4 = this.elementData;
               ArraysKt.copyInto(var4, var4, var1 + 1, var1, var2);
            } else {
               var4 = this.elementData;
               ArraysKt.copyInto(var4, var4, 1, 0, var2);
               var4 = this.elementData;
               var4[0] = var4[var4.length - 1];
               var1 = this.head;
               ArraysKt.copyInto(var4, var4, var1 + 1, var1, var4.length - 1);
            }

            var4 = this.elementData;
            var1 = this.head;
            var4[var1] = null;
            this.head = this.incremented(var1);
         } else {
            var1 = CollectionsKt.getLastIndex(this);
            var1 = access$positiveMod(this, access$getHead$p(this) + var1);
            if (var2 <= var1) {
               var4 = this.elementData;
               ArraysKt.copyInto(var4, var4, var2, var2 + 1, var1 + 1);
            } else {
               var4 = this.elementData;
               ArraysKt.copyInto(var4, var4, var2, var2 + 1, var4.length);
               var4 = this.elementData;
               var4[var4.length - 1] = var4[0];
               ArraysKt.copyInto(var4, var4, 0, 1, var1 + 1);
            }

            this.elementData[var1] = null;
         }

         this.size = this.size() - 1;
         return var3;
      }
   }

   public final E removeFirst() {
      if (!this.isEmpty()) {
         int var1 = this.head;
         Object var2 = access$getElementData$p(this)[var1];
         Object[] var3 = this.elementData;
         var1 = this.head;
         var3[var1] = null;
         this.head = this.incremented(var1);
         this.size = this.size() - 1;
         return var2;
      } else {
         throw (Throwable)(new NoSuchElementException("ArrayDeque is empty."));
      }
   }

   public final E removeFirstOrNull() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeFirst();
      }

      return var1;
   }

   public final E removeLast() {
      if (!this.isEmpty()) {
         int var1 = CollectionsKt.getLastIndex(this);
         var1 = access$positiveMod(this, access$getHead$p(this) + var1);
         Object var2 = access$getElementData$p(this)[var1];
         this.elementData[var1] = null;
         this.size = this.size() - 1;
         return var2;
      } else {
         throw (Throwable)(new NoSuchElementException("ArrayDeque is empty."));
      }
   }

   public final E removeLastOrNull() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeLast();
      }

      return var1;
   }

   public boolean retainAll(Collection<? extends Object> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      boolean var2 = this.isEmpty();
      byte var3 = 0;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = var4;
      if (!var2) {
         boolean var7;
         if (access$getElementData$p(this).length == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var6 = var4;
         } else {
            int var12 = this.size();
            int var8 = access$positiveMod(this, access$getHead$p(this) + var12);
            var12 = access$getHead$p(this);
            int var9;
            Object var10;
            if (access$getHead$p(this) < var8) {
               for(var9 = access$getHead$p(this); var9 < var8; ++var9) {
                  var10 = access$getElementData$p(this)[var9];
                  if (var1.contains(var10)) {
                     access$getElementData$p(this)[var12] = var10;
                     ++var12;
                  } else {
                     var5 = true;
                  }
               }

               ArraysKt.fill(access$getElementData$p(this), (Object)null, var12, var8);
            } else {
               var9 = access$getHead$p(this);
               int var11 = access$getElementData$p(this).length;

               for(var5 = false; var9 < var11; ++var9) {
                  var10 = access$getElementData$p(this)[var9];
                  access$getElementData$p(this)[var9] = null;
                  if (var1.contains(var10)) {
                     access$getElementData$p(this)[var12] = var10;
                     ++var12;
                  } else {
                     var5 = true;
                  }
               }

               var12 = access$positiveMod(this, var12);

               for(var9 = var3; var9 < var8; ++var9) {
                  var10 = access$getElementData$p(this)[var9];
                  access$getElementData$p(this)[var9] = null;
                  if (var1.contains(var10)) {
                     access$getElementData$p(this)[var12] = var10;
                     var12 = access$incremented(this, var12);
                  } else {
                     var5 = true;
                  }
               }
            }

            var6 = var5;
            if (var5) {
               access$setSize$p(this, access$negativeMod(this, var12 - access$getHead$p(this)));
               var6 = var5;
            }
         }
      }

      return var6;
   }

   public E set(int var1, E var2) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      var1 = access$positiveMod(this, access$getHead$p(this) + var1);
      Object var3 = access$getElementData$p(this)[var1];
      this.elementData[var1] = var2;
      return var3;
   }
}
