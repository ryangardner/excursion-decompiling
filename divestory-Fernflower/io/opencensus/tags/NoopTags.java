package io.opencensus.tags;

import io.opencensus.common.Scope;
import io.opencensus.internal.NoopScope;
import io.opencensus.internal.Utils;
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
      return NoopTags.NoopTagContext.INSTANCE;
   }

   static TagContextBinarySerializer getNoopTagContextBinarySerializer() {
      return NoopTags.NoopTagContextBinarySerializer.INSTANCE;
   }

   static TagContextBuilder getNoopTagContextBuilder() {
      return NoopTags.NoopTagContextBuilder.INSTANCE;
   }

   static TagContextTextFormat getNoopTagContextTextSerializer() {
      return NoopTags.NoopTagContextTextFormat.INSTANCE;
   }

   static TagPropagationComponent getNoopTagPropagationComponent() {
      return NoopTags.NoopTagPropagationComponent.INSTANCE;
   }

   static Tagger getNoopTagger() {
      return NoopTags.NoopTagger.INSTANCE;
   }

   static TagsComponent newNoopTagsComponent() {
      return new NoopTags.NoopTagsComponent();
   }

   private static final class NoopTagContext extends TagContext {
      static final TagContext INSTANCE = new NoopTags.NoopTagContext();

      protected Iterator<Tag> getIterator() {
         return Collections.emptySet().iterator();
      }
   }

   private static final class NoopTagContextBinarySerializer extends TagContextBinarySerializer {
      static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
      static final TagContextBinarySerializer INSTANCE = new NoopTags.NoopTagContextBinarySerializer();

      public TagContext fromByteArray(byte[] var1) {
         Utils.checkNotNull(var1, "bytes");
         return NoopTags.getNoopTagContext();
      }

      public byte[] toByteArray(TagContext var1) {
         Utils.checkNotNull(var1, "tags");
         return EMPTY_BYTE_ARRAY;
      }
   }

   private static final class NoopTagContextBuilder extends TagContextBuilder {
      static final TagContextBuilder INSTANCE = new NoopTags.NoopTagContextBuilder();

      public TagContext build() {
         return NoopTags.getNoopTagContext();
      }

      public Scope buildScoped() {
         return NoopScope.getInstance();
      }

      public TagContextBuilder put(TagKey var1, TagValue var2) {
         Utils.checkNotNull(var1, "key");
         Utils.checkNotNull(var2, "value");
         return this;
      }

      public TagContextBuilder put(TagKey var1, TagValue var2, TagMetadata var3) {
         Utils.checkNotNull(var1, "key");
         Utils.checkNotNull(var2, "value");
         Utils.checkNotNull(var3, "tagMetadata");
         return this;
      }

      public TagContextBuilder remove(TagKey var1) {
         Utils.checkNotNull(var1, "key");
         return this;
      }
   }

   private static final class NoopTagContextTextFormat extends TagContextTextFormat {
      static final NoopTags.NoopTagContextTextFormat INSTANCE = new NoopTags.NoopTagContextTextFormat();

      public <C> TagContext extract(C var1, TagContextTextFormat.Getter<C> var2) throws TagContextDeserializationException {
         Utils.checkNotNull(var1, "carrier");
         Utils.checkNotNull(var2, "getter");
         return NoopTags.getNoopTagContext();
      }

      public List<String> fields() {
         return Collections.emptyList();
      }

      public <C> void inject(TagContext var1, C var2, TagContextTextFormat.Setter<C> var3) throws TagContextSerializationException {
         Utils.checkNotNull(var1, "tagContext");
         Utils.checkNotNull(var2, "carrier");
         Utils.checkNotNull(var3, "setter");
      }
   }

   private static final class NoopTagPropagationComponent extends TagPropagationComponent {
      static final TagPropagationComponent INSTANCE = new NoopTags.NoopTagPropagationComponent();

      public TagContextBinarySerializer getBinarySerializer() {
         return NoopTags.getNoopTagContextBinarySerializer();
      }

      public TagContextTextFormat getCorrelationContextFormat() {
         return NoopTags.getNoopTagContextTextSerializer();
      }
   }

   private static final class NoopTagger extends Tagger {
      static final Tagger INSTANCE = new NoopTags.NoopTagger();

      public TagContextBuilder currentBuilder() {
         return NoopTags.getNoopTagContextBuilder();
      }

      public TagContext empty() {
         return NoopTags.getNoopTagContext();
      }

      public TagContextBuilder emptyBuilder() {
         return NoopTags.getNoopTagContextBuilder();
      }

      public TagContext getCurrentTagContext() {
         return NoopTags.getNoopTagContext();
      }

      public TagContextBuilder toBuilder(TagContext var1) {
         Utils.checkNotNull(var1, "tags");
         return NoopTags.getNoopTagContextBuilder();
      }

      public Scope withTagContext(TagContext var1) {
         Utils.checkNotNull(var1, "tags");
         return NoopScope.getInstance();
      }
   }

   private static final class NoopTagsComponent extends TagsComponent {
      private volatile boolean isRead;

      private NoopTagsComponent() {
      }

      // $FF: synthetic method
      NoopTagsComponent(Object var1) {
         this();
      }

      public TaggingState getState() {
         this.isRead = true;
         return TaggingState.DISABLED;
      }

      public TagPropagationComponent getTagPropagationComponent() {
         return NoopTags.getNoopTagPropagationComponent();
      }

      public Tagger getTagger() {
         return NoopTags.getNoopTagger();
      }

      @Deprecated
      public void setState(TaggingState var1) {
         Utils.checkNotNull(var1, "state");
         Utils.checkState(this.isRead ^ true, "State was already read, cannot set state.");
      }
   }
}
