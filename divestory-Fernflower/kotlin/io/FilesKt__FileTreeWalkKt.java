package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007"},
   d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt {
   public FilesKt__FileTreeWalkKt() {
   }

   public static final FileTreeWalk walk(File var0, FileWalkDirection var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$walk");
      Intrinsics.checkParameterIsNotNull(var1, "direction");
      return new FileTreeWalk(var0, var1);
   }

   // $FF: synthetic method
   public static FileTreeWalk walk$default(File var0, FileWalkDirection var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = FileWalkDirection.TOP_DOWN;
      }

      return FilesKt.walk(var0, var1);
   }

   public static final FileTreeWalk walkBottomUp(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$walkBottomUp");
      return FilesKt.walk(var0, FileWalkDirection.BOTTOM_UP);
   }

   public static final FileTreeWalk walkTopDown(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$walkTopDown");
      return FilesKt.walk(var0, FileWalkDirection.TOP_DOWN);
   }
}
