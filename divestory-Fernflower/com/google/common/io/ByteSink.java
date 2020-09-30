package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class ByteSink {
   protected ByteSink() {
   }

   public CharSink asCharSink(Charset var1) {
      return new ByteSink.AsCharSink(var1);
   }

   public OutputStream openBufferedStream() throws IOException {
      OutputStream var1 = this.openStream();
      BufferedOutputStream var2;
      if (var1 instanceof BufferedOutputStream) {
         var2 = (BufferedOutputStream)var1;
      } else {
         var2 = new BufferedOutputStream(var1);
      }

      return var2;
   }

   public abstract OutputStream openStream() throws IOException;

   public void write(byte[] var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      try {
         OutputStream var3 = (OutputStream)var2.register(this.openStream());
         var3.write(var1);
         var3.flush();
      } catch (Throwable var9) {
         Throwable var10 = var9;

         try {
            throw var2.rethrow(var10);
         } finally {
            var2.close();
         }
      }

      var2.close();
   }

   public long writeFrom(InputStream var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var4;
      try {
         OutputStream var3 = (OutputStream)var2.register(this.openStream());
         var4 = ByteStreams.copy(var1, var3);
         var3.flush();
      } catch (Throwable var11) {
         Throwable var12 = var11;

         try {
            throw var2.rethrow(var12);
         } finally {
            var2.close();
         }
      }

      var2.close();
      return var4;
   }

   private final class AsCharSink extends CharSink {
      private final Charset charset;

      private AsCharSink(Charset var2) {
         this.charset = (Charset)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      AsCharSink(Charset var2, Object var3) {
         this(var2);
      }

      public Writer openStream() throws IOException {
         return new OutputStreamWriter(ByteSink.this.openStream(), this.charset);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(ByteSink.this.toString());
         var1.append(".asCharSink(");
         var1.append(this.charset);
         var1.append(")");
         return var1.toString();
      }
   }
}
