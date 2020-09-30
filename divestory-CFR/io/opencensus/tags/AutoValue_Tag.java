/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.Tag;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagMetadata;
import io.opencensus.tags.TagValue;

final class AutoValue_Tag
extends Tag {
    private final TagKey key;
    private final TagMetadata tagMetadata;
    private final TagValue value;

    AutoValue_Tag(TagKey tagKey, TagValue tagValue, TagMetadata tagMetadata) {
        if (tagKey == null) throw new NullPointerException("Null key");
        this.key = tagKey;
        if (tagValue == null) throw new NullPointerException("Null value");
        this.value = tagValue;
        if (tagMetadata == null) throw new NullPointerException("Null tagMetadata");
        this.tagMetadata = tagMetadata;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Tag)) return false;
        if (!this.key.equals(((Tag)(object = (Tag)object)).getKey())) return false;
        if (!this.value.equals(((Tag)object).getValue())) return false;
        if (!this.tagMetadata.equals(((Tag)object).getTagMetadata())) return false;
        return bl;
    }

    @Override
    public TagKey getKey() {
        return this.key;
    }

    @Override
    public TagMetadata getTagMetadata() {
        return this.tagMetadata;
    }

    @Override
    public TagValue getValue() {
        return this.value;
    }

    public int hashCode() {
        return ((this.key.hashCode() ^ 1000003) * 1000003 ^ this.value.hashCode()) * 1000003 ^ this.tagMetadata.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tag{key=");
        stringBuilder.append(this.key);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append(", tagMetadata=");
        stringBuilder.append(this.tagMetadata);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

