/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.common.Scope;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagMetadata;
import io.opencensus.tags.TagValue;

public abstract class TagContextBuilder {
    private static final TagMetadata METADATA_NO_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.NO_PROPAGATION);
    private static final TagMetadata METADATA_UNLIMITED_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.UNLIMITED_PROPAGATION);

    public abstract TagContext build();

    public abstract Scope buildScoped();

    @Deprecated
    public abstract TagContextBuilder put(TagKey var1, TagValue var2);

    public TagContextBuilder put(TagKey tagKey, TagValue tagValue, TagMetadata tagMetadata) {
        return this.put(tagKey, tagValue);
    }

    public final TagContextBuilder putLocal(TagKey tagKey, TagValue tagValue) {
        return this.put(tagKey, tagValue, METADATA_NO_PROPAGATION);
    }

    public final TagContextBuilder putPropagating(TagKey tagKey, TagValue tagValue) {
        return this.put(tagKey, tagValue, METADATA_UNLIMITED_PROPAGATION);
    }

    public abstract TagContextBuilder remove(TagKey var1);
}

