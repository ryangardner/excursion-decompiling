package kotlin.random;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B7\b\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lkotlin/random/XorWowRandom;", "Lkotlin/random/Random;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class XorWowRandom extends Random {
   private int addend;
   private int v;
   private int w;
   private int x;
   private int y;
   private int z;

   public XorWowRandom(int var1, int var2) {
      this(var1, var2, 0, 0, var1, var1 << 10 ^ var2 >>> 4);
   }

   public XorWowRandom(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      this.w = var4;
      this.v = var5;
      this.addend = var6;
      byte var8 = 0;
      boolean var7;
      if ((var1 | var2 | var3 | var4 | var5) != 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         throw (Throwable)(new IllegalArgumentException("Initial state must have at least one non-zero element.".toString()));
      } else {
         for(var1 = var8; var1 < 64; ++var1) {
            this.nextInt();
         }

      }
   }

   public int nextBits(int var1) {
      return RandomKt.takeUpperBits(this.nextInt(), var1);
   }

   public int nextInt() {
      int var1 = this.x;
      int var2 = var1 ^ var1 >>> 2;
      this.x = this.y;
      this.y = this.z;
      this.z = this.w;
      var1 = this.v;
      this.w = var1;
      var2 = var2 ^ var2 << 1 ^ var1 ^ var1 << 4;
      this.v = var2;
      var1 = this.addend + 362437;
      this.addend = var1;
      return var2 + var1;
   }
}
