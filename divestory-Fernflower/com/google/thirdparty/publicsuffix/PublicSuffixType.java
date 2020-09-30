package com.google.thirdparty.publicsuffix;

public enum PublicSuffixType {
   PRIVATE(':', ','),
   REGISTRY;

   private final char innerNodeCode;
   private final char leafNodeCode;

   static {
      PublicSuffixType var0 = new PublicSuffixType("REGISTRY", 1, '!', '?');
      REGISTRY = var0;
   }

   private PublicSuffixType(char var3, char var4) {
      this.innerNodeCode = (char)var3;
      this.leafNodeCode = (char)var4;
   }

   static PublicSuffixType fromCode(char var0) {
      PublicSuffixType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PublicSuffixType var4 = var1[var3];
         if (var4.getInnerNodeCode() == var0 || var4.getLeafNodeCode() == var0) {
            return var4;
         }
      }

      StringBuilder var5 = new StringBuilder();
      var5.append("No enum corresponding to given code: ");
      var5.append(var0);
      throw new IllegalArgumentException(var5.toString());
   }

   static PublicSuffixType fromIsPrivate(boolean var0) {
      PublicSuffixType var1;
      if (var0) {
         var1 = PRIVATE;
      } else {
         var1 = REGISTRY;
      }

      return var1;
   }

   char getInnerNodeCode() {
      return this.innerNodeCode;
   }

   char getLeafNodeCode() {
      return this.leafNodeCode;
   }
}
