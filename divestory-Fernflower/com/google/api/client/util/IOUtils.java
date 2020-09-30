package com.google.api.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class IOUtils {
   public static long computeLength(StreamingContent var0) throws IOException {
      ByteCountingOutputStream var1 = new ByteCountingOutputStream();

      try {
         var0.writeTo(var1);
      } finally {
         var1.close();
      }

      return var1.count;
   }

   public static void copy(InputStream var0, OutputStream var1) throws IOException {
      copy(var0, var1, true);
   }

   public static void copy(InputStream var0, OutputStream var1, boolean var2) throws IOException {
      try {
         ByteStreams.copy(var0, var1);
      } finally {
         if (var2) {
            var0.close();
         }

      }

   }

   public static <S extends Serializable> S deserialize(InputStream var0) throws IOException {
      Serializable var8;
      try {
         ObjectInputStream var7 = new ObjectInputStream(var0);
         var8 = (Serializable)var7.readObject();
      } catch (ClassNotFoundException var5) {
         IOException var1 = new IOException("Failed to deserialize object");
         var1.initCause(var5);
         throw var1;
      } finally {
         var0.close();
      }

      return var8;
   }

   public static <S extends Serializable> S deserialize(byte[] var0) throws IOException {
      return var0 == null ? null : deserialize((InputStream)(new ByteArrayInputStream(var0)));
   }

   public static boolean isSymbolicLink(File var0) throws IOException {
      try {
         Class var1 = Class.forName("java.nio.file.Files");
         Class var2 = Class.forName("java.nio.file.Path");
         Object var8 = File.class.getMethod("toPath").invoke(var0);
         boolean var4 = (Boolean)var1.getMethod("isSymbolicLink", var2).invoke((Object)null, var8);
         return var4;
      } catch (InvocationTargetException var5) {
         Throwable var7 = var5.getCause();
         Throwables.propagateIfPossible(var7, IOException.class);
         throw new RuntimeException(var7);
      } catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException var6) {
         if (File.separatorChar == '\\') {
            return false;
         } else {
            File var3 = var0;
            if (var0.getParent() != null) {
               var3 = new File(var0.getParentFile().getCanonicalFile(), var0.getName());
            }

            return var3.getCanonicalFile().equals(var3.getAbsoluteFile()) ^ true;
         }
      }
   }

   public static void serialize(Object var0, OutputStream var1) throws IOException {
      try {
         ObjectOutputStream var2 = new ObjectOutputStream(var1);
         var2.writeObject(var0);
      } finally {
         var1.close();
      }

   }

   public static byte[] serialize(Object var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      serialize(var0, var1);
      return var1.toByteArray();
   }
}
