package org.apache.http.util;

import java.io.Serializable;

public final class ByteArrayBuffer implements Serializable {
   private static final long serialVersionUID = 4359112959524048036L;
   private byte[] buffer;
   private int len;

   public ByteArrayBuffer(int var1) {
      if (var1 >= 0) {
         this.buffer = new byte[var1];
      } else {
         throw new IllegalArgumentException("Buffer capacity may not be negative");
      }
   }

   private void expand(int var1) {
      byte[] var2 = new byte[Math.max(this.buffer.length << 1, var1)];
      System.arraycopy(this.buffer, 0, var2, 0, this.len);
      this.buffer = var2;
   }

   public void append(int var1) {
      int var2 = this.len + 1;
      if (var2 > this.buffer.length) {
         this.expand(var2);
      }

      this.buffer[this.len] = (byte)((byte)var1);
      this.len = var2;
   }

   public void append(CharArrayBuffer var1, int var2, int var3) {
      if (var1 != null) {
         this.append(var1.buffer(), var2, var3);
      }
   }

   public void append(byte[] var1, int var2, int var3) {
      if (var1 != null) {
         if (var2 >= 0 && var2 <= var1.length && var3 >= 0) {
            int var4 = var2 + var3;
            if (var4 >= 0 && var4 <= var1.length) {
               if (var3 == 0) {
                  return;
               }

               var4 = this.len + var3;
               if (var4 > this.buffer.length) {
                  this.expand(var4);
               }

               System.arraycopy(var1, var2, this.buffer, this.len, var3);
               this.len = var4;
               return;
            }
         }

         StringBuffer var5 = new StringBuffer();
         var5.append("off: ");
         var5.append(var2);
         var5.append(" len: ");
         var5.append(var3);
         var5.append(" b.length: ");
         var5.append(var1.length);
         throw new IndexOutOfBoundsException(var5.toString());
      }
   }

   public void append(char[] var1, int var2, int var3) {
      if (var1 != null) {
         if (var2 >= 0 && var2 <= var1.length && var3 >= 0) {
            int var4 = var2 + var3;
            if (var4 >= 0 && var4 <= var1.length) {
               if (var3 == 0) {
                  return;
               }

               int var5 = this.len;
               int var6 = var3 + var5;
               var3 = var5;
               var4 = var2;
               if (var6 > this.buffer.length) {
                  this.expand(var6);
                  var4 = var2;
                  var3 = var5;
               }

               while(var3 < var6) {
                  this.buffer[var3] = (byte)((byte)var1[var4]);
                  ++var4;
                  ++var3;
               }

               this.len = var6;
               return;
            }
         }

         StringBuffer var7 = new StringBuffer();
         var7.append("off: ");
         var7.append(var2);
         var7.append(" len: ");
         var7.append(var3);
         var7.append(" b.length: ");
         var7.append(var1.length);
         throw new IndexOutOfBoundsException(var7.toString());
      }
   }

   public byte[] buffer() {
      return this.buffer;
   }

   public int byteAt(int var1) {
      return this.buffer[var1];
   }

   public int capacity() {
      return this.buffer.length;
   }

   public void clear() {
      this.len = 0;
   }

   public void ensureCapacity(int var1) {
      if (var1 > 0) {
         int var2 = this.buffer.length;
         int var3 = this.len;
         if (var1 > var2 - var3) {
            this.expand(var3 + var1);
         }

      }
   }

   public int indexOf(byte var1) {
      return this.indexOf(var1, 0, this.len);
   }

   public int indexOf(byte var1, int var2, int var3) {
      int var4 = var2;
      if (var2 < 0) {
         var4 = 0;
      }

      int var5 = this.len;
      var2 = var3;
      if (var3 > var5) {
         var2 = var5;
      }

      var3 = var4;
      if (var4 > var2) {
         return -1;
      } else {
         while(var3 < var2) {
            if (this.buffer[var3] == var1) {
               return var3;
            }

            ++var3;
         }

         return -1;
      }
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.len == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isFull() {
      boolean var1;
      if (this.len == this.buffer.length) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int length() {
      return this.len;
   }

   public void setLength(int var1) {
      if (var1 >= 0 && var1 <= this.buffer.length) {
         this.len = var1;
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append("len: ");
         var2.append(var1);
         var2.append(" < 0 or > buffer len: ");
         var2.append(this.buffer.length);
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   public byte[] toByteArray() {
      int var1 = this.len;
      byte[] var2 = new byte[var1];
      if (var1 > 0) {
         System.arraycopy(this.buffer, 0, var2, 0, var1);
      }

      return var2;
   }
}
