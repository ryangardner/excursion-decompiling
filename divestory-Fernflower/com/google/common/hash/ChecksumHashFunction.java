package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.zip.Checksum;

@Immutable
final class ChecksumHashFunction extends AbstractHashFunction implements Serializable {
   private static final long serialVersionUID = 0L;
   private final int bits;
   private final ImmutableSupplier<? extends Checksum> checksumSupplier;
   private final String toString;

   ChecksumHashFunction(ImmutableSupplier<? extends Checksum> var1, int var2, String var3) {
      this.checksumSupplier = (ImmutableSupplier)Preconditions.checkNotNull(var1);
      boolean var4;
      if (var2 != 32 && var2 != 64) {
         var4 = false;
      } else {
         var4 = true;
      }

      Preconditions.checkArgument(var4, "bits (%s) must be either 32 or 64", var2);
      this.bits = var2;
      this.toString = (String)Preconditions.checkNotNull(var3);
   }

   public int bits() {
      return this.bits;
   }

   public Hasher newHasher() {
      return new ChecksumHashFunction.ChecksumHasher((Checksum)this.checksumSupplier.get());
   }

   public String toString() {
      return this.toString;
   }

   private final class ChecksumHasher extends AbstractByteHasher {
      private final Checksum checksum;

      private ChecksumHasher(Checksum var2) {
         this.checksum = (Checksum)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      ChecksumHasher(Checksum var2, Object var3) {
         this(var2);
      }

      public HashCode hash() {
         long var1 = this.checksum.getValue();
         return ChecksumHashFunction.this.bits == 32 ? HashCode.fromInt((int)var1) : HashCode.fromLong(var1);
      }

      protected void update(byte var1) {
         this.checksum.update(var1);
      }

      protected void update(byte[] var1, int var2, int var3) {
         this.checksum.update(var1, var2, var3);
      }
   }
}
