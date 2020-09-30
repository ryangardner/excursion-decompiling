package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TraceOutputStream extends FilterOutputStream {
   private boolean quote = false;
   private boolean trace = false;
   private OutputStream traceOut;

   public TraceOutputStream(OutputStream var1, OutputStream var2) {
      super(var1);
      this.traceOut = var2;
   }

   private final void writeByte(int var1) throws IOException {
      int var2 = var1 & 255;
      var1 = var2;
      if (var2 > 127) {
         this.traceOut.write(77);
         this.traceOut.write(45);
         var1 = var2 & 127;
      }

      if (var1 == 13) {
         this.traceOut.write(92);
         this.traceOut.write(114);
      } else if (var1 == 10) {
         this.traceOut.write(92);
         this.traceOut.write(110);
         this.traceOut.write(10);
      } else if (var1 == 9) {
         this.traceOut.write(92);
         this.traceOut.write(116);
      } else if (var1 < 32) {
         this.traceOut.write(94);
         this.traceOut.write(var1 + 64);
      } else {
         this.traceOut.write(var1);
      }

   }

   public void setQuote(boolean var1) {
      this.quote = var1;
   }

   public void setTrace(boolean var1) {
      this.trace = var1;
   }

   public void write(int var1) throws IOException {
      if (this.trace) {
         if (this.quote) {
            this.writeByte(var1);
         } else {
            this.traceOut.write(var1);
         }
      }

      this.out.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (this.trace) {
         if (this.quote) {
            for(int var4 = 0; var4 < var3; ++var4) {
               this.writeByte(var1[var2 + var4]);
            }
         } else {
            this.traceOut.write(var1, var2, var3);
         }
      }

      this.out.write(var1, var2, var3);
   }
}
