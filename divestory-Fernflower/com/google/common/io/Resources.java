package com.google.common.io;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public final class Resources {
   private Resources() {
   }

   public static ByteSource asByteSource(URL var0) {
      return new Resources.UrlByteSource(var0);
   }

   public static CharSource asCharSource(URL var0, Charset var1) {
      return asByteSource(var0).asCharSource(var1);
   }

   public static void copy(URL var0, OutputStream var1) throws IOException {
      asByteSource(var0).copyTo(var1);
   }

   public static URL getResource(Class<?> var0, String var1) {
      URL var2 = var0.getResource(var1);
      boolean var3;
      if (var2 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "resource %s relative to %s not found.", var1, var0.getName());
      return var2;
   }

   public static URL getResource(String var0) {
      URL var1 = ((ClassLoader)MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader())).getResource(var0);
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "resource %s not found.", (Object)var0);
      return var1;
   }

   public static <T> T readLines(URL var0, Charset var1, LineProcessor<T> var2) throws IOException {
      return asCharSource(var0, var1).readLines(var2);
   }

   public static List<String> readLines(URL var0, Charset var1) throws IOException {
      return (List)readLines(var0, var1, new LineProcessor<List<String>>() {
         final List<String> result = Lists.newArrayList();

         public List<String> getResult() {
            return this.result;
         }

         public boolean processLine(String var1) {
            this.result.add(var1);
            return true;
         }
      });
   }

   public static byte[] toByteArray(URL var0) throws IOException {
      return asByteSource(var0).read();
   }

   public static String toString(URL var0, Charset var1) throws IOException {
      return asCharSource(var0, var1).read();
   }

   private static final class UrlByteSource extends ByteSource {
      private final URL url;

      private UrlByteSource(URL var1) {
         this.url = (URL)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      UrlByteSource(URL var1, Object var2) {
         this(var1);
      }

      public InputStream openStream() throws IOException {
         return this.url.openStream();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Resources.asByteSource(");
         var1.append(this.url);
         var1.append(")");
         return var1.toString();
      }
   }
}
