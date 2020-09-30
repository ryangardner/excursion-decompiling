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
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000Ü\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u00020\u0005\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b¢\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b¢\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"¢\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b¢\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\u0000\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b¢\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a\u0014\u0010~\u001a\u0004\u0018\u00010\u0005*\u00020\u0002H\u0087\b¢\u0006\u0002\u0010C\u001a\u001b\u0010~\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007¢\u0006\u0002\u0010\u007f\u001a7\u0010\u0080\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a?\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0087\b¢\u0006\u0003\u0010\u0083\u0001\u001a7\u0010\u0084\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0085\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a?\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0087\b¢\u0006\u0003\u0010\u0083\u0001\u001a\u000b\u0010\u0087\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0087\u0001\u001a\u00020 *\u00020 H\u0087\b\u001aQ\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0087\b¢\u0006\u0003\u0010\u0089\u0001\u001af\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0087\b¢\u0006\u0003\u0010\u008b\u0001\u001a=\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0087\b\u001aR\u0010\u008d\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0087\b\u001a\u000b\u0010\u008e\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u008e\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a)\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a\u001a\u0010\u0090\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0090\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0091\u0001\u001a\u00030\u0092\u0001\u001a\u001d\u0010\u0090\u0001\u001a\u00020 *\u00020 2\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0090\u0001\u001a\u00020 *\u00020 2\b\u0010\u0091\u0001\u001a\u00030\u0092\u0001\u001a\"\u0010\u0093\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0094\u0001\u001a\u00030\u0095\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u0095\u00010\u0004H\u0086\b\u001a\u0013\u0010\u0096\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0096\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0097\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u0097\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u0098\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0098\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0099\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0099\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u009a\u0001\u001a\u0002H6\"\u0010\b\u0000\u00106*\n\u0012\u0006\b\u0000\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6¢\u0006\u0003\u0010\u009b\u0001\u001a\u001d\u0010\u009c\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u009d\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u009e\u0001*\u00020\u0002\u001a\u0011\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010 \u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010¡\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050¢\u0001*\u00020\u0002\u001a1\u0010£\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010¤\u0001\u001a\u00020\"2\t\b\u0002\u0010¥\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010£\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010¤\u0001\u001a\u00020\"2\t\b\u0002\u0010¥\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010¦\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010¤\u0001\u001a\u00020\"2\t\b\u0002\u0010¥\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010¦\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010¤\u0001\u001a\u00020\"2\t\b\u0002\u0010¥\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010§\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050¨\u00010\b*\u00020\u0002\u001a)\u0010©\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010ª\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010©\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010ª\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(«\u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¬\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010\u00ad\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010\u00ad\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(«\u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¬\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b¨\u0006®\u0001"},
   d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "randomOrNull", "(Ljava/lang/CharSequence;Lkotlin/random/Random;)Ljava/lang/Character;", "reduce", "reduceIndexed", "reduceOrNull", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function2;)Ljava/lang/Character;", "reduceRight", "reduceRightIndexed", "reduceRightOrNull", "reversed", "scan", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "scanIndexed", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/util/List;", "scanReduce", "scanReduceIndexed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
   public StringsKt___StringsKt() {
   }

   public static final boolean all(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$all");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         if (!(Boolean)var1.invoke(var0.charAt(var2))) {
            return false;
         }
      }

      return true;
   }

   public static final boolean any(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$any");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 ^ true;
   }

   public static final boolean any(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$any");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         if ((Boolean)var1.invoke(var0.charAt(var2))) {
            return true;
         }
      }

      return false;
   }

   public static final Iterable<Character> asIterable(final CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asIterable");
      if (var0 instanceof String) {
         boolean var1;
         if (var0.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            return (Iterable)CollectionsKt.emptyList();
         }
      }

      return (Iterable)(new Iterable<Character>() {
         public Iterator<Character> iterator() {
            return (Iterator)StringsKt.iterator(var0);
         }
      });
   }

   public static final Sequence<Character> asSequence(final CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asSequence");
      if (var0 instanceof String) {
         boolean var1;
         if (var0.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            return SequencesKt.emptySequence();
         }
      }

      return (Sequence)(new Sequence<Character>() {
         public Iterator<Character> iterator() {
            return (Iterator)StringsKt.iterator(var0);
         }
      });
   }

   public static final <K, V> Map<K, V> associate(CharSequence var0, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associate");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Map var2 = (Map)(new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16)));

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         Pair var4 = (Pair)var1.invoke(var0.charAt(var3));
         var2.put(var4.getFirst(), var4.getSecond());
      }

      return var2;
   }

   public static final <K> Map<K, Character> associateBy(CharSequence var0, Function1<? super Character, ? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateBy");
      Intrinsics.checkParameterIsNotNull(var1, "keySelector");
      Map var2 = (Map)(new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16)));

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         var2.put(var1.invoke(var4), var4);
      }

      return var2;
   }

   public static final <K, V> Map<K, V> associateBy(CharSequence var0, Function1<? super Character, ? extends K> var1, Function1<? super Character, ? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateBy");
      Intrinsics.checkParameterIsNotNull(var1, "keySelector");
      Intrinsics.checkParameterIsNotNull(var2, "valueTransform");
      Map var3 = (Map)(new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16)));

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         var3.put(var1.invoke(var5), var2.invoke(var5));
      }

      return var3;
   }

   public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(CharSequence var0, M var1, Function1<? super Character, ? extends K> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateByTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "keySelector");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         var1.put(var2.invoke(var4), var4);
      }

      return var1;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(CharSequence var0, M var1, Function1<? super Character, ? extends K> var2, Function1<? super Character, ? extends V> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateByTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "keySelector");
      Intrinsics.checkParameterIsNotNull(var3, "valueTransform");

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         var1.put(var2.invoke(var5), var3.invoke(var5));
      }

      return var1;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(CharSequence var0, M var1, Function1<? super Character, ? extends Pair<? extends K, ? extends V>> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         Pair var4 = (Pair)var2.invoke(var0.charAt(var3));
         var1.put(var4.getFirst(), var4.getSecond());
      }

      return var1;
   }

   public static final <V> Map<Character, V> associateWith(CharSequence var0, Function1<? super Character, ? extends V> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateWith");
      Intrinsics.checkParameterIsNotNull(var1, "valueSelector");
      LinkedHashMap var2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16));

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         ((Map)var2).put(var4, var1.invoke(var4));
      }

      return (Map)var2;
   }

   public static final <V, M extends Map<? super Character, ? super V>> M associateWithTo(CharSequence var0, M var1, Function1<? super Character, ? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$associateWithTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "valueSelector");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         var1.put(var4, var2.invoke(var4));
      }

      return var1;
   }

   public static final List<String> chunked(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$chunked");
      return StringsKt.windowed(var0, var1, var1, true);
   }

   public static final <R> List<R> chunked(CharSequence var0, int var1, Function1<? super CharSequence, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$chunked");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      return StringsKt.windowed(var0, var1, var1, true, var2);
   }

   public static final Sequence<String> chunkedSequence(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$chunkedSequence");
      return StringsKt.chunkedSequence(var0, var1, (Function1)null.INSTANCE);
   }

   public static final <R> Sequence<R> chunkedSequence(CharSequence var0, int var1, Function1<? super CharSequence, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$chunkedSequence");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      return StringsKt.windowedSequence(var0, var1, var1, true, var2);
   }

   private static final int count(CharSequence var0) {
      return var0.length();
   }

   public static final int count(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$count");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var0.length(); var3 = var4) {
         var4 = var3;
         if ((Boolean)var1.invoke(var0.charAt(var2))) {
            var4 = var3 + 1;
         }

         ++var2;
      }

      return var3;
   }

   public static final CharSequence drop(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$drop");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         return var0.subSequence(RangesKt.coerceAtMost(var1, var0.length()), var0.length());
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final String drop(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$drop");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0 = var0.substring(RangesKt.coerceAtMost(var1, var0.length()));
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final CharSequence dropLast(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropLast");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final String dropLast(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropLast");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final CharSequence dropLastWhile(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropLastWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = StringsKt.getLastIndex(var0); var2 >= 0; --var2) {
         if (!(Boolean)var1.invoke(var0.charAt(var2))) {
            return var0.subSequence(0, var2 + 1);
         }
      }

      return (CharSequence)"";
   }

   public static final String dropLastWhile(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropLastWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = StringsKt.getLastIndex((CharSequence)var0); var2 >= 0; --var2) {
         if (!(Boolean)var1.invoke(var0.charAt(var2))) {
            var0 = var0.substring(0, var2 + 1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var0;
         }
      }

      return "";
   }

   public static final CharSequence dropWhile(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(var3, var0.length());
         }
      }

      return (CharSequence)"";
   }

   public static final String dropWhile(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dropWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = ((CharSequence)var0).length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            var0 = var0.substring(var3);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            return var0;
         }
      }

      return "";
   }

   private static final char elementAtOrElse(CharSequence var0, int var1, Function1<? super Integer, Character> var2) {
      char var3;
      char var4;
      if (var1 >= 0 && var1 <= StringsKt.getLastIndex(var0)) {
         var4 = var0.charAt(var1);
         var3 = var4;
      } else {
         var4 = (Character)var2.invoke(var1);
         var3 = var4;
      }

      return var3;
   }

   private static final Character elementAtOrNull(CharSequence var0, int var1) {
      return StringsKt.getOrNull(var0, var1);
   }

   public static final CharSequence filter(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filter");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Appendable var2 = (Appendable)(new StringBuilder());
      int var3 = var0.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         if ((Boolean)var1.invoke(var5)) {
            var2.append(var5);
         }
      }

      return (CharSequence)var2;
   }

   public static final String filter(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filter");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var2 = (CharSequence)var0;
      Appendable var6 = (Appendable)(new StringBuilder());
      int var3 = var2.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var2.charAt(var4);
         if ((Boolean)var1.invoke(var5)) {
            var6.append(var5);
         }
      }

      var0 = ((StringBuilder)var6).toString();
      Intrinsics.checkExpressionValueIsNotNull(var0, "filterTo(StringBuilder(), predicate).toString()");
      return var0;
   }

   public static final CharSequence filterIndexed(CharSequence var0, Function2<? super Integer, ? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Appendable var2 = (Appendable)(new StringBuilder());
      int var3 = 0;

      for(int var4 = 0; var3 < var0.length(); ++var4) {
         char var5 = var0.charAt(var3);
         if ((Boolean)var1.invoke(var4, var5)) {
            var2.append(var5);
         }

         ++var3;
      }

      return (CharSequence)var2;
   }

   public static final String filterIndexed(String var0, Function2<? super Integer, ? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var6 = (CharSequence)var0;
      Appendable var2 = (Appendable)(new StringBuilder());
      int var3 = 0;

      for(int var4 = 0; var3 < var6.length(); ++var4) {
         char var5 = var6.charAt(var3);
         if ((Boolean)var1.invoke(var4, var5)) {
            var2.append(var5);
         }

         ++var3;
      }

      var0 = ((StringBuilder)var2).toString();
      Intrinsics.checkExpressionValueIsNotNull(var0, "filterIndexedTo(StringBu…(), predicate).toString()");
      return var0;
   }

   public static final <C extends Appendable> C filterIndexedTo(CharSequence var0, C var1, Function2<? super Integer, ? super Character, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIndexedTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");
      int var3 = 0;

      for(int var4 = 0; var3 < var0.length(); ++var4) {
         char var5 = var0.charAt(var3);
         if ((Boolean)var2.invoke(var4, var5)) {
            var1.append(var5);
         }

         ++var3;
      }

      return var1;
   }

   public static final CharSequence filterNot(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Appendable var2 = (Appendable)(new StringBuilder());

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         if (!(Boolean)var1.invoke(var4)) {
            var2.append(var4);
         }
      }

      return (CharSequence)var2;
   }

   public static final String filterNot(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var5 = (CharSequence)var0;
      Appendable var2 = (Appendable)(new StringBuilder());

      for(int var3 = 0; var3 < var5.length(); ++var3) {
         char var4 = var5.charAt(var3);
         if (!(Boolean)var1.invoke(var4)) {
            var2.append(var4);
         }
      }

      var0 = ((StringBuilder)var2).toString();
      Intrinsics.checkExpressionValueIsNotNull(var0, "filterNotTo(StringBuilder(), predicate).toString()");
      return var0;
   }

   public static final <C extends Appendable> C filterNotTo(CharSequence var0, C var1, Function1<? super Character, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterNotTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         if (!(Boolean)var2.invoke(var4)) {
            var1.append(var4);
         }
      }

      return var1;
   }

   public static final <C extends Appendable> C filterTo(CharSequence var0, C var1, Function1<? super Character, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");
      int var3 = var0.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         if ((Boolean)var2.invoke(var5)) {
            var1.append(var5);
         }
      }

      return var1;
   }

   private static final Character find(CharSequence var0, Function1<? super Character, Boolean> var1) {
      int var2 = 0;

      Character var4;
      while(true) {
         if (var2 >= var0.length()) {
            var4 = null;
            break;
         }

         char var3 = var0.charAt(var2);
         if ((Boolean)var1.invoke(var3)) {
            var4 = var3;
            break;
         }

         ++var2;
      }

      return var4;
   }

   private static final Character findLast(CharSequence var0, Function1<? super Character, Boolean> var1) {
      int var2 = var0.length();

      Character var4;
      while(true) {
         --var2;
         if (var2 >= 0) {
            char var3 = var0.charAt(var2);
            if (!(Boolean)var1.invoke(var3)) {
               continue;
            }

            var4 = var3;
            break;
         }

         var4 = null;
         break;
      }

      return var4;
   }

   public static final char first(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$first");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (!var1) {
         return var0.charAt(0);
      } else {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      }
   }

   public static final char first(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$first");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if ((Boolean)var1.invoke(var3)) {
            return var3;
         }
      }

      throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
   }

   public static final Character firstOrNull(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$firstOrNull");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Character var2;
      if (var1) {
         var2 = null;
      } else {
         var2 = var0.charAt(0);
      }

      return var2;
   }

   public static final Character firstOrNull(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$firstOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if ((Boolean)var1.invoke(var3)) {
            return var3;
         }
      }

      return null;
   }

   public static final <R> List<R> flatMap(CharSequence var0, Function1<? super Character, ? extends Iterable<? extends R>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatMap");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList());

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         CollectionsKt.addAll(var2, (Iterable)var1.invoke(var0.charAt(var3)));
      }

      return (List)var2;
   }

   public static final <R, C extends Collection<? super R>> C flatMapTo(CharSequence var0, C var1, Function1<? super Character, ? extends Iterable<? extends R>> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatMapTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         CollectionsKt.addAll(var1, (Iterable)var2.invoke(var0.charAt(var3)));
      }

      return var1;
   }

   public static final <R> R fold(CharSequence var0, R var1, Function2<? super R, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$fold");
      Intrinsics.checkParameterIsNotNull(var2, "operation");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         var1 = var2.invoke(var1, var0.charAt(var3));
      }

      return var1;
   }

   public static final <R> R foldIndexed(CharSequence var0, R var1, Function3<? super Integer, ? super R, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$foldIndexed");
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var4;
         if (var3 >= var0.length()) {
            return var1;
         }

         char var6 = var0.charAt(var3);
         ++var4;
         var1 = var2.invoke(var5, var1, var6);
         ++var3;
      }
   }

   public static final <R> R foldRight(CharSequence var0, R var1, Function2<? super Character, ? super R, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$foldRight");
      Intrinsics.checkParameterIsNotNull(var2, "operation");

      for(int var3 = StringsKt.getLastIndex(var0); var3 >= 0; --var3) {
         var1 = var2.invoke(var0.charAt(var3), var1);
      }

      return var1;
   }

   public static final <R> R foldRightIndexed(CharSequence var0, R var1, Function3<? super Integer, ? super Character, ? super R, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$foldRightIndexed");
      Intrinsics.checkParameterIsNotNull(var2, "operation");

      for(int var3 = StringsKt.getLastIndex(var0); var3 >= 0; --var3) {
         var1 = var2.invoke(var3, var0.charAt(var3), var1);
      }

      return var1;
   }

   public static final void forEach(CharSequence var0, Function1<? super Character, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEach");
      Intrinsics.checkParameterIsNotNull(var1, "action");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.invoke(var0.charAt(var2));
      }

   }

   public static final void forEachIndexed(CharSequence var0, Function2<? super Integer, ? super Character, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var3;
         if (var2 >= var0.length()) {
            return;
         }

         char var5 = var0.charAt(var2);
         ++var3;
         var1.invoke(var4, var5);
         ++var2;
      }
   }

   private static final char getOrElse(CharSequence var0, int var1, Function1<? super Integer, Character> var2) {
      char var3;
      char var4;
      if (var1 >= 0 && var1 <= StringsKt.getLastIndex(var0)) {
         var4 = var0.charAt(var1);
         var3 = var4;
      } else {
         var4 = (Character)var2.invoke(var1);
         var3 = var4;
      }

      return var3;
   }

   public static final Character getOrNull(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getOrNull");
      Character var2;
      if (var1 >= 0 && var1 <= StringsKt.getLastIndex(var0)) {
         var2 = var0.charAt(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static final <K> Map<K, List<Character>> groupBy(CharSequence var0, Function1<? super Character, ? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$groupBy");
      Intrinsics.checkParameterIsNotNull(var1, "keySelector");
      Map var2 = (Map)(new LinkedHashMap());

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         Object var5 = var1.invoke(var4);
         Object var6 = var2.get(var5);
         Object var7 = var6;
         if (var6 == null) {
            var7 = new ArrayList();
            var2.put(var5, var7);
         }

         ((List)var7).add(var4);
      }

      return var2;
   }

   public static final <K, V> Map<K, List<V>> groupBy(CharSequence var0, Function1<? super Character, ? extends K> var1, Function1<? super Character, ? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$groupBy");
      Intrinsics.checkParameterIsNotNull(var1, "keySelector");
      Intrinsics.checkParameterIsNotNull(var2, "valueTransform");
      Map var3 = (Map)(new LinkedHashMap());

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         Object var6 = var1.invoke(var5);
         Object var7 = var3.get(var6);
         Object var8 = var7;
         if (var7 == null) {
            var8 = new ArrayList();
            var3.put(var6, var8);
         }

         ((List)var8).add(var2.invoke(var5));
      }

      return var3;
   }

   public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(CharSequence var0, M var1, Function1<? super Character, ? extends K> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$groupByTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "keySelector");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         Object var5 = var2.invoke(var4);
         Object var6 = var1.get(var5);
         Object var7 = var6;
         if (var6 == null) {
            var7 = new ArrayList();
            var1.put(var5, var7);
         }

         ((List)var7).add(var4);
      }

      return var1;
   }

   public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(CharSequence var0, M var1, Function1<? super Character, ? extends K> var2, Function1<? super Character, ? extends V> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$groupByTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "keySelector");
      Intrinsics.checkParameterIsNotNull(var3, "valueTransform");

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         Object var6 = var2.invoke(var5);
         Object var7 = var1.get(var6);
         Object var8 = var7;
         if (var7 == null) {
            var8 = new ArrayList();
            var1.put(var6, var8);
         }

         ((List)var8).add(var3.invoke(var5));
      }

      return var1;
   }

   public static final <K> Grouping<Character, K> groupingBy(final CharSequence var0, final Function1<? super Character, ? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$groupingBy");
      Intrinsics.checkParameterIsNotNull(var1, "keySelector");
      return (Grouping)(new Grouping<Character, K>() {
         public K keyOf(char var1x) {
            return var1.invoke(var1x);
         }

         public Iterator<Character> sourceIterator() {
            return (Iterator)StringsKt.iterator(var0);
         }
      });
   }

   public static final int indexOfFirst(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfFirst");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if ((Boolean)var1.invoke(var0.charAt(var3))) {
            return var3;
         }
      }

      return -1;
   }

   public static final int indexOfLast(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfLast");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = var0.length() - 1; var2 >= 0; --var2) {
         if ((Boolean)var1.invoke(var0.charAt(var2))) {
            return var2;
         }
      }

      return -1;
   }

   public static final char last(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$last");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (!var1) {
         return var0.charAt(StringsKt.getLastIndex(var0));
      } else {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      }
   }

   public static final char last(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$last");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      char var3;
      do {
         --var2;
         if (var2 < 0) {
            throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
         }

         var3 = var0.charAt(var2);
      } while(!(Boolean)var1.invoke(var3));

      return var3;
   }

   public static final Character lastOrNull(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastOrNull");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Character var2;
      if (var1) {
         var2 = null;
      } else {
         var2 = var0.charAt(var0.length() - 1);
      }

      return var2;
   }

   public static final Character lastOrNull(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      char var3;
      do {
         --var2;
         if (var2 < 0) {
            return null;
         }

         var3 = var0.charAt(var2);
      } while(!(Boolean)var1.invoke(var3));

      return var3;
   }

   public static final <R> List<R> map(CharSequence var0, Function1<? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$map");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList(var0.length()));

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         var2.add(var1.invoke(var0.charAt(var3)));
      }

      return (List)var2;
   }

   public static final <R> List<R> mapIndexed(CharSequence var0, Function2<? super Integer, ? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList(var0.length()));
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var4;
         if (var3 >= var0.length()) {
            return (List)var2;
         }

         char var6 = var0.charAt(var3);
         ++var4;
         var2.add(var1.invoke(var5, var6));
         ++var3;
      }
   }

   public static final <R> List<R> mapIndexedNotNull(CharSequence var0, Function2<? super Integer, ? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapIndexedNotNull");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList());
      int var3 = 0;

      for(int var4 = 0; var3 < var0.length(); ++var4) {
         Object var5 = var1.invoke(var4, var0.charAt(var3));
         if (var5 != null) {
            var2.add(var5);
         }

         ++var3;
      }

      return (List)var2;
   }

   public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(CharSequence var0, C var1, Function2<? super Integer, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapIndexedNotNullTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      int var3 = 0;

      for(int var4 = 0; var3 < var0.length(); ++var4) {
         Object var5 = var2.invoke(var4, var0.charAt(var3));
         if (var5 != null) {
            var1.add(var5);
         }

         ++var3;
      }

      return var1;
   }

   public static final <R, C extends Collection<? super R>> C mapIndexedTo(CharSequence var0, C var1, Function2<? super Integer, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapIndexedTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var4;
         if (var3 >= var0.length()) {
            return var1;
         }

         char var6 = var0.charAt(var3);
         ++var4;
         var1.add(var2.invoke(var5, var6));
         ++var3;
      }
   }

   public static final <R> List<R> mapNotNull(CharSequence var0, Function1<? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapNotNull");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Collection var2 = (Collection)(new ArrayList());

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         Object var4 = var1.invoke(var0.charAt(var3));
         if (var4 != null) {
            var2.add(var4);
         }
      }

      return (List)var2;
   }

   public static final <R, C extends Collection<? super R>> C mapNotNullTo(CharSequence var0, C var1, Function1<? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapNotNullTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         Object var4 = var2.invoke(var0.charAt(var3));
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public static final <R, C extends Collection<? super R>> C mapTo(CharSequence var0, C var1, Function1<? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         var1.add(var2.invoke(var0.charAt(var3)));
      }

      return var1;
   }

   public static final Character max(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$max");
      int var1 = var0.length();
      byte var2 = 1;
      boolean var7;
      if (var1 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return null;
      } else {
         char var3 = var0.charAt(0);
         int var4 = StringsKt.getLastIndex(var0);
         char var5 = var3;
         if (1 <= var4) {
            var1 = var2;
            char var8 = var3;

            while(true) {
               char var6 = var0.charAt(var1);
               var3 = var8;
               if (var8 < var6) {
                  var3 = var6;
               }

               var5 = var3;
               if (var1 == var4) {
                  break;
               }

               ++var1;
               var8 = var3;
            }
         }

         return var5;
      }
   }

   public static final <R extends Comparable<? super R>> Character maxBy(CharSequence var0, Function1<? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$maxBy");
      Intrinsics.checkParameterIsNotNull(var1, "selector");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var10;
      if (var2 == 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      if (var10) {
         return null;
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var4;
         } else {
            Comparable var6 = (Comparable)var1.invoke(var4);
            char var7 = var4;
            if (1 <= var5) {
               var2 = var3;
               char var11 = var4;

               while(true) {
                  var7 = var0.charAt(var2);
                  Comparable var8 = (Comparable)var1.invoke(var7);
                  Comparable var9 = var6;
                  if (var6.compareTo(var8) < 0) {
                     var11 = var7;
                     var9 = var8;
                  }

                  var7 = var11;
                  if (var2 == var5) {
                     break;
                  }

                  ++var2;
                  var6 = var9;
               }
            }

            return var7;
         }
      }
   }

   public static final Character maxWith(CharSequence var0, Comparator<? super Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$maxWith");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var8;
      if (var2 == 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         return null;
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         char var6 = var4;
         if (1 <= var5) {
            var2 = var3;
            var6 = var4;

            while(true) {
               char var7 = var0.charAt(var2);
               char var9 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var9 = var7;
               }

               var6 = var9;
               if (var2 == var5) {
                  break;
               }

               ++var2;
               var6 = var9;
            }
         }

         return var6;
      }
   }

   public static final Character min(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$min");
      int var1 = var0.length();
      byte var2 = 1;
      boolean var7;
      if (var1 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return null;
      } else {
         char var3 = var0.charAt(0);
         int var4 = StringsKt.getLastIndex(var0);
         char var5 = var3;
         if (1 <= var4) {
            var1 = var2;
            char var8 = var3;

            while(true) {
               char var6 = var0.charAt(var1);
               var3 = var8;
               if (var8 > var6) {
                  var3 = var6;
               }

               var5 = var3;
               if (var1 == var4) {
                  break;
               }

               ++var1;
               var8 = var3;
            }
         }

         return var5;
      }
   }

   public static final <R extends Comparable<? super R>> Character minBy(CharSequence var0, Function1<? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minBy");
      Intrinsics.checkParameterIsNotNull(var1, "selector");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var10;
      if (var2 == 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      if (var10) {
         return null;
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var4;
         } else {
            Comparable var6 = (Comparable)var1.invoke(var4);
            char var7 = var4;
            if (1 <= var5) {
               var2 = var3;
               char var11 = var4;

               while(true) {
                  var4 = var0.charAt(var2);
                  Comparable var8 = (Comparable)var1.invoke(var4);
                  Comparable var9 = var6;
                  if (var6.compareTo(var8) > 0) {
                     var11 = var4;
                     var9 = var8;
                  }

                  var7 = var11;
                  if (var2 == var5) {
                     break;
                  }

                  ++var2;
                  var6 = var9;
               }
            }

            return var7;
         }
      }
   }

   public static final Character minWith(CharSequence var0, Comparator<? super Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minWith");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var8;
      if (var2 == 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         return null;
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         char var6 = var4;
         if (1 <= var5) {
            var2 = var3;
            var6 = var4;

            while(true) {
               char var7 = var0.charAt(var2);
               char var9 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var9 = var7;
               }

               var6 = var9;
               if (var2 == var5) {
                  break;
               }

               ++var2;
               var6 = var9;
            }
         }

         return var6;
      }
   }

   public static final boolean none(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$none");
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final boolean none(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$none");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         if ((Boolean)var1.invoke(var0.charAt(var2))) {
            return false;
         }
      }

      return true;
   }

   public static final <S extends CharSequence> S onEach(S var0, Function1<? super Character, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$onEach");
      Intrinsics.checkParameterIsNotNull(var1, "action");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.invoke(var0.charAt(var2));
      }

      return var0;
   }

   public static final Pair<CharSequence, CharSequence> partition(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$partition");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = new StringBuilder();

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         if ((Boolean)var1.invoke(var5)) {
            var2.append(var5);
         } else {
            var3.append(var5);
         }
      }

      return new Pair(var2, var3);
   }

   public static final Pair<String, String> partition(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$partition");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = new StringBuilder();
      int var4 = var0.length();

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var0.charAt(var5);
         if ((Boolean)var1.invoke(var6)) {
            var2.append(var6);
         } else {
            var3.append(var6);
         }
      }

      return new Pair(var2.toString(), var3.toString());
   }

   private static final char random(CharSequence var0) {
      return StringsKt.random(var0, (Random)Random.Default);
   }

   public static final char random(CharSequence var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      boolean var2;
      if (var0.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         return var0.charAt(var1.nextInt(var0.length()));
      } else {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      }
   }

   private static final Character randomOrNull(CharSequence var0) {
      return StringsKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final Character randomOrNull(CharSequence var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      boolean var2;
      if (var0.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2 ? null : var0.charAt(var1.nextInt(var0.length()));
   }

   public static final char reduce(CharSequence var0, Function2<? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduce");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         char var6 = var4;
         if (1 <= var5) {
            var2 = var3;
            var6 = var4;

            while(true) {
               char var8 = (Character)var1.invoke(var6, var0.charAt(var2));
               var6 = var8;
               if (var2 == var5) {
                  break;
               }

               ++var2;
               var6 = var8;
            }
         }

         return var6;
      }
   }

   public static final char reduceIndexed(CharSequence var0, Function3<? super Integer, ? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         char var6 = var4;
         if (1 <= var5) {
            var2 = var3;
            var6 = var4;

            while(true) {
               char var8 = (Character)var1.invoke(var2, var6, var0.charAt(var2));
               var6 = var8;
               if (var2 == var5) {
                  break;
               }

               ++var2;
               var6 = var8;
            }
         }

         return var6;
      }
   }

   public static final Character reduceOrNull(CharSequence var0, Function2<? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return null;
      } else {
         char var4 = var0.charAt(0);
         int var5 = StringsKt.getLastIndex(var0);
         char var6 = var4;
         if (1 <= var5) {
            var2 = var3;
            var6 = var4;

            while(true) {
               char var8 = (Character)var1.invoke(var6, var0.charAt(var2));
               var6 = var8;
               if (var2 == var5) {
                  break;
               }

               ++var2;
               var6 = var8;
            }
         }

         return var6;
      }
   }

   public static final char reduceRight(CharSequence var0, Function2<? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceRight");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = StringsKt.getLastIndex(var0);
      if (var2 < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         int var3 = var2 - 1;
         char var5 = var0.charAt(var2);

         char var4;
         for(var4 = var5; var3 >= 0; var4 = var5) {
            var5 = (Character)var1.invoke(var0.charAt(var3), var4);
            --var3;
         }

         return var4;
      }
   }

   public static final char reduceRightIndexed(CharSequence var0, Function3<? super Integer, ? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceRightIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = StringsKt.getLastIndex(var0);
      if (var2 < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         int var3 = var2 - 1;
         char var5 = var0.charAt(var2);

         char var4;
         for(var4 = var5; var3 >= 0; var4 = var5) {
            var5 = (Character)var1.invoke(var3, var0.charAt(var3), var4);
            --var3;
         }

         return var4;
      }
   }

   public static final Character reduceRightOrNull(CharSequence var0, Function2<? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reduceRightOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = StringsKt.getLastIndex(var0);
      if (var2 < 0) {
         return null;
      } else {
         int var3 = var2 - 1;
         char var5 = var0.charAt(var2);

         char var4;
         for(var4 = var5; var3 >= 0; var4 = var5) {
            var5 = (Character)var1.invoke(var0.charAt(var3), var4);
            --var3;
         }

         return var4;
      }
   }

   public static final CharSequence reversed(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      StringBuilder var1 = (new StringBuilder(var0)).reverse();
      Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder(this).reverse()");
      return (CharSequence)var1;
   }

   private static final String reversed(String var0) {
      if (var0 != null) {
         return StringsKt.reversed((CharSequence)var0).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final <R> List<R> scan(CharSequence var0, R var1, Function2<? super R, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$scan");
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      int var3 = var0.length();
      byte var4 = 0;
      boolean var6;
      if (var3 == 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         return CollectionsKt.listOf(var1);
      } else {
         ArrayList var5 = new ArrayList(var0.length() + 1);
         var5.add(var1);

         for(var3 = var4; var3 < var0.length(); ++var3) {
            var1 = var2.invoke(var1, var0.charAt(var3));
            var5.add(var1);
         }

         return (List)var5;
      }
   }

   public static final <R> List<R> scanIndexed(CharSequence var0, R var1, Function3<? super Integer, ? super R, ? super Character, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$scanIndexed");
      Intrinsics.checkParameterIsNotNull(var2, "operation");
      int var3 = var0.length();
      byte var4 = 0;
      boolean var7;
      if (var3 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return CollectionsKt.listOf(var1);
      } else {
         ArrayList var5 = new ArrayList(var0.length() + 1);
         var5.add(var1);
         int var6 = var0.length();

         for(var3 = var4; var3 < var6; ++var3) {
            var1 = var2.invoke(var3, var1, var0.charAt(var3));
            var5.add(var1);
         }

         return (List)var5;
      }
   }

   public static final List<Character> scanReduce(CharSequence var0, Function2<? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$scanReduce");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return CollectionsKt.emptyList();
      } else {
         char var4 = var0.charAt(0);
         ArrayList var5 = new ArrayList(var0.length());
         var5.add(var4);
         int var6 = var0.length();

         for(var2 = var3; var2 < var6; ++var2) {
            var4 = (Character)var1.invoke(var4, var0.charAt(var2));
            var5.add(var4);
         }

         return (List)var5;
      }
   }

   public static final List<Character> scanReduceIndexed(CharSequence var0, Function3<? super Integer, ? super Character, ? super Character, Character> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$scanReduceIndexed");
      Intrinsics.checkParameterIsNotNull(var1, "operation");
      int var2 = var0.length();
      byte var3 = 1;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         return CollectionsKt.emptyList();
      } else {
         char var4 = var0.charAt(0);
         ArrayList var5 = new ArrayList(var0.length());
         var5.add(var4);
         int var6 = var0.length();

         for(var2 = var3; var2 < var6; ++var2) {
            var4 = (Character)var1.invoke(var2, var4, var0.charAt(var2));
            var5.add(var4);
         }

         return (List)var5;
      }
   }

   public static final char single(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$single");
      int var1 = var0.length();
      if (var1 != 0) {
         if (var1 == 1) {
            return var0.charAt(0);
         } else {
            throw (Throwable)(new IllegalArgumentException("Char sequence has more than one element."));
         }
      } else {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      }
   }

   public static final char single(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$single");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Character var2 = (Character)null;
      int var3 = 0;

      boolean var4;
      boolean var6;
      for(var4 = false; var3 < var0.length(); var4 = var6) {
         char var5 = var0.charAt(var3);
         var6 = var4;
         if ((Boolean)var1.invoke(var5)) {
            if (var4) {
               throw (Throwable)(new IllegalArgumentException("Char sequence contains more than one matching element."));
            }

            var2 = var5;
            var6 = true;
         }

         ++var3;
      }

      if (var4) {
         if (var2 != null) {
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
         }
      } else {
         throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
      }
   }

   public static final Character singleOrNull(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$singleOrNull");
      Character var1;
      if (var0.length() == 1) {
         var1 = var0.charAt(0);
      } else {
         var1 = null;
      }

      return var1;
   }

   public static final Character singleOrNull(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$singleOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Character var2 = (Character)null;
      int var3 = 0;

      boolean var4;
      boolean var6;
      for(var4 = false; var3 < var0.length(); var4 = var6) {
         char var5 = var0.charAt(var3);
         var6 = var4;
         if ((Boolean)var1.invoke(var5)) {
            if (var4) {
               return null;
            }

            var2 = var5;
            var6 = true;
         }

         ++var3;
      }

      if (!var4) {
         return null;
      } else {
         return var2;
      }
   }

   public static final CharSequence slice(CharSequence var0, Iterable<Integer> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$slice");
      Intrinsics.checkParameterIsNotNull(var1, "indices");
      int var2 = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return (CharSequence)"";
      } else {
         StringBuilder var3 = new StringBuilder(var2);
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            var3.append(var0.charAt(((Number)var4.next()).intValue()));
         }

         return (CharSequence)var3;
      }
   }

   public static final CharSequence slice(CharSequence var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$slice");
      Intrinsics.checkParameterIsNotNull(var1, "indices");
      return var1.isEmpty() ? (CharSequence)"" : StringsKt.subSequence(var0, var1);
   }

   private static final String slice(String var0, Iterable<Integer> var1) {
      if (var0 != null) {
         return StringsKt.slice((CharSequence)var0, var1).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final String slice(String var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$slice");
      Intrinsics.checkParameterIsNotNull(var1, "indices");
      return var1.isEmpty() ? "" : StringsKt.substring(var0, var1);
   }

   public static final int sumBy(CharSequence var0, Function1<? super Character, Integer> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sumBy");
      Intrinsics.checkParameterIsNotNull(var1, "selector");
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var0.length(); ++var2) {
         var3 += ((Number)var1.invoke(var0.charAt(var2))).intValue();
      }

      return var3;
   }

   public static final double sumByDouble(CharSequence var0, Function1<? super Character, Double> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sumByDouble");
      Intrinsics.checkParameterIsNotNull(var1, "selector");
      double var2 = 0.0D;

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         var2 += ((Number)var1.invoke(var0.charAt(var4))).doubleValue();
      }

      return var2;
   }

   public static final CharSequence take(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$take");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         return var0.subSequence(0, RangesKt.coerceAtMost(var1, var0.length()));
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final String take(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$take");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0 = var0.substring(0, RangesKt.coerceAtMost(var1, var0.length()));
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final CharSequence takeLast(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeLast");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         int var4 = var0.length();
         return var0.subSequence(var4 - RangesKt.coerceAtMost(var1, var4), var4);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final String takeLast(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeLast");
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         int var4 = var0.length();
         var0 = var0.substring(var4 - RangesKt.coerceAtMost(var1, var4));
         Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public static final CharSequence takeLastWhile(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeLastWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = StringsKt.getLastIndex(var0); var2 >= 0; --var2) {
         if (!(Boolean)var1.invoke(var0.charAt(var2))) {
            return var0.subSequence(var2 + 1, var0.length());
         }
      }

      return var0.subSequence(0, var0.length());
   }

   public static final String takeLastWhile(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeLastWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");

      for(int var2 = StringsKt.getLastIndex((CharSequence)var0); var2 >= 0; --var2) {
         if (!(Boolean)var1.invoke(var0.charAt(var2))) {
            var0 = var0.substring(var2 + 1);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).substring(startIndex)");
            return var0;
         }
      }

      return var0;
   }

   public static final CharSequence takeWhile(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(0, var3);
         }
      }

      return var0.subSequence(0, var0.length());
   }

   public static final String takeWhile(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$takeWhile");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            var0 = var0.substring(0, var3);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var0;
         }
      }

      return var0;
   }

   public static final <C extends Collection<? super Character>> C toCollection(CharSequence var0, C var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toCollection");
      Intrinsics.checkParameterIsNotNull(var1, "destination");

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.add(var0.charAt(var2));
      }

      return var1;
   }

   public static final HashSet<Character> toHashSet(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHashSet");
      return (HashSet)StringsKt.toCollection(var0, (Collection)(new HashSet(MapsKt.mapCapacity(var0.length()))));
   }

   public static final List<Character> toList(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toList");
      int var1 = var0.length();
      List var2;
      if (var1 != 0) {
         if (var1 != 1) {
            var2 = StringsKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0.charAt(0));
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   public static final List<Character> toMutableList(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMutableList");
      return (List)StringsKt.toCollection(var0, (Collection)(new ArrayList(var0.length())));
   }

   public static final Set<Character> toSet(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSet");
      int var1 = var0.length();
      Set var2;
      if (var1 != 0) {
         if (var1 != 1) {
            var2 = (Set)StringsKt.toCollection(var0, (Collection)(new LinkedHashSet(MapsKt.mapCapacity(var0.length()))));
         } else {
            var2 = SetsKt.setOf(var0.charAt(0));
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   public static final List<String> windowed(CharSequence var0, int var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$windowed");
      return StringsKt.windowed(var0, var1, var2, var3, (Function1)null.INSTANCE);
   }

   public static final <R> List<R> windowed(CharSequence var0, int var1, int var2, boolean var3, Function1<? super CharSequence, ? extends R> var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$windowed");
      Intrinsics.checkParameterIsNotNull(var4, "transform");
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      int var5 = var0.length();
      int var6 = var5 / var2;
      byte var7 = 0;
      byte var8;
      if (var5 % var2 == 0) {
         var8 = 0;
      } else {
         var8 = 1;
      }

      ArrayList var9 = new ArrayList(var6 + var8);

      for(int var11 = var7; var11 >= 0 && var5 > var11; var11 += var2) {
         int var10;
         label36: {
            var6 = var11 + var1;
            if (var6 >= 0) {
               var10 = var6;
               if (var6 <= var5) {
                  break label36;
               }
            }

            if (!var3) {
               break;
            }

            var10 = var5;
         }

         var9.add(var4.invoke(var0.subSequence(var11, var10)));
      }

      return (List)var9;
   }

   // $FF: synthetic method
   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3, var4);
   }

   public static final Sequence<String> windowedSequence(CharSequence var0, int var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$windowedSequence");
      return StringsKt.windowedSequence(var0, var1, var2, var3, (Function1)null.INSTANCE);
   }

   public static final <R> Sequence<R> windowedSequence(final CharSequence var0, final int var1, int var2, boolean var3, final Function1<? super CharSequence, ? extends R> var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$windowedSequence");
      Intrinsics.checkParameterIsNotNull(var4, "transform");
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      IntRange var5;
      if (var3) {
         var5 = StringsKt.getIndices(var0);
      } else {
         var5 = RangesKt.until(0, var0.length() - var1 + 1);
      }

      return SequencesKt.map(CollectionsKt.asSequence((Iterable)RangesKt.step((IntProgression)var5, var2)), (Function1)(new Function1<Integer, R>() {
         public final R invoke(int var1x) {
            int var2 = var1 + var1x;
            int var3;
            if (var2 >= 0) {
               var3 = var2;
               if (var2 <= var0.length()) {
                  return var4.invoke(var0.subSequence(var1x, var3));
               }
            }

            var3 = var0.length();
            return var4.invoke(var0.subSequence(var1x, var3));
         }
      }));
   }

   // $FF: synthetic method
   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3, var4);
   }

   public static final Iterable<IndexedValue<Character>> withIndex(final CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$withIndex");
      return (Iterable)(new IndexingIterable((Function0)(new Function0<CharIterator>() {
         public final CharIterator invoke() {
            return StringsKt.iterator(var0);
         }
      })));
   }

   public static final List<Pair<Character, Character>> zip(CharSequence var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$zip");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var2 = Math.min(var0.length(), var1.length());
      ArrayList var3 = new ArrayList(var2);

      for(int var4 = 0; var4 < var2; ++var4) {
         var3.add(TuplesKt.to(var0.charAt(var4), var1.charAt(var4)));
      }

      return (List)var3;
   }

   public static final <V> List<V> zip(CharSequence var0, CharSequence var1, Function2<? super Character, ? super Character, ? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$zip");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      int var3 = Math.min(var0.length(), var1.length());
      ArrayList var4 = new ArrayList(var3);

      for(int var5 = 0; var5 < var3; ++var5) {
         var4.add(var2.invoke(var0.charAt(var5), var1.charAt(var5)));
      }

      return (List)var4;
   }

   public static final List<Pair<Character, Character>> zipWithNext(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$zipWithNext");
      int var1 = var0.length() - 1;
      List var5;
      if (var1 < 1) {
         var5 = CollectionsKt.emptyList();
      } else {
         ArrayList var2 = new ArrayList(var1);
         int var3 = 0;

         while(var3 < var1) {
            char var4 = var0.charAt(var3);
            ++var3;
            var2.add(TuplesKt.to(var4, var0.charAt(var3)));
         }

         var5 = (List)var2;
      }

      return var5;
   }

   public static final <R> List<R> zipWithNext(CharSequence var0, Function2<? super Character, ? super Character, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$zipWithNext");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      int var2 = var0.length() - 1;
      if (var2 < 1) {
         return CollectionsKt.emptyList();
      } else {
         ArrayList var3 = new ArrayList(var2);
         int var4 = 0;

         while(var4 < var2) {
            char var5 = var0.charAt(var4);
            ++var4;
            var3.add(var1.invoke(var5, var0.charAt(var4)));
         }

         return (List)var3;
      }
   }
}
