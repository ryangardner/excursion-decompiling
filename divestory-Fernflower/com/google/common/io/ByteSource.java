package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class ByteSource {
   protected ByteSource() {
   }

   public static ByteSource concat(Iterable<? extends ByteSource> var0) {
      return new ByteSource.ConcatenatedByteSource(var0);
   }

   public static ByteSource concat(Iterator<? extends ByteSource> var0) {
      return concat((Iterable)ImmutableList.copyOf(var0));
   }

   public static ByteSource concat(ByteSource... var0) {
      return concat((Iterable)ImmutableList.copyOf((Object[])var0));
   }

   private long countBySkipping(InputStream var1) throws IOException {
      long var2 = 0L;

      while(true) {
         long var4 = ByteStreams.skipUpTo(var1, 2147483647L);
         if (var4 <= 0L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public static ByteSource empty() {
      return ByteSource.EmptyByteSource.INSTANCE;
   }

   public static ByteSource wrap(byte[] var0) {
      return new ByteSource.ByteArrayByteSource(var0);
   }

   public CharSource asCharSource(Charset var1) {
      return new ByteSource.AsCharSource(var1);
   }

   public boolean contentEquals(ByteSource var1) throws IOException {
      Preconditions.checkNotNull(var1);
      byte[] var2 = ByteStreams.createBuffer();
      byte[] var3 = ByteStreams.createBuffer();
      Closer var4 = Closer.create();

      label200: {
         Throwable var10000;
         label195: {
            InputStream var5;
            boolean var10001;
            InputStream var28;
            try {
               var5 = (InputStream)var4.register(this.openStream());
               var28 = (InputStream)var4.register(var1.openStream());
            } catch (Throwable var27) {
               var10000 = var27;
               var10001 = false;
               break label195;
            }

            while(true) {
               int var6;
               try {
                  var6 = ByteStreams.read(var5, var2, 0, var2.length);
                  if (var6 != ByteStreams.read(var28, var3, 0, var3.length) || !Arrays.equals(var2, var3)) {
                     break label200;
                  }
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break;
               }

               int var7;
               try {
                  var7 = var2.length;
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break;
               }

               if (var6 != var7) {
                  var4.close();
                  return true;
               }
            }
         }

         Throwable var29 = var10000;

         try {
            throw var4.rethrow(var29);
         } finally {
            var4.close();
         }
      }

      var4.close();
      return false;
   }

   public long copyTo(ByteSink var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var3;
      try {
         var3 = ByteStreams.copy((InputStream)var2.register(this.openStream()), (OutputStream)var2.register(var1.openStream()));
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

   public long copyTo(OutputStream var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var3;
      try {
         var3 = ByteStreams.copy((InputStream)var2.register(this.openStream()), var1);
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

   public HashCode hash(HashFunction var1) throws IOException {
      Hasher var2 = var1.newHasher();
      this.copyTo(Funnels.asOutputStream(var2));
      return var2.hash();
   }

   public boolean isEmpty() throws IOException {
      Optional var1 = this.sizeIfKnown();
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
            var5 = ((InputStream)var13.register(this.openStream())).read();
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

   public InputStream openBufferedStream() throws IOException {
      InputStream var1 = this.openStream();
      BufferedInputStream var2;
      if (var1 instanceof BufferedInputStream) {
         var2 = (BufferedInputStream)var1;
      } else {
         var2 = new BufferedInputStream(var1);
      }

      return var2;
   }

   public abstract InputStream openStream() throws IOException;

   public <T> T read(ByteProcessor<T> var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      Object var10;
      try {
         var10 = ByteStreams.readBytes((InputStream)var2.register(this.openStream()), var1);
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

   public byte[] read() throws IOException {
      Closer var1 = Closer.create();

      byte[] var11;
      try {
         InputStream var10 = (InputStream)var1.register(this.openStream());
         Optional var3 = this.sizeIfKnown();
         if (var3.isPresent()) {
            var11 = ByteStreams.toByteArray(var10, (Long)var3.get());
         } else {
            var11 = ByteStreams.toByteArray(var10);
         }
      } catch (Throwable var9) {
         Throwable var2 = var9;

         try {
            throw var1.rethrow(var2);
         } finally {
            var1.close();
         }
      }

      var1.close();
      return var11;
   }

   public long size() throws IOException {
      Optional var1 = this.sizeIfKnown();
      if (var1.isPresent()) {
         return (Long)var1.get();
      } else {
         Closer var21 = Closer.create();

         long var2;
         try {
            var2 = this.countBySkipping((InputStream)var21.register(this.openStream()));
            return var2;
         } catch (IOException var19) {
         } finally {
            var21.close();
         }

         var21 = Closer.create();

         try {
            var2 = ByteStreams.exhaust((InputStream)var21.register(this.openStream()));
         } catch (Throwable var18) {
            Throwable var4 = var18;

            try {
               throw var21.rethrow(var4);
            } finally {
               var21.close();
            }
         }

         var21.close();
         return var2;
      }
   }

   public Optional<Long> sizeIfKnown() {
      return Optional.absent();
   }

   public ByteSource slice(long var1, long var3) {
      return new ByteSource.SlicedByteSource(var1, var3);
   }

   class AsCharSource extends CharSource {
      final Charset charset;

      AsCharSource(Charset var2) {
         this.charset = (Charset)Preconditions.checkNotNull(var2);
      }

      public ByteSource asByteSource(Charset var1) {
         return var1.equals(this.charset) ? ByteSource.this : super.asByteSource(var1);
      }

      public Reader openStream() throws IOException {
         return new InputStreamReader(ByteSource.this.openStream(), this.charset);
      }

      public String read() throws IOException {
         return new String(ByteSource.this.read(), this.charset);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(ByteSource.this.toString());
         var1.append(".asCharSource(");
         var1.append(this.charset);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class ByteArrayByteSource extends ByteSource {
      final byte[] bytes;
      final int length;
      final int offset;

      ByteArrayByteSource(byte[] var1) {
         this(var1, 0, var1.length);
      }

      ByteArrayByteSource(byte[] var1, int var2, int var3) {
         this.bytes = var1;
         this.offset = var2;
         this.length = var3;
      }

      public long copyTo(OutputStream var1) throws IOException {
         var1.write(this.bytes, this.offset, this.length);
         return (long)this.length;
      }

      public HashCode hash(HashFunction var1) throws IOException {
         return var1.hashBytes(this.bytes, this.offset, this.length);
      }

      public boolean isEmpty() {
         boolean var1;
         if (this.length == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public InputStream openBufferedStream() throws IOException {
         return this.openStream();
      }

      public InputStream openStream() {
         return new ByteArrayInputStream(this.bytes, this.offset, this.length);
      }

      public <T> T read(ByteProcessor<T> var1) throws IOException {
         var1.processBytes(this.bytes, this.offset, this.length);
         return var1.getResult();
      }

      public byte[] read() {
         byte[] var1 = this.bytes;
         int var2 = this.offset;
         return Arrays.copyOfRange(var1, var2, this.length + var2);
      }

      public long size() {
         return (long)this.length;
      }

      public Optional<Long> sizeIfKnown() {
         return Optional.of((long)this.length);
      }

      public ByteSource slice(long var1, long var3) {
         boolean var5 = true;
         boolean var6;
         if (var1 >= 0L) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "offset (%s) may not be negative", var1);
         if (var3 >= 0L) {
            var6 = var5;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "length (%s) may not be negative", var3);
         var1 = Math.min(var1, (long)this.length);
         var3 = Math.min(var3, (long)this.length - var1);
         int var7 = this.offset;
         int var8 = (int)var1;
         return new ByteSource.ByteArrayByteSource(this.bytes, var7 + var8, (int)var3);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("ByteSource.wrap(");
         var1.append(Ascii.truncate(BaseEncoding.base16().encode(this.bytes, this.offset, this.length), 30, "..."));
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class ConcatenatedByteSource extends ByteSource {
      final Iterable<? extends ByteSource> sources;

      ConcatenatedByteSource(Iterable<? extends ByteSource> var1) {
         this.sources = (Iterable)Preconditions.checkNotNull(var1);
      }

      public boolean isEmpty() throws IOException {
         Iterator var1 = this.sources.iterator();

         do {
            if (!var1.hasNext()) {
               return true;
            }
         } while(((ByteSource)var1.next()).isEmpty());

         return false;
      }

      public InputStream openStream() throws IOException {
         return new MultiInputStream(this.sources.iterator());
      }

      public long size() throws IOException {
         Iterator var1 = this.sources.iterator();
         long var2 = 0L;

         long var4;
         do {
            if (!var1.hasNext()) {
               return var2;
            }

            var4 = var2 + ((ByteSource)var1.next()).size();
            var2 = var4;
         } while(var4 >= 0L);

         return Long.MAX_VALUE;
      }

      public Optional<Long> sizeIfKnown() {
         Iterable var1 = this.sources;
         if (!(var1 instanceof Collection)) {
            return Optional.absent();
         } else {
            Iterator var2 = var1.iterator();
            long var3 = 0L;

            long var5;
            do {
               if (!var2.hasNext()) {
                  return Optional.of(var3);
               }

               Optional var7 = ((ByteSource)var2.next()).sizeIfKnown();
               if (!var7.isPresent()) {
                  return Optional.absent();
               }

               var5 = var3 + (Long)var7.get();
               var3 = var5;
            } while(var5 >= 0L);

            return Optional.of(Long.MAX_VALUE);
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("ByteSource.concat(");
         var1.append(this.sources);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class EmptyByteSource extends ByteSource.ByteArrayByteSource {
      static final ByteSource.EmptyByteSource INSTANCE = new ByteSource.EmptyByteSource();

      EmptyByteSource() {
         super(new byte[0]);
      }

      public CharSource asCharSource(Charset var1) {
         Preconditions.checkNotNull(var1);
         return CharSource.empty();
      }

      public byte[] read() {
         return this.bytes;
      }

      public String toString() {
         return "ByteSource.empty()";
      }
   }

   private final class SlicedByteSource extends ByteSource {
      final long length;
      final long offset;

      SlicedByteSource(long var2, long var4) {
         boolean var6 = true;
         boolean var7;
         if (var2 >= 0L) {
            var7 = true;
         } else {
            var7 = false;
         }

         Preconditions.checkArgument(var7, "offset (%s) may not be negative", var2);
         if (var4 >= 0L) {
            var7 = var6;
         } else {
            var7 = false;
         }

         Preconditions.checkArgument(var7, "length (%s) may not be negative", var4);
         this.offset = var2;
         this.length = var4;
      }

      private InputStream sliceStream(InputStream var1) throws IOException {
         long var2 = this.offset;
         if (var2 > 0L) {
            try {
               var2 = ByteStreams.skipUpTo(var1, var2);
            } catch (Throwable var11) {
               Throwable var4 = var11;
               Closer var5 = Closer.create();
               var5.register(var1);

               try {
                  throw var5.rethrow(var4);
               } finally {
                  var5.close();
               }
            }

            if (var2 < this.offset) {
               var1.close();
               return new ByteArrayInputStream(new byte[0]);
            }
         }

         return ByteStreams.limit(var1, this.length);
      }

      public boolean isEmpty() throws IOException {
         boolean var1;
         if (this.length != 0L && !super.isEmpty()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public InputStream openBufferedStream() throws IOException {
         return this.sliceStream(ByteSource.this.openBufferedStream());
      }

      public InputStream openStream() throws IOException {
         return this.sliceStream(ByteSource.this.openStream());
      }

      public Optional<Long> sizeIfKnown() {
         Optional var1 = ByteSource.this.sizeIfKnown();
         if (var1.isPresent()) {
            long var2 = (Long)var1.get();
            long var4 = Math.min(this.offset, var2);
            return Optional.of(Math.min(this.length, var2 - var4));
         } else {
            return Optional.absent();
         }
      }

      public ByteSource slice(long var1, long var3) {
         boolean var5 = true;
         boolean var6;
         if (var1 >= 0L) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "offset (%s) may not be negative", var1);
         if (var3 >= 0L) {
            var6 = var5;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "length (%s) may not be negative", var3);
         long var7 = this.length;
         return ByteSource.this.slice(this.offset + var1, Math.min(var3, var7 - var1));
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(ByteSource.this.toString());
         var1.append(".slice(");
         var1.append(this.offset);
         var1.append(", ");
         var1.append(this.length);
         var1.append(")");
         return var1.toString();
      }
   }
}
