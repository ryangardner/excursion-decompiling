package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010)\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 C2\u00020\u00012\u00020\u0002:\u0004BCDEB\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0018\u00010\"R\u00020\fH\u0002J\b\u0010#\u001a\u00020 H\u0016J\u0006\u0010$\u001a\u00020 J\r\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\b%J\u0006\u0010&\u001a\u00020 J\b\u0010'\u001a\u00020 H\u0016J\u0017\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b,J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010-\u001a\u00020 J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0015\u001a\u00020\u0011J\u0017\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020)H\u0000¢\u0006\u0002\b1J\u0015\u00102\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b3J\u0006\u0010\u0016\u001a\u00020\u0011J\u0006\u00104\u001a\u00020\u0006J\r\u00105\u001a\u00020 H\u0000¢\u0006\u0002\b6J\u0015\u00107\u001a\u00020 2\u0006\u00108\u001a\u000209H\u0000¢\u0006\u0002\b:J\u001d\u0010;\u001a\u00020 2\u0006\u0010<\u001a\u00020)2\u0006\u0010=\u001a\u00020)H\u0000¢\u0006\u0002\b>J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@J\u0006\u0010\u0017\u001a\u00020\u0011J\u0006\u0010\u001c\u001a\u00020\u0011R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001b¨\u0006F"},
   d2 = {"Lokhttp3/Cache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "directory", "Ljava/io/File;", "maxSize", "", "(Ljava/io/File;J)V", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "(Ljava/io/File;JLokhttp3/internal/io/FileSystem;)V", "cache", "Lokhttp3/internal/cache/DiskLruCache;", "getCache$okhttp", "()Lokhttp3/internal/cache/DiskLruCache;", "()Ljava/io/File;", "hitCount", "", "isClosed", "", "()Z", "networkCount", "requestCount", "writeAbortCount", "getWriteAbortCount$okhttp", "()I", "setWriteAbortCount$okhttp", "(I)V", "writeSuccessCount", "getWriteSuccessCount$okhttp", "setWriteSuccessCount$okhttp", "abortQuietly", "", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "close", "delete", "-deprecated_directory", "evictAll", "flush", "get", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "get$okhttp", "initialize", "put", "Lokhttp3/internal/cache/CacheRequest;", "response", "put$okhttp", "remove", "remove$okhttp", "size", "trackConditionalCacheHit", "trackConditionalCacheHit$okhttp", "trackResponse", "cacheStrategy", "Lokhttp3/internal/cache/CacheStrategy;", "trackResponse$okhttp", "update", "cached", "network", "update$okhttp", "urls", "", "", "CacheResponseBody", "Companion", "Entry", "RealCacheRequest", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Cache implements Closeable, Flushable {
   public static final Cache.Companion Companion = new Cache.Companion((DefaultConstructorMarker)null);
   private static final int ENTRY_BODY = 1;
   private static final int ENTRY_COUNT = 2;
   private static final int ENTRY_METADATA = 0;
   private static final int VERSION = 201105;
   private final DiskLruCache cache;
   private int hitCount;
   private int networkCount;
   private int requestCount;
   private int writeAbortCount;
   private int writeSuccessCount;

   public Cache(File var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "directory");
      this(var1, var2, FileSystem.SYSTEM);
   }

   public Cache(File var1, long var2, FileSystem var4) {
      Intrinsics.checkParameterIsNotNull(var1, "directory");
      Intrinsics.checkParameterIsNotNull(var4, "fileSystem");
      super();
      this.cache = new DiskLruCache(var4, var1, 201105, 2, var2, TaskRunner.INSTANCE);
   }

   private final void abortQuietly(DiskLruCache.Editor var1) {
      if (var1 != null) {
         try {
            var1.abort();
         } catch (IOException var2) {
         }
      }

   }

   @JvmStatic
   public static final String key(HttpUrl var0) {
      return Companion.key(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "directory",
   imports = {}
)
   )
   public final File _deprecated_directory/* $FF was: -deprecated_directory*/() {
      return this.cache.getDirectory();
   }

   public void close() throws IOException {
      this.cache.close();
   }

   public final void delete() throws IOException {
      this.cache.delete();
   }

   public final File directory() {
      return this.cache.getDirectory();
   }

   public final void evictAll() throws IOException {
      this.cache.evictAll();
   }

   public void flush() throws IOException {
      this.cache.flush();
   }

   public final Response get$okhttp(Request var1) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      String var2 = Companion.key(var1.url());

      DiskLruCache.Snapshot var3;
      try {
         var3 = this.cache.get(var2);
      } catch (IOException var5) {
         return null;
      }

      if (var3 != null) {
         Cache.Entry var7;
         try {
            var7 = new Cache.Entry(var3.getSource(0));
         } catch (IOException var4) {
            Util.closeQuietly((Closeable)var3);
            return null;
         }

         Response var8 = var7.response(var3);
         if (!var7.matches(var1, var8)) {
            ResponseBody var6 = var8.body();
            if (var6 != null) {
               Util.closeQuietly((Closeable)var6);
            }

            return null;
         } else {
            return var8;
         }
      } else {
         return null;
      }
   }

   public final DiskLruCache getCache$okhttp() {
      return this.cache;
   }

   public final int getWriteAbortCount$okhttp() {
      return this.writeAbortCount;
   }

   public final int getWriteSuccessCount$okhttp() {
      return this.writeSuccessCount;
   }

   public final int hitCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.hitCount;
      } finally {
         ;
      }

      return var1;
   }

   public final void initialize() throws IOException {
      this.cache.initialize();
   }

   public final boolean isClosed() {
      return this.cache.isClosed();
   }

   public final long maxSize() {
      return this.cache.getMaxSize();
   }

   public final int networkCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.networkCount;
      } finally {
         ;
      }

      return var1;
   }

   public final CacheRequest put$okhttp(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      String var2 = var1.request().method();
      if (HttpMethod.INSTANCE.invalidatesCache(var1.request().method())) {
         try {
            this.remove$okhttp(var1.request());
         } catch (IOException var4) {
         }

         return null;
      } else if (Intrinsics.areEqual((Object)var2, (Object)"GET") ^ true) {
         return null;
      } else if (Companion.hasVaryAll(var1)) {
         return null;
      } else {
         Cache.Entry var3 = new Cache.Entry(var1);
         DiskLruCache.Editor var12 = (DiskLruCache.Editor)null;

         label70: {
            boolean var10001;
            DiskLruCache.Editor var10;
            try {
               var10 = DiskLruCache.edit$default(this.cache, Companion.key(var1.request().url()), 0L, 2, (Object)null);
            } catch (IOException var9) {
               var10001 = false;
               break label70;
            }

            if (var10 == null) {
               return null;
            }

            var12 = var10;

            try {
               var3.writeTo(var10);
            } catch (IOException var8) {
               var10001 = false;
               break label70;
            }

            var12 = var10;

            Cache.RealCacheRequest var13;
            try {
               var13 = new Cache.RealCacheRequest;
            } catch (IOException var7) {
               var10001 = false;
               break label70;
            }

            var12 = var10;

            try {
               var13.<init>(var10);
            } catch (IOException var6) {
               var10001 = false;
               break label70;
            }

            var12 = var10;

            try {
               CacheRequest var11 = (CacheRequest)var13;
               return var11;
            } catch (IOException var5) {
               var10001 = false;
            }
         }

         this.abortQuietly(var12);
         return null;
      }
   }

   public final void remove$okhttp(Request var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      this.cache.remove(Companion.key(var1.url()));
   }

   public final int requestCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.requestCount;
      } finally {
         ;
      }

      return var1;
   }

   public final void setWriteAbortCount$okhttp(int var1) {
      this.writeAbortCount = var1;
   }

   public final void setWriteSuccessCount$okhttp(int var1) {
      this.writeSuccessCount = var1;
   }

   public final long size() throws IOException {
      return this.cache.size();
   }

   public final void trackConditionalCacheHit$okhttp() {
      synchronized(this){}

      try {
         ++this.hitCount;
      } finally {
         ;
      }

   }

   public final void trackResponse$okhttp(CacheStrategy var1) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "cacheStrategy");
         ++this.requestCount;
         if (var1.getNetworkRequest() != null) {
            ++this.networkCount;
         } else if (var1.getCacheResponse() != null) {
            ++this.hitCount;
         }
      } finally {
         ;
      }

   }

   public final void update$okhttp(Response var1, Response var2) {
      Intrinsics.checkParameterIsNotNull(var1, "cached");
      Intrinsics.checkParameterIsNotNull(var2, "network");
      Cache.Entry var3 = new Cache.Entry(var2);
      ResponseBody var7 = var1.body();
      if (var7 != null) {
         DiskLruCache.Snapshot var9 = ((Cache.CacheResponseBody)var7).getSnapshot$okhttp();
         DiskLruCache.Editor var8 = (DiskLruCache.Editor)null;

         label46: {
            boolean var10001;
            DiskLruCache.Editor var10;
            try {
               var10 = var9.edit();
            } catch (IOException var6) {
               var10001 = false;
               break label46;
            }

            if (var10 == null) {
               return;
            }

            var8 = var10;

            try {
               var3.writeTo(var10);
            } catch (IOException var5) {
               var10001 = false;
               break label46;
            }

            var8 = var10;

            try {
               var10.commit();
               return;
            } catch (IOException var4) {
               var10001 = false;
            }
         }

         this.abortQuietly(var8);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type okhttp3.Cache.CacheResponseBody");
      }
   }

   public final Iterator<String> urls() throws IOException {
      return (Iterator)(new Iterator<String>() {
         private boolean canRemove;
         private final Iterator<DiskLruCache.Snapshot> delegate = Cache.this.getCache$okhttp().snapshots();
         private String nextUrl;

         public boolean hasNext() {
            if (this.nextUrl != null) {
               return true;
            } else {
               this.canRemove = false;

               while(this.delegate.hasNext()) {
                  Closeable var1;
                  boolean var10001;
                  Throwable var2;
                  try {
                     var1 = (Closeable)this.delegate.next();
                     var2 = (Throwable)null;
                  } catch (IOException var18) {
                     var10001 = false;
                     continue;
                  }

                  try {
                     this.nextUrl = Okio.buffer(((DiskLruCache.Snapshot)var1).getSource(0)).readUtf8LineStrict();
                  } catch (Throwable var17) {
                     Throwable var3 = var17;

                     try {
                        throw var3;
                     } finally {
                        try {
                           CloseableKt.closeFinally(var1, var3);
                        } catch (IOException var14) {
                           var10001 = false;
                           continue;
                        }
                     }
                  }

                  try {
                     CloseableKt.closeFinally(var1, var2);
                     return true;
                  } catch (IOException var16) {
                     var10001 = false;
                  }
               }

               return false;
            }
         }

         public String next() {
            if (this.hasNext()) {
               String var1 = this.nextUrl;
               if (var1 == null) {
                  Intrinsics.throwNpe();
               }

               this.nextUrl = (String)null;
               this.canRemove = true;
               return var1;
            } else {
               throw (Throwable)(new NoSuchElementException());
            }
         }

         public void remove() {
            if (this.canRemove) {
               this.delegate.remove();
            } else {
               throw (Throwable)(new IllegalStateException("remove() before next()".toString()));
            }
         }
      });
   }

   public final int writeAbortCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.writeAbortCount;
      } finally {
         ;
      }

      return var1;
   }

   public final int writeSuccessCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.writeSuccessCount;
      } finally {
         ;
      }

      return var1;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B'\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u0007\u001a\u00020\rH\u0016J\n\u0010\u0005\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"},
      d2 = {"Lokhttp3/Cache$CacheResponseBody;", "Lokhttp3/ResponseBody;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "contentType", "", "contentLength", "(Lokhttp3/internal/cache/DiskLruCache$Snapshot;Ljava/lang/String;Ljava/lang/String;)V", "bodySource", "Lokio/BufferedSource;", "getSnapshot$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "", "Lokhttp3/MediaType;", "source", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class CacheResponseBody extends ResponseBody {
      private final BufferedSource bodySource;
      private final String contentLength;
      private final String contentType;
      private final DiskLruCache.Snapshot snapshot;

      public CacheResponseBody(DiskLruCache.Snapshot var1, String var2, String var3) {
         Intrinsics.checkParameterIsNotNull(var1, "snapshot");
         super();
         this.snapshot = var1;
         this.contentType = var2;
         this.contentLength = var3;
         final Source var4 = var1.getSource(1);
         this.bodySource = Okio.buffer((Source)(new ForwardingSource(var4) {
            public void close() throws IOException {
               CacheResponseBody.this.getSnapshot$okhttp().close();
               super.close();
            }
         }));
      }

      public long contentLength() {
         String var1 = this.contentLength;
         long var2 = -1L;
         if (var1 != null) {
            var2 = Util.toLongOrDefault(var1, -1L);
         }

         return var2;
      }

      public MediaType contentType() {
         String var1 = this.contentType;
         MediaType var2;
         if (var1 != null) {
            var2 = MediaType.Companion.parse(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public final DiskLruCache.Snapshot getSnapshot$okhttp() {
         return this.snapshot;
      }

      public BufferedSource source() {
         return this.bodySource;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0015\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\n\u0010\u001b\u001a\u00020\u0015*\u00020\u0017J\u0012\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u001d*\u00020\u0011H\u0002J\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001e"},
      d2 = {"Lokhttp3/Cache$Companion;", "", "()V", "ENTRY_BODY", "", "ENTRY_COUNT", "ENTRY_METADATA", "VERSION", "key", "", "url", "Lokhttp3/HttpUrl;", "readInt", "source", "Lokio/BufferedSource;", "readInt$okhttp", "varyHeaders", "Lokhttp3/Headers;", "requestHeaders", "responseHeaders", "varyMatches", "", "cachedResponse", "Lokhttp3/Response;", "cachedRequest", "newRequest", "Lokhttp3/Request;", "hasVaryAll", "varyFields", "", "okhttp"},
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

      private final Set<String> varyFields(Headers var1) {
         Set var2 = (Set)null;
         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            if (StringsKt.equals("Vary", var1.name(var4), true)) {
               String var5 = var1.value(var4);
               Set var6 = var2;
               if (var2 == null) {
                  var6 = (Set)(new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE)));
               }

               Iterator var8 = StringsKt.split$default((CharSequence)var5, new char[]{','}, false, 0, 6, (Object)null).iterator();

               while(true) {
                  var2 = var6;
                  if (!var8.hasNext()) {
                     break;
                  }

                  String var7 = (String)var8.next();
                  if (var7 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                  }

                  var6.add(StringsKt.trim((CharSequence)var7).toString());
               }
            }
         }

         if (var2 == null) {
            var2 = SetsKt.emptySet();
         }

         return var2;
      }

      private final Headers varyHeaders(Headers var1, Headers var2) {
         Set var3 = ((Cache.Companion)this).varyFields(var2);
         if (var3.isEmpty()) {
            return Util.EMPTY_HEADERS;
         } else {
            Headers.Builder var4 = new Headers.Builder();
            int var5 = 0;

            for(int var6 = var1.size(); var5 < var6; ++var5) {
               String var7 = var1.name(var5);
               if (var3.contains(var7)) {
                  var4.add(var7, var1.value(var5));
               }
            }

            return var4.build();
         }
      }

      public final boolean hasVaryAll(Response var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$hasVaryAll");
         return ((Cache.Companion)this).varyFields(var1.headers()).contains("*");
      }

      @JvmStatic
      public final String key(HttpUrl var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         return ByteString.Companion.encodeUtf8(var1.toString()).md5().hex();
      }

      public final int readInt$okhttp(BufferedSource var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "source");

         NumberFormatException var10000;
         label43: {
            boolean var10001;
            long var2;
            String var4;
            try {
               var2 = var1.readDecimalLong();
               var4 = var1.readUtf8LineStrict();
            } catch (NumberFormatException var9) {
               var10000 = var9;
               var10001 = false;
               break label43;
            }

            if (var2 >= 0L && var2 <= (long)Integer.MAX_VALUE) {
               boolean var5;
               label32: {
                  label31: {
                     try {
                        if (((CharSequence)var4).length() > 0) {
                           break label31;
                        }
                     } catch (NumberFormatException var8) {
                        var10000 = var8;
                        var10001 = false;
                        break label43;
                     }

                     var5 = false;
                     break label32;
                  }

                  var5 = true;
               }

               if (!var5) {
                  return (int)var2;
               }
            }

            try {
               StringBuilder var6 = new StringBuilder();
               var6.append("expected an int but was \"");
               var6.append(var2);
               var6.append(var4);
               var6.append('"');
               IOException var11 = new IOException(var6.toString());
               throw (Throwable)var11;
            } catch (NumberFormatException var7) {
               var10000 = var7;
               var10001 = false;
            }
         }

         NumberFormatException var10 = var10000;
         throw (Throwable)(new IOException(var10.getMessage()));
      }

      public final Headers varyHeaders(Response var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$varyHeaders");
         Response var2 = var1.networkResponse();
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         Headers var4 = var2.request().headers();
         Headers var3 = var1.headers();
         return ((Cache.Companion)this).varyHeaders(var4, var3);
      }

      public final boolean varyMatches(Response var1, Headers var2, Request var3) {
         Intrinsics.checkParameterIsNotNull(var1, "cachedResponse");
         Intrinsics.checkParameterIsNotNull(var2, "cachedRequest");
         Intrinsics.checkParameterIsNotNull(var3, "newRequest");
         Iterable var7 = (Iterable)((Cache.Companion)this).varyFields(var1.headers());
         boolean var4 = var7 instanceof Collection;
         boolean var5 = true;
         if (var4 && ((Collection)var7).isEmpty()) {
            var4 = var5;
         } else {
            Iterator var6 = var7.iterator();

            while(true) {
               var4 = var5;
               if (!var6.hasNext()) {
                  break;
               }

               String var8 = (String)var6.next();
               if (Intrinsics.areEqual((Object)var2.values(var8), (Object)var3.headers(var8)) ^ true) {
                  var4 = false;
                  break;
               }
            }
         }

         return var4;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0010\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010!\u001a\u00020\"H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\n\u0010#\u001a\u00060$R\u00020%J\u001e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J\u0012\u0010+\u001a\u00020'2\n\u0010,\u001a\u00060-R\u00020%R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"},
      d2 = {"Lokhttp3/Cache$Entry;", "", "rawSource", "Lokio/Source;", "(Lokio/Source;)V", "response", "Lokhttp3/Response;", "(Lokhttp3/Response;)V", "code", "", "handshake", "Lokhttp3/Handshake;", "isHttps", "", "()Z", "message", "", "protocol", "Lokhttp3/Protocol;", "receivedResponseMillis", "", "requestMethod", "responseHeaders", "Lokhttp3/Headers;", "sentRequestMillis", "url", "varyHeaders", "matches", "request", "Lokhttp3/Request;", "readCertificateList", "", "Ljava/security/cert/Certificate;", "source", "Lokio/BufferedSource;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "writeCertList", "", "sink", "Lokio/BufferedSink;", "certificates", "writeTo", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Companion", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Entry {
      public static final Cache.Entry.Companion Companion = new Cache.Entry.Companion((DefaultConstructorMarker)null);
      private static final String RECEIVED_MILLIS;
      private static final String SENT_MILLIS;
      private final int code;
      private final Handshake handshake;
      private final String message;
      private final Protocol protocol;
      private final long receivedResponseMillis;
      private final String requestMethod;
      private final Headers responseHeaders;
      private final long sentRequestMillis;
      private final String url;
      private final Headers varyHeaders;

      static {
         StringBuilder var0 = new StringBuilder();
         var0.append(Platform.Companion.get().getPrefix());
         var0.append("-Sent-Millis");
         SENT_MILLIS = var0.toString();
         var0 = new StringBuilder();
         var0.append(Platform.Companion.get().getPrefix());
         var0.append("-Received-Millis");
         RECEIVED_MILLIS = var0.toString();
      }

      public Entry(Response var1) {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         super();
         this.url = var1.request().url().toString();
         this.varyHeaders = Cache.Companion.varyHeaders(var1);
         this.requestMethod = var1.request().method();
         this.protocol = var1.protocol();
         this.code = var1.code();
         this.message = var1.message();
         this.responseHeaders = var1.headers();
         this.handshake = var1.handshake();
         this.sentRequestMillis = var1.sentRequestAtMillis();
         this.receivedResponseMillis = var1.receivedResponseAtMillis();
      }

      public Entry(Source var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "rawSource");
         super();

         label2127: {
            Throwable var10000;
            label2131: {
               BufferedSource var2;
               Headers.Builder var3;
               int var4;
               boolean var10001;
               try {
                  var2 = Okio.buffer(var1);
                  this.url = var2.readUtf8LineStrict();
                  this.requestMethod = var2.readUtf8LineStrict();
                  var3 = new Headers.Builder();
                  var4 = Cache.Companion.readInt$okhttp(var2);
               } catch (Throwable var252) {
                  var10000 = var252;
                  var10001 = false;
                  break label2131;
               }

               boolean var5 = false;

               int var6;
               for(var6 = 0; var6 < var4; ++var6) {
                  try {
                     var3.addLenient$okhttp(var2.readUtf8LineStrict());
                  } catch (Throwable var251) {
                     var10000 = var251;
                     var10001 = false;
                     break label2131;
                  }
               }

               try {
                  this.varyHeaders = var3.build();
                  StatusLine var254 = StatusLine.Companion.parse(var2.readUtf8LineStrict());
                  this.protocol = var254.protocol;
                  this.code = var254.code;
                  this.message = var254.message;
                  var3 = new Headers.Builder();
                  var4 = Cache.Companion.readInt$okhttp(var2);
               } catch (Throwable var250) {
                  var10000 = var250;
                  var10001 = false;
                  break label2131;
               }

               for(var6 = 0; var6 < var4; ++var6) {
                  try {
                     var3.addLenient$okhttp(var2.readUtf8LineStrict());
                  } catch (Throwable var249) {
                     var10000 = var249;
                     var10001 = false;
                     break label2131;
                  }
               }

               String var7;
               String var8;
               try {
                  var7 = var3.get(SENT_MILLIS);
                  var8 = var3.get(RECEIVED_MILLIS);
                  var3.removeAll(SENT_MILLIS);
                  var3.removeAll(RECEIVED_MILLIS);
               } catch (Throwable var248) {
                  var10000 = var248;
                  var10001 = false;
                  break label2131;
               }

               long var9 = 0L;
               long var11;
               if (var7 != null) {
                  try {
                     var11 = Long.parseLong(var7);
                  } catch (Throwable var247) {
                     var10000 = var247;
                     var10001 = false;
                     break label2131;
                  }
               } else {
                  var11 = 0L;
               }

               try {
                  this.sentRequestMillis = var11;
               } catch (Throwable var246) {
                  var10000 = var246;
                  var10001 = false;
                  break label2131;
               }

               var11 = var9;
               if (var8 != null) {
                  try {
                     var11 = Long.parseLong(var8);
                  } catch (Throwable var245) {
                     var10000 = var245;
                     var10001 = false;
                     break label2131;
                  }
               }

               label2134: {
                  String var257;
                  try {
                     this.receivedResponseMillis = var11;
                     this.responseHeaders = var3.build();
                     if (!this.isHttps()) {
                        break label2134;
                     }

                     var257 = var2.readUtf8LineStrict();
                  } catch (Throwable var244) {
                     var10000 = var244;
                     var10001 = false;
                     break label2131;
                  }

                  boolean var259 = var5;

                  label2076: {
                     try {
                        if (((CharSequence)var257).length() <= 0) {
                           break label2076;
                        }
                     } catch (Throwable var242) {
                        var10000 = var242;
                        var10001 = false;
                        break label2131;
                     }

                     var259 = true;
                  }

                  if (!var259) {
                     List var258;
                     CipherSuite var260;
                     List var262;
                     TlsVersion var253;
                     label2068: {
                        try {
                           var257 = var2.readUtf8LineStrict();
                           var260 = CipherSuite.Companion.forJavaName(var257);
                           var262 = this.readCertificateList(var2);
                           var258 = this.readCertificateList(var2);
                           if (!var2.exhausted()) {
                              var253 = TlsVersion.Companion.forJavaName(var2.readUtf8LineStrict());
                              break label2068;
                           }
                        } catch (Throwable var241) {
                           var10000 = var241;
                           var10001 = false;
                           break label2131;
                        }

                        try {
                           var253 = TlsVersion.SSL_3_0;
                        } catch (Throwable var240) {
                           var10000 = var240;
                           var10001 = false;
                           break label2131;
                        }
                     }

                     try {
                        this.handshake = Handshake.Companion.get(var253, var260, var262, var258);
                        break label2127;
                     } catch (Throwable var239) {
                        var10000 = var239;
                        var10001 = false;
                        break label2131;
                     }
                  } else {
                     try {
                        StringBuilder var255 = new StringBuilder();
                        var255.append("expected \"\" but was \"");
                        var255.append(var257);
                        var255.append('"');
                        IOException var261 = new IOException(var255.toString());
                        throw (Throwable)var261;
                     } catch (Throwable var238) {
                        var10000 = var238;
                        var10001 = false;
                        break label2131;
                     }
                  }
               }

               label2080:
               try {
                  this.handshake = (Handshake)null;
                  break label2127;
               } catch (Throwable var243) {
                  var10000 = var243;
                  var10001 = false;
                  break label2080;
               }
            }

            Throwable var256 = var10000;
            var1.close();
            throw var256;
         }

         var1.close();
      }

      private final boolean isHttps() {
         return StringsKt.startsWith$default(this.url, "https://", false, 2, (Object)null);
      }

      private final List<Certificate> readCertificateList(BufferedSource var1) throws IOException {
         int var2 = Cache.Companion.readInt$okhttp(var1);
         if (var2 == -1) {
            return CollectionsKt.emptyList();
         } else {
            CertificateException var10000;
            label52: {
               CertificateFactory var3;
               ArrayList var4;
               boolean var10001;
               try {
                  var3 = CertificateFactory.getInstance("X.509");
                  var4 = new ArrayList(var2);
               } catch (CertificateException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label52;
               }

               for(int var5 = 0; var5 < var2; ++var5) {
                  Buffer var7;
                  ByteString var15;
                  try {
                     String var6 = var1.readUtf8LineStrict();
                     var7 = new Buffer();
                     var15 = ByteString.Companion.decodeBase64(var6);
                  } catch (CertificateException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label52;
                  }

                  if (var15 == null) {
                     try {
                        Intrinsics.throwNpe();
                     } catch (CertificateException var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label52;
                     }
                  }

                  try {
                     var7.write(var15);
                     var4.add(var3.generateCertificate(var7.inputStream()));
                  } catch (CertificateException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label52;
                  }
               }

               try {
                  List var14 = (List)var4;
                  return var14;
               } catch (CertificateException var8) {
                  var10000 = var8;
                  var10001 = false;
               }
            }

            CertificateException var13 = var10000;
            throw (Throwable)(new IOException(var13.getMessage()));
         }
      }

      private final void writeCertList(BufferedSink var1, List<? extends Certificate> var2) throws IOException {
         CertificateEncodingException var10000;
         label35: {
            boolean var10001;
            try {
               var1.writeDecimalLong((long)var2.size()).writeByte(10);
            } catch (CertificateEncodingException var9) {
               var10000 = var9;
               var10001 = false;
               break label35;
            }

            int var3 = 0;

            int var4;
            try {
               var4 = var2.size();
            } catch (CertificateEncodingException var8) {
               var10000 = var8;
               var10001 = false;
               break label35;
            }

            while(true) {
               if (var3 >= var4) {
                  return;
               }

               try {
                  byte[] var5 = ((Certificate)var2.get(var3)).getEncoded();
                  ByteString.Companion var6 = ByteString.Companion;
                  Intrinsics.checkExpressionValueIsNotNull(var5, "bytes");
                  var1.writeUtf8(ByteString.Companion.of$default(var6, var5, 0, 0, 3, (Object)null).base64()).writeByte(10);
               } catch (CertificateEncodingException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         CertificateEncodingException var10 = var10000;
         throw (Throwable)(new IOException(var10.getMessage()));
      }

      public final boolean matches(Request var1, Response var2) {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         Intrinsics.checkParameterIsNotNull(var2, "response");
         boolean var3;
         if (Intrinsics.areEqual((Object)this.url, (Object)var1.url().toString()) && Intrinsics.areEqual((Object)this.requestMethod, (Object)var1.method()) && Cache.Companion.varyMatches(var2, this.varyHeaders, var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public final Response response(DiskLruCache.Snapshot var1) {
         Intrinsics.checkParameterIsNotNull(var1, "snapshot");
         String var2 = this.responseHeaders.get("Content-Type");
         String var3 = this.responseHeaders.get("Content-Length");
         Request var4 = (new Request.Builder()).url(this.url).method(this.requestMethod, (RequestBody)null).headers(this.varyHeaders).build();
         return (new Response.Builder()).request(var4).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body((ResponseBody)(new Cache.CacheResponseBody(var1, var2, var3))).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
      }

      public final void writeTo(DiskLruCache.Editor var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "editor");
         byte var2 = 0;
         Closeable var98 = (Closeable)Okio.buffer(var1.newSink(0));
         Throwable var3 = (Throwable)null;

         label816: {
            Throwable var10000;
            label817: {
               BufferedSink var4;
               int var5;
               boolean var10001;
               try {
                  var4 = (BufferedSink)var98;
                  var4.writeUtf8(this.url).writeByte(10);
                  var4.writeUtf8(this.requestMethod).writeByte(10);
                  var4.writeDecimalLong((long)this.varyHeaders.size()).writeByte(10);
                  var5 = this.varyHeaders.size();
               } catch (Throwable var97) {
                  var10000 = var97;
                  var10001 = false;
                  break label817;
               }

               int var6;
               for(var6 = 0; var6 < var5; ++var6) {
                  try {
                     var4.writeUtf8(this.varyHeaders.name(var6)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(var6)).writeByte(10);
                  } catch (Throwable var96) {
                     var10000 = var96;
                     var10001 = false;
                     break label817;
                  }
               }

               try {
                  StatusLine var7 = new StatusLine(this.protocol, this.code, this.message);
                  var4.writeUtf8(var7.toString()).writeByte(10);
                  var4.writeDecimalLong((long)(this.responseHeaders.size() + 2)).writeByte(10);
                  var5 = this.responseHeaders.size();
               } catch (Throwable var95) {
                  var10000 = var95;
                  var10001 = false;
                  break label817;
               }

               for(var6 = var2; var6 < var5; ++var6) {
                  try {
                     var4.writeUtf8(this.responseHeaders.name(var6)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(var6)).writeByte(10);
                  } catch (Throwable var94) {
                     var10000 = var94;
                     var10001 = false;
                     break label817;
                  }
               }

               label820: {
                  Handshake var100;
                  try {
                     var4.writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
                     var4.writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
                     if (!this.isHttps()) {
                        break label820;
                     }

                     var4.writeByte(10);
                     var100 = this.handshake;
                  } catch (Throwable var93) {
                     var10000 = var93;
                     var10001 = false;
                     break label817;
                  }

                  if (var100 == null) {
                     try {
                        Intrinsics.throwNpe();
                     } catch (Throwable var92) {
                        var10000 = var92;
                        var10001 = false;
                        break label817;
                     }
                  }

                  try {
                     var4.writeUtf8(var100.cipherSuite().javaName()).writeByte(10);
                     this.writeCertList(var4, this.handshake.peerCertificates());
                     this.writeCertList(var4, this.handshake.localCertificates());
                     var4.writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
                  } catch (Throwable var91) {
                     var10000 = var91;
                     var10001 = false;
                     break label817;
                  }
               }

               label776:
               try {
                  Unit var99 = Unit.INSTANCE;
                  break label816;
               } catch (Throwable var90) {
                  var10000 = var90;
                  var10001 = false;
                  break label776;
               }
            }

            var3 = var10000;

            try {
               throw var3;
            } finally {
               CloseableKt.closeFinally(var98, var3);
            }
         }

         CloseableKt.closeFinally(var98, var3);
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"},
         d2 = {"Lokhttp3/Cache$Entry$Companion;", "", "()V", "RECEIVED_MILLIS", "", "SENT_MILLIS", "okhttp"},
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
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0013\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0012\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
      d2 = {"Lokhttp3/Cache$RealCacheRequest;", "Lokhttp3/internal/cache/CacheRequest;", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/Cache;Lokhttp3/internal/cache/DiskLruCache$Editor;)V", "body", "Lokio/Sink;", "cacheOut", "done", "", "getDone$okhttp", "()Z", "setDone$okhttp", "(Z)V", "abort", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class RealCacheRequest implements CacheRequest {
      private final Sink body;
      private final Sink cacheOut;
      private boolean done;
      private final DiskLruCache.Editor editor;

      public RealCacheRequest(DiskLruCache.Editor var2) {
         Intrinsics.checkParameterIsNotNull(var2, "editor");
         super();
         this.editor = var2;
         this.cacheOut = var2.newSink(1);
         this.body = (Sink)(new ForwardingSink(this.cacheOut) {
            public void close() throws IOException {
               Cache var1 = Cache.this;
               synchronized(var1){}

               Throwable var10000;
               label78: {
                  boolean var10001;
                  boolean var2;
                  try {
                     var2 = RealCacheRequest.this.getDone$okhttp();
                  } catch (Throwable var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label78;
                  }

                  if (var2) {
                     return;
                  }

                  try {
                     RealCacheRequest.this.setDone$okhttp(true);
                     Cache var10 = Cache.this;
                     var10.setWriteSuccessCount$okhttp(var10.getWriteSuccessCount$okhttp() + 1);
                  } catch (Throwable var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label78;
                  }

                  super.close();
                  RealCacheRequest.this.editor.commit();
                  return;
               }

               Throwable var3 = var10000;
               throw var3;
            }
         });
      }

      public void abort() {
         Cache var1 = Cache.this;
         synchronized(var1){}

         Throwable var10000;
         label97: {
            boolean var10001;
            boolean var2;
            try {
               var2 = this.done;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label97;
            }

            if (var2) {
               return;
            }

            try {
               this.done = true;
               Cache var13 = Cache.this;
               var13.setWriteAbortCount$okhttp(var13.getWriteAbortCount$okhttp() + 1);
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               break label97;
            }

            Util.closeQuietly((Closeable)this.cacheOut);

            try {
               this.editor.abort();
            } catch (IOException var10) {
            }

            return;
         }

         Throwable var3 = var10000;
         throw var3;
      }

      public Sink body() {
         return this.body;
      }

      public final boolean getDone$okhttp() {
         return this.done;
      }

      public final void setDone$okhttp(boolean var1) {
         this.done = var1;
      }
   }
}
