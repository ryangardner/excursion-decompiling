package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000~\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0017\u001a]\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aU\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u001e\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\u001a1\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0012\u001a1\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0016\u001a!\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a!\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a*\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a*\u0010\u001d\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a9\u0010\u001e\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\"\u001a1\u0010#\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b *\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0002\b\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\"\u001a7\u0010$\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\t\b\u0001\u0010\u0003¢\u0006\u0002\b *\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010%\u001a\u0002H\u0003H\u0087\b¢\u0006\u0002\u0010\"\u001aS\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b\u001aG\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b\u001aS\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b\u001an\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b¢\u0006\u0002\u0010-\u001an\u0010.\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b¢\u0006\u0002\u0010-\u001aG\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\b\u001a;\u00100\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u00101\u001a@\u00102\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0087\b¢\u0006\u0002\u00105\u001a@\u00106\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0080\b¢\u0006\u0002\u00105\u001a@\u00107\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0086\b¢\u0006\u0002\u00105\u001a1\u00108\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u00101\u001a<\u00109\u001a\u0002H:\"\u0014\b\u0000\u0010+*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0001*\u0002H:\"\u0004\b\u0001\u0010:*\u0002H+2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H:04H\u0087\b¢\u0006\u0002\u0010;\u001a'\u0010<\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u001a:\u0010=\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a9\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b0?\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001a<\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030A0@\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\bH\u0087\n¢\u0006\u0002\bB\u001aY\u0010C\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\b\u001at\u0010E\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H:\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\b¢\u0006\u0002\u0010-\u001aY\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H:0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\b\u001at\u0010G\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H:0\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\b¢\u0006\u0002\u0010-\u001a@\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\u0002¢\u0006\u0002\u0010I\u001aH\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\u0002¢\u0006\u0002\u0010K\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\u0002\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\u0002\u001a2\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010O\u001a:\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\n¢\u0006\u0002\u0010P\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\n\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\n\u001a0\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0000\u001a3\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u001aT\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0086\u0002¢\u0006\u0002\u0010T\u001aG\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0086\u0002\u001aI\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0014\u0010V\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0086\u0002\u001aJ\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0087\n¢\u0006\u0002\u0010X\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0087\n\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0087\n\u001aG\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010X\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001a;\u0010Z\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u00101\u001a:\u0010[\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\u0006\u0010%\u001a\u0002H\u0003H\u0087\n¢\u0006\u0002\u0010\\\u001a;\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010\u0018\u001aQ\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u00102\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010^\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010_\u001a2\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aM\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+H\u0007¢\u0006\u0002\u0010`\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010a\u001a2\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001a1\u0010c\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\b¨\u0006d"},
   d2 = {"buildMap", "", "K", "V", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "", "Lkotlin/ExtensionFunctionType;", "emptyMap", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
   public MapsKt__MapsKt() {
   }

   private static final <K, V> Map<K, V> buildMap(int var0, Function1<? super Map<K, V>, Unit> var1) {
      LinkedHashMap var2 = new LinkedHashMap(MapsKt.mapCapacity(var0));
      var1.invoke(var2);
      return (Map)var2;
   }

   private static final <K, V> Map<K, V> buildMap(Function1<? super Map<K, V>, Unit> var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      var0.invoke(var1);
      return (Map)var1;
   }

   private static final <K, V> K component1(Entry<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$component1");
      return var0.getKey();
   }

   private static final <K, V> V component2(Entry<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$component2");
      return var0.getValue();
   }

   private static final <K, V> boolean contains(Map<? extends K, ? extends V> var0, K var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.containsKey(var1);
   }

   private static final <K> boolean containsKey(Map<? extends K, ?> var0, K var1) {
      if (var0 != null) {
         return var0.containsKey(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
      }
   }

   private static final <K, V> boolean containsValue(Map<K, ? extends V> var0, V var1) {
      return var0.containsValue(var1);
   }

   public static final <K, V> Map<K, V> emptyMap() {
      EmptyMap var0 = EmptyMap.INSTANCE;
      if (var0 != null) {
         return (Map)var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
      }
   }

   public static final <K, V> Map<K, V> filter(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filter");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Map var2 = (Map)(new LinkedHashMap());
      Iterator var4 = var0.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var3 = (Entry)var4.next();
         if ((Boolean)var1.invoke(var3)) {
            var2.put(var3.getKey(), var3.getValue());
         }
      }

      return var2;
   }

   public static final <K, V> Map<K, V> filterKeys(Map<? extends K, ? extends V> var0, Function1<? super K, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterKeys");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      LinkedHashMap var2 = new LinkedHashMap();
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if ((Boolean)var1.invoke(var4.getKey())) {
            var2.put(var4.getKey(), var4.getValue());
         }
      }

      return (Map)var2;
   }

   public static final <K, V> Map<K, V> filterNot(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterNot");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      Map var2 = (Map)(new LinkedHashMap());
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (!(Boolean)var1.invoke(var4)) {
            var2.put(var4.getKey(), var4.getValue());
         }
      }

      return var2;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M filterNotTo(Map<? extends K, ? extends V> var0, M var1, Function1<? super Entry<? extends K, ? extends V>, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterNotTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");
      Iterator var4 = var0.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var3 = (Entry)var4.next();
         if (!(Boolean)var2.invoke(var3)) {
            var1.put(var3.getKey(), var3.getValue());
         }
      }

      return var1;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M filterTo(Map<? extends K, ? extends V> var0, M var1, Function1<? super Entry<? extends K, ? extends V>, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if ((Boolean)var2.invoke(var4)) {
            var1.put(var4.getKey(), var4.getValue());
         }
      }

      return var1;
   }

   public static final <K, V> Map<K, V> filterValues(Map<? extends K, ? extends V> var0, Function1<? super V, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterValues");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      LinkedHashMap var2 = new LinkedHashMap();
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if ((Boolean)var1.invoke(var4.getValue())) {
            var2.put(var4.getKey(), var4.getValue());
         }
      }

      return (Map)var2;
   }

   private static final <K, V> V get(Map<? extends K, ? extends V> var0, K var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$get");
      return var0.get(var1);
   }

   private static final <K, V> V getOrElse(Map<K, ? extends V> var0, K var1, Function0<? extends V> var2) {
      Object var3 = var0.get(var1);
      if (var3 == null) {
         var3 = var2.invoke();
      }

      return var3;
   }

   public static final <K, V> V getOrElseNullable(Map<K, ? extends V> var0, K var1, Function0<? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getOrElseNullable");
      Intrinsics.checkParameterIsNotNull(var2, "defaultValue");
      Object var3 = var0.get(var1);
      return var3 == null && !var0.containsKey(var1) ? var2.invoke() : var3;
   }

   public static final <K, V> V getOrPut(Map<K, V> var0, K var1, Function0<? extends V> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getOrPut");
      Intrinsics.checkParameterIsNotNull(var2, "defaultValue");
      Object var3 = var0.get(var1);
      Object var4 = var3;
      if (var3 == null) {
         var4 = var2.invoke();
         var0.put(var1, var4);
      }

      return var4;
   }

   public static final <K, V> V getValue(Map<K, ? extends V> var0, K var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getValue");
      return MapsKt.getOrImplicitDefaultNullable(var0, var1);
   }

   private static final <K, V> HashMap<K, V> hashMapOf() {
      return new HashMap();
   }

   public static final <K, V> HashMap<K, V> hashMapOf(Pair<? extends K, ? extends V>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pairs");
      HashMap var1 = new HashMap(MapsKt.mapCapacity(var0.length));
      MapsKt.putAll((Map)var1, var0);
      return var1;
   }

   private static final <M extends Map<?, ?> & R, R> R ifEmpty(M var0, Function0<? extends R> var1) {
      Object var2 = var0;
      if (var0.isEmpty()) {
         var2 = var1.invoke();
      }

      return var2;
   }

   private static final <K, V> boolean isNotEmpty(Map<? extends K, ? extends V> var0) {
      return var0.isEmpty() ^ true;
   }

   private static final <K, V> boolean isNullOrEmpty(Map<? extends K, ? extends V> var0) {
      boolean var1;
      if (var0 != null && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static final <K, V> Iterator<Entry<K, V>> iterator(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$iterator");
      return var0.entrySet().iterator();
   }

   private static final <K, V> LinkedHashMap<K, V> linkedMapOf() {
      return new LinkedHashMap();
   }

   public static final <K, V> LinkedHashMap<K, V> linkedMapOf(Pair<? extends K, ? extends V>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pairs");
      return (LinkedHashMap)MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length))));
   }

   public static final <K, V, R> Map<R, V> mapKeys(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapKeys");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Map var2 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.size())));
      Iterator var3 = ((Iterable)var0.entrySet()).iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var2.put(var1.invoke(var4), ((Entry)var4).getValue());
      }

      return var2;
   }

   public static final <K, V, R, M extends Map<? super R, ? super V>> M mapKeysTo(Map<? extends K, ? extends V> var0, M var1, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapKeysTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      Iterator var4 = ((Iterable)var0.entrySet()).iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         var1.put(var2.invoke(var3), ((Entry)var3).getValue());
      }

      return var1;
   }

   private static final <K, V> Map<K, V> mapOf() {
      return MapsKt.emptyMap();
   }

   public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pairs");
      Map var1;
      if (var0.length > 0) {
         var1 = MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length))));
      } else {
         var1 = MapsKt.emptyMap();
      }

      return var1;
   }

   public static final <K, V, R> Map<K, R> mapValues(Map<? extends K, ? extends V> var0, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapValues");
      Intrinsics.checkParameterIsNotNull(var1, "transform");
      Map var2 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.size())));
      Iterator var4 = ((Iterable)var0.entrySet()).iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         var2.put(((Entry)var3).getKey(), var1.invoke(var3));
      }

      return var2;
   }

   public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesTo(Map<? extends K, ? extends V> var0, M var1, Function1<? super Entry<? extends K, ? extends V>, ? extends R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$mapValuesTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      Iterator var3 = ((Iterable)var0.entrySet()).iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var1.put(((Entry)var4).getKey(), var2.invoke(var4));
      }

      return var1;
   }

   public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> var0, Iterable<? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "keys");
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> var0, K var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      var0 = MapsKt.toMutableMap(var0);
      var0.remove(var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> var0, Sequence<? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "keys");
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   public static final <K, V> Map<K, V> minus(Map<? extends K, ? extends V> var0, K[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      Intrinsics.checkParameterIsNotNull(var1, "keys");
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   private static final <K, V> void minusAssign(Map<K, V> var0, Iterable<? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   private static final <K, V> void minusAssign(Map<K, V> var0, K var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      var0.remove(var1);
   }

   private static final <K, V> void minusAssign(Map<K, V> var0, Sequence<? extends K> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   private static final <K, V> void minusAssign(Map<K, V> var0, K[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusAssign");
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   private static final <K, V> Iterator<Entry<K, V>> mutableIterator(Map<K, V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$iterator");
      return var0.entrySet().iterator();
   }

   private static final <K, V> Map<K, V> mutableMapOf() {
      return (Map)(new LinkedHashMap());
   }

   public static final <K, V> Map<K, V> mutableMapOf(Pair<? extends K, ? extends V>... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "pairs");
      Map var1 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length)));
      MapsKt.putAll(var1, var0);
      return var1;
   }

   public static final <K, V> Map<K, V> optimizeReadOnlyMap(Map<K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$optimizeReadOnlyMap");
      int var1 = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            var0 = MapsKt.toSingletonMap(var0);
         }
      } else {
         var0 = MapsKt.emptyMap();
      }

      return var0;
   }

   private static final <K, V> Map<K, V> orEmpty(Map<K, ? extends V> var0) {
      if (var0 == null) {
         var0 = MapsKt.emptyMap();
      }

      return var0;
   }

   public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> var0, Iterable<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      if (var0.isEmpty()) {
         var0 = MapsKt.toMap(var1);
      } else {
         var0 = (Map)(new LinkedHashMap(var0));
         MapsKt.putAll(var0, var1);
      }

      return var0;
   }

   public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> var0, Map<? extends K, ? extends V> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "map");
      LinkedHashMap var2 = new LinkedHashMap(var0);
      var2.putAll(var1);
      return (Map)var2;
   }

   public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> var0, Pair<? extends K, ? extends V> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "pair");
      if (var0.isEmpty()) {
         var0 = MapsKt.mapOf(var1);
      } else {
         LinkedHashMap var2 = new LinkedHashMap(var0);
         var2.put(var1.getFirst(), var1.getSecond());
         var0 = (Map)var2;
      }

      return var0;
   }

   public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> var0, Sequence<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      var0 = (Map)(new LinkedHashMap(var0));
      MapsKt.putAll(var0, var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   public static final <K, V> Map<K, V> plus(Map<? extends K, ? extends V> var0, Pair<? extends K, ? extends V>[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      if (var0.isEmpty()) {
         var0 = MapsKt.toMap(var1);
      } else {
         var0 = (Map)(new LinkedHashMap(var0));
         MapsKt.putAll(var0, var1);
      }

      return var0;
   }

   private static final <K, V> void plusAssign(Map<? super K, ? super V> var0, Iterable<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      MapsKt.putAll(var0, var1);
   }

   private static final <K, V> void plusAssign(Map<? super K, ? super V> var0, Map<K, ? extends V> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      var0.putAll(var1);
   }

   private static final <K, V> void plusAssign(Map<? super K, ? super V> var0, Pair<? extends K, ? extends V> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      var0.put(var1.getFirst(), var1.getSecond());
   }

   private static final <K, V> void plusAssign(Map<? super K, ? super V> var0, Sequence<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      MapsKt.putAll(var0, var1);
   }

   private static final <K, V> void plusAssign(Map<? super K, ? super V> var0, Pair<? extends K, ? extends V>[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
      MapsKt.putAll(var0, var1);
   }

   public static final <K, V> void putAll(Map<? super K, ? super V> var0, Iterable<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Pair var3 = (Pair)var2.next();
         var0.put(var3.component1(), var3.component2());
      }

   }

   public static final <K, V> void putAll(Map<? super K, ? super V> var0, Sequence<? extends Pair<? extends K, ? extends V>> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         var0.put(var2.component1(), var2.component2());
      }

   }

   public static final <K, V> void putAll(Map<? super K, ? super V> var0, Pair<? extends K, ? extends V>[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$putAll");
      Intrinsics.checkParameterIsNotNull(var1, "pairs");
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Pair var4 = var1[var3];
         var0.put(var4.component1(), var4.component2());
      }

   }

   private static final <K, V> V remove(Map<? extends K, V> var0, K var1) {
      if (var0 != null) {
         return TypeIntrinsics.asMutableMap(var0).remove(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
      }
   }

   private static final <K, V> void set(Map<K, V> var0, K var1, V var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$set");
      var0.put(var1, var2);
   }

   public static final <K, V> Map<K, V> toMap(Iterable<? extends Pair<? extends K, ? extends V>> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      if (var0 instanceof Collection) {
         Collection var1 = (Collection)var0;
         int var2 = var1.size();
         Map var3;
         if (var2 != 0) {
            if (var2 != 1) {
               var3 = MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var1.size()))));
            } else {
               Object var4;
               if (var0 instanceof List) {
                  var4 = ((List)var0).get(0);
               } else {
                  var4 = var0.iterator().next();
               }

               var3 = MapsKt.mapOf((Pair)var4);
            }
         } else {
            var3 = MapsKt.emptyMap();
         }

         return var3;
      } else {
         return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, (Map)(new LinkedHashMap())));
      }
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Iterable<? extends Pair<? extends K, ? extends V>> var0, M var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      MapsKt.putAll(var1, var0);
      return var1;
   }

   public static final <K, V> Map<K, V> toMap(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      int var1 = var0.size();
      if (var1 != 0) {
         if (var1 != 1) {
            var0 = MapsKt.toMutableMap(var0);
         } else {
            var0 = MapsKt.toSingletonMap(var0);
         }
      } else {
         var0 = MapsKt.emptyMap();
      }

      return var0;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Map<? extends K, ? extends V> var0, M var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      var1.putAll(var0);
      return var1;
   }

   public static final <K, V> Map<K, V> toMap(Sequence<? extends Pair<? extends K, ? extends V>> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, (Map)(new LinkedHashMap())));
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Sequence<? extends Pair<? extends K, ? extends V>> var0, M var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      MapsKt.putAll(var1, var0);
      return var1;
   }

   public static final <K, V> Map<K, V> toMap(Pair<? extends K, ? extends V>[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      int var1 = var0.length;
      Map var2;
      if (var1 != 0) {
         if (var1 != 1) {
            var2 = MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length))));
         } else {
            var2 = MapsKt.mapOf(var0[0]);
         }
      } else {
         var2 = MapsKt.emptyMap();
      }

      return var2;
   }

   public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Pair<? extends K, ? extends V>[] var0, M var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      MapsKt.putAll(var1, var0);
      return var1;
   }

   public static final <K, V> Map<K, V> toMutableMap(Map<? extends K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toMutableMap");
      return (Map)(new LinkedHashMap(var0));
   }

   private static final <K, V> Pair<K, V> toPair(Entry<? extends K, ? extends V> var0) {
      return new Pair(var0.getKey(), var0.getValue());
   }
}
