/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Closer;
import com.google.common.io.FileWriteMode;
import com.google.common.io.LineProcessor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Files {
    private static final SuccessorsFunction<File> FILE_TREE;
    private static final TreeTraverser<File> FILE_TREE_TRAVERSER;
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    static {
        FILE_TREE_TRAVERSER = new TreeTraverser<File>(){

            @Override
            public Iterable<File> children(File file) {
                return Files.fileTreeChildren(file);
            }

            public String toString() {
                return "Files.fileTreeTraverser()";
            }
        };
        FILE_TREE = new SuccessorsFunction<File>(){

            @Override
            public Iterable<File> successors(File file) {
                return Files.fileTreeChildren(file);
            }
        };
    }

    private Files() {
    }

    @Deprecated
    public static void append(CharSequence charSequence, File file, Charset charset) throws IOException {
        Files.asCharSink(file, charset, FileWriteMode.APPEND).write(charSequence);
    }

    public static ByteSink asByteSink(File file, FileWriteMode ... arrfileWriteMode) {
        return new FileByteSink(file, arrfileWriteMode);
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode ... arrfileWriteMode) {
        return Files.asByteSink(file, arrfileWriteMode).asCharSink(charset);
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return Files.asByteSource(file).asCharSource(charset);
    }

    public static void copy(File file, File file2) throws IOException {
        Preconditions.checkArgument(file.equals(file2) ^ true, "Source %s and destination %s must be different", (Object)file, (Object)file2);
        Files.asByteSource(file).copyTo(Files.asByteSink(file2, new FileWriteMode[0]));
    }

    public static void copy(File file, OutputStream outputStream2) throws IOException {
        Files.asByteSource(file).copyTo(outputStream2);
    }

    @Deprecated
    public static void copy(File file, Charset charset, Appendable appendable) throws IOException {
        Files.asCharSource(file, charset).copyTo(appendable);
    }

    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        Comparable<File> comparable = file.getCanonicalFile().getParentFile();
        if (comparable == null) {
            return;
        }
        ((File)comparable).mkdirs();
        if (((File)comparable).isDirectory()) {
            return;
        }
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append("Unable to create parent directories of ");
        ((StringBuilder)comparable).append(file);
        throw new IOException(((StringBuilder)comparable).toString());
    }

    public static File createTempDir() {
        Comparable<File> comparable = new File(System.getProperty("java.io.tmpdir"));
        CharSequence charSequence = new StringBuilder();
        charSequence.append(System.currentTimeMillis());
        charSequence.append("-");
        charSequence = charSequence.toString();
        int n = 0;
        do {
            if (n >= 10000) {
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append("Failed to create directory within 10000 attempts (tried ");
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append("0 to ");
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append(9999);
                ((StringBuilder)comparable).append(')');
                throw new IllegalStateException(((StringBuilder)comparable).toString());
            }
            Comparable<StringBuilder> comparable2 = new StringBuilder();
            ((StringBuilder)comparable2).append((String)charSequence);
            ((StringBuilder)comparable2).append(n);
            comparable2 = new File((File)comparable, ((StringBuilder)comparable2).toString());
            if (((File)comparable2).mkdir()) {
                return comparable2;
            }
            ++n;
        } while (true);
    }

    public static boolean equal(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2) return true;
        if (file.equals(file2)) {
            return true;
        }
        long l = file.length();
        long l2 = file2.length();
        if (l == 0L) return Files.asByteSource(file).contentEquals(Files.asByteSource(file2));
        if (l2 == 0L) return Files.asByteSource(file).contentEquals(Files.asByteSource(file2));
        if (l == l2) return Files.asByteSource(file).contentEquals(Files.asByteSource(file2));
        return false;
    }

    public static Traverser<File> fileTraverser() {
        return Traverser.forTree(FILE_TREE);
    }

    private static Iterable<File> fileTreeChildren(File arrfile) {
        if (!arrfile.isDirectory()) return Collections.emptyList();
        if ((arrfile = arrfile.listFiles()) == null) return Collections.emptyList();
        return Collections.unmodifiableList(Arrays.asList(arrfile));
    }

    @Deprecated
    static TreeTraverser<File> fileTreeTraverser() {
        return FILE_TREE_TRAVERSER;
    }

    public static String getFileExtension(String string2) {
        Preconditions.checkNotNull(string2);
        string2 = new File(string2).getName();
        int n = string2.lastIndexOf(46);
        if (n != -1) return string2.substring(n + 1);
        return "";
    }

    public static String getNameWithoutExtension(String string2) {
        Preconditions.checkNotNull(string2);
        string2 = new File(string2).getName();
        int n = string2.lastIndexOf(46);
        if (n != -1) return string2.substring(0, n);
        return string2;
    }

    @Deprecated
    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return Files.asByteSource(file).hash(hashFunction);
    }

    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }

    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return Files.map(file, FileChannel.MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode) throws IOException {
        return Files.mapInternal(file, mapMode, -1L);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode, long l) throws IOException {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "size (%s) may not be negative", l);
        return Files.mapInternal(file, mapMode, l);
    }

    private static MappedByteBuffer mapInternal(File object, FileChannel.MapMode mapMode, long l) throws IOException {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(mapMode);
        Closer closer = Closer.create();
        try {
            String string2 = mapMode == FileChannel.MapMode.READ_ONLY ? "r" : "rw";
            RandomAccessFile randomAccessFile = new RandomAccessFile((File)object, string2);
            object = closer.register(closer.register(randomAccessFile).getChannel());
            long l2 = l;
            if (l == -1L) {
                l2 = ((FileChannel)object).size();
            }
            object = ((FileChannel)object).map(mapMode, 0L, l2);
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return object;
    }

    public static void move(File comparable, File comparable2) throws IOException {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        Preconditions.checkArgument(((File)comparable).equals(comparable2) ^ true, "Source %s and destination %s must be different", (Object)comparable, (Object)comparable2);
        if (((File)comparable).renameTo((File)comparable2)) return;
        Files.copy((File)comparable, (File)comparable2);
        if (((File)comparable).delete()) return;
        if (!((File)comparable2).delete()) {
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append("Unable to delete ");
            ((StringBuilder)comparable).append(comparable2);
            throw new IOException(((StringBuilder)comparable).toString());
        }
        comparable2 = new StringBuilder();
        ((StringBuilder)comparable2).append("Unable to delete ");
        ((StringBuilder)comparable2).append(comparable);
        throw new IOException(((StringBuilder)comparable2).toString());
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset));
    }

    @Deprecated
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) throws IOException {
        return Files.asByteSource(file).read(byteProcessor);
    }

    @Deprecated
    public static String readFirstLine(File file, Charset charset) throws IOException {
        return Files.asCharSource(file, charset).readFirstLine();
    }

    @Deprecated
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return Files.asCharSource(file, charset).readLines(lineProcessor);
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        return Files.asCharSource(file, charset).readLines(new LineProcessor<List<String>>(){
            final List<String> result = Lists.newArrayList();

            @Override
            public List<String> getResult() {
                return this.result;
            }

            @Override
            public boolean processLine(String string2) {
                this.result.add(string2);
                return true;
            }
        });
    }

    public static String simplifyPath(String object) {
        Preconditions.checkNotNull(object);
        int n = ((String)object).length();
        String string2 = ".";
        if (n == 0) {
            return ".";
        }
        Object object2 = Splitter.on('/').omitEmptyStrings().split((CharSequence)object);
        Object object3 = new ArrayList();
        object2 = object2.iterator();
        do {
            String string3;
            block12 : {
                block11 : {
                    block10 : {
                        boolean bl = object2.hasNext();
                        n = 0;
                        if (!bl) break;
                        string3 = (String)object2.next();
                        int n2 = string3.hashCode();
                        if (n2 == 46) break block10;
                        if (n2 != 1472 || !string3.equals("..")) break block11;
                        n = 1;
                        break block12;
                    }
                    if (string3.equals(".")) break block12;
                }
                n = -1;
            }
            if (n == 0) continue;
            if (n != 1) {
                object3.add(string3);
                continue;
            }
            if (object3.size() > 0 && !((String)object3.get(object3.size() - 1)).equals("..")) {
                object3.remove(object3.size() - 1);
                continue;
            }
            object3.add("..");
        } while (true);
        object3 = object2 = Joiner.on('/').join((Iterable<?>)object3);
        if (((String)object).charAt(0) == '/') {
            object = new StringBuilder();
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append((String)object2);
            object3 = ((StringBuilder)object).toString();
        }
        while (((String)object3).startsWith("/../")) {
            object3 = ((String)object3).substring(3);
        }
        if (((String)object3).equals("/..")) {
            return "/";
        }
        if (!"".equals(object3)) return object3;
        return string2;
    }

    public static byte[] toByteArray(File file) throws IOException {
        return Files.asByteSource(file).read();
    }

    @Deprecated
    public static String toString(File file, Charset charset) throws IOException {
        return Files.asCharSource(file, charset).read();
    }

    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (file.createNewFile()) return;
        if (file.setLastModified(System.currentTimeMillis())) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to update modification time of ");
        stringBuilder.append(file);
        throw new IOException(stringBuilder.toString());
    }

    @Deprecated
    public static void write(CharSequence charSequence, File file, Charset charset) throws IOException {
        Files.asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    public static void write(byte[] arrby, File file) throws IOException {
        Files.asByteSink(file, new FileWriteMode[0]).write(arrby);
    }

    private static final class FileByteSink
    extends ByteSink {
        private final File file;
        private final ImmutableSet<FileWriteMode> modes;

        private FileByteSink(File file, FileWriteMode ... arrfileWriteMode) {
            this.file = Preconditions.checkNotNull(file);
            this.modes = ImmutableSet.copyOf(arrfileWriteMode);
        }

        @Override
        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains((Object)FileWriteMode.APPEND));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Files.asByteSink(");
            stringBuilder.append(this.file);
            stringBuilder.append(", ");
            stringBuilder.append(this.modes);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class FileByteSource
    extends ByteSource {
        private final File file;

        private FileByteSource(File file) {
            this.file = Preconditions.checkNotNull(file);
        }

        @Override
        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }

        @Override
        public byte[] read() throws IOException {
            byte[] arrby;
            Closer closer = Closer.create();
            try {
                arrby = closer.register(this.openStream());
                arrby = ByteStreams.toByteArray((InputStream)arrby, arrby.getChannel().size());
            }
            catch (Throwable throwable) {
                try {
                    throw closer.rethrow(throwable);
                }
                catch (Throwable throwable2) {
                    closer.close();
                    throw throwable2;
                }
            }
            closer.close();
            return arrby;
        }

        @Override
        public long size() throws IOException {
            if (!this.file.isFile()) throw new FileNotFoundException(this.file.toString());
            return this.file.length();
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            if (!this.file.isFile()) return Optional.absent();
            return Optional.of(this.file.length());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Files.asByteSource(");
            stringBuilder.append(this.file);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static abstract class FilePredicate
    extends Enum<FilePredicate>
    implements Predicate<File> {
        private static final /* synthetic */ FilePredicate[] $VALUES;
        public static final /* enum */ FilePredicate IS_DIRECTORY;
        public static final /* enum */ FilePredicate IS_FILE;

        static {
            FilePredicate filePredicate;
            IS_DIRECTORY = new FilePredicate(){

                @Override
                public boolean apply(File file) {
                    return file.isDirectory();
                }

                public String toString() {
                    return "Files.isDirectory()";
                }
            };
            IS_FILE = filePredicate = new FilePredicate(){

                @Override
                public boolean apply(File file) {
                    return file.isFile();
                }

                public String toString() {
                    return "Files.isFile()";
                }
            };
            $VALUES = new FilePredicate[]{IS_DIRECTORY, filePredicate};
        }

        public static FilePredicate valueOf(String string2) {
            return Enum.valueOf(FilePredicate.class, string2);
        }

        public static FilePredicate[] values() {
            return (FilePredicate[])$VALUES.clone();
        }

    }

}

