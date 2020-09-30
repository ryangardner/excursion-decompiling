package com.google.common.io;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Files {
   private static final SuccessorsFunction<File> FILE_TREE = new SuccessorsFunction<File>() {
      public Iterable<File> successors(File var1) {
         return Files.fileTreeChildren(var1);
      }
   };
   private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>() {
      public Iterable<File> children(File var1) {
         return Files.fileTreeChildren(var1);
      }

      public String toString() {
         return "Files.fileTreeTraverser()";
      }
   };
   private static final int TEMP_DIR_ATTEMPTS = 10000;

   private Files() {
   }

   @Deprecated
   public static void append(CharSequence var0, File var1, Charset var2) throws IOException {
      asCharSink(var1, var2, FileWriteMode.APPEND).write(var0);
   }

   public static ByteSink asByteSink(File var0, FileWriteMode... var1) {
      return new Files.FileByteSink(var0, var1);
   }

   public static ByteSource asByteSource(File var0) {
      return new Files.FileByteSource(var0);
   }

   public static CharSink asCharSink(File var0, Charset var1, FileWriteMode... var2) {
      return asByteSink(var0, var2).asCharSink(var1);
   }

   public static CharSource asCharSource(File var0, Charset var1) {
      return asByteSource(var0).asCharSource(var1);
   }

   public static void copy(File var0, File var1) throws IOException {
      Preconditions.checkArgument(var0.equals(var1) ^ true, "Source %s and destination %s must be different", var0, var1);
      asByteSource(var0).copyTo(asByteSink(var1));
   }

   public static void copy(File var0, OutputStream var1) throws IOException {
      asByteSource(var0).copyTo(var1);
   }

   @Deprecated
   public static void copy(File var0, Charset var1, Appendable var2) throws IOException {
      asCharSource(var0, var1).copyTo(var2);
   }

   public static void createParentDirs(File var0) throws IOException {
      Preconditions.checkNotNull(var0);
      File var1 = var0.getCanonicalFile().getParentFile();
      if (var1 != null) {
         var1.mkdirs();
         if (!var1.isDirectory()) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Unable to create parent directories of ");
            var2.append(var0);
            throw new IOException(var2.toString());
         }
      }
   }

   public static File createTempDir() {
      File var0 = new File(System.getProperty("java.io.tmpdir"));
      StringBuilder var1 = new StringBuilder();
      var1.append(System.currentTimeMillis());
      var1.append("-");
      String var5 = var1.toString();

      for(int var2 = 0; var2 < 10000; ++var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var5);
         var3.append(var2);
         File var6 = new File(var0, var3.toString());
         if (var6.mkdir()) {
            return var6;
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("Failed to create directory within 10000 attempts (tried ");
      var4.append(var5);
      var4.append("0 to ");
      var4.append(var5);
      var4.append(9999);
      var4.append(')');
      throw new IllegalStateException(var4.toString());
   }

   public static boolean equal(File var0, File var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      if (var0 != var1 && !var0.equals(var1)) {
         long var2 = var0.length();
         long var4 = var1.length();
         return var2 != 0L && var4 != 0L && var2 != var4 ? false : asByteSource(var0).contentEquals(asByteSource(var1));
      } else {
         return true;
      }
   }

   public static Traverser<File> fileTraverser() {
      return Traverser.forTree(FILE_TREE);
   }

   private static Iterable<File> fileTreeChildren(File var0) {
      if (var0.isDirectory()) {
         File[] var1 = var0.listFiles();
         if (var1 != null) {
            return Collections.unmodifiableList(Arrays.asList(var1));
         }
      }

      return Collections.emptyList();
   }

   @Deprecated
   static TreeTraverser<File> fileTreeTraverser() {
      return FILE_TREE_TRAVERSER;
   }

   public static String getFileExtension(String var0) {
      Preconditions.checkNotNull(var0);
      var0 = (new File(var0)).getName();
      int var1 = var0.lastIndexOf(46);
      if (var1 == -1) {
         var0 = "";
      } else {
         var0 = var0.substring(var1 + 1);
      }

      return var0;
   }

   public static String getNameWithoutExtension(String var0) {
      Preconditions.checkNotNull(var0);
      var0 = (new File(var0)).getName();
      int var1 = var0.lastIndexOf(46);
      if (var1 != -1) {
         var0 = var0.substring(0, var1);
      }

      return var0;
   }

   @Deprecated
   public static HashCode hash(File var0, HashFunction var1) throws IOException {
      return asByteSource(var0).hash(var1);
   }

   public static Predicate<File> isDirectory() {
      return Files.FilePredicate.IS_DIRECTORY;
   }

   public static Predicate<File> isFile() {
      return Files.FilePredicate.IS_FILE;
   }

   public static MappedByteBuffer map(File var0) throws IOException {
      Preconditions.checkNotNull(var0);
      return map(var0, MapMode.READ_ONLY);
   }

   public static MappedByteBuffer map(File var0, MapMode var1) throws IOException {
      return mapInternal(var0, var1, -1L);
   }

   public static MappedByteBuffer map(File var0, MapMode var1, long var2) throws IOException {
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "size (%s) may not be negative", var2);
      return mapInternal(var0, var1, var2);
   }

   private static MappedByteBuffer mapInternal(File var0, MapMode var1, long var2) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Closer var4 = Closer.create();

      MappedByteBuffer var41;
      label275: {
         Throwable var10000;
         label276: {
            RandomAccessFile var5;
            String var6;
            boolean var10001;
            label269: {
               label268: {
                  try {
                     var5 = new RandomAccessFile;
                     if (var1 == MapMode.READ_ONLY) {
                        break label268;
                     }
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label276;
                  }

                  var6 = "rw";
                  break label269;
               }

               var6 = "r";
            }

            FileChannel var39;
            try {
               var5.<init>(var0, var6);
               var39 = (FileChannel)var4.register(((RandomAccessFile)var4.register(var5)).getChannel());
            } catch (Throwable var37) {
               var10000 = var37;
               var10001 = false;
               break label276;
            }

            long var7 = var2;
            if (var2 == -1L) {
               try {
                  var7 = var39.size();
               } catch (Throwable var36) {
                  var10000 = var36;
                  var10001 = false;
                  break label276;
               }
            }

            label255:
            try {
               var41 = var39.map(var1, 0L, var7);
               break label275;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label255;
            }
         }

         Throwable var40 = var10000;

         try {
            throw var4.rethrow(var40);
         } finally {
            var4.close();
         }
      }

      var4.close();
      return var41;
   }

   public static void move(File var0, File var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(var0.equals(var1) ^ true, "Source %s and destination %s must be different", var0, var1);
      if (!var0.renameTo(var1)) {
         copy(var0, var1);
         if (!var0.delete()) {
            if (!var1.delete()) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Unable to delete ");
               var2.append(var1);
               throw new IOException(var2.toString());
            }

            StringBuilder var3 = new StringBuilder();
            var3.append("Unable to delete ");
            var3.append(var0);
            throw new IOException(var3.toString());
         }
      }

   }

   public static BufferedReader newReader(File var0, Charset var1) throws FileNotFoundException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new BufferedReader(new InputStreamReader(new FileInputStream(var0), var1));
   }

   public static BufferedWriter newWriter(File var0, Charset var1) throws FileNotFoundException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(var0), var1));
   }

   @Deprecated
   public static <T> T readBytes(File var0, ByteProcessor<T> var1) throws IOException {
      return asByteSource(var0).read(var1);
   }

   @Deprecated
   public static String readFirstLine(File var0, Charset var1) throws IOException {
      return asCharSource(var0, var1).readFirstLine();
   }

   @Deprecated
   public static <T> T readLines(File var0, Charset var1, LineProcessor<T> var2) throws IOException {
      return asCharSource(var0, var1).readLines(var2);
   }

   public static List<String> readLines(File var0, Charset var1) throws IOException {
      return (List)asCharSource(var0, var1).readLines(new LineProcessor<List<String>>() {
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

   public static String simplifyPath(String var0) {
      Preconditions.checkNotNull(var0);
      int var1 = var0.length();
      String var2 = ".";
      if (var1 == 0) {
         return ".";
      } else {
         Iterable var3 = Splitter.on('/').omitEmptyStrings().split(var0);
         ArrayList var4 = new ArrayList();
         Iterator var10 = var3.iterator();

         while(true) {
            while(true) {
               String var6;
               byte var9;
               do {
                  boolean var5 = var10.hasNext();
                  var9 = 0;
                  if (!var5) {
                     String var11 = Joiner.on('/').join((Iterable)var4);
                     String var12 = var11;
                     if (var0.charAt(0) == '/') {
                        StringBuilder var8 = new StringBuilder();
                        var8.append("/");
                        var8.append(var11);
                        var12 = var8.toString();
                     }

                     while(var12.startsWith("/../")) {
                        var12 = var12.substring(3);
                     }

                     if (var12.equals("/..")) {
                        var0 = "/";
                     } else if ("".equals(var12)) {
                        var0 = var2;
                     } else {
                        var0 = var12;
                     }

                     return var0;
                  }

                  var6 = (String)var10.next();
                  int var7 = var6.hashCode();
                  if (var7 != 46) {
                     if (var7 == 1472 && var6.equals("..")) {
                        var9 = 1;
                        continue;
                     }
                  } else if (var6.equals(".")) {
                     continue;
                  }

                  var9 = -1;
               } while(var9 == 0);

               if (var9 != 1) {
                  var4.add(var6);
               } else if (var4.size() > 0 && !((String)var4.get(var4.size() - 1)).equals("..")) {
                  var4.remove(var4.size() - 1);
               } else {
                  var4.add("..");
               }
            }
         }
      }
   }

   public static byte[] toByteArray(File var0) throws IOException {
      return asByteSource(var0).read();
   }

   @Deprecated
   public static String toString(File var0, Charset var1) throws IOException {
      return asCharSource(var0, var1).read();
   }

   public static void touch(File var0) throws IOException {
      Preconditions.checkNotNull(var0);
      if (!var0.createNewFile() && !var0.setLastModified(System.currentTimeMillis())) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unable to update modification time of ");
         var1.append(var0);
         throw new IOException(var1.toString());
      }
   }

   @Deprecated
   public static void write(CharSequence var0, File var1, Charset var2) throws IOException {
      asCharSink(var1, var2).write(var0);
   }

   public static void write(byte[] var0, File var1) throws IOException {
      asByteSink(var1).write(var0);
   }

   private static final class FileByteSink extends ByteSink {
      private final File file;
      private final ImmutableSet<FileWriteMode> modes;

      private FileByteSink(File var1, FileWriteMode... var2) {
         this.file = (File)Preconditions.checkNotNull(var1);
         this.modes = ImmutableSet.copyOf((Object[])var2);
      }

      // $FF: synthetic method
      FileByteSink(File var1, FileWriteMode[] var2, Object var3) {
         this(var1, var2);
      }

      public FileOutputStream openStream() throws IOException {
         return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Files.asByteSink(");
         var1.append(this.file);
         var1.append(", ");
         var1.append(this.modes);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class FileByteSource extends ByteSource {
      private final File file;

      private FileByteSource(File var1) {
         this.file = (File)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      FileByteSource(File var1, Object var2) {
         this(var1);
      }

      public FileInputStream openStream() throws IOException {
         return new FileInputStream(this.file);
      }

      public byte[] read() throws IOException {
         Closer var1 = Closer.create();

         byte[] var10;
         try {
            FileInputStream var9 = (FileInputStream)var1.register(this.openStream());
            var10 = ByteStreams.toByteArray(var9, var9.getChannel().size());
         } catch (Throwable var8) {
            Throwable var2 = var8;

            try {
               throw var1.rethrow(var2);
            } finally {
               var1.close();
            }
         }

         var1.close();
         return var10;
      }

      public long size() throws IOException {
         if (this.file.isFile()) {
            return this.file.length();
         } else {
            throw new FileNotFoundException(this.file.toString());
         }
      }

      public Optional<Long> sizeIfKnown() {
         return this.file.isFile() ? Optional.of(this.file.length()) : Optional.absent();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Files.asByteSource(");
         var1.append(this.file);
         var1.append(")");
         return var1.toString();
      }
   }

   private static enum FilePredicate implements Predicate<File> {
      IS_DIRECTORY {
         public boolean apply(File var1) {
            return var1.isDirectory();
         }

         public String toString() {
            return "Files.isDirectory()";
         }
      },
      IS_FILE;

      static {
         Files.FilePredicate var0 = new Files.FilePredicate("IS_FILE", 1) {
            public boolean apply(File var1) {
               return var1.isFile();
            }

            public String toString() {
               return "Files.isFile()";
            }
         };
         IS_FILE = var0;
      }

      private FilePredicate() {
      }

      // $FF: synthetic method
      FilePredicate(Object var3) {
         this();
      }
   }
}
