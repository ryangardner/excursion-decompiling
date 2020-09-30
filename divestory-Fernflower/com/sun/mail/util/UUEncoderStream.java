package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class UUEncoderStream extends FilterOutputStream {
   private byte[] buffer;
   private int bufsize;
   protected int mode;
   protected String name;
   private boolean wrotePrefix;

   public UUEncoderStream(OutputStream var1) {
      this(var1, "encoder.buf", 644);
   }

   public UUEncoderStream(OutputStream var1, String var2) {
      this(var1, var2, 644);
   }

   public UUEncoderStream(OutputStream var1, String var2, int var3) {
      super(var1);
      this.bufsize = 0;
      this.wrotePrefix = false;
      this.name = var2;
      this.mode = var3;
      this.buffer = new byte[45];
   }

   private void encode() throws IOException {
      this.out.write((this.bufsize & 63) + 32);
      int var1 = 0;

      while(true) {
         int var2 = this.bufsize;
         if (var1 >= var2) {
            this.out.write(10);
            return;
         }

         byte var5;
         byte var8;
         byte var10;
         label19: {
            byte[] var3 = this.buffer;
            int var4 = var1 + 1;
            var5 = var3[var1];
            byte var6 = 1;
            if (var4 < var2) {
               int var9 = var4 + 1;
               byte var7 = var3[var4];
               if (var9 < var2) {
                  var2 = var9 + 1;
                  var10 = var3[var9];
                  var8 = var7;
                  var1 = var2;
                  break label19;
               }

               var8 = var7;
               var1 = var9;
            } else {
               var1 = var4;
               var8 = var6;
            }

            var10 = 1;
         }

         this.out.write((var5 >>> 2 & 63) + 32);
         this.out.write((var5 << 4 & 48 | var8 >>> 4 & 15) + 32);
         this.out.write((var8 << 2 & 60 | var10 >>> 6 & 3) + 32);
         this.out.write((var10 & 63) + 32);
      }
   }

   private void writePrefix() throws IOException {
      if (!this.wrotePrefix) {
         PrintStream var1 = new PrintStream(this.out);
         StringBuilder var2 = new StringBuilder("begin ");
         var2.append(this.mode);
         var2.append(" ");
         var2.append(this.name);
         var1.println(var2.toString());
         var1.flush();
         this.wrotePrefix = true;
      }

   }

   private void writeSuffix() throws IOException {
      PrintStream var1 = new PrintStream(this.out);
      var1.println(" \nend");
      var1.flush();
   }

   public void close() throws IOException {
      this.flush();
      this.out.close();
   }

   public void flush() throws IOException {
      if (this.bufsize > 0) {
         this.writePrefix();
         this.encode();
      }

      this.writeSuffix();
      this.out.flush();
   }

   public void setNameMode(String var1, int var2) {
      this.name = var1;
      this.mode = var2;
   }

   public void write(int var1) throws IOException {
      byte[] var2 = this.buffer;
      int var3 = this.bufsize;
      int var4 = var3 + 1;
      this.bufsize = var4;
      var2[var3] = (byte)((byte)var1);
      if (var4 == 45) {
         this.writePrefix();
         this.encode();
         this.bufsize = 0;
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
