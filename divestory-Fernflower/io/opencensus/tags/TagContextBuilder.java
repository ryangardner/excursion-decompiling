package io.opencensus.tags;

import io.opencensus.common.Scope;

public abstract class TagContextBuilder {
   private static final TagMetadata METADATA_NO_PROPAGATION;
   private static final TagMetadata METADATA_UNLIMITED_PROPAGATION;

   static {
      METADATA_NO_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.NO_PROPAGATION);
      METADATA_UNLIMITED_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.UNLIMITED_PROPAGATION);
   }

   public abstract TagContext build();

   public abstract Scope buildScoped();

   @Deprecated
   public abstract TagContextBuilder put(TagKey var1, TagValue var2);

   public TagContextBuilder put(TagKey var1, TagValue var2, TagMetadata var3) {
      return this.put(var1, var2);
   }

   public final TagContextBuilder putLocal(TagKey var1, TagValue var2) {
      return this.put(var1, var2, METADATA_NO_PROPAGATION);
   }

   public final TagContextBuilder putPropagating(TagKey var1, TagValue var2) {
      return this.put(var1, var2, METADATA_UNLIMITED_PROPAGATION);
   }

   public abstract TagContextBuilder remove(TagKey var1);
}
