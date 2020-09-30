package kotlin.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0089\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0096\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\u00002\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FileTreeWalk implements Sequence<File> {
   private final FileWalkDirection direction;
   private final int maxDepth;
   private final Function1<File, Boolean> onEnter;
   private final Function2<File, IOException, Unit> onFail;
   private final Function1<File, Unit> onLeave;
   private final File start;

   public FileTreeWalk(File var1, FileWalkDirection var2) {
      Intrinsics.checkParameterIsNotNull(var1, "start");
      Intrinsics.checkParameterIsNotNull(var2, "direction");
      this(var1, var2, (Function1)null, (Function1)null, (Function2)null, 0, 32, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public FileTreeWalk(File var1, FileWalkDirection var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      this(var1, var2);
   }

   private FileTreeWalk(File var1, FileWalkDirection var2, Function1<? super File, Boolean> var3, Function1<? super File, Unit> var4, Function2<? super File, ? super IOException, Unit> var5, int var6) {
      super();
      this.start = var1;
      this.direction = var2;
      this.onEnter = var3;
      this.onLeave = var4;
      this.onFail = var5;
      this.maxDepth = var6;
   }

   // $FF: synthetic method
   FileTreeWalk(File var1, FileWalkDirection var2, Function1 var3, Function1 var4, Function2 var5, int var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      if ((var7 & 32) != 0) {
         var6 = Integer.MAX_VALUE;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   public Iterator<File> iterator() {
      return (Iterator)(new FileTreeWalk.FileTreeWalkIterator());
   }

   public final FileTreeWalk maxDepth(int var1) {
      if (var1 > 0) {
         return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, var1);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("depth must be positive, but was ");
         var2.append(var1);
         var2.append('.');
         throw (Throwable)(new IllegalArgumentException(var2.toString()));
      }
   }

   public final FileTreeWalk onEnter(Function1<? super File, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "function");
      return new FileTreeWalk(this.start, this.direction, var1, this.onLeave, this.onFail, this.maxDepth);
   }

   public final FileTreeWalk onFail(Function2<? super File, ? super IOException, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, var1, this.maxDepth);
   }

   public final FileTreeWalk onLeave(Function1<? super File, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, var1, this.onFail, this.maxDepth);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
      d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private abstract static class DirectoryState extends FileTreeWalk.WalkState {
      public DirectoryState(File var1) {
         Intrinsics.checkParameterIsNotNull(var1, "rootDir");
         super(var1);
         if (_Assertions.ENABLED) {
            boolean var2 = var1.isDirectory();
            if (_Assertions.ENABLED && !var2) {
               throw (Throwable)(new AssertionError("rootDir must be verified to be directory beforehand."));
            }
         }

      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0082\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class FileTreeWalkIterator extends AbstractIterator<File> {
      private final ArrayDeque<FileTreeWalk.WalkState> state = new ArrayDeque();

      public FileTreeWalkIterator() {
         if (FileTreeWalk.this.start.isDirectory()) {
            this.state.push(this.directoryState(FileTreeWalk.this.start));
         } else if (FileTreeWalk.this.start.isFile()) {
            this.state.push(new FileTreeWalk.FileTreeWalkIterator.SingleFileState(FileTreeWalk.this.start));
         } else {
            this.done();
         }

      }

      private final FileTreeWalk.DirectoryState directoryState(File var1) {
         FileWalkDirection var2 = FileTreeWalk.this.direction;
         int var3 = FileTreeWalk$FileTreeWalkIterator$WhenMappings.$EnumSwitchMapping$0[var2.ordinal()];
         FileTreeWalk.DirectoryState var4;
         if (var3 != 1) {
            if (var3 != 2) {
               throw new NoWhenBranchMatchedException();
            }

            var4 = (FileTreeWalk.DirectoryState)(new FileTreeWalk.FileTreeWalkIterator.BottomUpDirectoryState(var1));
         } else {
            var4 = (FileTreeWalk.DirectoryState)(new FileTreeWalk.FileTreeWalkIterator.TopDownDirectoryState(var1));
         }

         return var4;
      }

      private final File gotoNext() {
         while(true) {
            FileTreeWalk.WalkState var1 = (FileTreeWalk.WalkState)this.state.peek();
            if (var1 != null) {
               File var2 = var1.step();
               if (var2 == null) {
                  this.state.pop();
                  continue;
               }

               if (!Intrinsics.areEqual((Object)var2, (Object)var1.getRoot()) && var2.isDirectory() && this.state.size() < FileTreeWalk.this.maxDepth) {
                  this.state.push(this.directoryState(var2));
                  continue;
               }

               return var2;
            }

            return null;
         }
      }

      protected void computeNext() {
         File var1 = this.gotoNext();
         if (var1 != null) {
            this.setNext(var1);
         } else {
            this.done();
         }

      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"},
         k = 1,
         mv = {1, 1, 16}
      )
      private final class BottomUpDirectoryState extends FileTreeWalk.DirectoryState {
         private boolean failed;
         private int fileIndex;
         private File[] fileList;
         private boolean rootVisited;

         public BottomUpDirectoryState(File var2) {
            Intrinsics.checkParameterIsNotNull(var2, "rootDir");
            super(var2);
         }

         public File step() {
            Function1 var1;
            File[] var3;
            Unit var5;
            if (!this.failed && this.fileList == null) {
               var1 = FileTreeWalk.this.onEnter;
               if (var1 != null && !(Boolean)var1.invoke(this.getRoot())) {
                  return null;
               }

               var3 = this.getRoot().listFiles();
               this.fileList = var3;
               if (var3 == null) {
                  Function2 var4 = FileTreeWalk.this.onFail;
                  if (var4 != null) {
                     var5 = (Unit)var4.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                  }

                  this.failed = true;
               }
            }

            var3 = this.fileList;
            if (var3 != null) {
               int var2 = this.fileIndex;
               if (var3 == null) {
                  Intrinsics.throwNpe();
               }

               if (var2 < var3.length) {
                  var3 = this.fileList;
                  if (var3 == null) {
                     Intrinsics.throwNpe();
                  }

                  var2 = this.fileIndex++;
                  return var3[var2];
               }
            }

            if (!this.rootVisited) {
               this.rootVisited = true;
               return this.getRoot();
            } else {
               var1 = FileTreeWalk.this.onLeave;
               if (var1 != null) {
                  var5 = (Unit)var1.invoke(this.getRoot());
               }

               return null;
            }
         }
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"},
         k = 1,
         mv = {1, 1, 16}
      )
      private final class SingleFileState extends FileTreeWalk.WalkState {
         private boolean visited;

         public SingleFileState(File var2) {
            Intrinsics.checkParameterIsNotNull(var2, "rootFile");
            super(var2);
            if (_Assertions.ENABLED) {
               boolean var3 = var2.isFile();
               if (_Assertions.ENABLED && !var3) {
                  throw (Throwable)(new AssertionError("rootFile must be verified to be file beforehand."));
               }
            }

         }

         public File step() {
            if (this.visited) {
               return null;
            } else {
               this.visited = true;
               return this.getRoot();
            }
         }
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"},
         k = 1,
         mv = {1, 1, 16}
      )
      private final class TopDownDirectoryState extends FileTreeWalk.DirectoryState {
         private int fileIndex;
         private File[] fileList;
         private boolean rootVisited;

         public TopDownDirectoryState(File var2) {
            Intrinsics.checkParameterIsNotNull(var2, "rootDir");
            super(var2);
         }

         public File step() {
            Function1 var5;
            if (!this.rootVisited) {
               var5 = FileTreeWalk.this.onEnter;
               if (var5 != null && !(Boolean)var5.invoke(this.getRoot())) {
                  return null;
               } else {
                  this.rootVisited = true;
                  return this.getRoot();
               }
            } else {
               File[] var1 = this.fileList;
               int var2;
               Unit var4;
               if (var1 != null) {
                  var2 = this.fileIndex;
                  if (var1 == null) {
                     Intrinsics.throwNpe();
                  }

                  if (var2 >= var1.length) {
                     var5 = FileTreeWalk.this.onLeave;
                     if (var5 != null) {
                        var4 = (Unit)var5.invoke(this.getRoot());
                     }

                     return null;
                  }
               }

               label61: {
                  if (this.fileList == null) {
                     var1 = this.getRoot().listFiles();
                     this.fileList = var1;
                     if (var1 == null) {
                        Function2 var3 = FileTreeWalk.this.onFail;
                        if (var3 != null) {
                           var4 = (Unit)var3.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                        }
                     }

                     var1 = this.fileList;
                     if (var1 == null) {
                        break label61;
                     }

                     if (var1 == null) {
                        Intrinsics.throwNpe();
                     }

                     if (var1.length == 0) {
                        break label61;
                     }
                  }

                  var1 = this.fileList;
                  if (var1 == null) {
                     Intrinsics.throwNpe();
                  }

                  var2 = this.fileIndex++;
                  return var1[var2];
               }

               var5 = FileTreeWalk.this.onLeave;
               if (var5 != null) {
                  var4 = (Unit)var5.invoke(this.getRoot());
               }

               return null;
            }
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"},
      d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private abstract static class WalkState {
      private final File root;

      public WalkState(File var1) {
         Intrinsics.checkParameterIsNotNull(var1, "root");
         super();
         this.root = var1;
      }

      public final File getRoot() {
         return this.root;
      }

      public abstract File step();
   }
}
