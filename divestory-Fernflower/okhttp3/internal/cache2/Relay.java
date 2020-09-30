package okhttp3.internal.cache2;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 :2\u00020\u0001:\u0002:;B3\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\u000e\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\tJ\b\u00105\u001a\u0004\u0018\u00010\u0005J \u00106\u001a\u0002032\u0006\u00107\u001a\u00020\t2\u0006\u00104\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0007H\u0002J\u0010\u00109\u001a\u0002032\u0006\u00104\u001a\u00020\u0007H\u0002R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0015R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0011\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101¨\u0006<"},
   d2 = {"Lokhttp3/internal/cache2/Relay;", "", "file", "Ljava/io/RandomAccessFile;", "upstream", "Lokio/Source;", "upstreamPos", "", "metadata", "Lokio/ByteString;", "bufferMaxSize", "(Ljava/io/RandomAccessFile;Lokio/Source;JLokio/ByteString;J)V", "buffer", "Lokio/Buffer;", "getBuffer", "()Lokio/Buffer;", "getBufferMaxSize", "()J", "complete", "", "getComplete", "()Z", "setComplete", "(Z)V", "getFile", "()Ljava/io/RandomAccessFile;", "setFile", "(Ljava/io/RandomAccessFile;)V", "isClosed", "sourceCount", "", "getSourceCount", "()I", "setSourceCount", "(I)V", "getUpstream", "()Lokio/Source;", "setUpstream", "(Lokio/Source;)V", "upstreamBuffer", "getUpstreamBuffer", "getUpstreamPos", "setUpstreamPos", "(J)V", "upstreamReader", "Ljava/lang/Thread;", "getUpstreamReader", "()Ljava/lang/Thread;", "setUpstreamReader", "(Ljava/lang/Thread;)V", "commit", "", "upstreamSize", "newSource", "writeHeader", "prefix", "metadataSize", "writeMetadata", "Companion", "RelaySource", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Relay {
   public static final Relay.Companion Companion = new Relay.Companion((DefaultConstructorMarker)null);
   private static final long FILE_HEADER_SIZE = 32L;
   public static final ByteString PREFIX_CLEAN;
   public static final ByteString PREFIX_DIRTY;
   private static final int SOURCE_FILE = 2;
   private static final int SOURCE_UPSTREAM = 1;
   private final Buffer buffer;
   private final long bufferMaxSize;
   private boolean complete;
   private RandomAccessFile file;
   private final ByteString metadata;
   private int sourceCount;
   private Source upstream;
   private final Buffer upstreamBuffer;
   private long upstreamPos;
   private Thread upstreamReader;

   static {
      PREFIX_CLEAN = ByteString.Companion.encodeUtf8("OkHttp cache v1\n");
      PREFIX_DIRTY = ByteString.Companion.encodeUtf8("OkHttp DIRTY :(\n");
   }

   private Relay(RandomAccessFile var1, Source var2, long var3, ByteString var5, long var6) {
      this.file = var1;
      this.upstream = var2;
      this.upstreamPos = var3;
      this.metadata = var5;
      this.bufferMaxSize = var6;
      this.upstreamBuffer = new Buffer();
      boolean var8;
      if (this.upstream == null) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.complete = var8;
      this.buffer = new Buffer();
   }

   // $FF: synthetic method
   public Relay(RandomAccessFile var1, Source var2, long var3, ByteString var5, long var6, DefaultConstructorMarker var8) {
      this(var1, var2, var3, var5, var6);
   }

   private final void writeHeader(ByteString var1, long var2, long var4) throws IOException {
      Buffer var6 = new Buffer();
      var6.write(var1);
      var6.writeLong(var2);
      var6.writeLong(var4);
      boolean var7;
      if (var6.size() == 32L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         RandomAccessFile var8 = this.file;
         if (var8 == null) {
            Intrinsics.throwNpe();
         }

         FileChannel var9 = var8.getChannel();
         Intrinsics.checkExpressionValueIsNotNull(var9, "file!!.channel");
         (new FileOperator(var9)).write(0L, var6, 32L);
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }

   private final void writeMetadata(long var1) throws IOException {
      Buffer var3 = new Buffer();
      var3.write(this.metadata);
      RandomAccessFile var4 = this.file;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      FileChannel var5 = var4.getChannel();
      Intrinsics.checkExpressionValueIsNotNull(var5, "file!!.channel");
      (new FileOperator(var5)).write(32L + var1, var3, (long)this.metadata.size());
   }

   public final void commit(long var1) throws IOException {
      this.writeMetadata(var1);
      RandomAccessFile var3 = this.file;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      var3.getChannel().force(false);
      this.writeHeader(PREFIX_CLEAN, var1, (long)this.metadata.size());
      var3 = this.file;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      var3.getChannel().force(false);
      synchronized(this){}

      try {
         this.complete = true;
         Unit var6 = Unit.INSTANCE;
      } finally {
         ;
      }

      Source var7 = this.upstream;
      if (var7 != null) {
         Util.closeQuietly((Closeable)var7);
      }

      this.upstream = (Source)null;
   }

   public final Buffer getBuffer() {
      return this.buffer;
   }

   public final long getBufferMaxSize() {
      return this.bufferMaxSize;
   }

   public final boolean getComplete() {
      return this.complete;
   }

   public final RandomAccessFile getFile() {
      return this.file;
   }

   public final int getSourceCount() {
      return this.sourceCount;
   }

   public final Source getUpstream() {
      return this.upstream;
   }

   public final Buffer getUpstreamBuffer() {
      return this.upstreamBuffer;
   }

   public final long getUpstreamPos() {
      return this.upstreamPos;
   }

   public final Thread getUpstreamReader() {
      return this.upstreamReader;
   }

   public final boolean isClosed() {
      boolean var1;
      if (this.file == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final ByteString metadata() {
      return this.metadata;
   }

   public final Source newSource() {
      synchronized(this){}

      Throwable var10000;
      label78: {
         RandomAccessFile var1;
         boolean var10001;
         try {
            var1 = this.file;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label78;
         }

         if (var1 == null) {
            return null;
         }

         try {
            ++this.sourceCount;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label78;
         }

         return (Source)(new Relay.RelaySource());
      }

      Throwable var8 = var10000;
      throw var8;
   }

   public final void setComplete(boolean var1) {
      this.complete = var1;
   }

   public final void setFile(RandomAccessFile var1) {
      this.file = var1;
   }

   public final void setSourceCount(int var1) {
      this.sourceCount = var1;
   }

   public final void setUpstream(Source var1) {
      this.upstream = var1;
   }

   public final void setUpstreamPos(long var1) {
      this.upstreamPos = var1;
   }

   public final void setUpstreamReader(Thread var1) {
      this.upstreamReader = var1;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"},
      d2 = {"Lokhttp3/internal/cache2/Relay$Companion;", "", "()V", "FILE_HEADER_SIZE", "", "PREFIX_CLEAN", "Lokio/ByteString;", "PREFIX_DIRTY", "SOURCE_FILE", "", "SOURCE_UPSTREAM", "edit", "Lokhttp3/internal/cache2/Relay;", "file", "Ljava/io/File;", "upstream", "Lokio/Source;", "metadata", "bufferMaxSize", "read", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Relay edit(File var1, Source var2, ByteString var3, long var4) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "file");
         Intrinsics.checkParameterIsNotNull(var2, "upstream");
         Intrinsics.checkParameterIsNotNull(var3, "metadata");
         RandomAccessFile var6 = new RandomAccessFile(var1, "rw");
         Relay var7 = new Relay(var6, var2, 0L, var3, var4, (DefaultConstructorMarker)null);
         var6.setLength(0L);
         var7.writeHeader(Relay.PREFIX_DIRTY, -1L, -1L);
         return var7;
      }

      public final Relay read(File var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "file");
         RandomAccessFile var8 = new RandomAccessFile(var1, "rw");
         FileChannel var2 = var8.getChannel();
         Intrinsics.checkExpressionValueIsNotNull(var2, "randomAccessFile.channel");
         FileOperator var9 = new FileOperator(var2);
         Buffer var3 = new Buffer();
         var9.read(0L, var3, 32L);
         if (!(Intrinsics.areEqual((Object)var3.readByteString((long)Relay.PREFIX_CLEAN.size()), (Object)Relay.PREFIX_CLEAN) ^ true)) {
            long var4 = var3.readLong();
            long var6 = var3.readLong();
            var3 = new Buffer();
            var9.read(var4 + 32L, var3, var6);
            return new Relay(var8, (Source)null, var4, var3.readByteString(), 0L, (DefaultConstructorMarker)null);
         } else {
            throw (Throwable)(new IOException("unreadable cache file"));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
      d2 = {"Lokhttp3/internal/cache2/Relay$RelaySource;", "Lokio/Source;", "(Lokhttp3/internal/cache2/Relay;)V", "fileOperator", "Lokhttp3/internal/cache2/FileOperator;", "sourcePos", "", "timeout", "Lokio/Timeout;", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class RelaySource implements Source {
      private FileOperator fileOperator;
      private long sourcePos;
      private final Timeout timeout = new Timeout();

      public RelaySource() {
         RandomAccessFile var2 = Relay.this.getFile();
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         FileChannel var3 = var2.getChannel();
         Intrinsics.checkExpressionValueIsNotNull(var3, "file!!.channel");
         this.fileOperator = new FileOperator(var3);
      }

      public void close() throws IOException {
         if (this.fileOperator != null) {
            this.fileOperator = (FileOperator)null;
            RandomAccessFile var1 = (RandomAccessFile)null;
            Relay var2 = Relay.this;
            synchronized(var2){}

            try {
               Relay var3 = Relay.this;
               var3.setSourceCount(var3.getSourceCount() - 1);
               if (Relay.this.getSourceCount() == 0) {
                  var1 = Relay.this.getFile();
                  Relay.this.setFile((RandomAccessFile)null);
               }

               Unit var6 = Unit.INSTANCE;
            } finally {
               ;
            }

            if (var1 != null) {
               Util.closeQuietly((Closeable)var1);
            }

         }
      }

      public long read(Buffer param1, long param2) throws IOException {
         // $FF: Couldn't be decompiled
      }

      public Timeout timeout() {
         return this.timeout;
      }
   }
}
