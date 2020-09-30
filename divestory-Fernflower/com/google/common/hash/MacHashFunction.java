package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

@Immutable
final class MacHashFunction extends AbstractHashFunction {
   private final int bits;
   private final Key key;
   private final Mac prototype;
   private final boolean supportsClone;
   private final String toString;

   MacHashFunction(String var1, Key var2, String var3) {
      this.prototype = getMac(var1, var2);
      this.key = (Key)Preconditions.checkNotNull(var2);
      this.toString = (String)Preconditions.checkNotNull(var3);
      this.bits = this.prototype.getMacLength() * 8;
      this.supportsClone = supportsClone(this.prototype);
   }

   private static Mac getMac(String var0, Key var1) {
      try {
         Mac var4 = Mac.getInstance(var0);
         var4.init(var1);
         return var4;
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException(var2);
      } catch (InvalidKeyException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   private static boolean supportsClone(Mac var0) {
      try {
         var0.clone();
         return true;
      } catch (CloneNotSupportedException var1) {
         return false;
      }
   }

   public int bits() {
      return this.bits;
   }

   public Hasher newHasher() {
      if (this.supportsClone) {
         try {
            MacHashFunction.MacHasher var1 = new MacHashFunction.MacHasher((Mac)this.prototype.clone());
            return var1;
         } catch (CloneNotSupportedException var2) {
         }
      }

      return new MacHashFunction.MacHasher(getMac(this.prototype.getAlgorithm(), this.key));
   }

   public String toString() {
      return this.toString;
   }

   private static final class MacHasher extends AbstractByteHasher {
      private boolean done;
      private final Mac mac;

      private MacHasher(Mac var1) {
         this.mac = var1;
      }

      // $FF: synthetic method
      MacHasher(Mac var1, Object var2) {
         this(var1);
      }

      private void checkNotDone() {
         Preconditions.checkState(this.done ^ true, "Cannot re-use a Hasher after calling hash() on it");
      }

      public HashCode hash() {
         this.checkNotDone();
         this.done = true;
         return HashCode.fromBytesNoCopy(this.mac.doFinal());
      }

      protected void update(byte var1) {
         this.checkNotDone();
         this.mac.update(var1);
      }

      protected void update(ByteBuffer var1) {
         this.checkNotDone();
         Preconditions.checkNotNull(var1);
         this.mac.update(var1);
      }

      protected void update(byte[] var1) {
         this.checkNotDone();
         this.mac.update(var1);
      }

      protected void update(byte[] var1, int var2, int var3) {
         this.checkNotDone();
         this.mac.update(var1, var2, var3);
      }
   }
}
