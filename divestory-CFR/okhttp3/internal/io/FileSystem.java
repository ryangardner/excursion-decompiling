/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.io.FileSystem$Companion$SYSTEM
 */
package okhttp3.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okhttp3.internal.io.FileSystem;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H&\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u0015"}, d2={"Lokhttp3/internal/io/FileSystem;", "", "appendingSink", "Lokio/Sink;", "file", "Ljava/io/File;", "delete", "", "deleteContents", "directory", "exists", "", "rename", "from", "to", "sink", "size", "", "source", "Lokio/Source;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public interface FileSystem {
    public static final Companion Companion = new Companion(null);
    public static final FileSystem SYSTEM = new FileSystem(){

        public Sink appendingSink(File object) throws FileNotFoundException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(object, "file");
            try {
                Sink sink2 = okio.Okio.appendingSink((File)object);
                return sink2;
            }
            catch (FileNotFoundException fileNotFoundException) {
                ((File)object).getParentFile().mkdirs();
                return okio.Okio.appendingSink((File)object);
            }
        }

        public void delete(File file) throws IOException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file, "file");
            if (file.delete()) return;
            if (!file.exists()) {
                return;
            }
            java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
            stringBuilder.append("failed to delete ");
            stringBuilder.append(file);
            throw (java.lang.Throwable)new IOException(stringBuilder.toString());
        }

        public void deleteContents(File object) throws IOException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(object, "directory");
            Object object2 = ((File)object).listFiles();
            if (object2 == null) {
                object2 = new java.lang.StringBuilder();
                ((java.lang.StringBuilder)object2).append("not a readable directory: ");
                ((java.lang.StringBuilder)object2).append(object);
                throw (java.lang.Throwable)new IOException(((java.lang.StringBuilder)object2).toString());
            }
            int n = ((File[])object2).length;
            int n2 = 0;
            while (n2 < n) {
                object = object2[n2];
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(object, "file");
                if (((File)object).isDirectory()) {
                    this.deleteContents((File)object);
                }
                if (!((File)object).delete()) {
                    object2 = new java.lang.StringBuilder();
                    ((java.lang.StringBuilder)object2).append("failed to delete ");
                    ((java.lang.StringBuilder)object2).append(object);
                    throw (java.lang.Throwable)new IOException(((java.lang.StringBuilder)object2).toString());
                }
                ++n2;
            }
        }

        public boolean exists(File file) {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file, "file");
            return file.exists();
        }

        public void rename(File file, File file2) throws IOException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file, "from");
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file2, "to");
            this.delete(file2);
            if (file.renameTo(file2)) {
                return;
            }
            java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
            stringBuilder.append("failed to rename ");
            stringBuilder.append(file);
            stringBuilder.append(" to ");
            stringBuilder.append(file2);
            throw (java.lang.Throwable)new IOException(stringBuilder.toString());
        }

        public Sink sink(File object) throws FileNotFoundException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(object, "file");
            try {
                Sink sink2 = okio.Okio.sink$default((File)object, false, 1, null);
                return sink2;
            }
            catch (FileNotFoundException fileNotFoundException) {
                ((File)object).getParentFile().mkdirs();
                return okio.Okio.sink$default((File)object, false, 1, null);
            }
        }

        public long size(File file) {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file, "file");
            return file.length();
        }

        public Source source(File file) throws FileNotFoundException {
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(file, "file");
            return okio.Okio.source(file);
        }

        public java.lang.String toString() {
            return "FileSystem.SYSTEM";
        }
    };

    public Sink appendingSink(File var1) throws FileNotFoundException;

    public void delete(File var1) throws IOException;

    public void deleteContents(File var1) throws IOException;

    public boolean exists(File var1);

    public void rename(File var1, File var2) throws IOException;

    public Sink sink(File var1) throws FileNotFoundException;

    public long size(File var1);

    public Source source(File var1) throws FileNotFoundException;

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0001\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/io/FileSystem$Companion;", "", "()V", "SYSTEM", "Lokhttp3/internal/io/FileSystem;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

