package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QPEncoderStream extends FilterOutputStream {
   private static final char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private int bytesPerLine;
   private int count;
   private boolean gotCR;
   private boolean gotSpace;

   public QPEncoderStream(OutputStream var1) {
      this(var1, 76);
   }

   public QPEncoderStream(OutputStream var1, int var2) {
      super(var1);
      this.count = 0;
      this.gotSpace = false;
      this.gotCR = false;
      this.bytesPerLine = var2 - 1;
   }

   private void outputCRLF() throws IOException {
      this.out.write(13);
      this.out.write(10);
      this.count = 0;
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   protected void output(int var1, boolean var2) throws IOException {
      int var3;
      if (var2) {
         var3 = this.count + 3;
         this.count = var3;
         if (var3 > this.bytesPerLine) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 3;
         }

         this.out.write(61);
         this.out.write(hex[var1 >> 4]);
         this.out.write(hex[var1 & 15]);
      } else {
         var3 = this.count + 1;
         this.count = var3;
         if (var3 > this.bytesPerLine) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 1;
         }

         this.out.write(var1);
      }

   }

   public void write(int var1) throws IOException {
      var1 &= 255;
      if (this.gotSpace) {
         if (var1 != 13 && var1 != 10) {
            this.output(32, false);
         } else {
            this.output(32, true);
         }

         this.gotSpace = false;
      }

      if (var1 == 13) {
         this.gotCR = true;
         this.outputCRLF();
      } else {
         if (var1 == 10) {
            if (!this.gotCR) {
               this.outputCRLF();
            }
         } else if (var1 == 32) {
            this.gotSpace = true;
         } else if (var1 >= 32 && var1 < 127 && var1 != 61) {
            this.output(var1, false);
         } else {
            this.output(var1, true);
         }

         this.gotCR = false;
      }

   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         this.write(var1[var2 + var4]);
      }

   }
}
