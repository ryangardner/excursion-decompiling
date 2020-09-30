package javax.mail.internet;

public class AddressException extends ParseException {
   private static final long serialVersionUID = 9134583443539323120L;
   protected int pos = -1;
   protected String ref = null;

   public AddressException() {
   }

   public AddressException(String var1) {
      super(var1);
   }

   public AddressException(String var1, String var2) {
      super(var1);
      this.ref = var2;
   }

   public AddressException(String var1, String var2, int var3) {
      super(var1);
      this.ref = var2;
      this.pos = var3;
   }

   public int getPos() {
      return this.pos;
   }

   public String getRef() {
      return this.ref;
   }

   public String toString() {
      String var1 = super.toString();
      if (this.ref == null) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder(String.valueOf(var1));
         var2.append(" in string ``");
         var2.append(this.ref);
         var2.append("''");
         var1 = var2.toString();
         if (this.pos < 0) {
            return var1;
         } else {
            var2 = new StringBuilder(String.valueOf(var1));
            var2.append(" at position ");
            var2.append(this.pos);
            return var2.toString();
         }
      }
   }
}
