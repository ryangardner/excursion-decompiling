package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a:\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001aE\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b\u001c\u001a:\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0006\u001a4\u0010 \u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a4\u0010&\u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a;\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b)\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u0010+\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u0010+\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\r\u0010.\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u0010/\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00100\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a \u00101\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00102\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00103\u001a\u000204*\u00020\u0002H\u0086\u0002\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00106\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u00106\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0010\u00107\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u0002\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u0002\u001a\u0015\u0010;\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\f\u001a\u000f\u0010<\u001a\u00020\n*\u0004\u0018\u00010\nH\u0087\b\u001a\u001c\u0010=\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010=\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001aG\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u000e\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0004\bE\u0010F\u001a=\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u0006\u0010B\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\bE\u001a4\u0010G\u001a\u00020\r*\u00020\u00022\u0006\u0010H\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010I\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010J\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u0002\u001a\u0012\u0010J\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u0002\u001a\u001a\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006\u001a\u0012\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a+\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\b\u0010R\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00020SH\u0087\b\u001a\u001d\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001d\u0010[\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a\"\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002\u001a\u001a\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002\u001a%\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a=\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010^\u001a0\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a/\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010P\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\b_\u001a%\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010D\u001a\u00020\u0006H\u0087\b\u001a=\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010a\u001a0\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a$\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010c\u001a\u00020\u0002*\u00020\n2\u0006\u0010d\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u0012\u0010f\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\n\u0010k\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010k\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010k\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010k\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010m\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010m\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010m\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010m\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010n\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010n\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010n\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010n\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006o"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "limit", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
   public StringsKt__StringsKt() {
   }

   public static final String commonPrefixWith(CharSequence var0, CharSequence var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonPrefixWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var3 = Math.min(var0.length(), var1.length());

      int var4;
      for(var4 = 0; var4 < var3 && CharsKt.equals(var0.charAt(var4), var1.charAt(var4), var2); ++var4) {
      }

      int var5 = var4 - 1;
      if (!StringsKt.hasSurrogatePairAt(var0, var5)) {
         var3 = var4;
         if (!StringsKt.hasSurrogatePairAt(var1, var5)) {
            return var0.subSequence(0, var3).toString();
         }
      }

      var3 = var4 - 1;
      return var0.subSequence(0, var3).toString();
   }

   // $FF: synthetic method
   public static String commonPrefixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonPrefixWith(var0, var1, var2);
   }

   public static final String commonSuffixWith(CharSequence var0, CharSequence var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSuffixWith");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var3 = var0.length();
      int var4 = var1.length();
      int var5 = Math.min(var3, var4);

      int var6;
      for(var6 = 0; var6 < var5 && CharsKt.equals(var0.charAt(var3 - var6 - 1), var1.charAt(var4 - var6 - 1), var2); ++var6) {
      }

      if (!StringsKt.hasSurrogatePairAt(var0, var3 - var6 - 1)) {
         var5 = var6;
         if (!StringsKt.hasSurrogatePairAt(var1, var4 - var6 - 1)) {
            return var0.subSequence(var3 - var5, var3).toString();
         }
      }

      var5 = var6 - 1;
      return var0.subSequence(var3 - var5, var3).toString();
   }

   // $FF: synthetic method
   public static String commonSuffixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonSuffixWith(var0, var1, var2);
   }

   public static final boolean contains(CharSequence var0, char var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      if (StringsKt.indexOf$default(var0, var1, 0, var2, 2, (Object)null) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final boolean contains(CharSequence var0, CharSequence var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      boolean var3 = var1 instanceof String;
      boolean var4 = true;
      if (var3) {
         if (StringsKt.indexOf$default(var0, (String)var1, 0, var2, 2, (Object)null) >= 0) {
            var2 = var4;
            return var2;
         }
      } else if (indexOf$StringsKt__StringsKt$default(var0, var1, 0, var0.length(), var2, false, 16, (Object)null) >= 0) {
         var2 = var4;
         return var2;
      }

      var2 = false;
      return var2;
   }

   private static final boolean contains(CharSequence var0, Regex var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var1.containsMatchIn(var0);
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   // $FF: synthetic method
   public static boolean contains$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   public static final boolean endsWith(CharSequence var0, char var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$endsWith");
      if (var0.length() > 0 && CharsKt.equals(var0.charAt(StringsKt.getLastIndex(var0)), var1, var2)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final boolean endsWith(CharSequence var0, CharSequence var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return !var2 && var0 instanceof String && var1 instanceof String ? StringsKt.endsWith$default((String)var0, (String)var1, false, 2, (Object)null) : StringsKt.regionMatchesImpl(var0, var0.length() - var1.length(), var1, 0, var1.length(), var2);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   public static final Pair<Integer, String> findAnyOf(CharSequence var0, Collection<String> var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$findAnyOf");
      Intrinsics.checkParameterIsNotNull(var1, "strings");
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
   }

   private static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence var0, Collection<String> var1, int var2, boolean var3, boolean var4) {
      IntProgression var5 = null;
      if (!var3 && var1.size() == 1) {
         String var11 = (String)CollectionsKt.single((Iterable)var1);
         if (!var4) {
            var2 = StringsKt.indexOf$default(var0, var11, var2, false, 4, (Object)null);
         } else {
            var2 = StringsKt.lastIndexOf$default(var0, var11, var2, false, 4, (Object)null);
         }

         Pair var10;
         if (var2 < 0) {
            var10 = var5;
         } else {
            var10 = TuplesKt.to(var2, var11);
         }

         return var10;
      } else {
         if (!var4) {
            var5 = (IntProgression)(new IntRange(RangesKt.coerceAtLeast(var2, 0), var0.length()));
         } else {
            var5 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), 0);
         }

         int var6;
         int var7;
         Object var12;
         String var13;
         if (var0 instanceof String) {
            var2 = var5.getFirst();
            var6 = var5.getLast();
            var7 = var5.getStep();
            if (var7 >= 0) {
               if (var2 > var6) {
                  return null;
               }
            } else if (var2 < var6) {
               return null;
            }

            while(true) {
               Iterator var8 = ((Iterable)var1).iterator();

               String var9;
               do {
                  if (!var8.hasNext()) {
                     var12 = null;
                     break;
                  }

                  var12 = var8.next();
                  var9 = (String)var12;
               } while(!StringsKt.regionMatches(var9, 0, (String)var0, var2, var9.length(), var3));

               var13 = (String)var12;
               if (var13 != null) {
                  return TuplesKt.to(var2, var13);
               }

               if (var2 == var6) {
                  break;
               }

               var2 += var7;
            }
         } else {
            var2 = var5.getFirst();
            var6 = var5.getLast();
            var7 = var5.getStep();
            if (var7 >= 0) {
               if (var2 > var6) {
                  return null;
               }
            } else if (var2 < var6) {
               return null;
            }

            while(true) {
               Iterator var15 = ((Iterable)var1).iterator();

               String var14;
               do {
                  if (!var15.hasNext()) {
                     var12 = null;
                     break;
                  }

                  var12 = var15.next();
                  var14 = (String)var12;
               } while(!StringsKt.regionMatchesImpl((CharSequence)var14, 0, var0, var2, var14.length(), var3));

               var13 = (String)var12;
               if (var13 != null) {
                  return TuplesKt.to(var2, var13);
               }

               if (var2 == var6) {
                  break;
               }

               var2 += var7;
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   public static Pair findAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findAnyOf(var0, var1, var2, var3);
   }

   public static final Pair<Integer, String> findLastAnyOf(CharSequence var0, Collection<String> var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$findLastAnyOf");
      Intrinsics.checkParameterIsNotNull(var1, "strings");
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
   }

   // $FF: synthetic method
   public static Pair findLastAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findLastAnyOf(var0, var1, var2, var3);
   }

   public static final IntRange getIndices(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indices");
      return new IntRange(0, var0.length() - 1);
   }

   public static final int getLastIndex(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndex");
      return var0.length() - 1;
   }

   public static final boolean hasSurrogatePairAt(CharSequence var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$hasSurrogatePairAt");
      int var2 = var0.length();
      boolean var3 = true;
      if (var1 < 0 || var2 - 2 < var1 || !Character.isHighSurrogate(var0.charAt(var1)) || !Character.isLowSurrogate(var0.charAt(var1 + 1))) {
         var3 = false;
      }

      return var3;
   }

   private static final <C extends CharSequence & R, R> R ifBlank(C var0, Function0<? extends R> var1) {
      Object var2 = var0;
      if (StringsKt.isBlank(var0)) {
         var2 = var1.invoke();
      }

      return var2;
   }

   private static final <C extends CharSequence & R, R> R ifEmpty(C var0, Function0<? extends R> var1) {
      boolean var2;
      if (((CharSequence)var0).length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0 = var1.invoke();
      }

      return var0;
   }

   public static final int indexOf(CharSequence var0, char var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOf");
      if (!var3 && var0 instanceof String) {
         var2 = ((String)var0).indexOf(var1, var2);
      } else {
         var2 = StringsKt.indexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var2;
   }

   public static final int indexOf(CharSequence var0, String var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOf");
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (!var3 && var0 instanceof String) {
         var2 = ((String)var0).indexOf(var1, var2);
      } else {
         var2 = indexOf$StringsKt__StringsKt$default(var0, (CharSequence)var1, var2, var0.length(), var3, false, 16, (Object)null);
      }

      return var2;
   }

   private static final int indexOf$StringsKt__StringsKt(CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5) {
      IntProgression var6;
      if (!var5) {
         var6 = (IntProgression)(new IntRange(RangesKt.coerceAtLeast(var2, 0), RangesKt.coerceAtMost(var3, var0.length())));
      } else {
         var6 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), RangesKt.coerceAtLeast(var3, 0));
      }

      int var7;
      if (var0 instanceof String && var1 instanceof String) {
         var2 = var6.getFirst();
         var3 = var6.getLast();
         var7 = var6.getStep();
         if (var7 >= 0) {
            if (var2 > var3) {
               return -1;
            }
         } else if (var2 < var3) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatches((String)var1, 0, (String)var0, var2, var1.length(), var4)) {
               return var2;
            }

            if (var2 == var3) {
               break;
            }

            var2 += var7;
         }
      } else {
         var2 = var6.getFirst();
         var7 = var6.getLast();
         var3 = var6.getStep();
         if (var3 >= 0) {
            if (var2 > var7) {
               return -1;
            }
         } else if (var2 < var7) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatchesImpl(var1, 0, var0, var2, var1.length(), var4)) {
               return var2;
            }

            if (var2 == var7) {
               break;
            }

            var2 += var3;
         }
      }

      return -1;
   }

   // $FF: synthetic method
   static int indexOf$StringsKt__StringsKt$default(CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return indexOf$StringsKt__StringsKt(var0, var1, var2, var3, var4, var5);
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int indexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int indexOfAny(CharSequence var0, Collection<String> var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfAny");
      Intrinsics.checkParameterIsNotNull(var1, "strings");
      Pair var4 = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
      if (var4 != null) {
         Integer var5 = (Integer)var4.getFirst();
         if (var5 != null) {
            var2 = var5;
            return var2;
         }
      }

      var2 = -1;
      return var2;
   }

   public static final int indexOfAny(CharSequence var0, char[] var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfAny");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      if (!var3 && var1.length == 1 && var0 instanceof String) {
         char var9 = ArraysKt.single(var1);
         return ((String)var0).indexOf(var9, var2);
      } else {
         var2 = RangesKt.coerceAtLeast(var2, 0);
         int var5 = StringsKt.getLastIndex(var0);
         if (var2 <= var5) {
            while(true) {
               char var6 = var0.charAt(var2);
               int var7 = var1.length;
               int var4 = 0;

               boolean var8;
               while(true) {
                  if (var4 >= var7) {
                     var8 = false;
                     break;
                  }

                  if (CharsKt.equals(var1[var4], var6, var3)) {
                     var8 = true;
                     break;
                  }

                  ++var4;
               }

               if (var8) {
                  return var2;
               }

               if (var2 == var5) {
                  break;
               }

               ++var2;
            }
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int indexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   private static final boolean isEmpty(CharSequence var0) {
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final boolean isNotBlank(CharSequence var0) {
      return StringsKt.isBlank(var0) ^ true;
   }

   private static final boolean isNotEmpty(CharSequence var0) {
      boolean var1;
      if (var0.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final boolean isNullOrBlank(CharSequence var0) {
      boolean var1;
      if (var0 != null && !StringsKt.isBlank(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static final boolean isNullOrEmpty(CharSequence var0) {
      boolean var1;
      if (var0 != null && var0.length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static final CharIterator iterator(final CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$iterator");
      return (CharIterator)(new CharIterator() {
         private int index;

         public boolean hasNext() {
            boolean var1;
            if (this.index < var0.length()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public char nextChar() {
            CharSequence var1 = var0;
            int var2 = this.index++;
            return var1.charAt(var2);
         }
      });
   }

   public static final int lastIndexOf(CharSequence var0, char var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndexOf");
      if (!var3 && var0 instanceof String) {
         var2 = ((String)var0).lastIndexOf(var1, var2);
      } else {
         var2 = StringsKt.lastIndexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var2;
   }

   public static final int lastIndexOf(CharSequence var0, String var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndexOf");
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (!var3 && var0 instanceof String) {
         var2 = ((String)var0).lastIndexOf(var1, var2);
      } else {
         var2 = indexOf$StringsKt__StringsKt(var0, (CharSequence)var1, var2, 0, var3, true);
      }

      return var2;
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(CharSequence var0, Collection<String> var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndexOfAny");
      Intrinsics.checkParameterIsNotNull(var1, "strings");
      Pair var4 = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
      if (var4 != null) {
         Integer var5 = (Integer)var4.getFirst();
         if (var5 != null) {
            var2 = var5;
            return var2;
         }
      }

      var2 = -1;
      return var2;
   }

   public static final int lastIndexOfAny(CharSequence var0, char[] var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lastIndexOfAny");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      if (!var3 && var1.length == 1 && var0 instanceof String) {
         char var9 = ArraysKt.single(var1);
         return ((String)var0).lastIndexOf(var9, var2);
      } else {
         for(var2 = RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)); var2 >= 0; --var2) {
            char var5 = var0.charAt(var2);
            int var6 = var1.length;
            boolean var7 = false;
            int var8 = 0;

            boolean var4;
            while(true) {
               var4 = var7;
               if (var8 >= var6) {
                  break;
               }

               if (CharsKt.equals(var1[var8], var5, var3)) {
                  var4 = true;
                  break;
               }

               ++var8;
            }

            if (var4) {
               return var2;
            }
         }

         return -1;
      }
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int lastIndexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   public static final Sequence<String> lineSequence(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lineSequence");
      return StringsKt.splitToSequence$default(var0, new String[]{"\r\n", "\n", "\r"}, false, 0, 6, (Object)null);
   }

   public static final List<String> lines(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lines");
      return SequencesKt.toList(StringsKt.lineSequence(var0));
   }

   private static final boolean matches(CharSequence var0, Regex var1) {
      return var1.matches(var0);
   }

   private static final String orEmpty(String var0) {
      if (var0 == null) {
         var0 = "";
      }

      return var0;
   }

   public static final CharSequence padEnd(CharSequence var0, int var1, char var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$padEnd");
      if (var1 >= 0) {
         if (var1 <= var0.length()) {
            return var0.subSequence(0, var0.length());
         } else {
            StringBuilder var3 = new StringBuilder(var1);
            var3.append(var0);
            int var4 = var1 - var0.length();
            var1 = 1;
            if (1 <= var4) {
               while(true) {
                  var3.append(var2);
                  if (var1 == var4) {
                     break;
                  }

                  ++var1;
               }
            }

            return (CharSequence)var3;
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Desired length ");
         var5.append(var1);
         var5.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   public static final String padEnd(String var0, int var1, char var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$padEnd");
      return StringsKt.padEnd((CharSequence)var0, var1, var2).toString();
   }

   // $FF: synthetic method
   public static CharSequence padEnd$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      char var5 = var2;
      if ((var3 & 2) != 0) {
         byte var6 = 32;
         var5 = (char)var6;
      }

      return StringsKt.padEnd(var0, var1, var5);
   }

   // $FF: synthetic method
   public static String padEnd$default(String var0, int var1, char var2, int var3, Object var4) {
      char var5 = var2;
      if ((var3 & 2) != 0) {
         byte var6 = 32;
         var5 = (char)var6;
      }

      return StringsKt.padEnd(var0, var1, var5);
   }

   public static final CharSequence padStart(CharSequence var0, int var1, char var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$padStart");
      if (var1 >= 0) {
         if (var1 <= var0.length()) {
            return var0.subSequence(0, var0.length());
         } else {
            StringBuilder var3 = new StringBuilder(var1);
            int var4 = var1 - var0.length();
            var1 = 1;
            if (1 <= var4) {
               while(true) {
                  var3.append(var2);
                  if (var1 == var4) {
                     break;
                  }

                  ++var1;
               }
            }

            var3.append(var0);
            return (CharSequence)var3;
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Desired length ");
         var5.append(var1);
         var5.append(" is less than zero.");
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   public static final String padStart(String var0, int var1, char var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$padStart");
      return StringsKt.padStart((CharSequence)var0, var1, var2).toString();
   }

   // $FF: synthetic method
   public static CharSequence padStart$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      char var5 = var2;
      if ((var3 & 2) != 0) {
         byte var6 = 32;
         var5 = (char)var6;
      }

      return StringsKt.padStart(var0, var1, var5);
   }

   // $FF: synthetic method
   public static String padStart$default(String var0, int var1, char var2, int var3, Object var4) {
      char var5 = var2;
      if ((var3 & 2) != 0) {
         byte var6 = 32;
         var5 = (char)var6;
      }

      return StringsKt.padStart(var0, var1, var5);
   }

   private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence var0, final char[] var1, int var2, final boolean var3, int var4) {
      boolean var5;
      if (var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         return (Sequence)(new DelimitedRangesSequence(var0, var2, var4, (Function2)(new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>() {
            public final Pair<Integer, Integer> invoke(CharSequence var1x, int var2) {
               Intrinsics.checkParameterIsNotNull(var1x, "$receiver");
               var2 = StringsKt.indexOfAny(var1x, var1, var2, var3);
               Pair var3x;
               if (var2 < 0) {
                  var3x = null;
               } else {
                  var3x = TuplesKt.to(var2, 1);
               }

               return var3x;
            }
         })));
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Limit must be non-negative, but was ");
         var6.append(var4);
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
      }
   }

   private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence var0, String[] var1, int var2, final boolean var3, int var4) {
      boolean var5;
      if (var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         return (Sequence)(new DelimitedRangesSequence(var0, var2, var4, (Function2)(new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>(ArraysKt.asList(var1)) {
            // $FF: synthetic field
            final List $delimitersList;

            {
               this.$delimitersList = var1;
            }

            public final Pair<Integer, Integer> invoke(CharSequence var1, int var2) {
               Intrinsics.checkParameterIsNotNull(var1, "$receiver");
               Pair var3x = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(var1, (Collection)this.$delimitersList, var2, var3, false);
               if (var3x != null) {
                  var3x = TuplesKt.to(var3x.getFirst(), ((String)var3x.getSecond()).length());
               } else {
                  var3x = null;
               }

               return var3x;
            }
         })));
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Limit must be non-negative, but was ");
         var6.append(var4);
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
      }
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   // $FF: synthetic method
   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, String[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   public static final boolean regionMatchesImpl(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$regionMatchesImpl");
      Intrinsics.checkParameterIsNotNull(var2, "other");
      if (var3 >= 0 && var1 >= 0 && var1 <= var0.length() - var4 && var3 <= var2.length() - var4) {
         for(int var6 = 0; var6 < var4; ++var6) {
            if (!CharsKt.equals(var0.charAt(var1 + var6), var2.charAt(var3 + var6), var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final CharSequence removePrefix(CharSequence var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removePrefix");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return StringsKt.startsWith$default(var0, var1, false, 2, (Object)null) ? var0.subSequence(var1.length(), var0.length()) : var0.subSequence(0, var0.length());
   }

   public static final String removePrefix(String var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removePrefix");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      String var2 = var0;
      if (StringsKt.startsWith$default((CharSequence)var0, var1, false, 2, (Object)null)) {
         var2 = var0.substring(var1.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).substring(startIndex)");
      }

      return var2;
   }

   public static final CharSequence removeRange(CharSequence var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeRange");
      if (var2 >= var1) {
         if (var2 == var1) {
            return var0.subSequence(0, var0.length());
         } else {
            StringBuilder var3 = new StringBuilder(var0.length() - (var2 - var1));
            var3.append(var0, 0, var1);
            Intrinsics.checkExpressionValueIsNotNull(var3, "this.append(value, startIndex, endIndex)");
            var3.append(var0, var2, var0.length());
            Intrinsics.checkExpressionValueIsNotNull(var3, "this.append(value, startIndex, endIndex)");
            return (CharSequence)var3;
         }
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("End index (");
         var4.append(var2);
         var4.append(") is less than start index (");
         var4.append(var1);
         var4.append(").");
         throw (Throwable)(new IndexOutOfBoundsException(var4.toString()));
      }
   }

   public static final CharSequence removeRange(CharSequence var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeRange");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      return StringsKt.removeRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   private static final String removeRange(String var0, int var1, int var2) {
      if (var0 != null) {
         return StringsKt.removeRange((CharSequence)var0, var1, var2).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   private static final String removeRange(String var0, IntRange var1) {
      if (var0 != null) {
         return StringsKt.removeRange((CharSequence)var0, var1).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final CharSequence removeSuffix(CharSequence var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSuffix");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return StringsKt.endsWith$default(var0, var1, false, 2, (Object)null) ? var0.subSequence(0, var0.length() - var1.length()) : var0.subSequence(0, var0.length());
   }

   public static final String removeSuffix(String var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSuffix");
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      String var2 = var0;
      if (StringsKt.endsWith$default((CharSequence)var0, var1, false, 2, (Object)null)) {
         var2 = var0.substring(0, var0.length() - var1.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   public static final CharSequence removeSurrounding(CharSequence var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   public static final CharSequence removeSurrounding(CharSequence var0, CharSequence var1, CharSequence var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      Intrinsics.checkParameterIsNotNull(var2, "suffix");
      return var0.length() >= var1.length() + var2.length() && StringsKt.startsWith$default(var0, var1, false, 2, (Object)null) && StringsKt.endsWith$default(var0, var2, false, 2, (Object)null) ? var0.subSequence(var1.length(), var0.length() - var2.length()) : var0.subSequence(0, var0.length());
   }

   public static final String removeSurrounding(String var0, CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   public static final String removeSurrounding(String var0, CharSequence var1, CharSequence var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$removeSurrounding");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      Intrinsics.checkParameterIsNotNull(var2, "suffix");
      String var3 = var0;
      if (var0.length() >= var1.length() + var2.length()) {
         CharSequence var4 = (CharSequence)var0;
         var3 = var0;
         if (StringsKt.startsWith$default(var4, var1, false, 2, (Object)null)) {
            var3 = var0;
            if (StringsKt.endsWith$default(var4, var2, false, 2, (Object)null)) {
               var3 = var0.substring(var1.length(), var0.length() - var2.length());
               Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            }
         }
      }

      return var3;
   }

   private static final String replace(CharSequence var0, Regex var1, String var2) {
      return var1.replace(var0, var2);
   }

   private static final String replace(CharSequence var0, Regex var1, Function1<? super MatchResult, ? extends CharSequence> var2) {
      return var1.replace(var0, var2);
   }

   public static final String replaceAfter(String var0, char var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceAfter");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.indexOf$default(var4, var1, 0, false, 6, (Object)null);
      if (var5 != -1) {
         var3 = StringsKt.replaceRange(var4, var5 + 1, var0.length(), (CharSequence)var2).toString();
      }

      return var3;
   }

   public static final String replaceAfter(String var0, String var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceAfter");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.indexOf$default(var4, var1, 0, false, 6, (Object)null);
      if (var5 != -1) {
         var3 = StringsKt.replaceRange(var4, var5 + var1.length(), var0.length(), (CharSequence)var2).toString();
      }

      return var3;
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replaceAfter$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   public static final String replaceAfterLast(String var0, char var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceAfterLast");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.lastIndexOf$default(var4, var1, 0, false, 6, (Object)null);
      if (var5 != -1) {
         var3 = StringsKt.replaceRange(var4, var5 + 1, var0.length(), (CharSequence)var2).toString();
      }

      return var3;
   }

   public static final String replaceAfterLast(String var0, String var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceAfterLast");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var4 = (CharSequence)var0;
      int var5 = StringsKt.lastIndexOf$default(var4, var1, 0, false, 6, (Object)null);
      if (var5 != -1) {
         var3 = StringsKt.replaceRange(var4, var5 + var1.length(), var0.length(), (CharSequence)var2).toString();
      }

      return var3;
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replaceAfterLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   public static final String replaceBefore(String var0, char var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceBefore");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var5 = (CharSequence)var0;
      int var4 = StringsKt.indexOf$default(var5, var1, 0, false, 6, (Object)null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, (CharSequence)var2).toString();
      }

      return var3;
   }

   public static final String replaceBefore(String var0, String var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceBefore");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var5 = (CharSequence)var0;
      int var4 = StringsKt.indexOf$default(var5, var1, 0, false, 6, (Object)null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, (CharSequence)var2).toString();
      }

      return var3;
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replaceBefore$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   public static final String replaceBeforeLast(String var0, char var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceBeforeLast");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var5 = (CharSequence)var0;
      int var4 = StringsKt.lastIndexOf$default(var5, var1, 0, false, 6, (Object)null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, (CharSequence)var2).toString();
      }

      return var3;
   }

   public static final String replaceBeforeLast(String var0, String var1, String var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceBeforeLast");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      Intrinsics.checkParameterIsNotNull(var3, "missingDelimiterValue");
      CharSequence var5 = (CharSequence)var0;
      int var4 = StringsKt.lastIndexOf$default(var5, var1, 0, false, 6, (Object)null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, (CharSequence)var2).toString();
      }

      return var3;
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static String replaceBeforeLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   private static final String replaceFirst(CharSequence var0, Regex var1, String var2) {
      return var1.replaceFirst(var0, var2);
   }

   public static final CharSequence replaceRange(CharSequence var0, int var1, int var2, CharSequence var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceRange");
      Intrinsics.checkParameterIsNotNull(var3, "replacement");
      if (var2 >= var1) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var0, 0, var1);
         Intrinsics.checkExpressionValueIsNotNull(var4, "this.append(value, startIndex, endIndex)");
         var4.append(var3);
         var4.append(var0, var2, var0.length());
         Intrinsics.checkExpressionValueIsNotNull(var4, "this.append(value, startIndex, endIndex)");
         return (CharSequence)var4;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("End index (");
         var5.append(var2);
         var5.append(") is less than start index (");
         var5.append(var1);
         var5.append(").");
         throw (Throwable)(new IndexOutOfBoundsException(var5.toString()));
      }
   }

   public static final CharSequence replaceRange(CharSequence var0, IntRange var1, CharSequence var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$replaceRange");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      return StringsKt.replaceRange(var0, var1.getStart(), var1.getEndInclusive() + 1, var2);
   }

   private static final String replaceRange(String var0, int var1, int var2, CharSequence var3) {
      if (var0 != null) {
         return StringsKt.replaceRange((CharSequence)var0, var1, var2, var3).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   private static final String replaceRange(String var0, IntRange var1, CharSequence var2) {
      if (var0 != null) {
         return StringsKt.replaceRange((CharSequence)var0, var1, var2).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   private static final List<String> split(CharSequence var0, Regex var1, int var2) {
      return var1.split(var0, var2);
   }

   public static final List<String> split(CharSequence var0, char[] var1, boolean var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$split");
      Intrinsics.checkParameterIsNotNull(var1, "delimiters");
      if (var1.length == 1) {
         return split$StringsKt__StringsKt(var0, String.valueOf(var1[0]), var2, var3);
      } else {
         Iterable var4 = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (char[])var1, 0, var2, var3, 2, (Object)null));
         Collection var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10)));
         Iterator var6 = var4.iterator();

         while(var6.hasNext()) {
            var5.add(StringsKt.substring(var0, (IntRange)var6.next()));
         }

         return (List)var5;
      }
   }

   public static final List<String> split(CharSequence var0, String[] var1, boolean var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$split");
      Intrinsics.checkParameterIsNotNull(var1, "delimiters");
      int var4 = var1.length;
      boolean var5 = true;
      if (var4 == 1) {
         String var6 = var1[0];
         if (((CharSequence)var6).length() != 0) {
            var5 = false;
         }

         if (!var5) {
            return split$StringsKt__StringsKt(var0, var6, var2, var3);
         }
      }

      Iterable var8 = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (String[])var1, 0, var2, var3, 2, (Object)null));
      Collection var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10)));
      Iterator var9 = var8.iterator();

      while(var9.hasNext()) {
         var7.add(StringsKt.substring(var0, (IntRange)var9.next()));
      }

      return (List)var7;
   }

   private static final List<String> split$StringsKt__StringsKt(CharSequence var0, String var1, boolean var2, int var3) {
      int var4 = 0;
      boolean var5;
      if (var3 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (!var5) {
         StringBuilder var10 = new StringBuilder();
         var10.append("Limit must be non-negative, but was ");
         var10.append(var3);
         var10.append('.');
         throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
      } else {
         int var6 = StringsKt.indexOf(var0, var1, 0, var2);
         if (var6 != -1 && var3 != 1) {
            if (var3 > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            int var7 = 10;
            if (var5) {
               var7 = RangesKt.coerceAtMost(var3, 10);
            }

            ArrayList var8 = new ArrayList(var7);
            var7 = var6;

            int var9;
            do {
               var8.add(var0.subSequence(var4, var7).toString());
               var6 = var1.length() + var7;
               if (var5 && var8.size() == var3 - 1) {
                  break;
               }

               var9 = StringsKt.indexOf(var0, var1, var6, var2);
               var4 = var6;
               var7 = var9;
            } while(var9 != -1);

            var8.add(var0.subSequence(var6, var0.length()).toString());
            return (List)var8;
         } else {
            return CollectionsKt.listOf(var0.toString());
         }
      }
   }

   // $FF: synthetic method
   static List split$default(CharSequence var0, Regex var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var1.split(var0, var2);
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   public static final Sequence<String> splitToSequence(final CharSequence var0, char[] var1, boolean var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$splitToSequence");
      Intrinsics.checkParameterIsNotNull(var1, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (char[])var1, 0, var2, var3, 2, (Object)null), (Function1)(new Function1<IntRange, String>() {
         public final String invoke(IntRange var1) {
            Intrinsics.checkParameterIsNotNull(var1, "it");
            return StringsKt.substring(var0, var1);
         }
      }));
   }

   public static final Sequence<String> splitToSequence(final CharSequence var0, String[] var1, boolean var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$splitToSequence");
      Intrinsics.checkParameterIsNotNull(var1, "delimiters");
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (String[])var1, 0, var2, var3, 2, (Object)null), (Function1)(new Function1<IntRange, String>() {
         public final String invoke(IntRange var1) {
            Intrinsics.checkParameterIsNotNull(var1, "it");
            return StringsKt.substring(var0, var1);
         }
      }));
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static Sequence splitToSequence$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   public static final boolean startsWith(CharSequence var0, char var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      int var3 = var0.length();
      boolean var4 = false;
      boolean var5 = var4;
      if (var3 > 0) {
         var5 = var4;
         if (CharsKt.equals(var0.charAt(0), var1, var2)) {
            var5 = true;
         }
      }

      return var5;
   }

   public static final boolean startsWith(CharSequence var0, CharSequence var1, int var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return !var3 && var0 instanceof String && var1 instanceof String ? StringsKt.startsWith$default((String)var0, (String)var1, var2, false, 4, (Object)null) : StringsKt.regionMatchesImpl(var0, var2, var1, 0, var1.length(), var3);
   }

   public static final boolean startsWith(CharSequence var0, CharSequence var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return !var2 && var0 instanceof String && var1 instanceof String ? StringsKt.startsWith$default((String)var0, (String)var1, false, 2, (Object)null) : StringsKt.regionMatchesImpl(var0, 0, var1, 0, var1.length(), var2);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final CharSequence subSequence(CharSequence var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$subSequence");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1);
   }

   @Deprecated(
      message = "Use parameters named startIndex and endIndex.",
      replaceWith = @ReplaceWith(
   expression = "subSequence(startIndex = start, endIndex = end)",
   imports = {}
)
   )
   private static final CharSequence subSequence(String var0, int var1, int var2) {
      return var0.subSequence(var1, var2);
   }

   private static final String substring(CharSequence var0, int var1, int var2) {
      return var0.subSequence(var1, var2).toString();
   }

   public static final String substring(CharSequence var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substring");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1).toString();
   }

   public static final String substring(String var0, IntRange var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substring");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      var0 = var0.substring(var1.getStart(), var1.getEndInclusive() + 1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return var0;
   }

   // $FF: synthetic method
   static String substring$default(CharSequence var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return var0.subSequence(var1, var2).toString();
   }

   public static final String substringAfter(String var0, char var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringAfter");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + 1, var0.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   public static final String substringAfter(String var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringAfter");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + var1.length(), var0.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   // $FF: synthetic method
   public static String substringAfter$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   public static final String substringAfterLast(String var0, char var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringAfterLast");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + 1, var0.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   public static final String substringAfterLast(String var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringAfterLast");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + var1.length(), var0.length());
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   // $FF: synthetic method
   public static String substringAfterLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   public static final String substringBefore(String var0, char var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringBefore");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   public static final String substringBefore(String var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringBefore");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   // $FF: synthetic method
   public static String substringBefore$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   public static final String substringBeforeLast(String var0, char var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringBeforeLast");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   public static final String substringBeforeLast(String var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$substringBeforeLast");
      Intrinsics.checkParameterIsNotNull(var1, "delimiter");
      Intrinsics.checkParameterIsNotNull(var2, "missingDelimiterValue");
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }

      return var2;
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   // $FF: synthetic method
   public static String substringBeforeLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   public static final CharSequence trim(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trim");
      int var1 = var0.length() - 1;
      int var2 = 0;
      boolean var3 = false;

      while(var2 <= var1) {
         int var4;
         if (!var3) {
            var4 = var2;
         } else {
            var4 = var1;
         }

         boolean var5 = CharsKt.isWhitespace(var0.charAt(var4));
         if (!var3) {
            if (!var5) {
               var3 = true;
            } else {
               ++var2;
            }
         } else {
            if (!var5) {
               break;
            }

            --var1;
         }
      }

      return var0.subSequence(var2, var1 + 1);
   }

   public static final CharSequence trim(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trim");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length() - 1;
      int var3 = 0;
      boolean var4 = false;

      while(var3 <= var2) {
         int var5;
         if (!var4) {
            var5 = var3;
         } else {
            var5 = var2;
         }

         boolean var6 = (Boolean)var1.invoke(var0.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var6) {
               break;
            }

            --var2;
         }
      }

      return var0.subSequence(var3, var2 + 1);
   }

   public static final CharSequence trim(CharSequence var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trim");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      int var2 = var0.length() - 1;
      int var3 = 0;
      boolean var4 = false;

      while(var3 <= var2) {
         int var5;
         if (!var4) {
            var5 = var3;
         } else {
            var5 = var2;
         }

         boolean var6 = ArraysKt.contains(var1, var0.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var6) {
               break;
            }

            --var2;
         }
      }

      return var0.subSequence(var3, var2 + 1);
   }

   private static final String trim(String var0) {
      if (var0 != null) {
         return StringsKt.trim((CharSequence)var0).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final String trim(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trim");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var7 = (CharSequence)var0;
      int var2 = var7.length() - 1;
      int var3 = 0;
      boolean var4 = false;

      while(var3 <= var2) {
         int var5;
         if (!var4) {
            var5 = var3;
         } else {
            var5 = var2;
         }

         boolean var6 = (Boolean)var1.invoke(var7.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var6) {
               break;
            }

            --var2;
         }
      }

      return var7.subSequence(var3, var2 + 1).toString();
   }

   public static final String trim(String var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trim");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      CharSequence var7 = (CharSequence)var0;
      int var2 = var7.length() - 1;
      int var3 = 0;
      boolean var4 = false;

      while(var3 <= var2) {
         int var5;
         if (!var4) {
            var5 = var3;
         } else {
            var5 = var2;
         }

         boolean var6 = ArraysKt.contains(var1, var7.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var6) {
               break;
            }

            --var2;
         }
      }

      return var7.subSequence(var3, var2 + 1).toString();
   }

   public static final CharSequence trimEnd(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimEnd");
      int var1 = var0.length();

      while(true) {
         int var2 = var1 - 1;
         if (var2 >= 0) {
            var1 = var2;
            if (CharsKt.isWhitespace(var0.charAt(var2))) {
               continue;
            }

            var0 = var0.subSequence(0, var2 + 1);
            break;
         }

         var0 = (CharSequence)"";
         break;
      }

      return var0;
   }

   public static final CharSequence trimEnd(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      int var3;
      do {
         var3 = var2 - 1;
         if (var3 < 0) {
            return (CharSequence)"";
         }

         var2 = var3;
      } while((Boolean)var1.invoke(var0.charAt(var3)));

      return var0.subSequence(0, var3 + 1);
   }

   public static final CharSequence trimEnd(CharSequence var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      int var2 = var0.length();

      while(true) {
         int var3 = var2 - 1;
         if (var3 >= 0) {
            var2 = var3;
            if (ArraysKt.contains(var1, var0.charAt(var3))) {
               continue;
            }

            var0 = var0.subSequence(0, var3 + 1);
            break;
         }

         var0 = (CharSequence)"";
         break;
      }

      return var0;
   }

   private static final String trimEnd(String var0) {
      if (var0 != null) {
         return StringsKt.trimEnd((CharSequence)var0).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final String trimEnd(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var4 = (CharSequence)var0;
      int var2 = var4.length();

      while(true) {
         int var3 = var2 - 1;
         if (var3 >= 0) {
            var2 = var3;
            if ((Boolean)var1.invoke(var4.charAt(var3))) {
               continue;
            }

            var4 = var4.subSequence(0, var3 + 1);
            break;
         }

         var4 = (CharSequence)"";
         break;
      }

      return var4.toString();
   }

   public static final String trimEnd(String var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimEnd");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      CharSequence var4 = (CharSequence)var0;
      int var2 = var4.length();

      while(true) {
         int var3 = var2 - 1;
         if (var3 >= 0) {
            var2 = var3;
            if (ArraysKt.contains(var1, var4.charAt(var3))) {
               continue;
            }

            var4 = var4.subSequence(0, var3 + 1);
            break;
         }

         var4 = (CharSequence)"";
         break;
      }

      return var4.toString();
   }

   public static final CharSequence trimStart(CharSequence var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimStart");
      int var1 = var0.length();
      int var2 = 0;

      while(true) {
         if (var2 >= var1) {
            var0 = (CharSequence)"";
            break;
         }

         if (!CharsKt.isWhitespace(var0.charAt(var2))) {
            var0 = var0.subSequence(var2, var0.length());
            break;
         }

         ++var2;
      }

      return var0;
   }

   public static final CharSequence trimStart(CharSequence var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(var3, var0.length());
         }
      }

      return (CharSequence)"";
   }

   public static final CharSequence trimStart(CharSequence var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      int var2 = var0.length();
      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            var0 = (CharSequence)"";
            break;
         }

         if (!ArraysKt.contains(var1, var0.charAt(var3))) {
            var0 = var0.subSequence(var3, var0.length());
            break;
         }

         ++var3;
      }

      return var0;
   }

   private static final String trimStart(String var0) {
      if (var0 != null) {
         return StringsKt.trimStart((CharSequence)var0).toString();
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
      }
   }

   public static final String trimStart(String var0, Function1<? super Character, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      CharSequence var4 = (CharSequence)var0;
      int var2 = var4.length();
      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            var4 = (CharSequence)"";
            break;
         }

         if (!(Boolean)var1.invoke(var4.charAt(var3))) {
            var4 = var4.subSequence(var3, var4.length());
            break;
         }

         ++var3;
      }

      return var4.toString();
   }

   public static final String trimStart(String var0, char... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimStart");
      Intrinsics.checkParameterIsNotNull(var1, "chars");
      CharSequence var4 = (CharSequence)var0;
      int var2 = var4.length();
      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            var4 = (CharSequence)"";
            break;
         }

         if (!ArraysKt.contains(var1, var4.charAt(var3))) {
            var4 = var4.subSequence(var3, var4.length());
            break;
         }

         ++var3;
      }

      return var4.toString();
   }
}
