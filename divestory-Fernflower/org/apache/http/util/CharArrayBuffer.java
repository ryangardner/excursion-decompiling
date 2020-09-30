package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.protocol.HTTP;

public final class CharArrayBuffer implements Serializable {
   private static final long serialVersionUID = -6208952725094867135L;
   private char[] buffer;
   private int len;

   public CharArrayBuffer(int var1) {
      if (var1 >= 0) {
         this.buffer = new char[var1];
      } else {
         throw new IllegalArgumentException("Buffer capacity may not be negative");
      }
   }

   private void expand(int var1) {
      char[] var2 = new char[Math.max(this.buffer.length << 1, var1)];
      System.arraycopy(this.buffer, 0, var2, 0, this.len);
      this.buffer = var2;
   }

   public void append(char var1) {
      int var2 = this.len + 1;
      if (var2 > this.buffer.length) {
         this.expand(var2);
      }

      this.buffer[this.len] = (char)var1;
      this.len = var2;
   }

   public void append(Object var1) {
      this.append(String.valueOf(var1));
   }

   public void append(String var1) {
      String var2 = var1;
      if (var1 == null) {
         var2 = "null";
      }

      int var3 = var2.length();
      int var4 = this.len + var3;
      if (var4 > this.buffer.length) {
         this.expand(var4);
      }

      var2.getChars(0, var3, this.buffer, this.len);
      this.len = var4;
   }

   public void append(ByteArrayBuffer var1, int var2, int var3) {
      if (var1 != null) {
         this.append(var1.buffer(), var2, var3);
      }
   }

   public void append(CharArrayBuffer var1) {
      if (var1 != null) {
         this.append((char[])var1.buffer, 0, var1.len);
      }
   }

   public void append(CharArrayBuffer var1, int var2, int var3) {
      if (var1 != null) {
         this.append(var1.buffer, var2, var3);
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
                  this.buffer[var3] = (char)((char)(var1[var4] & 255));
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

   public void append(char[] var1, int var2, int var3) {
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

   public char[] buffer() {
      return this.buffer;
   }

   public int capacity() {
      return this.buffer.length;
   }

   public char charAt(int var1) {
      return this.buffer[var1];
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

   public int indexOf(int var1) {
      return this.indexOf(var1, 0, this.len);
   }

   public int indexOf(int var1, int var2, int var3) {
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

   public String substring(int var1, int var2) {
      return new String(this.buffer, var1, var2 - var1);
   }

   public String substringTrimmed(int var1, int var2) {
      StringBuffer var4;
      if (var1 < 0) {
         var4 = new StringBuffer();
         var4.append("Negative beginIndex: ");
         var4.append(var1);
         throw new IndexOutOfBoundsException(var4.toString());
      } else if (var2 > this.len) {
         var4 = new StringBuffer();
         var4.append("endIndex: ");
         var4.append(var2);
         var4.append(" > length: ");
         var4.append(this.len);
         throw new IndexOutOfBoundsException(var4.toString());
      } else if (var1 > var2) {
         var4 = new StringBuffer();
         var4.append("beginIndex: ");
         var4.append(var1);
         var4.append(" > endIndex: ");
         var4.append(var2);
         throw new IndexOutOfBoundsException(var4.toString());
      } else {
         int var3;
         while(true) {
            var3 = var2;
            if (var1 >= var2) {
               break;
            }

            var3 = var2;
            if (!HTTP.isWhitespace(this.buffer[var1])) {
               break;
            }

            ++var1;
         }

         while(var3 > var1 && HTTP.isWhitespace(this.buffer[var3 - 1])) {
            --var3;
         }

         return new String(this.buffer, var1, var3 - var1);
      }
   }

   public char[] toCharArray() {
      int var1 = this.len;
      char[] var2 = new char[var1];
      if (var1 > 0) {
         System.arraycopy(this.buffer, 0, var2, 0, var1);
      }

      return var2;
   }

   public String toString() {
      return new String(this.buffer, 0, this.len);
   }
}
