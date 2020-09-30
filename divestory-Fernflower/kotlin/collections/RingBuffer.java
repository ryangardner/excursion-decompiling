package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001d\u0012\u000e\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0096\u0002J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0006J\u0015\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014¢\u0006\u0002\u0010#J'\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014¢\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00020\u0006*\u00020\u00062\u0006\u0010!\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"},
   d2 = {"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class RingBuffer<T> extends AbstractList<T> implements RandomAccess {
   private final Object[] buffer;
   private final int capacity;
   private int size;
   private int startIndex;

   public RingBuffer(int var1) {
      this(new Object[var1], 0);
   }

   public RingBuffer(Object[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      super();
      this.buffer = var1;
      boolean var3 = true;
      boolean var4;
      if (var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var5;
      if (var4) {
         if (var2 <= this.buffer.length) {
            var4 = var3;
         } else {
            var4 = false;
         }

         if (var4) {
            this.capacity = this.buffer.length;
            this.size = var2;
         } else {
            var5 = new StringBuilder();
            var5.append("ring buffer filled size: ");
            var5.append(var2);
            var5.append(" cannot be larger than the buffer size: ");
            var5.append(this.buffer.length);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      } else {
         var5 = new StringBuilder();
         var5.append("ring buffer filled size should not be negative but it is ");
         var5.append(var2);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   // $FF: synthetic method
   public static final int access$forward(RingBuffer var0, int var1, int var2) {
      return var0.forward(var1, var2);
   }

   // $FF: synthetic method
   public static final int access$getSize$p(RingBuffer var0) {
      return var0.size();
   }

   // $FF: synthetic method
   public static final void access$setSize$p(RingBuffer var0, int var1) {
      var0.size = var1;
   }

   // $FF: synthetic method
   public static final void access$setStartIndex$p(RingBuffer var0, int var1) {
      var0.startIndex = var1;
   }

   private final int forward(int var1, int var2) {
      return (var1 + var2) % access$getCapacity$p(this);
   }

   public final void add(T var1) {
      if (!this.isFull()) {
         this.buffer[(this.startIndex + this.size()) % access$getCapacity$p(this)] = var1;
         this.size = this.size() + 1;
      } else {
         throw (Throwable)(new IllegalStateException("ring buffer is full"));
      }
   }

   public final RingBuffer<T> expanded(int var1) {
      int var2 = this.capacity;
      var1 = RangesKt.coerceAtMost(var2 + (var2 >> 1) + 1, var1);
      Object[] var3;
      if (this.startIndex == 0) {
         var3 = Arrays.copyOf(this.buffer, var1);
         Intrinsics.checkExpressionValueIsNotNull(var3, "java.util.Arrays.copyOf(this, newSize)");
      } else {
         var3 = this.toArray(new Object[var1]);
      }

      return new RingBuffer(var3, this.size());
   }

   public T get(int var1) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      return this.buffer[(this.startIndex + var1) % access$getCapacity$p(this)];
   }

   public int getSize() {
      return this.size;
   }

   public final boolean isFull() {
      boolean var1;
      if (this.size() == this.capacity) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Iterator<T> iterator() {
      return (Iterator)(new AbstractIterator<T>() {
         private int count = RingBuffer.this.size();
         private int index;

         {
            this.index = RingBuffer.this.startIndex;
         }

         protected void computeNext() {
            if (this.count == 0) {
               this.done();
            } else {
               this.setNext(RingBuffer.this.buffer[this.index]);
               RingBuffer var1 = RingBuffer.this;
               this.index = (this.index + 1) % var1.capacity;
               --this.count;
            }

         }
      });
   }

   public final void removeFirst(int var1) {
      boolean var2 = true;
      boolean var3;
      if (var1 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      StringBuilder var4;
      if (var3) {
         if (var1 <= this.size()) {
            var3 = var2;
         } else {
            var3 = false;
         }

         if (var3) {
            if (var1 > 0) {
               int var6 = this.startIndex;
               int var5 = (var6 + var1) % access$getCapacity$p(this);
               if (var6 > var5) {
                  ArraysKt.fill(this.buffer, (Object)null, var6, this.capacity);
                  ArraysKt.fill(this.buffer, (Object)null, 0, var5);
               } else {
                  ArraysKt.fill(this.buffer, (Object)null, var6, var5);
               }

               this.startIndex = var5;
               this.size = this.size() - var1;
            }

         } else {
            var4 = new StringBuilder();
            var4.append("n shouldn't be greater than the buffer size: n = ");
            var4.append(var1);
            var4.append(", size = ");
            var4.append(this.size());
            throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
         }
      } else {
         var4 = new StringBuilder();
         var4.append("n shouldn't be negative but it is ");
         var4.append(var1);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   public <T> T[] toArray(T[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "array");
      Object[] var2 = var1;
      if (var1.length < this.size()) {
         var2 = Arrays.copyOf(var1, this.size());
         Intrinsics.checkExpressionValueIsNotNull(var2, "java.util.Arrays.copyOf(this, newSize)");
      }

      int var3 = this.size();
      int var4 = this.startIndex;
      byte var5 = 0;
      int var6 = 0;

      int var7;
      int var8;
      while(true) {
         var7 = var5;
         var8 = var6;
         if (var6 >= var3) {
            break;
         }

         var7 = var5;
         var8 = var6;
         if (var4 >= this.capacity) {
            break;
         }

         var2[var6] = this.buffer[var4];
         ++var6;
         ++var4;
      }

      while(var8 < var3) {
         var2[var8] = this.buffer[var7];
         ++var8;
         ++var7;
      }

      if (var2.length > this.size()) {
         var2[this.size()] = null;
      }

      if (var2 != null) {
         return var2;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }
}
