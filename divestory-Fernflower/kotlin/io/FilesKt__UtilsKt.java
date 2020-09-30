package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"},
   d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/io/FilesKt"
)
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
   public FilesKt__UtilsKt() {
   }

   public static final boolean copyRecursively(File var0, File var1, boolean var2, final Function2<? super File, ? super IOException, ? extends OnErrorAction> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$copyRecursively");
      Intrinsics.checkParameterIsNotNull(var1, "target");
      Intrinsics.checkParameterIsNotNull(var3, "onError");
      boolean var4 = var0.exists();
      boolean var5 = true;
      if (!var4) {
         if ((OnErrorAction)var3.invoke(var0, new NoSuchFileException(var0, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null)) != OnErrorAction.TERMINATE) {
            var2 = var5;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         boolean var10001;
         Iterator var21;
         try {
            FileTreeWalk var6 = FilesKt.walkTopDown(var0);
            Function2 var7 = new Function2<File, IOException, Unit>() {
               public final void invoke(File var1, IOException var2) {
                  Intrinsics.checkParameterIsNotNull(var1, "f");
                  Intrinsics.checkParameterIsNotNull(var2, "e");
                  if ((OnErrorAction)var3.invoke(var1, var2) == OnErrorAction.TERMINATE) {
                     throw (Throwable)(new TerminateException(var1));
                  }
               }
            };
            var21 = var6.onFail((Function2)var7).iterator();
         } catch (TerminateException var18) {
            var10001 = false;
            return false;
         }

         while(true) {
            File var19;
            try {
               while(true) {
                  if (!var21.hasNext()) {
                     return true;
                  }

                  var19 = (File)var21.next();
                  if (var19.exists()) {
                     break;
                  }

                  NoSuchFileException var8 = new NoSuchFileException(var19, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null);
                  if ((OnErrorAction)var3.invoke(var19, var8) == OnErrorAction.TERMINATE) {
                     return false;
                  }
               }
            } catch (TerminateException var13) {
               var10001 = false;
               break;
            }

            File var22;
            label119: {
               try {
                  String var9 = FilesKt.toRelativeString(var19, var0);
                  var22 = new File(var1, var9);
                  if (!var22.exists() || var19.isDirectory() && var22.isDirectory()) {
                     break label119;
                  }
               } catch (TerminateException var17) {
                  var10001 = false;
                  break;
               }

               boolean var10;
               label96: {
                  label124: {
                     if (var2) {
                        label123: {
                           try {
                              if (var22.isDirectory()) {
                                 if (FilesKt.deleteRecursively(var22)) {
                                    break label124;
                                 }
                                 break label123;
                              }
                           } catch (TerminateException var16) {
                              var10001 = false;
                              break;
                           }

                           try {
                              if (var22.delete()) {
                                 break label124;
                              }
                           } catch (TerminateException var15) {
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     var10 = true;
                     break label96;
                  }

                  var10 = false;
               }

               if (var10) {
                  try {
                     FileAlreadyExistsException var25 = new FileAlreadyExistsException(var19, var22, "The destination file already exists.");
                     if ((OnErrorAction)var3.invoke(var22, var25) == OnErrorAction.TERMINATE) {
                        return false;
                     }
                     continue;
                  } catch (TerminateException var11) {
                     var10001 = false;
                     break;
                  }
               }
            }

            try {
               if (var19.isDirectory()) {
                  var22.mkdirs();
                  continue;
               }
            } catch (TerminateException var14) {
               var10001 = false;
               break;
            }

            OnErrorAction var20;
            OnErrorAction var24;
            try {
               if (FilesKt.copyTo$default(var19, var22, var2, 0, 4, (Object)null).length() == var19.length()) {
                  continue;
               }

               IOException var23 = new IOException("Source file wasn't copied completely, length of destination file differs.");
               var20 = (OnErrorAction)var3.invoke(var19, var23);
               var24 = OnErrorAction.TERMINATE;
            } catch (TerminateException var12) {
               var10001 = false;
               break;
            }

            if (var20 == var24) {
               return false;
            }
         }

         return false;
      }
   }

   // $FF: synthetic method
   public static boolean copyRecursively$default(File var0, File var1, boolean var2, Function2 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = (Function2)null.INSTANCE;
      }

      return FilesKt.copyRecursively(var0, var1, var2, var3);
   }

   public static final File copyTo(File var0, File var1, boolean var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(var1, "target");
      if (var0.exists()) {
         if (var1.exists()) {
            if (!var2) {
               throw (Throwable)(new FileAlreadyExistsException(var0, var1, "The destination file already exists."));
            }

            if (!var1.delete()) {
               throw (Throwable)(new FileAlreadyExistsException(var0, var1, "Tried to overwrite the destination, but failed to delete it."));
            }
         }

         if (var0.isDirectory()) {
            if (!var1.mkdirs()) {
               throw (Throwable)(new FileSystemException(var0, var1, "Failed to create target directory."));
            }
         } else {
            File var4 = var1.getParentFile();
            if (var4 != null) {
               var4.mkdirs();
            }

            Closeable var51 = (Closeable)(new FileInputStream(var0));
            Throwable var5 = (Throwable)null;

            label491: {
               Throwable var10000;
               label492: {
                  FileInputStream var6;
                  Throwable var7;
                  boolean var10001;
                  Closeable var54;
                  try {
                     var6 = (FileInputStream)var51;
                     FileOutputStream var53 = new FileOutputStream(var1);
                     var54 = (Closeable)var53;
                     var7 = (Throwable)null;
                  } catch (Throwable var50) {
                     var10000 = var50;
                     var10001 = false;
                     break label492;
                  }

                  try {
                     FileOutputStream var8 = (FileOutputStream)var54;
                     ByteStreamsKt.copyTo((InputStream)var6, (OutputStream)var8, var3);
                  } catch (Throwable var49) {
                     Throwable var52 = var49;

                     try {
                        throw var52;
                     } finally {
                        try {
                           CloseableKt.closeFinally(var54, var52);
                        } catch (Throwable var46) {
                           var10000 = var46;
                           var10001 = false;
                           break label492;
                        }
                     }
                  }

                  label472:
                  try {
                     CloseableKt.closeFinally(var54, var7);
                     break label491;
                  } catch (Throwable var48) {
                     var10000 = var48;
                     var10001 = false;
                     break label472;
                  }
               }

               Throwable var55 = var10000;

               try {
                  throw var55;
               } finally {
                  CloseableKt.closeFinally(var51, var55);
               }
            }

            CloseableKt.closeFinally(var51, var5);
         }

         return var1;
      } else {
         throw (Throwable)(new NoSuchFileException(var0, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null));
      }
   }

   // $FF: synthetic method
   public static File copyTo$default(File var0, File var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 8192;
      }

      return FilesKt.copyTo(var0, var1, var2, var3);
   }

   public static final File createTempDir(String var0, String var1, File var2) {
      Intrinsics.checkParameterIsNotNull(var0, "prefix");
      File var3 = File.createTempFile(var0, var1, var2);
      var3.delete();
      if (var3.mkdir()) {
         Intrinsics.checkExpressionValueIsNotNull(var3, "dir");
         return var3;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Unable to create temporary directory ");
         var4.append(var3);
         var4.append('.');
         throw (Throwable)(new IOException(var4.toString()));
      }
   }

   // $FF: synthetic method
   public static File createTempDir$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempDir(var0, var1, var2);
   }

   public static final File createTempFile(String var0, String var1, File var2) {
      Intrinsics.checkParameterIsNotNull(var0, "prefix");
      File var3 = File.createTempFile(var0, var1, var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "File.createTempFile(prefix, suffix, directory)");
      return var3;
   }

   // $FF: synthetic method
   public static File createTempFile$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempFile(var0, var1, var2);
   }

   public static final boolean deleteRecursively(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$deleteRecursively");
      Iterator var1 = ((Sequence)FilesKt.walkBottomUp(var0)).iterator();

      label24:
      while(true) {
         boolean var2;
         for(var2 = true; var1.hasNext(); var2 = false) {
            var0 = (File)var1.next();
            if ((var0.delete() || !var0.exists()) && var2) {
               continue label24;
            }
         }

         return var2;
      }
   }

   public static final boolean endsWith(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      FilePathComponents var2 = FilesKt.toComponents(var0);
      FilePathComponents var3 = FilesKt.toComponents(var1);
      if (var3.isRooted()) {
         return Intrinsics.areEqual((Object)var0, (Object)var1);
      } else {
         int var4 = var2.getSize() - var3.getSize();
         boolean var5;
         if (var4 < 0) {
            var5 = false;
         } else {
            var5 = var2.getSegments().subList(var4, var2.getSize()).equals(var3.getSegments());
         }

         return var5;
      }
   }

   public static final boolean endsWith(File var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return FilesKt.endsWith(var0, new File(var1));
   }

   public static final String getExtension(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$extension");
      String var1 = var0.getName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "name");
      return StringsKt.substringAfterLast(var1, '.', "");
   }

   public static final String getInvariantSeparatorsPath(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$invariantSeparatorsPath");
      String var1;
      if (File.separatorChar != '/') {
         var1 = var0.getPath();
         Intrinsics.checkExpressionValueIsNotNull(var1, "path");
         var1 = StringsKt.replace$default(var1, File.separatorChar, '/', false, 4, (Object)null);
      } else {
         var1 = var0.getPath();
         Intrinsics.checkExpressionValueIsNotNull(var1, "path");
      }

      return var1;
   }

   public static final String getNameWithoutExtension(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$nameWithoutExtension");
      String var1 = var0.getName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "name");
      return StringsKt.substringBeforeLast$default(var1, ".", (String)null, 2, (Object)null);
   }

   public static final File normalize(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$normalize");
      FilePathComponents var1 = FilesKt.toComponents(var0);
      var0 = var1.getRoot();
      Iterable var3 = (Iterable)normalize$FilesKt__UtilsKt(var1.getSegments());
      String var2 = File.separator;
      Intrinsics.checkExpressionValueIsNotNull(var2, "File.separator");
      return FilesKt.resolve(var0, CollectionsKt.joinToString$default(var3, (CharSequence)var2, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
   }

   private static final List<File> normalize$FilesKt__UtilsKt(List<? extends File> var0) {
      List var1 = (List)(new ArrayList(var0.size()));
      Iterator var2 = var0.iterator();

      while(true) {
         while(true) {
            while(var2.hasNext()) {
               File var3 = (File)var2.next();
               String var5 = var3.getName();
               if (var5 != null) {
                  int var4 = var5.hashCode();
                  if (var4 != 46) {
                     if (var4 == 1472 && var5.equals("..")) {
                        if (!var1.isEmpty() && Intrinsics.areEqual((Object)((File)CollectionsKt.last(var1)).getName(), (Object)"..") ^ true) {
                           var1.remove(var1.size() - 1);
                           continue;
                        }

                        var1.add(var3);
                        continue;
                     }
                  } else if (var5.equals(".")) {
                     continue;
                  }
               }

               var1.add(var3);
            }

            return var1;
         }
      }
   }

   private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents var0) {
      return new FilePathComponents(var0.getRoot(), normalize$FilesKt__UtilsKt(var0.getSegments()));
   }

   public static final File relativeTo(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$relativeTo");
      Intrinsics.checkParameterIsNotNull(var1, "base");
      return new File(FilesKt.toRelativeString(var0, var1));
   }

   public static final File relativeToOrNull(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$relativeToOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "base");
      String var2 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         var0 = new File(var2);
      } else {
         var0 = null;
      }

      return var0;
   }

   public static final File relativeToOrSelf(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$relativeToOrSelf");
      Intrinsics.checkParameterIsNotNull(var1, "base");
      String var2 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         var0 = new File(var2);
      }

      return var0;
   }

   public static final File resolve(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$resolve");
      Intrinsics.checkParameterIsNotNull(var1, "relative");
      if (FilesKt.isRooted(var1)) {
         return var1;
      } else {
         String var4 = var0.toString();
         Intrinsics.checkExpressionValueIsNotNull(var4, "this.toString()");
         CharSequence var2 = (CharSequence)var4;
         boolean var3;
         if (var2.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         StringBuilder var5;
         if (!var3 && !StringsKt.endsWith$default(var2, File.separatorChar, false, 2, (Object)null)) {
            var5 = new StringBuilder();
            var5.append(var4);
            var5.append(File.separatorChar);
            var5.append(var1);
            var0 = new File(var5.toString());
         } else {
            var5 = new StringBuilder();
            var5.append(var4);
            var5.append(var1);
            var0 = new File(var5.toString());
         }

         return var0;
      }
   }

   public static final File resolve(File var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$resolve");
      Intrinsics.checkParameterIsNotNull(var1, "relative");
      return FilesKt.resolve(var0, new File(var1));
   }

   public static final File resolveSibling(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$resolveSibling");
      Intrinsics.checkParameterIsNotNull(var1, "relative");
      FilePathComponents var2 = FilesKt.toComponents(var0);
      if (var2.getSize() == 0) {
         var0 = new File("..");
      } else {
         var0 = var2.subPath(0, var2.getSize() - 1);
      }

      return FilesKt.resolve(FilesKt.resolve(var2.getRoot(), var0), var1);
   }

   public static final File resolveSibling(File var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$resolveSibling");
      Intrinsics.checkParameterIsNotNull(var1, "relative");
      return FilesKt.resolveSibling(var0, new File(var1));
   }

   public static final boolean startsWith(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      FilePathComponents var4 = FilesKt.toComponents(var0);
      FilePathComponents var5 = FilesKt.toComponents(var1);
      boolean var2 = Intrinsics.areEqual((Object)var4.getRoot(), (Object)var5.getRoot());
      boolean var3 = false;
      if (var2 ^ true) {
         return false;
      } else {
         if (var4.getSize() >= var5.getSize()) {
            var3 = var4.getSegments().subList(0, var5.getSize()).equals(var5.getSegments());
         }

         return var3;
      }
   }

   public static final boolean startsWith(File var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return FilesKt.startsWith(var0, new File(var1));
   }

   public static final String toRelativeString(File var0, File var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toRelativeString");
      Intrinsics.checkParameterIsNotNull(var1, "base");
      String var2 = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("this and base files have different roots: ");
         var3.append(var0);
         var3.append(" and ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File var0, File var1) {
      FilePathComponents var2 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var0));
      FilePathComponents var9 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var1));
      if (Intrinsics.areEqual((Object)var2.getRoot(), (Object)var9.getRoot()) ^ true) {
         return null;
      } else {
         int var3 = var9.getSize();
         int var4 = var2.getSize();
         int var5 = 0;

         int var6;
         for(var6 = Math.min(var4, var3); var5 < var6 && Intrinsics.areEqual((Object)((File)var2.getSegments().get(var5)), (Object)((File)var9.getSegments().get(var5))); ++var5) {
         }

         StringBuilder var8 = new StringBuilder();
         var6 = var3 - 1;
         if (var6 >= var5) {
            label55: {
               while(!Intrinsics.areEqual((Object)((File)var9.getSegments().get(var6)).getName(), (Object)"..")) {
                  var8.append("..");
                  if (var6 != var5) {
                     var8.append(File.separatorChar);
                  }

                  if (var6 == var5) {
                     break label55;
                  }

                  --var6;
               }

               return null;
            }
         }

         if (var5 < var4) {
            if (var5 < var3) {
               var8.append(File.separatorChar);
            }

            Iterable var11 = (Iterable)CollectionsKt.drop((Iterable)var2.getSegments(), var5);
            Appendable var10 = (Appendable)var8;
            String var7 = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(var7, "File.separator");
            CollectionsKt.joinTo$default(var11, var10, (CharSequence)var7, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null);
         }

         return var8.toString();
      }
   }
}
