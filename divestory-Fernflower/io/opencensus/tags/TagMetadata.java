package io.opencensus.tags;

public abstract class TagMetadata {
   TagMetadata() {
   }

   public static TagMetadata create(TagMetadata.TagTtl var0) {
      return new AutoValue_TagMetadata(var0);
   }

   public abstract TagMetadata.TagTtl getTagTtl();

   public static enum TagTtl {
      NO_PROPAGATION(0),
      UNLIMITED_PROPAGATION;

      private final int hops;

      static {
         TagMetadata.TagTtl var0 = new TagMetadata.TagTtl("UNLIMITED_PROPAGATION", 1, -1);
         UNLIMITED_PROPAGATION = var0;
      }

      private TagTtl(int var3) {
         this.hops = var3;
      }
   }
}
