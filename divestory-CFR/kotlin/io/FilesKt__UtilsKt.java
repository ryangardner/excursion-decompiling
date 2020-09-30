/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.io.FilesKt__UtilsKt$copyRecursively
 */
package kotlin.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FileAlreadyExistsException;
import kotlin.io.FilePathComponents;
import kotlin.io.FileTreeWalk;
import kotlin.io.FilesKt;
import kotlin.io.FilesKt__FileTreeWalkKt;
import kotlin.io.FilesKt__UtilsKt;
import kotlin.io.NoSuchFileException;
import kotlin.io.OnErrorAction;
import kotlin.io.TerminateException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0004\u00a8\u0006+"}, d2={"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/io/FilesKt")
class FilesKt__UtilsKt
extends FilesKt__FileTreeWalkKt {
    public static final boolean copyRecursively(File file, File file2, boolean bl, Function2<? super File, ? super IOException, ? extends OnErrorAction> function2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$copyRecursively");
        Intrinsics.checkParameterIsNotNull(file2, "target");
        Intrinsics.checkParameterIsNotNull(function2, "onError");
        boolean bl2 = file.exists();
        boolean bl3 = true;
        if (!bl2) {
            if (function2.invoke(file, new NoSuchFileException(file, null, "The source file doesn't exist.", 2, null)) == OnErrorAction.TERMINATE) return false;
            return bl3;
        }
        try {
            Object object = FilesKt.walkTopDown(file);
            Function2<File, IOException, Unit> function22 = new Function2<File, IOException, Unit>(function2){
                final /* synthetic */ Function2 $onError;
                {
                    this.$onError = function2;
                    super(2);
                }

                public final void invoke(File file, IOException iOException) {
                    Intrinsics.checkParameterIsNotNull(file, "f");
                    Intrinsics.checkParameterIsNotNull(iOException, "e");
                    if ((OnErrorAction)((Object)this.$onError.invoke(file, iOException)) == OnErrorAction.TERMINATE) throw (Throwable)new TerminateException(file);
                }
            };
            function22 = ((FileTreeWalk)object).onFail((Function2<? super File, ? super IOException, Unit>)function22).iterator();
            while (function22.hasNext()) {
                Object object2;
                boolean bl4;
                object = (File)function22.next();
                if (!((File)object).exists()) {
                    object2 = new NoSuchFileException((File)object, null, "The source file doesn't exist.", 2, null);
                    if (function2.invoke((File)object, (IOException)object2) != OnErrorAction.TERMINATE) continue;
                    return false;
                }
                Object object3 = FilesKt.toRelativeString((File)object, file);
                object2 = new File(file2, (String)object3);
                if (((File)object2).exists() && (!((File)object).isDirectory() || !((File)object2).isDirectory()) && (bl4 = !bl || (((File)object2).isDirectory() ? !FilesKt.deleteRecursively((File)object2) : !((File)object2).delete()))) {
                    object3 = new FileAlreadyExistsException((File)object, (File)object2, "The destination file already exists.");
                    if (function2.invoke((File)object2, (IOException)object3) != OnErrorAction.TERMINATE) continue;
                    return false;
                }
                if (((File)object).isDirectory()) {
                    ((File)object2).mkdirs();
                    continue;
                }
                if (FilesKt.copyTo$default((File)object, (File)object2, bl, 0, 4, null).length() == ((File)object).length()) continue;
                object2 = new IOException("Source file wasn't copied completely, length of destination file differs.");
                object = function2.invoke((File)object, (IOException)object2);
                object2 = OnErrorAction.TERMINATE;
                if (object == object2) return false;
            }
            return true;
        }
        catch (TerminateException terminateException) {
            return false;
        }
    }

    public static /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean bl, Function2 function2, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) == 0) return FilesKt.copyRecursively(file, file2, bl, function2);
        function2 = copyRecursively.1.INSTANCE;
        return FilesKt.copyRecursively(file, file2, bl, function2);
    }

    /*
     * Exception decompiling
     */
    public static final File copyTo(File var0, File var1_1, boolean var2_4, int var3_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public static /* synthetic */ File copyTo$default(File file, File file2, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) == 0) return FilesKt.copyTo(file, file2, bl, n);
        n = 8192;
        return FilesKt.copyTo(file, file2, bl, n);
    }

    public static final File createTempDir(String object, String charSequence, File file) {
        Intrinsics.checkParameterIsNotNull(object, "prefix");
        object = File.createTempFile((String)object, (String)charSequence, file);
        ((File)object).delete();
        if (((File)object).mkdir()) {
            Intrinsics.checkExpressionValueIsNotNull(object, "dir");
            return object;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to create temporary directory ");
        ((StringBuilder)charSequence).append(object);
        ((StringBuilder)charSequence).append('.');
        throw (Throwable)new IOException(((StringBuilder)charSequence).toString());
    }

    public static /* synthetic */ File createTempDir$default(String string2, String string3, File file, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "tmp";
        }
        if ((n & 2) != 0) {
            string3 = null;
        }
        if ((n & 4) == 0) return FilesKt.createTempDir(string2, string3, file);
        file = null;
        return FilesKt.createTempDir(string2, string3, file);
    }

    public static final File createTempFile(String object, String string2, File file) {
        Intrinsics.checkParameterIsNotNull(object, "prefix");
        object = File.createTempFile((String)object, string2, file);
        Intrinsics.checkExpressionValueIsNotNull(object, "File.createTempFile(prefix, suffix, directory)");
        return object;
    }

    public static /* synthetic */ File createTempFile$default(String string2, String string3, File file, int n, Object object) {
        if ((n & 1) != 0) {
            string2 = "tmp";
        }
        if ((n & 2) != 0) {
            string3 = null;
        }
        if ((n & 4) == 0) return FilesKt.createTempFile(string2, string3, file);
        file = null;
        return FilesKt.createTempFile(string2, string3, file);
    }

    public static final boolean deleteRecursively(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$this$deleteRecursively");
        Iterator iterator2 = ((Sequence)FilesKt.walkBottomUp(file)).iterator();
        block0 : do {
            boolean bl = true;
            while (iterator2.hasNext()) {
                file = (File)iterator2.next();
                if ((file.delete() || !file.exists()) && bl) continue block0;
                bl = false;
            }
            return bl;
            break;
        } while (true);
    }

    public static final boolean endsWith(File file, File file2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(file2, "other");
        FilePathComponents filePathComponents = FilesKt.toComponents(file);
        FilePathComponents filePathComponents2 = FilesKt.toComponents(file2);
        if (filePathComponents2.isRooted()) {
            return Intrinsics.areEqual(file, file2);
        }
        int n = filePathComponents.getSize() - filePathComponents2.getSize();
        if (n >= 0) return ((Object)filePathComponents.getSegments().subList(n, filePathComponents.getSize())).equals(filePathComponents2.getSegments());
        return false;
    }

    public static final boolean endsWith(File file, String string2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(string2, "other");
        return FilesKt.endsWith(file, new File(string2));
    }

    public static final String getExtension(File object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$extension");
        object = ((File)object).getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "name");
        return StringsKt.substringAfterLast((String)object, '.', "");
    }

    public static final String getInvariantSeparatorsPath(File object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$invariantSeparatorsPath");
        if (File.separatorChar != '/') {
            object = ((File)object).getPath();
            Intrinsics.checkExpressionValueIsNotNull(object, "path");
            return StringsKt.replace$default((String)object, File.separatorChar, '/', false, 4, null);
        }
        object = ((File)object).getPath();
        Intrinsics.checkExpressionValueIsNotNull(object, "path");
        return object;
    }

    public static final String getNameWithoutExtension(File object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$nameWithoutExtension");
        object = ((File)object).getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "name");
        return StringsKt.substringBeforeLast$default((String)object, ".", null, 2, null);
    }

    public static final File normalize(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$this$normalize");
        Object object = FilesKt.toComponents(file);
        file = ((FilePathComponents)object).getRoot();
        object = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(((FilePathComponents)object).getSegments());
        String string2 = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(string2, "File.separator");
        return FilesKt.resolve(file, CollectionsKt.joinToString$default((Iterable)object, string2, null, null, 0, null, null, 62, null));
    }

    private static final List<File> normalize$FilesKt__UtilsKt(List<? extends File> object) {
        List list = new ArrayList(object.size());
        Iterator<? extends File> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            File file = iterator2.next();
            object = file.getName();
            if (object != null) {
                int n = ((String)object).hashCode();
                if (n != 46) {
                    if (n == 1472 && ((String)object).equals("..")) {
                        if (!list.isEmpty() && Intrinsics.areEqual(((File)CollectionsKt.last(list)).getName(), "..") ^ true) {
                            list.remove(list.size() - 1);
                            continue;
                        }
                        list.add(file);
                        continue;
                    }
                } else if (((String)object).equals(".")) continue;
            }
            list.add(file);
        }
        return list;
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents filePathComponents) {
        return new FilePathComponents(filePathComponents.getRoot(), FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(filePathComponents.getSegments()));
    }

    public static final File relativeTo(File file, File file2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$relativeTo");
        Intrinsics.checkParameterIsNotNull(file2, "base");
        return new File(FilesKt.toRelativeString(file, file2));
    }

    public static final File relativeToOrNull(File object, File file) {
        Intrinsics.checkParameterIsNotNull(object, "$this$relativeToOrNull");
        Intrinsics.checkParameterIsNotNull(file, "base");
        object = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt((File)object, file);
        if (object == null) return null;
        return new File((String)object);
    }

    public static final File relativeToOrSelf(File file, File object) {
        Intrinsics.checkParameterIsNotNull(file, "$this$relativeToOrSelf");
        Intrinsics.checkParameterIsNotNull(object, "base");
        object = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt(file, (File)object);
        if (object == null) return file;
        return new File((String)object);
    }

    public static final File resolve(File object, File file) {
        Intrinsics.checkParameterIsNotNull(object, "$this$resolve");
        Intrinsics.checkParameterIsNotNull(file, "relative");
        if (FilesKt.isRooted(file)) {
            return file;
        }
        object = ((File)object).toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "this.toString()");
        CharSequence charSequence = (CharSequence)object;
        boolean bl = charSequence.length() == 0;
        if (!bl && !StringsKt.endsWith$default(charSequence, File.separatorChar, false, 2, null)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(File.separatorChar);
            ((StringBuilder)charSequence).append(file);
            return new File(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(file);
        return new File(((StringBuilder)charSequence).toString());
    }

    public static final File resolve(File file, String string2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$resolve");
        Intrinsics.checkParameterIsNotNull(string2, "relative");
        return FilesKt.resolve(file, new File(string2));
    }

    public static final File resolveSibling(File file, File file2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$resolveSibling");
        Intrinsics.checkParameterIsNotNull(file2, "relative");
        FilePathComponents filePathComponents = FilesKt.toComponents(file);
        if (filePathComponents.getSize() == 0) {
            file = new File("..");
            return FilesKt.resolve(FilesKt.resolve(filePathComponents.getRoot(), file), file2);
        }
        file = filePathComponents.subPath(0, filePathComponents.getSize() - 1);
        return FilesKt.resolve(FilesKt.resolve(filePathComponents.getRoot(), file), file2);
    }

    public static final File resolveSibling(File file, String string2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$resolveSibling");
        Intrinsics.checkParameterIsNotNull(string2, "relative");
        return FilesKt.resolveSibling(file, new File(string2));
    }

    public static final boolean startsWith(File object, File object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(object2, "other");
        object = FilesKt.toComponents((File)object);
        object2 = FilesKt.toComponents((File)object2);
        boolean bl = Intrinsics.areEqual(((FilePathComponents)object).getRoot(), ((FilePathComponents)object2).getRoot());
        boolean bl2 = false;
        if (bl ^ true) {
            return false;
        }
        if (((FilePathComponents)object).getSize() >= ((FilePathComponents)object2).getSize()) return ((Object)((FilePathComponents)object).getSegments().subList(0, ((FilePathComponents)object2).getSize())).equals(((FilePathComponents)object2).getSegments());
        return bl2;
    }

    public static final boolean startsWith(File file, String string2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(string2, "other");
        return FilesKt.startsWith(file, new File(string2));
    }

    public static final String toRelativeString(File file, File file2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$toRelativeString");
        Intrinsics.checkParameterIsNotNull(file2, "base");
        CharSequence charSequence = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt(file, file2);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("this and base files have different roots: ");
        ((StringBuilder)charSequence).append(file);
        ((StringBuilder)charSequence).append(" and ");
        ((StringBuilder)charSequence).append(file2);
        ((StringBuilder)charSequence).append('.');
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File comparable, File object) {
        int n;
        Object object2 = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(FilesKt.toComponents((File)comparable));
        object = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(FilesKt.toComponents((File)object));
        if (Intrinsics.areEqual(((FilePathComponents)object2).getRoot(), ((FilePathComponents)object).getRoot()) ^ true) {
            return null;
        }
        int n2 = ((FilePathComponents)object).getSize();
        int n3 = ((FilePathComponents)object2).getSize();
        int n4 = Math.min(n3, n2);
        for (n = 0; n < n4 && Intrinsics.areEqual(((FilePathComponents)object2).getSegments().get(n), ((FilePathComponents)object).getSegments().get(n)); ++n) {
        }
        comparable = new StringBuilder();
        n4 = n2 - 1;
        if (n4 >= n) {
            do {
                if (Intrinsics.areEqual(((FilePathComponents)object).getSegments().get(n4).getName(), "..")) {
                    return null;
                }
                ((StringBuilder)comparable).append("..");
                if (n4 != n) {
                    ((StringBuilder)comparable).append(File.separatorChar);
                }
                if (n4 == n) break;
                --n4;
            } while (true);
        }
        if (n >= n3) return ((StringBuilder)comparable).toString();
        if (n < n2) {
            ((StringBuilder)comparable).append(File.separatorChar);
        }
        object2 = CollectionsKt.drop((Iterable)((FilePathComponents)object2).getSegments(), n);
        object = (Appendable)((Object)comparable);
        String string2 = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(string2, "File.separator");
        CollectionsKt.joinTo$default((Iterable)object2, (Appendable)object, string2, null, null, 0, null, null, 124, null);
        return ((StringBuilder)comparable).toString();
    }
}

