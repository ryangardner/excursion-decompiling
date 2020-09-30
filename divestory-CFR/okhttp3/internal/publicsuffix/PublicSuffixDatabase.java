/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.publicsuffix;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0016\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000f\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "", "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publicSuffixExceptionListBytes", "", "publicSuffixListBytes", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "findMatchingRule", "", "", "domainLabels", "getEffectiveTldPlusOne", "domain", "readTheList", "", "readTheListUninterruptibly", "setListBytes", "splitDomain", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class PublicSuffixDatabase {
    public static final Companion Companion = new Companion(null);
    private static final char EXCEPTION_MARKER = '!';
    private static final List<String> PREVAILING_RULE;
    public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
    private static final byte[] WILDCARD_LABEL;
    private static final PublicSuffixDatabase instance;
    private final AtomicBoolean listRead = new AtomicBoolean(false);
    private byte[] publicSuffixExceptionListBytes;
    private byte[] publicSuffixListBytes;
    private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

    static {
        WILDCARD_LABEL = new byte[]{(byte)42};
        PREVAILING_RULE = CollectionsKt.listOf("*");
        instance = new PublicSuffixDatabase();
    }

    public static final /* synthetic */ byte[] access$getPublicSuffixListBytes$p(PublicSuffixDatabase arrby) {
        arrby = arrby.publicSuffixListBytes;
        if (arrby != null) return arrby;
        Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
        return arrby;
    }

    public static final /* synthetic */ void access$setPublicSuffixListBytes$p(PublicSuffixDatabase publicSuffixDatabase, byte[] arrby) {
        publicSuffixDatabase.publicSuffixListBytes = arrby;
    }

    private final List<String> findMatchingRule(List<String> object) {
        Object object2;
        block32 : {
            block31 : {
                Object object3;
                block30 : {
                    block29 : {
                        byte[][] arrby;
                        int n;
                        Object object4;
                        int n2;
                        block28 : {
                            if (!this.listRead.get() && this.listRead.compareAndSet(false, true)) {
                                this.readTheListUninterruptibly();
                            } else {
                                try {
                                    this.readCompleteLatch.await();
                                }
                                catch (InterruptedException interruptedException) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            n2 = this.publicSuffixListBytes != null ? 1 : 0;
                            if (n2 == 0) throw (Throwable)new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString());
                            n = object.size();
                            object2 = new byte[n][];
                            for (n2 = 0; n2 < n; object3 = object3.getBytes((Charset)object4), ++n2) {
                                object3 = object.get(n2);
                                object4 = StandardCharsets.UTF_8;
                                Intrinsics.checkExpressionValueIsNotNull(object4, "UTF_8");
                                if (object3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                Intrinsics.checkExpressionValueIsNotNull(object3, "(this as java.lang.String).getBytes(charset)");
                                object2[n2] = object3;
                            }
                            arrby = (byte[][])object2;
                            object = null;
                            n = arrby.length;
                            for (n2 = 0; n2 < n; ++n2) {
                                object3 = Companion;
                                object2 = this.publicSuffixListBytes;
                                if (object2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
                                }
                                if ((object2 = ((Companion)object3).binarySearch((byte[])object2, arrby, n2)) == null) {
                                    continue;
                                }
                                break block28;
                            }
                            object2 = object;
                        }
                        Object object5 = (Object[])arrby;
                        if (((Object[])object5).length > 1) {
                            object4 = (byte[][])object5.clone();
                            n = ((Object[])object4).length;
                            for (n2 = 0; n2 < n - 1; ++n2) {
                                object4[n2] = WILDCARD_LABEL;
                                object3 = Companion;
                                byte[] arrby2 = this.publicSuffixListBytes;
                                if (arrby2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
                                }
                                if ((object3 = ((Companion)object3).binarySearch(arrby2, object4, n2)) == null) {
                                    continue;
                                }
                                break;
                            }
                        } else {
                            object3 = object;
                        }
                        object4 = object;
                        if (object3 != null) {
                            n = ((Object[])object5).length;
                            n2 = 0;
                            do {
                                object4 = object;
                                if (n2 >= n - 1) break;
                                object5 = Companion;
                                object4 = this.publicSuffixExceptionListBytes;
                                if (object4 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
                                }
                                if ((object4 = ((Companion)object5).binarySearch((byte[])object4, arrby, n2)) != null) break;
                                ++n2;
                            } while (true);
                        }
                        if (object4 != null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append('!');
                            ((StringBuilder)object).append((String)object4);
                            return StringsKt.split$default((CharSequence)((StringBuilder)object).toString(), new char[]{'.'}, false, 0, 6, null);
                        }
                        if (object2 == null && object3 == null) {
                            return PREVAILING_RULE;
                        }
                        if (object2 == null) break block29;
                        object = StringsKt.split$default((CharSequence)object2, new char[]{'.'}, false, 0, 6, null);
                        if (object != null) break block30;
                    }
                    object = CollectionsKt.emptyList();
                }
                if (object3 == null) break block31;
                object2 = StringsKt.split$default((CharSequence)object3, new char[]{'.'}, false, 0, 6, null);
                if (object2 != null) break block32;
            }
            object2 = CollectionsKt.emptyList();
        }
        if (object.size() <= object2.size()) return object2;
        return object;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final void readTheList() throws IOException {
        byte[] arrby;
        byte[] arrby2;
        byte[] arrby3 = null;
        InputStream inputStream2 = PublicSuffixDatabase.class.getResourceAsStream(PUBLIC_SUFFIX_RESOURCE);
        if (inputStream2 == null) return;
        Closeable closeable = Okio.buffer(new GzipSource(Okio.source(inputStream2)));
        Throwable throwable = null;
        try {
            arrby = (byte[])closeable;
            arrby2 = arrby.readByteArray(arrby.readInt());
            arrby = arrby.readByteArray(arrby.readInt());
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
        synchronized (this) {
            if (arrby2 == null) {
                Intrinsics.throwNpe();
            }
            this.publicSuffixListBytes = arrby2;
            if (arrby == null) {
                Intrinsics.throwNpe();
            }
            this.publicSuffixExceptionListBytes = arrby;
            Unit unit = Unit.INSTANCE;
        }
        this.readCompleteLatch.countDown();
    }

    /*
     * Exception decompiling
     */
    private final void readTheListUninterruptibly() {
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

    private final List<String> splitDomain(String object) {
        if (!Intrinsics.areEqual((String)CollectionsKt.last(object = StringsKt.split$default((CharSequence)object, new char[]{'.'}, false, 0, 6, null)), "")) return object;
        return CollectionsKt.dropLast(object, 1);
    }

    public final String getEffectiveTldPlusOne(String string2) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(string2, "domain");
        Object object = IDN.toUnicode(string2);
        Intrinsics.checkExpressionValueIsNotNull(object, "unicodeDomain");
        List<String> list = this.splitDomain((String)object);
        object = this.findMatchingRule(list);
        if (list.size() == object.size() && ((String)object.get(0)).charAt(0) != '!') {
            return null;
        }
        if (((String)object.get(0)).charAt(0) == '!') {
            n = list.size();
            n2 = object.size();
            return SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence((Iterable)this.splitDomain(string2)), n - n2), ".", null, null, 0, null, null, 62, null);
        }
        n = list.size();
        n2 = object.size() + 1;
        return SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence((Iterable)this.splitDomain(string2)), n - n2), ".", null, null, 0, null, null, 62, null);
    }

    public final void setListBytes(byte[] arrby, byte[] arrby2) {
        Intrinsics.checkParameterIsNotNull(arrby, "publicSuffixListBytes");
        Intrinsics.checkParameterIsNotNull(arrby2, "publicSuffixExceptionListBytes");
        this.publicSuffixListBytes = arrby;
        this.publicSuffixExceptionListBytes = arrby2;
        this.listRead.set(true);
        this.readCompleteLatch.countDown();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\fJ)\u0010\u000e\u001a\u0004\u0018\u00010\u0007*\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase$Companion;", "", "()V", "EXCEPTION_MARKER", "", "PREVAILING_RULE", "", "", "PUBLIC_SUFFIX_RESOURCE", "WILDCARD_LABEL", "", "instance", "Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "get", "binarySearch", "labels", "", "labelIndex", "", "([B[[BI)Ljava/lang/String;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final String binarySearch(byte[] arrby, byte[][] object, int n) {
            int n2 = arrby.length;
            String string2 = null;
            int n3 = 0;
            block0 : do {
                int n4;
                int n5;
                String string3 = string2;
                if (n3 >= n2) return string3;
                for (n5 = (n3 + n2) / 2; n5 > -1 && arrby[n5] != (byte)10; --n5) {
                }
                int n6 = n5 + 1;
                n5 = 1;
                while (arrby[n4 = n6 + n5] != (byte)10) {
                    ++n5;
                }
                int n7 = n4 - n6;
                int n8 = n;
                int n9 = 0;
                n5 = 0;
                int n10 = 0;
                do {
                    block12 : {
                        block14 : {
                            block15 : {
                                block13 : {
                                    int n11;
                                    block11 : {
                                        if (n9 != 0) {
                                            n11 = 46;
                                            n9 = 0;
                                        } else {
                                            n11 = Util.and(object[n8][n5], 255);
                                        }
                                        if ((n11 -= Util.and(arrby[n6 + n10], 255)) != 0) break block11;
                                        ++n5;
                                        if (++n10 == n7) break block11;
                                        if (object[n8].length != n5) continue;
                                        if (n8 != ((Object[])object).length - 1) break block12;
                                    }
                                    if (n11 < 0) break block13;
                                    if (n11 > 0) break block14;
                                    n10 = n7 - n10;
                                    n9 = object[n8].length - n5;
                                    n5 = n8 + 1;
                                    n8 = ((Object[])object).length;
                                    while (n5 < n8) {
                                        n9 += object[n5].length;
                                        ++n5;
                                    }
                                    if (n9 >= n10) break block15;
                                }
                                n2 = n6 - 1;
                                continue block0;
                            }
                            if (n9 <= n10) {
                                object = StandardCharsets.UTF_8;
                                Intrinsics.checkExpressionValueIsNotNull(object, "UTF_8");
                                return new String(arrby, n6, n7, (Charset)object);
                            }
                        }
                        n3 = n4 + 1;
                        continue block0;
                    }
                    ++n8;
                    n9 = 1;
                    n5 = -1;
                } while (true);
                break;
            } while (true);
        }

        public final PublicSuffixDatabase get() {
            return instance;
        }
    }

}

