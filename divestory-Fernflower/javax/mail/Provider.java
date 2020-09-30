package javax.mail;

public class Provider {
   private String className;
   private String protocol;
   private Provider.Type type;
   private String vendor;
   private String version;

   public Provider(Provider.Type var1, String var2, String var3, String var4, String var5) {
      this.type = var1;
      this.protocol = var2;
      this.className = var3;
      this.vendor = var4;
      this.version = var5;
   }

   public String getClassName() {
      return this.className;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public Provider.Type getType() {
      return this.type;
   }

   public String getVendor() {
      return this.vendor;
   }

   public String getVersion() {
      return this.version;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("javax.mail.Provider[");
      var1.append(this.type);
      var1.append(",");
      var1.append(this.protocol);
      var1.append(",");
      var1.append(this.className);
      String var2 = var1.toString();
      String var3 = var2;
      if (this.vendor != null) {
         var1 = new StringBuilder(String.valueOf(var2));
         var1.append(",");
         var1.append(this.vendor);
         var3 = var1.toString();
      }

      var2 = var3;
      if (this.version != null) {
         var1 = new StringBuilder(String.valueOf(var3));
         var1.append(",");
         var1.append(this.version);
         var2 = var1.toString();
      }

      var1 = new StringBuilder(String.valueOf(var2));
      var1.append("]");
      return var1.toString();
   }

   public static class Type {
      public static final Provider.Type STORE = new Provider.Type("STORE");
      public static final Provider.Type TRANSPORT = new Provider.Type("TRANSPORT");
      private String type;

      private Type(String var1) {
         this.type = var1;
      }

      public String toString() {
         return this.type;
      }
   }
}
