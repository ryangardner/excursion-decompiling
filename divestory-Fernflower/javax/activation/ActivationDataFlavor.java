package javax.activation;

import myjava.awt.datatransfer.DataFlavor;

public class ActivationDataFlavor extends DataFlavor {
   private String humanPresentableName = null;
   private MimeType mimeObject = null;
   private String mimeType = null;
   private Class representationClass = null;

   public ActivationDataFlavor(Class var1, String var2) {
      super(var1, var2);
      this.mimeType = super.getMimeType();
      this.representationClass = var1;
      this.humanPresentableName = var2;
   }

   public ActivationDataFlavor(Class var1, String var2, String var3) {
      super(var2, var3);
      this.mimeType = var2;
      this.humanPresentableName = var3;
      this.representationClass = var1;
   }

   public ActivationDataFlavor(String var1, String var2) {
      super(var1, var2);
      this.mimeType = var1;

      try {
         this.representationClass = Class.forName("java.io.InputStream");
      } catch (ClassNotFoundException var3) {
      }

      this.humanPresentableName = var2;
   }

   public boolean equals(DataFlavor var1) {
      return this.isMimeTypeEqual(var1) && var1.getRepresentationClass() == this.representationClass;
   }

   public String getHumanPresentableName() {
      return this.humanPresentableName;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public Class getRepresentationClass() {
      return this.representationClass;
   }

   public boolean isMimeTypeEqual(String var1) {
      MimeType var2;
      try {
         if (this.mimeObject == null) {
            var2 = new MimeType(this.mimeType);
            this.mimeObject = var2;
         }

         var2 = new MimeType(var1);
      } catch (MimeTypeParseException var3) {
         return this.mimeType.equalsIgnoreCase(var1);
      }

      return this.mimeObject.match(var2);
   }

   protected String normalizeMimeType(String var1) {
      return var1;
   }

   protected String normalizeMimeTypeParameter(String var1, String var2) {
      return var2;
   }

   public void setHumanPresentableName(String var1) {
      this.humanPresentableName = var1;
   }
}
