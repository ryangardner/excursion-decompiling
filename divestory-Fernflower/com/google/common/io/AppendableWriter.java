package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class AppendableWriter extends Writer {
   private boolean closed;
   private final Appendable target;

   AppendableWriter(Appendable var1) {
      this.target = (Appendable)Preconditions.checkNotNull(var1);
   }

   private void checkNotClosed() throws IOException {
      if (this.closed) {
         throw new IOException("Cannot write to a closed writer.");
      }
   }

   public Writer append(char var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
      return this;
   }

   public Writer append(@NullableDecl CharSequence var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
      return this;
   }

   public Writer append(@NullableDecl CharSequence var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      this.target.append(var1, var2, var3);
      return this;
   }

   public void close() throws IOException {
      this.closed = true;
      Appendable var1 = this.target;
      if (var1 instanceof Closeable) {
         ((Closeable)var1).close();
      }

   }

   public void flush() throws IOException {
      this.checkNotClosed();
      Appendable var1 = this.target;
      if (var1 instanceof Flushable) {
         ((Flushable)var1).flush();
      }

   }

   public void write(int var1) throws IOException {
      this.checkNotClosed();
      this.target.append((char)var1);
   }

   public void write(@NullableDecl String var1) throws IOException {
      this.checkNotClosed();
      this.target.append(var1);
   }

   public void write(@NullableDecl String var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      this.target.append(var1, var2, var3 + var2);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.checkNotClosed();
      this.target.append(new String(var1, var2, var3));
   }
}
