/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.annotation.meta;

public final class When
extends Enum<When> {
    private static final /* synthetic */ When[] $VALUES;
    public static final /* enum */ When ALWAYS;
    public static final /* enum */ When MAYBE;
    public static final /* enum */ When NEVER;
    public static final /* enum */ When UNKNOWN;

    static {
        When when;
        ALWAYS = new When();
        UNKNOWN = new When();
        MAYBE = new When();
        NEVER = when = new When();
        $VALUES = new When[]{ALWAYS, UNKNOWN, MAYBE, when};
    }

    public static When valueOf(String string2) {
        return Enum.valueOf(When.class, string2);
    }

    public static When[] values() {
        return (When[])$VALUES.clone();
    }
}

