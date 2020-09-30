package io.opencensus.tags;

final class AutoValue_Tag extends Tag {
   private final TagKey key;
   private final TagMetadata tagMetadata;
   private final TagValue value;

   AutoValue_Tag(TagKey var1, TagValue var2, TagMetadata var3) {
      if (var1 != null) {
         this.key = var1;
         if (var2 != null) {
            this.value = var2;
            if (var3 != null) {
               this.tagMetadata = var3;
            } else {
               throw new NullPointerException("Null tagMetadata");
            }
         } else {
            throw new NullPointerException("Null value");
         }
      } else {
         throw new NullPointerException("Null key");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Tag)) {
         return false;
      } else {
         Tag var3 = (Tag)var1;
         if (!this.key.equals(var3.getKey()) || !this.value.equals(var3.getValue()) || !this.tagMetadata.equals(var3.getTagMetadata())) {
            var2 = false;
         }

         return var2;
      }
   }

   public TagKey getKey() {
      return this.key;
   }

   public TagMetadata getTagMetadata() {
      return this.tagMetadata;
   }

   public TagValue getValue() {
      return this.value;
   }

   public int hashCode() {
      return ((this.key.hashCode() ^ 1000003) * 1000003 ^ this.value.hashCode()) * 1000003 ^ this.tagMetadata.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Tag{key=");
      var1.append(this.key);
      var1.append(", value=");
      var1.append(this.value);
      var1.append(", tagMetadata=");
      var1.append(this.tagMetadata);
      var1.append("}");
      return var1.toString();
   }
}
