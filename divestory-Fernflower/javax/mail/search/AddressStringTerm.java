package javax.mail.search;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

public abstract class AddressStringTerm extends StringTerm {
   private static final long serialVersionUID = 3086821234204980368L;

   protected AddressStringTerm(String var1) {
      super(var1, true);
   }

   public boolean equals(Object var1) {
      return !(var1 instanceof AddressStringTerm) ? false : super.equals(var1);
   }

   protected boolean match(Address var1) {
      return var1 instanceof InternetAddress ? super.match(((InternetAddress)var1).toUnicodeString()) : super.match(var1.toString());
   }
}
