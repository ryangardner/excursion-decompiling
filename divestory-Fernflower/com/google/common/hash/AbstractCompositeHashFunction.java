package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Immutable
abstract class AbstractCompositeHashFunction extends AbstractHashFunction {
   private static final long serialVersionUID = 0L;
   final HashFunction[] functions;

   AbstractCompositeHashFunction(HashFunction... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Preconditions.checkNotNull(var1[var3]);
      }

      this.functions = var1;
   }

   private Hasher fromHashers(final Hasher[] var1) {
      return new Hasher() {
         public HashCode hash() {
            return AbstractCompositeHashFunction.this.makeHash(var1);
         }

         public Hasher putBoolean(boolean var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putBoolean(var1x);
            }

            return this;
         }

         public Hasher putByte(byte var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putByte(var1x);
            }

            return this;
         }

         public Hasher putBytes(ByteBuffer var1x) {
            int var2 = var1x.position();
            Hasher[] var3 = var1;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Hasher var6 = var3[var5];
               var1x.position(var2);
               var6.putBytes(var1x);
            }

            return this;
         }

         public Hasher putBytes(byte[] var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putBytes(var1x);
            }

            return this;
         }

         public Hasher putBytes(byte[] var1x, int var2, int var3) {
            Hasher[] var4 = var1;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               var4[var6].putBytes(var1x, var2, var3);
            }

            return this;
         }

         public Hasher putChar(char var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putChar(var1x);
            }

            return this;
         }

         public Hasher putDouble(double var1x) {
            Hasher[] var3 = var1;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3[var5].putDouble(var1x);
            }

            return this;
         }

         public Hasher putFloat(float var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putFloat(var1x);
            }

            return this;
         }

         public Hasher putInt(int var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putInt(var1x);
            }

            return this;
         }

         public Hasher putLong(long var1x) {
            Hasher[] var3 = var1;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3[var5].putLong(var1x);
            }

            return this;
         }

         public <T> Hasher putObject(T var1x, Funnel<? super T> var2) {
            Hasher[] var3 = var1;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3[var5].putObject(var1x, var2);
            }

            return this;
         }

         public Hasher putShort(short var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putShort(var1x);
            }

            return this;
         }

         public Hasher putString(CharSequence var1x, Charset var2) {
            Hasher[] var3 = var1;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3[var5].putString(var1x, var2);
            }

            return this;
         }

         public Hasher putUnencodedChars(CharSequence var1x) {
            Hasher[] var2 = var1;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               var2[var4].putUnencodedChars(var1x);
            }

            return this;
         }
      };
   }

   abstract HashCode makeHash(Hasher[] var1);

   public Hasher newHasher() {
      int var1 = this.functions.length;
      Hasher[] var2 = new Hasher[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = this.functions[var3].newHasher();
      }

      return this.fromHashers(var2);
   }

   public Hasher newHasher(int var1) {
      int var2 = 0;
      boolean var3;
      if (var1 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      int var4 = this.functions.length;

      Hasher[] var5;
      for(var5 = new Hasher[var4]; var2 < var4; ++var2) {
         var5[var2] = this.functions[var2].newHasher(var1);
      }

      return this.fromHashers(var5);
   }
}
