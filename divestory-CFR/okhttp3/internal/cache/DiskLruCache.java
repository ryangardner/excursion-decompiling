/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.cache.DiskLruCache$Editor$newSink$
 *  okhttp3.internal.cache.DiskLruCache$Editor$newSink$$inlined
 *  okhttp3.internal.cache.DiskLruCache$Editor$newSink$$inlined$synchronized
 *  okhttp3.internal.cache.DiskLruCache$Editor$newSink$$inlined$synchronized$lambda
 *  okhttp3.internal.cache.DiskLruCache$Entry$newSource
 *  okhttp3.internal.cache.DiskLruCache$cleanupTask
 *  okhttp3.internal.cache.DiskLruCache$newJournalWriter
 *  okhttp3.internal.cache.DiskLruCache$newJournalWriter$faultHidingSink
 *  okhttp3.internal.cache.DiskLruCache$snapshots
 */
package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.DiskLruCache$Editor$newSink$;
import okhttp3.internal.cache.FaultHidingSink;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000y\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010)\n\u0002\b\u0007*\u0001\u0014\u0018\u0000 [2\u00020\u00012\u00020\u0002:\u0004[\\]^B7\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u000209H\u0016J!\u0010;\u001a\u0002092\n\u0010<\u001a\u00060=R\u00020\u00002\u0006\u0010>\u001a\u00020\u0010H\u0000\u00a2\u0006\u0002\b?J\u0006\u0010@\u001a\u000209J \u0010A\u001a\b\u0018\u00010=R\u00020\u00002\u0006\u0010B\u001a\u00020(2\b\b\u0002\u0010C\u001a\u00020\u000bH\u0007J\u0006\u0010D\u001a\u000209J\b\u0010E\u001a\u000209H\u0016J\u0017\u0010F\u001a\b\u0018\u00010GR\u00020\u00002\u0006\u0010B\u001a\u00020(H\u0086\u0002J\u0006\u0010H\u001a\u000209J\u0006\u0010I\u001a\u00020\u0010J\b\u0010J\u001a\u00020\u0010H\u0002J\b\u0010K\u001a\u00020%H\u0002J\b\u0010L\u001a\u000209H\u0002J\b\u0010M\u001a\u000209H\u0002J\u0010\u0010N\u001a\u0002092\u0006\u0010O\u001a\u00020(H\u0002J\r\u0010P\u001a\u000209H\u0000\u00a2\u0006\u0002\bQJ\u000e\u0010R\u001a\u00020\u00102\u0006\u0010B\u001a\u00020(J\u0019\u0010S\u001a\u00020\u00102\n\u0010T\u001a\u00060)R\u00020\u0000H\u0000\u00a2\u0006\u0002\bUJ\b\u0010V\u001a\u00020\u0010H\u0002J\u0006\u00105\u001a\u00020\u000bJ\u0010\u0010W\u001a\f\u0012\b\u0012\u00060GR\u00020\u00000XJ\u0006\u0010Y\u001a\u000209J\u0010\u0010Z\u001a\u0002092\u0006\u0010B\u001a\u00020(H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0010X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0003\u001a\u00020\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R$\u0010&\u001a\u0012\u0012\u0004\u0012\u00020(\u0012\b\u0012\u00060)R\u00020\u00000'X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R&\u0010\n\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000b8F@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u000e\u00101\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00107\u00a8\u0006_"}, d2={"Lokhttp3/internal/cache/DiskLruCache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "directory", "Ljava/io/File;", "appVersion", "", "valueCount", "maxSize", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "(Lokhttp3/internal/io/FileSystem;Ljava/io/File;IIJLokhttp3/internal/concurrent/TaskRunner;)V", "civilizedFileSystem", "", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/cache/DiskLruCache$cleanupTask$1", "Lokhttp3/internal/cache/DiskLruCache$cleanupTask$1;", "closed", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getDirectory", "()Ljava/io/File;", "getFileSystem$okhttp", "()Lokhttp3/internal/io/FileSystem;", "hasJournalErrors", "initialized", "journalFile", "journalFileBackup", "journalFileTmp", "journalWriter", "Lokio/BufferedSink;", "lruEntries", "Ljava/util/LinkedHashMap;", "", "Lokhttp3/internal/cache/DiskLruCache$Entry;", "getLruEntries$okhttp", "()Ljava/util/LinkedHashMap;", "value", "getMaxSize", "()J", "setMaxSize", "(J)V", "mostRecentRebuildFailed", "mostRecentTrimFailed", "nextSequenceNumber", "redundantOpCount", "size", "getValueCount$okhttp", "()I", "checkNotClosed", "", "close", "completeEdit", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "success", "completeEdit$okhttp", "delete", "edit", "key", "expectedSequenceNumber", "evictAll", "flush", "get", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "initialize", "isClosed", "journalRebuildRequired", "newJournalWriter", "processJournal", "readJournal", "readJournalLine", "line", "rebuildJournal", "rebuildJournal$okhttp", "remove", "removeEntry", "entry", "removeEntry$okhttp", "removeOldestEntry", "snapshots", "", "trimToSize", "validateKey", "Companion", "Editor", "Entry", "Snapshot", "okhttp"}, k=1, mv={1, 1, 16})
public final class DiskLruCache
implements Closeable,
Flushable {
    public static final long ANY_SEQUENCE_NUMBER = -1L;
    public static final String CLEAN = "CLEAN";
    public static final Companion Companion = new Companion(null);
    public static final String DIRTY = "DIRTY";
    public static final String JOURNAL_FILE = "journal";
    public static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    public static final String JOURNAL_FILE_TEMP = "journal.tmp";
    public static final Regex LEGAL_KEY_PATTERN;
    public static final String MAGIC = "libcore.io.DiskLruCache";
    public static final String READ = "READ";
    public static final String REMOVE = "REMOVE";
    public static final String VERSION_1 = "1";
    private final int appVersion;
    private boolean civilizedFileSystem;
    private final TaskQueue cleanupQueue;
    private final cleanupTask.1 cleanupTask;
    private boolean closed;
    private final File directory;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries;
    private long maxSize;
    private boolean mostRecentRebuildFailed;
    private boolean mostRecentTrimFailed;
    private long nextSequenceNumber;
    private int redundantOpCount;
    private long size;
    private final int valueCount;

    static {
        JOURNAL_FILE = JOURNAL_FILE;
        JOURNAL_FILE_TEMP = JOURNAL_FILE_TEMP;
        JOURNAL_FILE_BACKUP = JOURNAL_FILE_BACKUP;
        MAGIC = MAGIC;
        VERSION_1 = VERSION_1;
        ANY_SEQUENCE_NUMBER = -1L;
        LEGAL_KEY_PATTERN = new Regex("[a-z0-9_-]{1,120}");
        CLEAN = CLEAN;
        DIRTY = DIRTY;
        REMOVE = REMOVE;
        READ = READ;
    }

    public DiskLruCache(FileSystem object, File file, int n, int n2, long l, TaskRunner taskRunner) {
        Intrinsics.checkParameterIsNotNull(object, "fileSystem");
        Intrinsics.checkParameterIsNotNull(file, "directory");
        Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
        this.fileSystem = object;
        this.directory = file;
        this.appVersion = n;
        this.valueCount = n2;
        this.maxSize = l;
        n2 = 0;
        this.lruEntries = new LinkedHashMap(0, 0.75f, true);
        this.cleanupQueue = taskRunner.newQueue();
        object = new StringBuilder();
        ((StringBuilder)object).append(Util.okHttpName);
        ((StringBuilder)object).append(" Cache");
        this.cleanupTask = new Task(this, ((StringBuilder)object).toString()){
            final /* synthetic */ DiskLruCache this$0;
            {
                this.this$0 = diskLruCache;
                super(string2, false, 2, null);
            }

            public long runOnce() {
                DiskLruCache diskLruCache = this.this$0;
                synchronized (diskLruCache) {
                    if (!DiskLruCache.access$getInitialized$p(this.this$0)) return -1L;
                    boolean bl = this.this$0.getClosed$okhttp();
                    if (bl) {
                        return -1L;
                    }
                    try {
                        this.this$0.trimToSize();
                    }
                    catch (IOException iOException) {
                        DiskLruCache.access$setMostRecentTrimFailed$p(this.this$0, true);
                    }
                    try {
                        if (!DiskLruCache.access$journalRebuildRequired(this.this$0)) return -1L;
                        this.this$0.rebuildJournal$okhttp();
                        DiskLruCache.access$setRedundantOpCount$p(this.this$0, 0);
                    }
                    catch (IOException iOException) {
                        DiskLruCache.access$setMostRecentRebuildFailed$p(this.this$0, true);
                        DiskLruCache.access$setJournalWriter$p(this.this$0, Okio.buffer(Okio.blackhole()));
                    }
                    return -1L;
                }
            }
        };
        n = l > 0L ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalArgumentException("maxSize <= 0".toString());
        n = n2;
        if (this.valueCount > 0) {
            n = 1;
        }
        if (n == 0) throw (Throwable)new IllegalArgumentException("valueCount <= 0".toString());
        this.journalFile = new File(this.directory, JOURNAL_FILE);
        this.journalFileTmp = new File(this.directory, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(this.directory, JOURNAL_FILE_BACKUP);
    }

    public static final /* synthetic */ boolean access$getHasJournalErrors$p(DiskLruCache diskLruCache) {
        return diskLruCache.hasJournalErrors;
    }

    public static final /* synthetic */ boolean access$getInitialized$p(DiskLruCache diskLruCache) {
        return diskLruCache.initialized;
    }

    public static final /* synthetic */ BufferedSink access$getJournalWriter$p(DiskLruCache diskLruCache) {
        return diskLruCache.journalWriter;
    }

    public static final /* synthetic */ boolean access$getMostRecentRebuildFailed$p(DiskLruCache diskLruCache) {
        return diskLruCache.mostRecentRebuildFailed;
    }

    public static final /* synthetic */ boolean access$getMostRecentTrimFailed$p(DiskLruCache diskLruCache) {
        return diskLruCache.mostRecentTrimFailed;
    }

    public static final /* synthetic */ int access$getRedundantOpCount$p(DiskLruCache diskLruCache) {
        return diskLruCache.redundantOpCount;
    }

    public static final /* synthetic */ boolean access$journalRebuildRequired(DiskLruCache diskLruCache) {
        return diskLruCache.journalRebuildRequired();
    }

    public static final /* synthetic */ void access$setCivilizedFileSystem$p(DiskLruCache diskLruCache, boolean bl) {
        diskLruCache.civilizedFileSystem = bl;
    }

    public static final /* synthetic */ void access$setHasJournalErrors$p(DiskLruCache diskLruCache, boolean bl) {
        diskLruCache.hasJournalErrors = bl;
    }

    public static final /* synthetic */ void access$setInitialized$p(DiskLruCache diskLruCache, boolean bl) {
        diskLruCache.initialized = bl;
    }

    public static final /* synthetic */ void access$setJournalWriter$p(DiskLruCache diskLruCache, BufferedSink bufferedSink) {
        diskLruCache.journalWriter = bufferedSink;
    }

    public static final /* synthetic */ void access$setMostRecentRebuildFailed$p(DiskLruCache diskLruCache, boolean bl) {
        diskLruCache.mostRecentRebuildFailed = bl;
    }

    public static final /* synthetic */ void access$setMostRecentTrimFailed$p(DiskLruCache diskLruCache, boolean bl) {
        diskLruCache.mostRecentTrimFailed = bl;
    }

    public static final /* synthetic */ void access$setRedundantOpCount$p(DiskLruCache diskLruCache, int n) {
        diskLruCache.redundantOpCount = n;
    }

    private final void checkNotClosed() {
        synchronized (this) {
            boolean bl = this.closed;
            if (bl ^ true) {
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("cache is closed".toString());
            throw (Throwable)illegalStateException;
        }
    }

    public static /* synthetic */ Editor edit$default(DiskLruCache diskLruCache, String string2, long l, int n, Object object) throws IOException {
        if ((n & 2) == 0) return diskLruCache.edit(string2, l);
        l = ANY_SEQUENCE_NUMBER;
        return diskLruCache.edit(string2, l);
    }

    private final boolean journalRebuildRequired() {
        int n = this.redundantOpCount;
        if (n < 2000) return false;
        if (n < this.lruEntries.size()) return false;
        return true;
    }

    private final BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile), (Function1<? super IOException, Unit>)new Function1<IOException, Unit>(this){
            final /* synthetic */ DiskLruCache this$0;
            {
                this.this$0 = diskLruCache;
                super(1);
            }

            public final void invoke(IOException serializable) {
                Intrinsics.checkParameterIsNotNull(serializable, "it");
                DiskLruCache diskLruCache = this.this$0;
                if (Util.assertionsEnabled && !Thread.holdsLock(diskLruCache)) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Thread ");
                    Thread thread2 = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                    ((StringBuilder)serializable).append(thread2.getName());
                    ((StringBuilder)serializable).append(" MUST hold lock on ");
                    ((StringBuilder)serializable).append(diskLruCache);
                    throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)serializable).toString()));
                }
                DiskLruCache.access$setHasJournalErrors$p(this.this$0, true);
            }
        }));
    }

    private final void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator<Entry> iterator2 = this.lruEntries.values().iterator();
        block0 : while (iterator2.hasNext()) {
            Entry entry = iterator2.next();
            Intrinsics.checkExpressionValueIsNotNull(entry, "i.next()");
            Editor editor = entry.getCurrentEditor$okhttp();
            int n = 0;
            int n2 = 0;
            if (editor == null) {
                n = this.valueCount;
                do {
                    if (n2 >= n) continue block0;
                    this.size += entry.getLengths$okhttp()[n2];
                    ++n2;
                } while (true);
            }
            entry.setCurrentEditor$okhttp(null);
            int n3 = this.valueCount;
            for (n2 = n; n2 < n3; ++n2) {
                this.fileSystem.delete(entry.getCleanFiles$okhttp().get(n2));
                this.fileSystem.delete(entry.getDirtyFiles$okhttp().get(n2));
            }
            iterator2.remove();
        }
    }

    /*
     * Exception decompiling
     */
    private final void readJournal() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    private final void readJournalLine(String object) throws IOException {
        CharSequence charSequence;
        Object object2 = (CharSequence)object;
        int n = StringsKt.indexOf$default((CharSequence)object2, ' ', 0, false, 6, null);
        if (n == -1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unexpected journal line: ");
            ((StringBuilder)object2).append((String)object);
            throw (Throwable)new IOException(((StringBuilder)object2).toString());
        }
        int n2 = n + 1;
        int n3 = StringsKt.indexOf$default((CharSequence)object2, ' ', n2, false, 4, null);
        if (n3 == -1) {
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            object2 = ((String)object).substring(n2);
            Intrinsics.checkExpressionValueIsNotNull(object2, "(this as java.lang.String).substring(startIndex)");
            charSequence = object2;
            if (n == REMOVE.length()) {
                charSequence = object2;
                if (StringsKt.startsWith$default((String)object, REMOVE, false, 2, null)) {
                    this.lruEntries.remove(object2);
                    return;
                }
            }
        } else {
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            charSequence = ((String)object).substring(n2, n3);
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        }
        Entry entry = this.lruEntries.get(charSequence);
        object2 = entry;
        if (entry == null) {
            object2 = new Entry((String)charSequence);
            ((Map)this.lruEntries).put(charSequence, object2);
        }
        if (n3 != -1 && n == CLEAN.length() && StringsKt.startsWith$default((String)object, CLEAN, false, 2, null)) {
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            object = ((String)object).substring(n3 + 1);
            Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).substring(startIndex)");
            object = StringsKt.split$default((CharSequence)object, new char[]{' '}, false, 0, 6, null);
            ((Entry)object2).setReadable$okhttp(true);
            ((Entry)object2).setCurrentEditor$okhttp(null);
            ((Entry)object2).setLengths$okhttp((List<String>)object);
            return;
        }
        if (n3 == -1 && n == DIRTY.length() && StringsKt.startsWith$default((String)object, DIRTY, false, 2, null)) {
            ((Entry)object2).setCurrentEditor$okhttp(new Editor((Entry)object2));
            return;
        }
        if (n3 == -1 && n == READ.length() && StringsKt.startsWith$default((String)object, READ, false, 2, null)) {
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("unexpected journal line: ");
        ((StringBuilder)object2).append((String)object);
        throw (Throwable)new IOException(((StringBuilder)object2).toString());
    }

    private final boolean removeOldestEntry() {
        Entry entry;
        Iterator<Entry> iterator2 = this.lruEntries.values().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while ((entry = iterator2.next()).getZombie$okhttp());
        Intrinsics.checkExpressionValueIsNotNull(entry, "toEvict");
        this.removeEntry$okhttp(entry);
        return true;
    }

    private final void validateKey(String string2) {
        if (LEGAL_KEY_PATTERN.matches(string2)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
        stringBuilder.append(string2);
        stringBuilder.append('\"');
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled unnecessary exception pruning
     */
    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (this.initialized && !this.closed) {
                Object object = this.lruEntries.values();
                Intrinsics.checkExpressionValueIsNotNull(object, "lruEntries.values");
                object = object.toArray(new Entry[0]);
                if (object == null) {
                    object = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    throw object;
                }
                for (Object object2 : object) {
                    if (((Entry)object2).getCurrentEditor$okhttp() == null || (object2 = ((Entry)object2).getCurrentEditor$okhttp()) == null) continue;
                    ((Editor)object2).detach$okhttp();
                }
                this.trimToSize();
                object = this.journalWriter;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                object.close();
                this.journalWriter = null;
                this.closed = true;
                return;
            }
            this.closed = true;
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public final void completeEdit$okhttp(Editor object, boolean bl) throws IOException {
        synchronized (this) {
            long l;
            Object object2;
            void var2_2;
            int n;
            int n2;
            Intrinsics.checkParameterIsNotNull(object, "editor");
            Object object3 = ((Editor)object).getEntry$okhttp();
            if (!Intrinsics.areEqual(((Entry)object3).getCurrentEditor$okhttp(), object)) {
                object = new IllegalStateException("Check failed.".toString());
                throw (Throwable)object;
            }
            int n3 = 0;
            if (var2_2 != false && !((Entry)object3).getReadable$okhttp()) {
                n2 = this.valueCount;
                for (n = 0; n < n2; ++n) {
                    object2 = ((Editor)object).getWritten$okhttp();
                    if (object2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (!object2[n]) {
                        ((Editor)object).abort();
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Newly created entry didn't create value for index ");
                        ((StringBuilder)object3).append(n);
                        object = new IllegalStateException(((StringBuilder)object3).toString());
                        throw (Throwable)object;
                    }
                    if (this.fileSystem.exists(((Entry)object3).getDirtyFiles$okhttp().get(n))) continue;
                    ((Editor)object).abort();
                    return;
                }
            }
            n2 = this.valueCount;
            for (n = n3; n < n2; ++n) {
                object = ((Entry)object3).getDirtyFiles$okhttp().get(n);
                if (var2_2 != false && !((Entry)object3).getZombie$okhttp()) {
                    if (!this.fileSystem.exists((File)object)) continue;
                    object2 = ((Entry)object3).getCleanFiles$okhttp().get(n);
                    this.fileSystem.rename((File)object, (File)object2);
                    long l2 = ((Entry)object3).getLengths$okhttp()[n];
                    object3.getLengths$okhttp()[n] = l = this.fileSystem.size((File)object2);
                    this.size = this.size - l2 + l;
                    continue;
                }
                this.fileSystem.delete((File)object);
            }
            ((Entry)object3).setCurrentEditor$okhttp(null);
            if (((Entry)object3).getZombie$okhttp()) {
                this.removeEntry$okhttp((Entry)object3);
                return;
            }
            ++this.redundantOpCount;
            object = this.journalWriter;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            if (!((Entry)object3).getReadable$okhttp() && var2_2 == false) {
                this.lruEntries.remove(((Entry)object3).getKey$okhttp());
                object.writeUtf8(REMOVE).writeByte(32);
                object.writeUtf8(((Entry)object3).getKey$okhttp());
                object.writeByte(10);
            } else {
                ((Entry)object3).setReadable$okhttp(true);
                object.writeUtf8(CLEAN).writeByte(32);
                object.writeUtf8(((Entry)object3).getKey$okhttp());
                ((Entry)object3).writeLengths$okhttp((BufferedSink)object);
                object.writeByte(10);
                if (var2_2 != false) {
                    l = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1L + l;
                    ((Entry)object3).setSequenceNumber$okhttp(l);
                }
            }
            object.flush();
            if (this.size <= this.maxSize) {
                if (!this.journalRebuildRequired()) return;
            }
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return;
        }
    }

    public final void delete() throws IOException {
        this.close();
        this.fileSystem.deleteContents(this.directory);
    }

    public final Editor edit(String string2) throws IOException {
        return DiskLruCache.edit$default(this, string2, 0L, 2, null);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public final Editor edit(String object, long l) throws IOException {
        synchronized (this) {
            Object object2;
            int n;
            void var2_2;
            Intrinsics.checkParameterIsNotNull(object, "key");
            this.initialize();
            this.checkNotClosed();
            this.validateKey((String)object);
            Entry entry = this.lruEntries.get(object);
            if (var2_2 != ANY_SEQUENCE_NUMBER) {
                if (entry == null) return null;
                long l2 = entry.getSequenceNumber$okhttp();
                if (l2 != var2_2) {
                    return null;
                }
            }
            if ((object2 = entry != null ? entry.getCurrentEditor$okhttp() : null) != null) {
                return null;
            }
            if (entry != null && (n = entry.getLockingSourceCount$okhttp()) != 0) {
                return null;
            }
            if (!this.mostRecentTrimFailed && !this.mostRecentRebuildFailed) {
                object2 = this.journalWriter;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                object2.writeUtf8(DIRTY).writeByte(32).writeUtf8((String)object).writeByte(10);
                object2.flush();
                boolean bl = this.hasJournalErrors;
                if (bl) {
                    return null;
                }
                object2 = entry;
                if (entry == null) {
                    object2 = new Entry((String)object);
                    ((Map)this.lruEntries).put(object, object2);
                }
                object = new Editor((Entry)object2);
                ((Entry)object2).setCurrentEditor$okhttp((Editor)object);
                return object;
            }
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return null;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public final void evictAll() throws IOException {
        synchronized (this) {
            int n;
            Entry[] arrentry;
            this.initialize();
            Object object = this.lruEntries.values();
            Intrinsics.checkExpressionValueIsNotNull(object, "lruEntries.values");
            object = object.toArray(new Entry[0]);
            if (object != null) {
                arrentry = object;
                n = arrentry.length;
            } else {
                object = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                throw object;
            }
            for (int i = 0; i < n; ++i) {
                object = arrentry[i];
                Intrinsics.checkExpressionValueIsNotNull(object, "entry");
                this.removeEntry$okhttp((Entry)object);
            }
            this.mostRecentTrimFailed = false;
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        synchronized (this) {
            boolean bl = this.initialized;
            if (!bl) {
                return;
            }
            this.checkNotClosed();
            this.trimToSize();
            BufferedSink bufferedSink = this.journalWriter;
            if (bufferedSink == null) {
                Intrinsics.throwNpe();
            }
            bufferedSink.flush();
            return;
        }
    }

    public final Snapshot get(String string2) throws IOException {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(string2, "key");
            this.initialize();
            this.checkNotClosed();
            this.validateKey(string2);
            Object object = this.lruEntries.get(string2);
            if (object == null) return null;
            Intrinsics.checkExpressionValueIsNotNull(object, "lruEntries[key] ?: return null");
            object = ((Entry)object).snapshot$okhttp();
            if (object == null) return null;
            ++this.redundantOpCount;
            BufferedSink bufferedSink = this.journalWriter;
            if (bufferedSink == null) {
                Intrinsics.throwNpe();
            }
            bufferedSink.writeUtf8(READ).writeByte(32).writeUtf8(string2).writeByte(10);
            if (!this.journalRebuildRequired()) return object;
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return object;
        }
    }

    public final boolean getClosed$okhttp() {
        return this.closed;
    }

    public final File getDirectory() {
        return this.directory;
    }

    public final FileSystem getFileSystem$okhttp() {
        return this.fileSystem;
    }

    public final LinkedHashMap<String, Entry> getLruEntries$okhttp() {
        return this.lruEntries;
    }

    public final long getMaxSize() {
        synchronized (this) {
            return this.maxSize;
        }
    }

    public final int getValueCount$okhttp() {
        return this.valueCount;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void initialize() throws IOException {
        synchronized (this) {
            if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST hold lock on ");
                stringBuilder.append(this);
                AssertionError assertionError = new AssertionError((Object)stringBuilder.toString());
                throw (Throwable)((Object)assertionError);
            }
            boolean bl = this.initialized;
            if (bl) {
                return;
            }
            if (this.fileSystem.exists(this.journalFileBackup)) {
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.delete(this.journalFileBackup);
                } else {
                    this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                }
            }
            this.civilizedFileSystem = Util.isCivilized(this.fileSystem, this.journalFileBackup);
            bl = this.fileSystem.exists(this.journalFile);
            if (bl) {
                try {
                    this.readJournal();
                    this.processJournal();
                    this.initialized = true;
                    return;
                }
                catch (IOException iOException) {
                    Platform platform = Platform.Companion.get();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DiskLruCache ");
                    stringBuilder.append(this.directory);
                    stringBuilder.append(" is corrupt: ");
                    stringBuilder.append(iOException.getMessage());
                    stringBuilder.append(", removing");
                    platform.log(stringBuilder.toString(), 5, iOException);
                    try {
                        this.delete();
                    }
                    finally {
                        this.closed = false;
                    }
                }
            }
            this.rebuildJournal$okhttp();
            this.initialized = true;
            return;
        }
    }

    public final boolean isClosed() {
        synchronized (this) {
            return this.closed;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void rebuildJournal$okhttp() throws IOException {
        synchronized (this) {
            Closeable closeable = this.journalWriter;
            if (closeable != null) {
                closeable.close();
            }
            closeable = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
            Throwable throwable = null;
            try {
                Closeable closeable2 = closeable;
                closeable2.writeUtf8(MAGIC).writeByte(10);
                closeable2.writeUtf8(VERSION_1).writeByte(10);
                closeable2.writeDecimalLong(this.appVersion).writeByte(10);
                closeable2.writeDecimalLong(this.valueCount).writeByte(10);
                closeable2.writeByte(10);
                for (Entry entry : this.lruEntries.values()) {
                    if (entry.getCurrentEditor$okhttp() != null) {
                        closeable2.writeUtf8(DIRTY).writeByte(32);
                        closeable2.writeUtf8(entry.getKey$okhttp());
                        closeable2.writeByte(10);
                        continue;
                    }
                    closeable2.writeUtf8(CLEAN).writeByte(32);
                    closeable2.writeUtf8(entry.getKey$okhttp());
                    entry.writeLengths$okhttp((BufferedSink)closeable2);
                    closeable2.writeByte(10);
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    CloseableKt.closeFinally(closeable, throwable2);
                    throw throwable3;
                }
            }
            CloseableKt.closeFinally(closeable, throwable);
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = this.newJournalWriter();
            this.hasJournalErrors = false;
            this.mostRecentRebuildFailed = false;
            return;
        }
    }

    public final boolean remove(String object) throws IOException {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(object, "key");
            this.initialize();
            this.checkNotClosed();
            this.validateKey((String)object);
            object = this.lruEntries.get(object);
            if (object == null) return false;
            Intrinsics.checkExpressionValueIsNotNull(object, "lruEntries[key] ?: return false");
            boolean bl = this.removeEntry$okhttp((Entry)object);
            if (!bl) return bl;
            if (this.size > this.maxSize) return bl;
            this.mostRecentTrimFailed = false;
            return bl;
        }
    }

    public final boolean removeEntry$okhttp(Entry entry) throws IOException {
        Object object;
        Intrinsics.checkParameterIsNotNull(entry, "entry");
        if (!this.civilizedFileSystem) {
            if (entry.getLockingSourceCount$okhttp() > 0 && (object = this.journalWriter) != null) {
                object.writeUtf8(DIRTY);
                object.writeByte(32);
                object.writeUtf8(entry.getKey$okhttp());
                object.writeByte(10);
                object.flush();
            }
            if (entry.getLockingSourceCount$okhttp() > 0 || entry.getCurrentEditor$okhttp() != null) {
                entry.setZombie$okhttp(true);
                return true;
            }
        }
        if ((object = entry.getCurrentEditor$okhttp()) != null) {
            ((Editor)object).detach$okhttp();
        }
        int n = this.valueCount;
        for (int i = 0; i < n; this.size -= entry.getLengths$okhttp()[i], ++i) {
            this.fileSystem.delete(entry.getCleanFiles$okhttp().get(i));
            entry.getLengths$okhttp()[i] = 0L;
        }
        ++this.redundantOpCount;
        object = this.journalWriter;
        if (object != null) {
            object.writeUtf8(REMOVE);
            object.writeByte(32);
            object.writeUtf8(entry.getKey$okhttp());
            object.writeByte(10);
        }
        this.lruEntries.remove(entry.getKey$okhttp());
        if (!this.journalRebuildRequired()) return true;
        TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
        return true;
    }

    public final void setClosed$okhttp(boolean bl) {
        this.closed = bl;
    }

    public final void setMaxSize(long l) {
        synchronized (this) {
            this.maxSize = l;
            if (!this.initialized) return;
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return;
        }
    }

    public final long size() throws IOException {
        synchronized (this) {
            this.initialize();
            return this.size;
        }
    }

    public final Iterator<Snapshot> snapshots() throws IOException {
        synchronized (this) {
            this.initialize();
            Iterator<Snapshot> iterator2 = new Iterator<Snapshot>(this){
                private final Iterator<Entry> delegate;
                private Snapshot nextSnapshot;
                private Snapshot removeSnapshot;
                final /* synthetic */ DiskLruCache this$0;
                {
                    this.this$0 = object;
                    object = new ArrayList<Entry>(((DiskLruCache)object).getLruEntries$okhttp().values()).iterator();
                    Intrinsics.checkExpressionValueIsNotNull(object, "ArrayList(lruEntries.values).iterator()");
                    this.delegate = object;
                }

                public boolean hasNext() {
                    if (this.nextSnapshot != null) {
                        return true;
                    }
                    DiskLruCache diskLruCache = this.this$0;
                    synchronized (diskLruCache) {
                        Object object;
                        boolean bl = this.this$0.getClosed$okhttp();
                        if (bl) {
                            return false;
                        }
                        do {
                            if (this.delegate.hasNext()) continue;
                            object = Unit.INSTANCE;
                            return false;
                        } while ((object = this.delegate.next()) == null || (object = ((Entry)object).snapshot$okhttp()) == null);
                        this.nextSnapshot = object;
                        return true;
                    }
                }

                public Snapshot next() {
                    Snapshot snapshot;
                    if (!this.hasNext()) throw (Throwable)new java.util.NoSuchElementException();
                    this.removeSnapshot = snapshot = this.nextSnapshot;
                    this.nextSnapshot = null;
                    if (snapshot != null) return snapshot;
                    Intrinsics.throwNpe();
                    return snapshot;
                }

                /*
                 * Exception decompiling
                 */
                public void remove() {
                    // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                    // java.lang.IllegalStateException: Last catch has completely empty body
                    // org.benf.cfr.reader.bytecode.analysis.parse.utils.finalhelp.FinalAnalyzer.identifyFinally(FinalAnalyzer.java:285)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.FinallyRewriter.identifyFinally(FinallyRewriter.java:40)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:476)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                    // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                    // org.benf.cfr.reader.entities.Method.dump(Method.java:475)
                    // org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperAnonymousInner.dumpWithArgs(ClassFileDumperAnonymousInner.java:87)
                    // org.benf.cfr.reader.bytecode.analysis.parse.expression.ConstructorInvokationAnonymousInner.dumpInner(ConstructorInvokationAnonymousInner.java:73)
                    // org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dumpWithOuterPrecedence(AbstractExpression.java:113)
                    // org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dump(AbstractExpression.java:74)
                    // org.benf.cfr.reader.util.output.StreamDumper.dump(StreamDumper.java:146)
                    // org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredAssignment.dump(StructuredAssignment.java:59)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                    // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.dump(Block.java:559)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                    // org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredSynchronized.dump(StructuredSynchronized.java:32)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                    // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.dump(Block.java:559)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                    // org.benf.cfr.reader.entities.attributes.AttributeCode.dump(AttributeCode.java:141)
                    // org.benf.cfr.reader.util.output.StreamDumper.dump(StreamDumper.java:146)
                    // org.benf.cfr.reader.entities.Method.dump(Method.java:494)
                    // org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperNormal.dump(ClassFileDumperNormal.java:87)
                    // org.benf.cfr.reader.entities.ClassFile.dump(ClassFile.java:1016)
                    // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:231)
                    // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                    // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                    // org.benf.cfr.reader.Main.main(Main.java:48)
                    // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
                    // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
                    throw new IllegalStateException("Decompilation failed");
                }
            };
            return iterator2;
        }
    }

    public final void trimToSize() throws IOException {
        do {
            if (this.size > this.maxSize) continue;
            this.mostRecentTrimFailed = false;
            return;
        } while (this.removeOldestEntry());
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00068\u0006X\u0087D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lokhttp3/internal/cache/DiskLruCache$Companion;", "", "()V", "ANY_SEQUENCE_NUMBER", "", "CLEAN", "", "DIRTY", "JOURNAL_FILE", "JOURNAL_FILE_BACKUP", "JOURNAL_FILE_TEMP", "LEGAL_KEY_PATTERN", "Lkotlin/text/Regex;", "MAGIC", "READ", "REMOVE", "VERSION_1", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0013\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\r\u0010\u0011\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2={"Lokhttp3/internal/cache/DiskLruCache$Editor;", "", "entry", "Lokhttp3/internal/cache/DiskLruCache$Entry;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/internal/cache/DiskLruCache;Lokhttp3/internal/cache/DiskLruCache$Entry;)V", "done", "", "getEntry$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Entry;", "written", "", "getWritten$okhttp", "()[Z", "abort", "", "commit", "detach", "detach$okhttp", "newSink", "Lokio/Sink;", "index", "", "newSource", "Lokio/Source;", "okhttp"}, k=1, mv={1, 1, 16})
    public final class Editor {
        private boolean done;
        private final Entry entry;
        private final boolean[] written;

        public Editor(Entry entry) {
            Intrinsics.checkParameterIsNotNull(entry, "entry");
            this.entry = entry;
            DiskLruCache.this = entry.getReadable$okhttp() ? null : new boolean[DiskLruCache.this.getValueCount$okhttp()];
            this.written = DiskLruCache.this;
        }

        public final void abort() throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (!(this.done ^ true)) {
                    IllegalStateException illegalStateException = new IllegalStateException("Check failed.".toString());
                    throw (Throwable)illegalStateException;
                }
                if (Intrinsics.areEqual(this.entry.getCurrentEditor$okhttp(), this)) {
                    DiskLruCache.this.completeEdit$okhttp(this, false);
                }
                this.done = true;
                Unit unit = Unit.INSTANCE;
                return;
            }
        }

        public final void commit() throws IOException {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (!(this.done ^ true)) {
                    IllegalStateException illegalStateException = new IllegalStateException("Check failed.".toString());
                    throw (Throwable)illegalStateException;
                }
                if (Intrinsics.areEqual(this.entry.getCurrentEditor$okhttp(), this)) {
                    DiskLruCache.this.completeEdit$okhttp(this, true);
                }
                this.done = true;
                Unit unit = Unit.INSTANCE;
                return;
            }
        }

        public final void detach$okhttp() {
            if (!Intrinsics.areEqual(this.entry.getCurrentEditor$okhttp(), this)) return;
            if (DiskLruCache.this.civilizedFileSystem) {
                DiskLruCache.this.completeEdit$okhttp(this, false);
                return;
            }
            this.entry.setZombie$okhttp(true);
        }

        public final Entry getEntry$okhttp() {
            return this.entry;
        }

        public final boolean[] getWritten$okhttp() {
            return this.written;
        }

        public final Sink newSink(int n) {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (this.done ^ true) {
                    Object object;
                    Sink sink2;
                    if (Intrinsics.areEqual(this.entry.getCurrentEditor$okhttp(), this) ^ true) {
                        return Okio.blackhole();
                    }
                    if (!this.entry.getReadable$okhttp()) {
                        object = this.written;
                        if (object == null) {
                            Intrinsics.throwNpe();
                        }
                        object[n] = true;
                    }
                    object = this.entry.getDirtyFiles$okhttp().get(n);
                    try {
                        sink2 = DiskLruCache.this.getFileSystem$okhttp().sink((File)object);
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        return Okio.blackhole();
                    }
                    Function1<IOException, Unit> function1 = new Function1<IOException, Unit>(this, n){
                        final /* synthetic */ int $index$inlined;
                        final /* synthetic */ Editor this$0;
                        {
                            this.this$0 = editor;
                            this.$index$inlined = n;
                            super(1);
                        }

                        public final void invoke(IOException object) {
                            Intrinsics.checkParameterIsNotNull(object, "it");
                            object = this.this$0.DiskLruCache.this;
                            synchronized (object) {
                                this.this$0.detach$okhttp();
                                Unit unit = Unit.INSTANCE;
                                return;
                            }
                        }
                    };
                    object = new FaultHidingSink(sink2, (Function1<? super IOException, Unit>)function1);
                    return (Sink)object;
                }
                IllegalStateException illegalStateException = new IllegalStateException("Check failed.".toString());
                throw (Throwable)illegalStateException;
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        public final Source newSource(int n) {
            DiskLruCache diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache) {
                if (!(this.done ^ true)) {
                    IllegalStateException illegalStateException = new IllegalStateException("Check failed.".toString());
                    throw (Throwable)illegalStateException;
                }
                boolean bl = this.entry.getReadable$okhttp();
                Source source2 = null;
                if (!bl) return null;
                if (Intrinsics.areEqual(this.entry.getCurrentEditor$okhttp(), this) ^ true) return null;
                bl = this.entry.getZombie$okhttp();
                if (bl) {
                    return null;
                }
                try {
                    Source source3 = DiskLruCache.this.getFileSystem$okhttp().source(this.entry.getCleanFiles$okhttp().get(n));
                    return source3;
                }
                catch (FileNotFoundException source3) {}
                return source2;
            }
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0016\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010.\u001a\u00020/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000301H\u0002J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u001aH\u0002J\u001b\u00105\u001a\u0002062\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000301H\u0000\u00a2\u0006\u0002\b7J\u0013\u00108\u001a\b\u0018\u000109R\u00020\fH\u0000\u00a2\u0006\u0002\b:J\u0015\u0010;\u001a\u0002062\u0006\u0010<\u001a\u00020=H\u0000\u00a2\u0006\u0002\b>R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0018\u00010\u000bR\u00020\fX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020 X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\"\"\u0004\b-\u0010$\u00a8\u0006?"}, d2={"Lokhttp3/internal/cache/DiskLruCache$Entry;", "", "key", "", "(Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;)V", "cleanFiles", "", "Ljava/io/File;", "getCleanFiles$okhttp", "()Ljava/util/List;", "currentEditor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "getCurrentEditor$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Editor;", "setCurrentEditor$okhttp", "(Lokhttp3/internal/cache/DiskLruCache$Editor;)V", "dirtyFiles", "getDirtyFiles$okhttp", "getKey$okhttp", "()Ljava/lang/String;", "lengths", "", "getLengths$okhttp", "()[J", "lockingSourceCount", "", "getLockingSourceCount$okhttp", "()I", "setLockingSourceCount$okhttp", "(I)V", "readable", "", "getReadable$okhttp", "()Z", "setReadable$okhttp", "(Z)V", "sequenceNumber", "", "getSequenceNumber$okhttp", "()J", "setSequenceNumber$okhttp", "(J)V", "zombie", "getZombie$okhttp", "setZombie$okhttp", "invalidLengths", "", "strings", "", "newSource", "Lokio/Source;", "index", "setLengths", "", "setLengths$okhttp", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "snapshot$okhttp", "writeLengths", "writer", "Lokio/BufferedSink;", "writeLengths$okhttp", "okhttp"}, k=1, mv={1, 1, 16})
    public final class Entry {
        private final List<File> cleanFiles;
        private Editor currentEditor;
        private final List<File> dirtyFiles;
        private final String key;
        private final long[] lengths;
        private int lockingSourceCount;
        private boolean readable;
        private long sequenceNumber;
        private boolean zombie;

        public Entry(String charSequence) {
            Intrinsics.checkParameterIsNotNull(charSequence, "key");
            this.key = charSequence;
            this.lengths = new long[DiskLruCache.this.getValueCount$okhttp()];
            this.cleanFiles = new ArrayList();
            this.dirtyFiles = new ArrayList();
            charSequence = new StringBuilder(this.key);
            ((StringBuilder)charSequence).append('.');
            int n = ((StringBuilder)charSequence).length();
            int n2 = DiskLruCache.this.getValueCount$okhttp();
            int n3 = 0;
            while (n3 < n2) {
                ((StringBuilder)charSequence).append(n3);
                ((Collection)this.cleanFiles).add(new File(DiskLruCache.this.getDirectory(), ((StringBuilder)charSequence).toString()));
                ((StringBuilder)charSequence).append(".tmp");
                ((Collection)this.dirtyFiles).add(new File(DiskLruCache.this.getDirectory(), ((StringBuilder)charSequence).toString()));
                ((StringBuilder)charSequence).setLength(n);
                ++n3;
            }
        }

        private final Void invalidLengths(List<String> list) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal line: ");
            stringBuilder.append(list);
            throw (Throwable)new IOException(stringBuilder.toString());
        }

        private final Source newSource(int n) {
            Source source2 = DiskLruCache.this.getFileSystem$okhttp().source(this.cleanFiles.get(n));
            if (DiskLruCache.this.civilizedFileSystem) {
                return source2;
            }
            ++this.lockingSourceCount;
            return new ForwardingSource(this, source2, source2){
                final /* synthetic */ Source $fileSource;
                private boolean closed;
                final /* synthetic */ Entry this$0;
                {
                    this.this$0 = entry;
                    this.$fileSource = source2;
                    super(source3);
                }

                public void close() {
                    super.close();
                    if (this.closed) return;
                    this.closed = true;
                    DiskLruCache diskLruCache = this.this$0.DiskLruCache.this;
                    synchronized (diskLruCache) {
                        Object object = this.this$0;
                        ((Entry)object).setLockingSourceCount$okhttp(((Entry)object).getLockingSourceCount$okhttp() - 1);
                        if (this.this$0.getLockingSourceCount$okhttp() == 0 && this.this$0.getZombie$okhttp()) {
                            this.this$0.DiskLruCache.this.removeEntry$okhttp(this.this$0);
                        }
                        object = Unit.INSTANCE;
                        return;
                    }
                }
            };
        }

        public final List<File> getCleanFiles$okhttp() {
            return this.cleanFiles;
        }

        public final Editor getCurrentEditor$okhttp() {
            return this.currentEditor;
        }

        public final List<File> getDirtyFiles$okhttp() {
            return this.dirtyFiles;
        }

        public final String getKey$okhttp() {
            return this.key;
        }

        public final long[] getLengths$okhttp() {
            return this.lengths;
        }

        public final int getLockingSourceCount$okhttp() {
            return this.lockingSourceCount;
        }

        public final boolean getReadable$okhttp() {
            return this.readable;
        }

        public final long getSequenceNumber$okhttp() {
            return this.sequenceNumber;
        }

        public final boolean getZombie$okhttp() {
            return this.zombie;
        }

        public final void setCurrentEditor$okhttp(Editor editor) {
            this.currentEditor = editor;
        }

        public final void setLengths$okhttp(List<String> list) throws IOException {
            Intrinsics.checkParameterIsNotNull(list, "strings");
            if (list.size() != DiskLruCache.this.getValueCount$okhttp()) {
                this.invalidLengths(list);
                throw null;
            }
            int n = 0;
            try {
                int n2 = ((Collection)list).size();
                while (n < n2) {
                    this.lengths[n] = Long.parseLong(list.get(n));
                    ++n;
                }
                return;
            }
            catch (NumberFormatException numberFormatException) {
                this.invalidLengths(list);
                throw null;
            }
        }

        public final void setLockingSourceCount$okhttp(int n) {
            this.lockingSourceCount = n;
        }

        public final void setReadable$okhttp(boolean bl) {
            this.readable = bl;
        }

        public final void setSequenceNumber$okhttp(long l) {
            this.sequenceNumber = l;
        }

        public final void setZombie$okhttp(boolean bl) {
            this.zombie = bl;
        }

        public final Snapshot snapshot$okhttp() {
            DiskLruCache diskLruCache = DiskLruCache.this;
            if (Util.assertionsEnabled && !Thread.holdsLock(diskLruCache)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST hold lock on ");
                stringBuilder.append(diskLruCache);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            if (!this.readable) {
                return null;
            }
            if (!DiskLruCache.this.civilizedFileSystem) {
                if (this.currentEditor != null) return null;
                if (this.zombie) {
                    return null;
                }
            }
            Object object = new ArrayList();
            Object object2 = (long[])this.lengths.clone();
            int n = 0;
            try {
                int n2 = DiskLruCache.this.getValueCount$okhttp();
                while (n < n2) {
                    ((Collection)object).add(this.newSource(n));
                    ++n;
                }
                return new Snapshot(this.key, this.sequenceNumber, (List<? extends Source>)object, (long[])object2);
            }
            catch (FileNotFoundException fileNotFoundException) {
                object = object.iterator();
                while (object.hasNext()) {
                    Util.closeQuietly((Source)object.next());
                }
                try {
                    DiskLruCache.this.removeEntry$okhttp(this);
                    return null;
                }
                catch (IOException iOException) {
                    return null;
                }
            }
        }

        public final void writeLengths$okhttp(BufferedSink bufferedSink) throws IOException {
            Intrinsics.checkParameterIsNotNull(bufferedSink, "writer");
            long[] arrl = this.lengths;
            int n = arrl.length;
            int n2 = 0;
            while (n2 < n) {
                long l = arrl[n2];
                bufferedSink.writeByte(32).writeDecimalLong(l);
                ++n2;
            }
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B-\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016J\f\u0010\u000e\u001a\b\u0018\u00010\u000fR\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0002\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Ljava/io/Closeable;", "key", "", "sequenceNumber", "", "sources", "", "Lokio/Source;", "lengths", "", "(Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;JLjava/util/List;[J)V", "close", "", "edit", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "getLength", "index", "", "getSource", "okhttp"}, k=1, mv={1, 1, 16})
    public final class Snapshot
    implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final List<Source> sources;

        public Snapshot(String string2, long l, List<? extends Source> list, long[] arrl) {
            Intrinsics.checkParameterIsNotNull(string2, "key");
            Intrinsics.checkParameterIsNotNull(list, "sources");
            Intrinsics.checkParameterIsNotNull(arrl, "lengths");
            this.key = string2;
            this.sequenceNumber = l;
            this.sources = list;
            this.lengths = arrl;
        }

        @Override
        public void close() {
            Iterator<Source> iterator2 = this.sources.iterator();
            while (iterator2.hasNext()) {
                Util.closeQuietly(iterator2.next());
            }
        }

        public final Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public final long getLength(int n) {
            return this.lengths[n];
        }

        public final Source getSource(int n) {
            return this.sources.get(n);
        }

        public final String key() {
            return this.key;
        }
    }

}

