package kotlin.io;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"},
   d2 = {"Lkotlin/io/FileSystemException;", "Ljava/io/IOException;", "file", "Ljava/io/File;", "other", "reason", "", "(Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V", "getFile", "()Ljava/io/File;", "getOther", "getReason", "()Ljava/lang/String;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public class FileSystemException extends IOException {
   private final File file;
   private final File other;
   private final String reason;

   public FileSystemException(File var1, File var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      super(ExceptionsKt.access$constructMessage(var1, var2, var3));
      this.file = var1;
      this.other = var2;
      this.reason = var3;
   }

   // $FF: synthetic method
   public FileSystemException(File var1, File var2, String var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 2) != 0) {
         var2 = (File)null;
      }

      if ((var4 & 4) != 0) {
         var3 = (String)null;
      }

      this(var1, var2, var3);
   }

   public final File getFile() {
      return this.file;
   }

   public final File getOther() {
      return this.other;
   }

   public final String getReason() {
      return this.reason;
   }
}
