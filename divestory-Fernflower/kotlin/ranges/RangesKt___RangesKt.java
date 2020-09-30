package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u0016H\u0087\b¢\u0006\u0002\u0010-\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u0010.\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\b*\u00020!H\u0087\b¢\u0006\u0002\u0010/\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00100\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\t*\u00020#H\u0087\b¢\u0006\u0002\u00101\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00102\u001a\n\u00103\u001a\u00020)*\u00020)\u001a\n\u00103\u001a\u00020&*\u00020&\u001a\n\u00103\u001a\u00020(*\u00020(\u001a\u0015\u00104\u001a\u00020)*\u00020)2\u0006\u00104\u001a\u00020\bH\u0086\u0004\u001a\u0015\u00104\u001a\u00020&*\u00020&2\u0006\u00104\u001a\u00020\bH\u0086\u0004\u001a\u0015\u00104\u001a\u00020(*\u00020(2\u0006\u00104\u001a\u00020\tH\u0086\u0004\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00107\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00108\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00109\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u0010@\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010A\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010C\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010D\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010E\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006H"},
   d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/ranges/RangesKt"
)
class RangesKt___RangesKt extends RangesKt__RangesKt {
   public RangesKt___RangesKt() {
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean byteRangeContains(ClosedRange<Byte> var0, double var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Byte var3 = RangesKt.toByteExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean byteRangeContains(ClosedRange<Byte> var0, float var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean byteRangeContains(ClosedRange<Byte> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean byteRangeContains(ClosedRange<Byte> var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Byte var3 = RangesKt.toByteExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   public static final boolean byteRangeContains(ClosedRange<Byte> var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final byte coerceAtLeast(byte var0, byte var1) {
      byte var2 = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final double coerceAtLeast(double var0, double var2) {
      double var4 = var0;
      if (var0 < var2) {
         var4 = var2;
      }

      return var4;
   }

   public static final float coerceAtLeast(float var0, float var1) {
      float var2 = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final int coerceAtLeast(int var0, int var1) {
      int var2 = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final long coerceAtLeast(long var0, long var2) {
      long var4 = var0;
      if (var0 < var2) {
         var4 = var2;
      }

      return var4;
   }

   public static final <T extends Comparable<? super T>> T coerceAtLeast(T var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$coerceAtLeast");
      Intrinsics.checkParameterIsNotNull(var1, "minimumValue");
      Comparable var2 = var0;
      if (var0.compareTo(var1) < 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final short coerceAtLeast(short var0, short var1) {
      short var2 = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final byte coerceAtMost(byte var0, byte var1) {
      byte var2 = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final double coerceAtMost(double var0, double var2) {
      double var4 = var0;
      if (var0 > var2) {
         var4 = var2;
      }

      return var4;
   }

   public static final float coerceAtMost(float var0, float var1) {
      float var2 = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final int coerceAtMost(int var0, int var1) {
      int var2 = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final long coerceAtMost(long var0, long var2) {
      long var4 = var0;
      if (var0 > var2) {
         var4 = var2;
      }

      return var4;
   }

   public static final <T extends Comparable<? super T>> T coerceAtMost(T var0, T var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$coerceAtMost");
      Intrinsics.checkParameterIsNotNull(var1, "maximumValue");
      Comparable var2 = var0;
      if (var0.compareTo(var1) > 0) {
         var2 = var1;
      }

      return var2;
   }

   public static final short coerceAtMost(short var0, short var1) {
      short var2 = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   public static final byte coerceIn(byte var0, byte var1, byte var2) {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return var0 > var2 ? var2 : var0;
         }
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final double coerceIn(double var0, double var2, double var4) {
      if (var2 <= var4) {
         if (var0 < var2) {
            return var2;
         } else {
            return var0 > var4 ? var4 : var0;
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Cannot coerce value to an empty range: maximum ");
         var6.append(var4);
         var6.append(" is less than minimum ");
         var6.append(var2);
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final float coerceIn(float var0, float var1, float var2) {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return var0 > var2 ? var2 : var0;
         }
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final int coerceIn(int var0, int var1, int var2) {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return var0 > var2 ? var2 : var0;
         }
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final int coerceIn(int var0, ClosedRange<Integer> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "range");
      if (var1 instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)var0, (ClosedFloatingPointRange)var1)).intValue();
      } else if (!var1.isEmpty()) {
         int var2;
         if (var0 < ((Number)var1.getStart()).intValue()) {
            var2 = ((Number)var1.getStart()).intValue();
         } else {
            var2 = var0;
            if (var0 > ((Number)var1.getEndInclusive()).intValue()) {
               var2 = ((Number)var1.getEndInclusive()).intValue();
            }
         }

         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final long coerceIn(long var0, long var2, long var4) {
      if (var2 <= var4) {
         if (var0 < var2) {
            return var2;
         } else {
            return var0 > var4 ? var4 : var0;
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Cannot coerce value to an empty range: maximum ");
         var6.append(var4);
         var6.append(" is less than minimum ");
         var6.append(var2);
         var6.append('.');
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final long coerceIn(long var0, ClosedRange<Long> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "range");
      if (var2 instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)var0, (ClosedFloatingPointRange)var2)).longValue();
      } else if (!var2.isEmpty()) {
         long var3;
         if (var0 < ((Number)var2.getStart()).longValue()) {
            var3 = ((Number)var2.getStart()).longValue();
         } else {
            var3 = var0;
            if (var0 > ((Number)var2.getEndInclusive()).longValue()) {
               var3 = ((Number)var2.getEndInclusive()).longValue();
            }
         }

         return var3;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Cannot coerce value to an empty range: ");
         var5.append(var2);
         var5.append('.');
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   public static final <T extends Comparable<? super T>> T coerceIn(T var0, T var1, T var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$coerceIn");
      if (var1 != null && var2 != null) {
         if (var1.compareTo(var2) > 0) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Cannot coerce value to an empty range: maximum ");
            var3.append(var2);
            var3.append(" is less than minimum ");
            var3.append(var1);
            var3.append('.');
            throw (Throwable)(new IllegalArgumentException(var3.toString()));
         }

         if (var0.compareTo(var1) < 0) {
            return var1;
         }

         if (var0.compareTo(var2) > 0) {
            return var2;
         }
      } else {
         if (var1 != null && var0.compareTo(var1) < 0) {
            return var1;
         }

         if (var2 != null && var0.compareTo(var2) > 0) {
            return var2;
         }
      }

      return var0;
   }

   public static final <T extends Comparable<? super T>> T coerceIn(T var0, ClosedFloatingPointRange<T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$coerceIn");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      if (var1.isEmpty()) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      } else {
         Comparable var2;
         if (var1.lessThanOrEquals(var0, var1.getStart()) && !var1.lessThanOrEquals(var1.getStart(), var0)) {
            var2 = var1.getStart();
         } else {
            var2 = var0;
            if (var1.lessThanOrEquals(var1.getEndInclusive(), var0)) {
               var2 = var0;
               if (!var1.lessThanOrEquals(var0, var1.getEndInclusive())) {
                  var2 = var1.getEndInclusive();
               }
            }
         }

         return var2;
      }
   }

   public static final <T extends Comparable<? super T>> T coerceIn(T var0, ClosedRange<T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$coerceIn");
      Intrinsics.checkParameterIsNotNull(var1, "range");
      if (var1 instanceof ClosedFloatingPointRange) {
         return RangesKt.coerceIn(var0, (ClosedFloatingPointRange)var1);
      } else if (!var1.isEmpty()) {
         Comparable var2;
         if (var0.compareTo(var1.getStart()) < 0) {
            var2 = var1.getStart();
         } else {
            var2 = var0;
            if (var0.compareTo(var1.getEndInclusive()) > 0) {
               var2 = var1.getEndInclusive();
            }
         }

         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   public static final short coerceIn(short var0, short var1, short var2) {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return var0 > var2 ? var2 : var0;
         }
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }

   private static final boolean contains(CharRange var0, Character var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var2;
      if (var1 != null && var0.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final boolean contains(IntRange var0, Integer var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var2;
      if (var1 != null && var0.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final boolean contains(LongRange var0, Long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      boolean var2;
      if (var1 != null && var0.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean doubleRangeContains(ClosedRange<Double> var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(double)var1);
   }

   public static final boolean doubleRangeContains(ClosedRange<Double> var0, float var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(double)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean doubleRangeContains(ClosedRange<Double> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(double)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean doubleRangeContains(ClosedRange<Double> var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(double)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean doubleRangeContains(ClosedRange<Double> var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(double)var1);
   }

   public static final CharProgression downTo(char var0, char var1) {
      return CharProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(byte var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(byte var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(byte var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(int var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(int var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(int var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(short var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(short var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final IntProgression downTo(short var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   public static final LongProgression downTo(byte var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   public static final LongProgression downTo(int var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   public static final LongProgression downTo(long var0, byte var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   public static final LongProgression downTo(long var0, int var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   public static final LongProgression downTo(long var0, long var2) {
      return LongProgression.Companion.fromClosedRange(var0, var2, -1L);
   }

   public static final LongProgression downTo(long var0, short var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   public static final LongProgression downTo(short var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean floatRangeContains(ClosedRange<Float> var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(float)var1);
   }

   public static final boolean floatRangeContains(ClosedRange<Float> var0, double var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(float)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean floatRangeContains(ClosedRange<Float> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(float)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean floatRangeContains(ClosedRange<Float> var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(float)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean floatRangeContains(ClosedRange<Float> var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(float)var1);
   }

   public static final boolean intRangeContains(ClosedRange<Integer> var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)Integer.valueOf(var1));
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean intRangeContains(ClosedRange<Integer> var0, double var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Integer var3 = RangesKt.toIntExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean intRangeContains(ClosedRange<Integer> var0, float var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Integer var2 = RangesKt.toIntExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean intRangeContains(ClosedRange<Integer> var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Integer var3 = RangesKt.toIntExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   public static final boolean intRangeContains(ClosedRange<Integer> var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)Integer.valueOf(var1));
   }

   public static final boolean longRangeContains(ClosedRange<Long> var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(long)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean longRangeContains(ClosedRange<Long> var0, double var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Long var3 = RangesKt.toLongExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean longRangeContains(ClosedRange<Long> var0, float var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Long var2 = RangesKt.toLongExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean longRangeContains(ClosedRange<Long> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(long)var1);
   }

   public static final boolean longRangeContains(ClosedRange<Long> var0, short var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(long)var1);
   }

   private static final char random(CharRange var0) {
      return RangesKt.random(var0, (Random)Random.Default);
   }

   public static final char random(CharRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      int var2;
      try {
         var2 = var1.nextInt(var0.getFirst(), var0.getLast() + 1);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }

      return (char)var2;
   }

   private static final int random(IntRange var0) {
      return RangesKt.random(var0, (Random)Random.Default);
   }

   public static final int random(IntRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      try {
         int var2 = RandomKt.nextInt(var1, var0);
         return var2;
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   private static final long random(LongRange var0) {
      return RangesKt.random(var0, (Random)Random.Default);
   }

   public static final long random(LongRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$random");
      Intrinsics.checkParameterIsNotNull(var1, "random");

      try {
         long var2 = RandomKt.nextLong(var1, var0);
         return var2;
      } catch (IllegalArgumentException var4) {
         throw (Throwable)(new NoSuchElementException(var4.getMessage()));
      }
   }

   private static final Character randomOrNull(CharRange var0) {
      return RangesKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final Character randomOrNull(CharRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      return var0.isEmpty() ? null : (char)var1.nextInt(var0.getFirst(), var0.getLast() + 1);
   }

   private static final Integer randomOrNull(IntRange var0) {
      return RangesKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final Integer randomOrNull(IntRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      return var0.isEmpty() ? null : RandomKt.nextInt(var1, var0);
   }

   private static final Long randomOrNull(LongRange var0) {
      return RangesKt.randomOrNull(var0, (Random)Random.Default);
   }

   public static final Long randomOrNull(LongRange var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$randomOrNull");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      return var0.isEmpty() ? null : RandomKt.nextLong(var1, var0);
   }

   public static final CharProgression reversed(CharProgression var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      return CharProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   public static final IntProgression reversed(IntProgression var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      return IntProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   public static final LongProgression reversed(LongProgression var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reversed");
      return LongProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   public static final boolean shortRangeContains(ClosedRange<Short> var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      return var0.contains((Comparable)(short)var1);
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean shortRangeContains(ClosedRange<Short> var0, double var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Short var3 = RangesKt.toShortExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   public static final boolean shortRangeContains(ClosedRange<Short> var0, float var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Short var2 = RangesKt.toShortExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean shortRangeContains(ClosedRange<Short> var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Short var2 = RangesKt.toShortExactOrNull(var1);
      boolean var3;
      if (var2 != null) {
         var3 = var0.contains((Comparable)var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final boolean shortRangeContains(ClosedRange<Short> var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contains");
      Short var3 = RangesKt.toShortExactOrNull(var1);
      boolean var4;
      if (var3 != null) {
         var4 = var0.contains((Comparable)var3);
      } else {
         var4 = false;
      }

      return var4;
   }

   public static final CharProgression step(CharProgression var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$step");
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      RangesKt.checkStepIsPositive(var2, (Number)var1);
      CharProgression.Companion var3 = CharProgression.Companion;
      char var4 = var0.getFirst();
      char var5 = var0.getLast();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var3.fromClosedRange(var4, var5, var1);
   }

   public static final IntProgression step(IntProgression var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$step");
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      RangesKt.checkStepIsPositive(var2, (Number)var1);
      IntProgression.Companion var3 = IntProgression.Companion;
      int var4 = var0.getFirst();
      int var5 = var0.getLast();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var3.fromClosedRange(var4, var5, var1);
   }

   public static final LongProgression step(LongProgression var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$step");
      boolean var3;
      if (var1 > 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      RangesKt.checkStepIsPositive(var3, (Number)var1);
      LongProgression.Companion var4 = LongProgression.Companion;
      long var5 = var0.getFirst();
      long var7 = var0.getLast();
      if (var0.getStep() <= 0L) {
         var1 = -var1;
      }

      return var4.fromClosedRange(var5, var7, var1);
   }

   public static final Byte toByteExactOrNull(double var0) {
      double var2 = (double)-128;
      double var4 = (double)127;
      Byte var6;
      if (var0 >= var2 && var0 <= var4) {
         var6 = (byte)((int)var0);
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Byte toByteExactOrNull(float var0) {
      float var1 = (float)-128;
      float var2 = (float)127;
      Byte var3;
      if (var0 >= var1 && var0 <= var2) {
         var3 = (byte)((int)var0);
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final Byte toByteExactOrNull(int var0) {
      Byte var1;
      if (-128 <= var0 && 127 >= var0) {
         var1 = (byte)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   public static final Byte toByteExactOrNull(long var0) {
      long var2 = (long)-128;
      long var4 = (long)127;
      Byte var6;
      if (var2 <= var0 && var4 >= var0) {
         var6 = (byte)((int)var0);
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Byte toByteExactOrNull(short var0) {
      short var1 = (short)-128;
      short var2 = (short)127;
      Byte var3;
      if (var1 <= var0 && var2 >= var0) {
         var3 = (byte)var0;
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final Integer toIntExactOrNull(double var0) {
      double var2 = (double)Integer.MIN_VALUE;
      double var4 = (double)Integer.MAX_VALUE;
      Integer var6;
      if (var0 >= var2 && var0 <= var4) {
         var6 = (int)var0;
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Integer toIntExactOrNull(float var0) {
      float var1 = (float)Integer.MIN_VALUE;
      float var2 = (float)Integer.MAX_VALUE;
      Integer var3;
      if (var0 >= var1 && var0 <= var2) {
         var3 = (int)var0;
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final Integer toIntExactOrNull(long var0) {
      long var2 = (long)Integer.MIN_VALUE;
      long var4 = (long)Integer.MAX_VALUE;
      Integer var6;
      if (var2 <= var0 && var4 >= var0) {
         var6 = (int)var0;
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Long toLongExactOrNull(double var0) {
      double var2 = (double)Long.MIN_VALUE;
      double var4 = (double)Long.MAX_VALUE;
      Long var6;
      if (var0 >= var2 && var0 <= var4) {
         var6 = (long)var0;
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Long toLongExactOrNull(float var0) {
      float var1 = (float)Long.MIN_VALUE;
      float var2 = (float)Long.MAX_VALUE;
      Long var3;
      if (var0 >= var1 && var0 <= var2) {
         var3 = (long)var0;
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final Short toShortExactOrNull(double var0) {
      double var2 = (double)-32768;
      double var4 = (double)32767;
      Short var6;
      if (var0 >= var2 && var0 <= var4) {
         var6 = (short)((int)var0);
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final Short toShortExactOrNull(float var0) {
      float var1 = (float)-32768;
      float var2 = (float)32767;
      Short var3;
      if (var0 >= var1 && var0 <= var2) {
         var3 = (short)((int)var0);
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final Short toShortExactOrNull(int var0) {
      Short var1;
      if (-32768 <= var0 && 32767 >= var0) {
         var1 = (short)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   public static final Short toShortExactOrNull(long var0) {
      long var2 = (long)-32768;
      long var4 = (long)32767;
      Short var6;
      if (var2 <= var0 && var4 >= var0) {
         var6 = (short)((int)var0);
      } else {
         var6 = null;
      }

      return var6;
   }

   public static final CharRange until(char var0, char var1) {
      return var1 <= 0 ? CharRange.Companion.getEMPTY() : new CharRange(var0, (char)(var1 - 1));
   }

   public static final IntRange until(byte var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(byte var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(byte var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(int var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(int var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(int var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(short var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(short var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   public static final IntRange until(short var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   public static final LongRange until(byte var0, long var1) {
      return var1 <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)var0, var1 - 1L);
   }

   public static final LongRange until(int var0, long var1) {
      return var1 <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)var0, var1 - 1L);
   }

   public static final LongRange until(long var0, byte var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   public static final LongRange until(long var0, int var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   public static final LongRange until(long var0, long var2) {
      return var2 <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange(var0, var2 - 1L);
   }

   public static final LongRange until(long var0, short var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   public static final LongRange until(short var0, long var1) {
      return var1 <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange((long)var0, var1 - 1L);
   }
}
