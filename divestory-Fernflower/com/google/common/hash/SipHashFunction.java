package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class SipHashFunction extends AbstractHashFunction implements Serializable {
   static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
   private static final long serialVersionUID = 0L;
   private final int c;
   private final int d;
   private final long k0;
   private final long k1;

   SipHashFunction(int var1, int var2, long var3, long var5) {
      boolean var7 = true;
      boolean var8;
      if (var1 > 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "The number of SipRound iterations (c=%s) during Compression must be positive.", var1);
      if (var2 > 0) {
         var8 = var7;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "The number of SipRound iterations (d=%s) during Finalization must be positive.", var2);
      this.c = var1;
      this.d = var2;
      this.k0 = var3;
      this.k1 = var5;
   }

   public int bits() {
      return 64;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof SipHashFunction;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         SipHashFunction var5 = (SipHashFunction)var1;
         var4 = var3;
         if (this.c == var5.c) {
            var4 = var3;
            if (this.d == var5.d) {
               var4 = var3;
               if (this.k0 == var5.k0) {
                  var4 = var3;
                  if (this.k1 == var5.k1) {
                     var4 = true;
                  }
               }
            }
         }
      }

      return var4;
   }

   public int hashCode() {
      return (int)((long)(this.getClass().hashCode() ^ this.c ^ this.d) ^ this.k0 ^ this.k1);
   }

   public Hasher newHasher() {
      return new SipHashFunction.SipHasher(this.c, this.d, this.k0, this.k1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Hashing.sipHash");
      var1.append(this.c);
      var1.append("");
      var1.append(this.d);
      var1.append("(");
      var1.append(this.k0);
      var1.append(", ");
      var1.append(this.k1);
      var1.append(")");
      return var1.toString();
   }

   private static final class SipHasher extends AbstractStreamingHasher {
      private static final int CHUNK_SIZE = 8;
      private long b = 0L;
      private final int c;
      private final int d;
      private long finalM = 0L;
      private long v0 = 8317987319222330741L;
      private long v1 = 7237128888997146477L;
      private long v2 = 7816392313619706465L;
      private long v3 = 8387220255154660723L;

      SipHasher(int var1, int var2, long var3, long var5) {
         super(8);
         this.c = var1;
         this.d = var2;
         this.v0 = 8317987319222330741L ^ var3;
         this.v1 = 7237128888997146477L ^ var5;
         this.v2 = 7816392313619706465L ^ var3;
         this.v3 = 8387220255154660723L ^ var5;
      }

      private void processM(long var1) {
         this.v3 ^= var1;
         this.sipRound(this.c);
         this.v0 ^= var1;
      }

      private void sipRound(int var1) {
         for(int var2 = 0; var2 < var1; ++var2) {
            long var3 = this.v0;
            long var5 = this.v1;
            this.v0 = var3 + var5;
            this.v2 += this.v3;
            this.v1 = Long.rotateLeft(var5, 13);
            long var7 = Long.rotateLeft(this.v3, 16);
            this.v3 = var7;
            var3 = this.v1;
            var5 = this.v0;
            this.v1 = var3 ^ var5;
            this.v3 = var7 ^ this.v2;
            var3 = Long.rotateLeft(var5, 32);
            this.v0 = var3;
            var5 = this.v2;
            var7 = this.v1;
            this.v2 = var5 + var7;
            this.v0 = var3 + this.v3;
            this.v1 = Long.rotateLeft(var7, 17);
            var7 = Long.rotateLeft(this.v3, 21);
            this.v3 = var7;
            var3 = this.v1;
            var5 = this.v2;
            this.v1 = var3 ^ var5;
            this.v3 = var7 ^ this.v0;
            this.v2 = Long.rotateLeft(var5, 32);
         }

      }

      public HashCode makeHash() {
         long var1 = this.finalM ^ this.b << 56;
         this.finalM = var1;
         this.processM(var1);
         this.v2 ^= 255L;
         this.sipRound(this.d);
         return HashCode.fromLong(this.v0 ^ this.v1 ^ this.v2 ^ this.v3);
      }

      protected void process(ByteBuffer var1) {
         this.b += 8L;
         this.processM(var1.getLong());
      }

      protected void processRemaining(ByteBuffer var1) {
         this.b += (long)var1.remaining();

         for(int var2 = 0; var1.hasRemaining(); var2 += 8) {
            this.finalM ^= ((long)var1.get() & 255L) << var2;
         }

      }
   }
}
