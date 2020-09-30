package com.google.common.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class FileBackedOutputStream extends OutputStream {
   @NullableDecl
   private File file;
   private final int fileThreshold;
   private FileBackedOutputStream.MemoryOutput memory;
   private OutputStream out;
   @NullableDecl
   private final File parentDirectory;
   private final boolean resetOnFinalize;
   private final ByteSource source;

   public FileBackedOutputStream(int var1) {
      this(var1, false);
   }

   public FileBackedOutputStream(int var1, boolean var2) {
      this(var1, var2, (File)null);
   }

   private FileBackedOutputStream(int var1, boolean var2, @NullableDecl File var3) {
      this.fileThreshold = var1;
      this.resetOnFinalize = var2;
      this.parentDirectory = var3;
      FileBackedOutputStream.MemoryOutput var4 = new FileBackedOutputStream.MemoryOutput();
      this.memory = var4;
      this.out = var4;
      if (var2) {
         this.source = new ByteSource() {
            protected void finalize() {
               try {
                  FileBackedOutputStream.this.reset();
               } catch (Throwable var3) {
                  var3.printStackTrace(System.err);
                  return;
               }

            }

            public InputStream openStream() throws IOException {
               return FileBackedOutputStream.this.openInputStream();
            }
         };
      } else {
         this.source = new ByteSource() {
            public InputStream openStream() throws IOException {
               return FileBackedOutputStream.this.openInputStream();
            }
         };
      }

   }

   private InputStream openInputStream() throws IOException {
      synchronized(this){}

      ByteArrayInputStream var1;
      try {
         if (this.file != null) {
            FileInputStream var4 = new FileInputStream(this.file);
            return var4;
         }

         var1 = new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
      } finally {
         ;
      }

      return var1;
   }

   private void update(int var1) throws IOException {
      if (this.file == null && this.memory.getCount() + var1 > this.fileThreshold) {
         File var2 = File.createTempFile("FileBackedOutputStream", (String)null, this.parentDirectory);
         if (this.resetOnFinalize) {
            var2.deleteOnExit();
         }

         FileOutputStream var3 = new FileOutputStream(var2);
         var3.write(this.memory.getBuffer(), 0, this.memory.getCount());
         var3.flush();
         this.out = var3;
         this.file = var2;
         this.memory = null;
      }

   }

   public ByteSource asByteSource() {
      return this.source;
   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         this.out.close();
      } finally {
         ;
      }

   }

   public void flush() throws IOException {
      synchronized(this){}

      try {
         this.out.flush();
      } finally {
         ;
      }

   }

   File getFile() {
      synchronized(this){}

      File var1;
      try {
         var1 = this.file;
      } finally {
         ;
      }

      return var1;
   }

   public void reset() throws IOException {
      synchronized(this){}
      boolean var85 = false;

      Throwable var10000;
      label823: {
         FileBackedOutputStream.MemoryOutput var1;
         StringBuilder var3;
         boolean var10001;
         try {
            var85 = true;
            this.close();
            var85 = false;
         } finally {
            if (var85) {
               label790: {
                  try {
                     if (this.memory == null) {
                        var1 = new FileBackedOutputStream.MemoryOutput();
                        this.memory = var1;
                        break label790;
                     }
                  } catch (Throwable var89) {
                     var10000 = var89;
                     var10001 = false;
                     break label823;
                  }

                  try {
                     this.memory.reset();
                  } catch (Throwable var88) {
                     var10000 = var88;
                     var10001 = false;
                     break label823;
                  }
               }

               try {
                  this.out = this.memory;
                  if (this.file != null) {
                     File var95 = this.file;
                     this.file = null;
                     if (!var95.delete()) {
                        var3 = new StringBuilder();
                        var3.append("Could not delete: ");
                        var3.append(var95);
                        IOException var97 = new IOException(var3.toString());
                        throw var97;
                     }
                  }
               } catch (Throwable var87) {
                  var10000 = var87;
                  var10001 = false;
                  break label823;
               }

               try {
                  ;
               } catch (Throwable var86) {
                  var10000 = var86;
                  var10001 = false;
                  break label823;
               }
            }
         }

         label811: {
            try {
               if (this.memory == null) {
                  var1 = new FileBackedOutputStream.MemoryOutput();
                  this.memory = var1;
                  break label811;
               }
            } catch (Throwable var94) {
               var10000 = var94;
               var10001 = false;
               break label823;
            }

            try {
               this.memory.reset();
            } catch (Throwable var91) {
               var10000 = var91;
               var10001 = false;
               break label823;
            }
         }

         File var2;
         try {
            this.out = this.memory;
            if (this.file == null) {
               return;
            }

            var2 = this.file;
            this.file = null;
            if (var2.delete()) {
               return;
            }
         } catch (Throwable var93) {
            var10000 = var93;
            var10001 = false;
            break label823;
         }

         label794:
         try {
            var3 = new StringBuilder();
            var3.append("Could not delete: ");
            var3.append(var2);
            IOException var98 = new IOException(var3.toString());
            throw var98;
         } catch (Throwable var90) {
            var10000 = var90;
            var10001 = false;
            break label794;
         }
      }

      Throwable var96 = var10000;
      throw var96;
   }

   public void write(int var1) throws IOException {
      synchronized(this){}

      try {
         this.update(1);
         this.out.write(var1);
      } finally {
         ;
      }

   }

   public void write(byte[] var1) throws IOException {
      synchronized(this){}

      try {
         this.write(var1, 0, var1.length);
      } finally {
         ;
      }

   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}

      try {
         this.update(var3);
         this.out.write(var1, var2, var3);
      } finally {
         ;
      }

   }

   private static class MemoryOutput extends ByteArrayOutputStream {
      private MemoryOutput() {
      }

      // $FF: synthetic method
      MemoryOutput(Object var1) {
         this();
      }

      byte[] getBuffer() {
         return this.buf;
      }

      int getCount() {
         return this.count;
      }
   }
}
