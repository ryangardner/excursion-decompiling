package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class CharSource {
   protected CharSource() {
   }

   public static CharSource concat(Iterable<? extends CharSource> var0) {
      return new CharSource.ConcatenatedCharSource(var0);
   }

   public static CharSource concat(Iterator<? extends CharSource> var0) {
      return concat((Iterable)ImmutableList.copyOf(var0));
   }

   public static CharSource concat(CharSource... var0) {
      return concat((Iterable)ImmutableList.copyOf((Object[])var0));
   }

   private long countBySkipping(Reader var1) throws IOException {
      long var2 = 0L;

      while(true) {
         long var4 = var1.skip(Long.MAX_VALUE);
         if (var4 == 0L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public static CharSource empty() {
      return CharSource.EmptyCharSource.INSTANCE;
   }

   public static CharSource wrap(CharSequence var0) {
      Object var1;
      if (var0 instanceof String) {
         var1 = new CharSource.StringCharSource((String)var0);
      } else {
         var1 = new CharSource.CharSequenceCharSource(var0);
      }

      return (CharSource)var1;
   }

   public ByteSource asByteSource(Charset var1) {
      return new CharSource.AsByteSource(var1);
   }

   public long copyTo(CharSink var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var3;
      try {
         var3 = CharStreams.copy((Reader)var2.register(this.openStream()), (Writer)var2.register(var1.openStream()));
      } catch (Throwable var10) {
         Throwable var11 = var10;

         try {
            throw var2.rethrow(var11);
         } finally {
            var2.close();
         }
      }

      var2.close();
      return var3;
   }

   public long copyTo(Appendable var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var3;
      try {
         var3 = CharStreams.copy((Reader)var2.register(this.openStream()), var1);
      } catch (Throwable var10) {
         Throwable var11 = var10;

         try {
            throw var2.rethrow(var11);
         } finally {
            var2.close();
         }
      }

      var2.close();
      return var3;
   }

   public boolean isEmpty() throws IOException {
      Optional var1 = this.lengthIfKnown();
      boolean var2 = var1.isPresent();
      boolean var3 = true;
      boolean var4 = true;
      if (var2) {
         if ((Long)var1.get() != 0L) {
            var4 = false;
         }

         return var4;
      } else {
         Closer var13 = Closer.create();

         int var5;
         try {
            var5 = ((Reader)var13.register(this.openStream())).read();
         } catch (Throwable var12) {
            Throwable var6 = var12;

            try {
               throw var13.rethrow(var6);
            } finally {
               var13.close();
            }
         }

         if (var5 == -1) {
            var4 = var3;
         } else {
            var4 = false;
         }

         var13.close();
         return var4;
      }
   }

   public long length() throws IOException {
      Optional var1 = this.lengthIfKnown();
      if (var1.isPresent()) {
         return (Long)var1.get();
      } else {
         Closer var11 = Closer.create();

         long var2;
         try {
            var2 = this.countBySkipping((Reader)var11.register(this.openStream()));
         } catch (Throwable var10) {
            Throwable var4 = var10;

            try {
               throw var11.rethrow(var4);
            } finally {
               var11.close();
            }
         }

         var11.close();
         return var2;
      }
   }

   public Optional<Long> lengthIfKnown() {
      return Optional.absent();
   }

   public BufferedReader openBufferedStream() throws IOException {
      Reader var1 = this.openStream();
      BufferedReader var2;
      if (var1 instanceof BufferedReader) {
         var2 = (BufferedReader)var1;
      } else {
         var2 = new BufferedReader(var1);
      }

      return var2;
   }

   public abstract Reader openStream() throws IOException;

   public String read() throws IOException {
      Closer var1 = Closer.create();

      String var9;
      try {
         var9 = CharStreams.toString((Reader)var1.register(this.openStream()));
      } catch (Throwable var8) {
         Throwable var2 = var8;

         try {
            throw var1.rethrow(var2);
         } finally {
            var1.close();
         }
      }

      var1.close();
      return var9;
   }

   @NullableDecl
   public String readFirstLine() throws IOException {
      Closer var1 = Closer.create();

      String var9;
      try {
         var9 = ((BufferedReader)var1.register(this.openBufferedStream())).readLine();
      } catch (Throwable var8) {
         Throwable var2 = var8;

         try {
            throw var1.rethrow(var2);
         } finally {
            var1.close();
         }
      }

      var1.close();
      return var9;
   }

   public ImmutableList<String> readLines() throws IOException {
      Closer var1 = Closer.create();

      ImmutableList var36;
      label229: {
         Throwable var10000;
         label224: {
            BufferedReader var2;
            ArrayList var3;
            boolean var10001;
            try {
               var2 = (BufferedReader)var1.register(this.openBufferedStream());
               var3 = Lists.newArrayList();
            } catch (Throwable var34) {
               var10000 = var34;
               var10001 = false;
               break label224;
            }

            while(true) {
               String var4;
               try {
                  var4 = var2.readLine();
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break;
               }

               if (var4 == null) {
                  try {
                     var36 = ImmutableList.copyOf((Collection)var3);
                     break label229;
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var3.add(var4);
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var35 = var10000;

         try {
            throw var1.rethrow(var35);
         } finally {
            var1.close();
         }
      }

      var1.close();
      return var36;
   }

   public <T> T readLines(LineProcessor<T> var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      Object var10;
      try {
         var10 = CharStreams.readLines((Reader)var2.register(this.openStream()), var1);
      } catch (Throwable var8) {
         Throwable var9 = var8;

         try {
            throw var2.rethrow(var9);
         } finally {
            var2.close();
         }
      }

      var2.close();
      return var10;
   }

   private final class AsByteSource extends ByteSource {
      final Charset charset;

      AsByteSource(Charset var2) {
         this.charset = (Charset)Preconditions.checkNotNull(var2);
      }

      public CharSource asCharSource(Charset var1) {
         return var1.equals(this.charset) ? CharSource.this : super.asCharSource(var1);
      }

      public InputStream openStream() throws IOException {
         return new ReaderInputStream(CharSource.this.openStream(), this.charset, 8192);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(CharSource.this.toString());
         var1.append(".asByteSource(");
         var1.append(this.charset);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class CharSequenceCharSource extends CharSource {
      private static final Splitter LINE_SPLITTER = Splitter.onPattern("\r\n|\n|\r");
      protected final CharSequence seq;

      protected CharSequenceCharSource(CharSequence var1) {
         this.seq = (CharSequence)Preconditions.checkNotNull(var1);
      }

      private Iterator<String> linesIterator() {
         return new AbstractIterator<String>() {
            Iterator<String> lines;

            {
               this.lines = CharSource.CharSequenceCharSource.LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();
            }

            protected String computeNext() {
               if (this.lines.hasNext()) {
                  String var1 = (String)this.lines.next();
                  if (this.lines.hasNext() || !var1.isEmpty()) {
                     return var1;
                  }
               }

               return (String)this.endOfData();
            }
         };
      }

      public boolean isEmpty() {
         boolean var1;
         if (this.seq.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public long length() {
         return (long)this.seq.length();
      }

      public Optional<Long> lengthIfKnown() {
         return Optional.of((long)this.seq.length());
      }

      public Reader openStream() {
         return new CharSequenceReader(this.seq);
      }

      public String read() {
         return this.seq.toString();
      }

      public String readFirstLine() {
         Iterator var1 = this.linesIterator();
         String var2;
         if (var1.hasNext()) {
            var2 = (String)var1.next();
         } else {
            var2 = null;
         }

         return var2;
      }

      public ImmutableList<String> readLines() {
         return ImmutableList.copyOf(this.linesIterator());
      }

      public <T> T readLines(LineProcessor<T> var1) throws IOException {
         Iterator var2 = this.linesIterator();

         while(var2.hasNext() && var1.processLine((String)var2.next())) {
         }

         return var1.getResult();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharSource.wrap(");
         var1.append(Ascii.truncate(this.seq, 30, "..."));
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class ConcatenatedCharSource extends CharSource {
      private final Iterable<? extends CharSource> sources;

      ConcatenatedCharSource(Iterable<? extends CharSource> var1) {
         this.sources = (Iterable)Preconditions.checkNotNull(var1);
      }

      public boolean isEmpty() throws IOException {
         Iterator var1 = this.sources.iterator();

         do {
            if (!var1.hasNext()) {
               return true;
            }
         } while(((CharSource)var1.next()).isEmpty());

         return false;
      }

      public long length() throws IOException {
         Iterator var1 = this.sources.iterator();

         long var2;
         for(var2 = 0L; var1.hasNext(); var2 += ((CharSource)var1.next()).length()) {
         }

         return var2;
      }

      public Optional<Long> lengthIfKnown() {
         Iterator var1 = this.sources.iterator();

         long var2;
         Optional var4;
         for(var2 = 0L; var1.hasNext(); var2 += (Long)var4.get()) {
            var4 = ((CharSource)var1.next()).lengthIfKnown();
            if (!var4.isPresent()) {
               return Optional.absent();
            }
         }

         return Optional.of(var2);
      }

      public Reader openStream() throws IOException {
         return new MultiReader(this.sources.iterator());
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CharSource.concat(");
         var1.append(this.sources);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class EmptyCharSource extends CharSource.StringCharSource {
      private static final CharSource.EmptyCharSource INSTANCE = new CharSource.EmptyCharSource();

      private EmptyCharSource() {
         super("");
      }

      public String toString() {
         return "CharSource.empty()";
      }
   }

   private static class StringCharSource extends CharSource.CharSequenceCharSource {
      protected StringCharSource(String var1) {
         super(var1);
      }

      public long copyTo(CharSink var1) throws IOException {
         Preconditions.checkNotNull(var1);
         Closer var2 = Closer.create();

         int var3;
         try {
            ((Writer)var2.register(var1.openStream())).write((String)this.seq);
            var3 = this.seq.length();
         } catch (Throwable var11) {
            Throwable var12 = var11;

            try {
               throw var2.rethrow(var12);
            } finally {
               var2.close();
            }
         }

         long var4 = (long)var3;
         var2.close();
         return var4;
      }

      public long copyTo(Appendable var1) throws IOException {
         var1.append(this.seq);
         return (long)this.seq.length();
      }

      public Reader openStream() {
         return new StringReader((String)this.seq);
      }
   }
}
