/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.annotation;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/annotation/AnnotationRetention;", "", "(Ljava/lang/String;I)V", "SOURCE", "BINARY", "RUNTIME", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class AnnotationRetention
extends Enum<AnnotationRetention> {
    private static final /* synthetic */ AnnotationRetention[] $VALUES;
    public static final /* enum */ AnnotationRetention BINARY;
    public static final /* enum */ AnnotationRetention RUNTIME;
    public static final /* enum */ AnnotationRetention SOURCE;

    static {
        AnnotationRetention annotationRetention;
        AnnotationRetention annotationRetention2;
        AnnotationRetention annotationRetention3;
        SOURCE = annotationRetention2 = new AnnotationRetention();
        BINARY = annotationRetention = new AnnotationRetention();
        RUNTIME = annotationRetention3 = new AnnotationRetention();
        $VALUES = new AnnotationRetention[]{annotationRetention2, annotationRetention, annotationRetention3};
    }

    public static AnnotationRetention valueOf(String string2) {
        return Enum.valueOf(AnnotationRetention.class, string2);
    }

    public static AnnotationRetention[] values() {
        return (AnnotationRetention[])$VALUES.clone();
    }
}

