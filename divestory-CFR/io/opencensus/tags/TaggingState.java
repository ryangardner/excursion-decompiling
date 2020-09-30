/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

public final class TaggingState
extends Enum<TaggingState> {
    private static final /* synthetic */ TaggingState[] $VALUES;
    public static final /* enum */ TaggingState DISABLED;
    public static final /* enum */ TaggingState ENABLED;

    static {
        TaggingState taggingState;
        ENABLED = new TaggingState();
        DISABLED = taggingState = new TaggingState();
        $VALUES = new TaggingState[]{ENABLED, taggingState};
    }

    public static TaggingState valueOf(String string2) {
        return Enum.valueOf(TaggingState.class, string2);
    }

    public static TaggingState[] values() {
        return (TaggingState[])$VALUES.clone();
    }
}

