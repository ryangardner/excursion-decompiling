package javax.mail.search;

import java.util.Locale;
import javax.mail.Message;

public final class HeaderTerm extends StringTerm {
   private static final long serialVersionUID = 8342514650333389122L;
   protected String headerName;

   public HeaderTerm(String var1, String var2) {
      super(var2);
      this.headerName = var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof HeaderTerm)) {
         return false;
      } else {
         HeaderTerm var2 = (HeaderTerm)var1;
         return var2.headerName.equalsIgnoreCase(this.headerName) && super.equals(var2);
      }
   }

   public String getHeaderName() {
      return this.headerName;
   }

   public int hashCode() {
      return this.headerName.toLowerCase(Locale.ENGLISH).hashCode() + super.hashCode();
   }

   public boolean match(Message var1) {
      String[] var4;
      try {
         var4 = var1.getHeader(this.headerName);
      } catch (Exception var3) {
         return false;
      }

      if (var4 == null) {
         return false;
      } else {
         for(int var2 = 0; var2 < var4.length; ++var2) {
            if (super.match(var4[var2])) {
               return true;
            }
         }

         return false;
      }
   }
}
