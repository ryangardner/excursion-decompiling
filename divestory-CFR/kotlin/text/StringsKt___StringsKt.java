/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.StringsKt___StringsKt$asIterable$
 *  kotlin.text.StringsKt___StringsKt$asIterable$$inlined
 *  kotlin.text.StringsKt___StringsKt$asIterable$$inlined$Iterable
 *  kotlin.text.StringsKt___StringsKt$asSequence$
 *  kotlin.text.StringsKt___StringsKt$asSequence$$inlined
 *  kotlin.text.StringsKt___StringsKt$asSequence$$inlined$Sequence
 *  kotlin.text.StringsKt___StringsKt$chunkedSequence
 *  kotlin.text.StringsKt___StringsKt$groupingBy
 *  kotlin.text.StringsKt___StringsKt$windowed
 *  kotlin.text.StringsKt___StringsKt$windowedSequence
 *  kotlin.text.StringsKt___StringsKt$withIndex
 */
package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt___StringsJvmKt;
import kotlin.text.StringsKt___StringsKt;
import kotlin.text.StringsKt___StringsKt$asIterable$;
import kotlin.text.StringsKt___StringsKt$asSequence$;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00dc\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u00020\u0005\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b\u00a2\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u00a2\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b\u00a2\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b\u00a2\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"\u00a2\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u00a2\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r\u00a2\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r\u00a2\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\u0000\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b\u00a2\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a\u0014\u0010~\u001a\u0004\u0018\u00010\u0005*\u00020\u0002H\u0087\b\u00a2\u0006\u0002\u0010C\u001a\u001b\u0010~\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u00a2\u0006\u0002\u0010\u001a7\u0010\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a?\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0087\b\u00a2\u0006\u0003\u0010\u0083\u0001\u001a7\u0010\u0084\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0085\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a?\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0087\b\u00a2\u0006\u0003\u0010\u0083\u0001\u001a\u000b\u0010\u0087\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0087\u0001\u001a\u00020 *\u00020 H\u0087\b\u001aQ\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0087\b\u00a2\u0006\u0003\u0010\u0089\u0001\u001af\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0087\b\u00a2\u0006\u0003\u0010\u008b\u0001\u001a=\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0087\b\u001aR\u0010\u008d\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0087\b\u001a\u000b\u0010\u008e\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u008e\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a)\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a\u001a\u0010\u0090\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0090\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0091\u0001\u001a\u00030\u0092\u0001\u001a\u001d\u0010\u0090\u0001\u001a\u00020 *\u00020 2\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0090\u0001\u001a\u00020 *\u00020 2\b\u0010\u0091\u0001\u001a\u00030\u0092\u0001\u001a\"\u0010\u0093\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0094\u0001\u001a\u00030\u0095\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u0095\u00010\u0004H\u0086\b\u001a\u0013\u0010\u0096\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0096\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0097\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0097\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u0098\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0098\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0099\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0099\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u009a\u0001\u001a\u0002H6\"\u0010\b\u0000\u00106*\n\u0012\u0006\b\u0000\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6\u00a2\u0006\u0003\u0010\u009b\u0001\u001a\u001d\u0010\u009c\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u009d\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u009e\u0001*\u00020\u0002\u001a\u0011\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010\u00a0\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010\u00a1\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u00a2\u0001*\u00020\u0002\u001a1\u0010\u00a3\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u00a4\u0001\u001a\u00020\"2\t\b\u0002\u0010\u00a5\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u00a3\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u00a4\u0001\u001a\u00020\"2\t\b\u0002\u0010\u00a5\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010\u00a6\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u00a4\u0001\u001a\u00020\"2\t\b\u0002\u0010\u00a5\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u00a6\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u00a4\u0001\u001a\u00020\"2\t\b\u0002\u0010\u00a5\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010\u00a7\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u00a8\u00010\b*\u00020\u0002\u001a)\u0010\u00a9\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010\u00aa\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u00a9\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u00aa\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00ab\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00ac\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010\u00ad\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010\u00ad\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00ab\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00ac\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b\u00a8\u0006\u00ae\u0001"}, d2={"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "randomOrNull", "(Ljava/lang/CharSequence;Lkotlin/random/Random;)Ljava/lang/Character;", "reduce", "reduceIndexed", "reduceOrNull", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function2;)Ljava/lang/Character;", "reduceRight", "reduceRightIndexed", "reduceRightOrNull", "reversed", "scan", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "scanIndexed", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/util/List;", "scanReduce", "scanReduceIndexed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt___StringsKt
extends StringsKt___StringsJvmKt {
    public static final boolean all(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$all");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static final boolean any(CharSequence charSequence) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$any");
        if (charSequence.length() == 0) {
            bl = true;
            return bl ^ true;
        }
        bl = false;
        return bl ^ true;
    }

    public static final boolean any(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$any");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            if (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static final Iterable<Character> asIterable(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$asIterable");
        if (!(charSequence instanceof String)) return new Iterable<Character>(charSequence){
            final /* synthetic */ CharSequence $this_asIterable$inlined;
            {
                this.$this_asIterable$inlined = charSequence;
            }

            public Iterator<Character> iterator() {
                return StringsKt.iterator(this.$this_asIterable$inlined);
            }
        };
        boolean bl = charSequence.length() == 0;
        if (!bl) return new /* invalid duplicate definition of identical inner class */;
        return CollectionsKt.emptyList();
    }

    public static final Sequence<Character> asSequence(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$asSequence");
        if (!(charSequence instanceof String)) return new Sequence<Character>(charSequence){
            final /* synthetic */ CharSequence $this_asSequence$inlined;
            {
                this.$this_asSequence$inlined = charSequence;
            }

            public Iterator<Character> iterator() {
                return StringsKt.iterator(this.$this_asSequence$inlined);
            }
        };
        boolean bl = charSequence.length() == 0;
        if (!bl) return new /* invalid duplicate definition of identical inner class */;
        return SequencesKt.emptySequence();
    }

    public static final <K, V> Map<K, V> associate(CharSequence charSequence, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associate");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        Map map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(charSequence.length()), 16));
        int n = 0;
        while (n < charSequence.length()) {
            Pair<K, V> pair = function1.invoke(Character.valueOf(charSequence.charAt(n)));
            map.put(pair.getFirst(), pair.getSecond());
            ++n;
        }
        return map;
    }

    public static final <K> Map<K, Character> associateBy(CharSequence charSequence, Function1<? super Character, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Map map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(charSequence.length()), 16));
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            map.put(function1.invoke(Character.valueOf(c)), Character.valueOf(c));
            ++n;
        }
        return map;
    }

    public static final <K, V> Map<K, V> associateBy(CharSequence charSequence, Function1<? super Character, ? extends K> function1, Function1<? super Character, ? extends V> function12) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Intrinsics.checkParameterIsNotNull(function12, "valueTransform");
        Map map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(charSequence.length()), 16));
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            map.put(function1.invoke(Character.valueOf(c)), function12.invoke(Character.valueOf(c)));
            ++n;
        }
        return map;
    }

    public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(CharSequence charSequence, M m, Function1<? super Character, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            m.put(function1.invoke(Character.valueOf(c)), (Character)Character.valueOf(c));
            ++n;
        }
        return m;
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(CharSequence charSequence, M m, Function1<? super Character, ? extends K> function1, Function1<? super Character, ? extends V> function12) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Intrinsics.checkParameterIsNotNull(function12, "valueTransform");
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            m.put(function1.invoke(Character.valueOf(c)), function12.invoke(Character.valueOf(c)));
            ++n;
        }
        return m;
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(CharSequence charSequence, M m, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        int n = 0;
        while (n < charSequence.length()) {
            Pair<K, V> pair = function1.invoke(Character.valueOf(charSequence.charAt(n)));
            m.put(pair.getFirst(), pair.getSecond());
            ++n;
        }
        return m;
    }

    public static final <V> Map<Character, V> associateWith(CharSequence charSequence, Function1<? super Character, ? extends V> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateWith");
        Intrinsics.checkParameterIsNotNull(function1, "valueSelector");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(charSequence.length()), 16));
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            ((Map)linkedHashMap).put(Character.valueOf(c), function1.invoke(Character.valueOf(c)));
            ++n;
        }
        return linkedHashMap;
    }

    public static final <V, M extends Map<? super Character, ? super V>> M associateWithTo(CharSequence charSequence, M m, Function1<? super Character, ? extends V> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$associateWithTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "valueSelector");
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            m.put((Character)Character.valueOf(c), function1.invoke(Character.valueOf(c)));
            ++n;
        }
        return m;
    }

    public static final List<String> chunked(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$chunked");
        return StringsKt.windowed(charSequence, n, n, true);
    }

    public static final <R> List<R> chunked(CharSequence charSequence, int n, Function1<? super CharSequence, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$chunked");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        return StringsKt.windowed(charSequence, n, n, true, function1);
    }

    public static final Sequence<String> chunkedSequence(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$chunkedSequence");
        return StringsKt.chunkedSequence(charSequence, n, chunkedSequence.1.INSTANCE);
    }

    public static final <R> Sequence<R> chunkedSequence(CharSequence charSequence, int n, Function1<? super CharSequence, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$chunkedSequence");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        return StringsKt.windowedSequence(charSequence, n, n, true, function1);
    }

    private static final int count(CharSequence charSequence) {
        return charSequence.length();
    }

    public static final int count(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$count");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            int n3 = n2;
            if (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    public static final CharSequence drop(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$drop");
        boolean bl = n >= 0;
        if (bl) {
            return charSequence.subSequence(RangesKt.coerceAtMost(n, charSequence.length()), charSequence.length());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final String drop(String charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$drop");
        boolean bl = n >= 0;
        if (bl) {
            charSequence = ((String)charSequence).substring(RangesKt.coerceAtMost(n, ((String)charSequence).length()));
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.String).substring(startIndex)");
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final CharSequence dropLast(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$dropLast");
        boolean bl = n >= 0;
        if (bl) {
            return StringsKt.take(charSequence, RangesKt.coerceAtLeast(charSequence.length() - n, 0));
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final String dropLast(String charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$dropLast");
        boolean bl = n >= 0;
        if (bl) {
            return StringsKt.take((String)charSequence, RangesKt.coerceAtLeast(((String)charSequence).length() - n, 0));
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final CharSequence dropLastWhile(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = StringsKt.getLastIndex(charSequence);
        while (n >= 0) {
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return charSequence.subSequence(0, n + 1);
            }
            --n;
        }
        return "";
    }

    public static final String dropLastWhile(String string2, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = StringsKt.getLastIndex(string2);
        while (n >= 0) {
            if (!function1.invoke(Character.valueOf(string2.charAt(n))).booleanValue()) {
                string2 = string2.substring(0, n + 1);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return string2;
            }
            --n;
        }
        return "";
    }

    public static final CharSequence dropWhile(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n2))).booleanValue()) {
                return charSequence.subSequence(n2, charSequence.length());
            }
            ++n2;
        }
        return "";
    }

    public static final String dropWhile(String string2, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = ((CharSequence)string2).length();
        int n2 = 0;
        while (n2 < n) {
            if (!function1.invoke(Character.valueOf(string2.charAt(n2))).booleanValue()) {
                string2 = string2.substring(n2);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                return string2;
            }
            ++n2;
        }
        return "";
    }

    private static final char elementAtOrElse(CharSequence charSequence, int n, Function1<? super Integer, Character> function1) {
        if (n >= 0 && n <= StringsKt.getLastIndex(charSequence)) {
            n = charSequence.charAt(n);
            return (char)n;
        }
        n = function1.invoke((Integer)n).charValue();
        return (char)n;
    }

    private static final Character elementAtOrNull(CharSequence charSequence, int n) {
        return StringsKt.getOrNull(charSequence, n);
    }

    public static final CharSequence filter(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filter");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        Appendable appendable = new StringBuilder();
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            char c = charSequence.charAt(n2);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                appendable.append(c);
            }
            ++n2;
        }
        return (CharSequence)((Object)appendable);
    }

    public static final String filter(String object, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(object, "$this$filter");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        CharSequence charSequence = (CharSequence)object;
        object = new StringBuilder();
        int n = charSequence.length();
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = ((StringBuilder)object).toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "filterTo(StringBuilder(), predicate).toString()");
                return object;
            }
            char c = charSequence.charAt(n2);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                object.append(c);
            }
            ++n2;
        } while (true);
    }

    public static final CharSequence filterIndexed(CharSequence charSequence, Function2<? super Integer, ? super Character, Boolean> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(function2, "predicate");
        Appendable appendable = new StringBuilder();
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            if (function2.invoke((Integer)n2, Character.valueOf(c)).booleanValue()) {
                appendable.append(c);
            }
            ++n;
            ++n2;
        }
        return (CharSequence)((Object)appendable);
    }

    public static final String filterIndexed(String charSequence, Function2<? super Integer, ? super Character, Boolean> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(function2, "predicate");
        charSequence = charSequence;
        Appendable appendable = new StringBuilder();
        int n = 0;
        int n2 = 0;
        do {
            if (n >= charSequence.length()) {
                charSequence = ((StringBuilder)appendable).toString();
                Intrinsics.checkExpressionValueIsNotNull(charSequence, "filterIndexedTo(StringBu\u2026(), predicate).toString()");
                return charSequence;
            }
            char c = charSequence.charAt(n);
            if (function2.invoke((Integer)n2, Character.valueOf(c)).booleanValue()) {
                appendable.append(c);
            }
            ++n;
            ++n2;
        } while (true);
    }

    public static final <C extends Appendable> C filterIndexedTo(CharSequence charSequence, C c, Function2<? super Integer, ? super Character, Boolean> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterIndexedTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function2, "predicate");
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            char c2 = charSequence.charAt(n);
            if (function2.invoke((Integer)n2, Character.valueOf(c2)).booleanValue()) {
                c.append(c2);
            }
            ++n;
            ++n2;
        }
        return c;
    }

    public static final CharSequence filterNot(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        Appendable appendable = new StringBuilder();
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            if (!function1.invoke(Character.valueOf(c)).booleanValue()) {
                appendable.append(c);
            }
            ++n;
        }
        return (CharSequence)((Object)appendable);
    }

    public static final String filterNot(String charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        charSequence = charSequence;
        Appendable appendable = new StringBuilder();
        int n = 0;
        do {
            if (n >= charSequence.length()) {
                charSequence = ((StringBuilder)appendable).toString();
                Intrinsics.checkExpressionValueIsNotNull(charSequence, "filterNotTo(StringBuilder(), predicate).toString()");
                return charSequence;
            }
            char c = charSequence.charAt(n);
            if (!function1.invoke(Character.valueOf(c)).booleanValue()) {
                appendable.append(c);
            }
            ++n;
        } while (true);
    }

    public static final <C extends Appendable> C filterNotTo(CharSequence charSequence, C c, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterNotTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            char c2 = charSequence.charAt(n);
            if (!function1.invoke(Character.valueOf(c2)).booleanValue()) {
                c.append(c2);
            }
            ++n;
        }
        return c;
    }

    public static final <C extends Appendable> C filterTo(CharSequence charSequence, C c, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$filterTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            char c2 = charSequence.charAt(n2);
            if (function1.invoke(Character.valueOf(c2)).booleanValue()) {
                c.append(c2);
            }
            ++n2;
        }
        return c;
    }

    private static final Character find(CharSequence object, Function1<? super Character, Boolean> function1) {
        int n = 0;
        while (n < object.length()) {
            char c = object.charAt(n);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                return Character.valueOf(c);
            }
            ++n;
        }
        return null;
    }

    private static final Character findLast(CharSequence object, Function1<? super Character, Boolean> function1) {
        char c;
        int n = object.length();
        do {
            if (--n >= 0) continue;
            return null;
        } while (!function1.invoke(Character.valueOf(c = object.charAt(n))).booleanValue());
        return Character.valueOf(c);
    }

    public static final char first(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$first");
        boolean bl = charSequence.length() == 0;
        if (bl) throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        return charSequence.charAt(0);
    }

    public static final char first(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$first");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                return c;
            }
            ++n;
        }
        throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    public static final Character firstOrNull(CharSequence object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$firstOrNull");
        if (object.length() == 0) {
            return null;
        }
        boolean bl = false;
        if (!bl) return Character.valueOf(object.charAt(0));
        return null;
    }

    public static final Character firstOrNull(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$firstOrNull");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                return Character.valueOf(c);
            }
            ++n;
        }
        return null;
    }

    public static final <R> List<R> flatMap(CharSequence charSequence, Function1<? super Character, ? extends Iterable<? extends R>> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$flatMap");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        Collection collection = new ArrayList();
        int n = 0;
        while (n < charSequence.length()) {
            CollectionsKt.addAll(collection, function1.invoke(Character.valueOf(charSequence.charAt(n))));
            ++n;
        }
        return (List)collection;
    }

    public static final <R, C extends Collection<? super R>> C flatMapTo(CharSequence charSequence, C c, Function1<? super Character, ? extends Iterable<? extends R>> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$flatMapTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        int n = 0;
        while (n < charSequence.length()) {
            CollectionsKt.addAll(c, function1.invoke(Character.valueOf(charSequence.charAt(n))));
            ++n;
        }
        return c;
    }

    public static final <R> R fold(CharSequence charSequence, R r, Function2<? super R, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$fold");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = 0;
        while (n < charSequence.length()) {
            r = function2.invoke(r, Character.valueOf(charSequence.charAt(n)));
            ++n;
        }
        return r;
    }

    public static final <R> R foldIndexed(CharSequence charSequence, R r, Function3<? super Integer, ? super R, ? super Character, ? extends R> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$foldIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n2;
            if (n >= charSequence.length()) return r;
            char c = charSequence.charAt(n);
            n2 = n3 + 1;
            r = function3.invoke((Integer)n3, r, Character.valueOf(c));
            ++n;
        } while (true);
    }

    public static final <R> R foldRight(CharSequence charSequence, R r, Function2<? super Character, ? super R, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$foldRight");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = StringsKt.getLastIndex(charSequence);
        while (n >= 0) {
            r = function2.invoke(Character.valueOf(charSequence.charAt(n)), r);
            --n;
        }
        return r;
    }

    public static final <R> R foldRightIndexed(CharSequence charSequence, R r, Function3<? super Integer, ? super Character, ? super R, ? extends R> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$foldRightIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        int n = StringsKt.getLastIndex(charSequence);
        while (n >= 0) {
            r = function3.invoke((Integer)n, Character.valueOf(charSequence.charAt(n)), r);
            --n;
        }
        return r;
    }

    public static final void forEach(CharSequence charSequence, Function1<? super Character, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(function1, "action");
        int n = 0;
        while (n < charSequence.length()) {
            function1.invoke(Character.valueOf(charSequence.charAt(n)));
            ++n;
        }
    }

    public static final void forEachIndexed(CharSequence charSequence, Function2<? super Integer, ? super Character, Unit> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$forEachIndexed");
        Intrinsics.checkParameterIsNotNull(function2, "action");
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n2;
            if (n >= charSequence.length()) return;
            char c = charSequence.charAt(n);
            n2 = n3 + 1;
            function2.invoke((Integer)n3, Character.valueOf(c));
            ++n;
        } while (true);
    }

    private static final char getOrElse(CharSequence charSequence, int n, Function1<? super Integer, Character> function1) {
        if (n >= 0 && n <= StringsKt.getLastIndex(charSequence)) {
            n = charSequence.charAt(n);
            return (char)n;
        }
        n = function1.invoke((Integer)n).charValue();
        return (char)n;
    }

    public static final Character getOrNull(CharSequence object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "$this$getOrNull");
        if (n < 0) return null;
        if (n > StringsKt.getLastIndex((CharSequence)object)) return null;
        return Character.valueOf(object.charAt(n));
    }

    public static final <K> Map<K, List<Character>> groupBy(CharSequence charSequence, Function1<? super Character, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Map map = new LinkedHashMap();
        int n = 0;
        while (n < charSequence.length()) {
            Object v;
            char c = charSequence.charAt(n);
            K k = function1.invoke(Character.valueOf(c));
            Object object = v = map.get(k);
            if (v == null) {
                object = new ArrayList();
                map.put(k, object);
            }
            ((List)object).add(Character.valueOf(c));
            ++n;
        }
        return map;
    }

    public static final <K, V> Map<K, List<V>> groupBy(CharSequence charSequence, Function1<? super Character, ? extends K> function1, Function1<? super Character, ? extends V> function12) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Intrinsics.checkParameterIsNotNull(function12, "valueTransform");
        Map map = new LinkedHashMap();
        int n = 0;
        while (n < charSequence.length()) {
            Object v;
            char c = charSequence.charAt(n);
            K k = function1.invoke(Character.valueOf(c));
            Object object = v = map.get(k);
            if (v == null) {
                object = new ArrayList();
                map.put(k, object);
            }
            ((List)object).add(function12.invoke(Character.valueOf(c)));
            ++n;
        }
        return map;
    }

    public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(CharSequence charSequence, M m, Function1<? super Character, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        int n = 0;
        while (n < charSequence.length()) {
            List<Character> list;
            char c = charSequence.charAt(n);
            K k = function1.invoke(Character.valueOf(c));
            List<Character> list2 = list = m.get(k);
            if (list == null) {
                list2 = new ArrayList<Character>();
                m.put(k, list2);
            }
            list2.add(Character.valueOf(c));
            ++n;
        }
        return m;
    }

    public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(CharSequence charSequence, M m, Function1<? super Character, ? extends K> function1, Function1<? super Character, ? extends V> function12) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        Intrinsics.checkParameterIsNotNull(function12, "valueTransform");
        int n = 0;
        while (n < charSequence.length()) {
            List<V> list;
            char c = charSequence.charAt(n);
            K k = function1.invoke(Character.valueOf(c));
            List<V> list2 = list = m.get(k);
            if (list == null) {
                list2 = new ArrayList<V>();
                m.put(k, list2);
            }
            list2.add(function12.invoke(Character.valueOf(c)));
            ++n;
        }
        return m;
    }

    public static final <K> Grouping<Character, K> groupingBy(CharSequence charSequence, Function1<? super Character, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$groupingBy");
        Intrinsics.checkParameterIsNotNull(function1, "keySelector");
        return new Grouping<Character, K>(charSequence, function1){
            final /* synthetic */ Function1 $keySelector;
            final /* synthetic */ CharSequence $this_groupingBy;
            {
                this.$this_groupingBy = charSequence;
                this.$keySelector = function1;
            }

            public K keyOf(char c) {
                return (K)this.$keySelector.invoke(Character.valueOf(c));
            }

            public Iterator<Character> sourceIterator() {
                return StringsKt.iterator(this.$this_groupingBy);
            }
        };
    }

    public static final int indexOfFirst(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indexOfFirst");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (function1.invoke(Character.valueOf(charSequence.charAt(n2))).booleanValue()) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static final int indexOfLast(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indexOfLast");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length() - 1;
        while (n >= 0) {
            if (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return n;
            }
            --n;
        }
        return -1;
    }

    public static final char last(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$last");
        boolean bl = charSequence.length() == 0;
        if (bl) throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        return charSequence.charAt(StringsKt.getLastIndex(charSequence));
    }

    public static final char last(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        char c;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$last");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        do {
            if (--n < 0) throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
        } while (!function1.invoke(Character.valueOf(c = charSequence.charAt(n))).booleanValue());
        return c;
    }

    public static final Character lastOrNull(CharSequence object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$lastOrNull");
        if (object.length() == 0) {
            return null;
        }
        boolean bl = false;
        if (!bl) return Character.valueOf(object.charAt(object.length() - 1));
        return null;
    }

    public static final Character lastOrNull(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        char c;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lastOrNull");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        do {
            if (--n < 0) return null;
        } while (!function1.invoke(Character.valueOf(c = charSequence.charAt(n))).booleanValue());
        return Character.valueOf(c);
    }

    public static final <R> List<R> map(CharSequence charSequence, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$map");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        Collection collection = new ArrayList(charSequence.length());
        int n = 0;
        while (n < charSequence.length()) {
            collection.add(function1.invoke(Character.valueOf(charSequence.charAt(n))));
            ++n;
        }
        return (List)collection;
    }

    public static final <R> List<R> mapIndexed(CharSequence charSequence, Function2<? super Integer, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapIndexed");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        Collection collection = new ArrayList(charSequence.length());
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n2;
            if (n >= charSequence.length()) return (List)collection;
            char c = charSequence.charAt(n);
            n2 = n3 + 1;
            collection.add(function2.invoke((Integer)n3, Character.valueOf(c)));
            ++n;
        } while (true);
    }

    public static final <R> List<R> mapIndexedNotNull(CharSequence charSequence, Function2<? super Integer, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapIndexedNotNull");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        Collection collection = new ArrayList();
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            R r = function2.invoke((Integer)n2, Character.valueOf(charSequence.charAt(n)));
            if (r != null) {
                collection.add(r);
            }
            ++n;
            ++n2;
        }
        return (List)collection;
    }

    public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(CharSequence charSequence, C c, Function2<? super Integer, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapIndexedNotNullTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            R r = function2.invoke((Integer)n2, Character.valueOf(charSequence.charAt(n)));
            if (r != null) {
                c.add(r);
            }
            ++n;
            ++n2;
        }
        return c;
    }

    public static final <R, C extends Collection<? super R>> C mapIndexedTo(CharSequence charSequence, C c, Function2<? super Integer, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapIndexedTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n2;
            if (n >= charSequence.length()) return c;
            char c2 = charSequence.charAt(n);
            n2 = n3 + 1;
            c.add(function2.invoke((Integer)n3, Character.valueOf(c2)));
            ++n;
        } while (true);
    }

    public static final <R> List<R> mapNotNull(CharSequence charSequence, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapNotNull");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        Collection collection = new ArrayList();
        int n = 0;
        while (n < charSequence.length()) {
            R r = function1.invoke(Character.valueOf(charSequence.charAt(n)));
            if (r != null) {
                collection.add(r);
            }
            ++n;
        }
        return (List)collection;
    }

    public static final <R, C extends Collection<? super R>> C mapNotNullTo(CharSequence charSequence, C c, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapNotNullTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        int n = 0;
        while (n < charSequence.length()) {
            R r = function1.invoke(Character.valueOf(charSequence.charAt(n)));
            if (r != null) {
                c.add(r);
            }
            ++n;
        }
        return c;
    }

    public static final <R, C extends Collection<? super R>> C mapTo(CharSequence charSequence, C c, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$mapTo");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        int n = 0;
        while (n < charSequence.length()) {
            c.add(function1.invoke(Character.valueOf(charSequence.charAt(n))));
            ++n;
        }
        return c;
    }

    public static final Character max(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$max");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n2 = n3;
        do {
            char c = charSequence.charAt(n);
            n3 = n2;
            if (n2 < c) {
                n3 = c;
            }
            n5 = n3;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            n2 = n3;
        } while (true);
    }

    public static final <R extends Comparable<? super R>> Character maxBy(CharSequence charSequence, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$maxBy");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        if (n4 == 0) {
            return Character.valueOf((char)n3);
        }
        Comparable comparable = (Comparable)function1.invoke(Character.valueOf((char)n3));
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n2 = n3;
        do {
            n5 = charSequence.charAt(n);
            Comparable comparable2 = (Comparable)function1.invoke(Character.valueOf((char)n5));
            Comparable comparable3 = comparable;
            if (comparable.compareTo(comparable2) < 0) {
                n2 = n5;
                comparable3 = comparable2;
            }
            n5 = n2;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            comparable = comparable3;
        } while (true);
    }

    public static final Character maxWith(CharSequence charSequence, Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$maxWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n5 = n3;
        do {
            char c = charSequence.charAt(n);
            n2 = n5;
            if (comparator.compare(Character.valueOf((char)n5), Character.valueOf(c)) < 0) {
                n2 = c;
            }
            n5 = n2;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            n5 = n2;
        } while (true);
    }

    public static final Character min(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$min");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n2 = n3;
        do {
            char c = charSequence.charAt(n);
            n3 = n2;
            if (n2 > c) {
                n3 = c;
            }
            n5 = n3;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            n2 = n3;
        } while (true);
    }

    public static final <R extends Comparable<? super R>> Character minBy(CharSequence charSequence, Function1<? super Character, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$minBy");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        if (n4 == 0) {
            return Character.valueOf((char)n3);
        }
        Comparable comparable = (Comparable)function1.invoke(Character.valueOf((char)n3));
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n2 = n3;
        do {
            n3 = charSequence.charAt(n);
            Comparable comparable2 = (Comparable)function1.invoke(Character.valueOf((char)n3));
            Comparable comparable3 = comparable;
            if (comparable.compareTo(comparable2) > 0) {
                n2 = n3;
                comparable3 = comparable2;
            }
            n5 = n2;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            comparable = comparable3;
        } while (true);
    }

    public static final Character minWith(CharSequence charSequence, Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$minWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n5 = n3;
        do {
            char c = charSequence.charAt(n);
            n2 = n5;
            if (comparator.compare(Character.valueOf((char)n5), Character.valueOf(c)) > 0) {
                n2 = c;
            }
            n5 = n2;
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            n5 = n2;
        } while (true);
    }

    public static final boolean none(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$none");
        if (charSequence.length() != 0) return false;
        return true;
    }

    public static final boolean none(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$none");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = 0;
        while (n < charSequence.length()) {
            if (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static final <S extends CharSequence> S onEach(S s, Function1<? super Character, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(s, "$this$onEach");
        Intrinsics.checkParameterIsNotNull(function1, "action");
        int n = 0;
        while (n < s.length()) {
            function1.invoke(Character.valueOf(s.charAt(n)));
            ++n;
        }
        return s;
    }

    public static final Pair<CharSequence, CharSequence> partition(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$partition");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        int n = 0;
        while (n < charSequence.length()) {
            char c = charSequence.charAt(n);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                stringBuilder.append(c);
            } else {
                stringBuilder2.append(c);
            }
            ++n;
        }
        return new Pair<CharSequence, CharSequence>(stringBuilder, stringBuilder2);
    }

    public static final Pair<String, String> partition(String string2, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$partition");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (function1.invoke(Character.valueOf(c)).booleanValue()) {
                stringBuilder.append(c);
            } else {
                stringBuilder2.append(c);
            }
            ++n2;
        }
        return new Pair<String, String>(stringBuilder.toString(), stringBuilder2.toString());
    }

    private static final char random(CharSequence charSequence) {
        return StringsKt.random(charSequence, Random.Default);
    }

    public static final char random(CharSequence charSequence, Random random) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$random");
        Intrinsics.checkParameterIsNotNull(random, "random");
        boolean bl = charSequence.length() == 0;
        if (bl) throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        return charSequence.charAt(random.nextInt(charSequence.length()));
    }

    private static final Character randomOrNull(CharSequence charSequence) {
        return StringsKt.randomOrNull(charSequence, Random.Default);
    }

    public static final Character randomOrNull(CharSequence charSequence, Random random) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$randomOrNull");
        Intrinsics.checkParameterIsNotNull(random, "random");
        boolean bl = charSequence.length() == 0;
        if (!bl) return Character.valueOf(charSequence.charAt(random.nextInt(charSequence.length())));
        return null;
    }

    public static final char reduce(CharSequence charSequence, Function2<? super Character, ? super Character, Character> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduce");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = charSequence.length();
        int n2 = 1;
        n = n == 0 ? 1 : 0;
        if (n != 0) throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return (char)n5;
        n = n2;
        n5 = n3;
        do {
            n5 = n2 = (int)function2.invoke(Character.valueOf((char)n5), Character.valueOf(charSequence.charAt(n))).charValue();
            if (n == n4) return (char)n5;
            ++n;
            n5 = n2;
        } while (true);
    }

    public static final char reduceIndexed(CharSequence charSequence, Function3<? super Integer, ? super Character, ? super Character, Character> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduceIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        int n = charSequence.length();
        int n2 = 1;
        n = n == 0 ? 1 : 0;
        if (n != 0) throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return (char)n5;
        n = n2;
        n5 = n3;
        do {
            n5 = n2 = (int)function3.invoke((Integer)n, Character.valueOf((char)n5), Character.valueOf(charSequence.charAt(n))).charValue();
            if (n == n4) return (char)n5;
            ++n;
            n5 = n2;
        } while (true);
    }

    public static final Character reduceOrNull(CharSequence charSequence, Function2<? super Character, ? super Character, Character> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduceOrNull");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = charSequence.length();
        int n2 = 1;
        if (n == 0) {
            return null;
        }
        n = 0;
        if (n != 0) {
            return null;
        }
        int n3 = charSequence.charAt(0);
        int n4 = StringsKt.getLastIndex(charSequence);
        int n5 = n3;
        if (1 > n4) return Character.valueOf((char)n5);
        n = n2;
        n5 = n3;
        do {
            n5 = n2 = (int)function2.invoke(Character.valueOf((char)n5), Character.valueOf(charSequence.charAt(n))).charValue();
            if (n == n4) return Character.valueOf((char)n5);
            ++n;
            n5 = n2;
        } while (true);
    }

    public static final char reduceRight(CharSequence charSequence, Function2<? super Character, ? super Character, Character> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduceRight");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        char c = StringsKt.getLastIndex(charSequence);
        if (c < '\u0000') throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        int n = c - 1;
        char c2 = c = (char)charSequence.charAt(c);
        while (n >= 0) {
            c = function2.invoke(Character.valueOf(charSequence.charAt(n)), Character.valueOf(c2)).charValue();
            --n;
            c2 = c;
        }
        return c2;
    }

    public static final char reduceRightIndexed(CharSequence charSequence, Function3<? super Integer, ? super Character, ? super Character, Character> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduceRightIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        char c = StringsKt.getLastIndex(charSequence);
        if (c < '\u0000') throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        int n = c - 1;
        char c2 = c = (char)charSequence.charAt(c);
        while (n >= 0) {
            c = function3.invoke((Integer)n, Character.valueOf(charSequence.charAt(n)), Character.valueOf(c2)).charValue();
            --n;
            c2 = c;
        }
        return c2;
    }

    public static final Character reduceRightOrNull(CharSequence charSequence, Function2<? super Character, ? super Character, Character> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reduceRightOrNull");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        char c = StringsKt.getLastIndex(charSequence);
        if (c < '\u0000') {
            return null;
        }
        int n = c - 1;
        char c2 = c = (char)charSequence.charAt(c);
        while (n >= 0) {
            c = function2.invoke(Character.valueOf(charSequence.charAt(n)), Character.valueOf(c2)).charValue();
            --n;
            c2 = c;
        }
        return Character.valueOf(c2);
    }

    public static final CharSequence reversed(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$reversed");
        charSequence = new StringBuilder(charSequence).reverse();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder(this).reverse()");
        return charSequence;
    }

    private static final String reversed(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.reversed((CharSequence)string2)).toString();
    }

    public static final <R> List<R> scan(CharSequence charSequence, R r, Function2<? super R, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$scan");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = charSequence.length();
        int n2 = 0;
        n = n == 0 ? 1 : 0;
        if (n != 0) {
            return CollectionsKt.listOf(r);
        }
        ArrayList<R> arrayList = new ArrayList<R>(charSequence.length() + 1);
        arrayList.add(r);
        n = n2;
        while (n < charSequence.length()) {
            r = function2.invoke(r, Character.valueOf(charSequence.charAt(n)));
            arrayList.add(r);
            ++n;
        }
        return arrayList;
    }

    public static final <R> List<R> scanIndexed(CharSequence charSequence, R r, Function3<? super Integer, ? super R, ? super Character, ? extends R> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$scanIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        int n = charSequence.length();
        int n2 = 0;
        n = n == 0 ? 1 : 0;
        if (n != 0) {
            return CollectionsKt.listOf(r);
        }
        ArrayList<R> arrayList = new ArrayList<R>(charSequence.length() + 1);
        arrayList.add(r);
        int n3 = charSequence.length();
        n = n2;
        while (n < n3) {
            r = function3.invoke((Integer)n, r, Character.valueOf(charSequence.charAt(n)));
            arrayList.add(r);
            ++n;
        }
        return arrayList;
    }

    public static final List<Character> scanReduce(CharSequence charSequence, Function2<? super Character, ? super Character, Character> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$scanReduce");
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        int n = charSequence.length();
        int n2 = 1;
        n = n == 0 ? 1 : 0;
        if (n != 0) {
            return CollectionsKt.emptyList();
        }
        char c = charSequence.charAt(0);
        ArrayList<Character> arrayList = new ArrayList<Character>(charSequence.length());
        arrayList.add(Character.valueOf(c));
        int n3 = charSequence.length();
        n = n2;
        while (n < n3) {
            c = function2.invoke(Character.valueOf(c), Character.valueOf(charSequence.charAt(n))).charValue();
            arrayList.add(Character.valueOf(c));
            ++n;
        }
        return arrayList;
    }

    public static final List<Character> scanReduceIndexed(CharSequence charSequence, Function3<? super Integer, ? super Character, ? super Character, Character> function3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$scanReduceIndexed");
        Intrinsics.checkParameterIsNotNull(function3, "operation");
        int n = charSequence.length();
        int n2 = 1;
        n = n == 0 ? 1 : 0;
        if (n != 0) {
            return CollectionsKt.emptyList();
        }
        char c = charSequence.charAt(0);
        ArrayList<Character> arrayList = new ArrayList<Character>(charSequence.length());
        arrayList.add(Character.valueOf(c));
        int n3 = charSequence.length();
        n = n2;
        while (n < n3) {
            c = function3.invoke((Integer)n, Character.valueOf(c), Character.valueOf(charSequence.charAt(n))).charValue();
            arrayList.add(Character.valueOf(c));
            ++n;
        }
        return arrayList;
    }

    public static final char single(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$single");
        int n = charSequence.length();
        if (n == 0) throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        if (n != 1) throw (Throwable)new IllegalArgumentException("Char sequence has more than one element.");
        return charSequence.charAt(0);
    }

    public static final char single(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$single");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        Character c = null;
        int n = 0;
        boolean bl = false;
        do {
            if (n >= charSequence.length()) {
                if (!bl) throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
                if (c == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
                return c.charValue();
            }
            char c2 = charSequence.charAt(n);
            boolean bl2 = bl;
            if (function1.invoke(Character.valueOf(c2)).booleanValue()) {
                if (bl) throw (Throwable)new IllegalArgumentException("Char sequence contains more than one matching element.");
                c = Character.valueOf(c2);
                bl2 = true;
            }
            ++n;
            bl = bl2;
        } while (true);
    }

    public static final Character singleOrNull(CharSequence object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$singleOrNull");
        if (object.length() != 1) return null;
        return Character.valueOf(object.charAt(0));
    }

    public static final Character singleOrNull(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$singleOrNull");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        Character c = null;
        int n = 0;
        boolean bl = false;
        do {
            if (n >= charSequence.length()) {
                if (bl) return c;
                return null;
            }
            char c2 = charSequence.charAt(n);
            boolean bl2 = bl;
            if (function1.invoke(Character.valueOf(c2)).booleanValue()) {
                if (bl) {
                    return null;
                }
                c = Character.valueOf(c2);
                bl2 = true;
            }
            ++n;
            bl = bl2;
        } while (true);
    }

    public static final CharSequence slice(CharSequence charSequence, Iterable<Integer> object) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$slice");
        Intrinsics.checkParameterIsNotNull(object, "indices");
        int n = CollectionsKt.collectionSizeOrDefault(object, 10);
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        object = object.iterator();
        while (object.hasNext()) {
            stringBuilder.append(charSequence.charAt(((Number)object.next()).intValue()));
        }
        return stringBuilder;
    }

    public static final CharSequence slice(CharSequence charSequence, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$slice");
        Intrinsics.checkParameterIsNotNull(intRange, "indices");
        if (!intRange.isEmpty()) return StringsKt.subSequence(charSequence, intRange);
        return "";
    }

    private static final String slice(String string2, Iterable<Integer> iterable) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.slice((CharSequence)string2, iterable)).toString();
    }

    public static final String slice(String string2, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$slice");
        Intrinsics.checkParameterIsNotNull(intRange, "indices");
        if (!intRange.isEmpty()) return StringsKt.substring(string2, intRange);
        return "";
    }

    public static final int sumBy(CharSequence charSequence, Function1<? super Character, Integer> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$sumBy");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            n2 += ((Number)function1.invoke(Character.valueOf(charSequence.charAt(n)))).intValue();
            ++n;
        }
        return n2;
    }

    public static final double sumByDouble(CharSequence charSequence, Function1<? super Character, Double> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$sumByDouble");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        double d = 0.0;
        int n = 0;
        while (n < charSequence.length()) {
            d += ((Number)function1.invoke(Character.valueOf(charSequence.charAt(n)))).doubleValue();
            ++n;
        }
        return d;
    }

    public static final CharSequence take(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$take");
        boolean bl = n >= 0;
        if (bl) {
            return charSequence.subSequence(0, RangesKt.coerceAtMost(n, charSequence.length()));
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final String take(String charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$take");
        boolean bl = n >= 0;
        if (bl) {
            charSequence = ((String)charSequence).substring(0, RangesKt.coerceAtMost(n, ((String)charSequence).length()));
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final CharSequence takeLast(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$takeLast");
        int n2 = n >= 0 ? 1 : 0;
        if (n2 != 0) {
            n2 = charSequence.length();
            return charSequence.subSequence(n2 - RangesKt.coerceAtMost(n, n2), n2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final String takeLast(String charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$takeLast");
        int n2 = n >= 0 ? 1 : 0;
        if (n2 != 0) {
            n2 = ((String)charSequence).length();
            charSequence = ((String)charSequence).substring(n2 - RangesKt.coerceAtMost(n, n2));
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "(this as java.lang.String).substring(startIndex)");
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested character count ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is less than zero.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    public static final CharSequence takeLastWhile(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = StringsKt.getLastIndex(charSequence);
        while (n >= 0) {
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue()) {
                return charSequence.subSequence(n + 1, charSequence.length());
            }
            --n;
        }
        return charSequence.subSequence(0, charSequence.length());
    }

    public static final String takeLastWhile(String string2, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = StringsKt.getLastIndex(string2);
        while (n >= 0) {
            if (!function1.invoke(Character.valueOf(string2.charAt(n))).booleanValue()) {
                string2 = string2.substring(n + 1);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                return string2;
            }
            --n;
        }
        return string2;
    }

    public static final CharSequence takeWhile(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n2))).booleanValue()) {
                return charSequence.subSequence(0, n2);
            }
            ++n2;
        }
        return charSequence.subSequence(0, charSequence.length());
    }

    public static final String takeWhile(String string2, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            if (!function1.invoke(Character.valueOf(string2.charAt(n2))).booleanValue()) {
                string2 = string2.substring(0, n2);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return string2;
            }
            ++n2;
        }
        return string2;
    }

    public static final <C extends Collection<? super Character>> C toCollection(CharSequence charSequence, C c) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$toCollection");
        Intrinsics.checkParameterIsNotNull(c, "destination");
        int n = 0;
        while (n < charSequence.length()) {
            c.add((Character)Character.valueOf(charSequence.charAt(n)));
            ++n;
        }
        return c;
    }

    public static final HashSet<Character> toHashSet(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$toHashSet");
        return (HashSet)StringsKt.toCollection(charSequence, (Collection)new HashSet(MapsKt.mapCapacity(charSequence.length())));
    }

    public static final List<Character> toList(CharSequence list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$toList");
        int n = list.length();
        if (n == 0) {
            return CollectionsKt.emptyList();
        }
        if (n == 1) return CollectionsKt.listOf(Character.valueOf(list.charAt(0)));
        return StringsKt.toMutableList((CharSequence)((Object)list));
    }

    public static final List<Character> toMutableList(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$toMutableList");
        return (List)StringsKt.toCollection(charSequence, (Collection)new ArrayList(charSequence.length()));
    }

    public static final Set<Character> toSet(CharSequence set) {
        Intrinsics.checkParameterIsNotNull(set, "$this$toSet");
        int n = set.length();
        if (n == 0) {
            return SetsKt.emptySet();
        }
        if (n == 1) return SetsKt.setOf(Character.valueOf(set.charAt(0)));
        return (Set)StringsKt.toCollection(set, (Collection)new LinkedHashSet(MapsKt.mapCapacity(set.length())));
    }

    public static final List<String> windowed(CharSequence charSequence, int n, int n2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$windowed");
        return StringsKt.windowed(charSequence, n, n2, bl, windowed.1.INSTANCE);
    }

    public static final <R> List<R> windowed(CharSequence charSequence, int n, int n2, boolean bl, Function1<? super CharSequence, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$windowed");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        SlidingWindowKt.checkWindowSizeStep(n, n2);
        int n3 = charSequence.length();
        int n4 = n3 / n2;
        int n5 = 0;
        int n6 = n3 % n2 == 0 ? 0 : 1;
        ArrayList<R> arrayList = new ArrayList<R>(n4 + n6);
        n6 = n5;
        do {
            block6 : {
                block5 : {
                    if (n6 < 0) {
                        return arrayList;
                    }
                    if (n3 <= n6) return arrayList;
                    n4 = n6 + n;
                    if (n4 < 0) break block5;
                    n5 = n4;
                    if (n4 <= n3) break block6;
                }
                if (!bl) return arrayList;
                n5 = n3;
            }
            arrayList.add(function1.invoke(charSequence.subSequence(n6, n5)));
            n6 += n2;
        } while (true);
    }

    public static /* synthetic */ List windowed$default(CharSequence charSequence, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) == 0) return StringsKt.windowed(charSequence, n, n2, bl);
        bl = false;
        return StringsKt.windowed(charSequence, n, n2, bl);
    }

    public static /* synthetic */ List windowed$default(CharSequence charSequence, int n, int n2, boolean bl, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) == 0) return StringsKt.windowed(charSequence, n, n2, bl, function1);
        bl = false;
        return StringsKt.windowed(charSequence, n, n2, bl, function1);
    }

    public static final Sequence<String> windowedSequence(CharSequence charSequence, int n, int n2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$windowedSequence");
        return StringsKt.windowedSequence(charSequence, n, n2, bl, windowedSequence.1.INSTANCE);
    }

    public static final <R> Sequence<R> windowedSequence(CharSequence charSequence, int n, int n2, boolean bl, Function1<? super CharSequence, ? extends R> function1) {
        IntRange intRange;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$windowedSequence");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        SlidingWindowKt.checkWindowSizeStep(n, n2);
        if (bl) {
            intRange = StringsKt.getIndices(charSequence);
            return SequencesKt.map(CollectionsKt.asSequence(RangesKt.step(intRange, n2)), new Function1<Integer, R>(charSequence, n, function1){
                final /* synthetic */ int $size;
                final /* synthetic */ CharSequence $this_windowedSequence;
                final /* synthetic */ Function1 $transform;
                {
                    this.$this_windowedSequence = charSequence;
                    this.$size = n;
                    this.$transform = function1;
                    super(1);
                }

                public final R invoke(int n) {
                    int n2;
                    int n3 = this.$size + n;
                    if (n3 >= 0) {
                        n2 = n3;
                        if (n3 <= this.$this_windowedSequence.length()) return this.$transform.invoke(this.$this_windowedSequence.subSequence(n, n2));
                    }
                    n2 = this.$this_windowedSequence.length();
                    return this.$transform.invoke(this.$this_windowedSequence.subSequence(n, n2));
                }
            });
        }
        intRange = RangesKt.until(0, charSequence.length() - n + 1);
        return SequencesKt.map(CollectionsKt.asSequence(RangesKt.step(intRange, n2)), new /* invalid duplicate definition of identical inner class */);
    }

    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) == 0) return StringsKt.windowedSequence(charSequence, n, n2, bl);
        bl = false;
        return StringsKt.windowedSequence(charSequence, n, n2, bl);
    }

    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int n, int n2, boolean bl, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) == 0) return StringsKt.windowedSequence(charSequence, n, n2, bl, function1);
        bl = false;
        return StringsKt.windowedSequence(charSequence, n, n2, bl, function1);
    }

    public static final Iterable<IndexedValue<Character>> withIndex(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$withIndex");
        return new IndexingIterable((Function0)new Function0<CharIterator>(charSequence){
            final /* synthetic */ CharSequence $this_withIndex;
            {
                this.$this_withIndex = charSequence;
                super(0);
            }

            public final CharIterator invoke() {
                return StringsKt.iterator(this.$this_withIndex);
            }
        });
    }

    public static final List<Pair<Character, Character>> zip(CharSequence charSequence, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$zip");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        int n = Math.min(charSequence.length(), charSequence2.length());
        ArrayList<Pair<Character, Character>> arrayList = new ArrayList<Pair<Character, Character>>(n);
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(TuplesKt.to(Character.valueOf(charSequence.charAt(n2)), Character.valueOf(charSequence2.charAt(n2))));
            ++n2;
        }
        return arrayList;
    }

    public static final <V> List<V> zip(CharSequence charSequence, CharSequence charSequence2, Function2<? super Character, ? super Character, ? extends V> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$zip");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        int n = Math.min(charSequence.length(), charSequence2.length());
        ArrayList<V> arrayList = new ArrayList<V>(n);
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(function2.invoke(Character.valueOf(charSequence.charAt(n2)), Character.valueOf(charSequence2.charAt(n2))));
            ++n2;
        }
        return arrayList;
    }

    public static final List<Pair<Character, Character>> zipWithNext(CharSequence object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$zipWithNext");
        int n = object.length() - 1;
        if (n < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList<Pair<Character, Character>> arrayList = new ArrayList<Pair<Character, Character>>(n);
        int n2 = 0;
        while (n2 < n) {
            char c = object.charAt(n2);
            arrayList.add(TuplesKt.to(Character.valueOf(c), Character.valueOf(object.charAt(++n2))));
        }
        return arrayList;
    }

    public static final <R> List<R> zipWithNext(CharSequence charSequence, Function2<? super Character, ? super Character, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$zipWithNext");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        int n = charSequence.length() - 1;
        if (n < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList<R> arrayList = new ArrayList<R>(n);
        int n2 = 0;
        while (n2 < n) {
            char c = charSequence.charAt(n2);
            arrayList.add(function2.invoke(Character.valueOf(c), Character.valueOf(charSequence.charAt(++n2))));
        }
        return arrayList;
    }
}

