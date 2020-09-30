package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class CharStreams {
   private static final int DEFAULT_BUF_SIZE = 2048;

   private CharStreams() {
   }

   public static Writer asWriter(Appendable var0) {
      return (Writer)(var0 instanceof Writer ? (Writer)var0 : new AppendableWriter(var0));
   }

   public static long copy(Readable var0, Appendable var1) throws IOException {
      if (var0 instanceof Reader) {
         return var1 instanceof StringBuilder ? copyReaderToBuilder((Reader)var0, (StringBuilder)var1) : copyReaderToWriter((Reader)var0, asWriter(var1));
      } else {
         Preconditions.checkNotNull(var0);
         Preconditions.checkNotNull(var1);
         long var2 = 0L;
         CharBuffer var4 = createBuffer();

         while(var0.read(var4) != -1) {
            var4.flip();
            var1.append(var4);
            var2 += (long)var4.remaining();
            var4.clear();
         }

         return var2;
      }
   }

   static long copyReaderToBuilder(Reader var0, StringBuilder var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      char[] var2 = new char[2048];
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if (var5 == -1) {
            return var3;
         }

         var1.append(var2, 0, var5);
         var3 += (long)var5;
      }
   }

   static long copyReaderToWriter(Reader var0, Writer var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      char[] var2 = new char[2048];
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if (var5 == -1) {
            return var3;
         }

         var1.write(var2, 0, var5);
         var3 += (long)var5;
      }
   }

   static CharBuffer createBuffer() {
      return CharBuffer.allocate(2048);
   }

   public static long exhaust(Readable var0) throws IOException {
      CharBuffer var1 = createBuffer();
      long var2 = 0L;

      while(true) {
         long var4 = (long)var0.read(var1);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
         var1.clear();
      }
   }

   public static Writer nullWriter() {
      return CharStreams.NullWriter.INSTANCE;
   }

   public static <T> T readLines(Readable var0, LineProcessor<T> var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      LineReader var3 = new LineReader(var0);

      String var2;
      do {
         var2 = var3.readLine();
      } while(var2 != null && var1.processLine(var2));

      return var1.getResult();
   }

   public static List<String> readLines(Readable var0) throws IOException {
      ArrayList var1 = new ArrayList();
      LineReader var2 = new LineReader(var0);

      while(true) {
         String var3 = var2.readLine();
         if (var3 == null) {
            return var1;
         }

         var1.add(var3);
      }
   }

   public static void skipFully(Reader var0, long var1) throws IOException {
      Preconditions.checkNotNull(var0);

      while(var1 > 0L) {
         long var3 = var0.skip(var1);
         if (var3 == 0L) {
            throw new EOFException();
         }

         var1 -= var3;
      }

   }

   public static String toString(Readable var0) throws IOException {
      return toStringBuilder(var0).toString();
   }

   private static StringBuilder toStringBuilder(Readable var0) throws IOException {
      StringBuilder var1 = new StringBuilder();
      if (var0 instanceof Reader) {
         copyReaderToBuilder((Reader)var0, var1);
      } else {
         copy(var0, var1);
      }

      return var1;
   }

   private static final class NullWriter extends Writer {
      private static final CharStreams.NullWriter INSTANCE = new CharStreams.NullWriter();

      public Writer append(char var1) {
         return this;
      }

      public Writer append(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         return this;
      }

      public Writer append(CharSequence var1, int var2, int var3) {
         Preconditions.checkPositionIndexes(var2, var3, var1.length());
         return this;
      }

      public void close() {
      }

      public void flush() {
      }

      public String toString() {
         return "CharStreams.nullWriter()";
      }

      public void write(int var1) {
      }

      public void write(String var1) {
         Preconditions.checkNotNull(var1);
      }

      public void write(String var1, int var2, int var3) {
         Preconditions.checkPositionIndexes(var2, var3 + var2, var1.length());
      }

      public void write(char[] var1) {
         Preconditions.checkNotNull(var1);
      }

      public void write(char[] var1, int var2, int var3) {
         Preconditions.checkPositionIndexes(var2, var3 + var2, var1.length);
      }
   }
}
