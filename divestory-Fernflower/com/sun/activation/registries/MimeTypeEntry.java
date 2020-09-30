package com.sun.activation.registries;

public class MimeTypeEntry {
   private String extension;
   private String type;

   public MimeTypeEntry(String var1, String var2) {
      this.type = var1;
      this.extension = var2;
   }

   public String getFileExtension() {
      return this.extension;
   }

   public String getMIMEType() {
      return this.type;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("MIMETypeEntry: ");
      var1.append(this.type);
      var1.append(", ");
      var1.append(this.extension);
      return var1.toString();
   }
}
