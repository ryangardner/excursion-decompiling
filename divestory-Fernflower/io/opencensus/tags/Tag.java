package io.opencensus.tags;

public abstract class Tag {
   private static final TagMetadata METADATA_UNLIMITED_PROPAGATION;

   static {
      METADATA_UNLIMITED_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.UNLIMITED_PROPAGATION);
   }

   Tag() {
   }

   @Deprecated
   public static Tag create(TagKey var0, TagValue var1) {
      return create(var0, var1, METADATA_UNLIMITED_PROPAGATION);
   }

   public static Tag create(TagKey var0, TagValue var1, TagMetadata var2) {
      return new AutoValue_Tag(var0, var1, var2);
   }

   public abstract TagKey getKey();

   public abstract TagMetadata getTagMetadata();

   public abstract TagValue getValue();
}
