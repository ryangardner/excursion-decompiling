package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Immutable
final class MessageDigestHashFunction extends AbstractHashFunction implements Serializable {
   private final int bytes;
   private final MessageDigest prototype;
   private final boolean supportsClone;
   private final String toString;

   MessageDigestHashFunction(String var1, int var2, String var3) {
      this.toString = (String)Preconditions.checkNotNull(var3);
      MessageDigest var6 = getMessageDigest(var1);
      this.prototype = var6;
      int var4 = var6.getDigestLength();
      boolean var5;
      if (var2 >= 4 && var2 <= var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "bytes (%s) must be >= 4 and < %s", var2, var4);
      this.bytes = var2;
      this.supportsClone = supportsClone(this.prototype);
   }

   MessageDigestHashFunction(String var1, String var2) {
      MessageDigest var3 = getMessageDigest(var1);
      this.prototype = var3;
      this.bytes = var3.getDigestLength();
      this.toString = (String)Preconditions.checkNotNull(var2);
      this.supportsClone = supportsClone(this.prototype);
   }

   private static MessageDigest getMessageDigest(String var0) {
      try {
         MessageDigest var2 = MessageDigest.getInstance(var0);
         return var2;
      } catch (NoSuchAlgorithmException var1) {
         throw new AssertionError(var1);
      }
   }

   private static boolean supportsClone(MessageDigest var0) {
      try {
         var0.clone();
         return true;
      } catch (CloneNotSupportedException var1) {
         return false;
      }
   }

   public int bits() {
      return this.bytes * 8;
   }

   public Hasher newHasher() {
      if (this.supportsClone) {
         try {
            MessageDigestHashFunction.MessageDigestHasher var1 = new MessageDigestHashFunction.MessageDigestHasher((MessageDigest)this.prototype.clone(), this.bytes);
            return var1;
         } catch (CloneNotSupportedException var2) {
         }
      }

      return new MessageDigestHashFunction.MessageDigestHasher(getMessageDigest(this.prototype.getAlgorithm()), this.bytes);
   }

   public String toString() {
      return this.toString;
   }

   Object writeReplace() {
      return new MessageDigestHashFunction.SerializedForm(this.prototype.getAlgorithm(), this.bytes, this.toString);
   }

   private static final class MessageDigestHasher extends AbstractByteHasher {
      private final int bytes;
      private final MessageDigest digest;
      private boolean done;

      private MessageDigestHasher(MessageDigest var1, int var2) {
         this.digest = var1;
         this.bytes = var2;
      }

      // $FF: synthetic method
      MessageDigestHasher(MessageDigest var1, int var2, Object var3) {
         this(var1, var2);
      }

      private void checkNotDone() {
         Preconditions.checkState(this.done ^ true, "Cannot re-use a Hasher after calling hash() on it");
      }

      public HashCode hash() {
         this.checkNotDone();
         this.done = true;
         HashCode var1;
         if (this.bytes == this.digest.getDigestLength()) {
            var1 = HashCode.fromBytesNoCopy(this.digest.digest());
         } else {
            var1 = HashCode.fromBytesNoCopy(Arrays.copyOf(this.digest.digest(), this.bytes));
         }

         return var1;
      }

      protected void update(byte var1) {
         this.checkNotDone();
         this.digest.update(var1);
      }

      protected void update(ByteBuffer var1) {
         this.checkNotDone();
         this.digest.update(var1);
      }

      protected void update(byte[] var1, int var2, int var3) {
         this.checkNotDone();
         this.digest.update(var1, var2, var3);
      }
   }

   private static final class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      private final String algorithmName;
      private final int bytes;
      private final String toString;

      private SerializedForm(String var1, int var2, String var3) {
         this.algorithmName = var1;
         this.bytes = var2;
         this.toString = var3;
      }

      // $FF: synthetic method
      SerializedForm(String var1, int var2, String var3, Object var4) {
         this(var1, var2, var3);
      }

      private Object readResolve() {
         return new MessageDigestHashFunction(this.algorithmName, this.bytes, this.toString);
      }
   }
}
