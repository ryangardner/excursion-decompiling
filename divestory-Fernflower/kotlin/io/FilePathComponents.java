package kotlin.io;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0013HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013J\t\u0010\u001f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "},
   d2 = {"Lkotlin/io/FilePathComponents;", "", "root", "Ljava/io/File;", "segments", "", "(Ljava/io/File;Ljava/util/List;)V", "isRooted", "", "()Z", "getRoot", "()Ljava/io/File;", "rootName", "", "getRootName", "()Ljava/lang/String;", "getSegments", "()Ljava/util/List;", "size", "", "getSize", "()I", "component1", "component2", "copy", "equals", "other", "hashCode", "subPath", "beginIndex", "endIndex", "toString", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FilePathComponents {
   private final File root;
   private final List<File> segments;

   public FilePathComponents(File var1, List<? extends File> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "root");
      Intrinsics.checkParameterIsNotNull(var2, "segments");
      super();
      this.root = var1;
      this.segments = var2;
   }

   // $FF: synthetic method
   public static FilePathComponents copy$default(FilePathComponents var0, File var1, List var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.root;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.segments;
      }

      return var0.copy(var1, var2);
   }

   public final File component1() {
      return this.root;
   }

   public final List<File> component2() {
      return this.segments;
   }

   public final FilePathComponents copy(File var1, List<? extends File> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "root");
      Intrinsics.checkParameterIsNotNull(var2, "segments");
      return new FilePathComponents(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof FilePathComponents)) {
            return false;
         }

         FilePathComponents var2 = (FilePathComponents)var1;
         if (!Intrinsics.areEqual((Object)this.root, (Object)var2.root) || !Intrinsics.areEqual((Object)this.segments, (Object)var2.segments)) {
            return false;
         }
      }

      return true;
   }

   public final File getRoot() {
      return this.root;
   }

   public final String getRootName() {
      String var1 = this.root.getPath();
      Intrinsics.checkExpressionValueIsNotNull(var1, "root.path");
      return var1;
   }

   public final List<File> getSegments() {
      return this.segments;
   }

   public final int getSize() {
      return this.segments.size();
   }

   public int hashCode() {
      File var1 = this.root;
      int var2 = 0;
      int var3;
      if (var1 != null) {
         var3 = var1.hashCode();
      } else {
         var3 = 0;
      }

      List var4 = this.segments;
      if (var4 != null) {
         var2 = var4.hashCode();
      }

      return var3 * 31 + var2;
   }

   public final boolean isRooted() {
      String var1 = this.root.getPath();
      Intrinsics.checkExpressionValueIsNotNull(var1, "root.path");
      boolean var2;
      if (((CharSequence)var1).length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final File subPath(int var1, int var2) {
      if (var1 >= 0 && var1 <= var2 && var2 <= this.getSize()) {
         Iterable var3 = (Iterable)this.segments.subList(var1, var2);
         String var4 = File.separator;
         Intrinsics.checkExpressionValueIsNotNull(var4, "File.separator");
         return new File(CollectionsKt.joinToString$default(var3, (CharSequence)var4, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
      } else {
         throw (Throwable)(new IllegalArgumentException());
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("FilePathComponents(root=");
      var1.append(this.root);
      var1.append(", segments=");
      var1.append(this.segments);
      var1.append(")");
      return var1.toString();
   }
}
