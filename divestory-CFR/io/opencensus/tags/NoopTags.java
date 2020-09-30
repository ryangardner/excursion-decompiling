/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.common.Scope;
import io.opencensus.internal.NoopScope;
import io.opencensus.internal.Utils;
import io.opencensus.tags.Tag;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagContextBuilder;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagMetadata;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.TaggingState;
import io.opencensus.tags.TagsComponent;
import io.opencensus.tags.propagation.TagContextBinarySerializer;
import io.opencensus.tags.propagation.TagContextDeserializationException;
import io.opencensus.tags.propagation.TagContextSerializationException;
import io.opencensus.tags.propagation.TagContextTextFormat;
import io.opencensus.tags.propagation.TagPropagationComponent;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class NoopTags {
    private NoopTags() {
    }

    static TagContext getNoopTagContext() {
        return NoopTagContext.INSTANCE;
    }

    static TagContextBinarySerializer getNoopTagContextBinarySerializer() {
        return NoopTagContextBinarySerializer.INSTANCE;
    }

    static TagContextBuilder getNoopTagContextBuilder() {
        return NoopTagContextBuilder.INSTANCE;
    }

    static TagContextTextFormat getNoopTagContextTextSerializer() {
        return NoopTagContextTextFormat.INSTANCE;
    }

    static TagPropagationComponent getNoopTagPropagationComponent() {
        return NoopTagPropagationComponent.INSTANCE;
    }

    static Tagger getNoopTagger() {
        return NoopTagger.INSTANCE;
    }

    static TagsComponent newNoopTagsComponent() {
        return new NoopTagsComponent();
    }

    private static final class NoopTagContext
    extends TagContext {
        static final TagContext INSTANCE = new NoopTagContext();

        private NoopTagContext() {
        }

        @Override
        protected Iterator<Tag> getIterator() {
            return Collections.emptySet().iterator();
        }
    }

    private static final class NoopTagContextBinarySerializer
    extends TagContextBinarySerializer {
        static final byte[] EMPTY_BYTE_ARRAY;
        static final TagContextBinarySerializer INSTANCE;

        static {
            INSTANCE = new NoopTagContextBinarySerializer();
            EMPTY_BYTE_ARRAY = new byte[0];
        }

        private NoopTagContextBinarySerializer() {
        }

        @Override
        public TagContext fromByteArray(byte[] arrby) {
            Utils.checkNotNull(arrby, "bytes");
            return NoopTags.getNoopTagContext();
        }

        @Override
        public byte[] toByteArray(TagContext tagContext) {
            Utils.checkNotNull(tagContext, "tags");
            return EMPTY_BYTE_ARRAY;
        }
    }

    private static final class NoopTagContextBuilder
    extends TagContextBuilder {
        static final TagContextBuilder INSTANCE = new NoopTagContextBuilder();

        private NoopTagContextBuilder() {
        }

        @Override
        public TagContext build() {
            return NoopTags.getNoopTagContext();
        }

        @Override
        public Scope buildScoped() {
            return NoopScope.getInstance();
        }

        @Override
        public TagContextBuilder put(TagKey tagKey, TagValue tagValue) {
            Utils.checkNotNull(tagKey, "key");
            Utils.checkNotNull(tagValue, "value");
            return this;
        }

        @Override
        public TagContextBuilder put(TagKey tagKey, TagValue tagValue, TagMetadata tagMetadata) {
            Utils.checkNotNull(tagKey, "key");
            Utils.checkNotNull(tagValue, "value");
            Utils.checkNotNull(tagMetadata, "tagMetadata");
            return this;
        }

        @Override
        public TagContextBuilder remove(TagKey tagKey) {
            Utils.checkNotNull(tagKey, "key");
            return this;
        }
    }

    private static final class NoopTagContextTextFormat
    extends TagContextTextFormat {
        static final NoopTagContextTextFormat INSTANCE = new NoopTagContextTextFormat();

        private NoopTagContextTextFormat() {
        }

        @Override
        public <C> TagContext extract(C c, TagContextTextFormat.Getter<C> getter) throws TagContextDeserializationException {
            Utils.checkNotNull(c, "carrier");
            Utils.checkNotNull(getter, "getter");
            return NoopTags.getNoopTagContext();
        }

        @Override
        public List<String> fields() {
            return Collections.emptyList();
        }

        @Override
        public <C> void inject(TagContext tagContext, C c, TagContextTextFormat.Setter<C> setter) throws TagContextSerializationException {
            Utils.checkNotNull(tagContext, "tagContext");
            Utils.checkNotNull(c, "carrier");
            Utils.checkNotNull(setter, "setter");
        }
    }

    private static final class NoopTagPropagationComponent
    extends TagPropagationComponent {
        static final TagPropagationComponent INSTANCE = new NoopTagPropagationComponent();

        private NoopTagPropagationComponent() {
        }

        @Override
        public TagContextBinarySerializer getBinarySerializer() {
            return NoopTags.getNoopTagContextBinarySerializer();
        }

        @Override
        public TagContextTextFormat getCorrelationContextFormat() {
            return NoopTags.getNoopTagContextTextSerializer();
        }
    }

    private static final class NoopTagger
    extends Tagger {
        static final Tagger INSTANCE = new NoopTagger();

        private NoopTagger() {
        }

        @Override
        public TagContextBuilder currentBuilder() {
            return NoopTags.getNoopTagContextBuilder();
        }

        @Override
        public TagContext empty() {
            return NoopTags.getNoopTagContext();
        }

        @Override
        public TagContextBuilder emptyBuilder() {
            return NoopTags.getNoopTagContextBuilder();
        }

        @Override
        public TagContext getCurrentTagContext() {
            return NoopTags.getNoopTagContext();
        }

        @Override
        public TagContextBuilder toBuilder(TagContext tagContext) {
            Utils.checkNotNull(tagContext, "tags");
            return NoopTags.getNoopTagContextBuilder();
        }

        @Override
        public Scope withTagContext(TagContext tagContext) {
            Utils.checkNotNull(tagContext, "tags");
            return NoopScope.getInstance();
        }
    }

    private static final class NoopTagsComponent
    extends TagsComponent {
        private volatile boolean isRead;

        private NoopTagsComponent() {
        }

        @Override
        public TaggingState getState() {
            this.isRead = true;
            return TaggingState.DISABLED;
        }

        @Override
        public TagPropagationComponent getTagPropagationComponent() {
            return NoopTags.getNoopTagPropagationComponent();
        }

        @Override
        public Tagger getTagger() {
            return NoopTags.getNoopTagger();
        }

        @Deprecated
        @Override
        public void setState(TaggingState taggingState) {
            Utils.checkNotNull(taggingState, "state");
            Utils.checkState(this.isRead ^ true, "State was already read, cannot set state.");
        }
    }

}

