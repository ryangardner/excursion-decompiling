/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.CharDirectionality$Companion$directionalityMap
 */
package kotlin.text;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.CharDirectionality;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0086\u0001\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a\u00a8\u0006\u001c"}, d2={"Lkotlin/text/CharDirectionality;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "UNDEFINED", "LEFT_TO_RIGHT", "RIGHT_TO_LEFT", "RIGHT_TO_LEFT_ARABIC", "EUROPEAN_NUMBER", "EUROPEAN_NUMBER_SEPARATOR", "EUROPEAN_NUMBER_TERMINATOR", "ARABIC_NUMBER", "COMMON_NUMBER_SEPARATOR", "NONSPACING_MARK", "BOUNDARY_NEUTRAL", "PARAGRAPH_SEPARATOR", "SEGMENT_SEPARATOR", "WHITESPACE", "OTHER_NEUTRALS", "LEFT_TO_RIGHT_EMBEDDING", "LEFT_TO_RIGHT_OVERRIDE", "RIGHT_TO_LEFT_EMBEDDING", "RIGHT_TO_LEFT_OVERRIDE", "POP_DIRECTIONAL_FORMAT", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class CharDirectionality
extends Enum<CharDirectionality> {
    private static final /* synthetic */ CharDirectionality[] $VALUES;
    public static final /* enum */ CharDirectionality ARABIC_NUMBER;
    public static final /* enum */ CharDirectionality BOUNDARY_NEUTRAL;
    public static final /* enum */ CharDirectionality COMMON_NUMBER_SEPARATOR;
    public static final Companion Companion;
    public static final /* enum */ CharDirectionality EUROPEAN_NUMBER;
    public static final /* enum */ CharDirectionality EUROPEAN_NUMBER_SEPARATOR;
    public static final /* enum */ CharDirectionality EUROPEAN_NUMBER_TERMINATOR;
    public static final /* enum */ CharDirectionality LEFT_TO_RIGHT;
    public static final /* enum */ CharDirectionality LEFT_TO_RIGHT_EMBEDDING;
    public static final /* enum */ CharDirectionality LEFT_TO_RIGHT_OVERRIDE;
    public static final /* enum */ CharDirectionality NONSPACING_MARK;
    public static final /* enum */ CharDirectionality OTHER_NEUTRALS;
    public static final /* enum */ CharDirectionality PARAGRAPH_SEPARATOR;
    public static final /* enum */ CharDirectionality POP_DIRECTIONAL_FORMAT;
    public static final /* enum */ CharDirectionality RIGHT_TO_LEFT;
    public static final /* enum */ CharDirectionality RIGHT_TO_LEFT_ARABIC;
    public static final /* enum */ CharDirectionality RIGHT_TO_LEFT_EMBEDDING;
    public static final /* enum */ CharDirectionality RIGHT_TO_LEFT_OVERRIDE;
    public static final /* enum */ CharDirectionality SEGMENT_SEPARATOR;
    public static final /* enum */ CharDirectionality UNDEFINED;
    public static final /* enum */ CharDirectionality WHITESPACE;
    private static final Lazy directionalityMap$delegate;
    private final int value;

    static {
        CharDirectionality charDirectionality;
        CharDirectionality charDirectionality2;
        CharDirectionality charDirectionality3;
        CharDirectionality charDirectionality4;
        CharDirectionality charDirectionality5;
        CharDirectionality charDirectionality6;
        CharDirectionality charDirectionality7;
        CharDirectionality charDirectionality8;
        CharDirectionality charDirectionality9;
        CharDirectionality charDirectionality10;
        CharDirectionality charDirectionality11;
        CharDirectionality charDirectionality12;
        CharDirectionality charDirectionality13;
        CharDirectionality charDirectionality14;
        CharDirectionality charDirectionality15;
        CharDirectionality charDirectionality16;
        CharDirectionality charDirectionality17;
        CharDirectionality charDirectionality18;
        CharDirectionality charDirectionality19;
        CharDirectionality charDirectionality20;
        UNDEFINED = charDirectionality15 = new CharDirectionality(-1);
        LEFT_TO_RIGHT = charDirectionality16 = new CharDirectionality(0);
        RIGHT_TO_LEFT = charDirectionality = new CharDirectionality(1);
        RIGHT_TO_LEFT_ARABIC = charDirectionality9 = new CharDirectionality(2);
        EUROPEAN_NUMBER = charDirectionality5 = new CharDirectionality(3);
        EUROPEAN_NUMBER_SEPARATOR = charDirectionality20 = new CharDirectionality(4);
        EUROPEAN_NUMBER_TERMINATOR = charDirectionality12 = new CharDirectionality(5);
        ARABIC_NUMBER = charDirectionality8 = new CharDirectionality(6);
        COMMON_NUMBER_SEPARATOR = charDirectionality18 = new CharDirectionality(7);
        NONSPACING_MARK = charDirectionality17 = new CharDirectionality(8);
        BOUNDARY_NEUTRAL = charDirectionality4 = new CharDirectionality(9);
        PARAGRAPH_SEPARATOR = charDirectionality11 = new CharDirectionality(10);
        SEGMENT_SEPARATOR = charDirectionality7 = new CharDirectionality(11);
        WHITESPACE = charDirectionality2 = new CharDirectionality(12);
        OTHER_NEUTRALS = charDirectionality19 = new CharDirectionality(13);
        LEFT_TO_RIGHT_EMBEDDING = charDirectionality13 = new CharDirectionality(14);
        LEFT_TO_RIGHT_OVERRIDE = charDirectionality6 = new CharDirectionality(15);
        RIGHT_TO_LEFT_EMBEDDING = charDirectionality10 = new CharDirectionality(16);
        RIGHT_TO_LEFT_OVERRIDE = charDirectionality3 = new CharDirectionality(17);
        POP_DIRECTIONAL_FORMAT = charDirectionality14 = new CharDirectionality(18);
        $VALUES = new CharDirectionality[]{charDirectionality15, charDirectionality16, charDirectionality, charDirectionality9, charDirectionality5, charDirectionality20, charDirectionality12, charDirectionality8, charDirectionality18, charDirectionality17, charDirectionality4, charDirectionality11, charDirectionality7, charDirectionality2, charDirectionality19, charDirectionality13, charDirectionality6, charDirectionality10, charDirectionality3, charDirectionality14};
        Companion = new Companion(null);
        directionalityMap$delegate = LazyKt.lazy(Companion.directionalityMap.2.INSTANCE);
    }

    private CharDirectionality(int n2) {
        this.value = n2;
    }

    public static CharDirectionality valueOf(String string2) {
        return Enum.valueOf(CharDirectionality.class, string2);
    }

    public static CharDirectionality[] values() {
        return (CharDirectionality[])$VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005R'\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2={"Lkotlin/text/CharDirectionality$Companion;", "", "()V", "directionalityMap", "", "", "Lkotlin/text/CharDirectionality;", "getDirectionalityMap", "()Ljava/util/Map;", "directionalityMap$delegate", "Lkotlin/Lazy;", "valueOf", "directionality", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Map<Integer, CharDirectionality> getDirectionalityMap() {
            Lazy lazy = directionalityMap$delegate;
            Companion companion = Companion;
            return (Map)lazy.getValue();
        }

        public final CharDirectionality valueOf(int n) {
            Object object = this.getDirectionalityMap().get(n);
            if (object != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Directionality #");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is not defined.");
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

}

