package io.opencensus.tags;

final class AutoValue_TagMetadata extends TagMetadata {
   private final TagMetadata.TagTtl tagTtl;

   AutoValue_TagMetadata(TagMetadata.TagTtl var1) {
      if (var1 != null) {
         this.tagTtl = var1;
      } else {
         throw new NullPointerException("Null tagTtl");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof TagMetadata) {
         TagMetadata var2 = (TagMetadata)var1;
         return this.tagTtl.equals(var2.getTagTtl());
      } else {
         return false;
      }
   }

   public TagMetadata.TagTtl getTagTtl() {
      return this.tagTtl;
   }

   public int hashCode() {
      return this.tagTtl.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TagMetadata{tagTtl=");
      var1.append(this.tagTtl);
      var1.append("}");
      return var1.toString();
   }
}
