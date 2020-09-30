package com.sun.mail.iap;

import java.io.ByteArrayInputStream;

public class ByteArray {
   private byte[] bytes;
   private int count;
   private int start;

   public ByteArray(int var1) {
      this(new byte[var1], 0, var1);
   }

   public ByteArray(byte[] var1, int var2, int var3) {
      this.bytes = var1;
      this.start = var2;
      this.count = var3;
   }

   public byte[] getBytes() {
      return this.bytes;
   }

   public int getCount() {
      return this.count;
   }

   public byte[] getNewBytes() {
      int var1 = this.count;
      byte[] var2 = new byte[var1];
      System.arraycopy(this.bytes, this.start, var2, 0, var1);
      return var2;
   }

   public int getStart() {
      return this.start;
   }

   public void grow(int var1) {
      byte[] var2 = this.bytes;
      byte[] var3 = new byte[var2.length + var1];
      System.arraycopy(var2, 0, var3, 0, var2.length);
      this.bytes = var3;
   }

   public void setCount(int var1) {
      this.count = var1;
   }

   public ByteArrayInputStream toByteArrayInputStream() {
      return new ByteArrayInputStream(this.bytes, this.start, this.count);
   }
}
