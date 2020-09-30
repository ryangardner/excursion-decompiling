/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0016"}, d2={"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"}, k=1, mv={1, 1, 16})
public final class Options
extends AbstractList<ByteString>
implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    private Options(ByteString[] arrbyteString, int[] arrn) {
        this.byteStrings = arrbyteString;
        this.trie = arrn;
    }

    public /* synthetic */ Options(ByteString[] arrbyteString, int[] arrn, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrbyteString, arrn);
    }

    @JvmStatic
    public static final Options of(ByteString ... arrbyteString) {
        return Companion.of(arrbyteString);
    }

    @Override
    public ByteString get(int n) {
        return this.byteStrings[n];
    }

    public final ByteString[] getByteStrings$okio() {
        return this.byteStrings;
    }

    @Override
    public int getSize() {
        return this.byteStrings.length;
    }

    public final int[] getTrie$okio() {
        return this.trie;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007\u00a2\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0018"}, d2={"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final void buildTrieRecursive(long l, Buffer buffer, int n, List<? extends ByteString> list, int n2, int n3, List<Integer> list2) {
            int n4;
            int n5;
            int n6 = n;
            n = n2 < n3 ? 1 : 0;
            if (n == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            for (n = n2; n < n3; ++n) {
                n4 = list.get(n).size() >= n6 ? 1 : 0;
                if (n4 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
            }
            Object object = list.get(n2);
            Object object2 = list.get(n3 - 1);
            if (n6 == ((ByteString)object).size()) {
                n4 = ((Number)list2.get(n2)).intValue();
                n = n2 + 1;
                object = list.get(n);
                n2 = n4;
            } else {
                n = n2;
                n2 = -1;
            }
            if (((ByteString)object).getByte(n6) != ((ByteString)object2).getByte(n6)) {
                n4 = 1;
                for (n5 = n + 1; n5 < n3; ++n5) {
                    int n7 = n4;
                    if (list.get(n5 - 1).getByte(n6) != list.get(n5).getByte(n6)) {
                        n7 = n4 + 1;
                    }
                    n4 = n7;
                }
                object2 = this;
                l = l + Companion.super.getIntCount(buffer) + (long)2 + (long)(n4 * 2);
                buffer.writeInt(n4);
                buffer.writeInt(n2);
                for (n2 = n; n2 < n3; ++n2) {
                    n4 = list.get(n2).getByte(n6);
                    if (n2 != n && n4 == list.get(n2 - 1).getByte(n6)) continue;
                    buffer.writeInt(n4 & 255);
                }
            } else {
                int n8 = Math.min(((ByteString)object).size(), ((ByteString)object2).size());
                int n9 = 0;
                for (n4 = n6; n4 < n8 && ((ByteString)object).getByte(n4) == ((ByteString)object2).getByte(n4); ++n9, ++n4) {
                }
                object2 = this;
                l = 1L + (l + Companion.super.getIntCount(buffer) + (long)2 + (long)n9);
                buffer.writeInt(-n9);
                buffer.writeInt(n2);
                n2 = n6 + n9;
                while (n6 < n2) {
                    buffer.writeInt(((ByteString)object).getByte(n6) & 255);
                    ++n6;
                }
                if (n + 1 != n3) {
                    object = new Buffer();
                    buffer.writeInt((int)(Companion.super.getIntCount((Buffer)object) + l) * -1);
                    Companion.super.buildTrieRecursive(l, (Buffer)object, n2, list, n, n3, list2);
                    buffer.writeAll((Source)object);
                    return;
                }
                n2 = n2 == list.get(n).size() ? 1 : 0;
                if (n2 == 0) throw (Throwable)new IllegalStateException("Check failed.".toString());
                buffer.writeInt(((Number)list2.get(n)).intValue());
                return;
            }
            object = new Buffer();
            n2 = n;
            do {
                block17 : {
                    if (n2 >= n3) {
                        buffer.writeAll((Source)object);
                        return;
                    }
                    n5 = list.get(n2).getByte(n6);
                    for (n = n4 = n2 + 1; n < n3; ++n) {
                        if (n5 == list.get(n).getByte(n6)) {
                            continue;
                        }
                        break block17;
                    }
                    n = n3;
                }
                if (n4 == n && n6 + 1 == list.get(n2).size()) {
                    buffer.writeInt(((Number)list2.get(n2)).intValue());
                } else {
                    buffer.writeInt((int)(l + Companion.super.getIntCount((Buffer)object)) * -1);
                    Companion.super.buildTrieRecursive(l, (Buffer)object, n6 + 1, list, n2, n, list2);
                }
                n2 = n;
            } while (true);
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long l, Buffer buffer, int n, List list, int n2, int n3, List list2, int n4, Object object) {
            if ((n4 & 1) != 0) {
                l = 0L;
            }
            if ((n4 & 4) != 0) {
                n = 0;
            }
            if ((n4 & 16) != 0) {
                n2 = 0;
            }
            if ((n4 & 32) != 0) {
                n3 = list.size();
            }
            companion.buildTrieRecursive(l, buffer, n, list, n2, n3, list2);
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / (long)4;
        }

        @JvmStatic
        public final Options of(ByteString ... object) {
            Object object2;
            Object object3;
            Intrinsics.checkParameterIsNotNull(object, "byteStrings");
            int n = ((ByteString[])object).length;
            int n2 = 0;
            n = n == 0 ? 1 : 0;
            if (n != 0) {
                return new Options(new ByteString[0], new int[]{0, -1}, null);
            }
            int[] arrn = ArraysKt.toMutableList(object);
            CollectionsKt.sort(arrn);
            Object object4 = (Integer[])new ArrayList(((ByteString[])object).length);
            int n3 = ((ByteString[])object).length;
            for (n = 0; n < n3; ++n) {
                object2 = object[n];
                object4.add(-1);
            }
            if ((object4 = ((Collection)((List)object4)).toArray(new Integer[0])) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            object4 = CollectionsKt.mutableListOf(Arrays.copyOf(object4, ((Integer[])object4).length));
            int n4 = ((ByteString[])object).length;
            n = 0;
            for (n3 = 0; n3 < n4; ++n3, ++n) {
                object4.set(CollectionsKt.binarySearch$default(arrn, object[n3], 0, 0, 6, null), n);
            }
            n = arrn.get(0).size() > 0 ? 1 : 0;
            if (n == 0) throw (Throwable)new IllegalArgumentException("the empty byte string is not a supported option".toString());
            n3 = 0;
            while (n3 < arrn.size()) {
                object3 = arrn.get(n3);
                n4 = n = n3 + 1;
                while (n4 < arrn.size() && ((ByteString)(object2 = arrn.get(n4))).startsWith((ByteString)object3)) {
                    boolean bl = ((ByteString)object2).size() != ((ByteString)object3).size();
                    if (!bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("duplicate option: ");
                        ((StringBuilder)object).append(object2);
                        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                    }
                    if (((Number)object4.get(n4)).intValue() > ((Number)object4.get(n3)).intValue()) {
                        arrn.remove(n4);
                        object4.remove(n4);
                        continue;
                    }
                    ++n4;
                }
                n3 = n;
            }
            object2 = new Buffer();
            object3 = this;
            Companion.buildTrieRecursive$default((Companion)object3, 0L, (Buffer)object2, 0, arrn, 0, 0, (List)object4, 53, null);
            arrn = new int[(int)Companion.super.getIntCount((Buffer)object2)];
            n = n2;
            do {
                if (((Buffer)object2).exhausted()) {
                    object = Arrays.copyOf(object, ((ByteString[])object).length);
                    Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.copyOf(this, size)");
                    return new Options((ByteString[])object, arrn, null);
                }
                arrn[n] = ((Buffer)object2).readInt();
                ++n;
            } while (true);
        }
    }

}

