package androidx.collection;

public final class CircularIntArray {
   private int mCapacityBitmask;
   private int[] mElements;
   private int mHead;
   private int mTail;

   public CircularIntArray() {
      this(8);
   }

   public CircularIntArray(int var1) {
      if (var1 >= 1) {
         if (var1 <= 1073741824) {
            int var2 = var1;
            if (Integer.bitCount(var1) != 1) {
               var2 = Integer.highestOneBit(var1 - 1) << 1;
            }

            this.mCapacityBitmask = var2 - 1;
            this.mElements = new int[var2];
         } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
         }
      } else {
         throw new IllegalArgumentException("capacity must be >= 1");
      }
   }

   private void doubleCapacity() {
      int[] var1 = this.mElements;
      int var2 = var1.length;
      int var3 = this.mHead;
      int var4 = var2 - var3;
      int var5 = var2 << 1;
      if (var5 >= 0) {
         int[] var6 = new int[var5];
         System.arraycopy(var1, var3, var6, 0, var4);
         System.arraycopy(this.mElements, 0, var6, var4, this.mHead);
         this.mElements = var6;
         this.mHead = 0;
         this.mTail = var2;
         this.mCapacityBitmask = var5 - 1;
      } else {
         throw new RuntimeException("Max array capacity exceeded");
      }
   }

   public void addFirst(int var1) {
      int var2 = this.mHead - 1 & this.mCapacityBitmask;
      this.mHead = var2;
      this.mElements[var2] = var1;
      if (var2 == this.mTail) {
         this.doubleCapacity();
      }

   }

   public void addLast(int var1) {
      int[] var2 = this.mElements;
      int var3 = this.mTail;
      var2[var3] = var1;
      var1 = this.mCapacityBitmask & var3 + 1;
      this.mTail = var1;
      if (var1 == this.mHead) {
         this.doubleCapacity();
      }

   }

   public void clear() {
      this.mTail = this.mHead;
   }

   public int get(int var1) {
      if (var1 >= 0 && var1 < this.size()) {
         int[] var2 = this.mElements;
         int var3 = this.mHead;
         return var2[this.mCapacityBitmask & var3 + var1];
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public int getFirst() {
      int var1 = this.mHead;
      if (var1 != this.mTail) {
         return this.mElements[var1];
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public int getLast() {
      int var1 = this.mHead;
      int var2 = this.mTail;
      if (var1 != var2) {
         return this.mElements[var2 - 1 & this.mCapacityBitmask];
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.mHead == this.mTail) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int popFirst() {
      int var1 = this.mHead;
      if (var1 != this.mTail) {
         int var2 = this.mElements[var1];
         this.mHead = var1 + 1 & this.mCapacityBitmask;
         return var2;
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public int popLast() {
      int var1 = this.mHead;
      int var2 = this.mTail;
      if (var1 != var2) {
         var2 = this.mCapacityBitmask & var2 - 1;
         var1 = this.mElements[var2];
         this.mTail = var2;
         return var1;
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public void removeFromEnd(int var1) {
      if (var1 > 0) {
         if (var1 <= this.size()) {
            int var2 = this.mTail;
            this.mTail = this.mCapacityBitmask & var2 - var1;
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   public void removeFromStart(int var1) {
      if (var1 > 0) {
         if (var1 <= this.size()) {
            int var2 = this.mHead;
            this.mHead = this.mCapacityBitmask & var2 + var1;
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   public int size() {
      return this.mTail - this.mHead & this.mCapacityBitmask;
   }
}
