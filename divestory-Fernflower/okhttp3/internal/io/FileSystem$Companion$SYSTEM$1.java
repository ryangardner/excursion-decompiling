package okhttp3.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0016J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0016"},
   d2 = {"okhttp3/internal/io/FileSystem$Companion$SYSTEM$1", "Lokhttp3/internal/io/FileSystem;", "appendingSink", "Lokio/Sink;", "file", "Ljava/io/File;", "delete", "", "deleteContents", "directory", "exists", "", "rename", "from", "to", "sink", "size", "", "source", "Lokio/Source;", "toString", "", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FileSystem$Companion$SYSTEM$1 implements FileSystem {
   FileSystem$Companion$SYSTEM$1() {
   }

   public Sink appendingSink(File var1) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var1, "file");

      Sink var2;
      Sink var4;
      try {
         var2 = Okio.appendingSink(var1);
      } catch (FileNotFoundException var3) {
         var1.getParentFile().mkdirs();
         var4 = Okio.appendingSink(var1);
         return var4;
      }

      var4 = var2;
      return var4;
   }

   public void delete(File var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      if (!var1.delete() && var1.exists()) {
         StringBuilder var2 = new StringBuilder();
         var2.append("failed to delete ");
         var2.append(var1);
         throw (Throwable)(new IOException(var2.toString()));
      }
   }

   public void deleteContents(File var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "directory");
      File[] var2 = var1.listFiles();
      StringBuilder var5;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1 = var2[var4];
            Intrinsics.checkExpressionValueIsNotNull(var1, "file");
            if (var1.isDirectory()) {
               this.deleteContents(var1);
            }

            if (!var1.delete()) {
               var5 = new StringBuilder();
               var5.append("failed to delete ");
               var5.append(var1);
               throw (Throwable)(new IOException(var5.toString()));
            }
         }

      } else {
         var5 = new StringBuilder();
         var5.append("not a readable directory: ");
         var5.append(var1);
         throw (Throwable)(new IOException(var5.toString()));
      }
   }

   public boolean exists(File var1) {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return var1.exists();
   }

   public void rename(File var1, File var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "from");
      Intrinsics.checkParameterIsNotNull(var2, "to");
      this.delete(var2);
      if (!var1.renameTo(var2)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("failed to rename ");
         var3.append(var1);
         var3.append(" to ");
         var3.append(var2);
         throw (Throwable)(new IOException(var3.toString()));
      }
   }

   public Sink sink(File var1) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var1, "file");

      Sink var2;
      Sink var4;
      try {
         var2 = Okio.sink$default(var1, false, 1, (Object)null);
      } catch (FileNotFoundException var3) {
         var1.getParentFile().mkdirs();
         var4 = Okio.sink$default(var1, false, 1, (Object)null);
         return var4;
      }

      var4 = var2;
      return var4;
   }

   public long size(File var1) {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return var1.length();
   }

   public Source source(File var1) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return Okio.source(var1);
   }

   public String toString() {
      return "FileSystem.SYSTEM";
   }
}
