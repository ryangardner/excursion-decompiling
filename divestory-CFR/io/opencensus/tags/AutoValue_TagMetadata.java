/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.TagMetadata;

final class AutoValue_TagMetadata
extends TagMetadata {
    private final TagMetadata.TagTtl tagTtl;

    AutoValue_TagMetadata(TagMetadata.TagTtl tagTtl) {
        if (tagTtl == null) throw new NullPointerException("Null tagTtl");
        this.tagTtl = tagTtl;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TagMetadata)) return false;
        object = (TagMetadata)object;
        return this.tagTtl.equals((Object)((TagMetadata)object).getTagTtl());
    }

    @Override
    public TagMetadata.TagTtl getTagTtl() {
        return this.tagTtl;
    }

    public int hashCode() {
        return this.tagTtl.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TagMetadata{tagTtl=");
        stringBuilder.append((Object)this.tagTtl);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

