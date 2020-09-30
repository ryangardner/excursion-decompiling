/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.StringsKt__StringsKt$iterator
 *  kotlin.text.StringsKt__StringsKt$rangesDelimitedBy
 *  kotlin.text.StringsKt__StringsKt$splitToSequence
 */
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
import kotlin.text.CharsKt;
import kotlin.text.DelimitedRangesSequence;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a:\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001aE\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0002\u00a2\u0006\u0002\b\u001c\u001a:\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0006\u001a4\u0010 \u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b\u00a2\u0006\u0002\u0010%\u001a4\u0010&\u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b\u00a2\u0006\u0002\u0010%\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a;\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\rH\u0002\u00a2\u0006\u0002\b)\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u0010+\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u0010+\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\r\u0010.\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u0010/\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00100\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a \u00101\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00102\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00103\u001a\u000204*\u00020\u0002H\u0086\u0002\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00106\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u00106\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0010\u00107\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u0002\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u0002\u001a\u0015\u0010;\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\f\u001a\u000f\u0010<\u001a\u00020\n*\u0004\u0018\u00010\nH\u0087\b\u001a\u001c\u0010=\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010=\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001aG\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u000e\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\bE\u0010F\u001a=\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u0006\u0010B\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\bE\u001a4\u0010G\u001a\u00020\r*\u00020\u00022\u0006\u0010H\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010I\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010J\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u0002\u001a\u0012\u0010J\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u0002\u001a\u001a\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006\u001a\u0012\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a+\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\b\u0010R\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00020SH\u0087\b\u001a\u001d\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001d\u0010[\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a\"\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002\u001a\u001a\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002\u001a%\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a=\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u00a2\u0006\u0002\u0010^\u001a0\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a/\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010P\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b_\u001a%\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010D\u001a\u00020\u0006H\u0087\b\u001a=\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u00a2\u0006\u0002\u0010a\u001a0\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a$\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010c\u001a\u00020\u0002*\u00020\n2\u0006\u0010d\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u0012\u0010f\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\n\u0010k\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010k\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010k\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010k\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010m\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010m\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010m\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010m\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010n\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010n\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010n\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010n\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006o"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "limit", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringsKt
extends StringsKt__StringsJVMKt {
    public static final /* synthetic */ Pair access$findAnyOf(CharSequence charSequence, Collection collection, int n, boolean bl, boolean bl2) {
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, bl2);
    }

    public static final String commonPrefixWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$commonPrefixWith");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && CharsKt.equals(charSequence.charAt(n), charSequence2.charAt(n), bl); ++n) {
        }
        int n3 = n - 1;
        if (!StringsKt.hasSurrogatePairAt(charSequence, n3)) {
            n2 = n;
            if (!StringsKt.hasSurrogatePairAt(charSequence2, n3)) return ((Object)charSequence.subSequence(0, n2)).toString();
        }
        n2 = n - 1;
        return ((Object)charSequence.subSequence(0, n2)).toString();
    }

    public static /* synthetic */ String commonPrefixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.commonPrefixWith(charSequence, charSequence2, bl);
        bl = false;
        return StringsKt.commonPrefixWith(charSequence, charSequence2, bl);
    }

    public static final String commonSuffixWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$commonSuffixWith");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        int n2 = charSequence.length();
        int n3 = charSequence2.length();
        int n4 = Math.min(n2, n3);
        for (n = 0; n < n4 && CharsKt.equals(charSequence.charAt(n2 - n - 1), charSequence2.charAt(n3 - n - 1), bl); ++n) {
        }
        if (!StringsKt.hasSurrogatePairAt(charSequence, n2 - n - 1)) {
            n4 = n;
            if (!StringsKt.hasSurrogatePairAt(charSequence2, n3 - n - 1)) return ((Object)charSequence.subSequence(n2 - n4, n2)).toString();
        }
        n4 = n - 1;
        return ((Object)charSequence.subSequence(n2 - n4, n2)).toString();
    }

    public static /* synthetic */ String commonSuffixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.commonSuffixWith(charSequence, charSequence2, bl);
        bl = false;
        return StringsKt.commonSuffixWith(charSequence, charSequence2, bl);
    }

    public static final boolean contains(CharSequence charSequence, char c, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$contains");
        if (StringsKt.indexOf$default(charSequence, c, 0, bl, 2, null) < 0) return false;
        return true;
    }

    public static final boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$contains");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        boolean bl2 = charSequence2 instanceof String;
        boolean bl3 = true;
        if (bl2) {
            if (StringsKt.indexOf$default(charSequence, (String)charSequence2, 0, bl, 2, null) < 0) return false;
            return bl3;
        }
        if (StringsKt__StringsKt.indexOf$StringsKt__StringsKt$default(charSequence, charSequence2, 0, charSequence.length(), bl, false, 16, null) < 0) return false;
        return bl3;
    }

    private static final boolean contains(CharSequence charSequence, Regex regex) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$contains");
        return regex.containsMatchIn(charSequence);
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.contains(charSequence, c, bl);
        bl = false;
        return StringsKt.contains(charSequence, c, bl);
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.contains(charSequence, charSequence2, bl);
        bl = false;
        return StringsKt.contains(charSequence, charSequence2, bl);
    }

    public static final boolean endsWith(CharSequence charSequence, char c, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$endsWith");
        if (charSequence.length() <= 0) return false;
        if (!CharsKt.equals(charSequence.charAt(StringsKt.getLastIndex(charSequence)), c, bl)) return false;
        return true;
    }

    public static final boolean endsWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(charSequence2, "suffix");
        if (bl) return StringsKt.regionMatchesImpl(charSequence, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence instanceof String)) return StringsKt.regionMatchesImpl(charSequence, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence2 instanceof String)) return StringsKt.regionMatchesImpl(charSequence, charSequence.length() - charSequence2.length(), charSequence2, 0, charSequence2.length(), bl);
        return StringsKt.endsWith$default((String)charSequence, (String)charSequence2, false, 2, null);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.endsWith(charSequence, c, bl);
        bl = false;
        return StringsKt.endsWith(charSequence, c, bl);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.endsWith(charSequence, charSequence2, bl);
        bl = false;
        return StringsKt.endsWith(charSequence, charSequence2, bl);
    }

    public static final Pair<Integer, String> findAnyOf(CharSequence charSequence, Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$findAnyOf");
        Intrinsics.checkParameterIsNotNull(collection, "strings");
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, false);
    }

    private static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence pair, Collection<String> object, int n, boolean bl, boolean bl2) {
        int n2;
        int n3;
        Object object22 = null;
        if (!bl && object.size() == 1) {
            object = (String)CollectionsKt.single((Iterable)object);
            n = !bl2 ? StringsKt.indexOf$default((CharSequence)((Object)pair), (String)object, n, false, 4, null) : StringsKt.lastIndexOf$default((CharSequence)((Object)pair), (String)object, n, false, 4, null);
            if (n >= 0) return TuplesKt.to(n, object);
            return object22;
        }
        object22 = !bl2 ? (IntProgression)new IntRange(RangesKt.coerceAtLeast(n, 0), pair.length()) : RangesKt.downTo(RangesKt.coerceAtMost(n, StringsKt.getLastIndex((CharSequence)((Object)pair))), 0);
        if (!(pair instanceof String)) {
            n = ((IntProgression)object22).getFirst();
            n2 = ((IntProgression)object22).getLast();
            n3 = ((IntProgression)object22).getStep();
            if (n3 >= 0) {
                if (n > n2) return null;
            } else if (n < n2) return null;
        } else {
            n = ((IntProgression)object22).getFirst();
            int n4 = ((IntProgression)object22).getLast();
            int n5 = ((IntProgression)object22).getStep();
            if (n5 >= 0) {
                if (n > n4) return null;
            } else if (n < n4) return null;
            do {
                block13 : {
                    for (Object object22 : (Iterable)object) {
                        String string2 = (String)object22;
                        if (!StringsKt.regionMatches(string2, 0, (String)((Object)pair), n, string2.length(), bl)) continue;
                        break block13;
                    }
                    object22 = null;
                }
                object22 = (String)object22;
                if (object22 != null) {
                    return TuplesKt.to(n, object22);
                }
                if (n == n4) return null;
                n += n5;
            } while (true);
        }
        do {
            block14 : {
                for (Object object22 : (Iterable)object) {
                    String string3 = (String)object22;
                    if (!StringsKt.regionMatchesImpl(string3, 0, pair, n, string3.length(), bl)) continue;
                    break block14;
                }
                object22 = null;
            }
            object22 = (String)object22;
            if (object22 != null) {
                return TuplesKt.to(n, object22);
            }
            if (n == n2) return null;
            n += n3;
        } while (true);
    }

    public static /* synthetic */ Pair findAnyOf$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) == 0) return StringsKt.findAnyOf(charSequence, collection, n, bl);
        bl = false;
        return StringsKt.findAnyOf(charSequence, collection, n, bl);
    }

    public static final Pair<Integer, String> findLastAnyOf(CharSequence charSequence, Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$findLastAnyOf");
        Intrinsics.checkParameterIsNotNull(collection, "strings");
        return StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt(charSequence, collection, n, bl, true);
    }

    public static /* synthetic */ Pair findLastAnyOf$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) == 0) return StringsKt.findLastAnyOf(charSequence, collection, n, bl);
        bl = false;
        return StringsKt.findLastAnyOf(charSequence, collection, n, bl);
    }

    public static final IntRange getIndices(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indices");
        return new IntRange(0, charSequence.length() - 1);
    }

    public static final int getLastIndex(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lastIndex");
        return charSequence.length() - 1;
    }

    public static final boolean hasSurrogatePairAt(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$hasSurrogatePairAt");
        int n2 = charSequence.length();
        boolean bl = true;
        if (n < 0) return false;
        if (n2 - 2 < n) return false;
        if (!Character.isHighSurrogate(charSequence.charAt(n))) return false;
        if (!Character.isLowSurrogate(charSequence.charAt(n + 1))) return false;
        return bl;
    }

    private static final <C extends CharSequence & R, R> R ifBlank(C c, Function0<? extends R> function0) {
        Object object = c;
        if (!StringsKt.isBlank(c)) return object;
        object = function0.invoke();
        return object;
    }

    private static final <C extends CharSequence & R, R> R ifEmpty(C object, Function0<? extends R> function0) {
        boolean bl = object.length() == 0;
        if (!bl) return object;
        object = function0.invoke();
        return object;
    }

    public static final int indexOf(CharSequence charSequence, char c, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indexOf");
        if (bl || !(charSequence instanceof String)) return StringsKt.indexOfAny(charSequence, new char[]{c}, n, bl);
        return ((String)charSequence).indexOf(c, n);
    }

    public static final int indexOf(CharSequence charSequence, String string2, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indexOf");
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (bl) return StringsKt__StringsKt.indexOf$StringsKt__StringsKt$default(charSequence, string2, n, charSequence.length(), bl, false, 16, null);
        if (!(charSequence instanceof String)) return StringsKt__StringsKt.indexOf$StringsKt__StringsKt$default(charSequence, string2, n, charSequence.length(), bl, false, 16, null);
        return ((String)charSequence).indexOf(string2, n);
    }

    private static final int indexOf$StringsKt__StringsKt(CharSequence charSequence, CharSequence charSequence2, int n, int n2, boolean bl, boolean bl2) {
        int n3;
        IntProgression intProgression = !bl2 ? (IntProgression)new IntRange(RangesKt.coerceAtLeast(n, 0), RangesKt.coerceAtMost(n2, charSequence.length())) : RangesKt.downTo(RangesKt.coerceAtMost(n, StringsKt.getLastIndex(charSequence)), RangesKt.coerceAtLeast(n2, 0));
        if (!(charSequence instanceof String) || !(charSequence2 instanceof String)) {
            n = intProgression.getFirst();
            n3 = intProgression.getLast();
            n2 = intProgression.getStep();
            if (n2 >= 0) {
                if (n > n3) return -1;
            } else if (n < n3) return -1;
        } else {
            n = intProgression.getFirst();
            n2 = intProgression.getLast();
            int n4 = intProgression.getStep();
            if (n4 >= 0) {
                if (n > n2) return -1;
            } else if (n < n2) return -1;
            do {
                if (StringsKt.regionMatches((String)charSequence2, 0, (String)charSequence, n, charSequence2.length(), bl)) {
                    return n;
                }
                if (n == n2) return -1;
                n += n4;
            } while (true);
        }
        while (!StringsKt.regionMatchesImpl(charSequence2, 0, charSequence, n, charSequence2.length(), bl)) {
            if (n == n3) return -1;
            n += n2;
        }
        return n;
    }

    static /* synthetic */ int indexOf$StringsKt__StringsKt$default(CharSequence charSequence, CharSequence charSequence2, int n, int n2, boolean bl, boolean bl2, int n3, Object object) {
        if ((n3 & 16) == 0) return StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, charSequence2, n, n2, bl, bl2);
        bl2 = false;
        return StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, charSequence2, n, n2, bl, bl2);
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, char c, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) == 0) return StringsKt.indexOf(charSequence, c, n, bl);
        bl = false;
        return StringsKt.indexOf(charSequence, c, n, bl);
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, String string2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) == 0) return StringsKt.indexOf(charSequence, string2, n, bl);
        bl = false;
        return StringsKt.indexOf(charSequence, string2, n, bl);
    }

    public static final int indexOfAny(CharSequence object, Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(object, "$this$indexOfAny");
        Intrinsics.checkParameterIsNotNull(collection, "strings");
        object = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt((CharSequence)object, collection, n, bl, false);
        if (object == null) return -1;
        if ((object = (Integer)((Pair)object).getFirst()) == null) return -1;
        return (Integer)object;
    }

    public static final int indexOfAny(CharSequence charSequence, char[] arrc, int n, boolean bl) {
        int n2;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$indexOfAny");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        if (!bl && arrc.length == 1 && charSequence instanceof String) {
            char c = ArraysKt.single(arrc);
            return ((String)charSequence).indexOf(c, n);
        }
        if ((n = RangesKt.coerceAtLeast(n, 0)) > (n2 = StringsKt.getLastIndex(charSequence))) return -1;
        do {
            int n3;
            block4 : {
                char c = charSequence.charAt(n);
                int n4 = arrc.length;
                for (n3 = 0; n3 < n4; ++n3) {
                    if (!CharsKt.equals(arrc[n3], c, bl)) continue;
                    n3 = 1;
                    break block4;
                }
                n3 = 0;
            }
            if (n3 != 0) {
                return n;
            }
            if (n == n2) return -1;
            ++n;
        } while (true);
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) == 0) return StringsKt.indexOfAny(charSequence, collection, n, bl);
        bl = false;
        return StringsKt.indexOfAny(charSequence, collection, n, bl);
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, char[] arrc, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) == 0) return StringsKt.indexOfAny(charSequence, arrc, n, bl);
        bl = false;
        return StringsKt.indexOfAny(charSequence, arrc, n, bl);
    }

    private static final boolean isEmpty(CharSequence charSequence) {
        if (charSequence.length() != 0) return false;
        return true;
    }

    private static final boolean isNotBlank(CharSequence charSequence) {
        return StringsKt.isBlank(charSequence) ^ true;
    }

    private static final boolean isNotEmpty(CharSequence charSequence) {
        if (charSequence.length() <= 0) return false;
        return true;
    }

    private static final boolean isNullOrBlank(CharSequence charSequence) {
        if (charSequence == null) return true;
        if (StringsKt.isBlank(charSequence)) return true;
        return false;
    }

    private static final boolean isNullOrEmpty(CharSequence charSequence) {
        if (charSequence == null) return true;
        if (charSequence.length() == 0) return true;
        return false;
    }

    public static final CharIterator iterator(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$iterator");
        return new CharIterator(charSequence){
            final /* synthetic */ CharSequence $this_iterator;
            private int index;
            {
                this.$this_iterator = charSequence;
            }

            public boolean hasNext() {
                if (this.index >= this.$this_iterator.length()) return false;
                return true;
            }

            public char nextChar() {
                CharSequence charSequence = this.$this_iterator;
                int n = this.index;
                this.index = n + 1;
                return charSequence.charAt(n);
            }
        };
    }

    public static final int lastIndexOf(CharSequence charSequence, char c, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lastIndexOf");
        if (bl || !(charSequence instanceof String)) return StringsKt.lastIndexOfAny(charSequence, new char[]{c}, n, bl);
        return ((String)charSequence).lastIndexOf(c, n);
    }

    public static final int lastIndexOf(CharSequence charSequence, String string2, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lastIndexOf");
        Intrinsics.checkParameterIsNotNull(string2, "string");
        if (bl) return StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, string2, n, 0, bl, true);
        if (!(charSequence instanceof String)) return StringsKt__StringsKt.indexOf$StringsKt__StringsKt(charSequence, string2, n, 0, bl, true);
        return ((String)charSequence).lastIndexOf(string2, n);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, char c, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) == 0) return StringsKt.lastIndexOf(charSequence, c, n, bl);
        bl = false;
        return StringsKt.lastIndexOf(charSequence, c, n, bl);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, String string2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) == 0) return StringsKt.lastIndexOf(charSequence, string2, n, bl);
        bl = false;
        return StringsKt.lastIndexOf(charSequence, string2, n, bl);
    }

    public static final int lastIndexOfAny(CharSequence object, Collection<String> collection, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(object, "$this$lastIndexOfAny");
        Intrinsics.checkParameterIsNotNull(collection, "strings");
        object = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt((CharSequence)object, collection, n, bl, true);
        if (object == null) return -1;
        if ((object = (Integer)((Pair)object).getFirst()) == null) return -1;
        return (Integer)object;
    }

    public static final int lastIndexOfAny(CharSequence charSequence, char[] arrc, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lastIndexOfAny");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        if (!bl && arrc.length == 1 && charSequence instanceof String) {
            char c = ArraysKt.single(arrc);
            return ((String)charSequence).lastIndexOf(c, n);
        }
        n = RangesKt.coerceAtMost(n, StringsKt.getLastIndex(charSequence));
        while (n >= 0) {
            boolean bl2;
            char c = charSequence.charAt(n);
            int n2 = arrc.length;
            boolean bl3 = false;
            int n3 = 0;
            do {
                bl2 = bl3;
                if (n3 >= n2) break;
                if (CharsKt.equals(arrc[n3], c, bl)) {
                    bl2 = true;
                    break;
                }
                ++n3;
            } while (true);
            if (bl2) {
                return n;
            }
            --n;
        }
        return -1;
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, Collection collection, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) == 0) return StringsKt.lastIndexOfAny(charSequence, collection, n, bl);
        bl = false;
        return StringsKt.lastIndexOfAny(charSequence, collection, n, bl);
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, char[] arrc, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = StringsKt.getLastIndex(charSequence);
        }
        if ((n2 & 4) == 0) return StringsKt.lastIndexOfAny(charSequence, arrc, n, bl);
        bl = false;
        return StringsKt.lastIndexOfAny(charSequence, arrc, n, bl);
    }

    public static final Sequence<String> lineSequence(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lineSequence");
        return StringsKt.splitToSequence$default(charSequence, new String[]{"\r\n", "\n", "\r"}, false, 0, 6, null);
    }

    public static final List<String> lines(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$lines");
        return SequencesKt.toList(StringsKt.lineSequence(charSequence));
    }

    private static final boolean matches(CharSequence charSequence, Regex regex) {
        return regex.matches(charSequence);
    }

    private static final String orEmpty(String string2) {
        if (string2 == null) return "";
        return string2;
    }

    public static final CharSequence padEnd(CharSequence charSequence, int n, char c) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$padEnd");
        if (n < 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Desired length ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" is less than zero.");
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (n <= charSequence.length()) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(charSequence);
        int n2 = n - charSequence.length();
        n = 1;
        if (1 > n2) return stringBuilder;
        do {
            stringBuilder.append(c);
            if (n == n2) return stringBuilder;
            ++n;
        } while (true);
    }

    public static final String padEnd(String string2, int n, char c) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$padEnd");
        return ((Object)StringsKt.padEnd((CharSequence)string2, n, c)).toString();
    }

    public static /* synthetic */ CharSequence padEnd$default(CharSequence charSequence, int n, char c, int n2, Object object) {
        char c2 = c;
        if ((n2 & 2) == 0) return StringsKt.padEnd(charSequence, n, c2);
        c2 = c = (char)32;
        return StringsKt.padEnd(charSequence, n, c2);
    }

    public static /* synthetic */ String padEnd$default(String string2, int n, char c, int n2, Object object) {
        char c2 = c;
        if ((n2 & 2) == 0) return StringsKt.padEnd(string2, n, c2);
        c2 = c = (char)32;
        return StringsKt.padEnd(string2, n, c2);
    }

    public static final CharSequence padStart(CharSequence charSequence, int n, char c) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$padStart");
        if (n < 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Desired length ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" is less than zero.");
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (n <= charSequence.length()) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = n - charSequence.length();
        n = 1;
        if (1 <= n2) {
            do {
                stringBuilder.append(c);
                if (n == n2) break;
                ++n;
            } while (true);
        }
        stringBuilder.append(charSequence);
        return stringBuilder;
    }

    public static final String padStart(String string2, int n, char c) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$padStart");
        return ((Object)StringsKt.padStart((CharSequence)string2, n, c)).toString();
    }

    public static /* synthetic */ CharSequence padStart$default(CharSequence charSequence, int n, char c, int n2, Object object) {
        char c2 = c;
        if ((n2 & 2) == 0) return StringsKt.padStart(charSequence, n, c2);
        c2 = c = (char)32;
        return StringsKt.padStart(charSequence, n, c2);
    }

    public static /* synthetic */ String padStart$default(String string2, int n, char c, int n2, Object object) {
        char c2 = c;
        if ((n2 & 2) == 0) return StringsKt.padStart(string2, n, c2);
        c2 = c = (char)32;
        return StringsKt.padStart(string2, n, c2);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence charSequence, char[] arrc, int n, boolean bl, int n2) {
        boolean bl2 = n2 >= 0;
        if (bl2) {
            return new DelimitedRangesSequence(charSequence, n, n2, (Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>>)new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>(arrc, bl){
                final /* synthetic */ char[] $delimiters;
                final /* synthetic */ boolean $ignoreCase;
                {
                    this.$delimiters = arrc;
                    this.$ignoreCase = bl;
                    super(2);
                }

                public final Pair<Integer, Integer> invoke(CharSequence object, int n) {
                    Intrinsics.checkParameterIsNotNull(object, "$receiver");
                    n = StringsKt.indexOfAny((CharSequence)object, this.$delimiters, n, this.$ignoreCase);
                    if (n >= 0) return TuplesKt.to(n, 1);
                    return null;
                }
            });
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Limit must be non-negative, but was ");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append('.');
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence charSequence, String[] arrstring, int n, boolean bl, int n2) {
        boolean bl2 = n2 >= 0;
        if (bl2) {
            return new DelimitedRangesSequence(charSequence, n, n2, (Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>>)new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>(ArraysKt.asList(arrstring), bl){
                final /* synthetic */ List $delimitersList;
                final /* synthetic */ boolean $ignoreCase;
                {
                    this.$delimitersList = list;
                    this.$ignoreCase = bl;
                    super(2);
                }

                public final Pair<Integer, Integer> invoke(CharSequence pair, int n) {
                    Intrinsics.checkParameterIsNotNull(pair, "$receiver");
                    pair = StringsKt__StringsKt.access$findAnyOf((CharSequence)((Object)pair), this.$delimitersList, n, this.$ignoreCase, false);
                    if (pair == null) return null;
                    return TuplesKt.to(pair.getFirst(), ((String)pair.getSecond()).length());
                }
            });
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Limit must be non-negative, but was ");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append('.');
        throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
    }

    static /* synthetic */ Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, char[] arrc, int n, boolean bl, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        if ((n3 & 8) == 0) return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, arrc, n, bl, n2);
        n2 = 0;
        return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, arrc, n, bl, n2);
    }

    static /* synthetic */ Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, String[] arrstring, int n, boolean bl, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        if ((n3 & 8) == 0) return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, arrstring, n, bl, n2);
        n2 = 0;
        return StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt(charSequence, arrstring, n, bl, n2);
    }

    public static final boolean regionMatchesImpl(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$regionMatchesImpl");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        if (n2 < 0) return false;
        if (n < 0) return false;
        if (n > charSequence.length() - n3) return false;
        if (n2 > charSequence2.length() - n3) {
            return false;
        }
        int n4 = 0;
        while (n4 < n3) {
            if (!CharsKt.equals(charSequence.charAt(n + n4), charSequence2.charAt(n2 + n4), bl)) {
                return false;
            }
            ++n4;
        }
        return true;
    }

    public static final CharSequence removePrefix(CharSequence charSequence, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removePrefix");
        Intrinsics.checkParameterIsNotNull(charSequence2, "prefix");
        if (!StringsKt.startsWith$default(charSequence, charSequence2, false, 2, null)) return charSequence.subSequence(0, charSequence.length());
        return charSequence.subSequence(charSequence2.length(), charSequence.length());
    }

    public static final String removePrefix(String string2, CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$removePrefix");
        Intrinsics.checkParameterIsNotNull(charSequence, "prefix");
        String string3 = string2;
        if (!StringsKt.startsWith$default((CharSequence)string2, charSequence, false, 2, null)) return string3;
        string3 = string2.substring(charSequence.length());
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).substring(startIndex)");
        return string3;
    }

    public static final CharSequence removeRange(CharSequence charSequence, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removeRange");
        if (n2 < n) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("End index (");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(") is less than start index (");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(").");
            throw (Throwable)new IndexOutOfBoundsException(((StringBuilder)charSequence).toString());
        }
        if (n2 == n) {
            return charSequence.subSequence(0, charSequence.length());
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence.length() - (n2 - n));
        stringBuilder.append(charSequence, 0, n);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, startIndex, endIndex)");
        stringBuilder.append(charSequence, n2, charSequence.length());
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, startIndex, endIndex)");
        return stringBuilder;
    }

    public static final CharSequence removeRange(CharSequence charSequence, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removeRange");
        Intrinsics.checkParameterIsNotNull(intRange, "range");
        return StringsKt.removeRange(charSequence, ((Integer)intRange.getStart()).intValue(), (Integer)intRange.getEndInclusive() + 1);
    }

    private static final String removeRange(String string2, int n, int n2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.removeRange((CharSequence)string2, n, n2)).toString();
    }

    private static final String removeRange(String string2, IntRange intRange) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.removeRange((CharSequence)string2, intRange)).toString();
    }

    public static final CharSequence removeSuffix(CharSequence charSequence, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removeSuffix");
        Intrinsics.checkParameterIsNotNull(charSequence2, "suffix");
        if (!StringsKt.endsWith$default(charSequence, charSequence2, false, 2, null)) return charSequence.subSequence(0, charSequence.length());
        return charSequence.subSequence(0, charSequence.length() - charSequence2.length());
    }

    public static final String removeSuffix(String string2, CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$removeSuffix");
        Intrinsics.checkParameterIsNotNull(charSequence, "suffix");
        String string3 = string2;
        if (!StringsKt.endsWith$default((CharSequence)string2, charSequence, false, 2, null)) return string3;
        string3 = string2.substring(0, string2.length() - charSequence.length());
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    public static final CharSequence removeSurrounding(CharSequence charSequence, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removeSurrounding");
        Intrinsics.checkParameterIsNotNull(charSequence2, "delimiter");
        return StringsKt.removeSurrounding(charSequence, charSequence2, charSequence2);
    }

    public static final CharSequence removeSurrounding(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$removeSurrounding");
        Intrinsics.checkParameterIsNotNull(charSequence2, "prefix");
        Intrinsics.checkParameterIsNotNull(charSequence3, "suffix");
        if (charSequence.length() < charSequence2.length() + charSequence3.length()) return charSequence.subSequence(0, charSequence.length());
        if (!StringsKt.startsWith$default(charSequence, charSequence2, false, 2, null)) return charSequence.subSequence(0, charSequence.length());
        if (!StringsKt.endsWith$default(charSequence, charSequence3, false, 2, null)) return charSequence.subSequence(0, charSequence.length());
        return charSequence.subSequence(charSequence2.length(), charSequence.length() - charSequence3.length());
    }

    public static final String removeSurrounding(String string2, CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$removeSurrounding");
        Intrinsics.checkParameterIsNotNull(charSequence, "delimiter");
        return StringsKt.removeSurrounding(string2, charSequence, charSequence);
    }

    public static final String removeSurrounding(String string2, CharSequence charSequence, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$removeSurrounding");
        Intrinsics.checkParameterIsNotNull(charSequence, "prefix");
        Intrinsics.checkParameterIsNotNull(charSequence2, "suffix");
        String string3 = string2;
        if (string2.length() < charSequence.length() + charSequence2.length()) return string3;
        CharSequence charSequence3 = string2;
        string3 = string2;
        if (!StringsKt.startsWith$default(charSequence3, charSequence, false, 2, null)) return string3;
        string3 = string2;
        if (!StringsKt.endsWith$default(charSequence3, charSequence2, false, 2, null)) return string3;
        string3 = string2.substring(charSequence.length(), string2.length() - charSequence2.length());
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    private static final String replace(CharSequence charSequence, Regex regex, String string2) {
        return regex.replace(charSequence, string2);
    }

    private static final String replace(CharSequence charSequence, Regex regex, Function1<? super MatchResult, ? extends CharSequence> function1) {
        return regex.replace(charSequence, function1);
    }

    public static final String replaceAfter(String string2, char c, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceAfter");
        Intrinsics.checkParameterIsNotNull(string3, "replacement");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        CharSequence charSequence = string2;
        int n = StringsKt.indexOf$default(charSequence, c, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, n + 1, string2.length(), (CharSequence)string3)).toString();
        return string4;
    }

    public static final String replaceAfter(String string2, String string3, String string4, String string5) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceAfter");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "replacement");
        Intrinsics.checkParameterIsNotNull(string5, "missingDelimiterValue");
        CharSequence charSequence = string2;
        int n = StringsKt.indexOf$default(charSequence, string3, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, n + string3.length(), string2.length(), (CharSequence)string4)).toString();
        return string5;
    }

    public static /* synthetic */ String replaceAfter$default(String string2, char c, String string3, String string4, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceAfter(string2, c, string3, string4);
        string4 = string2;
        return StringsKt.replaceAfter(string2, c, string3, string4);
    }

    public static /* synthetic */ String replaceAfter$default(String string2, String string3, String string4, String string5, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceAfter(string2, string3, string4, string5);
        string5 = string2;
        return StringsKt.replaceAfter(string2, string3, string4, string5);
    }

    public static final String replaceAfterLast(String string2, char c, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceAfterLast");
        Intrinsics.checkParameterIsNotNull(string3, "replacement");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        CharSequence charSequence = string2;
        int n = StringsKt.lastIndexOf$default(charSequence, c, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, n + 1, string2.length(), (CharSequence)string3)).toString();
        return string4;
    }

    public static final String replaceAfterLast(String string2, String string3, String string4, String string5) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceAfterLast");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "replacement");
        Intrinsics.checkParameterIsNotNull(string5, "missingDelimiterValue");
        CharSequence charSequence = string2;
        int n = StringsKt.lastIndexOf$default(charSequence, string3, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, n + string3.length(), string2.length(), (CharSequence)string4)).toString();
        return string5;
    }

    public static /* synthetic */ String replaceAfterLast$default(String string2, char c, String string3, String string4, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceAfterLast(string2, c, string3, string4);
        string4 = string2;
        return StringsKt.replaceAfterLast(string2, c, string3, string4);
    }

    public static /* synthetic */ String replaceAfterLast$default(String string2, String string3, String string4, String string5, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceAfterLast(string2, string3, string4, string5);
        string5 = string2;
        return StringsKt.replaceAfterLast(string2, string3, string4, string5);
    }

    public static final String replaceBefore(String charSequence, char c, String string2, String string3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceBefore");
        Intrinsics.checkParameterIsNotNull(string2, "replacement");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        charSequence = charSequence;
        int n = StringsKt.indexOf$default(charSequence, c, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, 0, n, (CharSequence)string2)).toString();
        return string3;
    }

    public static final String replaceBefore(String charSequence, String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceBefore");
        Intrinsics.checkParameterIsNotNull(string2, "delimiter");
        Intrinsics.checkParameterIsNotNull(string3, "replacement");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        charSequence = charSequence;
        int n = StringsKt.indexOf$default(charSequence, string2, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, 0, n, (CharSequence)string3)).toString();
        return string4;
    }

    public static /* synthetic */ String replaceBefore$default(String string2, char c, String string3, String string4, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceBefore(string2, c, string3, string4);
        string4 = string2;
        return StringsKt.replaceBefore(string2, c, string3, string4);
    }

    public static /* synthetic */ String replaceBefore$default(String string2, String string3, String string4, String string5, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceBefore(string2, string3, string4, string5);
        string5 = string2;
        return StringsKt.replaceBefore(string2, string3, string4, string5);
    }

    public static final String replaceBeforeLast(String charSequence, char c, String string2, String string3) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceBeforeLast");
        Intrinsics.checkParameterIsNotNull(string2, "replacement");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        charSequence = charSequence;
        int n = StringsKt.lastIndexOf$default(charSequence, c, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, 0, n, (CharSequence)string2)).toString();
        return string3;
    }

    public static final String replaceBeforeLast(String charSequence, String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceBeforeLast");
        Intrinsics.checkParameterIsNotNull(string2, "delimiter");
        Intrinsics.checkParameterIsNotNull(string3, "replacement");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        charSequence = charSequence;
        int n = StringsKt.lastIndexOf$default(charSequence, string2, 0, false, 6, null);
        if (n != -1) return ((Object)StringsKt.replaceRange(charSequence, 0, n, (CharSequence)string3)).toString();
        return string4;
    }

    public static /* synthetic */ String replaceBeforeLast$default(String string2, char c, String string3, String string4, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceBeforeLast(string2, c, string3, string4);
        string4 = string2;
        return StringsKt.replaceBeforeLast(string2, c, string3, string4);
    }

    public static /* synthetic */ String replaceBeforeLast$default(String string2, String string3, String string4, String string5, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceBeforeLast(string2, string3, string4, string5);
        string5 = string2;
        return StringsKt.replaceBeforeLast(string2, string3, string4, string5);
    }

    private static final String replaceFirst(CharSequence charSequence, Regex regex, String string2) {
        return regex.replaceFirst(charSequence, string2);
    }

    public static final CharSequence replaceRange(CharSequence charSequence, int n, int n2, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceRange");
        Intrinsics.checkParameterIsNotNull(charSequence2, "replacement");
        if (n2 >= n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charSequence, 0, n);
            Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, startIndex, endIndex)");
            stringBuilder.append(charSequence2);
            stringBuilder.append(charSequence, n2, charSequence.length());
            Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, startIndex, endIndex)");
            return stringBuilder;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("End index (");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(") is less than start index (");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(").");
        throw (Throwable)new IndexOutOfBoundsException(((StringBuilder)charSequence).toString());
    }

    public static final CharSequence replaceRange(CharSequence charSequence, IntRange intRange, CharSequence charSequence2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$replaceRange");
        Intrinsics.checkParameterIsNotNull(intRange, "range");
        Intrinsics.checkParameterIsNotNull(charSequence2, "replacement");
        return StringsKt.replaceRange(charSequence, ((Integer)intRange.getStart()).intValue(), (Integer)intRange.getEndInclusive() + 1, charSequence2);
    }

    private static final String replaceRange(String string2, int n, int n2, CharSequence charSequence) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.replaceRange((CharSequence)string2, n, n2, charSequence)).toString();
    }

    private static final String replaceRange(String string2, IntRange intRange, CharSequence charSequence) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.replaceRange((CharSequence)string2, intRange, charSequence)).toString();
    }

    private static final List<String> split(CharSequence charSequence, Regex regex, int n) {
        return regex.split(charSequence, n);
    }

    public static final List<String> split(CharSequence charSequence, char[] object, boolean bl, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$split");
        Intrinsics.checkParameterIsNotNull(object, "delimiters");
        if (((char[])object).length == 1) {
            return StringsKt__StringsKt.split$StringsKt__StringsKt(charSequence, String.valueOf(object[0]), bl, n);
        }
        Object object2 = SequencesKt.asIterable(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, (char[])object, 0, bl, n, 2, null));
        object = new ArrayList(CollectionsKt.collectionSizeOrDefault(object2, 10));
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(StringsKt.substring(charSequence, (IntRange)object2.next()));
        }
        return (List)object;
    }

    public static final List<String> split(CharSequence charSequence, String[] object, boolean bl, int n) {
        Object object2;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$split");
        Intrinsics.checkParameterIsNotNull(object, "delimiters");
        int n2 = ((String[])object).length;
        boolean bl2 = true;
        if (n2 == 1) {
            object2 = object[0];
            if (((CharSequence)object2).length() != 0) {
                bl2 = false;
            }
            if (!bl2) {
                return StringsKt__StringsKt.split$StringsKt__StringsKt(charSequence, (String)object2, bl, n);
            }
        }
        object2 = SequencesKt.asIterable(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, (String[])object, 0, bl, n, 2, null));
        object = new ArrayList(CollectionsKt.collectionSizeOrDefault(object2, 10));
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(StringsKt.substring(charSequence, (IntRange)object2.next()));
        }
        return (List)object;
    }

    private static final List<String> split$StringsKt__StringsKt(CharSequence charSequence, String string2, boolean bl, int n) {
        int n2;
        int n3 = 0;
        boolean bl2 = n >= 0;
        if (!bl2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Limit must be non-negative, but was ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append('.');
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        int n4 = StringsKt.indexOf(charSequence, string2, 0, bl);
        if (n4 == -1) return CollectionsKt.listOf(((Object)charSequence).toString());
        if (n == 1) {
            return CollectionsKt.listOf(((Object)charSequence).toString());
        }
        bl2 = n > 0;
        int n5 = 10;
        if (bl2) {
            n5 = RangesKt.coerceAtMost(n, 10);
        }
        ArrayList<String> arrayList = new ArrayList<String>(n5);
        n5 = n4;
        do {
            arrayList.add(((Object)charSequence.subSequence(n3, n5)).toString());
            n4 = string2.length() + n5;
            if (bl2 && arrayList.size() == n - 1) break;
            n2 = StringsKt.indexOf(charSequence, string2, n4, bl);
            n3 = n4;
            n5 = n2;
        } while (n2 != -1);
        arrayList.add(((Object)charSequence.subSequence(n4, charSequence.length())).toString());
        return arrayList;
    }

    static /* synthetic */ List split$default(CharSequence charSequence, Regex regex, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return regex.split(charSequence, n);
        n = 0;
        return regex.split(charSequence, n);
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, char[] arrc, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) == 0) return StringsKt.split(charSequence, arrc, bl, n);
        n = 0;
        return StringsKt.split(charSequence, arrc, bl, n);
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, String[] arrstring, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) == 0) return StringsKt.split(charSequence, arrstring, bl, n);
        n = 0;
        return StringsKt.split(charSequence, arrstring, bl, n);
    }

    public static final Sequence<String> splitToSequence(CharSequence charSequence, char[] arrc, boolean bl, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$splitToSequence");
        Intrinsics.checkParameterIsNotNull(arrc, "delimiters");
        return SequencesKt.map(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, arrc, 0, bl, n, 2, null), (Function1)new Function1<IntRange, String>(charSequence){
            final /* synthetic */ CharSequence $this_splitToSequence;
            {
                this.$this_splitToSequence = charSequence;
                super(1);
            }

            public final String invoke(IntRange intRange) {
                Intrinsics.checkParameterIsNotNull(intRange, "it");
                return StringsKt.substring(this.$this_splitToSequence, intRange);
            }
        });
    }

    public static final Sequence<String> splitToSequence(CharSequence charSequence, String[] arrstring, boolean bl, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$splitToSequence");
        Intrinsics.checkParameterIsNotNull(arrstring, "delimiters");
        return SequencesKt.map(StringsKt__StringsKt.rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, arrstring, 0, bl, n, 2, null), (Function1)new Function1<IntRange, String>(charSequence){
            final /* synthetic */ CharSequence $this_splitToSequence;
            {
                this.$this_splitToSequence = charSequence;
                super(1);
            }

            public final String invoke(IntRange intRange) {
                Intrinsics.checkParameterIsNotNull(intRange, "it");
                return StringsKt.substring(this.$this_splitToSequence, intRange);
            }
        });
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, char[] arrc, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) == 0) return StringsKt.splitToSequence(charSequence, arrc, bl, n);
        n = 0;
        return StringsKt.splitToSequence(charSequence, arrc, bl, n);
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, String[] arrstring, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) == 0) return StringsKt.splitToSequence(charSequence, arrstring, bl, n);
        n = 0;
        return StringsKt.splitToSequence(charSequence, arrstring, bl, n);
    }

    public static final boolean startsWith(CharSequence charSequence, char c, boolean bl) {
        boolean bl2;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$startsWith");
        int n = charSequence.length();
        boolean bl3 = bl2 = false;
        if (n <= 0) return bl3;
        bl3 = bl2;
        if (!CharsKt.equals(charSequence.charAt(0), c, bl)) return bl3;
        return true;
    }

    public static final boolean startsWith(CharSequence charSequence, CharSequence charSequence2, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(charSequence2, "prefix");
        if (bl) return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence instanceof String)) return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence2 instanceof String)) return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, 0, charSequence2.length(), bl);
        return StringsKt.startsWith$default((String)charSequence, (String)charSequence2, n, false, 4, null);
    }

    public static final boolean startsWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(charSequence2, "prefix");
        if (bl) return StringsKt.regionMatchesImpl(charSequence, 0, charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence instanceof String)) return StringsKt.regionMatchesImpl(charSequence, 0, charSequence2, 0, charSequence2.length(), bl);
        if (!(charSequence2 instanceof String)) return StringsKt.regionMatchesImpl(charSequence, 0, charSequence2, 0, charSequence2.length(), bl);
        return StringsKt.startsWith$default((String)charSequence, (String)charSequence2, false, 2, null);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, char c, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.startsWith(charSequence, c, bl);
        bl = false;
        return StringsKt.startsWith(charSequence, c, bl);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 4) == 0) return StringsKt.startsWith(charSequence, charSequence2, n, bl);
        bl = false;
        return StringsKt.startsWith(charSequence, charSequence2, n, bl);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.startsWith(charSequence, charSequence2, bl);
        bl = false;
        return StringsKt.startsWith(charSequence, charSequence2, bl);
    }

    public static final CharSequence subSequence(CharSequence charSequence, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$subSequence");
        Intrinsics.checkParameterIsNotNull(intRange, "range");
        return charSequence.subSequence((Integer)intRange.getStart(), (Integer)intRange.getEndInclusive() + 1);
    }

    @Deprecated(message="Use parameters named startIndex and endIndex.", replaceWith=@ReplaceWith(expression="subSequence(startIndex = start, endIndex = end)", imports={}))
    private static final CharSequence subSequence(String string2, int n, int n2) {
        return string2.subSequence(n, n2);
    }

    private static final String substring(CharSequence charSequence, int n, int n2) {
        return ((Object)charSequence.subSequence(n, n2)).toString();
    }

    public static final String substring(CharSequence charSequence, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$substring");
        Intrinsics.checkParameterIsNotNull(intRange, "range");
        return ((Object)charSequence.subSequence((Integer)intRange.getStart(), (Integer)intRange.getEndInclusive() + 1)).toString();
    }

    public static final String substring(String string2, IntRange intRange) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substring");
        Intrinsics.checkParameterIsNotNull(intRange, "range");
        string2 = string2.substring((Integer)intRange.getStart(), (Integer)intRange.getEndInclusive() + 1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    static /* synthetic */ String substring$default(CharSequence charSequence, int n, int n2, int n3, Object object) {
        if ((n3 & 2) == 0) return ((Object)charSequence.subSequence(n, n2)).toString();
        n2 = charSequence.length();
        return ((Object)charSequence.subSequence(n, n2)).toString();
    }

    public static final String substringAfter(String string2, char c, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringAfter");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string2, c, 0, false, 6, null);
        if (n == -1) {
            return string3;
        }
        string3 = string2.substring(n + 1, string2.length());
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    public static final String substringAfter(String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringAfter");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string2, string3, 0, false, 6, null);
        if (n == -1) {
            return string4;
        }
        string4 = string2.substring(n + string3.length(), string2.length());
        Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string4;
    }

    public static /* synthetic */ String substringAfter$default(String string2, char c, String string3, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringAfter(string2, c, string3);
        string3 = string2;
        return StringsKt.substringAfter(string2, c, string3);
    }

    public static /* synthetic */ String substringAfter$default(String string2, String string3, String string4, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringAfter(string2, string3, string4);
        string4 = string2;
        return StringsKt.substringAfter(string2, string3, string4);
    }

    public static final String substringAfterLast(String string2, char c, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringAfterLast");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string2, c, 0, false, 6, null);
        if (n == -1) {
            return string3;
        }
        string3 = string2.substring(n + 1, string2.length());
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    public static final String substringAfterLast(String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringAfterLast");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string2, string3, 0, false, 6, null);
        if (n == -1) {
            return string4;
        }
        string4 = string2.substring(n + string3.length(), string2.length());
        Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string4;
    }

    public static /* synthetic */ String substringAfterLast$default(String string2, char c, String string3, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringAfterLast(string2, c, string3);
        string3 = string2;
        return StringsKt.substringAfterLast(string2, c, string3);
    }

    public static /* synthetic */ String substringAfterLast$default(String string2, String string3, String string4, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringAfterLast(string2, string3, string4);
        string4 = string2;
        return StringsKt.substringAfterLast(string2, string3, string4);
    }

    public static final String substringBefore(String string2, char c, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringBefore");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string2, c, 0, false, 6, null);
        if (n == -1) {
            return string3;
        }
        string3 = string2.substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    public static final String substringBefore(String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringBefore");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        int n = StringsKt.indexOf$default((CharSequence)string2, string3, 0, false, 6, null);
        if (n == -1) {
            return string4;
        }
        string4 = string2.substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string4;
    }

    public static /* synthetic */ String substringBefore$default(String string2, char c, String string3, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringBefore(string2, c, string3);
        string3 = string2;
        return StringsKt.substringBefore(string2, c, string3);
    }

    public static /* synthetic */ String substringBefore$default(String string2, String string3, String string4, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringBefore(string2, string3, string4);
        string4 = string2;
        return StringsKt.substringBefore(string2, string3, string4);
    }

    public static final String substringBeforeLast(String string2, char c, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringBeforeLast");
        Intrinsics.checkParameterIsNotNull(string3, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string2, c, 0, false, 6, null);
        if (n == -1) {
            return string3;
        }
        string3 = string2.substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string3;
    }

    public static final String substringBeforeLast(String string2, String string3, String string4) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$substringBeforeLast");
        Intrinsics.checkParameterIsNotNull(string3, "delimiter");
        Intrinsics.checkParameterIsNotNull(string4, "missingDelimiterValue");
        int n = StringsKt.lastIndexOf$default((CharSequence)string2, string3, 0, false, 6, null);
        if (n == -1) {
            return string4;
        }
        string4 = string2.substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(string4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string4;
    }

    public static /* synthetic */ String substringBeforeLast$default(String string2, char c, String string3, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringBeforeLast(string2, c, string3);
        string3 = string2;
        return StringsKt.substringBeforeLast(string2, c, string3);
    }

    public static /* synthetic */ String substringBeforeLast$default(String string2, String string3, String string4, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.substringBeforeLast(string2, string3, string4);
        string4 = string2;
        return StringsKt.substringBeforeLast(string2, string3, string4);
    }

    public static final CharSequence trim(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trim");
        int n = charSequence.length() - 1;
        int n2 = 0;
        boolean bl = false;
        while (n2 <= n) {
            int n3 = !bl ? n2 : n;
            boolean bl2 = CharsKt.isWhitespace(charSequence.charAt(n3));
            if (!bl) {
                if (!bl2) {
                    bl = true;
                    continue;
                }
                ++n2;
                continue;
            }
            if (!bl2) {
                return charSequence.subSequence(n2, n + 1);
            }
            --n;
        }
        return charSequence.subSequence(n2, n + 1);
    }

    public static final CharSequence trim(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trim");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n = charSequence.length() - 1;
        int n2 = 0;
        boolean bl = false;
        while (n2 <= n) {
            int n3 = !bl ? n2 : n;
            boolean bl2 = function1.invoke(Character.valueOf(charSequence.charAt(n3)));
            if (!bl) {
                if (!bl2) {
                    bl = true;
                    continue;
                }
                ++n2;
                continue;
            }
            if (!bl2) {
                return charSequence.subSequence(n2, n + 1);
            }
            --n;
        }
        return charSequence.subSequence(n2, n + 1);
    }

    public static final CharSequence trim(CharSequence charSequence, char ... arrc) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trim");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        int n = charSequence.length() - 1;
        int n2 = 0;
        boolean bl = false;
        while (n2 <= n) {
            int n3 = !bl ? n2 : n;
            boolean bl2 = ArraysKt.contains(arrc, charSequence.charAt(n3));
            if (!bl) {
                if (!bl2) {
                    bl = true;
                    continue;
                }
                ++n2;
                continue;
            }
            if (!bl2) {
                return charSequence.subSequence(n2, n + 1);
            }
            --n;
        }
        return charSequence.subSequence(n2, n + 1);
    }

    private static final String trim(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.trim((CharSequence)string2)).toString();
    }

    public static final String trim(String charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trim");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        charSequence = charSequence;
        int n = charSequence.length() - 1;
        int n2 = 0;
        boolean bl = false;
        while (n2 <= n) {
            int n3 = !bl ? n2 : n;
            boolean bl2 = function1.invoke(Character.valueOf(charSequence.charAt(n3)));
            if (!bl) {
                if (!bl2) {
                    bl = true;
                    continue;
                }
                ++n2;
                continue;
            }
            if (!bl2) {
                return ((Object)charSequence.subSequence(n2, n + 1)).toString();
            }
            --n;
        }
        return ((Object)charSequence.subSequence(n2, n + 1)).toString();
    }

    public static final String trim(String charSequence, char ... arrc) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trim");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        charSequence = charSequence;
        int n = charSequence.length() - 1;
        int n2 = 0;
        boolean bl = false;
        while (n2 <= n) {
            int n3 = !bl ? n2 : n;
            boolean bl2 = ArraysKt.contains(arrc, charSequence.charAt(n3));
            if (!bl) {
                if (!bl2) {
                    bl = true;
                    continue;
                }
                ++n2;
                continue;
            }
            if (!bl2) {
                return ((Object)charSequence.subSequence(n2, n + 1)).toString();
            }
            --n;
        }
        return ((Object)charSequence.subSequence(n2, n + 1)).toString();
    }

    public static final CharSequence trimEnd(CharSequence charSequence) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimEnd");
        int n2 = charSequence.length();
        do {
            if ((n = n2 - 1) < 0) {
                return "";
            }
            n2 = n;
        } while (CharsKt.isWhitespace(charSequence.charAt(n)));
        return charSequence.subSequence(0, n + 1);
    }

    public static final CharSequence trimEnd(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimEnd");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        int n2 = charSequence.length();
        do {
            if ((n = n2 - 1) < 0) return "";
            n2 = n;
        } while (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue());
        return charSequence.subSequence(0, n + 1);
    }

    public static final CharSequence trimEnd(CharSequence charSequence, char ... arrc) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimEnd");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        int n2 = charSequence.length();
        do {
            if ((n = n2 - 1) < 0) {
                return "";
            }
            n2 = n;
        } while (ArraysKt.contains(arrc, charSequence.charAt(n)));
        return charSequence.subSequence(0, n + 1);
    }

    private static final String trimEnd(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.trimEnd((CharSequence)string2)).toString();
    }

    public static final String trimEnd(String charSequence, Function1<? super Character, Boolean> function1) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimEnd");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        charSequence = charSequence;
        int n2 = charSequence.length();
        do {
            if ((n = n2 - 1) < 0) {
                charSequence = "";
                return charSequence.toString();
            }
            n2 = n;
        } while (function1.invoke(Character.valueOf(charSequence.charAt(n))).booleanValue());
        charSequence = charSequence.subSequence(0, n + 1);
        return charSequence.toString();
    }

    public static final String trimEnd(String charSequence, char ... arrc) {
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimEnd");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        charSequence = charSequence;
        int n2 = charSequence.length();
        do {
            if ((n = n2 - 1) < 0) {
                charSequence = "";
                return charSequence.toString();
            }
            n2 = n;
        } while (ArraysKt.contains(arrc, charSequence.charAt(n)));
        charSequence = charSequence.subSequence(0, n + 1);
        return charSequence.toString();
    }

    public static final CharSequence trimStart(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimStart");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (!CharsKt.isWhitespace(charSequence.charAt(n2))) {
                return charSequence.subSequence(n2, charSequence.length());
            }
            ++n2;
        }
        return "";
    }

    public static final CharSequence trimStart(CharSequence charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimStart");
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

    public static final CharSequence trimStart(CharSequence charSequence, char ... arrc) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimStart");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (!ArraysKt.contains(arrc, charSequence.charAt(n2))) {
                return charSequence.subSequence(n2, charSequence.length());
            }
            ++n2;
        }
        return "";
    }

    private static final String trimStart(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        return ((Object)StringsKt.trimStart((CharSequence)string2)).toString();
    }

    public static final String trimStart(String charSequence, Function1<? super Character, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimStart");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        charSequence = charSequence;
        int n = charSequence.length();
        int n2 = 0;
        do {
            if (n2 >= n) {
                charSequence = "";
                return charSequence.toString();
            }
            if (!function1.invoke(Character.valueOf(charSequence.charAt(n2))).booleanValue()) {
                charSequence = charSequence.subSequence(n2, charSequence.length());
                return charSequence.toString();
            }
            ++n2;
        } while (true);
    }

    public static final String trimStart(String charSequence, char ... arrc) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$trimStart");
        Intrinsics.checkParameterIsNotNull(arrc, "chars");
        charSequence = charSequence;
        int n = charSequence.length();
        int n2 = 0;
        do {
            if (n2 >= n) {
                charSequence = "";
                return charSequence.toString();
            }
            if (!ArraysKt.contains(arrc, charSequence.charAt(n2))) {
                charSequence = charSequence.subSequence(n2, charSequence.length());
                return charSequence.toString();
            }
            ++n2;
        } while (true);
    }
}

