/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.AutoValue_TagMetadata;

public abstract class TagMetadata {
    TagMetadata() {
    }

    public static TagMetadata create(TagTtl tagTtl) {
        return new AutoValue_TagMetadata(tagTtl);
    }

    public abstract TagTtl getTagTtl();

    public static final class TagTtl
    extends Enum<TagTtl> {
        private static final /* synthetic */ TagTtl[] $VALUES;
        public static final /* enum */ TagTtl NO_PROPAGATION;
        public static final /* enum */ TagTtl UNLIMITED_PROPAGATION;
        private final int hops;

        static {
            TagTtl tagTtl;
            NO_PROPAGATION = new TagTtl(0);
            UNLIMITED_PROPAGATION = tagTtl = new TagTtl(-1);
            $VALUES = new TagTtl[]{NO_PROPAGATION, tagTtl};
        }

        private TagTtl(int n2) {
            this.hops = n2;
        }

        public static TagTtl valueOf(String string2) {
            return Enum.valueOf(TagTtl.class, string2);
        }

        public static TagTtl[] values() {
            return (TagTtl[])$VALUES.clone();
        }
    }

}

